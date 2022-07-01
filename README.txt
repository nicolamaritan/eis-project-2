Compile from the root of the project with:
javac -cp "./lib/*:./" src/myTest/*.java src/myAdapter/*.java (Linux)
javac -cp "./lib/*;./" src/myTest/*.java src/myAdapter/*.java (Windows)

Execute from the root of the project with:
java -cp "./src:./lib/*" myTest.TestRunner (Linux)
java -cp "./src;./lib/*" myTest.TestRunner (Windows)