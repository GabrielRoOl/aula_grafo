import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        System.out.println("\n============--------------+++++++++--------------==============");
        Grafo grafo = new GrafoDenso();
        grafo.adicionarVertice("A");
        grafo.adicionarVertice("B");
        grafo.adicionarVertice("C");
        grafo.adicionarVertice("D");
        grafo.adicionarVertice("E");

        grafo.adicionarAresta("A", "B");
        grafo.adicionarAresta("A", "C");
        grafo.adicionarAresta("C", "D");
        grafo.adicionarAresta("C", "E");
        grafo.adicionarAresta("C", "B");
        grafo.adicionarAresta("B", "D");


        // Visualizando o getArestas
        System.out.println(grafo.getArestes());

        // Visualizando o getVestices
        System.out.println(grafo.getVertices());

        // Visualiza se existe um subgrafo do gerador
        System.out.println(grafo.isSubGrafoGerador(grafo));

        // Visualiza se existe um subgrafo
        System.out.println(grafo.isSubGrafo(grafo));

        // Visualiza se existe um subgrafo induzido
        System.out.println(grafo.isSubGrafoInduzido(grafo));



        System.out.println("\n============--------------+++++++++--------------==============");


        Grafo grafoEsparso = new GrafoEsparso();
        grafoEsparso.adicionarVertice("A");
        grafoEsparso.adicionarVertice("B");
        grafoEsparso.adicionarVertice("C");
        grafoEsparso.adicionarVertice("D");
        grafoEsparso.adicionarVertice("E");


        grafoEsparso.adicionarAresta("A", "B");
        grafoEsparso.adicionarAresta("A", "C");
        grafoEsparso.adicionarAresta("A", "C");
        grafoEsparso.adicionarAresta("C", "D");
        grafoEsparso.adicionarAresta("C", "E");
        grafoEsparso.adicionarAresta("B", "D");

        // Visualizando o getArestas
        System.out.println(grafoEsparso.getArestes());

        // Visualizando o getVestices
        System.out.println(grafoEsparso.getVertices());

        // Visualiza se existe um subgrafo do gerador
        System.out.println(grafoEsparso.isSubGrafoGerador(grafo));

        // Visualiza se existe um subgrafo
        System.out.println(grafoEsparso.isSubGrafo(grafo));

        // Visualiza se existe um subgrafo induzido
        System.out.println(grafoEsparso.isSubGrafoInduzido(grafo));


    }
}


