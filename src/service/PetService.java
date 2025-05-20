package service;

import collector.PetCollector;
import dto.PetFormData;
import entity.Pet;
import entity.PetAddress;
import entity.PetBuilder;
import entity.enums.PetType;
import exception.InvalidInputException;
import repository.FileRepository;
import repository.PetRepository;
import service.search.*;
import util.ReadFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class PetService {
    private final PetCollector petCollector;
    private final PetRepository petRepository;
    private final Scanner scanner;
    private final FileRepository fileRepository;
    private List<Path> filteredPets;
    private static final Logger logger = Logger.getLogger(PetService.class.getName());
    private final Map<Integer, PetSearchStrategy> searchStrategies = new HashMap<>();


    public PetService(PetCollector petCollector, PetRepository petRepository, FileRepository fileRepository, Scanner scanner) {
        this.petCollector = petCollector;
        this.petRepository = petRepository;
        this.scanner = scanner;
        this.fileRepository = fileRepository;
        searchStrategies.put(1, new NameSearchStrategy());
        searchStrategies.put(2, new GenderSearchStrategy());
        searchStrategies.put(3, new AgeSearchStrategy());
        searchStrategies.put(4, new WeightSearchStrategy());
        searchStrategies.put(5, new BreedSearchStrategy());
        searchStrategies.put(6, new AddressSearchStrategy());
        searchStrategies.put(7, new NameAndAgeSearchStrategy(new NameSearchStrategy()));
    }

    private void processFormLine(int line, PetFormData formData) {
        switch (line) {
            case 0 -> formData.setName(petCollector.collectName());
            case 1 -> formData.setType(petCollector.collectPetType());
            case 2 -> formData.setGender(petCollector.collectPetGender());
            case 3 -> formData.setAddress(petCollector.collectPetAddress());
            case 4 -> formData.setAge(petCollector.collectPetAge());
            case 5 -> formData.setWeight(petCollector.collectPetWeight());
            case 6 -> formData.setBreed(petCollector.collectPetBreed());
        }
    }

    private Pet buildPetFromFormData(PetFormData data) {
        return new PetBuilder()
                .withName(data.getName())
                .withType(data.getType())
                .withGender(data.getGender())
                .withAddress(data.getAddress())
                .withAge(data.getAge())
                .withWeight(data.getWeight())
                .withBreed(data.getBreed())
                .build();
    }

    public void registerPet() {

        PetFormData formData = new PetFormData();
        List<String> questions = new ReadFile("resources/form.txt").readFile();

        for (int line = 0; line < questions.size(); line++) {
            System.out.println(questions.get(line));
            processFormLine(line, formData);
        }

        try {
            Pet pet = buildPetFromFormData(formData);
            petRepository.createPet(pet);
        } catch (RuntimeException ex) {
            System.out.println("Error during pet creation: " + ex);
            throw new RuntimeException();
        }
    }

    public void listAllPets() {
        ReadFile petTxt;
        List<Path> texts = List.of();
        int petNumber = 1;

        try(Stream<Path> stream = Files.walk(Paths.get("resources/registered-pets"))) {
            texts =
                    stream.filter(Files::isRegularFile)
                            .filter(path -> path.getFileName().toString().endsWith(".txt"))
                            .toList();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error reading file", ex);
        }
        System.out.println("=========== All pets info ===========");
        for (Path s : texts) {
            petTxt = new ReadFile(s.toString());
            System.out.println(petNumber + " - " + petTxt.getFormattedData(s.toString()));
            petNumber++;
        }
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


    public void listPetsByCriteria(PetCollector collector, FileRepository fileRepository) {
        int userChoice;
        boolean validInput;

        do {
            validInput = false;
            try {
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

                System.out.print("Enter your choice: ");

                if (scanner.hasNextInt()) {
                    userChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (userChoice < 1 || userChoice > 12) {
                        throw new InvalidInputException("Please enter a number between 1 and 12.");
                    }

                    System.out.println("Please, select the type of pet you want to search for.");
                    PetType petType = petCollector.collectPetType();

                    PetSearchStrategy strategy = searchStrategies.get(userChoice);
                    if (strategy != null) {
                        strategy.printSearchResult(petType, collector, fileRepository);
                    } else {
                        System.out.println("Invalid option.");
                    }
                    validInput = true;

                } else {
                    String invalidInput = scanner.next();
                    throw new InvalidInputException("Invalid input '" + invalidInput + "'. Numbers only!");
                }
            } catch (InputMismatchException | InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        } while (!validInput);
    }

    public void updatePet() {
        Path path;
        listPetsByCriteria(petCollector, fileRepository);

        System.out.println("Select the pet you want to update: ");
        int userChoice = scanner.nextInt();
        scanner.nextLine();

        List<Path> pets = this.getFilteredPets();

        if (userChoice <= 0 || userChoice > pets.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        path = pets.get(userChoice - 1);

        ReadFile readFile = new ReadFile(path.toString());

        Map<String, String> petAtt = readFile.getFormattedAttributes(path.toString());

        System.out.println("Enter the name: ");
        String name = petCollector.collectName();
        if (!name.isEmpty()) {
            petAtt.put("name", name);
        }
        PetAddress petAddress = new PetAddress(petCollector.collectPetStreet(), petCollector.collectPetAddressNumber(), petCollector.collectPetNeighborhood());
        if (!petAddress.toString().isEmpty()) {
            petAtt.put("address", petAddress.toString());
        }
        System.out.println("Enter the age: ");
        String age = (petCollector.collectPetAge()).toString();
        if (!age.isEmpty() && !age.equals("-1")) {
            petAtt.put("age", age);
        }
        System.out.println("Enter the weight: ");
        String weight = (petCollector.collectPetWeight()).toString();
        if (!weight.isEmpty() && !weight.equals("-1.0") ) {
            petAtt.put("weight", weight);
        }
        System.out.println("Enter the breed: ");
        String breed = petCollector.collectPetBreed();
        if (!breed.isEmpty()) {
            petAtt.put("breed", (breed + "kg"));
        }


        System.out.println(petAtt);

        File file = new File(path.toUri());
        try(FileWriter fw = new FileWriter(file)) {
            BufferedWriter br = new BufferedWriter(fw);
            br.write("1 - " + petAtt.get("name"));
            br.newLine();
            br.write("2 - " + petAtt.get("type"));
            br.newLine();
            br.write("3 - " + petAtt.get("gender"));
            br.newLine();
            br.write("4 - " + petAtt.get("address"));
            br.newLine();
            br.write("5 - " + petAtt.get("age"));
            br.newLine();
            br.write("6 - " + petAtt.get("weight"));
            br.newLine();
            br.write("7 - " + petAtt.get("breed"));
            br.newLine();
            br.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error reading file", ex);
        }

    }

    public void deletePet() {
        Path path;
        listPetsByCriteria(petCollector, fileRepository);

        System.out.println("Select the pet you want to delete from the system: ");
        int userChoice = scanner.nextInt();
        scanner.nextLine();

        List<Path> pets = this.getFilteredPets();

        if (userChoice <= 0 || userChoice >= pets.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        path = pets.get(userChoice - 1);

        System.out.println("============== Delete ==============");
        try {
            System.out.println("Exists before delete: " + Files.exists(path));
            Files.delete(path);
            System.out.println("Exists after delete: " + Files.exists(path));
            System.out.println("File deleted successfully");
        } catch (IOException ex) {
            System.out.println("Failed to delete the file: " + ex.getMessage());
        }
    }

    public List<Path> getFilteredPets() {
        return filteredPets;
    }

    public void setFilteredPets(List<Path> filteredPets) {
        this.filteredPets = filteredPets;
    }
}
