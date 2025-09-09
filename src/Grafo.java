import java.util.Map;
import java.util.Set;

interface Grafo {
    void imprimir();

    void numeroAresta();
    void numeroVertice();
    void adicionarVertice(String vertice);
    void sequenciaGraus();
    void adicionarAresta(String vertice1, String vertice2);
    void removerAresta(String vertice1, String vertice2);

    Set<Aresta> getArestas();

    Set<String> getVertices();

    boolean isSubGrafo(Grafo grafo);

    boolean isSubGrafoGerador(Grafo grafo);

    boolean isSubGrafoInduzido(Grafo grafo);

    Map<String, Integer> colorirGrafo();
}
