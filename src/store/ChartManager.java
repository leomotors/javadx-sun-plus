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

public final class ChartManager {
    private static ChartManager instance;

    public static final String CHARTS_DIR = "/charts/";
    private String pathPrefix;

    private static final Map<String, Difficulty> stringToDifficulty = Collections
            .unmodifiableMap(
                    Map.of("basic", Difficulty.BASIC, "advanced",
                            Difficulty.ADVANCED, "expert", Difficulty.EXPERT,
                            "master", Difficulty.MASTER, "ultima",
                            Difficulty.ULTIMA));

    private ArrayList<Chart> charts = new ArrayList<>();

    private ChartManager() throws IOException {
        this.pathPrefix = FileUtil.getPathPrefix();

        var charts = this.listSong(this.pathPrefix + ChartManager.CHARTS_DIR);

        for (var chart : charts) {
            try {
                this.getCharts().add(this.loadChart(chart));
            } catch (Exception e) {
                System.out.println("Fail to load chart " + chart);
                e.printStackTrace();
            }
        }
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
        var location = this.pathPrefix + ChartManager.CHARTS_DIR + chartId;

        var meta = FileUtil.readFileAsLines(location + "/metadata.txt");

        var title = meta.get(0).split(" ", 2)[1];
        var author = meta.get(1).split(" ", 2)[1];
        var diffMap = new HashMap<Difficulty, String>();

        for (var level : meta.subList(2, meta.size())) {
            var tokens = level.split(" ");
            var levelName = tokens[0];
            var levelConstant = tokens[1];

            if (ChartManager.stringToDifficulty.keySet().contains(levelName)) {
                diffMap.put(ChartManager.stringToDifficulty.get(levelName),
                        levelConstant);
            }
        }

        var url = new File(location + "/cover.jpg").toURI().toString();
        var image = new Image(url);

        return new Chart(chartId, title, author, image, diffMap);
    }

    public static synchronized ChartManager createInstance()
            throws IOException {
        ChartManager.instance = new ChartManager();
        return ChartManager.instance;
    }

    public static synchronized ChartManager getInstance() {
        return ChartManager.instance;
    }

    public ArrayList<Chart> getCharts() {
        return this.charts;
    }
}
