import collector.PetCollector;
import repository.PetRepository;
import service.ListAllPets;
import service.PrintMenu;
import service.RegisterPet;
import util.ReadFile;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        PetRepository petRepository = new PetRepository();

        PetCollector petCollector = new PetCollector(scanner);

        RegisterPet registerService = new RegisterPet(petCollector, petRepository);

        ReadFile readFile = new ReadFile("resources/registered-pets/20250506T16:11-NOTINFORMED.txt");

        ListAllPets listAllPets = new ListAllPets(readFile);

        PrintMenu mainMenu = new PrintMenu();
        mainMenu.setRegisterService(registerService);
        mainMenu.setListAllPetsService(listAllPets);

        mainMenu.printInitialMenu();

    }
}
