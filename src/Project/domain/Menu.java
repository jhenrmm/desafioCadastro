package Project.domain;

import Project.domain.Constante.Indefinido;
import Project.domain.Enum.SexoPet;
import Project.domain.Enum.TipoPet;
import Project.domain.Excecoes.IdadeInvalida;
import Project.domain.Excecoes.NomeInvalidoException;
import Project.domain.Excecoes.PesoInvalido;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    public static int mostrarMenu(){
        Scanner scanner = new Scanner(System.in);
        int option = -1;
            System.out.println("=================================");
            System.out.println("1. Cadastrar um novo pet");
            System.out.println("2. Alterar os dados do pet cadastrado");
            System.out.println("3. Deletar um pet cadastrado");
            System.out.println("4. Listar todos os pets cadastrados");
            System.out.println("5. Listar pets por algum critério(idade, nome, raça)");
            System.out.println("6. Sair");
            System.out.println("=================================");
            System.out.print("Digite a Opção: ");
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                scanner.nextLine();
                if (option < 1 || option > 6) {
                    System.out.println("Número Inválido. Digite um número de 1 a 6");
                }
            } else {
                System.out.println("Entrada Inválida! Por favor digite apenas números de 1 a 6");
                scanner.nextLine();
            }

        return option;
    }
    public static Pet cadastrarPet(){
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        String respostaNome = "";
        String respostaSobrenome = "";
        TipoPet respostaTipo = null;
        SexoPet respostaSexo = null;
        String respostaBairro = "";
        int respostaIdade = 0;
        double respostaPeso = 0.0;
        String respostaRaca = "";
        try (FileReader fr = new FileReader("C:\\Users\\João Henrique\\IdeaProjects\\desafioCadastro\\src\\Project\\Data_Acess\\formulario.txt");
             BufferedReader br = new BufferedReader(fr)) {
            String linha;
            int contador = 1;
            while ((linha = br.readLine()) != null) {
                switch (contador) {
                    case 1: {
                        System.out.println(linha);
                        respostaNome = scanner.nextLine();
                        if (respostaNome.isEmpty()) {
                            respostaNome = Indefinido.NOME;
                        }
                        String[] partes = respostaNome.split(" ", 2);
                        respostaNome = partes[0];
                        respostaSobrenome = (partes.length > 1) ? partes[1] : Indefinido.SOBRENOME;
                        break;
                    }
                    case 2: {
                        while (true) {
                            try {
                                System.out.println(linha);
                                respostaTipo = TipoPet.valueOf(scanner.next().toUpperCase());
                                break;
                            } catch (IllegalArgumentException e) {
                                System.err.println("Opção Inválida");
                                scanner.nextLine();
                            }
                        }
                        break;
                    }
                    case 3: {
                        while (true) {
                            try {
                                System.out.println(linha);
                                respostaSexo = SexoPet.valueOf(scanner.next().toUpperCase());
                                break;
                            } catch (IllegalArgumentException e) {
                                System.err.println("Opção Inválida");
                                scanner.nextLine();
                            }
                        }
                        break;
                    }
                    case 4: {
                        System.out.println(linha);
                        System.out.print("Número da Casa: ");
                        Integer numeroCasa = scanner.nextInt();
                        scanner.nextLine();
                        if (numeroCasa == null) {
                            numeroCasa = Indefinido.NUMERO_ENDERECO;
                        }
                        System.out.print("Cidade: ");
                        String cidadePet = scanner.nextLine();
                        System.out.print("Rua: ");
                        String ruaPet = scanner.nextLine();
                        respostaBairro = String.valueOf(sb.append(ruaPet).append(", ").append(numeroCasa).append(", ").append(cidadePet));
                        break;
                    }
                    case 5: {
                        System.out.println(linha);
                        int idadeInput = scanner.nextInt();
                        scanner.nextLine();
                        if (idadeInput > 20) {
                            throw new IdadeInvalida("Idade Inválida!");
                        }
                        if (idadeInput < 1) {
                            respostaIdade = idadeInput / 12;
                        } else {
                            respostaIdade = idadeInput;
                        }
                        break;
                    }
                    case 6: {
                        System.out.println(linha);
                        String pesoInput = scanner.nextLine().replace(",", ".");
                        if (pesoInput.isEmpty()) {
                            respostaPeso = Indefinido.PESO;
                        }
                        try {
                            respostaPeso = Double.parseDouble(pesoInput);
                            if (respostaPeso > 60 || respostaPeso < 0.5) {
                                throw new PesoInvalido("Peso Inválido!");
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Peso inválido! Tente novamente.");
                            contador--;
                        }
                        break;
                    }
                    case 7: {
                        System.out.println(linha);
                        respostaRaca = scanner.next();
                        if (respostaRaca.isEmpty()) {
                            respostaRaca = Indefinido.RACA;
                        }
                        if (!respostaRaca.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+$")) {
                            System.out.println("Caracteres Inválidos! Tente Novamente");
                            contador--;
                        }
                        break;
                    }
                }
                contador++;
            }
            Pet novoPet = new Pet(respostaNome, respostaSobrenome, respostaTipo, respostaSexo, respostaBairro, respostaIdade, respostaPeso, respostaRaca);
            novoPet.salvarEmArquivo("C:\\Users\\João Henrique\\IdeaProjects\\desafioCadastro\\src\\Project\\petsCadastrados");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NomeInvalidoException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static void buscarPet() {
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

        scanner.nextLine();

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
            scanner.nextLine();
        }

        // Valores dos critérios
        String valor1 = "";
        String valor2 = "";

        if (criterio1 == 1 || criterio2 == 1) {
            System.out.println("Digite o nome ou sobrenome: ");
            if (criterio1 == 1) valor1 = scanner.nextLine();
            else valor2 = scanner.nextLine();
        }

        if (criterio1 == 2 || criterio2 == 2) {
            System.out.println("Digite o sexo:");
            if (criterio1 == 2) valor1 = scanner.nextLine();
            else valor2 = scanner.nextLine();
        }

        if (criterio1 == 3 || criterio2 == 3) {
            System.out.println("Digite a idade:");
            if (criterio1 == 3) valor1 = scanner.nextLine();
            else valor2 = scanner.nextLine();
        }

        if (criterio1 == 4 || criterio2 == 4) {
            System.out.println("Digite o peso:");
            if (criterio1 == 4) valor1 = scanner.nextLine();
            else valor2 = scanner.nextLine();
        }

        if (criterio1 == 5 || criterio2 == 5) {
            System.out.println("Digite a raça:");
            if (criterio1 == 5) valor1 = scanner.nextLine();
            else valor2 = scanner.nextLine();
        }

        if (criterio1 == 6 || criterio2 == 6) {
            System.out.println("Digite o endereço:");
            if (criterio1 == 6) valor1 = scanner.nextLine();
            else valor2 = scanner.nextLine();
        }

        List<Pet> pets = Pet.lerPetsDaPasta("C:\\Users\\João Henrique\\IdeaProjects\\desafioCadastro\\src\\Project\\petsCadastrados");

        List<Pet> resultados = Pet.buscarPets(pets, tipoAnimal, criterio1, valor1, criterio2, valor2);

        if (resultados.isEmpty()) {
            System.out.println("Nenhum pet encontrado com os critérios informados.");
        } else {
            for (int i = 0; i < resultados.size(); i++) {
                System.out.println(resultados.get(i).formatarParaLista(i + 1));
            }
        }

    }
    public static void listarTodosPetsFormatado(String caminhoPasta) {
        // Lê todos os pets da pasta
        List<Pet> pets = Pet.lerPetsDaPasta(caminhoPasta);

        // Verifica se não encontrou nenhum pet
        if (pets.isEmpty()) {
            System.out.println("Nenhum pet cadastrado.");
        } else {
            // Exibe os pets no formato solicitado
            for (int i = 0; i < pets.size(); i++) {
                System.out.println(pets.get(i).formatarParaLista(i + 1)); // Formata e imprime
            }
        }
    }


}
