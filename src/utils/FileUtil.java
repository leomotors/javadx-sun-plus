package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class FileUtil {
    private FileUtil() {}

    public static List<String> readFileAsLines(String filePath) {
        var fileContent = new ArrayList<String>();

        try {
            var file = new File(filePath);
            var reader = new Scanner(file);

            while (reader.hasNextLine()) {
                fileContent.add(reader.nextLine());
            }

            reader.close();
        } catch (FileNotFoundException e) {}

        return fileContent;
    }

    public static void writeToFile(String filePath, String content)
            throws IOException {
        var path = Path.of(filePath);

        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        }

        Files.write(path, content.getBytes());
    }
}
