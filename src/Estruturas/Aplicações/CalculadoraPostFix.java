package Estruturas.Aplicações;

import Estruturas.Stack.ArrayStack;
import Exceptions.EmptyCollectionException;

public class CalculadoraPostFix {

    private ArrayStack<Integer> stack;

    public CalculadoraPostFix(){
        stack = new ArrayStack<Integer>();
    }

    public int calculate(String expression) throws EmptyCollectionException {
        int result = 0;
        for (int i = 0; i < expression.length(); i++){
            char c = expression.charAt(i);
            if (Character.isDigit(c)){
                stack.push(Character.getNumericValue(c));
            } else {
                int a = stack.pop();
                int b = stack.pop();
                switch (c){
                    case '+':
                        result = a + b;
                        break;
                    case '-':
                        result = a - b;
                        break;
                    case '*':
                        result = a * b;
                        break;
                    case '/':
                        result = a / b;
                        break;
                }
                stack.push(result);
            }
        }
        return stack.pop();
    }

    public static void main (String[] args){
        CalculadoraPostFix calc = new CalculadoraPostFix();
        try{
            System.out.println(calc.calculate("23+"));
            System.out.println(calc.calculate("23*"));
            System.out.println(calc.calculate("23-"));
            System.out.println(calc.calculate("23/"));
        } catch (EmptyCollectionException e){
            e.printStackTrace();
        }
    }

}
