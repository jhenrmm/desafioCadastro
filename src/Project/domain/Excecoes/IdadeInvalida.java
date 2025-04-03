package Project.domain.Excecoes;

public class IdadeInvalida extends RuntimeException {
    public IdadeInvalida(String message) {
        super(message);
    }
}
