package myAdapter;

import java.util.Enumeration;

public class Hashtable
{   
    private java.util.Hashtable ht;

    public void clear()
    {
        ht.clear();
    }

    public void contains(Object value)
    {
        ht.contains(value);
    }

    public void containsKey(Object key)
    {
        ht.containsKey(key);
    }

    public Enumeration elements()
    {
        return ht.elements();
    }

    public Object get(Object key)
    {
        return ht.get(key);
    }

    public Object put(Object key, Object value)
    {
        return ht.put(key, value);
    }

    public Object remove(Object key)
    {
        return ht.remove(key);
    }

    public int size()
    {
        return ht.size();
    }

    public String toString()
    {
        return ht.toString();
    }
}