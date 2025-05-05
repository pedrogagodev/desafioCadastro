package repository;

import entity.Pet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileRepository {
    private static final Logger logger = Logger.getLogger(FileRepository.class.getName());


    public void createFile(Pet pet) {
        String formattedPetName = pet.getName().replace(" ", "").toUpperCase();
        LocalDateTime dateTimeNow = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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
        br.write("5 - " + (pet.getPetAge() == -1 ? "NOT INFORMED" : pet.getPetAge()));
        br.newLine();
        br.write("6 - " + (pet.getPetWeight() == -1 ? "NOT INFORMED" : pet.getPetWeight()));
        br.newLine();
        br.write("7 - " + (pet.getPetBreed().isEmpty() ? "NOT INFORMED" : pet.getPetBreed()));
        br.newLine();
        return br;
    }

}
