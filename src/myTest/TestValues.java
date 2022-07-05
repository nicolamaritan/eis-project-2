package myTest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import static myTest.TestUtilities.*;

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.junit.*;

import myAdapter.*;

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

    @Before
    public void BeforeMethod()
    {
        m = new MapAdapter();
        m2 = new MapAdapter();
        v = m.values();
        v2 = m2.values();
        c = new CollectionAdapter();
    }

    @AfterClass
    public static void AfterClassMethod()
    {
        System.out.println(TestValues.class.getName() + " ended.");
    }

    @After
    public void AfterMethod()
    {
        m = null;
        v = null;
        v2 = null;
    }
    
    // ------------------------------------------ single method test case ------------------------------------------

    // ------------------------------------------ add method ------------------------------------------
    @Test (expected = UnsupportedOperationException.class)
    public void Add_UOE()
    {
        v.add(1);
    }

    // ------------------------------------------ addAll method ------------------------------------------
    @Test (expected = UnsupportedOperationException.class)
    public void AddAll_UOE()
    {
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
     * are invoked to test Values - map coherence and the iteration.</p>
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
        assertEquals("isEmpty did not returned false.", false, v.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a Values with 3 elements
     * should have a size of 3 and isEmpty call returning
     * false.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 3 size and not being empty. Propagation
     * map -> Values is tested.</p>
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
        assertEquals("isEmpty did not returned false.", false, v.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a Values with 3 elements
     * should have a size of 345 and isEmpty call returning
     * false.
     * The Values is modiefied before the asserts.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 345 size and not being empty. Propagation
     * map -> Values is tested.</p>
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
     * (i, "i") is contained in the Values, for i in (0,1000).
     * Propagation map -> Values is tested.</p>
     * <p><b>Test Description</b>: contains is invoked 1000 times
     * in a for loop. At each iteration a contained entry is generated
     * and checked to be in the Values.</p>
     * <p><b>Pre-Condition</b>: v contains {"0":"1000"}, m
     * contains {0="0":1000="1000"}.</p>
     * <p><b>Post-Condition</b>: v is unchanged.
     * m is unchanged.</p>
     * <p><b>Expected Results</b>: each contains returns true</p>
     */
    @Test
    public void Contains_0To1000()
    {
        int bound = 1000;
        addToHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
            assertTrue("Should be contained", v.contains(""+i));
    }

    /**
     * <p><b>Summary</b>: contains method test case</p>
     * <p><b>Test Case Design</b>: Tests if the key
     * i + 1000 is contained in the Values, for i in (0,1000).
     * This is obviusly false for each i, as v contains {"0":"1000"}.</p>
     * <p><b>Test Description</b>: contains is invoked 1000 times
     * in a for loop. At each iteration a contained entry is generated
     * and checked to be in the Values.</p>
     * <p><b>Pre-Condition</b>: v contains {"0":"1000"}, m
     * contains {0="0":1000="1000"}.</p>
     * <p><b>Post-Condition</b>: v is unchanged.
     * m is unchanged.</p>
     * <p><b>Expected Results</b>: each contains returns false</p>
     */
    @Test
    public void Contains_0To1000_DifferentKey()
    {
        int bound = 1000;
        addToHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
            assertFalse("Should NOT be contained", v.contains(i + bound));
    }


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
     * empty collection. The tested case is a limit case of containsAll.</p>
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
        assertEquals("The method should return true because the collection is empty.", true, v.containsAll(c)); 
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
        c.add("1");
        assertTrue("The Values contains 1=1.", v.containsAll(c));
        
        // {10 as key}
        c = new CollectionAdapter();
        c.add("10");
        assertTrue("The Values contains 10=10.", v.containsAll(c));

        // {3, 4, 5 as keys}
        c = getStringHCollection(3, 6);
        assertTrue("The Values contains 3=3, 4=4 and 5=5.", v.containsAll(c));

        // {1, 5, 10}
        c = new CollectionAdapter();
        c.add("1");
        c.add("5");
        c.add("10");
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

        c.add("11");
        assertEquals("11=11 is not contained.", false, v.containsAll(c));

        c = new CollectionAdapter();
        c.add("3");
        c.add("4");
        c.add("5");
        c.add("12");   // Single element not present
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
    
    // ------------------------------------------ equals method ------------------------------------------

    /**
     * <p><b>Summary</b>: equals method test case.</p>
     * <p><b>Test Case Design</b>: equals method is tested with an equal 
     * and a bigger and a smaller one. The returned values should be true and false.
     * Also reflective property of equal is tested.</p>
     * <p><b>Test Description</b>: Values is initialized, then different equals invoke are
     * asserted with different arguments, generated for each case.</p>
     * <p><b>Pre-Condition</b>: map contains {0="0" : 2000="2000"},
     * therefore v contains {"0":"2000"}.</p>
     * <p><b>Post-Condition</b>: Values is unchanged.</p>
     * <p><b>Expected Results</b>: The Values is unchanged and symmetric property is valid.</p>
     */
    @Test
    public void Equals_0To10()
    {
        int to = 2000;
        addToHMap(m, 0, to);
        assertEquals(true, v.equals(getIntegerMapAdapter(0, to).values()));
        assertEquals(true, getIntegerMapAdapter(0, to).values().equals(v));   // Symmetric property
        assertEquals(false, v.equals(getIntegerMapAdapter(0, to + 15).values()));  // bigger Values returns false
        assertEquals(false, v.equals(getIntegerMapAdapter(0, 5).values()));  // smaller Values returns false
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
        assertEquals("Two empty Valuess should equals.", true, v.equals(v2));
        assertEquals("Two empty Valuess should equals.", true, v2.equals(v));
    }

    /**
     * <p><b>Summary</b>: equals method test case.
     * The reflective property of equal method is tested.</p>
     * <p><b>Test Case Design</b>: equals method should be reflective,
     * therefore x.equals(x) should always return true. Propagation
     * map -> Values is tested through initHMap.</p>
     * <p><b>Test Description</b>: The test invokes v.equals(v) when
     * v is empty, when it has 10 elements and when it has 1000 elements.</p>
     * <p><b>Pre-Condition</b>: Values is not null.</p>
     * <p><b>Post-Condition</b>: Values has 1000 elements. </p>
     * <p><b>Expected Results</b>: Values equals itself, therefore
     * reflective property is valid.</p>
     */
    @Test
    public void Equals_Reflective()
    {
        assertEquals("Reflective property is not met.", true, v.equals(v));    // Values is empty
        initHMap(m, 0, 10);
        assertEquals("Reflective property is not met.", true, v.equals(v));    // Values is not empty, should return true anyways
        initHMap(m, 0, 1000);
        assertEquals("Reflective property is not met.", true, v.equals(v));    // Values is not empty, should return true anyways
    }

    /**
     * <p><b>Summary</b>: equals method test case.
     * The transitive property of equal method is tested.</p>
     * <p><b>Test Case Design</b>: equals method should be transitive,
     * therefore a.equals(b) and b.equals(c) {@literal =>} a.equals(c).
     * Propagation map -> Values is tested.</p>
     * <p><b>Test Description</b>: The test invokes v.equals(v2) and v2.equals(Values3)
     * and v.equals(v3)</p>
     * <p><b>Pre-Condition</b>: Values contain {"1" : "2000"}.</p>
     * <p><b>Post-Condition</b>: Values are unchanged. </p>
     * <p><b>Expected Results</b>: Equals has transitive property.</p>
     */
    @Test
    public void Equals_Transitive()
    {
        int to = 2000;
        addToHMap(m, 0, to);
        addToHMap(m2, 0, to);
        HCollection v3 = getIntegerMapAdapter(0, to).values();

        assertEquals("Valuess should be equal.", true, v.equals(v2));
        assertEquals("Valuess should be equal.", true, v2.equals(v3));
        assertEquals("Transitive property is not met.",true, v.equals(v3));
    }

    // ------------------------------------------ clear method ------------------------------------------

    /**
     * <p><b>Summary</b>: clear, containsKey, containsValue, get method test case.</p>
     * <p><b>Test Case Design</b>: Tests the behaviour of clear method of Values
     * and of map. Tests the backing map -> Values and viceversa, Values -> map.</p>
     * <p><b>Test Description</b>: map is initialized with {0="0" : 1000="1000"},
     * therefore v contains {"0":"1000"}.
     * Through v iterators iterates through the elements to assert that they both
     * share the same informations about the map entriv. Then clear is invoked
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
        addToHMap(m, 0, 1000);
        assertEquals(m.size(), v.size());
        checkValues(m, v);
        v.clear();
        assertTrue("Should both have size 0", (m.size() == v.size()) && v.size() == 0);
        assertTrue("Should be both empty", m.isEmpty() && v.isEmpty());

        addToHMap(m, 0, 1000);
        checkValues(m, v);
        m.clear();  // Invoked from m this time
        assertTrue("Should both have size 0", (m.size() == v.size()) && v.size() == 0);
        assertTrue("Should be both empty", m.isEmpty() && v.isEmpty());
    }

    /**
     * <p><b>Summary</b>: clear method test case.</p>
     * <p><b>Test Case Design</b>: Tests the behaviour of clear method of Values
     * and map when they both are empty, which is a limit case (obviusly the limit case is that
     * they are empty, not that they have the same size, as it is trivial).</p>
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
        v.clear();
        checkValues(m, v);
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
     * <p><b>Pre-Condition</b>: Valuess have same hashCode and they are equal.</p>
     * <p><b>Post-Condition</b>: Valuess have same hashCode and they are equal.</p>
     * <p><b>Expected Results</b>: Valuess have same hashCode and they are equal.</p>
     */
    @Test
    public void HashCode_Mixed()
    {
        // Empty map case
        assertEquals("Maps should be equal.", true, v.equals(v2));
        assertEquals("Hash codes should be equal.", v.hashCode(), v2.hashCode());

        // One element case
        m.put(1, "1");
        m2.put(1, "1");
        assertEquals("Maps should be equal.", true, v.equals(v2));
        assertEquals("Hash codes should be equal.", v.hashCode(), v2.hashCode());

        initHMap(m, -100, 100);
        initHMap(m2, -100, 100);
        assertEquals("Maps should be equal.", true, v.equals(v2));
        assertEquals("Hash codes should be equal.", v.hashCode(), v2.hashCode());

        v.remove((Object)0);
        v2.remove((Object)0);
        assertEquals("Maps should be equal.", true, v.equals(v2));
        assertEquals("Hash codes should be equal.", v.hashCode(), v2.hashCode());

        m.put(101, "101");
        m2.put(101, "101");
        assertEquals("Maps should be equal.", true, v.equals(v2));
        assertEquals("Hash codes should be equal.", v.hashCode(), v2.hashCode());

        addToHMap(m, 500, 1000);
        addToHMap(m2, 500, 1000);
        assertEquals("Maps should be equal.", true, v.equals(v2));
        assertEquals("Hash codes should be equal.", v.hashCode(), v2.hashCode());

        HMap t = getIntegerMapAdapter(-1000, -900);
        m.putAll(t);
        m2.putAll(t);
        assertEquals("Maps should be equal.", true, v.equals(v2));
        assertEquals("Hash codes should be equal.", v.hashCode(), v2.hashCode());

        initHMap(t, 5000, 6000);
        m.putAll(t);
        m2.putAll(t);
        assertEquals("Maps should be equal.", true, v.equals(v2));
        assertEquals("Hash codes should be equal.", v.hashCode(), v2.hashCode());
    }

    // ------------------------------------------ remove method ------------------------------------------
    /**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests remove method invoked by map
     * and its Values and checv their consistency in propagation.
     * They both are empty, which is a limit case.</p>
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
        assertFalse(v.remove("Random Object"));
        checkValues(m, v);
    }

    /**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests remove method invoked by map
     * and its Values and checv their consistency in propagation.
     * Map contains 10 elements, and arguments are keys/entries not
     * in the map/collection.</p>
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
        m.remove("Not in map Key");
        checkValues(m, v);
        v.remove("Not in Values Entry");
        checkValues(m, v);
    }

    /**
     * <p><b>Summary</b>: remove method test case.
     * remove is tested to make changes from map and
     * from collection, propagating removals from one to another.</p>
     * <p><b>Test Case Design</b>: After each removals and
     * modification to map and collection's structures are checked
     * by checkValues. Test aims to show the correct propagation
     * of information.</p>
     * <p><b>Test Description</b>: Entries (i, "i") are inserted and removed
     * from the map remove. The map is initiated with {0="0", 1000="1000"},
     * therefore v contains {"0":"1000"}
     * and each element in map and Values is removed by map's remove.
     * The map is initiated with {0="0": 1000="1000"},
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
        int bound = 1000;
        for (int i = 0; i < bound; i++)
        {
            m.put(i, "" + i);
            checkValues(m, v);
            m.remove(i);
            checkValues(m, v);
        }
        initHMap(m, 0, 1000);
        checkValues(m, v);
        for (int i = bound - 1; i >= 0; i--)
        {
            m.remove(i);
            checkValues(m, v);
        }
        initHMap(m, 0, 1000);
        checkValues(m, v);
        for (int i = bound - 1; i >= 0; i--)
        {
            v.remove(i);
            checkValues(m, v);
        }
        checkValues(m, v);
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
     * The collection keeps changing during execution.</p>
     * <p><b>Test Description</b>: The collection contains {0="0":500="500"},
     * while map contains {0="0" : i="i"} and the Values contains 
     * {"0":"i"}, for each i in (0,1000)
     * (Note that empty map/Values limit case is being tested). Then
     * v.removeAll(c) is invoked and coherence is check through checkValues.
     * Then the test asserts that m and v have the right size and isEmpty
     * returns the right value.</p>
     * <p><b>Pre-Condition</b>: c contains {0="0":500="500"}, m and v are
     * empty.</p>
     * <p><b>Post-Condition</b>: c is unchanged, m contains
     * {500="500":1000="1000"}, while v contains {500:1000}.</p>
     * <p><b>Expected Results</b>: Each removal is correctly propagated
     * from map to Values and from Values to map. In particular,
     * they still contains coherent informations.</p>
     */
    @Test
    public void RemoveAll_Backing0()
    {
        int bound = 1000;
        int secondBound = 500;
        c = getStringHCollection(0, secondBound);
        for (int i = 0; i < bound; i++)
        {
            initHMap(m, 0, i);
            v.removeAll(c);
            checkValues(m, v);

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

    // ------------------------------------------ retainAll method ------------------------------------------

    /**
     * <p><b>Summary</b>: retainAll method test case. The test aim
     * to test coherence of map and its Values after invoking retainAll
     * in different scenarios and problem size.</p>
     * <p><b>Test Case Design</b>: After each retainAll method invoke
     * the coherence is checked through checkValues. Tests right
     * propagation from the entry collection to the map. Retaining all entries
     * from the Values implies retaining all the entries in the map.
     * The collection keeps changing during execution.</p>
     * <p><b>Test Description</b>: The collection contains {0="0":500="500"},
     * while map contains {0="0" : i="i"}
     * and the Values contains {"0":"i"}, for each i in (0,1000)
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
        int bound = 1000;
        int secondBound = 500;
        c = getStringHCollection(0, secondBound);
        for (int i = 0; i < bound; i++)
        {
            initHMap(m, 0, i);
            v.retainAll(c);
            checkValues(m, v);

            if (i <= secondBound)
                assertTrue("Both should have size " + i, m.size() == v.size() && m.size() == i);
            else
                assertTrue("Both should have size 500", m.size() == v.size() && m.size() == 500);
        }
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
     * creating different situations and cases to test the method.</p>
     * <p><b>Test Description</b>: The test first removes the
     * entries with even key, then the entries with odd key. After each
     * removal the checkToArray method is invoked to check if the
     * generated array in all of this cases is right.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":1000="1000"}, therefore
     * v contains {"0":"1000"}.</p>
     * <p><b>Post-Condition</b>: m and v are empty</p>
     * <p><b>Expected Results</b>: After each removal the generated array
     * through v.toArray is right and coherent. At the end v and m are empty.</p>
     */
    @Test
    public void ToArray_0To1000()
    {
        int bound = 1000;
        initHMap(m, 0, 1000);
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
     * <p><b>Pre-Condition</b>: m contains {0="0":1000="1000"}, therefore
     * v contains {"0":"1000"}.</p>
     * <p><b>Post-Condition</b>: m and v are empty</p>
     * <p><b>Expected Results</b>: After each removal the generated array
     * through v.toArray is right and coherent. At the end v and m are empty.</p>
     */
    @Test
    public void ToArrayArrayArg_0To1000()
    {
        int bound = 1000;
        
        initHMap(m, 0, 1000);
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
        int bound = 1000;
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
     * call to next) will throw HISE.</p>
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
     * to assure correct propagation iterator -> Values -> map.</p>
     * <p><b>Test Description</b>: map and v initially contain
     * {0="0":1000="1000"}. An iterator iterates through
     * each element and after each next it invokes the remove
     * method, removing the just returned element.
     * Then checkValues and checkIteration are invoke
     * to check Values - map coherence and iteration.
     * After iterating through all elements, the iterator.hasNext
     * method must return false, and v and m should be both empty.</p>
     * <p><b>Pre-Condition</b>: map contains
     * {0="0":1000="1000"}, therefore v contains {"0":"1000"}</p>
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
     * <p><b>Test Description</b>: m is filled with entries {0="0":1000="1000"}.
     * Through a for loop entries {i="i"}, i being 10, 20, 30,...
     * are removed from the map through map.remove method. That means that 100 entries
     * are removed from m and v. Then while the iterator's has next,
     * it removes 1 element through iterator.remove each 10 elements,
     * therefore it removes 90 entries, as the iterator starts iterating
     * the map and v have 900 elements.</p>
     * <p><b>Pre-Condition</b>: m and v are empty.</p>
     * <p><b>Post-Condition</b>: m and v contains 810 entriv.</p>
     * <p><b>Expected Results</b>: Each removal from map and v propagates
     * the changes correctly to the other structure. checkValues and checkIteration
     * are invoked to check coherence and that iteration worv correctly.</p>
     */
    @Test
    public void RemoveIteratorEsMap()
    {
        int bound = 1000;
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
        assertTrue("100 should be removed", (initSize - m.size()) == 100 && m.size() == v.size());
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
        assertTrue("190 should be removed", (initSize - m.size()) == 190 && m.size() == v.size());
        assertTrue("Should be size 810", m.size() == v.size() && m.size() == 810);
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
     * <p><b>Test Description</b>: esArr contains 100 the Values
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
        int bound = 100;
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


    private void checkValues(HMap m, HCollection v)
    {
        assertEquals("Map and Values are NOT coherent. Propagation went wrong.", m.size(), v.size());
        HIterator it = v.iterator();
        
        while (it.hasNext())
        {
            Object val = it.next();
            assertTrue("Map and Values are NOT coherent. Propagation went wrong.", m.containsValue(val));
        }
    }

    private void checkToArray(HCollection v, Object[] arr)
    {
        assertEquals("The array and the collection do NOT have the same elements.", v.size(), arr.length);
        for (int i = 0; i < arr.length; i++)
            assertTrue("The array and the collection do NOT have the same elements.", v.contains(arr[i]));
    }

    private void checkIteration(HCollection v)
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