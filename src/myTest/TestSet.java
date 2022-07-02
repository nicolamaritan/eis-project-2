package myTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
     * <p><b>Summary</b>: Tests the toString method on the set.</p>
     * <p><b>Test Case Design</b>: Tests the toString method on the set.</p>
     * <p><b>Test Description</b>: Tests the toString method on the set.</p>
     * <p><b>Pre-Condition</b>: s contains argv elements.</p>
     * <p><b>Post-Condition</b>: s in unchanged.</p>
     * <p><b>Expected Results</b>: toString returns [pluto, ciccio, qui, paperino, pippo].
	 * Note that the contents is enclosed by [].</p>
     */
	@Test
	public void SetToStringTest()
	{
		argvInitiate(s);
		assertEquals("[pluto, ciccio, qui, paperino, pippo]", s.toString());
	}

	/**
     * <p><b>Summary</b>: Tests removing an element of the set
	 * through its iterator, after each next. Set's iterator tested methods
	 * are next, remove and hasNext.</p>
     * <p><b>Test Case Design</b>: Iterator's remove should work and remove
	 * method from the set. Removes elements until the set is empty.</p>
     * <p><b>Test Description</b>: Set is initiated with argv elements.
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
		argvInitiate(s);
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
     * <p><b>Summary</b>: Tests the toString method on the set.</p>
     * <p><b>Test Case Design</b>: Tests the toString method on the set.</p>
     * <p><b>Test Description</b>: Tests the toString method on the set.</p>
     * <p><b>Pre-Condition</b>: s contains argv elements.</p>
     * <p><b>Post-Condition</b>: s in unchanged.</p>
     * <p><b>Expected Results</b>: toString returns "[pippo, pippo, pluto, paperino, ciccio, qui]".
	 * Note that the contents is enclosed by [].</p>
     */
	@Test
	public void CollectionToStringTest()
	{
		argvInitiate(c);
		assertEquals("[pippo, pippo, pluto, paperino, ciccio, qui]", c.toString());
	}

	/**
     * <p><b>Summary</b>: Tests removing an element of the collection
	 * through its iterator, after each next. collection's iterator tested methods
	 * are next, remove and hasNext.</p>
     * <p><b>Test Case Design</b>: Iterator's remove should work and remove
	 * method from the collection. Removes elements until the collection is empty.</p>
     * <p><b>Test Description</b>: collection is initiated with argv elements.
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
		argvInitiate(c);
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

	public static void iterate(HIterator iter)
	{
		System.out.print("{");
		while(iter.hasNext())
		{
			System.out.print(iter.next() + "; ");
		}
		System.out.println("}");
	}

	public void argvInitiate(HCollection c)
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
