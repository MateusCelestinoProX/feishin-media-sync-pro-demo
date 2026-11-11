# Graph Report - feishin-media-sync-pro  (2026-11-01)

## Corpus Check
- 26 files · ~93,497 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 2521 nodes · 5739 edges · 119 communities (73 shown, 42 thin omitted)
- Extraction: 93% EXTRACTED · 7% INFERRED · 0% AMBIGUOUS · INFERRED: 380 edges (avg confidence: 0.84)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `b1116f9c`
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
- ei
- ls
- sr
- tn
- en
- e
- vt
- Lt
- .setAttribute
- St
- U
- ds
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
- jc
- AudioPlaybackService
- W
- V
- ct
- ys
- .updateWorldMatrix
- .toJSON
- Ne
- nl
- ws
- sn
- yc
- WebAppInterface
- .fromJSON
- MediaSyncManager
- ya
- yt
- pt
- no
- .multiplyScalar
- yi
- mc
- ks
- ec
- ml
- Wn
- bl
- MediaSyncService
- jt
- re
- .update
- .updateProjectionMatrix
- parseObject
- le
- Ce
- zt
- us
- yo
- bt
- en
- .dispatchEvent
- Kn
- nc
- At
- K
- pi
- Na
- clone
- ._update
- .lookAt
- .rotateOnAxis
- .copy
- mi
- fo
- ia
- manifest.json
- _s
- .subVectors
- fa
- fl
- mr
- qc
- ge
- build_android_apk.sh
- .applyMatrix4
- .getAttribute
- update
- .parse
- constructor
- q
- .x
- gt
- updateMatrixWorld
- gr
- pl
- gradlew
- Yl
- .updateMatrix
- rc
- Zc
- DirectMemoryGovernor
- el
- Xe
- ol

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
- `tt()` --indirect_call--> `Y()`  [INFERRED]
  android/app/src/main/assets/js/ogl.js → android/app/src/main/assets/js/vendor/three.min.js

## Import Cycles
- None detected.

## Communities (119 total, 42 thin omitted)

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
Cohesion: 0.03
Nodes (34): Vr(), bi(), br(), bs, $c(), er(), Hi(), hr() (+26 more)

### Community 7 - "ei"
Cohesion: 0.12
Nodes (8): ei, s(), i(), a(), o(), G(), rt, Tt

### Community 9 - "sr"
Cohesion: 0.08
Nodes (10): cr, er, ke, ki(), nn(), sr(), C(), et() (+2 more)

### Community 10 - "tn"
Cohesion: 0.05
Nodes (9): bo, dt(), $e(), is(), ms(), Qe(), ss(), tn (+1 more)

### Community 12 - "e"
Cohesion: 0.18
Nodes (11): bindSkeletons(), cl, i(), il(), load(), or(), parse(), parseAnimations() (+3 more)

### Community 15 - ".setAttribute"
Cohesion: 0.15
Nodes (3): v(), ht(), mn

### Community 17 - "U"
Cohesion: 0.05
Nodes (16): ai(), ci(), di(), fi(), gi(), hi(), ii(), li() (+8 more)

### Community 18 - "ds"
Cohesion: 0.16
Nodes (22): ds(), G(), k(), V(), z(), s(), F(), G() (+14 more)

### Community 19 - "copy"
Cohesion: 0.09
Nodes (4): copy(), hs, sc, w()

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
Cohesion: 0.42
Nodes (8): bootApp(), fetchVpsStats(), formatBytes(), formatRelativeTime(), NativeAndroid, refreshData(), renderHistory(), setupEvents()

### Community 26 - "FloatingWidgetService"
Cohesion: 0.12
Nodes (9): android.annotation.SuppressLint, android.app.Service, FloatingWidgetService, Intent, Override, WakeLock, android.view.View, android.view.WindowManager (+1 more)

### Community 27 - "tn"
Cohesion: 0.11
Nodes (9): fr(), A(), E(), m(), v(), ns(), tn, tr (+1 more)

### Community 28 - "MainActivity"
Cohesion: 0.12
Nodes (11): Override, MainActivity, android.content.BroadcastReceiver, android.os.Bundle, android.webkit.WebView, android.widget.ProgressBar, androidx.activity.result.ActivityResultLauncher, androidx.appcompat.app.AppCompatActivity (+3 more)

### Community 29 - ".z"
Cohesion: 0.09
Nodes (3): Gs(), Hs(), Xs()

### Community 31 - "jc"
Cohesion: 0.08
Nodes (3): bind(), getValue(), jc

### Community 32 - "AudioPlaybackService"
Cohesion: 0.12
Nodes (15): android.app.Notification, android.app.NotificationManager, android.app.PendingIntent, AudioPlaybackService, Intent, Override, WakeLock, android.content.Intent (+7 more)

### Community 33 - "W"
Cohesion: 0.11
Nodes (6): Ds(), it, on, St, un, W

### Community 34 - "V"
Cohesion: 0.08
Nodes (4): De(), ji(), qi(), V

### Community 35 - "ct"
Cohesion: 0.15
Nodes (3): ct(), es(), ns()

### Community 38 - ".toJSON"
Cohesion: 0.10
Nodes (4): ac, Al, r(), tc

### Community 41 - "ws"
Cohesion: 0.15
Nodes (15): A(), C(), f(), m(), mt(), setValue(), _t, ws() (+7 more)

### Community 42 - "sn"
Cohesion: 0.05
Nodes (11): an, cn, dn, fn, hn, ln, on, pn (+3 more)

### Community 45 - ".fromJSON"
Cohesion: 0.18
Nodes (3): n(), parseShapes(), zl

### Community 46 - "MediaSyncManager"
Cohesion: 0.07
Nodes (13): JSONObject, LocalMediaTrack, JSONObject, MediaSyncManager, SyncListener, UploadProgressCallback, SyncStorage, android.content.Context (+5 more)

### Community 47 - "ya"
Cohesion: 0.13
Nodes (9): Ah, ca, co(), fh, hh, lo(), uh(), uo() (+1 more)

### Community 48 - "yt"
Cohesion: 0.09
Nodes (3): vi(), h(), yt

### Community 50 - "no"
Cohesion: 0.14
Nodes (12): ao(), eo(), ho(), io(), ja(), no(), oo(), ro() (+4 more)

### Community 51 - ".multiplyScalar"
Cohesion: 0.11
Nodes (5): as(), S(), y(), M(), b()

### Community 52 - "yi"
Cohesion: 0.18
Nodes (7): dispose(), Et, ni, U(), Tt(), yi(), i()

### Community 53 - "mc"
Cohesion: 0.23
Nodes (3): dc, mc(), pc

### Community 57 - "Wn"
Cohesion: 0.11
Nodes (23): _allocateTargets(), _applyPMREM(), _blur(), _cleanup(), compileCubemapShader(), compileEquirectangularShader(), _compileMaterial(), fromCubemap() (+15 more)

### Community 59 - "MediaSyncService"
Cohesion: 0.19
Nodes (6): JSONObject, CustomFileObserver, Intent, Override, MediaSyncService, android.os.FileObserver

### Community 61 - "re"
Cohesion: 0.08
Nodes (3): Ea(), re, rh

### Community 62 - ".update"
Cohesion: 0.12
Nodes (5): cs, Fs(), is, rr, Ys()

### Community 64 - "parseObject"
Cohesion: 0.13
Nodes (5): qs(), parseObject(), b(), Vs, xs

### Community 67 - "zt"
Cohesion: 0.24
Nodes (4): Ie(), Ne(), or, zt

### Community 68 - "us"
Cohesion: 0.23
Nodes (3): kl, Ql, us()

### Community 69 - "yo"
Cohesion: 0.14
Nodes (3): jo, qo, yo

### Community 70 - "bt"
Cohesion: 0.13
Nodes (5): Aa, bt, li(), Ra, Mt()

### Community 72 - ".dispatchEvent"
Cohesion: 0.17
Nodes (3): Re(), ts(), v()

### Community 75 - "At"
Cohesion: 0.06
Nodes (3): pe, At, fe

### Community 76 - "K"
Cohesion: 0.13
Nodes (4): br, K, kr, nr

### Community 77 - "pi"
Cohesion: 0.20
Nodes (8): ci, oc, i(), parseGeometries(), pi(), p(), Xn(), yn()

### Community 78 - "Na"
Cohesion: 0.18
Nodes (5): Ba, Ko, Na, _o, to()

### Community 79 - "clone"
Cohesion: 0.08
Nodes (3): clone(), dl, la

### Community 81 - ".lookAt"
Cohesion: 0.13
Nodes (10): be(), _e(), gt, rn, l(), we(), yi(), zr (+2 more)

### Community 82 - ".rotateOnAxis"
Cohesion: 0.18
Nodes (3): Ee(), Te(), xe()

### Community 83 - ".copy"
Cohesion: 0.15
Nodes (5): he(), Lt, Ot, ur(), ye()

### Community 84 - "mi"
Cohesion: 0.18
Nodes (8): v(), mi(), g(), y(), h(), x(), x(), y()

### Community 85 - "fo"
Cohesion: 0.24
Nodes (4): fo(), mo(), l(), po

### Community 87 - "manifest.json"
Cohesion: 0.20
Nodes (9): background_color, description, display, icons, name, orientation, short_name, start_url (+1 more)

### Community 89 - ".subVectors"
Cohesion: 0.12
Nodes (3): ze, je, jn()

### Community 92 - "mr"
Cohesion: 0.15
Nodes (4): kt(), mr, Ut(), yr

### Community 95 - "build_android_apk.sh"
Cohesion: 0.33
Nodes (5): ANDROID_HOME, BUILD_TOOLS, JAVA_HOME, PATH, build_android_apk.sh script

### Community 97 - ".getAttribute"
Cohesion: 0.25
Nodes (3): Ga, setFromCamera(), wh()

### Community 98 - "update"
Cohesion: 0.32
Nodes (4): fi(), Mh, V(), update()

### Community 100 - "constructor"
Cohesion: 0.16
Nodes (24): constructor(), d(), u(), Da, t(), gi(), l(), o() (+16 more)

### Community 101 - "q"
Cohesion: 0.23
Nodes (3): p(), q, zi()

### Community 103 - "gt"
Cohesion: 0.33
Nodes (7): j(), L(), tt(), K(), Q(), gt(), _t()

### Community 104 - "updateMatrixWorld"
Cohesion: 0.24
Nodes (3): Si(), ti, updateMatrixWorld()

### Community 108 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 113 - "DirectMemoryGovernor"
Cohesion: 0.38
Nodes (3): DirectMemoryGovernor, android.os.SharedMemory, java.nio.ByteBuffer

### Community 115 - "Xe"
Cohesion: 0.11
Nodes (7): _a, cs, Do, go, Rs, Xe(), zo

### Community 119 - "ol"
Cohesion: 0.10
Nodes (9): hl, ii, ol, parseImages(), parseTextures(), sl, uc, ul (+1 more)

## Knowledge Gaps
- **77 isolated node(s):** `NativeAndroid`, `fe`, `Ei`, `J`, `H` (+72 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 708 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **42 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `_` connect `_` to `webgl-backgrounds.js`, `three.min.js`, `sr`, `U`, `ar`, `pt`, `tn`, `.z`, `W`, `V`, `.updateWorldMatrix`, `ks`, `.update`, `parseObject`, `le`, `zt`, `en`, `.dispatchEvent`, `At`, `K`, `.lookAt`, `.rotateOnAxis`, `.copy`, `.subVectors`, `mr`, `.applyMatrix4`, `q`, `.x`, `gr`?**
  _High betweenness centrality (0.294) - this node is a cross-community bridge._
- **Why does `ws()` connect `ws` to `three.min.js`, `ei`, `tn`, `e`, `vt`, `Lt`, `.setAttribute`, `St`, `ds`, `copy`, `se`, `xc`, `tn`, `Ne`, `.multiplyScalar`, `yi`, `parseObject`, `Ce`, `yo`, `bt`, `.dispatchEvent`, `mi`, `ia`, `.applyMatrix4`, `update`, `constructor`, `gt`, `updateMatrixWorld`, `Xe`?**
  _High betweenness centrality (0.104) - this node is a cross-community bridge._
- **Why does `R()` connect `tn` to `sr`, `_`, `ws`?**
  _High betweenness centrality (0.081) - this node is a cross-community bridge._
- **Are the 21 inferred relationships involving `ws()` (e.g. with `L()` and `R()`) actually correct?**
  _`ws()` has 21 INFERRED edges - model-reasoned connections that need verification._
- **What connects `NativeAndroid`, `fe`, `Ei` to the rest of the system?**
  _77 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `package.json` be split into smaller, more focused modules?**
  _Cohesion score 0.1111111111111111 - nodes in this community are weakly interconnected._
- **Should `compilerOptions` be split into smaller, more focused modules?**
  _Cohesion score 0.14285714285714285 - nodes in this community are weakly interconnected._