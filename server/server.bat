@echo off
set JRE_HOME=%~dp0%..\jre
set /P PORT=<conf\kira\port.txt
if not exist logs mkdir logs
start "Kira" /B "%JRE_HOME%\bin\java" -jar Server.jar >logs/start.log 2>&1
exit 0
