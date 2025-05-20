package service.search;

import collector.PetCollector;
import entity.PetAddress;
import entity.enums.PetType;
import repository.FileRepository;
import util.ReadFile;
import util.Util;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressSearchStrategy implements PetSearchStrategy {
    @Override
    public void printSearchResult(PetType petType, PetCollector collector, FileRepository fileRepository) {
        List<Path> pets = this.search(petType, collector, fileRepository);
        Util.printResults(pets);
    }
    @Override
    public List<Path> search(PetType petType, PetCollector collector, FileRepository fileRepository) {
        PetAddress petAddress = collector.collectPetAddress();
        List<Path> allPets = fileRepository.listAllPaths();
        List<Path> filteredPets = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b" + petAddress + "\\b", Pattern.CASE_INSENSITIVE);
        for (Path s : allPets) {
            ReadFile petTxt = new ReadFile(s.toString());
            String petInfo = petTxt.getFormattedData(s.toString());

            Matcher matcher = pattern.matcher(petInfo);

            if (matcher.find()) {
                filteredPets.add(s);
            }
        };
        return Util.filterByType(filteredPets, petType);
    }
}
