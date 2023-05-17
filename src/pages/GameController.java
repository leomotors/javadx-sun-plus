package pages;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import router.AppPage;
import router.Router;

public class GameController {
    @FXML
    private Label BSCount;
    @FXML
    private Label SCount;
    @FXML
    private Label MKCount;
    @FXML
    private Label MissCount;
    @FXML
    private Label Rank;
    @FXML
    private Label PlayScore;
    @FXML
    private ImageView SongImage;
    @FXML
    private Label SongName;
    @FXML
    private Label ArtistName;
    @FXML
    private Canvas PlayArea;
    @FXML
    private Label JudgeName;
    @FXML
    private Label ComboCount;

    @FXML
    private void keyPressHandler(KeyEvent event) {
        System.out.println(event.getCode());
        switch (event.getCode()) {
            case ESCAPE:
                // TODO It should actually bring up Pause Menu
                // (To be implemented)
                Router.getInstance().push(AppPage.SONG_SELECTION);
                break;
            default:
                break;
        }
    }
}
