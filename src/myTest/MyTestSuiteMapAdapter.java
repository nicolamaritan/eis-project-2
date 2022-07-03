package myTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import myAdapter.*;

public class MyTestSuiteMapAdapter 
{
    private MapAdapter m;

    @BeforeClass
    public static void BeforeClassMethod()
    {
        System.out.println(MyTestSuiteMapAdapter.class.getName() + " running.");
    }

    @Before
    public void BeforeMethod()
    {
        m = new MapAdapter();
    }

    // -------------------- clear, isEmpty --------------------

    /**
     * <p><b>Summary</b>: clear, isEmpty and size method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case of a
     * clear invoke on a empty map.</p>
     * <p><b>Test Description</b>: map is empty. clear method
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: map is empty.</p>
     * <p><b>Post-Condition</b>: map is still empty.</p>
     * <p><b>Expected Results</b>: isEmpty returns true and size is 0.</p>
     */
    @Test
    public void Clear_OnEmpty()
    {
        m.clear();
        assertTrue("Should be empty.", m.isEmpty());
        assertEquals(0, m.size());
    }

    /**
     * <p><b>Summary</b>: clear, isEmpty and size method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case of a
     * clear invoke on a map with only one element.</p>
     * <p><b>Test Description</b>: map is empty. clear method
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: map contains 1=1.</p>
     * <p><b>Post-Condition</b>: map is empty.</p>
     * <p><b>Expected Results</b>: isEmpty returns true and size is 0.</p>
     */
    @Test
    public void Clear_OneElement()
    {
        m.put(1 + "", 1);
        assertEquals(1, m.size());
        m.clear();
        assertTrue("Should be empty.", m.isEmpty());
        assertEquals(0, m.size());
    }

    /**
     * <p><b>Summary</b>: clear, isEmpty and size method test case.</p>
     * <p><b>Test Case Design</b>: Tests the case of a
     * clear invoke on a map containing 10 entries.</p>
     * <p><b>Test Description</b>: map contains 10 entries. clear method
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: map contains {1=1:10=10}.</p>
     * <p><b>Post-Condition</b>: map is empty.</p>
     * <p><b>Expected Results</b>: isEmpty returns true and size is 0.</p>
     */
    @Test
    public void Clear_On1To10()
    {
        TestUtilities.getIntegerMapAdapter(0, 10);
        m.clear();
        assertTrue("Should be empty.", m.isEmpty());
        assertEquals(0, m.size());
    }

    /**
     * <p><b>Summary</b>: clear, isEmpty and size method test case.</p>
     * <p><b>Test Case Design</b>: Tests the case of a
     * clear invoke on a map containing 10000 entries, which is a
     * case with a big number of elements.</p>
     * <p><b>Test Description</b>: map contains 10000 entries. clear method
     * is invoked.</p>
     * <p><b>Pre-Condition</b>: map contains {1=1:10000=10000}.</p>
     * <p><b>Post-Condition</b>: map is empty.</p>
     * <p><b>Expected Results</b>: isEmpty returns true and size is 0.</p>
     */
    @Test
    public void Clear_On1To10000()
    {
        TestUtilities.getIntegerMapAdapter(0, 10000);
        m.clear();
        assertTrue("Should be empty.", m.isEmpty());
        assertEquals(0, m.size());
    }

    // -------------------- containsKey --------------------

    /**
     * <p><b>Summary</b>: containsKey method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case of
     * invoking the method in an empty map, which should always
     * return false.</p>
     * <p><b>Test Description</b>: A,B,C,D are tested to be in
     * the map as keys.</p>
     * <p><b>Pre-Condition</b>: map is Empty.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: Each containsKey returns false.</p>
     */
    @Test
    public void ContainsKey_4LettersEmpty()
    {
        assertFalse("Should not contain.", m.containsKey("A"));
        assertFalse("Should not contain.", m.containsKey("B"));
        assertFalse("Should not contain.", m.containsKey("C"));
        assertFalse("Should not contain.", m.containsKey("D"));
    }

    /**
     * <p><b>Summary</b>: containsKey method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case of
     * invoking the method in map.</p>
     * <p><b>Test Description</b>: A,B,C,D are tested to be in
     * the map as keys.</p>
     * <p><b>Pre-Condition</b>: map contains the first 4 letters.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: Each containsKey returns true.</p>
     */
    @Test
    public void ContainsKey_4Letters0()
    {
        m.put("A", "a");
        m.put("B", "b");
        m.put("C", "c");
        m.put("D", "d");

        assertTrue("Should contain.", m.containsKey("A"));
        assertTrue("Should contain.", m.containsKey("B"));
        assertTrue("Should contain.", m.containsKey("C"));
        assertTrue("Should contain.", m.containsKey("D"));
    }

    /**
     * <p><b>Summary</b>: containsKey method test case.</p>
     * <p><b>Test Case Design</b>: Tests the case of
     * invoking the method in map with keys in and out.</p>
     * <p><b>Test Description</b>: A,B,C,D are tested to be in
     * the map as keys.</p>
     * <p><b>Pre-Condition</b>: map contains A and C as keys.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: A and C are contained, the others no.</p>
     */
    @Test
    public void ContainsKey_4Letters1()
    {
        m.put("A", "a");
        m.put("C", "c");

        assertTrue("Should contain.", m.containsKey("A"));
        assertFalse("Should not contain.", m.containsKey("B"));
        assertTrue("Should contain.", m.containsKey("C"));
        assertFalse("Should not contain.", m.containsKey("D"));
    }

    /**
     * <p><b>Summary</b>: containsKey method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case of
     * invoking the method in map with a big size.</p>
     * <p><b>Test Description</b>: {0=0, 100000=100000} are 
     * checked to be in the list or not.</p>
     * <p><b>Pre-Condition</b>: map contains {0=0, 10000=10000}.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: {0 : 10000} keys are contained,
     * the others no.</p>
     */
    @Test
    public void ContainsKey_0To10000()
    {
        int bound = 10000;
        int bound2 = 100000;
        TestUtilities.initHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
            assertTrue("Should contain.", m.containsKey(i));
        for (int i = bound; i < bound2; i++)
            assertFalse("Should not contain.", m.containsKey(i));
        
    }

    // -------------------- containsValue --------------------

    /**
     * <p><b>Summary</b>: containsValue method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case of
     * invoking the method in an empty map, which should always
     * return false.</p>
     * <p><b>Test Description</b>: a, b, c, d are tested to be in
     * the map as values.</p>
     * <p><b>Pre-Condition</b>: map is empty.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: Each containsKey returns false.</p>
     */
    @Test
    public void ContainsValue_4LettersEmpty()
    {
        assertFalse("Should not contain.", m.containsValue("a"));
        assertFalse("Should not contain.", m.containsValue("b"));
        assertFalse("Should not contain.", m.containsValue("c"));
        assertFalse("Should not contain.", m.containsValue("d"));
    }

    /**
     * <p><b>Summary</b>: containsKey method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case of
     * invoking the method in map.</p>
     * <p><b>Test Description</b>: a, b, c, d are tested to be in
     * the map as values.</p>
     * <p><b>Pre-Condition</b>: map contains the first 4 letters.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: Each containsKey returns true.</p>
     */
    @Test
    public void ContainsValue_4Letters0()
    {
        m.put("A", "a");
        m.put("B", "b");
        m.put("C", "c");
        m.put("D", "d");

        assertTrue("Should contain.", m.containsValue("a"));
        assertTrue("Should contain.", m.containsValue("b"));
        assertTrue("Should contain.", m.containsValue("c"));
        assertTrue("Should contain.", m.containsValue("d"));
    }

    /**
     * <p><b>Summary</b>: containsKey method test case.</p>
     * <p><b>Test Case Design</b>: Tests the case of
     * invoking the method in map with keys in and out.</p>
     * <p><b>Test Description</b>: a,b,c,d are tested to be in
     * the map as keys.</p>
     * <p><b>Pre-Condition</b>: map contains A and C as keys.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: a and c are contained, the others no.</p>
     */
    @Test
    public void ContainsValue_4Letters1()
    {
        m.put("A", "a");
        m.put("C", "c");

        assertTrue("Should contain.", m.containsValue("a"));
        assertFalse("Should not contain.", m.containsValue("b"));
        assertTrue("Should contain.", m.containsValue("c"));
        assertFalse("Should not contain.", m.containsValue("d"));
    }

    /**
     * <p><b>Summary</b>: containsValue method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case of
     * invoking the method in map with a big size.</p>
     * <p><b>Test Description</b>: {0=0, 100000=100000} are 
     * checked to be in the list or not.</p>
     * <p><b>Pre-Condition</b>: map contains {0=0, 10000=10000}.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: {0 : 10000} values are contained,
     * the others no.</p>
     */
    @Test
    public void ContainsValue_0To10000()
    {
        int bound = 10000;
        int bound2 = 100000;
        TestUtilities.initHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
            assertTrue("Should contain.", m.containsValue(i));
        for (int i = bound; i < bound2; i++)
            assertFalse("Should not contain.", m.containsValue(i));
        
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
