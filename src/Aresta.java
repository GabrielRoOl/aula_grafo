import java.util.Objects;

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
