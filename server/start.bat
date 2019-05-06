@echo off
rem you have to pass JRE_HOME, PORT and TIMESTAMP as environment variables to this script
echo start at %date% %time%
echo Kira starten...
echo bereinigen...
cd webapps
if exist ROOT.war (
	del ROOT.war /q /f
)
if exist ROOT (
	rd ROOT /s /q
)
cd ..
echo webserver starten...
set CATALINA_OPTS=-Dkira.port=%PORT%
set TITLE="Kira"
call "bin/catalina.bat" run >logs/tomcat-%TIMESTAMP%.log 2>&1
cd ..
exit 0
