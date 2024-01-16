package Interfaces;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

import java.util.Iterator;

public interface ListADT<T> extends Iterable<T> {

    public T removeFirst() throws EmptyCollectionException;
    public T removeLast() throws EmptyCollectionException;
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException;
    public T first() throws EmptyCollectionException;
    public T last() throws EmptyCollectionException;
    public boolean contains(T target) throws EmptyCollectionException, ElementNotFoundException;
    public boolean isEmpty();
    public int size();
    public Iterator<T> iterator();

    public String toString();
}
