package pane;

import component.TodoItem;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class InputPane extends HBox {
    public InputPane() {
        this.setPrefHeight(70);
        this.setBackground(new Background(
                new BackgroundFill(Color.LIGHTBLUE, null, null)));
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
        TextField textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Submit");
        button.setOnAction(event -> {
            if (!textField.getText().isEmpty()) {
                TodoItem todo = new TodoItem(textField.getText());
                RootPane.getDisplayPane().addTodoItem(todo);
                textField.clear();
            }
        });
        this.getChildren().addAll(textField, button);
    }
}
