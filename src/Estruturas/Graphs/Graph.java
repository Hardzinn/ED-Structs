package Estruturas.Graphs;

import Estruturas.Lists.ArrayUnorderedList;
import Estruturas.Queues.LinkedQueue;
import Estruturas.Stack.LinkedStack;
import Exceptions.EmptyCollectionException;
import Interfaces.GraphADT;

import java.util.Iterator;

public class Graph<T> implements GraphADT<T> {

    protected final int DEFAULT_CAPACITY = 10;
    protected int numVertices;
    protected boolean[][] adjMatrix;
    protected T[] vertices;

    public Graph(){
        this.numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) new Object[DEFAULT_CAPACITY];
    }


    @Override
    public void addVertex(T vertex) {
        if(numVertices == vertices.length){
            expandCapacity();
        }
        vertices[numVertices] = vertex;
        for(int i = 0; i < numVertices; i++){
            adjMatrix[numVertices][i] = false;
            adjMatrix[i][numVertices] = false;
        }
        numVertices++;

    }

    protected void expandCapacity() {
        T[] largerVertices = (T[]) (new Object[vertices.length * 2]);
        boolean[][] largerAdjMatrix = new boolean[vertices.length * 2][vertices.length * 2];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                largerAdjMatrix[i][j] = adjMatrix[i][j];
            }
            largerVertices[i] = vertices[i];
        }

        vertices = largerVertices;
        adjMatrix = largerAdjMatrix;
    }


    @Override
    public void removeVertex(T vertex) {

        for (int i = 0; i < numVertices; i++){
            if(vertex.equals(vertices[i])){
                removeVertex(i);
                return;
            }
        }

    }


    public void removeVertex(int index){
        if(indexIsValid(index)){
            numVertices--;
            for(int i = index; i < numVertices; i++){
                vertices[i] = vertices[i + 1];
            }
            for(int i = index; i < numVertices; i++){
                for(int j = 0; j < numVertices; j++){
                    adjMatrix[i][j] = adjMatrix[i + 1][j];
                }
            }
            for(int i = index; i < numVertices; i++){
                for(int j = 0; j < numVertices; j++){
                    adjMatrix[j][i] = adjMatrix[j][i + 1];
                }
            }
        }
    }


    boolean indexIsValid(int index){
        return ((index < numVertices) && (index >= 0));
    }

    int getIndex(T vertex){
        for(int i = 0; i < numVertices; i++){
            if(vertex.equals(vertices[i])){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void addEdge(T vertex1, T vertex2) {
        addEdge(getIndex(vertex1), getIndex(vertex2));
    }

    public void addEdge(int index1, int index2){
        if(indexIsValid(index1) && indexIsValid(index2)){
            adjMatrix[index1][index2] = true;
            adjMatrix[index2][index1] = true;
        }
    }


    @Override
    public void removeEdge(T vertex1, T vertex2) {
        removeEdge(getIndex(vertex1), getIndex(vertex2));
    }

    public void removeEdge(int index1, int index2){
        if(indexIsValid(index1) && indexIsValid(index2)){
            adjMatrix[index1][index2] = false;
            adjMatrix[index2][index1] = false;
        }
    }


    @Override
    public Iterator<T> iteratorBFS(T startVertex) throws EmptyCollectionException {
        return iteratorBFS(getIndex(startVertex));
    }

    public Iterator<T> iteratorBFS(int startIndex) throws EmptyCollectionException {
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();

        if(!indexIsValid(startIndex)){
            return resultList.iterator();
        }

        boolean[] visited = new boolean[numVertices];

        for(int i = 0; i < numVertices; i++){
            visited[i] = false;
        }
        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while(!traversalQueue.isEmpty()){
            x = traversalQueue.dequeue();
            resultList.addToRear(vertices[x]);

            for(int i = 0; i < numVertices; i++){
                if(adjMatrix[x][i] && !visited[i]){
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }

    @Override
    public Iterator<T> iteratorDFS(T startVertex) throws EmptyCollectionException {
        return iteratorDFS(getIndex(startVertex));
    }

    public Iterator<T> iteratorDFS(int startIndex) throws EmptyCollectionException {
        Integer x;
        boolean found;

        LinkedStack<Integer> traversalStack = new LinkedStack<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();
        boolean[] visited = new boolean[numVertices];

        if(!indexIsValid(startIndex)){
            return resultList.iterator();
        }


        for(int i = 0; i < numVertices; i++){
            visited[i] = false;
        }

        traversalStack.push(startIndex);
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;


        while(!traversalStack.isEmpty()){
            x = traversalStack.peek();
            found = false;

            for(int i = 0; i < numVertices; i++){
                if(adjMatrix[x][i] && !visited[i]){
                    traversalStack.push(i);
                    resultList.addToRear(vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }
            if(!found && !traversalStack.isEmpty()){
                traversalStack.pop();
            }
        }
        return resultList.iterator();
    }

    @Override
    public Iterator<T>  iteratorShortestPath(T startVertex, T targetVertex) throws EmptyCollectionException {
        return iteratorShortestPath(getIndex(startVertex),getIndex(targetVertex));
    }

    public Iterator<T> iteratorShortestPath(int startIndex, int targetIndex) throws EmptyCollectionException {
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return resultList.iterator();
        }

        Iterator<Integer> it = iteratorShortestPathIndices(startIndex,
                targetIndex);
        while (it.hasNext()) {
            resultList.addToRear(vertices[it.next()]);
        }
        return resultList.iterator();
    }
    protected Iterator<Integer> iteratorShortestPathIndices(int startIndex, int targetIndex) throws EmptyCollectionException {
        int index = startIndex;
        int[] pathLength = new int[numVertices];
        int[] predecessor = new int[numVertices];
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<Integer> resultList = new ArrayUnorderedList<>();

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex) || (startIndex == targetIndex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;
        pathLength[startIndex] = 0;
        predecessor[startIndex] = -1;

        while (!traversalQueue.isEmpty() && (index != targetIndex)) {
            index = traversalQueue.dequeue();

            //Update the pathLength for each unvisited vertex adjacent to the
            //vertex at the current index
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[index][i] && !visited[i]) {
                    pathLength[i] = pathLength[index] + 1;
                    predecessor[i] = index;
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        if (index != targetIndex) { // no path must have been found
            return resultList.iterator();
        }

        LinkedStack<Integer> stack = new LinkedStack<>();
        index = targetIndex;
        stack.push(index);
        do {
            index = predecessor[index];
            stack.push(index);
        } while (index != startIndex);

        while (!stack.isEmpty()) {
            resultList.addToRear(stack.pop());
        }

        return resultList.iterator();
    }

    @Override
    public boolean isEmpty() {
        return (numVertices == 0);
    }

    @Override
    public boolean isConnected() throws EmptyCollectionException {
        if (isEmpty()) {
            return false;
        }

        Iterator<T> it = iteratorBFS(0);
        int count = 0;

        while (it.hasNext()) {
            it.next();
            count++;
        }
        return (count == numVertices);    }

    @Override
    public int size() {
        return numVertices;
    }

    @Override
    public String toString() {
        String result = "";
        result += "Adjacency Matrix\n";
        result += "----------------\n";
        result += "index\t";
        for(int i = 0; i < numVertices; i++){
            result += "" + i;
        }
        result += "\n\n";
        for(int i = 0; i < numVertices; i++){
            result += "" + i + "\t";
            for(int j = 0; j < numVertices; j++){
                if(adjMatrix[i][j]){
                    result += "1";
                }else{
                    result += "0";
                }
            }
            result += "\n";
        }
        result += "\n\nVertex Values";
        result += "\n----------------\n";
        result += "index\tvalue\n\n";
        for(int i = 0; i < numVertices; i++){
            result += "" + i + "\t";
            result += vertices[i].toString() + "\n";
        }
        result += "\n";
        return result;
    }

    public T[] getVertices() {
        Object[] vertices = new Object[numVertices];
        Object vertex;

        for (int i = 0; i < numVertices; i++) {
            vertex = this.vertices[i];
            vertices[i] = vertex;
        }
        return (T[]) vertices;
    }

}
