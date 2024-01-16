package Estruturas.Lists;

import Exceptions.NonComparableElementException;
import Interfaces.OrderedListADT;


public class DoubleLinkedOrderedList<T> extends DoubleLinkedList<T> implements OrderedListADT<T> {


    public DoubleLinkedOrderedList() {
        super();
    }

    @Override
    public void add(T element) throws NonComparableElementException {

        if(!(element instanceof Comparable))
            throw new NonComparableElementException("Element is not comparable");

        if(isEmpty()){
            head = tail = new DoubleLinkedNode<>(element);
        }
        else{
            DoubleLinkedNode<T> current = head;
            DoubleLinkedNode<T> previous = null;
            while(current != null && ((Comparable<T>) element).compareTo(current.getElement()) > 0){
                previous = current;
                current = current.getNext();
            }
            DoubleLinkedNode<T> newNode = new DoubleLinkedNode<>(element);
            if(current == null){
                newNode.setNext(head);
                head.setPrevious(newNode);
                head = newNode;
            }
            else{
                newNode.setNext(current);
                newNode.setPrevious(previous);
                previous.setNext(newNode);
                current.setPrevious(newNode);
            }
        }
        count++;
        modCount++;
    }
}
