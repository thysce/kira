@echo off
echo build main boot application
javac -cp lib/commons-logging-1.2.jar;lib/httpclient-4.5.7.jar;lib/httpcore-4.4.11.jar Server.java
echo package main boot application
jar cfm Server.jar Server.mf Server.class

echo build boot webapp
cd webapps/boot/WEB-INF/classes
call compile.bat
cd ../../../..

echo build Kira.exe
g++ Kira.cpp -o ../Kira.exe