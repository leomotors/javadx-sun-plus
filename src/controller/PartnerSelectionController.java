package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
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

    @FXML
    private void SelectCpp() {
        DataManager.getInstance().set(Setting.PARTNER, "CPP");
        AudioClip Cpp = new AudioClip(ClassLoader
                .getSystemResource("sounds/partner/CPP_SELECT.wav")
                .toString());
        Cpp.play();
    }

    @FXML
    private void SelectJava() {
        DataManager.getInstance().set(Setting.PARTNER, "JAVA");
        AudioClip Java = new AudioClip(ClassLoader
                .getSystemResource("sounds/partner/JAVA_SELECT.wav")
                .toString());
        Java.play();
    }

    @FXML
    private void backButtonHandler() {
        Router.getInstance().push(AppPage.SONG_SELECTION);
    }

}
