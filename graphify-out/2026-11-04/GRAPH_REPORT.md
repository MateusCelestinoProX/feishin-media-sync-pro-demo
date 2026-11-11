# Graph Report - feishin-media-sync-pro  (2026-11-03)

## Corpus Check
- 27 files · ~129,388 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 2548 nodes · 5816 edges · 118 communities (71 shown, 42 thin omitted)
- Extraction: 93% EXTRACTED · 7% INFERRED · 0% AMBIGUOUS · INFERRED: 385 edges (avg confidence: 0.84)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `9e008dd3`
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
- .copy
- Wn
- tn
- zt
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
- .getX
- jc
- AudioPlaybackService
- it
- V
- ct
- .constructor
- updateMatrixWorld
- .toJSON
- Ne
- nl
- W
- sn
- yc
- WebAppInterface
- zl
- MediaSyncManager
- ya
- yt
- eh
- .multiplyScalar
- ol
- .removeEventListener
- ks
- n
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
- bind
- ws
- yo
- qo
- en
- rh
- us
- nc
- At
- K
- .invert
- Na
- .fromArray
- wo
- bt
- qc
- .computeFrenetFrames
- mi
- mc
- .fromJSON
- manifest.json
- s
- .addAttribute
- en
- fl
- ic
- _s
- parse
- build_android_apk.sh
- .applyMatrix4
- parseObject
- pl
- xs
- constructor
- q
- .x
- build_feishin_sync_assets.py
- gr
- ls
- rc
- gradlew
- ge
- DirectMemoryGovernor
- .getRenderList
- Zc
- ia
- Xe
- gt

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

## Communities (118 total, 42 thin omitted)

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
Nodes (34): Vr(), bi(), bs, $c(), er(), fi(), Hi(), hr() (+26 more)

### Community 7 - ".copy"
Cohesion: 0.10
Nodes (11): he(), Lt, or, Ot, sr(), C(), L(), N() (+3 more)

### Community 8 - "Wn"
Cohesion: 0.11
Nodes (23): _allocateTargets(), _applyPMREM(), _blur(), _cleanup(), compileCubemapShader(), compileEquirectangularShader(), _compileMaterial(), fromCubemap() (+15 more)

### Community 10 - "tn"
Cohesion: 0.05
Nodes (9): bo, dt(), $e(), is(), ms(), Qe(), ss(), tn (+1 more)

### Community 11 - "zt"
Cohesion: 0.20
Nodes (5): Ie(), Ne(), et(), xs(), zt

### Community 12 - "e"
Cohesion: 0.31
Nodes (5): cl, load(), or(), e(), o()

### Community 15 - ".setAttribute"
Cohesion: 0.16
Nodes (6): ca, ci, v(), mn, i(), yn()

### Community 17 - "U"
Cohesion: 0.05
Nodes (17): ai(), di(), _e(), fi(), gi(), hi(), ii(), li() (+9 more)

### Community 18 - "ds"
Cohesion: 0.16
Nodes (17): ds(), G(), k(), V(), z(), F(), z(), ps() (+9 more)

### Community 19 - "copy"
Cohesion: 0.06
Nodes (7): Aa, ai, clone(), copy(), sc, ts(), Tt

### Community 21 - "ar"
Cohesion: 0.08
Nodes (5): ar, kt(), mr, Ut(), yr

### Community 22 - "pt"
Cohesion: 0.10
Nodes (7): bi(), _i(), pn, pt, Si(), Ti(), vi()

### Community 23 - "Lc"
Cohesion: 0.08
Nodes (4): bc, getInput(), getOutput(), Lc

### Community 25 - "app.js"
Cohesion: 0.23
Nodes (17): ALL_SHADERS, applyShader(), bootApp(), fetchVpsStats(), formatBytes(), formatRelativeTime(), getShaderFriendlyName(), initBackgroundEngine() (+9 more)

### Community 26 - "FloatingWidgetService"
Cohesion: 0.13
Nodes (8): android.annotation.SuppressLint, FloatingWidgetService, Intent, Override, WakeLock, android.view.View, android.view.WindowManager, LayoutParams

### Community 28 - "MainActivity"
Cohesion: 0.11
Nodes (12): Intent, Override, MainActivity, android.content.BroadcastReceiver, android.os.Bundle, android.webkit.WebView, android.widget.ProgressBar, androidx.activity.result.ActivityResultLauncher (+4 more)

### Community 29 - ".z"
Cohesion: 0.09
Nodes (3): Gs(), Hs(), Xs()

### Community 30 - ".getX"
Cohesion: 0.18
Nodes (3): Ga, setFromCamera(), wh()

### Community 32 - "AudioPlaybackService"
Cohesion: 0.14
Nodes (10): android.app.PendingIntent, AudioPlaybackService, Intent, Override, WakeLock, android.graphics.Bitmap, android.media.AudioFocusRequest, android.media.AudioManager (+2 more)

### Community 33 - "it"
Cohesion: 0.16
Nodes (3): Ds(), it, St

### Community 34 - "V"
Cohesion: 0.08
Nodes (4): De(), ji(), qi(), V

### Community 35 - "ct"
Cohesion: 0.14
Nodes (4): ct(), es(), ns(), Mt()

### Community 36 - ".constructor"
Cohesion: 0.16
Nodes (6): Re(), gs, oi(), r(), v(), ys

### Community 37 - "updateMatrixWorld"
Cohesion: 0.15
Nodes (3): Si(), ti, updateMatrixWorld()

### Community 41 - "W"
Cohesion: 0.13
Nodes (9): nn(), on, rn, l(), un, W, zr, h() (+1 more)

### Community 42 - "sn"
Cohesion: 0.05
Nodes (11): an, cn, dn, fn, hn, ln, on, pn (+3 more)

### Community 45 - "zl"
Cohesion: 0.24
Nodes (3): n(), parseShapes(), zl

### Community 46 - "MediaSyncManager"
Cohesion: 0.07
Nodes (13): JSONObject, LocalMediaTrack, JSONObject, MediaSyncManager, SyncListener, UploadProgressCallback, SyncStorage, android.content.Context (+5 more)

### Community 47 - "ya"
Cohesion: 0.08
Nodes (21): Ah, ao(), co(), eo(), fh, hh, ho(), io() (+13 more)

### Community 48 - "yt"
Cohesion: 0.09
Nodes (3): vi(), h(), yt

### Community 50 - ".multiplyScalar"
Cohesion: 0.13
Nodes (4): as(), S(), M(), b()

### Community 51 - "ol"
Cohesion: 0.15
Nodes (4): ol, setTexturePath(), uc, vc

### Community 52 - ".removeEventListener"
Cohesion: 0.15
Nodes (9): dispose(), s(), Et, ni, G(), U(), Tt(), yi() (+1 more)

### Community 55 - "n"
Cohesion: 0.14
Nodes (5): ze, je, jn(), n(), Xn()

### Community 56 - "ml"
Cohesion: 0.07
Nodes (4): Al, el, ml, Rl

### Community 59 - "MediaSyncService"
Cohesion: 0.11
Nodes (15): android.app.Notification, android.app.NotificationManager, android.app.Service, JSONObject, CustomFileObserver, Intent, Override, MediaSyncService (+7 more)

### Community 61 - "ke"
Cohesion: 0.10
Nodes (11): er, fr(), A(), E(), m(), v(), ke, tr (+3 more)

### Community 62 - ".update"
Cohesion: 0.13
Nodes (4): cs, Fs(), is, rr

### Community 66 - "Ce"
Cohesion: 0.08
Nodes (6): Ee(), Te(), xe(), Ce, sh, Wl

### Community 68 - "ws"
Cohesion: 0.15
Nodes (14): C(), f(), m(), mt(), setValue(), _t, ws(), At() (+6 more)

### Community 73 - "us"
Cohesion: 0.06
Nodes (8): fs, Jl, kl, Kn, Ql, us(), vl(), Yl

### Community 75 - "At"
Cohesion: 0.06
Nodes (4): me(), pe, At, fe

### Community 76 - "K"
Cohesion: 0.13
Nodes (4): br, K, kr, nr

### Community 77 - ".invert"
Cohesion: 0.16
Nodes (4): be(), Se(), we(), qn

### Community 78 - "Na"
Cohesion: 0.20
Nodes (5): Ba, Ko, Na, _o, to()

### Community 80 - "wo"
Cohesion: 0.14
Nodes (3): jo, wo, xo

### Community 81 - "bt"
Cohesion: 0.12
Nodes (7): bt, ei, ii, li(), parseTextures(), Ra, rt

### Community 83 - ".computeFrenetFrames"
Cohesion: 0.15
Nodes (4): cr, dr(), ki(), pr()

### Community 84 - "mi"
Cohesion: 0.16
Nodes (15): d(), u(), y(), gi(), v(), mi(), g(), p() (+7 more)

### Community 86 - ".fromJSON"
Cohesion: 0.14
Nodes (3): dl, oc, parseGeometries()

### Community 87 - "manifest.json"
Cohesion: 0.20
Nodes (9): background_color, description, display, icons, name, orientation, short_name, start_url (+1 more)

### Community 88 - "s"
Cohesion: 0.24
Nodes (9): s(), A(), G(), H(), O(), U(), mo(), l() (+1 more)

### Community 93 - "_s"
Cohesion: 0.25
Nodes (3): Ys(), _s(), n()

### Community 94 - "parse"
Cohesion: 0.29
Nodes (6): bindSkeletons(), il(), parse(), parseAnimations(), parseSkeletons(), pc

### Community 95 - "build_android_apk.sh"
Cohesion: 0.33
Nodes (5): ANDROID_HOME, BUILD_TOOLS, JAVA_HOME, PATH, build_android_apk.sh script

### Community 97 - "parseObject"
Cohesion: 0.10
Nodes (7): fa, $l, parseObject(), Rs, ta, tc, Vs

### Community 98 - "pl"
Cohesion: 0.14
Nodes (5): hl, parseImages(), pl, sl, ul

### Community 99 - "xs"
Cohesion: 0.22
Nodes (3): qs(), b(), xs

### Community 100 - "constructor"
Cohesion: 0.15
Nodes (22): br(), constructor(), Da, i(), t(), gn(), l(), o() (+14 more)

### Community 101 - "q"
Cohesion: 0.24
Nodes (3): p(), q, zi()

### Community 103 - "build_feishin_sync_assets.py"
Cohesion: 0.70
Nodes (4): build_masters(), create_arrow(), export_all(), render_svg_to_png()

### Community 108 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 110 - "DirectMemoryGovernor"
Cohesion: 0.60
Nodes (3): DirectMemoryGovernor, android.os.SharedMemory, java.nio.ByteBuffer

### Community 114 - "Xe"
Cohesion: 0.09
Nodes (9): _a, cs, Do, fo(), go, hs, w(), Xe() (+1 more)

### Community 115 - "gt"
Cohesion: 0.47
Nodes (6): j(), K(), Q(), Y(), gt(), _t()

## Knowledge Gaps
- **78 isolated node(s):** `NativeAndroid`, `ALL_SHADERS`, `fe`, `Ei`, `J` (+73 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 708 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **42 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `_` connect `_` to `webgl-backgrounds.js`, `three.min.js`, `.copy`, `zt`, `U`, `ar`, `pt`, `tn`, `.z`, `it`, `V`, `.constructor`, `W`, `ks`, `n`, `ke`, `.update`, `le`, `Ce`, `en`, `At`, `K`, `.invert`, `.computeFrenetFrames`, `.addAttribute`, `_s`, `.applyMatrix4`, `xs`, `q`, `.x`, `gr`, `.getRenderList`?**
  _High betweenness centrality (0.268) - this node is a cross-community bridge._
- **Why does `ws()` connect `ws` to `three.min.js`, `.copy`, `tn`, `vt`, `Lt`, `.setAttribute`, `St`, `ds`, `copy`, `se`, `ct`, `.constructor`, `updateMatrixWorld`, `.multiplyScalar`, `.removeEventListener`, `ke`, `Ce`, `yo`, `us`, `bt`, `mi`, `s`, `parse`, `.applyMatrix4`, `xs`, `constructor`, `ia`, `Xe`, `gt`?**
  _High betweenness centrality (0.115) - this node is a cross-community bridge._
- **Why does `R()` connect `ke` to `ws`, `_`?**
  _High betweenness centrality (0.083) - this node is a cross-community bridge._
- **Are the 21 inferred relationships involving `ws()` (e.g. with `L()` and `R()`) actually correct?**
  _`ws()` has 21 INFERRED edges - model-reasoned connections that need verification._
- **What connects `NativeAndroid`, `ALL_SHADERS`, `fe` to the rest of the system?**
  _78 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `package.json` be split into smaller, more focused modules?**
  _Cohesion score 0.1111111111111111 - nodes in this community are weakly interconnected._
- **Should `compilerOptions` be split into smaller, more focused modules?**
  _Cohesion score 0.14285714285714285 - nodes in this community are weakly interconnected._