package Estruturas.Trees;

public class HeapNode <T> extends BinaryTreeNode<T>{
    protected HeapNode<T> parent;
    public HeapNode(T element) {
        super(element);
        this.parent = null;
    }

    public HeapNode<T> getParent() {
        return parent;
    }

    public void setParent(HeapNode<T> parent) {
        this.parent = parent;
    }


}
