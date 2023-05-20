package logic.components;

import java.util.Collections;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import logic.core.Chart;
import logic.core.Difficulty;

public class ChartCard extends HBox {
    public static final Map<Difficulty, Color> difficultyToColor = Collections
            .unmodifiableMap(
                    Map.of(Difficulty.BASIC, Color.GREEN, Difficulty.ADVANCED,
                            Color.YELLOW, Difficulty.EXPERT, Color.RED,
                            Difficulty.MASTER, Color.PURPLE, Difficulty.ULTIMA,
                            Color.DARKRED));

    public ChartCard(Chart chart, Difficulty difficulty) {
        this.setBackground(new Background(new BackgroundFill(
                ChartCard.difficultyToColor.get(difficulty), null, null)));

        var imageView = new ImageView(chart.image());
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        this.getChildren().addAll(imageView,
                this.createRightPane(chart, difficulty));
    }

    private VBox createRightPane(Chart chart, Difficulty difficulty) {
        var box = new VBox();

        var diffText = new DXText(
                difficulty.name() + " " + chart.difficulties().get(difficulty));

        var titleText = new DXText(chart.title());
        var authorText = new DXText(chart.author());

        diffText.setFontSize(24);
        titleText.setFontSize(24);
        authorText.setFontSize(20);

        titleText.setWrappingWidth(250);
        authorText.setWrappingWidth(250);

        box.getChildren().addAll(diffText, titleText, authorText);
        box.setPadding(new Insets(16));
        box.setSpacing(8);
        box.setAlignment(Pos.CENTER_LEFT);

        return box;
    }
}
