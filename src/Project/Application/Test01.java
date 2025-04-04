package Project.Application;
import Project.domain.*;
import Project.domain.Constante.Indefinido;
import Project.domain.Enum.SexoPet;
import Project.domain.Enum.TipoPet;
import Project.domain.Excecoes.IdadeInvalida;
import Project.domain.Excecoes.NomeInvalidoException;
import Project.domain.Excecoes.PesoInvalido;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Test01 {
    public static void main(String[] args) throws NomeInvalidoException {
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        int option;
        while (true){
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
        if (option == 1){
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
                    switch(contador) {
                        case 1:{
                            System.out.println(linha);
                            respostaNome = scanner.nextLine();
                            if (respostaNome.isEmpty()){
                                respostaNome = Indefinido.NOME;
                            }
                            String[] partes = respostaNome.split(" ", 2);
                            respostaNome = partes[0];
                            respostaSobrenome = (partes.length > 1) ? partes[1] : Indefinido.SOBRENOME;
                            break;
                        } case 2: {
                            while (true){
                                try {
                                    System.out.println(linha);
                                    respostaTipo = TipoPet.valueOf(scanner.next().toUpperCase());
                                    break;
                                } catch (IllegalArgumentException e){
                                    System.err.println("Opção Inválida");
                                    scanner.nextLine();
                                }
                            }
                            break;
                        } case 3: {
                            while (true){
                                try {
                                    System.out.println(linha);
                                    respostaSexo = SexoPet.valueOf(scanner.next().toUpperCase());
                                    break;
                                } catch (IllegalArgumentException e){
                                    System.err.println("Opção Inválida");
                                    scanner.nextLine();
                                }
                            }
                            break;
                        } case 4: {
                            System.out.println(linha);
                            System.out.print("Número da Casa: ");
                            Integer numeroCasa = scanner.nextInt();
                            scanner.nextLine();
                            if (numeroCasa == null){
                                numeroCasa = Indefinido.NUMERO_ENDERECO;
                            }
                            System.out.print("Cidade: ");
                            String cidadePet = scanner.nextLine();
                            System.out.print("Rua: ");
                            String ruaPet = scanner.nextLine();
                            respostaBairro = String.valueOf(sb.append(ruaPet).append(", ").append(numeroCasa).append(", ").append(cidadePet));
                            break;
                        } case 5: {
                            System.out.println(linha);
                            int idadeInput = scanner.nextInt();
                            scanner.nextLine();
                            if (idadeInput > 20){
                                throw new IdadeInvalida("Idade Inválida!");
                            }
                            if (idadeInput < 1){
                                respostaIdade = idadeInput * 12;
                            } else {
                                respostaIdade = idadeInput;
                            }
                            break;
                        } case 6: {
                            System.out.println(linha);
                            String pesoInput = scanner.nextLine().replace(",", ".");
                            if (pesoInput.isEmpty()) {
                                respostaPeso = Indefinido.PESO;
                            }
                            try {
                                respostaPeso = Double.parseDouble(pesoInput);
                                if (respostaPeso > 60 || respostaPeso < 0.5){
                                    throw new PesoInvalido("Peso Inválido!");
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("Peso inválido! Tente novamente.");
                                contador--;
                            }
                            break;
                        } case 7: {
                            System.out.println(linha);
                            respostaRaca = scanner.next();
                            if (respostaRaca.isEmpty()){
                                respostaRaca = Indefinido.RACA;
                            }
                            if (!respostaRaca.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+$")){
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
        if (option == 2){
            System.out.println("Selecione o Tipo do Animal: ");
            for (TipoPet tipo : TipoPet.values()){
                System.out.println("- "+ tipo.name());
            }
            TipoPet tipoSelecionado;
            while(true){
                try {
                    tipoSelecionado = TipoPet.valueOf(scanner.nextLine().toUpperCase());
                    break;
                } catch (IllegalArgumentException e){
                    System.out.println("Tipo Inválido. Tente novamente!");
                }
            }
            System.out.println("Escolha os critérios para busca:");
            System.out.println("1. Nome");
            System.out.println("2. Sobrenome");
            System.out.println("3. Sexo");
            System.out.println("4. Idade");
            System.out.println("5. Peso");
            System.out.println("6. Raça");
            System.out.println("7. Endereço");

            System.out.println("Digite o número do Primeiro Critério: ");
            int opt1 = scanner.nextInt();
            System.out.println("Deseja informar um Segundo Critério (S/N)?");
            boolean segundo = scanner.nextLine().equalsIgnoreCase("s");
            int opt2 = -1;
            if (segundo){
                System.out.println("Digite o número do Segundo Critério: ");
                opt2 = scanner.nextInt();
            }
        }
        if (option == 3){

        }
        if (option == 4){

        }
        if (option == 5){

        }
        if (option == 6){

        }
        scanner.close();
    }
}
