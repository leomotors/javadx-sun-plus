package application;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class FallingRectanglesApp extends Application {

    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 600;
    private static final int RECTANGLE_WIDTH = 50;
    private static final int RECTANGLE_HEIGHT = 50;
    private static final int RECTANGLE_SPEED = 5;

    private List<Rectangle> rectangles;
    private Canvas canvas;
    private GraphicsContext gc;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Falling Rectangles");

        rectangles = new ArrayList<>();
        canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        Button addButton = new Button("Add Rectangle");
        addButton.setPrefWidth(120);
        addButton.setOnAction(e -> addRectangle());

        Button removeButton = new Button("Remove Rectangle");
        removeButton.setPrefWidth(120);
        removeButton.setOnAction(e -> removeRectangle());

        StackPane root = new StackPane(canvas, addButton, removeButton);
        StackPane.setAlignment(addButton, javafx.geometry.Pos.TOP_LEFT);
        StackPane.setAlignment(removeButton, javafx.geometry.Pos.TOP_RIGHT);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

        animate();
    }

    private void animate() {
        // Animation loop
        javafx.animation.AnimationTimer timer = new javafx.animation.AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw();
            }
        };
        timer.start();
    }

    private void update() {
        for (Rectangle rectangle : rectangles) {
            rectangle
                    .setTranslateY(rectangle.getTranslateY() + RECTANGLE_SPEED);

            if (rectangle.getTranslateY() > WINDOW_HEIGHT) {
                rectangles.remove(rectangle);
                break;
            }
        }
    }

    private void draw() {
        gc.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        for (Rectangle rectangle : rectangles) {
            gc.setFill(Color.RED);
            gc.fillRect(rectangle.getTranslateX(), rectangle.getTranslateY(),
                    RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
        }
    }

    private void addRectangle() {
        Rectangle rectangle = new Rectangle(RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
        rectangle.setTranslateX(
                Math.random() * (WINDOW_WIDTH - RECTANGLE_WIDTH));
        rectangles.add(rectangle);
    }

    private void removeRectangle() {
        if (!rectangles.isEmpty()) {
            rectangles.remove(0);
        }
    }
}
