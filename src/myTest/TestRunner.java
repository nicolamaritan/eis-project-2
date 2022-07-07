package myTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.util.List;

/**
 * TestRunner class can be launched through command line to
 * invoke all tests for myTest package. In fact it has a main
 * method to initiate and run all the testing. It is based on
 * JUnit framework.
 * 
 * @version JUnit 4.13
 * @author Nicola Maritan
 */
public class TestRunner
{
    // Contains the number of total test
    private static int numberOfTests = 0;
    private static int numberOfSuccesses = 0;
    private static int numberOfFails = 0;
    private static long time = 0;
    private static List<Failure> failures;

    /**
     * Main method. TestRunner is designed to be launched through
     * command line. Starting point for all testing.
     * @param args unused
     */
    public static void main(String[] args) 
    {
        // Contains the test result from calling JUnitCore.runClasses
        Result testResults;

        System.out.println("TestRunner started. Starting test executions.");
        testResults = JUnitCore.runClasses(TestCollection.class,
                                           TestSet.class,
                                           TestMap.class,
                                           TestEntrySet.class,
                                           TestKeySet.class,
                                           TestValues.class);

        System.out.println("TestRunner ended. All the tests have been completed.");

        elaborateResults(testResults);
        printStatistics();
    }

    private static void elaborateResults(Result result)
    {
        numberOfTests = result.getRunCount();
        numberOfFails = result.getFailureCount();
        numberOfSuccesses = numberOfTests - numberOfFails;
        failures = result.getFailures();
        time = result.getRunTime();
    }

    private static void printStatistics()
    {
        System.out.println("Number of tests runned: " + numberOfTests);
        System.out.println("Number of successfull tests: " + numberOfSuccesses + "(" + (float)numberOfSuccesses / (float)numberOfTests * 100 + "%)");
        System.out.println("Number of failed tests: " + numberOfFails + "(" + (float)numberOfFails / (float)numberOfTests * 100 + "%)");
        System.out.println("Time passed: " + time);

        if (numberOfFails > 0)
        {
            System.out.println("Failed tests are: ");
            
            for (Failure f : failures)
            {
                System.out.println(" - " + f);
            }
        }
        else
        {
            System.out.println("All tests were completed successfully.");
        }
    }
}