package repository;

import entity.Pet;

public class PetRepository {
    public void createPet(Pet pet) {
        FileRepository file = new FileRepository();
        file.createFile(pet);
    }
}
