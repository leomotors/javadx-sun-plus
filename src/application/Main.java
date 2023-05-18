package application;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;
import router.Router;
import store.DataManager;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Router.createInstance(stage);
            DataManager.createInstance();
            AudioClip welcome = new AudioClip(ClassLoader
                    .getSystemResource("sounds/partner/CPP_WELCOME.wav")
                    .toString());
            welcome.play();
            AudioClip bgmPlayer = new AudioClip(ClassLoader
                    .getSystemResource("sounds/bgm/welcome.mp3")
                    .toString());
            bgmPlayer.setCycleCount(AudioClip.INDEFINITE);
            Duration delay = Duration.seconds(2);
            Timeline timeline = new Timeline(
                    new KeyFrame(delay, event -> bgmPlayer.play()));
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
