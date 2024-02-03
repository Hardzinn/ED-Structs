package Estruturas.Aplicações;

import Estruturas.Graphs.Network;
import Exceptions.EmptyCollectionException;

public class GraphTest {

    public static void main(String[] args) throws EmptyCollectionException {
        Network<Integer> graph = new Network<>();

        for(int i = 0; i < 6; i++){
            graph.addVertex(i);
        }

        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 20);
        graph.addEdge(0, 3, 30);
        graph.addEdge(1, 2, 40);
        graph.addEdge(1, 4, 50);
        graph.addEdge(2, 3, 60);
        graph.addEdge(2, 4, 70);
        graph.addEdge(4, 5, 80);

        System.out.println(graph.adjPrint());

        System.out.println(graph.mstNetworkToString());

    }

}
