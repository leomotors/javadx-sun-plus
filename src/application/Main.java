package application;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import router.Router;
import store.DataManager;
import store.SongManager;
import store.SoundManager;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            DataManager.createInstance();
            SongManager.createInstance();
            SoundManager.createInstance();
            Router.createInstance(stage);
            SoundManager.getInstance().playPartner("partner/CPP_WELCOME.wav");
            Duration delay = Duration.seconds(2);
            Timeline timeline = new Timeline(
                    new KeyFrame(delay, event -> SoundManager.getInstance()
                            .playBGM("bgm/welcome.mp3")));
            timeline.play();

        } catch (IOException e) {
            e.printStackTrace();
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
