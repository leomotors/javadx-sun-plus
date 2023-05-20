package logic.components;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;
import logic.core.Difficulty;
import store.DataManager;
import store.SongManager;
import utils.FileUtil;
import utils.ImageUtil;

public class SongInfo {

    private String id;
    private Image image;
    private String name;
    private String artist;
    private String status; // X = Unclear ,C = Clear, FC = FC, AP = AP
    private String rank; // X = No Rank, Else = respective rank
    private int score;

    private Map<Difficulty, String> difficulties;

    public SongInfo(String id) {
        this.id = id;
        this.image = ImageUtil.loadImage(getPath("/cover.png"));
        this.status = "X";
        this.rank = "X";
        this.score = 0;
        difficulties = Collections
                .unmodifiableMap(
                        Map.of(Difficulty.BASIC, "0",
                                Difficulty.ADVANCED, "0",
                                Difficulty.EXPERT, "0",
                                Difficulty.MASTER, "0"));
        var stringToEnum = new HashMap<String, Difficulty>();
        Arrays.asList(Difficulty.values()).stream()
                .map(val -> stringToEnum.put(val.name(), val));
        List<String> lines = FileUtil
                .readFileAsLines(getPath("/metadata.txt"));
        this.name = lines.get(0).split(" ", 2)[1];
        this.artist = lines.get(1).split(" ", 2)[1];
        for (int i = 2; i < lines.size(); i++) {
            var token = lines.get(i).split(" ");
            if (!stringToEnum.keySet().contains(token[0])) {
                continue;
            }

            this.difficulties.put(stringToEnum.get(token[0]),
                    token[1]);
        }
    }

    public String getPath() {
        return DataManager.getInstance().getPathPrefix()
                + SongManager.SONGS_DIR + this.id;
    }

    public String getPath(String path) {
        return DataManager.getInstance().getPathPrefix()
                + SongManager.SONGS_DIR + this.id + path;
    }

    public void fetch() {

    }

    public String getId() {
        return id;
    }

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDifficulty(Difficulty key) {
        return this.difficulties.get(key);
    }

}
