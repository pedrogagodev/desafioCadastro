package service.search;

import collector.PetCollector;
import entity.enums.PetType;
import repository.FileRepository;
import util.ReadFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class NameSearchStrategy implements PetSearchStrategy {
    
    @Override
    public void printSearchResult(PetType petType, PetCollector collector, FileRepository fileRepository) {
        List<Path> pets = this.search(petType, collector, fileRepository);
        ReadFile petTxt;
        int petNumber = 1;

        System.out.println("=========== Results ===========");
        if (pets.isEmpty()) {
            System.out.println("No pet was found with this/these criteria(s).");
        }

        for (Path s : pets) {
            petTxt = new ReadFile(s.toString());
            String petInfo = petTxt.getFormattedData(s.toString());

            System.out.println(petNumber + " - " + petInfo);
            petNumber++;

        }
    }
    @Override
    public List<Path> search(PetType petType, PetCollector collector, FileRepository fileRepository) {
        System.out.println("Enter the name: ");
        String petName = collector.collectName();
        List<Path> pets = fileRepository.searchByName(petName);
        return filterByType(pets, petType);
    }
    private List<Path> filterByType(List<Path> pets, PetType type) {
        ReadFile readFile;
        List<Path> filteredPets = new ArrayList<>();

        for (Path pet : pets) {
            readFile = new ReadFile(pet.toString());
            if (readFile.getFormattedData(pet.toString()).contains(type.toString().toUpperCase())) {
                filteredPets.add(pet);
            }
        }
        return filteredPets;
    }
}
