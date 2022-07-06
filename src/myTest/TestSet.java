package myTest;

// Imports
import static org.junit.Assert.*;
import static myTest.TestUtilities.*;
import org.junit.*;
import myAdapter.*;

public class TestSet
{
	HSet s = null, s2 = null;
	HCollection c = null;
    HMap m = null;
	HIterator iter2 = null;
	String[] argv = {"pippo", "pippo", "pluto", "paperino", "ciccio", "qui"};

	@BeforeClass
	public static void BeforeClassMethod()
	{
		System.out.println("Test Set");
	}

    /**
	 * Method invoke before each test for setup.
	 */
	@Before
	public void Setup()
	{
        m = new MapAdapter();
	}

	@AfterClass
	public static void AfterClassMethod()
	{
		System.out.println(TestSet.class.getName() + " ended.");
	}


    /**
	 * Method invoke before each test for cleanup.
	 */
	@After
	public void Cleanup()
	{
		s = null;
        s2 = null;
		c = null;
        m = null;
        iter2 = null;
	}

	// ------------------------------------------ Test cases assigned from the professor ------------------------------------------

	/**
     * <p><b>Summary</b>: Tests the behaviour of the entrySet of a map
	 * when inserting clones in the bacing map. size, and itertator of entryset
	 * are tested.</p>
     * <p><b>Test Case Design</b>: HSet interface states that
	 * it should not contain clones, therefore inserting an
	 * already present element in the backing map should just ignore the insertion
	 * and return false as the set has not changed. In the test
	 * elements are added when clones or not.</p>
     * <p><b>Test Description</b>: iterates through the set.
	 * pippo=pippo is added to the map, tries to add pippo=pippo once again
	 * but it is already present. Adds the remaining elements to the map, asserts
	 * size to be i and iterate for each remaining element.</p>
     * <p><b>Pre-Condition</b>: Set is empty.</p>
     * <p><b>Post-Condition</b>: Set contains argv elements in the form
     * of argv[i]=argv[i].</p>
     * <p><b>Expected Results</b>: First pippo=pippo insertion returns true,
	 * second returns false. Remaining insertion return true, size is
	 * i at each iteration. In the end argv lenght is 1 more than s size.
     * Propagation from map to entryset works correctly.</p>
     */
	@Test
	public void EntrySetClonesAcceptanceTest()
	{
        s = m.entrySet();
		System.out.print(s.size() + " ");
		assertEquals(0, s.size());
		iterate(s.iterator());

		//assertTrue(s.add(argv[0]));
		//assertFalse(s.add(argv[1]));

        m.put(argv[0], argv[0]);
        m.put(argv[1], argv[1]);

		for(int i=2;i<argv.length;i++)
		{
			boolean res = !s.contains(getEntry(argv[i], argv[i]));
            m.put(argv[i], argv[i]);

			System.out.println(argv[i] + " " + res);
			System.out.print(s.size() + " ");

			assertTrue(res);
			assertEquals(i, s.size());
			iterate(s.iterator());
		}
		assertEquals("\n*** set wronlgy accepts clones ***\n", argv.length - 1, s.size());
	}

    /**
     * <p><b>Summary</b>: Tests the content of the entryset, which should contain
	 * the elements in argv as keys and values, [pluto=pippo, ciccio=ciccio, qui=qui,
     * paperino=paperino, pippo=pippo].</p>
     * <p><b>Test Case Design</b>: Tests the content of the set. As the element
	 * in a set are not ordered, unlike in Hset, printing
	 * a string representation of the object to test its content
	 * is not reliable, therefore it is better looping through the elements in
	 * some way to test their presence in the set.</p>
     * <p><b>Test Description</b>: Removes the element in argv in entry form
     * (key, value) one by one, asserting them to be contained.
	 * If they are the same elements, at each iteration the element must be
	 * in the set and its size should be  greater than zero (it should not
	 * be empty). If the for loop finish and the final
	 * size is zero, the elements are the same and the
	 * sets contains the right elements.
	 * The sets is then restored.</p>
     * <p><b>Pre-Condition</b>: s contains argv=argv (key, value) elements.</p>
     * <p><b>Post-Condition</b>: s in unchanged.</p>
     * <p><b>Expected Results</b>: At each iteration isEmpty returns false
	 * and contains returns true, therefore set contains the argv elements
     * in entry form, pluto=pippo, ciccio=ciccio, qui=qui,
     * paperino=paperino, pippo=pippo.</p>
     */
	@Test
	public void EntrySetCheckContentTest()
	{
		argvInitialize(m);
        s = m.entrySet();
		System.out.println("Set.toString()? " + s);
		for (int i = 1; i < argv.length; i++)
		{
			assertFalse("Not same elements.", s.isEmpty());
			assertTrue("Should be contained", s.contains(getEntry(argv[i], argv[i])));
			s.remove(getEntry(argv[i], argv[i]));
		}
		assertTrue("Not all elements removed", s.isEmpty());
		argvInitialize(m);
	}

	/**
     * <p><b>Summary</b>: Tests removing an element of the set
	 * through its iterator, after each next. Set's iterator tested methods
	 * are next, remove and hasNext.</p>
     * <p><b>Test Case Design</b>: Iterator's remove should work and remove
	 * method from the set. The test case emoves elements until the set is empty,
	 * therefore s.size() should return 0 in the last assertion.</p>
     * <p><b>Test Description</b>: Set is Initialized with argv elements.
	 * Iterates through set and after each next it removes the element,
	 * decrementing i. i starts from 5. At each iteration iterate with a new
	 * iterator is invoked to test correct iteration.</p>
     * <p><b>Pre-Condition</b>: set contains argv elements.</p>
     * <p><b>Post-Condition</b>: set is empty, as it has been emptied
	 * by iterator's remove.</p>
     * <p><b>Expected Results</b>: In each iteration must be --i equals s.size.
	 * At the end set size is 0. Therefore iterator's remove works correctly.</p>
     */
	@Test
	public void EntrySetIteratorRemovalTest()
	{
		argvInitialize(m);
        s = m.entrySet();
		
		iter2 = s.iterator();
		int i = 5;
		while(iter2.hasNext())
		{
			iter2.next();
			iter2.remove();
			System.out.print(s.size() + " ");
			assertEquals(--i, s.size());
			iterate(s.iterator());
		}
		assertEquals("\n*** set iterator removal fails ***\n", 0, s.size());
	}

	/**
     * <p><b>Summary</b>: Tests the behaviour of the keyset of a map
	 * when inserting clones in the backing map. size, add and itertator of HSet
	 * are tested.</p>
     * <p><b>Test Case Design</b>: HSet interface states that
	 * it should not contain clones, therefore inserting an
	 * already present element in the backing map should just ignore the insertion
	 * and return false as the set has not changed. In the test
	 * elements are added when clones or not.</p>
     * <p><b>Test Description</b>: iterates through the set.
	 * pippo=pippo is added to the map, tries to add pippo=pippo once again
	 * but it is already present. Note that the key is already
     * present in the keyset as key. Adds the remaining elements to the map, asserts
	 * size to be i and iterate for each remaining element.</p>
     * <p><b>Pre-Condition</b>: Set is empty.</p>
     * <p><b>Post-Condition</b>: Set contains argv elements only once.</p>
     * <p><b>Expected Results</b>: First pippo=pippo insertion returns true,
	 * second returns false. Remaining insertion return true, size is
	 * i at each iteration. In the end argv lenght is 1 more than s size.
     * Propagation from map to keyset works correctly.</p>
     */
	@Test
	public void KeySetClonesAcceptanceTest()
	{
        s = m.keySet();
		System.out.print(s.size() + " ");
		assertEquals(0, s.size());
		iterate(s.iterator());

		//assertTrue(s.add(argv[0]));
		//assertFalse(s.add(argv[1]));

        m.put(argv[0], argv[0]);
        m.put(argv[1], argv[1]);

		for(int i=2;i<argv.length;i++)
		{
            // Check if modification have been made
			boolean res = !s.contains(argv[i]);
            m.put(argv[i], argv[i]);

			System.out.println(argv[i] + " " + res);
			System.out.print(s.size() + " ");

			assertTrue(res);
			assertEquals(i, s.size());
			iterate(s.iterator());
		}
		assertEquals("\n*** set wronlgy accepts clones ***\n", argv.length - 1, s.size());
	}

    /**
     * <p><b>Summary</b>: Tests the content of the entryset, which should contain
	 * the keys of the map only once, as definition of map
     * (containing unique keys) and definition of set (containing unique elements)
     * [pluto, ciccio, qui, paperino, pippo].</p>
     * <p><b>Test Case Design</b>: Tests the content of the set. As the element
	 * in a set are not ordered, printing
	 * a string representation of the object to test its content
	 * is not reliable, therefore it is better looping through the elements in
	 * some way to test their presence in the set.</p>
     * <p><b>Test Description</b>: Removes the element in argv in key form
     * one by one, asserting them to be contained.
	 * If they are the same elements, at each iteration the element must be
	 * in the set and its size should be  greater than zero (it should not
	 * be empty). If the for loop finish and the final
	 * size is zero, the elements are the same and the
	 * sets contains the right elements.
	 * The sets is then restored.</p>
     * <p><b>Pre-Condition</b>: s contains argv elements.</p>
     * <p><b>Post-Condition</b>: s in unchanged.</p>
     * <p><b>Expected Results</b>: At each iteration isEmpty returns false
	 * and contains returns true, therefore set contains the argv elements
     * in key form, pluto, ciccio, qui, paperino, pippo.</p>
     */
	@Test
	public void KeySetCheckContentTest()
	{
		argvInitialize(m);
        s = m.keySet();
		System.out.println("Set.toString()? " + s);
		for (int i = 1; i < argv.length; i++)
		{
			assertFalse("Not same elements.", s.isEmpty());
			assertTrue("Should be contained", s.contains(argv[i]));
			s.remove(argv[i]);
		}
		assertTrue("Not all elements removed", s.isEmpty());
		argvInitialize(m);
	}

	/**
     * <p><b>Summary</b>: Tests removing an element of the set
	 * through its iterator, after each next. Set's iterator tested methods
	 * are next, remove and hasNext.</p>
     * <p><b>Test Case Design</b>: Iterator's remove should work and remove
	 * method from the set. The test case emoves elements until the set is empty,
	 * therefore s.size() should return 0 in the last assertion.</p>
     * <p><b>Test Description</b>: Set is Initialized with argv elements.
	 * Iterates through set and after each next it removes the element,
	 * decrementing i. i starts from 5. At each iteration iterate with a new
	 * iterator is invoked to test correct iteration.</p>
     * <p><b>Pre-Condition</b>: set contains argv elements.</p>
     * <p><b>Post-Condition</b>: set is empty, as it has been emptied
	 * by iterator's remove.</p>
     * <p><b>Expected Results</b>: In each iteration must be --i equals s.size.
	 * At the end set size is 0. Therefore iterator's remove works correctly.</p>
     */
	@Test
	public void KeySetIteratorRemovalTest()
	{
		argvInitialize(m);
        s = m.keySet();
		
		iter2 = s.iterator();
		int i = 5;
		while(iter2.hasNext())
		{
			iter2.next();
			iter2.remove();
			System.out.print(s.size() + " ");
			assertEquals(--i, s.size());
			iterate(s.iterator());
		}
		assertEquals("\n*** set iterator removal fails ***\n", 0, s.size());
	}

	/**
     * <p><b>Summary</b>: Tests the behaviour of the values HSet of a map
	 * when inserting cloned value to the map. size, add and itertator of SetAdapter
	 * are tested.</p>
     * <p><b>Test Case Design</b>: HCollection interface does not
	 * specify clones presence. In a collection clones are accepted. In the test
	 * elements are added when clones or not. Propagation from values to map
     * and viceversa are tested.</p>
     * <p><b>Test Description</b>: iterates through the set.
	 * pippo value is added from the map, tries to add pippo value once again
	 * even if it is already present. Adds the remaining elements as value, asserts
	 * size to be i and iterate for each remaining element.</p>
     * <p><b>Pre-Condition</b>: Set is empty.</p>
     * <p><b>Post-Condition</b>: Set contains argv elements.</p>
     * <p><b>Expected Results</b>: First pippo insertion returns true,
	 * second returns true too. Remaining insertions return true, size is
	 * i at each iteration. In the end argv lenght is not 1 more than s size.</p>
     */
	@Test
	public void ValuesCollectionClonesAcceptanceTest()
	{
        
        c = m.values();
		assertEquals(0, c.size());
		iterate(c.iterator());
		
        // Out of the loop because manual key
        m.put("Random key", argv[0]);   // The important thing is that the key will be unique

		for(int i=1;i<argv.length;i++)
		{
            // Check if modification have been made
            int initSize = c.size();
            m.put(argv[i], argv[i]);
			boolean res = initSize - c.size() < 0 ? true : false;

			System.out.println(argv[i] + " " + res);
			System.out.print(c.size() + " ");
			assertTrue(res);
			assertEquals(i + 1, c.size());
			iterate(c.iterator());
		}
		assertFalse("\n*** collection refuses clones ***\n", c.size() == argv.length - 1);
	}

    /**
     * <p><b>Summary</b>: Tests the content of the set, which should contain
	 * the elements in argv, [pluto, ciccio, qui, paperino, pippo, pippo].</p>
     * <p><b>Test Case Design</b>: Tests the content of the set. As the element
	 * in a collection are not ordered printing
	 * a string representation of the object to test its content
	 * is not reliable, therefore it is better looping through the elements in
	 * some way to test their presence in the collection.</p>
     * <p><b>Test Description</b>: Removes the element in argv one by one.
	 * If they are the same elements, at each iteration the element must be
	 * in the set and its size should be  greater than zero (it should not
	 * be empty). If the for loop finish and the final size is zero,
	 * the elements are the same and the
	 * sets contains the right elements.
	 * The sets is then restored.</p>
     * <p><b>Pre-Condition</b>: s contains argv elements.</p>
     * <p><b>Post-Condition</b>: s in unchanged.</p>
     * <p><b>Expected Results</b>: At each iteration isEmpty returns false
	 * and contains returns true, therefore argv and the sets contains
	 * the same elements. set contains [pluto, ciccio, qui, paperino, pippo, pippo].
     * Propagation from map to set works right.</p>
     */
	@Test
	public void ValuesCollectionCheckContentTest()
	{
		argvInitialize(m);
        // Out of the loop because manual key
        m.put("Random key", argv[0]); 

        c = m.values();

		System.out.println("Collection.toString()? " + c);
		for (int i = 0; i < argv.length; i++)
		{
			assertFalse("Not same elements.", c.isEmpty());
			assertTrue("Should be contained", c.contains(argv[i]));
			c.remove(argv[i]);
		}
		assertTrue("Not all elements removed", c.isEmpty());
		argvInitialize(m);
	}

	/**
     * <p><b>Summary</b>: Tests removing an element of the Collection
	 * through its iterator, after each next. Collection's iterator tested methods
	 * are next, remove and hasNext.</p>
     * <p><b>Test Case Design</b>: Iterator's remove should work and remove
	 * method from the Collection. The test case emoves elements until the Collection is empty,
	 * therefore s.size() should return 0 in the last assertion.</p>
     * <p><b>Test Description</b>: Collection is Initialized with argv elements.
	 * Iterates through Collection and after each next it removes the element,
	 * decrementing i. i starts from 6. At each iteration iterate with a new
	 * iterator is invoked to test correct iteration.</p>
     * <p><b>Pre-Condition</b>: Collection contains argv elements.</p>
     * <p><b>Post-Condition</b>: Collection is empty, as it has been emptied
	 * by iterator's remove.</p>
     * <p><b>Expected Results</b>: In each iteration must be --i equals s.size.
	 * At the end Collection size is 0. Therefore iterator's remove works correctly.</p>
     */
	@Test
	public void CollectionIteratorRemovalTest()
	{
		argvInitialize(m);
        // Out of the loop because manual key
        m.put("Random key", argv[0]); 
        c = m.values();

		int i = 6;
		iter2 = c.iterator();
		while(iter2.hasNext())
		{
			iter2.next();
			iter2.remove();
			System.out.print(c.size() + " ");
			assertEquals(--i, c.size());
			iterate(c.iterator());
		}
		assertEquals("\n*** collection iterator removal fails ***\n", 0, c.size());
	}

	/**
	 * Iterates from iter's current position while has
	 * next, and constructs a string while doing it.
	 * @param iter the iterator
	 */
	public static void iterate(HIterator iter)
	{
		System.out.print("{");
		while(iter.hasNext())
		{
			System.out.print(iter.next() + "; ");
		}
		System.out.println("}");
	}

	/**
	 * Initialize the collection c with the elements
	 * in argv.
	 * @param c collection to be initialized
	 */
	public void argvInitialize(HCollection c)
	{
        c.clear();
		for (int i = 0; i < argv.length; i++)
			c.add(argv[i]);
	}

    /**
	 * Initialize the map m with the elements
	 * in argv as keys and values.
	 * @param c collection to be initialized
	 */
    public void argvInitialize(HMap m)
    {
        m.clear();
        for (int i = 0; i < argv.length; i++)
            m.put(argv[i], argv[i]);
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
