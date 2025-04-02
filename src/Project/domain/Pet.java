package Project.domain;

public class Pet {
    private String nome;
    private String sobrenome;
    private TipoPet tipo;
    private SexoPet sexo;
    private String bairro;
    private int idade;
    private double peso;
    private String raca;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    private boolean isNomeValido(String nome){
        return nome != null && nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+$");
    }

    public Pet(String nome, String sobrenome, TipoPet tipo, SexoPet sexo, String bairro, int idade, double peso, String raca) throws NomeInvalidoException {
        if (sobrenome == null || sobrenome.isEmpty()){
            throw new NomeInvalidoException("O animal deve ter um Sobrenome.");
        }
        if (!isNomeValido(nome)){
            System.out.println("Nome com Números ou Carácteres Inválidos.");
        }
        if (!isNomeValido(sobrenome)){
            System.out.println("Sobrenome com Números ou Carácteres Inválidos.");
        }

        this.nome = nome;
        this.sobrenome = sobrenome;
        this.tipo = tipo;
        this.sexo = sexo;
        this.bairro = bairro;
        this.idade = idade;
        this.peso = peso;
        this.raca = raca;
    }

    public void exibirInfo(){
        System.out.println("==============================");
        System.out.println("Nome: "+nome);
        System.out.println("Sobrenome: "+sobrenome);
        System.out.println("Tipo: "+tipo);
        System.out.println("Sexo: "+sexo);
        System.out.println("Bairro: "+bairro);
        System.out.println("Idade: "+idade);
        System.out.println("Peso: "+peso);
        System.out.println("Raça: "+raca);
        System.out.println("==============================");
    }
}
