package Interfaces;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

public interface BinarySearchTreeADT<T> extends BinaryTreeADT<T>{
    public void addElement(T element);
    public T removeElement(T targetElement) throws EmptyCollectionException, ElementNotFoundException;
    public void removeAllOccurrences(T targetElement) throws ElementNotFoundException, EmptyCollectionException;
    public T removeMin() throws ElementNotFoundException, EmptyCollectionException;
    public T removeMax() throws ElementNotFoundException, EmptyCollectionException;
    public T findMin() throws ElementNotFoundException;
    public T findMax() throws ElementNotFoundException;
}
