package Estruturas.Lists;

public class DoubleLinkedNode<T>{
    private T element;
    private DoubleLinkedNode<T> previous;
    private DoubleLinkedNode<T> next;

    public DoubleLinkedNode(T element){
        this.element = element;
        this.next = this.previous = null;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public DoubleLinkedNode<T> getPrevious() {
        return previous;
    }

    public void setPrevious(DoubleLinkedNode<T> previous) {
        this.previous = previous;
    }

    public DoubleLinkedNode<T> getNext() {
        return next;
    }

    public void setNext(DoubleLinkedNode<T> next) {
        this.next = next;
    }
}

