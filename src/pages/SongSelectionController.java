package pages;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SongSelectionController {
    @FXML
    private Label username;
    @FXML
    private StackPane settingButton;
    @FXML
    private VBox PrevButton;
    @FXML
    public VBox NextButton;
    @FXML
    private VBox PrevSong;
    @FXML
    private ImageView PrevImage;
    @FXML
    private Label PrevDifficulty;
    @FXML
    private Label PrevName;
    @FXML
    private Label PrevArtist;
    @FXML
    private Label PrevClear;
    @FXML
    private Label PrevRank;
    @FXML
    private Label PrevAPFC;
    @FXML
    private Label PrevScore;
    @FXML
    private VBox SelectButton;
    @FXML
    private VBox SelectSong;
    @FXML
    private ImageView SelectImage;
    @FXML
    private Label SelectDifficulty;
    @FXML
    private Label SelectName;
    @FXML
    private Label SelectArtist;
    @FXML
    private Label SelectClear;
    @FXML
    private Label SelectRank;
    @FXML
    private Label SelectAPFC;
    @FXML
    private Label SelectScore;
    @FXML
    private VBox NextSong;
    @FXML
    private ImageView NextImage;
    @FXML
    private Label NextDifficulty;
    @FXML
    private Label NextName;
    @FXML
    private Label NextArtist;
    @FXML
    private Label NextClear;
    @FXML
    private Label NextRank;
    @FXML
    private Label NextAPFC;
    @FXML
    private Label NextScore;

    @FXML
    private void backButtonHandler() {

    }

    @FXML
    private void nextButtonHandler() {

    }

    @FXML
    private void prevButtonHandler() {

    }

    @FXML
    private void settingButtonHandler() {

    }

    @FXML
    private void partnerButtonHandler() {
        System.out.println("CLICKED");
        NextName.setText("CLIKED");
    }

    @FXML
    private void keyPressHandler(KeyEvent event) {
        System.out.println("PRESSED!");
    }
}
