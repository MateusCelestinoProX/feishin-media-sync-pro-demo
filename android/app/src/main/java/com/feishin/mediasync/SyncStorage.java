package com.feishin.mediasync;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class SyncStorage {

    private static final String PREF_NAME = "feishin_sync_prefs";
    private static final String PREF_HASH_CACHE = "feishin_hash_cache";
    private static final String KEY_SERVER_URL = "server_url";
    private static final String KEY_AUTO_SYNC = "auto_sync";
    private static final String KEY_WIFI_ONLY = "wifi_only";
    private static final String KEY_LAST_SYNC_TIME = "last_sync_time";
    private static final String KEY_LAST_SYNC_COUNT = "last_sync_count";
    private static final String KEY_HISTORY = "history_json";
    private static final String KEY_SYNCED_HASHES = "synced_hashes_set";
    private static final String KEY_SYNCED_NAMES = "synced_names_set";

    public static final String DEFAULT_SERVER_URL = "https://demo-navidrome.feishin.pro:4533";

    private final SharedPreferences prefs;
    private final SharedPreferences hashCachePrefs;

    public SyncStorage(Context context) {
        this.prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.hashCachePrefs = context.getApplicationContext().getSharedPreferences(PREF_HASH_CACHE, Context.MODE_PRIVATE);
    }

    public String getServerUrl() {
        return prefs.getString(KEY_SERVER_URL, DEFAULT_SERVER_URL);
    }

    public void setServerUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            url = DEFAULT_SERVER_URL;
        }
        url = url.trim();
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        prefs.edit().putString(KEY_SERVER_URL, url).apply();
    }

    public boolean isAutoSyncEnabled() {
        return false;
    }

    public void setAutoSyncEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_AUTO_SYNC, false).apply();
    }

    public boolean isWifiOnly() {
        return prefs.getBoolean(KEY_WIFI_ONLY, false);
    }

    public void setWifiOnly(boolean wifiOnly) {
        prefs.edit().putBoolean(KEY_WIFI_ONLY, wifiOnly).apply();
    }

    public long getLastSyncTime() {
        return prefs.getLong(KEY_LAST_SYNC_TIME, 0);
    }

    public int getLastSyncCount() {
        return prefs.getInt(KEY_LAST_SYNC_COUNT, 0);
    }

    public void recordSyncCompletion(int syncedCount) {
        prefs.edit()
                .putLong(KEY_LAST_SYNC_TIME, System.currentTimeMillis())
                .putInt(KEY_LAST_SYNC_COUNT, syncedCount)
                .apply();
    }

    // --------------------------------------------------------------------------
    // VERIFICADOR DE HASH CRIPTOGRÁFICO (GARANTIA DE NÃO-DUPLICAÇÃO)
    // --------------------------------------------------------------------------
    public synchronized boolean isHashSynced(String sha256) {
        if (sha256 == null || sha256.isEmpty()) return false;
        Set<String> set = prefs.getStringSet(KEY_SYNCED_HASHES, null);
        return set != null && set.contains(sha256.toLowerCase());
    }

    public synchronized void addSyncedHash(String sha256) {
        if (sha256 == null || sha256.isEmpty()) return;
        Set<String> set = prefs.getStringSet(KEY_SYNCED_HASHES, null);
        Set<String> newSet = set != null ? new HashSet<>(set) : new HashSet<>();
        newSet.add(sha256.toLowerCase());
        prefs.edit().putStringSet(KEY_SYNCED_HASHES, newSet).apply();
    }

    public synchronized boolean isNameSynced(String name) {
        if (name == null || name.isEmpty()) return false;
        Set<String> set = prefs.getStringSet(KEY_SYNCED_NAMES, null);
        return set != null && set.contains(name.toLowerCase());
    }

    public synchronized void addSyncedName(String name) {
        if (name == null || name.isEmpty()) return;
        Set<String> set = prefs.getStringSet(KEY_SYNCED_NAMES, null);
        Set<String> newSet = set != null ? new HashSet<>(set) : new HashSet<>();
        newSet.add(name.toLowerCase());
        prefs.edit().putStringSet(KEY_SYNCED_NAMES, newSet).apply();
    }

    public String getCachedFileHash(String path, long size, long lastModified) {
        String cacheKey = path + ":" + size + ":" + lastModified;
        return hashCachePrefs.getString(cacheKey, null);
    }

    public void cacheFileHash(String path, long size, long lastModified, String hash) {
        if (hash == null || hash.isEmpty()) return;
        String cacheKey = path + ":" + size + ":" + lastModified;
        hashCachePrefs.edit().putString(cacheKey, hash).apply();
    }

    // --------------------------------------------------------------------------
    // HISTÓRICO DE MÍDIAS SINCRONIZADAS
    // --------------------------------------------------------------------------
    public synchronized void addTrackToHistory(String trackName, long size, String folder, String sha256) {
        try {
            JSONArray arr = getHistoryJsonArray();
            JSONObject item = new JSONObject();
            item.put("name", trackName);
            item.put("size", size);
            item.put("folder", folder);
            item.put("hash", sha256 != null ? sha256 : "");
            item.put("timestamp", System.currentTimeMillis());
            item.put("status", "synced");

            JSONArray newArr = new JSONArray();
            newArr.put(item);
            for (int i = 0; i < arr.length() && newArr.length() < 50; i++) {
                JSONObject old = arr.getJSONObject(i);
                String oldHash = old.optString("hash", "");
                String oldName = old.optString("name", "");
                // Previne duplicações no histórico por hash ou nome
                if (sha256 != null && !sha256.isEmpty() && !oldHash.isEmpty() && oldHash.equalsIgnoreCase(sha256)) continue;
                if (trackName != null && !trackName.isEmpty() && oldName.equalsIgnoreCase(trackName)) continue;
                newArr.put(old);
            }
            prefs.edit().putString(KEY_HISTORY, newArr.toString()).apply();
            
            if (sha256 != null && !sha256.isEmpty()) {
                addSyncedHash(sha256);
            }
        } catch (JSONException ignored) {}
    }

    public synchronized JSONArray getHistoryJsonArray() {
        String raw = prefs.getString(KEY_HISTORY, "[]");
        try {
            return new JSONArray(raw);
        } catch (JSONException e) {
            return new JSONArray();
        }
    }
}
