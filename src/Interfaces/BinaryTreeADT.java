package Interfaces;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

import java.util.Iterator;

public interface BinaryTreeADT<T> {

    public T getRoot() throws ElementNotFoundException, EmptyCollectionException;
    public boolean isEmpty();
    public int size();
    public boolean contains(T element) throws ElementNotFoundException;
    public T find(T element) throws ElementNotFoundException, EmptyCollectionException;
    public String toString();
    public Iterator<T> iteratorInOrder();
    public Iterator<T> iteratorPreOrder();
    public Iterator<T> iteratorPostOrder();
    public Iterator<T> iteratorLevelOrder() throws EmptyCollectionException;


}
