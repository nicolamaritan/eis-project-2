package myAdapter;

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
        return null;

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
}
