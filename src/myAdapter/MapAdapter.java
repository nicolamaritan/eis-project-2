package myAdapter;

import java.util.Enumeration;

public class MapAdapter implements HMap
{
    private Hashtable ht;

    public MapAdapter()
    {
        ht = new Hashtable();
    }

    public void clear()
    {
        ht.clear();
    }

    public boolean containsKey(Object key)
    {
        return ht.containsKey(key);
    }

    public boolean containsValue(Object value)
    {
        return false;
    }

    public Object get(Object key)
    {
        return ht.get(key);
    }

    public HSet entrySet()
    {
        return new EntrySet();
    }

    public int hashCode()
    {
        return ht.hashCode();
    }

    public boolean isEmpty()
    {
        return ht.isEmpty();
    }

    public HSet keySet()
    {
        /*HSet set = new SetAdapter();
        for (Enumeration e = ht.keys(); e.hasMoreElements(); )
            set.add(e.nextElement());
        return set;*/
        return null;
    }

    public Object put(Object key, Object value)
    {
        return ht.put(key, value);
    }

    public void putAll(HMap t)
    {
        
    }

    public Object remove(Object key)
    {
        return ht.remove(key);
    } 

    public int size()
    {
        return ht.size();
    }

    public HCollection values()
    {
        return null;
    }

    public class Entry
    {
        private Object value;
        private Object key;

        public boolean equals(Object o)
        {
            if (!(o instanceof Entry))
                return false;
            
            Entry oEntry = (Entry)o;

            return (this.getKey()==null ?
            oEntry.getKey()==null : this.getKey().equals(oEntry.getKey()))  &&
           (this.getValue()==null ?
           oEntry.getValue()==null : this.getValue().equals(oEntry.getValue()));
        }

        public Object getKey(){return key;}

        public Object getValue(){return value;}

        public int hashCode()
        {
            return (getKey()==null ? 0 : getKey().hashCode()) ^
            (getValue()==null ? 0 : getValue().hashCode());
        }
    }


    
    private class EntrySet implements HSet
    {
        public EntrySet()
        {
            
        }
    
        public boolean add(Object o)
        {
            throw new UnsupportedOperationException();
        }
    
        public boolean addAll(HCollection c)
        {
            throw new UnsupportedOperationException();
        }
    
        public void clear()
        {
            ht.clear();
        }
    
        public boolean contains(Object o)
        {
            if (!(o instanceof Entry))
                return false;
            Entry oEntry = (Entry)o;

            if (!ht.containsKey(oEntry.getKey()))
                return false;
            if (!ht.get(oEntry.getKey()).equals(oEntry.getValue()))
                return false;
            return true;
        }
    
        public boolean containsAll(HCollection c)
        {
            HIterator it = c.iterator();
            while (it.hasNext())
            {
                Object element = it.next();
                if (!this.contains(element))
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
            
            return hc;
        }
    
        public boolean isEmpty()
        {
            return ht.isEmpty();
        }
    
        public HIterator iterator()
        {
            return new EntrySetIterator();
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

        private class EntrySetIterator implements HIterator
        {
            //private Enumeration values;
            private Enumeration keys;
            private Object lastReturned;
    
            public EntrySetIterator()
            {
                keys = ht.keys();
                lastReturned = null;
            }
    
            public boolean hasNext()
            {
                return keys.hasMoreElements();
            }
    
            public Object next()
            {
                lastReturned = keys.nextElement();

                Entry returned = new Entry();
                returned.key = lastReturned;
                returned.value = MapAdapter.this.get(lastReturned);
                return returned;
            }
    
            public void remove()
            {
                MapAdapter.this.remove(lastReturned);
            }
        }
    }
}
