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

    public static final String MAC_OS_X_PREFIX = "/Library/Application Support/javadx";
    public static final String WINDOWS_PREFIX = "/AppData/Local/javadx";
    public static final String LINUX_PREFIX = ".javadx";

    private static String pathPrefix;

    public static String getPathPrefix() throws IOException {
        if (FileUtil.pathPrefix == null) {
            var os = System.getProperty("os.name");

            if (os.startsWith("Mac")) {
                FileUtil.pathPrefix = System.getProperty("user.home")
                        + FileUtil.MAC_OS_X_PREFIX;
            } else if (os.startsWith("Windows")) {
                FileUtil.pathPrefix = System.getProperty("user.home")
                        + FileUtil.WINDOWS_PREFIX;

            } else {
                // Assume it is Linux
                FileUtil.pathPrefix = System.getProperty("user.home")
                        + FileUtil.LINUX_PREFIX;
            }
        }

        return FileUtil.pathPrefix;
    }

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
