package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import router.Router;
import store.DataManager;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Router.createInstance(stage);
            DataManager.createInstance();
        } catch (IOException e) {
            e.printStackTrace();
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
