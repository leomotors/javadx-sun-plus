package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import router.AppPage;
import router.Router;

public class WelcomeController implements BaseController {
    @FXML
    private Button welcomeButton;

    @Override
    public void start() {

    }

    @FXML
    public void buttonHandler() {
        Router.getInstance().push(AppPage.SONG_SELECTION);
    }

    @FXML
    private Button tempButton;

    @FXML
    public void tempHandler() {
        Router.getInstance().push(AppPage.RESULT);
    }
}
