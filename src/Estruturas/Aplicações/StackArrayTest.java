package Estruturas.Aplicações;

import Estruturas.Stack.ArrayStack;
import Exceptions.EmptyCollectionException;

public class StackArrayTest {

    public static void main (String[] args) {

        ArrayStack<Integer> stack = new ArrayStack<Integer>();

        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        System.out.println(stack.toString());


            try {
                stack.pop();
            } catch (EmptyCollectionException e) {
                e.printStackTrace();
            }

        System.out.println(stack.toString());

        try{
            System.out.println(stack.peek());
        } catch (EmptyCollectionException e) {
            e.printStackTrace();
        }

        System.out.println(stack.toString());

        System.out.println(stack.isEmpty());

        System.out.println(stack.size());

    }
}
