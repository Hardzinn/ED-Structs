package Interfaces;

import Exceptions.NonComparableElementException;

public interface OrderedListADT<T> extends ListADT<T> {

    public void add(T element) throws NonComparableElementException;

}
