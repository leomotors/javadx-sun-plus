package logic.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import constant.Config;
import logic.components.game.BaseNote;
import logic.components.game.EXTapNote;
import logic.components.game.FlickNote;
import logic.components.game.HoldNote;
import logic.components.game.TapNote;
import logic.core.Chart;
import logic.core.Difficulty;
import store.AppState;
import store.ChartManager;
import utils.FileUtil;

public class MapLoader {
    private LinkedList<BaseNote> notes = new LinkedList<>();
    private int totalNotes = 0;

    public MapLoader(Chart chart, Difficulty difficulty) throws IOException {
        // Choose another difficulty if not exist
        if (!chart.difficulties().containsKey(difficulty)) {
            difficulty = chart.difficulties().keySet().stream().findFirst()
                    .get();
            AppState.getInstance().setSelectedDifficulty(difficulty);
        }

        var filePath = FileUtil.getPathPrefix() + ChartManager.CHARTS_DIR
                + chart.id() + "/" + difficulty.name().toLowerCase() + ".txt";

        var notesData = FileUtil.readFileAsLines(filePath);

        var notes = notesData.stream().map(line -> {
            var tokens = line.split(" ");

            if (tokens.length < 4) {
                System.out.println("INVALID NOTE " + line);
                return null;
            }

            var time = Integer.parseInt(tokens[1]);
            var laneStart = Integer.parseInt(tokens[2]) - 1;
            var laneEnd = Integer.parseInt(tokens[3]) - 1;
            var arg0 = tokens.length >= 5 ? Integer.parseInt(tokens[4]) : 0;

            if (tokens[0].equals("TAP")) {
                if (arg0 > 0) {
                    return new EXTapNote(time, laneStart, laneEnd);
                } else {
                    return new TapNote(time, laneStart, laneEnd);
                }
            }

            if (tokens[0].equals("HOLD")) {
                return new HoldNote(time, laneStart, laneEnd, arg0);
            }

            if (tokens[0].equals("FLICK")) {
                return new FlickNote(time, laneStart, laneEnd);
            }

            System.out.println("UNKNOWN NOTE TYPE " + line);

            return null;
        }).filter(note -> note != null).toList();

        this.totalNotes = notes.stream().mapToInt(note -> {
            if (note instanceof HoldNote holdNote) {
                return holdNote.getTotalTicks();
            } else {
                return 1;
            }
        }).sum();

        for (var note : notes) {
            this.notes.add(note);
        }

        System.out.printf("Notes Object %d, Total Notes %d\n",
                this.notes.size(),
                this.getTotalNotes());
    }

    /**
     * Load notes that should be show up
     */
    public ArrayList<BaseNote> getNotes(int currentTime) {
        var notes = new ArrayList<BaseNote>();

        while (!this.notes.isEmpty() && this.notes.peekFirst()
                .getTime() <= currentTime + Config.NOTE_SHOW_TIME) {
            notes.add(this.notes.pop());
        }

        return notes;
    }

    public int getTotalNotes() {
        return this.totalNotes;
    }
}
