import collector.PetCollector;
import repository.PetRepository;
import service.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        PetRepository petRepository = new PetRepository();

        PetCollector petCollector = new PetCollector(scanner);

        PetService petService = new PetService(petCollector, petRepository, scanner);

        PrintMenu mainMenu = new PrintMenu();
        mainMenu.setPetService(petService);

        mainMenu.printInitialMenu();


    }
}
