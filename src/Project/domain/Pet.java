package Project.domain;

import Project.domain.Enum.SexoPet;
import Project.domain.Enum.TipoPet;
import Project.domain.Excecoes.NomeInvalidoException;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pet {
    private String nome;
    private String sobrenome;
    private TipoPet tipo;
    private SexoPet sexo;
    private String bairro;
    private int idade;
    private double peso;
    private String raca;

    public void setTipo(TipoPet tipo) {
        this.tipo = tipo;
    }

    public void setSexo(SexoPet sexo) {
        this.sexo = sexo;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public TipoPet getTipo() {
        return tipo;
    }

    public SexoPet getSexo() {
        return sexo;
    }

    public String getBairro() {
        return bairro;
    }

    public int getIdade() {
        return idade;
    }

    public double getPeso() {
        return peso;
    }

    public String getRaca() {
        return raca;
    }

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

    public Pet(String nome, String sobrenome, TipoPet tipo, SexoPet sexo, String bairro, int idade, double peso, String raca) throws NomeInvalidoException, IOException {
        if (sobrenome == null || sobrenome.isEmpty()){
            throw new NomeInvalidoException("O animal deve ter um Sobrenome.");
        }

        this.nome = nome;
        this.sobrenome = sobrenome;
        this.tipo = tipo;
        this.sexo = sexo;
        this.bairro = bairro;
        this.idade = idade;
        this.peso = peso;
        this.raca = raca;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm");
        String dataHoraFormatada = now.format(formatter);
        String nomeArquivo = dataHoraFormatada+"-"+nome.toUpperCase().concat(sobrenome.toUpperCase().replaceAll(" ",""))+".txt";
        File filePet = new File("C:\\Users\\João Henrique\\IdeaProjects\\desafioCadastro\\src\\Project\\petsCadastrados\\"+ nomeArquivo);
        FileWriter fw = new FileWriter(filePet, true);
        try (BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write("1 - " + nome + sobrenome);
            bw.newLine();
            bw.write("2 - " + tipo);
            bw.newLine();
            bw.write("3 - " + sexo);
            bw.newLine();
            bw.write("4 - Rua " + bairro);
            bw.newLine();
            bw.write("5 - " + idade + " anos");
            bw.newLine();
            bw.write("6 - " + peso + "kg");
            bw.newLine();
            bw.write("7 - " + raca);
            bw.newLine();
        } catch (IOException e){
            System.out.println("Erro ao escrever Arquivo");
        }
    }
}
