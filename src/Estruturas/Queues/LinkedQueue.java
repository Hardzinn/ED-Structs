package Estruturas.Queues;

import Exceptions.EmptyCollectionException;
import Interfaces.QueueADT;
import Estruturas.Lists.Node;

public class LinkedQueue<T> implements QueueADT<T>{

    private int count;
    private Node<T> front, rear;

    public LinkedQueue(){
        count = 0;
        front = rear = null;
    }

    public void enqueue(T element){
        Node<T> node = new Node<T>(element);
        if(isEmpty()){
            front = node;
        }else{
            rear.setNext(node);
        }
        rear = node;
        count++;
    }

    public T dequeue() throws EmptyCollectionException {
        if(isEmpty()){
            throw new EmptyCollectionException("Lista vazia");
        }
        T elementRemoved = front.getData();
        front = front.getNext();
        count--;
        return elementRemoved;
    }

    public T first(){
        return front.getData();
    }

    public boolean isEmpty(){
        return (count == 0);
    }

    public int size(){
        return count;
    }

    public String toString(){
        String result = "";
        Node<T> current = front;
        while(current != null){
            result += current.getData().toString() + "\n";
            current = current.getNext();
        }
        return result;
    }



}
