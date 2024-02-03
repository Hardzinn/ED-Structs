/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estruturas.Graphs;


import Interfaces.NetworkADT;
import Estruturas.Lists.ArrayUnorderedList;
import Exceptions.EmptyCollectionException;
import Exceptions.VertexNotFoundException;
import Estruturas.Trees.LinkedHeap;
import Estruturas.Queues.LinkedQueue;
import Estruturas.Stack.LinkedStack;

import java.io.Serializable;
import java.util.Iterator;



public class Network<T> implements NetworkADT<T>,Serializable {

    private static final double INFINITY = Double.POSITIVE_INFINITY;
    protected final int DEFAULT_CAPACITY = 10;
    protected int numVertices; // number of vertices in the graph
    protected double[][] adjMatrix; // adjacency matrix
    protected T[] vertices; // values of vertices

    public Network() {
        numVertices = 0;
        this.adjMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        try {
            addEdge(getIndex(vertex1), getIndex(vertex2), weight);
        } catch (VertexNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Inserts an edge between two vertices of the network graph.
     *
     * @param index1 the first index
     * @param index2 the second index
     */
    private void addEdge(int index1, int index2, double weight) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = weight;
            adjMatrix[index2][index1] = weight;
        }
    }

    @Override
    public void addVertex(T vertex) {
        if (numVertices == vertices.length) {
            expandCapacity();
        }
        vertices[numVertices] = vertex;
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = INFINITY;
            adjMatrix[i][numVertices] = INFINITY;
        }
        adjMatrix[numVertices][numVertices] = 0;
        numVertices++;
    }

    private void expandCapacity() {
        T[] newVertex = (T[]) (new Object[this.vertices.length * 2]);
        for (int i = 0; i < this.numVertices; i++) {
            newVertex[i] = this.vertices[i];
        }
        this.vertices = newVertex;

        double[][] newAdjMatrix = new double[this.vertices.length][this.vertices.length];

        for (int i = 0; i < this.numVertices; i++) {
            for (int j = 0; j < this.numVertices; j++) {
                newAdjMatrix[i][j] = this.adjMatrix[i][j];
            }
        }
        this.adjMatrix = newAdjMatrix;
    }

    @Override
    public void addEdge(T vertex1, T vertex2) {
        try {
            addEdge(getIndex(vertex1), getIndex(vertex2));
        } catch (VertexNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param index1 the first index
     * @param index2 the second index
     */
    private void addEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = 1;
            adjMatrix[index2][index1] = 1;
        }
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) {
        try {
            this.adjMatrix[getIndex(vertex1)][getIndex(vertex2)] = INFINITY;
            this.adjMatrix[getIndex(vertex2)][getIndex(vertex1)] = INFINITY;
        } catch (VertexNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean indexIsValid(int index) {
        return (index >= 0 && index < this.numVertices);
    }

    private int getIndex(T vertex) throws VertexNotFoundException {
        for (int i = 0; i < this.numVertices; i++) {
            if (this.vertices[i] ==  vertex) {
                return i;
            }
        }
        throw new VertexNotFoundException("Vertice não encontrado");
    }

    @Override
    public Iterator iteratorBFS(T startVertex) throws VertexNotFoundException {
        return iteratorBFS(getIndex(startVertex));
    }

    /**
     * Returns an iterator that performs a breadth first search traversal
     * starting at the given index.
     *
     * @param startIndex the index to begin the search from
     * @return an iterator that performs a breadth first traversal
     */
    private Iterator<T> iteratorBFS(int startIndex) {
        Integer x = -1;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        if (!indexIsValid(startIndex)) {
            return (Iterator<T>) resultList.iterator();
        }
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            try {
                x = traversalQueue.dequeue();
            } catch (EmptyCollectionException e) {
                System.out.println(e.getMessage());
            }

            resultList.addToRear(vertices[x.intValue()]);
            /**
             * Find all vertices adjacent to x that have not been visited and
             * queue them up
             */
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x.intValue()][i] != INFINITY && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        return (Iterator<T>) resultList.iterator();
    }

    @Override
    public Iterator iteratorDFS(T startVertex) {
        return iteratorDFS(startVertex);
    }

    /**
     * Returns an iterator that performs a depth first search traversal starting
     * at the given index.
     *
     * @param startIndex the index to begin the search traversal from
     * @return an iterator that performs a depth first traversal
     */
    private Iterator<T> iteratorDFS(int startIndex) {
        Integer x = -1;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        boolean[] visited = new boolean[numVertices];
        if (!indexIsValid(startIndex)) {
            return (Iterator<T>) resultList.iterator();
        }
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalStack.push(startIndex);
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;

        while (!traversalStack.isEmpty()) {
            try {
                x = traversalStack.peek();
            } catch (EmptyCollectionException ex) {
                System.out.println(ex.getMessage());
            }
            found = false;
            /**
             * Find a vertex adjacent to x that has not been visited and push it
             * on the stack
             */
            for (int i = 0; (i < numVertices) && !found; i++) {
                if (adjMatrix[x.intValue()][i] != INFINITY && !visited[i]) {
                    traversalStack.push(i);
                    resultList.addToRear(vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }
            if (!found && !traversalStack.isEmpty()) {
                try {
                    traversalStack.pop();
                } catch (EmptyCollectionException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return (Iterator<T>) resultList.iterator();
    }

    @Override
    public boolean isEmpty() {
        return (this.numVertices == 0);
    }

    @Override
    public boolean isConnected() {
        Iterator it = this.iteratorBFS(0);
        int i = 0;

        while (it.hasNext()) {
            it.next();
            i++;
        }

        return (i == this.numVertices);
    }

    @Override
    public int size() {
        return this.numVertices;
    }

    @Override
    public void removeVertex(T vertex) throws VertexNotFoundException {
        int indexToRemove = getIndex(vertex);

        for (int i = indexToRemove; i < numVertices - 1; i++) {
            vertices[i] = vertices[i + 1];
            for (int j = 0; j < numVertices; j++) {
                adjMatrix[i][j] = adjMatrix[i + 1][j];
            }
            for (int j = 0; j < numVertices; j++) {
                adjMatrix[j][i] = adjMatrix[j][i + 1];
            }
        }
        numVertices--;

        for (int i = 0; i < numVertices; i++) {
            adjMatrix[numVertices][i] = INFINITY;
            adjMatrix[i][numVertices] = INFINITY;
        }
    }

    @Override
    public double shortestPathWeight(T vertex1, T vertex2) throws VertexNotFoundException {
        Iterator<T> it = this.iteratorShortestPath(vertex1, vertex2);
        T previous = null;
        T current;
        double total = 0;
        while (it.hasNext()) {
            current = it.next();
            if (previous != null) {
                total += edgeWeight(previous, current);
            }
            previous = current;
        }
        return total;
    }

    private double edgeWeight(T vertex1, T vertex2) throws VertexNotFoundException {
        return this.adjMatrix[getIndex(vertex1)][getIndex(vertex2)];
    }

    @Override
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) throws VertexNotFoundException {
        ArrayUnorderedList<T> shortestPath = new ArrayUnorderedList<T>();
        int[] predecessors = getcomputedShortestPaths(startVertex);
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);
        int currentIndex = targetIndex;

        while (currentIndex != startIndex) {
            shortestPath.addToFront(this.vertices[currentIndex]);
            currentIndex = predecessors[currentIndex];
            if (currentIndex == -1) {
                return null;
            }
        }

        shortestPath.addToFront(this.vertices[startIndex]);

        return (Iterator<T>) shortestPath.iterator();
    }

    public double[] computeShortestPaths(T start) throws VertexNotFoundException {
        int startVertex = getIndex(start);
        double[] D = new double[numVertices];
        boolean[] tight = new boolean[numVertices];

        for (int z = 0; z < numVertices; z++) {
            D[z] = INFINITY;
        }
        D[startVertex] = 0;

        for (int count = 0; count < numVertices; count++) {
            int u = findMinDistanceVertex(D, tight);
            tight[u] = true;

            for (int z = 0; z < numVertices; z++) {
                if (!tight[z] && adjMatrix[u][z] != INFINITY && D[u] != INFINITY && D[u] + adjMatrix[u][z] < D[z]) {
                    D[z] = D[u] + adjMatrix[u][z];
                }
            }
        }

        return D;
    }

    private static int findMinDistanceVertex(double[] D, boolean[] tight) {
        double minDistance = INFINITY;
        int minVertex = -1;

        for (int v = 0; v < D.length; v++) {
            if (!tight[v] && D[v] <= minDistance) {
                minDistance = D[v];
                minVertex = v;
            }
        }

        return minVertex;
    }

    public int[] getcomputedShortestPaths(T start) throws VertexNotFoundException {
        int startVertex = getIndex(start);
        double[] D = new double[numVertices];
        boolean[] tight = new boolean[numVertices];
        int[] predecessors = new int[this.numVertices];

        for (int z = 0; z < numVertices; z++) {
            D[z] = INFINITY;
        }
        D[startVertex] = 0;

        for (int count = 0; count < numVertices; count++) {
            int u = findMinDistanceVertex(D, tight);
            tight[u] = true;

            for (int z = 0; z < numVertices; z++) {
                if (!tight[z] && adjMatrix[u][z] != INFINITY && D[u] != INFINITY && D[u] + adjMatrix[u][z] < D[z]) {
                    D[z] = D[u] + adjMatrix[u][z];
                    predecessors[z] = u;
                }
            }
        }

        return predecessors;
    }

    public String adjPrint() {
        String s = "";
        for (int i = 0; i < this.numVertices; i++) {
            for (int j = 0; j < this.numVertices; j++) {
                s += adjMatrix[i][j] + " ";
            }
            s += "\n";
        }
        return s;

    }

    /**
     * Returns a minimum spanning tree of the network.
     *
     * @return a minimum spanning tree of the network
     */
    public Network mstNetwork() {
        int x, y;
        int index;
        double weight;
        int[] edge = new int[2];
        LinkedHeap<Double> minHeap = new LinkedHeap<Double>();
        Network<T> resultGraph = new Network<T>();
        if (isEmpty() || !isConnected()) {
            return resultGraph;
        }
        resultGraph.adjMatrix = new double[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                resultGraph.adjMatrix[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        resultGraph.vertices = (T[]) (new Object[numVertices]);

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        edge[0] = 0;
        resultGraph.vertices[0] = this.vertices[0];
        resultGraph.numVertices++;
        visited[0] = true;
        /**
         * Add all edges, which are adjacent to the starting vertex, to the heap
         */
        for (int i = 0; i < numVertices; i++) {
            minHeap.addElement(adjMatrix[0][i]);
        }
        while ((resultGraph.size() < this.size()) && !minHeap.isEmpty()) {
            /**
             * Get the edge with the smallest weight that has exactly one vertex
             * already in the resultGraph
             */
            do {
                try {
                    weight = (minHeap.removeMin()).doubleValue();
                    edge = getEdgeWithWeightOf(weight, visited);
                } catch (EmptyCollectionException ex) {
                    System.out.println("Heap Vazia");
                }

            } while (!indexIsValid(edge[0]) || !indexIsValid(edge[1]));

            x = edge[0];
            y = edge[1];
            if (!visited[x]) {
                index = x;
            } else {
                index = y;
            }
            /**
             * Add the new edge and vertex to the resultGraph
             */
            resultGraph.vertices[index] = this.vertices[index];
            visited[index] = true;
            resultGraph.numVertices++;
            resultGraph.adjMatrix[x][y] = this.adjMatrix[x][y];
            resultGraph.adjMatrix[y][x] = this.adjMatrix[y][x];

            /**
             * Add all edges, that are adjacent to the newly added vertex, to
             * the heap
             */
            for (int i = 0; i < numVertices; i++) {
                if (!visited[i] && (this.adjMatrix[i][index]
                        < Double.POSITIVE_INFINITY)) {
                    edge[0] = index;
                    edge[1] = i;
                    minHeap.addElement(adjMatrix[index][i]);
                }
            }
        }
        return resultGraph;

    }

    public String mstNetworkToString() {
        Network<T> mst = mstNetwork();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < mst.numVertices; i++) {
            for (int j = 0; j < mst.numVertices; j++) {
                if (mst.adjMatrix[i][j] != Double.POSITIVE_INFINITY) {
                    sb.append(mst.vertices[i]).append(" - ").append(mst.vertices[j]).append(" : ").append(mst.adjMatrix[i][j]).append("\n");
                }
            }
        }

        return sb.toString();
    }

    private int[] getEdgeWithWeightOf(double weight, boolean[] visited) {
        int[] edge = new int[2];
        for (int i = 0; i < this.numVertices; i++) {
            for (int j = 0; j < this.numVertices; j++) {
                if (this.onlyOneVertexVisited(i, j, visited)) {
                    if (weight == adjMatrix[i][j]) {
                        edge[0] = i;
                        edge[1] = j;
                        return edge;
                    }
                }
            }
        }
        edge[0] = -1;
        edge[1] = -1;
        return edge;
    }

    private boolean onlyOneVertexVisited(int index1, int index2, boolean[] visited) {
        if (visited[index1] && visited[index2]) {
            return false;
        } else if (visited[index1]) {
            return true;
        } else if (visited[index2]) {
            return true;
        }

        return false;
    }

}
