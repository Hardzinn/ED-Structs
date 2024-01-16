package Estruturas.Stack;

import Estruturas.Lists.Node;
import Interfaces.StackADT;
import Exceptions.*;

public class LinkedStack<T> implements StackADT<T> {
    private int count;
    private Node<T> top;

    public  LinkedStack(){
        count = 0;
        top = null;
    }

    public void push(T element){
        Node<T> newNode = new Node<T>(element);
        top.setNext(newNode);
        top = newNode;
        count++;
    }

    @Override
    public T pop() throws EmptyCollectionException {
        if(isEmpty()){
            throw new EmptyCollectionException("Stack vazia");
        }

        T result  = top.getData();
        top = top.getNext();
        count--;
        return result;
    }

    @Override
    public T peek() throws EmptyCollectionException {
        if(isEmpty()){
            throw new EmptyCollectionException("Stack");
        }

        return top.getData();
    }
    public boolean isEmpty(){
        return (count == 0);
    }

    public int size(){
        return count;
    }

}
