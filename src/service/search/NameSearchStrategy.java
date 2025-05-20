package service.search;

import collector.PetCollector;
import entity.enums.PetType;
import repository.FileRepository;
import util.Util;

import java.nio.file.Path;
import java.util.List;

public class NameSearchStrategy implements PetSearchStrategy {
    @Override
    public void printSearchResult(PetType petType, PetCollector collector, FileRepository fileRepository) {
        List<Path> pets = this.search(petType, collector, fileRepository);
        Util.printResults(pets);
    }
    @Override
    public List<Path> search(PetType petType, PetCollector collector, FileRepository fileRepository) {
        System.out.println("Enter the name: ");
        String petName = collector.collectName();
        List<Path> pets = fileRepository.searchByName(petName);
        return Util.filterByType(pets, petType);
    }
}
