package Estruturas.Aplicações;

import Estruturas.Lists.ArrayOrderedList;
import Exceptions.NonComparableElementException;


public class ArrayOrdered {

    public static void main(String[] args) {

        ArrayOrderedList<Integer> list = new ArrayOrderedList<>();

        try {
            list.add(5);
            list.add(2);
            list.add(3);
            list.add(1);
            list.add(4);
        } catch (NonComparableElementException e) {
            System.out.println(e.getMessage());
        }
        try {
            list.remove(3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(list.toString());



    }

}
