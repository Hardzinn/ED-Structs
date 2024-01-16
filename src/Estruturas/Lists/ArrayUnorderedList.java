package Estruturas.Lists;

import Exceptions.EmptyCollectionException;
import Interfaces.UnorderedListADT;

public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {

    public ArrayUnorderedList() {
        super();
    }

    @Override
    public void addToFront(T element) {

        if(size() == list.length)
            expandCapacity();

        for (int i = count; i > 0; i--) {
            list[i] = list[i-1];
        }
        list[0] = element;
        count++;
        modCount++;
    }

    @Override
    public void addToRear(T element) {

        if(size() == list.length)
            expandCapacity();

        list[size()] = element;
        count++;
        modCount++;
    }

    @Override
    public void addAfter(T element, T target) throws EmptyCollectionException {

        if(isEmpty())
            throw new EmptyCollectionException("Empty List");

        if(size() == list.length)
            expandCapacity();

        int index = 0;

        while (index < count && !target.equals(list[index])) {
            index++;
        }

        if(index == count)
            throw new EmptyCollectionException("Target not found");

        for (int i = count; i > index; i--) {
            list[i] = list[i-1];
        }

        list[index+1] = element;
        count++;
        modCount++;
    }
}
