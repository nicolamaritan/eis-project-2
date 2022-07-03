package myTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import myAdapter.*;

public class MyTestSuiteMapAdapter 
{
    private MapAdapter m, m2;

    @BeforeClass
    public static void BeforeClassMethod()
    {
        System.out.println(MyTestSuiteMapAdapter.class.getName() + " running.");
    }

    @Before
    public void BeforeMethod()
    {
        m = new MapAdapter();
        m2 = new MapAdapter();
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
     * checked to be in the map or not.</p>
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
     * checked to be in the map or not.</p>
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
            assertTrue("Should contain.", m.containsValue(""+i));
        for (int i = bound; i < bound2; i++)
            assertFalse("Should not contain.", m.containsValue(i));
        
    }

    /**
     * <p><b>Summary</b>: containsValue method test case.</p>
     * <p><b>Test Case Design</b>: Tests the limit case of
     * invoking the method in map with a big size. Values contained
     * are duplicated, therefore the method must still work correctly.</p>
     * <p><b>Test Description</b>: {0=0, 100000=100000} are 
     * checked to be in the map or not.</p>
     * <p><b>Pre-Condition</b>: map contains {0=0, 10000=10000}.</p>
     * <p><b>Post-Condition</b>: map is unchanged.</p>
     * <p><b>Expected Results</b>: {0 : 10000} values are contained,
     * the others no.</p>
     */
    @Test
    public void ContainsValue_0To10000Duplicates()
    {
        int bound = 10000;
        int bound2 = 100000;
        TestUtilities.initHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
            m.put(i + bound, i);

        for (int i = 0; i < bound; i++)
            assertTrue("Should contain.", m.containsValue(i));
        for (int i = bound; i < bound2; i++)
            assertFalse("Should not contain.", m.containsValue(i));
        
    }

    /**
     * <p><b>Summary</b>: get method test case.</p>
     * <p><b>Test Case Design</b>: Tests get behaviour when the
     * map is empty, which is a limit case.</p>
     * <p><b>Test Description</b>: get is invoked on an empty map
     * with a random key.</p>
     * <p><b>Pre-Condition</b>: m is empty</p>
     * <p><b>Post-Condition</b>: m is unchanged</p>
     * <p><b>Expected Results</b>: null is returned</p>
     */
    @Test
    public void Get_Empty()
    {
        assertNull("Should be null", m.get("Random Key"));
    }

    /**
     * <p><b>Summary</b>: get method test case.</p>
     * <p><b>Test Case Design</b>: Tries to get a value
     * of a key not in the map, where the map is not empty.</p>
     * <p><b>Test Description</b>: Gets a not in map key.</p>
     * <p><b>Pre-Condition</b>: Map contains {0=0:10=10}.</p>
     * <p><b>Post-Condition</b>: Map is unchanged.</p>
     * <p><b>Expected Results</b>: m.get returns null.</p>
     */
    @Test
    public void Get_OneElementNotInMap()
    {
        TestUtilities.initHMap(m, 0, 10);
        assertNull("Should be null", m.get(11));
    }
    
    /**
     * <p><b>Summary</b>: get method test case.</p>
     * <p><b>Test Case Design</b>: Tests get behaviour when the
     * map contains a big number of element.</p>
     * <p><b>Test Description</b>: get is invoked with keys
     * from 1000 to 2000.</p>
     * <p><b>Pre-Condition</b>: m contains {0=0:1000=1000}</p>
     * <p><b>Post-Condition</b>: m is unchanged</p>
     * <p><b>Expected Results</b>: each get returns null.</p>
     */
    @Test
    public void Get_0To1000NotInMap()
    {
        int bound = 1000;
        TestUtilities.initHMap(m, 0, bound);
        for (int i = bound; i < 2* bound; i++)
            assertNull("Should be null", m.get(i));
    }

    /**
     * <p><b>Summary</b>: get method test case.</p>
     * <p><b>Test Case Design</b>: Tests get behaviour when the
     * map contains only one element, which is a limit case.</p>
     * <p><b>Test Description</b>: get is invoked with "1" key.</p>
     * <p><b>Pre-Condition</b>: m contains {1=1}</p>
     * <p><b>Post-Condition</b>: m is unchanged</p>
     * <p><b>Expected Results</b>: "1" is returned</p>
     */
    @Test
    public void Get_OneElement()
    {
        m.put("1", 1);
        assertEquals(1, m.get("1"));
    }

    /**
     * <p><b>Summary</b>: get method test case.</p>
     * <p><b>Test Case Design</b>: Tests get behaviour when the
     * map contains a big number of element.</p>
     * <p><b>Test Description</b>: get is invoked with keys
     * from 0 to 1000.</p>
     * <p><b>Pre-Condition</b>: m contains {0=0:1000=1000}</p>
     * <p><b>Post-Condition</b>: m is unchanged</p>
     * <p><b>Expected Results</b>: each get returns i in string form.</p>
     */
    @Test
    public void Get_0To1000()
    {
        int bound = 1000;
        TestUtilities.initHMap(m, 0, bound);
        for (int i = 0; i < bound; i++)
            assertEquals("" + i, m.get(i));
    }

    /**
     * <p><b>Summary</b>: hashCode test case.
     * Tests the behaviour of hashCode method with different
     * configurations.</p>
     * <p><b>Test Case Design</b>: The same operations are applied to map 1 and 2,
     * so they must have the same elements each time, therefore they are equals.
     * If they are equals they must have the same hashCode.</p>
     * <p><b>Test Description</b>: Different configurations have been tested. In mathematical symbols:
     * empty, {1}, {-100:100}, {-100:100}/{0}, {-100:101}/{0}, {-100:100, 500:1000}/{0},
     * {-100:100, 500:1000} U {-1000:-900}/({0} ), {-100:100, 500:1000, 5000:6000}  U {-1000:-900}/({0})</p>
     * <p><b>Pre-Condition</b>: maps have same hashCode and they are equal.</p>
     * <p><b>Post-Condition</b>: maps have same hashCode and they are equal.</p>
     * <p><b>Expected Results</b>: maps have same hashCode and they are equal.</p>
     */
    @Test
    public void HashCode_Mixed()
    {
        // Empty map case
        assertEquals("maps should be equal.", true, m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        // One element case
        m.put(1, "1");
        m2.put(1, "1");
        assertEquals("maps should be equal.", true, m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        TestUtilities.initHMap(m, -100, 100);
        TestUtilities.initHMap(m2, -100, 100);
        assertEquals("maps should be equal.", true, m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        m.remove((Object)0);
        m2.remove((Object)0);
        assertEquals("maps should be equal.", true, m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        m.put(101, "101");
        m2.put(101, "101");
        assertEquals("maps should be equal.", true, m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        TestUtilities.addToHMap(m, 500, 1000);
        TestUtilities.addToHMap(m2, 500, 1000);
        assertEquals("maps should be equal.", true, m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        HMap t = TestUtilities.getIntegerMapAdapter(-1000, -900);
        m.putAll(t);
        m2.putAll(t);
        assertEquals("maps should be equal.", true, m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

        TestUtilities.initHMap(t, 5000, 6000);
        m.putAll(t);
        m2.putAll(t);
        assertEquals("maps should be equal.", true, m.equals(m2));
        assertEquals("Hash codes should be equal.", m.hashCode(), m2.hashCode());

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
