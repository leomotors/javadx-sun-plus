package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import router.AppPage;
import router.Router;
import store.DataManager;
import store.Setting;

public class PartnerSelectionController {
    @FXML
    private StackPane BackButton;
    @FXML
    private Button SelectCppButton;
    @FXML
    private Button SelectJavaButton;

    private AudioClip selectFx = new AudioClip(ClassLoader
            .getSystemResource("sounds/fx/SELECT.mp4")
            .toString());

    @FXML
    private void SelectCpp() {
        DataManager.getInstance().set(Setting.PARTNER, "CPP");
        selectFx.play();
        AudioClip Cpp = new AudioClip(ClassLoader
                .getSystemResource("sounds/partner/CPP_SELECT.wav")
                .toString());
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), event -> Cpp.play()));
        timeline.play();
    }

    @FXML
    private void SelectJava() {
        DataManager.getInstance().set(Setting.PARTNER, "JAVA");
        selectFx.play();
        AudioClip Java = new AudioClip(ClassLoader
                .getSystemResource("sounds/partner/JAVA_SELECT.wav")
                .toString());
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), event -> Java.play()));
        timeline.play();
    }

    @FXML
    private void backButtonHandler() {
        Router.getInstance().push(AppPage.SONG_SELECTION);
    }

}
