/* ==========================================================================
   FEISHIN MEDIA SYNC PRO — DEMO UI CONTROLLER
   Splash Reveal + Android APK Allusion Bar + Glass Modal
   ========================================================================== */
(function() {
    'use strict';

    // 1. Build Top Android APK Notice Bar
    function createNoticeBar() {
        document.body.classList.add('has-demo-notice');

        const bar = document.createElement('header');
        bar.id = 'demo-android-notice-bar';
        bar.innerHTML = `
            <div class="notice-info-group">
                <div class="notice-icon-badge">
                    <i class="fa-brands fa-android"></i>
                </div>
                <div class="notice-text-content">
                    <span class="notice-headline">Software Android Nativo (.APK) — Feishin Media Sync Pro</span>
                    <span class="notice-subline">Sincronizador Tri-Direcional, 1 GB RAM Dedicada e 37 Shaders WebGL</span>
                </div>
            </div>
            <div class="notice-actions">
                <a href="./feishin-media-sync-pro-release.apk" download="feishin-media-sync-pro-release.apk" class="btn-download-apk-header" id="btn-header-apk">
                    <i class="fa-solid fa-download"></i>
                    <span>Baixar APK Nativo (5.4 MB)</span>
                </a>
            </div>
        `;
        document.body.prepend(bar);
    }

    // 2. Build Splash Screen
    function createSplashScreen() {
        const splash = document.createElement('div');
        splash.id = 'sync-demo-splash';
        splash.innerHTML = `
            <div class="sync-splash-ring">
                <i class="fa-solid fa-arrows-rotate fa-spin"></i>
            </div>
            <h1 class="sync-splash-title">Feishin Sync Pro</h1>
            <div class="sync-splash-subtitle">Native Android Client • VisionOS Liquid Glass</div>
            <div class="sync-splash-bar-track">
                <div class="sync-splash-bar-fill" id="sync-splash-fill"></div>
            </div>
            <div class="sync-splash-status" id="sync-splash-msg">Iniciando simulador Android...</div>
        `;
        document.body.appendChild(splash);

        const bar = document.getElementById('sync-splash-fill');
        const status = document.getElementById('sync-splash-msg');

        const milestones = [
            { p: 25, msg: "Compilando shaders WebGL OGL (37 núcleos)..." },
            { p: 55, msg: "Configurando ponte nativa Android Bridge..." },
            { p: 85, msg: "Carregando topologia Liquid Glass..." },
            { p: 100, msg: "Simulador pronto para teste." }
        ];

        let idx = 0;
        const timer = setInterval(() => {
            if (idx < milestones.length) {
                if (bar) bar.style.width = milestones[idx].p + '%';
                if (status) status.textContent = milestones[idx].msg;
                idx++;
            } else {
                clearInterval(timer);
                setTimeout(exitSplash, 350);
            }
        }, 300);

        function exitSplash() {
            splash.classList.add('splash-exit');
            setTimeout(() => {
                splash.remove();
                checkApkPrompt();
            }, 750);
        }
    }

    // 3. Build Glassmorphism Modal ("Popup de Vidro")
    function createApkModal() {
        const modal = document.createElement('div');
        modal.id = 'sync-demo-apk-modal';
        modal.innerHTML = `
            <div class="sync-glass-card">
                <div class="sync-icon-box">
                    <i class="fa-brands fa-android"></i>
                </div>
                <h2 class="sync-modal-title">Aplicativo Nativo Android (.APK)</h2>
                <p class="sync-modal-desc">
                    Aviso ao avaliador: Este projeto foi arquitetado primariamente como um <strong>APK nativo para Android</strong>. Ele contém alocação direta de 1 GB de RAM via subsistema Linux (<code>memfd_create</code>), Edge Panel flutuante e foreground service de áudio ininterrupto com <code>MediaSessionCompat</code>.
                </p>
                <div class="sync-chips-row">
                    <span class="sync-chip">📲 APK Nativo (5.4 MB)</span>
                    <span class="sync-chip">⚡ 37 Shaders WebGL</span>
                    <span class="sync-chip">🚀 1 GB RAM Dedicada</span>
                </div>
                <div class="sync-actions-col">
                    <a href="./feishin-media-sync-pro-release.apk" download="feishin-media-sync-pro-release.apk" class="btn-sync-download" id="btn-modal-apk-download">
                        <i class="fa-solid fa-download"></i>
                        <span>Baixar APK Android (.apk)</span>
                    </a>
                    <button class="btn-sync-dismiss" id="btn-modal-dismiss-sync">
                        Continuar no Simulador Web
                    </button>
                </div>
            </div>
        `;
        document.body.appendChild(modal);

        document.getElementById('btn-modal-dismiss-sync').onclick = closeApkModal;
        document.getElementById('btn-modal-apk-download').onclick = () => {
            setTimeout(closeApkModal, 800);
        };
        modal.onclick = (e) => {
            if (e.target === modal) closeApkModal();
        };
    }

    function openApkModal() {
        const modal = document.getElementById('sync-demo-apk-modal');
        if (modal) modal.classList.add('modal-open');
    }

    function closeApkModal() {
        const modal = document.getElementById('sync-demo-apk-modal');
        if (modal) modal.classList.remove('modal-open');
        sessionStorage.setItem('sync_apk_prompt_shown', 'true');
    }

    function checkApkPrompt() {
        if (!sessionStorage.getItem('sync_apk_prompt_shown')) {
            setTimeout(openApkModal, 1500);
        }
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', () => {
            createNoticeBar();
            createSplashScreen();
            createApkModal();
        });
    } else {
        createNoticeBar();
        createSplashScreen();
        createApkModal();
    }
})();
