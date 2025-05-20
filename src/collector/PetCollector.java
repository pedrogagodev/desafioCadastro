package collector;

import entity.PetAddress;
import entity.enums.PetGender;
import entity.enums.PetType;
import exception.RequiredFieldException;
import validator.PetValidator;

import java.util.Scanner;

public class PetCollector {
    private final Scanner scanner;

    public PetCollector(Scanner scanner) {
        this.scanner = scanner;
    }

    public String collectName() {
        String name;
        while (true) {
            name = scanner.nextLine().trim();

            if (name.isEmpty()) return "NOT INFORMED";

            if (PetValidator.isValidName(name)) {
                return name;
            } else {
                System.out.println("The pet's name must have at least 2 characters and cannot contain numbers or special characters, please try again.");
            }
        }
    }

    public PetType collectPetType() {
        while (true) {
            System.out.println("1 - DOG / 2 - CAT");
            int typeChoice = scanner.nextInt();
            scanner.nextLine();
            if (typeChoice == 1) {
                return PetType.DOG;
            } else if (typeChoice == 2) {
                return PetType.CAT;
            } else {
                System.out.println("Invalid option, please try again");
            }
        }
    }

    public PetGender collectPetGender() {
        while (true) {
            System.out.println("1 - MALE / 2 - FEMALE");
            int genderChoice = scanner.nextInt();
            scanner.nextLine();

            if (genderChoice == 1) {
               return PetGender.MALE;
            } else if (genderChoice == 2) {
                return PetGender.FEMALE;
            } else {
                System.out.println("Invalid option, please try again");
            }
        }
    }

    public String collectPetStreet() {
        String street;

        while (true){
            System.out.println("Enter the street: ");
            street = scanner.nextLine().trim();

            if(PetValidator.isValidStreet(street)) {
                return street;
            } else {
                System.out.println("The street must have at least 4 characters and cannot contain numbers or special characters, please try again.");
            }

        }
    }

    public Integer collectPetAddressNumber() {
        int number;

        do {
            System.out.println("Enter the number (or press ENTER to skip): ");
            String userInputNumber  = scanner.nextLine().trim();
            if (userInputNumber.isEmpty()) return null;

            number = Integer.parseInt(userInputNumber);
            if (PetValidator.isValidPetAddressNumber(number)) {
                number = Integer.parseInt(userInputNumber);
                return number;
            } else {
                System.out.println("Please, provide a valid number.");
            }

        } while (!PetValidator.isValidPetAddressNumber(number));
        return number;
    }

    public String collectPetNeighborhood() {
        String neighborhood;

        while (true) {
            System.out.println("Enter the neighborhood: ");
            neighborhood = scanner.nextLine().trim();
            if (PetValidator.isValidNeighborhood(neighborhood)) {
                return neighborhood;
            } else {
                System.out.println("The neighborhood must have at least 4 characters and cannot contain numbers or special characters, please try again.");
            }
        }
    }

    public PetAddress collectPetAddress() {
        while (true) {
            try {
                String street = collectPetStreet();
                int number = collectPetAddressNumber();
                String neighborhood = collectPetNeighborhood();
                return new PetAddress(street, number, neighborhood);
            } catch (RequiredFieldException ex) {
                System.out.println("Error: " + ex.getMessage() + " Please, try again.\n");
            }
        }
    }



    public Integer collectPetAge() {
        int age;

        do {
            String userInputAge = scanner.nextLine().trim();
            if (userInputAge.isEmpty()) return null;
            age = Integer.parseInt(userInputAge);
            if (PetValidator.isValidAge(age)) {
                return age;
            } else {
                System.out.println("The pet's age must be a value greater than 0 and less than 20, please try again.");
            }

        } while (!PetValidator.isValidAge(age));
        return age;
    }

    public Double collectPetWeight() {
        double weight;
        String userInputWeight = scanner.nextLine().trim();

        if(userInputWeight.isEmpty()) return null;

        do {
            weight = Double.parseDouble(userInputWeight);
            if (PetValidator.isValidWeight(weight)) {
                return weight;
            } else {
                System.out.println("The pet's weight must be a value greater than 0.5 and less than 60, please try again.");
            }
        } while (!PetValidator.isValidWeight(weight));

        return weight;
    }

    public String collectPetBreed() {
        String breed;
        while (true) {
            breed = scanner.nextLine().trim();

            if (breed.isEmpty()) return "NOT INFORMED";

            if (PetValidator.isValidBreed(breed)) {
                return breed;
            } else {
                System.out.println("The pet breed must have at least 4 characters and cannot contain numbers or special characters, please try again.");
            }
        }
    }
}
