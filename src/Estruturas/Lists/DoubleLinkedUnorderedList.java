package Estruturas.Lists;

import Exceptions.EmptyCollectionException;
import Interfaces.UnorderedListADT;
public class DoubleLinkedUnorderedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T> {

    public DoubleLinkedUnorderedList(){
        super();
    }

    @Override
    public void addToFront(T element) {

        if(isEmpty()){
            head = new DoubleLinkedNode<T>(element);
            tail = head;
        }else{
            DoubleLinkedNode<T> newNode = new DoubleLinkedNode<T>(element);
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }
        count++;
        modCount++;
    }

    @Override
    public void addToRear(T element) {
        if(isEmpty()){
            head = new DoubleLinkedNode<T>(element);
            tail = head;
        }else{
            DoubleLinkedNode<T> newNode = new DoubleLinkedNode<T>(element);
            newNode.setPrevious(tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        count++;
        modCount++;
    }

    @Override
    public void addAfter(T element, T target) throws EmptyCollectionException {

        if (isEmpty())
            throw new EmptyCollectionException("Empty List");

        DoubleLinkedNode<T> current = head;
        DoubleLinkedNode<T> newNode = new DoubleLinkedNode<T>(element);

        while(current != null && !current.getElement().equals(target)){
            current = current.getNext();
        }

        if(current == null)
            throw new EmptyCollectionException("Element not found");

        if(current == tail){
            addToRear(element);
        } else{
            newNode.setNext(current.getNext());
            newNode.setPrevious(current);
            current.getNext().setPrevious(newNode);
            current.setNext(newNode);
            count++;
            modCount++;
        }

    }
}
