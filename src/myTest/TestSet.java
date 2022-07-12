package myTest;

// Imports
import static org.junit.Assert.*;
import static myTest.TestUtilities.*;
import org.junit.*;
import myAdapter.*;

/**
 * <p><b>Summary</b>: The test suite TestSet focuses on the HCollection returned from value()
 * method of HMap, and HSet returned from keySet() method of HMap, tests their correct
 * behaviour in different case scenario. Each HSet/HCollection method is tested in different
 * test cases.
 * The test suite contains the tests in the TestSet.java
 * file assigned by the professor in the JUnit format (further testing in {@code TestEntrySet},
 * {@code TestValues} and {@code TestKeySet}). The original tests
 * directly inserted elements in the set through add(Object) method,
 * but the HMap interfaces states that the HSet/HCollection returned from value() cannot use
 * add or addAll. Therefore the original test (contained in TestSet.java given by the professor)
 * has being recreated inserting an entry
 * with adeguate value to the map, in other to indirectly "insert" that element in
 * the set.
 * Notation used in this test suite:
 * <ul>
 * <li>x=y means an entry, where s is the key, and y is its mapped value.</li>
 * </ul>
 * 
 * <p><b>Test Suite Design</b>: Test cases include modification test, where
 * the map structure is modified in different ways, and inspection test, where information are retrieved
 * from the map to see if the informations are stored correctly, and tests where modifications and
 * inspections are combined.
 * The kind of test cases in this test suite are:
 * <ul>
 * <li>ClonesAcceptanceTest, where the object behaviour is tested inserting clones.</li>
 * <li>CheckContentTest, where the object content is checked</li>
 * <li>IteratorRemovalTest, where the object is emptied through HIterator.remove()</li>
 * </ul>
 * Prints are kept from the original file, as it helped during the Test Driven Development
 * at looking for errors and fixing them.
 * @author  Nicola Maritan
 * @see TestEntrySet
 * @see TestKeySet
 */
public class TestSet
{
	private HSet s = null;
	private HCollection c = null;
    private HMap m = null;
	private HIterator iter2 = null;
	private String[] argv = {"pippo", "pippo", "pluto", "paperino", "ciccio", "qui"};

	/**
	 * Prints test suite name before running tests.
	 */
    @BeforeClass
    public static void BeforeClassMethod()
    {
        System.out.println(TestSet.class.getName() + " running.");
    }

    /**
	 * Method invoke before each test for setup.
	 */
	@Before
	public void Setup()
	{
        m = new MapAdapter();
	}
	
	/**
	 * Prints test suite name after running tests.
	 */
	@AfterClass
	public static void AfterClassMethod()
	{
		System.out.println(TestSet.class.getName() + " ended.");
	}


    /**
	 * Method invoke after each test for cleanup.
	 */
	@After
	public void Cleanup()
	{
		s = null;
		c = null;
        m = null;
        iter2 = null;
	}

	// ------------------------------------------ Test cases assigned by the professor ------------------------------------------

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
	 * but it is already present. Adds the remaining elements to the map
	 * and iterate for each remaining element.
	 * In each iteration it tries to put new entry argv[i]=argv[i],
	 * prints true if a new element is inserted, false if it was already
	 * present in the map (entry already in the map) and iterates through
	 * its elements through a new iterator
	 * (iterate(s.iterator())).</p>
     * <p><b>Pre-Condition</b>: Set is empty.</p>
     * <p><b>Post-Condition</b>: Set contains argv elements in the form
     * of argv[i]=argv[i].</p>
     * <p><b>Expected Results</b>: EntrySet correctly refuses clones.
	 * First pippo=pippo insertion returns true,
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
		for(int i=0;i<argv.length;i++)
		{
			// True if new insertion
			boolean res = m.put(argv[i], argv[i]) == null;
			System.out.println(argv[i] + " " + res);
			System.out.print(s.size() + " ");
			iterate(s.iterator());
		}
		assertEquals("\n*** set wronlgy accepts clones ***\n", argv.length - 1, s.size());
	}

    /**
     * <p><b>Summary</b>: Tests the content of the entryset, which should contain
	 * the elements in argv as keys and values, [pluto=pippo, ciccio=ciccio, qui=qui,
     * paperino=paperino, pippo=pippo].</p>
     * <p><b>Test Case Design</b>: Tests the content of the set. As the element
	 * in a set are not ordered, asserting Equals
	 * a string representation of the object to test its content
	 * is not reliable, therefore it is better looping through the elements in
	 * some way to test their presence in the set.</p>
     * <p><b>Test Description</b>: Initialize the map with argv keys and
	 * values (recreates the previous test's situation, as the original
     * file TestMap.java was not formatted in the JUnit format).
	 * Removes the element in argv in entry form
     * (key, value) one by one, asserting them to be contained.
	 * If they are the same elements, at each iteration the element must be
	 * in the set and its size should be  greater than zero (it should not
	 * be empty). If the for loop finish and the final
	 * size is zero, the elements are the same and the
	 * sets contains the right elements.
	 * The sets is then restored.</p>
     * <p><b>Pre-Condition</b>: s contains argv[i]=argv[i] (key, value) elements,
	 * for each i in (1, argv.lenght) (1 because only one pippo=pippo in map).</p>
     * <p><b>Post-Condition</b>: s in unchanged.</p>
     * <p><b>Expected Results</b>: At each iteration isEmpty returns false
	 * and contains returns true, therefore set contains the argv elements
     * in entry form, pluto=pippo, ciccio=ciccio, qui=qui,
     * paperino=paperino, pippo=pippo.</p>
     */
	@Test
	public void EntrySetCheckContentTest()
	{
		// Recreates original file configuration for test case
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
     * <p><b>Test Description</b>: Initialize the map with argv keys and
	 * values (recreates the previous test's situation, as the original
     * file TestMap.java was not formatted in the JUnit format).
	 * Set is Initialized with argv elements.
	 * Iterates through set and after each next it removes the element (iter2.remove()),
	 * decrementing i. i starts from 5. At each iteration iterate with a new
	 * iterator is invoked to test correct iteration (iterate(s.iterator())).</p>
     * <p><b>Pre-Condition</b>: Entryset contains argv elements.</p>
     * <p><b>Post-Condition</b>: Entryset is empty, as it has been emptied
	 * by iterator's remove.</p>
     * <p><b>Expected Results</b>: EntrySet iterator removal works.
	 * In each iteration must be --i equals s.size.
	 * At the end set size is 0. Therefore iterator's remove works correctly.</p>
     */
	@Test
	public void EntrySetIteratorRemovalTest()
	{
		// Recreates original file configuration for test case
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
     * present in the keyset as key. Adds the remaining elements to the map
	 * and iterate for each remaining element.
	 * In each iteration it tries to put new entry argv[i]=argv[i],
	 * prints true if a new element is inserted, false if it was already
	 * present in the keySet (key already in the map) and iterates through
	 * its elements through a new iterator
	 * (iterate(s.iterator())).</p>
     * <p><b>Pre-Condition</b>: KeySet is empty.</p>
     * <p><b>Post-Condition</b>: KeySet contains argv elements only once.</p>
     * <p><b>Expected Results</b>: KeySet correctly refuses clones.
	 * First pippo=pippo insertion returns true,
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
		for(int i=0;i<argv.length;i++)
		{
			// True if new insertion
			boolean res = m.put(argv[i], argv[i]) == null;

			System.out.println(argv[i] + " " + res);
			System.out.print(s.size() + " ");
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
	 * in a set are not ordered, asserting Equals
	 * a string representation of the object to test its content
	 * is not reliable, therefore it is better looping through the elements in
	 * some way to test their presence in the set.</p>
     * <p><b>Test Description</b>: Initialize the map with argv keys and
	 * values (recreates the previous test's situation, as the original
     * file TestMap.java was not formatted in the JUnit format).
	 * Removes the element in argv as key
     * one by one, asserting them to be contained.
	 * If they are the same elements, at each iteration the element must be
	 * in the set and its size should be  greater than zero (it should not
	 * be empty). If the for loop finish and the final
	 * size is zero, the elements are the same and the
	 * sets contains the right elements.
	 * The sets is then restored.</p>
     * <p><b>Pre-Condition</b>: s contains argv[i]=argv[i] (key, value) elements,
	 * for each i in (1, argv.lenght) (1 because only one pippo=pippo in map).</p>
     * <p><b>Post-Condition</b>: s in unchanged.</p>
     * <p><b>Expected Results</b>: At each iteration isEmpty returns false
	 * and contains returns true, therefore set contains the argv elements
     * as keys, pluto, ciccio, qui, paperino, pippo.</p>
     */
	@Test
	public void KeySetCheckContentTest()
	{
		// Recreates original file configuration for test case
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
     * <p><b>Test Description</b>: Initialize the map with argv keys and
	 * values (recreates the previous test's situation, as the original
     * file TestMap.java was not formatted in the JUnit format).
	 * Set is Initialized with argv elements.
	 * Iterates through set and after each next it removes the element,
	 * decrementing i. i starts from 5. At each iteration iterate with a new
	 * iterator is invoked to test correct iteration (iterate(s.iterator())).</p>
     * <p><b>Pre-Condition</b>: set contains argv elements.</p>
     * <p><b>Post-Condition</b>: set is empty, as it has been emptied
	 * by iterator's remove.</p>
     * <p><b>Expected Results</b>: KeySet iterator removal works.
	 * In each iteration must be --i equals s.size.
	 * At the end set size is 0. Therefore iterator's remove works correctly.</p>
     */
	@Test
	public void KeySetIteratorRemovalTest()
	{
		// Recreates original file configuration for test case
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
	 * when inserting cloned value to the map. size, iterator of SetAdapter
	 * are tested.</p>
     * <p><b>Test Case Design</b>: HCollection interface does not
	 * specify clones presence. In a collection clones are accepted. In the test
	 * elements are added when clones or not. Propagation from values to map
     * and viceversa are tested.</p>
     * <p><b>Test Description</b>: Initialize the map with argv keys and
	 * values (recreates the previous test's situation, as the original
     * file TestMap.java was not formatted in the JUnit format).
	 * Iterates through the set.
	 * pippo value is added from the map, tries to add pippo value once again
	 * even if it is already present. Adds the remaining elements as value
	 * and iterate for each remaining element.
	 * In each iteration it tries to put new entry argv[i]=argv[i],
	 * prints true if a new element is inserted, false if no insertion
	 * was made and iterates through
	 * its elements through a new iterator
	 * (iterate(s.iterator())).</p>
     * <p><b>Pre-Condition</b>: Set is empty.</p>
     * <p><b>Post-Condition</b>: Set contains argv elements.</p>
     * <p><b>Expected Results</b>: Collection value accepts clones.
	 * First pippo insertion returns true,
	 * second returns true too. Remaining insertions return true, size is
	 * i at each iteration. In the end argv lenght is not 1 more than s size.</p>
     */
	@Test
	public void ValuesCollectionClonesAcceptanceTest()
	{
        c = m.values();
		assertEquals(0, c.size());
		iterate(c.iterator());
		
        // Out of the loop because manual key, in order to insert 2 pippos in collection
        m.put("Random key", argv[0]);   // The important thing is that the key will be unique, only values HCollection is tested

		for(int i=1;i<argv.length;i++)
		{
            // Check if modification have been made
            int initSize = c.size();
            m.put(argv[i], argv[i]);
			boolean res = c.size() - initSize == 1;

			System.out.println(argv[i] + " " + res);
			System.out.print(c.size() + " ");
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
     * <p><b>Test Description</b>: Initialize the map with argv keys and
	 * values (recreates the previous test's situation, as the original
     * file TestMap.java was not formatted in the JUnit format).
	 * Removes the element in argv one by one.
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
		// Recreates original file configuration for test case
		argvInitialize(m);
        // Out of the loop because manual key, in order to insert 2 pippo in collection
        m.put("Random key", argv[0]);	// The important thing is that the key will be unique, only values HCollection is tested

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
     * <p><b>Test Description</b>: Initialize the map with argv keys and
	 * values (recreates the previous test's situation, as the original
     * file TestMap.java was not formatted in the JUnit format).
	 * Collection is Initialized with argv elements.
	 * Iterates through Collection and after each next it removes the element,
	 * decrementing i. i starts from 6. At each iteration iterate with a new
	 * iterator is invoked to test correct iteration (iterate(c.iterator())).</p>
     * <p><b>Pre-Condition</b>: Collection value contains argv elements.</p>
     * <p><b>Post-Condition</b>: Collection value iterator removal works.
	 * Collection is empty, as it has been emptied
	 * by iterator's remove.</p>
     * <p><b>Expected Results</b>: In each iteration must be --i equals s.size.
	 * At the end Collection size is 0. Therefore iterator's remove works correctly.</p>
     */
	@Test
	public void CollectionIteratorRemovalTest()
	{
		// Recreates original file configuration for test case
		argvInitialize(m);
        // Out of the loop because manual key, in order to insert 2 pippos in the collection
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
	 * Initialize the map m with the elements
	 * in argv as keys and values.
	 * @param m map to be initialized
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
