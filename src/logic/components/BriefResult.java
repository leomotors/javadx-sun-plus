package logic.components;

import constant.DXColor;
import constant.JudgementName;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.core.PlayResult;
import utils.ScoreUtil;

/**
 * Component of {@link pages.Result}
 */
public class BriefResult extends BorderPane {
    // States

    // Top Pane
    private Text scoreText;
    private Text rankText;

    // Left Pane
    private DXText criticalPerfectText;
    private DXText perfectText;
    private DXText goodText;
    private DXText missText;

    // Right Pane
    private DXText comboText;
    private DXText fullComboLabel;
    private DXText fastText;
    private DXText lateText;

    // Bottom Pane
    private DXText platinumScoreText;

    public BriefResult() {
        this.setTop(this.createTopPane());
        this.setLeft(this.createLeftPane());
        this.setRight(this.createRightPane());
        this.setBottom(this.createBottomPane());

        this.setPadding(new Insets(24));
    }

    private Node createTopPane() {
        var topPane = new GridPane();

        this.scoreText = new Text();
        this.scoreText.setFont(new Font(144));
        this.rankText = new Text();
        this.rankText.setFont(new Font(144));

        topPane.add(new DXText("SCORE"), 0, 0);
        topPane.add(new DXText("RANK"), 1, 0);

        topPane.add(this.scoreText, 0, 1);
        topPane.add(this.rankText, 1, 1);

        topPane.setHgap(72);
        topPane.setPadding(new Insets(24));

        topPane.setBackground(new Background(
                new BackgroundFill(new Color(1.0, 1.0, 1.0, 0.5), null, null)));

        return topPane;
    }

    private Node createLeftPane() {
        var scoreTable = new GridPane();

        scoreTable.add(new DXText(JudgementName.CRITICAL_PERFECT,
                DXColor.CRITICAL_PERFECT), 0, 0);
        scoreTable.add(new DXText(JudgementName.PERFECT,
                DXColor.PERFECT), 0, 1);
        scoreTable.add(new DXText(JudgementName.GOOD, DXColor.GOOD), 0, 2);
        scoreTable.add(new DXText(JudgementName.MISS, DXColor.MISS), 0, 3);

        this.criticalPerfectText = new DXText(Color.WHITE);
        this.perfectText = new DXText(Color.WHITE);
        this.goodText = new DXText(Color.WHITE);
        this.missText = new DXText(Color.WHITE);

        scoreTable.add(this.criticalPerfectText, 1, 0);
        scoreTable.add(this.perfectText, 1, 1);
        scoreTable.add(this.goodText, 1, 2);
        scoreTable.add(this.missText, 1, 3);

        scoreTable.setVgap(16);
        scoreTable.setHgap(16);
        scoreTable.setPadding(new Insets(24));

        scoreTable.setBackground(new Background(
                new BackgroundFill(new Color(0, 0, 0, 0.75), null, null)));

        return scoreTable;
    }

    private Node createRightPane() {
        var rightPane = new VBox();

        this.comboText = new DXText("MAX COMBO ");
        this.fullComboLabel = new DXText("FULL COMBO");

        this.fastText = new DXText("FAST ", Color.BLUE);
        this.lateText = new DXText("LATE ", Color.RED);

        rightPane.getChildren().addAll(
                this.comboText, this.fullComboLabel,
                this.fastText, this.lateText);
        rightPane.setSpacing(16);
        rightPane.setPadding(new Insets(24));

        return rightPane;
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
        this.scoreText.setText(Integer.toString(score));
        this.rankText.setText(ScoreUtil.getRank(score));

        // Render Left Pane
        this.criticalPerfectText.setText(
                playResult.getPlatinumCriticalPerfect()
                        + playResult.getCriticalPerfect());
        this.perfectText.setText(playResult.getPerfect());
        this.goodText.setText(playResult.getGood());
        this.missText.setText(playResult.getMiss());

        // Render Right Pane
        this.comboText.setText("MAX COMBO " + playResult.getMaxCombo());
        this.fastText.setText("FAST " + playResult.getFast());
        this.lateText.setText("LATE " + playResult.getLate());

        // Render Bottom Pane
        var pScore = ScoreUtil.calculatePlatinumScore(playResult);
        var maxPScore = ScoreUtil.calculateMaxPlatinumScore(playResult);
        this.platinumScoreText
                .setText(String.format("PLATINUM SCORE %d/%d (%.2f%%)",
                        pScore, maxPScore, pScore * 100.0 / maxPScore));
    }
}
