package Project.domain.Excecoes;

public class PesoInvalido extends IllegalArgumentException {
    public PesoInvalido(String message) {
        super(message);
    }
}
