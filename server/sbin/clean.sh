#echo off
cd work
echo clean /work
rm -r Catalina
cd ..

cd temp
echo clean /temp
rm -r *
cd ..

cd logs
echo clean /logs
rm *
cd ..

echo tomcat cleaned.