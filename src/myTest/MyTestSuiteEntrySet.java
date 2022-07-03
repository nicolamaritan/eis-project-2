package myTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import myAdapter.*;

public class MyTestSuiteEntrySet 
{
    private MapAdapter m;
    private HSet es;

    @BeforeClass
    public static void BeforeClassMethod()
    {
        System.out.println(MyTestSuiteEntrySet.class.getName() + " running.");
    }

    @Before
    public void BeforeMethod()
    {
        m = new MapAdapter();
    }
    

    /**
     * <p><b>Summary</b>: empty entry set test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case
     * of an empty entrySet, created from an empty
     * Map.</p>
     * <p><b>Test Description</b>: es is empty, then size,
     * isEmpty and its iterators have been tested.</p>
     * <p><b>Pre-Condition</b>: es is empty.</p>
     * <p><b>Post-Condition</b>: es is unchanged.</p>
     * <p><b>Expected Results</b>: size is 0, isEmpty returns
     * true, iterator.hasNext is false and next throws NSEE</p>
     */
    @Test (expected = NoSuchElementException.class)
    public void EntrySet_Empty()
    {
        es = m.entrySet();
        assertEquals("Size should be 0", 0, es.size());
        assertTrue("Should be empty", es.isEmpty());

        HIterator it = es.iterator();
        assertFalse("Sould not have next", it.hasNext());
        it.next();  // Exception throw
    }
    
}

    /**
     * <p><b>Summary</b>:</p>
     * <p><b>Test Case Design</b>:</p>
     * <p><b>Test Description</b>:</p>
     * <p><b>Pre-Condition</b>:</p>
     * <p><b>Post-Condition</b>:</p>
     * <p><b>Expected Results</b>:</p>
     */