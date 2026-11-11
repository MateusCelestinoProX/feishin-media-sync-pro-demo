package com.feishin.mediasync;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.view.animation.DecelerateInterpolator;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class FloatingWidgetService extends Service {

    public static final String ACTION_SHOW = "com.feishin.mediasync.action.SHOW_BUBBLE";
    public static final String ACTION_HIDE = "com.feishin.mediasync.action.HIDE_BUBBLE";
    public static final String ACTION_SET_STATE = "com.feishin.mediasync.action.BUBBLE_STATE";
    public static final String EXTRA_IS_PLAYING = "extra_bubble_playing";

    private WindowManager windowManager;
    private View floatingView;
    private WindowManager.LayoutParams floatingParams;
    private View pulseDot;

    // Alvo de Desativação / Apagador Visual na base da tela (Estilo Samsung / Messenger)
    private View dismissTargetView;
    private WindowManager.LayoutParams dismissParams;
    private View dismissCircle;
    private boolean isOverDismissTarget = false;
    private boolean hasVibratedForDismiss = false;

    private int screenWidth = 1080;
    private int screenHeight = 2400;

    // Modo Painel Edge Samsung (cantinho da tela)
    private boolean isEdgeDocked = false;
    private boolean isDockedLeft = false;

    // WakeLock para manter a tela 100% ligada e brilhante de forma permanente
    private PowerManager.WakeLock screenWakeLock;

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !android.provider.Settings.canDrawOverlays(this)) {
            stopSelf();
            return;
        }

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        updateScreenDimensions();

        setupFloatingView();
        setupDismissTargetView();
        setupTouchListener();

        acquireScreenWakeLock();

        MainActivity.onFloatingBubbleActiveChanged(true);
    }

    private void updateScreenDimensions() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
    }

    private int getOverlayWindowType() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            return WindowManager.LayoutParams.TYPE_PHONE;
        }
    }

    private void setupFloatingView() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        floatingView = inflater.inflate(R.layout.floating_bubble_layout, null);

        floatingParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                getOverlayWindowType(),
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE 
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.TRANSLUCENT
        );

        int viewWidth = (int) (60 * getResources().getDisplayMetrics().density);
        int edgeOffset = (int) (viewWidth * 0.40f);

        floatingParams.gravity = Gravity.TOP | Gravity.START;
        // Inicia acoplado no cantinho da tela no padrão Painel Edge da Samsung
        floatingParams.x = -edgeOffset;
        floatingParams.y = screenHeight / 3;
        isEdgeDocked = true;
        isDockedLeft = true;

        pulseDot = floatingView.findViewById(R.id.bubble_pulse_dot);

        try {
            windowManager.addView(floatingView, floatingParams);
            floatingView.post(() -> applyEdgePanelStyle(true, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupDismissTargetView() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        dismissTargetView = inflater.inflate(R.layout.floating_dismiss_layout, null);
        dismissCircle = dismissTargetView.findViewById(R.id.dismiss_circle);

        dismissParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                getOverlayWindowType(),
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT
        );

        dismissParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        dismissParams.y = 120; // Distância da barra de navegação inferior

        dismissTargetView.setVisibility(View.GONE);

        try {
            windowManager.addView(dismissTargetView, dismissParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupTouchListener() {
        final View container = floatingView.findViewById(R.id.bubble_container);

        container.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;
            private long touchStartTime;
            private boolean isDragging = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                updateScreenDimensions();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchStartTime = System.currentTimeMillis();
                        initialX = floatingParams.x;
                        initialY = floatingParams.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        isDragging = false;
                        hasVibratedForDismiss = false;
                        isOverDismissTarget = false;

                        // Se estava de ladinho no cantinho da tela, ao tocar já restaura destaque total
                        if (isEdgeDocked) {
                            applyEdgePanelStyle(false, isDockedLeft);
                        }
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        float deltaX = event.getRawX() - initialTouchX;
                        float deltaY = event.getRawY() - initialTouchY;

                        if (Math.abs(deltaX) > 8 || Math.abs(deltaY) > 8) {
                            if (!isDragging) {
                                isDragging = true;
                                isEdgeDocked = false;
                                showDismissTarget();
                            }
                        }

                        if (isDragging) {
                            floatingParams.x = initialX + (int) deltaX;
                            floatingParams.y = initialY + (int) deltaY;

                            if (floatingView != null && windowManager != null) {
                                windowManager.updateViewLayout(floatingView, floatingParams);
                            }

                            checkOverlapWithDismissTarget(event.getRawX(), event.getRawY());
                        }
                        return true;

                    case MotionEvent.ACTION_UP:
                        long duration = System.currentTimeMillis() - touchStartTime;
                        float diffX = Math.abs(event.getRawX() - initialTouchX);
                        float diffY = Math.abs(event.getRawY() - initialTouchY);

                        hideDismissTarget();

                        if (isOverDismissTarget) {
                            // Soltou no apagador visual: Desativa o ícone flutuante e libera a tela
                            vibrateHaptic(50);
                            Toast.makeText(FloatingWidgetService.this, "Ícone desativado • Modo Tela Sempre Ligada desligado", Toast.LENGTH_SHORT).show();
                            MainActivity.onFloatingBubbleActiveChanged(false);
                            stopSelf();
                            return true;
                        }

                        if (!isDragging && duration < 350 && diffX < 15 && diffY < 15) {
                            // Toque simples: reabre o app Feishin Media Sync Pro com feedback tátil
                            vibrateHaptic(20);
                            openMainActivity();
                        } else if (isDragging) {
                            // Fixação de ladinho no cantinho da tela estilo Painel Edge da Samsung
                            snapToNearestEdge();
                        } else if (isEdgeDocked) {
                            // Se tocou enquanto ancorado no cantinho, reabre
                            vibrateHaptic(20);
                            openMainActivity();
                        }
                        return true;
                }
                return false;
            }
        });
    }

    private void showDismissTarget() {
        if (dismissTargetView != null) {
            dismissTargetView.setAlpha(0f);
            dismissTargetView.setVisibility(View.VISIBLE);
            dismissTargetView.animate().alpha(1f).setDuration(180).start();
        }
    }

    private void hideDismissTarget() {
        if (dismissTargetView != null && dismissTargetView.getVisibility() == View.VISIBLE) {
            dismissTargetView.animate().alpha(0f).setDuration(150).withEndAction(() -> {
                dismissTargetView.setVisibility(View.GONE);
                if (dismissCircle != null) {
                    dismissCircle.setScaleX(1.0f);
                    dismissCircle.setScaleY(1.0f);
                }
            }).start();
        }
    }

    private void checkOverlapWithDismissTarget(float touchX, float touchY) {
        // Centro do alvo na base da tela
        float targetCenterX = screenWidth / 2.0f;
        float targetCenterY = screenHeight - dismissParams.y - 100; // aproximação do centro do círculo

        float distance = (float) Math.hypot(touchX - targetCenterX, touchY - targetCenterY);
        float thresholdPx = 220; // Raio de captura do apagador (~80dp)

        if (distance < thresholdPx) {
            if (!isOverDismissTarget) {
                isOverDismissTarget = true;
                if (!hasVibratedForDismiss) {
                    vibrateHaptic(25);
                    hasVibratedForDismiss = true;
                }
                if (dismissCircle != null) {
                    dismissCircle.animate().scaleX(1.3f).scaleY(1.3f).setDuration(120).start();
                }
            }
        } else {
            if (isOverDismissTarget) {
                isOverDismissTarget = false;
                hasVibratedForDismiss = false;
                if (dismissCircle != null) {
                    dismissCircle.animate().scaleX(1.0f).scaleY(1.0f).setDuration(120).start();
                }
            }
        }
    }

    private void snapToNearestEdge() {
        if (floatingView == null) return;
        int viewWidth = floatingView.getWidth();
        if (viewWidth <= 0) viewWidth = (int) (60 * getResources().getDisplayMetrics().density);

        boolean dockLeft = (floatingParams.x + viewWidth / 2) < (screenWidth / 2);
        isDockedLeft = dockLeft;

        // Modo Painel Edge Samsung: retrai 40% do ícone para dentro da borda lateral no cantinho da tela
        int edgeOffset = (int) (viewWidth * 0.40f);
        int targetX = dockLeft ? -edgeOffset : (screenWidth - viewWidth + edgeOffset);

        // Limites ergonômicos verticais na tela
        int minY = 120;
        int maxY = screenHeight - (int) (viewWidth * 2.2f);
        int clampedY = Math.max(minY, Math.min(floatingParams.y, maxY));

        ValueAnimator animX = ValueAnimator.ofInt(floatingParams.x, targetX);
        ValueAnimator animY = ValueAnimator.ofInt(floatingParams.y, clampedY);

        animX.setDuration(240);
        animX.setInterpolator(new DecelerateInterpolator());
        animX.addUpdateListener(animation -> {
            floatingParams.x = (int) animation.getAnimatedValue();
            if (floatingView != null && windowManager != null) {
                try {
                    windowManager.updateViewLayout(floatingView, floatingParams);
                } catch (Exception ignored) {}
            }
        });

        animY.setDuration(240);
        animY.addUpdateListener(animation -> {
            floatingParams.y = (int) animation.getAnimatedValue();
        });

        animX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isEdgeDocked = true;
                applyEdgePanelStyle(true, dockLeft);
                vibrateHaptic(18); // Vibração suave confirmando acoplamento no Painel Edge
            }
        });

        animX.start();
        animY.start();
    }

    private void applyEdgePanelStyle(boolean docked, boolean isLeft) {
        if (floatingView == null) return;
        View container = floatingView.findViewById(R.id.bubble_container);
        if (container != null) {
            if (docked) {
                // Modo Painel Edge Samsung no cantinho da tela: semi-translúcido e encaixado
                container.animate()
                        .alpha(0.72f)
                        .scaleX(0.92f)
                        .scaleY(0.95f)
                        .setDuration(200)
                        .start();
            } else {
                // Modo Flutuante normal: 100% visível e escala padrão
                container.animate()
                        .alpha(1.0f)
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .setDuration(150)
                        .start();
            }
        }
    }

    private void vibrateHaptic(long durationMs) {
        try {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null && vibrator.hasVibrator()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(durationMs);
                }
            }
        } catch (Exception ignored) {}
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_HIDE.equals(action)) {
                MainActivity.onFloatingBubbleActiveChanged(false);
                stopSelf();
                return START_NOT_STICKY;
            }

            if (intent.hasExtra(EXTRA_IS_PLAYING) && pulseDot != null) {
                boolean playing = intent.getBooleanExtra(EXTRA_IS_PLAYING, false);
                pulseDot.setVisibility(playing ? View.VISIBLE : View.GONE);
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.onFloatingBubbleActiveChanged(false);

        releaseScreenWakeLock();

        if (dismissTargetView != null && windowManager != null) {
            try {
                windowManager.removeView(dismissTargetView);
            } catch (Exception ignored) {}
        }

        if (floatingView != null && windowManager != null) {
            try {
                windowManager.removeView(floatingView);
            } catch (Exception ignored) {}
        }
    }

    private void acquireScreenWakeLock() {
        if (screenWakeLock == null) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (pm != null) {
                screenWakeLock = pm.newWakeLock(
                        PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE,
                        "FeishinMediaSyncPro:ScreenKeepOnLock"
                );
                screenWakeLock.setReferenceCounted(false);
            }
        }
        if (screenWakeLock != null && !screenWakeLock.isHeld()) {
            screenWakeLock.acquire();
        }
    }

    private void releaseScreenWakeLock() {
        if (screenWakeLock != null && screenWakeLock.isHeld()) {
            try {
                screenWakeLock.release();
            } catch (Exception ignored) {}
            screenWakeLock = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
