#!/usr/bin/env python3
import os
import math
import subprocess
from PIL import Image

SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
PROJECT_ROOT = SCRIPT_DIR
ANDROID_RES = os.path.join(PROJECT_ROOT, "android/app/src/main/res")
ANDROID_ASSETS = os.path.join(PROJECT_ROOT, "android/app/src/main/assets")
TEMP_DIR = os.path.join(PROJECT_ROOT, ".icon_build_cache")
os.makedirs(TEMP_DIR, exist_ok=True)

FEISHIN_DOT_D = "cx=\"512\" cy=\"480.62\" r=\"43\""
FEISHIN_BOTTOM_D = "M 441.7 555.9 L 367 631.2 L 439 703.4 L 479 664 C 479 664 490.7 651.6 512 651.6 C 533.3 651.6 545 664 545 664 L 585 703.4 L 657 631.2 L 582.3 555.9 A 103 103 0 0 1 512 583.6 A 103 103 0 0 1 441.7 555.9 Z"
FEISHIN_MAIN_D = "M 512 290.8 C 497.8 290.8 484.9 296.6 475.5 305.9 L 133.9 646.9 C 124.7 656.2 119 668.9 119 683 C 119 711.2 141.8 734 170 734 C 184.1 734 196.8 728.3 206.1 719 L 413.9 511.9 A 103 103 0 0 1 409 480.6 A 103 103 0 0 1 512 377.6 A 103 103 0 0 1 615 480.6 A 103 103 0 0 1 610.1 511.9 L 817.9 719.1 C 827.2 728.3 839.9 734 854 734 C 882.2 734 905 711.2 905 683 C 905 668.9 899.3 656.2 890.1 646.9 L 548.5 305.9 C 539.1 296.6 526.2 290.8 512 290.8 Z"

def create_arrow(cx, cy, radius, start_deg, span_deg, stroke_w, head_w, head_len, color):
    end_deg = start_deg + span_deg
    rad_start = math.radians(start_deg)
    rad_end = math.radians(end_deg)
    xs = cx + radius * math.cos(rad_start)
    ys = cy + radius * math.sin(rad_start)
    xe = cx + radius * math.cos(rad_end)
    ye = cy + radius * math.sin(rad_end)
    large_arc = 1 if span_deg > 180 else 0
    arc_d = f"M {xs:.2f} {ys:.2f} A {radius} {radius} 0 {large_arc} 1 {xe:.2f} {ye:.2f}"
    
    tangent_angle = math.radians(end_deg + 90)
    tx = math.cos(tangent_angle)
    ty = math.sin(tangent_angle)
    nx = math.cos(rad_end)
    ny = math.sin(rad_end)
    
    tip_x = xe + tx * head_len
    tip_y = ye + ty * head_len
    p1_x = xe + nx * (head_w / 2.0)
    p1_y = ye + ny * (head_w / 2.0)
    p2_x = xe - nx * (head_w / 2.0)
    p2_y = ye - ny * (head_w / 2.0)
    head_d = f"M {tip_x:.2f} {tip_y:.2f} L {p1_x:.2f} {p1_y:.2f} L {p2_x:.2f} {p2_y:.2f} Z"
    return arc_d, head_d

def render_svg_to_png(name, svg_content, size=1024):
    svg_path = os.path.join(TEMP_DIR, f"{name}.svg")
    with open(svg_path, "w") as f:
        f.write(svg_content)
    
    subprocess.run(["qlmanage", "-t", "-s", str(size), "-o", TEMP_DIR, svg_path], capture_output=True)
    gen_file = os.path.join(TEMP_DIR, f"{name}.svg.png")
    dest_png = os.path.join(TEMP_DIR, f"{name}.png")
    if os.path.exists(gen_file):
        os.rename(gen_file, dest_png)
    
    im = Image.open(dest_png).convert("RGBA")
    if im.size != (size, size):
        im = im.resize((size, size), Image.Resampling.LANCZOS)
    return im

def build_masters():
    cx, cy = 512, 512
    # Safe zone optimized geometry for 1024x1024
    radius = 336
    stroke_w = 40
    head_w = 76
    head_len = 64
    arrow_color = "#2563EB"
    feishin_color = "#0F1115"
    scale = 0.63
    
    arc1_d, head1_d = create_arrow(cx, cy, radius, 205, 130, stroke_w, head_w, head_len, arrow_color)
    arc2_d, head2_d = create_arrow(cx, cy, radius, 25, 130, stroke_w, head_w, head_len, arrow_color)
    emblem_transform = f"transform=\"translate(512, 512) scale({scale}) translate(-512, -512)\""
    
    # 1. Master Squircle
    svg_squircle = f"""<svg width="1024" height="1024" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg">
  <defs>
    <linearGradient id="bgGrad" x1="512" y1="0" x2="512" y2="1024" gradientUnits="userSpaceOnUse">
      <stop offset="0%" stop-color="#FFFFFF" />
      <stop offset="100%" stop-color="#E2E7EC" />
    </linearGradient>
    <filter id="shadow" x="-10%" y="-10%" width="120%" height="120%">
      <feDropShadow dx="0" dy="12" stdDeviation="20" flood-color="#000000" flood-opacity="0.14" />
    </filter>
    <filter id="emblemShadow" x="-20%" y="-20%" width="140%" height="140%">
      <feDropShadow dx="0" dy="6" stdDeviation="6" flood-color="#000000" flood-opacity="0.15" />
    </filter>
  </defs>
  <rect x="36" y="36" width="952" height="952" rx="216" fill="url(#bgGrad)" stroke="#E2E7ED" stroke-width="4" filter="url(#shadow)" />
  <g id="sync-cycle" filter="url(#emblemShadow)">
    <path d="{arc1_d}" fill="none" stroke="{arrow_color}" stroke-width="{stroke_w}" stroke-linecap="round" />
    <path d="{head1_d}" fill="{arrow_color}" stroke="{arrow_color}" stroke-width="4" stroke-linejoin="round" />
    <path d="{arc2_d}" fill="none" stroke="{arrow_color}" stroke-width="{stroke_w}" stroke-linecap="round" />
    <path d="{head2_d}" fill="{arrow_color}" stroke="{arrow_color}" stroke-width="4" stroke-linejoin="round" />
  </g>
  <g id="feishin-emblem" {emblem_transform} filter="url(#emblemShadow)">
    <circle {FEISHIN_DOT_D} fill="{feishin_color}" />
    <path d="{FEISHIN_BOTTOM_D}" fill="{feishin_color}" />
    <path d="{FEISHIN_MAIN_D}" fill="{feishin_color}" />
  </g>
</svg>"""
    master_squircle = render_svg_to_png("master_squircle", svg_squircle, 1024)

    # 2. Master Circle
    svg_circle = f"""<svg width="1024" height="1024" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg">
  <defs>
    <linearGradient id="bgGrad" x1="512" y1="0" x2="512" y2="1024" gradientUnits="userSpaceOnUse">
      <stop offset="0%" stop-color="#FFFFFF" />
      <stop offset="100%" stop-color="#E2E7EC" />
    </linearGradient>
    <filter id="shadow" x="-10%" y="-10%" width="120%" height="120%">
      <feDropShadow dx="0" dy="12" stdDeviation="20" flood-color="#000000" flood-opacity="0.14" />
    </filter>
    <filter id="emblemShadow" x="-20%" y="-20%" width="140%" height="140%">
      <feDropShadow dx="0" dy="6" stdDeviation="6" flood-color="#000000" flood-opacity="0.15" />
    </filter>
  </defs>
  <circle cx="512" cy="512" r="496" fill="url(#bgGrad)" stroke="#E5E9EE" stroke-width="4" filter="url(#shadow)" />
  <g id="sync-cycle" filter="url(#emblemShadow)">
    <path d="{arc1_d}" fill="none" stroke="{arrow_color}" stroke-width="{stroke_w}" stroke-linecap="round" />
    <path d="{head1_d}" fill="{arrow_color}" stroke="{arrow_color}" stroke-width="4" stroke-linejoin="round" />
    <path d="{arc2_d}" fill="none" stroke="{arrow_color}" stroke-width="{stroke_w}" stroke-linecap="round" />
    <path d="{head2_d}" fill="{arrow_color}" stroke="{arrow_color}" stroke-width="4" stroke-linejoin="round" />
  </g>
  <g id="feishin-emblem" {emblem_transform} filter="url(#emblemShadow)">
    <circle {FEISHIN_DOT_D} fill="{feishin_color}" />
    <path d="{FEISHIN_BOTTOM_D}" fill="{feishin_color}" />
    <path d="{FEISHIN_MAIN_D}" fill="{feishin_color}" />
  </g>
</svg>"""
    master_circle = render_svg_to_png("master_circle", svg_circle, 1024)

    # 3. Adaptive Background (Full bleed gradient square)
    svg_adaptive_bg = f"""<svg width="1024" height="1024" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg">
  <defs>
    <linearGradient id="bgGrad" x1="512" y1="0" x2="512" y2="1024" gradientUnits="userSpaceOnUse">
      <stop offset="0%" stop-color="#FFFFFF" />
      <stop offset="100%" stop-color="#E2E7EC" />
    </linearGradient>
  </defs>
  <rect width="1024" height="1024" fill="url(#bgGrad)" />
</svg>"""
    adaptive_bg = render_svg_to_png("adaptive_bg", svg_adaptive_bg, 1024)

    # 4. Adaptive Foreground (Transparent canvas with emblem + sync arrows)
    svg_adaptive_fg = f"""<svg width="1024" height="1024" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg">
  <defs>
    <filter id="emblemShadow" x="-20%" y="-20%" width="140%" height="140%">
      <feDropShadow dx="0" dy="6" stdDeviation="6" flood-color="#000000" flood-opacity="0.16" />
    </filter>
  </defs>
  <g id="sync-cycle" filter="url(#emblemShadow)">
    <path d="{arc1_d}" fill="none" stroke="{arrow_color}" stroke-width="{stroke_w}" stroke-linecap="round" />
    <path d="{head1_d}" fill="{arrow_color}" stroke="{arrow_color}" stroke-width="4" stroke-linejoin="round" />
    <path d="{arc2_d}" fill="none" stroke="{arrow_color}" stroke-width="{stroke_w}" stroke-linecap="round" />
    <path d="{head2_d}" fill="{arrow_color}" stroke="{arrow_color}" stroke-width="4" stroke-linejoin="round" />
  </g>
  <g id="feishin-emblem" {emblem_transform} filter="url(#emblemShadow)">
    <circle {FEISHIN_DOT_D} fill="{feishin_color}" />
    <path d="{FEISHIN_BOTTOM_D}" fill="{feishin_color}" />
    <path d="{FEISHIN_MAIN_D}" fill="{feishin_color}" />
  </g>
</svg>"""
    adaptive_fg = render_svg_to_png("adaptive_fg", svg_adaptive_fg, 1024)

    return master_squircle, master_circle, adaptive_bg, adaptive_fg, svg_squircle

def export_all():
    print("🎨 Gerando artes master Feishin Media Sync Pro 1024x1024...")
    master_squircle, master_circle, adaptive_bg, adaptive_fg, raw_svg = build_masters()

    # 1. Export Web & PWA assets
    print("🌐 Exportando assets Web & PWA em android/app/src/main/assets...")
    web_dir = ANDROID_ASSETS
    os.makedirs(web_dir, exist_ok=True)
    
    master_circle.resize((16, 16), Image.Resampling.LANCZOS).save(os.path.join(web_dir, "favicon-16x16.png"))
    master_circle.resize((32, 32), Image.Resampling.LANCZOS).save(os.path.join(web_dir, "favicon-32x32.png"))
    master_circle.resize((64, 64), Image.Resampling.LANCZOS).save(os.path.join(web_dir, "favicon.png"))
    master_squircle.resize((180, 180), Image.Resampling.LANCZOS).save(os.path.join(web_dir, "apple-touch-icon.png"))
    master_squircle.resize((192, 192), Image.Resampling.LANCZOS).save(os.path.join(web_dir, "icon-192.png"))
    master_squircle.resize((512, 512), Image.Resampling.LANCZOS).save(os.path.join(web_dir, "icon-512.png"))
    
    # Favicon.ico
    master_circle.save(os.path.join(web_dir, "favicon.ico"), sizes=[(16, 16), (32, 32), (48, 48), (64, 64)])
    with open(os.path.join(web_dir, "favicon.svg"), "w") as f:
        f.write(raw_svg)
    
    # 2. Export Android Mipmap densities
    print("📱 Exportando mipmaps Android (mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi)...")
    densities = {
        "mipmap-mdpi": (48, 108),
        "mipmap-hdpi": (72, 162),
        "mipmap-xhdpi": (96, 216),
        "mipmap-xxhdpi": (144, 324),
        "mipmap-xxxhdpi": (192, 432)
    }

    for folder, (size, fg_size) in densities.items():
        folder_path = os.path.join(ANDROID_RES, folder)
        os.makedirs(folder_path, exist_ok=True)
        
        # Launcher icons
        master_squircle.resize((size, size), Image.Resampling.LANCZOS).save(os.path.join(folder_path, "ic_launcher.png"))
        master_circle.resize((size, size), Image.Resampling.LANCZOS).save(os.path.join(folder_path, "ic_launcher_round.png"))
        
        # Adaptive layers
        adaptive_bg.resize((fg_size, fg_size), Image.Resampling.LANCZOS).save(os.path.join(folder_path, "ic_launcher_background.png"))
        adaptive_fg.resize((fg_size, fg_size), Image.Resampling.LANCZOS).save(os.path.join(folder_path, "ic_launcher_foreground.png"))
        print(f"  ✓ {folder}: launcher {size}x{size}, adaptive {fg_size}x{fg_size}")

    # Drawable direct fallback
    drawable_dir = os.path.join(ANDROID_RES, "drawable")
    os.makedirs(drawable_dir, exist_ok=True)
    master_squircle.resize((192, 192), Image.Resampling.LANCZOS).save(os.path.join(drawable_dir, "ic_launcher.png"))

    # Update ic_media_wave.xml for notifications and floating bubble
    # Vector Feishin Sync Icon in 24dp viewport
    media_wave_xml = """<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
    <!-- Feishin Music Sync Notification & Floating Bubble Vector -->
    <!-- Central Turntable Dot -->
    <path
        android:fillColor="#FFFFFFFF"
        android:pathData="M12,11.25m-1.1,0a1.1,1.1 0 1,0 2.2,0a1.1,1.1 0 1,0 -2.2,0" />
    <!-- Feishin Bottom Notch -->
    <path
        android:fillColor="#FFFFFFFF"
        android:pathData="M10.4,13 L8.7,14.7 L10.3,16.3 L11.2,15.4 C11.4,15.2 11.7,15.1 12,15.1 C12.3,15.1 12.6,15.2 12.8,15.4 L13.7,16.3 L15.3,14.7 L13.6,13 C13.2,13.4 12.6,13.6 12,13.6 C11.4,13.6 10.8,13.4 10.4,13 Z" />
    <!-- Feishin Chevron Wings -->
    <path
        android:fillColor="#FFFFFFFF"
        android:pathData="M12,7 C11.7,7 11.4,7.1 11.2,7.3 L3.3,15.2 C3,15.5 2.8,15.8 2.8,16.2 C2.8,17 3.4,17.6 4.2,17.6 C4.6,17.6 4.9,17.4 5.2,17.1 L10,12.3 C10.2,11.7 10.7,11.2 11.3,10.9 C11.5,10.8 11.7,10.7 12,10.7 C12.3,10.7 12.5,10.8 12.7,10.9 C13.3,11.2 13.8,11.7 14,12.3 L18.8,17.1 C19.1,17.4 19.4,17.6 19.8,17.6 C20.6,17.6 21.2,17 21.2,16.2 C21.2,15.8 21,15.5 20.7,15.2 L12.8,7.3 C12.6,7.1 12.3,7 12,7 Z" />
    <!-- Dual Sync Orbit Arcs -->
    <path
        android:strokeColor="#FFFFFFFF"
        android:strokeWidth="1.3"
        android:strokeLineCap="round"
        android:fillColor="#00000000"
        android:pathData="M5,9.5 A8.5,8.5 0 0,1 18.5,9.5" />
    <path
        android:fillColor="#FFFFFFFF"
        android:pathData="M20,11 L17,8.5 L17,11.5 Z" />
    <path
        android:strokeColor="#FFFFFFFF"
        android:strokeWidth="1.3"
        android:strokeLineCap="round"
        android:fillColor="#00000000"
        android:pathData="M19,14.5 A8.5,8.5 0 0,1 5.5,14.5" />
    <path
        android:fillColor="#FFFFFFFF"
        android:pathData="M4,13 L7,15.5 L7,12.5 Z" />
</vector>
"""
    with open(os.path.join(drawable_dir, "ic_media_wave.xml"), "w") as f:
        f.write(media_wave_xml)

    # Adaptive icon XMLs in mipmap-anydpi-v26
    anydpi_dir = os.path.join(ANDROID_RES, "mipmap-anydpi-v26")
    os.makedirs(anydpi_dir, exist_ok=True)
    adaptive_xml = """<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@mipmap/ic_launcher_background" />
    <foreground android:drawable="@mipmap/ic_launcher_foreground" />
</adaptive-icon>
"""
    with open(os.path.join(anydpi_dir, "ic_launcher.xml"), "w") as f:
        f.write(adaptive_xml)
    with open(os.path.join(anydpi_dir, "ic_launcher_round.xml"), "w") as f:
        f.write(adaptive_xml)

    print("✨ Todos os assets do Feishin Media Sync Pro foram gerados com perfeição!")

if __name__ == "__main__":
    export_all()
