package pages;

import constant.Resource;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import router.AppPage;
import router.Page;
import router.Router;
import utils.ImageUtil;

public class Welcome implements Page {
    private VBox node;

    @Override
    public void initialize() {
        var logo = ImageUtil.loadImageAsView(Resource.JAVADX_LOGO, 500, 500);

        this.node = new VBox();

        var button = new Button();
        button.setText("Enter Game");
        button.setFont(new Font(36));
        button.setCursor(Cursor.HAND);

        button.setOnAction(e -> {
            Router.getInstance().push(AppPage.GAME);
        });

        this.node.getChildren().addAll(logo, button);
        this.node.setAlignment(Pos.CENTER);
        this.node.setSpacing(48);
        this.node.setBackground(new Background(
                new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, null, null)));
    }

    @Override
    public Parent getNode() {
        return this.node;
    }
}
