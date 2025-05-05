import collector.PetCollector;
import repository.PetRepository;
import service.PrintMenu;
import service.RegisterPet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        PetRepository petRepository = new PetRepository();

        PetCollector petCollector = new PetCollector(scanner);

        RegisterPet registerService = new RegisterPet(petCollector, petRepository);

        PrintMenu mainMenu = new PrintMenu();
        mainMenu.setRegisterService(registerService);

        mainMenu.printInitialMenu();

    }
}
