import java.util.*;

public class Main
{
    public static void main(String[] args) {

        GrafoDenso v = new GrafoDenso();
        v.adicionarVertice("A");
        v.adicionarVertice("B");
        v.adicionarVertice("C");
        v.adicionarVertice("D");
        v.adicionarVertice("E");
        System.out.println("============--------------+++++++++--------------==============");
        v.adicionarAresta("A", "B");
        v.adicionarAresta("A", "C");
        v.adicionarAresta("C", "D");
        v.adicionarAresta("C", "E");
        v.adicionarAresta("B", "D");
        v.imprimir();
        System.out.println("============--------------+++++++++--------------==============");
        v.numeroVertice();
        v.numeroAresta();
        v.removerAresta("A", "C");
        v.sequenciaGraus();
        v.imprimir();

    }

    static class GrafoDenso implements Grafo{
        List<String> vertices = new ArrayList<>();
        List<String> arestas = new ArrayList<>();

        @Override
        public void adicionarVertice(String vertice){
            vertices.add(vertice);
        }

        @Override
        public void sequenciaGraus() {
            Map<String, Integer> graus = new HashMap<>();

            // Inicializa todos os vértices com grau 0
            for (String vertice : vertices) {
                graus.put(vertice, 0);
            }

            // Conta as arestas para cada vértice
            for (String aresta : arestas) {
                String v1 = aresta.substring(0, 1);
                String v2 = aresta.substring(1);
                graus.put(v1, graus.get(v1) + 1);
                graus.put(v2, graus.get(v2) + 1);
            }

            // Obtém os graus em uma lista e ordena em ordem decrescente
            List<Integer> sequencia = new ArrayList<>(graus.values());
            Collections.sort(sequencia, Collections.reverseOrder());

            System.out.println("Sequência de graus: " + sequencia);
        }

        @Override
        public void adicionarAresta(String vertice1, String vertice2){
            String aresta = vertice1 + vertice2;
            arestas.add(aresta);
        }

        @Override
        public void removerAresta(String vertice1, String vertice2) {
            if(arestas.isEmpty()){
                System.out.println("Não existe aresta.");
            }
            String aresta = vertice1 + vertice2;
            if(arestas.contains(aresta)){
                arestas.remove(aresta);
                System.out.println("Aresta " + aresta + " removida.");
            }else{
                System.out.println("Aresta " + aresta + " não existe.");
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
            System.out.println();
            System.out.println("-----------------");
            for (String verticeLinha : vertices) {
                System.out.print(verticeLinha + " | ");

                // Para cada vértice na coluna
                for (String verticeColuna : vertices) {
                    // Verifica se existe aresta entre verticeLinha e verticeColuna
                    String aresta1 = verticeLinha + verticeColuna;
                    String aresta2 = verticeColuna + verticeLinha; // Para grafos não direcionados
                    int valor = (arestas.contains(aresta1) || arestas.contains(aresta2)) ? 1 : 0;
                    System.out.print(valor + "  ");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println("\n============--------------+++++++++--------------==============\n");
        }

        @Override
        public void numeroAresta(){
            System.out.println("Número de Arestas: " + arestas.size());
        }

        @Override
        public void numeroVertice(){
            System.out.println("Número de Vertices: " + vertices.size());
        }
    }

    interface Grafo{
        void imprimir();
        void numeroAresta();
        void numeroVertice();
        void adicionarVertice(String vertice);
        void sequenciaGraus();
        void adicionarAresta(String vertice1, String vertice2);
        void removerAresta(String vertice1, String vertice2);
    }
}