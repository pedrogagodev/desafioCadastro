import collector.PetCollector;
import repository.FileRepository;
import repository.PetRepository;
import service.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        PetRepository petRepository = new PetRepository();

        PetCollector petCollector = new PetCollector(scanner);

        FileRepository fileRepository = new FileRepository();

        PetService petService = new PetService(petCollector, petRepository, fileRepository, scanner);

        PrintMenu mainMenu = new PrintMenu(petCollector, fileRepository, petService);

        mainMenu.printInitialMenu();


    }
}
