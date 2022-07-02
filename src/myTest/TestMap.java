package myTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import myAdapter.*;

public class TestMap
{
	int count = 0;
	HMap m = null;
	HSet s1 = null;
	HSet ks = null;
	HIterator iter = null;
	HCollection c = null;

	String[] argv = {"pippo", "pluto", "qui", "ciccio", "gambatek"};

	@Before
	public void BeforeMethod()
	{
		m = new MapAdapter();
	}
	
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
			assertTrue("Should contain argv key.", m.containsKey(str));
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
			assertTrue("Should contain argv key.", m.containsKey(argv[i]));
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
			assertTrue("Should contain argv key.", m.containsKey(str));
		assertEquals(5, m.size());

		//System.out.println(ks + " " + ks.size());
		for (String str : argv)
			assertTrue("Should contain argv key", ks.contains(str));
		assertEquals(5, ks.size());

		assertEquals(true, sm0 == ss0 && sm1 == ss1 && sm2 == ss2 && (sm0-sm1) == 1);
	}

    /**
     * <p><b>Summary</b>:</p>
     * <p><b>Test Case Design</b>:</p>
     * <p><b>Test Description</b>:</p>
     * <p><b>Pre-Condition</b>:</p>
     * <p><b>Post-Condition</b>:</p>
     * <p><b>Expected Results</b>:</p>
     */
	@Test
	public void TestToString()
	{
		argvInitialize(m);
		//System.out.println("Map.toString() ? " + m);
		assertEquals("Map.toString() ? {pluto=pluto, gambatek=gambatek, ciccio=ciccio, qui=qui, pippo=pippo}", "Map.toString() ? " + m.toString());
	}

	@Test
	public void TestKeySetAndBacking()
	{
		argvInitialize(m);
		int sm0, sm1, sm2, ss0, ss1, ss2;
		System.out.println("Test keyset and backing");

		//System.out.println(m + " " + m.size());
		assertEquals("{pluto=pluto, gambatek=gambatek, ciccio=ciccio, qui=qui, pippo=pippo} 5", m + " " + m.size());

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
		assertEquals("[pluto, gambatek, ciccio, qui, pippo]", s1.toString());
		
		s1.remove(argv[0]);

		sm1 = m.size();
		ss1 = s1.size();

		//System.out.println(m + " " + m.size());
		assertEquals("{pluto=pluto, gambatek=gambatek, ciccio=ciccio, qui=qui} 4", m + " " + m.size());
		
		iter = s1.iterator();
		count = s1.size()+2;
		while(iter.hasNext()&&count-->=0)
		{
			Object k = iter.next();

			//System.out.print("[" + k + "=" + m.get(k) + "]; ");
			assertEquals(true, k.equals(m.get(k)));
		}

		//System.out.println("\n" + s1);
		assertEquals("[pluto, gambatek, ciccio, qui]", s1.toString());

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
		assertEquals(true, s1.remove("carrozza"));

		System.out.println("set size=" + s1.size() + "; map size=" + m.size());
		assertEquals("set size=4; map size=4", "set size=" + s1.size() + "; map size=" + m.size());
		assertEquals(false, sm2 == m.size() || ss2 == s1.size() || s1.size() != m.size());
		assertEquals(true, (sm0 == ss0 && sm1 == ss1 && sm2 == ss2 && (sm0-sm1) == 1));
	}

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
		assertEquals("after {pluto=pluto, gambatek=gambatek, ciccio=ciccio, qui=qui, pippo=pippo} 5",
					 "after " + m + " " + m.size());
		

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
		assertEquals("[pluto, gambatek, ciccio, qui, pippo]", c.toString());
		//System.out.println("\n" + c);

		c.remove(argv[0]);

		sm1 = m.size();
		ss1 = c.size();

		System.out.println(m + " " + m.size());
		assertEquals("{pluto=pluto, gambatek=gambatek, ciccio=ciccio, qui=qui} 4",
					 m.toString() + " " + m.size());

		iter = c.iterator();
		count = c.size()+2;

		temp = "";
		while(iter.hasNext()&&count-->= 0)
			temp += iter.next() + "; ";
		assertEquals("pluto; gambatek; ciccio; qui; ", temp);
		//System.out.print(iter.next() + "; ");
		//System.out.println("\n" + c);
		assertEquals("[pluto, gambatek, ciccio, qui]", c.toString());

		sm2 = m.size();
		ss2 = c.size();

		assertTrue("*** values NON propaga modifiche a map ***", (sm0 == ss0 && sm1 == ss1 && sm2 == ss2 && (sm0-sm1) == 1));
	}

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

}

    /**
     * <p><b>Summary</b>:</p>
     * <p><b>Test Case Design</b>:</p>
     * <p><b>Test Description</b>:</p>
     * <p><b>Pre-Condition</b>:</p>
     * <p><b>Post-Condition</b>:</p>
     * <p><b>Expected Results</b>:</p>
     */
