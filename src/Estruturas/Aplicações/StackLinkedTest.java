package Estruturas.Aplicações;

import Estruturas.Stack.LinkedStack;
import Exceptions.EmptyCollectionException;

public class StackLinkedTest {

    public static void main(String[] args) throws EmptyCollectionException {

        LinkedStack<Integer> stack = new LinkedStack<Integer>();

        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        System.out.println(stack.toString());

        stack.pop();
        stack.pop();

        System.out.println(stack.toString());

        stack.push(5);
        stack.push(6);

        System.out.println(stack.toString());

        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();

        System.out.println(stack.toString());

    }

}
