@echo off
rem you have to pass JRE_HOME as an environment variable to this script
echo shutdown at %date% %time%
call .\bin\shutdown.bat
