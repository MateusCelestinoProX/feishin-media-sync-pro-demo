# Graph Report - feishin-media-sync-pro  (2026-11-08)

## Corpus Check
- 27 files · ~129,916 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 2560 nodes · 5829 edges · 127 communities (60 shown, 59 thin omitted)
- Extraction: 93% EXTRACTED · 7% INFERRED · 0% AMBIGUOUS · INFERRED: 387 edges (avg confidence: 0.84)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `29eb0e0c`
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
- uc
- e
- vt
- Lt
- en
- St
- U
- gt
- .constructor
- copy
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
- Xe
- yt
- eh
- .multiplyScalar
- se
- .x
- .isEmpty
- ks
- constructor
- ml
- pt
- bl
- MediaSyncService
- jt
- .copy
- .update
- re
- ws
- le
- Ce
- fe
- ei
- yo
- qo
- en
- mr
- kl
- nc
- At
- K
- .equals
- Na
- la
- wo
- bt
- qc
- sr
- .dot
- ia
- .fromArray
- manifest.json
- fo
- Ll
- .fromJSON
- fl
- Rl
- Al
- el
- build_android_apk.sh
- .applyMatrix4
- bind
- .distanceTo
- us
- .rotateOnAxis
- Kn
- build_feishin_sync_assets.py
- .updateProjectionMatrix
- ic
- Et
- rc
- gradlew
- .setHSL
- .getRenderList
- Zc
- DirectMemoryGovernor
- Wl
- ms
- ss
- ts
- vl
- .copyLinearToSRGB
- .convertSRGBToLinear
- bo

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
- `tt()` --indirect_call--> `Y()`  [INFERRED]
  android/app/src/main/assets/js/ogl.js → android/app/src/main/assets/js/vendor/three.min.js
- `gt()` --indirect_call--> `tt()`  [INFERRED]
  android/app/src/main/assets/js/vendor/three.min.js → android/app/src/main/assets/js/ogl.js

## Import Cycles
- None detected.

## Communities (127 total, 59 thin omitted)

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
Nodes (51): _, ae(), at, bs(), bt(), ce(), ct(), dt (+43 more)

### Community 6 - "three.min.js"
Cohesion: 0.03
Nodes (41): Vr(), bi(), bs, $c(), compileCubemapShader(), compileEquirectangularShader(), _compileMaterial(), er() (+33 more)

### Community 7 - "q"
Cohesion: 0.20
Nodes (3): As(), gr, q

### Community 8 - "Wn"
Cohesion: 0.13
Nodes (14): _allocateTargets(), _applyPMREM(), _blur(), _cleanup(), fromCubemap(), fromEquirectangular(), fromScene(), _fromTexture() (+6 more)

### Community 9 - "parseObject"
Cohesion: 0.13
Nodes (6): fa, $l, parseObject(), Rs, ta, Vs

### Community 12 - "e"
Cohesion: 0.09
Nodes (20): cl, hl, ii, il(), load(), oc, ol, or() (+12 more)

### Community 15 - "en"
Cohesion: 0.08
Nodes (9): ca, Da, v(), en, t(), mn, i(), p() (+1 more)

### Community 17 - "U"
Cohesion: 0.05
Nodes (15): ai(), di(), fi(), gi(), hi(), ii(), li(), mi() (+7 more)

### Community 18 - "gt"
Cohesion: 0.13
Nodes (28): j(), ds(), F(), G(), k(), V(), s(), F() (+20 more)

### Community 20 - "copy"
Cohesion: 0.08
Nodes (11): _e(), ai, copy(), hs, qn, _s(), n(), _sceneToCubeUV() (+3 more)

### Community 21 - "ar"
Cohesion: 0.16
Nodes (3): ar, dr(), pr()

### Community 22 - "W"
Cohesion: 0.07
Nodes (12): bi(), Fs(), _i(), on, pn, pt, Si(), un (+4 more)

### Community 23 - "Lc"
Cohesion: 0.08
Nodes (4): bc, getInput(), getOutput(), Lc

### Community 25 - "app.js"
Cohesion: 0.18
Nodes (22): ALL_SHADERS, applyShader(), bootApp(), closeShaderModal(), currentShader, fetchVpsStats(), formatBytes(), formatRelativeTime() (+14 more)

### Community 26 - "FloatingWidgetService"
Cohesion: 0.13
Nodes (7): FloatingWidgetService, Intent, Override, WakeLock, android.view.View, android.view.WindowManager, LayoutParams

### Community 27 - "tn"
Cohesion: 0.10
Nodes (11): cs, fr(), A(), E(), m(), v(), p(), ns() (+3 more)

### Community 28 - "MainActivity"
Cohesion: 0.10
Nodes (13): android.annotation.SuppressLint, Intent, Override, MainActivity, android.content.BroadcastReceiver, android.os.Bundle, android.webkit.WebView, android.widget.ProgressBar (+5 more)

### Community 29 - ".z"
Cohesion: 0.08
Nodes (4): Gs(), Hs(), qs(), Xs()

### Community 32 - "AudioPlaybackService"
Cohesion: 0.14
Nodes (11): android.app.PendingIntent, android.app.Service, AudioPlaybackService, Intent, Override, WakeLock, android.graphics.Bitmap, android.media.AudioFocusRequest (+3 more)

### Community 33 - "it"
Cohesion: 0.12
Nodes (5): Ds(), it, or, St, ui()

### Community 34 - "V"
Cohesion: 0.09
Nodes (4): De(), ji(), qi(), V

### Community 35 - "ct"
Cohesion: 0.16
Nodes (3): ct(), es(), ns()

### Community 36 - ".constructor"
Cohesion: 0.12
Nodes (6): z(), ge, gs, oi(), v(), ys

### Community 37 - "update"
Cohesion: 0.13
Nodes (12): ci, clone(), fi(), A(), pi(), p(), V(), Si() (+4 more)

### Community 38 - ".toJSON"
Cohesion: 0.14
Nodes (3): ac, r(), tc

### Community 41 - "be"
Cohesion: 0.11
Nodes (10): be(), Ie(), Ne(), nn(), rn, l(), Se(), h() (+2 more)

### Community 42 - "sn"
Cohesion: 0.05
Nodes (11): an, cn, dn, fn, hn, ln, on, pn (+3 more)

### Community 44 - "WebAppInterface"
Cohesion: 0.14
Nodes (3): WebAppInterface, android.webkit.JavascriptInterface, org.json.JSONArray

### Community 46 - "MediaSyncManager"
Cohesion: 0.06
Nodes (16): JSONObject, LocalMediaTrack, JSONObject, FolderStats, JSONObject, MediaSyncManager, SyncListener, UploadProgressCallback (+8 more)

### Community 47 - "Xe"
Cohesion: 0.06
Nodes (26): _a, Ah, ao(), co(), cs, Do, eo(), fh (+18 more)

### Community 50 - ".multiplyScalar"
Cohesion: 0.13
Nodes (3): as(), S(), setFromCamera()

### Community 55 - "constructor"
Cohesion: 0.13
Nodes (22): ze, br(), constructor(), i(), l(), o(), i(), a() (+14 more)

### Community 59 - "MediaSyncService"
Cohesion: 0.12
Nodes (13): android.app.Notification, android.app.NotificationManager, CustomFileObserver, Intent, Override, MediaSyncService, android.content.Intent, android.database.ContentObserver (+5 more)

### Community 61 - ".copy"
Cohesion: 0.13
Nodes (6): he(), Lt, Ot, Ti(), ur(), ye()

### Community 62 - ".update"
Cohesion: 0.19
Nodes (3): is, rr, Ys()

### Community 64 - "ws"
Cohesion: 0.14
Nodes (13): C(), setValue(), uh(), M(), ws(), At(), Ct(), Dt() (+5 more)

### Community 66 - "Ce"
Cohesion: 0.11
Nodes (3): Re(), Ce, sh

### Community 68 - "ei"
Cohesion: 0.09
Nodes (12): dispose(), ei, s(), gn(), o(), ni, U(), rt (+4 more)

### Community 72 - "mr"
Cohesion: 0.14
Nodes (4): kt(), mr, Ut(), yr

### Community 73 - "kl"
Cohesion: 0.15
Nodes (3): kl, Ql, Yl

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

### Community 83 - "sr"
Cohesion: 0.07
Nodes (12): cr, er, ke, ki(), sr(), C(), et(), L() (+4 more)

### Community 87 - "manifest.json"
Cohesion: 0.20
Nodes (9): background_color, description, display, icons, name, orientation, short_name, start_url (+1 more)

### Community 88 - "fo"
Cohesion: 0.24
Nodes (3): fo(), l(), po

### Community 90 - ".fromJSON"
Cohesion: 0.10
Nodes (6): bindSkeletons(), n(), parseShapes(), parseSkeletons(), pl, zl

### Community 95 - "build_android_apk.sh"
Cohesion: 0.33
Nodes (5): ANDROID_HOME, BUILD_TOOLS, JAVA_HOME, PATH, build_android_apk.sh script

### Community 100 - "us"
Cohesion: 0.15
Nodes (19): d(), u(), y(), gi(), v(), mc(), mi(), f() (+11 more)

### Community 101 - ".rotateOnAxis"
Cohesion: 0.18
Nodes (3): Ee(), Te(), xe()

### Community 103 - "build_feishin_sync_assets.py"
Cohesion: 0.70
Nodes (4): build_masters(), create_arrow(), export_all(), render_svg_to_png()

### Community 108 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 109 - ".setHSL"
Cohesion: 0.33
Nodes (3): dt(), Qe(), ut()

### Community 113 - "DirectMemoryGovernor"
Cohesion: 0.47
Nodes (3): DirectMemoryGovernor, android.os.SharedMemory, java.nio.ByteBuffer

## Knowledge Gaps
- **79 isolated node(s):** `NativeAndroid`, `ALL_SHADERS`, `currentShader`, `fe`, `Ei` (+74 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 714 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **59 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `_` connect `_` to `webgl-backgrounds.js`, `three.min.js`, `q`, `U`, `copy`, `ar`, `W`, `tn`, `.z`, `it`, `V`, `be`, `.x`, `ks`, `constructor`, `.copy`, `.update`, `le`, `Ce`, `fe`, `en`, `mr`, `K`, `sr`, `.applyMatrix4`, `.rotateOnAxis`, `.getRenderList`?**
  _High betweenness centrality (0.277) - this node is a cross-community bridge._
- **Why does `ws()` connect `ws` to `three.min.js`, `tn`, `vt`, `Lt`, `en`, `St`, `gt`, `copy`, `tn`, `.constructor`, `update`, `Xe`, `.multiplyScalar`, `se`, `constructor`, `ei`, `yo`, `bt`, `sr`, `ia`, `.fromJSON`, `.applyMatrix4`, `.distanceTo`, `us`?**
  _High betweenness centrality (0.128) - this node is a cross-community bridge._
- **Why does `R()` connect `tn` to `ws`, `sr`, `_`?**
  _High betweenness centrality (0.086) - this node is a cross-community bridge._
- **Are the 21 inferred relationships involving `ws()` (e.g. with `L()` and `R()`) actually correct?**
  _`ws()` has 21 INFERRED edges - model-reasoned connections that need verification._
- **What connects `NativeAndroid`, `ALL_SHADERS`, `currentShader` to the rest of the system?**
  _79 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `package.json` be split into smaller, more focused modules?**
  _Cohesion score 0.1111111111111111 - nodes in this community are weakly interconnected._
- **Should `compilerOptions` be split into smaller, more focused modules?**
  _Cohesion score 0.14285714285714285 - nodes in this community are weakly interconnected._