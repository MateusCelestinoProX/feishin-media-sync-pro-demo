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
                    window.onSyncStateUpdate('SCANNING', { message: "Escaneando catálogo local de faixas..." });

                    syncTimer = setTimeout(() => {
                        window.onSyncStateUpdate('CHECKING', { message: "Consultando VPS Navidrome por hashes SHA-256..." });

                        syncTimer = setTimeout(() => {
                            window.onSyncStateUpdate('UPLOADING', {
                                currentIndex: 1,
                                totalPending: 2,
                                percent: 50,
                                trackName: "Planeta Sonho - 14 Bis",
                                bytesUploaded: 5242880,
                                totalBytes: 10485760
                            });

                            syncTimer = setTimeout(() => {
                                window.onSyncStateUpdate('SUCCESS', {
                                    message: "Sincronização tri-direcional concluída com sucesso!",
                                    syncedCount: 15
                                });
                                isSyncing = false;
                            }, 1800);
                        }, 1400);
                    }, 1200);
                }
            },

            cancelSync() {
                if (syncTimer) clearTimeout(syncTimer);
                isSyncing = false;
                if (typeof window.onSyncStateUpdate === 'function') {
                    window.onSyncStateUpdate('IDLE', { message: "Sincronização cancelada pelo usuário." });
                }
                this.showToast("Sincronização cancelada");
            },

            getSyncOverview() {
                return JSON.stringify({
                    totalLocalTracks: 15,
                    totalVpsTracks: 15,
                    pendingUploads: 0,
                    pendingDownloads: 0,
                    lastSyncTime: "Agora mesmo",
                    autoSyncEnabled: isAutoSync
                });
            },

            getVpsSummary() {
                return JSON.stringify({
                    status: "Online",
                    tracks: 15,
                    server: "Navidrome Cloud Demo",
                    version: "0.64.2"
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
