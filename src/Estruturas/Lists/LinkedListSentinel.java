package Estruturas.Lists;

import Estruturas.Lists.Node;

public class LinkedListSentinel<T> {
    private Node<T> sentinel;
    private int count;

    public LinkedListSentinel(){
        this.sentinel = new Node<>();
        this.count = 0;
    }

    public void addNode(T data){
        Node<T> newNode = new Node<>(data);
        Node<T> current = this.sentinel;

        while(current.getNext() != null){
            current = current.getNext();
        }
        current.setNext(newNode);

        this.count++;

    }

    public void removeNode(T data){
        Node<T> current = this.sentinel.getNext();
        Node<T> previous = this.sentinel;

        while(current != null && !current.getData().equals(data)){
            previous = current;
            current = current.getNext();
        }

        if(current != null){
            previous.setNext(current.getNext());
            this.count--;
        }
    }

    public void print() {
        Node<T> current = sentinel.getNext(); // Start from the first node after sentinel
        while (current != null) {
            System.out.println(current.getData());
            current = current.getNext();
        }
    }

}
