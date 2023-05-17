package pages;

import java.io.IOException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.components.DXButton;
import router.Page;
import router.Router;
import utils.ImageUtil;

public class Result implements Page {
    private StackPane node;

    private Text titleText;

    @Override
    public void initialize() throws IOException {
        this.node = new StackPane();

        // TODO Change magic constant to variable
        var background = ImageUtil
                .loadImageAsView("images/SongSelectBackground.jpg", 1600, 900);

        var mainPane = this.createMainPane();

        this.node.getChildren().addAll(background, mainPane);
    }

    private BorderPane createMainPane() {
        var mainPane = new BorderPane();

        mainPane.setTop(this.createTopPane());

        // TODO Left and Right Component

        mainPane.setBottom(this.createBottomPane());

        return mainPane;
    }

    private HBox createTopPane() {
        this.titleText = new Text("PLAY RESULT");
        this.titleText.setFont(new Font("Helvetica", 48));

        var topPane = new HBox();
        topPane.getChildren().add(this.titleText);
        topPane.setPadding(new Insets(36));
        topPane.setAlignment(Pos.CENTER);

        return topPane;
    }

    private HBox createBottomPane() {
        var bottomPane = new HBox();

        var detailButton = new DXButton("Change Message");

        var nextButton = new DXButton("Next");
        nextButton.setOnAction(
                event -> Router.getInstance().reset());

        bottomPane.getChildren().addAll(detailButton, nextButton);
        bottomPane.setAlignment(Pos.CENTER_RIGHT);
        bottomPane.setPadding(new Insets(24));
        bottomPane.setSpacing(16);

        return bottomPane;
    }

    @Override
    public Parent getNode() {
        return this.node;
    }

}
