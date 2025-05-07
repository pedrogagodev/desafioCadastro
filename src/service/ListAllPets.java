package service;

import util.ReadFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class ListAllPets {
    private ReadFile petTxt;
    private List<Path> texts;
    private int petNumber = 1;
    private static final Logger logger = Logger.getLogger(ListAllPets.class.getName());

    public ListAllPets(ReadFile petTxt) {
        this.petTxt = petTxt;
    }


    public void listPets() {
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
}
