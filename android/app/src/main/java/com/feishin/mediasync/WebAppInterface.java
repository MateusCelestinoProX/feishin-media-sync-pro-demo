package com.feishin.mediasync;

import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class WebAppInterface {

    private final MainActivity activity;

    public WebAppInterface(MainActivity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void startSync() {
        activity.runOnUiThread(() -> {
            if (!activity.hasStoragePermissions()) {
                activity.checkAndRequestStoragePermissions();
                Toast.makeText(activity, "Conceda permissão de acesso a todos os arquivos para sincronizar.", Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(activity, "Iniciando sincronização de músicas...", Toast.LENGTH_SHORT).show();
            MediaSyncManager.getInstance(activity).startSync(true);
        });
    }

    @JavascriptInterface
    public void cancelSync() {
        activity.runOnUiThread(() -> {
            MediaSyncManager.getInstance(activity).cancelSync();
        });
    }

    @JavascriptInterface
    public String getSyncOverview() {
        JSONObject overview = new JSONObject();
        try {
            MediaSyncManager manager = MediaSyncManager.getInstance(activity);
            SyncStorage storage = manager.getStorage();

            overview.put("isSyncing", manager.isSyncing());
            overview.put("serverUrl", storage.getServerUrl());
            overview.put("autoSyncEnabled", storage.isAutoSyncEnabled());
            overview.put("wifiOnly", storage.isWifiOnly());
            overview.put("lastSyncTime", storage.getLastSyncTime());
            overview.put("lastSyncCount", storage.getLastSyncCount());
            overview.put("hasStoragePermission", activity.hasStoragePermissions());

            // Pastas monitoradas
            JSONArray dirsArr = new JSONArray();
            List<File> dirs = manager.getMonitoredDirectories();
            int musicCount = 0;
            int snaptubeCount = 0;

            for (File d : dirs) {
                JSONObject dirObj = new JSONObject();
                dirObj.put("path", d.getAbsolutePath());
                dirObj.put("name", d.getName());
                dirObj.put("exists", d.exists() && d.canRead());
                dirsArr.put(dirObj);
            }
            overview.put("monitoredFolders", dirsArr);

            // Contagem rápida e ultra-leve de músicas locais (SEM hashing síncrono que trava a interface)
            MediaSyncManager.FolderStats stats = manager.getQuickTrackCounts();
            overview.put("totalLocalTracks", stats.totalTracks);
            overview.put("uniqueLocalTracks", stats.totalTracks);
            overview.put("musicFolderCount", stats.musicFolderCount);
            overview.put("snaptubeFolderCount", stats.snaptubeFolderCount);

            // Histórico
            overview.put("history", storage.getHistoryJsonArray());

        } catch (Exception e) {
            try {
                overview.put("error", e.getMessage());
            } catch (Exception ignored) {}
        }
        return overview.toString();
    }

    @JavascriptInterface
    public void setAutoSync(boolean enabled) {
        MediaSyncManager.getInstance(activity).getStorage().setAutoSyncEnabled(false);
        activity.runOnUiThread(activity::stopAllBackgroundServices);
        showToast("Segundo plano desativado: execução apenas sob demanda.");
    }

    @JavascriptInterface
    public void setServerUrl(String url) {
        MediaSyncManager.getInstance(activity).getStorage().setServerUrl(url);
        showToast("Servidor atualizado: " + url);
    }

    @JavascriptInterface
    public void triggerGDriveSync() {
        new Thread(() -> {
            boolean ok = MediaSyncManager.getInstance(activity).triggerVpsGDriveSync();
            activity.runOnUiThread(() -> {
                showToast(ok ? "Google Drive Sync disparado na VPS!" : "Falha ao disparar sync no Google Drive.");
            });
        }).start();
    }

    @JavascriptInterface
    public String getVpsSummary() {
        MediaSyncManager manager = MediaSyncManager.getInstance(activity);
        JSONObject cached = manager.getCachedVpsSummary();
        manager.refreshVpsSummaryAsync();
        return cached != null ? cached.toString() : "{}";
    }

    @JavascriptInterface
    public boolean hasStoragePermissions() {
        return activity.hasStoragePermissions();
    }

    @JavascriptInterface
    public void requestStoragePermissions() {
        activity.runOnUiThread(activity::checkAndRequestStoragePermissions);
    }

    @JavascriptInterface
    public void onPlaybackStateChanged(final boolean isPlaying, final String trackTitle) {
        activity.runOnUiThread(() -> {
            activity.updatePlaybackState(isPlaying, trackTitle, 0, -1);
        });
    }

    @JavascriptInterface
    public void onPlaybackStateProgress(final boolean isPlaying, final String trackTitle, final double currentSec, final double durationSec) {
        activity.runOnUiThread(() -> {
            long posMs = (long) (Math.max(0, currentSec) * 1000);
            long durMs = durationSec > 0 ? (long) (durationSec * 1000) : -1;
            activity.updatePlaybackState(isPlaying, trackTitle, posMs, durMs);
        });
    }

    @JavascriptInterface
    public boolean toggleFloatingBubble() {
        return activity.toggleFloatingBubble();
    }

    @JavascriptInterface
    public void setFloatingBubbleEnabled(final boolean enable) {
        activity.runOnUiThread(() -> {
            activity.setFloatingBubbleEnabled(enable);
        });
    }

    @JavascriptInterface
    public boolean isFloatingBubbleActive() {
        return activity.isFloatingBubbleActive();
    }

    @JavascriptInterface
    public boolean isIgnoringBatteryOptimizations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PowerManager pm = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
            return pm != null && pm.isIgnoringBatteryOptimizations(activity.getPackageName());
        }
        return true;
    }

    @JavascriptInterface
    public void requestBatteryOptimizationExemption() {
        activity.runOnUiThread(activity::stopAllBackgroundServices);
        showToast("Modo economia total: sem processos em segundo plano.");
    }

    @JavascriptInterface
    public boolean canDrawOverlays() {
        return false;
    }

    @JavascriptInterface
    public void requestOverlayPermission() {
        // Desativado no modo sob demanda
    }

    @JavascriptInterface
    public void showToast(final String message) {
        activity.runOnUiThread(() -> {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        });
    }

    @JavascriptInterface
    public boolean isOneGigabyteAllocated() {
        return false;
    }

    @JavascriptInterface
    public void allocateDedicatedRam() {
        DirectMemoryGovernor.releaseDirectMemory();
        showToast("RAM off-heap desativada para preservar memória.");
    }

    @JavascriptInterface
    public void releaseDedicatedRam() {
        DirectMemoryGovernor.releaseDirectMemory();
    }
}
