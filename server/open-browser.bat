@ECHO off
set /P PORT=<conf\kira\port.txt
rem remove user data dir to prevent chromium from caching
set /P CLEARCACHE=<conf\kira\clearchromecache.txt
if %CLEARCACHE% EQU true (
	rd /S /Q "../browser/user"
)
start "Kira" /B "../browser/chrome.exe" "--user-data-dir=../browser/user" --window-size=1280,720 --disable-sync --disable-translate --fast --fast-start --no-first-run --disable-infobars --disable-session-crashed-bubble --app=http://localhost:%PORT%/boot/index.html
exit 0