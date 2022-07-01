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
	HMap c = null;
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
		c = new MapAdapter();
	}

	@After
	public void AfterMethod()
	{
		s = null;
		c = null;
	}

	@Test
	public void ClonesAcceptanceTest()
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
	public void ToStringTest()
	{
		argvInitiate();
		assertEquals("[pluto, ciccio, qui, paperino, pippo]", s.toString());
	}

	@Test
	public void IteratorRemovalTest()
	{
		argvInitiate();
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

	/*public static void main(String[] argv)
	{

		try
		{


		System.out.println("Test cwCollection");


		System.out.print(c.size() + " ");
		iterate(c.iterator());
		for(int i=0;i<argv.length;i++)
		{
			System.out.println(argv[i] + " " + c.add(argv[i]));
			System.out.print(c.size() + " ");
			iterate(c.iterator());
		}

		if(c.size() == argv.length-1)
			System.out.println("\n*** collection refuses clones ***\n");
		else
			System.out.println("\n*** collection accepts clones ***\n");
			

		System.out.println("Collection.toString()? " + c);

		iter2 = c.iterator();
		while(iter2.hasNext())
		{
			iter2.next();
			iter2.remove();
			System.out.print(c.size() + " ");
			iterate(c.iterator());
		}

		if(c.size() == 0)
			System.out.println("\n*** collection iterator removal works ***\n");
		else
			System.out.println("\n*** collection iterator removal fails ***\n");
	}*/

	public static void iterate(HIterator iter)
	{
		System.out.print("{");
		while(iter.hasNext())
		{
			System.out.print(iter.next() + "; ");
		}
		System.out.println("}");
	}

	public void argvInitiate()
	{
		for (int i = 0; i < argv.length; i++)
			s.add(argv[i]);
	}


}
