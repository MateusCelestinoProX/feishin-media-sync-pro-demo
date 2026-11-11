# Graph Report - feishin-media-sync-pro  (2026-11-03)

## Corpus Check
- 27 files · ~129,365 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 2548 nodes · 5815 edges · 124 communities (59 shown, 59 thin omitted)
- Extraction: 93% EXTRACTED · 7% INFERRED · 0% AMBIGUOUS · INFERRED: 385 edges (avg confidence: 0.84)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `58325eb7`
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
- pi
- Wn
- .length
- tn
- sr
- e
- vt
- Lt
- .setAttribute
- St
- U
- ws
- copy
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
- ls
- jc
- AudioPlaybackService
- it
- V
- ct
- .dispatchEvent
- updateMatrixWorld
- .toJSON
- Ne
- nl
- be
- sn
- yc
- WebAppInterface
- .fromJSON
- MediaSyncManager
- parseObject
- yt
- eh
- .multiplyScalar
- ol
- yi
- ks
- n
- ml
- pt
- bl
- MediaSyncService
- jt
- Mh
- .update
- re
- ec
- le
- Ce
- bind
- us
- yo
- qo
- en
- Kn
- nc
- At
- K
- .rotateOnAxis
- Na
- .fromArray
- wo
- bt
- qc
- .copy
- .constructor
- mc
- .updateProjectionMatrix
- manifest.json
- .translateOnAxis
- .addAttribute
- constructor
- fl
- ic
- .updateMatrix
- rh
- build_android_apk.sh
- .applyMatrix4
- ta
- pl
- as
- i
- q
- .x
- build_feishin_sync_assets.py
- gr
- rc
- gradlew
- Vs
- DirectMemoryGovernor
- .getRenderList
- Zc
- Re
- hs
- fo
- ms
- ss
- tc
- ts
- vl
- cs

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

## Communities (124 total, 59 thin omitted)

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
Nodes (61): buildStrandsPalette(), easePower2InOut(), getOptimizedDPR(), hexToRgb(), hexToRgbArr(), initAcidSquares(), initAntigravity(), initAurora() (+53 more)

### Community 5 - "_"
Cohesion: 0.03
Nodes (50): _, ae(), at, bs(), bt(), ce(), ct(), dt (+42 more)

### Community 6 - "three.min.js"
Cohesion: 0.04
Nodes (33): Vr(), bi(), bs, $c(), er(), fi(), Ga, Hi() (+25 more)

### Community 7 - "pi"
Cohesion: 0.25
Nodes (6): ci, i(), pi(), p(), Xn(), yn()

### Community 8 - "Wn"
Cohesion: 0.11
Nodes (22): _allocateTargets(), _applyPMREM(), _blur(), _cleanup(), compileCubemapShader(), compileEquirectangularShader(), _compileMaterial(), fromCubemap() (+14 more)

### Community 10 - "tn"
Cohesion: 0.05
Nodes (10): _a, bo, Do, dt(), $e(), is(), Qe(), tn (+2 more)

### Community 11 - "sr"
Cohesion: 0.12
Nodes (10): Ie(), Ne(), sr(), C(), et(), L(), N(), tt() (+2 more)

### Community 12 - "e"
Cohesion: 0.18
Nodes (11): bindSkeletons(), cl, il(), load(), or(), parse(), parseAnimations(), parseSkeletons() (+3 more)

### Community 15 - ".setAttribute"
Cohesion: 0.15
Nodes (5): ca, gn(), o(), mn, p()

### Community 17 - "U"
Cohesion: 0.05
Nodes (17): ai(), di(), _e(), fi(), gi(), hi(), ii(), li() (+9 more)

### Community 18 - "ws"
Cohesion: 0.05
Nodes (54): j(), dispose(), ds(), F(), G(), k(), V(), z() (+46 more)

### Community 19 - "copy"
Cohesion: 0.09
Nodes (4): Aa, clone(), copy(), sc

### Community 21 - "ar"
Cohesion: 0.08
Nodes (5): ar, kt(), mr, Ut(), yr

### Community 22 - "W"
Cohesion: 0.06
Nodes (14): bi(), Fs(), _i(), on, pn, pt, rr, Si() (+6 more)

### Community 23 - "Lc"
Cohesion: 0.08
Nodes (4): bc, getInput(), getOutput(), Lc

### Community 25 - "app.js"
Cohesion: 0.23
Nodes (17): ALL_SHADERS, applyShader(), bootApp(), fetchVpsStats(), formatBytes(), formatRelativeTime(), getShaderFriendlyName(), initBackgroundEngine() (+9 more)

### Community 26 - "FloatingWidgetService"
Cohesion: 0.13
Nodes (8): android.annotation.SuppressLint, FloatingWidgetService, Intent, Override, WakeLock, android.view.View, android.view.WindowManager, LayoutParams

### Community 27 - "tn"
Cohesion: 0.11
Nodes (9): fr(), A(), E(), m(), v(), ns(), tn, tr (+1 more)

### Community 28 - "MainActivity"
Cohesion: 0.11
Nodes (12): Intent, Override, MainActivity, android.content.BroadcastReceiver, android.os.Bundle, android.webkit.WebView, android.widget.ProgressBar, androidx.activity.result.ActivityResultLauncher (+4 more)

### Community 29 - ".z"
Cohesion: 0.09
Nodes (3): Gs(), Hs(), Xs()

### Community 32 - "AudioPlaybackService"
Cohesion: 0.14
Nodes (10): android.app.PendingIntent, AudioPlaybackService, Intent, Override, WakeLock, android.graphics.Bitmap, android.media.AudioFocusRequest, android.media.AudioManager (+2 more)

### Community 33 - "it"
Cohesion: 0.13
Nodes (4): Ds(), it, or, St

### Community 34 - "V"
Cohesion: 0.08
Nodes (4): De(), ji(), qi(), V

### Community 35 - "ct"
Cohesion: 0.14
Nodes (4): ct(), es(), ns(), Mt()

### Community 36 - ".dispatchEvent"
Cohesion: 0.20
Nodes (3): gs, v(), ys

### Community 37 - "updateMatrixWorld"
Cohesion: 0.10
Nodes (7): Ys(), hh, _s(), n(), Si(), ti, updateMatrixWorld()

### Community 41 - "be"
Cohesion: 0.15
Nodes (8): be(), gt, nn(), rn, l(), Se(), h(), l()

### Community 42 - "sn"
Cohesion: 0.05
Nodes (11): an, cn, dn, fn, hn, ln, on, pn (+3 more)

### Community 45 - ".fromJSON"
Cohesion: 0.12
Nodes (4): n(), el, parseShapes(), zl

### Community 46 - "MediaSyncManager"
Cohesion: 0.07
Nodes (13): JSONObject, LocalMediaTrack, JSONObject, MediaSyncManager, SyncListener, UploadProgressCallback, SyncStorage, android.content.Context (+5 more)

### Community 47 - "parseObject"
Cohesion: 0.08
Nodes (24): Ah, ao(), co(), eo(), fa, fh, go, ho() (+16 more)

### Community 48 - "yt"
Cohesion: 0.09
Nodes (3): vi(), h(), yt

### Community 50 - ".multiplyScalar"
Cohesion: 0.15
Nodes (3): S(), M(), b()

### Community 51 - "ol"
Cohesion: 0.07
Nodes (11): dl, hl, oc, ol, parseGeometries(), parseImages(), setTexturePath(), sl (+3 more)

### Community 52 - "yi"
Cohesion: 0.25
Nodes (5): Et, ni, Tt(), yi(), Zh()

### Community 55 - "n"
Cohesion: 0.17
Nodes (4): ze, je, jn(), n()

### Community 56 - "ml"
Cohesion: 0.10
Nodes (3): Al, ml, Rl

### Community 59 - "MediaSyncService"
Cohesion: 0.11
Nodes (15): android.app.Notification, android.app.NotificationManager, android.app.Service, JSONObject, CustomFileObserver, Intent, Override, MediaSyncService (+7 more)

### Community 68 - "us"
Cohesion: 0.23
Nodes (3): kl, Ql, us()

### Community 73 - "Kn"
Cohesion: 0.16
Nodes (3): fs, Kn, Yl

### Community 75 - "At"
Cohesion: 0.06
Nodes (7): me(), pe, At, fe, ht(), setFromCartesianCoords(), setFromVector3()

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
Cohesion: 0.09
Nodes (8): bt, ei, ii, li(), parseTextures(), Ra, rt, Tt

### Community 83 - ".copy"
Cohesion: 0.07
Nodes (11): cr, dr(), er, he(), ke, ki(), Lt, Ot (+3 more)

### Community 84 - ".constructor"
Cohesion: 0.15
Nodes (13): d(), u(), v(), y(), v(), mi(), g(), y() (+5 more)

### Community 87 - "manifest.json"
Cohesion: 0.20
Nodes (9): background_color, description, display, icons, name, orientation, short_name, start_url (+1 more)

### Community 90 - "constructor"
Cohesion: 0.12
Nodes (6): constructor(), Da, en, update(), wh(), wi()

### Community 95 - "build_android_apk.sh"
Cohesion: 0.33
Nodes (5): ANDROID_HOME, BUILD_TOOLS, JAVA_HOME, PATH, build_android_apk.sh script

### Community 99 - "as"
Cohesion: 0.09
Nodes (7): qs(), we(), as(), qn, setFromCamera(), b(), xs

### Community 100 - "i"
Cohesion: 0.20
Nodes (17): br(), i(), t(), gi(), l(), o(), i(), c() (+9 more)

### Community 103 - "build_feishin_sync_assets.py"
Cohesion: 0.70
Nodes (4): build_masters(), create_arrow(), export_all(), render_svg_to_png()

### Community 108 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 110 - "DirectMemoryGovernor"
Cohesion: 0.60
Nodes (3): DirectMemoryGovernor, android.os.SharedMemory, java.nio.ByteBuffer

## Knowledge Gaps
- **78 isolated node(s):** `NativeAndroid`, `ALL_SHADERS`, `fe`, `Ei`, `J` (+73 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 708 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **59 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `_` connect `_` to `webgl-backgrounds.js`, `three.min.js`, `sr`, `U`, `ar`, `W`, `tn`, `.z`, `it`, `V`, `updateMatrixWorld`, `be`, `ks`, `n`, `.update`, `le`, `en`, `At`, `K`, `.rotateOnAxis`, `.copy`, `.translateOnAxis`, `.addAttribute`, `.applyMatrix4`, `as`, `q`, `.x`, `gr`, `.getRenderList`, `Re`?**
  _High betweenness centrality (0.268) - this node is a cross-community bridge._
- **Why does `ws()` connect `ws` to `three.min.js`, `.length`, `tn`, `sr`, `e`, `vt`, `Lt`, `.setAttribute`, `St`, `copy`, `se`, `tn`, `ct`, `.dispatchEvent`, `updateMatrixWorld`, `.multiplyScalar`, `yi`, `Ce`, `yo`, `bt`, `.constructor`, `constructor`, `.applyMatrix4`, `as`, `i`?**
  _High betweenness centrality (0.115) - this node is a cross-community bridge._
- **Why does `R()` connect `tn` to `ws`, `.copy`, `_`?**
  _High betweenness centrality (0.083) - this node is a cross-community bridge._
- **Are the 21 inferred relationships involving `ws()` (e.g. with `L()` and `R()`) actually correct?**
  _`ws()` has 21 INFERRED edges - model-reasoned connections that need verification._
- **What connects `NativeAndroid`, `ALL_SHADERS`, `fe` to the rest of the system?**
  _78 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `package.json` be split into smaller, more focused modules?**
  _Cohesion score 0.1111111111111111 - nodes in this community are weakly interconnected._
- **Should `compilerOptions` be split into smaller, more focused modules?**
  _Cohesion score 0.14285714285714285 - nodes in this community are weakly interconnected._