package myTest;

import static org.junit.Assert.*;
import org.junit.*;
import static myTest.TestUtilities.*;
import java.util.NoSuchElementException;
import myAdapter.*;

public class TestEntrySet 
{
    private MapAdapter m, m2;
    private HSet es, es2;
    private HCollection c;
    private HIterator it;

    @BeforeClass
    public static void BeforeClassMethod()
    {
        System.out.println(TestEntrySet.class.getName() + " running.");
    }

    @Before
    public void BeforeMethod()
    {
        m = new MapAdapter();
        m2 = new MapAdapter();
        es = m.entrySet();
        es2 = m2.entrySet();
        c = new CollectionAdapter();
    }

    @AfterClass
    public static void AfterClassMethod()
    {
        System.out.println(TestEntrySet.class.getName() + " ended.");
    }

    @After
    public void AfterMethod()
    {
        m = new MapAdapter();
        es = m.entrySet();
    }
    
    // ------------------------------------------ single method test case ------------------------------------------

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
     * map -> entryset is tested. checkEntrySet and checkIteration
     * are invoked to test entryset - map coherence and the iteration.</p>
     * <p><b>Pre-Condition</b>: The entryset contains 1 element.</p>
     * <p><b>Post-Condition</b>: The entryset contains 1 element.</p>
     * <p><b>Expected Results</b>: The size method returns 1 and 
     * as it only contains one element and the isEmpty
     * method returns false.</p>
     */
    @Test
    public void Size_OneElement_1()
    {
        m.put(1, "1");
        checkEntrySet(m, es);
        checkIteration(es);
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
     * map -> entryset is tested, as the map is modified
     * through addToHMap(m, 0, 3).</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the entryset.</p>
     * <p><b>Pre-Condition</b>: The entryset is empty.</p>
     * <p><b>Post-Condition</b>: The entryset has 3 elements ({0, 1, 2}).</p>
     * <p><b>Expected Results</b>: The size method returns 3 as the
     * entryset contains 3 elements and the isEmpty
     * method returns false. Map initialization keeps coherence.</p>
     */
    @Test
    public void Size_ThreeElements_3()
    {
        TestUtilities.addToHMap(m, 0, 3);
        checkEntrySet(m, es);
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
     * map -> entryset is tested, as the entryset is modified
     * through addToHMap(m, 0, 345).</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the entryset.</p>
     * <p><b>Pre-Condition</b>: The entryset is empty.</p>
     * <p><b>Post-Condition</b>: The entryset contains 345 elements ({0:345}).</p>
     * <p><b>Expected Results</b>: The size method returns 345 and the isEmpty
     * method returns false. Map initialization keeps coherence.</p>
     */
    @Test
    public void Size_345Elements_345()
    {
        TestUtilities.addToHMap(m, 0, 345);
        checkEntrySet(m, es);
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
     * <p><b>Expected Results</b>: contains returns false, as the object
     * string "Random object" is not contained in the entryset</p>
     */
    @Test
    public void Contains_Empty()
    {
        assertFalse("Should not contain", es.contains("Random object"));
    }

    
    /**
     * <p><b>Summary</b>: contains method test case</p>
     * <p><b>Test Case Design</b>: Tests if each entry of type
     * (i, "i") is contained in the entrySet, for i in (0,1000).
     * Propagation map -> entryset is tested, as map is modified
     * through addToHMap(m, 0, bound).</p>
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
        checkEntrySet(m, es);
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
     * Propagation map -> entryset is tested, as map is modified
     * through addToHMap(m, 0, bound).
     * This is obviusly false for each i, as es contains {0="0":1000="1000"},
     * and not {0="1":1000="1001"}.</p>
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
        checkEntrySet(m, es);
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
     * This is obviusly false for each i, as es contains {0="0":1000="1000"},
     * and not (1="0":1001="1000").
     * Propagation map -> entryset is tested, as map is modified
     * through addToHMap(m, 0, bound).</p>
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
        checkEntrySet(m, es);
        for (int i = 0; i < bound; i++)
        {
            HMap.Entry e = getEntry(i + 1, "" + i);   // value does NOT match
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
        c = getEntryHCollection(1, 4);
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
     * with some as argument. Propagation map -> entryset is tested.</p>
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
        c = getEntryHCollection(3, 6);
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
     * not present in the entryset is a common case for the method. Propagation
     * map -> entryset is tested.</p>
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
    
    // ------------------------------------------ equals method ------------------------------------------

    /**
     * <p><b>Summary</b>: equals method test case.</p>
     * <p><b>Test Case Design</b>: equals method is tested with an equal 
     * and a bigger and a smaller one. The returned values should be true and false.
     * Also reflective property of equal is tested.</p>
     * <p><b>Test Description</b>: EntrySet is initialized, then different equals invoke are
     * asserted with different arguments, generated for each case.</p>
     * <p><b>Pre-Condition</b>: EntrySet contains {0="0" : 2000="2000"}.</p>
     * <p><b>Post-Condition</b>: EntrySet is unchanged.</p>
     * <p><b>Expected Results</b>: The EntrySet is unchanged and symmetric property is valid.</p>
     */
    @Test
    public void Equals_0To10()
    {
        int to = 2000;
        TestUtilities.addToHMap(m, 0, to);
        assertEquals(true, es.equals(getIntegerMapAdapter(0, to).entrySet()));
        assertEquals(true, getIntegerMapAdapter(0, to).entrySet().equals(es));   // Symmetric property
        assertEquals(false, es.equals(getIntegerMapAdapter(0, to + 15).entrySet()));  // bigger EntrySet returns false
        assertEquals(false, es.equals(getIntegerMapAdapter(0, 5).entrySet()));  // smaller EntrySet returns false
    }

    
    /**
     * <p><b>Summary</b>: equals method test case.
     * The test case the method behaviour with 2 empty EntrySet.</p>
     * <p><b>Test Case Design</b>: When both EntrySets are empty the equals
     * method should return true because an empty EntrySet is equal to an
     * empty EntrySet.</p>
     * <p><b>Test Description</b>: Single assert, l1.equals(l2) invoked.</p>
     * <p><b>Pre-Condition</b>: Both EntrySet are empty.</p>
     * <p><b>Post-Condition</b>: Both EntrySet are empty.</p>
     * <p><b>Expected Results</b>: equals returns true, as one
     * empty EntrySet of course equals another empty EntrySet.</p>
     */
    @Test
    public void Equals_Empty_True()
    {
        assertEquals("Two empty EntrySets should equals.", true, es.equals(es2));
        assertEquals("Two empty EntrySets should equals.", true, es2.equals(es));
    }

    /**
     * <p><b>Summary</b>: equals method test case.
     * The reflective property of equal method is tested.</p>
     * <p><b>Test Case Design</b>: equals method should be reflective,
     * therefore x.equals(x) should always return true. Propagation
     * map -> entryset is tested through initHMap.</p>
     * <p><b>Test Description</b>: The test invokes es.equals(es) when
     * es is empty, when it has 10 elements and when it has 1000 elements.</p>
     * <p><b>Pre-Condition</b>: EntrySet is not null.</p>
     * <p><b>Post-Condition</b>: EntrySet has 1000 elements. </p>
     * <p><b>Expected Results</b>: EntrySet equals itself, therefore
     * reflective property is valid.</p>
     */
    @Test
    public void Equals_Reflective()
    {
        assertEquals("Reflective property is not met.", true, es.equals(es));    // EntrySet is empty
        TestUtilities.initHMap(m, 0, 10);
        assertEquals("Reflective property is not met.", true, es.equals(es));    // EntrySet is not empty, should return true anyways
        TestUtilities.initHMap(m, 0, 1000);
        assertEquals("Reflective property is not met.", true, es.equals(es));    // EntrySet is not empty, should return true anyways
    }

    /**
     * <p><b>Summary</b>: equals method test case.
     * The transitive property of equal method is tested.</p>
     * <p><b>Test Case Design</b>: equals method should be transitive,
     * therefore a.equals(b) and b.equals(c) {@literal =>} a.equals(c).
     * Propagation map -> entryset is tested.</p>
     * <p><b>Test Description</b>: The test invokes es.equals(es2) and es2.equals(EntrySet3)
     * and es.equals(es3)</p>
     * <p><b>Pre-Condition</b>: EntrySets contain {1 : 2000}.</p>
     * <p><b>Post-Condition</b>: EntrySets are unchanged. </p>
     * <p><b>Expected Results</b>: Equals has transitive property.</p>
     */
    @Test
    public void Equals_Transitive()
    {
        int to = 2000;
        addToHMap(m, 0, to);
        addToHMap(m2, 0, to);
        HSet es3 = getIntegerMapAdapter(0, to).entrySet();

        assertEquals("EntrySets should be equal.", true, es.equals(es2));
        assertEquals("EntrySets should be equal.", true, es2.equals(es3));
        assertEquals("Transitive property is not met.",true, es.equals(es3));
    }

    // ------------------------------------------ clear method ------------------------------------------

    /**
     * <p><b>Summary</b>: clear, containsKey, containsValue, get method test case.</p>
     * <p><b>Test Case Design</b>: Tests the behaviour of clear method of entrySet
     * and of map. Tests the backing map -> entrySet and viceversa, entrySet -> map.</p>
     * <p><b>Test Description</b>: map is initialized with {0="0" : 1000="1000"}.
     * Through checkEntrySet(m, es) and checkIteration(es) asserts that they both
     * share the same informations about the map entries. Then clear is invoked
     * by the entrySet. Same initialization and iteration are done,
     * but this time clear is invoked by m.</p>
     * <p><b>Pre-Condition</b>: m and es are empty.</p>
     * <p><b>Post-Condition</b>: m and es are still empty.</p>
     * <p><b>Expected Results</b>: clear propagates successfully from map to entrySet
     * and viceversa.</p>
     */
    @Test
    public void Clear_Backing0()
    {
        addToHMap(m, 0, 1000);
        assertEquals(m.size(), es.size());
        checkEntrySet(m, es);
        checkIteration(es);
        es.clear();
        checkEntrySet(m, es);
        checkIteration(es);
        assertTrue("Should both have size 0", (m.size() == es.size()) && es.size() == 0);
        assertTrue("Should be both empty", m.isEmpty() && es.isEmpty());

        addToHMap(m, 0, 1000);
        checkEntrySet(m, es);
        checkIteration(es);
        m.clear();  // Invoked from m this time
        checkEntrySet(m, es);
        checkIteration(es);
        assertTrue("Should both have size 0", (m.size() == es.size()) && es.size() == 0);
        assertTrue("Should be both empty", m.isEmpty() && es.isEmpty());
    }

    /**
     * <p><b>Summary</b>: clear method test case.</p>
     * <p><b>Test Case Design</b>: Tests the behaviour of clear method of entrySet
     * and map when they both are empty, which is a limit case (obviusly the limit case is that
     * they are empty, not that they have the same size, as it is trivial).</p>
     * <p><b>Test Description</b>: clear is invoked by m and they are checked through checkEntrySet,
     * and checkIteration.
     * Same then but clear is invoked by the entrySet and they are checked through checkEntrySet,
     * and checkIteration.</p>
     * <p><b>Pre-Condition</b>: m and es are empty</p>
     * <p><b>Post-Condition</b>: m and es are empty</p>
     * <p><b>Expected Results</b>: clear propagates successfully from map to entrySet
     * and viceversa.</p>
     */
    @Test
    public void Clear_Empty()
    {
        m.clear();
        checkEntrySet(m, es);
        checkIteration(es);
        es.clear();
        checkEntrySet(m, es);
        checkIteration(es);
    }
    



    // ------------------------------------------ hashCode method ------------------------------------------

    /**
     * <p><b>Summary</b>: hashCode test case.
     * Tests the behaviour of hashCode method with different
     * map and entrySet configurations.</p>
     * <p><b>Test Case Design</b>: The same operations are applied to map 1 and 2,
     * so their entrySets must have the same elements each time, therefore they are equals.
     * If they are equals they must have the same hashCode. Changes propagates successfully from map to entrySet
     * and viceversa, tests therefore the consinstency of equals and hashCode.</p>
     * <p><b>Test Description</b>: Different configurations have been tested. In mathematical symbols, considering the keys:
     * empty, {1}, {-100:100}, {-100:100}/{0}, {-100:101}/{0}, {-100:100, 500:1000}/{0},
     * {-100:100, 500:1000} U {-1000:-900}/({0} ), {-100:100, 500:1000, 5000:6000}  U {-1000:-900}/({0})</p>
     * <p><b>Pre-Condition</b>: EntrySets have same hashCode and they are equal.</p>
     * <p><b>Post-Condition</b>: EntrySets have same hashCode and they are equal.</p>
     * <p><b>Expected Results</b>: EntrySets have same hashCode and they are equal.</p>
     */
    @Test
    public void HashCode_Mixed()
    {
        // Empty map case
        assertEquals("maps should be equal.", true, es.equals(es2));
        assertEquals("Hash codes should be equal.", es.hashCode(), es2.hashCode());

        // One element case
        m.put(1, "1");
        m2.put(1, "1");
        assertEquals("maps should be equal.", true, es.equals(es2));
        assertEquals("Hash codes should be equal.", es.hashCode(), es2.hashCode());

        TestUtilities.initHMap(m, -100, 100);
        TestUtilities.initHMap(m2, -100, 100);
        assertEquals("maps should be equal.", true, es.equals(es2));
        assertEquals("Hash codes should be equal.", es.hashCode(), es2.hashCode());

        es.remove((Object)0);
        es2.remove((Object)0);
        assertEquals("maps should be equal.", true, es.equals(es2));
        assertEquals("Hash codes should be equal.", es.hashCode(), es2.hashCode());

        m.put(101, "101");
        m2.put(101, "101");
        assertEquals("maps should be equal.", true, es.equals(es2));
        assertEquals("Hash codes should be equal.", es.hashCode(), es2.hashCode());

        TestUtilities.addToHMap(m, 500, 1000);
        TestUtilities.addToHMap(m2, 500, 1000);
        assertEquals("maps should be equal.", true, es.equals(es2));
        assertEquals("Hash codes should be equal.", es.hashCode(), es2.hashCode());

        HMap t = TestUtilities.getIntegerMapAdapter(-1000, -900);
        m.putAll(t);
        m2.putAll(t);
        assertEquals("maps should be equal.", true, es.equals(es2));
        assertEquals("Hash codes should be equal.", es.hashCode(), es2.hashCode());

        TestUtilities.initHMap(t, 5000, 6000);
        m.putAll(t);
        m2.putAll(t);
        assertEquals("maps should be equal.", true, es.equals(es2));
        assertEquals("Hash codes should be equal.", es.hashCode(), es2.hashCode());
    }

    // ------------------------------------------ remove method ------------------------------------------
    /**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests remove method invoked by map
     * and its entrySet and checks their consistency in propagation.
     * They both are empty, which is a limit case.</p>
     * <p><b>Test Description</b>: remove is invoked by map, m and set
     * are checked, remove is invoked by entryset and m and set are checked again.
     * Through checkEntrySet(m, es) and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     * <p><b>Pre-Condition</b>: m and es are empty.</p>
     * <p><b>Post-Condition</b>: m and es are unchanged.</p>
     * <p><b>Expected Results</b>: m.remove returns null object,
     * while es.remove returns false. map and es pass the check
     * after each remove invoke.</p>
     */
    @Test
    public void Remove_Empty()
    {
        assertNull(m.remove("Random Key"));
        checkEntrySet(m, es);
        checkIteration(es);
        assertFalse(es.remove("Random Object"));
        checkEntrySet(m, es);
        checkIteration(es);
    }

    /**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests remove method invoked by map
     * and its entrySet and checks their consistency in propagation.
     * Map contains 10 elements, and arguments are keys/entries not
     * in the map/set.</p>
     * <p><b>Test Description</b>: remove is invoked by map, m and set
     * are checked, remove is invoked by entryset and m and set are checked again.
     * Through checkEntrySet(m, es) and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     * <p><b>Pre-Condition</b>: m and es contains {0="0", 10="10"}.</p>
     * <p><b>Post-Condition</b>: m and es are unchanged.</p>
     * <p><b>Expected Results</b>: m.remove returns null object,
     * while es.remove returns false. map and es pass the check
     * after each remove invoke.</p>
     */
    @Test
    public void Remove_NotInMap()
    {
        addToHMap(m, 0, 10);
        m.remove("Not in map Key");
        checkEntrySet(m, es);
        checkIteration(es);
        es.remove("Not in entryset Entry");
        checkEntrySet(m, es);
        checkIteration(es);
    }

    /**
     * <p><b>Summary</b>: remove method test case.
     * remove is tested to make changes from map and
     * from set, propagating removals from one to another.</p>
     * <p><b>Test Case Design</b>: After each removals and
     * modification to map and set's structures are checked
     * by checkEntrySet. Test aims to show the correct propagation
     * of information.</p>
     * <p><b>Test Description</b>: Entries (i, "i") are inserted and removed
     * from the map remove. The map is initiated with {0="0", 500="500"},
     * and each element in map and entrySet is removed by map's remove.
     * The map is initiated with {0="0", 500="500"},
     * and each element in map and entrySet is removed by entrySet's remove.
     * After each modification to the map, through checkEntrySet(m, es) and checkIteration(es)
     * asserts that they both
     * share the same informations about the map entries.
     * </p>
     * <p><b>Pre-Condition</b>: map and set are empty.</p>
     * <p><b>Post-Condition</b>: map and set are empty.</p>
     * <p><b>Expected Results</b>: Each removal is correctly propagated
     * from map to entrySet and from entrySet to map. Through checkEntrySet(m, es)
     * and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void Remove_Backing0()
    {
        int bound = 500;
        for (int i = 0; i < bound; i++)
        {
            m.put(i, "i");
            checkEntrySet(m, es);
            checkIteration(es);
            m.remove(i);
            checkEntrySet(m, es);
            checkIteration(es);
        }
        initHMap(m, 0, 500);
        checkEntrySet(m, es);
        checkIteration(es);
        for (int i = bound - 1; i >= 0; i--)
        {
            m.remove(i);
            checkEntrySet(m, es);
        }
        initHMap(m, 0, 500);
        checkEntrySet(m, es);
        checkIteration(es);
        for (int i = bound - 1; i >= 0; i--)
        {
            es.remove(getEntry(i, "" + i));
            checkEntrySet(m, es);
            checkIteration(es);
        }
        checkEntrySet(m, es);
        checkIteration(es);
    }

    // -------------------- removeAll method --------------------

    /**
     * <p><b>Summary</b>: removeAll method test case. The test aim
     * to test coherence of map and its entrySet after invoking removeAll
     * in different scenarios and problem size.</p>
     * <p><b>Test Case Design</b>: After each removeAll method invoke
     * the coherence is checked through checkEntrySet. Tests right
     * propagation from the entry set to the map. Removing all entries
     * from the entrySet implies removing all the entries in the map.
     * The collection keeps changing during execution.</p>
     * <p><b>Test Description</b>: The collection contains {0="0":500="500"},
     * while map and entrySet contain {0="0" : i="i"}, for each i in (0,1000)
     * (Note that empty map/entrySet limit case is being tested). Then
     * es.removeAll(c) is invoked and coherence is check through checkEntrySet.
     * Then the test asserts that m and es have the right size and isEmpty
     * returns the right value. After each modification of the map and
     * set, coherence is checked, through checkEntrySet(m, es) and checkIteration(es)
     * asserts that they both
     * share the same informations about the map entries.</p>
     * <p><b>Pre-Condition</b>: c contains {0="0":500="500"}, m and es are
     * empty.</p>
     * <p><b>Post-Condition</b>: c is unchanged, m and es contain
     * {500="500":1000="1000"}.</p>
     * <p><b>Expected Results</b>: Each removal is correctly propagated
     * from map to entrySet and from entrySet to map. Through checkEntrySet(m, es)
     * and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void RemoveAll_Backing0()
    {
        int bound = 1000;
        int secondBound = 500;
        c = getEntryHCollection(0, secondBound);
        for (int i = 0; i < bound; i++)
        {
            initHMap(m, 0, i);
            es.removeAll(c);
            checkEntrySet(m, es);

            if (i <= secondBound)
            {
                assertTrue("Both should have size 0", m.size() == es.size() && m.size() == 0);
                assertTrue("Both should be empty", es.isEmpty() == m.isEmpty() && es.isEmpty());
            }
            else
            {
                assertTrue("Both should have size > 0", m.size() == es.size() && m.size() == i - secondBound);
                assertTrue("Both should not be empty", es.isEmpty() == m.isEmpty() && !es.isEmpty());
            }
        }
    }

    // -------------------- retainAll method --------------------

    /**
     * <p><b>Summary</b>: retainAll method test case. The test aim
     * to test coherence of map and its entrySet after invoking retainAll
     * in different scenarios and problem size.</p>
     * <p><b>Test Case Design</b>: After each retainAll method invoke
     * the coherence is checked through checkEntrySet. Tests right
     * propagation from the entry set to the map. Retaining all entries
     * from the entrySet implies retaining all the entries in the map.
     * The collection keeps changing during execution.</p>
     * <p><b>Test Description</b>: The collection contains {0="0":500="500"},
     * while map and entrySet contain {0="0" : i="i"}, for each i in (0,1000)
     * (Note that empty map/entrySet limit case is being tested). Then
     * es.retainAll(c) is invoked and coherence is checked through checkEntrySet(m, es)
     * and checkIteration(es), asserting that they both
     * share the same informations about the map entries.
     * Then the test asserts that m and es have the right size and isEmpty
     * returns the right value.</p>
     * <p><b>Pre-Condition</b>: c contains {0="0":500="500"}, m and es are
     * empty.</p>
     * <p><b>Post-Condition</b>: c is unchanged, m and es contain
     * {0="0":500="500"}.</p>
     * <p><b>Expected Results</b>: Each retainAll is correctly propagated
     * from map to entrySet and from entrySet to map. Through checkEntrySet(m, es)
     * and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void RetainAll_Backing0()
    {
        int bound = 1000;
        int secondBound = 500;
        c = getEntryHCollection(0, secondBound);
        for (int i = 0; i < bound; i++)
        {
            initHMap(m, 0, i);
            es.retainAll(c);
            checkEntrySet(m, es);

            if (i <= secondBound)
                assertTrue("Both should have size 0", m.size() == es.size() && m.size() == i);
            else
                assertTrue("Both should have size 500", m.size() == es.size() && m.size() == 500);
        }
    }

        /**
     * <p><b>Summary</b>: retainAll method test case.
     * retainAll is being called with an empty collection as an argument,
     * therefore the set should became the empty set, since mainteining
     * only the "empty" subset means deleting all the elements.</p>
     * <p><b>Test Case Design</b>: retainAll being called with the limit case of
     * an empty collection as an argument.</p>
     * <p><b>Test Description</b>: The set removes all but "empty", so
     * it empties. In fact initially it contains {1="1", 2="2", 3="3"}.
     * Through checkEntrySet(m, es) and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     * <p><b>Pre-Condition</b>: The set contains {1="1", 2="2", 3="3"}.</p>
     * <p><b>Post-Condition</b>: The set is empty.</p>
     * <p><b>Expected Results</b>: The set is empty, retainAll returns true
     * because the set has changed. Through checkEntrySet(m, es) and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void RetainAll_Empty_True()
    {
        initHMap(m, 1, 4);
        assertEquals("The set has changed, it should return true.", true, es.retainAll(c));
        assertEquals("set should be empty.", 0, es.size());
        assertEquals("coll should be empty.", 0, c.size());
        checkEntrySet(m, es);
        checkIteration(es);
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * retainAll is being called with an empty collection as an argument and
     * an empty set as object,
     * therefore the set should remain the empty set, since mainteining
     * only the "empty" subset means deleting all the elements. Unlike the
     * RetainAll_Empty_True test case, the set is already empty, therefore
     * the method returns false.</p>
     * <p><b>Test Case Design</b>: retainAll being called with the limit case of
     * an empty collection as an argument and an empty set.</p>
     * <p><b>Test Description</b>: The set removes all but "empty", so
     * it empties. Through checkEntrySet(m, es) and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     * <p><b>Pre-Condition</b>: The set is empty.</p>
     * <p><b>Post-Condition</b>: The set is still empty.</p>
     * <p><b>Expected Results</b>: retainAll returns false because the set is unchanged.
     * Coherence is checked after retainAll invoke. Through checkEntrySet(m, es)
     * and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void RetainAll_Empty_False()
    {
        assertEquals("The set has not changed, it should return false.", false, es.retainAll(c));
        checkEntrySet(m, es);
        checkIteration(es);
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * The test calls retainAll with a collection containing
     * few elements.</p>
     * <p><b>Test Case Design</b>: The retainAll method is tested with small
     * input. Testing a typical case.</p>
     * <p><b>Test Description</b>: The set initially contains entries integer - string
     * from 1 to 5 included. retainAll is called with a collection
     * containing {3=3, 4=4, 5=5}, therefore the set should contain
     * {3=3, 4=4, 5=5}. Through checkEntrySet(m, es) and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     * <p><b>Pre-Condition</b>: The set contains {1="1", ..., 5="5"}, c contains
     * {3=3, 4=4, 5=5}.</p>
     * <p><b>Post-Condition</b>: The set contains {3=3, 4=4, 5=5}, c contains
     * {3=3, 4=4, 5=5}.</p>
     * <p><b>Expected Results</b>: set contains {3=3, 4=4, 5=5}, set has changed. retainAll returns true
     * because the set has changed. Coherence is checked after retainAll invoke.
     * Through checkEntrySet(m, es) and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void RetainAll_12345()
    {
        initHMap(m, 1, 6);
        c = getEntryHCollection(3, 6);
        assertEquals("The set has changed, it should return true.", true, es.retainAll(c));
        assertTrue("set should contain {3=3, 4=4, 5=5}.", es.equals(getEntryHSet(3, 6)));
        checkEntrySet(m, es);
        checkIteration(es);
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * The test calls retainAll with a collection containing
     * few elements. Testing a typical case.</p>
     * <p><b>Test Case Design</b>: The retainAll method is tested with small
     * input.</p>
     * <p><b>Test Description</b>: The set initially contains entries integer - string
     * from 1 to 9 included. retainAll is called with a collection
     * containing {2="2", 3="3"}, therefore the set should contain
     * {2="2", 3="3"}. Through checkEntrySet(m, es) and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     * <p><b>Pre-Condition</b>: The set contains {1="1":10="10"}, c contains
     * {2="2", 3="3"}.</p>
     * <p><b>Post-Condition</b>: The set contains {2="2", 3="3"}, c contains
     * {2="2", 3="3"}.</p>
     * <p><b>Expected Results</b>: set contains {2="2", 3="3"}, set has changed.  retainAll returns true
     * because the set has changed. Coherence is checked after retainAll invoke.
     * Through checkEntrySet(m, es) and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void RetainAll_23()
    {
        initHMap(m, 1, 10);
        c.add(getEntry(2, "2"));
        c.add(getEntry(3, "3"));
        assertTrue("The set has changed, it should return true.", es.retainAll(c));
        assertTrue("set should contain {2=2, 3=3}.", es.equals(getEntryHSet(2, 4)));
        checkEntrySet(m, es);
        checkIteration(es);
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * The test calls retainAll with a collection containing
     * many elements. Testing a typical case with a large input.</p>
     * <p><b>Test Case Design</b>:  The retainAll method is tested with large
     * input. The case is still a common case.</p>
     * <p><b>Test Description</b>: The set initially contains entries integer - string
     * from 1 to 999 included. retainAll is called with a collection
     * containing {300="300" : 600="600"}, therefore the set should contain
     * {300="300" : 600="600"}. Through checkEntrySet(m, es) and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     * <p><b>Pre-Condition</b>: The set contains {1="1" : 1000="1000"}, c contains
     * {300="300" : 600="600"}.</p>
     * <p><b>Post-Condition</b>: The set contains {300="300" : 600="600"}, c contains
     * {300="300" : 600="600"}.</p>
     * <p><b>Expected Results</b>: The arrays are equal, therefore set contains {300="300" : 600="600"}. retainAll returns true
     * as the set is being modified. Coherence is checked after each retainAll invoke.
     * Through checkEntrySet(m, es) and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void RetainAll_1000()
    {
        initHMap(m, 0, 1000);
        c = getEntryHCollection(300, 600);
        es.retainAll(c);
        assertEquals("The arrays should match.", getEntryHSet(300, 600), es);
        checkEntrySet(m, es);
        checkIteration(es);
    }

	/**
     * <p><b>Summary</b>: retainAll method test case.
     * retainAll is being called with collection containing element
     * not present in the set as an argument,
     * therefore the set should became the empty set, since mainteining
     * only a subset not contained in the set means deleting all the elements.</p>
     * <p><b>Test Case Design</b>: retainAll being called with the limit case of
     * an empty intersection of set and c.</p>
     * <p><b>Test Description</b>: The set removes all but "empty", so
     * it empties. In fact initially it contains {1="1":20="20"}.
     * Through checkEntrySet(m, es) and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     * <p><b>Pre-Condition</b>: The set contains {1="1":20="20"}.</p>
     * <p><b>Post-Condition</b>: The set is empty.</p>
     * <p><b>Expected Results</b>: The set is empty, retainAll returns true
     * because the set changes. Coherence is checked after each retainAll invoke.
     * Through checkEntrySet(m, es) and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void RetainAll_ToEmpty()
    {
        initHMap(m, 1, 20);
        TestUtilities.initHCollection(c, 20, 24);

        assertEquals("The set has changed, it should return true.", true, es.retainAll(c));
        assertEquals("The set should be empty.", 0, es.size());
        checkEntrySet(m, es);
        checkIteration(es);
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * The method is being tested with a collection containing
     * duplicated elements. This should not change the method
     * behaviour as the absence of one element in c removes
     * all its occurencies in the set.</p>
     * <p><b>Test Case Design</b>: c can contain duplicated element, and
     * this should not change retainAll behaviour. At the end of
     * retainAll execution every element not contained in coll
     * must be removed.</p>
     * <p><b>Test Description</b>: set contains {1="1", ..., 19="19"}. c contains
     * {4="4", 4="4", 5="5", 5="5", 6="6", 6="6"}. retainAll is called, so the set should contain
     * {4="4", 5="5", 6="6"}. Through checkEntrySet(m, es) and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     * <p><b>Pre-Condition</b>: set contains {1="1", ..., 19="19"}. c contains
     * {4="4", 4="4", 5="5", 5="5", 6="6", 6="6"}.</p>
     * <p><b>Post-Condition</b>: set contains {4="4", 5="5", 6="6"}. c contains
     * {4="4", 4="4", 5="5", 5="5", 6="6", 6="6"}.</p>
     * <p><b>Expected Results</b>: The arrays are equal, therefore the
     * set contains {4="4", 5="5", 6="6"}. retainAll returns true
     * as the set is being modified. Through checkEntrySet(m, es) and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void RetainAll_DuplicatesColl()
    {
        initHMap(m, 0, 20);
        for (int i = 4; i < 7; i++)
        {
            c.add(getEntry(i, ""+i));
            c.add(getEntry(i, ""+i));
        }
        assertEquals("The set has changed, it should return true.", true, es.retainAll(c));
        assertEquals("The arrays should match.", getEntryHSet(4, 7), es);
        checkEntrySet(m, es);
        checkIteration(es);
    }


    // -------------------- toArray() method --------------------

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of es and map is zero, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an empty
     * entrySet</p>
     * <p><b>Pre-Condition</b>: es and m are empty</p>
     * <p><b>Post-Condition</b>: es and m are empty</p>
     * <p><b>Expected Results</b>: es.toArray returns an empty
     * toArray. Through checkToArray(es, es.toArray()) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void ToArray_Empty()
    {
        assertArrayEquals(new Object[0], es.toArray());
        checkToArray(es, es.toArray());
    }

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of es and map is 1, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an entryset
     * containing only one element, then es and the array returned
     * from toArray are checked through checkToArray.</p>
     * <p><b>Pre-Condition</b>: es and m contains 1 element</p>
     * <p><b>Post-Condition</b>: es and m are unchanged</p>
     * <p><b>Expected Results</b>: es.toArray returns an array
     * of lenght 1 containing only the element 1="One".
     * Through checkToArray(es, es.toArray()) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void ToArray_OneElement()
    {
        m.put(1, "One");
        checkToArray(es, es.toArray());
    }

    /**
     * <p><b>Summary</b>: toArray and remove method test case.
     * Tests the behaviour of toArray method in different cases
     * and configurations of the entrySet.</p>
     * <p><b>Test Case Design</b>: Checks the array to be right
     * through checkToArray after each removals through es.remove,
     * creating different situations and cases to test the method.</p>
     * <p><b>Test Description</b>: The test first removes the
     * entries with even key, then the entries with odd key. After each
     * removal the checkToArray method is invoked to check if the
     * generated array in all of this cases is right.</p>
     * <p><b>Pre-Condition</b>: m and es contain {0="0":500="500"}.</p>
     * <p><b>Post-Condition</b>: m and es are empty</p>
     * <p><b>Expected Results</b>: After each removal the generated array
     * through es.toArray is right and coherent. At the end es and m are empty.
     * Through checkToArray(es, es.toArray()), checkEntrySet(m, es)
     * and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void ToArray_0To500()
    {
        int bound = 500;
        initHMap(m, 0, 500);
        for (int i = 0; i < bound; i += 2)
        {
            es.remove(getEntry(i, "" + i));
            checkToArray(es, es.toArray());
            checkEntrySet(m, es);
            checkIteration(es);
        }
        for (int i = 1; i < bound; i += 2)
        {
            es.remove(getEntry(i, "" + i));
            checkToArray(es, es.toArray());
            checkEntrySet(m, es);
            checkIteration(es);
        }
        assertEquals("Should be empty", 0, es.size());
        assertEquals("Should be empty", 0, m.size());
    }

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of es and map is zero, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an empty
     * entrySet</p>
     * <p><b>Pre-Condition</b>: es and m are empty</p>
     * <p><b>Post-Condition</b>: es and m are empty</p>
     * <p><b>Expected Results</b>: es.toArray returns an empty
     * toArray. Through checkToArray(es, es.toArray()), asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void ToArrayArrayArg_Empty()
    {
        Object[] a = new Object[0];
        es.toArray(a);
        assertArrayEquals(new Object[0], a);
        checkToArray(es, a);
    }

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of es and map is 1, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an entryset
     * containing only one element, then es and the array returned
     * from toArray are checked through checkToArray.</p>
     * <p><b>Pre-Condition</b>: es and m contains 1 element</p>
     * <p><b>Post-Condition</b>: es and m are unchanged</p>
     * <p><b>Expected Results</b>: es.toArray returns an array
     * of lenght 1 containing only the element 1="One".
     * Through checkToArray(es, es.toArray()), checkEntrySet(m, es)
     * and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void ToArrayArrayArg_OneElement()
    {
        Object[] a = new Object[1];
        m.put(1, "One");
        es.toArray(a);
        checkToArray(es, a);
        checkEntrySet(m, es);
        checkIteration(es);
    }

    /**
     * <p><b>Summary</b>: toArray and remove method test case.
     * Tests the behaviour of toArray method in different cases
     * and configurations of the entrySet.</p>
     * <p><b>Test Case Design</b>: Checks the array to be right
     * through checkToArray after each removals through es.remove,
     * creating different situations and cases to test the method.</p>
     * <p><b>Test Description</b>: The test first removes the
     * entries with even key, then the entries with odd key. After each
     * removal the checkToArray method is invoked to check if the
     * generated array in all of this cases is right.</p>
     * <p><b>Pre-Condition</b>: m and es contain {0="0":500="500"}.</p>
     * <p><b>Post-Condition</b>: m and es are empty</p>
     * <p><b>Expected Results</b>: After each removal the generated array
     * through es.toArray is right and coherent. At the end es and m are empty.
     * Through checkToArray(es, es.toArray()), checkEntrySet(m, es)
     * and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void ToArrayArrayArg_0To500()
    {
        int bound = 500;
        
        initHMap(m, 0, 500);
        for (int i = 0; i < bound; i += 2)
        {
            es.remove(getEntry(i, "" + i));
            Object[] a = new Object[es.size()];
            es.toArray(a);
            checkToArray(es, a);
            checkEntrySet(m, es);
            checkIteration(es);
        }
        for (int i = 1; i < bound; i += 2)
        {
            es.remove(getEntry(i, "" + i));
            Object[] a = new Object[es.size()];
            es.toArray(a);
            checkToArray(es, a);
            checkEntrySet(m, es);
            checkIteration(es);
        }
        assertEquals("Should be empty", 0, es.size());
        assertEquals("Should be empty", 0, m.size());
    }

    // ------------------------------------------ iterator method and EntrySetIterator ------------------------------------------
    
    /**
     * <p><b>Summary</b>: iteration on an entryset test case.</p>
     * <p><b>Test Case Design</b>: Tests the iterator's behaviour
     * on the limit case of an empty entrySet.</p>
     * <p><b>Test Description</b>: checks if the iterator is right
     * through checkIteration method. In particular, checks
     * if the iterator iterated the right amount of times
     * (checks size coherence) and if after its last iteration
     * (if it happens, otherwise the first) has next.</p>
     * <p><b>Pre-Condition</b>: entrySet is empty</p>
     * <p><b>Post-Condition</b>: entrySet is empty</p>
     * <p><b>Expected Results</b>: first iteration has no next,
     * iterated 0 times as the entrylist is empty.</p>
     */
    @Test
    public void ESIterator_Empty()
    {
        checkIteration(es);
    }

    /**
     * <p><b>Summary</b>: iteration on an entryset test case.</p>
     * <p><b>Test Case Design</b>: Tests the iterator's behaviour
     * on the limit case of an empty entrySet.</p>
     * <p><b>Test Description</b>: checks if the iterator is right
     * through checkIteration method. In particular, checks
     * if the iterator iterated the right amount of times
     * (checks size coherence) and if after its last iteration
     * (if it happens, otherwise the first) has next.
     * Then invokes next method on iterator, which should throw
     * NoSuchElementException as the iterator, being empty,
     * has no next.</p>
     * <p><b>Pre-Condition</b>: entrySet is empty</p>
     * <p><b>Post-Condition</b>: entrySet is empty</p>
     * <p><b>Expected Results</b>: first iteration has no next,
     * iterated 0 times as the entrylist is empty. NSEE is thrown
     * due to iterator.next() call.</p>
     */
    @Test (expected = NoSuchElementException.class)
    public void ESIterator_EmptyNSEE()
    {
        checkIteration(es);
        es.iterator().next();
    }

    /**
     * <p><b>Summary</b>: iteration on an entryset test case.</p>
     * <p><b>Test Case Design</b>: Tests the iterator's behaviour
     * on an entrySet of variable size, including the size 1 and
     * a much bigger size.</p>
     * <p><b>Test Description</b>: checks if the iterator is right
     * through checkIteration method. In particular, checks
     * if the iterator iterated the right amount of times
     * (checks size coherence) and if after its last iteration
     * (if it happens, otherwise the first) has next.
     * The iteration is checked 1000 times for 1000 different configurations
     * of the HMap. Infact, m contains {0="0":i="i"} for
     * each i in {0:1000}.</p>
     * <p><b>Pre-Condition</b>: entrySet and m are empty</p>
     * <p><b>Post-Condition</b>: entrySet and m contain {0="0":1000="1000"}</p>
     * <p><b>Expected Results</b>: For each iteration, m containing {0="0":i="i"},
     * the iteration is tested through checkIteration. In particular,
     * iterates i times, for each i in {0:1000}.
     * Through checkEntrySet(m, es) and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void ESIterator_Variable()
    {
        int bound = 1000;
        for (int i = 1; i < bound; i++)
        {
            initHMap(m, 0, i);
            checkIteration(es);
            checkEntrySet(m, es);
        }
    }

    /**
     * <p><b>Summary</b>: iteration on entryset test case.</p>
     * <p><b>Test Case Design</b>: Tests the iterator's remove
     * method behaviour when the entryset is empty, which is a limit case.
     * Obviusly a next method would throw NSEE, therefore a remove
     * method, that needs a next invoke before using it (This method can
     * be called only once per
     * call to next) will throw HISE.</p>
     * <p><b>Test Description</b>: remove method is invoked by a generated
     * iterator.</p>
     * <p><b>Pre-Condition</b>: m and es are empty.</p>
     * <p><b>Post-Condition</b>: m and es are empty.</p>
     * <p><b>Expected Results</b>: HIllegalStateException has been thrown
     * by iterator.remove().</p>
     */
    @Test (expected = HIllegalStateException.class)
    public void ESIterator_EmptyRemoveISE()
    {
        es.iterator().remove();
    }

    /**
     * <p><b>Summary</b>: iteration on entryset test case.</p>
     * <p><b>Test Case Design</b>: Tests the iterator's remove
     * method behaviour when the entryset contains {0="0" : 10="10"}.
     * For the first remove a next is invoked before, which must work
     * normally. The iteration is check through checkIterarion, and the
     * map - entryset coherence is checked through checkEntrySet.
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
     * <p><b>Pre-Condition</b>: m and es contains {0="0":10="10"}.</p>
     * <p><b>Post-Condition</b>: m and es contains {0="0":10="10"}.</p>
     * <p><b>Expected Results</b>: iteration and entrySet are checked through checkIteration
     * and checkEntrySet after the first remove. HIllegalStateException has been thrown
     * by iterator.remove().</p>
     */
    @Test (expected = HIllegalStateException.class)
    public void ESIterator_0To10RemoveISE()
    {
        initHMap(m, 0, 10);
        it = es.iterator();
        
        it.next();
        it.remove();
        checkEntrySet(m, es);
        checkIteration(es);

        // No previous next
        it.remove();
    }

    /**
     * <p><b>Summary</b>: iteration on entryset test case.
     * Tests iterator.remove method on a changing entryset,
     * checking entryset - map coherence and the iteration
     * with checkEntrySet and checkIteration.</p>
     * <p><b>Test Case Design</b>: The map is constantly
     * changing during execution due to it.remove,
     * therefore coherence and iteration must be check
     * to assure correct propagation iterator -> entryset -> map.</p>
     * <p><b>Test Description</b>: map and es initially contain
     * {0="0":1000="1000"}. An iterator iterates through
     * each element and after each next it invokes the remove
     * method, removing the just returned element.
     * Then checkEntrySet and checkIteration are invoke
     * to check entryset - map coherence and iteration.
     * After iterating through all elements, the iterator.hasNext
     * method must return false, and es and m should be both empty.</p>
     * <p><b>Pre-Condition</b>: map and es initially contain
     * {0="0":1000="1000"}</p>
     * <p><b>Post-Condition</b>: m and es are empty.</p>
     * <p><b>Expected Results</b>: Each remove invoke works right,
     * the element is removed correctly and through checkEntrySet(m, es)
     * and checkIteration(es) asserts that they both
     * share the same informations about the map entries.</p>
     */
    @Test
    public void ESIterator_0To100Remove()
    {
        initHMap(m, 0, 100);
        it = es.iterator();
        
        while (it.hasNext())
        {
            HMap.Entry e = (HMap.Entry)it.next();
            it.remove();

            assertFalse("Should be removed", m.containsKey(e.getKey()));
            checkEntrySet(m, es);
            checkIteration(es);
        }
        assertFalse("Should have not next", it.hasNext());
        assertTrue("Should be empty.", es.isEmpty() && m.isEmpty());
    }

    // ------------------------------------------ Coarse grained tests ------------------------------------------
    
    /**
     * <p><b>Summary</b>: iterator.remove and m.remove method test case.
     * Tests the behaviour of map and entrySet while elements are
     * constantly removed from them via different ways.</p>
     * <p><b>Test Case Design</b>: Tests different ways to remove
     * elements from the backing map and the entryset. After each
     * removal checkEntry and checkIteration are invoked to
     * check map - entryset coherence and iterator's iteration
     * working correctly. Tests map -> entryset propagation and
     * iterator -> entryset -> map propagation.</p>
     * <p><b>Test Description</b>: m is filled with entries {0="0":1000="1000"}.
     * Through a for loop entries {i="i"}, i being 10, 20, 30,...
     * are removed from the map through map.remove method. That means that 100 entries
     * are removed from m and es. Then while the iterator's has next,
     * it removes 1 element through iterator.remove each 10 elements,
     * therefore it removes 90 entries, as the iterator starts iterating
     * the map and es have 900 elements.</p>
     * <p><b>Pre-Condition</b>: m and es are empty.</p>
     * <p><b>Post-Condition</b>: m and es contains 810 entries.</p>
     * <p><b>Expected Results</b>: Each removal from map and es propagates
     * the changes correctly to the other structure. checkEntrySet and checkIteration
     * are invoked to check coherence and asserts that iteration works correctly.</p>
     */
    @Test
    public void RemoveIteratorEsMap()
    {
        int bound = 1000;
        for (int i = 0; i < bound; i++)
        {
            m.put(i, "" + i);
            checkEntrySet(m, es);
            checkIteration(es);
        }
        int initSize = m.size();
        for (int i = 0; i < bound; i += 10)
        {
            m.remove(i);
            checkEntrySet(m, es);
            checkIteration(es);
        }
        assertTrue("100 should be removed", (initSize - m.size()) == 100 && m.size() == es.size());
        it = es.iterator();
        while (it.hasNext())
        {
            for (int i = 0; i < 10; i++)
            {
                it.next();
                checkEntrySet(m, es);
                checkIteration(es);
            }
            it.remove();
            checkEntrySet(m, es);
            checkIteration(es); 
        }
        assertTrue("190 should be removed", (initSize - m.size()) == 190 && m.size() == es.size());
        assertTrue("Should be size 810", m.size() == es.size() && m.size() == 810);
    }

    /**
     * <p><b>Summary</b>: Tests the correct propagation from multiple
     * entrysets of a map to the backing map and the propagation from
     * the single map to the multiple entrymaps.</p>
     * <p><b>Test Case Design</b>: Tests the case of multiple entrysets
     * propagating changes to ther other entrysets and to the map,
     * and the single map propagating changes to multiple entrysets,
     * inserting entries through initHMap and removing them, through
     * simple remove method. This is a more general case than the
     * single entryset.</p>
     * <p><b>Test Description</b>: esArr contains 100 the entryset
     * generated by the map. After map initialization, each entryset
     * is checked through checkEntrySet, checkIteration and checkToArray.
     * First is tested propagation from the entrySets to the map.
     * From each entryset one element is removed, then each entryset
     * is checked through aforementioned methods. At the end of
     * this operation, all the entrysets and the map have a size of 0.
     * Second is tested propagation from the map to the entrysets.
     * Each element is removed from the map and after each removal
     * each entryset is checked through aforementioned methods.
     * Again, at the end of
     * this operation, all the entrysets and the map have a size of 0.</p>
     * <p><b>Pre-Condition</b>: m and all the entrysets are empty.</p>
     * <p><b>Post-Condition</b>: m and all the entrysets are empty.</p>
     * <p><b>Expected Results</b>: Each modification to the map and entrysets
     * mantains coherence, checkEntrySet, checkIteration and checkToArray
     * tests pass.</p>
     */
    @Test
    public void MultipleEntrySets()
    {
        int bound = 100;
        HSet[] esArr = new HSet[bound];
        for (int i = 0; i < bound; i++)
            esArr[i] = m.entrySet();
        initHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
        {
            checkEntrySet(m, es);
            checkIteration(es);
            checkToArray(es, es.toArray());
        }

        // entrysets -> map
        for (int i = 0; i < bound; i++)
        {
            esArr[i].remove(getEntry(i, "" + i));
            for (int j = 0; j < bound; j++)
            {
                checkEntrySet(m, esArr[j]);
                checkIteration(esArr[j]);
                checkToArray(esArr[j], esArr[j].toArray());
            }
        }
        
        for (int i = 0; i < bound; i++)
            assertTrue(esArr[i].size() == 0 && esArr[i].isEmpty() && m.isEmpty());

        // map -> entrysets
        initHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
        {
            m.remove(i);
            for (int j = 0; j < bound; j++)
            {
                checkEntrySet(m, esArr[j]);
                checkIteration(esArr[j]);
                checkToArray(esArr[j], esArr[j].toArray());
            }
        }
        for (int i = 0; i < bound; i++)
            assertTrue(esArr[i].size() == 0 && esArr[i].isEmpty() && m.isEmpty());
    }

    /**
     * Checks if the entrySet and the backing map contains the same informations.
     * Firstly they must have the same size, then each entry in the entrySet
     * must be contained in the map and its value should mach m.get(e.getKey()),
     * which means that they share the same elements.
     * @param m the backing map
     * @param es the entrySet
     */
    public void checkEntrySet(HMap m, HSet es)
    {
        assertEquals("Map and EntrySet are NOT coherent. Propagation went wrong.", m.size(), es.size());
        HIterator it = es.iterator();
        while (it.hasNext())
        {
            HMap.Entry e = (HMap.Entry)it.next();
            assertTrue("Map and EntrySet are NOT coherent. Propagation went wrong.", m.containsKey(e.getKey()));
            assertTrue("Map and EntrySet are NOT coherent. Propagation went wrong.", m.containsValue(e.getValue()));
            assertEquals("Map and EntrySet are NOT coherent. Propagation went wrong.", e.getValue(), m.get(e.getKey()));
        }
    }

    /**
     * Checks if the entryset and the array contains the same elements
     * and their size equals.
     * @param es entryset to be checked
     * @param arr array to be checked
     */
    public void checkToArray(HSet es, Object[] arr)
    {
        assertEquals("The array and the set do NOT have the same elements.", es.size(), arr.length);
        for (int i = 0; i < arr.length; i++)
            assertTrue("The array and the set do NOT have the same elements.", es.contains(arr[i]));
    }

    /**
     * Checks if the elements returned by the iteration are
     * coherent with the entryset's elements, and if the number
     * of iteration equals the actual size of the entrySet.
     * @param es entryset to be checked
     */
    public void checkIteration(HSet es)
    {
        HIterator it = es.iterator();
        int count = 0;
        while (it.hasNext())
        {
            Object e = it.next();
            count++;
            assertTrue("EntrySet's iterator is NOT coherent.", es.contains(e));
        }

        assertFalse("EntrySet's iterator is NOT coherent.", it.hasNext());
        assertEquals("EntrySet's iterator is NOT coherent.", count, es.size());
        if (count == 0)
            assertTrue("EntrySet's iterator is NOT coherent.", es.isEmpty());
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