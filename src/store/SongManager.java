package store;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SongManager {
    public List<String> listSong(String directoryPath) {
        List<String> folders = new ArrayList<>();
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    folders.add(file.getName());
                }
            }
        }
        return folders;
    }
}
