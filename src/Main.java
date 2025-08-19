import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
//        System.out.println("\nINICIANDO PROGRAMA ...");
//        Grafo grafo = new GrafoDenso();
//        grafo.adicionarVertice("A");
//        grafo.adicionarVertice("B");
//        grafo.adicionarVertice("C");
//        grafo.adicionarVertice("D");
//        grafo.adicionarVertice("E");
//
//        grafo.adicionarAresta("A", "B");
//        grafo.adicionarAresta("A", "C");
//        grafo.adicionarAresta("C", "D");
//        grafo.adicionarAresta("C", "E");
//        grafo.adicionarAresta("B", "D");
//
//        grafo.imprimir();
//        grafo.numeroVertice();
//        grafo.numeroAresta();
//        grafo.sequenciaGraus();
//
//        grafo.removerAresta("A", "C");
//
//
//        grafo.imprimir();
//        grafo.numeroVertice();
//        grafo.numeroAresta();
//        grafo.sequenciaGraus();
//
//        System.out.println("\n============--------------+++++++++--------------==============");
//        System.out.println("\nFIM DO PROGRAMA ....");
        System.out.println("\n--- TESTANDO GRAFO ESPARSO (MULTIGRAFO COM LISTA DE ADJACÊNCIAS) ---");

        // Criando a instância da nova classe GrafoEsparso
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

        System.out.println();
        grafoEsparso.imprimir();
        grafoEsparso.numeroVertice();
        grafoEsparso.numeroAresta();
        grafoEsparso.sequenciaGraus();

        System.out.println();
        grafoEsparso.removerAresta("A", "C");

        System.out.println("\nApós remover uma aresta (A,C):");
        grafoEsparso.imprimir();
        grafoEsparso.numeroVertice();
        grafoEsparso.numeroAresta();
        grafoEsparso.sequenciaGraus();

    }

    public record Aresta(String v1, String v2) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Aresta aresta = (Aresta) o;
            // A aresta (A, B) é igual a (B, A)
            return (Objects.equals(v1, aresta.v1) && Objects.equals(v2, aresta.v2)) ||
                    (Objects.equals(v1, aresta.v2) && Objects.equals(v2, aresta.v1));
        }

        @Override
        public int hashCode() {
            return Objects.hash(v1) + Objects.hash(v2);
        }
    }

    static class GrafoDenso implements Grafo {

        private final Set<String> vertices = new LinkedHashSet<>();

        private final Set<Aresta> arestas = new HashSet<>();

        @Override
        public void adicionarVertice(String vertice) {
            vertices.add(vertice);
        }

        @Override
        public void sequenciaGraus() {
            if (vertices.isEmpty()) {
                System.out.println("Sequência de graus: [ ]");
                return;
            }


            Map<String, Integer> graus = new LinkedHashMap<>();
            for (String vertice : vertices) {
                graus.put(vertice, 0);
            }


            for (Aresta aresta : arestas) {
                graus.put(aresta.v1(), graus.get(aresta.v1()) + 1);
                graus.put(aresta.v2(), graus.get(aresta.v2()) + 1);
            }


            String sequencia = graus.values().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            System.out.println("Sequência de graus: [ " + sequencia + " ]");
        }

        @Override
        public void adicionarAresta(String vertice1, String vertice2) {

            if (vertices.contains(vertice1) && vertices.contains(vertice2)) {
                arestas.add(new Aresta(vertice1, vertice2));
            } else {
                System.out.println("Erro: Um ou ambos os vértices não existem no grafo.");
            }
        }

        @Override
        public void removerAresta(String vertice1, String vertice2) {
            if (arestas.isEmpty()) {
                System.out.println("Não há arestas para remover.");
                return;
            }
            Aresta arestaParaRemover = new Aresta(vertice1, vertice2);
            if (arestas.remove(arestaParaRemover)) {
                System.out.println("Aresta (" + vertice1 + ", " + vertice2 + ") removida.");
            } else {
                System.out.println("Aresta (" + vertice1 + ", " + vertice2 + ") não existe.");
            }
        }

        @Override
        public void imprimir() {
            System.out.println("\n============--------------+++++++++--------------==============\n");
            System.out.println("Matriz de Adjacência:");

            System.out.print("    ");
            for (String vertice : vertices) {
                System.out.print(vertice + "  ");
            }
            System.out.println("\n---------------------");

            for (String verticeLinha : vertices) {
                System.out.print(verticeLinha + " | ");
                for (String verticeColuna : vertices) {
                    Aresta aresta = new Aresta(verticeLinha, verticeColuna);
                    System.out.print((arestas.contains(aresta) ? 1 : 0) + "  ");
                }
                System.out.println();
            }
            System.out.println("\n============--------------+++++++++--------------==============\n");
        }

        @Override
        public void numeroAresta() {
            System.out.println("Número de Arestas: " + arestas.size());
        }

        @Override
        public void numeroVertice() {
            System.out.println("Número de Vértices: " + vertices.size());
        }
    }

    static class GrafoEsparso implements Grafo {

        private final Map<String, List<String>> adjacencias = new LinkedHashMap<>();

        @Override
        public void adicionarVertice(String vertice) {
            adjacencias.putIfAbsent(vertice, new ArrayList<>());
        }

        @Override
        public void adicionarAresta(String v1, String v2) {
            if (!adjacencias.containsKey(v1) || !adjacencias.containsKey(v2)) {
                System.out.println("Erro: Vértice não encontrado para adicionar aresta.");
                return;
            }
            adjacencias.get(v1).add(v2);
            adjacencias.get(v2).add(v1);
        }

        @Override
        public void removerAresta(String v1, String v2) {
            if (!adjacencias.containsKey(v1) || !adjacencias.containsKey(v2)) {
                System.out.println("Aresta (" + v1 + ", " + v2 + ") não existe.");
                return;
            }
            boolean removeu1 = adjacencias.get(v1).remove(v2);
            boolean removeu2 = adjacencias.get(v2).remove(v1);

            if (removeu1 && removeu2) {
                System.out.println("Aresta removida entre " + v1 + " e " + v2 + ".");
            } else {
                System.out.println("Aresta (" + v1 + ", " + v2 + ") não existe.");
            }
        }

        @Override
        public void imprimir() {
            System.out.println("Lista de Adjacências:");
            for (Map.Entry<String, List<String>> entry : adjacencias.entrySet()) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }
        }

        @Override
        public void numeroAresta() {
            int totalDeGraus = 0;
            for (List<String> vizinhos : adjacencias.values()) {
                totalDeGraus += vizinhos.size();
            }
            System.out.println("Número de arestas: " + totalDeGraus / 2);
        }

        @Override
        public void numeroVertice() {
            System.out.println("Número de vértices: " + adjacencias.size());
        }

        @Override
        public void sequenciaGraus() {
            String sequencia = adjacencias.values().stream()
                    .map(List::size)
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            System.out.println("Sequência de graus: [ " + sequencia + " ]");
        }
    }
}


interface Grafo {
        void imprimir();
        void numeroAresta();
        void numeroVertice();
        void adicionarVertice(String vertice);
        void sequenciaGraus();
        void adicionarAresta(String vertice1, String vertice2);
        void removerAresta(String vertice1, String vertice2);
    }
}