package Estruturas.Trees;

import Estruturas.Lists.LinkedUnorderedList;
import Estruturas.Queues.LinkedQueue;
import Estruturas.Trees.BinaryTreeNode;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Interfaces.BinaryTreeADT;

import java.util.Iterator;

public class LinkedBinaryTree<T> implements BinaryTreeADT<T> {

    protected int count;
    protected BinaryTreeNode<T> root;


    public LinkedBinaryTree() {
        this.count = 0;
        this.root = null;
    }

    public LinkedBinaryTree(T element) {
        this.count = 1;
        this.root = new BinaryTreeNode<T>(element);
    }

    public void setRoot(BinaryTreeNode<T> root) throws EmptyCollectionException {
        if(isEmpty())
            throw new EmptyCollectionException("binary tree");
        this.root = root;
    }

    @Override
    public T getRoot() throws EmptyCollectionException {
        if (isEmpty())
            throw new EmptyCollectionException("binary tree");

        return root.getElement();
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
        } catch (ElementNotFoundException | EmptyCollectionException e) {
            return false;
        }
    }

    @Override
    public T find(T element) throws ElementNotFoundException, EmptyCollectionException {

        if (isEmpty())
            throw new EmptyCollectionException("binary tree");


        BinaryTreeNode<T> current = findAgain(element, root);

        if(current == null)
            throw new ElementNotFoundException("binary tree");



        return current.getElement();

    }

    private BinaryTreeNode<T> findAgain(T element, BinaryTreeNode<T> next) {
        if (next == null)
            return null;

        if (next.getElement().equals(element))
            return next;

        BinaryTreeNode<T> temp = findAgain(element, next.getLeft());

        if (temp == null)
            temp = findAgain(element, next.getRight());

        return temp;
    }

    @Override
    public Iterator<T> iteratorInOrder() {
        LinkedUnorderedList<T> templist = new LinkedUnorderedList<T>();
        inorder(0, templist);
        return templist.iterator();
    }

    private void inorder(int index, LinkedUnorderedList<T> templist) {
        if (index < count) {
            inorder(2 * index + 1, templist);
            templist.addToRear(root.getElement());
            inorder(2 * index + 2, templist);
        }
    }


    @Override
    public Iterator<T> iteratorPreOrder() {
        LinkedUnorderedList<T> templist = new LinkedUnorderedList<T>();
        preorder(0, templist);
        return templist.iterator();

    }

    private void preorder(int index, LinkedUnorderedList<T> templist) {
        if (index < count) {
            templist.addToRear(root.getElement());
            preorder(2 * index + 1, templist);
            preorder(2 * index + 2, templist);
        }
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        LinkedUnorderedList<T> templist = new LinkedUnorderedList<T>();
        postorder(0, templist);
        return templist.iterator();
    }

    private void postorder(int index, LinkedUnorderedList<T> templist) {
        if (index < count) {
            postorder(2 * index + 1, templist);
            postorder(2 * index + 2, templist);
            templist.addToRear(root.getElement());
        }
    }

    @Override
    public Iterator<T> iteratorLevelOrder() throws EmptyCollectionException {
        LinkedUnorderedList<T> templist = new LinkedUnorderedList<T>();
        LinkedQueue<BinaryTreeNode<T>> queue = new LinkedQueue<BinaryTreeNode<T>>();

        if (!isEmpty()) {
            queue.enqueue(root);

            while (!queue.isEmpty()) {
                BinaryTreeNode<T> current = queue.dequeue();
                if(current.getLeft() != null)
                    queue.enqueue(current.getLeft());
                if(current.getRight() != null)
                    queue.enqueue(current.getRight());
                templist.addToRear(current.getElement());

            }
        }

        return templist.iterator();
    }



}
