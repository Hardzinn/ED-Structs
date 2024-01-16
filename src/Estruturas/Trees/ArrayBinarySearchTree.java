package Estruturas.Trees;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Interfaces.BinarySearchTreeADT;

public class ArrayBinarySearchTree<T> extends ArrayBinaryTree<T> implements BinarySearchTreeADT<T> {

    protected int height;
    protected int maxIndex;
    public ArrayBinarySearchTree() {
        super();
        height = 0;
        maxIndex = -1;
    }

    public ArrayBinarySearchTree(T element) {
        super(element);
        height = 1;
        maxIndex = 0;
    }

    @Override
    public void addElement(T element) {

        if (tree.length < maxIndex*2+3)
            expandCapacity();
        Comparable<T> tempelement = (Comparable<T>)element;
        if (isEmpty()) {
            tree[0] = element;
            maxIndex = 0;
        }
        else{
            boolean added = false;
            int currentIndex = 0;
            while (!added) {
                if (tempelement.compareTo((tree[currentIndex]) ) < 0) {
                    if (tree[currentIndex*2+1] == null)
                    {
                        tree[currentIndex*2+1] = element;
                        added = true;
                        if (currentIndex*2+1 > maxIndex)
                            maxIndex = currentIndex*2+1;
                    }
                    else
                        currentIndex = currentIndex*2+1;
                }else {
                    if (tree[currentIndex*2+2] == null)
                    {
                        tree[currentIndex*2+2] = element;
                        added = true;
                        if (currentIndex*2+2 > maxIndex)
                            maxIndex = currentIndex*2+2;
                    }
                    else
                        currentIndex = currentIndex*2+2;
                }
            }
        }
        height = (int)(Math.log(maxIndex + 1) / Math.log(2)) + 1;
        count++;
    }

    private void expandCapacity() {
        T[] temp = (T[]) new Object[tree.length * 2];
        for (int i = 0; i < tree.length; i++)
            temp[i] = tree[i];
        tree = temp;
    }

    @Override
    public T removeElement(T targetElement) throws EmptyCollectionException, ElementNotFoundException {
        if (isEmpty()) {
            throw new EmptyCollectionException("binary tree");
        }

        T removed = null;
        boolean found = false;

        for (int i = 0; (i <= maxIndex) && !found; i++) {
            if ((tree[i] != null) && targetElement.equals(tree[i])) {
                found = true;
                removed = tree[i];
                try {
                    replacement(i);
                } catch (ElementNotFoundException e) {
                    tree[i] = null;
                }
                count--;
            }
        }
        if (!found) {
            throw new ElementNotFoundException("binary tree");
        }
        return removed;
    }

    private void replacement(int targetIndex) throws ElementNotFoundException {
        if (tree[targetIndex * 2 + 2] != null) {
            tree[targetIndex] = tree[targetIndex * 2 + 2];
            replacement(targetIndex * 2 + 2);
        } else if (tree[targetIndex * 2 + 1] != null) {
            tree[targetIndex] = tree[targetIndex * 2 + 1];
            replacement(targetIndex * 2 + 1);
        } else {
            throw new ElementNotFoundException("binary tree");
        }
    }

    @Override
    public void removeAllOccurrences(T targetElement) throws ElementNotFoundException, EmptyCollectionException {

        removeElement(targetElement);
        try {
            while (contains(targetElement))
                removeElement(targetElement);
        } catch (ElementNotFoundException e) {
            System.out.println("Elemento não encontrado");
        }

    }

    @Override
    public T removeMin() throws ElementNotFoundException, EmptyCollectionException {
        return removeElement(findMin());
    }

    @Override
    public T removeMax() throws ElementNotFoundException, EmptyCollectionException {
        return removeElement(findMax());
    }

    @Override
    public T findMin() throws ElementNotFoundException {
        if(isEmpty())
            throw new ElementNotFoundException("binary tree");

        int currentIndex = 0;
        while (tree[currentIndex*2+1] != null)
            currentIndex = currentIndex*2+1;
        return tree[currentIndex];
    }

    @Override
    public T findMax() throws ElementNotFoundException {
        if(isEmpty())
            throw new ElementNotFoundException("binary tree");

        int currentIndex = 0;
        while (tree[currentIndex*2+2] != null)
            currentIndex = currentIndex*2+2;
        return tree[currentIndex];

    }
}
