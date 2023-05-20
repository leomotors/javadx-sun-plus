package logic.components;

import constant.DXColor;
import constant.JudgementName;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import logic.core.ClearType;
import logic.core.Judgement;
import logic.core.PlayResult;
import utils.ScoreUtil;

/**
 * Component of {@link pages.Result}
 */
public class DetailedResult extends BorderPane {
    // States

    // Top Pane
    private DXText scoreText;
    private DXText comboText;
    private DXText rankText;
    private DXText clearText;

    // Left Pane
    private GridPane scoreTable;

    // Bottom Pane
    private DXText platinumScoreText;

    public DetailedResult() {
        this.setTop(this.createTopPane());
        this.setLeft(this.createLeftPane());
        this.setRight(this.createRightPane());
        this.setBottom(this.createBottomPane());

        this.setPadding(new Insets(16));
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
        this.scoreTable = new GridPane();

        this.scoreTable.setPadding(new Insets(16));

        this.scoreTable.setHgap(2);
        this.scoreTable.setVgap(2);

        this.scoreTable.setBackground(new Background(
                new BackgroundFill(new Color(0, 0, 0, 0.75), null, null)));

        return this.scoreTable;
    }

    private void renderLeftPane(PlayResult playResult) {
        this.scoreTable.getChildren().clear();

        this.scoreTable.add(this.createCell(), 0, 0);
        this.scoreTable.add(
                this.createCell(JudgementName.CRITICAL_PERFECT,
                        DXColor.CRITICAL_PERFECT),
                0, 1);
        this.scoreTable.add(
                this.createCell(JudgementName.PERFECT, DXColor.PERFECT), 0, 2);
        this.scoreTable.add(this.createCell(JudgementName.GOOD, DXColor.GOOD),
                0, 3);
        this.scoreTable.add(this.createCell(JudgementName.MISS, DXColor.MISS),
                0, 4);

        // Render Col 1 - 3
        this.renderJudgement(playResult.getTap(), "TAP", 1);
        this.renderJudgement(playResult.getHold(), "HOLD", 2);
        this.renderJudgement(playResult.getFlick(), "FLICK", 3);

        // Render Col 4
        this.renderFastLateColumn(playResult);
    }

    private void renderJudgement(Judgement judgement, String name, int col) {
        var topCell = this.createCell(name, Color.WHITE);
        var percentText = new DXText(String.format("%.2f%%",
                ScoreUtil.calculatePartialScoreAsPercentage(judgement)),
                Color.WHITE);
        percentText.setFontSize(16);
        topCell.getChildren().add(percentText);
        topCell.setAlignment(Pos.CENTER);
        topCell.setSpacing(4);

        this.scoreTable.add(topCell, col, 0);

        this.scoreTable
                .add(this.createCell(judgement.getPlatinumCriticalPerfect()
                        + judgement.getCriticalPerfect(),
                        DXColor.CRITICAL_PERFECT), col, 1);
        this.scoreTable.add(
                this.createCell(judgement.getPerfect(), DXColor.PERFECT),
                col, 2);
        this.scoreTable.add(this.createCell(judgement.getGood(), DXColor.GOOD),
                col, 3);
        this.scoreTable.add(this.createCell(judgement.getMiss(), DXColor.MISS),
                col, 4);
    }

    private void renderFastLateColumn(PlayResult playResult) {
        this.scoreTable.add(
                this.renderFastLateCell(playResult.getFast(),
                        playResult.getLate(), 22),
                4, 0);

        this.scoreTable.add(
                this.renderFastLateCell(playResult.getFastCriticalPerfect(),
                        playResult.getLateCriticalPerfect()),
                4, 1);
        this.scoreTable.add(
                this.renderFastLateCell(playResult.getFastPerfect(),
                        playResult.getLatePerfect()),
                4, 2);
        this.scoreTable.add(
                this.renderFastLateCell(playResult.getFastGood(),
                        playResult.getLateGood()),
                4, 3);

        this.scoreTable.add(this.createCell("-", Color.WHITE), 4, 4);
    }

    private VBox renderFastLateCell(int fast, int late) {
        return this.renderFastLateCell(fast, late, 20);
    }

    private VBox renderFastLateCell(int fast, int late, int fontSize) {
        var fastText = new DXText("FAST " + fast, Color.LIGHTBLUE);
        var lateText = new DXText("LATE " + late, Color.PINK);

        fastText.setFontSize(fontSize);
        lateText.setFontSize(fontSize);

        return this.createCell(fastText, lateText);
    }

    private VBox createCell(Node... children) {
        var cell = new VBox();
        cell.setBorder(new Border(
                new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        cell.getChildren().addAll(children);

        cell.setPadding(new Insets(16));

        return cell;
    }

    private VBox createCell(String text) {
        if (text != null) {
            var dxText = new DXText(text);
            dxText.setFontSize(24);
            return this.createCell(dxText);
        }

        return this.createCell();
    }

    private VBox createCell(String text, Color color) {
        if (text != null) {
            var dxText = new DXText(text, color);
            dxText.setFontSize(24);
            return this.createCell(dxText);
        }

        return this.createCell();
    }

    private Pane createCell(int number) {
        return this.createCell(Integer.toString(number));
    }

    private VBox createCell(int number, Color color) {
        return this.createCell(Integer.toString(number), color);
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
        // Render Top Pane
        var score = ScoreUtil.calculateScore(playResult);
        var rank = ScoreUtil.getRank(score);
        var clearType = ScoreUtil.getClearType(playResult);

        this.scoreText.setText("SCORE " + score);
        this.rankText.setText("RANK " + rank);
        this.comboText.setText("MAX COMBO " + playResult.getMaxCombo());
        this.clearText.setText(clearType == ClearType.ALL_PERFECT
                ? "MADE IN SAMUT PRAKAN"
                : clearType == ClearType.FULL_COMBO ? "FULL COMBO" : "CLEAR");

        // Render Left Pane
        this.renderLeftPane(playResult);

        // Render Bottom Pane
        var pScore = ScoreUtil.calculatePlatinumScore(playResult);
        var maxPScore = ScoreUtil.calculateMaxPlatinumScore(playResult);
        this.platinumScoreText
                .setText(String.format("PLATINUM SCORE %d/%d (%.2f%%)",
                        pScore, maxPScore, pScore * 100.0 / maxPScore));
    }
}
