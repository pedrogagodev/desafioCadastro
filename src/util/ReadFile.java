package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadFile {
    private final File file;
    private static final Logger logger = Logger.getLogger(ReadFile.class.getName());

    public ReadFile(String pathname) {
        this.file = new File(pathname);
    }

    public void printFile() {
        try(FileReader fr = new FileReader(this.file)) {
            int i;
            while((i= fr.read()) != -1) {
                System.out.print((char)i);
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file", e);
        }
    }

   public void printLine(int line) {
       try(FileReader fr = new FileReader(file)) {
           BufferedReader br = new BufferedReader(fr);
           String question;
           int currentLine = 0;
           while((question = br.readLine()) != null) {
               if (currentLine == line) {
                   System.out.println(question);
                   break;
               }
               currentLine++;
           }
       } catch (IOException ex) {
           logger.log(Level.SEVERE, "Error reading file", ex);
       }
   }

   public String getFormattedData(String filePath) {
       StringBuilder sb = new StringBuilder();
       try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
           String line;
           while ((line = br.readLine()) != null) {
               String[] parts = line.split(" - ", 2);

               if (parts.length > 1) {
                   sb.append(parts[1].trim()).append(", ");
               }
           }
       } catch (IOException ex) {
           ex.printStackTrace();
       }
       if (sb.length() > 2) {
           sb.setLength(sb.length() - 2);
       }
       return sb.toString();
   }

    private static final Map<Integer, String> KEY_MAPPING = Map.of(
            1, "name",
            2, "type",
            3, "gender",
            4, "address",
            5, "age",
            6, "weight",
            7, "breed"
    );


    public Map<String, String> getFormattedAttributes(String filePath) {
        Map<String, String> petMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" - ", 2);

                if (parts.length == 2) {
                    int keyNumber = Integer.parseInt(parts[0].trim());
                    String value = parts[1].trim();

                    String attributeKey = KEY_MAPPING.get(keyNumber);

                    if (attributeKey != null) {
                        petMap.put(attributeKey, value);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return petMap;
    }

}
