package myTest;

// Imports
import static org.junit.Assert.*;
import static myTest.TestUtilities.*;
import org.junit.*;
import myAdapter.*;

public class TestCollectionAdapter
{
	HCollection c = null, c2 = null;
	HIterator iter2 = null;
	String[] argv = {"pippo", "pippo", "pluto", "paperino", "ciccio", "qui"};

    HMap m = null;
    HCollection head;
	HCollection param = null;
	HCollection intersect = null;
	HCollection ct = null;

	@BeforeClass
	public static void BeforeClassMethod()
	{
		System.out.println("Test Set");
	}

	@Before
	public void Setup()
	{
		c = new CollectionAdapter();
        c2 = new CollectionAdapter();

        head = new CollectionAdapter();
        head.add("Collection Adapter");

        ct = new CollectionAdapter();
        ct.add("Collection Adapter");
	    ct.add("aaa");
	    ct.add("bbb");
	    ct.add("ccc");

		param = new CollectionAdapter();
		param.add("aaa");
		param.add("bbb");
		param.add("ccc");
		
		intersect = new CollectionAdapter();
		intersect.add("aaa");
		intersect.add("bbb");
		intersect.add("ccc");
	}

	@After
	public void Cleanup()
	{
		c = null;
        c2 = null;
	}

	// ------------------------------------------ Test cases assigned from the professor ------------------------------------------


	/**
     * <p><b>Summary</b>: Tests the behaviour of CollectionAdapter
	 * when inserting clones. size, add and itertator of SetAdapter
	 * are tested.</p>
     * <p><b>Test Case Design</b>: HCollection interface does not
	 * specify clones presence. In a collection clones are accepted. In the test
	 * elements are added when clones or not.</p>
     * <p><b>Test Description</b>: iterates through the collection.
	 * pippo is added, tries to add pippo once again
	 * even if it is already present. Adds the remaining elements, asserts
	 * size to be i and iterate for each remaining element.</p>
     * <p><b>Pre-Condition</b>: collection is empty.</p>
     * <p><b>Post-Condition</b>: collection contains argv elements.</p>
     * <p><b>Expected Results</b>: First pippo insertion returns true,
	 * second returns true too. Remaining insertion return true, size is
	 * i at each iteration. In the end argv lenght is not 1 more than c size.</p>
     */
	@Test
	public void CollectionClonesAcceptanceTest()
	{
		assertEquals(0, c.size());
		iterate(c.iterator());
		
		for(int i=0;i<argv.length;i++)
		{
			boolean res = c.add(argv[i]);
			System.out.println(argv[i] + " " + res);
			System.out.print(c.size() + " ");
			assertTrue(res);
			assertEquals(i + 1, c.size());
			iterate(c.iterator());
		}
		assertFalse("\n*** collection refuses clones ***\n", c.size() == argv.length - 1);
	}

    /**
     * <p><b>Summary</b>: Tests the content of the collection, which should contain
	 * the elements in argv, [pluto, ciccio, qui, paperino, pippo].</p>
     * <p><b>Test Case Design</b>: Tests the content of the collection. As the element
	 * in a collection are not ordered printing
	 * a string representation of the object to test its content
	 * is not reliable, therefore it is better looping through the elements in
	 * some way to test their presence in the collection.</p>
     * <p><b>Test Description</b>: Removes the element in argv one by one.
	 * If they are the same elements, at each iteration the element must be
	 * in the collection and its size should be  greater than zero (it should not
	 * be empty). If the for loop finish and the final size is zero,
	 * the elements are the same and the
	 *  collections  contains the right elements.
	 * The  collections  is then restored.</p>
     * <p><b>Pre-Condition</b>: c contains argv elements.</p>
     * <p><b>Post-Condition</b>: c in unchanged.</p>
     * <p><b>Expected Results</b>: At each iteration isEmpty returns false
	 * and contains returns true, therefore argv and the  collections  contains
	 * the same elements.</p>
     */
	@Test
	public void CollectionCheckContentTest()
	{
		argvInitialize(c);
		System.out.println("Collection.toString()? " + c);
		for (int i = 0; i < argv.length; i++)
		{
			assertFalse("Not same elements.", c.isEmpty());
			assertTrue("Should be contained", c.contains(argv[i]));
			c.remove(argv[i]);
		}
		assertTrue("Not all elements removed", c.isEmpty());
		argvInitialize(c);
	}

	/**
     * <p><b>Summary</b>: Tests removing an element of the Collection
	 * through its iterator, after each next. Collection's iterator tested methods
	 * are next, remove and hasNext.</p>
     * <p><b>Test Case Design</b>: Iterator's remove should work and remove
	 * method from the Collection. The test case emoves elements until the Collection is empty,
	 * therefore c.size() should return 0 in the last assertion.</p>
     * <p><b>Test Description</b>: Collection is Initialized with argv elements.
	 * Iterates through Collection and after each next it removes the element,
	 * decrementing i. i starts from 6. At each iteration iterate with a new
	 * iterator is invoked to test correct iteration.</p>
     * <p><b>Pre-Condition</b>: Collection contains argv elements.</p>
     * <p><b>Post-Condition</b>: Collection is empty, as it has been emptied
	 * by iterator's remove.</p>
     * <p><b>Expected Results</b>: In each iteration must be --i equals c.size.
	 * At the end Collection size is 0. Therefore iterator's remove works correctly.</p>
     */
	@Test
	public void CollectionIteratorRemovalTest()
	{
		argvInitialize(c);
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
		for (int i = 0; i < argv.length; i++)
			c.add(argv[i]);
	}

    	/**
     * <p><b>Summary</b>: clear method test case.</p>
     * <p><b>Test Case Design</b>: Tests clear through clear method.</p>
     * <p><b>Test Description</b>: clear is invoked.</p>
     * <p><b>Pre-Condition</b>: ct contains at least one element.</p>
     * <p><b>Post-Condition</b>: ct is empty.</p>
     * <p><b>Expected Results</b>: ct is empty.</p>
     */
	@Test
	public void clear() {
		ct.clear();
		assertTrue(ct.isEmpty());
	}
	
	/**
     * <p><b>Summary</b>: contains method test case.</p>
     * <p><b>Test Case Design</b>: Tests contains behaviour
	 * with one element in the list.</p>
     * <p><b>Test Description</b>: contains is invoked.</p>
     * <p><b>Pre-Condition</b>: ct contains the element to verify.</p>
     * <p><b>Post-Condition</b>: ris is true.</p>
     * <p><b>Expected Results</b>: ris is true.</p>
     */
	@Test
	public void contains_o() {
		boolean ris = ct.contains("Collection Adapter");
		assertTrue(ris);
	}
	
	/**
     * <p><b>Summary</b>: containsAll method test case and verify that all element
	 * in c are contained in ct.</p>
     * <p><b>Test Case Design</b>: containsAll method is invoked.</p>
     * <p><b>Test Description</b>: containsAll method is invoked.</p>
     * <p><b>Pre-Condition</b>: ct contains each element contained in c.</p>
     * <p><b>Post-Condition</b>: ris is true.</p>
     * <p><b>Expected Results</b>: ris is true</p>
     */
	@Test
	public void containsAll_c() {
		boolean ris = ct.containsAll(param);
		assertTrue(ris);
	}
	
	/**
     * <p><b>Summary</b>: equals method test case.</p>
     * <p><b>Test Case Design</b>: Tests equals behaviour with
	 * 4 equals objects.</p>
     * <p><b>Test Description</b>: Collection are made equal, then equals
	 * is invoked.</p>
     * <p><b>Pre-Condition</b>: Two objects are equals.</p>
     * <p><b>Post-Condition</b>: ris is true.</p>
     * <p><b>Expected Results</b>: ris is true</p>
     */
	@Test
	public void equals_o() {
		CollectionAdapter temp = new CollectionAdapter();
		temp.add("Collection Adapter");
		temp.add("aaa");
		temp.add("bbb");
		temp.add("ccc");
		
		boolean ris = ct.equals(temp);
		assertTrue(ris);
	}
	
	@Test
	/**
	 * @safe.precondition
	 * @safe.postcondition
	 * @safe.summary
	 */
	public void hash_Code() {
		
	}
	
	/**
     * <p><b>Summary</b>: isEmpty method test case.</p>
     * <p><b>Test Case Design</b>: isEmpty is tested after a clear invoke.</p>
     * <p><b>Test Description</b>: ct is cleared, then isEmpty is invoked.</p>
     * <p><b>Pre-Condition</b>: ct contains 1 element.</p>
     * <p><b>Post-Condition</b>: ct is empty, ris is true.</p>
     * <p><b>Expected Results</b>: ris is true</p>
     */
	@Test
	public void is_empty() {
		ct.clear();
		boolean ris = ct.isEmpty();
		assertTrue(ris);
	}
	
	@Test
	/**
	 * @safe.precondition
	 * @safe.postcondition
	 * @safe.summary
	 */
	public void iterator() {
		
	}
	
    /**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests remove method on a present
	 * object, which should return true.</p>
     * <p><b>Test Description</b>: o is removed from ct.</p>
     * <p><b>Pre-Condition</b>: ct contains at least one element.</p>
     * <p><b>Post-Condition</b>: o is removed from ct.</p>
     * <p><b>Expected Results</b>: ris is true.</p>
     */
	@Test
	public void remove_o() {
		Object o = "Collection Adapter";
		boolean ris = ct.remove(o);
		assertTrue(ris);
	}
	
	/**
     * <p><b>Summary</b>: removeAll method test case.</p>
     * <p><b>Test Case Design</b>: Tests removeAll method on a present
	 * collection, which should return true.</p>
     * <p><b>Test Description</b>: o is removed from ct.</p>
     * <p><b>Pre-Condition</b>: ct contains at least one element.</p>
     * <p><b>Post-Condition</b>: o is removed from ct.</p>
     * <p><b>Expected Results</b>: ris is true.</p>
     */
	@Test
	public void remove_all_c() {
		boolean ris = ct.removeAll(param);
		assertTrue(ris);
	}
	
	/**
     * <p><b>Summary</b>: remove method test case.</p>
     * <p><b>Test Case Design</b>: Tests remove method on a present
	 * object, which should return true.</p>
     * <p><b>Test Description</b>: o is removed from ct.</p>
     * <p><b>Pre-Condition</b>: ct contains at least one element.</p>
     * <p><b>Post-Condition</b>: o is removed from ct.</p>
     * <p><b>Expected Results</b>: ris is true.</p>
     */
	@Test
	public void retain_all_c() {
		boolean ris = ct.retainAll(param);
		assertTrue(ris);
	}
	
    /**
     * <p><b>Summary</b>: size method test case.</p>
     * <p><b>Test Case Design</b>: Tests size method.</p>
     * <p><b>Test Description</b>: Invoke size method.</p>
     * <p><b>Pre-Condition</b>: ct has 4 elements.</p>
     * <p><b>Post-Condition</b>: ct is unchanged.</p>
     * <p><b>Expected Results</b>: size is 4.</p>
     */
	@Test
	public void size() {
		int size = ct.size();
		assertEquals(4, size);
	}
	
	/**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests toArray with a single element.</p>
     * <p><b>Test Description</b>: size is true if lenght is 1, content
	 * is true if temp[0] equals "Collection Adapter".</p>
     * <p><b>Pre-Condition</b>: head has at least 1 element.</p>
     * <p><b>Post-Condition</b>: temp is an array containing head elements.</p>
     * <p><b>Expected Results</b>:size AND content are true.</p>
     */
	@Test
	public void to_array() {
		Object[] temp = head.toArray();
		boolean size = (temp.length == 1);
		boolean content = (temp[0].equals("Collection Adapter"));
		assertTrue(size && content);
	}
	
	/**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests toArray with a single element.</p>
     * <p><b>Test Description</b>: size is true if lenght is 1, content
	 * is true if temp[0] equals "Collection Adapter".</p>
     * <p><b>Pre-Condition</b>: head has at least 1 element.</p>
     * <p><b>Post-Condition</b>: temp is an array containing head elements.</p>
     * <p><b>Expected Results</b>:size AND content are true.</p>
     */
	@Test
	public void to_array_a() {
		Object[] a = new Object[10];
		a = head.toArray(a);
		boolean size = (a.length == 1);
		boolean content = (a[0].equals("Collection Adapter"));
		assertTrue(size && content);
	}

	// ------------------------------------------ Test cases ideated by me ------------------------------------------

	// ------------------------------------------ size method ------------------------------------------

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that an empty collection
     * should have a size of zero and isEmpty call returning
     * true.
     * The collection is not modified
     * since its creation.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 0 size (empty). Test based on the trivial but possible
     * state of an empty collection.</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the collection.</p>
     * <p><b>Pre-Condition</b>: The collection is empty.</p>
     * <p><b>Post-Condition</b>: The collection is still empty.</p>
     * <p><b>Expected Results</b>: The size method returns 0 and the isEmpty
     * method returns true.</p>
     */
    @Test
    public void Size_Empty_0()
    {
        assertEquals("Empty collection does not have size of zero.", 0, c.size());
        assertEquals("isEmpty did not returned true.", true, c.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a collection with 1 element
     * should have a size of 1 and isEmpty call returning
     * false.
     * The collection is modified before the assertc.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 1 size and not being empty.</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the collection.</p>
     * <p><b>Pre-Condition</b>: The collection is empty.</p>
     * <p><b>Post-Condition</b>: The collection contains 1.</p>
     * <p><b>Expected Results</b>: The size method returns 1 and the isEmpty
     * method returns false.</p>
     */
    @Test
    public void Size_OneElement_1()
    {
        c.add(1);
        assertEquals("Empty collection does not have size of one.", 1, c.size());
        assertEquals("isEmpty did not returned false.", false, c.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a collection with 3 elements
     * should have a size of 3 and isEmpty call returning
     * false.
     * The collection is modified before the assertc.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 3 size and not being empty.</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the collection.</p>
     * <p><b>Pre-Condition</b>: The collection is empty.</p>
     * <p><b>Post-Condition</b>: The collection has 3 elements ({0, 1, 2}).</p>
     * <p><b>Expected Results</b>: The size method returns 3 and the isEmpty
     * method returns false.</p>
     */
    @Test
    public void Size_ThreeElements_3()
    {
        initHCollection(c, 0, 3);
        assertEquals("collection with 3 elements does not have size of 3.", 3, c.size());
        assertEquals("isEmpty did not returned false.", false, c.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a collection with 3 elements
     * should have a size of 500 and isEmpty call returning
     * false.
     * The collection is modiefied before the assertc.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 500 size and not being empty. Large
     * input is considered in this test case.</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the collection.</p>
     * <p><b>Pre-Condition</b>: The collection is empty.</p>
     * <p><b>Post-Condition</b>: The collection contains 500 elements ({0:500}).</p>
     * <p><b>Expected Results</b>: The size method returns 500 and the isEmpty
     * method returns false.</p>
     */
    @Test
    public void Size_500Elements()
    {
        initHCollection(c, 0, 500);
        assertEquals("collection with 500 elements does not have size of 500.", 500, c.size());
        assertEquals("isEmpty did not returned false.", false, c.isEmpty());
    }

	// ------------------------------------------ contains method ------------------------------------------

    /**
     * <p><b>Summary</b>: contains method test case.
     * The test case checks in various situation its internal state
     * with containc.</p>
     * <p><b>Test Case Design</b>: contains method is tested with the same
     * elements while adding them.</p>
     * <p><b>Test Description</b>: First 1 is added and asserts are performed,
     * then 2 and 3 are added and asserts are performed.</p>
     * <p><b>Pre-Condition</b>: The collection is empty.</p>
     * <p><b>Post-Condition</b>: The collection is not empty.</p>
     * <p><b>Expected Results</b>: The collection contains the right elements during
     * execution, in particular:
     * <ul>
     * <li>contains(1) = false</li>
     * <li>contains(1) = true</li>
     * <li>contains(3) = false</li>
     * <li>contains(1) = true</li>
     * </ul>
     */
    @Test
    public void Contains_1and3()
    {
        assertEquals("The collection contains 1 even if it is empty.", false, c.contains(1));
        c.add(1);
        assertEquals("The collection does not contain 1 even if it should.", true, c.contains(1));
        assertEquals("The collection should not contain 3.", false, c.contains(3));
        c.add(2);
        c.add(3);
        assertEquals("The collection does not contain 1 even if it should.", true, c.contains(1));
    }

    /**
     * <p><b>Summary</b>: contains method test case.
     * <p><b>Test Case Design</b>: The test case checks in various situation its internal state
     * with containc, changing through execution.</p>
     * <p><b>Test Description</b>: Numbers from 50 (included) to 100 (excluded) are added, then checks if
     * elements from 25 to 125 are contained in the collection in 3 different stepc.
     * <ul>
     * <li>{25:50} not contained (beginning).</li>
     * <li>{50:100} contained (middle).</li>
     * <li>{100:125} not contained (ending).</li>
     * </ul>
     * <p><b>Pre-Condition</b>: The collection is empty.</p>
     * <p><b>Post-Condition</b>: The collection is not empty.</p>
     * <p><b>Expected Results</b>: The collection contains the right elements during
     * execution. In particular:
     * <ul>
     * <li>{25:50} not contained (beginning).</li>
     * <li>{50:100} contained (middle).</li>
     * <li>{100:125} not contained (ending).</li>
     * </ul>
     */
    @Test
    public void Contains_50to100()
    {
        for (int i = 50; i < 100; i++)
            c.add(i);
        for (int i = 25; i < 50; i++)
            assertEquals("The collection should NOT include " + i, false, c.contains(i));
        for (int i = 50; i < 100; i++)
            assertEquals("The collection should include " + i, true, c.contains(i));
        for (int i = 100; i < 125; i++)
            assertEquals("The collection should NOT include " + i, false, c.contains(i));
    }

	// ------------------------------------------ equals method ------------------------------------------

    /**
     * <p><b>Summary</b>: equals method test case.</p>
     * <p><b>Test Case Design</b>: equals method is tested with an equal CollectionAdapter
     * and a bigger and a smaller one. The returned values should be true and false.
     * Also reflective property of equal is tested.</p>
     * <p><b>Test Description</b>: Collection is initialized, then different equals invoke are
     * asserted with different argumentc, generated for each case.</p>
     * <p><b>Pre-Condition</b>: Collection contains {0 : 1000}.</p>
     * <p><b>Post-Condition</b>: Collection is unchanged.</p>
     * <p><b>Expected Results</b>: The Collection is unchanged and symmetric property is valid.</p>
     */
    @Test
    public void Equals_0To1000()
    {
        int to = 1000;
        initHCollection(c, 0, to);
        assertEquals(true, c.equals(getIntegerHCollection(0, to)));
        assertEquals(true, c.equals(getIntegerHCollection(0, to)));   // Symmetric property
        assertEquals(false, c.equals(getIntegerHCollection(0, to + 15)));  // bigger Collection returns false
        assertEquals(false, c.equals(getIntegerHCollection(0, 5)));  // smaller Collection returns false
    }

    
    /**
     * <p><b>Summary</b>: equals method test case.
     * The test case the method behaviour with 2 empty collection.</p>
     * <p><b>Test Case Design</b>: When both  collections  are empty the equals
     * method should return true because an empty collection is equal to an
     * empty collection.</p>
     * <p><b>Test Description</b>: Single assert, l1.equals(l2) invoked.</p>
     * <p><b>Pre-Condition</b>: Both collection are empty.</p>
     * <p><b>Post-Condition</b>: Both collection are empty.</p>
     * <p><b>Expected Results</b>: equals returns true, as one
     * empty collection of course equals another empty collection.</p>
     */
    @Test
    public void Equals_Empty_True()
    {
        assertEquals("Two empty  collections  should equalc.", true, c.equals(c2));
        assertEquals("Two empty  collections  should equalc.", true, c2.equals(c));
    }

    /**
     * <p><b>Summary</b>: equals method test case.
     * The reflective property of equal method is tested.</p>
     * <p><b>Test Case Design</b>: equals method should be reflective,
     * therefore x.equals(x) should always return true.</p>
     * <p><b>Test Description</b>: The test invokes c.equals(c) when
     * c is empty, when it has 10 elements and when it has 1000 elements.</p>
     * <p><b>Pre-Condition</b>: collection is not null.</p>
     * <p><b>Post-Condition</b>: collection has 1000 elements. </p>
     * <p><b>Expected Results</b>: collection equals itself, therefore
     * reflective property is valid.</p>
     */
    @Test
    public void Equals_Reflective()
    {
        assertEquals("Reflective property is not met.", true, c.equals(c));    // collection is empty
        initHCollection(c, 0, 10);
        assertEquals("Reflective property is not met.", true, c.equals(c));    // collection is not empty, should return true anyways
        initHCollection(c, 0, 1000);
        assertEquals("Reflective property is not met.", true, c.equals(c));    // collection is not empty, should return true anyways
    }

    /**
     * <p><b>Summary</b>: equals method test case.
     * The transitive property of equal method is tested.</p>
     * <p><b>Test Case Design</b>: equals method should be transitive,
     * therefore a.equals(b) and b.equals(c) {@literal =>} a.equals(c).</p>
     * <p><b>Test Description</b>: The test invokes c.equals(s2) and s2.equals(s3)
     * and c.equals(s3)</p>
     * <p><b>Pre-Condition</b>:  collections  contain {1 : 1000}.</p>
     * <p><b>Post-Condition</b>:  collections  are unchanged. </p>
     * <p><b>Expected Results</b>: Equals has transitive property.</p>
     */
    @Test
    public void Equals_Transitive()
    {
        int to = 1000;
        initHCollection(c, 0, to);
        initHCollection(c2, 0, to);
        HCollection c3 = getIntegerHCollection(0, to);

        assertEquals(" collections  should be equal.", true, c.equals(c2));
        assertEquals(" collections  should be equal.", true, c2.equals(c3));
        assertEquals("Transitive property is not met.",true, c.equals(c3));
    }

	// ------------------------------------------ toArray method ------------------------------------------

	/**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of c is zero, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an empty
     * collection</p>
     * <p><b>Pre-Condition</b>: c is empty</p>
     * <p><b>Post-Condition</b>: c is empty</p>
     * <p><b>Expected Results</b>: c.toArray returns an empty
     * toArray.</p>
     */
    @Test
    public void ToArray_Empty()
    {
        assertArrayEquals(new Object[0], c.toArray());
    }

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of c is 1, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an collection
     * containing only one element, then c and the array returned
     * from toArray are checked through checkToArray.</p>
     * <p><b>Pre-Condition</b>: c contains 1 element</p>
     * <p><b>Post-Condition</b>: c is unchanged</p>
     * <p><b>Expected Results</b>: c.toArray returns an array
     * of lenght 1 containing only the element 1</p>
     */
    @Test
    public void ToArray_OneElement()
    {
        c.add(1);
        checkToArray(c, c.toArray());
    }

    /**
     * <p><b>Summary</b>: toArray and remove method test case.
     * Tests the behaviour of toArray method in different cases
     * and configurations of the collection.</p>
     * <p><b>Test Case Design</b>: Checks the array to be right
     * through checkToArray after each removals through ec.remove,
     * creating different situations and cases to test the method.</p>
     * <p><b>Test Description</b>: The test first removes the
     * even elements, then the odd elements. After each
     * removal the checkToArray method is invoked to check if the
     * generated array in all of this cases is right.</p>
     * <p><b>Pre-Condition</b>: c contains {0:100}.</p>
     * <p><b>Post-Condition</b>: c is empty</p>
     * <p><b>Expected Results</b>: After each removal the generated array
     * through c.toArray is right and coherent. At the end c is empty.</p>
     */
    @Test
    public void ToArray_0To100()
    {
        int bound = 100;
		initHCollection(c, 0, 100);
        for (int i = 0; i < bound; i += 2)
        {
            c.remove(i);
            checkToArray(c, c.toArray());
        }
        for (int i = 1; i < bound; i += 2)
        {
            c.remove(i);
            checkToArray(c, c.toArray());
        }
        assertEquals("Should be empty", 0, c.size());
    }

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of c is zero, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an empty
     * collection</p>
     * <p><b>Pre-Condition</b>: c is empty</p>
     * <p><b>Post-Condition</b>: c isempty</p>
     * <p><b>Expected Results</b>: c.toArray returns an empty
     * toArray.</p>
     */
    @Test
    public void ToArrayArrayArg_Empty()
    {
        Object[] a = new Object[0];
        c.toArray(a);
        assertArrayEquals(new Object[0], a);
    }

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of c is 1, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an collection
     * containing only one element, then c and the array modified
     * from toArray are checked through checkToArray.</p>
     * <p><b>Pre-Condition</b>: c contains 1 element</p>
     * <p><b>Post-Condition</b>: c is unchanged</p>
     * <p><b>Expected Results</b>: c.toArray returns an array
     * of lenght 1 containing only the element 1</p>
     */
    @Test
    public void ToArrayArrayArg_OneElement()
    {
        Object[] a = new Object[1];
        c.add(1);
        c.toArray(a);
        checkToArray(c, a);
    }

    /**
     * <p><b>Summary</b>: toArray and remove method test case.
     * Tests the behaviour of toArray method in different cases
     * and configurations of the collection.</p>
     * <p><b>Test Case Design</b>: Checks the array to be right
     * through checkToArray after each removals through ec.remove,
     * creating different situations and cases to test the method.</p>
     * <p><b>Test Description</b>: The test first removes the
     * even elements, then the odd elements. After each
     * removal the checkToArray method is invoked to check if the
     * modified array in all of this cases is right.</p>
     * <p><b>Pre-Condition</b>: c contains {0:100}.</p>
     * <p><b>Post-Condition</b>: c is empty</p>
     * <p><b>Expected Results</b>: After each removal the modified array
     * through c.toArray is right and coherent. At the end c is empty.</p>
     */
    @Test
    public void ToArrayArrayArg_0To100()
    {
        int bound = 100;
        
        initHCollection(c, 0, 100);
        for (int i = 0; i < bound; i += 2)
        {
            c.remove(i);
            Object[] a = new Object[c.size()];
            c.toArray(a);
            checkToArray(c, a);
        }
        for (int i = 1; i < bound; i += 2)
        {
            c.remove(i);
            Object[] a = new Object[c.size()];
            c.toArray(a);
            checkToArray(c, a);
        }
        assertEquals("Should be empty", 0, c.size());
    }

	// ------------------------------------------ remove method ------------------------------------------

    /**
     * <p><b>Summary</b>: remove method test case.
     * Removing from an empty collection should always return false.</p>
     * <p><b>Test Case Design</b>: Test the collection behaviour removing when it is empty, which
     * is a limit case of the method. After the method invoke the collection remains empty
     * and the method returnc,
     * as removing from an empty collection does not modify it.</p>
     * <p><b>Test Description</b>: Tries to remove the object 345 from the empty collection.
     * Note that without the explicit cast it would have thrown IndexOutOfBoundsException.</p>
     * <p><b>Pre-Condition</b>: collection is empty.</p>
     * <p><b>Post-Condition</b>: collectioni is still empty.</p>
     * <p><b>Expected Results</b>: remove method returns false as the collection was not modified.</p>
     */
    @Test
    public void Remove_Empty()
    {
        assertEquals("collection is empty therefore remove method must return false.", false, c.remove((Object)345));
    }

    /**
     * <p><b>Summary</b>: remove method test case.
     * Test tries to remove the 7 object from a collection containing numbers
     * from 0 (included) to 6 (excluded), therefore it return false.</p>
     * <p><b>Test Case Design</b>: Method tries to remove a non present
     * element, which is a special case. </p>
     * <p><b>Test Description</b>: The test asserts that remove method returns false.
     * In fact, it contains numbers
     * from 0 (included) to 6 (excluded), so removing 7 does not change the collection and returns false.
     * Note that without the explicit cast it would have thrown IndexOutOfBoundsException.</p>
     * <p><b>Pre-Condition</b>: The collection is empty.</p>
     * <p><b>Post-Condition</b>: The collection is not empty and removing 7 does not modify the collection.</p>
     * <p><b>Expected Results</b>: Method returns false as 7 is not contained in the collection,
     * therefore the collection is not modified because of the remove method invoke.</p>
     */
    @Test
    public void Remove_NotInSet()
    {
        initHCollection(c, 0, 6);
        assertEquals("Method should return false because it does not contains 7.", false, c.remove(7));
    }

	/**
     * <p><b>Summary</b>: remove method test case.
	 * Tests remove behaviour with a variable collection's size.</p>
     * <p><b>Test Case Design</b>: Set's size is changing due
	 * to remove method, therefore remove is tested with different
	 * size of collection, including the size 1, which is a limit case.</p>
     * <p><b>Test Description</b>: At each iteration the element
	 * i (i in (0:100)) is removed from the collection. At the end of the for loop
	 * the collection is empty.</p>
     * <p><b>Pre-Condition</b>: collection contains {0:100}.</p>
     * <p><b>Post-Condition</b>: collection is empty.</p>
     * <p><b>Expected Results</b>: collection has been emptied by the
	 * remove method through the iteration. Each iteration is asserted
	 * to be true, as the collection was modified due to remove. Size is decremented
	 * by one after each remove. collection is asserted to be equal to a collection
	 * containing elements from i to bound.</p>
     */

	@Test
	public void Remove_0To100()
	{
		int bound = 100;
		initHCollection(c, 0, bound);
		for (int i = 0; i < bound; i++)
		{
			assertTrue(c.remove(i));
			assertEquals("Size should be decremented", bound - i - 1,c.size());
			assertEquals("collections  should be equal", c, getIntegerHCollection(i + 1, bound));
		}
		assertTrue("Should be empty", c.isEmpty() && c.size() == 0);
	}

	/**
     * <p><b>Summary</b>: remove and add method test case.
     * The collection starts with 2 element, and after repeated
     * adds and removes it has still 2 element.</p>
     * <p><b>Test Case Design</b>: Repeated adds and removes of the
     * same element should keep the collection invariant, which is a way to
     * stress the collection structure.</p>
     * <p><b>Test Description</b>: The test starts with 2 elements.
     * Then the element 1 is added and removed 100 timec.
     * At the end the collection must have still 2 element, and they should
     * be the same one.</p>
     * <p><b>Pre-Condition</b>: The collection contains 0.</p>
     * <p><b>Post-Condition</b>: The collection is invariated.</p>
     * <p><b>Expected Results</b>: The only element is 0,
	 * checked through c.iterator().next()</p>
     */
    @Test
    public void RemoveAdd_ObjArg_Mixed1()
    {
        c.add(0);
        c.add(0);
        for (int i = 0; i < 100; i++)
        {
            c.add(1);
            c.remove(1);
        }
        assertEquals("After these operation the collection must have 1 element.", 2, c.size());
        assertEquals("The first element should be 0.", 0, c.iterator().next());
    }

    // ------------------------------------------ containsAll method ------------------------------------------


    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The method tests if an empty collection contains the elements
     * of another collection, which is obviusly false.</p>
     * <p><b>Test Case Design</b>: The test case tests the limit case of
     * checking an empty collection containing something.</p>
     * <p><b>Test Description</b>: The collection c2 contains 1, 2 and 3.
     * The containsAll method obviously should return false for
     * any coll's content as the collection is empty.</p>
     * <p><b>Pre-Condition</b>: The collection is empty.</p>
     * <p><b>Post-Condition</b>: The collection is still empty.</p>
     * <p><b>Expected Results</b>: The containsAll method return false.</p>
     */
    @Test
    public void ContainsAll_Empty()
    {
        initHCollection(c2, 1, 4);
        assertEquals("The method should return false because the collection is empty.", false, c.containsAll(c2)); 
    }

   /**
     * <p><b>Summary</b>: containsAll method test case.
     * The method tests if an empty collection contains the elements
     * of another collection.</p>
     * <p><b>Test Case Design</b>: The test case tests the limit case of
     * checking an empty collection containing an empty collection, which is true, 
     * as the empty subCollection is the subCollection of every collection, therefore even of the
     * empty collection. The tested case is a limit case of containsAll.</p>
     * <p><b>Test Description</b>: The collection c2 is empty.
     * The containsAll method obviously should return true for
     * any c's content, because the empty subCollection is the
     * subCollection of every collection.</p>
     * <p><b>Pre-Condition</b>: The collection is empty.</p>
     * <p><b>Post-Condition</b>: The collection is empty.</p>
     * <p><b>Expected Results</b>: The containsAll method return true.</p>
     */
    @Test
    public void ContainsAll_BothEmpty()
    {
        assertEquals("The method should return true because the collection is empty.", true, c.containsAll(c2)); 
    }
    
    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The test case tests different containsAll calls
     * with different collection.</p>
     * <p><b>Test Case Design</b>: The tests calls containsAll
     * with the first, the last, the middle, and a collection of
     * middle elements as argument.</p>
     * <p><b>Test Description</b>: In the test containsAll is called with the
     * collection containing
     * {1}, {10}, {3, 4, 5}, {1, 5, 10}.</p>
     * <p><b>Pre-Condition</b>: The collection contains {0:10}.</p>
     * <p><b>Post-Condition</b>: The collection contains {0:10}.</p>
     * <p><b>Expected Results</b>: Every containsAll calls return true.</p>
     */
    @Test
    public void ContainsAll_0to11()
    {
        initHCollection(c, 0, 11);

        // {1}
        c.add(1);
        assertEquals("The collection contains 1.", true, c.containsAll(c2));
        
        // {10}
        c = new CollectionAdapter();
        c.add(10);
        assertEquals("The collection contains 10.", true, c.containsAll(c2));

        // {3, 4, 5}
        c = new CollectionAdapter();
        initHCollection(c, 3, 6);
        assertEquals("The collection contains 3, 4 and 5.", true, c.containsAll(c2));

        // {1, 5, 10}
        c = new CollectionAdapter();
        c.add(1);
        c.add(5);
        c.add(10);
        assertEquals("The collection contains 1, 5 and 10.", true, c.containsAll(c));
    }

    /**
     * <p><b>Summary</b>: containsAll method test case.
     * Tests if different combination are contained in the collection.
     * Each containsAll call returns false.</p>
     * <p><b>Test Case Design</b>: Different scenario are tested where
     * containsAll should return false. Calling containsAll with arguments
     * not present in the collection is a common case for the method.</p>
     * <p><b>Test Description</b>: The first containsAll takes as argument
     * a collection c2 containing a single element, not present in the
     * collection. The second one takes as argument a collection containing element
     * in the collection e one not contained in the collection: therefore, the call
     * must return false as one of the collection is not contained.</p>
     * <p><b>Pre-Condition</b>: The collection contains {0:11}.</p>
     * <p><b>Post-Condition</b>: The collection contains {0:11}.</p>
     * <p><b>Expected Results</b>: All containsAll calls return true.</p>
     */
    @Test
    public void ContainsAll_0To11()
    {
        initHCollection(c, 0, 11);

        c2.add(11);
        assertEquals("11 is not contained.", false, c.containsAll(c2));

        c2 = new CollectionAdapter();
        c2.add(3);
        c2.add(4);
        c2.add(5);
        c2.add(12);   // Single element not present
        assertEquals("3, 4 and 5 are contained. 12 is not contained.", false, c.containsAll(c2));
    }

    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The test case tries to call containsAll with a null
     * collection on an empty collection.</p>
     * <p><b>Test Case Design</b>: Tests the method with null argumentc, which is a special
     * case of invalid argument.</p>
     * <p><b>Test Description</b>: Calls method with null collection.</p>
     * <p><b>Pre-Condition</b>: collection is empty, the argument is null.</p>
     * <p><b>Post-Condition</b>: collection is empty, the argument is null.</p>
     * <p><b>Expected Results</b>: NullPointerException thrown.</p>
     */    
    @Test(expected = java.lang.NullPointerException.class)
    public void ContainsAll_NullCollection_NPE()
    {
        c.containsAll(null);
    }

    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The test case tries to call containsAll with a null
     * collection on a non empty collection.</p>
     * <p><b>Test Case Design</b>: Tests the method with null argumentc, which is a special
     * case of invalid argument.</p>
     * <p><b>Test Description</b>: Calls method with null collection.</p>
     * <p><b>Pre-Condition</b>: collection is not empty, the argument is null.</p>
     * <p><b>Post-Condition</b>: collection is not empty, the argument is null.</p>
     * <p><b>Expected Results</b>: NullPointerException thrown.</p>
     */    
    @Test(expected = java.lang.NullPointerException.class)
    public void ContainsAll_NullCollectionNotEmptyCollection_NPE()
    {
        c.add(1);
        c.add(2);
        c.containsAll(null);
    }

    // ------------------------------------------ addAll method ------------------------------------------

    /**
     * <p><b>Summary</b>: addAll method test case.
     * While adding a null object is ok, calling addAll
     * method, which takes a HCollection argument, throws an exception.</p>
     * <p><b>Test Case Design</b>: Tests the case with null
     * argument passed, which is a special case of invalid argument.</p>
     * <p><b>Test Description</b>: Calls addAll with null argument, therefore it
     * throws NullPointerException.</p>
     * <p><b>Pre-Condition</b>: collection is empty.</p>
     * <p><b>Post-Condition</b>: collection is empty.</p>
     * <p><b>Expected Results</b>: NullPointerException thrown.</p>
     */
    @Test(expected = java.lang.NullPointerException.class)
    public void AddAll_NullCollection_NPE()
    {
        c.addAll(null);
    }

    /**
     * <p><b>Summary</b>: addAll method test case.</p>
     * <p><b>Test Case Design</b>: Tests the case of empty collection being
     * passed as argument, which is a limit case.</p>
     * <p><b>Test Description</b>: The test cases calls addAll as empty
     * collection as argument. Adding an empty collection means that no element
     * is added, therefore addAll returns false as the collection is unchanged.</p>
     * <p><b>Pre-Condition</b>: collection is empty, c is empty.</p>
     * <p><b>Post-Condition</b>: collection is empty, c is empty.</p>
     * <p><b>Expected Results</b>: addAll returns false because no
     * changes are made. collection is still empty.</p>
     */
    @Test
    public void AddAll_EmptyCollection_False()
    {
        assertEquals("collection did not change, so the method should return false.", false, c.addAll(c2));
        assertEquals("The collection should be empty.", true, c.isEmpty());
    }

    /**
     * <p><b>Summary</b>: addAll method test case.
     * The test adds a collection to the collection,
     * then checks if the elements were stored correctly.</p>
     * <p><b>Test Case Design</b>: addAll must behave correctly
     * adding a collection of 3 elements, which is a common case for
     * the addAll method.</p>
     * <p><b>Test Description</b>: 3 elements are added and then the test
     * checks the contained element and its size.</p>
     * <p><b>Pre-Condition</b>: collection is empty, c2 has {1, 2, 3}.</p>
     * <p><b>Post-Condition</b>: collection and c2 both contain {1, 2, 3}. In particular,
     * c2 is unchanged.</p>
     * <p><b>Expected Results</b>: collection contains the added elements {1, 2, 3},
	 * therefore containsAll returns true and its size becomes 3.</p>
     */
    @Test
    public void AddAll_FromEmptyTo123()
    {
        initHCollection(c2, 1, 4);

        assertTrue(c.addAll(c2));
		assertTrue(c.containsAll(c2));
        assertEquals("collection size should be 3.", 3, c.size());
    }

    /**
     * <p><b>Summary</b>: addAll method test case.
     * The collection initially contains {1, 2, 3}, then through
     * remove and addAll it finally contains numbers from 1
     * to 5 included.</p>
     * <p><b>Test Case Design</b>: It tests how the collection behaves with
     * removals and then with adding a collection c2.</p>
     * <p><b>Test Description</b>: The test removes the 3 number, then it
     * adds through addAll method the elements {3, 4, 5}.</p>
     * <p><b>Pre-Condition</b>: collection contains {1, 2, 3}.</p>
     * <p><b>Post-Condition</b>: collection contains {1:6}.</p>
     * <p><b>Expected Results</b>: collection contains the added elements {1:6},
	 * therefore c.equals(getEntryHCollection(1, 6)) returns true.</p>
     */
    @Test
    public void AddAll_From123to12345()
    {
        initHCollection(c, 1, 3);
        c.remove(3);
        initHCollection(c2, 3, 6);
        assertTrue("addAll should return true as c has been changed.", c.addAll(c2));
        assertTrue("The collection should be equal.", c.equals(getIntegerHCollection(1, 6)));
    }

    /**
     * <p><b>Summary</b>: addAll method test case.
     * The method thest the insertion of a large number
     * of elements through addAll.</p>
     * <p><b>Test Case Design</b>: The test considers the case
     * scenario of large input. From the Sommerville: "Force computation results to be
     * too large or too small."</p>
     * <p><b>Test Description</b>: Numbers from 1 to 100 are inserted in c2, then addAll
     * method is invoked with c2 as argument.</p>
     * <p><b>Pre-Condition</b>: The collection is empty, c contains aforementioed numberc.</p>
     * <p><b>Post-Condition</b>: The collection contains number from 1 to 100.</p>
     * <p><b>Expected Results</b>: The collection contains all the element
	 * inserted through addAll, checked through containsAll method. collection size is 100.</p>
     */
    @Test
    public void AddAll_0To100()
    {
        initHCollection(c2, 0, 100);
        assertTrue("The collection did change.", c.addAll(c2));
		assertTrue("collection should contain all", c.containsAll(c2));
		assertEquals("collection size should be 100.", 100, c.size());

    }

	// ------------------------------------------ removeAll method ------------------------------------------
    
    /**
     * <p><b>Summary</b>: removeAll method test case.</p>
     * <p><b>Test Case Design</b>: removeAll method called with
     * empty collection as an argument, which is a method's
     * limit case.</p>
     * <p><b>Test Description</b>: The test adds 3 elements to the collection
     * and then calls removeAll with an empty collection. Therefore
     * the collection should be unchanged.</p>
     * <p><b>Pre-Condition</b>: The collection contains {1, 2, 3}.</p>
     * <p><b>Post-Condition</b>: The collection is unchaged.</p>
     * <p><b>Expected Results</b>: The collection is unchanged, therefore removeAll
     * returns false, c2 is unchanged.</p>
     */
    @Test
    public void RemoveAll_EmptyColl_False()
    {
        initHCollection(c, 1, 4);
        assertFalse("removeAll did not remove anything, therefore it should return false.", c.removeAll(c2));
        assertEquals(" collections  should be equal.", true, c.equals(getIntegerHCollection(1, 4)));
        assertEquals("Wrong collection size.", 3, c.size());
        assertEquals("coll should be empty.", 0, c2.size());
    }

    /**
     * <p><b>Summary</b>: removeAll method test case.
     * The method is called with the limit case of an
     * calling empty collection.</p>
     * <p><b>Test Case Design</b>: removeAll method is called by an
     * empty collection, which is a limit case. The collection should be unchanged, so the method
     * should return false.</p>
     * <p><b>Test Description</b>: removeAll is called by an empty collection.</p>
     * <p><b>Pre-Condition</b>: The collection is empty.</p>
     * <p><b>Post-Condition</b>: The collection is still empty.</p>
     * <p><b>Expected Results</b>: The collection is unchanged, therefore removeAll
     * returns false, its size is zero.</p>
     */
    @Test
    public void RemoveAll_EmptyCollection_False()
    {
        assertEquals("removeAll did not remove anything, therefore it should return false.", false, c.removeAll(c2));
        assertEquals("collection should be empty.", 0, c.size());
        assertEquals("coll should be empty.", 0, c.size());
    }

    /**
     * <p><b>Summary</b>: removeAll method test case.
     * Method is tested when the collection is empty and the
     * collection is not. Because the already empty collection
     * is unchanged the method should return false.</p>
     * <p><b>Test Case Design</b>: c2 is not empty and removeAll is called
     * with c2 as an argument, which is a limit case.</p>
     * <p><b>Test Description</b>: The test calls removeAll
     * with collection being empty, c2 being not (c2 contains {1, 2, 3}).</p>
     * <p><b>Pre-Condition</b>: collection is empty, c2 contains {1, 2, 3}.</p>
     * <p><b>Post-Condition</b>: collection is still empty, c2 contains {1, 2, 3}.</p>
     * <p><b>Expected Results</b>: collection is empty, c2 is unchanged.</p>
     */
    @Test
    public void RemoveAll_EmptyCollectionNoEmptyColl_False()
    {
        initHCollection(c2, 1, 4);

        assertEquals(false, c.removeAll(c2));
        assertEquals("collection should be empty.", 0, c.size());
        assertEquals("coll' size should be 3.", 3, c2.size());
        assertEquals("coll should contains {1, 2, 3}.", true, c2.equals(getIntegerHCollection(1, 4)));
    }

    /**
     * <p><b>Summary</b>: removeAll method test case.</p>
     * <p><b>Test Case Design</b>: Removes middle elements from
     * the collection. This kind of removal is a common case for the
     * removeAll method.</p>
     * <p><b>Test Description</b>: collection contains {1, ..., 10} and
     * 3, 4, 5, are removed through removeAll method.</p>
     * <p><b>Pre-Condition</b>: collection contains aforementioned numbers.</p>
     * <p><b>Post-Condition</b>: 3, 4, 5 are removed. c2 is unchanged.</p>
     * <p><b>Expected Results</b>: 3, 4, 5 are removed, collection has been changed,
     * therefore removeAll returns true.
     * c2 is unchanged.</p>
     */
    @Test
    public void RemoveAll_From1To10Remove345()
    {
        initHCollection(c, 1, 10);
        // To be removed
        initHCollection(c2, 3, 6);

        assertTrue("The collection has changed, it should return true.", c.removeAll(c2));
		assertTrue(c.contains(1) && c.contains(2) && c.contains(6) && c.contains(7) && c.contains(8) && c.contains(9));
        assertEquals(6, c.size());
        assertTrue(c2.contains(3) && c2.contains(4) && c2.contains(5));
    }

    /**
     * <p><b>Summary</b>: removeAll method test case.</p>
     * <p><b>Test Case Design</b>: Removes middle elements from
     * the collection. Those elements are multiple in collection. Removing middle
     * and duplicated elements is a common case for removeAll method.</p>
     * <p><b>Test Description</b>: collection contains {1, ..., 10,3, 4, 5, 3, 5} and
     * 3, 4, 5, are removed through removeAll method.</p>
     * <p><b>Pre-Condition</b>: collection contains aforementioned numberc.</p>
     * <p><b>Post-Condition</b>: 3, 4, 5 are removed. c2 is unchanged.</p>
     * <p><b>Expected Results</b>: 3, 4, 5 are removed, collection has been changed,
     * therefore removeAll returns true.
     * c is unchanged.</p>
     */
    @Test
    public void RemoveAll_From1To10DuplicatesRemove345()
    {
        initHCollection(c, 1, 10);
        // Should all be removed by removeAll
        c.add(3);
        c.add(4);
        c.add(5);
        c.add(3);
        c.add(5);

        // To be removed
        c2.add(3);
        c2.add(4);
        c2.add(5);

        assertTrue("The collection has changed, it should return true.", c. removeAll(c2));
		assertTrue(c.contains(1) && c.contains(2) && c.contains(6) && c.contains(7) && c.contains(8) && c.contains(9));
        assertEquals(6, c.size());
        assertTrue(c2.contains(3) && c2.contains(4) && c2.contains(5));
    }

    /**
     * <p><b>Summary</b>: removeAll and remove method test case.</p>
     * <p><b>Test Case Design</b>: Various add, remove and removeAll
     * interchanged and repeated to stress the collection behaviour.</p>
     * <p><b>Test Description</b>: First collection contains {1, 2, 3, 3}.
     * Then element 3 is removed via remove. The removeAll
	 * does remove any 3. Then 10 threes are added to the collection. {4, 5} being
     * added. removeAll is called with c2 as an argument.</p>
     * <p><b>Pre-Condition</b>: collection contains {1, 2, 3, 3}.</p>
     * <p><b>Post-Condition</b>: collection contains {1, 2, 4}.</p>
     * <p><b>Expected Results</b>: c.contains(1) && c.contains(2) && c.contains(4) && c.size() == 3
	 * returns true, therefore c contains 1, 2, 4 and its size is 3.</p>
     */
    @Test
    public void RemoveAll_Duplicates3_Mixed()
    {
        c.add(1);
        c.add(2);
        c.add(3);
        c.add(3);
        c.remove((Object)3);
        c2.add(3);
        assertTrue("3 should be removed.", c. removeAll(c2));
        assertFalse("3 not present in the collection.", c. removeAll(c2));

        for (int i = 0; i < 10; i++)
            c.add(3);

        c.add(4);
        c.add(5);

        c2.add(5);
        assertEquals(true, c.removeAll(c2));
		assertTrue(c.contains(1) && c.contains(2) && c.contains(4) && c.size() == 3);
    }

    // ------------------------------------------ retainAll method ------------------------------------------

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * retainAll is being called with an empty collection as an argument,
     * therefore the collection should became the empty collection, since mainteining
     * only the "empty" subCollection means deleting all the elements.</p>
     * <p><b>Test Case Design</b>: retainAll being called with the limit case of
     * an empty collection c2 as an argument.</p>
     * <p><b>Test Description</b>: The collection removes all but "empty", so
     * it empties. In fact initially it contains {1, 2, 3}</p>
     * <p><b>Pre-Condition</b>: The collection contains {1, 2, 3}.</p>
     * <p><b>Post-Condition</b>: The collection is empty.</p>
     * <p><b>Expected Results</b>: The collection is empty, retainAll returns true
     * because the collection has changed.</p>
     */
    @Test
    public void RetainAll_Empty_True()
    {
        initHCollection(c, 1, 4);
        assertEquals("The collection has changed, it should return true.", true, c.retainAll(c2));
        assertEquals("collection should be empty.", 0, c.size());
        assertEquals("coll should be empty.", 0, c.size());
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * retainAll is being called with an empty collection as an argument and
     * an empty collection as object,
     * therefore the collection should remain the empty collection, since mainteining
     * only the "empty" subCollection means deleting all the elements. Unlike the
     * RetainAll_Empty_True test case, the collection is already empty, therefore
     * the method returns false.</p>
     * <p><b>Test Case Design</b>: retainAll being called with the limit case of
     * an empty collection as an argument and an empty collection.</p>
     * <p><b>Test Description</b>: The collection removes all but "empty", so
     * it empties.</p>
     * <p><b>Pre-Condition</b>: The collection is empty.</p>
     * <p><b>Post-Condition</b>: The collection is still empty.</p>
     * <p><b>Expected Results</b>: retainAll returns false because the collection is unchanged.</p>
     */
    @Test
    public void RetainAll_Empty_False()
    {
        assertEquals("The collection has not changed, it should return false.", false, c.retainAll(c2));
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * The test calls retainAll with a collection containing
     * few elements.</p>
     * <p><b>Test Case Design</b>: The retainAll method is tested with small
     * input. Testing a typical case.</p>
     * <p><b>Test Description</b>: The collection initially contains numbers
     * from 1 to 5 included. retainAll is called with a collection
     * containing {3, 4, 5}, therefore c should contain
     * {3, 4, 5}.</p>
     * <p><b>Pre-Condition</b>: The collection contains {1, ..., 5}, c2 contains
     * {3, 4, 5}.</p>
     * <p><b>Post-Condition</b>: The collection contains {3, 4, 5}, c2 contains
     * {3, 4, 5}.</p>
     * <p><b>Expected Results</b>: collection contains {3, 4, 5}, collection has changed. retainAll returns true
     * because the collection has changed.</p>
     */
    @Test
    public void RetainAll_12345()
    {
        initHCollection(c, 1, 6);
        initHCollection(c2, 3, 6);
        assertEquals("The collection has changed, it should return true.", true, c.retainAll(c2));
        assertTrue("collection should contain {3, 4, 5}.", c.equals(getIntegerHCollection(3, 6)));
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * The test calls retainAll with a collection containing
     * few elements. Testing a typical case.</p>
     * <p><b>Test Case Design</b>: The retainAll method is tested with small
     * input.</p>
     * <p><b>Test Description</b>: The collection initially contains numbers
     * from 1 to 9 included. retainAll is called with a collection
     * containing {2, 3}, therefore the collection should contain
     * {2, 3}.</p>
     * <p><b>Pre-Condition</b>: The collection contains {1, ..., 9}, c2 contains
     * {2, 3}.</p>
     * <p><b>Post-Condition</b>: The collection contains {2, 3}, c2 contains
     * {2, 3}.</p>
     * <p><b>Expected Results</b>: collection contains {2, 3}, collection has changed.  retainAll returns true
     * because the collection has changed.</p>
     */
    @Test
    public void RetainAll_23()
    {
        initHCollection(c, 1, 10);
        c2.add(2);
        c2.add(3);
        assertTrue("The collection has changed, it should return true.", c.retainAll(c2));
        assertTrue("collection should contain {2, 3}.", c.equals(getIntegerHCollection(2, 4)));
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * The test calls retainAll with a collection containing
     * many elements. Testing a typical case with a large input.</p>
     * <p><b>Test Case Design</b>:  The retainAll method is tested with large
     * input. The case is still a common case.</p>
     * <p><b>Test Description</b>: The collection initially contains numbers
     * from 1 to 999 included. retainAll is called with a collection c2
     * containing {300, ..., 599}, therefore the collection should contain
     * {300, 599}.</p>
     * <p><b>Pre-Condition</b>: The collection contains {1, ..., 999}, c2 contains
     * {300, ..., 599}.</p>
     * <p><b>Post-Condition</b>: The collection contains {300, ..., 599}, c2 contains
     * {300, ..., 599}.</p>
     * <p><b>Expected Results</b>: The elements are equal, therefore collection contains {300:600}. retainAll returns true
     * as the collection is being modified.</p>
     */
    @Test
    public void RetainAll_1000()
    {
        initHCollection(c, 1, 1000);
        initHCollection(c2, 300, 600);
        c.retainAll(c2);
        assertEquals("The elements should match.", c, getIntegerHCollection(300, 600));
    }

	    /**
     * <p><b>Summary</b>: retainAll method test case.
     * retainAll is being called with collection containing element
     * not present in the collection as an argument,
     * therefore the collection should became the empty collection, since mainteining
     * only a subCollection not contained in the collection means deleting all the elements.</p>
     * <p><b>Test Case Design</b>: retainAll being called with the limit case of
     * an empty intersection of collection and c.</p>
     * <p><b>Test Description</b>: The collection removes all but "empty", so
     * it empties. In fact initially it contains {1:20}</p>
     * <p><b>Pre-Condition</b>: The collection contains {1:20}.</p>
     * <p><b>Post-Condition</b>: The collection is empty.</p>
     * <p><b>Expected Results</b>: The collection is empty, retainAll returns true
     * because the collection changes</p>
     */
    @Test
    public void RetainAll_ToEmpty()
    {
        initHCollection(c, 1, 20);
        initHCollection(c2, 20, 24);

        assertEquals("The collection has changed, it should return true.", true, c.retainAll(c2));
        assertEquals("The collection should be empty.", 0, c.size());
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * The method is being tested with a collection containing
     * duplicated elements. This should not change the method
     * behaviour as the absence of one element in c2 removes
     * all its occurencies in the collection.</p>
     * <p><b>Test Case Design</b>: c2 can contain duplicated element, and
     * this should not change retainAll behaviour. At the end of
     * retainAll execution every element not contained in coll
     * must be removed.</p>
     * <p><b>Test Description</b>: collection contains {1:20}. c2 contains
     * {4, 4, 5, 5, 6, 6}. retainAll is called, so the collection should contain
     * {4, 5, 6}.</p>
     * <p><b>Pre-Condition</b>: collection contains {1:20}. c2 contains
     * {4, 4, 5, 5, 6, 6}.</p>
     * <p><b>Post-Condition</b>: collection contains {4, 5, 6}. c2 contains
     * {4, 4, 5, 5, 6, 6}.</p>
     * <p><b>Expected Results</b>: The collections equals, therefore the
     * collection contains {4, 5, 6}. retainAll returns true
     * as the collection is being modified.</p>
     */
    @Test
    public void RetainAll_DuplicatesColl()
    {
        initHCollection(c, 0, 20);
        for (int i = 4; i < 7; i++)
        {
            c2.add(i);
            c2.add(i);
        }
        assertEquals("The collection has changed, it should return true.", true, c.retainAll(c2));
        assertEquals("The elements should match.", c, getIntegerHCollection(4, 7));
    }

    private void checkToArray(HCollection v, Object[] arr)
    {
        assertEquals("The array and the collection do NOT have the same elements.", v.size(), arr.length);
        for (int i = 0; i < arr.length; i++)
            assertTrue("The array and the collection do NOT have the same elements.", v.contains(arr[i]));
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
