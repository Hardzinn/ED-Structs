package Interfaces;

import Exceptions.EmptyCollectionException;

public interface QueueADT<T>{
    public void enqueue(T element);
    public T dequeue() throws EmptyCollectionException;
    public T first();
    public boolean isEmpty();
    public int size();
    public String toString();
}
