package myTest;

import static org.junit.Assert.*;
import static myTest.TestUtilities.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import myAdapter.*;

public class TestSet
{
	HSet s = null, s2 = null;
	HCollection c = null;
	HIterator iter2 = null;
	String[] argv = {"pippo", "pippo", "pluto", "paperino", "ciccio", "qui"};

	@BeforeClass
	public static void BeforeClassMethod()
	{
		System.out.println("Test Set");
	}

	@Before
	public void BeforeMethod()
	{
		s = new SetAdapter();
		s2 = new SetAdapter();
		c = new CollectionAdapter();
	}

	@After
	public void AfterMethod()
	{
		s = null;
		c = null;
	}

	// ------------------------------------------ Test cases assigned from the professor ------------------------------------------

	/**
     * <p><b>Summary</b>: Tests the behaviour of SetAdapter
	 * when inserting clones. size, add and itertator of SetAdapter
	 * are tested.</p>
     * <p><b>Test Case Design</b>: HSet interface states that
	 * it should not contain clones, therefore inserting an
	 * already present element should just ignore the insertion
	 * and return false as the set has not changed. In the test
	 * elements are added when clones or not.</p>
     * <p><b>Test Description</b>: iterates through the set.
	 * pippo is added, tries to add pippo once again
	 * but it is already present. Adds the remaining elements, asserts
	 * size to be i and iterate for each remaining element.</p>
     * <p><b>Pre-Condition</b>: Set is empty.</p>
     * <p><b>Post-Condition</b>: Set contains argv elements.</p>
     * <p><b>Expected Results</b>: First pippo insertion returns true,
	 * second returns false. Remaining insertion return true, size is
	 * i at each iteration. In the end argv lenght is 1 more than s size.</p>
     */
	@Test
	public void SetClonesAcceptanceTest()
	{
		System.out.print(s.size() + " ");
		assertEquals(0, s.size());
		iterate(s.iterator());

		assertTrue(s.add(argv[0]));
		assertFalse(s.add(argv[1]));

		for(int i=2;i<argv.length;i++)
		{
			boolean res = s.add(argv[i]);
			System.out.println(argv[i] + " " + res);
			System.out.print(s.size() + " ");

			assertEquals(true, res);
			assertEquals(i, s.size());
			iterate(s.iterator());
		}
		assertEquals("\n*** set wronlgy accepts clones ***\n", argv.length - 1, s.size());
	}

    /**
     * <p><b>Summary</b>: Tests the content of the set, which should contain
	 * the elements in argv, [pluto, ciccio, qui, paperino, pippo].</p>
     * <p><b>Test Case Design</b>: Tests the content of the set. As the element
	 * in a set are not ordered, unlike in Hset, printing
	 * a string representation of the object to test its content
	 * is not reliable, therefore it is better looping through the elements in
	 * some way to test their presence in the set.</p>
     * <p><b>Test Description</b>: Removes the element in argv one by one.
	 * If they are the same elements, at each iteration the element must be
	 * in the set and its size should be  greater than zero (it should not
	 * be empty). If the for loop finish and the final
	 * size is zero, the elements are the same and the
	 * sets contains the right elements.
	 * The sets is then restored.</p>
     * <p><b>Pre-Condition</b>: s contains argv elements.</p>
     * <p><b>Post-Condition</b>: s in unchanged.</p>
     * <p><b>Expected Results</b>: At each iteration isEmpty returns false
	 * and contains returns true, therefore argv and the sets contains
	 * the same elements.</p>
     */
	@Test
	public void SetCheckContentTest()
	{
		argvInitialize(s);
		System.out.println("Set.toString()? " + s);
		for (int i = 1; i < argv.length; i++)
		{
			assertFalse("Not same elements.", s.isEmpty());
			assertTrue("Should be contained", s.contains(argv[i]));
			s.remove(argv[i]);
		}
		assertTrue("Not all elements removed", s.isEmpty());
		argvInitialize(s);
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
	public void SetIteratorRemovalTest()
	{
		argvInitialize(s);
		
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
     * <p><b>Summary</b>: Tests the behaviour of CollectionAdapter
	 * when inserting clones. size, add and itertator of SetAdapter
	 * are tested.</p>
     * <p><b>Test Case Design</b>: HCollection interface does not
	 * specify clones presence. In a collection clones are accepted. In the test
	 * elements are added when clones or not.</p>
     * <p><b>Test Description</b>: iterates through the set.
	 * pippo is added, tries to add pippo once again
	 * even if it is already present. Adds the remaining elements, asserts
	 * size to be i and iterate for each remaining element.</p>
     * <p><b>Pre-Condition</b>: Set is empty.</p>
     * <p><b>Post-Condition</b>: Set contains argv elements.</p>
     * <p><b>Expected Results</b>: First pippo insertion returns true,
	 * second returns true too. Remaining insertion return true, size is
	 * i at each iteration. In the end argv lenght is not 1 more than s size.</p>
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
     * <p><b>Summary</b>: Tests the content of the set, which should contain
	 * the elements in argv, [pluto, ciccio, qui, paperino, pippo].</p>
     * <p><b>Test Case Design</b>: Tests the content of the set. As the element
	 * in a collection are not ordered, unlike in Hset, printing
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

	// ------------------------------------------ Test cases ideated by me ------------------------------------------

	// ------------------------------------------ size method ------------------------------------------

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that an empty set
     * should have a size of zero and isEmpty call returning
     * true.
     * The set is not modified
     * since its creation.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 0 size (empty). Test based on the trivial but possible
     * state of an empty set.</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the set.</p>
     * <p><b>Pre-Condition</b>: The set is empty.</p>
     * <p><b>Post-Condition</b>: The set is still empty.</p>
     * <p><b>Expected Results</b>: The size method returns 0 and the isEmpty
     * method returns true.</p>
     */
    @Test
    public void Size_Empty_0()
    {
        assertEquals("Empty set does not have size of zero.", 0, s.size());
        assertEquals("isEmpty did not returned true.", true, s.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a set with 1 element
     * should have a size of 1 and isEmpty call returning
     * false.
     * The set is modified before the asserts.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 1 size and not being empty.</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the set.</p>
     * <p><b>Pre-Condition</b>: The set is empty.</p>
     * <p><b>Post-Condition</b>: The set contains 1.</p>
     * <p><b>Expected Results</b>: The size method returns 1 and the isEmpty
     * method returns false.</p>
     */
    @Test
    public void Size_OneElement_1()
    {
        s.add(1);
        assertEquals("Empty set does not have size of one.", 1, s.size());
        assertEquals("isEmpty did not returned false.", false, s.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a set with 3 elements
     * should have a size of 3 and isEmpty call returning
     * false.
     * The set is modified before the asserts.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 3 size and not being empty.</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the set.</p>
     * <p><b>Pre-Condition</b>: The set is empty.</p>
     * <p><b>Post-Condition</b>: The set has 3 elements ({0, 1, 2}).</p>
     * <p><b>Expected Results</b>: The size method returns 3 and the isEmpty
     * method returns false.</p>
     */
    @Test
    public void Size_ThreeElements_3()
    {
        TestUtilities.initHCollection(s, 0, 3);
        assertEquals("set with 3 elements does not have size of 3.", 3, s.size());
        assertEquals("isEmpty did not returned false.", false, s.isEmpty());
    }

    /**
     * <p><b>Summary</b>: size method test case. 
     * The test case asserts that a set with 3 elements
     * should have a size of 500 and isEmpty call returning
     * false.
     * The set is modiefied before the asserts.</p>
     * <p><b>Test Case Design</b>: The design is a simple assert of
     * a size call and expected 500 size and not being empty. Large
     * input is considered in this test case.</p>
     * <p><b>Test Description</b>: size and isEmpty methods are invoked on
     * the set.</p>
     * <p><b>Pre-Condition</b>: The set is empty.</p>
     * <p><b>Post-Condition</b>: The set contains 500 elements ({0:500}).</p>
     * <p><b>Expected Results</b>: The size method returns 500 and the isEmpty
     * method returns false.</p>
     */
    @Test
    public void Size_500Elements()
    {
        TestUtilities.initHCollection(s, 0, 500);
        assertEquals("set with 500 elements does not have size of 500.", 500, s.size());
        assertEquals("isEmpty did not returned false.", false, s.isEmpty());
    }

	// ------------------------------------------ contains method ------------------------------------------

    /**
     * <p><b>Summary</b>: contains method test case.
     * The test case checks in various situation its internal state
     * with contains.</p>
     * <p><b>Test Case Design</b>: contains method is tested with the same
     * elements while adding them.</p>
     * <p><b>Test Description</b>: First 1 is added and asserts are performed,
     * then 2 and 3 are added and asserts are performed.</p>
     * <p><b>Pre-Condition</b>: The set is empty.</p>
     * <p><b>Post-Condition</b>: The set is not empty.</p>
     * <p><b>Expected Results</b>: The set contains the right elements during
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
        assertEquals("The set contains 1 even if it is empty.", false, s.contains(1));
        s.add(1);
        assertEquals("The set does not contain 1 even if it should.", true, s.contains(1));
        assertEquals("The set should not contain 3.", false, s.contains(3));
        s.add(2);
        s.add(3);
        assertEquals("The set does not contain 1 even if it should.", true, s.contains(1));
    }

    /**
     * <p><b>Summary</b>: contains method test case.
     * <p><b>Test Case Design</b>: The test case checks in various situation its internal state
     * with contains, changing through execution.</p>
     * <p><b>Test Description</b>: Numbers from 50 (included) to 100 (excluded) are added, then checks if
     * elements from 25 to 125 are contained in the set in 3 different steps.
     * <ul>
     * <li>{25:50} not contained (beginning).</li>
     * <li>{50:100} contained (middle).</li>
     * <li>{100:125} not contained (ending).</li>
     * </ul>
     * <p><b>Pre-Condition</b>: The set is empty.</p>
     * <p><b>Post-Condition</b>: The set is not empty.</p>
     * <p><b>Expected Results</b>: The set contains the right elements during
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
            s.add(i);
        for (int i = 25; i < 50; i++)
            assertEquals("The set should NOT include " + i, false, s.contains(i));
        for (int i = 50; i < 100; i++)
            assertEquals("The set should include " + i, true, s.contains(i));
        for (int i = 100; i < 125; i++)
            assertEquals("The set should NOT include " + i, false, s.contains(i));
    }

	// ------------------------------------------ equals method ------------------------------------------

    /**
     * <p><b>Summary</b>: equals method test case.</p>
     * <p><b>Test Case Design</b>: equals method is tested with an equal CollectionAdapter
     * and a bigger and a smaller one. The returned values should be true and false.
     * Also reflective property of equal is tested.</p>
     * <p><b>Test Description</b>: Collection is initialized, then different equals invoke are
     * asserted with different arguments, generated for each case.</p>
     * <p><b>Pre-Condition</b>: Collection contains {0 : 1000}.</p>
     * <p><b>Post-Condition</b>: Collection is unchanged.</p>
     * <p><b>Expected Results</b>: The Collection is unchanged and symmetric property is valid.</p>
     */
    @Test
    public void Equals_0To10()
    {
        int to = 1000;
        TestUtilities.initHCollection(s, 0, to);
        assertEquals(true, s.equals(TestUtilities.getIntegerHSet(0, to)));
        assertEquals(true, TestUtilities.getIntegerHSet(0, to).equals(s));   // Symmetric property
        assertEquals(false, s.equals(TestUtilities.getIntegerHSet(0, to + 15)));  // bigger Collection returns false
        assertEquals(false, s.equals(TestUtilities.getIntegerHSet(0, 5)));  // smaller Collection returns false
    }

    
    /**
     * <p><b>Summary</b>: equals method test case.
     * The test case the method behaviour with 2 empty set.</p>
     * <p><b>Test Case Design</b>: When both sets are empty the equals
     * method should return true because an empty set is equal to an
     * empty set.</p>
     * <p><b>Test Description</b>: Single assert, l1.equals(l2) invoked.</p>
     * <p><b>Pre-Condition</b>: Both set are empty.</p>
     * <p><b>Post-Condition</b>: Both set are empty.</p>
     * <p><b>Expected Results</b>: equals returns true, as one
     * empty set of course equals another empty set.</p>
     */
    @Test
    public void Equals_Empty_True()
    {
        assertEquals("Two empty sets should equals.", true, s.equals(s2));
        assertEquals("Two empty sets should equals.", true, s2.equals(s));
    }

    /**
     * <p><b>Summary</b>: equals method test case.
     * The reflective property of equal method is tested.</p>
     * <p><b>Test Case Design</b>: equals method should be reflective,
     * therefore x.equals(x) should always return true.</p>
     * <p><b>Test Description</b>: The test invokes s.equals(s) when
     * s is empty, when it has 10 elements and when it has 1000 elements.</p>
     * <p><b>Pre-Condition</b>: set is not null.</p>
     * <p><b>Post-Condition</b>: set has 1000 elements. </p>
     * <p><b>Expected Results</b>: set equals itself, therefore
     * reflective property is valid.</p>
     */
    @Test
    public void Equals_Reflective()
    {
        assertEquals("Reflective property is not met.", true, s.equals(s));    // set is empty
        TestUtilities.initHCollection(s, 0, 10);
        assertEquals("Reflective property is not met.", true, s.equals(s));    // set is not empty, should return true anyways
        TestUtilities.initHCollection(s, 0, 1000);
        assertEquals("Reflective property is not met.", true, s.equals(s));    // set is not empty, should return true anyways
    }

    /**
     * <p><b>Summary</b>: equals method test case.
     * The transitive property of equal method is tested.</p>
     * <p><b>Test Case Design</b>: equals method should be transitive,
     * therefore a.equals(b) and b.equals(c) {@literal =>} a.equals(c).</p>
     * <p><b>Test Description</b>: The test invokes s.equals(s2) and s2.equals(s3)
     * and s.equals(s3)</p>
     * <p><b>Pre-Condition</b>: sets contain {1 : 1000}.</p>
     * <p><b>Post-Condition</b>: sets are unchanged. </p>
     * <p><b>Expected Results</b>: Equals has transitive property.</p>
     */
    @Test
    public void Equals_Transitive()
    {
        int to = 1000;
        TestUtilities.initHCollection(s, 0, to);
        TestUtilities.initHCollection(s2, 0, to);
        HSet s3 = TestUtilities.getIntegerHSet(0, to);

        assertEquals("sets should be equal.", true, s.equals(s2));
        assertEquals("sets should be equal.", true, s2.equals(s3));
        assertEquals("Transitive property is not met.",true, s.equals(s3));
    }

	// ------------------------------------------ toArray method ------------------------------------------

	/**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of s is zero, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an empty
     * set</p>
     * <p><b>Pre-Condition</b>: s is empty</p>
     * <p><b>Post-Condition</b>: s is empty</p>
     * <p><b>Expected Results</b>: es.toArray returns an empty
     * toArray.</p>
     */
    @Test
    public void ToArray_Empty()
    {
        assertArrayEquals(new Object[0], s.toArray());
    }

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of s is 1, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an set
     * containing only one element, then s and the array returned
     * from toArray are checked through checkToArray.</p>
     * <p><b>Pre-Condition</b>: s contains 1 element</p>
     * <p><b>Post-Condition</b>: s is unchanged</p>
     * <p><b>Expected Results</b>: es.toArray returns an array
     * of lenght 1 containing only the element 1</p>
     */
    @Test
    public void ToArray_OneElement()
    {
        s.add(1);
        checkToArray(s, s.toArray());
    }

    /**
     * <p><b>Summary</b>: toArray and remove method test case.
     * Tests the behaviour of toArray method in different cases
     * and configurations of the set.</p>
     * <p><b>Test Case Design</b>: Checks the array to be right
     * through checkToArray after each removals through es.remove,
     * creating different situations and cases to test the method.</p>
     * <p><b>Test Description</b>: The test first removes the
     * even elements, then the odd elements. After each
     * removal the checkToArray method is invoked to check if the
     * generated array in all of this cases is right.</p>
     * <p><b>Pre-Condition</b>: s contains {0:1000}.</p>
     * <p><b>Post-Condition</b>: s is empty</p>
     * <p><b>Expected Results</b>: After each removal the generated array
     * through es.toArray is right and coherent. At the end s is empty.</p>
     */
    @Test
    public void ToArray_0To1000()
    {
        int bound = 1000;
		initHCollection(s, 0, 1000);
        for (int i = 0; i < bound; i += 2)
        {
            s.remove(i);
            checkToArray(s, s.toArray());
        }
        for (int i = 1; i < bound; i += 2)
        {
            s.remove(i);
            checkToArray(s, s.toArray());
        }
        assertEquals("Should be empty", 0, s.size());
    }

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of s is zero, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an empty
     * set</p>
     * <p><b>Pre-Condition</b>: s is empty</p>
     * <p><b>Post-Condition</b>: s isempty</p>
     * <p><b>Expected Results</b>: es.toArray returns an empty
     * toArray.</p>
     */
    @Test
    public void ToArrayArrayArg_Empty()
    {
        Object[] a = new Object[0];
        s.toArray(a);
        assertArrayEquals(new Object[0], a);
    }

    /**
     * <p><b>Summary</b>: toArray method test case.</p>
     * <p><b>Test Case Design</b>: Tests the toArray method
     * when the size of s is 1, which is a limit case.</p>
     * <p><b>Test Description</b>: toArray is invoked by an set
     * containing only one element, then s and the array modified
     * from toArray are checked through checkToArray.</p>
     * <p><b>Pre-Condition</b>: s contains 1 element</p>
     * <p><b>Post-Condition</b>: s is unchanged</p>
     * <p><b>Expected Results</b>: es.toArray returns an array
     * of lenght 1 containing only the element 1</p>
     */
    @Test
    public void ToArrayArrayArg_OneElement()
    {
        Object[] a = new Object[1];
        s.add(1);
        s.toArray(a);
        checkToArray(s, a);
    }

    /**
     * <p><b>Summary</b>: toArray and remove method test case.
     * Tests the behaviour of toArray method in different cases
     * and configurations of the set.</p>
     * <p><b>Test Case Design</b>: Checks the array to be right
     * through checkToArray after each removals through es.remove,
     * creating different situations and cases to test the method.</p>
     * <p><b>Test Description</b>: The test first removes the
     * even elements, then the odd elements. After each
     * removal the checkToArray method is invoked to check if the
     * modified array in all of this cases is right.</p>
     * <p><b>Pre-Condition</b>: s contains {0:1000}.</p>
     * <p><b>Post-Condition</b>: s is empty</p>
     * <p><b>Expected Results</b>: After each removal the modified array
     * through es.toArray is right and coherent. At the end s is empty.</p>
     */
    @Test
    public void ToArrayArrayArg_0To1000()
    {
        int bound = 1000;
        
        initHCollection(s, 0, 1000);
        for (int i = 0; i < bound; i += 2)
        {
            s.remove(i);
            Object[] a = new Object[s.size()];
            s.toArray(a);
            checkToArray(s, a);
        }
        for (int i = 1; i < bound; i += 2)
        {
            s.remove(i);
            Object[] a = new Object[s.size()];
            s.toArray(a);
            checkToArray(s, a);
        }
        assertEquals("Should be empty", 0, s.size());
    }

	// ------------------------------------------ remove method ------------------------------------------

    /**
     * <p><b>Summary</b>: remove method test case.
     * Removing from an empty set should always return false.</p>
     * <p><b>Test Case Design</b>: Test the set behaviour removing when it is empty, which
     * is a limit case of the method. After the method invoke the set remains empty
     * and the method returns,
     * as removing from an empty set does not modify it.</p>
     * <p><b>Test Description</b>: Tries to remove the object 345 from the empty set.
     * Note that without the explicit cast it would have thrown IndexOutOfBoundsException.</p>
     * <p><b>Pre-Condition</b>: set is empty.</p>
     * <p><b>Post-Condition</b>: seti is still empty.</p>
     * <p><b>Expected Results</b>: remove method returns false as the set was not modified.</p>
     */
    @Test
    public void Remove_Empty()
    {
        assertEquals("set is empty therefore remove method must return false.", false, s.remove((Object)345));
    }

    /**
     * <p><b>Summary</b>: remove method test case.
     * Test tries to remove the 7 object from a set containing numbers
     * from 0 (included) to 6 (excluded), therefore it return false.</p>
     * <p><b>Test Case Design</b>: Method tries to remove a non present
     * element, which is a special case. </p>
     * <p><b>Test Description</b>: The test asserts that remove method returns false.
     * In fact, it contains numbers
     * from 0 (included) to 6 (excluded), so removing 7 does not change the set and returns false.
     * Note that without the explicit cast it would have thrown IndexOutOfBoundsException.</p>
     * <p><b>Pre-Condition</b>: The set is empty.</p>
     * <p><b>Post-Condition</b>: The set is not empty and removing 7 does not modify the set.</p>
     * <p><b>Expected Results</b>: Method returns false as 7 is not contained in the set,
     * therefore the set is not modified because of the remove method invoke.</p>
     */
    @Test
    public void Remove_NotInSet()
    {
        TestUtilities.initHCollection(s, 0, 6);
        assertEquals("Method should return false because it does not contains 7.", false, s.remove(7));
    }

	/**
     * <p><b>Summary</b>: remove method test case.
	 * Tests remove behaviour with a variable set's size.</p>
     * <p><b>Test Case Design</b>: Set's size is changing due
	 * to remove method, therefore remove is tested with different
	 * size of set, including the size 1, which is a limit case.</p>
     * <p><b>Test Description</b>: At each iteration the element
	 * i (i in (0:100)) is removed from the set. At the end of the for loop
	 * the set is empty.</p>
     * <p><b>Pre-Condition</b>: set contains {0:1000}.</p>
     * <p><b>Post-Condition</b>: set is empty.</p>
     * <p><b>Expected Results</b>: Set has been emptied by the
	 * remove method through the iteration. Each iteration is asserted
	 * to be true, as the set was modified due to remove. Size is decremented
	 * by one after each remove. Set is asserted to be equal to a set
	 * containing elements from i to bound.</p>
     */

	@Test
	public void Remove_0To100()
	{
		int bound = 100;
		TestUtilities.initHCollection(s, 0, bound);
		for (int i = 0; i < bound; i++)
		{
			assertTrue(s.remove(i));
			assertEquals("Size should be decremented", bound - i - 1,s.size());
			assertEquals("Sets should be equal", s, getIntegerHSet(i + 1, bound));
		}
		assertTrue("Should be empty", s.isEmpty() && s.size() == 0);
	}

	/**
     * <p><b>Summary</b>: remove and add method test case.
     * The set starts with 1 element, and after repeated
     * adds and removes it has still 1 element.</p>
     * <p><b>Test Case Design</b>: Repeated adds and removes of the
     * same element should keep the set invariant, which is a way to
     * stress the set structure.</p>
     * <p><b>Test Description</b>: The test starts with 1 elements.
	 * In fact, even if there are 2 adds, the set does not allow
	 * multiple elements.
     * Then the element 1 is added and removed 100 times.
     * At the end the set must have still 1 element, and they should
     * be the same one.</p>
     * <p><b>Pre-Condition</b>: The set contains 0.</p>
     * <p><b>Post-Condition</b>: The set is invariated.</p>
     * <p><b>Expected Results</b>: The only element is 0,
	 * checked through s.iterator().next()</p>
     */
    @Test
    public void RemoveAdd_ObjArg_Mixed1()
    {
        s.add(0);
        s.add(0);	// Not actually inserted
        for (int i = 0; i < 100; i++)
        {
            s.add(1);
            s.remove(1);
        }
        assertEquals("After these operation the set must have 1 element.", 1, s.size());
        assertEquals("The first element should be 0.", 0, s.iterator().next());
    }

    // ------------------------------------------ containsAll method ------------------------------------------


    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The method tests if an empty set contains the elements
     * of another collection, which is obviusly false.</p>
     * <p><b>Test Case Design</b>: The test case tests the limit case of
     * checking an empty set containing something.</p>
     * <p><b>Test Description</b>: The collection contains 1, 2 and 3.
     * The containsAll method obviously should return false for
     * any coll's content as the set is empty.</p>
     * <p><b>Pre-Condition</b>: The set is empty.</p>
     * <p><b>Post-Condition</b>: The set is still empty.</p>
     * <p><b>Expected Results</b>: The containsAll method return false.</p>
     */
    @Test
    public void ContainsAll_Empty()
    {
        TestUtilities.initHCollection(c, 1, 4);
        assertEquals("The method should return false because the set is empty.", false, s.containsAll(c)); 
    }

   /**
     * <p><b>Summary</b>: containsAll method test case.
     * The method tests if an empty set contains the elements
     * of another collection.</p>
     * <p><b>Test Case Design</b>: The test case tests the limit case of
     * checking an empty set containing an empty collection, which is true, 
     * as the empty subset is the subset of every set, therefore even of the
     * empty set. The tested case is a limit case of containsAll.</p>
     * <p><b>Test Description</b>: The collection is empty.
     * The containsAll method obviously should return true for
     * any c's content, because the empty subset is the
     * subset of every set.</p>
     * <p><b>Pre-Condition</b>: The set is empty.</p>
     * <p><b>Post-Condition</b>: The set is empty.</p>
     * <p><b>Expected Results</b>: The containsAll method return true.</p>
     */
    @Test
    public void ContainsAll_BothEmpty()
    {
        assertEquals("The method should return true because the collection is empty.", true, s.containsAll(c)); 
    }
    
    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The test case tests different containsAll calls
     * with different collection.</p>
     * <p><b>Test Case Design</b>: The tests calls containsAll
     * with the first, the last, the middle, and a set of
     * middle elements as argument.</p>
     * <p><b>Test Description</b>: In the test containsAll is called with the
     * collection containing
     * {1}, {10}, {3, 4, 5}, {1, 5, 10}.</p>
     * <p><b>Pre-Condition</b>: The set contains {0:10}.</p>
     * <p><b>Post-Condition</b>: The set contains {0:10}.</p>
     * <p><b>Expected Results</b>: Every containsAll calls return true.</p>
     */
    @Test
    public void ContainsAll_0to11()
    {
        TestUtilities.initHCollection(s, 0, 11);

        // {1}
        c.add(1);
        assertEquals("The set contains 1.", true, s.containsAll(c));
        
        // {10}
        c = new CollectionAdapter();
        c.add(10);
        assertEquals("The set contains 10.", true, s.containsAll(c));

        // {3, 4, 5}
        c = new CollectionAdapter();
        TestUtilities.initHCollection(c, 3, 6);
        assertEquals("The set contains 3, 4 and 5.", true, s.containsAll(c));

        // {1, 5, 10}
        c = new CollectionAdapter();
        c.add(1);
        c.add(5);
        c.add(10);
        assertEquals("The set contains 1, 5 and 10.", true, s.containsAll(c));
    }

    /**
     * <p><b>Summary</b>: containsAll method test case.
     * Tests if different combination are contained in the set.
     * Each containsAll call returns false.</p>
     * <p><b>Test Case Design</b>: Different scenario are tested where
     * containsAll should return false. Calling containsAll with arguments
     * not present in the set is a common case for the method.</p>
     * <p><b>Test Description</b>: The first containsAll takes as argument
     * a collection containing a single element, not present in the
     * set. The second one takes as argument a collection containing element
     * in the set e one not contained in the set: therefore, the call
     * must return false as one of the collection is not contained.</p>
     * <p><b>Pre-Condition</b>: The set contains {0:10}.</p>
     * <p><b>Post-Condition</b>: The set contains {0:10}.</p>
     * <p><b>Expected Results</b>: All containsAll calls return true.</p>
     */
    @Test
    public void ContainsAll_0To11()
    {
        TestUtilities.initHCollection(s, 0, 11);

        c.add(11);
        assertEquals("11 is not contained.", false, s.containsAll(c));

        c = new CollectionAdapter();
        c.add(3);
        c.add(4);
        c.add(5);
        c.add(12);   // Single element not present
        assertEquals("3, 4 and 5 are contained. 12 is not contained.", false, s.containsAll(c));
    }

    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The test case tries to call containsAll with a null
     * collection on an empty set.</p>
     * <p><b>Test Case Design</b>: Tests the method with null arguments, which is a special
     * case of invalid argument.</p>
     * <p><b>Test Description</b>: Calls method with null collection.</p>
     * <p><b>Pre-Condition</b>: set is empty, c is null.</p>
     * <p><b>Post-Condition</b>: set is empty, c is null.</p>
     * <p><b>Expected Results</b>: NullPointerException thrown.</p>
     */    
    @Test(expected = java.lang.NullPointerException.class)
    public void ContainsAll_NullCollection_NPE()
    {
        s.containsAll(null);
    }

    /**
     * <p><b>Summary</b>: containsAll method test case.
     * The test case tries to call containsAll with a null
     * collection on a non empty set.</p>
     * <p><b>Test Case Design</b>: Tests the method with null arguments, which is a special
     * case of invalid argument.</p>
     * <p><b>Test Description</b>: Calls method with null collection.</p>
     * <p><b>Pre-Condition</b>: set is not empty, c is null.</p>
     * <p><b>Post-Condition</b>: set is not empty, c is null.</p>
     * <p><b>Expected Results</b>: NullPointerException thrown.</p>
     */    
    @Test(expected = java.lang.NullPointerException.class)
    public void ContainsAll_NullCollectionNotEmptyset_NPE()
    {
        s.add(1);
        s.add(2);
        s.containsAll(null);
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
     * <p><b>Pre-Condition</b>: set is empty.</p>
     * <p><b>Post-Condition</b>: set is empty.</p>
     * <p><b>Expected Results</b>: NullPointerException thrown.</p>
     */
    @Test(expected = java.lang.NullPointerException.class)
    public void AddAll_NullCollection_NPE()
    {
        s.addAll(null);
    }

    /**
     * <p><b>Summary</b>: addAll method test case.</p>
     * <p><b>Test Case Design</b>: Tests the case of empty collection being
     * passed as argument, which is a limit case.</p>
     * <p><b>Test Description</b>: The test cases calls addAll as empty
     * collection as argument. Adding an empty set means that no element
     * is added, therefore addAll returns false as the set is unchanged.</p>
     * <p><b>Pre-Condition</b>: set is empty, c is empty.</p>
     * <p><b>Post-Condition</b>: set is empty, c is empty.</p>
     * <p><b>Expected Results</b>: addAll returns false because no
     * changes are made. set is still empty.</p>
     */
    @Test
    public void AddAll_EmptyCollection_False()
    {
        assertEquals("set did not change, so the method should return false.", false, s.addAll(c));
        assertEquals("The set should be empty.", true, s.isEmpty());
    }

    /**
     * <p><b>Summary</b>: addAll method test case.
     * The test adds a collection to the set,
     * then checks if the elements were stored correctly.</p>
     * <p><b>Test Case Design</b>: addAll must behave correctly
     * adding a collection of 3 elements, which is a common case for
     * the addAll method.</p>
     * <p><b>Test Description</b>: 3 elements are added and then the test
     * checks the contained element and its size.</p>
     * <p><b>Pre-Condition</b>: set is empty, c has {1, 2, 3}.</p>
     * <p><b>Post-Condition</b>: set and c both contain {1, 2, 3}. In particular,
     * c is unchanged.</p>
     * <p><b>Expected Results</b>: set contains the added elements {1, 2, 3},
	 * therefore containsAll returns true and its size is 3.</p>
     */
    @Test
    public void AddAll_FromEmptyTo123()
    {
        TestUtilities.initHCollection(c, 1, 4);

        assertTrue(s.addAll(c));
		assertTrue(s.containsAll(c));
        assertEquals("Set size should be 3.", 3, s.size());
    }

    /**
     * <p><b>Summary</b>: addAll method test case.
     * The set initially contains {1, 2, 3}, then through
     * remove and addAll it finally contains numbers from 1
     * to 5 included.</p>
     * <p><b>Test Case Design</b>: It tests how the set behaves with
     * removals and then with adding a collection.</p>
     * <p><b>Test Description</b>: The test removes the 3 number, then it
     * adds through addAll method the elements {3, 4, 5}.</p>
     * <p><b>Pre-Condition</b>: set contains {1, 2, 3}.</p>
     * <p><b>Post-Condition</b>: set contains {1:6}.</p>
     * <p><b>Expected Results</b>: set contains the added elements {1:6},
	 * therefore s.equals(TestUtilities.getEntryHCollection(1, 6)).</p>
     */
    @Test
    public void AddAll_From123to12345()
    {
        TestUtilities.initHCollection(s, 1, 3);
        s.remove(3);
        TestUtilities.initHCollection(c, 3, 6);
        assertTrue("addAll should return true as s has been changed.", s.addAll(c));
        assertTrue("The set should be equal.", s.equals(TestUtilities.getIntegerHSet(1, 6)));
    }

    /**
     * <p><b>Summary</b>: addAll method test case.
     * The method thest the insertion of a large number
     * of elements through addAll.</p>
     * <p><b>Test Case Design</b>: The test considers the case
     * scenario of large input. From the Sommerville: "Force computation results to be
     * too large or too small."</p>
     * <p><b>Test Description</b>: Numbers from 1 to 100 are inserted in c, then addAll
     * method is invoked with c as argument.</p>
     * <p><b>Pre-Condition</b>: The set is empty, c contains aforementioed numbers.</p>
     * <p><b>Post-Condition</b>: The set contains number from 1 to 100.</p>
     * <p><b>Expected Results</b>: The set contains all the element
	 * inserted through addAll, checked through containsAll method. Set size is 100.</p>
     */
    @Test
    public void AddAll_0To100()
    {
        TestUtilities.initHCollection(c, 0, 100);
        assertTrue("The set did change.", s.addAll(c));
		assertTrue("Set should contain all", s.containsAll(c));
		assertEquals("Set size should be 100.", 100, s.size());

    }

	// ------------------------------------------ removeAll method ------------------------------------------
    
    /**
     * <p><b>Summary</b>: removeAll method test case.</p>
     * <p><b>Test Case Design</b>: removeAll method called with
     * empty collection as an argument, which is a method's
     * limit case.</p>
     * <p><b>Test Description</b>: The test adds 3 elements to the set
     * and then calls removeAll with an empty collection. Therefore
     * the set should be unchanged.</p>
     * <p><b>Pre-Condition</b>: The set contains {1, 2, 3}.</p>
     * <p><b>Post-Condition</b>: The set is unchaged.</p>
     * <p><b>Expected Results</b>: The set is unchanged, therefore removeAll
     * returns false, c is unchanged.</p>
     */
    @Test
    public void RemoveAll_EmptyColl_False()
    {
        TestUtilities.initHCollection(s, 1, 4);
        assertFalse("removeAll did not remove anything, therefore it should return false.", s.removeAll(c));
        assertEquals("sets should be equal.", true, s.equals(TestUtilities.getIntegerHSet(1, 4)));
        assertEquals("Wrong set size.", 3, s.size());
        assertEquals("coll should be empty.", 0, c.size());
    }

    /**
     * <p><b>Summary</b>: removeAll method test case.
     * The method is called with the limit case of an
     * calling empty set.</p>
     * <p><b>Test Case Design</b>: removeAll method is called by an
     * empty set, which is a limit case. The set should be unchanged, so the method
     * should return false.</p>
     * <p><b>Test Description</b>: removeAll is called by an empty set.</p>
     * <p><b>Pre-Condition</b>: The set is empty.</p>
     * <p><b>Post-Condition</b>: The set is still empty.</p>
     * <p><b>Expected Results</b>: The set is unchanged, therefore removeAll
     * returns false, its size is zero.</p>
     */
    @Test
    public void RemoveAll_Emptyset_False()
    {
        assertEquals("removeAll did not remove anything, therefore it should return false.", false, s.removeAll(c));
        assertEquals("set should be empty.", 0, s.size());
        assertEquals("coll should be empty.", 0, c.size());
    }

    /**
     * <p><b>Summary</b>: removeAll method test case.
     * Method is tested when the set is empty and the
     * collection is not. Because the already empty set
     * is unchanged the method should return false.</p>
     * <p><b>Test Case Design</b>: c is not empty and removeAll is called
     * with c as an argument, which is a limit case.</p>
     * <p><b>Test Description</b>: The test calls removeAll
     * with set being empty, c being not (c contains {1, 2, 3}).</p>
     * <p><b>Pre-Condition</b>: set is empty, c contains {1, 2, 3}.</p>
     * <p><b>Post-Condition</b>: set is still empty, c contains {1, 2, 3}.</p>
     * <p><b>Expected Results</b>: set is empty, c is unchanged.</p>
     */
    @Test
    public void RemoveAll_EmptysetNoEmptyColl_False()
    {
        TestUtilities.initHCollection(c, 1, 4);

        assertEquals(false, s.removeAll(c));
        assertEquals("set should be empty.", 0, s.size());
        assertEquals("coll' size should be 3.", 3, c.size());
        assertEquals("coll should contains {1, 2, 3}.", true, c.equals(TestUtilities.getIntegerHSet(1, 4)));
    }

    /**
     * <p><b>Summary</b>: removeAll method test case.</p>
     * <p><b>Test Case Design</b>: Removes middle elements from
     * the set. This kind of removal is a common case for the
     * removeAll method.</p>
     * <p><b>Test Description</b>: set contains {1, ..., 10} and
     * 3, 4, 5, are removed through removeAll method.</p>
     * <p><b>Pre-Condition</b>: set contains aforementioned numbers.</p>
     * <p><b>Post-Condition</b>: 3, 4, 5 are removed. c is unchanged.</p>
     * <p><b>Expected Results</b>: 3, 4, 5 are removed, set has been changed,
     * therefore removeAll returns true.
     * c is unchanged.</p>
     */
    @Test
    public void RemoveAll_From1To10Remove345()
    {
        TestUtilities.initHCollection(s, 1, 10);
        // To be removed
        TestUtilities.initHCollection(c, 3, 6);

        assertTrue("The set has changed, it should return true.", s.removeAll(c));
		assertTrue(s.contains(1) && s.contains(2) && s.contains(6) && s.contains(7) && s.contains(8) && s.contains(9));
        assertEquals(6, s.size());
        assertTrue(c.contains(3) && c.contains(4) && c.contains(5));
    }

    /**
     * <p><b>Summary</b>: removeAll method test case.</p>
     * <p><b>Test Case Design</b>: Removes middle elements from
     * the set. Those elements are multiple in set. Removing middle
     * and duplicated elements is a common case for removeAll method.</p>
     * <p><b>Test Description</b>: set contains {1, ..., 10,3, 4, 5, 3, 5} and
     * 3, 4, 5, are removed through removeAll method.</p>
     * <p><b>Pre-Condition</b>: set contains aforementioned numbers.</p>
     * <p><b>Post-Condition</b>: 3, 4, 5 are removed. c is unchanged.</p>
     * <p><b>Expected Results</b>: 3, 4, 5 are removed, set has been changed,
     * therefore removeAll returns true.
     * c is unchanged.</p>
     */
    @Test
    public void RemoveAll_From1To10DuplicatesRemove345()
    {
        TestUtilities.initHCollection(s, 1, 10);
        // Should all be removed by removeAll
        s.add(3);
        s.add(4);
        s.add(5);
        s.add(3);
        s.add(5);

        // To be removed
        c.add(3);
        c.add(4);
        c.add(5);

        assertTrue("The set has changed, it should return true.", s.removeAll(c));
		assertTrue(s.contains(1) && s.contains(2) && s.contains(6) && s.contains(7) && s.contains(8) && s.contains(9));
        assertEquals(6, s.size());
        assertTrue(c.contains(3) && c.contains(4) && c.contains(5));
    }

    /**
     * <p><b>Summary</b>: removeAll and remove method test case.</p>
     * <p><b>Test Case Design</b>: Various add, remove and removeAll
     * interchanged and repeated to stress the set behaviour.</p>
     * <p><b>Test Description</b>: First set contains {1, 2, 3, 3}.
     * Then element 3 is removed via remove. The removeAll
	 * does NOT remove any 3, as in set only unique element
	 * are possible. Then 10 threes are added to the set. {4, 5} being
     * added. removeAll is called with c as an argument.</p>
     * <p><b>Pre-Condition</b>: set contains {1, 2, 3, 3}.</p>
     * <p><b>Post-Condition</b>: set contains {1, 2, 4}.</p>
     * <p><b>Expected Results</b>: ss.contains(1) && s.contains(2) && s.contains(4) && s.size() == 3
	 * returns true, therefore s contains 1, 2, 4 and its size is 3.</p>
     */
    @Test
    public void RemoveAll_Duplicates3_Mixed()
    {
        s.add(1);
        s.add(2);
        s.add(3);
        s.add(3);
        s.remove((Object)3);
        c.add(3);
        assertFalse("3 should be removed.", s.removeAll(c));
        assertFalse("3 not present in the set.", s.removeAll(c));

        for (int i = 0; i < 10; i++)
            s.add(3);

        s.add(4);
        s.add(5);

        c.add(5);
        assertEquals(true, s.removeAll(c));
		assertTrue(s.contains(1) && s.contains(2) && s.contains(4) && s.size() == 3);
    }

    // ------------------------------------------ retainAll method ------------------------------------------

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * retainAll is being called with an empty collection as an argument,
     * therefore the set should became the empty set, since mainteining
     * only the "empty" subset means deleting all the elements.</p>
     * <p><b>Test Case Design</b>: retainAll being called with the limit case of
     * an empty collection as an argument.</p>
     * <p><b>Test Description</b>: The set removes all but "empty", so
     * it empties. In fact initially it contains {1, 2, 3}</p>
     * <p><b>Pre-Condition</b>: The set contains {1, 2, 3}.</p>
     * <p><b>Post-Condition</b>: The set is empty.</p>
     * <p><b>Expected Results</b>: The set is empty, retainAll returns true
     * because the set has changed.</p>
     */
    @Test
    public void RetainAll_Empty_True()
    {
        TestUtilities.initHCollection(s, 1, 4);
        assertEquals("The set has changed, it should return true.", true, s.retainAll(c));
        assertEquals("set should be empty.", 0, s.size());
        assertEquals("coll should be empty.", 0, c.size());
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
     * it empties.</p>
     * <p><b>Pre-Condition</b>: The set is empty.</p>
     * <p><b>Post-Condition</b>: The set is still empty.</p>
     * <p><b>Expected Results</b>: retainAll returns false because the set is unchanged.</p>
     */
    @Test
    public void RetainAll_Empty_False()
    {
        assertEquals("The set has not changed, it should return false.", false, s.retainAll(c));
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * The test calls retainAll with a collection containing
     * few elements.</p>
     * <p><b>Test Case Design</b>: The retainAll method is tested with small
     * input. Testing a typical case.</p>
     * <p><b>Test Description</b>: The set initially contains numbers
     * from 1 to 5 included. retainAll is called with a collection
     * containing {3, 4, 5}, therefore the set should contain
     * {3, 4, 5}.</p>
     * <p><b>Pre-Condition</b>: The set contains {1, ..., 5}, c contains
     * {3, 4, 5}.</p>
     * <p><b>Post-Condition</b>: The set contains {3, 4, 5}, c contains
     * {3, 4, 5}.</p>
     * <p><b>Expected Results</b>: set contains {3, 4, 5}, set has changed. retainAll returns true
     * because the set has changed.</p>
     */
    @Test
    public void RetainAll_12345()
    {
        TestUtilities.initHCollection(s, 1, 6);
        TestUtilities.initHCollection(c, 3, 6);
        assertEquals("The set has changed, it should return true.", true, s.retainAll(c));
        assertTrue("set should contain {3, 4, 5}.", s.equals(TestUtilities.getIntegerHSet(3, 6)));
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * The test calls retainAll with a collection containing
     * few elements. Testing a typical case.</p>
     * <p><b>Test Case Design</b>: The retainAll method is tested with small
     * input.</p>
     * <p><b>Test Description</b>: The set initially contains numbers
     * from 1 to 9 included. retainAll is called with a collection
     * containing {2, 3}, therefore the set should contain
     * {2, 3}.</p>
     * <p><b>Pre-Condition</b>: The set contains {1, ..., 9}, c contains
     * {2, 3}.</p>
     * <p><b>Post-Condition</b>: The set contains {2, 3}, c contains
     * {2, 3}.</p>
     * <p><b>Expected Results</b>: set contains {2, 3}, set has changed.  retainAll returns true
     * because the set has changed.</p>
     */
    @Test
    public void RetainAll_23()
    {
        TestUtilities.initHCollection(s, 1, 10);
        c.add(2);
        c.add(3);
        assertTrue("The set has changed, it should return true.", s.retainAll(c));
        assertTrue("set should contain {2, 3}.", s.equals(TestUtilities.getIntegerHSet(2, 4)));
    }

    /**
     * <p><b>Summary</b>: retainAll method test case.
     * The test calls retainAll with a collection containing
     * many elements. Testing a typical case with a large input.</p>
     * <p><b>Test Case Design</b>:  The retainAll method is tested with large
     * input. The case is still a common case.</p>
     * <p><b>Test Description</b>: The set initially contains numbers
     * from 1 to 999 included. retainAll is called with a collection
     * containing {300, ..., 599}, therefore the set should contain
     * {300, 599}.</p>
     * <p><b>Pre-Condition</b>: The set contains {1, ..., 999}, c contains
     * {300, ..., 599}.</p>
     * <p><b>Post-Condition</b>: The set contains {300, ..., 599}, c contains
     * {300, ..., 599}.</p>
     * <p><b>Expected Results</b>: The arrays are equal, therefore set contains {300:600}. retainAll returns true
     * as the set is being modified.</p>
     */
    @Test
    public void RetainAll_1000()
    {
        TestUtilities.initHCollection(s, 1, 1000);
        TestUtilities.initHCollection(c, 300, 600);
        s.retainAll(c);
        assertEquals("The arrays should match.", TestUtilities.getIntegerHSet(300, 600), s);
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
     * it empties. In fact initially it contains {1, ..., 20}</p>
     * <p><b>Pre-Condition</b>: The set contains {1, ..., 20}.</p>
     * <p><b>Post-Condition</b>: The set is empty.</p>
     * <p><b>Expected Results</b>: The set is empty, retainAll returns true
     * because the set changes.</p>
     */
    @Test
    public void RetainAll_ToEmpty()
    {
        TestUtilities.initHCollection(s, 1, 21);
        TestUtilities.initHCollection(c, 21, 24);

        assertEquals("The set has changed, it should return true.", true, s.retainAll(c));
        assertEquals("The set should be empty.", 0, s.size());
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
     * <p><b>Test Description</b>: set contains {1, ..., 19}. c contains
     * {4, 4, 5, 5, 6, 6}. retainAll is called, so the set should contain
     * {4, 5, 6}.</p>
     * <p><b>Pre-Condition</b>: set contains {1, ..., 19}. c contains
     * {4, 4, 5, 5, 6, 6}.</p>
     * <p><b>Post-Condition</b>: set contains {4, 5, 6}. c contains
     * {4, 4, 5, 5, 6, 6}.</p>
     * <p><b>Expected Results</b>: The arrays are equal, therefore the
     * set contains {4, 5, 6}. retainAll returns true
     * as the set is being modified.</p>
     */
    @Test
    public void RetainAll_DuplicatesColl()
    {
        TestUtilities.initHCollection(s, 0, 20);
        for (int i = 4; i < 7; i++)
        {
            c.add(i);
            c.add(i);
        }
        assertEquals("The set has changed, it should return true.", true, s.retainAll(c));
        assertEquals("The arrays should match.", TestUtilities.getIntegerHSet(4, 7), s);
    }

	private void checkToArray(HSet es, Object[] arr)
    {
        assertEquals("The array and the set do NOT have the same elements.", es.size(), arr.length);
        for (int i = 0; i < arr.length; i++)
            assertTrue("The array and the set do NOT have the same elements.", es.contains(arr[i]));
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
