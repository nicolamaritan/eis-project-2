javac -cp "./lib/*;./" src/myTest/*.java src/myAdapter/*.java
java -cp "./src;./lib/*" myTest.TestRunner
cd ./src/myTest
del *.class
cd ../myAdapter
del *.class
cd ../../