package Estruturas.Lists;

import Exceptions.EmptyCollectionException;
import Exceptions.ElementNotFoundException;
import Interfaces.UnorderedListADT;

public class LinkedUnorderedList<T> extends LinkedList<T> implements UnorderedListADT<T> {

    @Override
    public void addToFront(T element) {
        Node<T> newNode = new Node<>(element);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setNext(head);
            head = newNode;
        }
        size++;
        modCount++;
    }

    @Override
    public void addToRear(T element) {
        Node<T> newNode = new Node<>(element);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;
        modCount++;
    }

    @Override
    public void addAfter(T element, T target) throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("LinkedList");
        Node<T> newNode = new Node<>(element);
        Node<T> current = head;
        while (!current.getData().equals(target)) {
            current = current.getNext();
        }
        newNode.setNext(current.getNext());
        current.setNext(newNode);
        if (newNode.getNext() == null) {
            tail = newNode;
        }
        size++;
        modCount++;
    }

    public void invert() {
        this.head = invert(head);
    }

    private Node<T> invert(Node<T> node) {
        if (node == null || node.getNext() == null) return node;
        Node<T> newHead = invert(node.getNext());
        node.getNext().setNext(node);
        node.setNext(null);
        return newHead;
    }
}