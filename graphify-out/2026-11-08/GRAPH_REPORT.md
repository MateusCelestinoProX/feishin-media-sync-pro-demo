# Graph Report - feishin-media-sync-pro  (2026-11-08)

## Corpus Check
- 27 files · ~130,139 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 2560 nodes · 5846 edges · 118 communities (62 shown, 45 thin omitted)
- Extraction: 93% EXTRACTED · 7% INFERRED · 0% AMBIGUOUS · INFERRED: 387 edges (avg confidence: 0.84)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `dcf84555`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- package.json
- sync-engine.ts
- compilerOptions
- Feishin Media Sync Pro 🎵🚀
- webgl-backgrounds.js
- _
- three.min.js
- q
- Wn
- parseObject
- tn
- ol
- e
- vt
- Lt
- en
- St
- U
- gt
- se
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
- V
- ct
- .constructor
- update
- .toJSON
- Ne
- nl
- be
- sn
- yc
- WebAppInterface
- ls
- MediaSyncManager
- no
- yt
- eh
- copy
- hl
- .x
- ks
- i
- ml
- pt
- bl
- MediaSyncService
- jt
- ke
- .update
- re
- ec
- le
- Ce
- parse
- ws
- yo
- qo
- en
- mr
- Kn
- nc
- At
- K
- Na
- la
- wo
- bt
- qc
- .copy
- .addAttribute
- .fromArray
- manifest.json
- fo
- Ll
- pl
- fl
- Rl
- Al
- el
- build_android_apk.sh
- .applyMatrix4
- Xe
- bind
- .distanceTo
- constructor
- Tt
- build_feishin_sync_assets.py
- ic
- Et
- rc
- gradlew
- ge
- sc
- .getRenderList
- Zc
- ia

## God Nodes (most connected - your core abstractions)
1. `_` - 208 edges
2. `Lt` - 135 edges
3. `copy()` - 128 edges
4. `vt` - 90 edges
5. `ws()` - 70 edges
6. `tn` - 65 edges
7. `St` - 60 edges
8. `Ce` - 59 edges
9. `sn` - 53 edges
10. `se` - 49 edges

## Surprising Connections (you probably didn't know these)
- `xi()` --indirect_call--> `f()`  [INFERRED]
  android/app/src/main/assets/js/ogl.js → android/app/src/main/assets/js/vendor/three.min.js
- `ws()` --indirect_call--> `R()`  [INFERRED]
  android/app/src/main/assets/js/vendor/three.min.js → android/app/src/main/assets/js/ogl.js
- `ws()` --indirect_call--> `L()`  [INFERRED]
  android/app/src/main/assets/js/vendor/three.min.js → android/app/src/main/assets/js/ogl.js
- `j()` --indirect_call--> `Y()`  [INFERRED]
  android/app/src/main/assets/js/ogl.js → android/app/src/main/assets/js/vendor/three.min.js
- `gt()` --indirect_call--> `j()`  [INFERRED]
  android/app/src/main/assets/js/vendor/three.min.js → android/app/src/main/assets/js/ogl.js

## Import Cycles
- None detected.

## Communities (118 total, 45 thin omitted)

### Community 0 - "package.json"
Cohesion: 0.11
Nodes (17): author, bin, feishin-sync, description, engines, node, keywords, license (+9 more)

### Community 1 - "sync-engine.ts"
Cohesion: 0.26
Nodes (6): SyncEngine, CacheManager, MediaTrack, PlaylistSyncTarget, SyncConfig, SyncResult

### Community 2 - "compilerOptions"
Cohesion: 0.14
Nodes (13): compilerOptions, declaration, esModuleInterop, forceConsistentCasingInFileNames, module, moduleResolution, outDir, rootDir (+5 more)

### Community 3 - "Feishin Media Sync Pro 🎵🚀"
Cohesion: 0.33
Nodes (5): 🛠️ Arquitetura do Repositório, 👤 Autor, 🚀 Compilação do APK Android, Feishin Media Sync Pro 🎵🚀, ⚡ Recursos Principais

### Community 4 - "webgl-backgrounds.js"
Cohesion: 0.06
Nodes (62): gt, buildStrandsPalette(), easePower2InOut(), getOptimizedDPR(), hexToRgb(), hexToRgbArr(), initAcidSquares(), initAntigravity() (+54 more)

### Community 5 - "_"
Cohesion: 0.03
Nodes (49): _, ae(), at, bs(), bt(), ce(), ct(), dt (+41 more)

### Community 6 - "three.min.js"
Cohesion: 0.03
Nodes (35): Vr(), bi(), bs, $c(), er(), Ga, Hi(), hr() (+27 more)

### Community 7 - "q"
Cohesion: 0.20
Nodes (3): As(), gr, q

### Community 8 - "Wn"
Cohesion: 0.13
Nodes (21): _allocateTargets(), _applyPMREM(), _blur(), _cleanup(), compileCubemapShader(), compileEquirectangularShader(), _compileMaterial(), fromCubemap() (+13 more)

### Community 9 - "parseObject"
Cohesion: 0.09
Nodes (10): Ah, fa, fh, hh, $l, Mh, parseObject(), ta (+2 more)

### Community 10 - "tn"
Cohesion: 0.05
Nodes (10): bo, dt(), $e(), is(), Ke(), ms(), Qe(), ss() (+2 more)

### Community 11 - "ol"
Cohesion: 0.13
Nodes (5): ol, setTexturePath(), uc, ul, vc

### Community 12 - "e"
Cohesion: 0.30
Nodes (5): cl, il(), load(), or(), e()

### Community 15 - "en"
Cohesion: 0.08
Nodes (8): ca, Da, v(), en, t(), mn, i(), yn()

### Community 17 - "U"
Cohesion: 0.05
Nodes (15): ai(), di(), fi(), gi(), hi(), ii(), li(), mi() (+7 more)

### Community 18 - "gt"
Cohesion: 0.14
Nodes (28): ds(), F(), G(), k(), V(), s(), F(), G() (+20 more)

### Community 20 - "se"
Cohesion: 0.05
Nodes (10): _e(), as(), hs, _s(), n(), _sceneToCubeUV(), se, updateMatrixWorld() (+2 more)

### Community 21 - "ar"
Cohesion: 0.16
Nodes (3): ar, dr(), pr()

### Community 22 - "W"
Cohesion: 0.06
Nodes (14): bi(), Fs(), _i(), on, pn, pt, rn, Si() (+6 more)

### Community 23 - "Lc"
Cohesion: 0.08
Nodes (4): bc, getInput(), getOutput(), Lc

### Community 25 - "app.js"
Cohesion: 0.18
Nodes (22): ALL_SHADERS, applyShader(), bootApp(), closeShaderModal(), currentShader, fetchVpsStats(), formatBytes(), formatRelativeTime() (+14 more)

### Community 26 - "FloatingWidgetService"
Cohesion: 0.11
Nodes (9): android.annotation.SuppressLint, android.app.Service, FloatingWidgetService, Intent, Override, WakeLock, android.view.View, android.view.WindowManager (+1 more)

### Community 27 - "tn"
Cohesion: 0.14
Nodes (7): fr(), A(), E(), m(), p(), ns(), tn

### Community 28 - "MainActivity"
Cohesion: 0.11
Nodes (12): Intent, Override, MainActivity, android.content.BroadcastReceiver, android.os.Bundle, android.webkit.WebView, android.widget.ProgressBar, androidx.activity.result.ActivityResultLauncher (+4 more)

### Community 29 - ".z"
Cohesion: 0.08
Nodes (4): Gs(), Hs(), qs(), Xs()

### Community 32 - "AudioPlaybackService"
Cohesion: 0.15
Nodes (10): android.app.PendingIntent, AudioPlaybackService, Intent, Override, WakeLock, android.graphics.Bitmap, android.media.AudioFocusRequest, android.media.AudioManager (+2 more)

### Community 33 - "it"
Cohesion: 0.18
Nodes (3): Ds(), it, St

### Community 34 - "V"
Cohesion: 0.09
Nodes (3): De(), qi(), V

### Community 35 - "ct"
Cohesion: 0.16
Nodes (3): ct(), es(), ns()

### Community 36 - ".constructor"
Cohesion: 0.09
Nodes (11): Re(), gs, mi(), m(), y(), oi(), r(), v() (+3 more)

### Community 37 - "update"
Cohesion: 0.07
Nodes (25): ci, clone(), dispose(), fi(), A(), v(), ni, pi() (+17 more)

### Community 38 - ".toJSON"
Cohesion: 0.13
Nodes (3): ac, r(), ts()

### Community 41 - "be"
Cohesion: 0.13
Nodes (9): be(), Ie(), Ne(), nn(), or, l(), h(), l() (+1 more)

### Community 42 - "sn"
Cohesion: 0.05
Nodes (11): an, cn, dn, fn, hn, ln, on, pn (+3 more)

### Community 44 - "WebAppInterface"
Cohesion: 0.12
Nodes (6): DirectMemoryGovernor, WebAppInterface, android.os.SharedMemory, android.webkit.JavascriptInterface, java.nio.ByteBuffer, org.json.JSONArray

### Community 46 - "MediaSyncManager"
Cohesion: 0.06
Nodes (17): JSONObject, LocalMediaTrack, JSONObject, FolderStats, JSONObject, MediaSyncManager, SyncListener, UploadProgressCallback (+9 more)

### Community 47 - "no"
Cohesion: 0.11
Nodes (16): ao(), co(), eo(), ho(), io(), ja(), ka(), lo() (+8 more)

### Community 50 - "copy"
Cohesion: 0.12
Nodes (3): copy(), S(), setFromCamera()

### Community 51 - "hl"
Cohesion: 0.22
Nodes (6): hl, ii, parseImages(), parseTextures(), sl, o()

### Community 55 - "i"
Cohesion: 0.18
Nodes (7): ze, br(), i(), je, jn(), n(), xi()

### Community 59 - "MediaSyncService"
Cohesion: 0.13
Nodes (12): android.app.Notification, android.app.NotificationManager, CustomFileObserver, Intent, Override, MediaSyncService, android.content.Intent, android.database.ContentObserver (+4 more)

### Community 61 - "ke"
Cohesion: 0.09
Nodes (9): er, v(), ji(), ke, j(), tr, R(), ue() (+1 more)

### Community 62 - ".update"
Cohesion: 0.16
Nodes (4): cs, is, rr, Ys()

### Community 66 - "Ce"
Cohesion: 0.06
Nodes (6): Ee(), Se(), Te(), Ce, qn, Wl

### Community 67 - "parse"
Cohesion: 0.22
Nodes (7): bindSkeletons(), oc, parse(), parseAnimations(), parseGeometries(), parseSkeletons(), pc

### Community 68 - "ws"
Cohesion: 0.12
Nodes (16): ei, s(), gn(), C(), i(), a(), o(), f() (+8 more)

### Community 72 - "mr"
Cohesion: 0.12
Nodes (5): kt(), mr, Ot, Ut(), yr

### Community 73 - "Kn"
Cohesion: 0.05
Nodes (10): n(), fs, Jl, kl, Kn, parseShapes(), Ql, vl() (+2 more)

### Community 75 - "At"
Cohesion: 0.06
Nodes (4): pe, xe(), At, fe

### Community 76 - "K"
Cohesion: 0.13
Nodes (5): br, K, kr, nr, zr

### Community 78 - "Na"
Cohesion: 0.20
Nodes (5): Ba, Ko, Na, _o, to()

### Community 80 - "wo"
Cohesion: 0.18
Nodes (3): jo, wo, xo

### Community 81 - "bt"
Cohesion: 0.13
Nodes (5): Aa, bt, li(), Ra, Mt()

### Community 83 - ".copy"
Cohesion: 0.07
Nodes (14): cr, he(), ki(), Lt, sr(), C(), et(), L() (+6 more)

### Community 87 - "manifest.json"
Cohesion: 0.20
Nodes (9): background_color, description, display, icons, name, orientation, short_name, start_url (+1 more)

### Community 88 - "fo"
Cohesion: 0.24
Nodes (4): fo(), mo(), l(), po

### Community 95 - "build_android_apk.sh"
Cohesion: 0.33
Nodes (5): ANDROID_HOME, BUILD_TOOLS, JAVA_HOME, PATH, build_android_apk.sh script

### Community 96 - ".applyMatrix4"
Cohesion: 0.20
Nodes (3): ri(), ai, Ea()

### Community 97 - "Xe"
Cohesion: 0.09
Nodes (8): _a, cs, Do, go, Rs, Vs, Xe(), zo

### Community 100 - "constructor"
Cohesion: 0.21
Nodes (23): constructor(), d(), u(), y(), gi(), l(), o(), c() (+15 more)

### Community 103 - "build_feishin_sync_assets.py"
Cohesion: 0.70
Nodes (4): build_masters(), create_arrow(), export_all(), render_svg_to_png()

### Community 108 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

## Knowledge Gaps
- **79 isolated node(s):** `NativeAndroid`, `ALL_SHADERS`, `currentShader`, `fe`, `Ei` (+74 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 709 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **45 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `_` connect `_` to `webgl-backgrounds.js`, `three.min.js`, `q`, `U`, `se`, `ar`, `W`, `tn`, `.z`, `it`, `V`, `.constructor`, `be`, `.x`, `ks`, `i`, `ke`, `.update`, `le`, `Ce`, `en`, `mr`, `At`, `K`, `.copy`, `.addAttribute`, `.applyMatrix4`, `.getRenderList`?**
  _High betweenness centrality (0.277) - this node is a cross-community bridge._
- **Why does `ws()` connect `ws` to `three.min.js`, `tn`, `vt`, `Lt`, `en`, `St`, `gt`, `se`, `.constructor`, `update`, `copy`, `ke`, `parse`, `yo`, `bt`, `.copy`, `.applyMatrix4`, `Xe`, `.distanceTo`, `constructor`, `ia`?**
  _High betweenness centrality (0.128) - this node is a cross-community bridge._
- **Why does `R()` connect `ke` to `ws`, `_`?**
  _High betweenness centrality (0.086) - this node is a cross-community bridge._
- **Are the 21 inferred relationships involving `ws()` (e.g. with `L()` and `R()`) actually correct?**
  _`ws()` has 21 INFERRED edges - model-reasoned connections that need verification._
- **What connects `NativeAndroid`, `ALL_SHADERS`, `currentShader` to the rest of the system?**
  _79 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `package.json` be split into smaller, more focused modules?**
  _Cohesion score 0.1111111111111111 - nodes in this community are weakly interconnected._
- **Should `compilerOptions` be split into smaller, more focused modules?**
  _Cohesion score 0.14285714285714285 - nodes in this community are weakly interconnected._