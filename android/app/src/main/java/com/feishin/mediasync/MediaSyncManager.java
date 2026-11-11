package com.feishin.mediasync;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MediaSyncManager {

    private static final String TAG = "MediaSyncManager";
    private static MediaSyncManager instance;

    private static final Set<String> SUPPORTED_EXTENSIONS = new HashSet<>(Arrays.asList(
            ".mp3", ".flac", ".m4a", ".wav", ".ogg", ".opus", ".aac", ".wma", ".alac", ".aiff"
    ));

    private final Context context;
    private final SyncStorage storage;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private final AtomicBoolean isSyncing = new AtomicBoolean(false);
    private Future<?> currentSyncTask;
    private JSONObject cachedVpsSummary = null;
    private long lastVpsSummaryFetchTime = 0;

    public interface SyncListener {
        void onProgress(String state, JSONObject details);
    }

    private SyncListener activeListener;

    public static synchronized MediaSyncManager getInstance(Context context) {
        if (instance == null) {
            instance = new MediaSyncManager(context.getApplicationContext());
        }
        return instance;
    }

    private MediaSyncManager(Context context) {
        this.context = context;
        this.storage = new SyncStorage(context);
        setupTrustAllSsl();
    }

    public void setListener(SyncListener listener) {
        this.activeListener = listener;
    }

    public SyncStorage getStorage() {
        return storage;
    }

    public boolean isSyncing() {
        return isSyncing.get();
    }

    private void setupTrustAllSsl() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            Log.e(TAG, "SSL setup error: " + e.getMessage());
        }
    }

    public static final String VPS_TAILSCALE_IP = "100.99.207.96";
    public static final String VPS_TAILSCALE_HOST = "demo-navidrome.feishin.pro";

    private static SSLSocketFactory createSniSocketFactory(final String sniHost) {
        final SSLSocketFactory defaultFactory = HttpsURLConnection.getDefaultSSLSocketFactory();
        return new SSLSocketFactory() {
            private java.net.Socket configureSni(java.net.Socket s) {
                if (s instanceof SSLSocket && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    SSLSocket sslSocket = (SSLSocket) s;
                    SSLParameters params = sslSocket.getSSLParameters();
                    params.setServerNames(Collections.singletonList(new SNIHostName(sniHost)));
                    sslSocket.setSSLParameters(params);
                }
                return s;
            }

            @Override
            public String[] getDefaultCipherSuites() { return defaultFactory.getDefaultCipherSuites(); }
            @Override
            public String[] getSupportedCipherSuites() { return defaultFactory.getSupportedCipherSuites(); }
            @Override
            public java.net.Socket createSocket(java.net.Socket s, String host, int port, boolean autoClose) throws java.io.IOException {
                return configureSni(defaultFactory.createSocket(s, sniHost, port, autoClose));
            }
            @Override
            public java.net.Socket createSocket(String host, int port) throws java.io.IOException {
                return configureSni(defaultFactory.createSocket(host, port));
            }
            @Override
            public java.net.Socket createSocket(String host, int port, java.net.InetAddress localHost, int localPort) throws java.io.IOException {
                return configureSni(defaultFactory.createSocket(host, port, localHost, localPort));
            }
            @Override
            public java.net.Socket createSocket(java.net.InetAddress host, int port) throws java.io.IOException {
                return configureSni(defaultFactory.createSocket(host, port));
            }
            @Override
            public java.net.Socket createSocket(java.net.InetAddress address, int port, java.net.InetAddress localAddress, int localPort) throws java.io.IOException {
                return configureSni(defaultFactory.createSocket(address, port, localAddress, localPort));
            }
        };
    }

    public HttpURLConnection openSmartConnection(String urlStr) throws Exception {
        URL originalUrl = new URL(urlStr);
        String host = originalUrl.getHost();
        int port = originalUrl.getPort() != -1 ? originalUrl.getPort() : (originalUrl.getProtocol().equalsIgnoreCase("https") ? 443 : 80);

        if (VPS_TAILSCALE_HOST.equalsIgnoreCase(host)) {
            boolean useDirectTailnetIp = false;
            try {
                java.net.InetAddress addr = java.net.InetAddress.getByName(host);
                String ip = addr.getHostAddress();
                // Se o Android Private DNS não retornar o IP da Tailnet (100.x ou fd7a:), rotear diretamente para o IP Tailnet com SNI
                if (ip == null || (!ip.startsWith("100.") && !ip.startsWith("fd7a:"))) {
                    useDirectTailnetIp = true;
                }
            } catch (Exception e) {
                useDirectTailnetIp = true;
            }

            if (useDirectTailnetIp) {
                URL directUrl = new URL(originalUrl.getProtocol(), VPS_TAILSCALE_IP, port, originalUrl.getFile());
                HttpURLConnection conn = (HttpURLConnection) directUrl.openConnection();
                conn.setRequestProperty("Host", host);
                if (conn instanceof HttpsURLConnection) {
                    HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
                    httpsConn.setSSLSocketFactory(createSniSocketFactory(host));
                    httpsConn.setHostnameVerifier((h, session) -> true);
                }
                return conn;
            }
        }
        HttpURLConnection conn = (HttpURLConnection) originalUrl.openConnection();
        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection) conn).setHostnameVerifier((h, session) -> true);
        }
        return conn;
    }

    public List<File> getMonitoredDirectories() {
        List<File> dirs = new ArrayList<>();
        java.util.Set<String> addedPaths = new java.util.HashSet<>();

        // 1. Diretório padrão Music (/storage/emulated/0/Music)
        File musicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        if (musicDir != null && musicDir.exists()) {
            try {
                File canonical = musicDir.getCanonicalFile();
                dirs.add(canonical);
                addedPaths.add(canonical.getAbsolutePath());
            } catch (Exception e) {
                dirs.add(musicDir);
                addedPaths.add(musicDir.getAbsolutePath());
            }
        }
        File fallbackMusic = new File("/sdcard/Music");
        if (fallbackMusic.exists()) {
            try {
                File canonical = fallbackMusic.getCanonicalFile();
                if (addedPaths.add(canonical.getAbsolutePath())) {
                    dirs.add(canonical);
                }
            } catch (Exception ignored) {}
        }

        // 2. SnapTube Audio específico
        File[] possibleSnaptubeDirs = new File[]{
                new File("/sdcard/snaptube/download/SnapTube Audio"),
                new File("/storage/emulated/0/snaptube/download/SnapTube Audio"),
                new File("/sdcard/Download/SnapTube Audio"),
                new File("/storage/emulated/0/Download/SnapTube Audio"),
                new File(Environment.getExternalStorageDirectory(), "snaptube/download/SnapTube Audio"),
                new File(Environment.getExternalStorageDirectory(), "SnapTube/download/SnapTube Audio"),
                new File(Environment.getExternalStorageDirectory(), "Download/SnapTube Audio"),
                new File("/storage/emulated/0/Download/SnapTube"),
                new File("/sdcard/snaptube/download"),
                new File("/storage/emulated/0/snaptube/download")
        };
        for (File candidate : possibleSnaptubeDirs) {
            if (candidate != null && candidate.exists() && candidate.isDirectory()) {
                try {
                    File canonical = candidate.getCanonicalFile();
                    if (addedPaths.add(canonical.getAbsolutePath())) {
                        dirs.add(canonical);
                        break;
                    }
                } catch (Exception ignored) {}
            }
        }

        return dirs;
    }

    public static class FolderStats {
        public int totalTracks = 0;
        public int musicFolderCount = 0;
        public int snaptubeFolderCount = 0;
    }

    public FolderStats getQuickTrackCounts() {
        FolderStats stats = new FolderStats();
        List<File> rootDirs = getMonitoredDirectories();
        java.util.Set<String> seenCanonical = new java.util.HashSet<>();

        for (File dir : rootDirs) {
            boolean isSnaptube = dir.getName().toLowerCase().contains("snaptube") ||
                    dir.getAbsolutePath().toLowerCase().contains("snaptube");
            quickCountDirectoryRecursively(dir, isSnaptube, stats, seenCanonical);
        }
        stats.totalTracks = stats.musicFolderCount + stats.snaptubeFolderCount;
        return stats;
    }

    private void quickCountDirectoryRecursively(File dir, boolean isSnaptube, FolderStats stats, java.util.Set<String> seenCanonical) {
        if (dir == null || !dir.exists() || !dir.canRead()) return;
        try {
            String canonical = dir.getCanonicalPath();
            if (!seenCanonical.add(canonical)) return;
        } catch (Exception ignored) {}

        File[] files = dir.listFiles();
        if (files == null) return;

        for (File f : files) {
            if (f.isDirectory()) {
                String dirName = f.getName().toLowerCase();
                if (!dirName.startsWith(".") &&
                    !dirName.contains("record") &&
                    !dirName.contains("grava") &&
                    !dirName.contains("chamada") &&
                    !dirName.contains("call") &&
                    !dirName.contains("voice") &&
                    !dirName.contains("whatsapp") &&
                    !dirName.contains("telegram") &&
                    !dirName.contains("ringtone") &&
                    !dirName.contains("notification") &&
                    !dirName.contains("alarm")) {
                    quickCountDirectoryRecursively(f, isSnaptube, stats, seenCanonical);
                }
            } else if (f.isFile() && f.length() > 0) {
                if (isProhibitedNonMusic(f)) continue;

                String name = f.getName().toLowerCase();
                for (String ext : SUPPORTED_EXTENSIONS) {
                    if (name.endsWith(ext)) {
                        if (isSnaptube) {
                            stats.snaptubeFolderCount++;
                        } else {
                            stats.musicFolderCount++;
                        }
                        break;
                    }
                }
            }
        }
    }

    public List<LocalMediaTrack> scanLocalFiles() {
        List<LocalMediaTrack> tracks = new ArrayList<>();
        List<File> rootDirs = getMonitoredDirectories();

        for (File dir : rootDirs) {
            String category = dir.getName().toLowerCase().contains("snaptube") ? "SnapTube Audio" : "Music";
            scanDirectoryRecursively(dir, category, tracks);
        }

        Collections.sort(tracks, (a, b) -> Long.compare(b.getLastModified(), a.getLastModified()));
        return tracks;
    }

    public static boolean isProhibitedNonMusic(File file) {
        if (file == null) return true;
        String name = file.getName().toLowerCase();
        String path = file.getAbsolutePath().toLowerCase();

        // 1. Diretórios proibidos (gravações de mic, chamadas, WhatsApp, Telegram e forks, etc.)
        if (path.contains("whatsapp") || path.contains("telegram") || path.contains("ayugram") ||
            path.contains("nekogram") || path.contains("challegram") || path.contains("plus_messenger") ||
            path.contains("turbotel") || path.contains("recording") || path.contains("grava") ||
            path.contains("chamada") || path.contains("voice") || path.contains("call") ||
            path.contains("ringtone") || path.contains("notification") || path.contains("alarm") ||
            path.contains("podcast") || path.contains("audiobook")) {
            return true;
        }

        // 2. Nomes de arquivos característicos de notas de voz ou gravações
        if (name.startsWith("aud-") || name.startsWith("ptt-") ||
            name.contains("whatsapp audio") || name.contains("whatsapp voice") ||
            name.startsWith("call_") || name.startsWith("chamada_") ||
            name.startsWith("gravação") || name.startsWith("gravacao") ||
            name.startsWith("voice_") || name.startsWith("sound_record") ||
            name.startsWith("mic_") || name.startsWith("rec_") ||
            name.matches("^\\d+_\\d+@.*")) {
            return true;
        }

        // 3. Tamanho mínimo: arquivos de áudio < 300 KB não são faixas musicais reais
        if (file.isFile() && file.length() < 300 * 1024) {
            return true;
        }

        return false;
    }

    private void scanDirectoryRecursively(File dir, String category, List<LocalMediaTrack> list) {
        if (dir == null || !dir.exists() || !dir.canRead()) return;
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File f : files) {
            if (f.isDirectory()) {
                String dirName = f.getName().toLowerCase();
                if (!dirName.startsWith(".") &&
                    !dirName.contains("record") &&
                    !dirName.contains("grava") &&
                    !dirName.contains("chamada") &&
                    !dirName.contains("call") &&
                    !dirName.contains("voice") &&
                    !dirName.contains("whatsapp") &&
                    !dirName.contains("telegram") &&
                    !dirName.contains("ringtone") &&
                    !dirName.contains("notification") &&
                    !dirName.contains("alarm")) {
                    scanDirectoryRecursively(f, category, list);
                }
            } else if (f.isFile() && f.length() > 0) {
                if (isProhibitedNonMusic(f)) {
                    continue; // Rejeita gravações de microfone, chamadas e notas de voz do WhatsApp
                }

                String name = f.getName().toLowerCase();
                for (String ext : SUPPORTED_EXTENSIONS) {
                    if (name.endsWith(ext)) {
                        LocalMediaTrack track = new LocalMediaTrack(f, category);
                        
                        // Resgata SHA-256 do cache ou calcula
                        String cachedHash = storage.getCachedFileHash(track.getPath(), track.getSize(), track.getLastModified());
                        if (cachedHash != null && !cachedHash.isEmpty()) {
                            track.setSha256(cachedHash);
                        } else {
                            String calculated = track.calculateOrGetSha256();
                            storage.cacheFileHash(track.getPath(), track.getSize(), track.getLastModified(), calculated);
                        }
                        
                        list.add(track);
                        break;
                    }
                }
            }
        }
    }

    public synchronized void startSync(final boolean isManual) {
        if (isSyncing.get()) {
            Log.w(TAG, "Sync already running");
            return;
        }
        isSyncing.set(true);

        currentSyncTask = executor.submit(() -> {
            try {
                notifyState("SCANNING", createJson("message", "Escaneando faixas e verificando hashes criptográficos..."));

                List<LocalMediaTrack> localTracks = scanLocalFiles();
                int totalLocal = localTracks.size();

                JSONObject scanInfo = new JSONObject();
                scanInfo.put("totalLocal", totalLocal);
                scanInfo.put("message", totalLocal + " faixas verificadas. Validando com VPS por SHA-256...");
                notifyState("CHECKING", scanInfo);

                if (localTracks.isEmpty()) {
                    JSONObject emptyInfo = new JSONObject();
                    emptyInfo.put("message", "Nenhuma música encontrada nas pastas monitoradas.");
                    notifyState("SUCCESS", emptyInfo);
                    return;
                }

                String serverUrl = storage.getServerUrl();

                // Passo 2: Checagem com a VPS usando SHA-256 + Nome + Tamanho
                List<LocalMediaTrack> missingTracks = checkMissingWithVPS(localTracks, serverUrl);
                int pendingCount = missingTracks.size();

                if (pendingCount == 0) {
                    storage.recordSyncCompletion(0);
                    JSONObject completeInfo = new JSONObject();
                    completeInfo.put("message", "Biblioteca 100% sincronizada! Todas as " + totalLocal + " faixas já possuem hash idêntico na VPS.");
                    completeInfo.put("totalLocal", totalLocal);
                    completeInfo.put("syncedCount", 0);
                    notifyState("SUCCESS", completeInfo);
                    return;
                }

                JSONObject uploadStart = new JSONObject();
                uploadStart.put("message", "Iniciando upload de " + pendingCount + " faixas não duplicadas...");
                uploadStart.put("pendingCount", pendingCount);
                uploadStart.put("totalLocal", totalLocal);
                notifyState("UPLOADING", uploadStart);

                int successCount = 0;
                for (int i = 0; i < pendingCount; i++) {
                    if (!isSyncing.get() || Thread.currentThread().isInterrupted()) {
                        JSONObject cancelled = new JSONObject();
                        cancelled.put("message", "Sincronização interrompida pelo usuário.");
                        notifyState("CANCELLED", cancelled);
                        return;
                    }

                    LocalMediaTrack track = missingTracks.get(i);
                    final int currentIndex = i + 1;

                    boolean uploaded = uploadTrackMultipart(track, serverUrl, (bytesSent, totalBytes) -> {
                        int trackPercent = (int) ((bytesSent * 100.0) / totalBytes);
                        int globalPercent = (int) ((((currentIndex - 1) + (bytesSent * 1.0 / totalBytes)) / pendingCount) * 100.0);

                        JSONObject prog = new JSONObject();
                        try {
                            prog.put("currentIndex", currentIndex);
                            prog.put("totalPending", pendingCount);
                            prog.put("trackName", track.getName());
                            prog.put("trackSize", totalBytes);
                            prog.put("bytesSent", bytesSent);
                            prog.put("trackPercent", trackPercent);
                            prog.put("globalPercent", globalPercent);
                            prog.put("folder", track.getFolderCategory());
                            prog.put("hash", track.getSha256());
                            prog.put("message", "Enviando (" + currentIndex + "/" + pendingCount + "): " + track.getName());
                        } catch (Exception ignored) {}
                        notifyState("UPLOADING", prog);
                    });

                    if (uploaded) {
                        successCount++;
                        storage.addTrackToHistory(track.getName(), track.getSize(), track.getFolderCategory(), track.getSha256());
                    }
                }

                storage.recordSyncCompletion(successCount);

                if (successCount > 0) {
                    try {
                        HttpURLConnection notifyConn = openSmartConnection(serverUrl + "/api/engine/sync/notify-updated");
                        notifyConn.setRequestMethod("POST");
                        notifyConn.setConnectTimeout(5000);
                        notifyConn.setReadTimeout(5000);
                        notifyConn.getResponseCode();
                    } catch (Exception ignored) {}
                }

                JSONObject finalResult = new JSONObject();
                finalResult.put("message", "Sincronização concluída! " + successCount + " novas faixas catalogadas e replicando no Google Drive.");
                finalResult.put("syncedCount", successCount);
                finalResult.put("totalLocal", totalLocal);
                notifyState("SUCCESS", finalResult);

            } catch (Exception e) {
                Log.e(TAG, "Sync error: " + e.getMessage(), e);
                JSONObject err = new JSONObject();
                try {
                    err.put("error", e.getMessage());
                    err.put("message", "Falha na sincronização: " + e.getMessage());
                } catch (Exception ignored) {}
                notifyState("ERROR", err);
            } finally {
                isSyncing.set(false);
            }
        });
    }

    public synchronized void cancelSync() {
        if (isSyncing.get()) {
            isSyncing.set(false);
            if (currentSyncTask != null) {
                currentSyncTask.cancel(true);
            }
            JSONObject cancelled = new JSONObject();
            try {
                cancelled.put("message", "Operação interrompida.");
            } catch (Exception ignored) {}
            notifyState("IDLE", cancelled);
        }
    }

    private List<LocalMediaTrack> checkMissingWithVPS(List<LocalMediaTrack> localTracks, String serverUrl) throws Exception {
        HttpURLConnection conn = openSmartConnection(serverUrl + "/api/engine/sync/check-tracks");
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("User-Agent", "FeishinMediaSyncAndroid/2.5");
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(60000);
        conn.setDoOutput(true);

        JSONObject reqBody = new JSONObject();
        JSONArray tracksArr = new JSONArray();
        for (LocalMediaTrack t : localTracks) {
            JSONObject o = new JSONObject();
            o.put("name", t.getName());
            o.put("size", t.getSize());
            o.put("hash", t.getSha256());
            tracksArr.put(o);
        }
        reqBody.put("tracks", tracksArr);

        byte[] postData = reqBody.toString().getBytes(StandardCharsets.UTF_8);
        conn.setFixedLengthStreamingMode(postData.length);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(postData);
            os.flush();
        }

        int respCode = conn.getResponseCode();
        if (respCode != 200) {
            throw new Exception("Resposta inesperada da VPS: HTTP " + respCode);
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
        }

        JSONObject respJson = new JSONObject(sb.toString());
        JSONArray missingJson = respJson.optJSONArray("missing");
        JSONArray presentJson = respJson.optJSONArray("present");

        Set<String> missingHashes = new HashSet<>();
        Set<String> missingNames = new HashSet<>();
        Set<String> presentNames = new HashSet<>();

        if (missingJson != null) {
            for (int i = 0; i < missingJson.length(); i++) {
                JSONObject m = missingJson.getJSONObject(i);
                String h = m.optString("hash", "").toLowerCase();
                if (!h.isEmpty()) {
                    missingHashes.add(h);
                }
                String n = m.optString("name", "");
                if (!n.isEmpty()) {
                    missingNames.add(Normalizer.normalize(n, Normalizer.Form.NFC).toLowerCase());
                }
            }
        }

        if (presentJson != null) {
            for (int i = 0; i < presentJson.length(); i++) {
                JSONObject p = presentJson.getJSONObject(i);
                String pName = p.optString("name", "");
                if (!pName.isEmpty()) {
                    presentNames.add(Normalizer.normalize(pName, Normalizer.Form.NFC).toLowerCase());
                }
                String vName = p.optString("vps_name", "");
                if (!vName.isEmpty()) {
                    presentNames.add(Normalizer.normalize(vName, Normalizer.Form.NFC).toLowerCase());
                }
            }
        }

        List<LocalMediaTrack> missingList = new ArrayList<>();
        Set<String> addedMissingHashes = new HashSet<>();
        Set<String> addedMissingNames = new HashSet<>();

        for (LocalMediaTrack track : localTracks) {
            String trackHash = track.getSha256().toLowerCase();
            String normTrackName = Normalizer.normalize(track.getName(), Normalizer.Form.NFC).toLowerCase();

            // GUARDA 1: Se a VPS já confirmou explicitamente que o arquivo está 'present' (por hash, nome ou manifesto canônico):
            if (presentNames.contains(normTrackName)) {
                if (!trackHash.isEmpty()) {
                    storage.addSyncedHash(trackHash);
                }
                storage.addSyncedName(normTrackName);
                continue;
            }

            // GUARDA 2: Se o hash local OU nome já foi sincronizado e persistido no armazenamento local:
            if (!trackHash.isEmpty() && storage.isHashSynced(trackHash)) {
                continue;
            }
            if (storage.isNameSynced(normTrackName)) {
                continue;
            }

            // GUARDA 3: Somente marca como missing se estiver expressamente na lista de missing da VPS
            boolean isMissing = false;
            if (!trackHash.isEmpty() && missingHashes.contains(trackHash)) {
                isMissing = true;
            } else if (missingNames.contains(normTrackName)) {
                isMissing = true;
            }

            if (isMissing) {
                // Previne qualquer duplicação interna na lista de pendentes de upload
                boolean alreadyAdded = false;
                if (!trackHash.isEmpty()) {
                    if (!addedMissingHashes.add(trackHash)) {
                        alreadyAdded = true;
                    }
                } else {
                    if (!addedMissingNames.add(normTrackName)) {
                        alreadyAdded = true;
                    }
                }
                if (!alreadyAdded) {
                    missingList.add(track);
                }
            } else {
                if (!trackHash.isEmpty()) {
                    storage.addSyncedHash(trackHash);
                }
                storage.addSyncedName(normTrackName);
            }
        }
        return missingList;
    }

    public interface UploadProgressCallback {
        void onProgress(long bytesSent, long totalBytes);
    }

    private boolean uploadTrackMultipart(LocalMediaTrack track, String serverUrl, UploadProgressCallback callback) {
        File file = new File(track.getPath());
        if (!file.exists() || !file.canRead()) return false;

        String boundary = "===FeishinMediaSyncBoundary" + System.currentTimeMillis() + "===";
        String lineEnd = "\r\n";
        String twoHyphens = "--";

        HttpURLConnection conn = null;
        try {
            conn = openSmartConnection(serverUrl + "/api/engine/upload-track");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("User-Agent", "FeishinMediaSyncAndroid/2.5");
            conn.setRequestProperty("X-SHA256", track.getSha256());
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(120000);
            conn.setDoOutput(true);

            long fileLength = file.length();
            String header = twoHyphens + boundary + lineEnd
                    + "Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"" + lineEnd
                    + "Content-Type: audio/mpeg" + lineEnd + lineEnd;
            String footer = lineEnd + twoHyphens + boundary + twoHyphens + lineEnd;

            byte[] headerBytes = header.getBytes(StandardCharsets.UTF_8);
            byte[] footerBytes = footer.getBytes(StandardCharsets.UTF_8);
            long totalUploadSize = headerBytes.length + fileLength + footerBytes.length;

            conn.setFixedLengthStreamingMode(totalUploadSize);

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(headerBytes);

            byte[] buffer = new byte[65536];
            long bytesSent = 0;
            long lastProgressReportTime = 0;
            int lastReportedPercent = -1;
            try (FileInputStream fileInput = new FileInputStream(file)) {
                int bytesRead;
                while ((bytesRead = fileInput.read(buffer)) != -1) {
                    if (!isSyncing.get() || Thread.currentThread().isInterrupted()) {
                        return false;
                    }
                    outputStream.write(buffer, 0, bytesRead);
                    bytesSent += bytesRead;
                    if (callback != null) {
                        long now = System.currentTimeMillis();
                        int currentPct = (int) ((bytesSent * 100.0) / fileLength);
                        // Throttle: atualiza a cada 500ms com salto de 5% ou ao concluir a faixa (evita rate limit no Android)
                        if (bytesSent == fileLength || (now - lastProgressReportTime >= 500 && currentPct - lastReportedPercent >= 5)) {
                            lastProgressReportTime = now;
                            lastReportedPercent = currentPct;
                            callback.onProgress(bytesSent, fileLength);
                        }
                    }
                }
            }

            outputStream.write(footerBytes);
            outputStream.flush();

            int respCode = conn.getResponseCode();
            if (respCode >= 200 && respCode < 300) {
                try {
                    InputStream is = conn.getInputStream();
                    if (is != null) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                        StringBuilder sbResp = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) sbResp.append(line);
                        JSONObject uploadResp = new JSONObject(sbResp.toString());
                        if (uploadResp.optBoolean("duplicate_skipped", false)) {
                            Log.i(TAG, "VPS interceptou duplicação com sucesso: " + track.getName() + " -> " + uploadResp.optString("existing_filename"));
                        }
                    }
                } catch (Exception ignored) {}
                storage.addSyncedName(Normalizer.normalize(track.getName(), Normalizer.Form.NFC).toLowerCase());
                return true;
            }
            return false;

        } catch (Exception e) {
            Log.e(TAG, "Upload failed for " + track.getName() + ": " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public JSONObject getCachedVpsSummary() {
        return cachedVpsSummary;
    }

    public void refreshVpsSummaryAsync() {
        executor.submit(() -> {
            JSONObject res = fetchVpsSummary();
            if (res != null) {
                MainActivity.notifyWebSyncProgress("VPS_STATS_UPDATED", res);
            }
        });
    }

    public JSONObject fetchVpsSummary() {
        long now = System.currentTimeMillis();
        if (cachedVpsSummary != null && (now - lastVpsSummaryFetchTime < 10000)) {
            return cachedVpsSummary;
        }

        try {
            String serverUrl = storage.getServerUrl();
            HttpURLConnection conn = openSmartConnection(serverUrl + "/api/engine/sync/summary");
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "FeishinMediaSyncAndroid/2.5");
            conn.setConnectTimeout(6000);
            conn.setReadTimeout(8000);

            if (conn.getResponseCode() == 200) {
                StringBuilder sb = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = br.readLine()) != null) sb.append(line);
                }
                cachedVpsSummary = new JSONObject(sb.toString());
                lastVpsSummaryFetchTime = now;
                return cachedVpsSummary;
            }
        } catch (Exception e) {
            Log.w(TAG, "fetchVpsSummary error: " + e.getMessage());
        }
        return cachedVpsSummary;
    }

    public boolean triggerVpsGDriveSync() {
        try {
            String serverUrl = storage.getServerUrl();
            HttpURLConnection conn = openSmartConnection(serverUrl + "/api/engine/sync/trigger-gdrive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "FeishinMediaSyncAndroid/2.5");
            conn.setConnectTimeout(6000);
            conn.setReadTimeout(8000);
            return conn.getResponseCode() == 200;
        } catch (Exception e) {
            Log.w(TAG, "triggerVpsGDriveSync error: " + e.getMessage());
            return false;
        }
    }

    private void notifyState(final String state, final JSONObject details) {
        mainHandler.post(() -> {
            if (activeListener != null) {
                activeListener.onProgress(state, details);
            }
            MainActivity.notifyWebSyncProgress(state, details);
        });
    }

    private JSONObject createJson(String k, Object v) {
        JSONObject o = new JSONObject();
        try { o.put(k, v); } catch (Exception ignored) {}
        return o;
    }
}
