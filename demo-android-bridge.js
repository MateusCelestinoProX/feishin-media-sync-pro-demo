/* ==========================================================================
   FEISHIN MEDIA SYNC PRO — WEB DEMO ANDROID BRIDGE & SIMULATOR
   Simula fielmente a API nativa da ponte Android (window.Android)
   ========================================================================== */
(function() {
    'use strict';

    if (typeof window.Android === 'undefined') {
        console.info('[Demo Bridge] Android nativo não detectado. Ativando simulador interativo...');

        let isRamAllocated = false;
        let isAutoSync = true;
        let isSyncing = false;
        let syncTimer = null;

        window.Android = {
            isAvailable() { return true; },
            
            startSync() {
                if (isSyncing) return;
                isSyncing = true;

                if (typeof window.onSyncStateUpdate === 'function') {
                    window.onSyncStateUpdate('SCANNING', { message: "Escaneando catálogo local (/sdcard/Music e SnapTube)..." });

                    syncTimer = setTimeout(() => {
                        window.onSyncStateUpdate('CHECKING', { message: "Consultando VPS Navidrome por hashes SHA-256..." });

                        syncTimer = setTimeout(() => {
                            window.onSyncStateUpdate('UPLOADING', {
                                currentIndex: 1,
                                totalPending: 2,
                                trackPercent: 52,
                                trackName: "Planeta Sonho — 14 Bis",
                                bytesSent: 5452595,
                                totalBytes: 10485760
                            });

                            syncTimer = setTimeout(() => {
                                window.onSyncStateUpdate('UPLOADING', {
                                    currentIndex: 2,
                                    totalPending: 2,
                                    trackPercent: 88,
                                    trackName: "Flashing Lights — Kanye West",
                                    bytesSent: 4613734,
                                    totalBytes: 5242880
                                });

                                syncTimer = setTimeout(() => {
                                    window.onSyncStateUpdate('SUCCESS', {
                                        message: "Sincronização tri-direcional concluída com sucesso!",
                                        syncedCount: 2
                                    });
                                    isSyncing = false;
                                }, 1200);
                            }, 1300);
                        }, 1100);
                    }, 1000);
                }
            },

            cancelSync() {
                if (syncTimer) clearTimeout(syncTimer);
                isSyncing = false;
                if (typeof window.onSyncStateUpdate === 'function') {
                    window.onSyncStateUpdate('CANCELLED', { message: "Sincronização cancelada pelo usuário." });
                }
                this.showToast("Sincronização cancelada");
            },

            getSyncOverview() {
                const now = Date.now();
                return JSON.stringify({
                    totalLocalTracks: 15,
                    uniqueLocalTracks: 15,
                    musicFolderCount: 9,
                    snaptubeFolderCount: 6,
                    hasStoragePermission: true,
                    totalVpsTracks: 15,
                    pendingUploads: 0,
                    pendingDownloads: 0,
                    lastSyncTime: "Agora mesmo",
                    autoSyncEnabled: isAutoSync,
                    history: [
                        { name: "14 Bis — Planeta Sonho.mp3", size: 10485760, folder: "/sdcard/Music", timestamp: now - 120000, hash: "sha256_demo_01" },
                        { name: "J. Cole — 03' Adolescence.mp3", size: 10380902, folder: "/sdcard/Music", timestamp: now - 300000, hash: "sha256_demo_02" },
                        { name: "Chico Buarque & Milton Nascimento — CÁLICE.mp3", size: 3355443, folder: "SnapTube Audio", timestamp: now - 720000, hash: "sha256_demo_07" },
                        { name: "Kanye West — Flashing Lights.mp3", size: 5347737, folder: "/sdcard/Music", timestamp: now - 1800000, hash: "sha256_demo_13" },
                        { name: "Charlie Brown Jr. — Dias de Luta, Dias de Gloria.mp3", size: 5872025, folder: "SnapTube Audio", timestamp: now - 3600000, hash: "sha256_demo_14" },
                        { name: "Lana Del Rey — Born to Die.mp3", size: 4613734, folder: "/sdcard/Music", timestamp: now - 5400000, hash: "sha256_demo_15" }
                    ]
                });
            },

            getVpsSummary() {
                return JSON.stringify({
                    success: true,
                    status: "Online",
                    total_tracks: 15,
                    tracks: 15,
                    server: "Navidrome Cloud Demo",
                    version: "0.64.2",
                    gdrive_last_logs: ["Bisync completed: 15/15 tracks synced (SHA-256 verified)"]
                });
            },

            triggerGDriveSync() {
                this.showToast("☁️ Sincronização Google Drive agendada!");
            },

            setAutoSync(enabled) {
                isAutoSync = Boolean(enabled);
                this.showToast(isAutoSync ? "Auto-Sync Ativado (Wi-Fi + Carregador)" : "Auto-Sync Desativado");
            },

            allocateDedicatedRam() {
                isRamAllocated = true;
                this.showToast("⚡ 1 GB de RAM alocado via Linux ashmem / memfd_create");
            },

            releaseDedicatedRam() {
                isRamAllocated = false;
                this.showToast("RAM dedicada liberada.");
            },

            isOneGigabyteAllocated() {
                return isRamAllocated;
            },

            isIgnoringBatteryOptimizations() {
                return true;
            },

            requestBatteryOptimizationExemption() {
                this.showToast("Permissão de bateria em background concedida");
            },

            requestStoragePermissions() {
                this.showToast("Permissões de armazenamento concedidas");
            },

            toggleFloatingBubble() {
                this.showToast("Janela Flutuante (Samsung Edge Panel) alternada!");
                return true;
            },

            showToast(message) {
                let toast = document.getElementById('demo-floating-toast');
                if (!toast) {
                    toast = document.createElement('div');
                    toast.id = 'demo-floating-toast';
                    document.body.appendChild(toast);
                }
                toast.textContent = message;
                toast.className = 'show';
                clearTimeout(toast._timer);
                toast._timer = setTimeout(() => {
                    toast.className = '';
                }, 3000);
            }
        };
    }
})();
