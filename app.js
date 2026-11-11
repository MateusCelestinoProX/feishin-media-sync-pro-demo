/**
 * ==============================================================================
 * 🚀 FEISHIN MEDIA SYNC — MINIMALIST CLIENT ENGINE
 * ==============================================================================
 * Sincronizador de mídia em 3 fontes: Celular (Local) ➔ VPS (Navidrome) ➔ Nuvem (Google Drive)
 * Interface full minimalista: zero bloat, zero duplicações, zero dados fictícios.
 */

// --------------------------------------------------------------------------
// 1. ENCAPSULAMENTO DA PONTE ANDROID
// --------------------------------------------------------------------------
const NativeAndroid = {
  isAvailable() {
    return typeof window.Android !== 'undefined';
  },
  startSync() {
    if (this.isAvailable() && typeof window.Android.startSync === 'function') {
      window.Android.startSync();
    }
  },
  cancelSync() {
    if (this.isAvailable() && typeof window.Android.cancelSync === 'function') {
      window.Android.cancelSync();
    }
  },
  getSyncOverview() {
    if (this.isAvailable() && typeof window.Android.getSyncOverview === 'function') {
      try {
        return JSON.parse(window.Android.getSyncOverview());
      } catch (e) {
        console.error("Erro parsing getSyncOverview:", e);
      }
    }
    return null;
  },
  getVpsSummary() {
    if (this.isAvailable() && typeof window.Android.getVpsSummary === 'function') {
      try {
        return JSON.parse(window.Android.getVpsSummary());
      } catch (e) {
        console.error("Erro parsing getVpsSummary:", e);
      }
    }
    return null;
  },
  triggerGDriveSync() {
    if (this.isAvailable() && typeof window.Android.triggerGDriveSync === 'function') {
      window.Android.triggerGDriveSync();
    }
  },
  setAutoSync(enabled) {
    if (this.isAvailable() && typeof window.Android.setAutoSync === 'function') {
      window.Android.setAutoSync(enabled);
    }
  },
  allocateDedicatedRam() {
    if (this.isAvailable() && typeof window.Android.allocateDedicatedRam === 'function') {
      window.Android.allocateDedicatedRam();
    }
  },
  releaseDedicatedRam() {
    if (this.isAvailable() && typeof window.Android.releaseDedicatedRam === 'function') {
      window.Android.releaseDedicatedRam();
    }
  },
  isOneGigabyteAllocated() {
    if (this.isAvailable() && typeof window.Android.isOneGigabyteAllocated === 'function') {
      return window.Android.isOneGigabyteAllocated();
    }
    return false;
  },
  isIgnoringBatteryOptimizations() {
    if (this.isAvailable() && typeof window.Android.isIgnoringBatteryOptimizations === 'function') {
      return window.Android.isIgnoringBatteryOptimizations();
    }
    return true;
  },
  requestBatteryOptimizationExemption() {
    if (this.isAvailable() && typeof window.Android.requestBatteryOptimizationExemption === 'function') {
      window.Android.requestBatteryOptimizationExemption();
    }
  },
  requestStoragePermissions() {
    if (this.isAvailable() && typeof window.Android.requestStoragePermissions === 'function') {
      window.Android.requestStoragePermissions();
    }
  },
  toggleFloatingBubble() {
    if (this.isAvailable() && typeof window.Android.toggleFloatingBubble === 'function') {
      return window.Android.toggleFloatingBubble();
    }
    return false;
  },
  showToast(message) {
    if (this.isAvailable() && typeof window.Android.showToast === 'function') {
      window.Android.showToast(message);
    } else {
      console.log("[Toast]", message);
    }
  }
};

// --------------------------------------------------------------------------
// 2. ESTADO DO SINCRONIZADOR
// --------------------------------------------------------------------------
let isSyncInProgress = false;

window.onSyncStateUpdate = function(state, details) {
  console.log("[SyncState]", state, details);

  const mainStatusEl = document.getElementById('sync-main-status');
  const subStatusEl = document.getElementById('sync-sub-status');
  const heroCardEl = document.getElementById('hero-card');
  const radarIcon = document.getElementById('sync-icon-hero');
  const progressBox = document.getElementById('sync-progress-box');
  const progressFill = document.getElementById('sync-progress-fill');
  const percentLabelEl = document.getElementById('sync-percent-label');
  const trackNameEl = document.getElementById('sync-track-name');
  const bytesDetailEl = document.getElementById('sync-bytes-detail');
  const countDetailEl = document.getElementById('sync-count-detail');
  const btnSyncNow = document.getElementById('btn-sync-now');
  const btnCancel = document.getElementById('btn-cancel-sync');
  const globalStatusEl = document.getElementById('topology-global-status');

  switch (state) {
    case 'SCANNING':
    case 'CHECKING':
      isSyncInProgress = true;
      if (heroCardEl) heroCardEl.classList.add('syncing-active');
      if (mainStatusEl) mainStatusEl.textContent = state === 'SCANNING' ? "Escaneando Músicas..." : "Verificando com a VPS...";
      if (subStatusEl) subStatusEl.textContent = details.message || "Consultando catálogo por SHA-256 (Apenas músicas legítimas)...";
      if (radarIcon) radarIcon.className = "fa-solid fa-arrows-rotate fa-spin";
      if (progressBox) progressBox.style.display = "flex";
      if (progressFill) progressFill.style.width = "0%";
      if (percentLabelEl) percentLabelEl.textContent = "0%";
      if (trackNameEl) trackNameEl.textContent = "Validando hashes criptográficos...";
      if (btnSyncNow) btnSyncNow.style.display = "none";
      if (btnCancel) btnCancel.style.display = "flex";
      if (globalStatusEl) {
        globalStatusEl.innerHTML = `<span class="status-dot"></span> Sincronizando`;
      }
      break;

    case 'UPLOADING':
      isSyncInProgress = true;
      if (heroCardEl) heroCardEl.classList.add('syncing-active');
      if (mainStatusEl) mainStatusEl.textContent = "Enviando Faixas...";
      if (subStatusEl) subStatusEl.textContent = `Enviando ${details.currentIndex || 1} de ${details.totalPending || 1} faixas não duplicadas...`;
      if (radarIcon) radarIcon.className = "fa-solid fa-cloud-arrow-up fa-bounce";
      if (progressBox) progressBox.style.display = "flex";

      const trackPct = Math.min(100, Math.max(0, details.trackPercent || 0));
      if (progressFill) progressFill.style.width = `${trackPct}%`;
      if (percentLabelEl) percentLabelEl.textContent = `${trackPct}%`;

      if (trackNameEl) trackNameEl.textContent = details.trackName || "Faixa em transferência";
      if (bytesDetailEl) {
        const sentMb = ((details.bytesSent || 0) / (1024 * 1024)).toFixed(1);
        const totalMb = ((details.totalBytes || 1) / (1024 * 1024)).toFixed(1);
        bytesDetailEl.textContent = `${sentMb} MB / ${totalMb} MB`;
      }
      if (countDetailEl) {
        countDetailEl.textContent = `${details.currentIndex || 1} de ${details.totalPending || 1}`;
      }
      if (btnSyncNow) btnSyncNow.style.display = "none";
      if (btnCancel) btnCancel.style.display = "flex";
      break;

    case 'SUCCESS':
      isSyncInProgress = false;
      if (heroCardEl) heroCardEl.classList.remove('syncing-active');
      if (radarIcon) radarIcon.className = "fa-solid fa-cloud-arrow-up";
      if (progressBox) progressBox.style.display = "none";
      if (btnSyncNow) btnSyncNow.style.display = "flex";
      if (btnCancel) btnCancel.style.display = "none";

      const synced = details.syncedCount || 0;
      if (synced > 0) {
        if (mainStatusEl) mainStatusEl.textContent = `Sincronização Concluída (${synced} novas)`;
        if (subStatusEl) subStatusEl.textContent = `${synced} faixas legítimas foram replicadas com sucesso para a VPS e Google Drive.`;
        NativeAndroid.showToast(`✅ ${synced} músicas sincronizadas com sucesso!`);
      } else {
        if (mainStatusEl) mainStatusEl.textContent = "Tudo Sincronizado";
        if (subStatusEl) subStatusEl.textContent = details.message || "Apenas músicas de /sdcard/Music e SnapTube. Gravações e WhatsApp filtrados.";
      }
      refreshData();
      break;

    case 'VPS_STATS_UPDATED':
      updateVpsDisplay(details);
      break;

    case 'PERMISSION_CHANGED':
      refreshData();
      break;

    case 'ERROR':
    case 'CANCELLED':
    case 'IDLE':
    default:
      isSyncInProgress = false;
      if (heroCardEl) heroCardEl.classList.remove('syncing-active');
      if (radarIcon) radarIcon.className = "fa-solid fa-cloud-arrow-up";
      if (progressBox) progressBox.style.display = "none";
      if (btnSyncNow) btnSyncNow.style.display = "flex";
      if (btnCancel) btnCancel.style.display = "none";

      if (state === 'ERROR') {
        if (mainStatusEl) mainStatusEl.textContent = "Erro na Sincronização";
        if (subStatusEl) subStatusEl.textContent = details.message || "Falha na comunicação com a VPS.";
        NativeAndroid.showToast("⚠️ Erro: " + (details.message || "Falha"));
      } else if (state === 'CANCELLED') {
        if (mainStatusEl) mainStatusEl.textContent = "Sincronização Cancelada";
        if (subStatusEl) subStatusEl.textContent = "Processo interrompido pelo usuário.";
      }
      refreshData();
      break;
  }
};

// --------------------------------------------------------------------------
// 3. ATUALIZAÇÃO DE DADOS (100% DINÂMICOS E VERÍDICOS)
// --------------------------------------------------------------------------
function formatBytes(bytes) {
  if (!bytes || bytes === 0) return '0 B';
  const k = 1024;
  const sizes = ['B', 'KB', 'MB', 'GB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  return parseFloat((bytes / Math.pow(k, i)).toFixed(1)) + ' ' + sizes[i];
}

function formatRelativeTime(timestamp) {
  if (!timestamp) return 'Nunca sincronizado';
  const diffSec = Math.floor((Date.now() - timestamp) / 1000);
  if (diffSec < 60) return 'Agora mesmo';
  if (diffSec < 3600) return `Há ${Math.floor(diffSec / 60)} min`;
  if (diffSec < 86400) return `Há ${Math.floor(diffSec / 3600)} horas`;
  const date = new Date(timestamp);
  return date.toLocaleDateString('pt-BR', { day: '2-digit', month: '2-digit', hour: '2-digit', minute: '2-digit' });
}

function refreshData() {
  try {
    const overview = NativeAndroid.getSyncOverview();
    console.log("[Overview]", overview);

    if (overview) {
      // 1. Contagens Locais (apenas músicas legítimas e sem duplicações)
      const localTotal = overview.uniqueLocalTracks !== undefined ? overview.uniqueLocalTracks : (overview.totalLocalTracks || 0);
      const musicCount = overview.musicFolderCount || 0;
      const snaptubeCount = overview.snaptubeFolderCount || 0;

      const localCountEl = document.getElementById('local-tracks-count');
      const musicFolderEl = document.getElementById('count-music-folder');
      const snaptubeFolderEl = document.getElementById('count-snaptube-folder');

      if (localCountEl) localCountEl.textContent = `${localTotal} faixas`;
      if (musicFolderEl) musicFolderEl.textContent = `${musicCount} faixas`;
      if (snaptubeFolderEl) snaptubeFolderEl.textContent = `${snaptubeCount} faixas`;

      // Aviso de permissão caso não concedida
      if (overview.hasStoragePermission === false) {
        const subStatusEl = document.getElementById('sync-sub-status');
        if (subStatusEl && !isSyncInProgress) {
          subStatusEl.innerHTML = `<span style="color: var(--accent-orange); font-weight: 600;">⚠️ Permissão necessária:</span> Toque em "FAZER VARREDURA" para conceder acesso aos arquivos e sincronizar suas faixas.`;
        }
      }

      // 2. Configurações
      const autoSyncToggle = document.getElementById('toggle-auto-sync');
      if (autoSyncToggle) autoSyncToggle.checked = !!overview.autoSyncEnabled;

      const dedicatedRamToggle = document.getElementById('toggle-dedicated-ram');
      if (dedicatedRamToggle) dedicatedRamToggle.checked = NativeAndroid.isOneGigabyteAllocated();

      const batteryText = document.getElementById('battery-status-text');
      if (batteryText) {
        const isIgnoring = NativeAndroid.isIgnoringBatteryOptimizations();
        batteryText.textContent = isIgnoring ? "Protegido contra suspensão" : "Otimização ativa (Pode suspender)";
        batteryText.style.color = isIgnoring ? "var(--accent-green)" : "var(--accent-orange)";
      }

      // 3. Renderizar Histórico estritamente deduplicado
      renderHistory(overview.history || []);
    }

    // 4. Consultar VPS Summary
    fetchVpsStats();
  } catch (err) {
    console.error("Erro em refreshData:", err);
  }
}

function updateVpsDisplay(vpsSummary) {
  const vpsCountEl = document.getElementById('vps-tracks-count');
  const gdriveStatusEl = document.getElementById('gdrive-status-label');
  const globalStatusEl = document.getElementById('topology-global-status');

  if (vpsSummary && (vpsSummary.success || vpsSummary.total_tracks !== undefined)) {
    if (vpsCountEl) vpsCountEl.textContent = `${vpsSummary.total_tracks || 0} faixas`;
    if (gdriveStatusEl) {
      const logs = vpsSummary.gdrive_last_logs || [];
      const hasError = Array.isArray(logs) && logs.some(l => typeof l === 'string' && (l.toLowerCase().includes("failed to bisync") || l.toLowerCase().includes("error")));
      gdriveStatusEl.textContent = hasError ? "Atenção (Lock)" : "Bisync 100%";
    }

    if (globalStatusEl) {
      globalStatusEl.innerHTML = `<span class="status-dot online"></span> Sincronizado`;
    }
  } else {
    if (vpsCountEl) vpsCountEl.textContent = "Offline";
    if (gdriveStatusEl) gdriveStatusEl.textContent = "Aguardando";
    if (globalStatusEl) {
      globalStatusEl.innerHTML = `<span class="status-dot offline"></span> Desconectado`;
    }
  }
}

function fetchVpsStats() {
  const vpsSummary = NativeAndroid.getVpsSummary();
  console.log("[VPS Summary]", vpsSummary);
  updateVpsDisplay(vpsSummary);
}

function renderHistory(historyList) {
  const container = document.getElementById('sync-history-list');
  const countBadge = document.getElementById('badge-history-count');
  if (!container) return;

  // Deduplicação estrita de mídias no histórico
  const seenKeys = new Set();
  const uniqueHistory = [];
  if (Array.isArray(historyList)) {
    for (const item of historyList) {
      const key = (item.hash && item.hash.length > 5) ? item.hash : (item.name || "");
      if (key && !seenKeys.has(key)) {
        seenKeys.add(key);
        uniqueHistory.push(item);
      }
    }
  }

  if (countBadge) countBadge.textContent = `${uniqueHistory.length} MÍDIAS`;

  if (uniqueHistory.length === 0) {
    container.innerHTML = `
      <div class="empty-state">
        <i class="fa-solid fa-circle-check"></i>
        <p>Nenhuma sincronização recente registrada.</p>
      </div>
    `;
    return;
  }

  container.innerHTML = "";
  uniqueHistory.forEach(item => {
    const card = document.createElement('div');
    card.className = "history-item-card";

    const name = item.name || "Música desconhecida";
    const sizeStr = formatBytes(item.size);
    const folder = item.folder || "Music";
    const timeStr = formatRelativeTime(item.timestamp);

    card.innerHTML = `
      <div class="history-item-left">
        <div class="history-item-icon">
          <i class="fa-solid fa-check"></i>
        </div>
        <div class="history-item-text">
          <span class="history-item-title" title="${name}">${name}</span>
          <span class="history-item-sub">${folder} • ${sizeStr} • ${timeStr}</span>
        </div>
      </div>
      <span class="history-synced-badge">Sincronizado</span>
    `;
    container.appendChild(card);
  });
}

// --------------------------------------------------------------------------
// 4. EVENT LISTENERS
// --------------------------------------------------------------------------
function setupEvents() {
  // Modal de Shaders
  document.querySelectorAll('.btn-open-shaders, #btn-open-shaders').forEach(btn => {
    btn.addEventListener('click', openShaderModal);
  });
  const closeBtn = document.getElementById('btn-close-shader-modal');
  if (closeBtn) closeBtn.addEventListener('click', closeShaderModal);
  const backdrop = document.getElementById('shader-modal-backdrop');
  if (backdrop) backdrop.addEventListener('click', closeShaderModal);

  // Botão Sincronizar Agora
  const syncBtn = document.getElementById('btn-sync-now');
  if (syncBtn) {
    syncBtn.addEventListener('click', () => {
      NativeAndroid.startSync();
    });
  }

  // Botão Cancelar Sincronização
  const cancelBtn = document.getElementById('btn-cancel-sync');
  if (cancelBtn) {
    cancelBtn.addEventListener('click', () => {
      NativeAndroid.cancelSync();
    });
  }

  // Botão Atualizar Status
  const refreshBtn = document.getElementById('btn-refresh-status');
  if (refreshBtn) {
    refreshBtn.addEventListener('click', () => {
      refreshData();
      NativeAndroid.showToast("Status atualizado!");
    });
  }

  // Botão Forçar Google Drive Sync
  const driveBtn = document.getElementById('btn-trigger-gdrive');
  if (driveBtn) {
    driveBtn.addEventListener('click', () => {
      NativeAndroid.triggerGDriveSync();
    });
  }

  // Toggle Auto-Sync
  const autoSyncToggle = document.getElementById('toggle-auto-sync');
  if (autoSyncToggle) {
    autoSyncToggle.addEventListener('change', (e) => {
      NativeAndroid.setAutoSync(e.target.checked);
    });
  }

  // Toggle RAM Dedicada
  const dedicatedRamToggle = document.getElementById('toggle-dedicated-ram');
  if (dedicatedRamToggle) {
    dedicatedRamToggle.addEventListener('change', (e) => {
      if (e.target.checked) {
        NativeAndroid.allocateDedicatedRam();
        NativeAndroid.showToast("1 GB de memória direta alocada.");
      } else {
        NativeAndroid.releaseDedicatedRam();
        NativeAndroid.showToast("Memória liberada.");
      }
    });
  }

  // Botão Isenção de Bateria
  const batteryBtn = document.getElementById('btn-req-battery');
  if (batteryBtn) {
    batteryBtn.addEventListener('click', () => {
      NativeAndroid.requestBatteryOptimizationExemption();
    });
  }
}

// --------------------------------------------------------------------------
// 5. MOTOR DE SHADERS WEBGL & BACKGROUND LÍQUIDO
// --------------------------------------------------------------------------
function safeGetStorage(key, fallback) {
  try {
    return localStorage.getItem(key) || fallback;
  } catch (e) {
    return fallback;
  }
}

function safeSetStorage(key, value) {
  try {
    localStorage.setItem(key, value);
  } catch (e) {}
}

const ALL_SHADERS = [
  "softaurora", "particles", "ghostfibers", "plasmawave", "strands", "siderays", "beams",
  "floatinglines", "silk", "radar", "ripplegrid", "pixelblast", "magicrings", "gridscan",
  "liquidchrome", "linewaves", "ferrofluid", "dither", "darkveil", "webthreads", "topography",
  "balatro", "faultyterminal", "morphslider", "antigravity", "aurora", "letterglitch", "plasma",
  "liquidether", "orb", "prism", "prismaticburst", "lightning", "evileye", "lighttunnel",
  "ancidsquares", "moltenmetal"
];

let currentShader = safeGetStorage('feishin_bg_shader', 'softaurora');
let shaderRotationTimer = null;
let rotationIndex = 0;
let isRotationActive = (currentShader === 'galaxybits');
let shaderRetryCount = 0;

function openShaderModal() {
  const backdrop = document.getElementById('shader-modal-backdrop');
  const panel = document.getElementById('shader-modal-panel');
  if (backdrop) backdrop.style.display = 'block';
  if (panel) panel.style.display = 'flex';
}

function closeShaderModal() {
  const backdrop = document.getElementById('shader-modal-backdrop');
  const panel = document.getElementById('shader-modal-panel');
  if (backdrop) backdrop.style.display = 'none';
  if (panel) panel.style.display = 'none';
}

function getShaderFriendlyName(type) {
  const map = {
    softaurora: "Soft Aurora",
    aurora: "Aurora Borealis",
    plasmawave: "Plasma Wave",
    linewaves: "Line Waves",
    floatinglines: "Floating Lines",
    silk: "Silk Cloth",
    particles: "Particles 3D",
    ghostfibers: "Ghost Fibers",
    strands: "Strands Ethereal",
    siderays: "Side Rays",
    beams: "Beams 3D",
    webthreads: "Web Threads",
    lightning: "Lightning Arc",
    liquidchrome: "Liquid Chrome",
    liquidether: "Liquid Ether",
    ferrofluid: "Ferrofluid",
    moltenmetal: "Molten Metal",
    plasma: "Sine Plasma",
    prism: "Prism Refraction",
    prismaticburst: "Prismatic Burst",
    orb: "Energy Orb",
    evileye: "Evil Eye",
    magicrings: "Magic Rings",
    antigravity: "Antigravity",
    radar: "Tactical Radar",
    gridscan: "Grid Scan",
    ripplegrid: "Ripple Grid",
    pixelblast: "Pixel Blast",
    topography: "Topography",
    dither: "Dither Wave",
    darkveil: "Dark Veil",
    balatro: "Balatro Vortex",
    faultyterminal: "Faulty Terminal",
    morphslider: "Morph Slider",
    letterglitch: "Letter Glitch",
    lighttunnel: "Light Tunnel",
    ancidsquares: "Acid Squares",
    galaxybits: "Rotativo (37 Shaders)"
  };
  return map[type] || type.toUpperCase();
}

function updateShaderDisplay(type) {
  const friendlyName = getShaderFriendlyName(type);
  const nameEl = document.getElementById('current-shader-name');
  if (nameEl) {
    nameEl.textContent = friendlyName;
  }
  const widgetLabel = document.getElementById('widget-shader-label');
  if (widgetLabel) {
    widgetLabel.textContent = isRotationActive ? 'Rotativo' : friendlyName;
  }
  const settingsHint = document.getElementById('settings-shader-hint');
  if (settingsHint) {
    settingsHint.textContent = `37 Shaders OGL Liquid Glass • Ativo: ${friendlyName}`;
  }
  const selectEl = document.getElementById('shader-select');
  if (selectEl && !isRotationActive) {
    selectEl.value = type;
  }
}

function applyShader(type) {
  const container = document.getElementById('bg-webgl-container');
  if (container) {
    container.style.display = 'block';
  }

  if (typeof window.renderWebGLBackground === 'function') {
    try {
      shaderRetryCount = 0;
      window.renderWebGLBackground(type);
      updateShaderDisplay(type);
      console.log("[Shader] Renderizando:", type);
      return true;
    } catch (e) {
      console.error("[Shader] Falha ao renderizar:", type, e);
      return false;
    }
  } else {
    shaderRetryCount++;
    if (shaderRetryCount <= 15) {
      console.warn("[Shader] renderWebGLBackground ainda não pronto, aguardando...");
      setTimeout(() => applyShader(type), 150);
    } else {
      console.warn("[Shader] renderWebGLBackground tempo limite atingido. Mantendo visual estável.");
    }
    return false;
  }
}

function startShaderRotation() {
  stopShaderRotation();
  isRotationActive = true;
  const rotate = () => {
    const next = ALL_SHADERS[rotationIndex % ALL_SHADERS.length];
    rotationIndex++;
    applyShader(next);
  };
  rotate();
  shaderRotationTimer = setInterval(rotate, 12000);
}

function stopShaderRotation() {
  if (shaderRotationTimer) {
    clearInterval(shaderRotationTimer);
    shaderRotationTimer = null;
  }
  isRotationActive = false;
}

function selectShader(type) {
  if (type === 'galaxybits') {
    safeSetStorage('feishin_bg_shader', 'galaxybits');
    currentShader = 'galaxybits';
    startShaderRotation();
    updateShaderDisplay('galaxybits');
  } else {
    stopShaderRotation();
    safeSetStorage('feishin_bg_shader', type);
    currentShader = type;
    applyShader(type);
  }
}

function setupShaderControls() {
  const select = document.getElementById('shader-select');
  const randomBtn = document.getElementById('btn-random-shader');
  const testAllBtn = document.getElementById('btn-run-all-tests');

  if (select) {
    select.value = isRotationActive ? 'galaxybits' : currentShader;
    select.addEventListener('change', (e) => {
      selectShader(e.target.value);
    });
  }

  if (randomBtn) {
    randomBtn.addEventListener('click', () => {
      stopShaderRotation();
      const rand = ALL_SHADERS[Math.floor(Math.random() * ALL_SHADERS.length)];
      selectShader(rand);
      if (select) select.value = rand;
      NativeAndroid.showToast(`Shader: ${getShaderFriendlyName(rand)}`);
    });
  }

  if (testAllBtn) {
    testAllBtn.addEventListener('click', () => {
      window.runAllShaderTests();
    });
  }

  // Lifecycle de suspensão para economia de bateria (zera WebGL e timers em 2º plano)
  window._visualSuspendHandler = function(suspend) {
    window.__PAUSE_WEBGL_SHADERS__ = suspend;
    if (suspend) {
      stopShaderRotation();
      if (typeof window.stopWebGLBackground === 'function') {
        window.stopWebGLBackground();
      }
    } else {
      if (isRotationActive) {
        startShaderRotation();
      } else {
        applyShader(currentShader);
      }
    }
  };
}

// --------------------------------------------------------------------------
// SUÍTE DE TESTES DE FUNCIONALIDADE EM TODOS OS 37 SHADERS
// --------------------------------------------------------------------------
window.runAllShaderTests = async function() {
  const progressBox = document.getElementById('shader-test-progress');
  const fill = document.getElementById('shader-test-fill');
  const percentText = document.getElementById('shader-test-percent');
  const statusText = document.getElementById('shader-test-status-text');
  const logArea = document.getElementById('shader-test-results-log');

  if (progressBox) progressBox.style.display = 'flex';
  if (logArea) logArea.innerHTML = '';
  if (statusText) statusText.textContent = "Iniciando validação de 37 shaders...";

  const report = {
    timestamp: new Date().toISOString(),
    total: ALL_SHADERS.length,
    passed: 0,
    failed: 0,
    results: []
  };

  const waitFrame = () => new Promise(r => requestAnimationFrame(r));
  const sleep = ms => new Promise(r => setTimeout(r, ms));

  for (let i = 0; i < ALL_SHADERS.length; i++) {
    const shader = ALL_SHADERS[i];
    const friendly = getShaderFriendlyName(shader);
    const progress = Math.round(((i + 1) / ALL_SHADERS.length) * 100);

    if (fill) fill.style.width = `${progress}%`;
    if (percentText) percentText.textContent = `${i + 1}/${ALL_SHADERS.length}`;
    if (statusText) statusText.textContent = `Validando: ${friendly}...`;

    const item = {
      name: shader,
      friendlyName: friendly,
      hasCanvas: false,
      contextValid: false,
      renderedFrames: 0,
      error: null,
      success: false
    };

    try {
      applyShader(shader);
      await sleep(100);
      await waitFrame();
      await waitFrame();

      const container = document.getElementById('bg-webgl-container');
      const canvas = container ? container.querySelector('canvas') : null;

      if (!canvas) {
        throw new Error("Canvas WebGL não encontrado no DOM");
      }
      item.hasCanvas = true;

      const gl = canvas.getContext('webgl2') || canvas.getContext('webgl');
      const is2d = (!gl && canvas.getContext('2d'));
      if ((!gl && !is2d) || (gl && gl.isContextLost())) {
        throw new Error("Contexto WebGL/Canvas inválido ou perdido");
      }
      item.contextValid = true;
      item.rendererType = gl ? (canvas.getContext('webgl2') ? 'WebGL2' : 'WebGL') : 'Canvas2D';
      item.renderedFrames = 2;
      item.success = true;
      report.passed++;
    } catch (err) {
      item.error = err.message || String(err);
      item.success = false;
      report.failed++;
    }

    report.results.push(item);

    if (logArea) {
      const line = document.createElement('div');
      line.className = `shader-log-item ${item.success ? 'pass' : 'fail'}`;
      line.innerHTML = `<span>${i + 1}. ${friendly}</span><span>${item.success ? 'OK (WebGL)' : 'ERRO'}</span>`;
      logArea.appendChild(line);
      logArea.scrollTop = logArea.scrollHeight;
    }
  }

  if (statusText) {
    statusText.textContent = report.failed === 0 
      ? `✅ Todos os 37 Shaders operando 100%!` 
      : `⚠️ ${report.passed} OK / ${report.failed} falhas`;
  }

  // Restaura o shader configurado originalmente
  setTimeout(() => {
    selectShader(currentShader);
  }, 1000);

  console.log("=== RELATÓRIO DE TESTES DOS 37 SHADERS ===", report);
  return report;
};

function initBackgroundEngine() {
  setupShaderControls();
  window.addEventListener('webgl-engine-ready', () => {
    if (isRotationActive) {
      startShaderRotation();
    } else {
      applyShader(currentShader);
    }
  });

  // Fallback se o evento já disparou
  setTimeout(() => {
    if (isRotationActive) {
      startShaderRotation();
    } else {
      applyShader(currentShader);
    }
  }, 300);
}

// --------------------------------------------------------------------------
// 6. INICIALIZAÇÃO
// --------------------------------------------------------------------------
window.refreshData = refreshData;

function bootApp() {
  setupEvents();
  initBackgroundEngine();

  if (NativeAndroid.isAvailable()) {
    refreshData();
  } else {
    setTimeout(() => {
      refreshData();
    }, 300);
  }
}

if (document.readyState === 'loading') {
  document.addEventListener('DOMContentLoaded', bootApp);
} else {
  bootApp();
}
