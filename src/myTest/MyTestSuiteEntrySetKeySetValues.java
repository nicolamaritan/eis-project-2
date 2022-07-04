package myTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.*;

import myAdapter.*;

public class MyTestSuiteEntrySetKeySetValues 
{
    private MapAdapter m;
    private HSet es;
    private HCollection c;

    @BeforeClass
    public static void BeforeClassMethod()
    {
        System.out.println(MyTestSuiteEntrySetKeySetValues.class.getName() + " running.");
    }

    @Before
    public void BeforeMethod()
    {
        m = new MapAdapter();
        es = m.entrySet();
        c = new CollectionAdapter();
    }

    @AfterClass
    public static void AfterClassMethod()
    {
        System.out.println(MyTestSuiteEntrySetKeySetValues.class.getName() + " running.");
    }

    @After
    public void AfterMethod()
    {
        m = new MapAdapter();
        es = m.entrySet();
    }
    
    // ------------------------------------------ add method ------------------------------------------
    @Test (expected = UnsupportedOperationException.class)
    public void Add_UOE()
    {
        es.add(1);
    }

    // ------------------------------------------ addAll method ------------------------------------------
    @Test (expected = UnsupportedOperationException.class)
    public void AddAll_UOE()
    {
        es.addAll(c);
    }

	// ------------------------------------------ isEmpty method ------------------------------------------

    /**
     * <p><b>Summary</b>: isEmpty method test case.</p>
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
    public void IsEmpty_EmptyMapEmptyES()
    {
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