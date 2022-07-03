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
    public static void initCollection(HCollection coll, int from, int to)
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
     * Initializes a map with integers starting at from (included) and
     * ending at to (escluded).
     * @param coll map to be initialize
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
            m.put(i + from, i + from);
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

        MapAdapter list = new MapAdapter();
        for (int i = from; i < to; i++)
            list.put("" + i, i);
        return list;
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
     * Returns an empty HMap.
     * @return an empty HMap
     */
    public static HMap getEmptyHMap()
    {
        return new MapAdapter();
    }
}