package myTest;

// Imports
import static org.junit.Assert.*;
import org.junit.*;
import myAdapter.*;


/**
 * <p><b>Summary</b>: The test suite TestCollection focuses on the HCollection returned from value()
 * method of HMap, tests its correct
 * behaviour in different case scenario. Each HCollection method is tested in different
 * test cases.
 * The test suite contains the tests in the TestCollection.java
 * file assigned by the professor in the JUnit format. The original tests
 * inserted elements directly inserted elements in the set through add(Object) method,
 * but the HMap interfaces states that the HCollection returned from value() cannot use
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
 * inspections are combined. Each method is tested apart from others.
 */
public class TestCollection 
{

	HMap m = null;
	HCollection head;
	HCollection param = null;
	HCollection intersect = null;
	HCollection ct = null;
	boolean a, aa, aaa;
	boolean b, bb, bbb;
	boolean c, cc, ccc;
	boolean h;

    @BeforeClass
    public static void BeforeClassMethod()
    {
        System.out.println(TestCollection.class.getName() + " running.");
    }

	/**
	 * Method invoke before each test for setup.
	 */
	@Before
	public void Setup()
	{
		/*
		 * Replicated the original initialization.
		 */

		head = new CollectionAdapter();
		h = head.add("Collection Adapter");

		m = new MapAdapter();
		m.put("Collection Adapter", "Collection Adapter");
		m.put("aaa", "aaa");
		m.put("bbb", "bbb");
		m.put("ccc", "ccc");
		ct = m.values();

		param = new CollectionAdapter();
		param.add("aaa");
		param.add("bbb");
		param.add("ccc");
		
		intersect = new CollectionAdapter();
		intersect.add("aaa");
		intersect.add("bbb");
		intersect.add("ccc");
	}

	@AfterClass
	public static void AfterClassMethod()
	{
		System.out.println(TestCollection.class.getName() + " ended.");
	}

	/**
	 * Method invoke after each test for cleanup.
	 */
	@After
	public void Cleanup()
	{
		m = null;
		ct = null;
		param = null;
		intersect = null;
		head = null;
	}
	
	
	// ------------------------------------------ Test cases assigned from the professor ------------------------------------------

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
}

    /**
     * <p><b>Summary</b>:</p>
     * <p><b>Test Case Design</b>:</p>
     * <p><b>Test Description</b>:</p>
     * <p><b>Pre-Condition</b>:</p>
     * <p><b>Post-Condition</b>:</p>
     * <p><b>Expected Results</b>:</p>
     */
