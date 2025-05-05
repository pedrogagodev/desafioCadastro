package service;

import collector.PetCollector;
import entity.Pet;
import entity.PetAddress;
import entity.enums.PetGender;
import entity.enums.PetType;
import repository.PetRepository;
import util.ReadFile;

public class RegisterPet {
    private final PetCollector petCollector;
    private final PetRepository petRepository;

    public RegisterPet(PetCollector petCollector, PetRepository petRepository) {
        this.petCollector = petCollector;
        this.petRepository = petRepository;
    }

    public void collectPetData() {
        Pet pet = new Pet();

        ReadFile form = new ReadFile("resources/form.txt");
        int line = 0;


        do {
            form.printLine(line);

            switch (line) {
                case 0:
                    pet.setName(petCollector.collectName());
                    break;
                case 1:
                    int typeChoice = petCollector.collectPetType();

                    if (typeChoice == 1) {
                        pet.setPetType(PetType.DOG);
                    } else if (typeChoice == 2) {
                        pet.setPetType(PetType.CAT);
                    }
                    break;
                case 2:
                    int genderChoice = petCollector.collectPetGender();
                    if (genderChoice == 1) {
                        pet.setPetGender(PetGender.MALE);
                    } else if (genderChoice == 2) {
                        pet.setPetGender(PetGender.FEMALE);
                    }
                    break;
                case 3:

                    PetAddress petAddress = new PetAddress(petCollector.collectPetStreet(), petCollector.collectPetAddressNumber(), petCollector.collectPetNeighborhood());
                    pet.setPetAddress(petAddress);
                    break;

                case 4:
                    pet.setPetAge(petCollector.collectPetAge());
                    break;

                case 5:
                    pet.setPetWeight(petCollector.collectPetWeight());
                    break;

                case 6:
                    pet.setPetBreed(petCollector.collectPetBreed());
                    break;
            }
            line++;
        } while(line != 7);

        petRepository.createPet(pet);
    }
}
