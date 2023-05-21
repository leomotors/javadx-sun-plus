package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import router.AppPage;
import router.Router;
import store.DataManager;
import store.Setting;
import store.SoundManager;

public class SettingController implements BaseController {
    @FXML
    private StackPane BackButton;
    @FXML
    private Slider musicVolume;
    @FXML
    private Slider effectVolume;
    @FXML
    private Slider partnerVolume;
    @FXML
    private Slider gameSpeed;
    @FXML
    private TextField nameInput;
    private DataManager instance;

    @Override
    public void start() {
        instance = DataManager.getInstance();
        musicVolume
                .setValue(Integer.parseInt(instance.get(Setting.BGM_VOLUME)));
        effectVolume
                .setValue(Integer.parseInt(instance.get(Setting.FX_VOLUME)));
        partnerVolume
                .setValue(
                        Integer.parseInt(instance.get(Setting.PARTNER_VOLUME)));
        gameSpeed
                .setValue(Integer.parseInt(instance.get(Setting.SPEED)));
        nameInput.setText(instance.get(Setting.PLAYER_NAME));
        musicVolume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0,
                    Number arg1, Number arg2) {
                DataManager.getInstance().set(Setting.BGM_VOLUME,
                        Integer.toString((int) musicVolume.getValue()));
                SoundManager.getInstance().setVolume();
            }
        });
        effectVolume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0,
                    Number arg1, Number arg2) {
                DataManager.getInstance().set(Setting.FX_VOLUME,
                        Integer.toString((int) effectVolume.getValue()));
                SoundManager.getInstance().setVolume();
            }
        });
        partnerVolume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0,
                    Number arg1, Number arg2) {
                DataManager.getInstance().set(Setting.PARTNER_VOLUME,
                        Integer.toString((int) partnerVolume.getValue()));
                SoundManager.getInstance().setVolume();
            }
        });
        gameSpeed.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0,
                    Number arg1, Number arg2) {
                DataManager.getInstance().set(Setting.SPEED,
                        Integer.toString((int) gameSpeed.getValue()));
            }
        });
    }

    @FXML
    private void backButtonHandler() {
        Router.getInstance().push(AppPage.SONG_SELECTION);
        instance.set(Setting.PLAYER_NAME, nameInput.getText());
    }

}
