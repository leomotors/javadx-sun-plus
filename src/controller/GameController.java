package controller;

import java.util.ArrayList;

import constant.Config;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
    private AnchorPane pageRoot;
    private ArrayList<Boolean> active = new ArrayList<Boolean>();

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

    private static final int WIDTH = 900;

    public void start() {
        for (int i = 0; i < Config.LANE_COUNT; i++) {
            drawLane(i);
            active.add(false);
        }
    }

    private void drawLane(int laneNumber) {
        GraphicsContext gc = PlayArea.getGraphicsContext2D();
        double x1 = Config.LANE_BOTTOM_WIDTH * laneNumber;
        double buffer = (WIDTH - Config.LANE_TOP_WIDTH * Config.LANE_COUNT) / 2;
        double x2 = buffer + Config.LANE_TOP_WIDTH * laneNumber;
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1.0);
        // gc.setGlobalAlpha(0.8);
        gc.beginPath();
        gc.moveTo(x1, Config.LANE_HEIGHT);
        gc.lineTo(x2, 0);
        gc.lineTo(x2 + Config.LANE_TOP_WIDTH, 0);
        gc.lineTo(x1 + Config.LANE_BOTTOM_WIDTH, Config.LANE_HEIGHT);
        gc.closePath();
        gc.fill();
        gc.stroke();
    }

    private void activateLane(int laneNumber) {
        GraphicsContext gc = PlayArea.getGraphicsContext2D();
        double x1 = Config.LANE_BOTTOM_WIDTH * laneNumber;
        double buffer = (WIDTH - Config.LANE_TOP_WIDTH * Config.LANE_COUNT) / 2;
        double x2 = buffer + Config.LANE_TOP_WIDTH * laneNumber;
        gc.setFill(Color.web("#383f47"));
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1.0);
        // gc.setGlobalAlpha(0.8);
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
    public void handleKeyRelease(KeyEvent e) {
        switch (e.getCode()) {
            case A:
                drawLane(0);
                active.set(0, false);
                break;
            case S:
                drawLane(1);
                active.set(1, false);
                break;
            case D:
                drawLane(2);
                active.set(2, false);
                break;
            case F:
                drawLane(3);
                active.set(3, false);
                break;
            case G:
                drawLane(4);
                active.set(4, false);
                break;
            case H:
                drawLane(5);
                active.set(5, false);
                break;
            case J:
                drawLane(6);
                active.set(6, false);
                break;
            case K:
                drawLane(7);
                active.set(7, false);
                break;
            case L:
                drawLane(8);
                active.set(8, false);
                break;
            case SEMICOLON:
                drawLane(9);
                active.set(9, false);
                break;
            case QUOTE:
                drawLane(10);
                active.set(10, false);
                break;
            case ENTER:
                drawLane(11);
                active.set(11, false);
                break;
            default:
                break;
        }
        e.consume();
    }

    @FXML
    public void handleKeyPress(KeyEvent e) {
        System.out.println(e.getCode());
        switch (e.getCode()) {
            case A:
                activateLane(0);
                active.set(0, true);
                break;
            case S:
                activateLane(1);
                active.set(1, true);
                break;
            case D:
                activateLane(2);
                active.set(2, true);
                break;
            case F:
                activateLane(3);
                active.set(3, true);
                break;
            case G:
                activateLane(4);
                active.set(4, true);
                break;
            case H:
                activateLane(5);
                active.set(5, true);
                break;
            case J:
                activateLane(6);
                active.set(6, true);
                break;
            case K:
                activateLane(7);
                active.set(7, true);
                break;
            case L:
                activateLane(8);
                active.set(8, true);
                break;
            case SEMICOLON:
                activateLane(9);
                active.set(9, true);
                break;
            case QUOTE:
                activateLane(10);
                active.set(10, true);
                break;
            case ENTER:
                activateLane(11);
                active.set(11, true);
                break;
            default:
                break;
        }
        e.consume();
    }
}
