package repository;

import entity.Pet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FileRepository {
    private static final Logger logger = Logger.getLogger(FileRepository.class.getName());

    public void createFile(Pet pet) {
        String formattedPetName = pet.getName().replace(" ", "").toUpperCase();
        LocalDateTime dateTimeNow = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateTimeNow.format(myFormatObj);
        String date = formattedDate.replace("-", "").replace(" ", "T");
        String directory = "resources/registered-pets";
        String fileName = date + "-" + formattedPetName + ".txt";

        File file = new File(directory, fileName);
        try(FileWriter fw = new FileWriter(file, true)) {
            BufferedWriter br = getBufferedWriter(pet, fw);
            br.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error reading file", ex);
        }
    }

    private static BufferedWriter getBufferedWriter(Pet pet, FileWriter fw) throws IOException {
        BufferedWriter br = new BufferedWriter(fw);
        br.write("1 - " + pet.getName());
        br.newLine();
        br.write("2 - " + pet.getPetType());
        br.newLine();
        br.write("3 - " + pet.getPetGender());
        br.newLine();
        br.write("4 - " + pet.getPetAddress());
        br.newLine();
        br.write("5 - " + (pet.getPetAge() == null ? "NOT INFORMED" : pet.getPetAge() + " years"));
        br.newLine();
        br.write("6 - " + (pet.getPetWeight() == null ? "NOT INFORMED" : pet.getPetWeight() + "kg"));
        br.newLine();
        br.write("7 - " + (pet.getPetBreed().isEmpty() ? "NOT INFORMED" : pet.getPetBreed()));
        br.newLine();
        return br;
    }

    public List<Path> searchByName(String name) {
        Path searchDir = Paths.get("resources/registered-pets");
        try(Stream<Path> stream = Files.find(
                searchDir,
                Integer.MAX_VALUE,
                ((path, basicFileAttributes) -> {
                    if(!basicFileAttributes.isRegularFile()) return false;
                    String fileName = path.getFileName().toString();
                    return fileName.contains(name.replace(" ", "").trim().toUpperCase());
                }
                ))) {
            return stream.toList();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
