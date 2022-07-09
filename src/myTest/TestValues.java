package myTest;

// Imports
import static org.junit.Assert.*;
import static myTest.TestUtilities.*;
import java.util.NoSuchElementException;
import org.junit.*;
import myAdapter.*;

/**
 * <p><b>Summary</b>: The TestValues test suite focuses on the HCollection returned from HMap.values()
 * method. Tests the HCollection's methods correct
 * behaviour in different case scenario. Each HCollection method is tested in different
 * test cases. In each test case it is made sure of the right behaviour of HCollection respect
 * to its elements and that changes from the HCollection to the backing map and viceversa
 * are propagated correctly, which is an important feature of the HCollection returned from
 * entrySet() method. In this field key methods are checkValues and checkIteration,
 * which check coherence between the HCollection and the backing HMap: if one of these methods
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
 * for map and entrysets.</li>
 * </ul></p>
 * 
 * <p><b>Test Suite Design</b>: The test suite contains fine-grained test cases in order to
 * easily individuate errors in HCollection methods and also coarse-grained test cases in order to
 * test different methods interaction. That means that test cases include modification test, where
 * the map structure is modified in different ways, and inspection test, where information are retrieved
 * from the map to see if the informations are stored correctly, and tests where modifications and
 * inspections are combined. In the test suite there are many test cases focusing on limit and special cases,
 * invalid arguments and etc.
 * Special attention is paid to backing: after almost each modification, through the HCollection returned
 * from entrySet() or through the backing map, checkValues() and checkIteration() are invoked
 * to assert that changes propagated successfully. Note that the afore mentioned HCollection can
 * actually contain clones, as the presence of a duplicated value in a HMap is prohibited.</p>
 */
public class TestValues 
{
    private MapAdapter m, m2;
    private HCollection v, v2;
    private HCollection c;
    private HIterator it;

    @BeforeClass
    public static void BeforeClassMethod()
    {
        System.out.println(TestValues.class.getName() + " running.");
    }

    /**
	 * Method invoke before each test for setup.
	 */
    @Before
    public void Setup()
    {
        m = new MapAdapter();
        m2 = new MapAdapter();
        v = m.values();
        v2 = m2.values();
        c = getEmptyHCollection();
    }

    
    @AfterClass
    public static void AfterClassMethod()
    {
        System.out.println(TestValues.class.getName() + " ended.");
    }

    /**
	 * Method invoke after each test for cleanup.
	 */
    @After
    public void Cleanup()
    {
        m = null;
        m2 = null;
        v = null;
        v2 = null;
        it = null;
    }
    
    // ------------------------------------------ single method test case ------------------------------------------

    // ------------------------------------------ add method ------------------------------------------
    
    /**
     * <p><b>Summary</b>: add method test case.</p>
     * <p><b>Test Case Design</b>: The methoud throws
     * UnsupportedOperationException.</p>
     * <p><b>Test Description</b>: add is invoked.</p>
     * <p><b>Pre-Condition</b>: v is empty.</p>
     * <p><b>Post-Condition</b>: v is empty.</p>
     * <p><b>Expected Results</b>: The add method is not supported.
     * UnsupportedOperationException is thrown.</p>
     */
    @Test (expected = UnsupportedOperationException.class)
    public void Add_UOE()
    {
        v.add(1);
    }

    /**
     * <p><b>Summary</b>: add method test case.</p>
     * <p><b>Test Case Design</b>: The methoud throws
     * UnsupportedOperationException.</p>
     * <p><b>Test Description</b>: add is invoked.</p>
     * <p><b>Pre-Condition</b>: v contains {"0":"10"}.</p>
     * <p><b>Post-Condition</b>: v contains {"0":"10"}.</p>
     * <p><b>Expected Results</b>: The add method is not supported.
     * UnsupportedOperationException is thrown.</p>
     */
    @Test (expected = UnsupportedOperationException.class)
    public void Add_10UOE()
    {
        initHMap(m, 0, 10);
        v.add(1);
    }

    // ------------------------------------------ addAll method ------------------------------------------
    
    /**
     * <p><b>Summary</b>: add method test case.</p>
     * <p><b>Test Case Design</b>: The methoud throws
     * UnsupportedOperationException.</p>
     * <p><b>Test Description</b>: addAll is invoked.</p>
     * <p><b>Pre-Condition</b>: v is empty.</p>
     * <p><b>Post-Condition</b>: v is empty.</p>
     * <p><b>Expected Results</b>: The add method is not supported.
     * UnsupportedOperationException is thrown.</p>
     */
    @Test (expected = UnsupportedOperationException.class)
    public void AddAll_UOE()
    {
        v.addAll(c);
    }

    /**
     * <p><b>Summary</b>: addAll method test case.</p>
     * <p><b>Test Case Design</b>: The methoud throws
     * UnsupportedOperationException.</p>
     * <p><b>Test Description</b>: add is invoked.</p>
     * <p><b>Pre-Condition</b>: v contains {"0":"10"}.</p>
     * <p><b>Post-Condition</b>: v contains {"0":"10"}.</p>
     * <p><b>Expected Results</b>: The add method is not supported.
     * UnsupportedOperationException is thrown.</p>
     */
    @Test (expected = UnsupportedOperationException.class)
    public void AddAll_10UOE()
    {
        initHMap(m, 0, 10);
        v.addAll(c);
    }

	// ------------------------------------------ isEmpty method ------------------------------------------

    /**
     * <p><b>Summary</b>: isEmpty method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case
     * of an empty Values, created from an empty
     * Map.</p>
     * <p><b>Test Description</b>: v is empty, then size,
     * isEmpty and its iterators have been tested.</p>
     * <p><b>Pre-Condition</b>: v is empty.</p>
     * <p><b>Post-Condition</b>: v is unchanged.</p>
     * <p><b>Expected Results</b>: size is 0, isEmpty returns
     * true, iterator.hasNext is false and next throws NSEE</p>
     */
    @Test (expected = NoSuchElementException.class)
    public void IsEmpty_EmptyMapEmptyES()
    {
        assertEquals("Size should be 0", 0, v.size());
        assertTrue("Should be empty", v.isEmpty());

        HIterator it = v.iterator();
        assertFalse("Sould not have next", it.hasNext());
        it.next();  // Exception throw
    }

    /**
     * <p><b>Summary</b>: isEmpty method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case
     * of an Values containing only 1 element. Propagation
     * map -> Values is tested.</p>
     * <p><b>Test Description</b>: v contains {"1"}, while map
     * contains {1="1"}, then size,
     * isEmpty and its iterators have been tested.</p>
     * <p><b>Pre-Condition</b>: v contains {"1"} map
     * contains {1="1"}.</p>
     * <p><b>Post-Condition</b>: v is unchanged, m is unchanged.</p>
     * <p><b>Expected Results</b>: isEmpty returns
     * false, iterator.hasNext is true and second next throws NSEE.
     * The only element in Values is 1, map
     * contains {1="1"}. Getting the only value from the values
     * collection through it.next() should return "1".</p>
     */
    @Test (expected = NoSuchElementException.class)
    public void IsEmpty_OneElement()
    {
        m.put(1, "1");
        assertFalse("Should contain 1 element", v.isEmpty());

        HIterator it = v.iterator();
        assertTrue("Sould have next", it.hasNext());

        Object k = it.next();
        assertEquals("1", k);

        assertFalse("Sould not have next", it.hasNext());
        it.next();  // Exception throw
    }

    // ------------------------------------------ size method ------------------------------------------

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that an empty collection
     * should have a size of zero and isEmpty call returning
     * true.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 0 size (empty).</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the collection.</p>
     * <p><b>Pre-Condition</b>: The collection is empty.</p>
     * <p><b>Post-Condition</b>: The collection is still empty.</p>
     * <p><b>Expected Results</b>: The collection method returns 0 and the isEmpty
     * method returns true.</p>
     */
    @Test
    public void Size_Empty_0()
    {
        assertEquals("Empty Values does not have size of zero.", 0, v.size());
        assertTrue("isEmpty did not returned true.", v.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a Values with 1 element
     * should have a size of 1 and isEmpty call returning
     * false.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 1 size and not being empty. Propagation
     * map -> Values is tested. checkValues and checkIteration
     * are invoked to test Values - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Pre-Condition</b>: The Values contains 1, map contains {1="1"}.</p>
     * <p><b>Post-Condition</b>: The Values contains 1 map contains {1="1"}.</p>
     * <p><b>Expected Results</b>: The size method returns 1 and the isEmpty
     * method returns false. checkValues and checkIteration tests pass.</p>
     */
    @Test
    public void Size_OneElement_1()
    {
        m.put(1, "1");
        checkValues(m, v);
        checkIteration(v);
        assertEquals("Empty Values does not have size of one.", 1, v.size());
        assertFalse("isEmpty did not returned false.", v.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a Values with 3 elements
     * should have a size of 3 and isEmpty call returning
     * false.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 3 size and not being empty. Propagation
     * map -> Values is tested. checkValues(m, es) and checkIteration(es)
     * are invoked to test values - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the Values.</p>
     * <p><b>Pre-Condition</b>: The Values is empty.</p>
     * <p><b>Post-Condition</b>: The Values has 3 elements ({0, 1, 2}).</p>
     * <p><b>Expected Results</b>: The size method returns 3 and the isEmpty
     * method returns false. checkValues and checkIteration tests pass.</p>
     */
    @Test
    public void Size_ThreeElements_3()
    {
        addToHMap(m, 0, 3);
        checkValues(m, v);
        checkIteration(v);
        assertEquals("Values with 3 elements does not have size of 3.", 3, v.size());
        assertFalse("isEmpty did not returned false.", v.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a Values with 3 elements
     * should have a size of 345 and isEmpty call returning
     * false.
     * The Values is modiefied before the asserts.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 345 size and not being empty. Propagation
     * map -> Values is tested. checkValues(m, es) and checkIteration(es)
     * are invoked to test values - map coherence and the iteration.</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the Values.</p>
     * <p><b>Pre-Condition</b>: The Values is empty.</p>
     * <p><b>Post-Condition</b>: The Values contains 345 elements ({0:345}).</p>
     * <p><b>Expected Results</b>: The size method returns 345 and the isEmpty
     * method returns false. checkValues and checkIteration tests pass.</p>
     */
    @Test
    public void Size_345Elements_345()
    {
        addToHMap(m, 0, 345);
        checkValues(m, v);
        checkIteration(v);
        assertEquals("Values with 345 elements does not have size of 345.", 345, v.size());
        assertFalse("isEmpty did not returned false.", v.isEmpty());
    }

    // ------------------------------------------ contains method ------------------------------------------

    /**
     * <p><b>Summary</b>: contains method test case</p>
     * <p><b>Test Case Design</b>: Tests the limit case of
     * a contains method invoke on a empty Values, which
     * is a limit case.</p>
     * <p><b>Test Description</b>: contains is invoked with
     * a random object, v is empty, therefore it should not
     * contain it.</p>
     * <p><b>Pre-Condition</b>: v is empty.</p>
     * <p><b>Post-Condition</b>: v is empty.</p>
     * <p><b>Expected Results</b>: cotnains returns false</p>
     */
    @Test
    public void Contains_Empty()
    {
        assertFalse("Should not contain", v.contains("Random object"));
    }

    
    /**
     * <p><b>Summary</b>: contains method test case</p>
     * <p><b>Test Case Design</b>: Tests if each entry of type
     * (i, "i") is contained in the Values, for i in (0,100).
     * Propagation map -> Values is tested.</p>
     * <p><b>Test Description</b>: contains is invoked 100 times
     * in a for loop. At each iteration a contained entry is generated
     * and checked to be in the Values.</p>
     * <p><b>Pre-Condition</b>: v contains {"0":"100"}, m
     * contains {0="0":100="100"}.</p>
     * <p><b>Post-Condition</b>: v is unchanged.
     * m is unchanged.</p>
     * <p><b>Expected Results</b>: each contains returns true</p>
     */
    @Test
    public void Contains_0To100()
    {
        int bound = 100;
        addToHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
            assertTrue("Should be contained", v.contains(""+i));
    }

    /**
     * <p><b>Summary</b>: contains method test case</p>
     * <p><b>Test Case Design</b>: Tests if the key
     * i + 100 is contained in the Values, for i in (0,100).
     * This is obviusly false for each i, as v contains {"0":"100"}.</p>
     * <p><b>Test Description</b>: contains is invoked 100 times
     * in a for loop. At each iteration a contained entry is generated
     * and checked to be in the Values.</p>
     * <p><b>Pre-Condition</b>: v contains {"0":"100"}, m
     * contains {0="0":100="100"}.</p>
     * <p><b>Post-Condition</b>: v is unchanged.
     * m is unchanged.</p>
     * <p><b>Expected Results</b>: each contains returns false</p>
     */
    @Test
    public void Contains_0To100_DifferentKey()
    {
        int bound = 100;
        addToHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
            assertFalse("Should NOT be contained", v.contains(i + bound));
    }

    /**
     * <p><b>Summary</b>: contains method test case.</p>
     * <p><b>Test Case Design</b>: Tests remove behaviour
     * when the passed argument is null. The method should throw
     * NullPointerException.</p>
     * <p><b>Test Description</b>: v.contains(null) is invoked.</p>
     * <p><b>Pre-Condition</b>: v contains {0:10}.</p>
     * <p><b>Post-Condition</b>: v contains {0:10}.</p>
     * <p><b>Expected Results</b>: NPE is thrown.</p>
     */
    @Test(expected = NullPointerException.class)
    public void Contains_Null()
    {
        initHMap(m, 0, 10);
        v.contains(null);
    }

    // ------------------------------------------ containsAll method ------------------------------------------


    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The method tests if an empty Values contains the elements
     * of another collection, which is obviusly false.</p>
     * <p><b>Test Case Design</b>: The test case tests the limit case of
     * checking an empty Values containing something.</p>
     * <p><b>Test Description</b>: The collection contains 1, 2 and 3.
     * The containsAll method obviously should return false for
     * any coll's content as the Values is empty.</p>
     * <p><b>Pre-Condition</b>: The Values and map are empty.</p>
     * <p><b>Post-Condition</b>: The Values and map are empty.</p>
     * <p><b>Expected Results</b>: The containsAll method return false.</p>
     */
    @Test
    public void ContainsAll_Empty_False()
    {
        c = getStringHCollection(1, 4);
        assertFalse("The method should return false because the Values is empty.", v.containsAll(c)); 
    }

   /**
     * <p><b>Summary</b>: containsAll method test case.
     * The method tests if an empty Values contains the elements
     * of another collection.</p>
     * <p><b>Test Case Design</b>: The test case tests the limit case of
     * checking an empty Values containing an empty collection, which is true, 
     * as the empty collection is the subcollection of every collection, therefore even of the
     * empty collection. The tested case is a limit case of containsAll.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: The collection is empty.
     * The containsAll method obviously should return true for
     * any coll's content, because the empty collection is the
     * subcollection of every collection.</p>
     * <p><b>Pre-Condition</b>: The Values is empty.</p>
     * <p><b>Post-Condition</b>: The Values is empty.</p>
     * <p><b>Expected Results</b>: The containsAll method return true.</p>
     */
    @Test
    public void ContainsAll_BothEmpty_False()
    {
        assertTrue("The method should return true because the collection is empty.", v.containsAll(c)); 
    }
    
    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The test case tests different containsAll calls
     * with different collection.</p>
     * <p><b>Test Case Design</b>: The tests calls containsAll
     * with some as argument. Propagation map -> Values is tested.</p>
     * <p><b>Test Description</b>: In the test containsAll is called with the
     * collection containing
     * {"1"}, {"10"}, {"3", "4", "5"}, {"1", "5", "10"}.</p>
     * <p><b>Pre-Condition</b>: The Values contains {"0":"10"}, the map
     * contains {0="0":10="10"}.</p>
     * <p><b>Post-Condition</b>: The Values contains {0:10}, the map
     * contains {0="0":10="10"}.</p>
     * <p><b>Expected Results</b>: Every containsAll calls return true.</p>
     */
    @Test
    public void ContainsAll_0to11_True()
    {
        addToHMap(m, 0, 11);

        // {1 as key}
        c = getHCollection(new Object[]{"1"});
        assertTrue("The Values contains 1=1.", v.containsAll(c));
        
        // {10 as key}
        c = getHCollection(new Object[]{"10"});
        assertTrue("The Values contains 10=10.", v.containsAll(c));

        // {3, 4, 5 as values}
        c = getStringHCollection(3, 6);
        assertTrue("The Values contains 3=3, 4=4 and 5=5.", v.containsAll(c));

        // {1, 5, 10}
        c = getHCollection(new Object[]{"1", "5", "10"});
        assertTrue("The Values contains 1, 5 and 10.", v.containsAll(c));
    }

    /**
     * <p><b>Summary</b>: containsAll method test case.
     * Tests if different combination are contained in the Values.
     * Each containsAll call returns false.</p>
     * <p><b>Test Case Design</b>: Different scenario are tested where
     * containsAll should return false. Calling containsAll with arguments
     * not present in the Values is a common case for the method. Propagation
     * map -> Values is tested.</p>
     * <p><b>Test Description</b>: The first containsAll takes as argument
     * a collection containing a single element, not present in the
     * Values. The second one takes as argument a collection containing element
     * in the Values e one not contained in the Values: therefore, the call
     * must return false as one of the collection is not contained.</p>
     * <p><b>Pre-Condition</b>: The Values contains {"0":"10"}, the map
     * contains {0="0":10="10"}.</p>
     * <p><b>Post-Condition</b>: The Values contains {"0":"10"}, the map
     * contains {0="0":10="10"}.</p>
     * <p><b>Expected Results</b>: All containsAll calls return true,
     * last containsAll returns false as 12 is not contained
     * in the Values..</p>
     */
    @Test
    public void ContainsAll_0to11_False()
    {
        addToHMap(m, 0, 11);

        c = getHCollection(new Object[]{"11"});
        assertFalse("11=11 is not contained.", v.containsAll(c));

        c = getHCollection(new Object[]{"3", "4", "5", "12"});
        assertFalse("3, 4 and 5 are contained. 12 is not contained.", v.containsAll(c));
    }

    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The test case tries to call containsAll with a null
     * collection on an empty Values.</p>
     * <p><b>Test Case Design</b>: Tests the method with null arguments, which is a special
     * case of invalid argument.</p>
     * <p><b>Test Description</b>: Calls method with null collection.</p>
     * <p><b>Pre-Condition</b>: Values is empty, coll is null.</p>
     * <p><b>Post-Condition</b>: Values is empty, coll is null.</p>
     * <p><b>Expected Results</b>: NullPointerException thrown.</p>
     */    
    @Test(expected = java.lang.NullPointerException.class)
    public void ContainsAll_NullCollection_NPE()
    {
        v.containsAll(null);
    }
    
    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The test case tries to call containsAll with a null
     * collection on an values.</p>
     * <p><b>Test Case Design</b>: Tests the method with null arguments, which is a special
     * case of invalid argument.</p>
     * <p><b>Test Description</b>: Calls method with null collection.</p>
     * <p><b>Pre-Condition</b>: values contains {0:10}, coll is null.</p>
     * <p><b>Post-Condition</b>: values contains {0:10}, coll is null.</p>
     * <p><b>Expected Results</b>: NullPointerException thrown.</p>
     */    
    @Test(expected = java.lang.NullPointerException.class)
    public void ContainsAll_NullCollection_NPE_NotEmpty()
    {
        initHMap(m, 0, 10);
        v.containsAll(null);
    }
    // ------------------------------------------ equals method ------------------------------------------

    /**
     * <p><b>Summary</b>: equals method test case.</p>
     * <p><b>Test Case Design</b>: equals method is tested with an equal 
     * and a bigger and a smaller one. The returned values should be true and false.
     * Also reflective property of equal is tested.</p>
     * <p><b>Test Description</b>: Values is initialized, then different equals invoke are
     * asserted with different arguments, generated for each case.</p>
     * <p><b>Pre-Condition</b>: map contains {0="0" : 200="200"},
     * therefore v contains {"0":"200"}.</p>
     * <p><b>Post-Condition</b>: Values is unchanged.</p>
     * <p><b>Expected Results</b>: The Values is unchanged and symmetric property is valid.</p>
     */
    @Test
    public void Equals_0To200()
    {
        int to = 200;
        addToHMap(m, 0, to);
        assertTrue("Should equal", v.equals(getIntegerMapAdapter(0, to).values()));
        assertTrue("Should equal", getIntegerMapAdapter(0, to).values().equals(v));   // Symmetric property
        assertFalse("Should NOT equal", v.equals(getIntegerMapAdapter(0, to + 15).values()));  // bigger Values returns false
        assertFalse("Should NOT equal", v.equals(getIntegerMapAdapter(0, 5).values()));  // smaller Values returns false
    }

    
    /**
     * <p><b>Summary</b>: equals method test case.
     * The test case the method behaviour with 2 empty Values.</p>
     * <p><b>Test Case Design</b>: When both Valuess are empty the equals
     * method should return true because an empty Values is equal to an
     * empty Values.</p>
     * <p><b>Test Description</b>: Single assert, l1.equals(l2) invoked.</p>
     * <p><b>Pre-Condition</b>: Both Values are empty.</p>
     * <p><b>Post-Condition</b>: Both Values are empty.</p>
     * <p><b>Expected Results</b>: equals returns true, as one
     * empty Values of course equals another empty Values.</p>
     */
    @Test
    public void Equals_Empty_True()
    {
        assertTrue("Two empty Valuess should equals.", v.equals(v2));
        assertTrue("Two empty Valuess should equals.", v2.equals(v));
    }

    /**
     * <p><b>Summary</b>: equals method test case.
     * The reflective property of equal method is tested.</p>
     * <p><b>Test Case Design</b>: equals method should be reflective,
     * therefore x.equals(x) should always return true. Propagation
     * map -> Values is tested through initHMap.</p>
     * <p><b>Test Description</b>: The test invokes v.equals(v) when
     * v is empty, when it has 10 elements and when it has 100 elements.</p>
     * <p><b>Pre-Condition</b>: Values is not null.</p>
     * <p><b>Post-Condition</b>: Values has 100 elements. </p>
     * <p><b>Expected Results</b>: Values equals itself, therefore
     * reflective property is valid.</p>
     */
    @Test
    public void Equals_Reflective()
    {
        assertTrue("Reflective property is not met.", v.equals(v));    // Values is empty
        initHMap(m, 0, 10);
        assertTrue("Reflective property is not met.", v.equals(v));    // Values is not empty, should return true anyways
        initHMap(m, 0, 100);
        assertTrue("Reflective property is not met.", v.equals(v));    // Values is not empty, should return true anyways
    }

    /**
     * <p><b>Summary</b>: equals method test case.
     * The transitive property of equal method is tested.</p>
     * <p><b>Test Case Design</b>: equals method should be transitive,
     * therefore a.equals(b) and b.equals(c) {@literal =>} a.equals(c).
     * Propagation map -> Values is tested.</p>
     * <p><b>Test Description</b>: The test invokes v.equals(v2) and v2.equals(Values3)
     * and v.equals(v3)</p>
     * <p><b>Pre-Condition</b>: Values contain {"1" : "200"}.</p>
     * <p><b>Post-Condition</b>: Values are unchanged. </p>
     * <p><b>Expected Results</b>: Equals has transitive property.</p>
     */
    @Test
    public void Equals_Transitive()
    {
        int to = 200;
        addToHMap(m, 0, to);
        addToHMap(m2, 0, to);
        HCollection v3 = getIntegerMapAdapter(0, to).values();

        assertTrue("Valuess should be equal.", v.equals(v2));
        assertTrue("Valuess should be equal.", v2.equals(v3));
        assertTrue("Transitive property is not met.", v.equals(v3));
    }

    /**
     * <p><b>Summary</b>: equals method test case.</p>
     * <p><b>Test Case Design</b>: equals method, when
     * invoked with a null argument, should just
     * return false.</p>
     * <p><b>Test Description</b>: v.equals(null)
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: v is empty</p>
     * <p><b>Post-Condition</b>: v is empty</p>
     * <p><b>Expected Results</b>: v.equals(null) returns false.</p>
     */
    @Test
    public void Equals_NullEmpty()
    {
        assertFalse(v.equals(null));
    }
	
    /**
     * <p><b>Summary</b>: equals method test case.</p>
     * <p><b>Test Case Design</b>: equals method, when
     * invoked with a null argument, should just
     * return false.</p>
     * <p><b>Test Description</b>: v.equals(null)
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: v contains {"0":"10"}.</p>
     * <p><b>Post-Condition</b>: v contains {"0":"10"}.</p>
     * <p><b>Expected Results</b>: v.equals(null) returns false.</p>
     */
    @Test
    public void Equals_NullNotEmpty()
    {
        initHMap(m, 0, 10);
        assertFalse(v.equals(null));
    }

    // ------------------------------------------ clear method ------------------------------------------

    /**
     * <p><b>Summary</b>: clear, containsKey, containsValue, get method test case.</p>
     * <p><b>Test Case Design</b>: Tests the behaviour of clear method of Values
     * and of map. Tests the backing map -> Values and viceversa, Values -> map.
     * checkValues(m, es) and checkIteration(es)
     * are invoked to test values - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: map is initialized with {0="0" : 500="500"},
     * therefore v contains {"0":"500"}.
     * Through v iterators iterates through the elements to assert that they both
     * share the same informations about the map entries. Then clear is invoked
     * by the Values. Same initialization and iteration are done,
     * but this time clear is invoked by m.</p>
     * <p><b>Pre-Condition</b>: m and v are empty.</p>
     * <p><b>Post-Condition</b>: m and v are still empty.</p>
     * <p><b>Expected Results</b>: clear propagates successfully from map to Values
     * and viceversa.</p>
     */
    @Test
    public void Clear_Backing0()
    {
        addToHMap(m, 0, 500);
        assertEquals("Size should be 500", 500, m.size());
        assertEquals(m.size(), v.size());
        checkIteration(v);
        checkValues(m, v);
        v.clear();
        assertTrue("Should both have size 0", (m.size() == v.size()) && v.size() == 0);
        assertTrue("Should be both empty", m.isEmpty() && v.isEmpty());

        addToHMap(m, 0, 500);
        assertEquals("Size should be 500", 500, m.size());
        checkIteration(v);
        checkValues(m, v);
        m.clear();  // Invoked from m this time
        assertTrue("Should both have size 0", (m.size() == v.size()) && v.size() == 0);
        assertTrue("Should be both empty", m.isEmpty() && v.isEmpty());
    }

    /**
     * <p><b>Summary</b>: clear method test case.</p>
     * <p><b>Test Case Design</b>: Tests the behaviour of clear method of Values
     * and map when they both are empty, which is a limit case (obviusly the limit case is that
     * they are empty, not that they have the same size, as it is trivial).
     * checkValues(m, es) and checkIteration(es)
     * are invoked to test values - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: clear is invoked by m and they are checked through checkValues,
     * same then but clear is invoked by the Values.</p>
     * <p><b>Pre-Condition</b>: m and v are empty</p>
     * <p><b>Post-Condition</b>: m and v are empty</p>
     * <p><b>Expected Results</b>: clear propagates successfully from map to Values
     * and viceversa.</p>
     */
    @Test
    public void Clear_Empty()
    {
        m.clear();
        checkValues(m, v);
        checkIteration(v);
        v.clear();
        checkValues(m, v);
        checkIteration(v);
    }
    
    // ------------------------------------------ hashCode method ------------------------------------------

    /**
     * <p><b>Summary</b>: hashCode test case.
     * Tests the behaviour of hashCode method with different
     * map and Values configurations.</p>
     * <p><b>Test Case Design</b>: The same operations are applied to map 1 and 2,
     * so their Valuess must have the same elements each time, therefore they are equals.
     * If they are equals they must have the same hashCode. Changes propagates successfully from map to Values
     * and viceversa, tests therefore the consinstency of equals and hashCode.</p>
     * <p><b>Test Description</b>: Different configurations have been tested. In mathematical symbols, considering the values:
     * empty, {1}, {-100:100}, {-100:100}/{0}, {-100:101}/{0}, {-100:100, 500:1000}/{0},
     * {-100:100, 500:1000} U {-1000:-900}/({0} ), {-100:100, 500:1000, 5000:6000}  U {-1000:-900}/({0})</p>
     * <p><b>Pre-Condition</b>: Values have same hashCode and they are equal.</p>
     * <p><b>Post-Condition</b>: Values have same hashCode and they are equal.</p>
     * <p><b>Expected Results</b>: Valuess have same hashCode and they are equal.</p>
     */
    @Test
    public void HashCode_Mixed()
    {
        // Empty map case
        assertTrue("Maps should be equal.", v.equals(v2));
        assertEquals("Hash codes should be equal.", v.hashCode(), v2.hashCode());

        // One element case
        m.put(1, "1");
        m2.put(1, "1");
        assertTrue("Maps should be equal.", v.equals(v2));
        assertEquals("Hash codes should be equal.", v.hashCode(), v2.hashCode());

        initHMap(m, -100, 100);
        initHMap(m2, -100, 100);
        assertTrue("Maps should be equal.", v.equals(v2));
        assertEquals("Hash codes should be equal.", v.hashCode(), v2.hashCode());

        v.remove((Object)0);
        v2.remove((Object)0);
        assertTrue("Maps should be equal.", v.equals(v2));
        assertEquals("Hash codes should be equal.", v.hashCode(), v2.hashCode());

        m.put(101, "101");
        m2.put(101, "101");
        assertTrue("Maps should be equal.", v.equals(v2));
        assertEquals("Hash codes should be equal.", v.hashCode(), v2.hashCode());

        addToHMap(m, 500, 1000);
        addToHMap(m2, 500, 1000);
        assertTrue("Maps should be equal.", v.equals(v2));
        assertEquals("Hash codes should be equal.", v.hashCode(), v2.hashCode());

        HMap t = getIntegerMapAdapter(-1000, -900);
        m.putAll(t);
        m2.putAll(t);
        assertTrue("Maps should be equal.", v.equals(v2));
        assertEquals("Hash codes should be equal.", v.hashCode(), v2.hashCode());

        initHMap(t, 5000, 6000);
        m.putAll(t);
        m2.putAll(t);
        assertTrue("Maps should be equal.", v.equals(v2));
        assertEquals("Hash codes should be equal.", v.hashCode(), v2.hashCode());
    }

    // ------------------------------------------ remove method ------------------------------------------
    /**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests remove behaviour
     * when the passed argument is null. The method should throw
     * NullPointerException.</p>
     * <p><b>Test Description</b>: v.remove(null) is invoked.</p>
     * <p><b>Pre-Condition</b>: v contains {"0":"10"}.</p>
     * <p><b>Post-Condition</b>: v contains {"0":"10"}.</p>
     * <p><b>Expected Results</b>: NPE is thrown.</p>
     */
    @Test(expected = NullPointerException.class)
    public void Remove_Null()
    {
        initHMap(m, 0, 10);
        v.remove(null);
    }
    
    /**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests remove method invoked by map
     * and its Values and checv their consistency in propagation.
     * They both are empty, which is a limit case.
     * checkValues(m, es) and checkIteration(es)
     * are invoked to test values - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: remove is invoked by map, m and collection
     * are checked, remove is invoked by Values and m and collection are checked again.</p>
     * <p><b>Pre-Condition</b>: m and v are empty.</p>
     * <p><b>Post-Condition</b>: m and v are unchanged.</p>
     * <p><b>Expected Results</b>: m.remove returns null object,
     * while v.remove returns false. map and v pass the check
     * after each remove invoke.</p>
     */
    @Test
    public void Remove_Empty()
    {
        assertNull(m.remove("Random Key"));
        checkValues(m, v);
        checkIteration(v);
        assertFalse(v.remove("Random Object"));
        checkValues(m, v);
        checkIteration(v);
    }

    /**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests remove method invoked by map
     * and its Values and checv their consistency in propagation.
     * Map contains 10 elements, and arguments are values/entries not
     * in the map/collection. checkValues(m, es) and checkIteration(es)
     * are invoked to test values - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: remove is invoked by map, m and collection
     * are checked, remove is invoked by Values and m and collection are checked again.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0", 10="10"},
     * therefore v contains {"0":"10"}.</p>
     * <p><b>Post-Condition</b>: m and v are unchanged.</p>
     * <p><b>Expected Results</b>: m.remove returns null object,
     * while v.remove returns false. map and v pass the check
     * after each remove invoke.</p>
     */
    @Test
    public void Remove_NotInMap()
    {
        addToHMap(m, 0, 10);
        assertNull(m.remove("Not in map Key"));
        checkValues(m, v);
        checkIteration(v);
        assertFalse(v.remove("Not in Values Entry"));
        checkValues(m, v);
        checkIteration(v);
    }

    /**
     * <p><b>Summary</b>: remove method test case.
     * remove is tested to make changes from map and
     * from collection, propagating removals from one to another.</p>
     * <p><b>Test Case Design</b>: After each removals and
     * modification to map and collection's structures are checked
     * by checkValues. Test aims to show the correct propagation
     * of information. checkValues(m, es) and checkIteration(es)
     * are invoked to test values - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: Entries (i, "i") are inserted and removed
     * from the map remove. The map is initiated with {0="0", 100="100"},
     * therefore v contains {"0":"100"}
     * and each element in map and Values is removed by map's remove.
     * The map is initiated with {0="0": 100="100"},
     * and each element in map and Values is removed by Values's remove.
     * </p>
     * <p><b>Pre-Condition</b>: map and collection are empty.</p>
     * <p><b>Post-Condition</b>: map and collection are empty.</p>
     * <p><b>Expected Results</b>: Each removal is correctly propagated
     * from map to Values and from Values to map. In particular,
     * they still contains coherent informations.</p>
     */
    @Test
    public void Remove_Backing0()
    {
        int bound = 100;
        for (int i = 0; i < bound; i++)
        {
            m.put(i, "" + i);
            checkValues(m, v);
            checkIteration(v);
            assertEquals("Should be removed", ""+i, m.remove(i));
            checkValues(m, v);
            checkIteration(v);
        }
        initHMap(m, 0, bound);
        checkValues(m, v);
        checkIteration(v);
        for (int i = bound - 1; i >= 0; i--)
        {
            assertEquals("Should be removed", ""+i, m.remove(i));
            checkValues(m, v);
            checkIteration(v);
        }
        assertEquals("Should be empty", 0, m.size());
        initHMap(m, 0, bound);
        checkValues(m, v);
        checkIteration(v);
        for (int i = bound - 1; i >= 0; i--)
        {
            assertTrue("Should be removed", v.remove(""+i));
            checkValues(m, v);
            checkIteration(v);
        }
        assertEquals("Should be empty", 0, m.size());
        checkValues(m, v);
        checkIteration(v);
    }

    // ------------------------------------------ removeAll method ------------------------------------------
    /**
     * <p><b>Summary</b>: removeAll method test case. The test aim
     * to test coherence of map and its Values after invoking removeAll
     * in different scenarios and problem size.</p>
     * <p><b>Test Case Design</b>: After each removeAll method invoke
     * the coherence is checked through checkValues. Tests right
     * propagation from the entry collection to the map. Removing all entries
     * from the Values implies removing all the entries in the map.
     * The collection keeps changing during execution. Note that
     * different sizes are tested, as first the size of v is smaller
     * than the size of c, while then the size of v is bigger than the
     * size of c. checkValues(m, es) and checkIteration(es)
     * are invoked to test values - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: The collection contains {0="0":500="500"},
     * while map contains {0="0" : i="i"} and the Values contains 
     * {"0":"i"}, for each i in (1,600)
     * (Note that empty map/Values limit case is being tested). Then
     * v.removeAll(c) is invoked and coherence is check through checkValues.
     * Then the test asserts that m and v have the right size and isEmpty
     * returns the right value.</p>
     * <p><b>Pre-Condition</b>: c contains {0="0":500="500"}, m and v are
     * empty.</p>
     * <p><b>Post-Condition</b>: c is unchanged, m contains
     * {500="500":600="600"}, while v contains {500:600}.</p>
     * <p><b>Expected Results</b>: Each removal is correctly propagated
     * from map to Values and from Values to map. In particular,
     * they still contains coherent informations.</p>
     */
    @Test
    public void RemoveAll_Backing0()
    {
        int bound = 600;
        int secondBound = 500;
        c = getStringHCollection(0, secondBound);
        for (int i = 1; i < bound; i++)
        {
            initHMap(m, 0, i);
            assertTrue("Should return true", v.removeAll(c));
            checkValues(m, v);
            checkIteration(v);

            if (i <= secondBound)
            {
                assertTrue("Both should have size 0", m.size() == v.size() && m.size() == 0);
                assertTrue("Both should be empty", v.isEmpty() == m.isEmpty() && v.isEmpty());
            }
            else
            {
                assertTrue("Both should have size > 0", m.size() == v.size() && m.size() == i - secondBound);
                assertTrue("Both should not be empty", v.isEmpty() == m.isEmpty() && !v.isEmpty());
            }
        }
    }

    /**
     * <p><b>Summary</b>: removeAll method test case.</p>
     * <p><b>Test Case Design</b>: Tests removeAll behaviour
     * when the passed argument is null. The method should throw
     * NullPointerException.</p>
     * <p><b>Test Description</b>: v.removeAll(null) is invoked.</p>
     * <p><b>Pre-Condition</b>: v is empty</p>
     * <p><b>Post-Condition</b>: v is empty</p>
     * <p><b>Expected Results</b>: NPE is thrown.</p>
     */
    @Test(expected = NullPointerException.class)
    public void RemoveAll_EmptyNull()
    {
        v.removeAll(null);
    }

    /**
     * <p><b>Summary</b>: removeAll method test case.</p>
     * <p><b>Test Case Design</b>: Tests removeAll behaviour
     * when the passed argument is empty.
     * The method should just return false, as collection
     * is not modified.</p>
     * <p><b>Test Description</b>: v.removeAll(c) is invoked, then m is
     * cleared and v.removeAll(c) is invoked.</p>
     * <p><b>Pre-Condition</b>: v contains {0="0":10="10"}</p>
     * <p><b>Post-Condition</b>: v is empty.</p>
     * <p><b>Expected Results</b>: false is returned both times,
     * as the collection was not modified..</p>
     */
    @Test
    public void RemoveAll_False()
    {
        initHMap(m, 0, 10);
        assertFalse(v.removeAll(c));
        m.clear();
        assertFalse(v.removeAll(c));
    }

    /**
     * <p><b>Summary</b>: removeAll method test case.</p>
     * <p><b>Test Case Design</b>: Tests removeAll behaviour
     * when the passed argument is null. The method should throw
     * NullPointerException.</p>
     * <p><b>Test Description</b>: v.removeAll(null) is invoked.</p>
     * <p><b>Pre-Condition</b>: v contains {0="0":10="10"}.</p>
     * <p><b>Post-Condition</b>: v contains {0="0":10="10"}.</p>
     * <p><b>Expected Results</b>: NPE is thrown.</p>
     */
    @Test(expected = NullPointerException.class)
    public void RemoveAll_NotEmptyNull()
    {
        initHMap(m, 0, 10);
        v.removeAll(null);
    }

    /**
     * <p><b>Summary</b>: removeAll method test case.
     * Tests removeAll method correct behaviour
     * and propagation from map to keySet and
     * viceversa. Coherence is also checked through
     * checkValues(m, es) and checkIteration(es).</p>
     * <p><b>Test Case Design</b>: Tests removeAll method with
     * values HCollection. Correct propagation is tested in both ways.
     * </p>
     * <p><b>Test Description</b>: m contains the
     * entries A=a, B=b, C=c, D=d, D2=d. The HCollection
     * c is initialized with a, c and c. That means,
     * invoking es.removeAll(c) will remove A=a, C=c, D=d AND D2=d:
     * it will remove both d's in values, therefore will
     * remove the whole entry from the HMap for propagation.
     * The test asserts that afore mentioned entries are
     * removed, while the remaining ones are still in the
     * HMap and in the keySet.</p>
     * <p><b>Pre-Condition</b>: m contains A=a, B=b, C=c, D=d, D2=d,
     * ks contains A, B, C, D, D2.</p>
     * <p><b>Post-Condition</b>: m contains B=b, ks contains B.</p>
     * <p><b>Expected Results</b>: m contains B=b, D2=d.
     * ks contains B.
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
            checkValues(m, v);
            checkIteration(v);
        }

        c = getHCollection(new Object[]{"a", "c", "d"});
        
        for (int i = 0; i < arg_k.length; i++)
        {
            assertTrue("Should be contained", v.contains(arg_v[i]));
            assertTrue("Should be contained", m.containsKey(arg_k[i]) && m.containsValue(arg_v[i]));
            assertTrue(m.get(arg_k[i]).equals(arg_v[i]));
        }

        
        assertTrue("Should be removed", v.removeAll(c));

        System.out.println(v);

        assertTrue(m.size() == v.size() && m.size() == 1);

        assertFalse("Should NOT be contained", v.contains("a"));
        assertTrue("Should NOT be contained", !m.containsKey("A") && !m.containsValue("a"));
        assertNull("Should be null", m.get("A"));

        assertTrue("Should be contained", v.contains("b"));
        assertTrue("Should be contained", m.containsKey("B") && m.containsValue("b"));
        assertTrue("Should match", m.get("B").equals("b"));

        assertFalse("Should NOT be contained", v.contains("c"));
        assertTrue("Should NOT be contained", !m.containsKey("C") && !m.containsValue("c"));
        assertNull("Should be null", m.get("C"));

        assertFalse("Should NOT be contained", v.contains("d"));
        assertTrue("Should NOT be contained", !m.containsKey("D") && !m.containsValue("d"));
        assertNull("Should be null", m.get("D"));
        assertTrue("Should be contained", !m.containsKey("D2") && !m.containsValue("d"));
        assertNull("Should match", m.get("D2"));  
    }

    /**
     * <p><b>Summary</b>: removeAll method test case.
     * Tests removeAll when the invoking object contains
     * duplicated objects (which is possible for an instance
     * of interface HCollection as the HCollection returned
     * from HMap.values()).</p>
     * <p><b>Test Case Design</b>: removeAll should remove
     * every instance in the invoking object contained in the
     * HCollection argument. That is, if v contains two "Random string"
     * and the Object "Random string" is contained in c, both
     * istances of "Random string" in v should be removed. This feature
     * is tested in this test. checkIteration(v) and checkValues(m, v)
     * are invoked to assert correct propagation and check coherency.</p>
     * <p><b>Test Description</b>: c is initialized with {0, 1, 2, 5, 7}.
     * m is initialized with {0=1,1=1,2=1,3=2,4=2,5=2,6=3,7=3,8=3,
     * ...,21=7,22=7,23=7} (keys that way in order to insert the entries,
     * not important in this test), therefore
     * v contains {1,1,1,2,2,2,...,7,7,7}. v.removeAll(c) removes
     * all the object in c from v, therefore v finally contains
     * {3,3,3,4,4,4,6,6,6} and its size is 9.</p>
     * <p><b>Pre-Condition</b>: c contains {0, 1, 2, 5, 7},
     * m contains {0=1,1=1,2=1,3=2,4=2,5=2,6=3,7=3,8=3,...,21=7,22=7,23=7},
     * v contains {1,1,1,2,2,2,...,7,7,7}.</p>
     * <p><b>Post-Condition</b>: m contains {6=3,7=3,8=3,
     * 9=4,10=4,11=4,15=6,16=6,17=6}, v contains {3,3,3,4,4,4,6,6,6}.
     * c is unchanged.</p>
     * <p><b>Expected Results</b>: m contains {6=3,7=3,8=3,
     * 9=4,10=4,11=4,15=6,16=6,17=6}, v contains {3,3,3,4,4,4,6,6,6}.
     * removeAll works correctly and propagation worked correctly.
     * checkIteration(v) and checkValues(m, v)
     * assert correct propagation and check coherency.
     * </p>
     */
    @Test
    public void RemoveAll_Duplicates0()
    {
        c = getHCollection(new Object[]{0, 1, 2, 5, 7});
        int keyCounter = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                m.put(keyCounter, i);
                checkIteration(v);
                checkValues(m, v); 
                keyCounter++;
            }
        }

        assertTrue("Should be modified", v.removeAll(c));
        checkIteration(v);
        checkValues(m, v);

        for (int i = 0; i < 8; i++)
        {
            // If it is in c <=> it is removed
            if (c.contains(i))
            {
                assertFalse("Should NOT be contained", v.contains(i));
                assertFalse("Should NOT be contained", m.containsValue(i));
            }
            else
            {
                assertTrue("Should be contained", v.contains(i));
                assertTrue("Should be contained", m.containsValue(i));
            }
        }
        assertTrue("Size should be 9", m.size() == v.size() && v.size() == 9);
    }

    // ------------------------------------------ retainAll method ------------------------------------------

    /**
     * <p><b>Summary</b>: retainAll method test case. The test aim
     * to test coherence of map and its Values after invoking retainAll
     * in different scenarios and problem size.</p>
     * <p><b>Test Case Design</b>: After each retainAll method invoke
     * the coherence is checked through checkValues. Tests right
     * propagation from the entry collection to the map. Retaining all entries
     * from the Values implies retaining all the entries in the map.
     * The collection keeps changing during execution.
     * checkValues(m, es) and checkIteration(es)
     * are invoked to test values - map coherence and the iteration.</p>
     * <p><b>Test Description</b>: The collection contains {0="0":500="500"},
     * while map contains {0="0" : i="i"}
     * and the Values contains {"0":"i"}, for each i in (0,600)
     * (Note that empty map/Values limit case is being tested). Then
     * v.retainAll(c) is invoked and coherence is check through checkValues.
     * Then the test asserts that m and v have the right size and isEmpty
     * returns the right value.</p>
     * <p><b>Pre-Condition</b>: c contains {0="0":500="500"}, m and v are
     * empty.</p>
     * <p><b>Post-Condition</b>: c is unchanged, m contains
     * {0="0":500="500"} and v contains {"0":"500"}.</p>
     * <p><b>Expected Results</b>: Each retainAll is correctly propagated
     * from map to Values and from Values to map. In particular,
     * they still contains coherent informations.</p>
     */
    @Test
    public void RetainAll_Backing0()
    {
        int bound = 600;
        int secondBound = 500;
        c = getStringHCollection(0, secondBound);
        for (int i = 0; i < bound; i++)
        {
            initHMap(m, 0, i);
            v.retainAll(c);
            checkValues(m, v);
            checkIteration(v);

            if (i <= secondBound)
                assertTrue("Both should have size " + i, m.size() == v.size() && m.size() == i);
            else
                assertTrue("Both should have size 500", m.size() == v.size() && m.size() == 500);
        }
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * retainAll is being called with an empty collection as an argument,
     * therefore the values should became the empty set, since mainteining
     * only the "empty" subset means deleting all the elements.</p>
     * <p><b>Test Case Design</b>: retainAll being called with the limit case of
     * an empty collection as an argument.
     * checkValues(m, es) and checkIteration(es)
     * are invoked to test values - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: The values removes all but "empty", so
     * it empties. In fact initially it contains the values {"1", "2", "3"}</p>
     * <p><b>Pre-Condition</b>: The values contains the values {"1", "2", "3"}.</p>
     * <p><b>Post-Condition</b>: The values is empty.</p>
     * <p><b>Expected Results</b>: The values is empty, retainAll returns true
     * because the values has changed. Coherence is checked through
     * checkValues.</p>
     */
    @Test
    public void RetainAll_Empty_True()
    {
        initHMap(m, 1, 4);
        assertTrue("The set has changed, it should return true.", v.retainAll(c));
        assertEquals("set should be empty.", 0, v.size());
        assertEquals("coll should be empty.", 0, c.size());
        checkValues(m, v);
        checkIteration(v);
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * retainAll is being called with an empty collection as an argument and
     * an empty values as object,
     * therefore the values should remain the empty values, since mainteining
     * only the "empty" subset means deleting all the elements. Unlike the
     * RetainAll_Empty_True test case, the values is already empty, therefore
     * the method returns false.</p>
     * <p><b>Test Case Design</b>: retainAll being called with the limit case of
     * an empty collection as an argument and an empty set.</p>
     * <p><b>Test Description</b>: The values removes all but "empty", so
     * it empties.</p>
     * <p><b>Pre-Condition</b>: The values is empty.</p>
     * <p><b>Post-Condition</b>: The values is still empty.</p>
     * <p><b>Expected Results</b>: retainAll returns false because values set is unchanged.</p>
     */
    @Test
    public void RetainAll_Empty_False()
    {
        assertFalse("The set has not changed, it should return false.", v.retainAll(c));
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * The test calls retainAll with a collection containing
     * few elements.</p>
     * <p><b>Test Case Design</b>: The retainAll method is tested with small
     * input. Testing a typical case.
     * checkValues(m, es) and checkIteration(es)
     * are invoked to test values - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: The values initially contains numbers in string
     * representation
     * from 1 to 5 included. retainAll is called with a collection
     * containing {"3", "4", "5"}, therefore the values should contain
     * {"3", "4", "5"}.</p>
     * <p><b>Pre-Condition</b>: The values contains {"1", ..., "5"}, c contains
     * {"3", "4", "5"}.</p>
     * <p><b>Post-Condition</b>: The values contains {"3", "4", "5"}, c contains
     * {"3", "4", "5"}.</p>
     * <p><b>Expected Results</b>: set contains {"3", "4", "5"}, values has changed. retainAll returns true
     * because the values has changed. Coherence is checked through
     * checkvalues</p>
     */
    @Test
    public void RetainAll_12345()
    {
        initHMap(m, 1, 6);
        c = getStringHCollection(3, 6);
        assertTrue("The set has changed, it should return true.", v.retainAll(c));
        assertTrue("set should contain {3, 4, 5}.", v.equals(TestUtilities.getStringHCollection(3, 6)));
        checkValues(m, v);
        checkIteration(v);
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * The test calls retainAll with a collection containing
     * few elements. Testing a typical case.</p>
     * <p><b>Test Case Design</b>: The retainAll method is tested with small
     * input. checkValues(m, es) and checkIteration(es)
     * are invoked to test values - map coherence and the iteration.</p>
     * <p><b>Test Description</b>: The values initially contains numbers
     * from 1 to 9 included. retainAll is called with a collection
     * containing {"2", "3"}, therefore the values should contain the values
     * {"2", "3"}. Tests propagation.</p>
     * <p><b>Pre-Condition</b>: The values contains {"1", ..., "9"}, c contains
     * {"2", "3"}.</p>
     * <p><b>Post-Condition</b>: The values contains {"2", "3"}, c contains
     * {"2", "3"}.</p>
     * <p><b>Expected Results</b>: values contains {"2", "3"}, svalueset has changed.
     * retainAll returns true
     * because the set has changed. Coherence is checked through
     * checkValues</p>
     */
    @Test
    public void RetainAll_23()
    {
        initHMap(m, 1, 10);
        c = getHCollection(new Object[]{"2", "3"});
        assertTrue("The set has changed, it should return true.", v.retainAll(c));
        assertTrue("set should contain {2, 3}.", v.equals(TestUtilities.getStringHCollection(2, 4)));
        checkValues(m, v);
        checkIteration(v);
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * The test calls retainAll with a collection containing
     * many elements. Testing a typical case with a large input.</p>
     * <p><b>Test Case Design</b>:  The retainAll method is tested with large
     * input. The case is still a common case. checkValues(m, es) and checkIteration(es)
     * are invoked to test values - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: The set initially contains numbers
     * from 1 to 699 included in string representation. retainAll is called with a collection
     * containing {"300", ..., "599"}, therefore the set should contain
     * {"300", "599"}.</p>
     * <p><b>Pre-Condition</b>: The values contains {"1", ..., "699"}, c contains
     * {"300", ..., "599"}.</p>
     * <p><b>Post-Condition</b>: The values contains {"300", ..., "599"}, c contains
     * {"300", ..., "599"}.</p>
     * <p><b>Expected Results</b>: The sets are equal, therefore values contains {"300":"600"}.
     * retainAll returns true
     * as the set is being modified. Coherence is checked through
     * checkvalues</p>
     */
    @Test
    public void RetainAll_700()
    {
        initHMap(m, 1, 700);
        c = getStringHCollection(300, 600);
        v.retainAll(c);
        assertTrue("The sets should match.", v.equals(getStringHCollection(300, 600)));
        checkValues(m, v);
        checkIteration(v);
    }

	/**
     * <p><b>Summary</b>: retainAll method test case.
     * retainAll is being called with collection containing element
     * not present in the values as an argument,
     * therefore the values should became the empty set, since mainteining
     * only a subset not contained in the set means deleting all the elements.</p>
     * <p><b>Test Case Design</b>: retainAll being called with the limit case of
     * an empty intersection of values and c. checkValues(m, es) and checkIteration(es)
     * are invoked to test values - map coherence and the iteration.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: The values removes all but "empty", so
     * it empties. In fact initially it contains {"1", ..., "20"}</p>
     * <p><b>Pre-Condition</b>: The values contains {"1", ..., "20"}.</p>
     * <p><b>Post-Condition</b>: The values is empty.</p>
     * <p><b>Expected Results</b>: The values is empty, retainAll returns true
     * because the set changes. Coherence is checked through
     * checkvalues</p>
     */
    @Test
    public void RetainAll_ToEmpty()
    {
        initHMap(m, 1, 21);
        c = getStringHCollection(21, 24);

        assertTrue("The set has changed, it should return true.", v.retainAll(c));
        assertEquals("The set should be empty.", 0, v.size());
        checkValues(m, v);
        checkIteration(v);
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * retainAll is being called with collection containing element
     * the same element of the invoking collection, therefore the
     * collection should remain unchanged and retainAll(c) invoke
     * should return false.</p>
     * <p><b>Test Case Design</b>: retainAll being called with the special case of
     * the collection to contain the same elements. Tests propagation.</p>
     * <p><b>Test Description</b>: The collection contains {"0":"i"}
     * for i in (0, 100). retainAll(c) is invoked and then collection is unchanged.
     * Through checkValues(m, es) and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     * <p><b>Pre-Condition</b>: The collection is empty.</p>
     * <p><b>Post-Condition</b>: The collection contains {"0":"100"}.</p>
     * <p><b>Expected Results</b>: retainAll returns false
     * because the collection is unchanged. Coherence is checked after each retainAll invoke.
     * Through checkValues(m, es) and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void RetainAll_Same()
    {
        int bound = 100;
        for (int i = 0; i < bound; i++)
        {
            initHMap(m, 0, i);
            assertFalse(v.retainAll(getStringHCollection(0, i)));
            checkIteration(v);
            checkValues(m, v);;
        }
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * The method is being tested with a collection containing
     * duplicated elements. This should not change the method
     * behaviour as the absence of one element in c removes
     * all its occurencies in the values.</p>
     * <p><b>Test Case Design</b>: c can contain duplicated element, and
     * this should not change retainAll behaviour. At the end of
     * retainAll execution every element not contained in coll
     * must be removed. Tests propagation.</p>
     * <p><b>Test Description</b>: values contains {"1", ..., "19"}. c contains
     * {"4", "4", "5", "5", "6", "6"}. retainAll is called, so the set should contain
     * {"4", "5", "6"}.</p>
     * <p><b>Pre-Condition</b>: values contains {"1", ..., "19"}. c contains
     * {"4", "4", "5", "5", "6", "6"}.</p>
     * <p><b>Post-Condition</b>: values contains {"4", "5", "6"}. c contains
     * {"4", "4", "5", "5", "6", "6"}.</p>
     * <p><b>Expected Results</b>: The arrays are equal, therefore the
     * values contains {"4", "5", "6"}. retainAll returns true
     * as the set is being modified. Coherence is checked through
     * checkvalues</p>
     */
    @Test
    public void RetainAll_DuplicatesColl()
    {
        initHMap(m, 0, 20);
        c = getHCollection(new Object[]{"4", "4", "5", "5", "6", "6"});
        assertTrue("The set has changed, it should return true.", v.retainAll(c));
        assertTrue("The arrays should match.", TestUtilities.getStringHCollection(4, 7).equals(v));
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.</p>
     * <p><b>Test Case Design</b>: Tests retainAll behaviour
     * when the passed argument is null. The method should throw
     * NullPointerException.</p>
     * <p><b>Test Description</b>: v.retainAll(null) is invoked.</p>
     * <p><b>Pre-Condition</b>: v is empty</p>
     * <p><b>Post-Condition</b>: v is empty</p>
     * <p><b>Expected Results</b>: NPE is thrown.</p>
     */
    @Test(expected = NullPointerException.class)
    public void RetainAll_EmptyNull()
    {
        v.retainAll(null);
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.</p>
     * <p><b>Test Case Design</b>: Tests retainAll behaviour
     * when the passed argument is null. The method should throw
     * NullPointerException.</p>
     * <p><b>Test Description</b>: v.retainAll(null) is invoked.</p>
     * <p><b>Pre-Condition</b>: v contains {0:10}.</p>
     * <p><b>Post-Condition</b>: v contains {0:10}.</p>
     * <p><b>Expected Results</b>: NPE is thrown.</p>
     */
    @Test(expected = NullPointerException.class)
    public void RetainAll_NotEmptyNull()
    {
        initHMap(m, 0, 10);
        v.retainAll(null);
    }

    // ------------------------------------------ toArray method ------------------------------------------

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of v and map is zero, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an empty
     * Values</p>
     * <p><b>Pre-Condition</b>: v and m are empty</p>
     * <p><b>Post-Condition</b>: v and m are empty</p>
     * <p><b>Expected Results</b>: v.toArray returns an empty
     * toArray.</p>
     */
    @Test
    public void ToArray_Empty()
    {
        assertArrayEquals(new Object[0], v.toArray());
    }

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of v and map is 1, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an Values
     * containing only one element, then v and the array returned
     * from toArray are checked through checkToArray.</p>
     * <p><b>Pre-Condition</b>: v and m contains 1 element</p>
     * <p><b>Post-Condition</b>: v and m are unchanged</p>
     * <p><b>Expected Results</b>: v.toArray returns an array
     * of lenght 1 containing only "1", checked through checkToArray.</p>
     */
    @Test
    public void ToArray_OneElement()
    {
        m.put(1, "1");
        checkToArray(v, v.toArray());
    }

    /**
     * <p><b>Summary</b>: toArray and remove method test case.
     * Tests the behaviour of toArray method in different cases
     * and configurations of the Values.</p>
     * <p><b>Test Case Design</b>: Checv the array to be right
     * through checkToArray after each removals through v.remove,
     * creating different situations and cases to test the method.
     * Tests propagation.</p>
     * <p><b>Test Description</b>: The test first removes the
     * entries with even key, then the entries with odd key. After each
     * removal the checkToArray method is invoked to check if the
     * generated array in all of this cases is right.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":100="100"}, therefore
     * v contains {"0":"100"}.</p>
     * <p><b>Post-Condition</b>: m and v are empty</p>
     * <p><b>Expected Results</b>: After each removal the generated array
     * through v.toArray is right and coherent. At the end v and m are empty.</p>
     */
    @Test
    public void ToArray_0To100()
    {
        int bound = 100;
        initHMap(m, 0, 100);
        for (int i = 0; i < bound; i += 2)
        {
            v.remove(""+i);
            checkToArray(v, v.toArray());
        }
        for (int i = 1; i < bound; i += 2)
        {
            v.remove(""+i);
            checkToArray(v, v.toArray());
        }
        assertEquals("Should be empty", 0, v.size());
        assertEquals("Should be empty", 0, m.size());
    }

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of v and map is zero, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an empty
     * Values</p>
     * <p><b>Pre-Condition</b>: v and m are empty</p>
     * <p><b>Post-Condition</b>: v and m are empty</p>
     * <p><b>Expected Results</b>: v.toArray returns an empty
     * toArray.</p>
     */
    @Test
    public void ToArrayArrayArg_Empty()
    {
        Object[] a = new Object[0];
        v.toArray(a);
        assertArrayEquals(new Object[0], a);
    }

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of v and map is 1, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an Values
     * containing only one element, then v and the array returned
     * from toArray are checked through checkToArray.</p>
     * <p><b>Pre-Condition</b>: v and m contains 1 element</p>
     * <p><b>Post-Condition</b>: v and m are unchanged</p>
     * <p><b>Expected Results</b>: v.toArray returns an array
     * of lenght 1 containing only the element 1="1"</p>
     */
    @Test
    public void ToArrayArrayArg_OneElement()
    {
        Object[] a = new Object[1];
        m.put(1, "1");
        v.toArray(a);
        checkToArray(v, a);
    }

    /**
     * <p><b>Summary</b>: toArray and remove method test case.
     * Tests the behaviour of toArray method in different cases
     * and configurations of the Values.</p>
     * <p><b>Test Case Design</b>: Checv the array to be right
     * through checkToArray after each removals through v.remove,
     * creating different situations and cases to test the method.</p>
     * <p><b>Test Description</b>: The test first removes the
     * entries with even key, then the entries with odd key. After each
     * removal the checkToArray method is invoked to check if the
     * generated array in all of this cases is right.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":100="100"}, therefore
     * v contains {"0":"100"}.</p>
     * <p><b>Post-Condition</b>: m and v are empty</p>
     * <p><b>Expected Results</b>: After each removal the generated array
     * through v.toArray is right and coherent. At the end v and m are empty.</p>
     */
    @Test
    public void ToArrayArrayArg_0To100()
    {
        int bound = 100;
        
        initHMap(m, 0, bound);
        for (int i = 0; i < bound; i += 2)
        {
            v.remove(""+i);
            Object[] a = new Object[v.size()];
            v.toArray(a);
            checkToArray(v, a);
        }
        for (int i = 1; i < bound; i += 2)
        {
            v.remove(""+i);
            Object[] a = new Object[v.size()];
            v.toArray(a);
            checkToArray(v, a);
        }
        assertEquals("Should be empty", 0, v.size());
        assertEquals("Should be empty", 0, m.size());
    }

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests toArray behaviour when
     * invoked with a null argument.</p>
     * <p><b>Test Description</b>: v.toArray(null) is invoked.</p>
     * <p><b>Pre-Condition</b>: v is empty.</p>
     * <p><b>Post-Condition</b>: v is empty.</p>
     * <p><b>Expected Results</b>: NullPointerException is being thrown.</p>
     */
    @Test (expected = NullPointerException.class)
    public void ToArrayArrayArg_NullEmpty()
    {
        v.toArray(null);
    }

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests toArray behaviour when
     * invoked with a null argument.</p>
     * <p><b>Test Description</b>: v.toArray(null) is invoked.</p>
     * <p><b>Pre-Condition</b>: v contains {"0":"10"}.</p>
     * <p><b>Post-Condition</b>: v contains {"0":"10"}.</p>
     * <p><b>Expected Results</b>: NullPointerException is being thrown.</p>
     */
    @Test (expected = NullPointerException.class)
    public void ToArrayArrayArg_Null0To10()
    {
        initHMap(m, 0, 10);
        v.toArray(null);
    }

    // ------------------------------------------ iterator method and ValuesIterator ------------------------------------------
    
    /**
     * <p><b>Summary</b>: iteration on an Values test case.</p>
     * <p><b>Test Case Design</b>: Tests the iterator's behaviour
     * on the limit case of an empty Values.</p>
     * <p><b>Test Description</b>: checv if the iterator is right
     * through checkIteration method. In particular, checv
     * if the iterator iterated the right amount of times
     * (checv size coherence) and if after its last iteration
     * (if it happens, otherwise the first) has next.</p>
     * <p><b>Pre-Condition</b>: Values is empty</p>
     * <p><b>Post-Condition</b>: Values is empty</p>
     * <p><b>Expected Results</b>: first iteration has no next,
     * iterated 0 times as the entrylist is empty.</p>
     */
    @Test
    public void ESIterator_Empty()
    {
        checkIteration(v);
    }

    /**
     * <p><b>Summary</b>: iteration on an Values test case.</p>
     * <p><b>Test Case Design</b>: Tests the iterator's behaviour
     * on the limit case of an empty Values.</p>
     * <p><b>Test Description</b>: checv if the iterator is right
     * through checkIteration method. In particular, checv
     * if the iterator iterated the right amount of times
     * (checv size coherence) and if after its last iteration
     * (if it happens, otherwise the first) has next.
     * Then invokes next method on iterator, which should throw
     * NoSuchElementException as the iterator, being empty,
     * has no next.</p>
     * <p><b>Pre-Condition</b>: Values is empty</p>
     * <p><b>Post-Condition</b>: Values is empty</p>
     * <p><b>Expected Results</b>: first iteration has no next,
     * iterated 0 times as the entrylist is empty. NSEE is thrown
     * due to iterator.next() call.</p>
     */
    @Test (expected = NoSuchElementException.class)
    public void ESIterator_EmptyNSEE()
    {
        checkIteration(v);
        v.iterator().next();
    }

    /**
     * <p><b>Summary</b>: iteration on an Values test case.</p>
     * <p><b>Test Case Design</b>: Tests the iterator's behaviour
     * on an Values of variable size, including the size 1 and
     * a much bigger size.</p>
     * <p><b>Test Description</b>: checv if the iterator is right
     * through checkIteration method. In particular, checv
     * if the iterator iterated the right amount of times
     * (checv size coherence) and if after its last iteration
     * (if it happens, otherwise the first) has next.
     * The iteration is checked 1000 times for 1000 different configurations
     * of the HMap. Infact, m contains {0="0":i="i"} for
     * each i in {0:1000}, therefore v contanis {0:i}.</p>
     * <p><b>Pre-Condition</b>: Values and m are empty</p>
     * <p><b>Post-Condition</b>: m contain {0="0":1000="1000"}, therefore
     * v contanis {"0":"i"}</p>
     * <p><b>Expected Results</b>: For each iteration, m containing {0="0":i="i"},
     * the iteration is tested through checkIteration. In particular,
     * iterates i times, for each i in {0:1000}.</p>
     */
    @Test
    public void ESIterator_Variable()
    {
        int bound = 100;
        for (int i = 1; i < bound; i++)
        {
            initHMap(m, 0, i);
            checkIteration(v);
        }
    }

    /**
     * <p><b>Summary</b>: iteration on Values test case.</p>
     * <p><b>Test Case Design</b>: Tests the iterator's remove
     * method behaviour when the Values is empty, which is a limit case.
     * Obviusly a next method would throw NSEE, therefore a remove
     * method, that needs a next invoke before using it (This method can
     * be called only once per
     * call to next) will throw HISE.</p>
     * <p><b>Test Description</b>: remove method is invoked by a generated
     * iterator.</p>
     * <p><b>Pre-Condition</b>: m and v are empty.</p>
     * <p><b>Post-Condition</b>: m and v are empty.</p>
     * <p><b>Expected Results</b>: HIllegalStateException has been thrown
     * by iterator.remove().</p>
     */
    @Test (expected = HIllegalStateException.class)
    public void ESIterator_EmptyRemoveISE()
    {
        v.iterator().remove();
    }

    /**
     * <p><b>Summary</b>: iteration on Values test case.</p>
     * <p><b>Test Case Design</b>: Tests the iterator's remove
     * method behaviour when the map contains {0="0" : 10="10"},
     * therefore the Values contains {0:10}.
     * For the first remove a next is invoked before, which must work
     * normally. The iteration is check through checkIterarion, and the
     * map - Values coherence is checked through checkValues.
     * The second remove is not backed by a next call:
     * a remove
     * method, that needs a next invoke before using it (This method can
     * be called only once per
     * call to next) will throw HISE. Tests propagation.</p>
     * <p><b>Test Description</b>: it invoke next and then remove, which
     * should work normally (no exception thrown here). Then the second
     * remove, which is not backed by any next call, throws
     * HIllegalStateException as remove method can
     * be called only once per
     * call to next.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":10="10"}, therefore
     * v contains {"0":"10"}.</p>
     * <p><b>Post-Condition</b>: m contains {0="0":10="10"}, therefore
     * v contains {"0":"10"}..</p>
     * <p><b>Expected Results</b>: iteration and Values are checked through checkIteration
     * and checkValues after the first remove. HIllegalStateException has been thrown
     * by iterator.remove().</p>
     */
    @Test (expected = HIllegalStateException.class)
    public void ESIterator_0To10RemoveISE()
    {
        initHMap(m, 0, 10);
        it = v.iterator();
        
        it.next();
        it.remove();
        checkValues(m, v);
        checkIteration(v);

        // No previous next
        it.remove();
    }

    /**
     * <p><b>Summary</b>: iteration on Values test case.
     * Tests iterator.remove method on a changing Values,
     * checking Values - map coherence and the iteration
     * with checkValues and checkIteration.</p>
     * <p><b>Test Case Design</b>: The map is constantly
     * changing during execution due to it.remove,
     * therefore coherence and iteration must be check
     * to assure correct propagation iterator -> Values -> map.
     * checkValues(m, es) and checkIteration(es)
     * are invoked to test values - map coherence and the iteration
     * Tests propagation..</p>
     * <p><b>Test Description</b>: map and v initially contain
     * {0="0":100="100"}. An iterator iterates through
     * each element and after each next it invokes the remove
     * method, removing the just returned element.
     * Then checkValues and checkIteration are invoke
     * to check Values - map coherence and iteration.
     * After iterating through all elements, the iterator.hasNext
     * method must return false, and v and m should be both empty.</p>
     * <p><b>Pre-Condition</b>: map contains
     * {0="0":100="100"}, therefore v contains {"0":"100"}</p>
     * <p><b>Post-Condition</b>: m and v are empty.</p>
     * <p><b>Expected Results</b>: Each remove invoke worv right,
     * the element is removed correctly and checkValues and checkIteration
     * work fine after each removal.</p>
     */
    @Test
    public void ESIterator_0To100Remove()
    {
        initHMap(m, 0, 100);
        it = v.iterator();
        
        while (it.hasNext())
        {
            Object k = it.next();
            it.remove();
            assertFalse("Should be removed", m.containsKey(k));
            checkValues(m, v);
            checkIteration(v);
        }
        assertFalse("Should have not next", it.hasNext());
        assertTrue("Should be empty.", v.isEmpty() && m.isEmpty());
    }

    // ------------------------------------------ Coarse grained tests ------------------------------------------
    
    /**
     * <p><b>Summary</b>: iterator.remove and m.remove method test case.
     * Tests the behaviour of map and Values while elements are
     * constantly removed from them via different ways.</p>
     * <p><b>Test Case Design</b>: Tests different ways to remove
     * elements from the backing map and the Values. After each
     * removal checkEntry and checkIteration are invoked to
     * check map - Values coherence and iterator's iteration
     * working correctly. Tests map -> Values propagation and
     * iterator -> Values -> map propagation.</p>
     * <p><b>Test Description</b>: m is filled with entries {0="0":100="100"}.
     * Through a for loop entries {i="i"}, i being 10, 20, 30,...
     * are removed from the map through map.remove method. That means that 100 entries
     * are removed from m and v. Then while the iterator's has next,
     * it removes 1 element through iterator.remove each 10 elements,
     * therefore it removes 9 entries, as the iterator starts iterating
     * the map and v have 90 elements.</p>
     * <p><b>Pre-Condition</b>: m and v are empty.</p>
     * <p><b>Post-Condition</b>: m and v contains 81 entries.</p>
     * <p><b>Expected Results</b>: Each removal from map and v propagates
     * the changes correctly to the other structure. checkValues and checkIteration
     * are invoked to check coherence and that iteration worv correctly.</p>
     */
    @Test
    public void RemoveIteratorEsMap()
    {
        int bound = 100;
        for (int i = 0; i < bound; i++)
        {
            m.put(i, "" + i);
            checkValues(m, v);
            checkIteration(v);
        }
        int initSize = m.size();
        for (int i = 0; i < bound; i += 10)
        {
            m.remove(i);
            checkValues(m, v);
            checkIteration(v);
        }
        assertTrue("10 should be removed", (initSize - m.size()) == 10 && m.size() == v.size());
        it = v.iterator();
        while (it.hasNext())
        {
            for (int i = 0; i < 10; i++)
            {
                it.next();
                checkValues(m, v);
                checkIteration(v);
            }
            it.remove();
            checkValues(m, v);
            checkIteration(v); 
        }
        assertTrue("190 should be removed", (initSize - m.size()) == 19 && m.size() == v.size());
        assertTrue("Should be size 81", m.size() == v.size() && m.size() == 81);
    }

    /**
     * <p><b>Summary</b>: Tests the correct propagation from multiple
     * Valuess of a map to the backing map and the propagation from
     * the single map to the multiple entrymaps.</p>
     * <p><b>Test Case Design</b>: Tests the case of multiple Valuess
     * propagating changes to ther other Valuess and to the map,
     * and the single map propagating changes to multiple Valuess,
     * inserting entries through initHMap and removing them, through
     * simple remove method. This is a more general case than the
     * single Values.</p>
     * <p><b>Test Description</b>: esArr contains 80 the Values
     * generated by the map. After map initialization, each Values
     * is checked through checkValues, checkIteration and checkToArray.
     * First is tested propagation from the Valuess to the map.
     * From each Values one element is removed, then each Values
     * is checked through aforementioned methods. At the end of
     * this operation, all the Valuess and the map have a size of 0.
     * Second is tested propagation from the map to the Valuess.
     * Each element is removed from the map and after each removal
     * each Values is checked through aforementioned methods.
     * Again, at the end of
     * this operation, all the Valuess and the map have a size of 0.</p>
     * <p><b>Pre-Condition</b>: m and all the Valuess are empty.</p>
     * <p><b>Post-Condition</b>: m and all the Valuess are empty.</p>
     * <p><b>Expected Results</b>: Each modification to the map and Valuess
     * mantains coherence, checkValues, checkIteration and checkToArray
     * tests pass.</p>
     */
    @Test
    public void MultipleValuess()
    {
        int bound = 80;
        HCollection[] vArr = new HCollection[bound];
        for (int i = 0; i < bound; i++)
        vArr[i] = m.values();
        initHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
        {
            checkValues(m, v);
            checkIteration(v);
            checkToArray(v, v.toArray());
        }

        // Valuess -> map
        for (int i = 0; i < bound; i++)
        {
            vArr[i].remove(""+i);
            for (int j = 0; j < bound; j++)
            {
                checkValues(m, vArr[j]);
                checkIteration(vArr[j]);
                checkToArray(vArr[j], vArr[j].toArray());
            }
        }
        
        for (int i = 0; i < bound; i++)
            assertTrue(vArr[i].size() == 0 && vArr[i].isEmpty() && m.isEmpty());

        // map -> Valuess
        initHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
        {
            m.remove(i);
            for (int j = 0; j < bound; j++)
            {
                checkValues(m, vArr[j]);
                checkIteration(vArr[j]);
                checkToArray(vArr[j], vArr[j].toArray());
            }
        }
        for (int i = 0; i < bound; i++)
            assertTrue(vArr[i].size() == 0 && vArr[i].isEmpty() && m.isEmpty());
    }

    /**
     * <p><b>Summary</b>: Tests the correct propagation from HMap
     * to values when an entry is modified through put:
     * keys remains the same but the mapped value changes.</p>
     * <p><b>Test Case Design</b>: Tests a feature of the put method
     * for HMap, which is that when inserting an entry with a key
     * already in the map, should return the old value and update
     * the new mapping. Values should remain coherent with
     * the backing HMap.</p>
     * <p><b>Test Description</b>: the HMap is initiated
     * through a for loop, where at each insertion with put
     * the return value is asserted to null, as m was empty
     * and thus there were no mappings.
     * Then each entry is updated inserting, through put,
     * a new entry with the same key but value shifted
     * of bound. Each one of this put should return the old value.
     * Finally each entry is checked to be correct through
     * m.contains(i), m.containsValue(""+(bound+i)) and m.get(i).
     * Also values content is checked to contain the element
     * "i + 100" but to not contained the previously removed element
     * "i". After each modification coherency is checked through
     * checkIteration(v) andh checkValues(m, v).</p>
     * <p><b>Pre-Condition</b>: m is empty.</p>
     * <p><b>Post-Condition</b>: m contains {0="100":100="200"}</p>
     * <p><b>Expected Results</b>: m.puts in the first loop
     * return all null. m.puts in the second loop return all "i".
     * m contains {0="100":100="200"}. Coherency is checked through
     * checkValues and checkIteration after each modification.</p>
     */
    @Test
    public void Put_Substitute1()
    {
        int bound = 100;
        for (int i = 0; i < bound; i++)
        {
            assertNull("No previous mapping, should return null", m.put(i, "" + i));
            checkIteration(v);
            checkValues(m, v);
        }
        
        for (int i = 0; i < bound; i++)
        {
            assertEquals("m.put should return the old value", "" + i, m.put(i, "" + (bound + i)));
            checkIteration(v);
            checkValues(m, v);
        }
        for (int i = 0; i < bound; i++)
        {
            assertTrue("Should be contained", m.containsKey(i));
            assertTrue("Should be contained", m.containsValue("" + (i + bound)));
            assertTrue("Should be contained in value", v.contains("" + (i + bound)));
            assertFalse("Should NOT be contained in value", v.contains("" + i));
            assertEquals("Value not substitued correctly.", "" + (i + bound), m.get(i));
            
        }
    }

    /**
     * <p><b>Summary</b>: Tests the correct propagation from the backing map
     * to values and viceversa, by adding and removing dinamically
     * elements.</p>
     * <p><b>Test Case Design</b>: Adding one element to the values is not
     * possible, but it is possible adding one entry to the map, so that
     * the values contains the key of the inserted entries. After each insertion
     * through HMap.put() checkValues(m, v) and checkIteration(v) are
     * invoked to check correct propagation.</p>
     * <p><b>Test Description</b>: The map is initiated with entries
     * {0="0":100="100"} three times. After each initiation the map
     * is made empty by HMap.remove, HCollection.remove (through values) and
     * HIterator.remove (iterator of values).</p>
     * <p><b>Pre-Condition</b>: map and v are empty.</p>
     * <p><b>Post-Condition</b>: map and v are empty.</p>
     * <p><b>Expected Results</b>: Each modification propagates correctly
     * to the other objects. In particular, m.put and remove propagates correctly
     * to v, v.remove propagates correctly to m, it.remove propagates
     * correctly to m and it.</p>
     */
    @Test
    public void IncreaseKeySetThenRemove()
    {
        int bound = 100;
        for (int i = 0; i < bound; i++)
        {
            m.put(i, "" + i);
            checkValues(m, v);;
            checkIteration(v);
            assertEquals("Size should be " + (i + 1), i + 1, v.size());
        }
        // Remove from map
        for (int i = 0; i < bound; i++)
        {
            assertTrue("Should be contained", m.containsKey(i));
            assertTrue("Should be contained", m.containsValue("" + i));
            m.remove(i);
            checkValues(m, v);
            checkIteration(v);
        }
        assertTrue("Should be empty", v.isEmpty());
        for (int i = 0; i < bound; i++)
        {
            m.put(i, "" + i);
            checkValues(m, v);
            checkIteration(v);
            assertEquals("Size should be " + (i + 1), i + 1, v.size());
        }
        // Remove from es
        for (int i = 0; i < bound; i++)
        {
            assertTrue("Should be contained", v.contains(""+i));
            v.remove(""+i);
            checkValues(m, v);
            checkIteration(v);
        }
        assertTrue("Should be empty", v.isEmpty());
        for (int i = 0; i < bound; i++)
        {
            m.put(i, "" + i);
            checkValues(m, v);
            checkIteration(v);
            assertEquals("Size should be " + (i + 1), i + 1, v.size());
        }
        // Remove from es iterator
        it = v.iterator();
        while (it.hasNext())
        {
            Object curr = it.next();
            it.remove();
            assertFalse("Should NOT be contained", m.containsValue(curr));
            checkIteration(v);
            checkValues(m, v);;
        }
        assertTrue("Should be empty", v.isEmpty());
    }

    /**
     * <p><b>Summary</b>: Tests the behaviour of the map when
     * trying to insert entries with a value already in the map. Inserting
     * the same entry twice or more should ignore the insertion,
     * as the key is already present in the map. In Values, instead,
     * there can be clones: there can be two entries in the map
     * with different keys but same values.</p>
     * <p><b>Test Case Design</b>: Tests behaviour of
     * inserting cloned elements. After each put call and after
     * the removal, checkValues and checkIteration are invoked
     * to assert correct propagation.</p>
     * <p><b>Test Description</b>: Tests tries to insert
     * in the map the element in argv, 0=g, 1=a, 2=t,
     * 3=t, 4=i, 5=n, 6=i. Therefore keyset contains
     * g, a, t, t, i, n, i. Then entries are removed from the map,
     * therefore are removed from the values collection too.</p>
     * <p><b>Pre-Condition</b>: m and v are empty</p>
     * <p><b>Post-Condition</b>: m contains 3=t, 6=i, v contains {t, i}.</p>
     * <p><b>Expected Results</b>: argv elements are correctly inserted
     * in the map, therefore
     * after insertion m.size() is 7. v.containsAll(c) returns true.
     * Then 5 elements are removed through m, so in the end m and v
     * contains 2 elements. contains 3=t, 6=i, v contains {t, i}.</p>
     */
    @Test
    public void DuplicateTest0()
    {
        String argv[] = {"g", "a", "t", "t", "i", "n", "i"};
        for (int i = 0; i < argv.length; i++)
        {
            m.put(i, argv[i]);
            checkValues(m, v);
            checkIteration(v);
        }
        assertEquals("Size should be 5", 7, m.size());
        for (int i = 0; i < argv.length; i++)
            assertTrue("Insertion went wrong.", m.containsKey(i) && v.contains(argv[i]) && m.containsValue(argv[i]));
        
        c = getHCollection(new Object[]{"g", "a", "t", "i", "n"});
        assertTrue("Should be contained.", v.containsAll(c));
        
        m.remove(0);
        m.remove(1);
        m.remove(2);
        m.remove(4);
        m.remove(5);

        assertFalse(v.containsAll(c));

        assertTrue("v and m should not be empty.", !v.isEmpty() && !m.isEmpty());
        assertTrue("Final size should be 2", v.size() == m.size() && v.size() == 2);
        assertTrue("Both t and i should be contained", v.contains("t") && v.contains("i"));
        checkValues(m, v);
        checkIteration(v);
    }

    /**
     * <p><b>Summary</b>: Tests the behaviour of the map when
     * trying to insert entries with a value already in the map. Inserting
     * the same entry twice or more should ignore the insertion,
     * as the key is already present in the map. In Values, instead,
     * there can be clones: there can be two entries in the map
     * with different keys but same values.</p>
     * <p><b>Test Case Design</b>: Tests behaviour of
     * inserting cloned elements. After each put call and after
     * the removal, checkValues and checkIteration are invoked
     * to assert correct propagation.</p>
     * <p><b>Test Description</b>: 100 entries of type i="Value"
     * for i in (0, 100) are inserted in the map, therefore
     * the values collection, for correct propagation, should contain
     * 100 "Value" string, as it can contain cloned elements.
     * Then each "Value" is removed through the values collection,
     * asserting at iteration i that v.size() returns 100-i-1, and that
     * there was correct propagation through checkValues(m, v) and
     * checkIteration(v). At the end both the map and the values
     * collection should be empty. </p>
     * <p><b>Pre-Condition</b>: m and v are empty</p>
     * <p><b>Post-Condition</b>: m and v are empty.</p>
     * <p><b>Expected Results</b>: Propagation works correctly and
     * values can contain duplicated elements. v and m sizes are
     * correct during the test. Value is contained in the value collection
     * and in the map as value before removal. After the removal Value
     * is not contained neither in map as value or in v. Both m and v
     * are empty.</p>
     */
    @Test
    public void DuplicateTest1()
    {
        int bound = 100;
        for (int i = 0; i < bound; i++)
        {
            m.put(i, "Value");
            checkValues(m, v);
            checkIteration(v);
        }
        assertEquals("Propagation went wrong.", v.size(), m.size());
        assertEquals("Size should be "+ bound, bound, m.size());
        assertTrue("Value should be contained in v and m", v.contains("Value") && m.containsValue("Value"));
        for (int i = 0; i < bound; i++)
        {
            v.remove("Value");
            checkValues(m, v);
            checkIteration(v);
            if (i < bound - 1)
                assertTrue("Should be still contained", v.contains("Value"));
            assertTrue("Propagation went wrong", bound - i - 1 == m.size() && m.size() == v.size());
        }
        assertTrue("Value should NOT be contained in v and m", !v.contains("Value") && !m.containsValue("Value"));
        assertTrue(m.size() == v.size() && m.size() == 0 && m.isEmpty() && v.isEmpty());
    }

    /**
     * Checks if the values set and the backing map contains the same informations.
     * This method asserts correct propagation from HMap to its
     * entrySet and from values to HMap, therefore it is invoked
     * whenever it is necessary to check correct propagation.
     * Firstly they must have the same size, then each entry in the values
     * must be contained in the map. If this method fails the propagation
     * did not work correctly. Other wise, the propagation worked correctly.
     * @param m the backing map
     * @param v the values collection
     */
    public static void checkValues(HMap m, HCollection v)
    {
        assertEquals("Map and Values are NOT coherent. Propagation went wrong.", m.size(), v.size());
        HIterator it = v.iterator();
        
        while (it.hasNext())
        {
            Object val = it.next();
            assertTrue("Map and Values are NOT coherent. Propagation went wrong.", m.containsValue(val));
        }
    }

    /**
     * Checks if the values set and the array contains the same elements
     * and their size equals.
     * @param v entryset to be checked
     * @param arr array to be checked
     */
    public static void checkToArray(HCollection v, Object[] arr)
    {
        assertEquals("The array and the collection do NOT have the same elements.", v.size(), arr.length);
        for (int i = 0; i < arr.length; i++)
            assertTrue("The array and the collection do NOT have the same elements.", v.contains(arr[i]));
    }

    /**
     * Checks if the elements returned by the iteration are
     * coherent with the values's elements, and if the number
     * of iteration equals the actual size of the value set.
     * This method asserts correct propagation from HMap to its
     * entrySet and from values to HMap, therefore it is invoked
     * whenever it is necessary to check correct propagation.
     * If this method fails the propagation
     * did not work correctly. Other wise, the propagation worked correctly.
     * @param v values collection to be checked
     */
    public static void checkIteration(HCollection v)
    {
        HIterator it = v.iterator();
        int count = 0;
        while (it.hasNext())
        {
            Object e = it.next();
            count++;
            assertTrue("Values's iterator is NOT coherent.", v.contains(e));
        }

        assertFalse("Values's iterator is NOT coherent.", it.hasNext());
        assertEquals("Values's iterator is NOT coherent.", count, v.size());
        if (count == 0)
            assertTrue("Values's iterator is NOT coherent.", v.isEmpty());
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

    /**
     * <p><b>Summary</b>:</p>
     * <p><b>Test Suite Design</b>:</p>
     * 
     */