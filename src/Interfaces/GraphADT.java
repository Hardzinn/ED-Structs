package Interfaces;

import Exceptions.EmptyCollectionException;

import java.util.Iterator;

public interface GraphADT<T> {


    public void addVertex(T vertex);
    public void removeVertex(T vertex);
    public void addEdge(T vertex1, T vertex2);
    public void removeEdge(T vertex1, T vertex2);
    public Iterator iteratorBFS(T startVertex) throws EmptyCollectionException;
    public Iterator iteratorDFS(T startVertex) throws EmptyCollectionException;
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) throws EmptyCollectionException;
    public boolean isEmpty();
    public boolean isConnected() throws EmptyCollectionException;
    public int size();

    public String toString();


}
