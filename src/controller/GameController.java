package controller;

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
        GraphicsContext gc = PlayArea.getGraphicsContext2D();
        for (int i = 0; i < Config.LANE_COUNT; i++) {
            drawLane(gc, i);
        }
    }

    private void drawLane(GraphicsContext gc, int laneNumber) {
        double x1 = Config.LANE_BOTTOM_WIDTH * laneNumber;
        double buffer = (WIDTH - Config.LANE_TOP_WIDTH * Config.LANE_COUNT) / 2;
        double x2 = buffer + Config.LANE_TOP_WIDTH * laneNumber;
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1.0);
        gc.setGlobalAlpha(0.8);
        gc.beginPath();
        gc.moveTo(x1, Config.LANE_HEIGHT);
        gc.lineTo(x2, 0);
        gc.lineTo(x2 + Config.LANE_TOP_WIDTH, 0);
        gc.lineTo(x1 + Config.LANE_BOTTOM_WIDTH, Config.LANE_HEIGHT);
        gc.closePath();
        gc.fill();
        gc.stroke();
    }
}
