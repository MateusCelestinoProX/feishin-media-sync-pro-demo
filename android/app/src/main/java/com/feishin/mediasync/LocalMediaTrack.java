package com.feishin.mediasync;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.Normalizer;

public class LocalMediaTrack {
    private final String name;
    private final String path;
    private final long size;
    private final long lastModified;
    private final String folderCategory;
    private String sha256;

    public LocalMediaTrack(File file, String folderCategory) {
        this.name = file != null ? Normalizer.normalize(file.getName(), Normalizer.Form.NFC) : "";
        this.path = file != null ? file.getAbsolutePath() : "";
        this.size = file != null ? file.length() : 0;
        this.lastModified = file != null ? file.lastModified() : 0;
        this.folderCategory = folderCategory;
        this.sha256 = "";
    }

    public LocalMediaTrack(String name, String path, long size, long lastModified, String folderCategory, String sha256) {
        this.name = name != null ? Normalizer.normalize(name, Normalizer.Form.NFC) : "";
        this.path = path != null ? path : "";
        this.size = size;
        this.lastModified = lastModified;
        this.folderCategory = folderCategory;
        this.sha256 = sha256 != null ? sha256 : "";
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public long getSize() {
        return size;
    }

    public long getLastModified() {
        return lastModified;
    }

    public String getFolderCategory() {
        return folderCategory;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    /**
     * Calcula o hash criptográfico SHA-256 do arquivo local via streaming em chunks de 64KB.
     */
    public String calculateOrGetSha256() {
        if (sha256 != null && !sha256.isEmpty()) {
            return sha256;
        }
        File file = new File(path);
        if (!file.exists() || !file.canRead()) {
            return "";
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            try (InputStream is = new FileInputStream(file)) {
                byte[] buffer = new byte[65536];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    digest.update(buffer, 0, read);
                }
            }
            byte[] hashBytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            this.sha256 = sb.toString();
            return this.sha256;
        } catch (Exception e) {
            return "";
        }
    }

    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("path", path);
            json.put("size", size);
            json.put("lastModified", lastModified);
            json.put("folder", folderCategory);
            json.put("hash", sha256 != null ? sha256 : "");
        } catch (JSONException ignored) {}
        return json;
    }
}
