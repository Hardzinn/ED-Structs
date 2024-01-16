package Estruturas.Trees;

import Estruturas.Lists.ArrayUnorderedList;
import Exceptions.ElementNotFoundException;
import Interfaces.BinaryTreeADT;

import java.util.Iterator;

public class ArrayBinaryTree<T> implements BinaryTreeADT<T> {

    protected int count;
    protected T[] tree;
    private final int CAPACITY = 50;

    public ArrayBinaryTree() {
        this.count = 0;
        this.tree = (T[]) new Object[CAPACITY];
    }

    public ArrayBinaryTree(T element) {
        this.count = 1;
        this.tree = (T[]) new Object[CAPACITY];
        this.tree[0] = element;
    }

    @Override
    public T getRoot() throws ElementNotFoundException {
        if (isEmpty())
            throw new ElementNotFoundException("binary tree");
        return tree[0];
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean contains(T element) throws ElementNotFoundException {

        if (isEmpty())
            throw new ElementNotFoundException("binary tree");

        try {
            find(element);
            return true;
        } catch (ElementNotFoundException e) {
            return false;
        }

    }

    public T find(T targetElement) throws ElementNotFoundException {
        T temp = null;
        boolean found = false;

        for (int ct = 0; ct < count && !found; ct++)
            if (targetElement.equals(tree[ct])) {
                found = true;
                temp = tree[ct];
            }
        if (!found)
            throw new ElementNotFoundException("binary tree");
        return temp;
    }
    @Override
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        inorder(0, templist);
        return templist.iterator();
    }

    protected void inorder(int node, ArrayUnorderedList<T> templist) {
        if (node < tree.length)
            if (tree[node] != null) {
                inorder(node * 2 + 1, templist);
                templist.addToRear(tree[node]);
                inorder((node + 1) * 2, templist);
            }
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        preorder(0, templist);
        return templist.iterator();
    }

    protected void preorder(int node, ArrayUnorderedList<T> templist) {
        if (node < tree.length)
            if (tree[node] != null) {
                templist.addToRear(tree[node]);
                preorder(node * 2 + 1, templist);
                preorder((node + 1) * 2, templist);
            }
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        postorder(0, templist);
        return templist.iterator();
    }

    protected void postorder(int node, ArrayUnorderedList<T> templist) {
        if (node < tree.length)
            if (tree[node] != null) {
                postorder(node * 2 + 1, templist);
                postorder((node + 1) * 2, templist);
                templist.addToRear(tree[node]);
            }
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        for (int i = 0; i < count; i++)
            templist.addToRear(tree[i]);
        return templist.iterator();
    }



}
