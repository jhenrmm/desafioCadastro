package Project.domain;

import Project.domain.Enum.SexoPet;
import Project.domain.Enum.TipoPet;
import Project.domain.Excecoes.NomeInvalidoException;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    public static List<Pet> lerPetsDoArquivo(String pastaPath) {
        List<Pet> pets = new ArrayList<>();
        File pasta = new File(pastaPath);

        if (!pasta.exists() || !pasta.isDirectory()) {
            System.out.println("Pasta não encontrada ou inválida.");
            return pets;
        }

        File[] arquivos = pasta.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum arquivo .txt encontrado na pasta.");
            return pets;
        }

        for (File arquivo : arquivos) {
            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                String nome = br.readLine();
                String sobrenome = br.readLine();
                TipoPet tipo = TipoPet.valueOf(br.readLine().toUpperCase());
                SexoPet sexo = SexoPet.valueOf(br.readLine().toUpperCase());
                String bairro = br.readLine();
                int idade = Integer.parseInt(br.readLine());
                double peso = Double.parseDouble(br.readLine());
                String raca = br.readLine();

                Pet pet = new Pet(nome, sobrenome, tipo, sexo, bairro, idade, peso, raca);
                pets.add(pet);

            } catch (Exception e) {
                System.out.println("Erro ao ler arquivo " + arquivo.getName() + ": " + e.getMessage());
            }
        }

        return pets;
    }

    public String resumo() {
        return String.format("%s %s (%s, %d anos, %.2fkg) - Raça: %s - Endereço: %s",
                this.nome, this.sobrenome, this.sexo, this.idade, this.peso, this.raca, this.bairro);
    }

    public static void salvarPetsNoArquivo(String caminho, List<Pet> pets) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            for (Pet p : pets) {
                String linha = String.join(";",
                        p.getNome(),
                        p.getSobrenome(),
                        p.getTipo().name(),
                        p.getSexo().name(),
                        p.getBairro(),
                        String.valueOf(p.getIdade()),
                        String.valueOf(p.getPeso()),
                        p.getRaca()
                );
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
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
