package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import router.Router;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Router.createInstance(stage);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
