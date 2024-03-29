package myTest;

// Imports
import static org.junit.Assert.*;
import org.junit.*;
import static myTest.TestUtilities.*;
import java.util.NoSuchElementException;
import myAdapter.*;

/**
 * <p><b>Summary</b>: The TestKeySet test suite focuses on the HSet returned from HMap.entrySet()
 * method. Tests the HSet's methods correct
 * behaviour in different case scenario. Each HSet method is tested in different
 * test cases. In each test case it is made sure of the right behaviour of HSet respect
 * to its elements and that changes from the HSet to the backing map and viceversa
 * are propagated correctly, which is an important feature of the HSet returned from
 * entrySet() method. In this field key methods are checkKeySet and checkIteration,
 * which check coherence between the HSet and the backing HMap: if one of these methods
 * fails, that means that something went wrong during propagation of changes, otherwise.
 * changes propagated correctly.
 * Notation used in this test suite:
 * <ul>
 * <li>{x:y} = {x, ..., y} means elements from x (included) to y (excluded).</li>
 * <li>{x, y, z} means 3 elements, which are x, y and z.</li>
 * <li>{a:b, x, c:d} means elements from x (included) to y (excluded), then element
 * x, then elements from c (included) to d (excluded).</li>
 * <li>x=y means an entry, where s is the key, and y is its mapped value.</li>
 * <li>{x="x":y="y"} = {x="x", ..., y="y"} means entries, where the key is an element
 * and the value is its string representation, from x (included) to y (excluded), offently used
 * for map and keySets.</li>
 * </ul>
 * 
 * <p><b>Test Suite Design</b>: The test suite contains fine-grained test cases in order to
 * easily individuate errors in HSet methods and also coarse-grained test cases in order to
 * test different methods interaction. That means that test cases include modification test, where
 * the map structure is modified in different ways, and inspection test, where information are retrieved
 * from the map to see if the informations are stored correctly, and tests where modifications and
 * inspections are combined. In the test suite there are many test cases focusing on limit and special cases,
 * invalid arguments and etc.
 * Special attention is paid to backing: after almost each modification, through the HSet returned
 * from entrySet() or through the backing map, checkKeySet() and checkIteration() are invoked
 * to assert that changes propagated successfully. Note that the afore mentioned HSet cannot
 * contain duplicated, as the backing HMap cannot contain elements with the same key.
 * Even if not every test case is marked as "Backing" in the method name,
 * pretty much all of this test suite's test cases tests the backing feature.</p>
 * @author  Nicola Maritan
 */
public class TestKeySet 
{
    private MapAdapter m, m2;
    private HSet ks, ks2;
    private HCollection c;
    private HIterator it;

    /**
	 * Prints test suite name before running tests.
	 */
    @BeforeClass
    public static void BeforeClassMethod()
    {
        System.out.println(TestKeySet.class.getName() + " running.");
    }

    /**
	 * Method invoke before each test for setup.
	 */
    @Before
    public void Setup()
    {
        m = new MapAdapter();
        m2 = new MapAdapter();
        ks = m.keySet();
        ks2 = m2.keySet();
        c = getEmptyHCollection();
    }

    /**
	 * Prints test suite name after running tests.
	 */
    @AfterClass
    public static void AfterClassMethod()
    {
        System.out.println(TestKeySet.class.getName() + " ended.");
    }

    /**
	 * Method invoke after each test for cleanup.
	 */
    @After
    public void Cleanup()
    {
        m = null;
        m2 = null;
        ks = null;
        ks2 = null;
        c = null;
        it = null;
    }
    
    // ------------------------------------------ single method test case ------------------------------------------

    // ------------------------------------------ add method ------------------------------------------
    
    /**
     * <p><b>Summary</b>: add method test case.</p>
     * <p><b>Test Case Design</b>: The method throws
     * HUnsupportedOperationException.</p>
     * <p><b>Test Description</b>: add is invoked.</p>
     * <p><b>Pre-Condition</b>: ks is empty.</p>
     * <p><b>Post-Condition</b>: ks is empty.</p>
     * <p><b>Expected Results</b>: The add method is not supported.
     * HUnsupportedOperationException is thrown.</p>
     */
    @Test (expected = HUnsupportedOperationException.class)
    public void Add_UOE()
    {
        ks.add(1);
    }

    /**
     * <p><b>Summary</b>: add method test case.</p>
     * <p><b>Test Case Design</b>: The method throws
     * HUnsupportedOperationException.</p>
     * <p><b>Test Description</b>: add is invoked.</p>
     * <p><b>Pre-Condition</b>: ks contains {0:10}.</p>
     * <p><b>Post-Condition</b>: ks contains {0:10}.</p>
     * <p><b>Expected Results</b>: The add method is not supported.
     * HUnsupportedOperationException is thrown.</p>
     */
    @Test (expected = HUnsupportedOperationException.class)
    public void Add_10UOE()
    {
        initHMap(m, 0, 10);
        ks.add(1);
    }

    // ------------------------------------------ addAll method ------------------------------------------

    /**
     * <p><b>Summary</b>: addAll method test case.</p>
     * <p><b>Test Case Design</b>: The method throws
     * HUnsupportedOperationException.</p>
     * <p><b>Test Description</b>: addAll is invoked.</p>
     * <p><b>Pre-Condition</b>: ks is empty.</p>
     * <p><b>Post-Condition</b>: ks is empty.</p>
     * <p><b>Expected Results</b>: The add method is not supported.
     * HUnsupportedOperationException is thrown.</p>
     */
    @Test (expected = HUnsupportedOperationException.class)
    public void AddAll_UOE()
    {
        ks.addAll(c);
    }

    /**
     * <p><b>Summary</b>: addAll method test case.</p>
     * <p><b>Test Case Design</b>: The method throws
     * HUnsupportedOperationException.</p>
     * <p><b>Test Description</b>: add is invoked.</p>
     * <p><b>Pre-Condition</b>: ks contains {0:10}.</p>
     * <p><b>Post-Condition</b>: ks contains {0:10}.</p>
     * <p><b>Expected Results</b>: The add method is not supported.
     * HUnsupportedOperationException is thrown.</p>
     */
    @Test (expected = HUnsupportedOperationException.class)
    public void AddAll_10UOE()
    {
        initHMap(m, 0, 10);
        ks.addAll(c);
    }

	// ------------------------------------------ isEmpty method ------------------------------------------

    /**
     * <p><b>Summary</b>: isEmpty method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case
     * of an empty KeySet, created from an empty
     * Map.</p>
     * <p><b>Test Description</b>: ks is empty, then size,
     * isEmpty and its iterators have been tested.</p>
     * <p><b>Pre-Condition</b>: ks is empty.</p>
     * <p><b>Post-Condition</b>: ks is unchanged.</p>
     * <p><b>Expected Results</b>: size is 0, isEmpty returns
     * true, iterator.hasNext is false and next throws NSEE</p>
     */
    @Test (expected = NoSuchElementException.class)
    public void IsEmpty_EmptyMapEmptyES()
    {
        assertEquals("Size should be 0", 0, ks.size());
        assertTrue("Should be empty", ks.isEmpty());

        HIterator it = ks.iterator();
        assertFalse("Sould not have next", it.hasNext());
        it.next();  // Exception throw
    }

    /**
     * <p><b>Summary</b>: isEmpty method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case
     * of an KeySet containing only 1 element. Propagation
     * map -{@literal >} KeySet is tested.</p>
     * <p><b>Test Description</b>: ks contains {1}, while map
     * contains {1="One"}, then size,
     * isEmpty and its iterators have been tested.</p>
     * <p><b>Pre-Condition</b>: ks contains {1} map
     * contains {1="One"}.</p>
     * <p><b>Post-Condition</b>: ks is unchanged, m is unchanged.</p>
     * <p><b>Expected Results</b>: isEmpty returns
     * false, iterator.hasNext is true and second next throws NSEE.
     * The only element in keySet is 1, map
     * contains {1="One"}. Getting the only value from map get
     * through the obtained key in the keyset should return
     * "One".</p>
     */
    @Test (expected = NoSuchElementException.class)
    public void IsEmpty_OneElement()
    {
        m.put(1, "One");
        assertFalse("Should contain 1 element", ks.isEmpty());

        HIterator it = ks.iterator();
        assertTrue("Sould have next", it.hasNext());

        Object k = it.next();
        assertEquals("One", m.get(k));

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
    public void Size_Empty()
    {
        assertEquals("Empty KeySet does not have size of zero.", 0, ks.size());
        assertTrue("Should be empty.", ks.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a KeySet with 1 element
     * should have a size of 1 and isEmpty call returning
     * false.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 1 size and not being empty. Propagation
     * map -{@literal >} KeySet is tested, as the keySet is modified
     * through m.put(1, "1"). checkKeySet and checkIteration
     * are invoked to test KeySet - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Pre-Condition</b>: The KeySet contains 1, map contains {1="1"}.</p>
     * <p><b>Post-Condition</b>: The KeySet contains 1 map contains {1="1"}.</p>
     * <p><b>Expected Results</b>: The size method returns 1 as the
     * keyset contains only one element and the isEmpty
     * method returns false. checkKeySet and checkIteration tests pass.</p>
     */
    @Test
    public void Size_OneElement()
    {
        m.put(1, "1");
        checkKeySet(m, ks);
        checkIteration(ks);
        assertEquals("Empty KeySet does not have size of one.", 1, ks.size());
        assertFalse("Should NOT be empty.", ks.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a KeySet with 3 elements
     * should have a size of 3 and isEmpty call returning
     * false.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 3 size and not being empty. Propagation
     * map -{@literal >} KeySet is tested,  as the keySet is modified
     * through addToHMap(m, 0, 3). checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the KeySet.</p>
     * <p><b>Pre-Condition</b>: The KeySet is empty.</p>
     * <p><b>Post-Condition</b>: The KeySet has 3 elements ({0, 1, 2}).</p>
     * <p><b>Expected Results</b>: The size method returns 3
     * as the keyset contains 3 elements and the isEmpty
     * method returns false. checkKeySet and checkIteration tests pass.</p>
     */
    @Test
    public void Size_ThreeElements()
    {
        initHMap(m, 0, 3);
        checkKeySet(m, ks);
        checkIteration(ks);
        assertEquals("KeySet with 3 elements does not have size of 3.", 3, ks.size());
        assertFalse("Should NOT be empty.", ks.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a KeySet with 3 elements
     * should have a size of 345 and isEmpty call returning
     * false.
     * The KeySet is modified before the asserts.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 345 size and not being empty. Propagation
     * map -{@literal >} KeySet is tested,  as the keySet is modified
     * through addToHMap(m, 0, 345). checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the KeySet.</p>
     * <p><b>Pre-Condition</b>: The KeySet is empty.</p>
     * <p><b>Post-Condition</b>: The KeySet contains 345 elements ({0:345}).</p>
     * <p><b>Expected Results</b>: The size method returns 345 and the isEmpty
     * method returns false. checkKeySet and checkIteration tests pass.</p>
     */
    @Test
    public void Size_345Elements()
    {
        initHMap(m, 0, 345);
        checkKeySet(m, ks);
        checkIteration(ks);
        assertEquals("KeySet with 345 elements does not have size of 345.", 345, ks.size());
        assertFalse("Should NOT be empty.", ks.isEmpty());
    }

    // ------------------------------------------ contains method ------------------------------------------

    /**
     * <p><b>Summary</b>: contains method test case</p>
     * <p><b>Test Case Design</b>: Tests the limit case of
     * a contains method invoke on a empty KeySet, which
     * is a limit case.</p>
     * <p><b>Test Description</b>: contains is invoked with
     * a random object, ks is empty, therefore it should not
     * contain it.</p>
     * <p><b>Pre-Condition</b>: ks is empty.</p>
     * <p><b>Post-Condition</b>: ks is empty.</p>
     * <p><b>Expected Results</b>: cotnains returns false, as the
     * object string "Random object" is not containde in the
     * keyset.</p>
     */
    @Test
    public void Contains_Empty()
    {
        assertFalse("Should not contain", ks.contains("Random object"));
    }

    
    /**
     * <p><b>Summary</b>: contains method test case</p>
     * <p><b>Test Case Design</b>: Tests if each entry of type
     * (i, "i") is contained in the KeySet, for i in (0,500).
     * Propagation map -{@literal >} KeySet is tested.
     * checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.</p>
     * <p><b>Test Description</b>: contains is invoked 500 times
     * in a for loop. At each iteration a contained entry is generated
     * and checked to be in the KeySet.</p>
     * <p><b>Pre-Condition</b>: ks contains {0:500}, m
     * contains {0="0":500="500"}.</p>
     * <p><b>Post-Condition</b>: ks is unchanged.
     * m is unchanged.</p>
     * <p><b>Expected Results</b>: each contains returns true</p>
     */
    @Test
    public void Contains_0To500()
    {
        int bound = 500;
        addToHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
            assertTrue("Should be contained", ks.contains(i));
    }

    /**
     * <p><b>Summary</b>: contains method test case</p>
     * <p><b>Test Case Design</b>: Tests if the key
     * i + 500 is contained in the KeySet, for i in (0,500).
     * This is obviusly false for each i, as ks contains {0:500}.
     * checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.</p>
     * <p><b>Test Description</b>: contains is invoked 500 times
     * in a for loop. At each iteration a contained entry is generated
     * and checked to be in the KeySet.
     * Tests propagation.</p>
     * <p><b>Pre-Condition</b>: ks contains {0:500}, m
     * contains {0="0":500="500"}.</p>
     * <p><b>Post-Condition</b>: ks is unchanged.
     * m is unchanged.</p>
     * <p><b>Expected Results</b>: each contains returns false</p>
     */
    @Test
    public void Contains_0To500_DifferentKey()
    {
        int bound = 500;
        addToHMap(m, 0, bound);
        checkKeySet(m, ks);
        checkIteration(ks);
        for (int i = 0; i < bound; i++)
            assertFalse("Should NOT be contained", ks.contains(i + bound));
    }

    /**
     * <p><b>Summary</b>: contains method test case.</p>
     * <p><b>Test Case Design</b>: Tests remove behaviour
     * when the passed argument is null. The method should throw
     * NullPointerException.</p>
     * <p><b>Test Description</b>: ks.contains(null) is invoked.</p>
     * <p><b>Pre-Condition</b>: ks contains {0:10}.</p>
     * <p><b>Post-Condition</b>: ks contains {0:10}.</p>
     * <p><b>Expected Results</b>: NPE is thrown.</p>
     */
    @Test(expected = NullPointerException.class)
    public void Contains_Null()
    {
        initHMap(m, 0, 10);
        ks.contains(null);
    }

    // ------------------------------------------ containsAll method ------------------------------------------

    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The method tests if an empty KeySet contains the elements
     * of another collection, which is obviusly false.</p>
     * <p><b>Test Case Design</b>: The test case tests the limit case of
     * checking an empty KeySet containing something.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: The collection contains 1, 2 and 3.
     * The containsAll method obviously should return false for
     * any coll's content as the KeySet is empty.</p>
     * <p><b>Pre-Condition</b>: The KeySet and map are empty.</p>
     * <p><b>Post-Condition</b>: The KeySet and map are empty.</p>
     * <p><b>Expected Results</b>: The containsAll method return false.</p>
     */
    @Test
    public void ContainsAll_Empty()
    {
        c = getIntegerHCollection(1, 4);
        assertFalse("The method should return false because the KeySet is empty.", ks.containsAll(c)); 
    }

   /**
     * <p><b>Summary</b>: containsAll method test case.
     * The method tests if an empty KeySet contains the elements
     * of another collection.</p>
     * <p><b>Test Case Design</b>: The test case tests the limit case of
     * checking an empty KeySet containing an empty collection, which is true, 
     * as the empty subset is the subset of every set, therefore even of the
     * empty set. The tested case is a limit case of containsAll.</p>
     * <p><b>Test Description</b>: The collection is empty.
     * The containsAll method obviously should return true for
     * any coll's content, because the empty subset is the
     * subset of every set.</p>
     * <p><b>Pre-Condition</b>: The KeySet is empty.</p>
     * <p><b>Post-Condition</b>: The KeySet is empty.</p>
     * <p><b>Expected Results</b>: The containsAll method return true.</p>
     */
    @Test
    public void ContainsAll_BothEmpty()
    {
        c = getEmptyHCollection();
        assertEquals("The method should return true because the collection is empty.", true, ks.containsAll(c)); 
    }

   /**
     * <p><b>Summary</b>: containsAll method test case.
     * The method tests if an non-empty keyset contains the elements
     * of another collection.</p>
     * <p><b>Test Case Design</b>: The test case tests the limit case of
     * checking an keyset containing an empty collection, which is true, 
     * as the empty subset is the subset of every set.
     * The tested case is a limit case of containsAll.</p>
     * <p><b>Test Description</b>: The collection is empty.
     * The containsAll method obviously should return true for
     * any coll's content, because the empty subset is the
     * subset of every set.</p>
     * <p><b>Pre-Condition</b>: The keyset contains 10 elements.</p>
     * <p><b>Post-Condition</b>: The keyset contains 10 elements.</p>
     * <p><b>Expected Results</b>: The containsAll(c) method return true.</p>
     */
    @Test
    public void ContainsAll_EmptyCollection()
    {
        initHMap(m, 0, 10);
        assertTrue("The method should return true because the collection is empty.", ks.containsAll(c)); 
    }
    
    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The test case tests different containsAll calls
     * with different collection.</p>
     * <p><b>Test Case Design</b>: The tests calls containsAll
     * with some as argument. Propagation map -{@literal >} KeySet is tested.</p>
     * <p><b>Test Description</b>: In the test containsAll is called with the
     * collection containing
     * {1}, {10}, {3, 4, 5}, {1, 5, 10}.</p>
     * <p><b>Pre-Condition</b>: The KeySet contains {0:11}, the map
     * contains {0="0":11="11"}.</p>
     * <p><b>Post-Condition</b>: The KeySet contains {0:11}, the map
     * contains {0="0":11="11"}.</p>
     * <p><b>Expected Results</b>: Every containsAll calls return true.</p>
     */
    @Test
    public void ContainsAll_0to11_True()
    {
        addToHMap(m, 0, 11);

        // {1 as key}
        c = getHCollection(new Object[]{1});
        assertTrue("The KeySet contains 1=1.", ks.containsAll(c));
        
        // {10 as key}
        c = getHCollection(new Object[]{10});
        assertTrue("The KeySet contains 10=10.", ks.containsAll(c));

        // {3, 4, 5 as keys}
        c = getIntegerHCollection(3, 6);
        assertTrue("The KeySet contains 3=3, 4=4 and 5=5.", ks.containsAll(c));

        // {1, 5, 10 as keys}
        c = getHCollection(new Object[]{1, 5, 10});
        assertTrue("The KeySet contains 1, 5 and 10.", ks.containsAll(c));
    }

    /**
     * <p><b>Summary</b>: containsAll method test case.
     * Tests if different combination are contained in the KeySet.
     * Each containsAll call returns false.</p>
     * <p><b>Test Case Design</b>: Different scenario are tested where
     * containsAll should return false. Calling containsAll with arguments
     * not present in the KeySet is a common case for the method. Propagation
     * map -{@literal >} KeySet is tested. </p>
     * <p><b>Test Description</b>: The first containsAll takes as argument
     * a collection containing a single element, not present in the
     * KeySet. The second one takes as argument a collection containing element
     * in the KeySet e one not contained in the KeySet: therefore, the call
     * must return false as one of the collection is not contained.</p>
     * <p><b>Pre-Condition</b>: The KeySet contains {0:11}, the map
     * contains {0="0":11="11"}.</p>
     * <p><b>Post-Condition</b>: The KeySet contains {0:11}, the map
     * contains {0="0":11="11"}.</p>
     * <p><b>Expected Results</b>: All containsAll calls return true,
     * last containsAll returns false as 12 is not contained
     * in the keyset.</p>
     */
    @Test
    public void ContainsAll_0to11_False()
    {
        addToHMap(m, 0, 11);

        c = getHCollection(new Object[]{11});
        assertFalse("11=11 is not contained.", ks.containsAll(c));

        c = getHCollection(new Object[]{3, 4, 5, 12});
        assertFalse("3, 4 and 5 are contained. 12 is not contained.", ks.containsAll(c));
    }

    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The test case tries to call containsAll with a null
     * collection on an empty KeySet.</p>
     * <p><b>Test Case Design</b>: Tests the method with null arguments, which is a special
     * case of invalid argument.</p>
     * <p><b>Test Description</b>: Calls method with null collection.</p>
     * <p><b>Pre-Condition</b>: KeySet is empty, coll is null.</p>
     * <p><b>Post-Condition</b>: KeySet is empty, coll is null.</p>
     * <p><b>Expected Results</b>: NullPointerException thrown.</p>
     */    
    @Test(expected = java.lang.NullPointerException.class)
    public void ContainsAll_NullCollection_NPE()
    {
        ks.containsAll(null);
    }

    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The test case tries to call containsAll with a null
     * collection on an keyset.</p>
     * <p><b>Test Case Design</b>: Tests the method with null arguments, which is a special
     * case of invalid argument.</p>
     * <p><b>Test Description</b>: Calls method with null collection.</p>
     * <p><b>Pre-Condition</b>: keyset contains {0:10}, coll is null.</p>
     * <p><b>Post-Condition</b>: keyset contains {0:10}, coll is null.</p>
     * <p><b>Expected Results</b>: NullPointerException thrown.</p>
     */    
    @Test(expected = java.lang.NullPointerException.class)
    public void ContainsAll_NullCollection_NPE_NotEmpty()
    {
        initHMap(m, 0, 10);
        ks.containsAll(null);
    }
    
    // ------------------------------------------ equals method ------------------------------------------

    /**
     * <p><b>Summary</b>: equals method test case.</p>
     * <p><b>Test Case Design</b>: equals method is tested with an equal 
     * and a bigger and a smaller one. The returned values should be true and false.
     * Also reflective property of equal is tested.</p>
     * <p><b>Test Description</b>: KeySet is initialized, then different equals invoke are
     * asserted with different arguments, generated for each case.</p>
     * <p><b>Pre-Condition</b>: KeySet contains {0="0" : 400="400"}.</p>
     * <p><b>Post-Condition</b>: KeySet is unchanged.</p>
     * <p><b>Expected Results</b>: The KeySet is unchanged and symmetric property is valid.</p>
     */
    @Test
    public void Equals_0To400()
    {
        int to = 400;
        addToHMap(m, 0, to);
        assertTrue("Should equal", ks.equals(getIntegerMapAdapter(0, to).keySet()));
        assertTrue("Should equal", getIntegerMapAdapter(0, to).keySet().equals(ks));   // Symmetric property
        assertFalse("Should NOT equal", ks.equals(getIntegerMapAdapter(0, to + 15).keySet()));  // bigger KeySet returns false
        assertFalse("Should NOT equal", ks.equals(getIntegerMapAdapter(0, 5).keySet()));  // smaller KeySet returns false
    }

    
    /**
     * <p><b>Summary</b>: equals method test case.
     * The test case the method behaviour with 2 empty KeySet.</p>
     * <p><b>Test Case Design</b>: When both KeySets are empty the equals
     * method should return true because an empty KeySet is equal to an
     * empty KeySet.</p>
     * <p><b>Test Description</b>: Single assert, l1.equals(l2) invoked.</p>
     * <p><b>Pre-Condition</b>: Both KeySet are empty.</p>
     * <p><b>Post-Condition</b>: Both KeySet are empty.</p>
     * <p><b>Expected Results</b>: equals returns true, as one
     * empty KeySet of course equals another empty KeySet.</p>
     */
    @Test
    public void Equals_Empty_True()
    {
        assertTrue("Two empty KeySets should equals.", ks.equals(ks2));
        assertTrue("Two empty KeySets should equals.", ks2.equals(ks));
    }

    /**
     * <p><b>Summary</b>: equals method test case.
     * The reflective property of equal method is tested.</p>
     * <p><b>Test Case Design</b>: equals method should be reflective,
     * therefore x.equals(x) should always return true. Propagation
     * map -{@literal >} KeySet is tested through initHMap.</p>
     * <p><b>Test Description</b>: The test invokes ks.equals(ks) when
     * ks is empty, when it has 10 elements and when it has 500 elements.</p>
     * <p><b>Pre-Condition</b>: KeySet is not null.</p>
     * <p><b>Post-Condition</b>: KeySet has 500 elements. </p>
     * <p><b>Expected Results</b>: KeySet equals itself, therefore
     * reflective property is valid.</p>
     */
    @Test
    public void Equals_Reflective()
    {
        assertTrue("Reflective property is NOT met.", ks.equals(ks));    // KeySet is empty
        initHMap(m, 0, 10);
        assertTrue("Reflective property is NOT met.", ks.equals(ks));    // KeySet is not empty, should return true anyways
        initHMap(m, 0, 500);
        assertTrue("Reflective property is NOT met.", ks.equals(ks));    // KeySet is not empty, should return true anyways
    }

    /**
     * <p><b>Summary</b>: equals method test case.
     * The transitive property of equal method is tested.</p>
     * <p><b>Test Case Design</b>: equals method should be transitive,
     * therefore a.equals(b) and b.equals(c) {@literal =>} a.equals(c).
     * Propagation map -{@literal >} KeySet is tested.</p>
     * <p><b>Test Description</b>: The test invokes ks.equals(ks2) and ks2.equals(KeySet3)
     * and ks.equals(ks3)</p>
     * <p><b>Pre-Condition</b>: KeySets contain {1 : 1000}.</p>
     * <p><b>Post-Condition</b>: KeySets are unchanged. </p>
     * <p><b>Expected Results</b>: Equals has transitive property.</p>
     */
    @Test
    public void Equals_Transitive()
    {
        int to = 1000;
        addToHMap(m, 0, to);
        addToHMap(m2, 0, to);
        HSet ks3 = getIntegerMapAdapter(0, to).keySet();

        assertTrue("KeySets should be equal.", ks.equals(ks2));
        assertTrue("KeySets should be equal.", ks2.equals(ks3));
        assertTrue("Transitive property is NOT met.", ks.equals(ks3));
    }

    /**
     * <p><b>Summary</b>: equals method test case.</p>
     * <p><b>Test Case Design</b>: equals method, when
     * invoked with a null argument, should just
     * return false.</p>
     * <p><b>Test Description</b>: ks.equals(null)
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: ks is empty</p>
     * <p><b>Post-Condition</b>: ks is empty</p>
     * <p><b>Expected Results</b>: ks.equals(null) returns false.</p>
     */
    @Test
    public void Equals_NullEmpty()
    {
        assertFalse(ks.equals(null));
    }
	
    /**
     * <p><b>Summary</b>: equals method test case.</p>
     * <p><b>Test Case Design</b>: equals method, when
     * invoked with a null argument, should just
     * return false.</p>
     * <p><b>Test Description</b>: ks.equals(null)
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: ks contains {0:10}.</p>
     * <p><b>Post-Condition</b>: ks contains {0:10}.</p>
     * <p><b>Expected Results</b>: ks.equals(null) returns false.</p>
     */
    @Test
    public void Equals_NullNotEmpty()
    {
        initHMap(m, 0, 10);
        assertFalse(m.equals(null));
    }

    // ------------------------------------------ clear method ------------------------------------------

    /**
     * <p><b>Summary</b>: clear, containsKey, containsValue, get method test case.</p>
     * <p><b>Test Case Design</b>: Tests the behaviour of clear method of KeySet
     * and of map. Tests the backing map -{@literal >} KeySet and viceversa, KeySet -{@literal >} map.
     * checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: map is initialized with {0="0" : 500="500"},
     * therefore ks contains {0:500}.
     * Through ks iterators iterates through the elements to assert that they both
     * share the same informations about the map entries. Then clear is invoked
     * by the KeySet. Same initialization and iteration are done,
     * but this time clear is invoked by m.</p>
     * <p><b>Pre-Condition</b>: m and ks are empty.</p>
     * <p><b>Post-Condition</b>: m and ks are still empty.</p>
     * <p><b>Expected Results</b>: clear propagates successfully from map to KeySet
     * and viceversa.</p>
     */
    @Test
    public void Clear_Backing()
    {
        addToHMap(m, 0, 500);
        assertEquals("Size should be 500", 500, m.size());
        assertEquals(m.size(), ks.size());
        checkKeySet(m, ks);
        checkIteration(ks);
        ks.clear();
        checkKeySet(m, ks);
        checkIteration(ks);
        assertTrue("Should both have size 0", (m.size() == ks.size()) && ks.size() == 0);
        assertTrue("Should be both empty", m.isEmpty() && ks.isEmpty());

        addToHMap(m, 0, 500);
        assertEquals("Size should be 500", 500, m.size());
        checkKeySet(m, ks);
        checkIteration(ks);
        m.clear();  // Invoked from m this time
        checkKeySet(m, ks);
        checkIteration(ks);
        assertTrue("Should both have size 0", (m.size() == ks.size()) && ks.size() == 0);
        assertTrue("Should be both empty", m.isEmpty() && ks.isEmpty());
    }

    /**
     * <p><b>Summary</b>: clear method test case.</p>
     * <p><b>Test Case Design</b>: Tests the behaviour of clear method of KeySet
     * and map when they both are empty, which is a limit case (obviusly the limit case is that
     * they are empty, not that they have the same size, as it is trivial).
     * checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keyset - map coherence and the iteration. Tests propagation.</p>
     * <p><b>Test Description</b>: clear is invoked by m and and they are checked through checkKeySet,
     * and checkIteration.
     * Same then but clear is invoked by the KeySet, and they are checked through checkKeySet,
     * and checkIteration.</p>
     * <p><b>Pre-Condition</b>: m and ks are empty</p>
     * <p><b>Post-Condition</b>: m and ks are empty</p>
     * <p><b>Expected Results</b>: clear propagates successfully from map to KeySet
     * and viceversa.</p>
     */
    @Test
    public void Clear_Empty()
    {
        m.clear();
        checkKeySet(m, ks);
        checkIteration(ks);
        ks.clear();
        checkKeySet(m, ks);
        checkIteration(ks);
    }
    
    // ------------------------------------------ hashCode method ------------------------------------------

    /**
     * <p><b>Summary</b>: hashCode test case.
     * Tests the behaviour of hashCode method with different
     * map and KeySet configurations.</p>
     * <p><b>Test Case Design</b>: The same operations are applied to map 1 and 2,
     * so their KeySets must have the same elements each time, therefore they are equals.
     * If they are equals they must have the same hashCode. Changes propagates successfully from map to KeySet
     * and viceversa, tests therefore the consinstency of equals and hashCode.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: Different configurations have been tested. In mathematical symbols, considering the keys:
     * empty, {1}, {-100:100}, {-100:100}/{0}, {-100:101}/{0}, {-100:100, 500:1000}/{0},
     * {-100:100, 500:1000} U {-1000:-900}/({0} ), {-100:100, 500:1000, 5000:6000}  U {-1000:-900}/({0})</p>
     * <p><b>Pre-Condition</b>: KeySets have same hashCode and they are equal.</p>
     * <p><b>Post-Condition</b>: KeySets have same hashCode and they are equal.</p>
     * <p><b>Expected Results</b>: KeySets have same hashCode and they are equal.</p>
     */
    @Test
    public void HashCode_Mixed()
    {
        // Empty map case
        assertTrue("Maps should be equal.", ks.equals(ks2));
        assertEquals("Hash codes should be equal.", ks.hashCode(), ks2.hashCode());

        // One element case
        m.put(1, "1");
        m2.put(1, "1");
        assertTrue("Maps should be equal.", ks.equals(ks2));
        assertEquals("Hash codes should be equal.", ks.hashCode(), ks2.hashCode());

        initHMap(m, -100, 100);
        initHMap(m2, -100, 100);
        assertTrue("Maps should be equal.", ks.equals(ks2));
        assertEquals("Hash codes should be equal.", ks.hashCode(), ks2.hashCode());

        ks.remove(0);
        ks2.remove(0);
        assertTrue("Maps should be equal.", ks.equals(ks2));
        assertEquals("Hash codes should be equal.", ks.hashCode(), ks2.hashCode());

        m.put(100, "100");
        m2.put(100, "100");
        assertTrue("Maps should be equal.", ks.equals(ks2));
        assertEquals("Hash codes should be equal.", ks.hashCode(), ks2.hashCode());

        addToHMap(m, 500, 1000);
        addToHMap(m2, 500, 1000);
        assertTrue("Maps should be equal.", ks.equals(ks2));
        assertEquals("Hash codes should be equal.", ks.hashCode(), ks2.hashCode());

        HMap t = getIntegerMapAdapter(-1000, -900);
        m.putAll(t);
        m2.putAll(t);
        assertTrue("Maps should be equal.", ks.equals(ks2));
        assertEquals("Hash codes should be equal.", ks.hashCode(), ks2.hashCode());

        initHMap(t, 5000, 6000);
        m.putAll(t);
        m2.putAll(t);
        assertTrue("Maps should be equal.", ks.equals(ks2));
        assertEquals("Hash codes should be equal.", ks.hashCode(), ks2.hashCode());
    }

    // ------------------------------------------ remove method ------------------------------------------
    /**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests remove behaviour
     * when the passed argument is null. The method should throw
     * NullPointerException.</p>
     * <p><b>Test Description</b>: ks.remove(null) is invoked.</p>
     * <p><b>Pre-Condition</b>: ks contains {0:10}.</p>
     * <p><b>Post-Condition</b>: ks contains {0:10}.</p>
     * <p><b>Expected Results</b>: NPE is thrown.</p>
     */
    @Test(expected = NullPointerException.class)
    public void Remove_Null()
    {
        initHMap(m, 0, 10);
        ks.remove(null);
    }
    
    /**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests remove method invoked by map
     * and its KeySet and checks their consistency in propagation.
     * They both are empty, which is a limit case. checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: remove is invoked by map, m and set
     * are checked, remove is invoked by KeySet and m and set are checked again.
     * Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     * <p><b>Pre-Condition</b>: m and ks are empty.</p>
     * <p><b>Post-Condition</b>: m and ks are unchanged.</p>
     * <p><b>Expected Results</b>: m.remove returns null object,
     * while ks.remove returns false. map and ks pass the check
     * after each remove invoke.</p>
     */
    @Test
    public void Remove_Empty()
    {
        assertNull(m.remove("Random Key"));
        checkKeySet(m, ks);
        checkIteration(ks);
        assertFalse(ks.remove("Random Object"));
        checkKeySet(m, ks);
        checkIteration(ks);
    }

    /**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests remove method invoked by map
     * and its KeySet and checks their consistency in propagation.
     * Map contains 10 elements, and arguments are keys/entries not
     * in the map/set. checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: remove is invoked by map, m and set
     * are checked, remove is invoked by KeySet and m and set are checked again.
     * Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0", 10="10"},
     * therefore ks contains {0:10}.</p>
     * <p><b>Post-Condition</b>: m and ks are unchanged.</p>
     * <p><b>Expected Results</b>: m.remove returns null object,
     * while ks.remove returns false. map and ks pass the check
     * after each remove invoke.</p>
     */
    @Test
    public void Remove_NotInMap()
    {
        addToHMap(m, 0, 10);
        assertNull(m.remove("Not in map Key"));
        checkKeySet(m, ks);
        checkIteration(ks);
        assertFalse(ks.remove("Not in KeySet Entry"));
        checkKeySet(m, ks);
        checkIteration(ks);
    }

    /**
     * <p><b>Summary</b>: remove method test case.
     * remove is tested to make changes from map and
     * from set, propagating removals from one to another.</p>
     * <p><b>Test Case Design</b>: After each removals and
     * modification to map and set's structures are checked
     * by checkKeySet. Test aims to show the correct propagation
     * of information. checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: Entries (i, "i") are inserted and removed
     * from the map remove. The map is initiated with {0="0", 200="200"},
     * therefore ks contains {0:200}
     * and each element in map and KeySet is removed by map's remove.
     * The map is initiated with {0="0", 200="200"},
     * and each element in map and KeySet is removed by KeySet's remove.
     * After each modification to the map, through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.
     * </p>
     * <p><b>Pre-Condition</b>: map and set are empty.</p>
     * <p><b>Post-Condition</b>: map and set are empty.</p>
     * <p><b>Expected Results</b>: Each removal is correctly propagated
     * from map to KeySet and from KeySet to map. Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries. In particular,
     * they still contains coherent informations.</p>
     */
    @Test
    public void Remove_Backing()
    {
        int bound = 200;
        for (int i = 0; i < bound; i++)
        {
            m.put(i, ""+i);
            checkKeySet(m, ks);
            checkIteration(ks);
            assertEquals("Should be removed", ""+i, m.remove(i));
            checkKeySet(m, ks);
            checkIteration(ks);
        }
        assertEquals("Should be empty", 0, m.size());
        initHMap(m, 0, bound);
        checkKeySet(m, ks);
        checkIteration(ks);
        for (int i = bound - 1; i >= 0; i--)
        {
            assertEquals("Should be removed", ""+i, m.remove(i));
            checkKeySet(m, ks);
            checkIteration(ks);
        }
        initHMap(m, 0, bound);
        checkKeySet(m, ks);
        checkIteration(ks);
        for (int i = bound - 1; i >= 0; i--)
        {
            assertTrue("Should change and return true", ks.remove(i));
            checkKeySet(m, ks);
            checkIteration(ks);
        }
        assertEquals("Should be empty", 0, m.size());
        checkKeySet(m, ks);
        checkIteration(ks);
    }

    // ------------------------------------------ removeAll method ------------------------------------------
    /**
     * <p><b>Summary</b>: removeAll method test case. The test aim
     * to test coherence of map and its KeySet after invoking removeAll
     * in different scenarios and problem size.</p>
     * <p><b>Test Case Design</b>: After each removeAll method invoke
     * the coherence is checked through checkKeySet. Tests right
     * propagation from the entry set to the map. Removing all entries
     * from the KeySet implies removing all the entries in the map.
     * The collection keeps changing during execution. Note that
     * different sizes are tested, as first the size of ks is smaller
     * than the size of c, while then the size of ks is bigger than the
     * size of c. checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.</p>
     * <p><b>Test Description</b>: The collection contains {0="0":500="500"},
     * while map contains {0="0" : i="i"} and the keyset contains 
     * {0:i}, for each i in (1,700)
     * (Note that empty map/KeySet limit case is being tested). Then
     * ks.removeAll(c) is invoked and coherence is check through checkKeySet.
     * After each modification og the map and set, coherence is checked,
     * through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.
     * Then the test asserts that m and ks have the right size and isEmpty
     * returns the right value.</p>
     * <p><b>Pre-Condition</b>: c contains {0="0":500="500"}, m and ks are
     * empty.</p>
     * <p><b>Post-Condition</b>: c is unchanged, m contains
     * {500="500":700="700"}, while ks contains {500:700}.</p>
     * <p><b>Expected Results</b>: Each removal is correctly propagated
     * from map to KeySet and from KeySet to map. Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries. In particular,
     * they still contains coherent informations.</p>
     */
    @Test
    public void RemoveAll_Backing0()
    {
        int bound = 700;
        int secondBound = 500;
        c = getIntegerHCollection(0, secondBound);
        for (int i = 1; i < bound; i++)
        {
            initHMap(m, 0, i);
            assertTrue("Should return true", ks.removeAll(c));
            checkKeySet(m, ks);
            checkIteration(ks);

            if (i <= secondBound)
            {
                assertTrue("Both should have size 0", m.size() == ks.size() && m.size() == 0);
                assertTrue("Both should be empty", ks.isEmpty() == m.isEmpty() && ks.isEmpty());
            }
            else
            {
                assertTrue("Both should have size > 0", m.size() == ks.size() && m.size() == i - secondBound);
                assertTrue("Both should not be empty", ks.isEmpty() == m.isEmpty() && !ks.isEmpty());
            }
        }
    }

    /**
     * <p><b>Summary</b>: removeAll method test case.
     * Tests removeAll method correct behaviour
     * and propagation from map to keySet and
     * viceversa. Coherence is also checked through
     * checkKeySet(m, ks) and checkIteration(ks).</p>
     * <p><b>Test Case Design</b>: Tests removeAll method with
     * keySet HSet. Correct propagation is tested in both ways.
     * </p>
     * <p><b>Test Description</b>: m contains the
     * entries A=a, B=b, C=c, D=d, D2=d. The HCollection
     * c is initialized with A, C and D. That means,
     * invoking es.removeAll(c) will remove A=a, C=c, D=d.
     * The test asserts that afore mentioned entries are
     * removed, while the remaining ones are still in the
     * HMap and in the keySet.</p>
     * <p><b>Pre-Condition</b>: m contains A=a, B=b, C=c, D=d, D2=d,
     * ks contains A, B, C, D, D2.</p>
     * <p><b>Post-Condition</b>: m contains B=b, D2=d, ks contains B, D2.</p>
     * <p><b>Expected Results</b>: m contains B=b, D2=d.
     * ks contains B, D2.
     * Propagation works correctly from map to keySet and
     * from keySet to map.</p>
     */
    @Test
    public void RemoveAll_Backing1()
    {
        String[] arg_k = {"A", "B", "C", "D", "D2"};
        String[] arg_v = {"a", "b", "c", "d", "d"};
        
        for (int i = 0; i < arg_k.length; i++)
        {
            m.put(arg_k[i], arg_v[i]);
            checkKeySet(m, ks);
            checkIteration(ks);
        }

        c = getHCollection(new Object[]{"A", "C", "D"});
        
        for (int i = 0; i < arg_k.length; i++)
        {
            assertTrue("Should be contained", ks.contains(arg_k[i]));
            assertTrue("Should be contained", m.containsKey(arg_k[i]) && m.containsValue(arg_v[i]));
            assertTrue(m.get(arg_k[i]).equals(arg_v[i]));
        }

        assertTrue("Should be removed", ks.removeAll(c));
        assertTrue(m.size() == ks.size() && m.size() == 2);

        assertFalse("Should NOT be contained", ks.contains("A"));
        assertTrue("Should NOT be contained", !m.containsKey("A") && !m.containsValue("a"));
        assertNull("Should be null", m.get("A"));

        assertTrue("Should be contained", ks.contains("B"));
        assertTrue("Should be contained", m.containsKey("B") && m.containsValue("b"));
        assertTrue("Should match", m.get("B").equals("b"));

        assertFalse("Should NOT be contained", ks.contains("C"));
        assertTrue("Should NOT be contained", !m.containsKey("C") && !m.containsValue("c"));
        assertNull("Should be null", m.get("C"));

        assertFalse("Should NOT be contained", ks.contains("D"));
        assertTrue("Should NOT be contained", !m.containsKey("D") && m.containsValue("d"));
        assertNull("Should be null", m.get("D"));

        assertTrue("Should be contained", ks.contains("D2"));
        assertTrue("Should be contained", m.containsKey("D2") && m.containsValue("d"));
        assertTrue("Should match", m.get("D2").equals("d"));  
    }

    /**
     * <p><b>Summary</b>: removeAll method test case.</p>
     * <p><b>Test Case Design</b>: Tests removeAll behaviour
     * when the passed argument is null. The method should throw
     * NullPointerException.</p>
     * <p><b>Test Description</b>: ks.removeAll(null) is invoked.</p>
     * <p><b>Pre-Condition</b>: ks is empty</p>
     * <p><b>Post-Condition</b>: ks is empty</p>
     * <p><b>Expected Results</b>: NPE is thrown.</p>
     */
    @Test(expected = NullPointerException.class)
    public void RemoveAll_EmptyNull()
    {
        ks.removeAll(null);
    }

    /**
     * <p><b>Summary</b>: removeAll method test case.</p>
     * <p><b>Test Case Design</b>: Tests removeAll behaviour
     * when the passed argument is empty.
     * The method should just return false, as set
     * is not modified.</p>
     * <p><b>Test Description</b>: ks.removeAll(c) is invoked, then m is
     * cleared and ks.removeAll(c) is invoked.</p>
     * <p><b>Pre-Condition</b>: ks contains {0="0":10="10"}</p>
     * <p><b>Post-Condition</b>: ks is empty.</p>
     * <p><b>Expected Results</b>: false is returned both times,
     * as the set was not modified..</p>
     */
    @Test
    public void RemoveAll_False()
    {
        initHMap(m, 0, 10);
        assertFalse(ks.removeAll(c));
        m.clear();
        assertFalse(ks.removeAll(c));
    }

    /**
     * <p><b>Summary</b>: removeAll method test case.</p>
     * <p><b>Test Case Design</b>: Tests removeAll behaviour
     * when the passed argument is null. The method should throw
     * NullPointerException.</p>
     * <p><b>Test Description</b>: ks.removeAll(null) is invoked.</p>
     * <p><b>Pre-Condition</b>: ks contains {0="0":10="10"}.</p>
     * <p><b>Post-Condition</b>: ks contains {0="0":10="10"}.</p>
     * <p><b>Expected Results</b>: NPE is thrown.</p>
     */
    @Test(expected = NullPointerException.class)
    public void RemoveAll_NotEmptyNull()
    {
        initHMap(m, 0, 10);
        ks.removeAll(null);
    }

    /**
     * <p><b>Summary</b>: removeAll method test case.</p>
     * <p><b>Test Case Design</b>: Tests removeAll behaviour
     * when the passed argument is empty. The method should throw
     * return false as the set is unchanged.</p>
     * <p><b>Test Description</b>: ks.removeAll(c) is invoked when ks is empty
     * and when ks contains 10 elements. Tests propagation.</p>
     * <p><b>Pre-Condition</b>: c is empty, ks is empty.</p>
     * <p><b>Post-Condition</b>: c is empty, ks contains {0:10}.</p>
     * <p><b>Expected Results</b>: Both removeAll invoke return false,
     * propagation works correctly.</p>
     */
    @Test
    public void RemoveAll_Empty()
    {
        assertFalse("Should be unchanged", ks.removeAll(c));
        checkKeySet(m, ks);
        checkIteration(ks);
        initHMap(m, 0, 10);
        assertFalse("Should be unchanged", ks.removeAll(c));
        checkKeySet(m, ks);
        checkIteration(ks);
    }

    /**
     * <p><b>Summary</b>: removeAll method test case.</p>
     * <p><b>Test Case Design</b>: Tests removeAll behaviour
     * when the passed argument contains element not in the set.
     * The method should throw
     * return false as the set is unchanged.</p>
     * <p><b>Test Description</b>: es.removeAll(c) is invoked when ks contains
     * {0:i} for i in (0, 10). c contains {15:20}.
     * Tests propagation.</p>
     * <p><b>Pre-Condition</b>: c contains {15:20},
     * m is empty.</p>
     * <p><b>Post-Condition</b>: c is unchanged, ks contains {0:10}.</p>
     * <p><b>Expected Results</b>: removeAll(c) invoke return false,
     * propagation works correctly.</p>
     */
    @Test
    public void RemoveAll_NotInSet()
    { 
        c = getIntegerHCollection(15, 20);
        for (int i = 0; i < 10; i++)
        {
            initHMap(m, 0, i);
            assertFalse("Should not change", ks.removeAll(c));
            assertEquals("Should equals", getIntegerHCollection(0, i), ks);
        }
        checkKeySet(m, ks);
        checkIteration(ks);
    }

    // ------------------------------------------ retainAll method ------------------------------------------

    /**
     * <p><b>Summary</b>: retainAll method test case. The test aim
     * to test coherence of map and its KeySet after invoking retainAll
     * in different scenarios and problem size.</p>
     * <p><b>Test Case Design</b>: After each retainAll method invoke
     * the coherence is checked through checkKeySet. Tests right
     * propagation from the entry set to the map. Retaining all entries
     * from the KeySet implies retaining all the entries in the map.
     * The collection keeps changing during execution. checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.</p>
     * <p><b>Test Description</b>: The collection contains {0="0":300="300"},
     * while map contains {0="0" : i="i"}
     * and the keySet contains {0:i}, for each i in (0,500)
     * (Note that empty map/KeySet limit case is being tested). Then
     * ks.retainAll(c) is invoked and coherence is check through checkKeySet(m, ks)
     * and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.
     * Then the test asserts that m and ks have the right size and isEmpty
     * returns the right value.</p>
     * <p><b>Pre-Condition</b>: c contains {0="0":300="300"}, m and ks are
     * empty.</p>
     * <p><b>Post-Condition</b>: c is unchanged, m contains
     * {0="0":300="300"} and ks contains {0:300}.</p>
     * <p><b>Expected Results</b>: Each retainAll is correctly propagated
     * from map to KeySet and from KeySet to map. Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries. In particular,
     * they still contains coherent informations.</p>
     */
    @Test
    public void RetainAll_Backing()
    {
        int bound = 500;
        int secondBound = 300;
        c = getIntegerHCollection(0, secondBound);
        for (int i = 0; i < bound; i++)
        {
            initHMap(m, 0, i);
            ks.retainAll(c);
            checkKeySet(m, ks);
            checkIteration(ks);

            if (i <= secondBound)
                assertTrue("Both should have size " + i, m.size() == ks.size() && m.size() == i);
            else
                assertTrue("Both should have size " + secondBound, m.size() == ks.size() && m.size() == secondBound);
        }
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * retainAll is being called with an empty collection as an argument,
     * therefore the keyset should became the empty set, since mainteining
     * only the "empty" subset means deleting all the elements.</p>
     * <p><b>Test Case Design</b>: retainAll being called with the limit case of
     * an empty collection as an argument. checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.</p>
     * <p><b>Test Description</b>: The keyset removes all but "empty", so
     * it empties. In fact initially it contains the keys {1, 2, 3}.
     * Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.
     * Tests propagation.</p>
     * <p><b>Pre-Condition</b>: The keyset contains the keys {1, 2, 3}.</p>
     * <p><b>Post-Condition</b>: The keyset is empty.</p>
     * <p><b>Expected Results</b>: The keyset is empty, retainAll returns true
     * because the keyset has changed. Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries. Coherence is checked through
     * checkKeySet.</p>
     */
    @Test
    public void RetainAll_Empty_True()
    {
        initHMap(m, 1, 4);
        assertTrue("The set has changed, it should return true.", ks.retainAll(c));
        assertEquals("set should be empty.", 0, ks.size());
        assertEquals("coll should be empty.", 0, c.size());
        checkKeySet(m, ks);
        checkIteration(ks);
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * retainAll is being called with an empty collection as an argument and
     * an empty keyset as object,
     * therefore the keyset should remain the empty keyset, since mainteining
     * only the "empty" subset means deleting all the elements. Unlike the
     * RetainAll_Empty_True test case, the keyset is already empty, therefore
     * the method returns false.</p>
     * <p><b>Test Case Design</b>: retainAll being called with the limit case of
     * an empty collection as an argument and an empty set. checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.</p>
     * <p><b>Test Description</b>: The keyset removes all but "empty", so
     * it empties. Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.
     * Tests propagation.</p>
     * <p><b>Pre-Condition</b>: The keyset is empty.</p>
     * <p><b>Post-Condition</b>: The keyset is still empty.</p>
     * <p><b>Expected Results</b>: retainAll returns false because keyset set is unchanged.
     * Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void RetainAll_Empty_False()
    {
        assertFalse("The set has not changed, it should return false.", ks.retainAll(c));
        checkKeySet(m, ks);
        checkIteration(ks);
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * The test calls retainAll with a collection containing
     * few elements.</p>
     * <p><b>Test Case Design</b>: The retainAll method is tested with small
     * input. Testing a typical case. checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: The keyset initially contains numbers
     * from 1 to 5 included. retainAll is called with a collection
     * containing {3, 4, 5}, therefore the keyset should contain
     * {3, 4, 5}. Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     * <p><b>Pre-Condition</b>: The keyset contains {1, ..., 5}, c contains
     * {3, 4, 5}.</p>
     * <p><b>Post-Condition</b>: The keyset contains {3, 4, 5}, c contains
     * {3, 4, 5}.</p>
     * <p><b>Expected Results</b>: set contains {3, 4, 5}, keyset has changed. retainAll returns true
     * because the keyset has changed. Coherence is checked after each retainAll invoke.
     * Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void RetainAll_12345()
    {
        initHMap(m, 1, 6);
        c = getIntegerHCollection(3, 6);
        assertTrue("The set has changed, it should return true.", ks.retainAll(c));
        assertTrue("set should contain {3, 4, 5}.", ks.equals(getIntegerHSet(3, 6)));
        checkKeySet(m, ks);
        checkIteration(ks);
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * The test calls retainAll with a collection containing
     * few elements. Testing a typical case.</p>
     * <p><b>Test Case Design</b>: The retainAll method is tested with small
     * input. checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: The keyset initially contains numbers
     * from 1 to 9 included. retainAll is called with a collection
     * containing {2, 3}, therefore the keyset should contain the keys
     * {2, 3}.</p>
     * <p><b>Pre-Condition</b>: The keyset contains {1, ..., 9}, c contains
     * {2, 3}.</p>
     * <p><b>Post-Condition</b>: The keyset contains {2, 3}, c contains
     * {2, 3}.</p>
     * <p><b>Expected Results</b>: keyset contains {2, 3}, skeysetet has changed.
     * retainAll returns true
     * because the set has changed. Coherence is checked after each retainAll
     * invoke. Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void RetainAll_23()
    {
        initHMap(m, 1, 10);
        c = getHCollection(new Object[]{2, 3});
        assertTrue("The set has changed, it should return true.", ks.retainAll(c));
        assertTrue("set should contain {2, 3}.", ks.equals(getIntegerHSet(2, 4)));
        checkKeySet(m, ks);
        checkIteration(ks);
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * The test calls retainAll with a collection containing
     * many elements. Testing a typical case with a large input.</p>
     * <p><b>Test Case Design</b>:  The retainAll method is tested with large
     * input. The case is still a common case. checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: The set initially contains numbers
     * from 1 to 999 included. retainAll is called with a collection
     * containing {300, ..., 599}, therefore the set should contain
     * {300, 599}. Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     * <p><b>Pre-Condition</b>: The keyset contains {1, ..., 699}, c contains
     * {300, ..., 599}.</p>
     * <p><b>Post-Condition</b>: The keyset contains {300, ..., 599}, c contains
     * {300, ..., 599}.</p>
     * <p><b>Expected Results</b>: The sets are equal, therefore keyset contains {300:600}.
     * retainAll returns true
     * as the set is being modified. Coherence is checked after each
     * retainAll invoke. Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void RetainAll_700()
    {
        initHMap(m, 1, 700);
        c = getIntegerHCollection(300, 600);
        ks.retainAll(c);
        assertEquals("The sets should match.", TestUtilities.getIntegerHSet(300, 600), ks);
        checkKeySet(m, ks);
        checkIteration(ks);
    }

	/**
     * <p><b>Summary</b>: retainAll method test case.
     * retainAll is being called with collection containing element
     * not present in the keyset as an argument,
     * therefore the keyset should became the empty set, since mainteining
     * only a subset not contained in the set means deleting all the elements.</p>
     * <p><b>Test Case Design</b>: retainAll being called with the limit case of
     * an empty intersection of keyset and c. checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: The keyset removes all but "empty", so
     * it empties. In fact initially it contains {1, ..., 20}.
     * Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     * <p><b>Pre-Condition</b>: The keyset contains {1, ..., 20}.</p>
     * <p><b>Post-Condition</b>: The keyset is empty.</p>
     * <p><b>Expected Results</b>: The keyset is empty, retainAll returns true
     * because the set changes. Coherence is checked after each retainAll
     * invoke. Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void RetainAll_ToEmpty()
    {
        initHMap(m, 1, 21);
        c = getIntegerHCollection(21, 24);

        assertTrue("The set has changed, it should return true.", ks.retainAll(c));
        assertEquals("The set should be empty.", 0, ks.size());
        checkKeySet(m, ks);
        checkIteration(ks);
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * retainAll is being called with collection containing element
     * the same element of the invoking set, therefore the
     * set should remain unchanged and retainAll(c) invoke
     * should return false.</p>
     * <p><b>Test Case Design</b>: retainAll being called with the special case of
     * the collection to contain the same elements. Tests propagation.</p>
     * <p><b>Test Description</b>: The set contains {0:i}
     * for i in (0, 100). retainAll(c) is invoked and then set is unchanged.
     * Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     * <p><b>Pre-Condition</b>: The set is empty.</p>
     * <p><b>Post-Condition</b>: The set contains {0:100}.</p>
     * <p><b>Expected Results</b>: retainAll returns false
     * because the set is unchanged. Coherence is checked after each retainAll invoke.
     * Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void RetainAll_Same()
    {
        int bound = 100;
        for (int i = 0; i < bound; i++)
        {
            initHMap(m, 0, i);
            assertFalse("Contain same elements, should return false", ks.retainAll(getIntegerHCollection(0, i)));
            checkIteration(ks);
            checkKeySet(m, ks);
        }
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * retainAll is being called with collection containing element
     * the same element plus 5 more than the invoking set, therefore the
     * set should remain unchanged and retainAll(c) invoke
     * should return false.</p>
     * <p><b>Test Case Design</b>: retainAll being called with the special case of
     * the collection to contain the same elements plus 5 more. Tests propagation.</p>
     * <p><b>Test Description</b>: The set contains {0:i}
     * for i in (0, 100). retainAll(c) is invoked and then set is unchanged.
     * Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     * <p><b>Pre-Condition</b>: The set is empty.</p>
     * <p><b>Post-Condition</b>: The set contains {0:100}.</p>
     * <p><b>Expected Results</b>: retainAll returns false
     * because the set is unchanged. Coherence is checked after each retainAll invoke.
     * Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void RetainAll_SameMore()
    {
        int bound = 100;
        for (int i = 0; i < bound; i++)
        {
            initHMap(m, 0, i);
            assertFalse("Contain same elements plus more, should return false", ks.retainAll(getIntegerHCollection(0, i + 5)));
            checkIteration(ks);
            checkKeySet(m, ks);
        }
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * The method is being tested with a collection containing
     * duplicated elements. This should not change the method
     * behaviour as the absence of one element in c removes
     * all its occurencies in the keyset.</p>
     * <p><b>Test Case Design</b>: c can contain duplicated element, and
     * this should not change retainAll behaviour. At the end of
     * retainAll execution every element not contained in coll
     * must be removed. checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: keyset contains {1, ..., 19}. c contains
     * {4, 4, 5, 5, 6, 6}. retainAll is called, so the set should contain
     * {4, 5, 6}. Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     * <p><b>Pre-Condition</b>: keyset contains {1, ..., 19}. c contains
     * {4, 4, 5, 5, 6, 6}.</p>
     * <p><b>Post-Condition</b>: keyset contains {4, 5, 6}. c contains
     * {4, 4, 5, 5, 6, 6}.</p>
     * <p><b>Expected Results</b>: The arrays are equal, therefore the
     * keyset contains {4, 5, 6}. retainAll returns true
     * as the set is being modified. Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void RetainAll_DuplicatesColl()
    {
        initHMap(m, 0, 20);
        c = getHCollection(new Object[]{4, 4, 5, 5, 6, 6});
        assertTrue("The set has changed, it should return true.", ks.retainAll(c));
        assertEquals("The arrays should match.", TestUtilities.getIntegerHSet(4, 7), ks);
        checkKeySet(m, ks);
        checkIteration(ks);
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.</p>
     * <p><b>Test Case Design</b>: Tests retainAll behaviour
     * when the passed argument is null. The method should throw
     * NullPointerException.</p>
     * <p><b>Test Description</b>: ks.retainAll(null) is invoked.</p>
     * <p><b>Pre-Condition</b>: ks is empty</p>
     * <p><b>Post-Condition</b>: ks is empty</p>
     * <p><b>Expected Results</b>: NPE is thrown.</p>
     */
    @Test(expected = NullPointerException.class)
    public void RetainAll_EmptyNull()
    {
        ks.retainAll(null);
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.</p>
     * <p><b>Test Case Design</b>: Tests retainAll behaviour
     * when the passed argument is null. The method should throw
     * NullPointerException.</p>
     * <p><b>Test Description</b>: ks.retainAll(null) is invoked.</p>
     * <p><b>Pre-Condition</b>: ks contains {0="0":10="10"}.</p>
     * <p><b>Post-Condition</b>: ks contains {0="0":10="10"}.</p>
     * <p><b>Expected Results</b>: NPE is thrown.</p>
     */
    @Test(expected = NullPointerException.class)
    public void RetainAll_NotEmptyNull()
    {
        initHMap(m, 0, 10);
        ks.retainAll(null);
    }

    // ------------------------------------------ toArray method ------------------------------------------

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of ks and map is zero, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an empty
     * KeySet</p>
     * <p><b>Pre-Condition</b>: ks and m are empty</p>
     * <p><b>Post-Condition</b>: ks and m are empty</p>
     * <p><b>Expected Results</b>: ks.toArray returns an empty
     * toArray. Through checkToArray(es, es.toArray()) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void ToArray_Empty()
    {
        assertArrayEquals(new Object[0], ks.toArray());
    }

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of ks and map is 1, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an KeySet
     * containing only one element, then ks and the array returned
     * from toArray are checked through checkToArray.</p>
     * <p><b>Pre-Condition</b>: ks and m contains 1 element</p>
     * <p><b>Post-Condition</b>: ks and m are unchanged</p>
     * <p><b>Expected Results</b>: ks.toArray returns an array
     * of lenght 1 containing only 1, checked through checkToArray.
     * Through checkToArray(es, es.toArray()) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void ToArray_OneElement()
    {
        m.put(1, "One");
        assertArrayEquals(new Object[]{1}, ks.toArray());
        checkToArray(ks, ks.toArray());
    }

    /**
     * <p><b>Summary</b>: toArray and remove method test case.
     * Tests the behaviour of toArray method in different cases
     * and configurations of the KeySet.</p>
     * <p><b>Test Case Design</b>: Checks the array to be right
     * through checkToArray after each removals through ks.remove,
     * creating different situations and cases to test the method.
     * checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.</p>
     * <p><b>Test Description</b>: The test first removes the
     * entries with even key, then the entries with odd key. After each
     * removal the checkToArray method is invoked to check if the
     * generated array in all of this cases is right.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":100="100"}, therefore
     * ks contains {0:100}..</p>
     * <p><b>Post-Condition</b>: m and ks are empty</p>
     * <p><b>Expected Results</b>: After each removal the generated array
     * through ks.toArray is right and coherent. At the end ks and m are empty.
     * Through checkToArray(es, es.toArray()), checkKeySet(m, ks)
     * and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void ToArray_0To100()
    {
        int bound = 100;
        initHMap(m, 0, 100);
        for (int i = 0; i < bound; i += 2)
        {
            ks.remove(i);
            checkToArray(ks, ks.toArray());
            checkKeySet(m, ks);
            checkIteration(ks);
        }
        for (int i = 1; i < bound; i += 2)
        {
            ks.remove(i);
            checkToArray(ks, ks.toArray());
            checkKeySet(m, ks);
            checkIteration(ks);
        }
        assertEquals("Should be empty", 0, ks.size());
        assertEquals("Should be empty", 0, m.size());
    }

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of ks and map is zero, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an empty
     * KeySet</p>
     * <p><b>Pre-Condition</b>: ks and m are empty</p>
     * <p><b>Post-Condition</b>: ks and m are empty</p>
     * <p><b>Expected Results</b>: ks.toArray returns an empty
     * toArray. Through checkToArray(es, es.toArray()), asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void ToArrayArrayArg_Empty()
    {
        Object[] a = new Object[0];
        ks.toArray(a);
        assertArrayEquals(new Object[0], a);
    }

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of ks and map is 1, which is a limit case.
     * checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: toArray is invoked by an KeySet
     * containing only one element, then ks and the array returned
     * from toArray are checked through checkToArray.</p>
     * <p><b>Pre-Condition</b>: ks and m contains 1 element</p>
     * <p><b>Post-Condition</b>: ks and m are unchanged</p>
     * <p><b>Expected Results</b>: ks.toArray returns an array
     * of lenght 1 containing only the element 1="One".
     * Through checkToArray(es, es.toArray()), checkKeySet(m, ks)
     * and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void ToArrayArrayArg_OneElement()
    {
        Object[] a = new Object[1];
        m.put(1, "One");
        ks.toArray(a);
        assertArrayEquals(new Object[]{1}, a);
        checkToArray(ks, a);
        checkIteration(ks);
        checkKeySet(m, ks);
    }

    /**
     * <p><b>Summary</b>: toArray and remove method test case.
     * Tests the behaviour of toArray method in different cases
     * and configurations of the KeySet.</p>
     * <p><b>Test Case Design</b>: Checks the array to be right
     * through checkToArray after each removals through ks.remove,
     * creating different situations and cases to test the method.
     * checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.</p>
     * <p><b>Test Description</b>: The test first removes the
     * entries with even key, then the entries with odd key. After each
     * removal the checkToArray method is invoked to check if the
     * generated array in all of this cases is right.
     * Tests propagation.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":100="100"}, therefore
     * ks contains {0:100}.</p>
     * <p><b>Post-Condition</b>: m and ks are empty</p>
     * <p><b>Expected Results</b>: After each removal the generated array
     * through ks.toArray is right and coherent. At the end ks and m are empty.
     * Through checkToArray(es, es.toArray()), checkKeySet(m, ks)
     * and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void ToArrayArrayArg_0To100()
    {
        int bound = 100;
        
        initHMap(m, 0, 100);
        for (int i = 0; i < bound; i += 2)
        {
            ks.remove(i);
            Object[] a = new Object[ks.size()];
            ks.toArray(a);
            checkToArray(ks, a);
            checkKeySet(m, ks);
            checkIteration(ks);
        }
        for (int i = 1; i < bound; i += 2)
        {
            ks.remove(i);
            Object[] a = new Object[ks.size()];
            ks.toArray(a);
            checkToArray(ks, a);
            checkKeySet(m, ks);
            checkIteration(ks);
        }
        assertEquals("Should be empty", 0, ks.size());
        assertEquals("Should be empty", 0, m.size());
    }

    /**
     * <p><b>Summary</b>: toArray method test case.
     * Test took by TestValues.java tests assigned by the professor.</p>
     * <p><b>Test Case Design</b>: Tests keySet toArray with a single element.</p>
     * checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.
     * <p><b>Test Description</b>: size is true if lenght is 1, content
	 * is true if a[0] equals the Collection Adapter.</p>
     * <p><b>Pre-Condition</b>: head has at least 1 element.</p>
     * <p><b>Post-Condition</b>: a is an array containing head elements.</p>
     * <p><b>Expected Results</b>:size AND content are true.</p>
     */
	@Test
	public void to_array_a() {
		Object[] a = new Object[10];
        m.put("Collection Adapter", "Random value");
        HSet head = m.keySet();
		a = head.toArray(a);
		boolean size = (a.length == 1);
		boolean content = (a[0].equals("Collection Adapter"));
		assertTrue(size && content);
	}

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests toArray behaviour when
     * invoked with a null argument.</p>
     * <p><b>Test Description</b>: ks.toArray(null) is invoked.</p>
     * <p><b>Pre-Condition</b>: ks is empty.</p>
     * <p><b>Post-Condition</b>: ks is empty.</p>
     * <p><b>Expected Results</b>: NullPointerException is being thrown.</p>
     */
    @Test (expected = NullPointerException.class)
    public void ToArrayArrayArg_NullEmpty()
    {
        ks.toArray(null);
    }

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests toArray behaviour when
     * invoked with a null argument.</p>
     * <p><b>Test Description</b>: ks.toArray(null) is invoked.</p>
     * <p><b>Pre-Condition</b>: ks contains {0:10}.</p>
     * <p><b>Post-Condition</b>: ks contains {0:10}.</p>
     * <p><b>Expected Results</b>: NullPointerException is being thrown.</p>
     */
    @Test (expected = NullPointerException.class)
    public void ToArrayArrayArg_Null0To10()
    {
        initHMap(m, 0, 10);
        ks.toArray(null);
    }

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests toArray behaviour when
     * invoked the array argument is smaller than the set's size.</p>
     * <p><b>Test Description</b>: ks.toArray(a) is invoked.</p>
     * <p><b>Pre-Condition</b>: ks contains {0="0":10="10"}, a is
     * 1 element array.</p>
     * <p><b>Post-Condition</b>: ks contains {0="0":10="10"}, a is
     * 1 element array..</p>
     * <p><b>Expected Results</b>: IllegalArgumentException is being thrown.</p>
     */
    @Test (expected = IllegalArgumentException.class)
    public void ToArrayArrayArg_EmptyIAE()
    {
        initHMap(m, 0, 10);
        Object[] a = new Object[5];
        ks.toArray(a);
    }

    // ------------------------------------------ iterator method and KeySetIterator ------------------------------------------
    
    /**
     * <p><b>Summary</b>: iteration on an KeySet test case.</p>
     * <p><b>Test Case Design</b>: Tests the iterator's behaviour
     * on the limit case of an empty KeySet.</p>
     * <p><b>Test Description</b>: checks if the iterator is right
     * through checkIteration method. In particular, checks
     * if the iterator iterated the right amount of times
     * (checks size coherence) and if after its last iteration
     * (if it happens, otherwise the first) has next.</p>
     * <p><b>Pre-Condition</b>: KeySet is empty</p>
     * <p><b>Post-Condition</b>: KeySet is empty</p>
     * <p><b>Expected Results</b>: first iteration has no next,
     * iterated 0 times as the entrylist is empty.</p>
     */
    @Test
    public void KSIterator_Empty()
    {
        checkIteration(ks);
    }

    /**
     * <p><b>Summary</b>: iteration on an KeySet test case.</p>
     * <p><b>Test Case Design</b>: Tests the iterator's behaviour
     * on the limit case of an empty KeySet.</p>
     * <p><b>Test Description</b>: checks if the iterator is right
     * through checkIteration method. In particular, checks
     * if the iterator iterated the right amount of times
     * (checks size coherence) and if after its last iteration
     * (if it happens, otherwise the first) has next.
     * Then invokes next method on iterator, which should throw
     * NoSuchElementException as the iterator, being empty,
     * has no next.</p>
     * <p><b>Pre-Condition</b>: KeySet is empty</p>
     * <p><b>Post-Condition</b>: KeySet is empty</p>
     * <p><b>Expected Results</b>: first iteration has no next,
     * iterated 0 times as the entrylist is empty. NSEE is thrown
     * due to iterator.next() call.</p>
     */
    @Test (expected = NoSuchElementException.class)
    public void KSIterator_EmptyNSEE()
    {
        checkIteration(ks);
        ks.iterator().next();
    }

    /**
     * <p><b>Summary</b>: iteration on an KeySet test case.</p>
     * <p><b>Test Case Design</b>: Tests the iterator's behaviour
     * on an KeySet of variable size, including the size 1 and
     * a much bigger size. checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.</p>
     * <p><b>Test Description</b>: checks if the iterator is right
     * through checkIteration method. In particular, checks
     * if the iterator iterated the right amount of times
     * (checks size coherence) and if after its last iteration
     * (if it happens, otherwise the first) has next.
     * The iteration is checked 100 times for 100 different configurations
     * of the HMap. Infact, m contains {0="0":i="i"} for
     * each i in {0:100}, therefore ks contanis {0:i}.</p>
     * <p><b>Pre-Condition</b>: KeySet and m are empty</p>
     * <p><b>Post-Condition</b>: m contain {0="0":100="100"}, therefore
     * ks contanis {0:i}</p>
     * <p><b>Expected Results</b>: For each iteration, m containing {0="0":i="i"},
     * the iteration is tested through checkIteration. In particular,
     * iterates i times, for each i in {0:100}.
     * Through checkKeySet(m, ks) and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void KSIterator_Variable()
    {
        int bound = 100;
        for (int i = 1; i < bound; i++)
        {
            initHMap(m, 0, i);
            checkIteration(ks);
            checkKeySet(m, ks);
        }
    }

    /**
     * <p><b>Summary</b>: iteration on KeySet test case.</p>
     * <p><b>Test Case Design</b>: Tests the iterator's remove
     * method behaviour when the KeySet is empty, which is a limit case.
     * Obviusly a next method would throw NSEE, therefore a remove
     * method, that needs a next invoke before using it (This method can
     * be called only once per
     * call to next) will throw HISE.</p>
     * <p><b>Test Description</b>: remove method is invoked by a generated
     * iterator.</p>
     * <p><b>Pre-Condition</b>: m and ks are empty.</p>
     * <p><b>Post-Condition</b>: m and ks are empty.</p>
     * <p><b>Expected Results</b>: HIllegalStateException has been thrown
     * by iterator.remove().</p>
     */
    @Test (expected = HIllegalStateException.class)
    public void KSIterator_EmptyRemoveISE()
    {
        ks.iterator().remove();
    }

    /**
     * <p><b>Summary</b>: iteration on KeySet test case.</p>
     * <p><b>Test Case Design</b>: Tests the iterator's remove
     * method behaviour when the map contains {0="0" : 10="10"},
     * therefore the keyset contains {0:10}.
     * For the first remove a next is invoked before, which must work
     * normally. The iteration is check through checkIterarion, and the
     * map - KeySet coherence is checked through checkKeySet.
     * The second remove is not backed by a next call:
     * a remove
     * method, that needs a next invoke before using it (This method can
     * be called only once per
     * call to next) will throw HISE.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: it invoke next and then remove, which
     * should work normally (no exception thrown here). Then the second
     * remove, which is not backed by any next call, throws
     * HIllegalStateException as remove method can
     * be called only once per
     * call to next.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":10="10"}, therefore
     * ks contains {0:10}.</p>
     * <p><b>Post-Condition</b>: m contains {0="0":10="10"}, therefore
     * ks contains {0:10}..</p>
     * <p><b>Expected Results</b>: iteration and KeySet are checked through checkIteration
     * and checkKeySet after the first remove. HIllegalStateException has been thrown
     * by iterator.remove().</p>
     */
    @Test (expected = HIllegalStateException.class)
    public void KSIterator_0To10RemoveISE()
    {
        initHMap(m, 0, 10);
        it = ks.iterator();
        
        it.next();
        it.remove();
        checkKeySet(m, ks);
        checkIteration(ks);

        // No previous next
        it.remove();
    }

    /**
     * <p><b>Summary</b>: iteration on KeySet test case.
     * Tests iterator.remove method on a changing KeySet,
     * checking KeySet - map coherence and the iteration
     * with checkKeySet and checkIteration.</p>
     * <p><b>Test Case Design</b>: The map is constantly
     * changing during execution due to it.remove,
     * therefore coherence and iteration must be check
     * to assure correct propagation iterator -{@literal >} KeySet -{@literal >} map.
     * checkKeySet(m, ks) and checkIteration(ks)
     * are invoked to test keySet - map coherence and the iteration.</p>
     * <p><b>Test Description</b>: map and ks initially contain
     * {0="0":100="100"}. An iterator iterates through
     * each element and after each next it invokes the remove
     * method, removing the just returned element.
     * Then checkKeySet and checkIteration are invoke
     * to check KeySet - map coherence and iteration.
     * After iterating through all elements, the iterator.hasNext
     * method must return false, and ks and m should be both empty.</p>
     * <p><b>Pre-Condition</b>: map contains
     * {0="0":100="100"}, therefore ks contains {0:100}</p>
     * <p><b>Post-Condition</b>: m and ks are empty.</p>
     * <p><b>Expected Results</b>: Each remove invoke works right,
     * the element is removed correctly and through checkKeySet(m, ks)
     * and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void KSIterator_0To100Remove()
    {
        initHMap(m, 0, 100);
        it = ks.iterator();
        
        while (it.hasNext())
        {
            Object k = it.next();
            it.remove();
            assertFalse("Should be removed", m.containsKey(k));
            checkKeySet(m, ks);
            checkIteration(ks);
        }
        assertFalse("Should have not next", it.hasNext());
        assertTrue("Should be empty.", ks.isEmpty() && m.isEmpty());
    }

    /**
     * <p><b>Summary</b>: iteration on entryset test case.
     * Tests iterator.remove method on a changing entryset,
     * checking keyset - map coherence and the iteration
     * with checkKeySet and checkIteration.</p>
     * <p><b>Test Case Design</b>: The map is constantly
     * changing during execution due to it.remove,
     * therefore coherence and iteration must be check
     * to assure correct propagation iterator -{@literal >} entryset -{@literal >} map.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: map initially contain
     * {0="0":100="100"} and ks contains {0:100}. An iterator iterates through
     * each element and after 5 nexts it invokes the remove
     * method, removing the just returned element.
     * At the end 20 elements were removed from ks.
     * Then checkKeySet and checkIteration are invoke
     * to check keyset - map coherence and iteration.</p>
     * <p><b>Pre-Condition</b>: map initially contain
     * {0="0":100="100"} and ks contains {0:100}</p>
     * <p><b>Post-Condition</b>: m and ks contains 80 elements.</p>
     * <p><b>Expected Results</b>: Each remove invoke works right,
     * the element is removed correctly and through checkKeySet(m, ks)
     * and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void KSIterator_5Remove()
    {
        initHMap(m, 0, 100);
        it = ks.iterator();
        int i = 0; 
        while (it.hasNext())
        {
            if ((i + 1) % 5 == 0)
                it.remove();
            checkKeySet(m, ks);
            checkIteration(ks);
            it.next();
            i++;
        }
        assertEquals("20 should be removed.", 80, ks.size());
        assertEquals("20 should be removed.", 80, m.size());
    }

    /**
     * <p><b>Summary</b>: iteration on entryset test case.</p>
     * <p><b>Test Case Design</b>: Tests put on map propagation
     * to the entrySet.</p>
     * <p><b>Test Description</b>: map is initialized with
     * {0="0":3="3"}. Therefore ks contains {0:3}.
     * Then entries {0="3":3="6"} are put
     * in the map through map. Therefore ks still contains
     * {0:3}.
     * Note that, unlikely values collection and entrySet,
     * it is impossible to see at put changes through iterator,
     * as iterates only through keys, that are unchanged unless
     * some removal. Therefore, in this case immutability
     * for keyset is tested.</p>
     * <p><b>Pre-Condition</b>: map initially contain
     * {0="0":3="3"}, ks contains {0:3}.</p>
     * <p><b>Post-Condition</b>: map contains
     * {0="3":3="6"}, ks contains {0:3}.</p>
     * <p><b>Expected Results</b>: Each put propagation works right,
     * the element is substituted correctly and through checkKeySet(m, ks)
     * and checkIteration(ks) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void KSIterator_PutSubstitute()
    {
        initHMap(m, 0, 3);
        it = ks.iterator();
        while (it.hasNext())
        {
            Object curr = it.next();
            assertTrue(curr.equals(0) ||
                       curr.equals(1) ||
                       curr.equals(2));
        }
        for (int i = 0; i < 3; i++)
            assertEquals(""+i, m.put(i, ""+(i+3)));
        
        it = ks.iterator();
        while (it.hasNext())
        {
            Object curr = it.next();
            assertTrue(curr.equals(0) ||
                       curr.equals(1) ||
                       curr.equals(2));
        }
        checkKeySet(m, ks);
        checkIteration(ks);
    }

    /**
     * <p><b>Summary</b>: iteration on entryset test case.</p>
     * <p><b>Test Case Design</b>: Tests iteration and then
     * expects exception thrown after next invoke
     * when hasNext() returns false.</p>
     * <p><b>Test Description</b>: map is initialized with
     * {0="0":10="10"}. Therefore ks contains {0:10}.
     * Test iterated through the set incrementing a counter.
     * i is asserted to be 10, the map size. next is invoked.</p>
     * <p><b>Pre-Condition</b>: map contains
     * {0="0":10="10"}, ks contains {0:10}.</p>
     * <p><b>Post-Condition</b>: map and ks are unchanged</p>
     * <p><b>Expected Results</b>: Iteration works correctly.
     * Map and sets are coherent. i equals 10. Last next
     * throws NoSuchElementException.</p>
     */
    @Test (expected = NoSuchElementException.class)
    public void KSIterator_0To10NSE()
    {
        initHMap(m, 0, 10);
        it = ks.iterator();
        int i = 0; 
        while (it.hasNext())
        {
            it.next();
            i++;
        }
        assertEquals("i should be 10", 10, i);
        assertFalse("Should NOT have next", it.hasNext());
        it.next();
    }

    // ------------------------------------------ toString method ------------------------------------------

    /**
     * <p><b>Summary</b>: toString method test case.</p>
     * <p><b>Test Case Design</b>: toString is tested with
     * empty ks.</p>
     * <p><b>Test Description</b>: ks.toString() is invoked.</p>
     * <p><b>Pre-Condition</b>: ks is empty.</p>
     * <p><b>Post-Condition</b>: ks is empty.</p>
     * <p><b>Expected Results</b>: ks.toString() returns [].</p>
     */
    @Test
    public void ToString_Empty()
    {
        assertEquals("[]", ks.toString());
    }

    /**
     * <p><b>Summary</b>: toString method test case.</p>
     * <p><b>Test Case Design</b>: toString is tested with
     * one-element ks.</p>
     * <p><b>Test Description</b>: ks.toString() is invoked.</p>
     * <p><b>Pre-Condition</b>: ks contains Pippo=10.</p>
     * <p><b>Post-Condition</b>: ks contains Pippo=10.</p>
     * <p><b>Expected Results</b>: ks.toString() returns [Pippo=10].</p>
     */
    @Test
    public void ToString_OneElement()
    {
        m.put("Pippo", 10);
        assertEquals("[Pippo]", ks.toString());
    }

    // ------------------------------------------ Coarse grained tests ------------------------------------------
    
    /**
     * <p><b>Summary</b>: iterator.remove and m.remove method test case.
     * Tests the behaviour of map and KeySet while elements are
     * constantly removed from them via different ways.</p>
     * <p><b>Test Case Design</b>: Tests different ways to remove
     * elements from the backing map and the KeySet. After each
     * removal checkEntry and checkIteration are invoked to
     * check map - KeySet coherence and iterator's iteration
     * working correctly. Tests map -{@literal >} KeySet propagation and
     * iterator -{@literal >} KeySet -{@literal >} map propagation.</p>
     * <p><b>Test Description</b>: m is filled with entries {0="0":100="100"}.
     * Through a for loop entries {i="i"}, i being 10, 20, 30,...
     * are removed from the map through map.remove method. That means that 10 entries
     * are removed from m and ks. Then while the iterator's has next,
     * it removes 1 element through iterator.remove each 10 elements,
     * therefore it removes 9 entries, as the iterator starts iterating
     * the map and ks have 90 elements.</p>
     * <p><b>Pre-Condition</b>: m and ks are empty.</p>
     * <p><b>Post-Condition</b>: m and ks contains 81 entriks.</p>
     * <p><b>Expected Results</b>: Each removal from map and ks propagates
     * the changes correctly to the other structure. checkKeySet and checkIteration
     * are invoked to check coherence and that iteration works correctly.</p>
     */
    @Test
    public void RemoveIteratorEsMap()
    {
        int bound = 100;
        for (int i = 0; i < bound; i++)
        {
            m.put(i, "" + i);
            checkKeySet(m, ks);
            checkIteration(ks);
        }
        int initSize = m.size();
        for (int i = 0; i < bound; i += 10)
        {
            m.remove(i);
            checkKeySet(m, ks);
            checkIteration(ks);
        }
        assertTrue("10 should be removed", (initSize - m.size()) == 10 && m.size() == ks.size());
        it = ks.iterator();
        while (it.hasNext())
        {
            for (int i = 0; i < 10; i++)
            {
                it.next();
                checkKeySet(m, ks);
                checkIteration(ks);
            }
            it.remove();
            checkKeySet(m, ks);
            checkIteration(ks); 
        }
        assertTrue("190 should be removed", (initSize - m.size()) == 19 && m.size() == ks.size());
        assertTrue("Should be size 81", m.size() == ks.size() && m.size() == 81);
    }

    /**
     * <p><b>Summary</b>: Tests the correct propagation from multiple
     * KeySets of a map to the backing map and the propagation from
     * the single map to the multiple entrymaps.</p>
     * <p><b>Test Case Design</b>: Tests the case of multiple KeySets
     * propagating changes to ther other KeySets and to the map,
     * and the single map propagating changes to multiple KeySets,
     * inserting entries through initHMap and removing them, through
     * simple remove method. This is a more general case than the
     * single KeySet.</p>
     * <p><b>Test Description</b>: esArr contains 50 the KeySet
     * generated by the map. After map initialization, each KeySet
     * is checked through checkKeySet, checkIteration and checkToArray.
     * First is tested propagation from the KeySets to the map.
     * From each KeySet one element is removed, then each KeySet
     * is checked through aforementioned methods. At the end of
     * this operation, all the KeySets and the map have a size of 0.
     * Second is tested propagation from the map to the KeySets.
     * Each element is removed from the map and after each removal
     * each KeySet is checked through aforementioned methods.
     * Again, at the end of
     * this operation, all the KeySets and the map have a size of 0.</p>
     * <p><b>Pre-Condition</b>: m and all the KeySets are empty.</p>
     * <p><b>Post-Condition</b>: m and all the KeySets are empty.</p>
     * <p><b>Expected Results</b>: Each modification to the map and KeySets
     * mantains coherence, checkKeySet, checkIteration and checkToArray
     * tests pass.</p>
     */
    @Test
    public void MultipleKeySets()
    {
        int bound = 50;
        HSet[] ksArr = new HSet[bound];
        for (int i = 0; i < bound; i++)
        ksArr[i] = m.keySet();
        initHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
        {
            checkKeySet(m, ks);
            checkIteration(ks);
            checkToArray(ks, ks.toArray());
        }

        // KeySets -{@literal >} map
        for (int i = 0; i < bound; i++)
        {
            ksArr[i].remove(i);
            for (int j = 0; j < bound; j++)
            {
                checkKeySet(m, ksArr[j]);
                checkIteration(ksArr[j]);
                checkToArray(ksArr[j], ksArr[j].toArray());
            }
        }
        
        for (int i = 0; i < bound; i++)
            assertTrue(ksArr[i].size() == 0 && ksArr[i].isEmpty() && m.isEmpty());

        // map -{@literal >} KeySets
        initHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
        {
            m.remove(i);
            for (int j = 0; j < bound; j++)
            {
                checkKeySet(m, ksArr[j]);
                checkIteration(ksArr[j]);
                checkToArray(ksArr[j], ksArr[j].toArray());
            }
        }
        for (int i = 0; i < bound; i++)
            assertTrue(ksArr[i].size() == 0 && ksArr[i].isEmpty() && m.isEmpty());
    }

    /**
     * <p><b>Summary</b>: Tests the correct propagation from the backing map
     * to the keySet and viceversa, by adding and removing dinamically
     * elements.</p>
     * <p><b>Test Case Design</b>: Adding one element to the keySet is not
     * possible, but it is possible adding one entry to the map, so that
     * the keySet contains the key of the inserted entries. After each insertion
     * through HMap.put() checkKeySet(m, ks) and checkIteration(ks) are
     * invoked to check correct propagation.</p>
     * <p><b>Test Description</b>: The map is initiated with entries
     * {0="0":100="100"} three times. After each initiation the map
     * is made empty by HMap.remove, HSet.remove (through keySet) and
     * HIterator.remove (iterator of keySet).</p>
     * <p><b>Pre-Condition</b>: map and ks are empty.</p>
     * <p><b>Post-Condition</b>: map and ks are empty.</p>
     * <p><b>Expected Results</b>: Each modification propagates correctly
     * to the other objects. In particular, m.put and remove propagates correctly
     * to ks, ks.remove propagates correctly to m, it.remove propagates
     * correctly to m and it.</p>
     */
    @Test
    public void IncreaseKeySetThenRemove()
    {
        int bound = 100;
        for (int i = 0; i < bound; i++)
        {
            m.put(i, "" + i);
            checkKeySet(m, ks);
            checkIteration(ks);
            assertEquals("Size should be " + (i + 1), i + 1, ks.size());
        }
        // Remove from map
        for (int i = 0; i < bound; i++)
        {
            assertTrue("Should be contained", m.containsKey(i));
            assertTrue("Should be contained", m.containsValue("" + i));
            m.remove(i);
            checkKeySet(m, ks);
            checkIteration(ks);
        }
        assertTrue("Should be empty", ks.isEmpty());
        for (int i = 0; i < bound; i++)
        {
            m.put(i, "" + i);
            checkKeySet(m, ks);
            checkIteration(ks);
            assertEquals("Size should be " + (i + 1), i + 1, ks.size());
        }
        // Remove from es
        for (int i = 0; i < bound; i++)
        {
            assertTrue("Should be contained", ks.contains(i));
            ks.remove(i);
            checkKeySet(m, ks);
            checkIteration(ks);
        }
        assertTrue("Should be empty", ks.isEmpty());
        for (int i = 0; i < bound; i++)
        {
            m.put(i, "" + i);
            checkKeySet(m, ks);
            checkIteration(ks);
            assertEquals("Size should be " + (i + 1), i + 1, ks.size());
        }
        // Remove from es iterator
        it = ks.iterator();
        while (it.hasNext())
        {
            Object curr = it.next();
            it.remove();
            assertFalse("Should NOT be contained", m.containsKey(curr));
            checkIteration(ks);
            checkKeySet(m, ks);
        }
        assertTrue("Should be empty", ks.isEmpty());
    }

    /**
     * <p><b>Summary</b>: Tests the behaviour of the map when
     * trying to insert entries already in the map. Inserting
     * the same entry twice or more should ignore the insertion,
     * as the key is already present in the map.</p>
     * <p><b>Test Case Design</b>: Tests behaviour of
     * inserting cloned elements. After each put call and after
     * the removal, checkKeySet and checkIteration are invoked
     * to assert correct propagation.</p>
     * <p><b>Test Description</b>: Tests tries to insert
     * in the map the element in argv, g=g, a=a, t=t,
     * t=t, i=i, n=n, i=i, where there are t and i as duplicated
     * key. Therefore the map then contains g=g, a=a, t=t, i=i,
     * n=n and have a size of 5. Therefore keyset contains
     * g, a, t, i, n. Then entries are removed from the map,
     * therefore are removed from the keySet too.</p>
     * <p><b>Pre-Condition</b>: m and ks are empty</p>
     * <p><b>Post-Condition</b>: m and ks are empty</p>
     * <p><b>Expected Results</b>: argv elements are correctly inserted
     * in the map and the cloned key are correctly ignored, therefore
     * after insertion m.size() is 5. ks.containsAll(c) returns true.
     * Then all elements are removed through m, so in the end m and ks
     * are empty.</p>
     */
    @Test
    public void DuplicateTest()
    {
        String argv[] = {"g", "a", "t", "t", "i", "n", "i"};
        for (int i = 0; i < argv.length; i++)
        {
            m.put(argv[i], argv[i]);
            checkKeySet(m, ks);
            checkIteration(ks);
        }
        assertEquals("Size should be 5", 5, m.size());
        for (int i = 0; i < argv.length; i++)
            assertTrue(m.containsKey(argv[i]) && ks.contains(argv[i]) && m.containsValue(argv[i]));
        
        c = getHCollection(new Object[]{"g", "a", "t", "i", "n"});
        assertTrue(ks.containsAll(c));
        
        m.remove(argv[0]);
        m.remove(argv[1]);
        m.remove(argv[2]);
        m.remove(argv[4]);
        m.remove(argv[5]);

        assertFalse(ks.containsAll(c));

        assertTrue(ks.isEmpty() && m.isEmpty());
        checkKeySet(m, ks);
        checkIteration(ks);
    }


    /**
     * Tests propagation. Checks if the keyset and the backing map contains the same informations.
     * This method asserts correct propagation from HMap to its
     * keySet and from keySet to HMap, therefore it is invoked
     * whenever it is necessary to check correct propagation.
     * Firstly they must have the same size, then each key in the keyset must
     * be contained in the map and also the mapped key must
     * be contained in the map. Finally, the keySet should contain the
     * entry (key, value) obtained by the test.
     * which means that they share the same elements. If this method fails the propagation
     * did not work correctly. Other wise, the propagation worked correctly.
     * @param m the backing map
     * @param ks the keyset
     */
    public static void checkKeySet(HMap m, HSet ks)
    {
        assertEquals("Map and KeySet are NOT coherent. Propagation went wrong.", m.size(), ks.size());
        HIterator it = ks.iterator();
        HSet es = m.entrySet();
        while (it.hasNext())
        {
            Object k = it.next();
            assertTrue("Map and KeySet are NOT coherent. Propagation went wrong.", m.containsKey(k));
            assertTrue("Map and KeySet are NOT coherent. Propagation went wrong.", m.containsValue(m.get(k)));
            assertTrue("Map and KeySet are NOT coherent. Propagation went wrong.", es.contains(getEntry(k, m.get(k))));
        }
    }

    /**
     * Checks if the keyset and the array contains the same elements
     * and their size equals.
     * @param ks keyset to be checked
     * @param arr array to be checked
     */
    public static void checkToArray(HSet ks, Object[] arr)
    {
        assertEquals("The array and the set do NOT have the same elements.", ks.size(), arr.length);
        for (int i = 0; i < arr.length; i++)
            assertTrue("The array and the set do NOT have the same elements.", ks.contains(arr[i]));
    }

    /**
     * Checks if the elements returned by the iteration are
     * coherent with the keyset's elements, and if the number
     * of iteration equals the actual size of the keyset.
     * Together with checkKeySet() this method asserts correct propagation from HMap to its
     * keySet and from keySet to HMap, therefore it is invoked
     * whenever it is necessary to check correct propagation.
     * Alone it tests correct iteration.
     * If this method fails the propagation
     * did not work correctly. Other wise, the propagation worked correctly.
     * @param ks keyset to be checked
     */
    public static void checkIteration(HSet ks)
    {
        HIterator it = ks.iterator();
        int count = 0;
        while (it.hasNext())
        {
            Object e = it.next();
            count++;
            assertTrue("KeySet's iterator is NOT coherent.", ks.contains(e));
        }

        assertFalse("KeySet's iterator is NOT coherent.", it.hasNext());
        assertEquals("KeySet's iterator is NOT coherent.", count, ks.size());
        if (count == 0)
            assertTrue("KeySet's iterator is NOT coherent.", ks.isEmpty());
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