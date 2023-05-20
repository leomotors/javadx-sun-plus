package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import router.AppPage;
import router.Router;
import store.DataManager;
import store.Setting;
import store.SoundManager;

public class PartnerSelectionController implements BaseController {
    @FXML
    private StackPane BackButton;
    @FXML
    private Button SelectCppButton;
    @FXML
    private Button SelectJavaButton;

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
        SoundManager.getInstance().playFx("fx/SELECT.mp4");
        SelectCppButton.setDisable(true);
        SelectCppButton.setText("Selected");
        SelectJavaButton.setDisable(false);
        SelectJavaButton.setText("Select");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), event -> SoundManager
                        .getInstance().playPartner("partner/CPP_SELECT.wav")));
        timeline.play();
    }

    @FXML
    private void SelectJava() {
        DataManager.getInstance().set(Setting.PARTNER, "JAVA");
        SoundManager.getInstance().playFx("fx/SELECT.mp4");
        SelectCppButton.setDisable(false);
        SelectCppButton.setText("Select");
        SelectJavaButton.setDisable(true);
        SelectJavaButton.setText("Selected");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), event -> SoundManager
                        .getInstance().playPartner("partner/JAVA_SELECT.wav")));
        timeline.play();
    }

    @FXML
    private void backButtonHandler() {
        Router.getInstance().push(AppPage.SONG_SELECTION);
    }

}
