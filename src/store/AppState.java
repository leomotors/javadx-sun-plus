package store;

import logic.core.PlayResult;
import utils.ScoreUtilMock;

public final class AppState {
    private static AppState instance;

    private PlayResult playResult;

    private AppState() {
        this.playResult = ScoreUtilMock.getMockPlayResult();
    }

    public PlayResult getPlayResult() {
        return this.playResult;
    }

    public void setPlayResult(PlayResult playResult) {
        this.playResult = playResult;
    }

    public static synchronized AppState getInstance() {
        if (AppState.instance == null) {
            AppState.instance = new AppState();
        }

        return AppState.instance;
    }
}
