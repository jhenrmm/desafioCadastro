package Project.Application;

import Project.domain.*;

import java.util.List;
import java.util.Scanner;

public class Test01 {
    public static void main(String[] args) {
        int option;
        do {
            option = Menu.mostrarMenu();
            if (option == 1) {
                Menu.cadastrarPet();
            } else if (option == 2) {
                // Opção 2 ainda não implementada
            } else if (option == 3) {
                // Vamos solicitar ao usuário o nome do pet para deletar
                Scanner scanner = new Scanner(System.in);
                System.out.println("Digite o nome completo do pet para deletar:");

                // Receber o nome do pet e normalizar a entrada (remover espaços extras, normalizar letras)
                String nomePet = scanner.nextLine().trim();

                if (nomePet.isEmpty()) {
                    System.out.println("Nome inválido.");
                    break;
                }

                // Encontrar o pet pela lista de pets cadastrados
                List<Pet> pets = Pet.lerPetsDaPasta("C:\\Users\\João Henrique\\IdeaProjects\\desafioCadastro\\src\\Project\\petsCadastrados");

                // Procurando o pet com o nome correspondente
                Pet petParaDeletar = null;
                for (Pet pet : pets) {
                    if (pet.getNomeCompleto().equalsIgnoreCase(nomePet)) {
                        petParaDeletar = pet;
                        break;
                    }
                }

                if (petParaDeletar != null) {
                    // Chama o método deletarPet() para remover o pet
                    boolean sucesso = petParaDeletar.deletarPet();
                    if (sucesso) {
                        System.out.println("Pet deletado com sucesso!");
                    } else {
                        System.out.println("Falha ao deletar o pet.");
                    }
                } else {
                    System.out.println("Pet não encontrado.");
                }
            } else if (option == 4) {
                // Listar todos os pets formatados
                Menu.listarTodosPetsFormatado("C:\\Users\\João Henrique\\IdeaProjects\\desafioCadastro\\src\\Project\\petsCadastrados");
            } else if (option == 5) {
                // Buscar pet
                Menu.buscarPet();
            }
        } while (option != 6);
        System.out.println("Encerrando Sistema...");
    }
}
