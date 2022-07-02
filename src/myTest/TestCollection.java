package myTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import myAdapter.*;

public class TestCollection {
	
	/**
	 * @safe.precondition
	 * @safe.postcondition
	 * @safe.summary
	 */

	HCollection head = new CollectionAdapter();
	boolean h = head.add("Collection Adapter");
	
	HCollection param = new CollectionAdapter();
	boolean aa = param.add("aaa");
	boolean bb = param.add("bbb");
	boolean cc = param.add("ccc");
	
	HCollection intersect = new CollectionAdapter();
	boolean aaa = intersect.add("aaa");
	boolean bbb = intersect.add("bbb");
	boolean ccc = intersect.add("ccc");
	
	HCollection ct = new CollectionAdapter();
	boolean first = ct.add("Collection Adapter");
	boolean a = ct.add("aaa");
	boolean b = ct.add("bbb");
	boolean c = ct.add("ccc");
	
	/**
     * <p><b>Summary</b>: add method test case.</p>
     * <p><b>Test Case Design</b>: Checks if adding one element
	 * through collection add returns true.</p>
     * <p><b>Test Description</b>: add one string to collection.</p>
     * <p><b>Pre-Condition</b>: ct is at its predefined state.</p>
     * <p><b>Post-Condition</b>: ct has one more elem.</p>
     * <p><b>Expected Results</b>: ris is true.</p>
     */
	@Test
	public void add_o() {
		boolean ris = ct.add("add(Object o) funziona");
		assertTrue(ris);
	}
	
	/**
     * <p><b>Summary</b>: addAll method test case.</p>
     * <p><b>Test Case Design</b>: Checks if adding all element
	 * through addAll returns true.</p>
     * <p><b>Test Description</b>: add one collection.</p>
     * <p><b>Pre-Condition</b>: ct is at its actual state.</p>
     * <p><b>Post-Condition</b>: collection is added.</p>
     * <p><b>Expected Results</b>: ris is true</p>
     */
	@Test
	public void addAll_c() {
		boolean ris = ct.addAll(param);
		assertTrue(ris);
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
}

    /**
     * <p><b>Summary</b>:</p>
     * <p><b>Test Case Design</b>:</p>
     * <p><b>Test Description</b>:</p>
     * <p><b>Pre-Condition</b>:</p>
     * <p><b>Post-Condition</b>:</p>
     * <p><b>Expected Results</b>:</p>
     */
