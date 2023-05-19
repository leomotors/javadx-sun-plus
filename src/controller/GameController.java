package controller;

import java.util.ArrayList;

import constant.Config;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import router.AppPage;
import router.Router;
import store.DataManager;
import store.Setting;

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
    private Label MinusScore;
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
    private AnchorPane pageRoot;
    @FXML
    private Label MaxComboCount;
    @FXML
    private Label TechnicalScore;
    @FXML
    private Label PlatinumScore;
    @FXML
    private ImageView PartnerImage;

    private ArrayList<Boolean> active = new ArrayList<Boolean>();

    private static final int WIDTH = 900;

    public void start() {
        for (int i = 0; i < Config.LANE_COUNT; i++) {
            active.add(false);
            drawLane(i);
        }
        if (DataManager.getInstance().get(Setting.PARTNER) == "CPP") {
            PartnerImage.setImage(
                    new Image(ClassLoader.getSystemResource("images/CPP.png")
                            .toString()));
        } else {
            PartnerImage.setImage(
                    new Image(ClassLoader.getSystemResource("images/JAVA.png")
                            .toString()));
        }
    }

    private void drawLane(int laneNumber) {
        GraphicsContext gc = PlayArea.getGraphicsContext2D();
        double x1 = Config.LANE_BOTTOM_WIDTH * laneNumber;
        double buffer = (WIDTH - Config.LANE_TOP_WIDTH * Config.LANE_COUNT) / 2;
        double x2 = buffer + Config.LANE_TOP_WIDTH * laneNumber;
        if (active.get(laneNumber))
            gc.setFill(Color.web("#383f47"));
        else
            gc.setFill(Color.BLACK);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1.0);
        gc.beginPath();
        gc.moveTo(x1, Config.LANE_HEIGHT);
        gc.lineTo(x2, 0);
        gc.lineTo(x2 + Config.LANE_TOP_WIDTH, 0);
        gc.lineTo(x1 + Config.LANE_BOTTOM_WIDTH, Config.LANE_HEIGHT);
        gc.closePath();
        gc.fill();
        gc.stroke();
    }

    @FXML
    public void handleKeyPress(KeyEvent e) {
        int laneNum = -1;
        switch (e.getCode()) {
            case A:
                laneNum = 0;
                break;
            case S:
                laneNum = 1;
                break;
            case D:
                laneNum = 2;
                break;
            case F:
                laneNum = 3;
                break;
            case G:
                laneNum = 4;
                break;
            case H:
                laneNum = 5;
                break;
            case J:
                laneNum = 6;
                break;
            case K:
                laneNum = 7;
                break;
            case L:
                laneNum = 8;
                break;
            case SEMICOLON:
                laneNum = 9;
                break;
            case QUOTE:
                laneNum = 10;
                break;
            case ENTER:
                laneNum = 11;
                break;
            case ESCAPE:
                // TODO It should actually bring up Pause Menu
                // (To be implemented)
                Router.getInstance().push(AppPage.SONG_SELECTION);
                break;
            default:
                break;
        }
        if (laneNum != -1) {
            active.set(laneNum, true);
            drawLane(laneNum);
        }
    }

    @FXML
    public void handleKeyRelease(KeyEvent e) {
        int laneNum = -1;
        switch (e.getCode()) {
            case A:
                laneNum = 0;
                break;
            case S:
                laneNum = 1;
                break;
            case D:
                laneNum = 2;
                break;
            case F:
                laneNum = 3;
                break;
            case G:
                laneNum = 4;
                break;
            case H:
                laneNum = 5;
                break;
            case J:
                laneNum = 6;
                break;
            case K:
                laneNum = 7;
                break;
            case L:
                laneNum = 8;
                break;
            case SEMICOLON:
                laneNum = 9;
                break;
            case QUOTE:
                laneNum = 10;
                break;
            case ENTER:
                laneNum = 11;
                break;
            default:
                break;
        }
        if (laneNum != -1) {
            active.set(laneNum, false);
            drawLane(laneNum);
        }
    }

}
