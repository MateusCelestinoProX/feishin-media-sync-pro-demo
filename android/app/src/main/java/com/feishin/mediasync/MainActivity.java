package com.feishin.mediasync;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.webkit.WebViewAssetLoader;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_OVERLAY_PERMISSION = 1002;
    private static WeakReference<MainActivity> currentActivityRef;

    private WebView webView;
    private ProgressBar loadingIndicator;
    private WebViewAssetLoader assetLoader;
    private boolean isFloatingBubbleActive = false;
    private boolean isPlaying = false;
    private String currentTrack = "";

    private final ActivityResultLauncher<String> requestNotificationPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {});

    private final ActivityResultLauncher<String[]> requestStoragePermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                startMediaSyncService();
                notifyWebSyncProgress("PERMISSION_CHANGED", null);
            });

    public static void notifyWebSyncProgress(String state, org.json.JSONObject details) {
        if (currentActivityRef != null && currentActivityRef.get() != null) {
            MainActivity activity = currentActivityRef.get();
            activity.runOnUiThread(() -> {
                if (activity.webView != null) {
                    String jsonStr = details != null ? details.toString() : "{}";
                    activity.webView.evaluateJavascript("if (typeof window.onSyncStateUpdate === 'function') { window.onSyncStateUpdate('" + state + "', " + jsonStr + "); }", null);
                }
            });
        }
    }

    // Receptor de estado de tela: suspende processamento gráfico pesado imediatamente com tela desligada
    private final BroadcastReceiver screenStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) return;
            String action = intent.getAction();
            if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                if (webView != null) {
                    webView.post(() -> {
                        webView.evaluateJavascript("if (typeof window.suspendVisuals === 'function') { window.suspendVisuals(true); }", null);
                    });
                }
            } else if (Intent.ACTION_SCREEN_ON.equals(action)) {
                if (webView != null) {
                    webView.post(() -> {
                        webView.evaluateJavascript("if (typeof window.suspendVisuals === 'function') { window.suspendVisuals(false); }", null);
                    });
                }
            }
        }
    };

    public static void triggerMediaAction(String action) {
        if (currentActivityRef != null && currentActivityRef.get() != null) {
            MainActivity activity = currentActivityRef.get();
            activity.runOnUiThread(() -> {
                if (activity.webView != null) {
                    activity.webView.evaluateJavascript("if (typeof window.handleMediaAction === 'function') { window.handleMediaAction('" + action + "'); }", null);
                }
            });
        }
    }

    public static void togglePlayPauseFromNative() {
        triggerMediaAction("play_pause");
    }

    public static void triggerMediaSeek(long positionMs) {
        if (currentActivityRef != null && currentActivityRef.get() != null) {
            MainActivity activity = currentActivityRef.get();
            activity.runOnUiThread(() -> {
                if (activity.webView != null) {
                    double seconds = positionMs / 1000.0;
                    activity.webView.evaluateJavascript("if (typeof window.handleMediaSeek === 'function') { window.handleMediaSeek(" + seconds + "); }", null);
                }
            });
        }
    }

    public static void onFloatingBubbleActiveChanged(boolean active) {
        if (currentActivityRef != null && currentActivityRef.get() != null) {
            MainActivity activity = currentActivityRef.get();
            activity.isFloatingBubbleActive = active;
            activity.runOnUiThread(() -> {
                if (active) {
                    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                } else {
                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
                if (activity.webView != null) {
                    activity.webView.evaluateJavascript("if (typeof window.onFloatingBubbleStateChanged === 'function') { window.onFloatingBubbleStateChanged(" + active + "); }", null);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
        } catch (Throwable ignored) {}

        currentActivityRef = new WeakReference<>(this);

        setContentView(R.layout.activity_main);

        // Otimização para tela Super AMOLED Galaxy S22 Ultra (Edge-to-Edge nativo)
        configureEdgeToEdgeDisplay();

        webView = findViewById(R.id.web_view);
        loadingIndicator = findViewById(R.id.loading_indicator);

        setupAssetLoader();
        setupWebView();

        checkAndRequestStoragePermissions();
        stopAllBackgroundServices();

        // Registro de monitoramento de tela
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(screenStateReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(screenStateReceiver, filter);
        }

        webView.loadUrl("https://appassets.androidplatform.net/assets/index.html");

        if (getIntent() != null && getIntent().getBooleanExtra("trigger_sync", false)) {
            MediaSyncManager.getInstance(this).startSync(true);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent != null && intent.getBooleanExtra("trigger_sync", false)) {
            MediaSyncManager.getInstance(this).startSync(true);
        }
    }

    private void configureEdgeToEdgeDisplay() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                getWindow().setDecorFitsSystemWindows(false);
                View decor = getWindow().getDecorView();
                if (decor != null) {
                    WindowInsetsController controller = decor.getWindowInsetsController();
                    if (controller != null) {
                        controller.setSystemBarsAppearance(
                                0,
                                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS | WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                        );
                    }
                }
            } else {
                View decor = getWindow().getDecorView();
                if (decor != null) {
                    decor.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    );
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                getWindow().getAttributes().layoutInDisplayCutoutMode =
                        WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            }

            getWindow().setStatusBarColor(0x00000000);
            getWindow().setNavigationBarColor(0x00000000);
        } catch (Exception ignored) {}
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentActivityRef = new WeakReference<>(this);
        if (isFloatingBubbleActive) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        if (webView != null) {
            webView.evaluateJavascript("if (typeof window.suspendVisuals === 'function') { window.suspendVisuals(false); }", null);
            webView.evaluateJavascript("if (typeof window.onFloatingBubbleStateChanged === 'function') { window.onFloatingBubbleStateChanged(" + isFloatingBubbleActive + "); }", null);
            webView.evaluateJavascript("if (typeof window.refreshData === 'function') { window.refreshData(); }", null);
        }
    }

    private void setupAssetLoader() {
        assetLoader = new WebViewAssetLoader.Builder()
                .addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler(this))
                .build();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        WebView.setWebContentsDebuggingEnabled(true);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setUserAgentString(settings.getUserAgentString() + " FeishinMediaSyncProApp/1.0");
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);

        webView.setBackgroundColor(android.graphics.Color.parseColor("#060913"));
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        WebAppInterface webAppInterface = new WebAppInterface(this);
        webView.addJavascriptInterface(webAppInterface, "AndroidBridge");
        webView.addJavascriptInterface(webAppInterface, "Android");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return assetLoader.shouldInterceptRequest(request.getUrl());
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (loadingIndicator != null) {
                    loadingIndicator.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (loadingIndicator != null) {
                    loadingIndicator.setVisibility(View.GONE);
                }
                view.evaluateJavascript("if (typeof window.refreshData === 'function') { window.refreshData(); }", null);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(android.webkit.ConsoleMessage consoleMessage) {
                android.util.Log.d("WebConsole", consoleMessage.message() + " [" 
                        + consoleMessage.sourceId() + ":" + consoleMessage.lineNumber() + "]");
                return true;
            }

            @Override
            public Bitmap getDefaultVideoPoster() {
                return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            }
        });
    }

    public void promptBatteryOptimizationExemption() {
        // Desativado: execução 100% sob demanda (zero processos em segundo plano)
    }

    public void checkAndRequestOverlayPermission() {
        // Desativado: sem janelas flutuantes em segundo plano
    }

    private void checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    public boolean hasStoragePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        }
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public void checkAndRequestStoragePermissions() {
        if (hasStoragePermissions()) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            } catch (Exception e) {
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivity(intent);
                } catch (Exception ignored) {}
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestStoragePermissionLauncher.launch(new String[]{
                    Manifest.permission.READ_MEDIA_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            });
        } else {
            requestStoragePermissionLauncher.launch(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            });
        }
    }

    public void stopAllBackgroundServices() {
        try {
            stopService(new Intent(this, MediaSyncService.class));
        } catch (Exception ignored) {}
        try {
            stopService(new Intent(this, AudioPlaybackService.class));
        } catch (Exception ignored) {}
        try {
            stopService(new Intent(this, FloatingWidgetService.class));
        } catch (Exception ignored) {}
        try {
            android.app.NotificationManager nm = (android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (nm != null) nm.cancelAll();
        } catch (Exception ignored) {}
        DirectMemoryGovernor.releaseDirectMemory();
        isFloatingBubbleActive = false;
    }

    public void startMediaSyncService() {
        // Desativado: execução estritamente sob demanda (zero segundo plano)
        stopAllBackgroundServices();
    }

    public void updatePlaybackState(boolean playing, String trackTitle, long posMs, long durMs) {
        this.isPlaying = false;
        stopAllBackgroundServices();
    }

    public boolean isFloatingBubbleActive() {
        return false;
    }

    public boolean toggleFloatingBubble() {
        stopFloatingService();
        return false;
    }

    public void setFloatingBubbleEnabled(boolean enabled) {
        stopFloatingService();
    }

    private void stopFloatingService() {
        try {
            stopService(new Intent(this, FloatingWidgetService.class));
        } catch (Exception ignored) {}
        isFloatingBubbleActive = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) {
            webView.evaluateJavascript("if (typeof window.suspendVisuals === 'function') { window.suspendVisuals(true); }", null);
        }
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(screenStateReceiver);
        } catch (Exception ignored) {}

        currentActivityRef = null;
        try {
            MediaSyncManager.getInstance(this).cancelSync();
        } catch (Exception ignored) {}
        stopAllBackgroundServices();
        if (webView != null) {
            try {
                webView.stopLoading();
                webView.destroy();
            } catch (Exception ignored) {}
        }
        super.onDestroy();
    }
}
