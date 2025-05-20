package service.search;

import collector.PetCollector;
import entity.enums.PetType;
import repository.FileRepository;
import util.ReadFile;
import util.Util;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AgeSearchStrategy implements PetSearchStrategy {
    @Override
    public void printSearchResult(PetType petType, PetCollector collector, FileRepository fileRepository) {
        List<Path> pets = this.search(petType, collector, fileRepository);
        Util.printResults(pets);
    }
    @Override
    public List<Path> search(PetType petType, PetCollector collector, FileRepository fileRepository) {
        System.out.println("Enter the age: ");
        Integer petAge = collector.collectPetAge();
        List<Path> allPets = fileRepository.listAllPaths();
        List<Path> filteredPets = new ArrayList<>();
        Pattern agePattern = Pattern.compile("\\b" + petAge + "\\s+years\\b", Pattern.CASE_INSENSITIVE);        for (Path s : allPets) {
            ReadFile petTxt = new ReadFile(s.toString());
            String petInfo = petTxt.getFormattedData(s.toString());

            Matcher matcher = agePattern.matcher(petInfo);

            if (matcher.find()) {
                filteredPets.add(s);
            }
        };
        return Util.filterByType(filteredPets, petType);
    }
}
