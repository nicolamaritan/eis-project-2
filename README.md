# Elements of Software Engineering assignment 12/07/2022

The assignment is creating an adapter of ```java.util.Map``` (J2SE 1.4.2), where the adaptee is ```java.util.Hashtable``` (CLDC 1.1).
The adapter is called ```myAdapter.HMap```. It was developed through TDD. The test suites are contained in ```myTest``` package.

Assignment compilation:

```javac -cp "./JUnit/*;./Matcher/*" myAdapter/*.java myTest/*.java```

myTest.TestRunner execution:

```java -cp "./;./JUnit/*;./Matcher/*" myTest.TestRunner"```
