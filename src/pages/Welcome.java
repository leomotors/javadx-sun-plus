package pages;

import config.Resource;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import router.AppPage;
import router.Page;
import router.Router;
import utils.Utils;

public class Welcome implements Page {
    private VBox node;

    @Override
    public void initialize() {
        var logo = Utils.loadImage(Resource.JAVADX_LOGO, 500, 500);

        this.node = new VBox();

        var button = new Button();
        button.setText("Go to Game");
        button.setFont(new Font(48));

        button.setOnAction(e -> {
            Router.getInstance().push(AppPage.GAME);
        });

        this.node.getChildren().addAll(logo, button);
        this.node.setAlignment(Pos.CENTER);
        this.node.setSpacing(36);
    }

    @Override
    public Parent getNode() {
        return this.node;
    }
}
