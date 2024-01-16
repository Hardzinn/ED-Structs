package Estruturas.Lists;

import Estruturas.Lists.Node;
import Exceptions.*;


import Exceptions.EmptyCollectionException;
import Exceptions.ElementNotFoundException;
import Interfaces.ListADT;

import java.util.Iterator;

public class LinkedList<T> implements ListADT<T>, Iterable<T> {
    protected Node<T> head, tail;
    protected int size, modCount;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("LinkedList");
        }
        T removed = head.getData();
        head = head.getNext();
        size--;
        modCount++;
        return removed;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("LinkedList");
        }
        T removed = tail.getData();
        Node<T> current = head;
        while (current.getNext() != tail) {
            current = current.getNext();
        }
        current.setNext(null);
        tail = current;
        size--;
        modCount++;
        return removed;
    }

    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (isEmpty()) {
            throw new EmptyCollectionException("LinkedList");
        }
        if (!contains(element)) {
            throw new ElementNotFoundException("LinkedList");
        }
        T removed;
        Node<T> current = head;
        if (current.getData().equals(element)) {
            removed = removeFirst();
        } else if (tail.getData().equals(element)) {
            removed = removeLast();
        } else {
            while (!current.getNext().getData().equals(element)) {
                current = current.getNext();
            }
            removed = current.getNext().getData();
            current.setNext(current.getNext().getNext());
            size--;
            modCount++;
        }
        return removed;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("LinkedList");
        }
        return head.getData();
    }

    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("LinkedList");
        }
        return tail.getData();
    }

    @Override
    public boolean contains(T target) throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("LinkedList");
        }
        Node<T> current = head;
        while (current != null) {
            if (current.getData().equals(target)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " { ";
        if (!isEmpty()) {
            Node<T> current = head;
            while (current != null) {
                result += current.getData() + " ";
                current = current.getNext();
            }
        }
        return result + "}";
    }

    public void print(){
        Node<T> current = this.head;
        while (current != null){
            System.out.println(current.getData());
            current = current.getNext();
        }
    }

    public void printRecursive() {
        printNode(this.head);
    }
    private void printNode(Node<T> node) {
        if (node == null) {
            return;
        }
        System.out.println(node.getData());
        printNode(node.getNext());
    }

    @Override
    public Iterator<T> iterator() {
        return new BasicIterator<T>() {};
    }

    private abstract class BasicIterator<E> implements Iterator<T> {
        private Node<T> current;
        private int expectedModCount;
        private boolean okToRemove;

        public BasicIterator() {
            this.current = head;
            this.expectedModCount = modCount;
            this.okToRemove = false;
        }

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            if (expectedModCount != modCount) {
                throw new java.util.ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            T next = current.getData();
            current = current.getNext();
            okToRemove = true;
            return next;
        }

        public void remove() {
            if (expectedModCount != modCount) {
                throw new java.util.ConcurrentModificationException();
            }
            if (!okToRemove) {
                throw new IllegalStateException();
            }
            try {
                LinkedList.this.remove(current.getData());
            } catch (EmptyCollectionException | ElementNotFoundException e) {
                throw new RuntimeException(e);
            }
            expectedModCount++;
            okToRemove = false;
        }
    }
}

