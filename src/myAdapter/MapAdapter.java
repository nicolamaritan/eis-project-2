package myAdapter;

import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * An object that maps keys to values.  A map cannot contain duplicate keys;
 * each key can map to at most one value.
 *
 * <p>This interface takes the place of the {@code Dictionary} class, which
 * was a totally abstract class rather than an interface.
 *
 * <p>The {@code Map} interface provides three <i>collection views</i>, which
 * allow a map's contents to be viewed as a set of keys, collection of values,
 * or set of key-value mappings.  The <i>order</i> of a map is defined as
 * the order in which the iterators on the map's collection views return their
 * elements.  Some map implementations, like the {@code TreeMap} class, make
 * specific guarantees as to their order; others, like the {@code HashMap}
 * class, do not.
 *
 * <p>Note: great care must be exercised if mutable objects are used as map
 * keys.  The behavior of a map is not specified if the value of an object is
 * changed in a manner that affects {@code equals} comparisons while the
 * object is a key in the map.  A special case of this prohibition is that it
 * is not permissible for a map to contain itself as a key.  While it is
 * permissible for a map to contain itself as a value, extreme caution is
 * advised: the {@code equals} and {@code hashCode} methods are no longer
 * well defined on such a map.
 *
 * <p>All general-purpose map implementation classes should provide two
 * "standard" constructors: a void (no arguments) constructor which creates an
 * empty map, and a constructor with a single argument of type {@code Map},
 * which creates a new map with the same key-value mappings as its argument.
 * In effect, the latter constructor allows the user to copy any map,
 * producing an equivalent map of the desired class.  There is no way to
 * enforce this recommendation (as interfaces cannot contain constructors) but
 * all of the general-purpose map implementations in the JDK comply.
 *
 * <p>The "destructive" methods contained in this interface, that is, the
 * methods that modify the map on which they operate, are specified to throw
 * {@code UnsupportedOperationException} if this map does not support the
 * operation.  If this is the case, these methods may, but are not required
 * to, throw an {@code UnsupportedOperationException} if the invocation would
 * have no effect on the map.  For example, invoking the {@link #putAll(Map)}
 * method on an unmodifiable map may, but is not required to, throw the
 * exception if the map whose mappings are to be "superimposed" is empty.
 *
 * <p>Some map implementations have restrictions on the keys and values they
 * may contain.  For example, some implementations prohibit null keys and
 * values, and some have restrictions on the types of their keys.  Attempting
 * to insert an ineligible key or value throws an unchecked exception,
 * typically {@code NullPointerException} or {@code ClassCastException}.
 * Attempting to query the presence of an ineligible key or value may throw an
 * exception, or it may simply return false; some implementations will exhibit
 * the former behavior and some will exhibit the latter.  More generally,
 * attempting an operation on an ineligible key or value whose completion
 * would not result in the insertion of an ineligible element into the map may
 * throw an exception or it may succeed, at the option of the implementation.
 * Such exceptions are marked as "optional" in the specification for this
 * interface.
 *
 * <p>Many methods in Collections Framework interfaces are defined
 * in terms of the {@link Object#equals(Object) equals} method.  For
 * example, the specification for the {@link #containsKey(Object)
 * containsKey(Object key)} method says: "returns {@code true} if and
 * only if this map contains a mapping for a key {@code k} such that
 * {@code (key==null ? k==null : key.equals(k))}." This specification should
 * <i>not</i> be construed to imply that invoking {@code Map.containsKey}
 * with a non-null argument {@code key} will cause {@code key.equals(k)} to
 * be invoked for any key {@code k}.  Implementations are free to
 * implement optimizations whereby the {@code equals} invocation is avoided,
 * for example, by first comparing the hash codes of the two keys.  (The
 * {@link Object#hashCode()} specification guarantees that two objects with
 * unequal hash codes cannot be equal.)  More generally, implementations of
 * the various Collections Framework interfaces are free to take advantage of
 * the specified behavior of underlying {@link Object} methods wherever the
 * implementor deems it appropriate.
 *
 * <p>Some map operations which perform recursive traversal of the map may fail
 * with an exception for self-referential instances where the map directly or
 * indirectly contains itself. This includes the {@code clone()},
 * {@code equals()}, {@code hashCode()} and {@code toString()} methods.
 * Implementations may optionally handle the self-referential scenario, however
 * most current implementations do not do so.
 *
 * <h2><a id="unmodifiable">Unmodifiable Maps</a></h2>
 * <p>The {@link Map#of() Map.of},
 * {@link Map#ofEntries(Map.Entry...) Map.ofEntries}, and
 * {@link Map#copyOf Map.copyOf}
 * static factory methods provide a convenient way to create unmodifiable maps.
 * The {@code Map}
 * instances created by these methods have the following characteristics:
 *
 * <ul>
 * <li>They are <a href="Collection.html#unmodifiable"><i>unmodifiable</i></a>. Keys and values
 * cannot be added, removed, or updated. Calling any mutator method on the Map
 * will always cause {@code UnsupportedOperationException} to be thrown.
 * However, if the contained keys or values are themselves mutable, this may cause the
 * Map to behave inconsistently or its contents to appear to change.
 * <li>They disallow {@code null} keys and values. Attempts to create them with
 * {@code null} keys or values result in {@code NullPointerException}.
 * <li>They are serializable if all keys and values are serializable.
 * <li>They reject duplicate keys at creation time. Duplicate keys
 * passed to a static factory method result in {@code IllegalArgumentException}.
 * <li>The iteration order of mappings is unspecified and is subject to change.
 * <li>They are <a href="../lang/doc-files/ValueBased.html">value-based</a>.
 * Callers should make no assumptions about the identity of the returned instances.
 * Factories are free to create new instances or reuse existing ones. Therefore,
 * identity-sensitive operations on these instances (reference equality ({@code ==}),
 * identity hash code, and synchronization) are unreliable and should be avoided.
 * <li>They are serialized as specified on the
 * <a href="{@docRoot}/serialized-form.html#java.util.CollSer">Serialized Form</a>
 * page.
 * </ul>
 *
 *
 * @author Nicola Maritan
 * @see HCollection
 * @see HSet
 * @since 1.0
 */
public class MapAdapter implements HMap
{
    private Hashtable ht;

    public MapAdapter()
    {
        ht = new Hashtable();
    }

    /**
     * Removes all of the mappings from this map (optional operation).
     * The map will be empty after this call returns.
     */
    public void clear()
    {
        ht.clear();
    }

    /**
     * Returns {@code true} if this map contains a mapping for the specified
     * key.  More formally, returns {@code true} if and only if
     * this map contains a mapping for a key {@code k} such that
     * {@code Objects.equals(key, k)}.  (There can be
     * at most one such mapping.)
     *
     * @param key key whose presence in this map is to be tested
     * @return {@code true} if this map contains a mapping for the specified
     *         key
     * (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key is null and this map
     *         does not permit null keys
     * (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    public boolean containsKey(Object key)
    {
        return ht.containsKey(key);
    }

    /**
     * Returns {@code true} if this map maps one or more keys to the
     * specified value.  More formally, returns {@code true} if and only if
     * this map contains at least one mapping to a value {@code v} such that
     * {@code Objects.equals(value, v)}.  This operation
     * will probably require time linear in the map size for most
     * implementations of the {@code Map} interface.
     *
     * @param value value whose presence in this map is to be tested
     * @return {@code true} if this map maps one or more keys to the
     *         specified value
     * (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified value is null and this
     *         map does not permit null values
     * (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    public boolean containsValue(Object value)
    {
        return ht.contains(value);
    }
    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that
     * {@code Objects.equals(key, k)},
     * then this method returns {@code v}; otherwise
     * it returns {@code null}.  (There can be at most one such mapping.)
     *
     * <p>If this map permits null values, then a return value of
     * {@code null} does not <i>necessarily</i> indicate that the map
     * contains no mapping for the key; it's also possible that the map
     * explicitly maps the key to {@code null}.  The {@link #containsKey
     * containsKey} operation may be used to distinguish these two cases.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or
     *         {@code null} if this map contains no mapping for the key
     * @throws ClassCastException if the key is of an inappropriate type for
     *         this map
     * (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key is null and this map
     *         does not permit null keys
     * (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    public Object get(Object key)
    {
        return ht.get(key);
    }

    /**
     * Returns a {@link HSet} view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own {@code remove} operation, or through the
     * {@code setValue} operation on a map entry returned by the
     * iterator) the results of the iteration are undefined.  The set
     * supports element removal, which removes the corresponding
     * mapping from the map, via the {@code Iterator.remove},
     * {@code Set.remove}, {@code removeAll}, {@code retainAll} and
     * {@code clear} operations.  It does not support the
     * {@code add} or {@code addAll} operations.
     *
     * @return a set view of the mappings contained in this map
     */
    public HSet entrySet()
    {
        return new EntrySet();
    }

    public int hashCode()
    {
        return ht.hashCode();
    }

    /**
     * Returns {@code true} if this map contains no key-value mappings.
     *
     * @return {@code true} if this map contains no key-value mappings
     */
    public boolean isEmpty()
    {
        return ht.isEmpty();
    }

    /**
     * Returns a {@link HSet} view of the keys contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own {@code remove} operation), the results of
     * the iteration are undefined.  The set supports element removal,
     * which removes the corresponding mapping from the map, via the
     * {@code HIterator.remove}, {@code HSet.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear}
     * operations.  It does not support the {@code add} or {@code addAll}
     * operations.
     *
     * @return a set view of the keys contained in this map
     */
    public HSet keySet()
    {
        return new KeySet();
    }

    /**
     * Associates the specified value with the specified key in this map
     * (optional operation).  If the map previously contained a mapping for
     * the key, the old value is replaced by the specified value.  (A map
     * {@code m} is said to contain a mapping for a key {@code k} if and only
     * if {@link #containsKey(Object) m.containsKey(k)} would return
     * {@code true}.)
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with {@code key}, or
     *         {@code null} if there was no mapping for {@code key}.
     *         (A {@code null} return can also indicate that the map
     *         previously associated {@code null} with {@code key},
     *         if the implementation supports {@code null} values.)
     * @throws NullPointerException if the specified key or value is null
     *         and this map does not permit null keys or values
     */
    public Object put(Object key, Object value)
    {
        return ht.put(key, value);
    }

    /**
     * Copies all of the mappings from the specified map to this map
     * (optional operation).  The effect of this call is equivalent to that
     * of calling {@link #put(Object,Object) put(k, v)} on this map once
     * for each mapping from key {@code k} to value {@code v} in the
     * specified map.  The behavior of this operation is undefined if the
     * specified map is modified while the operation is in progress.
     *
     * @param m mappings to be stored in this map
     * @throws NullPointerException if the specified map is null, or if
     *         this map does not permit null keys or values, and the
     *         specified map contains null keys or values
     */
    public void putAll(HMap t)
    {
        HSet tEntrySet = t.entrySet();
        HIterator it = tEntrySet.iterator();
        while (it.hasNext())
        {
            Entry entry = (Entry)it.next();
            this.put(entry.getKey(), entry.getValue());
        }
    }
    /**
     * Removes the mapping for a key from this map if it is present
     * (optional operation).   More formally, if this map contains a mapping
     * from key {@code k} to value {@code v} such that
     * {@code Objects.equals(key, k)}, that mapping
     * is removed.  (The map can contain at most one such mapping.)
     *
     * <p>Returns the value to which this map previously associated the key,
     * or {@code null} if the map contained no mapping for the key.
     *
     * <p>If this map permits null values, then a return value of
     * {@code null} does not <i>necessarily</i> indicate that the map
     * contained no mapping for the key; it's also possible that the map
     * explicitly mapped the key to {@code null}.
     *
     * <p>The map will not contain a mapping for the specified key once the
     * call returns.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with {@code key}, or
     *         {@code null} if there was no mapping for {@code key}.
     * @throws NullPointerException if the specified key is null and this
     *         map does not permit null keys
     * (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    public Object remove(Object key)
    {
        return ht.remove(key);
    } 

    /**
     * Returns the number of key-value mappings in this map.  If the
     * map contains more than {@code Integer.MAX_VALUE} elements, returns
     * {@code Integer.MAX_VALUE}.
     *
     * @return the number of key-value mappings in this map
     */
    public int size()
    {
        return ht.size();
    }

    /**
     * Returns a {@link HCollection} view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are
     * reflected in the collection, and vice-versa.  If the map is
     * modified while an iteration over the collection is in progress
     * (except through the iterator's own {@code remove} operation),
     * the results of the iteration are undefined.  The collection
     * supports element removal, which removes the corresponding
     * mapping from the map, via the {@code HIterator.remove},
     * {@code HCollection.remove}, {@code removeAll},
     * {@code retainAll} and {@code clear} operations.  It does not
     * support the {@code add} or {@code addAll} operations.
     *
     * @return a collection view of the values contained in this map
     */
    public HCollection values()
    {
        return new Values();
    }

    @Override
    public String toString()
    {
        HSet es = this.entrySet();
        String res = es.toString();
        res = res.substring(1, res.length() - 1);
        res += "}";
        res = "{" + res;
        return res;
    }

    public class MapAdapterEntry implements HMap.Entry
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

        /**
         * Returns the key corresponding to this entry.
         *
         * @return the key corresponding to this entry
         */
        public Object getKey(){return key;}

            /**
         * Returns the value corresponding to this entry.  If the mapping
         * has been removed from the backing map (by the iterator's
         * {@code remove} operation), the results of this call are undefined.
         *
         * @return the value corresponding to this entry
         */
        public Object getValue(){return value;}

        public int hashCode()
        {
            return (getKey()==null ? 0 : getKey().hashCode()) ^
            (getValue()==null ? 0 : getValue().hashCode());
        }

        /**
         * Replaces the value corresponding to this entry with the specified
         * value (optional operation).  (Writes through to the map.)  The
         * behavior of this call is undefined if the mapping has already been
         * removed from the map (by the iterator's {@code remove} operation).
         *
         * @param value new value to be stored in this entry
         * @return old value corresponding to the entry
         */
        public Object setValue(Object value)
        {
            Object oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public String toString()
        {
            return key.toString() + "=" + value.toString();
        }
    }

    
    private class EntrySet implements HSet
    {   
        /**
         * Adds the specified element to this set if it is not already present
         * (optional operation).  More formally, adds the specified element
         * {@code e} to this set if the set contains no element {@code e2}
         * such that
         * {@code Objects.equals(e, e2)}.
         * If this set already contains the element, the call leaves the set
         * unchanged and returns {@code false}.  In combination with the
         * restriction on constructors, this ensures that sets never contain
         * duplicate elements.
         *
         * <p>The stipulation above does not imply that sets must accept all
         * elements; sets may refuse to add any particular element, including
         * {@code null}, and throw an exception, as described in the
         * specification for {@link HCollection#add HCollection.add}.
         * Individual set implementations should clearly document any
         * restrictions on the elements that they may contain.
         *
         * @param e element to be added to this set
         * @return {@code true} if this set did not already contain the specified
         *         element
         * @throws UnsupportedOperationException as the operation is not supported
         */
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
    
        /**
         * Returns {@code true} if this set contains the specified element.
         * More formally, returns {@code true} if and only if this set
         * contains an element {@code e} such that
         * {@code Objects.equals(o, e)}.
         *
         * @param o element whose presence in this set is to be tested
         * @return {@code true} if this set contains the specified element
         * @throws ClassCastException if the type of the specified element
         *         is incompatible with this set
         * (<a href="Collection.html#optional-restrictions">optional</a>)
         * @throws NullPointerException if the specified element is null and this
         *         set does not permit null elements
         * (<a href="Collection.html#optional-restrictions">optional</a>)
         */
        public boolean contains(Object o)
        {
            if (!(o instanceof Entry))
                return false;
            Entry oEntry = (Entry)o;

            if (!ht.containsKey(oEntry.getKey()))   // If key is not contained
                return false;
            if (!ht.get(oEntry.getKey()).equals(oEntry.getValue())) // If that key has not the right value
                return false;

            // True if and only if contains the key
            // and that key is associated to the right
            // value.
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
            // false if o is not a HSet
            if (!(o instanceof HSet))
                return false;
            HSet oSet = (HSet)o;
            
            // false if size differs
            if (oSet.size() != this.size())
                return false;
    
            // Iterates through entries
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
        
        /**
         * Returns {@code true} if this set contains no elements.
         *
         * @return {@code true} if this set contains no elements
         */
        public boolean isEmpty()
        {
            return ht.isEmpty();
        }
    
        /**
         * Returns an iterator over the elements in this set.  The elements are
         * returned in no particular order (unless this set is an instance of some
         * class that provides a guarantee).
         *
         * @return an iterator over the elements in this set
         */
        public HIterator iterator()
        {
            return new EntrySetIterator();
        }
    
        public boolean remove(Object o)
        {
            if (!(o instanceof Entry))
                return false;
            Entry oEntry = (Entry)o;

            if (!this.contains(oEntry))
                return false;
            ht.remove(oEntry.getKey());
            return true;
        }
    
        public boolean removeAll(HCollection c)
        {
            boolean modified = false;
            HIterator it = this.iterator();
            while (it.hasNext())
            {
                Object element = it.next();
                if (this.remove(element))
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
    
        /**
         * Returns the number of elements in this set (its cardinality).  If this
         * set contains more than {@code Integer.MAX_VALUE} elements, returns
         * {@code Integer.MAX_VALUE}.
         *
         * @return the number of elements in this set (its cardinality)
         */
        public int size()
        {
            return ht.size();
        }
    
        /**
         * Returns an array containing all of the elements in this set.
         * If this set makes any guarantees as to what order its elements
         * are returned by its iterator, this method must return the
         * elements in the same order.
         *
         * <p>The returned array will be "safe" in that no references to it
         * are maintained by this set.  (In other words, this method must
         * allocate a new array even if this set is backed by an array).
         * The caller is thus free to modify the returned array.
         *
         * <p>This method acts as bridge between array-based and collection-based
         * APIs.
         *
         * @return an array containing all the elements in this set
         */
        public Object[] toArray()
        {
            Object[] arr = new Object[this.size()];
            int i = 0;
            for (Enumeration e = ht.keys(); e.hasMoreElements(); i++)
            {
                MapAdapterEntry entry = new MapAdapterEntry();
                Object currentKey = e.nextElement();
                entry.key = currentKey;
                entry.value = ht.get(currentKey);
                arr[i] = entry;
            }
    
            return arr;
        }
    
        /**
         * Returns an array containing all of the elements in this set; the
         * runtime type of the returned array is that of the specified array.
         * If the set fits in the specified array, it is returned therein.
         * Otherwise, a new array is allocated with the runtime type of the
         * specified array and the size of this set.
         *
         * <p>If this set fits in the specified array with room to spare
         * (i.e., the array has more elements than this set), the element in
         * the array immediately following the end of the set is set to
         * {@code null}.  (This is useful in determining the length of this
         * set <i>only</i> if the caller knows that this set does not contain
         * any null elements.)
         *
         * <p>If this set makes any guarantees as to what order its elements
         * are returned by its iterator, this method must return the elements
         * in the same order.
         *
         * <p>Like the {@link #toArray()} method, this method acts as bridge between
         * array-based and collection-based APIs.  Further, this method allows
         * precise control over the runtime type of the output array, and may,
         * under certain circumstances, be used to save allocation costs.
         *
         * <p>Suppose {@code x} is a set known to contain only strings.
         * The following code can be used to dump the set into a newly allocated
         * array of {@code String}:
         *
         * <pre>
         *     String[] y = x.toArray(new String[0]);</pre>
         *
         * Note that {@code toArray(new Object[0])} is identical in function to
         * {@code toArray()}.
         *
         * @param a the array into which the elements of this set are to be
         *        stored, if it is big enough; otherwise, a new array of the same
         *        runtime type is allocated for this purpose.
         * @return an array containing all the elements in this set
         * @throws IllegalArgumentException if the specified array's lenght
         *          is less then this set lenght
         * @throws NullPointerException if the specified array is null
         */
        public Object[] toArray(Object[] a)
        {
            if (a.length < this.size())
                throw new IllegalArgumentException();
            int i = 0;
            for (Enumeration e = ht.keys(); e.hasMoreElements(); i++)
            {
                MapAdapterEntry entry = new MapAdapterEntry();
                Object currentKey = e.nextElement();
                entry.key = currentKey;
                entry.value = ht.get(currentKey);
                a[i] = entry;
            }
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
            private Object lastReturnedKey;
    
            public EntrySetIterator()
            {
                keys = ht.keys();
                lastReturnedKey = null;
            }
    
            /**
             * Returns true if the iteration has more elements. (In other
             * words, returns true if next would return an element
             * rather than throwing an exception.)
             *
             * @return true if the iterator has more elements.
             */
            public boolean hasNext()
            {
                return keys.hasMoreElements();
            }
    
            /**
             * Returns the next element in the iteration.
             *
             * @return the next element in the iteration.
             * @exception java.util.NoSuchElementException iteration has no more elements.
             */
            public Object next()
            {
                if (!this.hasNext())
                    throw new NoSuchElementException();

                lastReturnedKey = keys.nextElement();

                MapAdapterEntry returned = new MapAdapterEntry();
                returned.key = lastReturnedKey;
                returned.value = MapAdapter.this.get(lastReturnedKey);
                return returned;
            }
    
            /**
             *
             * Removes from the underlying collection the last element returned by the
             * iterator (optional operation).  This method can be called only once per
             * call to next.  The behavior of an iterator is unspecified if
             * the underlying collection is modified while the iteration is in
             * progress in any way other than by calling this method.
             *
             * @exception IllegalStateException if the next method has not
             *		  yet been called, or the remove method has already
            *		  been called after the last call to the next
            *		  method.
            */
            public void remove()
            {
                MapAdapter.this.remove(lastReturnedKey);
            }
        }
    }

    private class KeySet implements HSet
    {    
        /**
         * Adds the specified element to this set if it is not already present
         * (optional operation).  More formally, adds the specified element
         * {@code e} to this set if the set contains no element {@code e2}
         * such that
         * {@code Objects.equals(e, e2)}.
         * If this set already contains the element, the call leaves the set
         * unchanged and returns {@code false}.  In combination with the
         * restriction on constructors, this ensures that sets never contain
         * duplicate elements.
         *
         * <p>The stipulation above does not imply that sets must accept all
         * elements; sets may refuse to add any particular element, including
         * {@code null}, and throw an exception, as described in the
         * specification for {@link HCollection#add HCollection.add}.
         * Individual set implementations should clearly document any
         * restrictions on the elements that they may contain.
         *
         * @param e element to be added to this set
         * @return {@code true} if this set did not already contain the specified
         *         element
         * @throws UnsupportedOperationException as the operation is not supported
         */
        public boolean add(Object o)
        {
            throw new UnsupportedOperationException();
        }
    
        /**
         * Adds all of the elements in the specified collection to this set if
         * they're not already present (optional operation).  If the specified
         * collection is also a set, the {@code addAll} operation effectively
         * modifies this set so that its value is the <i>union</i> of the two
         * sets.  The behavior of this operation is undefined if the specified
         * collection is modified while the operation is in progress.
         *
         * @param  c collection containing elements to be added to this set
         * @return {@code true} if this set changed as a result of the call
         *
         * @throws UnsupportedOperationException as the operation is not supported
         * @see #add(Object)
         */
        public boolean addAll(HCollection c)
        {
            throw new UnsupportedOperationException();
        }
    
        /**
         * Removes all of the elements from this set (optional operation).
         * The set will be empty after this call returns.
         */
        public void clear()
        {
            ht.clear();
        }
    
        /**
         * Returns {@code true} if this set contains the specified element.
         * More formally, returns {@code true} if and only if this set
         * contains an element {@code e} such that
         * {@code Objects.equals(o, e)}.
         *
         * @param o element whose presence in this set is to be tested
         * @return {@code true} if this set contains the specified element
         * @throws NullPointerException if the specified element is null and this
         *         set does not permit null elements
         * (<a href="Collection.html#optional-restrictions">optional</a>)
         */
        public boolean contains(Object o)
        {
            return ht.containsKey(o);
        }
    
        /**
         * Returns {@code true} if this set contains all of the elements of the
         * specified collection.  If the specified collection is also a set, this
         * method returns {@code true} if it is a <i>subset</i> of this set.
         *
         * @param  c collection to be checked for containment in this set
         * @return {@code true} if this set contains all of the elements of the
         *         specified collection
         * @throws NullPointerException if the specified collection contains one
         *         or more null elements and this set does not permit null
         *         elements
         * (<a href="Collection.html#optional-restrictions">optional</a>),
         *         or if the specified collection is null
         * @see    #contains(Object)
         */
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
    
        /**
         * Compares the specified object with this set for equality.  Returns
         * {@code true} if the specified object is also a set, the two sets
         * have the same size, and every member of the specified set is
         * contained in this set (or equivalently, every member of this set is
         * contained in the specified set).  This definition ensures that the
         * equals method works properly across different implementations of the
         * set interface.
         *
         * @param o object to be compared for equality with this set
         * @return {@code true} if the specified object is equal to this set
         */
        public boolean equals(Object o)
        {
            // false if o is not a HSet
            if (!(o instanceof HSet))
                return false;
            HSet oSet = (HSet)o;
            
            // false if size differs
            if (oSet.size() != this.size())
                return false;
    
            // Iterates through entries
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
    
        /**
         * Returns the hash code value for this set.  The hash code of a set is
         * defined to be the sum of the hash codes of the elements in the set,
         * where the hash code of a {@code null} element is defined to be zero.
         * This ensures that {@code s1.equals(s2)} implies that
         * {@code s1.hashCode()==s2.hashCode()} for any two sets {@code s1}
         * and {@code s2}, as required by the general contract of
         * {@link Object#hashCode}.
         *
         * @return the hash code value for this set
         * @see Object#equals(Object)
         * @see Set#equals(Object)
         */
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
        
        /**
         * Returns {@code true} if this set contains no elements.
         *
         * @return {@code true} if this set contains no elements
         */
        public boolean isEmpty()
        {
            return ht.isEmpty();
        }
    
        /**
         * Returns an iterator over the elements in this set.  The elements are
         * returned in no particular order (unless this set is an instance of some
         * class that provides a guarantee).
         *
         * @return an iterator over the elements in this set
         */
        public HIterator iterator()
        {
            return new KeySetIterator();
        }
    
        /**
         * Removes the specified element from this set if it is present
         * (optional operation).  More formally, removes an element {@code e}
         * such that
         * {@code Objects.equals(o, e)}, if
         * this set contains such an element.  Returns {@code true} if this set
         * contained the element (or equivalently, if this set changed as a
         * result of the call).  (This set will not contain the element once the
         * call returns.)
         *
         * @param o object to be removed from this set, if present
         * @return {@code true} if this set contained the specified element
         * (<a href="Collection.html#optional-restrictions">optional</a>)
         * @throws NullPointerException if the specified element is null and this
         *         set does not permit null elements
         * (<a href="Collection.html#optional-restrictions">optional</a>)
         */
        public boolean remove(Object o)
        {
            if (!this.contains(o))
                return false;
            ht.remove(o);
            return true;
        }
    
        /**
         * Removes from this set all of its elements that are contained in the
         * specified collection (optional operation).  If the specified
         * collection is also a set, this operation effectively modifies this
         * set so that its value is the <i>asymmetric set difference</i> of
         * the two sets.
         *
         * @param  c collection containing elements to be removed from this set
         * @return {@code true} if this set changed as a result of the call
         * @throws NullPointerException if this set contains a null element and the
         *         specified collection does not permit null elements
         *         (<a href="Collection.html#optional-restrictions">optional</a>),
         *         or if the specified collection is null
         * @see #remove(Object)
         * @see #contains(Object)
         */
        public boolean removeAll(HCollection c)
        {
            boolean modified = false;
            HIterator it = this.iterator();
            while (it.hasNext())
            {
                Object element = it.next();
                if (this.remove(element))
                    modified = true;
            }
            return modified;
        }
    
        /**
         * Retains only the elements in this set that are contained in the
         * specified collection (optional operation).  In other words, removes
         * from this set all of its elements that are not contained in the
         * specified collection.  If the specified collection is also a set, this
         * operation effectively modifies this set so that its value is the
         * <i>intersection</i> of the two sets.
         *
         * @param  c collection containing elements to be retained in this set
         * @return {@code true} if this set changed as a result of the call
         * @throws NullPointerException if this set contains a null element and the
         *         specified collection does not permit null elements
         *         (<a href="Collection.html#optional-restrictions">optional</a>),
         *         or if the specified collection is null
         * @see #remove(Object)
         */
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
    
        /**
         * Returns the number of elements in this set (its cardinality).  If this
         * set contains more than {@code Integer.MAX_VALUE} elements, returns
         * {@code Integer.MAX_VALUE}.
         *
         * @return the number of elements in this set (its cardinality)
         */
        public int size()
        {
            return ht.size();
        }
    
        /**
         * Returns an array containing all of the elements in this set.
         * If this set makes any guarantees as to what order its elements
         * are returned by its iterator, this method must return the
         * elements in the same order.
         *
         * <p>The returned array will be "safe" in that no references to it
         * are maintained by this set.  (In other words, this method must
         * allocate a new array even if this set is backed by an array).
         * The caller is thus free to modify the returned array.
         *
         * <p>This method acts as bridge between array-based and collection-based
         * APIs.
         *
         * @return an array containing all the elements in this set
         */
        public Object[] toArray()
        {
            Object[] arr = new Object[this.size()];
            int i = 0;
            for (Enumeration e = ht.keys(); e.hasMoreElements(); i++)
                arr[i] = e.nextElement();
    
            return arr;
        }
    
        /**
         * Returns an array containing all of the elements in this set; the
         * runtime type of the returned array is that of the specified array.
         * If the set fits in the specified array, it is returned therein.
         * Otherwise, a new array is allocated with the runtime type of the
         * specified array and the size of this set.
         *
         * <p>If this set fits in the specified array with room to spare
         * (i.e., the array has more elements than this set), the element in
         * the array immediately following the end of the set is set to
         * {@code null}.  (This is useful in determining the length of this
         * set <i>only</i> if the caller knows that this set does not contain
         * any null elements.)
         *
         * <p>If this set makes any guarantees as to what order its elements
         * are returned by its iterator, this method must return the elements
         * in the same order.
         *
         * <p>Like the {@link #toArray()} method, this method acts as bridge between
         * array-based and collection-based APIs.  Further, this method allows
         * precise control over the runtime type of the output array, and may,
         * under certain circumstances, be used to save allocation costs.
         *
         * <p>Suppose {@code x} is a set known to contain only strings.
         * The following code can be used to dump the set into a newly allocated
         * array of {@code String}:
         *
         * <pre>
         *     String[] y = x.toArray(new String[0]);</pre>
         *
         * Note that {@code toArray(new Object[0])} is identical in function to
         * {@code toArray()}.
         *
         * @param a the array into which the elements of this set are to be
         *        stored, if it is big enough; otherwise, a new array of the same
         *        runtime type is allocated for this purpose.
         * @return an array containing all the elements in this set
         * @throws NullPointerException if the specified array is null
         */
        public Object[] toArray(Object[] a)
        {
            if (a.length < this.size())
                throw new IllegalArgumentException();
            int i = 0;
            for (Enumeration e = ht.keys(); e.hasMoreElements(); i++)
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

        private class KeySetIterator implements HIterator
        {
            //private Enumeration values;
            private Enumeration keys;
            private Object lastReturnedKey;
    
            public KeySetIterator()
            {
                keys = ht.keys();
                lastReturnedKey = null;
            }
    
            /**
             * Returns true if the iteration has more elements. (In other
             * words, returns true if next would return an element
             * rather than throwing an exception.)
             *
             * @return true if the iterator has more elements.
             */
            public boolean hasNext()
            {
                return keys.hasMoreElements();
            }
    
            /**
             * Returns the next element in the iteration.
             *
             * @return the next element in the iteration.
             * @exception java.util.NoSuchElementException iteration has no more elements.
             */
            public Object next()
            {
                if (!this.hasNext())
                    throw new NoSuchElementException();

                lastReturnedKey = keys.nextElement();
                return lastReturnedKey;
            }
    
            /**
             *
             * Removes from the underlying collection the last element returned by the
             * iterator (optional operation).  This method can be called only once per
             * call to next.  The behavior of an iterator is unspecified if
             * the underlying collection is modified while the iteration is in
             * progress in any way other than by calling this method.
             *
             * @exception IllegalStateException if the next method has not
             *		  yet been called, or the remove method has already
            *		  been called after the last call to the next
            *		  method.
            */
            public void remove()
            {
                if (lastReturnedKey == null)
                    throw new HIllegalStateException();
                ht.remove(lastReturnedKey);
                lastReturnedKey = null;
            }
        }
    }

    private class Values implements HCollection
    {
        // Query Operations

        /**
         * Returns the number of elements in this collection. If this collection
         * contains more than Integer.MAX_VALUE elements, returns
         * Integer.MAX_VALUE.
         *
         * @return the number of elements in this collection
         */
        public int size()
        {
            return ht.size();
        }

        /**
         * Returns true if this collection contains no elements.
         *
         * @return true if this collection contains no elements
         */
        public boolean isEmpty()
        {
            return ht.isEmpty();
        }

        /**
         * Returns true if this collection contains the specified element. More
         * formally, returns true if and only if this collection contains at
         * least one element e such that
         * (o==null ? e==null : o.equals(e)).
         *
         * @param obj element whose presence in this collection is to be tested.
         * @return true if this collection contains the specified element
         */
        public boolean contains(Object obj)
        {
            return ht.contains(obj);
        }

        /**
         * Returns an iterator over the elements in this collection. There are no
         * guarantees concerning the order in which the elements are returned (unless
         * this collection is an instance of some class that provides a guarantee).
         *
         * @return an Iterator over the elements in this collection
         */
        public HIterator iterator()
        {
            return new ValuesIterator();
        }

        /**
         * Returns an array containing all of the elements in this collection. If the
         * collection makes any guarantees as to what order its elements are returned by
         * its iterator, this method must return the elements in the same order.
         * <p>
         *
         * The returned array will be "safe" in that no references to it are maintained
         * by this collection. (In other words, this method must allocate a new array
         * even if this collection is backed by an array). The caller is thus free to
         * modify the returned array.
         * <p>
         *
         * This method acts as bridge between array-based and collection-based APIs.
         *
         * @return an array containing all of the elements in this collection
         */
        public Object[] toArray()
        {
            Object[] arr = new Object[this.size()];
            int i = 0;
            for (Enumeration e = ht.keys(); e.hasMoreElements(); i++)
                arr[i] = e.nextElement();
    
            return arr;
        }

        /**
         * Returns an array containing all of the elements in this collection; the
         * runtime type of the returned array is that of the specified array. If the
         * collection fits in the specified array, it is returned therein. Otherwise, a
         * new array is allocated with the runtime type of the specified array and the
         * size of this collection.
         * <p>
         *
         * If this collection fits in the specified array with room to spare (i.e., the
         * array has more elements than this collection), the element in the array
         * immediately following the end of the collection is set to null. This
         * is useful in determining the length of this collection <i>only</i> if the
         * caller knows that this collection does not contain any null
         * elements.)
         * <p>
         *
         * If this collection makes any guarantees as to what order its elements are
         * returned by its iterator, this method must return the elements in the same
         * order.
         * <p>
         *
         * Like the toArray method, this method acts as bridge between
         * array-based and collection-based APIs. Further, this method allows precise
         * control over the runtime type of the output array, and may, under certain
         * circumstances, be used to save allocation costs
         * <p>
         *
         * Suppose l is a List known to contain only strings. The
         * following code can be used to dump the list into a newly allocated array of
         * String:
         *
         * <pre>
         * String[] x = (String[]) v.toArray(new String[0]);
         * </pre>
         * <p>
         *
         * Note that toArray(new Object[0]) is identical in function to
         * toArray().
         *
         * @param arrayTarget the array into which the elements of this collection are
         *                    to be stored, if it is big enough; otherwise, a new array
         *                    of the same runtime type is allocated for this purpose.
         * @return an array containing the elements of this collection
         *
         * @throws NullPointerException if the specified array is null.
         */

        public Object[] toArray(Object[] a)
        {
            if (a.length < this.size())
                throw new IllegalArgumentException();
            int i = 0;
            for (Enumeration e = ht.keys(); e.hasMoreElements(); i++)
                a[i] = e.nextElement();
            return a;
        }

        // Modification Operations

        /**
         * Ensures that this collection contains the specified element (optional
         * operation). Returns true if this collection changed as a result of the call.
         * (Returns false if this collection does not permit duplicates and already
         * contains the specified element.)
         * <p>
         *
         * Collections that support this operation may place limitations on what
         * elements may be added to this collection. In particular, some collections
         * will refuse to add null elements, and others will impose restrictions on the
         * type of elements that may be added. Collection classes should clearly specify
         * in their documentation any restrictions on what elements may be added.
         * <p>
         *
         * If a collection refuses to add a particular element for any reason other than
         * that it already contains the element, it <i>must</i> throw an exception
         * (rather than returning false). This preserves the invariant that a collection
         * always contains the specified element after this call returns.
         *
         * @param obj element whose presence in this collection is to be ensured.
         * @return true if this collection changed as a result of the call
         */
        public boolean add(Object obj)
        {
            throw new UnsupportedOperationException();
        }

        /**
         * Removes a single instance of the specified element from this collection, if
         * it is present (optional operation). More formally, removes an element e such
         * that (o==null ? e==null : o.equals(e)), if this collection contains one or
         * more such elements. Returns true if this collection contained the specified
         * element (or equivalently, if this collection changed as a result of the
         * call).
         *
         * @param obj element to be removed from this collection, if present.
         * @return true if this collection changed as a result of the call
         */
        public boolean remove(Object obj)
        {
            for (Enumeration keys = ht.keys(); keys.hasMoreElements();)
            {
                Object key = keys.nextElement();
                if (ht.get(key).equals(obj))
                {
                    ht.remove(key);
                    return true;
                }
            }
            return false;
        }

        // Bulk Operations

        /**
         * Returns true if this collection contains all of the elements in the
         * specified collection.
         *
         * @param coll collection to be checked for containment in this collection.
         * @return true if this collection contains all of the elements in the
         *         specified collection
         * @throws NullPointerException if the specified collection is null.
         * @see #contains(Object)
         */
        public boolean containsAll(HCollection coll)
        {
            HIterator it = coll.iterator();
            while (it.hasNext())
            {
                Object element = it.next();
                if (!this.contains(element))
                    return false;
            }
            return true;
        }

        /**
         * Adds all of the elements in the specified collection to this collection
         * (optional operation). The behavior of this operation is undefined if the
         * specified collection is modified while the operation is in progress. (This
         * implies that the behavior of this call is undefined if the specified
         * collection is this collection, and this collection is nonempty.)
         *
         * @param coll elements to be inserted into this collection.
         * @return true if this collection changed as a result of the call
         * @see #add(Object)
         */
        public boolean addAll(HCollection coll)
        {
            throw new UnsupportedOperationException();
        }

        /**
         *
         * Removes all this collection's elements that are also contained in the
         * specified collection (optional operation). After this call returns, this
         * collection will contain no elements in common with the specified collection.
         *
         * @param coll elements to be removed from this collection.
         * @return true if this collection changed as a result of the call
         *
         * @throws NullPointerException if the specified collection is null.
         * @see #remove(Object)
         * @see #contains(Object)
         */
        public boolean removeAll(HCollection coll)
        {
            boolean modified = false;
            HIterator it = this.iterator();
            while (it.hasNext())
            {
                Object element = it.next();
                if (this.remove(element))
                    modified = true;
            }
            return modified;
        }

        /**
         * Retains only the elements in this collection that are contained in the
         * specified collection (optional operation). In other words, removes from this
         * collection all of its elements that are not contained in the specified
         * collection.
         *
         * @param coll elements to be retained in this collection.
         * @return true if this collection changed as a result of the call
         *
         * @throws NullPointerException if the specified collection is null.
         * @see #remove(Object)
         * @see #contains(Object)
         */
        public boolean retainAll(HCollection coll)
        {
            boolean modified = false;
            HIterator it = this.iterator();
            while (it.hasNext())
            {
                Object element = it.next();
                if (!coll.contains(element))
                {
                    this.remove(element);
                    modified = true;
                }     
            }
    
            return modified;
        }

        /**
         * Removes all of the elements from this collection (optional operation). This
         * collection will be empty after this method returns unless it throws an
         * exception.
         */
        public void clear()
        {
            ht.clear();
        }
        // Comparison and hashing

        /**
         * Compares the specified object with this collection for equality.
         * <p>
         *
         * While the Collection interface adds no stipulations to the general
         * contract for the Object.equals, programmers who implement the
         * Collection interface "directly" (in other words, create a class that
         * is a Collection but is not a Set or a List) must
         * exercise care if they choose to override the Object.equals. It is
         * not necessary to do so, and the simplest course of action is to rely on
         * Object's implementation, but the implementer may wish to implement a
         * "value comparison" in place of the default "reference comparison." (The
         * List and Set interfaces mandate such value comparisons.)
         * <p>
         *
         * The general contract for the Object.equals method states that equals
         * must be symmetric (in other words, a.equals(b) if and only if
         * b.equals(a)). The contracts for List.equals and
         * Set.equals state that lists are only equal to other lists, and sets
         * to other sets. Thus, a custom equals method for a collection class
         * that implements neither the List nor Set interface must
         * return false when this collection is compared to any list or set.
         * (By the same logic, it is not possible to write a class that correctly
         * implements both the Set and List interfaces.)
         *
         * @param obj Object to be compared for equality with this collection.
         * @return true if the specified object is equal to this collection
         *
         * @see Object#equals(Object)
         * @see HSet#equals(Object)
         */
        public boolean equals(Object o)
        {
            // false if o is not a HCollection
            if (!(o instanceof HCollection))
                return false;
            HCollection oCollection = (HCollection)o;
            
            // false if size differs
            if (oCollection.size() != this.size())
                return false;
    
            // Iterates through entries
            HIterator it = oCollection.iterator();
            while (it.hasNext())
            {
                Object element = it.next();
                if (count(this, element) != count(oCollection, element))
                    return false;
            }
            
            // Same size and same elements.
            return true;
        }

        private int count(HCollection coll, Object element)
        {
            if (!coll.contains(element))
                return 0;
            int elementCount = 0;
            HIterator it = coll.iterator();
            while (it.hasNext())
            {
                Object current = it.next();
                if (current.equals(element))
                    elementCount++;
            }
            return elementCount;
        }

        /**
         * Returns the hash code value for this collection. While the
         * Collection interface adds no stipulations to the general contract
         * for the Object.hashCode method, programmers should take note that
         * any class that overrides the Object.equals method must also override
         * the Object.hashCode method in order to satisfy the general contract
         * for the Object.hashCodemethod. In particular, c1.equals(c2)
         * implies that c1.hashCode()==c2.hashCode().
         *
         * @return the hash code value for this collection
         *
         * @see Object#hashCode()
         * @see Object#equals(Object)
         */
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

        public String toString()
        {
            HIterator it = this.iterator();
            String res = "[";
            while (it.hasNext())
            {
                res += it.next();
                if (it.hasNext())
                    res += ", ";
            }
            res += "]";
            return res;
        }

        private class ValuesIterator implements HIterator
        {
            Enumeration values;
            Object lastReturnedValue;

            public ValuesIterator()
            {
                values = ht.elements();
            }

            public boolean hasNext()
            {
                return values.hasMoreElements();
            }

            public Object next()
            {
                if (!this.hasNext())
                    throw new NoSuchElementException();
                lastReturnedValue = values.nextElement();
                return lastReturnedValue;
            } 

            public void remove()
            {
                if (lastReturnedValue == null)
                    throw new HIllegalStateException();
                Values.this.remove(lastReturnedValue);
            }
        }
    }
}

