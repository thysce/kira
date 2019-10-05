# kira
A standalone webapp deployment container for Windows 32/64bit that lets you run web applications just like windows native applications.

## licence
The entire kira framework is open-source, free and commercially usable, modifyable and everything thanks to the unlicence.
The shipped components are published under their respective licence that must be hold.

## installation
Just clone the entire repository and copy it with your app to the desired installation location.

## usage
- 1 write your web application
- 2 build a WAR archive and name it "app.war"
- 3 copy and overwrite your war to ./kira-install-path/server/app.war
- 4 start the kira application by executing ./kira-install-path/Kira.exe or Kira.bat

## hints
- To shutdown the kira server from your application, just trigger POST http://localhost:KIRA_PORT/boot/shutdown
- you may wnat to shutdown kira on beforeunload
- To check whether your kira instance (the framework, not your app) is running, GET http://localhost:KIRA_PORT/boot/online
- The default port your instance will run on is 80.
- There are instructions on how to use a TLS certificate with kira unter ./kira-install-path/server/conf/tls. For that you may want to use (or modify) conf/server-tls.xml. To activate that configuration, drop the server.xml and rename server-tls.xml to server.xml.
- All licences for the bundled binaries are under ./kira-install-path/legal
- All shipped binaries are 32bit to ensure the entire framework runs on a 32bit Windows machine.
- jQuery and jQuery UI are already accessible under http://localhost:KIRA_PORT/hosted/....
- There is a CGI interface you may use at http://localhost:KIRA_PORT/cgi/...
- There is a database server bundled that you may want to use at ./kira-install-path/server/db/h2.bat. To access that server use JDBC:H2:TCP://localhost/./YOUR_DATABASE or manually execute ./kira-install-path/server/db/h2shell.bat
Feel free to change everything on kira you like. Here are some points of special interest:
- renaming the starting execution binary Kira.exe to YourApp.exe
- changing the boot animation under ./kira-install-path/server/webapps/boot/index.html
- changing the application name under ./kira-install-path/server/static/appname.txt
- changing the port on that your kira instance is accessible by ./kira-install-path/server/conf/kira/port.txt
- set whether you want the shipped chromium to automatically clear it's cache on start/stop by ./kira-install-path/server/conf/kira/clearchromecache.txt
- To change the command line by which the chromium is started, modify ./kira-install-path/server/open-browser.bat

## bundled software
 - Java OpenJDK JRE 32bit Windows
 - Standalone 9.0.14 Tomcat Deployment Container
 - H2 Embedded Java Database
 - Chromium Web Browser
 - jQuery 2.2.4, jQuery UI 1.12.1
 all shipped under their respective licence that you must hold.
