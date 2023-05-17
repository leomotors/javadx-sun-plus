package logic.components;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import logic.core.ClearType;
import logic.core.PlayResult;
import utils.ScoreUtil;

public class DetailedResult extends BorderPane {
    // States

    // Top Pane
    private DXText scoreText;
    private DXText comboText;
    private DXText rankText;
    private DXText clearText;

    // Bottom Pane
    private DXText platinumScoreText;

    public DetailedResult() {
        this.setTop(this.createTopPane());
        this.setLeft(this.createLeftPane());
        this.setRight(this.createRightPane());
        this.setBottom(this.createBottomPane());
    }

    private Node createTopPane() {
        var topGrid = new GridPane();

        this.scoreText = new DXText("SCORE");
        this.comboText = new DXText("MAX COMBO");
        this.rankText = new DXText("RANK");
        this.clearText = new DXText("CLEAR");

        topGrid.add(this.scoreText, 0, 0);
        topGrid.add(this.comboText, 1, 0);
        topGrid.add(this.rankText, 0, 1);
        topGrid.add(this.clearText, 1, 1);

        topGrid.setPadding(new Insets(16));
        topGrid.setHgap(24);
        topGrid.setVgap(8);

        return topGrid;
    }

    private Node createLeftPane() {
        return new GridPane();
    }

    private Node createRightPane() {
        return new GridPane();
    }

    private Node createBottomPane() {
        var bottomPane = new HBox();
        this.platinumScoreText = new DXText("PLATINUM SCORE ");

        bottomPane.getChildren().add(this.platinumScoreText);
        bottomPane.setPadding(new Insets(16, 0, 0, 24));

        return bottomPane;
    }

    public void render(PlayResult playResult) {
        var score = ScoreUtil.calculateScore(playResult);
        var rank = ScoreUtil.getRank(score);
        var clearType = ScoreUtil.getClearType(playResult);

        this.scoreText.setText("SCORE " + score);
        this.rankText.setText("RANK " + rank);
        this.comboText.setText("MAX COMBO " + playResult.getMaxCombo());
        this.clearText.setText(clearType == ClearType.ALL_PERFECT
                ? "MADE IN SAMUT PRAKAN"
                : clearType == ClearType.FULL_COMBO ? "FULL COMBO" : "CLEAR");

        var pScore = ScoreUtil.calculatePlatinumScore(playResult);
        var maxPScore = ScoreUtil.calculateMaxPlatinumScore(playResult);
        this.platinumScoreText
                .setText(String.format("PLATINUM SCORE %d/%d (%.2f%%)",
                        pScore, maxPScore, pScore * 100.0 / maxPScore));
    }
}
