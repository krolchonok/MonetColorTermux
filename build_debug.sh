#!/bin/bash

# Цвета для вывода
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${GREEN}[*] Starting build process...${NC}"

# 1. Проверка окружения
if [ ! -f "local.properties" ]; then
    echo -e "${RED}[!] local.properties not found. Running setup_env.sh...${NC}"
    chmod +x setup_env.sh
    ./setup_env.sh
fi

# 2. Сборка APK
chmod +x gradlew
./gradlew assembleDebug

if [ $? -eq 0 ]; then
    echo -e "${GREEN}[+] Build successful!${NC}"
else
    echo -e "${RED}[!] Build failed!${NC}"
    exit 1
fi

# 3. Установка через ADB
APK_PATH=$(find app/build/outputs/apk/debug/ -name "*.apk" | head -n 1)

if [ -z "$APK_PATH" ]; then
    echo -e "${RED}[!] APK file not found!${NC}"
    exit 1
fi

echo -e "${GREEN}[*] Installing $APK_PATH...${NC}"
adb install -r "$APK_PATH"

if [ $? -eq 0 ]; then
    echo -e "${GREEN}[+] Successfully installed on device!${NC}"
else
    echo -e "${RED}[!] Installation failed! Check if device is connected via 'adb devices'.${NC}"
fi
