package store;

import logic.core.Chart;
import logic.core.Difficulty;
import logic.core.PlayResult;
import utils.ScoreUtilMock;

public final class AppState {
    private static AppState instance;

    private PlayResult playResult;

    private Chart currentChart;
    private Difficulty selectedDifficulty;

    private AppState() {
        this.playResult = ScoreUtilMock.getMockPlayResult();
        if (!ChartManager.getInstance().getCharts().isEmpty())
            this.currentChart = ChartManager.getInstance().getCharts().get(0);
        this.selectedDifficulty = Difficulty.BASIC;
    }

    public PlayResult getPlayResult() {
        return this.playResult;
    }

    public void setPlayResult(PlayResult playResult) {
        this.playResult = playResult;
    }

    public Chart getCurrentChart() {
        return this.currentChart;
    }

    public void setCurrentChart(Chart currentChart) {
        this.currentChart = currentChart;
    }

    public Difficulty getSelectedDifficulty() {
        return this.selectedDifficulty;
    }

    public void setSelectedDifficulty(Difficulty selectedDifficulty) {
        this.selectedDifficulty = selectedDifficulty;
    }

    public static synchronized AppState getInstance() {
        if (AppState.instance == null) {
            AppState.instance = new AppState();
        }

        return AppState.instance;
    }
}
