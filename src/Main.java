import java.util.Collections;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        System.out.println("\n============--------------+++++++++--------------==============");


        Grafo grafoEsparso = new GrafoEsparso();
        String[] aulas = {"M", "A", "C", "F", "Q", "P"};
        for (String aula : aulas) {
            grafoEsparso.adicionarVertice(aula);
        }

        grafoEsparso.adicionarAresta("C", "F");
        grafoEsparso.adicionarAresta("C", "A");
        grafoEsparso.adicionarAresta("F", "A");
        grafoEsparso.adicionarAresta("M", "P");
        grafoEsparso.adicionarAresta("M", "A");
        grafoEsparso.adicionarAresta("P", "A");
        grafoEsparso.adicionarAresta("Q", "F");
        grafoEsparso.adicionarAresta("Q", "A");
        grafoEsparso.adicionarAresta("C", "P");
        grafoEsparso.adicionarAresta("M", "Q");
        
        grafoEsparso.imprimir();

        System.out.println("\n============--------------+++++++++--------------==============");


        Map<String, Integer> coresAtribuidas = grafoEsparso.colorirGrafo();
        
        // Calcula o número cromático (número mínimo de cores/horários)
        int numeroMinimoHorarios = 0;
        if (!coresAtribuidas.isEmpty()) {
            numeroMinimoHorarios = Collections.max(coresAtribuidas.values());
        }

        System.out.println("Número mínimo de horários necessários (Número Cromático): " + numeroMinimoHorarios);
        System.out.println("Distribuição das aulas por horário:");
        
        // Imprime o resultado formatado
        for (Map.Entry<String, Integer> entry : coresAtribuidas.entrySet()) {
            System.out.println(" - Aula " + entry.getKey() + " -> Horário " + entry.getValue());
        }

    }
}


