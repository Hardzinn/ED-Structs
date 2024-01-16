package Estruturas.Aplicações;

import Estruturas.Graphs.Network;

public class network {

    public static void main(String[] args) {
        // Create a new Network
        Network<String> network = new Network<>();

        // Add vertices
        network.addVertex("A");
        network.addVertex("B");
        network.addVertex("C");

        // Check the size of the network
        if (network.size() != 3) {
            System.out.println("Error in addVertex or size method");
        }

        // Remove a vertex
        network.removeVertex("B");

        // Check the size of the network
        if (network.size() != 2) {
            System.out.println("Error in removeVertex or size method");
        }

        System.out.printf(network.toString());
    }
}
