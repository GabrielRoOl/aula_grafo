import java.util.*;
import java.util.stream.Collectors;

public class GrafoEsparso implements Grafo {

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

    @Override
    public Set<Aresta> getArestas() {
        Set<Aresta> arestas = new HashSet<>();
        for (String verticeOrigem : adjacencias.keySet()) {
            for (String verticeDestino : adjacencias.get(verticeOrigem)) {
                // Adiciona a aresta. Graças à implementação de equals/hashCode na classe
                // Aresta e à natureza do Set, a aresta (A, B) é tratada como igual
                // a (B, A), evitando duplicidade.
                arestas.add(new Aresta(verticeOrigem, verticeDestino));
            }
        }
        return arestas;
    }

    @Override
    public Set<String> getVertices() {
        // O conjunto de chaves do mapa de adjacências é o conjunto de vértices.
        // Retorna uma cópia para proteger a estrutura interna do grafo.
        return new LinkedHashSet<>(adjacencias.keySet());
    }

    @Override
    public boolean isSubGrafo(Grafo outroGrafo) {
        // G' (this) é um subgrafo de G (outroGrafo) se V' ⊆ V e E' ⊆ E.
        Set<String> verticesOutro = outroGrafo.getVertices();
        Set<Aresta> arestasOutro = outroGrafo.getArestas();

        return verticesOutro.containsAll(this.getVertices()) && arestasOutro.containsAll(this.getArestas());
    }

    @Override
    public boolean isSubGrafoGerador(Grafo outroGrafo) {
        // G' (this) é um subgrafo gerador de G (outroGrafo) se V' = V e E' ⊆ E.
        Set<String> verticesOutro = outroGrafo.getVertices();
        Set<Aresta> arestasOutro = outroGrafo.getArestas();

        boolean mesmosVertices = verticesOutro.equals(this.getVertices());
        boolean arestasContidas = arestasOutro.containsAll(this.getArestas());

        return mesmosVertices && arestasContidas;
    }

    @Override
    public boolean isSubGrafoInduzido(Grafo outroGrafo) {
        // G' (this) é induzido por V' se V' ⊆ V e E' contém TODAS as arestas de G
        // que conectam vértices em V'.

        // Passo 1: Verifica se os vértices de 'this' são um subconjunto dos do 'outroGrafo'.
        if (!outroGrafo.getVertices().containsAll(this.getVertices())) {
            return false;
        }

        // Passo 2: Monta o conjunto de arestas que 'this' DEVERIA ter.
        Set<Aresta> arestasInduzidasEsperadas = new HashSet<>();
        Set<String> nossosVertices = this.getVertices();

        for (Aresta arestaDoGrafoMaior : outroGrafo.getArestas()) {
            if (nossosVertices.contains(arestaDoGrafoMaior.v1()) && nossosVertices.contains(arestaDoGrafoMaior.v2())) {
                arestasInduzidasEsperadas.add(arestaDoGrafoMaior);
            }
        }

        // Passo 3: Compara o conjunto de arestas real com o esperado.
        return this.getArestas().equals(arestasInduzidasEsperadas);
    }

    @Override
    public Map<String, Integer> colorirGrafo() {
        // Mapa para guardar o resultado: Vértice -> Cor (Integer)
        Map<String, Integer> cores = new LinkedHashMap<>();

        // Itera sobre cada vértice do grafo para atribuir uma cor
        for (String vertice : this.getVertices()) {
            // Conjunto para armazenar as cores dos vizinhos já coloridos
            Set<Integer> coresVizinhas = new HashSet<>();
            
            // Pega a lista de vizinhos do vértice atual
            List<String> vizinhos = adjacencias.get(vertice);
            if (vizinhos != null) {
                // Para cada vizinho, verifica se ele já foi colorido. Se sim, adiciona sua cor ao conjunto.
                for (String vizinho : vizinhos) {
                    if (cores.containsKey(vizinho)) {
                        coresVizinhas.add(cores.get(vizinho));
                    }
                }
            }

            // Encontra a menor cor (horário) disponível.
            // Começa com a cor 1 e incrementa até encontrar uma que não esteja em uso pelos vizinhos.
            int cor = 1;
            while (coresVizinhas.contains(cor)) {
                cor++;
            }

            // Atribui a menor cor encontrada ao vértice atual
            cores.put(vertice, cor);
        }

        return cores;
    }
}
