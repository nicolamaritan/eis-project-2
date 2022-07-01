package myAdapter;

public class CollectionAdapter implements HCollection   // HCollection implementation is redundant as HList extends HCollection
{
    // ListAdapter is a Object Adapter.
    private Vector vector;

    /**
     * Default constructor with no arguments.
     */
    public CollectionAdapter()
    {
        vector = new Vector();
    }

    /**
     * Returns the number of elements in this list. If this list contains more than
     * Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
     *
     * @return the number of elements in this list.
     */
    public int size()
    {
        // Reuse vector's method to return the collection size
        return vector.size();
    }

    /**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements.
     */
    public boolean isEmpty()
    {
        // Reuse vector's method to return if the collection is empty
        return vector.isEmpty();
    }

     /**
     *
     * Returns true if this list contains the specified element. More formally,
     * returns true if and only if this list contains at least one element e such
     * that (o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e)).
     *
     * @param o element whose presence in this list is to be tested.
     * @return true if this list contains the specified element.
     */
    public boolean contains(Object o)
    {
        // Reuse vector's method to return if the collection contains o
        return vector.contains(o);
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * 
     * The iterator is a fail-fast iterator, that means that any modification to
     * the list through ListAdapter methods and not throgh the iterator ones will
     * invalidate the iterator. Every iterator's method then throws
     * {@code HConcurrentModificationException}
     * @see HConcurrentModificationException
     *
     * @return an iterator over the elements in this list in proper sequence.
     */
    public HIterator iterator()
    {
        return new CollectionAdapterIterator();
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence starting from index from (included) to index to
     * (excluded).
     * @param from lower bound index (included).
     * @param to upper bound index (escluded)
     * @return an array containing the ListAdapter elements from from index to
     * to index.
     */
    private Object[] toArray(int from, int to)
    {
        Object[] arr = new Object[to - from];
        for (int i = from; i < to; i++)
        {
            arr[i - from] = vector.elementAt(i);
        }
        return arr;
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence. Obeys the general contract of the Collection.toArray method.
     *
     * @return an array containing all of the elements in this list in proper
     *         sequence.
     */
    public Object[] toArray()
    {
        return toArray(0, size());
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence. If arrayTarget is not big enough to hold ListAdapter's elements
     * the method throws HIllegalArgumentException.
     *
     * @param arrayTarget the array into which the elements of this list are to be
     *                    stored, if it is big enough; otherwise, a new array of the
     *                    same runtime type is allocated for this purpose.
     * @return an array containing the elements of this list.
     *
     * @throws NullPointerException if the specified array is null.
     * @throws HIllegalArgumentException if arrayTarget.size() {@literal <} this.size().
     */
    public Object[] toArray(Object[] arrayTarget)
    {
        if (arrayTarget == null)
            throw new NullPointerException();
        // If c is smaller than the list throw HIAE
        if (arrayTarget.length < size())
            throw new HIllegalArgumentException();
        
        // Call to vector method
        vector.copyInto(arrayTarget);
        
        // Return the array
        return arrayTarget;
    }

    /**
     * Appends the specified element to the end of this list (optional operation).
     * <p>
     *
     *
     * @param a element to be appended to this list.
     * @return true (as per the general contract of the Collection.add method).
     *
     */
    public boolean add(Object a)
    {
        vector.addElement(a);
        return true;
    }

    /**
     * Removes the first occurrence in this list of the specified element (optional
     * operation). If this list does not contain the element, it is unchanged. More
     * formally, removes the element with the lowest index i such that (o==null ?
     * get(i)==null : o.equals(get(i))) (if such an element exists).
     *
     * @param o element to be removed from this list, if present.
     * @return true if this list contained the specified element.
     */
    public boolean remove(Object o)
    {
        return vector.removeElement(o);  // true if removed, false otherwise
    }

    /**
     *
     * Returns true if this list contains all of the elements of the specified
     * collection.
     *
     * @param c collection to be checked for containment in this list.
     * @return true if this list contains all of the elements of the specified
     *         collection.
     * @throws NullPointerException if the specified collection is null.
     * @see #contains(Object)
     */
    public boolean containsAll(HCollection c)
    {
        if (c == null)
            throw new NullPointerException();

        // The empty set is the subset of every set, then return true
        if (c.isEmpty())
            return true;

        /*
            If this list is empty and c is not, then any element in c is not
            contained in this.
        */
        if (isEmpty())
            return false;

        HIterator iterator = c.iterator();
        while (iterator.hasNext())  // For each element in c
        {
            Object current = iterator.next();

            // If one is not contained then returns false. 
            if (!vector.contains(current))
                return false;
        }
        return true;
    }

    /**
     * Appends all of the elements in the specified collection to the end of
     * this list, in the order that they are returned by the
     * specified collection's Iterator.  The behavior of this operation is
     * undefined if the specified collection is modified while the operation
     * is in progress.  (This implies that the behavior of this call is
     * undefined if the specified collection is this list, and this
     * list is nonempty.)
     *
     * @param c collection containing elements to be added to this list
     * @return true if this list changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    public boolean addAll(HCollection c)
    {
        if (c == null)
            throw new NullPointerException();
        // If it is empty the list will not change.
        if (c.isEmpty())
            return false;

        HIterator iterator = c.iterator();
        while (iterator.hasNext())
        {
            Object current = iterator.next();
            
            // Append to the end the single element
            vector.addElement(current);
        }
        return true;
    }


    /**
     * Removes from this list all of its elements that are contained in the
     * specified collection.
     *
     * @param c collection containing elements to be removed from this list
     * @return true if this list changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     * @see HCollection#contains(Object)
     */
    public boolean removeAll(HCollection c)
    {
        if (c == null)
            throw new NullPointerException();

        // Removing empty and from empty does not change the list, therefore returns false
        if (isEmpty() || c.isEmpty())
            return false;
            
        boolean listChanged = false;    // Contains true <-> the list has changed
        HIterator iterator = c.iterator();
        while (iterator.hasNext())
        {
            Object current = iterator.next();

            // Method must remove all elements in common
            while (contains(current))
            {
                vector.removeElement(current);
                // If the element has been removed listChanged becomes true
                listChanged = true;
            }
        }

        return listChanged;
    }

    /**
     * Retains only the elements in this list that are contained in the
     * specified collection. In other words, removes from this list all
     * of its elements that are not contained in the specified collection.
     *
     * @param c collection containing elements to be retained in this list
     * @return true if this list changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     * @see HCollection#contains(Object)
     */
    public boolean retainAll(HCollection c)
    {
        if (c == null)
            throw new NullPointerException();

        // If this is empty there is "nothing to remove".
        if (isEmpty())
            return false;

        // The list is not empty at this point
        if (c.isEmpty())
        {
            // None of the elements in this must remain, so call clear method
            clear();
            return true;
        }
        // Copy contains an initial copy of the list
        HCollection copy = new CollectionAdapter();
        HIterator it = this.iterator();
        while (it.hasNext())
        {
            Object element = it.next();
            copy.add(element);
        }
        
        // Remove from the copy the elements in the list to be retained
        copy.removeAll(c);
        // Remove all the other elements
        return removeAll(copy);
    }

    /**
     * Removes all of the elements from this list.  The list will
     * be empty after this call returns.
     */
    public void clear()
    {
        // Create new vector
        vector = new Vector();  // Clears in costant time.
    }

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
     * Returns the hash code value for this list.  The hash code of a list
     * is defined to be the result of vector.hashCode().
     * This ensures that list1.equals(list2) implies that
     * ist1.hashCode()==list2.hashCode() for any two lists,
     * list1 and list2, as required by the general
     * contract of {@link Object#hashCode}.
     *
     * @return the hash code value for this list
     * @see Object#equals(Object)
     * @see #equals(Object)
     */
    public int hashCode()
    {
        return vector.hashCode();
    }

    /**
     * Returns a string representation of the list.
     * @return string representation of the object.
     */
    public String toString()
    {
        return vector.toString();
    }

    /**
     * 
     */
    private class CollectionAdapterIterator implements HIterator
    {
        /*
            The modification count variable is used to implement
            a fail fast iterator. Whenever the ListAdapter is changed
            through its methods the counter increments by 1.
            An iterator instance stores a similar variable.
            When an iterator method is called it checks if the modification
            count of the ListAdapter and its one are the same. If not,
            the iterator cannot iterate on the list anymore as an
            external (from iterator's point of view) modification
            as occurred.
        */
        private int pointedIndex;
        private int previousPointedIndex;

        /**
         * Constructs a ListAdapterIterator pointing at the first element
         * of the list, if it exists.
         */
        public CollectionAdapterIterator()
        {
        }

        /**
         * Returns true if this list iterator has more elements when
         * traversing the list in the forward direction. (In other words,
         * returns true if next would return an element rather
         * than throwing an exception.)
         *
         * @return true if the list iterator has more elements when
         *         traversing the list in the forward direction
         */
        public boolean hasNext()
        {
            return pointedIndex != size();
        }

        /**
         * Returns the next element in the list and advances the cursor position.
         * This method may be called repeatedly to iterate through the list,
         * or intermixed with calls to previous to go back and forth.
         * (Note that alternating calls to next and previous
         * will return the same element repeatedly.)
         *
         * @return the next element in the list
         * @throws NoSuchElementException if the iteration has no next element
         */
        public Object next()
        {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            
            previousPointedIndex = pointedIndex;
            Object next = vector.elementAt(pointedIndex);
            pointedIndex++;
            return next;
        }

        /**
         * Removes from the list the last element that was returned by next
         * or previous (optional operation).  This call can
         * only be made once per call to next or previous.
         * It can be made only if add has not been
         * called after the last call to next or previous.
         *
         * @throws HIllegalStateException if neither next nor
         *         previous have been called, or remove or
         *         add have been called after the last call to
         *         next or previous
         */
        public void remove()
        {
            /*
                prevPointedIndex being -1 means no prev or next has been
                called, or remove or add have been called after the last call to
                next or previous
            */
            if (previousPointedIndex == -1)
                throw new HIllegalStateException();
            
            // Direct access to Vector
            vector.removeElementAt(previousPointedIndex);
            pointedIndex = previousPointedIndex;
            // Reset previous pointed index
            previousPointedIndex = -1;
        }
    }
}