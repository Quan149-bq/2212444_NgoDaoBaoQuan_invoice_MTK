@echo off
cd /d "%~dp0"

echo ===============================
echo   CHUONG TRINH HOA DON BAN HANG
echo ===============================

set "JAVA_TOOL_OPTIONS=--enable-native-access=ALL-UNNAMED"

java -jar "invoice-system.jar"

echo.
pause