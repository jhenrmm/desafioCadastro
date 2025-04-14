package Project.domain;

import Project.domain.Enum.SexoPet;
import Project.domain.Enum.TipoPet;
import Project.domain.Excecoes.NomeInvalidoException;

import java.io.*;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Pet {
    private String nome;
    private String sobrenome;
    private TipoPet tipo;
    private SexoPet sexo;
    private String bairro;
    private int idade;
    private double peso;
    private String raca;

    @Override
    public String toString() {
        return "Pet{" +
                "tipo=" + tipo +
                ", sexo=" + sexo +
                ", bairro='" + bairro + '\'' +
                ", idade=" + idade +
                ", peso=" + peso +
                ", raca='" + raca + '\'' +
                '}';
    }

    public String getNomeCompleto() {
        return nome + " " + sobrenome;
    }

    private boolean isNomeValido(String nome){
        return nome != null && nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+$");
    }

    public Pet(String nome, String sobrenome, TipoPet tipo, SexoPet sexo, String bairro, int idade, double peso, String raca) throws NomeInvalidoException, IOException {
        if (sobrenome == null || sobrenome.isEmpty()) {
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
    }

    // ✍️ Método responsável por salvar o Pet em arquivo .txt
    public void salvarEmArquivo(String caminhoPasta) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm");
        String dataHoraFormatada = now.format(formatter);
        String baseNome = dataHoraFormatada + "-" + nome.toUpperCase().concat(sobrenome.toUpperCase().replaceAll(" ", ""));
        String nomeArquivo = baseNome + ".txt";

        File pasta = new File(caminhoPasta);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        File filePet = new File(pasta, nomeArquivo);
        int contador = 1;

        while (filePet.exists()) {
            nomeArquivo = baseNome + "_" + contador + ".txt";
            filePet = new File(pasta, nomeArquivo);
            contador++;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePet))) {
            bw.write(nome + " " + sobrenome);
            bw.newLine();
            bw.write("" + tipo);
            bw.newLine();
            bw.write("" + sexo);
            bw.newLine();
            bw.write("Rua " + bairro);
            bw.newLine();
            bw.write(idade + " anos");
            bw.newLine();
            bw.write(peso + "kg");
            bw.newLine();
            bw.write(raca);
        } catch (IOException e) {
            System.out.println("Erro ao escrever Arquivo");
            throw e;
        }
    }

    public static List<Pet> lerPetsDaPasta(String caminhoPasta) {
        List<Pet> pets = new ArrayList<>();
        File pasta = new File(caminhoPasta);

        if (!pasta.exists() || !pasta.isDirectory()) {
            System.out.println("Pasta não encontrada!");
            return pets;
        }

        File[] arquivos = pasta.listFiles((dir, name) -> name.endsWith(".txt"));
        if (arquivos == null) return pets;

        for (File arquivo : arquivos) {
            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                List<String> linhas = new ArrayList<>();
                String linha;
                while ((linha = br.readLine()) != null) {
                    linha = linha.trim();
                    if (!linha.isEmpty()) {
                        linhas.add(linha);
                    }
                }

                if (linhas.size() == 7) {
                    String nomeCompleto = linhas.get(0).trim();
                    String[] partesNome = nomeCompleto.split(" ", 2);
                    String nome = partesNome[0];
                    String sobrenome = partesNome.length > 1 ? partesNome[1] : "";

                    TipoPet tipo = TipoPet.valueOf(linhas.get(1).toUpperCase());
                    SexoPet sexo = SexoPet.valueOf(linhas.get(2).toUpperCase());
                    String bairro = linhas.get(3).replace("Rua", "").trim();
                    int idade = Integer.parseInt(linhas.get(4).replaceAll("[^0-9]", ""));
                    double peso = Double.parseDouble(linhas.get(5).replaceAll("[^0-9.]", ""));
                    String raca = linhas.get(6).trim();

                    pets.add(new Pet(nome, sobrenome, tipo, sexo, bairro, idade, peso, raca));
                } else {
                    System.out.println("Arquivo mal formatado: " + arquivo.getName());
                }

            } catch (Exception e) {
                System.out.println("Erro ao ler arquivo: " + arquivo.getName());
                e.printStackTrace();
            }
        }
        return pets;
    }

    public static String removerAcentos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }

    public TipoPet getTipo() {
        return tipo;
    }

    private String normalizar(String texto) {
        if (texto == null) return "";
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase();
    }

    public boolean corresponde(int criterio, String valor) {
        String normalizadoValor = normalizar(valor);

        switch (criterio) {
            case 1: // Nome ou Sobrenome
                return normalizar(this.nome).contains(normalizadoValor) ||
                        normalizar(this.sobrenome).contains(normalizadoValor);
            case 2: // Sexo
                return this.sexo.name().equalsIgnoreCase(valor);
            case 3: // Idade
                return Integer.toString(this.idade).equals(valor);
            case 4: // Peso
                return Double.toString(this.peso).equals(valor.replace(",", "."));
            case 5: // Raça
                return normalizar(this.raca).contains(normalizadoValor);
            case 6: // Endereço
                return normalizar(this.bairro).contains(normalizadoValor);
            default:
                return false;
        }
    }

    public static List<Pet> buscarPets(List<Pet> pets, int tipoAnimal, int crit1, String val1, int crit2, String val2) {
        return pets.stream()
                .filter(p -> p.getTipo().ordinal() + 1 == tipoAnimal) // ordinal() + 1 para bater com seu menu 1-Cachorro 2-Gato
                .filter(p -> p.corresponde(crit1, val1))
                .filter(p -> crit2 == 0 || p.corresponde(crit2, val2))
                .collect(Collectors.toList());
    }

    public String formatarParaLista(int indice) {
        return String.format("%d. %s %s - %s - %s - Rua %s - %d anos - %.1fkg - %s",
                indice,
                nome,
                sobrenome,
                tipo.name().charAt(0) + tipo.name().substring(1).toLowerCase(), // Capitaliza
                sexo.name().equalsIgnoreCase("MACHO") ? "Macho" : "Fêmea",
                bairro,
                idade,
                peso,
                raca);
    }

    // Método de instância para deletar o pet
    public boolean deletarPet() {
        try {
            String caminhoArquivo = "C:\\Users\\João Henrique\\IdeaProjects\\desafioCadastro\\src\\Project\\petsCadastrados\\" + this.nome + ".txt";
            File arquivoPet = new File(caminhoArquivo);

            if (arquivoPet.exists()) {
                return arquivoPet.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Caso não consiga excluir
    }

    private static List<Pet> buscarPetsComCritérios() {
        Scanner scanner = new Scanner(System.in);
        int tipoAnimal;
        int criterio1;
        int criterio2 = 0;

        // Menu tipo de animal
        do {
            System.out.println("Escolha uma opção: ");
            System.out.println("1 - Cachorro");
            System.out.println("2 - Gato");
            tipoAnimal = scanner.nextInt();
        } while (tipoAnimal != 1 && tipoAnimal != 2);

        // Menu do primeiro critério
        do {
            System.out.println("Quais critérios adicionais deseja usar para buscar o pet?");
            System.out.println("1 - Nome ou Sobrenome");
            System.out.println("2 - Sexo");
            System.out.println("3 - Idade");
            System.out.println("4 - Peso");
            System.out.println("5 - Raça");
            System.out.println("6 - Endereço");
            criterio1 = scanner.nextInt();
        } while (criterio1 < 1 || criterio1 > 6);

        scanner.nextLine(); // Limpa o buffer de entrada

        System.out.println("Deseja um segundo critério de busca? [S/N]");
        String respostaCriterio = scanner.nextLine().trim();
        if (respostaCriterio.equalsIgnoreCase("S")) {
            do {
                System.out.println("Quais critérios adicionais deseja usar para buscar o pet?");
                System.out.println("1 - Nome ou Sobrenome");
                System.out.println("2 - Sexo");
                System.out.println("3 - Idade");
                System.out.println("4 - Peso");
                System.out.println("5 - Raça");
                System.out.println("6 - Endereço");
                criterio2 = scanner.nextInt();
            } while (criterio2 < 1 || criterio2 > 6);
            scanner.nextLine(); // Limpa o buffer de entrada
        }

        // Aqui você pode colocar os valores de busca que o usuário forneceu (como no código anterior)
        String valor1 = obterValorDeBusca(scanner, criterio1);
        String valor2 = criterio2 != 0 ? obterValorDeBusca(scanner, criterio2) : null;

        // Obtém todos os pets cadastrados
        List<Pet> pets = Pet.lerPetsDaPasta("C:\\Users\\João Henrique\\IdeaProjects\\desafioCadastro\\src\\Project\\petsCadastrados");

        // Realiza a busca com os critérios fornecidos
        return Pet.buscarPets(pets, tipoAnimal, criterio1, valor1, criterio2, valor2);
    }

    private static String obterValorDeBusca(Scanner scanner, int criterio) {
        String valor = "";
        if (criterio == 1) {
            System.out.println("Digite o nome ou sobrenome: ");
            valor = scanner.nextLine();
        } else if (criterio == 2) {
            System.out.println("Digite o sexo: ");
            valor = scanner.nextLine();
        } else if (criterio == 3) {
            System.out.println("Digite a idade: ");
            valor = scanner.nextLine();
        } else if (criterio == 4) {
            System.out.println("Digite o peso: ");
            valor = scanner.nextLine();
        } else if (criterio == 5) {
            System.out.println("Digite a raça: ");
            valor = scanner.nextLine();
        } else if (criterio == 6) {
            System.out.println("Digite o endereço: ");
            valor = scanner.nextLine();
        }
        return valor;
    }
}
