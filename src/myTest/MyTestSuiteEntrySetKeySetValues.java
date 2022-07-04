package myTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import static myTest.TestUtilities.*;

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

    /**
     * <p><b>Summary</b>: isEmpty method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case
     * of an entrySet containing only 1 element. Propagation
     * map -> entryset is tested.</p>
     * <p><b>Test Description</b>: es contains {1=One}, then size,
     * isEmpty and its iterators have been tested.</p>
     * <p><b>Pre-Condition</b>: es contains {1=One}.</p>
     * <p><b>Post-Condition</b>: es is unchanged.</p>
     * <p><b>Expected Results</b>: isEmpty returns
     * false, iterator.hasNext is true and second next throws NSEE.
     * The only element is 1=One.</p>
     */
    @Test (expected = NoSuchElementException.class)
    public void IsEmpty_OneElement()
    {
        m.put(1, "One");
        assertFalse("Should contain 1 element", es.isEmpty());

        HIterator it = es.iterator();
        assertTrue("Sould have next", it.hasNext());

        //Entry assertion
        HMap.Entry e = (HMap.Entry)it.next();
        assertEquals(1, e.getKey());
        assertEquals("One", e.getValue());

        assertFalse("Sould not have next", it.hasNext());
        it.next();  // Exception throw
    }

    // ------------------------------------------ size method ------------------------------------------

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that an empty set
     * should have a size of zero and isEmpty call returning
     * true.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 0 size (empty).</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the set.</p>
     * <p><b>Pre-Condition</b>: The set is empty.</p>
     * <p><b>Post-Condition</b>: The set is still empty.</p>
     * <p><b>Expected Results</b>: The set method returns 0 and the isEmpty
     * method returns true.</p>
     */
    @Test
    public void Size_Empty_0()
    {
        assertEquals("Empty entryset does not have size of zero.", 0, es.size());
        assertTrue("isEmpty did not returned true.", es.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a entryset with 1 element
     * should have a size of 1 and isEmpty call returning
     * false.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 1 size and not being empty. Propagation
     * map -> entryset is tested.</p>
     * <p><b>Pre-Condition</b>: The entryset contains 1.</p>
     * <p><b>Post-Condition</b>: The entryset contains 1.</p>
     * <p><b>Expected Results</b>: The size method returns 1 and the isEmpty
     * method returns false.</p>
     */
    @Test
    public void Size_OneElement_1()
    {
        m.put(1, "1");
        assertEquals("Empty entryset does not have size of one.", 1, es.size());
        assertEquals("isEmpty did not returned false.", false, es.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a entryset with 3 elements
     * should have a size of 3 and isEmpty call returning
     * false.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 3 size and not being empty. Propagation
     * map -> entryset is tested.</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the entryset.</p>
     * <p><b>Pre-Condition</b>: The entryset is empty.</p>
     * <p><b>Post-Condition</b>: The entryset has 3 elements ({0, 1, 2}).</p>
     * <p><b>Expected Results</b>: The size method returns 3 and the isEmpty
     * method returns false.</p>
     */
    @Test
    public void Size_ThreeElements_3()
    {
        TestUtilities.addToHMap(m, 0, 3);
        assertEquals("entryset with 3 elements does not have size of 3.", 3, es.size());
        assertEquals("isEmpty did not returned false.", false, es.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a entryset with 3 elements
     * should have a size of 345 and isEmpty call returning
     * false.
     * The entryset is modiefied before the asserts.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 345 size and not being empty. Propagation
     * map -> entryset is tested.</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the entryset.</p>
     * <p><b>Pre-Condition</b>: The entryset is empty.</p>
     * <p><b>Post-Condition</b>: The entryset contains 345 elements ({0:345}).</p>
     * <p><b>Expected Results</b>: The size method returns 345 and the isEmpty
     * method returns false.</p>
     */
    @Test
    public void Size_345Elements_345()
    {
        TestUtilities.addToHMap(m, 0, 345);
        assertEquals("entryset with 345 elements does not have size of 345.", 345, es.size());
        assertEquals("isEmpty did not returned false.", false, es.isEmpty());
    }

    // ------------------------------------------ contains method ------------------------------------------

    /**
     * <p><b>Summary</b>: contains method test case</p>
     * <p><b>Test Case Design</b>: Tests the limit case of
     * a contains method invoke on a empty entryset, which
     * is a limit case.</p>
     * <p><b>Test Description</b>: contains is invoked with
     * a random object, es is empty, therefore it should not
     * contain it.</p>
     * <p><b>Pre-Condition</b>: es is empty.</p>
     * <p><b>Post-Condition</b>: es is empty.</p>
     * <p><b>Expected Results</b>: cotnains returns false</p>
     */
    @Test
    public void Contains_Empty()
    {
        assertFalse("Should not contain", es.contains("Random object"));
    }

    
    /**
     * <p><b>Summary</b>: contains method test case</p>
     * <p><b>Test Case Design</b>: Tests if each entry of type
     * (i, "i") is contained in the entrySet, for i in (0,1000).</p>
     * <p><b>Test Description</b>: contains is invoked 1000 times
     * in a for loop. At each iteration a contained entry is generated
     * and checked to be in the entrySet.</p>
     * <p><b>Pre-Condition</b>: es contains {0="0":1000="1000"}.</p>
     * <p><b>Post-Condition</b>: es is unchanged.</p>
     * <p><b>Expected Results</b>: each contains returns true</p>
     */
    @Test
    public void Contains_0To1000()
    {
        int bound = 1000;
        TestUtilities.addToHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
        {
            HMap.Entry e = TestUtilities.getEntry(i, "" + i);
            assertTrue("Should be contained", es.contains(e));
        }
    }

    /**
     * <p><b>Summary</b>: contains method test case</p>
     * <p><b>Test Case Design</b>: Tests if each entry of type
     * (i, "i + 1") is contained in the entrySet, for i in (0,1000).
     * This is obviusly false for each i, as es contains {0="0":1000="1000"}.</p>
     * <p><b>Test Description</b>: contains is invoked 1000 times
     * in a for loop. At each iteration a contained entry is generated
     * and checked to be in the entrySet.</p>
     * <p><b>Pre-Condition</b>: es contains {0="0":1000="1000"}.</p>
     * <p><b>Post-Condition</b>: es is unchanged.</p>
     * <p><b>Expected Results</b>: each contains returns false</p>
     */
    @Test
    public void Contains_0To1000_DifferentKey()
    {
        int bound = 1000;
        TestUtilities.addToHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
        {
            HMap.Entry e = TestUtilities.getEntry(i, "" + i + 1);   // value does NOT match
            assertFalse("Should NOT be contained", es.contains(e));
        }
    }

    /**
     * <p><b>Summary</b>: contains method test case</p>
     * <p><b>Test Case Design</b>: Tests if each entry of type
     * (i + 1, "i") is contained in the entrySet, for i in (0,1000).
     * This is obviusly false for each i, as es contains {0="0":1000="1000"}.</p>
     * <p><b>Test Description</b>: contains is invoked 1000 times
     * in a for loop. At each iteration a contained entry is generated
     * and checked to be in the entrySet.</p>
     * <p><b>Pre-Condition</b>: es contains {0="0":1000="1000"}.</p>
     * <p><b>Post-Condition</b>: es is unchanged.</p>
     * <p><b>Expected Results</b>: each contains returns false</p>
     */
    @Test
    public void Contains_0To1000_DifferentValue()
    {
        int bound = 1000;
        TestUtilities.addToHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
        {
            HMap.Entry e = TestUtilities.getEntry(i + 1, "" + i);   // value does NOT match
            assertFalse("Should NOT be contained", es.contains(e));
        }
    }

        /**
     * <p><b>Summary</b>: containsAll method test case.
     * The method tests if an empty entryset contains the elements
     * of another collection, which is obviusly false.</p>
     * <p><b>Test Case Design</b>: The test case tests the limit case of
     * checking an empty entryset containing something.</p>
     * <p><b>Test Description</b>: The collection contains 1, 2 and 3.
     * The containsAll method obviously should return false for
     * any coll's content as the entryset is empty.</p>
     * <p><b>Pre-Condition</b>: The entryset is empty.</p>
     * <p><b>Post-Condition</b>: The entryset is still empty.</p>
     * <p><b>Expected Results</b>: The containsAll method return false.</p>
     */
    @Test
    public void ContainsAll_Empty_False()
    {
        c = TestUtilities.getEntryCollection(1, 4);
        assertEquals("The method should return false because the entryset is empty.", false, es.containsAll(c)); 
    }

   /**
     * <p><b>Summary</b>: containsAll method test case.
     * The method tests if an empty entryset contains the elements
     * of another collection.</p>
     * <p><b>Test Case Design</b>: The test case tests the limit case of
     * checking an empty entryset containing an empty collection, which is true, 
     * as the empty subset is the subset of every set, therefore even of the
     * empty set. The tested case is a limit case of containsAll.</p>
     * <p><b>Test Description</b>: The collection is empty.
     * The containsAll method obviously should return true for
     * any coll's content, because the empty subset is the
     * subset of every set.</p>
     * <p><b>Pre-Condition</b>: The entryset is empty.</p>
     * <p><b>Post-Condition</b>: The entryset is empty.</p>
     * <p><b>Expected Results</b>: The containsAll method return true.</p>
     */
    @Test
    public void ContainsAll_BothEmpty_False()
    {
        assertEquals("The method should return true because the collection is empty.", true, es.containsAll(c)); 
    }
    
    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The test case tests different containsAll calls
     * with different collection.</p>
     * <p><b>Test Case Design</b>: The tests calls containsAll
     * with some as argument.</p>
     * <p><b>Test Description</b>: In the test containsAll is called with the
     * collection containing
     * {1="1"}, {10="10"}, {3="3", 4="4", 5="5"}, {1="1", 5="5", 10="10"}.</p>
     * <p><b>Pre-Condition</b>: The entryset contains {0="0":10="10"}.</p>
     * <p><b>Post-Condition</b>: The entryset contains {0="0":10="10"}.</p>
     * <p><b>Expected Results</b>: Every containsAll calls return true.</p>
     */
    @Test
    public void ContainsAll_1to11_True()
    {
        TestUtilities.addToHMap(m, 0, 11);

        // {1="1"}
        c.add(TestUtilities.getEntry(1, "1"));
        assertEquals("The entryset contains 1=1.", true, es.containsAll(c));
        
        // {10="10"}
        c = new CollectionAdapter();
        c.add(TestUtilities.getEntry(10, "10"));
        assertEquals("The entryset contains 10=10.", true, es.containsAll(c));

        // {3="3", 4="4", 5="5"}
        c = new CollectionAdapter();
        TestUtilities.initHCollection(c, 3, 6);
        assertEquals("The entryset contains 3=3, 4=4 and 5=5.", true, es.containsAll(c));

        // {1, 5, 10}
        c = new CollectionAdapter();
        c.add(TestUtilities.getEntry(1, "1"));
        c.add(TestUtilities.getEntry(5, "5"));
        c.add(TestUtilities.getEntry(10, "10"));
        assertEquals("The entryset contains 1, 5 and 10.", true, es.containsAll(c));
    }

    /**
     * <p><b>Summary</b>: containsAll method test case.
     * Tests if different combination are contained in the entryset.
     * Each containsAll call returns false.</p>
     * <p><b>Test Case Design</b>: Different scenario are tested where
     * containsAll should return false. Calling containsAll with arguments
     * not present in the entryset is a common case for the method.</p>
     * <p><b>Test Description</b>: The first containsAll takes as argument
     * a collection containing a single element, not present in the
     * entryset. The second one takes as argument a collection containing element
     * in the entryset e one not contained in the entryset: therefore, the call
     * must return false as one of the collection is not contained.</p>
     * <p><b>Pre-Condition</b>: The entryset contains {0="0":10="10"}.</p>
     * <p><b>Post-Condition</b>: The entryset contains {0="0":10="10"}.</p>
     * <p><b>Expected Results</b>: All containsAll calls return true.</p>
     */
    @Test
    public void ContainsAll_1to11_False()
    {
        addToHMap(m, 0, 11);

        c.add(getEntry(11, "11"));
        assertEquals("11=11 is not contained.", false, es.containsAll(c));

        c = new CollectionAdapter();
        c.add(getEntry(3, "3"));
        c.add(getEntry(4, "4"));
        c.add(getEntry(5, "5"));
        c.add(getEntry(12, "12"));   // Single element not present
        assertFalse("3=3, 4=4 and 5=5 are contained. 12=12 is not contained.", es.containsAll(c));
    }

    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The test case tries to call containsAll with a null
     * collection on an empty entryset.</p>
     * <p><b>Test Case Design</b>: Tests the method with null arguments, which is a special
     * case of invalid argument.</p>
     * <p><b>Test Description</b>: Calls method with null collection.</p>
     * <p><b>Pre-Condition</b>: entryset is empty, coll is null.</p>
     * <p><b>Post-Condition</b>: entryset is empty, coll is null.</p>
     * <p><b>Expected Results</b>: NullPointerException thrown.</p>
     */    
    @Test(expected = java.lang.NullPointerException.class)
    public void ContainsAll_NullCollection_NPE()
    {
        es.containsAll(null);
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