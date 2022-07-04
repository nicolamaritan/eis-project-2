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

public class TestMap
{
	int count = 0;
	HMap m = null;
	HMap m2 = null;
	HSet s1 = null;
	HSet ks = null;
	HIterator iter = null;
	HCollection c = null;

	String[] argv = {"pippo", "pluto", "qui", "ciccio", "gambatek"};

	@BeforeClass
	public static void BeforeClassMethod()
	{
		System.out.println(TestMap.class.getName() + " running.");
	}

	@Before
	public void BeforeMethod()
	{
		m = new MapAdapter();
		m2 = new MapAdapter();
	}

	@BeforeClass
	public static void AfterClassMethod()
	{
		System.out.println(TestMap.class.getName() + " ended.");
	}

	@After
	public void AfterMethod()
	{
		m = null;
	}
	
    // -------------------- Test cases assigned from the professor --------------------

	/**
     * <p><b>Summary</b>: Tests the propagation of changes from the backing map
	 * to the KeySet, as adding or removing entries from the backing map
	 * affects the KeySet. Tests the methods keySet, size, remove
	 * and put of MapAdapter, size of the KeySet.
	 * </p>
     * <p><b>Test Case Design</b>: HMap interface states that a change in HMap
	 * affects the already generated KeySets and obviusly the future KeySets. Therefore
	 * this test focuses on the propagation of different changes through puts and removes
	 * from the HMap to the KeySet.</p>
     * <p><b>Test Description</b>: The map is first initialized with entries
	 * with key and value equals to the elements in argv. Then a KeySet is created
	 * from m. The entry of key pippo is removed from the map, and then reinserted.</p>
     * <p><b>Pre-Condition</b>: The map contains argv elements as keys and values.</p>
     * <p><b>Post-Condition</b>:  The map contains argv elements as keys and values.</p>
     * <p><b>Expected Results</b>: After initialization m contains 5 entries with keys
	 * the argv elements. KeySet contains argv elements and its size is 5. Next the entry pippo=pippo
	 * is removed from the map, therefore map and KeySet contains argv elements
	 * except pippo=pippo and their size is 4. Finally the pippo=pippo entry is reinserted
	 * through map, implying its presence in keySet. They both have size 5.
	 * Lastly asserts that m and ks have the same size after all removal/insertions and
	 * the difference in size from stage 0 to stage 1 is 1.</p>
     */
	@Test
	public void TestPropagationFromMapToKeySet()
	{
		argvInitialize(m);
		int sm0, sm1, sm2, ss0, ss1, ss2;
		
		System.out.println("Test propagation from map to keyset");
		ks = m.keySet();

		//System.out.println(m + " " + m.size());
		for (String str : argv)
		{
			assertTrue("Should contain argv key.", m.containsKey(str));
			assertEquals(str, m.get(str));
		}
		assertEquals(5, m.size());

		//System.out.println(ks + " " + ks.size());
		for (String str : argv)
			assertTrue("Should contain argv key", ks.contains(str));
		assertEquals(5, ks.size());
		
		sm0 = m.size();
		ss0 = ks.size();
		m.remove(argv[0]);
		sm1 = m.size();
		ss1 = ks.size();

		//System.out.println("Entry removed: " + m + " " + m.size());
		for (int i = 1; i < argv.length; i++)
		{
			assertTrue("Should contain argv key.", m.containsKey(argv[i]));
			assertEquals(argv[i], m.get(argv[i]));
		}
		assertEquals(4, m.size());

		//System.out.println(ks + " " + ks.size());
		for (int i = 1; i < argv.length; i++)
			assertTrue("Should contain argv key", ks.contains(argv[i]));
		assertEquals(4, ks.size());
		
		m.put(argv[0], argv[0]);
		sm2 = m.size();
		ss2 = ks.size();

		//System.out.println("Entry restored: " + m + " " + m.size());
		for (String str : argv)
		{
			assertTrue("Should contain argv key.", m.containsKey(str));
			assertEquals(str, m.get(str));
		}
		assertEquals(5, m.size());

		//System.out.println(ks + " " + ks.size());
		for (String str : argv)
			assertTrue("Should contain argv key", ks.contains(str));
		assertEquals(5, ks.size());

		assertEquals(true, sm0 == ss0 && sm1 == ss1 && sm2 == ss2 && (sm0-sm1) == 1);
	}

    /**
     * <p><b>Summary</b>: Tests the method toString after initialization.</p>
     * <p><b>Test Case Design</b>: Tests the method toString.</p>
     * <p><b>Test Description</b>: Initialize the map with argv keys and
	 * values, then invoke toString.</p>
     * <p><b>Pre-Condition</b>: map contains argv keys and values.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: m.toString returns
	 * {pluto=pluto, gambatek=gambatek, ciccio=ciccio, qui=qui, pippo=pippo}.
	 * Note that map encloses entries with {}.</p>
     */
	@Test
	public void TestToString()
	{
		argvInitialize(m);
		//System.out.println("Map.toString() ? " + m);
		assertEquals("Map.toString() ? {pluto=pluto, gambatek=gambatek, ciccio=ciccio, qui=qui, pippo=pippo}", "Map.toString() ? " + m.toString());
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
     * <p><b>Test Description</b>: map is initialized with argv values and keys, then
	 * their presence is tested with contains method, its size is 5.
	 * Then the keySet s1 is created. KeySet iterator iterates through the set
	 * and asserts that the next key returned is equal to the element of the same
	 * key in the map (in fact pippo=pippo, qui=qui, ecc).
	 * Next the key pippo is removed from the KeySet, thus affecting the backing
	 * map (it removes pippo=pippo from it). KeySet iterator iterates through the set
	 * and asserts that the next key returned is equal to the element of the same
	 * key in the map (in fact pluto=pluto, qui=qui, ecc).
	 * Then the entry carrozza=carrozza is inserted through the map,
	 * thus affecting s1. Then the key carrozza is removed from s1, thus
	 * affecting the backing Map.</p>
     * <p><b>Pre-Condition</b>: map contains argv keys and values.</p>
     * <p><b>Post-Condition</b>: pippo=pippo is removed.</p>
     * <p><b>Expected Results</b>: backing Map is correctly affected by changes
	 * in keySet. At the end sm2 == m.size() || ss2 == s1.size() || s1.size() != m.size() is
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
		int sm0, sm1, sm2, ss0, ss1, ss2;
		System.out.println("Test keyset and backing");

		//System.out.println(m + " " + m.size());
		for (String str : argv)
		{
			assertTrue("Should contain argv key.", m.containsKey(str));
			assertEquals(str, m.get(str));
		}
		assertEquals(5, m.size());

		s1 = m.keySet();
		sm0 = m.size();
		ss0 = s1.size();

		iter = s1.iterator();
		count = s1.size()+2;
		while(iter.hasNext()&&count-->=0)
		{
			Object k = iter.next();
			
			//System.out.print("[" + k + "=" + m.get(k) + "]; ");
			assertEquals(true, k.equals(m.get(k)));
		}

		//System.out.println("\n" + s1);
		for (String str : argv)
			assertTrue("Should contain argv key", s1.contains(str));
		assertEquals(5, s1.size());
		
		s1.remove(argv[0]);

		sm1 = m.size();
		ss1 = s1.size();

		//System.out.println(m + " " + m.size());
		for (int i = 1; i < argv.length; i++)
		{
			assertTrue("Should contain argv key.", m.containsKey(argv[i]));
			assertEquals(argv[i], m.get(argv[i]));
		}
		assertEquals(4, m.size());

		iter = s1.iterator();
		count = s1.size()+2;
		while(iter.hasNext()&&count-->=0)
		{
			Object k = iter.next();

			//System.out.print("[" + k + "=" + m.get(k) + "]; ");
			assertEquals(true, k.equals(m.get(k)));
		}

		//System.out.println("\n" + s1);
		assertFalse("Should NOT contain", s1.contains(argv[0]));
		for (int i = 1; i < argv.length; i++)
			assertTrue("Should contain.", s1.contains(argv[i]));

		System.out.println("Inserisco nella mappa e controllo il set");
		m.put("carrozza", "carrozza");
		
		//System.out.println(m + " " + m.size());
		assertEquals("{pluto=pluto, gambatek=gambatek, carrozza=carrozza, ciccio=ciccio, qui=qui} 5", m + " " + m.size());

		iter = s1.iterator();
		count = s1.size()+2;
		while(iter.hasNext()&&count-->=0)
		{
			Object k = iter.next();
			
			//System.out.print("[" + k + "=" + m.get(k) + "]; ");
			assertEquals(true, k.equals(m.get(k)));
		}
		
		//System.out.println("\n" + s1);
		assertEquals("[pluto, gambatek, carrozza, ciccio, qui]", s1.toString());

		sm2 = m.size();
		ss2 = s1.size();

		// s1.remove("carrozza");
		// System.out.println("Removed carrozza from keyset");
		assertEquals("Should be removed.", true, s1.remove("carrozza"));

		System.out.println("set size=" + s1.size() + "; map size=" + m.size());
		assertEquals("set size=4; map size=4", "set size=" + s1.size() + "; map size=" + m.size());
		assertEquals(false, sm2 == m.size() || ss2 == s1.size() || s1.size() != m.size());
		assertEquals(true, (sm0 == ss0 && sm1 == ss1 && sm2 == ss2 && (sm0-sm1) == 1));
	}

    /**
     * <p><b>Summary</b>: Tests emptying the map through keySet'iterator.
	 * KeySet's iterator removes obviusly affects the KeySet, which affects the
	 * map. Tests method remove, size, keySet of map, hasNext, next, remove of
	 * KeySet iterator.</p>
     * <p><b>Test Case Design</b>: The tests focuses on map changes through
	 * keySet's iterator. The changes on KeySet should affect the backing map.</p>
     * <p><b>Test Description</b>: m is initialized with argv keys and values.
	 * s1 the KeySet is created. pippo=pippo is removed from the map.
	 * Then iterates through the keySet creating the string "pluto 3; gambatek 2; ciccio 1; qui 0; ",
	 * and removing each element after each next. Therefore the Map is then empty.</p>
     * <p><b>Pre-Condition</b>: The map contains argv keys and values but pippo=pippo.</p>
     * <p><b>Post-Condition</b>: The map is empty.</p>
     * <p><b>Expected Results</b>: The created string during iteration is
	 * "pluto 3; gambatek 2; ciccio 1; qui 0; ". The map is empty after
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

		String temp = "";
		while(iter.hasNext() && count-- >= 0)
		{
			Object k = iter.next();
			iter.remove();
			temp += (k + " " + m.size() + "; ");
		}

		assertEquals("pluto 3; gambatek 2; ciccio 1; qui 0; ", temp);
		assertEquals("[]", s1.toString());
		//System.out.println("\n" + s1);

		assertEquals(true, m.size() == s1.size() && m.size() == 0);
	}

    /**
     * <p><b>Summary</b>: Tests clearing the map through clear,
	 * reinserting the argv elements and the propagation from
	 * values to map. Tests method clear, put, size, values of map
	 * iterator, remove of values, hasNext, next of values' iterator.</p>
     * <p><b>Test Case Design</b>: After clearing the map with clear,
	 * tests changes propagation from values to map. Infact, HMap interface
	 * states that changes in values set propagate to the backing map.</p>
     * <p><b>Test Description</b>: clear method is invoked on the map.
	 * Then argv keys and values are inserted in the map. pippo=pippo is
	 * inserted twice, but map only accept unique keys.
	 * values is created and iterates through the set to created the temp string.</p>
     * <p><b>Pre-Condition</b>: map contains argv values and keys.</p>
     * <p><b>Post-Condition</b>: map contains argv values and keys but pippo=pippo. </p>
     * <p><b>Expected Results</b>: After clear the map is empty (toString returns {}).
	 * After inserting argv keys and values and pippo=pippo m.size is equal to argv.size,
	 * as the second pippo=pippo insertion is not valid (unique keys). m is checked to
	 * contain all argv keys and values and its size is 5. temp then is equal to
	 * "pluto; gambatek; ciccio; qui; pippo; ". pippo is removed from values,
	 * temp is "pluto; gambatek; ciccio; qui; "
	 * (sm0 == ss0 && sm1 == ss1 && sm2 == ss2 && (sm0-sm1) == 1) is true, which means
	 * the sizes of map and values equals in each stage, and the difference in size
	 * from stage 0 and 1 is 1.</p>
     */
	@Test
	public void ResetMapContentAndTestValues()
	{
		int sm0, sm1, sm2, ss0, ss1, ss2;
		System.out.println("reset map content and test values");
		m.clear();
		
		//System.out.println("Before " + m + " " + m.size());
		assertEquals("Before{} 0", "Before" + m + " " + m.size());

		m.put(argv[0], argv[0]);
		for(int i=0;i<argv.length;i++)
		{
			m.put(argv[i], argv[i]);
		}
		
		assertFalse("*** map.put malfunction ***", m.size() != argv.length);

		//System.out.println("after " + m + " " + m.size());
		for (String str : argv)
		{
			assertTrue("Should contain argv key.", m.containsKey(str));
			assertEquals(str, m.get(str));
		}
		assertEquals(5, m.size());
		

		c = m.values();

		sm0 = m.size();
		ss0 = c.size();

		iter = c.iterator();
		count = c.size() +2;
		String temp = "";
		while(iter.hasNext() && count-- >= 0)
			temp += iter.next() + "; ";
			//System.out.print(iter.next() + "; ");

		assertEquals("pluto; gambatek; ciccio; qui; pippo; ", temp);
		for (String str : argv)
			assertTrue("Should be contained.", c.contains(str));
		//System.out.println("\n" + c);

		c.remove(argv[0]);

		sm1 = m.size();
		ss1 = c.size();

		//System.out.println(m + " " + m.size());
		for (int i = 1; i < argv.length; i++)
		{
			assertTrue("Should contain argv key", m.containsKey(argv[i]));
			assertEquals(m.get(argv[i]), argv[i]);
		}
		assertEquals(4, m.size());

		iter = c.iterator();
		count = c.size()+2;

		temp = "";
		while(iter.hasNext()&&count-->= 0)
			temp += iter.next() + "; ";
		assertEquals("pluto; gambatek; ciccio; qui; ", temp);
		//System.out.print(iter.next() + "; ");
		//System.out.println("\n" + c);
		
		assertFalse("Should NOT be contained", c.contains(argv[0]));
		for (int i = 1; i < argv.length; i++)
			assertTrue("Should be contained", c.contains(argv[i]));

		sm2 = m.size();
		ss2 = c.size();

		assertTrue("*** values NON propaga modifiche a map ***", (sm0 == ss0 && sm1 == ss1 && sm2 == ss2 && (sm0-sm1) == 1));
	}

    /**
     * <p><b>Summary</b>: Tests emptying the map through value's iterator.
	 * Value's iterator removes obviusly affects the Value, which affects the
	 * map. Tests method remove, size, keySet of map, hasNext, next, remove of
	 * Value iterator.</p>
     * <p><b>Test Case Design</b>: The tests focuses on map changes through
	 * Value's iterator. The changes on Value should affect the backing map.</p>
     * <p><b>Test Description</b>: m is initialized with argv keys and values.
	 * s1 the KeySet is created. pippo=pippo is removed from the map.
	 * Then iterates through the keySet creating the string "pluto 3; gambatek 2; ciccio 1; qui 0; ",
	 * and removing each element after each next. Therefore the Map is then empty.</p>
     * <p><b>Pre-Condition</b>: The map contains argv keys and values but pippo=pippo.</p>
     * <p><b>Post-Condition</b>: The map is empty.</p>
     * <p><b>Expected Results</b>: The created string during iteration is
	 * "pluto 3; gambatek 2; ciccio 1; qui 0; ". The map is empty after
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
		String temp = "";
		while(iter.hasNext()&&count-->=0)
		{
			//System.out.print(iter.next() + "; ");
			temp += iter.next() + "; ";
			iter.remove();
		}
		assertEquals("pluto; gambatek; ciccio; qui; ", temp);
		//System.out.println("\nmap " + m.size() + "; collection " + c.size());
		assertEquals("map 0; collection 0", "map " + m.size() + "; collection " + c.size());
		assertTrue("", m.size() == c.size() && m.size() == 0);	
	}

	private void argvInitialize(HMap m)
	{
		for(int i=0;i<argv.length;i++)
		{
			m.put(argv[i], argv[i]);
		}
	}

	// -------------------- Test cases ideated by me --------------------

	// -------------------- clear, isEmpty, size --------------------

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
        assertEquals(0, m.size());
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
        m.put(1 + "", 1);
        assertEquals(1, m.size());
        m.clear();
        assertTrue("Should be empty.", m.isEmpty());
        assertEquals(0, m.size());
    }

    /**
     * <p><b>Summary</b>: clear, isEmpty and size method test case.</p>
     * <p><b>Test Case Design</b>: Tests the case of a
     * clear invoke on a map containing 10 entries.</p>
     * <p><b>Test Description</b>: map contains 10 entries. clear method
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: map contains {1=1:10=10}.</p>
     * <p><b>Post-Condition</b>: map is empty.</p>
     * <p><b>Expected Results</b>: isEmpty returns true and size is 0.</p>
     */
    @Test
    public void Clear_On1To10()
    {
        TestUtilities.getIntegerMapAdapter(0, 10);
        m.clear();
        assertTrue("Should be empty.", m.isEmpty());
        assertEquals(0, m.size());
    }

    /**
     * <p><b>Summary</b>: clear, isEmpty and size method test case.</p>
     * <p><b>Test Case Design</b>: Tests the case of a
     * clear invoke on a map containing 10000 entries, which is a
     * case with a big number of elements.</p>
     * <p><b>Test Description</b>: map contains 10000 entries. clear method
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: map contains {1=1:10000=10000}.</p>
     * <p><b>Post-Condition</b>: map is empty.</p>
     * <p><b>Expected Results</b>: isEmpty returns true and size is 0.</p>
     */
    @Test
    public void Clear_On1To10000()
    {
        TestUtilities.getIntegerMapAdapter(0, 10000);
        m.clear();
        assertTrue("Should be empty.", m.isEmpty());
        assertEquals(0, m.size());
    }

    // -------------------- containsKey --------------------

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
     * <p><b>Test Description</b>: {0=0, 100000=100000} are 
     * checked to be in the map or not.</p>
     * <p><b>Pre-Condition</b>: map contains {0=0, 10000=10000}.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: {0 : 10000} keys are contained,
     * the others no.</p>
     */
    @Test
    public void ContainsKey_0To10000()
    {
        int bound = 10000;
        int bound2 = 100000;
        TestUtilities.initHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
            assertTrue("Should contain.", m.containsKey(i));
        for (int i = bound; i < bound2; i++)
            assertFalse("Should not contain.", m.containsKey(i));
        
    }

    // -------------------- containsValue --------------------

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
     * <p><b>Test Description</b>: {0=0, 100000=100000} are 
     * checked to be in the map or not.</p>
     * <p><b>Pre-Condition</b>: map contains {0=0, 10000=10000}.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: {0 : 10000} values are contained,
     * the others no.</p>
     */
    @Test
    public void ContainsValue_0To10000()
    {
        int bound = 10000;
        int bound2 = 100000;
        TestUtilities.initHMap(m, 0, bound);
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
     * <p><b>Test Description</b>: {0=0, 100000=100000} are 
     * checked to be in the map or not.</p>
     * <p><b>Pre-Condition</b>: map contains {0=0, 10000=10000}.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: {0 : 10000} values are contained,
     * the others no.</p>
     */
    @Test
    public void ContainsValue_0To10000Duplicates()
    {
        int bound = 10000;
        int bound2 = 100000;
        TestUtilities.initHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
            m.put(i + bound, i);

        for (int i = 0; i < bound; i++)
            assertTrue("Should contain.", m.containsValue(i));
        for (int i = bound; i < bound2; i++)
            assertFalse("Should not contain.", m.containsValue(i));
        
    }

	// -------------------- get --------------------

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
     * from 1000 to 2000.</p>
     * <p><b>Pre-Condition</b>: m contains {0=0:1000=1000}</p>
     * <p><b>Post-Condition</b>: m is unchanged</p>
     * <p><b>Expected Results</b>: each get returns null.</p>
     */
    @Test
    public void Get_0To1000NotInMap()
    {
        int bound = 1000;
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
     * from 0 to 1000.</p>
     * <p><b>Pre-Condition</b>: m contains {0=0:1000=1000}</p>
     * <p><b>Post-Condition</b>: m is unchanged</p>
     * <p><b>Expected Results</b>: each get returns i in string form.</p>
     */
    @Test
    public void Get_0To1000()
    {
        int bound = 1000;
        TestUtilities.initHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
            assertEquals("" + i, m.get(i));
    }

	// -------------------- hashCode --------------------

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
        assertEquals("maps should be equal.", true, m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        // One element case
        m.put(1, "1");
        m2.put(1, "1");
        assertEquals("maps should be equal.", true, m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        TestUtilities.initHMap(m, -100, 100);
        TestUtilities.initHMap(m2, -100, 100);
        assertEquals("maps should be equal.", true, m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        m.remove((Object)0);
        m2.remove((Object)0);
        assertEquals("maps should be equal.", true, m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        m.put(101, "101");
        m2.put(101, "101");
        assertEquals("maps should be equal.", true, m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        TestUtilities.addToHMap(m, 500, 1000);
        TestUtilities.addToHMap(m2, 500, 1000);
        assertEquals("maps should be equal.", true, m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        HMap t = TestUtilities.getIntegerMapAdapter(-1000, -900);
        m.putAll(t);
        m2.putAll(t);
        assertEquals("maps should be equal.", true, m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        TestUtilities.initHMap(t, 5000, 6000);
        m.putAll(t);
        m2.putAll(t);
        assertEquals("maps should be equal.", true, m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

    }

	// -------------------- put --------------------
	
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
		assertEquals(1, m.size());
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
     * <p><b>Post-Condition</b>: m contains {0="0" : 1000="1000"}</p>
     * <p><b>Expected Results</b>: m.get(i)="i" for each i in (0:1000) and its
	 * size is 1000, therefore puts works correctly.</p>
     */
	@Test
	public void Put_0To1000()
	{
		int bound = 1000;
		for (int i = 0; i < bound; i++)
			m.put(i, "" + i);
		assertEquals(bound, m.size());
		for (int i = 0; i < bound; i++)
			assertEquals("" + i, m.get(i));
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
    public void putAll_NullCollection_NPE()
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
    public void putAll_EmptyCollection_False()
    {
		m.putAll(m2);
        assertEquals("The Map should be empty.", true, m.isEmpty());
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
     * <p><b>Expected Results</b>: Map contains the added elements {1="1", 2="2", 3="3"} and its size is 3.</p>
     */
    @Test
    public void putAll_FromEmptyTo123()
    {
        TestUtilities.initHMap(m2, 1, 4);

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
     * <p><b>Expected Results</b>: Map contains the added elements {1="1":6="6"}.</p>
     */
    @Test
    public void putAll_From123to12345()
    {
        TestUtilities.initHMap(m, 1, 4);
        m.remove(3);
        TestUtilities.initHMap(m2, 3, 6);
        m.putAll(m2);
        assertEquals("The Map should be equal.", true, m.equals(TestUtilities.getIntegerMapAdapter(1, 6)));
    }

    /**
     * <p><b>Summary</b>: putAll method test case.
     * The method thest the insertion of a large number
     * of elements through putAll.</p>
     * <p><b>Test Case Design</b>: The test considers the case
     * scenario of large input. From the Sommerville: "Force computation results to be
     * too large or too small."</p>
     * <p><b>Test Description</b>: Numbers from 1 to 100 are inserted in coll, then putAll
     * method is invoked with coll as argument.</p>
     * <p><b>Pre-Condition</b>: The Map is empty, coll contains aforementioed numbers.</p>
     * <p><b>Post-Condition</b>: The Map contains number from 0 to 100.</p>
     * <p><b>Expected Results</b>: Entries i="i" entries are stored correctly,
	 * with i from 0 to 100.
     * The Map size is 100.
     */
    @Test
    public void putAll_0to100()
    {
        TestUtilities.initHMap(m2, 0, 100);
		m.putAll(m2);
        assertEquals("Size should be 100.",100, m.size());
		for (int i = 0; i < 100; i++)
			assertEquals("" + i, m.get(i));
    }

	// ------------------------------------------ remove method ------------------------------------------

	/**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests the remove method
	 * in a limit case, which is an empty map, which
	 * obviusly does not contain the key.</p>
     * <p><b>Test Description</b>: remove is invoked with 0
	 * key.</p>
     * <p><b>Pre-Condition</b>: m is empty.</p>
     * <p><b>Post-Condition</b>: m is unchanged.</p>
     * <p><b>Expected Results</b>: remove returns null and equals
	 * to empty map.</p>
     */
	@Test
	public void Remove_Empty()
	{
		assertNull("Should be null", m.remove(0));
		assertEquals(new MapAdapter(), m);
	}

	/**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests the remove method
	 * in a case where the map does not contain the key.</p>
     * <p><b>Test Description</b>: remove is invoked with 20
	 * key.</p>
     * <p><b>Pre-Condition</b>: m contains {0="0":10="10"}.</p>
     * <p><b>Post-Condition</b>: m is unchanged.</p>
     * <p><b>Expected Results</b>: remove returns null and map
	 * contains {0="0":10="10"}.</p>
     */
	@Test
	public void Remove_NotInMap()
	{
		TestUtilities.initHMap(m, 0, 10);
		assertNull("Should be null", m.remove(20));
		assertEquals(TestUtilities.getIntegerMapAdapter(0, 10), m);
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
	public void Remove_0To100()
	{
		int bound = 100;
		TestUtilities.initHMap(m, 0, bound);
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
		assertEquals("{}", m.toString());
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
		assertEquals("{1=One}", m.toString());
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
