package pages;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import router.AppPage;
import router.Router;

public class SongSelectionController {
    @FXML
    private Label username;
    @FXML
    private StackPane SettingButton;
    @FXML
    private StackPane BackButton;
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
    private VBox SelectBG;
    @FXML
    private VBox NextBG;
    @FXML
    private VBox PrevBG;

    private final String BASIC_COLOR = "#22ad39";
    private final String ADVANCE_COLOR = "#f39800";
    private final String EXPERT_COLOR = "#e5024f";
    private final String MASTER_COLOR = "#ae0cd2";
    private int currentDifficulty = 0;

    @FXML
    private void backButtonHandler() {
        Router.getInstance().push(AppPage.WELCOME);
    }

    @FXML
    private void nextButtonHandler() {
        toNextSong();
    }

    @FXML
    private void prevButtonHandler() {
        toPrevSong();
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
        switch (event.getCode()) {
            case ESCAPE:
                Router.getInstance().push(AppPage.WELCOME);
                break;
            case ENTER:
                playSong();
                break;
            case Q:
                difficultyHandler(-1);
                break;
            case E:
                difficultyHandler(1);
                break;
            case A:
                toPrevSong();
                break;
            case D:
                toNextSong();
            default:
                break;
        }
    }

    @FXML
    private void playSong() {
        Router.getInstance().push(AppPage.GAME);
    }

    private void difficultyHandler(int modifier) {
        if (currentDifficulty + modifier > 3
                || currentDifficulty + modifier < 0) {
            return;
        }
        currentDifficulty += modifier;
        String bgFill;
        switch (currentDifficulty) {
            case 0:
                bgFill = "-fx-background-color: " + BASIC_COLOR + ";";
                SelectDifficulty.setText("BASIC");
                PrevDifficulty.setText("BASIC");
                NextDifficulty.setText("BASIC");
                SelectBG.setStyle(bgFill);
                NextBG.setStyle(bgFill);
                PrevBG.setStyle(bgFill);
                break;
            case 1:
                bgFill = "-fx-background-color: " + ADVANCE_COLOR + ";";
                SelectDifficulty.setText("ADVANCE");
                PrevDifficulty.setText("ADVANCE");
                NextDifficulty.setText("ADVANCE");
                SelectBG.setStyle(bgFill);
                NextBG.setStyle(bgFill);
                PrevBG.setStyle(bgFill);
                break;
            case 2:
                bgFill = "-fx-background-color: " + EXPERT_COLOR + ";";
                SelectDifficulty.setText("EXPERT");
                PrevDifficulty.setText("EXPERT");
                NextDifficulty.setText("EXPERT");
                SelectBG.setStyle(bgFill);
                NextBG.setStyle(bgFill);
                PrevBG.setStyle(bgFill);
                break;
            case 3:
                bgFill = "-fx-background-color: " + MASTER_COLOR + ";";
                SelectDifficulty.setText("MASTER");
                PrevDifficulty.setText("MASTER");
                NextDifficulty.setText("MASTER");
                SelectBG.setStyle(bgFill);
                NextBG.setStyle(bgFill);
                PrevBG.setStyle(bgFill);
                break;
        }
    }

    private void toPrevSong() {

    }

    private void toNextSong() {

    }
}
