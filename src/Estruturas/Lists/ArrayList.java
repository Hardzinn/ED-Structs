package Estruturas.Lists;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Interfaces.ListADT;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class ArrayList<T> implements ListADT<T>, Iterable<T>{

    protected final static int DEFAULT_CAPACITY = 100;
    protected T[] list;
    protected int count, modCount;

    public ArrayList(){
        list = (T[]) new Object[DEFAULT_CAPACITY];
        count = 0;
        this.modCount = 0;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {

        if (isEmpty())
            throw new EmptyCollectionException("Empty List");

        T element = list[0];
        for (int i = 0; i < list.length; i++) {
            list[i] = list[i+1];
        }
        list[count - 1] = null;
        count--;
        modCount++;
        return element;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty())
            throw new EmptyCollectionException("Empty List");

        T element = list[count];
        list[count] = null;
        count--;
        modCount++;
        return element;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {

        if(isEmpty()){
            throw new EmptyCollectionException("Empty List");
        }

        int index = 0;

        while(index < count && !list[index].equals(element)){
            index++;
        }

        if(index == count){
            throw new ElementNotFoundException("Element not found");
        }

        for (int i = index; i < count; i++) {
            list[i] = list[i+1];
        }

        list[count] = null;
        count--;

        modCount++;
        return null;
    }

    @Override
    public T first() {
        T element = null;
        if(!isEmpty()){
            element = list[0];
        }
        return element;
    }

    @Override
    public T last() {
        T element = null;
        if(!isEmpty()){
            element = list[count];
        }
        return element;
    }

    @Override
    public boolean contains(T target) throws EmptyCollectionException{

        if (isEmpty())
            throw new EmptyCollectionException("Empty List");

        while (iterator().hasNext()){
            if(iterator().next().equals(target)){
                return true;
            }
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

    protected void expandCapacity(){
        T[] tempList = (T[]) new Object[list.length * 2];
        for (int i = 0; i < list.length; i++) {
            tempList[i] = list[i];
        }
        list = tempList;
    }


    public void set(int index, T value) {
        checkIndex(index, 0, count - 1);
        list[index] = value;
    }

    private void checkIndex(int index, int min, int max) {
        if (index < min || index > max) {
            throw new ArrayIndexOutOfBoundsException("Invalid index " + index);
        }
    }

    public T get(int index) {
        checkIndex(index, 0, count - 1);
        return list[index];
    }

    public String toString(){
        String str = "";
        for (int i = 0; i < count; i++) {
            str += list[i] + " ";
        }
        return str;
    }


    @Override
    public Iterator<T> iterator()
    {
        return new ArrayIterator(){};
    }

    protected class ArrayIterator implements Iterator<T>
    {
        private int current;
        private int expectedModCount;
        private boolean okToRemove;


        public ArrayIterator()
        {
            current = 0;
            expectedModCount = modCount;
            okToRemove = false;

        }

        @Override
        public boolean hasNext()
        {
            return current < size();
        }

        @Override
        public T next() {
            if(modCount != expectedModCount)
                throw new ConcurrentModificationException();

            if (!hasNext())
                throw new IndexOutOfBoundsException();
            okToRemove = true;

            return list[current++];
        }


    }


}
