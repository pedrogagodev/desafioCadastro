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

public class NameAndWeightSearchStrategy implements PetSearchStrategy {
    private final NameSearchStrategy nameSearchStrategy;

    public NameAndWeightSearchStrategy(NameSearchStrategy nameSearchStrategy) {
        this.nameSearchStrategy = nameSearchStrategy;
    }

    @Override
    public void printSearchResult(PetType petType, PetCollector collector, FileRepository fileRepository) {
        List<Path> pets = search(petType, collector, fileRepository);
        Util.printResults(pets);
    }
    @Override
    public List<Path> search(PetType petType, PetCollector collector, FileRepository fileRepository) {
        List<Path> petsByName = nameSearchStrategy.search(petType, collector, fileRepository);

        System.out.println("Enter the weight: ");
        Double petWeight = collector.collectPetWeight();

        List<Path> filteredPets = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b" + petWeight + "+kg\\b", Pattern.CASE_INSENSITIVE);
        for (Path s : petsByName) {
            ReadFile petTxt = new ReadFile(s.toString());
            String petInfo = petTxt.getFormattedData(s.toString());

            Matcher matcher = pattern.matcher(petInfo);

            if (matcher.find()) {
                filteredPets.add(s);
            }
        }
        return Util.filterByType(filteredPets, petType);
    }
}
