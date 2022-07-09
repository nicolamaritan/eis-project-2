package myTest;

// Imports
import static org.junit.Assert.*;
import static myTest.TestUtilities.*;
import org.junit.*;
import myAdapter.*;

/**
 * <p><b>Summary</b>: The test suite TestMap focuses on map methods, tests their correct
 * behaviour in different case scenario. Each MapAdapter method is tested in different
 * test cases. The first section of the test suite (Test cases assigned by the professor)
 * contains the tests in the TestMap.java
 * file assigned by the professor in the JUnit format, the second section of the test suite (Test cases ideated by me)
 * contains the test cases ideated by me on each element of HMap, excluding entrySet, keySet and values,
 * as they return a whole new HSet/HCollection to be tested apart from the methods of HMap.
 * At the end of this last section there are coarse-grained test cases, where multiple methods
 * of HMap are tested working together.
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
 * </ul>
 * 
 * <p><b>Test Suite Design</b>: The test suite contains fine-grained test cases in order to
 * easily individuate errors in HMap methods and also coarse-grained test cases in order to
 * test different methods interaction. That means that test cases include modification test, where
 * the map structure is modified in different ways, and inspection test, where information are retrieved
 * from the map to see if the informations are stored correctly, and tests where modifications and
 * inspections are combined. In the first section of the suite, propagation is tested
 * from the backing HMap to the KeySet/Values (and eventually their iterator) and viceversa, which is an important feature
 * of HMap. In the test suite there are many test cases focusing on limit and special cases,
 * invalid arguments and etc. Note that the in the map often contains entries where the key is an
 * integer i and value is the string representation of that integer "i". That is for testing
 * purpouse, to test the most general case possible.
 * Prints are kept from the original file, as it helped during the Test Driven Development
 * at looking for errors and fixing them.</p>
 */
public class TestMap
{
	int count = 0;
	HMap m = null;
	HMap m2 = null;
	HSet s1 = null;
	HSet ks = null;
	HIterator iter = null;
	HCollection c = null;

    int sm0, sm1, sm2, ss0, ss1, ss2;
	String[] argv = {"pippo", "pluto", "qui", "ciccio", "gambatek"};

	@BeforeClass
	public static void BeforeClassMethod()
	{
		System.out.println(TestMap.class.getName() + " running.");
	}

    /**
	 * Method invoke before each test for setup.
	 */
	@Before
	public void Setup()
	{
        sm0 = sm1 = sm2 = ss0 = ss1 = ss2 = 0;
		m = new MapAdapter();
		m2 = new MapAdapter();
	}

	@AfterClass
	public static void AfterClassMethod()
	{
		System.out.println(TestMap.class.getName() + " ended.");
	}

    /**
	 * Method invoke after each test for cleanup.
	 */
	@After
	public void Cleanup()
	{
		m = null;
        m2 = null;
        s1 = null;
        ks = null;
        iter = null;
        c = null;
        sm0 = sm1 = sm2 = ss0 = ss1 = ss2 = 0;
	}
	
    // -------------------- Test cases assigned by the professor --------------------

	/**
     * <p><b>Summary</b>: Tests the propagation of changes from the backing map
	 * to the KeySet, as adding or removing entries from the backing map
	 * affects the KeySet. Tests the methods keySet, size, remove
	 * and put of MapAdapter, size of the KeySet.
	 * </p>
     * <p><b>Test Case Design</b>: HMap interface states that a change in HMap
	 * affects the already generated KeySets and obviusly the future KeySets. Therefore
	 * this test focuses on the propagation of different changes through puts and removes
	 * from the HMap to the KeySet. The tests assert that propagation
     * happens correctly.</p>
     * <p><b>Test Description</b>: The map is first initialized with entries
	 * with key and value equals to the elements in argv. Then a KeySet is created
	 * from m. The entry of key pippo is removed from the map, and then reinserted.</p>
     * <p><b>Pre-Condition</b>: The map contains argv elements as keys and values,
     * therefore contains pippo=pippo, pluto=pluto, qui=qui, ciccio=ciccio,
     * gambatek=gambatek.</p>
     * <p><b>Post-Condition</b>:  The map contains argv elements as keys and values,
     * therefore contains pippo=pippo, pluto=pluto, qui=qui, ciccio=ciccio,
     * gambatek=gambatek.</p>
     * <p><b>Expected Results</b>: After initialization m contains 5 entries with keys
	 * the argv elements. KeySet contains argv elements and its size is 5. Next the entry pippo=pippo
	 * is removed from the map, therefore map and KeySet contains argv elements
	 * except pippo=pippo and their size is 4. Finally the pippo=pippo entry is reinserted
	 * through map, implying its presence in keySet. They both have size 5.
	 * Lastly asserts that m and ks have the same size after all removal/insertions and
	 * the difference in size from stage 0 to stage 1 is 1
     * (sm0 == ss0 && sm1 == ss1 && sm2 == ss2 && (sm0-sm1) == 1 must be true).
     * Hence the propagation from map to keySet works correctly.</p>
     */
	@Test
	public void TestPropagationFromMapToKeySet()
	{
		argvInitialize(m);
		
		System.out.println("Test propagation from map to keyset");
		ks = m.keySet();
		System.out.println(m + " " + m.size());
        System.out.println(ks + " " + ks.size());
		assertEquals("Size should be 5", 5, m.size());
        assertEquals("Size should be 5", 5, ks.size());
		sm0 = m.size();
		ss0 = ks.size();
		m.remove(argv[0]);
		sm1 = m.size();
		ss1 = ks.size();
		System.out.println("Entry removed: " + m + " " + m.size());
		for (int i = 1; i < argv.length; i++)
		assertEquals("Size should be 4", 4, m.size());
		System.out.println(ks + " " + ks.size());	
		m.put(argv[0], argv[0]);
		sm2 = m.size();
		ss2 = ks.size();

		System.out.println("Entry restored: " + m + " " + m.size());
		assertEquals("Size should be 5", 5, m.size());
		System.out.println(ks + " " + ks.size());
		assertEquals("Size should be 5", 5, ks.size());

		assertTrue("\n*** map NON propaga modifiche a keyset ***\n", sm0 == ss0 && sm1 == ss1 && sm2 == ss2 && (sm0-sm1) == 1);
	}

    /**
     * <p><b>Summary</b>: Tests the content of the map after initialization.</p>
     * <p><b>Test Case Design</b>: Asserting toString() to be equal to some
     * string is not reliable as
     * it is not possible predict HMap internal order/toString() output
     * (if not with iterators). Therefore, to
     * test its content, the test iterates through argv[i], asserting
     * the map to contains the right elements.
     * Original output is mantained to help checking consistency
     * during development.</p>
     * <p><b>Test Description</b>: Initialize the map with argv keys and
	 * values (recreates the previous test's situation, as the original
     * file TestMap.java was not formatted in the JUnit format),
     * asserts its size to be 5, then checks its content. For each element elem in argv,
     * it contains the key elem, it contains the value elem, and m.get(eleme)
     * equals elem.</p>
     * <p><b>Pre-Condition</b>: map contains argv keys and values,
     * therefore contains pippo=pippo, pluto=pluto, qui=qui, ciccio=ciccio,
     * gambatek=gambatek.</p>
     * <p><b>Post-Condition</b>: map contains argv keys and values,
     * therefore contains pippo=pippo, pluto=pluto, qui=qui, ciccio=ciccio,
     * gambatek=gambatek. map is unchanged</p>
     * <p><b>Expected Results</b>: map contains argv keys and values,
     * therefore contains pippo=pippo, pluto=pluto, qui=qui, ciccio=ciccio,
     * gambatek=gambatek, as the size is asserted to be 5 and
     * iterating through argv, each element is asserted to be stored
     * correctly. The map is unchanged</p>
     */
	@Test
	public void TestContent1()
	{
		argvInitialize(m);
		assertEquals("Size should be 5", m.size(), 5);
        System.out.println("Map.toString() ? " + m);
        for (int i = 0; i < argv.length; i++)
        {
            assertTrue(m.containsKey(argv[i]));
            assertTrue(m.containsValue(argv[i]));
            assertEquals(argv[i], m.get(argv[i]));
        }
	}

    /**
     * <p><b>Summary</b>: Tests KeySet and backing. HMap interface
	 * states that changes through the keySet affects the backing
	 * map. Tests method keySet, contains, get, size, put of MapAdapter,
	 * size, iterator, remove of KeySet, next and has Next of KeySet's
	 * iterator.</p>
     * <p><b>Test Case Design</b>: HMap interface states that a change in KeySet
	 * affects the backing HMap. Therefore
	 * this test focuses on the propagation of different changes
	 * from the KeySet to the backing HMap.</p>
     * <p><b>Test Description</b>: Initialize the map with argv keys and
	 * values (recreates the previous test's situation, as the original
     * file TestMap.java was not formatted in the JUnit format), then
	 * their presence is tested with contains method, its size is 5.
	 * Then the keySet s1 is created. KeySet iterator iterates through the set
	 * and asserts that the next key returned is equal to the element of the same
	 * key in the map (in fact pippo=pippo, qui=qui, ecc).
	 * Next the key pippo is removed from the KeySet (s1.remove(argv[0])), thus affecting the backing
	 * map (it removes pippo=pippo from it). KeySet iterator iterates through the set
	 * and asserts that the next key returned is equal to the element of the same
	 * key in the map (in fact pluto=pluto, qui=qui, ecc).
	 * Then the entry carrozza=carrozza is inserted through the map (m.put("carrozza", "carrozza")),
	 * thus affecting s1. Then the key carrozza is removed from s1 (s1.remove("carrozza")), thus
	 * affecting the backing Map.</p>
     * <p><b>Pre-Condition</b>: map contains argv keys and values.</p>
     * <p><b>Post-Condition</b>: pippo=pippo is removed.</p>
     * <p><b>Expected Results</b>: backing Map is correctly affected by changes
	 * in keySet.
     * After initialization map size is 5 and contains argv as key and m.get(argv[i])
     * equals argv[i]. The keyset's size is 5 and contains argv elements.
     * After pippo removal, map size and keyset size is 4. Then the
     * entry carrozza=carrozza is inserted, set is checked to be coherent, then carrozza
     * is removed.
     * At the end sm2 == m.size() || ss2 == s1.size() || s1.size() != m.size() is
	 * false, which means that map size at stage 2 is different from final map size AND
	 * keySet size at stage 2 is different from final keySet size AND keySet and map have the
	 * same final size.
	 * (sm0 == ss0 && sm1 == ss1 && sm2 == ss2 && (sm0-sm1) == 1) is true wich means the map
	 * and the keySet have the same size in each stage and the difference in size from stage 0 and
	 * 1 is 1.
	 * </p>
     */
	@Test
	public void TestKeySetAndBacking()
	{
		argvInitialize(m);
		System.out.println("Test keyset and backing");
		System.out.println(m + " " + m.size());

		s1 = m.keySet();
		sm0 = m.size();
		ss0 = s1.size();

		iter = s1.iterator();
		count = s1.size()+2;
		while(iter.hasNext()&&count-->=0)
		{
			Object k = iter.next();
			
			System.out.print("[" + k + "=" + m.get(k) + "]; ");
			assertTrue("Elements should match", k.equals(m.get(k)));
		}

		System.out.println("\n" + s1);	
		s1.remove(argv[0]);

		sm1 = m.size();
		ss1 = s1.size();

	    System.out.println(m + " " + m.size());
		assertEquals("Size should be 4", 4, m.size());

		iter = s1.iterator();
		count = s1.size()+2;
		while(iter.hasNext()&&count-->=0)
		{
			Object k = iter.next();

			System.out.print("[" + k + "=" + m.get(k) + "]; ");
			assertTrue("Elements should match", k.equals(m.get(k)));
		}

	    System.out.println("\n" + s1);
		System.out.println("Inserisco nella mappa e controllo il set");
		m.put("carrozza", "carrozza");
		System.out.println(m + " " + m.size());
		assertEquals("Size should be 5.", 5, m.size());

		iter = s1.iterator();
		count = s1.size()+2;
		while(iter.hasNext()&&count-->=0)
		{
			Object k = iter.next();
			
			System.out.print("[" + k + "=" + m.get(k) + "]; ");
			assertTrue("Elements should match", k.equals(m.get(k)));
		}
		
		System.out.println("\n" + s1);

		sm2 = m.size();
		ss2 = s1.size();

		System.out.println("Removed carrozza from keyset");
		assertTrue("Should be removed.", s1.remove("carrozza"));

		System.out.println("set size=" + s1.size() + "; map size=" + m.size());
		assertEquals("set size=4; map size=4", "set size=" + s1.size() + "; map size=" + m.size());
		assertFalse("\n*** map NON propaga modifiche a keyset ***\n", sm2 == m.size() || ss2 == s1.size() || s1.size() != m.size());
		assertTrue("\n*** map NON propaga modifiche a keyset ***\n", (sm0 == ss0 && sm1 == ss1 && sm2 == ss2 && (sm0-sm1) == 1));
	}

    /**
     * <p><b>Summary</b>: Tests emptying the map through keySet'iterator.
	 * KeySet's iterator removes obviusly affects the KeySet, which affects the
	 * map. Tests methods remove, size, keySet of map, hasNext, next, remove of
	 * KeySet iterator.</p>
     * <p><b>Test Case Design</b>: The test focuses on map changes through
	 * keySet's iterator. The changes on KeySet should affect the backing map.
     * In this case scenario propagation from the logical point of view
     * is: iterator -> keyset -> map. Therefore this kind of propagation
     * is tested in this test, by modifying the map from a keyset iterator.</p>
     * <p><b>Test Description</b>: Initialize the map with argv keys and
	 * values (recreates the previous test's situation, as the original
     * file TestMap.java was not formatted in the JUnit format).
	 * s1 the KeySet is created. pippo=pippo is removed from the map.
	 * Then iterates through the keySet asserting the returned element to be contained in the keyset
	 * and removing each element after each next, asserting them to be NOT
     * contained in the keyset. Therefore the Map is then empty.</p>
     * <p><b>Pre-Condition</b>: The map contains argv keys and values but pippo=pippo.</p>
     * <p><b>Post-Condition</b>: The map is empty.</p>
     * <p><b>Expected Results</b>: Contains assertion pass (the object is contained
     * before removal, the object is not contained after removal). The map is empty after
	 * removals. m.size() == s1.size() && m.size() == 0 is true, which means
	 * that the map and the keySet size both equals 0.</p>
     */
	@Test
	public void TestEmptyingByKeySetIterator()
	{
		argvInitialize(m);
		s1 = m.keySet();
		m.remove(argv[0]);

		System.out.println("Test emptying by keyset iterator");
		iter = s1.iterator();
		count = s1.size()+2;

		while(iter.hasNext() && count-- >= 0)
		{
			Object k = iter.next();
            assertTrue("Should be present", s1.contains(k));
			iter.remove();
            assertFalse("Should be just removed", s1.contains(k));
            System.out.println(k + " " + m.size() + "; ");
		}

		assertEquals("[]", s1.toString());
		System.out.println("\n" + s1);

		assertTrue("\n*** keyset iterator removal does not work ***\n", m.size() == s1.size() && m.size() == 0);
	}

    /**
     * <p><b>Summary</b>: Tests clearing the map through clear,
	 * reinserting the argv elements and the propagation from
	 * values to map. Tests method clear, put, size, values of map
	 * iterator, remove of values, hasNext, next of values' iterator.</p>
     * <p><b>Test Case Design</b>: After clearing the map with clear,
	 * tests changes propagation from values to map. Infact, HMap interface
	 * states that changes in values set propagate to the backing map.</p>
     * <p><b>Test Description</b>: Initialize the map with argv keys and
	 * values (recreates the previous test's situation, as the original
     * file TestMap.java was not formatted in the JUnit format).
     * clear method is invoked on the map.
	 * Then argv keys and values are inserted in the map. pippo=pippo is
	 * inserted twice, but map only accept unique keys.
	 * values is created. m and argv are asserted to have the
     * same size. Iteration prints c's elements separated by
     * a semicolon. Then pippo value is removed, therefore
     * the map now has a size of 4. Iteration prints c's elements separated by
     * a semicolon.</p>
     * <p><b>Pre-Condition</b>: map contains argv values and keys,
     * therefore contains pippo=pippo, pluto=pluto, qui=qui, ciccio=ciccio,
     * gambatek=gambatek.</p>
     * <p><b>Post-Condition</b>: map contains argv values and keys but pippo=pippo,
     * therefore contains pluto=pluto, qui=qui, ciccio=ciccio,
     * gambatek=gambatek. </p>
     * <p><b>Expected Results</b>: After clear the map is empty (toString returns {} when
     * the map is empty).
	 * After inserting argv keys and values and pippo=pippo m.size is equal to argv.size,
	 * as the second pippo=pippo insertion is not valid (map only accepts unique keys). m is checked to
	 * contain all argv keys and values and its size is 5.
     * pippo is removed from values,
	 * (sm0 == ss0 && sm1 == ss1 && sm2 == ss2 && (sm0-sm1) == 1) is true, which means
	 * the sizes of map and values equals in each stage, and the difference in size
	 * from stage 0 and 1 is 1.</p>
     */
	@Test
	public void ResetMapContentAndTestValues()
	{
		System.out.println("reset map content and test values");
		m.clear();
		
		System.out.println("Before " + m + " " + m.size());
		assertEquals("Before{} 0", "Before" + m + " " + m.size());

		m.put(argv[0], argv[0]);
		for(int i=0;i<argv.length;i++)
		{
			m.put(argv[i], argv[i]);
		}
		
		assertFalse("*** map.put malfunction ***", m.size() != argv.length);

		System.out.println("after " + m + " " + m.size());
		assertEquals("Size should be 5", 5, m.size());
		c = m.values();

		sm0 = m.size();
		ss0 = c.size();

		iter = c.iterator();
		count = c.size() +2;
		while(iter.hasNext() && count-- >= 0)
            System.out.print(iter.next() + "; ");
		System.out.println("\n" + c);

		c.remove(argv[0]);

		sm1 = m.size();
		ss1 = c.size();

		System.out.println(m + " " + m.size());
		assertEquals("Size should be 4", 4, m.size());

		iter = c.iterator();
		count = c.size()+2;
		while(iter.hasNext()&&count-->= 0)
            System.out.println("\n" + c);

		System.out.println("\n" + c);

		sm2 = m.size();
		ss2 = c.size();

		assertTrue("*** values NON propaga modifiche a map ***", (sm0 == ss0 && sm1 == ss1 && sm2 == ss2 && (sm0-sm1) == 1));
	}

    /**
     * <p><b>Summary</b>: Tests emptying the map through value's iterator.
	 * Value's iterator removes obviusly affects the Value, which affects the
	 * map. Tests method remove, size, keySet of map, hasNext, next, remove of
	 * KeySet iterator.</p>
     * <p><b>Test Case Design</b>: The tests focuses on map changes through
	 * value's iterator. The changes on KeySet should affect the backing map.
     * In this case scenario propagation from the logical point of view
     * is: iterator -> value -> map. Therefore this kind of propagation
     * is tested in this test, by modifying the map from a value iterator.</p>
     * <p><b>Test Description</b>: m is initialized with argv keys and values.
	 * c the value is created. pippo=pippo is removed from the map.
	 * Then iterates through the values
	 * and removing each element after each next. Therefore the Map is then empty.</p>
     * <p><b>Pre-Condition</b>: The map contains argv keys and values but pippo=pippo.</p>
     * <p><b>Post-Condition</b>: The map is empty.</p>
     * <p><b>Expected Results</b>:  Contains assertion pass (the object is contained
     * before removal, the object is not contained after removal)
     * The map is empty after
	 * removals. m.size() == s1.size() && m.size() == 0 is true, which means
	 * that the map and the keySet size both equals 0.</p>
     */
	@Test
	public void EmptyingByValuesIteratorTest()
	{
		argvInitialize(m);
		m.remove(argv[0]);
		c = m.values();

		System.out.println("\nemptying by values iterator");
		iter = c.iterator();
		count = c.size()+2;

		while(iter.hasNext()&&count-->=0)
		{
            Object next = iter.next();
            assertTrue("Should be present", c.contains(next));
			System.out.print(next + "; ");
			iter.remove();
            assertFalse("Should be just removed", c.contains(next));
		}

		System.out.println("\nmap " + m.size() + "; collection " + c.size());
		assertEquals("map 0; collection 0", "map " + m.size() + "; collection " + c.size());
		assertTrue("\n*** values iterator removal NON propaga modifiche a map ***\n", m.size() == c.size() && m.size() == 0);	
	}

    /**
     * Initiate the map with the elements in argv as key
     * and value, in order to recreate the test in a
     * no-JUnit format assigned by the professor in TestMap.java. 
     * @param m
     */
	private void argvInitialize(HMap m)
	{
		for(int i=0;i<argv.length;i++)
		{
			m.put(argv[i], argv[i]);
		}
	}

	// ------------------------------------------ Test cases ideated by me ------------------------------------------

	// ------------------------------------------ clear, isEmpty, size methods ------------------------------------------

    /**
     * <p><b>Summary</b>: clear, isEmpty and size method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case of a
     * clear invoke on a empty map.</p>
     * <p><b>Test Description</b>: map is empty. clear method
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: map is empty.</p>
     * <p><b>Post-Condition</b>: map is still empty.</p>
     * <p><b>Expected Results</b>: isEmpty returns true and size is 0.</p>
     */
    @Test
    public void Clear_OnEmpty()
    {
        m.clear();
        assertTrue("Should be empty.", m.isEmpty());
        assertEquals("Size should be 0", 0, m.size());
    }

    /**
     * <p><b>Summary</b>: clear, isEmpty and size method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case of a
     * clear invoke on a map with only one element.</p>
     * <p><b>Test Description</b>: map is empty. clear method
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: map contains 1=1.</p>
     * <p><b>Post-Condition</b>: map is empty.</p>
     * <p><b>Expected Results</b>: isEmpty returns true and size is 0.</p>
     */
    @Test
    public void Clear_OneElement()
    {
        m.put(1, 1);
        assertEquals(1, m.size());
        m.clear();
        assertTrue("Should be empty.", m.isEmpty());
        assertEquals("Size should be 0", 0, m.size());
    }

    /**
     * <p><b>Summary</b>: clear, isEmpty and size method test case.</p>
     * <p><b>Test Case Design</b>: Tests the case of a
     * clear invoke on a map containing 10 entries.</p>
     * <p><b>Test Description</b>: map contains 10 entries. clear method
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: map contains {0="0":10="10"}.</p>
     * <p><b>Post-Condition</b>: map is empty.</p>
     * <p><b>Expected Results</b>: isEmpty returns true and size is 0.</p>
     */
    @Test
    public void Clear_On1To10()
    {
        initHMap(m, 0, 10);
        m.clear();
        assertTrue("Should be empty.", m.isEmpty());
        assertEquals("Size should be 0", 0, m.size());
    }

    /**
     * <p><b>Summary</b>: clear, isEmpty and size method test case.</p>
     * <p><b>Test Case Design</b>: Tests the case of a
     * clear invoke on a map containing 1000 entries, which is a
     * case with a big number of elements.</p>
     * <p><b>Test Description</b>: map contains 1000 entries. clear method
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: map contains {0="0":1000="1000"}.</p>
     * <p><b>Post-Condition</b>: map is empty.</p>
     * <p><b>Expected Results</b>: isEmpty returns true and size is 0.</p>
     */
    @Test
    public void Clear_On1To1000()
    {
        initHMap(m, 0, 1000);
        m.clear();
        assertTrue("Should be empty.", m.isEmpty());
        assertEquals("Size should be 0", 0, m.size());
    }

    /**
     * <p><b>Summary</b>: clear, isEmpty and size method test case.</p>
     * <p><b>Test Case Design</b>: Tests the size after each insertion
     * 1000 times. Big HMap size is tested.</p>
     * <p><b>Test Description</b>: An entry is inserted onece per
     * iteration for 1000 times, size is asserted to be i.
     * After the for loop clear is invoked and the map must be
     * empty.</p>
     * <p><b>Pre-Condition</b>: map is empty.</p>
     * <p><b>Post-Condition</b>: map is empty.</p>
     * <p><b>Expected Results</b>: m size is i for i in (0:1000), where
     * at each iteration an entry is inserted. m is empty after clear.</p>
     */
    @Test
    public void ClearSize_From0To1000()
    {
        int bound = 10000;
        for (int i = 0; i < bound; i++)
        {
            m.put(i, i + "");
            assertEquals("Size should " + i + 1, i + 1, m.size());
        }
        m.clear();
        assertEquals("Should be cleared", 0, m.size());
        assertTrue("Should be empty.", m.isEmpty());
    }

    // ------------------------------------------ containsKey method ------------------------------------------

    /**
     * <p><b>Summary</b>: containsKey method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case of
     * invoking the method in an empty map, which should always
     * return false.</p>
     * <p><b>Test Description</b>: A,B,C,D are tested to be in
     * the map as keys.</p>
     * <p><b>Pre-Condition</b>: map is Empty.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: Each containsKey returns false.</p>
     */
    @Test
    public void ContainsKey_4LettersEmpty()
    {
        assertFalse("Should not contain.", m.containsKey("A"));
        assertFalse("Should not contain.", m.containsKey("B"));
        assertFalse("Should not contain.", m.containsKey("C"));
        assertFalse("Should not contain.", m.containsKey("D"));
    }

    /**
     * <p><b>Summary</b>: containsKey method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case of
     * invoking the method in map.</p>
     * <p><b>Test Description</b>: A,B,C,D are tested to be in
     * the map as keys.</p>
     * <p><b>Pre-Condition</b>: map contains the first 4 letters.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: Each containsKey returns true.</p>
     */
    @Test
    public void ContainsKey_4Letters0()
    {
        m.put("A", "a");
        m.put("B", "b");
        m.put("C", "c");
        m.put("D", "d");

        assertTrue("Should contain.", m.containsKey("A"));
        assertTrue("Should contain.", m.containsKey("B"));
        assertTrue("Should contain.", m.containsKey("C"));
        assertTrue("Should contain.", m.containsKey("D"));
    }

    /**
     * <p><b>Summary</b>: containsKey method test case.</p>
     * <p><b>Test Case Design</b>: Tests the case of
     * invoking the method in map with keys in and out.</p>
     * <p><b>Test Description</b>: A,B,C,D are tested to be in
     * the map as keys.</p>
     * <p><b>Pre-Condition</b>: map contains A and C as keys.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: A and C are contained, the others no.</p>
     */
    @Test
    public void ContainsKey_4Letters1()
    {
        m.put("A", "a");
        m.put("C", "c");

        assertTrue("Should contain.", m.containsKey("A"));
        assertFalse("Should not contain.", m.containsKey("B"));
        assertTrue("Should contain.", m.containsKey("C"));
        assertFalse("Should not contain.", m.containsKey("D"));
    }

    /**
     * <p><b>Summary</b>: containsKey method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case of
     * invoking the method in map with a big size.</p>
     * <p><b>Test Description</b>: {0=0, 1000=1000} are 
     * checked to be in the map or not.</p>
     * <p><b>Pre-Condition</b>: map contains {0=0, 100=100}.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: {0 : 100} keys are contained,
     * the others no.</p>
     */
    @Test
    public void ContainsKey_0To1000()
    {
        int bound = 100;
        int bound2 = 1000;
        initHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
            assertTrue("Should contain.", m.containsKey(i));
        for (int i = bound; i < bound2; i++)
            assertFalse("Should not contain.", m.containsKey(i));
        
    }

    // ------------------------------------------ containsValue method ------------------------------------------

    /**
     * <p><b>Summary</b>: containsValue method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case of
     * invoking the method in an empty map, which should always
     * return false.</p>
     * <p><b>Test Description</b>: a, b, c, d are tested to be in
     * the map as values.</p>
     * <p><b>Pre-Condition</b>: map is empty.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: Each containsKey returns false.</p>
     */
    @Test
    public void ContainsValue_4LettersEmpty()
    {
        assertFalse("Should not contain.", m.containsValue("a"));
        assertFalse("Should not contain.", m.containsValue("b"));
        assertFalse("Should not contain.", m.containsValue("c"));
        assertFalse("Should not contain.", m.containsValue("d"));
    }

    /**
     * <p><b>Summary</b>: containsKey method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case of
     * invoking the method in map.</p>
     * <p><b>Test Description</b>: a, b, c, d are tested to be in
     * the map as values.</p>
     * <p><b>Pre-Condition</b>: map contains the first 4 letters.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: Each containsKey returns true.</p>
     */
    @Test
    public void ContainsValue_4Letters0()
    {
        m.put("A", "a");
        m.put("B", "b");
        m.put("C", "c");
        m.put("D", "d");

        assertTrue("Should contain.", m.containsValue("a"));
        assertTrue("Should contain.", m.containsValue("b"));
        assertTrue("Should contain.", m.containsValue("c"));
        assertTrue("Should contain.", m.containsValue("d"));
    }

    /**
     * <p><b>Summary</b>: containsKey method test case.</p>
     * <p><b>Test Case Design</b>: Tests the case of
     * invoking the method in map with keys in and out.</p>
     * <p><b>Test Description</b>: a,b,c,d are tested to be in
     * the map as keys.</p>
     * <p><b>Pre-Condition</b>: map contains A and C as keys.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: a and c are contained, the others no.</p>
     */
    @Test
    public void ContainsValue_4Letters1()
    {
        m.put("A", "a");
        m.put("C", "c");

        assertTrue("Should contain.", m.containsValue("a"));
        assertFalse("Should not contain.", m.containsValue("b"));
        assertTrue("Should contain.", m.containsValue("c"));
        assertFalse("Should not contain.", m.containsValue("d"));
    }

    /**
     * <p><b>Summary</b>: containsValue method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case of
     * invoking the method in map with a big size.</p>
     * <p><b>Test Description</b>: {0=0, 1000=1000} are 
     * checked to be in the map or not.</p>
     * <p><b>Pre-Condition</b>: map contains {0=0, 100=100}.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: {0 : 100} values are contained,
     * the others no.</p>
     */
    @Test
    public void ContainsValue_0To1000()
    {
        int bound = 100;
        int bound2 = 1000;
        initHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
            assertTrue("Should contain.", m.containsValue(""+i));
        for (int i = bound; i < bound2; i++)
            assertFalse("Should not contain.", m.containsValue(i));
        
    }

    /**
     * <p><b>Summary</b>: containsValue method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case of
     * invoking the method in map with a big size. Values contained
     * are duplicated, therefore the method must still work correctly.</p>
     * <p><b>Test Description</b>: {0=0, 500=500} are 
     * checked to be in the map or not.</p>
     * <p><b>Pre-Condition</b>: map contains {0=0, 100=100}.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: {0 : 100} values are contained,
     * the others no.</p>
     */
    @Test
    public void ContainsValue_0To100Duplicates()
    {
        int bound = 100;
        int bound2 = 500;
        TestUtilities.initHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
            m.put(i + bound, i);

        for (int i = 0; i < bound; i++)
            assertTrue("Should contain.", m.containsValue(i));
        for (int i = bound; i < bound2; i++)
            assertFalse("Should not contain.", m.containsValue(i));
        
    }

	// ------------------------------------------ get method ------------------------------------------

    /**
     * <p><b>Summary</b>: get method test case.</p>
     * <p><b>Test Case Design</b>: Tests get behaviour when the
     * map is empty, which is a limit case.</p>
     * <p><b>Test Description</b>: get is invoked on an empty map
     * with a random key.</p>
     * <p><b>Pre-Condition</b>: m is empty</p>
     * <p><b>Post-Condition</b>: m is unchanged</p>
     * <p><b>Expected Results</b>: null is returned</p>
     */
    @Test
    public void Get_Empty()
    {
        assertNull("Should be null", m.get("Random Key"));
    }

    /**
     * <p><b>Summary</b>: get method test case.</p>
     * <p><b>Test Case Design</b>: Tries to get a value
     * of a key not in the map, where the map is not empty.</p>
     * <p><b>Test Description</b>: Gets a not in map key.</p>
     * <p><b>Pre-Condition</b>: Map contains {0=0:10=10}.</p>
     * <p><b>Post-Condition</b>: Map is unchanged.</p>
     * <p><b>Expected Results</b>: m.get returns null.</p>
     */
    @Test
    public void Get_OneElementNotInMap()
    {
        TestUtilities.initHMap(m, 0, 10);
        assertNull("Should be null", m.get(11));
    }
    
    /**
     * <p><b>Summary</b>: get method test case.</p>
     * <p><b>Test Case Design</b>: Tests get behaviour when the
     * map contains a big number of element.</p>
     * <p><b>Test Description</b>: get is invoked with keys
     * from 100 to 200.</p>
     * <p><b>Pre-Condition</b>: m contains {0=0:100=100}</p>
     * <p><b>Post-Condition</b>: m is unchanged</p>
     * <p><b>Expected Results</b>: each get returns null.</p>
     */
    @Test
    public void Get_0To100NotInMap()
    {
        int bound = 100;
        TestUtilities.initHMap(m, 0, bound);
        for (int i = bound; i < 2* bound; i++)
            assertNull("Should be null", m.get(i));
    }

    /**
     * <p><b>Summary</b>: get method test case.</p>
     * <p><b>Test Case Design</b>: Tests get behaviour when the
     * map contains only one element, which is a limit case.</p>
     * <p><b>Test Description</b>: get is invoked with "1" key.</p>
     * <p><b>Pre-Condition</b>: m contains {1=1}</p>
     * <p><b>Post-Condition</b>: m is unchanged</p>
     * <p><b>Expected Results</b>: "1" is returned</p>
     */
    @Test
    public void Get_OneElement()
    {
        m.put("1", 1);
        assertEquals(1, m.get("1"));
    }

    /**
     * <p><b>Summary</b>: get method test case.</p>
     * <p><b>Test Case Design</b>: Tests get behaviour when the
     * map contains a big number of element.</p>
     * <p><b>Test Description</b>: get is invoked with keys
     * from 0 to 100.</p>
     * <p><b>Pre-Condition</b>: m contains {0=0:100=100}</p>
     * <p><b>Post-Condition</b>: m is unchanged</p>
     * <p><b>Expected Results</b>: each get returns i in string form.</p>
     */
    @Test
    public void Get_0To100()
    {
        int bound = 100;
        TestUtilities.initHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
            assertEquals("Should return " + i, "" + i, m.get(i));
    }

    /**
     * <p><b>Summary</b>: get method test case.</p>
     * <p><b>Test Case Design</b>: Tests get behaviour when the
     * map is empty and m.get is invoked with null argument.</p>
     * <p><b>Test Description</b>: get with null key as an argument,
     * therefore NPE is thrown.</p>
     * <p><b>Pre-Condition</b>: m is empty</p>
     * <p><b>Post-Condition</b>: m is unchanged</p>
     * <p><b>Expected Results</b>: NullPointerException is thrown.</p>
     */
    @Test (expected = NullPointerException.class)
    public void Get_EmptyNull()
    {
        m.get(null);
    }

    /**
     * <p><b>Summary</b>: get method test case.</p>
     * <p><b>Test Case Design</b>: Tests get behaviour when the
     * map contains 1 entry and m.get is invoked with null argument.</p>
     * <p><b>Test Description</b>: get with null key as an argument,
     * therefore NPE is thrown.</p>
     * <p><b>Pre-Condition</b>: m contains 1=1</p>
     * <p><b>Post-Condition</b>: m is unchanged</p>
     * <p><b>Expected Results</b>: NullPointerException is thrown.</p>
     */
    @Test (expected = NullPointerException.class)
    public void Get_OneElementNull()
    {
        m.put(1, 1);
        m.get(null);
    }

    /**
     * <p><b>Summary</b>: get method test case.</p>
     * <p><b>Test Case Design</b>: Tests get behaviour when the
     * map contains {0="0":100="100"} and m.get is invoked with null argument.</p>
     * <p><b>Test Description</b>: get with null key as an argument,
     * therefore NPE is thrown.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":100="100"}</p>
     * <p><b>Post-Condition</b>: m is unchanged</p>
     * <p><b>Expected Results</b>: NullPointerException is thrown.</p>
     */
    @Test (expected = NullPointerException.class)
    public void Get_0To100Null()
    {
        initHMap(m, 0, 100);
        m.get(null);
    }

	// ------------------------------------------ hashCode method ------------------------------------------

    /**
     * <p><b>Summary</b>: hashCode test case.
     * Tests the behaviour of hashCode method with different
     * map configurations.</p>
     * <p><b>Test Case Design</b>: The same operations are applied to map 1 and 2,
     * so they must have the same elements each time, therefore they are equals.
     * If they are equals they must have the same hashCode.</p>
     * <p><b>Test Description</b>: Different configurations have been tested. In mathematical symbols, considering the keys:
     * empty, {1}, {-100:100}, {-100:100}/{0}, {-100:101}/{0}, {-100:100, 500:1000}/{0},
     * {-100:100, 500:1000} U {-1000:-900}/({0} ), {-100:100, 500:1000, 5000:6000}  U {-1000:-900}/({0})</p>
     * <p><b>Pre-Condition</b>: maps have same hashCode and they are equal.</p>
     * <p><b>Post-Condition</b>: maps have same hashCode and they are equal.</p>
     * <p><b>Expected Results</b>: maps have same hashCode and they are equal.</p>
     */
    @Test
    public void HashCode_Mixed()
    {
        // Empty map case
        assertTrue("maps should be equal.", m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        // One element case
        m.put(1, "1");
        m2.put(1, "1");
        assertTrue("maps should be equal.", m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        TestUtilities.initHMap(m, -100, 100);
        TestUtilities.initHMap(m2, -100, 100);
        assertTrue("maps should be equal.", m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        m.remove((Object)0);
        m2.remove((Object)0);
        assertTrue("maps should be equal.", m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        m.put(101, "101");
        m2.put(101, "101");
        assertTrue("maps should be equal.", m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        TestUtilities.addToHMap(m, 500, 1000);
        TestUtilities.addToHMap(m2, 500, 1000);
        assertTrue("maps should be equal.", m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        HMap t = TestUtilities.getIntegerMapAdapter(-1000, -900);
        m.putAll(t);
        m2.putAll(t);
        assertTrue("maps should be equal.", m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        TestUtilities.initHMap(t, 5000, 6000);
        m.putAll(t);
        m2.putAll(t);
        assertTrue("maps should be equal.", m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

    }

	// ------------------------------------------ put method ------------------------------------------
	
	/**
     * <p><b>Summary</b>: put method test case.
	 * Tests one put invoke.</p>
     * <p><b>Test Case Design</b>: Tests put invoke
	 * with random key and value.</p>
     * <p><b>Test Description</b>: put is invoked with key
	 * 5489 and value "Random value"</p>
     * <p><b>Pre-Condition</b>: m is empty.</p>
     * <p><b>Post-Condition</b>: m contains 5489="Random value".</p>
     * <p><b>Expected Results</b>: m.get(5489)="Random value" and its
	 * size is 1, therefore puts works correctly.</p>
     */
	@Test
	public void Put_OneElement()
	{
		m.put(5489, "Random value");
		assertEquals("Size should be 1", 1, m.size());
		assertEquals("Random value", m.get(5489));
	}

	/**
     * <p><b>Summary</b>: put method test case.
	 * Tests 1000 put invoke.</p>
     * <p><b>Test Case Design</b>: Tests put with 1000
	 * elements, testing big input for the map.</p>
     * <p><b>Test Description</b>: put is invoked with
	 * key i and the string "i" for each iteration.</p>
     * <p><b>Pre-Condition</b>: m is empty.</p>
     * <p><b>Post-Condition</b>: m contains {0="0" : 100="100"}</p>
     * <p><b>Expected Results</b>: m.get(i)="i" for each i in (0:100) and its
	 * size is 100, therefore puts works correctly.</p>
     */
	@Test
	public void Put_0To100()
	{
		int bound = 100;
		for (int i = 0; i < bound; i++)
			m.put(i, "" + i);
		assertEquals("Size should be " + bound, bound, m.size());
		for (int i = 0; i < bound; i++)
			assertEquals("Should return " + i, "" + i, m.get(i));
	}

	/**
     * <p><b>Summary</b>: put method test case.
	 * Tests 1000 put invoke, but this time entries with
     * the same key are inserted twice. The HMap interface
     * states that it is NOT possible to have two elements
     * with the same key in the map, therefore inserting a new
     * entry with the same key replaces the old mapping with the new.</p>
     * <p><b>Test Case Design</b>: Tests put with 1000
	 * elements, testing big input for the map. Tests the special
     * case of an entry with an already present key in the map
     * to be put.</p>
     * <p><b>Test Description</b>: put is invoked with
	 * key i and the string "i" for each iteration.
     * Then put is invoked with
	 * key i and the string "i + 1000" for each iteration, thus
     * replacing the old entries.</p>
     * <p><b>Pre-Condition</b>: m is empty.</p>
     * <p><b>Post-Condition</b>: m contains {0="100" : 100="200"}</p>
     * <p><b>Expected Results</b>: m.get(i)="i + 100" for each i in (0:100) and its
	 * size is 100, therefore puts works correctly, even replacing entries.</p>
     */
	@Test
	public void Put_0To100DuplicatesKey()
	{
		int bound = 100;
        // For loop twice
		for (int i = 0; i < bound; i++)
			assertNull("There should not be previous mapping.", m.put(i, "" + i));
        for (int i = 0; i < bound; i++)
			assertEquals("Previous mapping should be " + i, "" + i, m.put(i, "" + (i + bound)));

		assertEquals("Size should be " + bound, bound, m.size());
		for (int i = 0; i < bound; i++)
			assertEquals("Returned value should be " + (i + bound), "" + (i + bound), m.get(i));
	}
    
    /**
     * <p><b>Summary</b>: put method test case. Tests the put method
     * behaviour trying to insert entries with duplicated keys
     * many times.</p>
     * <p><b>Test Case Design</b>: Trying to insert an entry
     * with a key already in the map does NOT modify the size of
     * the map, altough it modify the associated mapping to that
     * kay. Therefore after inserting 100 times 100 entries
     * with the same key should not change the map size.</p>
     * <p><b>Test Description</b>: For 100 times, insert the entry
     * (i, "i") 100 times.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":100="100"}.</p>
     * <p><b>Post-Condition</b>: m contains {0="0":100="100"}.</p>
     * <p><b>Expected Results</b>: size did not change after the
     * nested for loop.</p>
     */

    @Test
    public void Put_0To100AllDuplicatesKey()
    {
        int bound = 100;
        for (int i = 0; i < bound; i++)
            m.put(i, "" + i);
        int initSize = m.size();
        for (int i = 0; i < bound; i++)
        {
            for (int j = 0; j < bound; j++)
                m.put(i, "" + 1);
        }
        assertTrue("Sizes should be equal", m.size() - initSize == 0 && m.size() == bound);
    }

    /**
     * <p><b>Summary</b>: put method test case.
     * Tests if the object returned from the put
     * method is correct.</p>
     * <p><b>Test Case Design</b>: Tests a feature of the put method
     * for HMap, which is that when inserting an entry with a key
     * already in the map, should return the old value and update
     * the new mapping.</p>
     * <p><b>Test Description</b>: m.put(1, "3") is invoked to
     * insert 1="3". Then the entry 1="1" is inserted,
     * updating the old one.</p>
     * <p><b>Pre-Condition</b>: m is empty.</p>
     * <p><b>Post-Condition</b>: m contains 1="1"</p>
     * <p><b>Expected Results</b>: m.put(1, "3") returns null as there
     * was no previous mapping.  m.put(1, "1") returns "3" as
     * the key was previously mapped to the value "3". The map
     * finally contains 1="1", in particular  m.containsKey(1) && m.containsValue("1") && !m.containsValue("3")
     * e m.get(1) == "1" return true.</p>
     */
    @Test
    public void Put_Substitute0()
    {
        assertNull("No previous mapping, should return null", m.put(1, "3"));
        assertEquals("Should return old value", "3", m.put(1, "1"));
        assertTrue(m.size() == 1);
        assertTrue("Should have put new entry", m.containsKey(1) && m.containsValue("1") && !m.containsValue("3"));
        assertTrue("New entry should match", m.get(1) == "1");
    }

    /**
     * <p><b>Summary</b>: put method test case.
     * Tests if the object returned from the put
     * method is correct.</p>
     * <p><b>Test Case Design</b>: Tests a feature of the put method
     * for HMap, which is that when inserting an entry with a key
     * already in the map, should return the old value and update
     * the new mapping.</p>
     * <p><b>Test Description</b>: the HMap is initiated
     * through a for loop, where at each insertion with put
     * the return value is asserted to null, as m was empty
     * and thus there were no mappings.
     * Then each entry is updated inserting, through put,
     * a new entry with the same key but value shifted
     * of bound. Each one of this put should return the old value.
     * Finally each entry is checked to be correct through
     * m.contains(i), m.containsValue(""+(bound+i)) and m.get(i)</p>
     * <p><b>Pre-Condition</b>: m is empty.</p>
     * <p><b>Post-Condition</b>: m contains {0="100":100="200"}</p>
     * <p><b>Expected Results</b>: m.puts in the first loop
     * return all null. m.puts in the second loop return all "i".
     * m contains {0="100":100="200"}.</p>
     */
    @Test
    public void Put_Substitute1()
    {
        int bound = 100;
        for (int i = 0; i < bound; i++)
            assertNull("No previous mapping, should return null", m.put(i, "" + i));
        for (int i = 0; i < bound; i++)
            assertEquals("m.put should return the old value", "" + i, m.put(i, "" + (bound + i)));
        for (int i = 0; i < bound; i++)
        {
            assertTrue("Should be contained", m.containsKey(i));
            assertTrue("Should be contained", m.containsValue("" + (i + bound)));
            assertEquals("Value not substitued correctly.", "" + (i + bound), m.get(i));
            
        }
    }

    /**
     * <p><b>Summary</b>: put method test case.</p>
     * <p><b>Test Case Design</b>: Tests the case of
     * an invalid argument, as the MapAdapter does not
     * accept null keys/values in put.</p>
     * <p><b>Test Description</b>: m.put(null, "Random value");
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: m is empty</p>
     * <p><b>Post-Condition</b>: m is still empty, exception
     * has been thrown.</p>
     * <p><b>Expected Results</b>: NPE has been thrown.</p>
     */
    @Test (expected = NullPointerException.class)
    public void Put_NullKey()
    {
        m.put(null, "Random value");
    }

    /**
     * <p><b>Summary</b>: put method test case.</p>
     * <p><b>Test Case Design</b>: Tests the case of
     * an invalid argument, as the MapAdapter does not
     * accept null keys/values in put.</p>
     * <p><b>Test Description</b>: m.put("Random key", null);
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: m is empty</p>
     * <p><b>Post-Condition</b>: m is still empty, exception
     * has been thrown.</p>
     * <p><b>Expected Results</b>: NPE has been thrown.</p>
     */
    @Test (expected = NullPointerException.class)
    public void Put_NullValue()
    {
        m.put("Random key", null);
    }

    /**
     * <p><b>Summary</b>: put method test case.</p>
     * <p><b>Test Case Design</b>: Tests the case of
     * an invalid argument, as the MapAdapter does not
     * accept null keys/values in put.</p>
     * <p><b>Test Description</b>: m.put(null, null);
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: m is empty</p>
     * <p><b>Post-Condition</b>: m is still empty, exception
     * has been thrown.</p>
     * <p><b>Expected Results</b>: NPE has been thrown.</p>
     */
    @Test (expected = NullPointerException.class)
    public void Put_NullBoth()
    {
        m.put(null, null);
    }

    /**
     * <p><b>Summary</b>: put method test case.</p>
     * <p><b>Test Case Design</b>: Tests the case of
     * an invalid argument, as the MapAdapter does not
     * accept null keys/values in put.</p>
     * <p><b>Test Description</b>: m.put(null, "Random value");
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":10="10}</p>
     * <p><b>Post-Condition</b>: m contains {0="0":10="10}, exception
     * has been thrown.</p>
     * <p><b>Expected Results</b>: NPE has been thrown.</p>
     */
    @Test (expected = NullPointerException.class)
    public void Put_NullKeyNotEmpty()
    {
        initHMap(m, 0, 10);
        m.put(null, "Random value");
    }

    /**
     * <p><b>Summary</b>: put method test case.</p>
     * <p><b>Test Case Design</b>: Tests the case of
     * an invalid argument, as the MapAdapter does not
     * accept null keys/values in put.</p>
     * <p><b>Test Description</b>: m.put("Random key", null);
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":10="10}</p>
     * <p><b>Post-Condition</b>: m contains {0="0":10="10}, exception
     * has been thrown.</p>
     * <p><b>Expected Results</b>: NPE has been thrown.</p>
     */
    @Test (expected = NullPointerException.class)
    public void Put_NullValueNotEmpty()
    {
        initHMap(m, 0, 10);
        m.put("Random key", null);
    }

    /**
     * <p><b>Summary</b>: put method test case.</p>
     * <p><b>Test Case Design</b>: Tests the case of
     * an invalid argument, as the MapAdapter does not
     * accept null keys/values in put.</p>
     * <p><b>Test Description</b>: m.put(null, null);
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":10="10}</p>
     * <p><b>Post-Condition</b>: m contains {0="0":10="10}, exception
     * has been thrown.</p>
     * <p><b>Expected Results</b>: NPE has been thrown.</p>
     */
    @Test (expected = NullPointerException.class)
    public void Put_NullBothNotEmpty()
    {
        initHMap(m, 0, 10);
        m.put(null, null);
    }


	// ------------------------------------------ putAll method ------------------------------------------

    /**
     * <p><b>Summary</b>: putAll method test case.
     * While adding a null object is ok, calling putAll
     * method, which takes a HMap argument, throws an exception.</p>
     * <p><b>Test Case Design</b>: Tests the case with null
     * argument passed, which is a special case of invalid argument.</p>
     * <p><b>Test Description</b>: Calls putAll with null argument, therefore it
     * throws NullPointerException.</p>
     * <p><b>Pre-Condition</b>: Map is empty.</p>
     * <p><b>Post-Condition</b>: Map is empty.</p>
     * <p><b>Expected Results</b>: NullPointerException thrown.</p>
     */
    @Test(expected = java.lang.NullPointerException.class)
    public void PutAll_NullCollection_NPE()
    {
        m.putAll(null);
    }

    /**
     * <p><b>Summary</b>: putAll method test case.</p>
     * <p><b>Test Case Design</b>: Tests the case of empty collection being
     * passed as argument, which is a limit case.</p>
     * <p><b>Test Description</b>: The test cases calls putAll as empty
     * collection as argument. Adding an empty Map means that no element
     * is added, therefore putAll returns false as the Map is unchanged.</p>
     * <p><b>Pre-Condition</b>: Map is empty, coll is empty.</p>
     * <p><b>Post-Condition</b>: Map is empty, coll is empty.</p>
     * <p><b>Expected Results</b>: Map is still empty.</p>
     */
    @Test
    public void PutAll_EmptyCollection_False()
    {
		m.putAll(m2);
        assertTrue("The Map should be empty.", m.isEmpty());
		assertEquals(0, m.size());
    }

    /**
     * <p><b>Summary</b>: putAll method test case.
     * The test adds a map to the Map,
     * then checks if the elements were stored correctly.</p>
     * <p><b>Test Case Design</b>: putAll must behave correctly
     * adding a map of 3 elements, which is a common case for
     * the putAll method.</p>
     * <p><b>Test Description</b>: 3 elements are added and then the test
     * checks the contained element and its size.</p>
     * <p><b>Pre-Condition</b>: Map is empty, m2 has {1="1", 2="2", 3="3"}.</p>
     * <p><b>Post-Condition</b>: Map and m2 both contain {1="1", 2="2", 3="3"}. In particular,
     * m2 is unchanged.</p>
     * <p><b>Expected Results</b>: Map contains the added elements {1="1", 2="2", 3="3"},
     * as the putAll(m2) method inserted all m2 mapping to m, and its size is 3.</p>
     */
    @Test
    public void PutAll_FromEmptyTo123()
    {
        initHMap(m2, 1, 4);

        m.putAll(m2);
        assertEquals("Should return 1.", "1", m.get(1));
        assertEquals("Should return 2.", "2", m.get(2));
        assertEquals("Should return 3.", "3", m.get(3));
        assertEquals("Maps size should be 3.", 3, m.size());
    }

    /**
     * <p><b>Summary</b>: putAll method test case.
     * The Map initially contains {1="1", 2="2", 3="3"}, then through
     * remove and putAll it finally contains entries from 1="1"
     * to 5="5" included.</p>
     * <p><b>Test Case Design</b>: It tests how the Map behaves with
     * removals and then with adding a map.</p>
     * <p><b>Test Description</b>: The test removes the 3 number, then it
     * adds through putAll method the elements {3="3", 4="4", 5="5"}.</p>
     * <p><b>Pre-Condition</b>: Map contains {1="1", 2="2", 3="3"}}.</p>
     * <p><b>Post-Condition</b>: Map contains {1="1":6="6"}.</p>
     * <p><b>Expected Results</b>: Map contains the added elements {1="1":6="6"}
     * through m.putAll(m2).</p>
     */
    @Test
    public void PutAll_From123to12345()
    {
        initHMap(m, 1, 4);
        m.remove(3);
        initHMap(m2, 3, 6);
        m.putAll(m2);
        assertTrue("The Map should be equal.", m.equals(getIntegerMapAdapter(1, 6)));
    }

    /**
     * <p><b>Summary</b>: putAll method test case.
     * The method test the insertion of a large number
     * of elements through putAll.</p>
     * <p><b>Test Case Design</b>: The test considers the case
     * scenario of large input. From the Sommerville: "Force computation results to be
     * too large or too small."</p>
     * <p><b>Test Description</b>: Numbers from 1 to 100 are inserted in coll, then putAll(m2)
     * method is invoked with m2 as argument. m2 contains {0="0":100="100"}.</p>
     * <p><b>Pre-Condition</b>: The Map is empty, m2 contains aforementioed entries.</p>
     * <p><b>Post-Condition</b>: The Map contains {0="0":100="100"} as m2.</p>
     * <p><b>Expected Results</b>: Entries i="i" entries are stored correctly,
	 * with i from 0 to 100.
     * The Map size is 100.
     */
    @Test
    public void PutAll_0to100()
    {
        int bound = 100;
        initHMap(m2, 0, bound);
		m.putAll(m2);
        assertEquals("Size should be 100.",100, m.size());
		for (int i = 0; i < bound; i++)
			assertEquals("" + i, m.get(i));
    }

    /**
     * <p><b>Summary</b>: putAll method test case.
     * The method tests putAll behaviour when inserting
     * mapping of keys already in the map.</p>
     * <p><b>Test Case Design</b>: From the HMap interface:
     * "The effect of this call is equivalent to that of calling put(k, v)
     * on this map once for each mapping from key k to value v in the specified map."
     * That means that if a key is already present, the new mapping
     * overrides the old one. This feature is tested with 100
     * entries.</p>
     * <p><b>Test Description</b>: m is firstly initiated with the entries
     * {0="0":100="100"}, and its content is checked through assertTrue(m.containsKey(i));
     * assertTrue(m.containsValue("" + i));
     * assertEquals("" + i, m.get(i));.
     * The m2 is initialized with {0="100":100="200"}, with the
     * values shifted by 100 respect to keys. then m.putAll(m2)
     * is invoked and m content is checked through assertTrue(m.containsKey(i));
     * assertTrue(m.containsValue("" + (i+bound)));
     * assertEquals("" + (i+bound), m.get(i));</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":100="100}, m2 contains {0="100":100="200"}.</p>
     * <p><b>Post-Condition</b>: Both maps contains {0="100":100="200"}.</p>
     * <p><b>Expected Results</b>: m.putAll(m2) overrides all previous mapping in m,
     * therefore m finally contains {0="100":100="200"}.</p>
     */
    @Test
    public void PutAll_0To100Duplicated()
    {
        int bound = 100;
        initHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
        {
            assertTrue(m.containsKey(i));
            assertTrue(m.containsValue("" + i));
            assertEquals("" + i, m.get(i));
        }
        // m2 initialization
        for (int i = 0; i < bound; i++)
            m2.put(i, "" + (i + bound));
        
        m.putAll(m2);

        for (int i = 0; i < bound; i++)
        {
            assertTrue(m.containsKey(i));
            assertTrue(m.containsValue("" + (i + bound)));
            assertEquals("" + (i + bound), m.get(i));
        }
    }

	// ------------------------------------------ remove method ------------------------------------------

	/**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests the remove method
	 * in a limit case, which is an empty map, which
	 * obviusly does not contain the key.</p>
     * <p><b>Test Description</b>: remove is invoked with 0
	 * key, which has not mapping in the map.</p>
     * <p><b>Pre-Condition</b>: m is empty.</p>
     * <p><b>Post-Condition</b>: m is unchanged.</p>
     * <p><b>Expected Results</b>: remove returns null as the
     * key has no mapping in the map and equals
	 * to empty map.</p>
     */
	@Test
	public void Remove_Empty()
	{
		assertNull("Should be null", m.remove(0));
		assertEquals("Should equal to empty map", new MapAdapter(), m);
	}

	/**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests the remove method
	 * in a case where the map does not contain the key.</p>
     * <p><b>Test Description</b>: remove is invoked with 20
	 * key, which is has not mapping in the map.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":10="10"}.</p>
     * <p><b>Post-Condition</b>: m is unchanged.</p>
     * <p><b>Expected Results</b>: remove returns null as the
     * key 20 had no mapping in the map and map
	 * contains {0="0":10="10"}, therefore it is unchanged.</p>
     */
	@Test
	public void Remove_NotInMap()
	{
		initHMap(m, 0, 10);
		assertNull("Should be null", m.remove(20));
		assertEquals("Should be equal", getIntegerMapAdapter(0, 10), m);
	}

	/**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests the remove method
	 * in a case where the map does contain the key.</p>
     * <p><b>Test Description</b>: remove is invoked with 1
	 * key, which is has already mapping in the map.</p>
     * <p><b>Pre-Condition</b>: m contains {1=3}.</p>
     * <p><b>Post-Condition</b>: m is empty.</p>
     * <p><b>Expected Results</b>: remove returns 3 as the
     * key 1 was mapped to 3 before removal.</p>
     */
    @Test
    public void Remove_Return()
    {
        m.put(1, 3);
        assertEquals(3, m.remove(1));
        assertTrue("Should be empty", m.isEmpty());
    }

	/**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests remove method
	 * with cases of size from 0 to 100. After each remove
	 * each assertions are being made. Tests a big input size
	 * for map.</p>
     * <p><b>Test Description</b>: Map contains entries {0="0":100="100"}.
	 * Then each entries is removed entry by entry, starting with key from 0 to 100.
	 * At each iteration the entry with key i is removed, is checked to not being contained,
	 * returning removed key's element should return null and size should have been
	 * decremented by one.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":100="100"}.</p>
     * <p><b>Post-Condition</b>: m is empty.</p>
     * <p><b>Expected Results</b>: At each iteration the entry with key i is removed, is checked to not being contained,
	 * returning removed key's element should return null and size should have been
	 * decremented by one. m is empty.</p>
     */
	@Test
	public void Remove_0To100Return()
	{
		int bound = 100;
		initHMap(m, 0, bound);
		for (int i = 0; i < bound; i++)
		{
			String iStr = "" + i;
			assertEquals("Should be contained", iStr, m.get(i));
			assertEquals("Should be removed", iStr, m.remove(i));
			assertFalse("Should not be contained", m.containsKey(i));
			assertNull("Shoud be null", m.get(i));
			assertEquals("Size should be " + (bound - i - 1), bound - i - 1, m.size());
		}
		assertTrue("Map should be empty.", m.isEmpty());

	}

    /**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: remove method is tested
     * when the argument is null, which should
     * cause NullPointerException to be thrown.</p>
     * <p><b>Test Description</b>: m.remove(null) is invoked.
     * NullPointerException has been
     * thrown.</p>
     * <p><b>Pre-Condition</b>: m is empty</p>
     * <p><b>Post-Condition</b>: m is empty</p>
     * <p><b>Expected Results</b>: NullPointerException has been
     * thrown.</p>
     */
    @Test (expected = NullPointerException.class)
    public void Remove_NullEmpty()
    {
        m.remove(null);
    }

    /**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: remove method is tested
     * when the argument is null, which should
     * cause NullPointerException to be thrown.</p>
     * <p><b>Test Description</b>: m.remove(null) is invoked.
     * NullPointerException has been
     * thrown.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":10="10}</p>
     * <p><b>Post-Condition</b>: m contains {0="0":10="10}</p>
     * <p><b>Expected Results</b>: NullPointerException has been
     * thrown.</p>
     */
    @Test (expected = NullPointerException.class)
    public void Remove_NullNotEmpty()
    {
        initHMap(m, 0, 10);
        m.remove(null);
    }


// ------------------------------------------ toString method ------------------------------------------

	/**
     * <p><b>Summary</b>: toString method test case.</p>
     * <p><b>Test Case Design</b>: Tests toString method on an empty
	 * map.</p>
     * <p><b>Test Description</b>: toString is invoked on a
	 * empty map.</p>
     * <p><b>Pre-Condition</b>: m is empty.</p>
     * <p><b>Post-Condition</b>: m is empty.</p>
     * <p><b>Expected Results</b>: m.toString returns {}</p>
     */
	@Test
	public void ToString_Empty()
	{
		assertEquals("Empty map toString should return {}", "{}", m.toString());
	}

	/**
     * <p><b>Summary</b>: toString method test case.</p>
     * <p><b>Test Case Design</b>: Tests toString method on a
	 * map containing 1=One.</p>
     * <p><b>Test Description</b>: toString is invoked on the map.</p>
     * <p><b>Pre-Condition</b>: m contains 1=One.</p>
     * <p><b>Post-Condition</b>: m contains 1=One.</p>
     * <p><b>Expected Results</b>: m.toString returns {1=One}</p>
     */
	@Test
	public void ToString_OneElement()
	{
		m.put(1, "One");
		assertEquals("This one element - map toString should return {1=One}", "{1=One}", m.toString());
	}

    /**
     * <p><b>Summary</b>: toString method test case.</p>
     * <p><b>Test Case Design</b>: Tests toString method on a
	 * map containing {0="0":100:"100"}. The toString method
     * is tested with m.keySet().iterator() iterating through
     * its elements, as the HMap interface states:
     * "The <i>order</i> of a map is defined as
     * the order in which the iterators on the map's collection views return their
     * elements."</p>
     * <p><b>Test Description</b>: Created the expected
     * string through iteration, then compares it with
     * toString return.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":100:"100"}.</p>
     * <p><b>Post-Condition</b>: m contains {0="0":100:"100"}.</p>
     * <p><b>Expected Results</b>: m.toString returns the expected
     * string.</p>
     */ 
	@Test
	public void ToString_0To100()
	{
		TestUtilities.initHMap(m, 0, 100);
		HIterator it = m.keySet().iterator();
		String expected = "{";
		while (it.hasNext())
		{
			Object k = it.next();
			expected += (k + "=");
			expected += (m.get(k));
			if (it.hasNext())
				expected += ", ";
		}
		expected += "}";

        assertEquals("Should return the expected string", expected, m.toString());
	}
    

    // ------------------------------------------ equals method ------------------------------------------

    /**
     * <p><b>Summary</b>: equals method test case.</p>
     * <p><b>Test Case Design</b>: equals method is tested with an equal MapAdapter
     * and a bigger and a smaller one. The returned values should be true and false.
     * Also reflective property of equal is tested.</p>
     * <p><b>Test Description</b>: Map is initialized, then different equals invoke are
     * asserted with different arguments, generated for each case.</p>
     * <p><b>Pre-Condition</b>: Map contains {0="0" : 100="100"}.</p>
     * <p><b>Post-Condition</b>: Map is unchanged.</p>
     * <p><b>Expected Results</b>: The Map is unchanged and symmetric property is valid.</p>
     */
    @Test
    public void Equals_0To100()
    {
        int to = 100;
        TestUtilities.initHMap(m, 0, to);
        assertTrue("Should equal", m.equals(TestUtilities.getIntegerMapAdapter(0, to)));
        assertTrue("Should equal", TestUtilities.getIntegerMapAdapter(0, to).equals(m));   // Symmetric property
        assertFalse("Should NOT equal", m.equals(TestUtilities.getIntegerMapAdapter(0, to + 15)));  // bigger Map returns false
        assertFalse("Should NOT equal", m.equals(TestUtilities.getIntegerMapAdapter(0, 5)));  // smaller Map returns false
    }

    
    /**
     * <p><b>Summary</b>: equals method test case.
     * The test case the method behaviour with 2 empty Map.</p>
     * <p><b>Test Case Design</b>: When both Maps are empty the equals
     * method should return true because an empty Map is equal to an
     * empty Map.</p>
     * <p><b>Test Description</b>: Single assert, m.equals(m2) invoked.</p>
     * <p><b>Pre-Condition</b>: Both Map are empty.</p>
     * <p><b>Post-Condition</b>: Both Map are empty.</p>
     * <p><b>Expected Results</b>: equals returns true, as one
     * empty Map of course equals another empty Map.</p>
     */
    @Test
    public void Equals_Empty_True()
    {
        assertTrue("Two empty Maps should equals.", m.equals(m2));
        assertTrue("Two empty Maps should equals.", m2.equals(m));
    }

    /**
     * <p><b>Summary</b>: equals method test case.
     * The reflective property of equal method is tested.</p>
     * <p><b>Test Case Design</b>: equals method should be reflective,
     * therefore x.equals(x) should always return true.</p>
     * <p><b>Test Description</b>: The test invokes m.equals(m) when
     * m is empty, when it has 10 elements and when it has 100 elements.</p>
     * <p><b>Pre-Condition</b>: Map is not null.</p>
     * <p><b>Post-Condition</b>: Map has 100 elements. </p>
     * <p><b>Expected Results</b>: Map equals itself, therefore
     * reflective property is valid.</p>
     */
    @Test
    public void Equals_Reflective()
    {
        assertTrue("Reflective property is not met.", m.equals(m));    // Map is empty
        TestUtilities.initHMap(m, 0, 10);
        assertTrue("Reflective property is not met.", m.equals(m));    // Map is not empty, should return true anyways
        TestUtilities.initHMap(m, 0, 100);
        assertTrue("Reflective property is not met.", m.equals(m));    // Map is not empty, should return true anyways
    }

    /**
     * <p><b>Summary</b>: equals method test case.
     * The transitive property of equal method is tested.</p>
     * <p><b>Test Case Design</b>: equals method should be transitive,
     * therefore a.equals(b) and b.equals(c) {@literal =>} a.equals(c).</p>
     * <p><b>Test Description</b>: The test invokes m.equals(m2) and m2.equals(m3)
     * and m.equals(m3)</p>
     * <p><b>Pre-Condition</b>: Maps contain {0="0" : 100="100"}.</p>
     * <p><b>Post-Condition</b>: Maps are unchanged. </p>
     * <p><b>Expected Results</b>: Equals has transitive property.</p>
     */
    @Test
    public void Equals_Transitive()
    {
        int to = 100;
        TestUtilities.initHMap(m, 0, to);
        TestUtilities.initHMap(m2, 0, to);
        HMap m3 = TestUtilities.getIntegerMapAdapter(0, to);

        assertTrue("Maps should be equal.", m.equals(m2));
        assertTrue("Maps should be equal.", m2.equals(m3));
        assertTrue("Transitive property is not met.", m.equals(m3));
    }

    /**
     * <p><b>Summary</b>: equals method test case.</p>
     * <p><b>Test Case Design</b>: equals method, when
     * invoked with a null argument, should just
     * return false.</p>
     * <p><b>Test Description</b>: m.equals(null)
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: m is empty</p>
     * <p><b>Post-Condition</b>: m is empty</p>
     * <p><b>Expected Results</b>: m.equals(null) returns false.</p>
     */
    @Test
    public void Equals_NullEmpty()
    {
        assertFalse(m.equals(null));
    }
	
    /**
     * <p><b>Summary</b>: equals method test case.</p>
     * <p><b>Test Case Design</b>: equals method, when
     * invoked with a null argument, should just
     * return false.</p>
     * <p><b>Test Description</b>: m.equals(null)
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":10="10"}.</p>
     * <p><b>Post-Condition</b>: m contains {0="0":10="10"}.</p>
     * <p><b>Expected Results</b>: m.equals(null) returns false.</p>
     */
    @Test
    public void Equals_NullNotEmpty()
    {
        initHMap(m, 0, 10);
        assertFalse(m.equals(null));
    }

    // ------------------------------------------ Coarse grained tests ------------------------------------------

    /**
     * <p><b>Summary</b>: Tests general insertions and inspections
     * on a map.</p>
     * <p><b>Test Case Design</b>: Map is modified through different
     * actions (insertions/removals) and its content is inspected.</p>
     * <p><b>Test Description</b>: Each pair of element is put in the
     * map</p>
     * <p><b>Pre-Condition</b>:</p>
     * <p><b>Post-Condition</b>:</p>
     * <p><b>Expected Results</b>:</p>
     */
    @Test
    public void MapInsertionsAndInspections0()
    {
        String[] arg_k = {"Under", "Break", "Life", "Money", "Ma"};
        String[] arg_v = {"Pressure", "Free", "Mars", "Money", "Baker"};
        int size = arg_k.length;

        for (int i = 0; i < size; i++)
        {
            assertFalse("Not contained yet", m.containsKey(arg_k[i]));
            assertNull("Should null", m.put(arg_k[i], arg_v[i]));
            assertTrue("Should be contained", m.containsKey(arg_k[i]));
            assertEquals("Size should be 1", 1, m.size());
            assertEquals("Should equal", arg_v[i], m.remove(arg_k[i]));
            assertFalse("Not contained yet", m.containsKey(arg_k[i]));
            assertEquals("Size should be 0", 0, m.size());
        }

        for (int i = 0; i < size; i++)
            assertNull("Should be null", m.put(arg_k[i], arg_v[i]));
        assertEquals("Should equal", "Free", m.remove("Break"));
        assertNull("Should be null", m.remove("Killer"));
        assertEquals("Should equal", "Mars", m.remove("Life"));

        assertEquals("Two removed, should be 3", 3, m.size());
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
