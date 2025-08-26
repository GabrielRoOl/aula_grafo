import java.util.*;
import java.util.stream.Collectors;

public class GrafoDenso implements Grafo {

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

    @Override
    public Set<Aresta> getArestes() {
        return new HashSet<>(this.arestas);
    }

    @Override
    public Set<String> getVertices() {
        return new LinkedHashSet<>(this.vertices);
    }


    @Override
    public boolean isSubGrafo(Grafo outroGrafo) {
        // Definição: G' (this) é um subgrafo de G (outroGrafo) se:
        // 1. O conjunto de vértices de G' é um subconjunto dos vértices de G.
        // 2. O conjunto de arestas de G' é um subconjunto das arestas de G.
        Set<String> verticesOutro = outroGrafo.getVertices();
        Set<Aresta> arestasOutro = outroGrafo.getArestes();

        return verticesOutro.containsAll(this.vertices) && arestasOutro.containsAll(this.arestas);
    }

    @Override
    public boolean isSubGrafoGerador(Grafo outroGrafo) {
        // Definição: G' (this) é um subgrafo gerador de G (outroGrafo) se:
        // 1. G' é um subgrafo de G.
        // 2. O conjunto de vértices de G' é IGUAL ao de G.
        Set<String> verticesOutro = outroGrafo.getVertices();
        Set<Aresta> arestasOutro = outroGrafo.getArestes();

        boolean mesmosVertices = verticesOutro.equals(this.vertices);
        boolean arestasContidas = arestasOutro.containsAll(this.arestas);

        return mesmosVertices && arestasContidas;
    }

    @Override
    public boolean isSubGrafoInduzido(Grafo outroGrafo) {
        // Definição: G' (this) é um subgrafo induzido por V' de G se:
        // 1. V' (vértices de this) é um subconjunto de V (vértices do outroGrafo).
        // 2. E' (arestas de this) contém TODAS as arestas de G que ligam vértices em V'.

        // Passo 1: Verifica se os vértices de 'this' são um subconjunto dos vértices do 'outroGrafo'.
        if (!outroGrafo.getVertices().containsAll(this.vertices)) {
            return false;
        }

        // Passo 2: Monta o conjunto de arestas que DEVERIAM existir em 'this' para ser induzido.
        Set<Aresta> arestasInduzidasEsperadas = new HashSet<>();
        for (Aresta arestaDoGrafoMaior : outroGrafo.getArestes()) {
            // Se ambos os vértices da aresta do grafo maior pertencem ao nosso conjunto
            // de vértices, então essa aresta deve estar no nosso subgrafo.
            if (this.vertices.contains(arestaDoGrafoMaior.v1()) && this.vertices.contains(arestaDoGrafoMaior.v2())) {
                arestasInduzidasEsperadas.add(arestaDoGrafoMaior);
            }
        }

        // Passo 3: Compara o conjunto de arestas de 'this' com o conjunto esperado.
        return this.arestas.equals(arestasInduzidasEsperadas);
    }
}

