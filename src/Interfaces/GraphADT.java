package Interfaces;

import Exceptions.EmptyCollectionException;
import Exceptions.VertexNotFoundException;

import java.util.Iterator;

public interface GraphADT<T> {


    public void addVertex(T vertex);
    public void removeVertex(T vertex) throws VertexNotFoundException;
    public void addEdge(T vertex1, T vertex2);
    public void removeEdge(T vertex1, T vertex2);
    public Iterator iteratorBFS(T startVertex) throws EmptyCollectionException, VertexNotFoundException;
    public Iterator iteratorDFS(T startVertex) throws EmptyCollectionException, VertexNotFoundException;
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) throws EmptyCollectionException, VertexNotFoundException;
    public boolean isEmpty();
    public boolean isConnected() throws EmptyCollectionException;
    public int size();

    public String toString();


}
