package Interfaces;

import Exceptions.EmptyCollectionException;
import Exceptions.VertexNotFoundException;

public interface NetworkADT<T> extends GraphADT<T> {

    public void addEdge(T vertex1, T vertex2, double weight);
    public double shortestPathWeight(T vertex1, T vertex2) throws EmptyCollectionException, VertexNotFoundException;


}
