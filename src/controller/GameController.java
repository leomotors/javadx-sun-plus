package controller;

import java.util.ArrayList;

import constant.Config;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import logic.components.DrawNote;
import logic.game.LaneManager;
import store.DataManager;
import store.Setting;

public class GameController implements BaseController {
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
    private Canvas NoteArea;
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
    private long startTime;
    private Timeline animation;
    private long UPDATE_DELAY = 10;

    private ArrayList<DrawNote> notes = new ArrayList<DrawNote>();
    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;
    private GraphicsContext gc;
    private GraphicsContext gcNote;

    private final ArrayList<LaneManager> laneManagers = new ArrayList<>();

    public GameController() {
        for (int i = 0; i < Config.N_LANES; i++) {
            this.laneManagers.add(new LaneManager());
        }
    }

    @Override
    public void start() {
        gc = PlayArea.getGraphicsContext2D();
        gcNote = NoteArea.getGraphicsContext2D();

        for (int i = 0; i < Config.LANE_COUNT; i++) {
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

        startTime = System.currentTimeMillis();
        animation = new Timeline(new KeyFrame(Duration.millis(UPDATE_DELAY),
                event -> {
                    update();
                    if (notes.isEmpty()) {
                        notes.add(new DrawNote(
                                getCurrentTime() + 3000, 1, 2));
                        notes.add(new DrawNote(
                                getCurrentTime() + 4000, 3, 4));
                        notes.add(new DrawNote(
                                getCurrentTime() + 5000, 5, 6));
                    }
                }));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }

    private int getCurrentTime() {
        return (int) (System.currentTimeMillis() - startTime);
    }

    private int calculatePosY(DrawNote note) {
        int timeDiff = Math.max(0, note.getTime() - getCurrentTime());
        float ratio = 1 - ((float) timeDiff / (float) Config.NOTE_SHOW_TIME);
        return (int) (ratio * HEIGHT);
    }

    private void update() {
        for (DrawNote note : notes) {
            note.setY(calculatePosY(note));
            if (note.getY() >= HEIGHT) {
                notes.remove(note);
                break;
            }
        }
        drawNote();
    }

    private void drawNote() {
        gcNote.clearRect(0, 0, WIDTH, HEIGHT);

        for (DrawNote note : notes) {
            gcNote.setFill(Color.RED);
            gcNote.fillRect(note.getX(), note.getY(),
                    Config.LANE_BOTTOM_WIDTH, 10);
        }
    }

    private void drawLane(int laneNumber) {
        double x1 = Config.LANE_BOTTOM_WIDTH * laneNumber;
        double buffer = (WIDTH - Config.LANE_TOP_WIDTH * Config.LANE_COUNT) / 2;
        double x2 = buffer + Config.LANE_TOP_WIDTH * laneNumber;
        if (this.getLaneManager(laneNumber).isPressed())
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

    public LaneManager getLaneManager(int lane) {
        return this.laneManagers.get(lane);
    }

    @FXML
    public void handleKeyPress(KeyEvent e) {
        int laneId = Config.getLaneFromKey(e.getCode()) - 1;

        if (laneId < 0)
            return;

        int laneNum = laneId % Config.N_LANES;

        this.getLaneManager(laneNum).handleKeyPress(0,
                laneId > Config.N_LANES);

        this.drawLane(laneNum);
    }

    @FXML
    public void handleKeyRelease(KeyEvent e) {
        int laneId = Config.getLaneFromKey(e.getCode()) - 1;

        if (laneId < 0)
            return;

        int laneNum = laneId % Config.N_LANES;

        this.getLaneManager(laneNum).handleKeyRelease(0,
                laneId > Config.N_LANES);

        this.drawLane(laneNum);
    }

}
