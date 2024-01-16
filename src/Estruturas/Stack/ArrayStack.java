package Estruturas.Stack;

import Interfaces.StackADT;
import Exceptions.EmptyCollectionException;

public class ArrayStack<T> implements StackADT<T> {

    private final int DEFAULT_CAPACITY = 100;
    private int top;
    private T[] stack;

    public ArrayStack(){
        top = 0;
        stack = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    private void expandCapacity(){
        T[] temp = (T[]) (new Object[stack.length * 2]);
        for( int i = 0; i < stack.length; i++){
            temp[i] = stack[i];
        }
        stack = temp;
    }

    public void push(T element){

        if ( this.stack.length == top){
            expandCapacity();
        }

        stack[top] = element;
        top++;

    }

    public boolean isEmpty() {
        return (top == 0);
    }

    public int size(){
        return top;
    }

    public T peek() throws EmptyCollectionException {
        if ( isEmpty() ){
            throw new EmptyCollectionException("Stack");
        }
        return stack[top-1];
    }

     public T pop() throws EmptyCollectionException {
        if ( isEmpty() ){
            throw new EmptyCollectionException("Stack");
        }
        else{
            top--;
            stack[top] = null;
        }
        return stack[top];
     }

     public String toString(){
        String result = "";
            for ( int i = 0; i < top; i++){
                result += stack[i] + "\n";
            }
            return result;
    }

}
