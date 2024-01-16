package Estruturas.Aplicações;

import Estruturas.Lists.LinkedListSentinel;

public class LinkedListWithSentinelTeste {

    public static void main(String[] args) {
        // TODO code application logic here
        LinkedListSentinel<Integer> lista = new LinkedListSentinel<>();

        lista.addNode(1);
        lista.addNode(2);
        lista.addNode(3);
        lista.addNode(4);
        lista.addNode(5);
        lista.addNode(6);

        lista.print();
        System.out.println("Removendo o 4");
        lista.removeNode(4);
        lista.print();


    }
}
