#!/bin/bash
set -e

# ==============================================================================
# 🚀 FEISHIN MEDIA SYNC PRO — ANDROID RELEASE APK BUILD SCRIPT
# ==============================================================================
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

export BUILD_TOOLS="/opt/homebrew/share/android-commandlinetools/build-tools/35.0.0"
export JAVA_HOME="/opt/homebrew/Cellar/openjdk@17/17.0.20.1/libexec/openjdk.jdk/Contents/Home"
export ANDROID_HOME="/opt/homebrew/share/android-commandlinetools"
export PATH="$JAVA_HOME/bin:$BUILD_TOOLS:$PATH"

echo "========================================================"
echo "🚀 Compilando APK Nativo Feishin Media Sync Pro para Android"
echo "========================================================"

cd "$SCRIPT_DIR/android"
chmod +x ./gradlew
./gradlew assembleRelease

APK_OUTPUT="$SCRIPT_DIR/android/app/build/outputs/apk/release/app-release.apk"
FINAL_APK="$SCRIPT_DIR/feishin-media-sync-pro-release.apk"

if [ -f "$APK_OUTPUT" ]; then
    cp "$APK_OUTPUT" "$FINAL_APK"
    echo "========================================================"
    echo "✅ APK Android gerado com sucesso!"
    ls -lh "$FINAL_APK"
    echo "========================================================"
fi

cd "$SCRIPT_DIR"

if [ "$1" == "--install" ] || [ "$1" == "-i" ]; then
    DEVICE_ID=$(adb devices | grep -w "device" | head -n 1 | awk '{print $1}')
    if [ -n "$DEVICE_ID" ]; then
        echo "📲 Instalando exclusivamente no perfil principal (User 0) no dispositivo $DEVICE_ID..."
        adb -s "$DEVICE_ID" install -r --user 0 "$FINAL_APK"
        echo "🚀 Iniciando app no perfil principal..."
        adb -s "$DEVICE_ID" shell am start --user 0 -n com.feishin.mediasync/.MainActivity
    fi
fi
