package util;

import entity.enums.PetType;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Util {
    public static List<Path> filterByType(List<Path> pets, PetType type) {
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

    public static void printResults(List<Path> pets) {
        int petNumber = 1;

        System.out.println("=========== Results ===========");
        if (pets.isEmpty()) {
            System.out.println("No pet was found with this/these criteria(s).");
            return;
        }

        for (Path s : pets) {
            ReadFile petTxt = new ReadFile(s.toString());
            String petInfo = petTxt.getFormattedData(s.toString());

            System.out.println(petNumber + " - " + petInfo);
            petNumber++;
        }
    }
}
