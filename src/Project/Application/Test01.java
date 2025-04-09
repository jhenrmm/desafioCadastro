package Project.Application;
import Project.domain.*;
import Project.domain.Constante.Indefinido;
import Project.domain.Enum.SexoPet;
import Project.domain.Enum.TipoPet;
import Project.domain.Excecoes.IdadeInvalida;
import Project.domain.Excecoes.NomeInvalidoException;
import Project.domain.Excecoes.PesoInvalido;
import jdk.jshell.execution.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test01 {
    public static void main(String[] args) throws NomeInvalidoException {
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        int option;
        while (true) {
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
                if (option >= 1 && option <= 6) {
                    break;
                } else {
                    System.out.println("Opção Inválida! Por favor tente novamente.");
                }
            } else {
                System.out.println("Entrada Inválida! Por favor digite apenas números de 1 a 6");
                scanner.nextLine();
            }
        }
        if (option == 1) {
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
                Pet pet = new Pet(respostaNome, respostaSobrenome, respostaTipo, respostaSexo, respostaBairro, respostaIdade, respostaPeso, respostaRaca);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (option == 2) {
            List<Pet> pets = Pet.lerPetsDoArquivo("src/Project/petsCadastrados");

            System.out.println("Digite o tipo do pet:");
            for (TipoPet tipo : TipoPet.values()) {
                System.out.println("- " + tipo.name());
            }

            TipoPet tipoSelecionado;
            while (true) {
                try {
                    tipoSelecionado = TipoPet.valueOf(scanner.nextLine().toUpperCase());
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Tipo inválido. Tente novamente:");
                }
            }

            List<Pet> petsDoTipo = new ArrayList<>();
            for (Pet p : pets) {
                if (p.getTipo().equals(tipoSelecionado)) {
                    petsDoTipo.add(p);
                }
            }

            if (petsDoTipo.isEmpty()) {
                System.out.println("Nenhum pet do tipo selecionado foi encontrado.");
                return;
            }

            System.out.println("Pets encontrados:");
            for (int i = 0; i < petsDoTipo.size(); i++) {
                System.out.println("[" + i + "] " + petsDoTipo.get(i).resumo());
            }

            System.out.print("Digite o índice do pet que deseja alterar: ");
            int indice = scanner.nextInt();
            scanner.nextLine();
            if (indice < 0 || indice >= petsDoTipo.size()) {
                System.out.println("Índice inválido.");
                return;
            }

            Pet petSelecionado = petsDoTipo.get(indice);

            System.out.println("Qual campo deseja alterar?");
            System.out.println("1. Nome");
            System.out.println("2. Sexo");
            System.out.println("3. Idade");
            System.out.println("4. Peso");
            System.out.println("5. Raça");
            System.out.println("6. Endereço");

            int campo = scanner.nextInt();
            scanner.nextLine();

            switch (campo) {
                case 1:
                    System.out.print("Novo nome: ");
                    String nome = scanner.nextLine();
                    petSelecionado.setNome(nome);
                    break;
                case 2:
                    System.out.print("Novo sexo (MACHO/FEMEA): ");
                    SexoPet sexo = SexoPet.valueOf(scanner.nextLine().toUpperCase());
                    petSelecionado.setSexo(sexo);
                    break;
                case 3:
                    System.out.print("Nova idade: ");
                    int idade = scanner.nextInt();
                    scanner.nextLine();
                    petSelecionado.setIdade(idade);
                    break;
                case 4:
                    System.out.print("Novo peso: ");
                    double peso = scanner.nextDouble();
                    scanner.nextLine();
                    petSelecionado.setPeso(peso);
                    break;
                case 5:
                    System.out.print("Nova raça: ");
                    String raca = scanner.nextLine();
                    petSelecionado.setRaca(raca);
                    break;
                case 6:
                    System.out.print("Novo endereço: ");
                    String endereco = scanner.nextLine();
                    petSelecionado.setBairro(endereco);
                    break;
                default:
                    System.out.println("Campo inválido.");
                    return;
            }

            int indexOriginal = pets.indexOf(petsDoTipo.get(indice));
            pets.set(indexOriginal, petSelecionado);

            Pet.salvarPetsNoArquivo("src/Project/petsCadastrados", pets);

            System.out.println("Pet atualizado com sucesso!");
        }

        // Opções 3 a 6 ainda não implementadas...

        scanner.close();

    if(option ==3) {

    }
        if(option ==4) {

    }
        if(option ==5) {

    }
        if(option ==6) {

    }
        scanner.close();
    }
}
