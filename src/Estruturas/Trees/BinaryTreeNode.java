package Estruturas.Trees;

public class BinaryTreeNode <T>{

    protected T element;
    protected BinaryTreeNode<T> left, right;

    public BinaryTreeNode(T obj) {
        this.element = obj;
        this.left = null;
        this.right = null;
    }

    public BinaryTreeNode(T obj, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
        this.element = obj;
        this.left = left;
        this.right = right;
    }

    public T getElement() {
        return element;
    }


    public void setElement(T element) {
        this.element = element;
    }

    public BinaryTreeNode<T> getLeft() {
        return left;
    }

    public BinaryTreeNode<T> getRight() {
        return right;
    }

    public void setLeft(BinaryTreeNode<T> left) {
        this.left = left;
    }

    public void setRight(BinaryTreeNode<T> right) {
        this.right = right;
    }

    public int numChildren() {
        int children = 0;

        if (this.left != null) {
            children = 1 + this.left.numChildren();
        }

        if (this.right != null) {
            children = children + 1 + this.right.numChildren();
        }

        return children;
    }

    public int getBalanceFactor() {
        return this.numChildren() - this.numChildren();
    }


}
