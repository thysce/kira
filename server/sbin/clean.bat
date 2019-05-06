@echo off
cd work
echo clean /work
rmdir /s /q Catalina
cd ..

cd temp
echo clean /temp
del /s /q *
cd ..

cd logs
echo clean /logs
del /s /q *
cd ..

echo tomcat cleaned.