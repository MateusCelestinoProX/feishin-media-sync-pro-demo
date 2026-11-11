# Graph Report - feishin-media-sync-pro-demo  (2026-11-11)

## Corpus Check
- 37 files · ~177,124 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 4877 nodes · 11099 edges · 136 communities (96 shown, 37 thin omitted)
- Extraction: 94% EXTRACTED · 6% INFERRED · 0% AMBIGUOUS · INFERRED: 719 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `32716c1a`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- package.json
- sync-engine.ts
- compilerOptions
- 📱 Feishin Media Sync Pro — Aplicativo Android Nativo (Portfolio Demo)
- webgl-backgrounds.js
- _
- assets/js/vendor/three.min.js
- js/vendor/three.min.js
- _
- js/webgl-backgrounds.js
- tn
- .constructor
- ol
- vt
- Xe
- en
- St
- U
- ws
- copy
- .fromArray
- ar
- W
- Lc
- xc
- app.js
- FloatingWidgetService
- tn
- MainActivity
- .z
- jc
- AudioPlaybackService
- it
- ke
- ct
- fe
- V
- .copy
- ws
- ml
- zt
- sn
- yc
- WebAppInterface
- ls
- MediaSyncManager
- ya
- se
- constructor
- Lt
- yt
- Ce
- zl
- ks
- us
- vt
- pt
- copy
- MediaSyncService
- .dispatchEvent
- ._update
- .update
- pt
- bt
- le
- Ce
- re
- tn
- yo
- At
- en
- mr
- jt
- nc
- At
- q
- zl
- en
- Lt
- ._update
- .toJSON
- Na
- V
- St
- ar
- Ne
- manifest.json
- U
- Lc
- .toJSON
- it
- ks
- q
- tn
- build_android_apk.sh
- en
- xc
- SyncStorage
- yc
- constructor
- sn
- Kn
- build_feishin_sync_assets.py
- app.js
- W
- jc
- ke
- gradlew
- .z
- .update
- je
- assets/demo-android-bridge.js
- DirectMemoryGovernor
- demo-android-bridge.js
- ct
- yo
- le
- nc
- zt
- wo
- qo
- vercel.json
- manifest.json
- bind
- .rotateOnAxis
- assets/demo-sync-ui.js
- demo-sync-ui.js
- fl
- qc
- bind
- ic
- qo
- Zc

## God Nodes (most connected - your core abstractions)
1. `_` - 208 edges
2. `_` - 208 edges
3. `Lt` - 135 edges
4. `Lt` - 135 edges
5. `copy()` - 128 edges
6. `copy()` - 128 edges
7. `vt` - 90 edges
8. `vt` - 90 edges
9. `ws()` - 69 edges
10. `ws()` - 69 edges

## Surprising Connections (you probably didn't know these)
- `xi()` --indirect_call--> `f()`  [INFERRED]
  android/app/src/main/assets/js/ogl.js → android/app/src/main/assets/js/vendor/three.min.js
- `ws()` --indirect_call--> `R()`  [INFERRED]
  android/app/src/main/assets/js/vendor/three.min.js → android/app/src/main/assets/js/ogl.js
- `et()` --indirect_call--> `S()`  [INFERRED]
  android/app/src/main/assets/js/ogl.js → android/app/src/main/assets/js/vendor/three.min.js
- `et()` --indirect_call--> `Y()`  [INFERRED]
  android/app/src/main/assets/js/ogl.js → android/app/src/main/assets/js/vendor/three.min.js
- `xs()` --indirect_call--> `Y()`  [INFERRED]
  android/app/src/main/assets/js/ogl.js → android/app/src/main/assets/js/vendor/three.min.js

## Import Cycles
- None detected.

## Communities (136 total, 37 thin omitted)

### Community 0 - "package.json"
Cohesion: 0.11
Nodes (17): author, bin, feishin-sync, description, engines, node, keywords, license (+9 more)

### Community 1 - "sync-engine.ts"
Cohesion: 0.26
Nodes (6): SyncEngine, CacheManager, MediaTrack, PlaylistSyncTarget, SyncConfig, SyncResult

### Community 2 - "compilerOptions"
Cohesion: 0.14
Nodes (13): compilerOptions, declaration, esModuleInterop, forceConsistentCasingInFileNames, module, moduleResolution, outDir, rootDir (+5 more)

### Community 3 - "📱 Feishin Media Sync Pro — Aplicativo Android Nativo (Portfolio Demo)"
Cohesion: 0.29
Nodes (6): 📥 Download Direto do APK de Produção:, 🛠️ Estrutura do Repositório, 🚀 Execução do Simulador Web, 📱 Feishin Media Sync Pro — Aplicativo Android Nativo (Portfolio Demo), ⚠️ Natureza do Software: Exclusivamente Android Nativo (.APK), ⚡ Recursos Nativos de Engenharia Android

### Community 4 - "webgl-backgrounds.js"
Cohesion: 0.06
Nodes (62): gt, buildStrandsPalette(), easePower2InOut(), getOptimizedDPR(), hexToRgb(), hexToRgbArr(), initAcidSquares(), initAntigravity() (+54 more)

### Community 5 - "_"
Cohesion: 0.03
Nodes (51): _, ae(), at, bs(), bt(), ce(), ct(), dt (+43 more)

### Community 6 - "assets/js/vendor/three.min.js"
Cohesion: 0.03
Nodes (48): Vr(), _allocateTargets(), _applyPMREM(), bi(), _blur(), bs, $c(), _cleanup() (+40 more)

### Community 7 - "js/vendor/three.min.js"
Cohesion: 0.02
Nodes (85): Vr(), Ah, _allocateTargets(), ao(), _applyPMREM(), Ba, _blur(), $c() (+77 more)

### Community 8 - "_"
Cohesion: 0.03
Nodes (53): _, ae(), at, bs(), bt(), ce(), ct(), dt (+45 more)

### Community 9 - "js/webgl-backgrounds.js"
Cohesion: 0.06
Nodes (62): gt, buildStrandsPalette(), easePower2InOut(), getOptimizedDPR(), hexToRgb(), hexToRgbArr(), initAcidSquares(), initAntigravity() (+54 more)

### Community 10 - "tn"
Cohesion: 0.05
Nodes (10): bo, dt(), $e(), is(), Ke(), ms(), Qe(), ss() (+2 more)

### Community 11 - ".constructor"
Cohesion: 0.07
Nodes (13): z(), ge, gs, mi(), m(), y(), oi(), M() (+5 more)

### Community 12 - "ol"
Cohesion: 0.07
Nodes (18): cl, hl, ic, load(), oc, ol, parse(), parseAnimations() (+10 more)

### Community 13 - "vt"
Cohesion: 0.04
Nodes (8): $h(), Kh(), qh(), _textureToCubeUV(), tu(), Vs, vt, Yl

### Community 14 - "Xe"
Cohesion: 0.09
Nodes (8): _a, cs, Do, go, hs, Rs, Xe(), zo

### Community 15 - "en"
Cohesion: 0.08
Nodes (10): ca, v(), en, Ga, mn, i(), Pa, Si() (+2 more)

### Community 17 - "U"
Cohesion: 0.04
Nodes (22): ai(), be(), ci(), di(), fi(), gi(), hi(), ii() (+14 more)

### Community 18 - "ws"
Cohesion: 0.07
Nodes (47): j(), L(), tt(), dispose(), ds(), F(), G(), k() (+39 more)

### Community 19 - "copy"
Cohesion: 0.04
Nodes (24): Aa, ai, bt, ci, clone(), copy(), ei, fi() (+16 more)

### Community 20 - ".fromArray"
Cohesion: 0.05
Nodes (6): bl, Da, ec, ia, jn(), la

### Community 21 - "ar"
Cohesion: 0.16
Nodes (3): ar, dr(), pr()

### Community 22 - "W"
Cohesion: 0.06
Nodes (15): bi(), Fs(), _i(), on, pn, pt, rn, Si() (+7 more)

### Community 23 - "Lc"
Cohesion: 0.08
Nodes (4): bc, getInput(), getOutput(), Lc

### Community 25 - "app.js"
Cohesion: 0.18
Nodes (22): ALL_SHADERS, applyShader(), bootApp(), closeShaderModal(), currentShader, fetchVpsStats(), formatBytes(), formatRelativeTime() (+14 more)

### Community 26 - "FloatingWidgetService"
Cohesion: 0.11
Nodes (10): android.annotation.SuppressLint, android.app.Service, FloatingWidgetService, Intent, Override, WakeLock, android.content.Intent, android.view.View (+2 more)

### Community 28 - "MainActivity"
Cohesion: 0.10
Nodes (12): Intent, Override, MainActivity, android.content.BroadcastReceiver, android.os.Bundle, android.webkit.WebView, android.widget.ProgressBar, androidx.activity.result.ActivityResultLauncher (+4 more)

### Community 29 - ".z"
Cohesion: 0.08
Nodes (4): Gs(), Hs(), qs(), Xs()

### Community 32 - "AudioPlaybackService"
Cohesion: 0.14
Nodes (11): android.app.PendingIntent, AudioPlaybackService, Intent, Override, WakeLock, android.graphics.Bitmap, android.media.AudioFocusRequest, android.media.AudioManager (+3 more)

### Community 33 - "it"
Cohesion: 0.14
Nodes (3): Ds(), it, St

### Community 34 - "ke"
Cohesion: 0.10
Nodes (11): er, fr(), A(), E(), m(), v(), ji(), ke (+3 more)

### Community 35 - "ct"
Cohesion: 0.13
Nodes (4): ct(), es(), ns(), Mt()

### Community 36 - "fe"
Cohesion: 0.09
Nodes (3): pe, fe, ge

### Community 37 - "V"
Cohesion: 0.04
Nodes (18): cr, De(), he(), ki(), Lt, qi(), sr(), C() (+10 more)

### Community 38 - ".copy"
Cohesion: 0.15
Nodes (5): he(), Lt, Ot, ur(), ye()

### Community 39 - "ws"
Cohesion: 0.06
Nodes (58): j(), bi(), bs, ci, dispose(), ds(), F(), G() (+50 more)

### Community 40 - "ml"
Cohesion: 0.06
Nodes (5): il(), Ll, ml, nl, Rl

### Community 41 - "zt"
Cohesion: 0.21
Nodes (4): Ie(), Ne(), or, zt

### Community 42 - "sn"
Cohesion: 0.05
Nodes (11): an, cn, dn, fn, hn, ln, on, pn (+3 more)

### Community 46 - "MediaSyncManager"
Cohesion: 0.08
Nodes (13): JSONObject, LocalMediaTrack, JSONObject, FolderStats, JSONObject, MediaSyncManager, SyncListener, UploadProgressCallback (+5 more)

### Community 47 - "ya"
Cohesion: 0.08
Nodes (21): Ah, ao(), co(), eo(), fh, hh, ho(), io() (+13 more)

### Community 48 - "se"
Cohesion: 0.03
Nodes (9): we(), as(), Da, dl, ia, la, os(), se (+1 more)

### Community 49 - "constructor"
Cohesion: 0.05
Nodes (52): bindSkeletons(), br(), r(), cl, constructor(), d(), u(), v() (+44 more)

### Community 50 - "Lt"
Cohesion: 0.03
Nodes (10): S(), A(), ht(), Lt, Ne, re, rh, setDirection() (+2 more)

### Community 52 - "Ce"
Cohesion: 0.05
Nodes (11): Ee(), Se(), Te(), Ce, Ql, qn, sh, Si() (+3 more)

### Community 53 - "zl"
Cohesion: 0.08
Nodes (9): dc, n(), fo(), mo(), l(), parseShapes(), pc, po (+1 more)

### Community 54 - "ks"
Cohesion: 0.10
Nodes (3): ge(), ks, me()

### Community 55 - "us"
Cohesion: 0.05
Nodes (12): clone(), fs, A(), Jl, kl, Kn, ts(), us() (+4 more)

### Community 57 - "pt"
Cohesion: 0.03
Nodes (5): eh, je, jt(), pt(), Dt()

### Community 58 - "copy"
Cohesion: 0.05
Nodes (9): Aa, copy(), ec, jn(), Rs, _s(), n(), sc (+1 more)

### Community 59 - "MediaSyncService"
Cohesion: 0.13
Nodes (10): android.app.Notification, android.app.NotificationManager, CustomFileObserver, Intent, Override, MediaSyncService, android.database.ContentObserver, android.os.FileObserver (+2 more)

### Community 60 - ".dispatchEvent"
Cohesion: 0.14
Nodes (5): Re(), gs, o(), v(), ys

### Community 62 - ".update"
Cohesion: 0.16
Nodes (4): cs, is, rr, Ys()

### Community 64 - "bt"
Cohesion: 0.07
Nodes (13): bt, ei, s(), Et, ii, li(), ni, parseTextures() (+5 more)

### Community 66 - "Ce"
Cohesion: 0.03
Nodes (21): _e(), Ee(), ri(), Se(), as(), Ce, Ea(), fa (+13 more)

### Community 68 - "tn"
Cohesion: 0.04
Nodes (18): _a, bo, ca, cs, Do, dt(), $e(), go (+10 more)

### Community 70 - "At"
Cohesion: 0.06
Nodes (4): pe, xe(), At, fe

### Community 72 - "mr"
Cohesion: 0.15
Nodes (4): kt(), mr, Ut(), yr

### Community 76 - "q"
Cohesion: 0.08
Nodes (9): As(), br, p(), gr, K, kr, nr, q (+1 more)

### Community 77 - "zl"
Cohesion: 0.13
Nodes (7): n(), fo(), mo(), l(), parseShapes(), po, zl

### Community 78 - "en"
Cohesion: 0.06
Nodes (6): Ea(), en, Ga, ls(), setFromCamera(), wh()

### Community 79 - "Lt"
Cohesion: 0.03
Nodes (4): S(), ht(), Lt, rh

### Community 80 - "._update"
Cohesion: 0.13
Nodes (3): jo, wo, xo

### Community 81 - ".toJSON"
Cohesion: 0.03
Nodes (12): ac, Al, bl, el, fl, gl, Ll, ml (+4 more)

### Community 82 - "Na"
Cohesion: 0.18
Nodes (5): Ba, Ko, Na, _o, to()

### Community 83 - "V"
Cohesion: 0.06
Nodes (10): cr, De(), ki(), qi(), sr(), C(), et(), N() (+2 more)

### Community 85 - "ar"
Cohesion: 0.08
Nodes (8): ar, dr(), kt(), mr, Ot, pr(), Ut(), yr

### Community 86 - "Ne"
Cohesion: 0.09
Nodes (3): ri(), ai, Ne

### Community 87 - "manifest.json"
Cohesion: 0.20
Nodes (9): background_color, description, display, icons, name, orientation, short_name, start_url (+1 more)

### Community 88 - "U"
Cohesion: 0.05
Nodes (14): ai(), ci(), di(), gi(), hi(), ii(), li(), mi() (+6 more)

### Community 89 - "Lc"
Cohesion: 0.08
Nodes (4): bc, getInput(), getOutput(), Lc

### Community 90 - ".toJSON"
Cohesion: 0.05
Nodes (8): ac, Al, r(), dl, el, pl, tc, ts()

### Community 91 - "it"
Cohesion: 0.16
Nodes (3): Ds(), it, St

### Community 92 - "ks"
Cohesion: 0.10
Nodes (3): ge(), ks, me()

### Community 93 - "q"
Cohesion: 0.08
Nodes (8): As(), br, gr, K, kr, nr, q, zr

### Community 95 - "build_android_apk.sh"
Cohesion: 0.33
Nodes (5): ANDROID_HOME, BUILD_TOOLS, JAVA_HOME, PATH, build_android_apk.sh script

### Community 98 - "SyncStorage"
Cohesion: 0.13
Nodes (5): SyncStorage, android.content.Context, android.content.SharedPreferences, JSONArray, org.json.JSONArray

### Community 100 - "constructor"
Cohesion: 0.07
Nodes (48): ze, br(), constructor(), d(), u(), dc, i(), s() (+40 more)

### Community 101 - "sn"
Cohesion: 0.04
Nodes (12): an, cn, dn, fn, hn, ln, Mh, on (+4 more)

### Community 102 - "Kn"
Cohesion: 0.11
Nodes (4): fs, Jl, Kn, vl()

### Community 103 - "build_feishin_sync_assets.py"
Cohesion: 0.70
Nodes (4): build_masters(), create_arrow(), export_all(), render_svg_to_png()

### Community 104 - "app.js"
Cohesion: 0.18
Nodes (22): ALL_SHADERS, applyShader(), bootApp(), closeShaderModal(), currentShader, fetchVpsStats(), formatBytes(), formatRelativeTime() (+14 more)

### Community 105 - "W"
Cohesion: 0.05
Nodes (19): be(), bi(), _i(), nn(), on, pn, pt, rn (+11 more)

### Community 107 - "ke"
Cohesion: 0.09
Nodes (12): er, fr(), A(), E(), m(), v(), p(), ji() (+4 more)

### Community 108 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 109 - ".z"
Cohesion: 0.08
Nodes (4): Gs(), Hs(), qs(), Xs()

### Community 110 - ".update"
Cohesion: 0.12
Nodes (5): cs, Fs(), is, rr, Ys()

### Community 112 - "assets/demo-android-bridge.js"
Cohesion: 0.19
Nodes (9): allocateDedicatedRam(), cancelSync(), releaseDedicatedRam(), requestBatteryOptimizationExemption(), requestStoragePermissions(), setAutoSync(), showToast(), toggleFloatingBubble() (+1 more)

### Community 113 - "DirectMemoryGovernor"
Cohesion: 0.47
Nodes (3): DirectMemoryGovernor, android.os.SharedMemory, java.nio.ByteBuffer

### Community 114 - "demo-android-bridge.js"
Cohesion: 0.19
Nodes (9): allocateDedicatedRam(), cancelSync(), releaseDedicatedRam(), requestBatteryOptimizationExemption(), requestStoragePermissions(), setAutoSync(), showToast(), toggleFloatingBubble() (+1 more)

### Community 115 - "ct"
Cohesion: 0.08
Nodes (5): ct(), es(), ns(), qc, Mt()

### Community 119 - "zt"
Cohesion: 0.24
Nodes (4): Ie(), Ne(), or, zt

### Community 124 - "vercel.json"
Cohesion: 0.20
Nodes (9): buildCommand, cleanUrls, framework, headers, installCommand, outputDirectory, rewrites, $schema (+1 more)

### Community 125 - "manifest.json"
Cohesion: 0.20
Nodes (9): background_color, description, display, icons, name, orientation, short_name, start_url (+1 more)

### Community 128 - "assets/demo-sync-ui.js"
Cohesion: 0.36
Nodes (6): checkApkPrompt(), closeApkModal(), createApkModal(), createSplashScreen(), exitSplash(), openApkModal()

### Community 129 - "demo-sync-ui.js"
Cohesion: 0.36
Nodes (6): checkApkPrompt(), closeApkModal(), createApkModal(), createSplashScreen(), exitSplash(), openApkModal()

### Community 132 - "bind"
Cohesion: 0.33
Nodes (4): bind(), bindSkeletons(), getValue(), parseSkeletons()

## Knowledge Gaps
- **131 isolated node(s):** `NativeAndroid`, `ALL_SHADERS`, `currentShader`, `fe`, `Ei` (+126 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 1407 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **37 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `_` connect `_` to `webgl-backgrounds.js`, `assets/js/vendor/three.min.js`, `U`, `ar`, `W`, `tn`, `.z`, `it`, `ke`, `fe`, `.copy`, `zt`, `ks`, `.dispatchEvent`, `.update`, `le`, `Ce`, `en`, `mr`, `q`, `V`, `constructor`, `.rotateOnAxis`?**
  _High betweenness centrality (0.087) - this node is a cross-community bridge._
- **Why does `_` connect `_` to `js/vendor/three.min.js`, `js/webgl-backgrounds.js`, `V`, `se`, `Ce`, `At`, `ar`, `Ne`, `U`, `it`, `ks`, `q`, `tn`, `en`, `W`, `ke`, `.z`, `.update`, `je`, `le`, `zt`?**
  _High betweenness centrality (0.061) - this node is a cross-community bridge._
- **Why does `ws()` connect `ws` to `ke`, `Ce`, `bind`, `constructor`, `assets/js/vendor/three.min.js`, `ct`, `yo`, `tn`, `vt`, `en`, `St`, `Lt`, `copy`, `.fromArray`, `pt`, `.dispatchEvent`?**
  _High betweenness centrality (0.038) - this node is a cross-community bridge._
- **What connects `NativeAndroid`, `ALL_SHADERS`, `currentShader` to the rest of the system?**
  _131 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `package.json` be split into smaller, more focused modules?**
  _Cohesion score 0.1111111111111111 - nodes in this community are weakly interconnected._
- **Should `compilerOptions` be split into smaller, more focused modules?**
  _Cohesion score 0.14285714285714285 - nodes in this community are weakly interconnected._
- **Should `webgl-backgrounds.js` be split into smaller, more focused modules?**
  _Cohesion score 0.060191518467852256 - nodes in this community are weakly interconnected._