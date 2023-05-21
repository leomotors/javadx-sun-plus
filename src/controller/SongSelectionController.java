package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import logic.core.Chart;
import router.AppPage;
import router.Router;
import store.AppState;
import store.DataManager;
import store.Setting;
import store.SongManager;
import store.SoundManager;

public class SongSelectionController implements BaseController {
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
    private int currentSongIndex = 1;
    private int chartSize;
    private String prevID;
    private String selectID;
    private String nextID;
    private SongManager instance;

    @Override
    public void start() {
        username.setText(DataManager.getInstance().get(Setting.PLAYER_NAME));
        instance = SongManager.getInstance();
        chartSize = instance.getCharts().size();
        setAllChart();
    }

    private void setAllChart() {
        if (chartSize == 0)
            return;
        var prevIdx = currentSongIndex - 1;
        var nextIdx = (currentSongIndex + 1) % chartSize;
        if (prevIdx < 0)
            prevIdx += chartSize;
        Chart prev = instance.getCharts()
                .get(prevIdx);
        Chart select = instance.getCharts().get(currentSongIndex % chartSize);
        Chart next = instance.getCharts()
                .get(nextIdx);
        setPrevChart(prev);
        setNextChart(next);
        setSelectChart(select);
    }

    private void setPrevChart(Chart chart) {
        prevID = chart.id();
        PrevName.setText(chart.title());
        PrevArtist.setText(chart.author());
        PrevImage.setImage(chart.image());
    }

    private void setSelectChart(Chart chart) {
        AppState.getInstance().setCurrentChart(chart);
        selectID = chart.id();
        SelectName.setText(chart.title());
        SelectArtist.setText(chart.author());
        SelectImage.setImage(chart.image());
    }

    private void setNextChart(Chart chart) {
        nextID = chart.id();
        NextName.setText(chart.title());
        NextArtist.setText(chart.author());
        NextImage.setImage(chart.image());
    }

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
        Router.getInstance().push(AppPage.PARTNER_SELECTION);
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
                SelectDifficulty.setText("ADVANCED");
                PrevDifficulty.setText("ADVANCED");
                NextDifficulty.setText("ADVANCED");
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
        currentSongIndex = currentSongIndex - 1;
        if (currentSongIndex < 0)
            currentSongIndex += chartSize;
        SoundManager.getInstance().playFx("fx/SELECT.mp3");
        setAllChart();
    }

    private void toNextSong() {
        currentSongIndex = (currentSongIndex + 1) % chartSize;
        SoundManager.getInstance().playFx("fx/SELECT.mp3");
        setAllChart();
    }
}
