package Project.Application;
import Project.domain.NomeInvalidoException;
import Project.domain.Pet;
import Project.domain.SexoPet;
import Project.domain.TipoPet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Test01 {
    public static void main(String[] args) throws NomeInvalidoException {
        Scanner scanner = new Scanner(System.in);
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
            if (scanner.hasNextInt()){
                option = scanner.nextInt();
                if (option >=1 && option <=6){
                    break;
                } else {
                    System.out.println("Opção Inválida! Por favor tente novamente.");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        if (option == 1){
            String nome;
            String sobrenome;
            TipoPet tipo;
            SexoPet sexo;
            String bairro;
            int idade;
            double peso;
            String raca;
            try (FileReader fr = new FileReader("C:\\Users\\João Henrique\\IdeaProjects\\desafioCadastro\\src\\Project\\Data_Acess\\formulario.txt");
                 BufferedReader br = new BufferedReader(fr);
                 Scanner scanner2 = new Scanner(System.in)) {
                String linha;
                int contador = 1;
                while ((linha = br.readLine()) != null) {
                    System.out.println(linha);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (option == 2){

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
