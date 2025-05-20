package service;

import collector.PetCollector;
import repository.FileRepository;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PrintMenu {

    private final PetCollector petCollector;
    private final FileRepository fileRepository;

    private final PetService petService;

    public PrintMenu(PetCollector petCollector, FileRepository fileRepository, PetService petService) {
        this.petCollector = petCollector;
        this.fileRepository = fileRepository;
        this.petService = petService;
    }

    public void printInitialMenu() {
        Scanner scanner = new Scanner(System.in);
        int userChoice;

        do {
            System.out.println("Welcome to Pet Register CLI, please choose a, option: ");
            System.out.println("\n1. Register a new pet");
            System.out.println("2. Change the data of a registered pet");
            System.out.println("3. Delete a registered pet");
            System.out.println("4. List all registered pets");
            System.out.println("5. List pets by any criteria (age, name, breed)");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try {
                userChoice = scanner.nextInt();
                scanner.nextLine();

                if (userChoice < 1 || userChoice > 6) {
                    System.out.println("Please, enter a number between 1 and 6.");
                    continue;
                }

                switch (userChoice) {
                    case 1:
                        petService.registerPet();
                        break;
                    case 2:
                        petService.updatePet();
                        break;
                    case 3:
                        petService.deletePet();
                        break;
                    case 4:
                        petService.listAllPets();
                        break;
                    case 5:
                        petService.listPetsByCriteria(petCollector,fileRepository);
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        break;
                    default:
                        throw new RuntimeException("Please, provide a valid value.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid value! Type numbers only.");
                scanner.nextLine();
                userChoice = 0;
            }
        } while (userChoice != 6);

    }
}
