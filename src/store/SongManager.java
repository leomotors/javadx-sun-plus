package store;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.FileUtil;

public final class SongManager {
    private static SongManager instance;

    public static final String SONGS_DIR = "/charts/";
    private String pathPrefix;

    private SongManager() throws IOException {
        this.pathPrefix = FileUtil.getPathPrefix();
    }

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

    public static synchronized SongManager createInstance() throws IOException {
        SongManager.instance = new SongManager();
        return SongManager.instance;
    }

    public static synchronized SongManager getInstance() {
        return SongManager.instance;
    }

}
