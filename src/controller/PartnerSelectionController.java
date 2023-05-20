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

public class PartnerSelectionController implements BaseController {
    @FXML
    private StackPane BackButton;
    @FXML
    private Button SelectCppButton;
    @FXML
    private Button SelectJavaButton;

    private AudioClip selectFx = new AudioClip(ClassLoader
            .getSystemResource("sounds/fx/SELECT.mp4")
            .toString());

    @Override
    public void start() {
        if (DataManager.getInstance().get(Setting.PARTNER).equals("CPP")) {
            SelectCppButton.setDisable(true);
            SelectCppButton.setText("Selected");
            SelectJavaButton.setDisable(false);
            SelectJavaButton.setText("Select");
        } else {
            SelectCppButton.setDisable(false);
            SelectCppButton.setText("Select");
            SelectJavaButton.setDisable(true);
            SelectJavaButton.setText("Selected");
        }
    }

    @FXML
    private void SelectCpp() {
        DataManager.getInstance().set(Setting.PARTNER, "CPP");
        selectFx.play();
        SelectCppButton.setDisable(true);
        SelectCppButton.setText("Selected");
        SelectJavaButton.setDisable(false);
        SelectJavaButton.setText("Select");
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
        SelectCppButton.setDisable(false);
        SelectCppButton.setText("Select");
        SelectJavaButton.setDisable(true);
        SelectJavaButton.setText("Selected");
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
