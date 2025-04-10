package Project.Application;
import Project.domain.*;

public class Test01 {
    public static void main(String[] args) {
        int option;
        do {
            option = Menu.mostrarMenu();
            if (option == 1) {
                Menu.cadastrarPet();
            } else if (option == 2) {
                Menu.buscarPet();
            } else if (option == 3) {

            } else if (option == 4) {

            } else if (option == 5) {

            }
        } while (option != 6);
        System.out.println("Encerrando Sistema...");
    }
}
