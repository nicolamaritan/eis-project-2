package myTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import myAdapter.*;

public class TestSet
{
	HSet s = null;
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
		c = new CollectionAdapter();
	}

	@After
	public void AfterMethod()
	{
		s = null;
		c = null;
	}

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
		assertEquals(0, s.size());
		iterate(s.iterator());

		assertEquals(true, s.add(argv[0]));
		assertEquals(false, s.add(argv[1]));

		for(int i=2;i<argv.length;i++)
		{
			assertEquals(true, s.add(argv[i]));
			assertEquals(i, s.size());
			iterate(s.iterator());
		}
		assertEquals(argv.length - 1, s.size());
	}

    /**
     * <p><b>Summary</b>: Tests the content of the set, which should contain
	 * the elements in argv, [pluto, ciccio, qui, paperino, pippo].</p>
     * <p><b>Test Case Design</b>: Tests the content of the set.</p>
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
	 * method from the set. Removes elements until the set is empty.</p>
     * <p><b>Test Description</b>: Set is Initialized with argv elements.
	 * Iterates through set and after each next it removes the element,
	 * decrementing i. i starts from 5. At each iteration iterate with a new
	 * iterator is invoked.</p>
     * <p><b>Pre-Condition</b>: set contains argv elements.</p>
     * <p><b>Post-Condition</b>: set is empty.</p>
     * <p><b>Expected Results</b>: In each iteration must be --i equals s.size.
	 * At the and set size is 0.</p>
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
			assertEquals(--i, s.size());
			iterate(s.iterator());
		}
		assertEquals(0, s.size());
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
			assertEquals(true, c.add(argv[i]));
			assertEquals(i + 1, c.size());
			iterate(c.iterator());
		}
		assertEquals(false, c.size() == argv.length - 1);
	}

    /**
     * <p><b>Summary</b>: Tests the content of the set, which should contain
	 * the elements in argv, [pluto, ciccio, qui, paperino, pippo].</p>
     * <p><b>Test Case Design</b>: Tests the content of the set.</p>
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
     * <p><b>Summary</b>: Tests removing an element of the collection
	 * through its iterator, after each next. collection's iterator tested methods
	 * are next, remove and hasNext.</p>
     * <p><b>Test Case Design</b>: Iterator's remove should work and remove
	 * method from the collection. Removes elements until the collection is empty.</p>
     * <p><b>Test Description</b>: collection is Initialized with argv elements.
	 * Iterates through collection and after each next it removes the element,
	 * decrementing i. i starts from 6. At each iteration iterate with a new
	 * iterator is invoked.</p>
     * <p><b>Pre-Condition</b>: collection contains argv elements.</p>
     * <p><b>Post-Condition</b>: collection is empty.</p>
     * <p><b>Expected Results</b>: In each iteration must be --i equals s.size.
	 * At the and collection size is 0.</p>
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
		assertEquals(0, c.size());
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


}

    /**
     * <p><b>Summary</b>:</p>
     * <p><b>Test Case Design</b>:</p>
     * <p><b>Test Description</b>:</p>
     * <p><b>Pre-Condition</b>:</p>
     * <p><b>Post-Condition</b>:</p>
     * <p><b>Expected Results</b>:</p>
     */
