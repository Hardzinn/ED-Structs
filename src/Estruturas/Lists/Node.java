package Estruturas.Lists;

public class Node<T> {

    private Node<T> next;
    private T data;

    public Node(){
        this.next = null;
        this.data = null;
    }
    public Node(T data) {
        this.data = data;
        this.next = null;
    }
    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {

        this.next = next;
    }

    public T getData() {

        return data;
    }

    public void setData(T data) {

        this.data = data;
    }
}
