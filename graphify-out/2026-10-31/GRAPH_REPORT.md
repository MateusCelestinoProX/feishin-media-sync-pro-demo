# Graph Report - feishin-media-sync-pro  (2026-10-31)

## Corpus Check
- 26 files · ~94,478 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 2531 nodes · 5751 edges · 120 communities (67 shown, 46 thin omitted)
- Extraction: 93% EXTRACTED · 7% INFERRED · 0% AMBIGUOUS · INFERRED: 380 edges (avg confidence: 0.84)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `21390cf9`
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
- eh
- fe
- sr
- tn
- .applyMatrix4
- e
- vt
- Lt
- constructor
- St
- U
- gt
- copy
- se
- ar
- pt
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
- W
- V
- es
- ys
- .lookAt
- .toJSON
- Ne
- nl
- ws
- sn
- yc
- WebAppInterface
- .fromJSON
- MediaSyncManager
- parseObject
- yt
- pt
- ya
- en
- Et
- Xe
- ks
- ec
- ml
- Wn
- bl
- MediaSyncService
- jt
- .multiplyScalar
- .update
- clone
- le
- Ce
- ke
- kl
- yo
- bt
- en
- .dispatchEvent
- Kn
- nc
- At
- K
- zt
- Na
- .fromArray
- ._update
- be
- xe
- .copy
- update
- qc
- ia
- manifest.json
- _s
- Al
- fl
- mr
- ge
- build_android_apk.sh
- ai
- ol
- .translateOnAxis
- parse
- o
- q
- .x
- updateMatrixWorld
- gr
- rh
- Rl
- gradlew
- .getRenderList
- Wl
- rc
- Zc
- ct
- Mh
- ss
- ts

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

## Communities (120 total, 46 thin omitted)

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
Nodes (48): _, ae(), at, bs(), bt(), ce(), ct(), dt (+40 more)

### Community 6 - "three.min.js"
Cohesion: 0.03
Nodes (34): Vr(), bs, $c(), er(), Ga, Hi(), hr(), intersectObject() (+26 more)

### Community 8 - "fe"
Cohesion: 0.11
Nodes (3): me(), pe, fe

### Community 9 - "sr"
Cohesion: 0.10
Nodes (10): cr, ki(), nn(), sr(), C(), et(), L(), N() (+2 more)

### Community 10 - "tn"
Cohesion: 0.06
Nodes (7): bo, dt(), $e(), ms(), Qe(), tn, ut()

### Community 12 - "e"
Cohesion: 0.23
Nodes (6): cl, hl, load(), or(), e(), o()

### Community 14 - "Lt"
Cohesion: 0.04
Nodes (5): ze, ht(), je, jn(), Lt

### Community 15 - "constructor"
Cohesion: 0.11
Nodes (21): br(), ca, constructor(), Da, v(), i(), t(), c() (+13 more)

### Community 17 - "U"
Cohesion: 0.05
Nodes (17): ai(), di(), _e(), fi(), gi(), hi(), ii(), li() (+9 more)

### Community 18 - "gt"
Cohesion: 0.09
Nodes (37): j(), bi(), ds(), F(), G(), k(), V(), z() (+29 more)

### Community 20 - "se"
Cohesion: 0.07
Nodes (4): as(), is(), os(), se

### Community 21 - "ar"
Cohesion: 0.13
Nodes (4): ar, dr(), pr(), _r

### Community 22 - "pt"
Cohesion: 0.09
Nodes (9): bi(), _i(), pn, pt, Si(), Ti(), vi(), xn (+1 more)

### Community 23 - "Lc"
Cohesion: 0.08
Nodes (4): bc, getInput(), getOutput(), Lc

### Community 25 - "app.js"
Cohesion: 0.16
Nodes (19): bootApp(), CINEMATIC_VIDEOS, fetchVpsStats(), formatBytes(), formatRelativeTime(), GALLERIES, GALLERIES_POOL, getRandomGalleryFromPool() (+11 more)

### Community 26 - "FloatingWidgetService"
Cohesion: 0.12
Nodes (10): FloatingWidgetService, Intent, Override, WakeLock, android.content.Intent, android.os.IBinder, android.view.View, android.view.WindowManager (+2 more)

### Community 27 - "tn"
Cohesion: 0.15
Nodes (6): fr(), A(), E(), m(), ns(), tn

### Community 28 - "MainActivity"
Cohesion: 0.11
Nodes (12): android.annotation.SuppressLint, Override, MainActivity, android.content.BroadcastReceiver, android.os.Bundle, android.webkit.WebView, android.widget.ProgressBar, androidx.activity.result.ActivityResultLauncher (+4 more)

### Community 29 - ".z"
Cohesion: 0.08
Nodes (4): Gs(), Hs(), qs(), Xs()

### Community 32 - "AudioPlaybackService"
Cohesion: 0.15
Nodes (10): android.app.PendingIntent, AudioPlaybackService, Intent, Override, WakeLock, android.graphics.Bitmap, android.media.AudioFocusRequest, android.media.AudioManager (+2 more)

### Community 33 - "W"
Cohesion: 0.11
Nodes (6): Ds(), it, on, St, un, W

### Community 34 - "V"
Cohesion: 0.08
Nodes (4): De(), ji(), qi(), V

### Community 36 - "ys"
Cohesion: 0.24
Nodes (3): vs(), gs, ys

### Community 37 - ".lookAt"
Cohesion: 0.15
Nodes (3): Se(), we(), qn

### Community 38 - ".toJSON"
Cohesion: 0.12
Nodes (3): ac, r(), el

### Community 41 - "ws"
Cohesion: 0.12
Nodes (14): C(), f(), m(), oi(), setValue(), uh(), ws(), At() (+6 more)

### Community 42 - "sn"
Cohesion: 0.05
Nodes (11): an, cn, dn, fn, hn, ln, on, pn (+3 more)

### Community 44 - "WebAppInterface"
Cohesion: 0.12
Nodes (5): DirectMemoryGovernor, WebAppInterface, android.os.SharedMemory, android.webkit.JavascriptInterface, java.nio.ByteBuffer

### Community 45 - ".fromJSON"
Cohesion: 0.11
Nodes (4): n(), parseShapes(), pl, zl

### Community 46 - "MediaSyncManager"
Cohesion: 0.07
Nodes (12): JSONObject, LocalMediaTrack, JSONObject, MediaSyncManager, SyncListener, UploadProgressCallback, SyncStorage, android.content.Context (+4 more)

### Community 47 - "parseObject"
Cohesion: 0.10
Nodes (7): fa, $l, parseObject(), Rs, ta, tc, Vs

### Community 48 - "yt"
Cohesion: 0.08
Nodes (3): vi(), h(), yt

### Community 50 - "ya"
Cohesion: 0.09
Nodes (20): Ah, ao(), co(), eo(), fh, hh, ho(), io() (+12 more)

### Community 51 - "en"
Cohesion: 0.11
Nodes (6): ci, en, pi(), p(), Si(), ti

### Community 53 - "Xe"
Cohesion: 0.09
Nodes (8): _a, cs, Do, fo(), go, hs, Xe(), zo

### Community 57 - "Wn"
Cohesion: 0.12
Nodes (20): _applyPMREM(), _blur(), _cleanup(), compileCubemapShader(), compileEquirectangularShader(), _compileMaterial(), fromCubemap(), fromEquirectangular() (+12 more)

### Community 59 - "MediaSyncService"
Cohesion: 0.15
Nodes (10): android.app.Notification, android.app.NotificationManager, android.app.Service, JSONObject, CustomFileObserver, Intent, Override, MediaSyncService (+2 more)

### Community 61 - ".multiplyScalar"
Cohesion: 0.07
Nodes (4): re, setDirection(), setFromCamera(), wh()

### Community 62 - ".update"
Cohesion: 0.12
Nodes (4): cs, Fs(), is, rr

### Community 64 - "clone"
Cohesion: 0.20
Nodes (4): clone(), A(), b(), xs

### Community 66 - "Ce"
Cohesion: 0.14
Nodes (3): Ee(), Ce, sh

### Community 67 - "ke"
Cohesion: 0.11
Nodes (7): er, v(), ke, tr, R(), ue(), ye()

### Community 69 - "yo"
Cohesion: 0.14
Nodes (3): jo, qo, yo

### Community 70 - "bt"
Cohesion: 0.11
Nodes (4): Aa, bt, Ra, Tt

### Community 72 - ".dispatchEvent"
Cohesion: 0.22
Nodes (4): Re(), gn(), o(), v()

### Community 73 - "Kn"
Cohesion: 0.11
Nodes (4): fs, Jl, Kn, vl()

### Community 74 - "nc"
Cohesion: 0.13
Nodes (3): bind(), getValue(), nc

### Community 76 - "K"
Cohesion: 0.13
Nodes (5): br, K, kr, nr, zr

### Community 77 - "zt"
Cohesion: 0.24
Nodes (4): Ie(), Ne(), or, zt

### Community 78 - "Na"
Cohesion: 0.18
Nodes (5): Ba, Ko, Na, _o, to()

### Community 81 - "be"
Cohesion: 0.24
Nodes (6): be(), gt, rn, l(), h(), l()

### Community 83 - ".copy"
Cohesion: 0.17
Nodes (4): he(), Lt, Ot, ur()

### Community 84 - "update"
Cohesion: 0.12
Nodes (23): _allocateTargets(), d(), u(), S(), y(), gi(), v(), mc() (+15 more)

### Community 87 - "manifest.json"
Cohesion: 0.20
Nodes (9): background_color, description, display, icons, name, orientation, short_name, start_url (+1 more)

### Community 88 - "_s"
Cohesion: 0.22
Nodes (3): Ys(), _s(), n()

### Community 92 - "mr"
Cohesion: 0.15
Nodes (4): kt(), mr, Ut(), yr

### Community 95 - "build_android_apk.sh"
Cohesion: 0.33
Nodes (5): ANDROID_HOME, BUILD_TOOLS, JAVA_HOME, PATH, build_android_apk.sh script

### Community 97 - "ol"
Cohesion: 0.10
Nodes (6): dl, ol, setTexturePath(), uc, ul, vc

### Community 99 - "parse"
Cohesion: 0.12
Nodes (13): bindSkeletons(), ic, ii, oc, parse(), parseAnimations(), parseGeometries(), parseImages() (+5 more)

### Community 100 - "o"
Cohesion: 0.11
Nodes (13): dispose(), ei, s(), l(), o(), i(), a(), ni (+5 more)

### Community 108 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 114 - "ct"
Cohesion: 0.20
Nodes (3): ct(), Mt(), Yl

## Knowledge Gaps
- **82 isolated node(s):** `GALLERIES`, `CINEMATIC_VIDEOS`, `webglTypes`, `GALLERIES_POOL`, `NativeAndroid` (+77 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 713 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **46 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `_` connect `_` to `webgl-backgrounds.js`, `three.min.js`, `fe`, `sr`, `Lt`, `U`, `ar`, `pt`, `tn`, `.z`, `W`, `V`, `ys`, `.lookAt`, `ks`, `.update`, `le`, `Ce`, `ke`, `en`, `.dispatchEvent`, `K`, `zt`, `be`, `xe`, `.copy`, `_s`, `mr`, `ai`, `.translateOnAxis`, `q`, `.x`, `gr`, `.getRenderList`?**
  _High betweenness centrality (0.272) - this node is a cross-community bridge._
- **Why does `ws()` connect `ws` to `three.min.js`, `sr`, `tn`, `.applyMatrix4`, `vt`, `Lt`, `constructor`, `St`, `gt`, `copy`, `se`, `xc`, `Xe`, `.multiplyScalar`, `clone`, `ke`, `yo`, `bt`, `.dispatchEvent`, `update`, `ia`, `ai`, `parse`, `o`, `updateMatrixWorld`, `ct`?**
  _High betweenness centrality (0.119) - this node is a cross-community bridge._
- **Why does `R()` connect `ke` to `ws`, `_`?**
  _High betweenness centrality (0.085) - this node is a cross-community bridge._
- **Are the 21 inferred relationships involving `ws()` (e.g. with `L()` and `R()`) actually correct?**
  _`ws()` has 21 INFERRED edges - model-reasoned connections that need verification._
- **What connects `GALLERIES`, `CINEMATIC_VIDEOS`, `webglTypes` to the rest of the system?**
  _82 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `package.json` be split into smaller, more focused modules?**
  _Cohesion score 0.1111111111111111 - nodes in this community are weakly interconnected._
- **Should `compilerOptions` be split into smaller, more focused modules?**
  _Cohesion score 0.14285714285714285 - nodes in this community are weakly interconnected._