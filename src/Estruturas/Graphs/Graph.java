
package Estruturas.Graphs;

import Interfaces.GraphADT;
import Estruturas.Lists.ArrayUnorderedList;
import Exceptions.EmptyCollectionException;
import Exceptions.VertexNotFoundException;
import Estruturas.Queues.LinkedQueue;
import Estruturas.Stack.LinkedStack;
import java.util.Iterator;

public class Graph<T> implements GraphADT<T> {

    private static final int INFINITY = Integer.MAX_VALUE;
    protected final int DEFAULT_CAPACITY = 10;
    protected int numVertices; // number of vertices in the graph
    protected boolean[][] adjMatrix; // adjacency matrix
    protected T[] vertices; // values of vertices

    /**
     * Creates an empty graph.
     */
    public Graph() {
        numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    @Override
    public void addEdge(T vertex1, T vertex2) {
        try {
            addEdge(getIndex(vertex1), getIndex(vertex2));
        } catch (VertexNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private int getIndex(T vertex) throws VertexNotFoundException {
        for (int i = 0; i < this.numVertices; i++) {
            if (this.vertices[i] == vertex) {
                return i;
            }
        }
        throw new VertexNotFoundException("Vertice não encontrado");
    }

    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param index1 the first index
     * @param index2 the second index
     */
    private void addEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = true;
            adjMatrix[index2][index1] = true;
        }
    }

    private boolean indexIsValid(int index) {
        return (index >= 0 && index < this.numVertices);
    }

    /**
     * Adds a vertex to the graph, expanding the capacity of the graph if
     * necessary. It also associates an object with the vertex.
     *
     * @param vertex the vertex to add to the graph
     */
    public void addVertex(T vertex) {
        if (numVertices == vertices.length) {
            expandCapacity();
        }
        vertices[numVertices] = vertex;
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = false;
            adjMatrix[i][numVertices] = false;
        }
        numVertices++;
    }

    private void expandCapacity() {
        T[] newVertex = (T[]) (new Object[this.vertices.length * 2]);
        for (int i = 0; i < this.numVertices; i++) {
            newVertex[i] = this.vertices[i];
        }
        this.vertices = newVertex;

        boolean[][] newAdjMatrix = new boolean[this.vertices.length][this.vertices.length];

        for (int i = 0; i < this.numVertices; i++) {
            for (int j = 0; j < this.numVertices; j++) {
                newAdjMatrix[i][j] = this.adjMatrix[i][j];
            }
        }
        this.adjMatrix = newAdjMatrix;
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
            adjMatrix[numVertices][i] = false;
            adjMatrix[i][numVertices] = false;
        }
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) {
        try {
            this.adjMatrix[getIndex(vertex1)][getIndex(vertex2)] = false;
            this.adjMatrix[getIndex(vertex2)][getIndex(vertex1)] = false;
        } catch (VertexNotFoundException e) {
            System.out.println(e.getMessage());
        }
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
                if (adjMatrix[x.intValue()][i] && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        return (Iterator<T>) resultList.iterator();
    }

    @Override
    public Iterator iteratorDFS(T startVertex) throws VertexNotFoundException {
        return iteratorDFS(getIndex(startVertex));
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
                if (adjMatrix[x.intValue()][i] && !visited[i]) {
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
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) throws VertexNotFoundException {
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

    public int[] computeShortestPaths(T start) throws VertexNotFoundException {
        int startVertex = getIndex(start);
        int[] D = new int[numVertices];
        boolean[] tight = new boolean[numVertices];

        for (int z = 0; z < numVertices; z++) {
            D[z] = INFINITY;
        }
        D[startVertex] = 0;

        for (int count = 0; count < numVertices; count++) {
            int u = findMinDistanceVertex(D, tight);
            tight[u] = true;

            for (int z = 0; z < numVertices; z++) {
                if (!tight[z] && adjMatrix[u][z] == true && D[u] != INFINITY && D[u] + 1 < D[z]) {
                    D[z] = D[u] + 1;
                }
            }
        }

        return D;
    }

    private static int findMinDistanceVertex(int[] D, boolean[] tight) {
        int minDistance = INFINITY;
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
        int[] D = new int[numVertices];
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
                if (!tight[z] && adjMatrix[u][z] == true && D[u] != INFINITY && D[u] + 1 < D[z]) {
                    D[z] = D[u] + 1;
                    predecessors[z] = u;
                }
            }
        }

        return predecessors;
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
}
