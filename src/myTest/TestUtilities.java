package myTest;

import myAdapter.*;

/**
 * Class with static methods used during TDD to initialize and create
 * HMap and other objects samples quickly.
 */
public class TestUtilities
{
    /**
     * Returns an arrau containing integers starting at from (included) and
     * ending at to (escluded). That means the array is empty if and only if
     * from == to.
     * @param from collection to be initialize
     * @param to lower bound (included) of the list
     * @return the array.
     * @throws IllegalArgumentException if from {@literal >} to
     */
    public static Object[] getIntegerArray(int from, int to)
    {
        if (from > to)
            throw new IllegalArgumentException();
        Object[] arr = new Object[to - from];
        for (int i = from; i < to; i++)
            arr[i - from] = i;
        return arr;
    }

    /**
     * Initializes a collection with integers starting at from (included) and
     * ending at to (escluded).
     * @param coll collection to be initialize
     * @param from lower bound (included) of the list
     * @param to upper bound (escluded) of the list
     * @throws NullPointerException if coll is null
     * @throws IllegalArgumentException if from {@literal >} to
     */
    public static void initHCollection(HCollection coll, int from, int to)
    {
        if (coll == null)
            throw new NullPointerException();
        if (from > to)
            throw new IllegalArgumentException();

        coll.clear();

        for (int i = 0; i < to - from; i++)
            coll.add(i + from);
    }

    /**
     * Initializes a map with integers keys and string values
     * starting at from (included) and
     * ending at to (escluded).
     * @param m map to be initialize
     * @param from lower bound (included) of the list
     * @param to upper bound (escluded) of the list
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
     * of the entry.
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
     * @param from lower bound (included) of the list
     * @param to upper bound (escluded) of the list
     * @throws IllegalArgumentException if from {@literal >} to
     */
    public static void addToCollection(HCollection coll, int from, int to)
    {
        if (coll == null)
            throw new NullPointerException();
        if (from > to)
            throw new IllegalArgumentException();

        for (int i = 0; i < to - from; i++)
            coll.add(i + from);
    }

        /**
     * Adds at the end of the collection the integers starting at from (included) and
     * ending at to (escluded).
     * @param coll collection where to append new elements
     * @param from lower bound (included) of the list
     * @param to upper bound (escluded) of the list
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
     * Returns an HMap.Entry of key key and value value
     * @param key
     * @param value
     * @return the aforementioned Entry.
     */
    public static HMap.Entry getEntry(Object key, Object value)
    {
        HMap m = new MapAdapter();
        m.put(key, value);
        HSet es = m.entrySet();
        HIterator it = es.iterator();
        return (HMap.Entry)it.next();
    }

    public static HCollection getEntryHCollection(int from, int to)
    {
        HCollection c = new CollectionAdapter();
        for (int i = from; i < to; i++)
        {
            HMap.Entry e = TestUtilities.getEntry(i, "" + i);
            c.add(e);
        }
        return c;
    }
}