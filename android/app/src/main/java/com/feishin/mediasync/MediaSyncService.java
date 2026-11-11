package com.feishin.mediasync;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.FileObserver;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaSyncService extends Service implements MediaSyncManager.SyncListener {

    private static final String TAG = "MediaSyncService";
    public static final String CHANNEL_ID = "feishin_sync_channel";
    public static final int NOTIFICATION_ID = 2026;

    public static final String ACTION_START_WATCHER = "com.feishin.mediasync.START_WATCHER";
    public static final String ACTION_TRIGGER_SYNC = "com.feishin.mediasync.TRIGGER_SYNC";

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final List<CustomFileObserver> observers = new ArrayList<>();
    private Runnable debounceRunnable;
    private NotificationManager notificationManager;
    private ContentObserver mediaStoreObserver;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancel(NOTIFICATION_ID);
        }
        stopSelf();
    }

    private void registerMediaStoreObserver() {
        if (mediaStoreObserver != null) return;
        try {
            mediaStoreObserver = new ContentObserver(handler) {
                @Override
                public void onChange(boolean selfChange, @Nullable Uri uri) {
                    super.onChange(selfChange, uri);
                    Log.i(TAG, "MediaStore audio change detected: " + uri);
                    triggerDebouncedSync("MediaStore");
                }
            };
            getContentResolver().registerContentObserver(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    true,
                    mediaStoreObserver
            );
            Log.i(TAG, "Registered MediaStore audio content observer successfully.");
        } catch (Exception e) {
            Log.e(TAG, "Failed to register MediaStore observer: " + e.getMessage());
        }
    }

    private void unregisterMediaStoreObserver() {
        if (mediaStoreObserver != null) {
            try {
                getContentResolver().unregisterContentObserver(mediaStoreObserver);
            } catch (Exception ignored) {}
            mediaStoreObserver = null;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        stopSelf();
        return START_NOT_STICKY;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Feishin Media Sync Ativo",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("Monitoramento de novas músicas e sincronização com VPS e Google Drive");
            channel.setShowBadge(false);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private final Runnable resetToIdleRunnable = () -> {
        if (!MediaSyncManager.getInstance(this).isSyncing()) {
            updateNotification("Monitorando downloads em tempo real...", 0, false);
        }
    };

    private Notification buildNotification(String text, int progress, boolean indeterminate) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0)
        );

        boolean isOngoing = (progress > 0 || indeterminate);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Feishin Media Sync Lite")
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_media_wave)
                .setContentIntent(pendingIntent)
                .setOngoing(isOngoing)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        if (progress > 0 || indeterminate) {
            builder.setProgress(100, progress, indeterminate);
        } else {
            // CRÍTICO: Limpa explicitamente qualquer barra de progresso residual no SystemUI do Android
            builder.setProgress(0, 0, false);
        }

        return builder.build();
    }

    private long lastNotificationUpdateTime = 0;
    private int lastNotificationProgress = -1;

    private synchronized void updateNotification(String text, int progress, boolean indeterminate) {
        long now = System.currentTimeMillis();
        boolean isTerminalState = (progress == 0 && !indeterminate);

        // Throttle updates para não floodar o NotificationManagerService do Android.
        // Estados terminais (sucesso, erro, idle) NUNCA sofrem throttle e sempre são despachados.
        if (!isTerminalState && (now - lastNotificationUpdateTime < 500)) {
            return;
        }

        lastNotificationUpdateTime = now;
        lastNotificationProgress = progress;
        if (notificationManager != null) {
            try {
                notificationManager.notify(NOTIFICATION_ID, buildNotification(text, progress, indeterminate));
            } catch (Exception e) {
                Log.e(TAG, "Falha ao postar notificação: " + e.getMessage());
            }
        }
    }

    private void startDirectoryObservers() {
        stopDirectoryObservers();
        List<File> dirs = MediaSyncManager.getInstance(this).getMonitoredDirectories();
        for (File dir : dirs) {
            if (dir.exists() && dir.isDirectory()) {
                try {
                    CustomFileObserver observer = new CustomFileObserver(dir.getAbsolutePath());
                    observer.startWatching();
                    observers.add(observer);
                    Log.i(TAG, "Watching directory for new media: " + dir.getAbsolutePath());
                } catch (Exception e) {
                    Log.e(TAG, "Error watching " + dir.getAbsolutePath() + ": " + e.getMessage());
                }
            }
        }
    }

    private void stopDirectoryObservers() {
        for (CustomFileObserver obs : observers) {
            try {
                obs.stopWatching();
            } catch (Exception ignored) {}
        }
        observers.clear();
    }

    private void triggerDebouncedSync(String reason) {
        if (!MediaSyncManager.getInstance(this).getStorage().isAutoSyncEnabled()) {
            return;
        }

        if (debounceRunnable != null) {
            handler.removeCallbacks(debounceRunnable);
        }

        debounceRunnable = () -> {
            Log.i(TAG, "Debounce completed (" + reason + "). Triggering auto-sync...");
            updateNotification("Nova mídia detectada. Iniciando envio para VPS...", 0, true);
            MediaSyncManager.getInstance(this).startSync(false);
        };
        handler.postDelayed(debounceRunnable, 3500);
    }

    private void onNewMediaFileDetected(String path) {
        if (path != null && MediaSyncManager.isProhibitedNonMusic(new java.io.File(path))) {
            Log.d(TAG, "Ignorando arquivo detectado (não-musical/gravação/whatsapp): " + path);
            return;
        }
        triggerDebouncedSync("FileObserver: " + path);
    }

    @Override
    public void onProgress(String state, JSONObject details) {
        switch (state) {
            case "SCANNING":
                handler.removeCallbacks(resetToIdleRunnable);
                updateNotification("Escaneando pastas locais...", 0, true);
                break;
            case "CHECKING":
                handler.removeCallbacks(resetToIdleRunnable);
                updateNotification("Consultando catálogo da VPS...", 0, true);
                break;
            case "UPLOADING":
                handler.removeCallbacks(resetToIdleRunnable);
                int percent = details.optInt("globalPercent", 0);
                String trackName = details.optString("trackName", "");
                updateNotification("Enviando: " + trackName + " (" + percent + "%)", percent, false);
                break;
            case "SUCCESS":
                int synced = details.optInt("syncedCount", 0);
                String msg = synced > 0 ? "Sincronizado! " + synced + " músicas enviadas." : "Biblioteca 100% sincronizada (VPS & Drive)";
                updateNotification(msg, 0, false);

                // Garante que o Android receba a remoção da barra de progresso após o debounce do sistema
                handler.removeCallbacks(resetToIdleRunnable);
                handler.postDelayed(() -> updateNotification(msg, 0, false), 400);

                // Retorna ao estado de vigilância IDLE após 5 segundos
                handler.postDelayed(resetToIdleRunnable, 5000);
                break;
            case "ERROR":
                updateNotification("Erro na sincronização: " + details.optString("message", ""), 0, false);
                handler.removeCallbacks(resetToIdleRunnable);
                handler.postDelayed(resetToIdleRunnable, 5000);
                break;
            case "CANCELLED":
            case "IDLE":
                handler.removeCallbacks(resetToIdleRunnable);
                updateNotification("Monitorando downloads em tempo real...", 0, false);
                break;
        }

        // Também repassa para a interface web na MainActivity se estiver aberta
        MainActivity.notifyWebSyncProgress(state, details);
    }

    private class CustomFileObserver extends FileObserver {
        private final String rootPath;

        public CustomFileObserver(String path) {
            super(path, FileObserver.CLOSE_WRITE | FileObserver.MOVED_TO);
            this.rootPath = path;
        }

        @Override
        public void onEvent(int event, @Nullable String path) {
            if (path == null) return;
            String lower = path.toLowerCase();
            if (lower.endsWith(".mp3") || lower.endsWith(".flac") || lower.endsWith(".m4a") || lower.endsWith(".wav")) {
                if (!lower.startsWith(".") && !lower.contains(".tmp")) {
                    Log.i(TAG, "FileObserver event (" + event + ") on: " + path);
                    onNewMediaFileDetected(rootPath + "/" + path);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        stopDirectoryObservers();
        unregisterMediaStoreObserver();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
