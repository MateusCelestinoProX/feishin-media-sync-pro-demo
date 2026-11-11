package com.feishin.mediasync;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Process;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class AudioPlaybackService extends Service {

    private static final String TAG = "AudioPlaybackService";

    public static final String ACTION_START = "com.feishin.mediasync.action.START";
    public static final String ACTION_STOP = "com.feishin.mediasync.action.STOP";
    public static final String ACTION_UPDATE = "com.feishin.mediasync.action.UPDATE";
    public static final String ACTION_PLAY_PAUSE = "com.feishin.mediasync.action.PLAY_PAUSE";
    public static final String ACTION_NEXT = "com.feishin.mediasync.action.NEXT";
    public static final String ACTION_PREV = "com.feishin.mediasync.action.PREV";

    public static final String EXTRA_TRACK_TITLE = "extra_track_title";
    public static final String EXTRA_IS_PLAYING = "extra_is_playing";
    public static final String EXTRA_POSITION_MS = "extra_pos_ms";
    public static final String EXTRA_DURATION_MS = "extra_dur_ms";

    private static final String CHANNEL_ID = "feishin_playback_channel";
    private static final int NOTIFICATION_ID = 1001;

    private PowerManager.WakeLock wakeLock;
    private MediaSessionCompat mediaSession;
    private AudioManager audioManager;
    private AudioFocusRequest audioFocusRequest;

    private boolean isPlaying = false;
    private String currentTrackTitle = "Feishin Media Sync Pro";
    private long currentPosition = 0;
    private long currentDuration = -1;
    private Bitmap artworkBitmap;

    @Override
    public void onCreate() {
        super.onCreate();
        // Prioridade de áudio urgente no kernel Linux para eliminar jitter e buffer underruns
        try {
            Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
        } catch (Throwable ignored) {}

        createNotificationChannel();
        setupMediaSession();
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        try {
            artworkBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        } catch (Exception ignored) {}
    }

    private void setupMediaSession() {
        mediaSession = new MediaSessionCompat(this, "FeishinMediaSyncSession");
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        );

        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                isPlaying = true;
                updateMediaSessionState();
                updateForegroundNotification();
                acquireWakeLock();
                requestAudioFocus();
                DirectMemoryGovernor.allocateDirectOneGigabyte();
                MainActivity.triggerMediaAction("play");
            }

            @Override
            public void onPause() {
                isPlaying = false;
                updateMediaSessionState();
                updateForegroundNotification();
                releaseWakeLock();
                abandonAudioFocus();
                DirectMemoryGovernor.releaseDirectMemory();
                MainActivity.triggerMediaAction("pause");
            }

            @Override
            public void onSkipToNext() {
                MainActivity.triggerMediaAction("next");
            }

            @Override
            public void onSkipToPrevious() {
                MainActivity.triggerMediaAction("prev");
            }

            @Override
            public void onSeekTo(long pos) {
                currentPosition = pos;
                updateMediaSessionState();
                MainActivity.triggerMediaSeek(pos);
            }

            @Override
            public void onStop() {
                isPlaying = false;
                MainActivity.triggerMediaAction("pause");
                DirectMemoryGovernor.releaseDirectMemory();
                abandonAudioFocus();
                releaseWakeLock();
                stopForeground(true);
                stopSelf();
            }
        });

        mediaSession.setActive(true);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    getString(R.string.playback_channel_name),
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription(getString(R.string.playback_channel_desc));
            channel.setShowBadge(false);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private void updateMediaSessionState() {
        if (mediaSession == null) return;

        long actions = PlaybackStateCompat.ACTION_PLAY
                | PlaybackStateCompat.ACTION_PAUSE
                | PlaybackStateCompat.ACTION_PLAY_PAUSE
                | PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                | PlaybackStateCompat.ACTION_SEEK_TO
                | PlaybackStateCompat.ACTION_STOP;

        int state = isPlaying ? PlaybackStateCompat.STATE_PLAYING : PlaybackStateCompat.STATE_PAUSED;

        PlaybackStateCompat.Builder stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(actions)
                .setState(state, currentPosition, isPlaying ? 1.0f : 0.0f);

        mediaSession.setPlaybackState(stateBuilder.build());

        MediaMetadataCompat.Builder metadataBuilder = new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, currentTrackTitle)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "Feishin Media Sync • Hi-Fi Audio")
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, "Feishin Sync Pro");

        if (currentDuration > 0) {
            metadataBuilder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION, currentDuration);
        }

        if (artworkBitmap != null) {
            metadataBuilder.putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, artworkBitmap);
            metadataBuilder.putBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, artworkBitmap);
        }

        mediaSession.setMetadata(metadataBuilder.build());
    }

    private void requestAudioFocus() {
        if (audioManager == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (audioFocusRequest == null) {
                AudioAttributes playbackAttributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();

                audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                        .setAudioAttributes(playbackAttributes)
                        .setAcceptsDelayedFocusGain(true)
                        .setOnAudioFocusChangeListener(focusChange -> {
                            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                                isPlaying = false;
                                updateMediaSessionState();
                                updateForegroundNotification();
                                MainActivity.triggerMediaAction("pause");
                            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                                MainActivity.triggerMediaAction("pause");
                            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                                MainActivity.triggerMediaAction("play");
                            }
                        })
                        .build();
            }
            audioManager.requestAudioFocus(audioFocusRequest);
        } else {
            audioManager.requestAudioFocus(
                    focusChange -> {},
                    AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN
            );
        }
    }

    private void abandonAudioFocus() {
        if (audioManager == null) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && audioFocusRequest != null) {
            audioManager.abandonAudioFocusRequest(audioFocusRequest);
        }
    }

    private void acquireWakeLock() {
        if (wakeLock == null) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (pm != null) {
                wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "FeishinMediaSyncPro:HardwareAudioLock");
                wakeLock.setReferenceCounted(false);
            }
        }
        if (wakeLock != null && !wakeLock.isHeld()) {
            wakeLock.acquire();
        }
    }

    private void releaseWakeLock() {
        if (wakeLock != null && wakeLock.isHeld()) {
            try {
                wakeLock.release();
            } catch (Exception ignored) {}
            wakeLock = null;
        }
    }

    private PendingIntent createActionPendingIntent(String action, int requestCode) {
        Intent intent = new Intent(this, AudioPlaybackService.class);
        intent.setAction(action);
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }
        return PendingIntent.getService(this, requestCode, intent, flags);
    }

    private Notification buildNotification() {
        Intent openAppIntent = new Intent(this, MainActivity.class);
        openAppIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int contentFlags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            contentFlags |= PendingIntent.FLAG_IMMUTABLE;
        }
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, openAppIntent, contentFlags);

        androidx.media.app.NotificationCompat.MediaStyle mediaStyle =
                new androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSession != null ? mediaSession.getSessionToken() : null)
                        .setShowActionsInCompactView(0, 1, 2)
                        .setShowCancelButton(true)
                        .setCancelButtonIntent(createActionPendingIntent(ACTION_STOP, 99));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setStyle(mediaStyle)
                .setSmallIcon(R.drawable.ic_media_wave)
                .setContentTitle(currentTrackTitle)
                .setContentText(isPlaying ? "Sincronização & Streaming Ativos" : "Reprodução Pausada")
                .setSubText(isPlaying ? "Feishin Media Sync Pro • 1GB RAM Ativo" : "Feishin Media Sync Pro")
                .setContentIntent(contentIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setOngoing(isPlaying)
                .setColor(0xFF00E5FF)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        if (artworkBitmap != null) {
            builder.setLargeIcon(artworkBitmap);
        }

        // Ações de reprodução (Anterior, Play/Pause, Próximo)
        builder.addAction(R.drawable.ic_prev, "Anterior", createActionPendingIntent(ACTION_PREV, 10));
        builder.addAction(
                isPlaying ? R.drawable.ic_pause : R.drawable.ic_play,
                isPlaying ? "Pausar" : "Tocar",
                createActionPendingIntent(ACTION_PLAY_PAUSE, 11)
        );
        builder.addAction(R.drawable.ic_next, "Próximo", createActionPendingIntent(ACTION_NEXT, 12));

        return builder.build();
    }

    private void updateForegroundNotification() {
        Notification notification = buildNotification();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK);
        } else {
            startForeground(NOTIFICATION_ID, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
        } catch (Throwable ignored) {}

        if (intent != null) {
            String action = intent.getAction();

            if (ACTION_STOP.equals(action)) {
                if (mediaSession != null) mediaSession.setActive(false);
                MainActivity.triggerMediaAction("pause");
                DirectMemoryGovernor.releaseDirectMemory();
                abandonAudioFocus();
                releaseWakeLock();
                stopForeground(true);
                stopSelf();
                return START_NOT_STICKY;
            }

            if (ACTION_PLAY_PAUSE.equals(action)) {
                isPlaying = !isPlaying;
                updateMediaSessionState();
                updateForegroundNotification();
                if (isPlaying) {
                    acquireWakeLock();
                    requestAudioFocus();
                    DirectMemoryGovernor.allocateDirectOneGigabyte();
                } else {
                    releaseWakeLock();
                    abandonAudioFocus();
                    DirectMemoryGovernor.releaseDirectMemory();
                }
                MainActivity.triggerMediaAction("play_pause");
                return START_STICKY;
            }

            if (ACTION_NEXT.equals(action)) {
                MainActivity.triggerMediaAction("next");
                return START_STICKY;
            }

            if (ACTION_PREV.equals(action)) {
                MainActivity.triggerMediaAction("prev");
                return START_STICKY;
            }

            if (intent.hasExtra(EXTRA_TRACK_TITLE)) {
                currentTrackTitle = intent.getStringExtra(EXTRA_TRACK_TITLE);
            }
            if (intent.hasExtra(EXTRA_IS_PLAYING)) {
                isPlaying = intent.getBooleanExtra(EXTRA_IS_PLAYING, true);
            }
            if (intent.hasExtra(EXTRA_POSITION_MS)) {
                currentPosition = intent.getLongExtra(EXTRA_POSITION_MS, 0);
            }
            if (intent.hasExtra(EXTRA_DURATION_MS)) {
                currentDuration = intent.getLongExtra(EXTRA_DURATION_MS, -1);
            }
        }

        updateMediaSessionState();
        updateForegroundNotification();

        if (isPlaying) {
            requestAudioFocus();
            acquireWakeLock();
            DirectMemoryGovernor.allocateDirectOneGigabyte();
        } else {
            releaseWakeLock();
            abandonAudioFocus();
            DirectMemoryGovernor.releaseDirectMemory();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DirectMemoryGovernor.releaseDirectMemory();
        abandonAudioFocus();
        releaseWakeLock();
        if (mediaSession != null) {
            mediaSession.setActive(false);
            mediaSession.release();
            mediaSession = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
