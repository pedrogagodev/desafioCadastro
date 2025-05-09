package service;

import collector.PetCollector;
import util.ReadFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ListSpecificPet {
    private final PetCollector petCollector;
    private ReadFile petTxt;
    private final Scanner scanner;


    public ListSpecificPet(PetCollector petCollector, ReadFile petTxt, Scanner scanner) {
        this.petCollector = petCollector;
        this.petTxt = petTxt;
        this.scanner = scanner;
    }

    public List<Path> searchPetsByName(String name) {
        Path searchDir = Paths.get("resources/registered-pets");
        try(Stream<Path> stream = Files.find(
                searchDir,
                Integer.MAX_VALUE,
                ((path, basicFileAttributes) -> {
                    if(!basicFileAttributes.isRegularFile()) return false;
                    String fileName = path.getFileName().toString();
                    return fileName.contains(name.replace(" ", "").trim().toUpperCase());
                }
        ))) {
            return stream.toList();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public List<Path> searchAllPets() {
        Path searchDir = Paths.get("resources/registered-pets");

        try(Stream<Path> stream = Files.list(searchDir)) {
            return stream.toList();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void listPetsByCriteria() {
        // TODO: simplify this method!!!! URGENT!!!!!

        List<Path> pets;
        int petNumber;
        boolean itFound = false;

        String petName;
        String petGender;
        String petAge;
        String petWeight;
        String petBreed;
        String petStreet;
        String petAddressNumber;
        String petNeighborhood;
        int userChoicePetGender;

        Pattern genderPattern;
        Pattern agePattern;
        Pattern weightPattern;
        Pattern numberPattern;
        Pattern streetPattern;
        Pattern neighborhoodPattern;
        Pattern breedPattern;



        do {
            System.out.println("Please, select the type of pet you want to search for.");
            int userChoicePetType = petCollector.collectPetType();
            String petType = userChoicePetType == 1 ? "DOG" : "CAT";


            System.out.println("\nSearch for:");
            System.out.println("1 - Name");
            System.out.println("2 - Gender");
            System.out.println("3 - Age");
            System.out.println("4 - Weight");
            System.out.println("5 - Breed");
            System.out.println("6 - Address");
            System.out.println("7 - Name and Age");
            System.out.println("8 - Age and Weight");
            System.out.println("9 - Name and Weight");
            System.out.println("10 - Breed and Weight");
            System.out.println("11 - Breed and Gender");

            int userChoice;
            try {
                userChoice = scanner.nextInt();
                scanner.nextLine();

                if (userChoice < 1 || userChoice > 11) {
                    System.out.println("Please, enter a number between 1 and 11.");
                    continue;
                }


                switch (userChoice) {
                    case 1:
                        System.out.println("Enter the name: ");
                        petName = petCollector.collectName();
                        pets = searchPetsByName(petName);
                        petNumber = 1;

                        System.out.println("=========== Results ===========");
                        if (pets.isEmpty()) {
                            System.out.println("No pet was found with this/these criteria(s).");
                        }
                        for (Path s : pets) {
                            petTxt = new ReadFile(s.toString());
                            String petInfo = petTxt.getFormattedData(s.toString());
                            if (petInfo.contains(petType)) {
                                System.out.println(petNumber + " - " + petInfo);
                                petNumber++;
                            }
                        }

                        break;
                    case 2:
                        userChoicePetGender = petCollector.collectPetGender();
                        petGender = userChoicePetGender == 1 ? "MALE" : "FEMALE";
                        pets = searchAllPets();

                        petNumber = 1;

                        genderPattern = Pattern.compile("\\b" + petGender + "\\b", Pattern.CASE_INSENSITIVE);

                        System.out.println("=========== Results ===========");

                        for (Path s : pets) {
                            petTxt = new ReadFile(s.toString());
                            String petInfo = petTxt.getFormattedData(s.toString());

                            Matcher matcher = genderPattern.matcher(petInfo);

                            if (petInfo.contains(petType) && matcher.find()) {
                                System.out.println(petNumber + " - " + petInfo);
                                petNumber++;
                                itFound = true;
                            }
                        }

                        if (!itFound) {
                            System.out.println("No pet was found with this/these criteria(s).");
                        }

                        break;
                    case 3:
                        System.out.println("Enter the age: ");
                        petAge = (petCollector.collectPetAge()).toString();
                        pets = searchAllPets();

                        petNumber = 1;

                        agePattern = Pattern.compile("\\b" + petAge + "\\b", Pattern.CASE_INSENSITIVE);

                        System.out.println("=========== Results ===========");

                        if (pets.isEmpty()) {
                            System.out.println("No pet was found with this/these criteria(s).");
                        }

                        for (Path s : pets) {
                            petTxt = new ReadFile(s.toString());
                            String petInfo = petTxt.getFormattedData(s.toString());

                            Matcher ageMatcher = agePattern.matcher(petInfo);

                            if (petInfo.contains(petType) && ageMatcher.find()) {
                                System.out.println(petNumber + " - " + petInfo);
                                petNumber++;
                                itFound = true;
                            }
                        }

                        if (!itFound) {
                            System.out.println("No pet was found with this/these criteria(s).");
                        }

                        break;
                    case 4:
                        System.out.println("Enter the weight: ");
                        petWeight = ((petCollector.collectPetWeight()).toString() + "kg");
                        pets = searchAllPets();
                        System.out.println(petWeight);
                        petNumber = 1;

                        weightPattern = Pattern.compile("\\b" + petWeight + "\\b", Pattern.CASE_INSENSITIVE);

                        System.out.println("=========== Results ===========");

                        if (pets.isEmpty()) {
                            System.out.println("No pet was found with this/these criteria(s).");
                        }

                        for (Path s : pets) {
                            petTxt = new ReadFile(s.toString());
                            String petInfo = petTxt.getFormattedData(s.toString());

                            Matcher weightMatcher = weightPattern.matcher(petInfo);

                            if (petInfo.contains(petType) && weightMatcher.find()) {
                                System.out.println(petNumber + " - " + petInfo);
                                petNumber++;
                                itFound = true;
                            }
                        }

                        if (!itFound) {
                            System.out.println("No pet was found with this/these criteria(s).");
                        }

                        break;
                    case 5:
                        System.out.println("Enter the breed: ");
                        petBreed = petCollector.collectPetBreed();
                        pets = searchAllPets();

                        petNumber = 1;

                        breedPattern = Pattern.compile("\\b" + petBreed + "\\b", Pattern.CASE_INSENSITIVE);


                        System.out.println("=========== Results ===========");

                        for (Path s : pets) {
                            petTxt = new ReadFile(s.toString());
                            String petInfo = petTxt.getFormattedData(s.toString());

                            Matcher breedMatcher = breedPattern.matcher(petInfo);


                            if (petInfo.contains(petType) && breedMatcher.find()) {
                                System.out.println(petNumber + " - " + petInfo);
                                petNumber++;
                                itFound = true;
                            }
                        }

                        if (!itFound) {
                            System.out.println("No pet was found with this/these criteria(s).");
                        }

                        break;
                    case 6:
                        petStreet = petCollector.collectPetStreet();
                        petAddressNumber = (petCollector.collectPetAddressNumber()).toString();
                        if (petAddressNumber.equals("-1")) {
                            petAddressNumber = "NOT INFORMED";
                        }
                        petNeighborhood = petCollector.collectPetNeighborhood();

                        pets = searchAllPets();

                        petNumber = 1;

                        streetPattern = Pattern.compile("\\b" + petStreet + "\\b", Pattern.CASE_INSENSITIVE);
                        numberPattern = Pattern.compile("\\b" + petAddressNumber + "\\b", Pattern.CASE_INSENSITIVE);
                        neighborhoodPattern = Pattern.compile("\\b" + petNeighborhood + "\\b", Pattern.CASE_INSENSITIVE);


                        System.out.println("=========== Results ===========");

                        for (Path s : pets) {
                            petTxt = new ReadFile(s.toString());
                            String petInfo = petTxt.getFormattedData(s.toString());

                            Matcher streetMatcher = streetPattern.matcher(petInfo);
                            Matcher numberMatcher = numberPattern.matcher(petInfo);
                            Matcher neighborhoodMatcher = neighborhoodPattern.matcher(petInfo);


                            if
                            (petInfo.contains(petType) &&
                                    streetMatcher.find() &&
                                    numberMatcher.find() &&
                                    neighborhoodMatcher.find()) {

                                System.out.println(petNumber + " - " + petInfo);
                                petNumber++;
                                itFound = true;
                            }
                        }

                        if (!itFound) {
                            System.out.println("No pet was found with this/these criteria(s).");
                        }

                        break;
                    case 7:
                        System.out.println("Enter the name: ");
                        petName = petCollector.collectName();
                        System.out.println("Enter the age: ");
                        petAge = (petCollector.collectPetAge()).toString();

                        pets = searchPetsByName(petName);
                        petNumber = 1;

                       agePattern = Pattern.compile("\\b" + petAge + "\\b", Pattern.CASE_INSENSITIVE);

                        System.out.println("=========== Results ===========");

                        if (pets.isEmpty()) {
                            System.out.println("No pet was found with this/these criteria(s).");
                        }

                        for (Path s : pets) {
                            petTxt = new ReadFile(s.toString());
                            String petInfo = petTxt.getFormattedData(s.toString());

                            Matcher ageMatcher = agePattern.matcher(petInfo);

                            if (petInfo.contains(petType) && ageMatcher.find()) {
                                System.out.println(petNumber + " - " + petInfo);
                                petNumber++;
                                itFound = true;
                            }
                        }

                        if (!itFound) {
                            System.out.println("No pet was found with this/these criteria(s).");
                        }

                        break;
                    case 8:
                        System.out.println("Enter the age: ");
                        petAge =  (petCollector.collectPetAge()).toString();
                        System.out.println("Enter the weight: ");
                        petWeight = ((petCollector.collectPetWeight()).toString() + "kg");

                        pets = searchAllPets();

                        petNumber = 1;

                        agePattern = Pattern.compile("\\b" + petAge + "\\b", Pattern.CASE_INSENSITIVE);
                        weightPattern = Pattern.compile("\\b" + petWeight + "\\b", Pattern.CASE_INSENSITIVE);

                        System.out.println("=========== Results ===========");

                        for (Path s : pets) {
                            petTxt = new ReadFile(s.toString());
                            String petInfo = petTxt.getFormattedData(s.toString());

                            Matcher ageMatcher = agePattern.matcher(petInfo);
                            Matcher weightMatcher = weightPattern.matcher(petInfo);

                            if
                            (petInfo.contains(petType) &&
                                    ageMatcher.find() &&
                                    weightMatcher.find()) {

                                System.out.println(petNumber + " - " + petInfo);
                                petNumber++;
                                itFound = true;
                            }
                        }

                        if (!itFound) {
                            System.out.println("No pet was found with this/these criteria(s).");
                        }

                        break;
                    case 9:
                        System.out.println("Enter the name: ");
                        petName = petCollector.collectName();
                        System.out.println("Enter the weight: ");
                        petWeight = ((petCollector.collectPetWeight()).toString() + "kg");

                        pets = searchPetsByName(petName);
                        petNumber = 1;

                        weightPattern = Pattern.compile("\\b" + petWeight + "\\b", Pattern.CASE_INSENSITIVE);

                        System.out.println("=========== Results ===========");

                        if (pets.isEmpty()) {
                            System.out.println("No pet was found with this/these criteria(s).");
                        }

                        for (Path s : pets) {
                            petTxt = new ReadFile(s.toString());
                            String petInfo = petTxt.getFormattedData(s.toString());

                            Matcher weightMatcher = weightPattern.matcher(petInfo);

                            if (petInfo.contains(petType) && weightMatcher.find()) {
                                System.out.println(petNumber + " - " + petInfo);
                                petNumber++;
                                itFound = true;
                            }
                        }

                        if (!itFound) {
                            System.out.println("No pet was found with this/these criteria(s).");
                        }

                        break;
                    case 10:
                        System.out.println("Enter the breed: ");
                        petBreed = petCollector.collectPetBreed();
                        System.out.println("Enter the weight: ");
                        petWeight = ((petCollector.collectPetWeight()).toString() + "kg");

                        pets = searchAllPets();

                        petNumber = 1;

                        breedPattern = Pattern.compile("\\b" + petBreed, Pattern.CASE_INSENSITIVE);
                        weightPattern = Pattern.compile("\\b" + petWeight + "\\b", Pattern.CASE_INSENSITIVE);

                        System.out.println("=========== Results ===========");

                        for (Path s : pets) {
                            petTxt = new ReadFile(s.toString());
                            String petInfo = petTxt.getFormattedData(s.toString());

                            Matcher breedMatcher = breedPattern.matcher(petInfo);
                            Matcher weightMatcher = weightPattern.matcher(petInfo);

                            if
                            (petInfo.contains(petType) &&
                                    breedMatcher.find() &&
                                    weightMatcher.find()) {

                                System.out.println(petNumber + " - " + petInfo);
                                petNumber++;
                                itFound = true;
                            }
                        }

                        if (!itFound) {
                            System.out.println("No pet was found with this/these criteria(s).");
                        }


                        break;
                    case 11:
                        System.out.println("Enter the breed: ");
                        petBreed = petCollector.collectPetBreed();
                        userChoicePetGender = petCollector.collectPetGender();
                        petGender = userChoicePetGender == 1 ? "MALE" : "FEMALE";
                        pets = searchAllPets();

                        petNumber = 1;

                        breedPattern = Pattern.compile("\\b" + petBreed + "\\b", Pattern.CASE_INSENSITIVE);
                        genderPattern = Pattern.compile("\\b" + petGender + "\\b", Pattern.CASE_INSENSITIVE);

                        System.out.println("=========== Results ===========");

                        for (Path s : pets) {
                            petTxt = new ReadFile(s.toString());
                            String petInfo = petTxt.getFormattedData(s.toString());

                            Matcher breedMatcher = breedPattern.matcher(petInfo);
                            Matcher genderMatcher = genderPattern.matcher(petInfo);

                            if (petInfo.contains(petType) && breedMatcher.find() && genderMatcher.find()) {
                                System.out.println(petNumber + " - " + petInfo);
                                petNumber++;
                                itFound = true;
                            }
                        }

                        if (!itFound) {
                            System.out.println("No pet was found with this/these criteria(s).");
                        }

                        break;
                    default:
                        throw new RuntimeException("Please, provide a valid value.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid value! Type numbers only.");
                scanner.nextLine();
                userChoice = 0;
            }
        } while (true);
    }
}
