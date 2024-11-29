package savetofile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Save {
    public static void saveResultToFile(String result, String folderName, String fileName) {
        try {
            folderName = "test/" + folderName;
            File resultFolder = new File(folderName);
            
            if (!resultFolder.exists()) {
                resultFolder.mkdir();
            }

            File outputFile = new File(resultFolder, fileName);

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            writer.write(result);
            writer.close();

        } catch (IOException e) {
            System.err.println("Error saving result to file: " + e.getMessage());
        }
    }
}