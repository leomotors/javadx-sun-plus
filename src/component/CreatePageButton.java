package component;

import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pane.RootPane;

public class CreatePageButton extends StackPane {
    public CreatePageButton() {
        Circle circle = new Circle(20, Color.LIGHTGRAY);
        Text text = new Text("+");
        text.setFont(Font.font(20));
        this.setCursor(Cursor.HAND);
        this.setOnMouseClicked(event -> {
            RootPane.getNavigationPane().addPage();
        });
        this.getChildren().addAll(circle, text);
    }
}
