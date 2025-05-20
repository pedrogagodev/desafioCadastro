package service.search;

import collector.PetCollector;
import entity.enums.PetGender;
import entity.enums.PetType;
import repository.FileRepository;
import util.ReadFile;
import util.Util;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BreedAndGenderSearchStrategy implements PetSearchStrategy {
    private final BreedSearchStrategy breedSearchStrategy;

    public BreedAndGenderSearchStrategy(BreedSearchStrategy breedSearchStrategy) {
        this.breedSearchStrategy = breedSearchStrategy;
    }

    @Override
    public void printSearchResult(PetType petType, PetCollector collector, FileRepository fileRepository) {
        List<Path> pets = search(petType, collector, fileRepository);
        Util.printResults(pets);
    }
    @Override
    public List<Path> search(PetType petType, PetCollector collector, FileRepository fileRepository) {
        List<Path> petsByBreed = breedSearchStrategy.search(petType, collector, fileRepository);

        PetGender petGender = collector.collectPetGender();
        List<Path> filteredPets = new ArrayList<>();
        Pattern genderPattern = Pattern.compile("\\b" + petGender + "\\b", Pattern.CASE_INSENSITIVE);
        for (Path s : petsByBreed) {
            ReadFile petTxt = new ReadFile(s.toString());
            String petInfo = petTxt.getFormattedData(s.toString());

            Matcher matcher = genderPattern.matcher(petInfo);

            if (matcher.find()) {
                filteredPets.add(s);
            }
        };
        return Util.filterByType(filteredPets, petType);
    }
}
