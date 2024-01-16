package Estruturas.Lists;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Interfaces.ListADT;

import java.util.Iterator;

public class DoubleLinkedList<T> implements ListADT<T>, Iterable<T> {

    protected DoubleLinkedNode<T> head, tail;
    protected int count, modCount;


    public DoubleLinkedList(){
        this.head = null;
        this.tail = null;
        this.count = 0;
        this.modCount = 0;
    }


    @Override
    public T removeFirst() throws EmptyCollectionException {
        if(isEmpty())
            throw new EmptyCollectionException("Empty List");

        T elementToRemove = head.getElement();
        head = head.getNext();
        count--;

        if(isEmpty())
            tail = null;
        else
            head.setPrevious(null);

        modCount++;
        return elementToRemove;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {

        if(isEmpty())
            throw new EmptyCollectionException("Empty List");

        T elementToRemove = tail.getElement();
        tail = tail.getPrevious();
        count--;

        if(isEmpty())
            head = null;
        else
            tail.setNext(null);

        modCount++;
        return elementToRemove;

    }

    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {

        if(isEmpty())
            throw new EmptyCollectionException("Empty List");

        DoubleLinkedNode<T> current = head;
        while(current != null && !element.equals(current.getElement()))
            current = current.getNext();

        if(current == null)
            throw new ElementNotFoundException("Element not found");

        if(size() == 1){
            head = tail = null;
        }else if(current == head){
            head = current.getNext();
            head.setPrevious(null);
        } else if(current == tail){
            tail = current.getPrevious();
            tail.setNext(null);
        } else{
            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());
        }

        count--;
        modCount++;
        return current.getElement();
    }

    @Override
    public T first() {
        return head.getElement();
    }

    @Override
    public T last() throws EmptyCollectionException{
        if (isEmpty())
            throw new EmptyCollectionException("Empty List");
        return tail.getElement();
    }

    @Override
    public boolean contains(T target) throws EmptyCollectionException {

        if(isEmpty())
            throw new EmptyCollectionException("Empty List");

        DoubleLinkedNode<T> current = head;
        while(current != null){
            if(target.equals(current.getElement()))
                return true;
            else
                current = current.getNext();
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return count;
    }

    public String toString(){
        String result = "";
        DoubleLinkedNode<T> current = head;
        while(current != null){
            result += current.getElement().toString() + "\n";
            current = current.getNext();
        }
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new DoubleLinkedListIterator<T>(){} ;
    }

    public abstract class DoubleLinkedListIterator<E> implements Iterator<T>{

        protected DoubleLinkedNode<T> current;
        protected int expectedModCount;
        protected boolean okToRemove;

        public DoubleLinkedListIterator(){
            this.current = head;
            this.expectedModCount = modCount;
            this.okToRemove = false;
        }

        @Override
        public boolean hasNext() {
            if(expectedModCount != modCount)
                throw new java.util.ConcurrentModificationException();

            return current != null;
        }

        @Override
        public T next() {
            if(expectedModCount != modCount)
                throw new java.util.ConcurrentModificationException();

            if(!hasNext())
                throw new java.util.NoSuchElementException();

            T result = current.getElement();
            current = current.getNext();
            okToRemove = true;
            return result;
        }

        @Override
        public void remove() {

            if(expectedModCount != modCount)
                throw new java.util.ConcurrentModificationException();

            if(!okToRemove)
                throw new IllegalStateException();

            try {
                DoubleLinkedList.this.remove(current.getPrevious().getElement());
            } catch (EmptyCollectionException | ElementNotFoundException e) {
                throw new RuntimeException(e);
            }
            okToRemove = false;

        }
    }
}
