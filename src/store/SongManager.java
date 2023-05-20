package store;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;
import logic.core.Chart;
import logic.core.Difficulty;
import utils.FileUtil;

public final class SongManager {
    private static SongManager instance;

    public static final String SONGS_DIR = "/charts/";
    private String pathPrefix;

    private static final Map<String, Difficulty> stringToDifficulty = Collections
            .unmodifiableMap(
                    Map.of("basic", Difficulty.BASIC, "advanced",
                            Difficulty.ADVANCED, "expert", Difficulty.EXPERT,
                            "master", Difficulty.MASTER, "ultima",
                            Difficulty.ULTIMA));

    private ArrayList<Chart> charts = new ArrayList<>();

    private SongManager() throws IOException {
        this.pathPrefix = FileUtil.getPathPrefix();

        var songs = this.listSong(this.pathPrefix + SongManager.SONGS_DIR);

        for (var song : songs) {
            try {
                var chart = this.loadChart(song);
                this.getCharts().add(chart);
            } catch (Exception e) {
                System.out.println("Fail to load chart " + song);
                e.printStackTrace();
            }
        }

        System.out.println(this.getCharts());
    }

    private List<String> listSong(String directoryPath) {
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

    private Chart loadChart(String chartId) {
        var location = this.pathPrefix + SongManager.SONGS_DIR + chartId;

        var meta = FileUtil.readFileAsLines(location + "/metadata.txt");

        var title = meta.get(0).split(" ", 2)[1];
        var author = meta.get(1).split(" ", 2)[1];
        var diffMap = new HashMap<Difficulty, String>();

        for (var level : meta.subList(2, meta.size())) {
            var tokens = level.split(" ");
            var levelName = tokens[0];
            var levelConstant = tokens[1];

            if (SongManager.stringToDifficulty.keySet().contains(levelName)) {
                diffMap.put(SongManager.stringToDifficulty.get(levelName),
                        levelConstant);
            }
        }

        var url = new File(location + "/cover.jpg").toURI().toString();
        var image = new Image(url);

        return new Chart(chartId, title, author, image, diffMap);
    }

    public static synchronized SongManager createInstance() throws IOException {
        SongManager.instance = new SongManager();
        return SongManager.instance;
    }

    public static synchronized SongManager getInstance() {
        return SongManager.instance;
    }

    public ArrayList<Chart> getCharts() {
        return this.charts;
    }
}
