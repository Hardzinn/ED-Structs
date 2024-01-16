package Estruturas.Lists;

import Exceptions.NonComparableElementException;
import Interfaces.OrderedListADT;

import java.util.Iterator;

public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T> {

    public ArrayOrderedList() {
        super();
    }

    @Override
    public void add(T element) throws NonComparableElementException {

        if(!(element instanceof Comparable))
            throw new NonComparableElementException("Element is not comparable");

        if (size() == list.length)
            expandCapacity();

        int index = 0;

        while (index < count && ((Comparable<T>) element).compareTo(list[index]) > 0)
            index++;

        for (int i = count; i > index; i--){
            list[i] = list[i-1];
        }

        list[index] = element;
        count++;
        modCount++;
    }
}
