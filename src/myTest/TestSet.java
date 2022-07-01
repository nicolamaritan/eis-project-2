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

	@Test
	public void SetToStringTest()
	{
		argvInitiate(s);
		assertEquals("[pluto, ciccio, qui, paperino, pippo]", s.toString());
	}

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

	@Test
	public void CollectionToStringTest()
	{
		argvInitiate(c);
		assertEquals("[pippo, pippo, pluto, paperino, ciccio, qui]", c.toString());
	}

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
