package util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadFile {
    private final File file;
    private static final Logger logger = Logger.getLogger(ReadFile.class.getName());

    public ReadFile(String pathname) {
        this.file = new File(pathname);
    }

    FileReader fr;

    public void printFile() {
        try {
            fr = new FileReader(this.file);
            int i;
            while((i= fr.read()) != -1) {
                System.out.print((char)i);
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file", e);
        }
    }

}
