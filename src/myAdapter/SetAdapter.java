package myAdapter;

import java.util.Enumeration;

public class SetAdapter implements HSet
{
    private Hashtable ht;

    public SetAdapter()
    {
        ht = new Hashtable();
    }

    public boolean add(Object o)
    {
        if (ht.contains(o))
            return false;
        ht.put(o.hashCode(), o);
        return true;
    }

    public boolean addAll(HCollection c)
    {
        boolean modified = false;
        HIterator it = c.iterator();
        while (it.hasNext())
        {
            Object element = it.next();
            if (add(element))
                modified = true;
        }
        return modified;
    }

    public void clear()
    {
        ht.clear();
    }

    public boolean contains(Object o)
    {
        return ht.contains(o);
    }

    public boolean containsAll(HCollection c)
    {
        HIterator it = c.iterator();
        while (it.hasNext())
        {
            Object element = it.next();
            if (!ht.contains(element))
                return false;
        }
        return true;
    }

    public boolean equals(Object o)
    {
        if (!(o instanceof HSet))
            return false;
        HSet oSet = (HSet)o;

        if (oSet.size() != this.size())
            return false;

        HIterator it = oSet.iterator();
        while (it.hasNext())
        {
            Object element = it.next();
            if (!this.contains(element))
                return false;
        }
        
        // Same size and same elements.
        return true;
    }

    public int hashCode()
    {
        int hc = 0;
        
        HIterator it = this.iterator();
        while (it.hasNext())
        {
            if (it.next() == null)
                continue;
            hc += it.next().hashCode();
        }

        return hc;
    }

    public boolean isEmpty()
    {
        return ht.isEmpty();
    }

    public HIterator iterator()
    {
        return new SetAdapterIterator();
    }

    public boolean remove(Object o)
    {
        if (!contains(o))
            return false;
        ht.remove(o.hashCode());
        return true;
    }

    public boolean removeAll(HCollection c)
    {
        boolean modified = false;
        HIterator it = this.iterator();
        while (it.hasNext())
        {
            Object element = it.next();
            if (remove(element))
                modified = true;
        }
        return modified;
    }

    public boolean retainAll(HCollection c)
    {
        boolean modified = false;
        HIterator it = this.iterator();
        while (it.hasNext())
        {
            Object element = it.next();
            if (!c.contains(element))
            {
                this.remove(element);
                modified = true;
            }     
        }

        return modified;
    }

    public int size()
    {
        return ht.size();
    }

    public Object[] toArray()
    {
        Object[] arr = new Object[this.size()];
        int i = 0;
        for (Enumeration e = ht.elements(); e.hasMoreElements(); i++)
            arr[i] = e.nextElement();

        return arr;
    }

    public Object[] toArray(Object[] a)
    {
        if (a.length < this.size())
            throw new IllegalArgumentException();
        int i = 0;
        for (Enumeration e = ht.elements(); e.hasMoreElements(); i++)
            a[i] = e.nextElement();

        return a;
    }

    public String toString()
    {
        String res = "[";
        HIterator it = this.iterator();
        while (it.hasNext())
        {
            Object element = it.next();
            res += element;
            if (it.hasNext())
                res += ", ";
        }
        res += "]";
        return res;
    }

    private class SetAdapterIterator implements HIterator
    {
        private Enumeration values;
        private Object lastReturned;

        public SetAdapterIterator()
        {
            values = ht.elements();
            lastReturned = null;
        }

        public boolean hasNext()
        {
            return values.hasMoreElements();
        }

        public Object next()
        {
            lastReturned = values.nextElement();
            return lastReturned;
        }

        public void remove()
        {
            SetAdapter.this.remove(lastReturned);
        }
    }

}