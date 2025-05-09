import collector.PetCollector;
import repository.PetRepository;
import service.ListAllPets;
import service.ListSpecificPet;
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

        ReadFile readFile = new ReadFile("");

        ListAllPets listAllPets = new ListAllPets(readFile);

        ListSpecificPet listSpecificPet = new ListSpecificPet(petCollector, readFile, scanner);

        PrintMenu mainMenu = new PrintMenu();
        mainMenu.setRegisterService(registerService);
        mainMenu.setListAllPetsService(listAllPets);
        mainMenu.setListSpecificPet(listSpecificPet);

        mainMenu.printInitialMenu();

    }
}
