package myTest;

import myAdapter.*;

/**
 * Class with static methods used during Test Driven Development to initialize and create
 * HMap and other objects samples quickly.
 * @author  Nicola Maritan
 */
public class TestUtilities
{
    /**
     * Initializes a map with integers keys and string values
     * starting at from (included) and
     * ending at to (escluded). The map contains {to="to":from="from"}.
     * @param m map to be initialize
     * @param from lower bound (included) of the map
     * @param to upper bound (escluded) of the map
     * @throws NullPointerException if coll is null
     * @throws IllegalArgumentException if from {@literal >} to
     */
    public static void initHMap(HMap m, int from, int to)
    {
        if (m == null)
            throw new NullPointerException();
        if (from > to)
            throw new IllegalArgumentException();

        m.clear();

        for (int i = 0; i < to - from; i++)
            m.put(i + from, "" + (i + from));
    }

    /**
     * Returns an HMap containing integers starting at from (included) and
     * ending at to (escluded), where the string integer is the key and integer is the value
     * of the entry. The returned map contains {to="to":from="from"}.
     * @param from lower bound (included) of the map
     * @param to upper bound (escluded) of the map
     * @return an HMap from (included) and
     * ending at to (escluded).
     * @throws IllegalArgumentException if from {@literal >} to
     */
    public static HMap getIntegerMapAdapter(int from, int to)
    {
        if (from > to)
            throw new IllegalArgumentException();

        MapAdapter m = new MapAdapter();
        for (int i = from; i < to; i++)
            m.put(i, "" + i);
        return m;
    }

        /**
     * Adds at the end of the collection the integers starting at from (included) and
     * ending at to (escluded).
     * @param coll collection where to append new elements
     * @param from lower bound (included) of the map
     * @param to upper bound (escluded) of the map
     * @throws IllegalArgumentException if from {@literal >} to
     */
    public static void addToHMap(HMap m, int from, int to)
    {
        if (m == null)
            throw new NullPointerException();
        if (from > to)
            throw new IllegalArgumentException();

        for (int i = 0; i < to - from; i++)
            m.put(i + from, "" + (i + from));
    }

    /**
     * Returns an empty HMap.
     * @return an empty HMap
     */
    public static HMap getEmptyHMap()
    {
        return new MapAdapter();
    }

    /**
     * Returns an HMap.Entry of key key and value value. As the
     * HMap.Entry states: "The only way to obtain a reference to a map
     * entry is from the iterator of this collection-view.", which is
     * the way the method gets the Entry. Note that the entry is for
     * check purpouse only (testing containsAll, removeAll, retainAll
     * methods of the entrySet returned from HMap), as it is impossible
     * to directly insert an HMap.Entry to an entryset. 
     * @param key
     * @param value
     * @return the aforementioned Entry.
     * @throws NullPointerException if key is null or value is null
     */
    public static HMap.Entry getEntry(Object key, Object value)
    {
        if (key == null || value == null)
            throw new NullPointerException();
        
        // Returns the next object returned by an iterator of an entrySet containing that entry
        HMap m = new MapAdapter();
        m.put(key, value);
        HSet es = m.entrySet();
        HIterator it = es.iterator();
        return (HMap.Entry)it.next();
    }

    /**
     * Returns an HSet containing integers starting at from (included) and
     * ending at to (excluded).
     * @param from lower bound (included) of the set
     * @param to upper bound (excluded) of the set
     * @return an HSet from (included) and
     * ending at to (excluded).
     * @throws IllegalArgumentException if from {@literal >} to
     */
    public static HSet getIntegerHSet(int from, int to)
    {
        if (from > to)
            throw new IllegalArgumentException();

        // Uses ad-hoc HMap.keySet() as there is no other HCollection implementation
        HMap m = new MapAdapter();
        for (int i = from; i < to; i++)
            m.put(i, i);
        return m.keySet();
    }

    /**
     * Returns an HSet containing string representation of integers starting at from (included) and
     * ending at to (excluded).
     * @param from lower bound (included) of the set
     * @param to upper bound (excluded) of the set
     * @return an HSet from (included) and
     * ending at to (excluded).
     * @throws IllegalArgumentException if from {@literal >} to
     */
    public static HSet getStringHSet(int from, int to)
    {
        if (from > to)
            throw new IllegalArgumentException();

        HMap m = new MapAdapter();
        for (int i = from; i < to; i++)
            m.put(""+i, i);
        return m.keySet();
    }

    /**
     * Returns an HCollection containing integers starting at from (included) and
     * ending at to (excluded).
     * @param from lower bound (included) of the set
     * @param to upper bound (excluded) of the set
     * @return an HCollection from (included) and
     * ending at to (excluded).
     * @throws IllegalArgumentException if from {@literal >} to
     */
    public static HCollection getIntegerHCollection(int from, int to)
    {
        // Uses ad-hoc HMap.values() as there is no other HCollection implementation
        HMap m = new MapAdapter();
        for (int i = from; i < to; i++)
            m.put(i, i);
        return m.values();
    }

    /**
     * Returns a HCollection instance containing the elements
     * in the array.
     * @param arr array containing the elements to be added to
     * the HCollection
     * @return the aforementioned HCollection instance
     */
    public static HCollection getHCollection(Object[] arr)
    {
        if (arr == null)
            throw new NullPointerException();

        // Uses ad-hoc HMap.values() as there is no other HCollection implementation
        HMap m = new MapAdapter();
        for (int i = 0; i < arr.length; i++)
            m.put(i, arr[i]);
        return m.values();
    }

    /**
     * Returns an empty HCollection instance. 
     * @return an empty HCollection instance.
     */
    public static HCollection getEmptyHCollection()
    {
        return getHCollection(new Object[]{});
    }

    /**
     * Returns an HCollection containing string representation of integers starting at from (included) and
     * ending at to (excluded).
     * @param from lower bound (included) of the set
     * @param to upper bound (excluded) of the set
     * @return an HCollection from (included) and
     * ending at to (excluded).
     * @throws IllegalArgumentException if from {@literal >} to
     */
    public static HCollection getStringHCollection(int from, int to)
    {
        if (from > to)
            throw new IllegalArgumentException();

        // Uses ad-hoc HMap.values() as there is no other HCollection implementation
        HMap m = new MapAdapter();
        for (int i = from; i < to; i++)
            m.put(i, ""+i);
        return m.values();
    }

    /**
     * Returns an HCollection containing objects of class HMap.Entry
     * of type i="i", with integer key and string value, for i in
     * (from, to).
     * @param from lower bound of HMap.Entry key and value
     * @param to upper bound of HMap.Entry key and value
     * @return an HCollection containing objects of class HMap.Entry
     * of type i="i", with integer key and string value, for i in
     * (from, to).
     */
    public static HCollection getEntryHCollection(int from, int to)
    {
        if (from > to)
            throw new IllegalArgumentException();
        
        // Uses ad-hoc HMap.values() as there is no other HCollection implementation
        HMap m = new MapAdapter();
        for (int i = from; i < to; i++)
        {
            HMap.Entry e = TestUtilities.getEntry(i, "" + i);
            m.put(i, e);
        }
        return m.values();
    }

    /**
     * Returns an HSet containing objects of class HMap.Entry
     * of type i="i", with integer key and string value, for i in
     * (from, to).
     * @param from lower bound of HMap.Entry key and value
     * @param to upper bound of HMap.Entry key and value
     * @return an HCollection containing objects of class HMap.Entry
     * of type i="i", with integer key and string value, for i in
     * (from, to).
     */
    public static HSet getEntryHSet(int from, int to)
    {
        if (from > to)
            throw new IllegalArgumentException();

        // Uses ad-hoc HMap.keySet() as there is no other HSet implementation
        HMap m = new MapAdapter();
        for (int i = from; i < to; i++)
        {
            HMap.Entry e = TestUtilities.getEntry(i, "" + i);
            m.put(e, e);
        }
        return m.keySet();
    }
}