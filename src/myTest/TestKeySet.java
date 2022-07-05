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

public class TestKeySet 
{
    private MapAdapter m, m2;
    private HSet ks, ks2;
    private HCollection c;
    private HIterator it;

    @BeforeClass
    public static void BeforeClassMethod()
    {
        System.out.println(TestKeySet.class.getName() + " running.");
    }

    @Before
    public void BeforeMethod()
    {
        m = new MapAdapter();
        m2 = new MapAdapter();
        ks = m.keySet();
        ks2 = m2.keySet();
        c = new CollectionAdapter();
    }

    @AfterClass
    public static void AfterClassMethod()
    {
        System.out.println(TestKeySet.class.getName() + " ended.");
    }

    @After
    public void AfterMethod()
    {
        m = new MapAdapter();
        ks = m.keySet();
    }
    
    // ------------------------------------------ single method test case ------------------------------------------

    // ------------------------------------------ add method ------------------------------------------
    @Test (expected = UnsupportedOperationException.class)
    public void Add_UOE()
    {
        ks.add(1);
    }

    // ------------------------------------------ addAll method ------------------------------------------
    @Test (expected = UnsupportedOperationException.class)
    public void AddAll_UOE()
    {
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
     * map -> KeySet is tested.</p>
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
    public void Size_Empty_0()
    {
        assertEquals("Empty KeySet does not have size of zero.", 0, ks.size());
        assertTrue("isEmpty did not returned true.", ks.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a KeySet with 1 element
     * should have a size of 1 and isEmpty call returning
     * false.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 1 size and not being empty. Propagation
     * map -> KeySet is tested. checkKeySet and checkIteration
     * are invoked to test KeySet - map coherence and the iteration.</p>
     * <p><b>Pre-Condition</b>: The KeySet contains 1, map contains {1="1"}.</p>
     * <p><b>Post-Condition</b>: The KeySet contains 1 map contains {1="1"}.</p>
     * <p><b>Expected Results</b>: The size method returns 1 and the isEmpty
     * method returns false. checkKeySet and checkIteration tests pass.</p>
     */
    @Test
    public void Size_OneElement_1()
    {
        m.put(1, "1");
        checkKeySet(m, ks);
        checkIteration(ks);
        assertEquals("Empty KeySet does not have size of one.", 1, ks.size());
        assertEquals("isEmpty did not returned false.", false, ks.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a KeySet with 3 elements
     * should have a size of 3 and isEmpty call returning
     * false.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 3 size and not being empty. Propagation
     * map -> KeySet is tested.</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the KeySet.</p>
     * <p><b>Pre-Condition</b>: The KeySet is empty.</p>
     * <p><b>Post-Condition</b>: The KeySet has 3 elements ({0, 1, 2}).</p>
     * <p><b>Expected Results</b>: The size method returns 3 and the isEmpty
     * method returns false. checkKeySet and checkIteration tests pass.</p>
     */
    @Test
    public void Size_ThreeElements_3()
    {
        addToHMap(m, 0, 3);
        checkKeySet(m, ks);
        checkIteration(ks);
        assertEquals("KeySet with 3 elements does not have size of 3.", 3, ks.size());
        assertEquals("isEmpty did not returned false.", false, ks.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a KeySet with 3 elements
     * should have a size of 345 and isEmpty call returning
     * false.
     * The KeySet is modiefied before the asserts.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 345 size and not being empty. Propagation
     * map -> KeySet is tested.</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the KeySet.</p>
     * <p><b>Pre-Condition</b>: The KeySet is empty.</p>
     * <p><b>Post-Condition</b>: The KeySet contains 345 elements ({0:345}).</p>
     * <p><b>Expected Results</b>: The size method returns 345 and the isEmpty
     * method returns false. checkKeySet and checkIteration tests pass.</p>
     */
    @Test
    public void Size_345Elements_345()
    {
        addToHMap(m, 0, 345);
        checkKeySet(m, ks);
        checkIteration(ks);
        assertEquals("KeySet with 345 elements does not have size of 345.", 345, ks.size());
        assertFalse("isEmpty did not returned false.", ks.isEmpty());
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
     * <p><b>Expected Results</b>: cotnains returns false</p>
     */
    @Test
    public void Contains_Empty()
    {
        assertFalse("Should not contain", ks.contains("Random object"));
    }

    
    /**
     * <p><b>Summary</b>: contains method test case</p>
     * <p><b>Test Case Design</b>: Tests if each entry of type
     * (i, "i") is contained in the KeySet, for i in (0,1000).
     * Propagation map -> KeySet is tested.</p>
     * <p><b>Test Description</b>: contains is invoked 1000 times
     * in a for loop. At each iteration a contained entry is generated
     * and checked to be in the KeySet.</p>
     * <p><b>Pre-Condition</b>: ks contains {0:1000}, m
     * contains {0="0":1000="1000"}.</p>
     * <p><b>Post-Condition</b>: ks is unchanged.
     * m is unchanged.</p>
     * <p><b>Expected Results</b>: each contains returns true</p>
     */
    @Test
    public void Contains_0To1000()
    {
        int bound = 1000;
        addToHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
            assertTrue("Should be contained", ks.contains(i));
    }

    /**
     * <p><b>Summary</b>: contains method test case</p>
     * <p><b>Test Case Design</b>: Tests if the key
     * i + 1000 is contained in the KeySet, for i in (0,1000).
     * This is obviusly false for each i, as ks contains {0:1000}.</p>
     * <p><b>Test Description</b>: contains is invoked 1000 times
     * in a for loop. At each iteration a contained entry is generated
     * and checked to be in the KeySet.</p>
     * <p><b>Pre-Condition</b>: ks contains {0:1000}, m
     * contains {0="0":1000="1000"}.</p>
     * <p><b>Post-Condition</b>: ks is unchanged.
     * m is unchanged.</p>
     * <p><b>Expected Results</b>: each contains returns false</p>
     */
    @Test
    public void Contains_0To1000_DifferentKey()
    {
        int bound = 1000;
        addToHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
            assertFalse("Should NOT be contained", ks.contains(i + bound));
    }


    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The method tests if an empty KeySet contains the elements
     * of another collection, which is obviusly false.</p>
     * <p><b>Test Case Design</b>: The test case tests the limit case of
     * checking an empty KeySet containing something.</p>
     * <p><b>Test Description</b>: The collection contains 1, 2 and 3.
     * The containsAll method obviously should return false for
     * any coll's content as the KeySet is empty.</p>
     * <p><b>Pre-Condition</b>: The KeySet and map are empty.</p>
     * <p><b>Post-Condition</b>: The KeySet and map are empty.</p>
     * <p><b>Expected Results</b>: The containsAll method return false.</p>
     */
    @Test
    public void ContainsAll_Empty_False()
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
    public void ContainsAll_BothEmpty_False()
    {
        assertEquals("The method should return true because the collection is empty.", true, ks.containsAll(c)); 
    }
    
    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The test case tests different containsAll calls
     * with different collection.</p>
     * <p><b>Test Case Design</b>: The tests calls containsAll
     * with some as argument. Propagation map -> KeySet is tested.</p>
     * <p><b>Test Description</b>: In the test containsAll is called with the
     * collection containing
     * {1}, {10}, {3, 4, 5}, {1, 5, 10}.</p>
     * <p><b>Pre-Condition</b>: The KeySet contains {0:10}, the map
     * contains {0="0":10="10"}.</p>
     * <p><b>Post-Condition</b>: The KeySet contains {0:10}, the map
     * contains {0="0":10="10"}.</p>
     * <p><b>Expected Results</b>: Every containsAll calls return true.</p>
     */
    @Test
    public void ContainsAll_1to11_True()
    {
        addToHMap(m, 0, 11);

        // {1 as key}
        c.add(1);
        assertTrue("The KeySet contains 1=1.", ks.containsAll(c));
        
        // {10 as key}
        c = new CollectionAdapter();
        c.add(10);
        assertTrue("The KeySet contains 10=10.", ks.containsAll(c));

        // {3, 4, 5 as keys}
        c = getIntegerHCollection(3, 6);
        assertTrue("The KeySet contains 3=3, 4=4 and 5=5.", ks.containsAll(c));

        // {1, 5, 10}
        c = new CollectionAdapter();
        c.add(1);
        c.add(5);
        c.add(10);
        assertTrue("The KeySet contains 1, 5 and 10.", ks.containsAll(c));
    }

    /**
     * <p><b>Summary</b>: containsAll method test case.
     * Tests if different combination are contained in the KeySet.
     * Each containsAll call returns false.</p>
     * <p><b>Test Case Design</b>: Different scenario are tested where
     * containsAll should return false. Calling containsAll with arguments
     * not present in the KeySet is a common case for the method. Propagation
     * map -> KeySet is tested.</p>
     * <p><b>Test Description</b>: The first containsAll takes as argument
     * a collection containing a single element, not present in the
     * KeySet. The second one takes as argument a collection containing element
     * in the KeySet e one not contained in the KeySet: therefore, the call
     * must return false as one of the collection is not contained.</p>
     * <p><b>Pre-Condition</b>: The KeySet contains {0:10}, the map
     * contains {0="0":10="10"}.</p>
     * <p><b>Post-Condition</b>: The KeySet contains {0:10}, the map
     * contains {0="0":10="10"}.</p>
     * <p><b>Expected Results</b>: All containsAll calls return true,
     * last containsAll returns false as 12 is not contained
     * in the keyset..</p>
     */
    @Test
    public void ContainsAll_0to11_False()
    {
        addToHMap(m, 0, 11);

        c.add(11);
        assertEquals("11=11 is not contained.", false, ks.containsAll(c));

        c = new CollectionAdapter();
        c.add(3);
        c.add(4);
        c.add(5);
        c.add(12);   // Single element not present
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
    
    // ------------------------------------------ equals method ------------------------------------------

    /**
     * <p><b>Summary</b>: equals method test case.</p>
     * <p><b>Test Case Design</b>: equals method is tested with an equal 
     * and a bigger and a smaller one. The returned values should be true and false.
     * Also reflective property of equal is tested.</p>
     * <p><b>Test Description</b>: KeySet is initialized, then different equals invoke are
     * asserted with different arguments, generated for each case.</p>
     * <p><b>Pre-Condition</b>: KeySet contains {0="0" : 1000000="1000000"}.</p>
     * <p><b>Post-Condition</b>: KeySet is unchanged.</p>
     * <p><b>Expected Results</b>: The KeySet is unchanged and symmetric property is valid.</p>
     */
    @Test
    public void Equals_0To10()
    {
        int to = 1000000;
        addToHMap(m, 0, to);
        assertEquals(true, ks.equals(getIntegerMapAdapter(0, to).keySet()));
        assertEquals(true, getIntegerMapAdapter(0, to).keySet().equals(ks));   // Symmetric property
        assertEquals(false, ks.equals(getIntegerMapAdapter(0, to + 15).keySet()));  // bigger KeySet returns false
        assertEquals(false, ks.equals(getIntegerMapAdapter(0, 5).keySet()));  // smaller KeySet returns false
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
        assertEquals("Two empty KeySets should equals.", true, ks.equals(ks2));
        assertEquals("Two empty KeySets should equals.", true, ks2.equals(ks));
    }

    /**
     * <p><b>Summary</b>: equals method test case.
     * The reflective property of equal method is tested.</p>
     * <p><b>Test Case Design</b>: equals method should be reflective,
     * therefore x.equals(x) should always return true. Propagation
     * map -> KeySet is tested through initHMap.</p>
     * <p><b>Test Description</b>: The test invokes ks.equals(ks) when
     * ks is empty, when it has 10 elements and when it has 1000 elements.</p>
     * <p><b>Pre-Condition</b>: KeySet is not null.</p>
     * <p><b>Post-Condition</b>: KeySet has 1000 elements. </p>
     * <p><b>Expected Results</b>: KeySet equals itself, therefore
     * reflective property is valid.</p>
     */
    @Test
    public void Equals_Reflective()
    {
        assertEquals("Reflective property is not met.", true, ks.equals(ks));    // KeySet is empty
        initHMap(m, 0, 10);
        assertEquals("Reflective property is not met.", true, ks.equals(ks));    // KeySet is not empty, should return true anyways
        initHMap(m, 0, 1000);
        assertEquals("Reflective property is not met.", true, ks.equals(ks));    // KeySet is not empty, should return true anyways
    }

    /**
     * <p><b>Summary</b>: equals method test case.
     * The transitive property of equal method is tested.</p>
     * <p><b>Test Case Design</b>: equals method should be transitive,
     * therefore a.equals(b) and b.equals(c) {@literal =>} a.equals(c).
     * Propagation map -> KeySet is tested.</p>
     * <p><b>Test Description</b>: The test invokes ks.equals(ks2) and ks2.equals(KeySet3)
     * and ks.equals(ks3)</p>
     * <p><b>Pre-Condition</b>: KeySets contain {1 : 1000000}.</p>
     * <p><b>Post-Condition</b>: KeySets are unchanged. </p>
     * <p><b>Expected Results</b>: Equals has transitive property.</p>
     */
    @Test
    public void Equals_Transitive()
    {
        int to = 1000000;
        addToHMap(m, 0, to);
        addToHMap(m2, 0, to);
        HSet ks3 = getIntegerMapAdapter(0, to).keySet();

        assertEquals("KeySets should be equal.", true, ks.equals(ks2));
        assertEquals("KeySets should be equal.", true, ks2.equals(ks3));
        assertEquals("Transitive property is not met.",true, ks.equals(ks3));
    }

    // ------------------------------------------ clear method ------------------------------------------

    /**
     * <p><b>Summary</b>: clear, containsKey, containsValue, get method test case.</p>
     * <p><b>Test Case Design</b>: Tests the behaviour of clear method of KeySet
     * and of map. Tests the backing map -> KeySet and viceversa, KeySet -> map.</p>
     * <p><b>Test Description</b>: map is initialized with {0="0" : 1000="1000"},
     * therefore ks contains {0:1000}.
     * Through ks iterators iterates through the elements to assert that they both
     * share the same informations about the map entriks. Then clear is invoked
     * by the KeySet. Same initialization and iteration are done,
     * but this time clear is invoked by m.</p>
     * <p><b>Pre-Condition</b>: m and ks are empty.</p>
     * <p><b>Post-Condition</b>: m and ks are still empty.</p>
     * <p><b>Expected Results</b>: clear propagates successfully from map to KeySet
     * and viceversa.</p>
     */
    @Test
    public void Clear_Backing0()
    {
        addToHMap(m, 0, 1000);
        assertEquals(m.size(), ks.size());
        checkKeySet(m, ks);
        ks.clear();
        assertTrue("Should both have size 0", (m.size() == ks.size()) && ks.size() == 0);
        assertTrue("Should be both empty", m.isEmpty() && ks.isEmpty());

        addToHMap(m, 0, 1000);
        checkKeySet(m, ks);
        m.clear();  // Invoked from m this time
        assertTrue("Should both have size 0", (m.size() == ks.size()) && ks.size() == 0);
        assertTrue("Should be both empty", m.isEmpty() && ks.isEmpty());
    }

    /**
     * <p><b>Summary</b>: clear method test case.</p>
     * <p><b>Test Case Design</b>: Tests the behaviour of clear method of KeySet
     * and map when they both are empty, which is a limit case (obviusly the limit case is that
     * they are empty, not that they have the same size, as it is trivial).</p>
     * <p><b>Test Description</b>: clear is invoked by m and they are checked through checkKeySet,
     * same then but clear is invoked by the KeySet.</p>
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
        ks.clear();
        checkKeySet(m, ks);
    }
    
    // ------------------------------------------ hashCode method ------------------------------------------

    /**
     * <p><b>Summary</b>: hashCode test case.
     * Tests the behaviour of hashCode method with different
     * map and KeySet configurations.</p>
     * <p><b>Test Case Design</b>: The same operations are applied to map 1 and 2,
     * so their KeySets must have the same elements each time, therefore they are equals.
     * If they are equals they must have the same hashCode. Changes propagates successfully from map to KeySet
     * and viceversa, tests therefore the consinstency of equals and hashCode.</p>
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
        assertEquals("Maps should be equal.", true, ks.equals(ks2));
        assertEquals("Hash codes should be equal.", ks.hashCode(), ks2.hashCode());

        // One element case
        m.put(1, "1");
        m2.put(1, "1");
        assertEquals("Maps should be equal.", true, ks.equals(ks2));
        assertEquals("Hash codes should be equal.", ks.hashCode(), ks2.hashCode());

        initHMap(m, -100, 100);
        initHMap(m2, -100, 100);
        assertEquals("Maps should be equal.", true, ks.equals(ks2));
        assertEquals("Hash codes should be equal.", ks.hashCode(), ks2.hashCode());

        ks.remove((Object)0);
        ks2.remove((Object)0);
        assertEquals("Maps should be equal.", true, ks.equals(ks2));
        assertEquals("Hash codes should be equal.", ks.hashCode(), ks2.hashCode());

        m.put(101, "101");
        m2.put(101, "101");
        assertEquals("Maps should be equal.", true, ks.equals(ks2));
        assertEquals("Hash codes should be equal.", ks.hashCode(), ks2.hashCode());

        addToHMap(m, 500, 1000);
        addToHMap(m2, 500, 1000);
        assertEquals("Maps should be equal.", true, ks.equals(ks2));
        assertEquals("Hash codes should be equal.", ks.hashCode(), ks2.hashCode());

        HMap t = getIntegerMapAdapter(-1000, -900);
        m.putAll(t);
        m2.putAll(t);
        assertEquals("Maps should be equal.", true, ks.equals(ks2));
        assertEquals("Hash codes should be equal.", ks.hashCode(), ks2.hashCode());

        initHMap(t, 5000, 6000);
        m.putAll(t);
        m2.putAll(t);
        assertEquals("Maps should be equal.", true, ks.equals(ks2));
        assertEquals("Hash codes should be equal.", ks.hashCode(), ks2.hashCode());
    }

    // ------------------------------------------ remove method ------------------------------------------
    /**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests remove method invoked by map
     * and its KeySet and checks their consistency in propagation.
     * They both are empty, which is a limit case.</p>
     * <p><b>Test Description</b>: remove is invoked by map, m and set
     * are checked, remove is invoked by KeySet and m and set are checked again.</p>
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
        assertFalse(ks.remove("Random Object"));
        checkKeySet(m, ks);
    }

    /**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests remove method invoked by map
     * and its KeySet and checks their consistency in propagation.
     * Map contains 10 elements, and arguments are keys/entries not
     * in the map/set.</p>
     * <p><b>Test Description</b>: remove is invoked by map, m and set
     * are checked, remove is invoked by KeySet and m and set are checked again.</p>
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
        m.remove("Not in map Key");
        checkKeySet(m, ks);
        ks.remove("Not in KeySet Entry");
        checkKeySet(m, ks);
    }

    /**
     * <p><b>Summary</b>: remove method test case.
     * remove is tested to make changes from map and
     * from set, propagating removals from one to another.</p>
     * <p><b>Test Case Design</b>: After each removals and
     * modification to map and set's structures are checked
     * by checkKeySet. Test aims to show the correct propagation
     * of information.</p>
     * <p><b>Test Description</b>: Entries (i, "i") are inserted and removed
     * from the map remove. The map is initiated with {0="0", 1000="1000"},
     * therefore ks contains {0:1000}
     * and each element in map and KeySet is removed by map's remove.
     * The map is initiated with {0="0", 1000="1000"},
     * and each element in map and KeySet is removed by KeySet's remove.
     * </p>
     * <p><b>Pre-Condition</b>: map and set are empty.</p>
     * <p><b>Post-Condition</b>: map and set are empty.</p>
     * <p><b>Expected Results</b>: Each removal is correctly propagated
     * from map to KeySet and from KeySet to map. In particular,
     * they still contains coherent informations.</p>
     */
    @Test
    public void Remove_Backing0()
    {
        int bound = 1000;
        for (int i = 0; i < bound; i++)
        {
            m.put(i, "i");
            checkKeySet(m, ks);
            m.remove(i);
            checkKeySet(m, ks);
        }
        initHMap(m, 0, 1000);
        checkKeySet(m, ks);
        for (int i = bound - 1; i >= 0; i--)
        {
            m.remove(i);
            checkKeySet(m, ks);
        }
        initHMap(m, 0, 1000);
        checkKeySet(m, ks);
        for (int i = bound - 1; i >= 0; i--)
        {
            ks.remove(i);
            checkKeySet(m, ks);
        }
        checkKeySet(m, ks);
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
     * The collection keeps changing during execution.</p>
     * <p><b>Test Description</b>: The collection contains {0="0":500="500"},
     * while map contains {0="0" : i="i"} and the keyset contains 
     * {0:i}, for each i in (0,1000)
     * (Note that empty map/KeySet limit case is being tested). Then
     * ks.removeAll(c) is invoked and coherence is check through checkKeySet.
     * Then the test asserts that m and ks have the right size and isEmpty
     * returns the right value.</p>
     * <p><b>Pre-Condition</b>: c contains {0="0":500="500"}, m and ks are
     * empty.</p>
     * <p><b>Post-Condition</b>: c is unchanged, m contains
     * {500="500":1000="1000"}, while ks contains {500:1000}.</p>
     * <p><b>Expected Results</b>: Each removal is correctly propagated
     * from map to KeySet and from KeySet to map. In particular,
     * they still contains coherent informations.</p>
     */
    @Test
    public void RemoveAll_Backing0()
    {
        int bound = 1000;
        int secondBound = 500;
        c = getIntegerHCollection(0, secondBound);
        for (int i = 0; i < bound; i++)
        {
            initHMap(m, 0, i);
            ks.removeAll(c);
            checkKeySet(m, ks);

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

    // ------------------------------------------ retainAll method ------------------------------------------

    /**
     * <p><b>Summary</b>: retainAll method test case. The test aim
     * to test coherence of map and its KeySet after invoking retainAll
     * in different scenarios and problem size.</p>
     * <p><b>Test Case Design</b>: After each retainAll method invoke
     * the coherence is checked through checkKeySet. Tests right
     * propagation from the entry set to the map. Retaining all entries
     * from the KeySet implies retaining all the entries in the map.
     * The collection keeps changing during execution.</p>
     * <p><b>Test Description</b>: The collection contains {0="0":500="500"},
     * while map contains {0="0" : i="i"}
     * and the keySet contains {0:i}, for each i in (0,1000)
     * (Note that empty map/KeySet limit case is being tested). Then
     * ks.retainAll(c) is invoked and coherence is check through checkKeySet.
     * Then the test asserts that m and ks have the right size and isEmpty
     * returns the right value.</p>
     * <p><b>Pre-Condition</b>: c contains {0="0":500="500"}, m and ks are
     * empty.</p>
     * <p><b>Post-Condition</b>: c is unchanged, m contains
     * {0="0":500="500"} and ks contains {0:500}.</p>
     * <p><b>Expected Results</b>: Each retainAll is correctly propagated
     * from map to KeySet and from KeySet to map. In particular,
     * they still contains coherent informations.</p>
     */
    @Test
    public void RetainAll_Backing0()
    {
        int bound = 1000;
        int secondBound = 500;
        c = getIntegerHCollection(0, secondBound);
        for (int i = 0; i < bound; i++)
        {
            initHMap(m, 0, i);
            ks.retainAll(c);
            checkKeySet(m, ks);

            if (i <= secondBound)
                assertTrue("Both should have size " + i, m.size() == ks.size() && m.size() == i);
            else
                assertTrue("Both should have size 500", m.size() == ks.size() && m.size() == 500);
        }
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
     * toArray.</p>
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
     * of lenght 1 containing only 1, checked through checkToArray.</p>
     */
    @Test
    public void ToArray_OneElement()
    {
        m.put(1, "One");
        checkToArray(ks, ks.toArray());
    }

    /**
     * <p><b>Summary</b>: toArray and remove method test case.
     * Tests the behaviour of toArray method in different cases
     * and configurations of the KeySet.</p>
     * <p><b>Test Case Design</b>: Checks the array to be right
     * through checkToArray after each removals through ks.remove,
     * creating different situations and cases to test the method.</p>
     * <p><b>Test Description</b>: The test first removes the
     * entries with even key, then the entries with odd key. After each
     * removal the checkToArray method is invoked to check if the
     * generated array in all of this cases is right.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":1000="1000"}, therefore
     * ks contains {0:1000}..</p>
     * <p><b>Post-Condition</b>: m and ks are empty</p>
     * <p><b>Expected Results</b>: After each removal the generated array
     * through ks.toArray is right and coherent. At the end ks and m are empty.</p>
     */
    @Test
    public void ToArray_0To1000()
    {
        int bound = 1000;
        initHMap(m, 0, 1000);
        for (int i = 0; i < bound; i += 2)
        {
            ks.remove(i);
            checkToArray(ks, ks.toArray());
        }
        for (int i = 1; i < bound; i += 2)
        {
            ks.remove(i);
            checkToArray(ks, ks.toArray());
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
     * toArray.</p>
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
     * when the size of ks and map is 1, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an KeySet
     * containing only one element, then ks and the array returned
     * from toArray are checked through checkToArray.</p>
     * <p><b>Pre-Condition</b>: ks and m contains 1 element</p>
     * <p><b>Post-Condition</b>: ks and m are unchanged</p>
     * <p><b>Expected Results</b>: ks.toArray returns an array
     * of lenght 1 containing only the element 1="One"</p>
     */
    @Test
    public void ToArrayArrayArg_OneElement()
    {
        Object[] a = new Object[1];
        m.put(1, "One");
        ks.toArray(a);
        checkToArray(ks, a);
    }

    /**
     * <p><b>Summary</b>: toArray and remove method test case.
     * Tests the behaviour of toArray method in different cases
     * and configurations of the KeySet.</p>
     * <p><b>Test Case Design</b>: Checks the array to be right
     * through checkToArray after each removals through ks.remove,
     * creating different situations and cases to test the method.</p>
     * <p><b>Test Description</b>: The test first removes the
     * entries with even key, then the entries with odd key. After each
     * removal the checkToArray method is invoked to check if the
     * generated array in all of this cases is right.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":1000="1000"}, therefore
     * ks contains {0:1000}.</p>
     * <p><b>Post-Condition</b>: m and ks are empty</p>
     * <p><b>Expected Results</b>: After each removal the generated array
     * through ks.toArray is right and coherent. At the end ks and m are empty.</p>
     */
    @Test
    public void ToArrayArrayArg_0To1000()
    {
        int bound = 1000;
        
        initHMap(m, 0, 1000);
        for (int i = 0; i < bound; i += 2)
        {
            ks.remove(i);
            Object[] a = new Object[ks.size()];
            ks.toArray(a);
            checkToArray(ks, a);
        }
        for (int i = 1; i < bound; i += 2)
        {
            ks.remove(i);
            Object[] a = new Object[ks.size()];
            ks.toArray(a);
            checkToArray(ks, a);
        }
        assertEquals("Should be empty", 0, ks.size());
        assertEquals("Should be empty", 0, m.size());
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
    public void ESIterator_Empty()
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
    public void ESIterator_EmptyNSEE()
    {
        checkIteration(ks);
        ks.iterator().next();
    }

    /**
     * <p><b>Summary</b>: iteration on an KeySet test case.</p>
     * <p><b>Test Case Design</b>: Tests the iterator's behaviour
     * on an KeySet of variable size, including the size 1 and
     * a much bigger size.</p>
     * <p><b>Test Description</b>: checks if the iterator is right
     * through checkIteration method. In particular, checks
     * if the iterator iterated the right amount of times
     * (checks size coherence) and if after its last iteration
     * (if it happens, otherwise the first) has next.
     * The iteration is checked 1000 times for 1000 different configurations
     * of the HMap. Infact, m contains {0="0":i="i"} for
     * each i in {0:1000}, therefore ks contanis {0:i}.</p>
     * <p><b>Pre-Condition</b>: KeySet and m are empty</p>
     * <p><b>Post-Condition</b>: m contain {0="0":1000="1000"}, therefore
     * ks contanis {0:i}</p>
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
            checkIteration(ks);
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
    public void ESIterator_EmptyRemoveISE()
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
     * call to next) will throw HISE.</p>
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
    public void ESIterator_0To10RemoveISE()
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
     * to assure correct propagation iterator -> KeySet -> map.</p>
     * <p><b>Test Description</b>: map and ks initially contain
     * {0="0":1000="1000"}. An iterator iterates through
     * each element and after each next it invokes the remove
     * method, removing the just returned element.
     * Then checkKeySet and checkIteration are invoke
     * to check KeySet - map coherence and iteration.
     * After iterating through all elements, the iterator.hasNext
     * method must return false, and ks and m should be both empty.</p>
     * <p><b>Pre-Condition</b>: map contains
     * {0="0":1000="1000"}, therefore ks contains {0:1000}</p>
     * <p><b>Post-Condition</b>: m and ks are empty.</p>
     * <p><b>Expected Results</b>: Each remove invoke works right,
     * the element is removed correctly and checkKeySet and checkIteration
     * work fine after each removal.</p>
     */
    @Test
    public void ESIterator_0To100Remove()
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

    // ------------------------------------------ Coarse grained tests ------------------------------------------
    
    /**
     * <p><b>Summary</b>: iterator.remove and m.remove method test case.
     * Tests the behaviour of map and KeySet while elements are
     * constantly removed from them via different ways.</p>
     * <p><b>Test Case Design</b>: Tests different ways to remove
     * elements from the backing map and the KeySet. After each
     * removal checkEntry and checkIteration are invoked to
     * check map - KeySet coherence and iterator's iteration
     * working correctly. Tests map -> KeySet propagation and
     * iterator -> KeySet -> map propagation.</p>
     * <p><b>Test Description</b>: m is filled with entries {0="0":1000="1000"}.
     * Through a for loop entries {i="i"}, i being 10, 20, 30,...
     * are removed from the map through map.remove method. That means that 100 entries
     * are removed from m and ks. Then while the iterator's has next,
     * it removes 1 element through iterator.remove each 10 elements,
     * therefore it removes 90 entries, as the iterator starts iterating
     * the map and ks have 900 elements.</p>
     * <p><b>Pre-Condition</b>: m and ks are empty.</p>
     * <p><b>Post-Condition</b>: m and ks contains 810 entriks.</p>
     * <p><b>Expected Results</b>: Each removal from map and ks propagates
     * the changes correctly to the other structure. checkKeySet and checkIteration
     * are invoked to check coherence and that iteration works correctly.</p>
     */
    @Test
    public void RemoveIteratorEsMap()
    {
        int bound = 1000;
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
        assertTrue("100 should be removed", (initSize - m.size()) == 100 && m.size() == ks.size());
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
        assertTrue("190 should be removed", (initSize - m.size()) == 190 && m.size() == ks.size());
        assertTrue("Should be size 810", m.size() == ks.size() && m.size() == 810);
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
     * <p><b>Test Description</b>: esArr contains 100 the KeySet
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
        int bound = 100;
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

        // KeySets -> map
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

        // map -> KeySets
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


    private void checkKeySet(HMap m, HSet ks)
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

    private void checkToArray(HSet ks, Object[] arr)
    {
        assertEquals("The array and the set do NOT have the same elements.", ks.size(), arr.length);
        for (int i = 0; i < arr.length; i++)
            assertTrue("The array and the set do NOT have the same elements.", ks.contains(arr[i]));
    }

    private void checkIteration(HSet ks)
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