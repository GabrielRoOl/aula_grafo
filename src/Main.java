import java.util.ArrayList;
import java.util.List;

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

    }

    static class GrafoDenso implements Grafo{
        List<String> vertices = new ArrayList<>();
        List<String> arestas = new ArrayList<>();

        @Override
        public void adicionarVertice(String vertice){
            vertices.add(vertice);
        }

        @Override
        public void adicionarAresta(String vertice1, String vertice2){
            String aresta = vertice1 + vertice2;
            arestas.add(aresta);

        }

        @Override
        public void removerAresta(String vertice1, String vertice2) {

        }

        @Override
        public void imprimir() {

        }

        @Override
        public void numeroAresta(){
            System.out.println(arestas.size());
        }

        @Override
        public void numeroVertice(){
            System.out.println(vertices.size());
        }

    }

    interface Grafo{
        void imprimir();
        void numeroAresta();
        void numeroVertice();
        void adicionarVertice(String vertice);
        void adicionarAresta(String vertice1, String vertice2);
        void removerAresta(String vertice1, String vertice2);
    }
}