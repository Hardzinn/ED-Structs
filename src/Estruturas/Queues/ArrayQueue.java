package Estruturas.Queues;

import Interfaces.QueueADT;
import Exceptions.EmptyCollectionException;

//Circular array implementation of a queue, normal array is Big-O(n) for enqueue and dequeue
public class ArrayQueue<T> implements QueueADT<T>{

    private final int DEFAULT_CAPACITY = 100;
    private int front, rear, count;
    private T[] queue;
    
    public ArrayQueue(){
        front = rear = count = 0;
        queue = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    private void expandCapacity(){
        T[] tempQueue = (T[]) (new Object[queue.length * 2]);
        System.arraycopy(queue, 0, tempQueue, 0, queue.length);
        queue = tempQueue;
    }

    public void enqueue(T element){
        if(this.queue.length == count){
            expandCapacity();
        }
        queue[rear] = element;
        rear = (rear + 1) % queue.length;
        count++;
    }

    public T first(){
        return queue[front];
    }

    public boolean isEmpty(){
        return (count == 0);
    }

    public T dequeue() throws EmptyCollectionException {
        if(isEmpty()){
            throw new EmptyCollectionException("Lista vazia");
        }
        T elementRemoved = (T) queue[front];
        queue[front] = null;
        front = (front + 1) % queue.length;
        count--;
        return elementRemoved;

    }

    public int size(){
        return count;
    }

    public String toString(){
        String result = "";
        int scan = 0;
        while(scan < count){
            result += queue[scan].toString() + "\n";
            scan++;
        }
        return result;
    }


}
