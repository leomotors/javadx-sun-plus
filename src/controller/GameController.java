package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

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
import logic.components.game.BaseNote;
import logic.components.game.TapNote;
import logic.core.FastLateType;
import logic.core.JudgementType;
import logic.core.NoteType;
import logic.game.LaneManager;
import logic.game.ScoreManager;
import store.DataManager;
import store.Setting;
import utils.ScoreUtil;

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
    private Label topLeft;
    @FXML
    private Label topRight;
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
    private Label fastLateLabel;
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

    private ArrayList<BaseNote> notes = new ArrayList<BaseNote>();
    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;
    private GraphicsContext gc;
    private GraphicsContext gcNote;

    private final ArrayList<LaneManager> laneManagers = new ArrayList<>();
    private ScoreManager scoreManager;

    private static DecimalFormat formatter = new DecimalFormat("#,###");

    public GameController() {
        for (int i = 0; i < Config.N_LANES; i++) {
            this.laneManagers.add(new LaneManager());
        }
    }

    @Override
    public void start() {
        this.scoreManager = new ScoreManager(1000);
        this.topLeft.setText(Config.TOP_LEFT_TEXT);

        gc = PlayArea.getGraphicsContext2D();
        gcNote = NoteArea.getGraphicsContext2D();

        for (int i = 0; i < Config.LANE_COUNT; i++) {
            drawLane(i);
        }

        if (DataManager.getInstance().get(Setting.PARTNER).equals("CPP")) {
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
                        notes.add(new TapNote(
                                getCurrentTime() + 3000, 1, 2));
                        notes.add(new TapNote(
                                getCurrentTime() + 4000, 3, 4));
                        notes.add(new TapNote(
                                getCurrentTime() + 5000, 5, 6));
                    }
                }));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }

    private int getCurrentTime() {
        return (int) (System.currentTimeMillis() - startTime);
    }

    private int calculatePosY(BaseNote note) {
        int timeDiff = Math.max(0, note.getTime() - getCurrentTime());
        float ratio = 1 - ((float) timeDiff / (float) Config.NOTE_SHOW_TIME);
        return (int) (ratio * HEIGHT);
    }

    // TEMP
    private int _tempCOUNTER = 0;
    Random random = new Random();

    private void update() {
        // for (DrawNote note : notes) {
        // note.setY(calculatePosY(note));
        // if (note.getY() >= HEIGHT) {
        // notes.remove(note);
        // break;
        // }
        // }
        drawNote();

        this.BSCount.setText(
                Integer.toString(this.scoreManager.getPlatinumCriticalPerfect()
                        + this.scoreManager.getCriticalPerfect()));
        this.SCount.setText(Integer.toString(this.scoreManager.getPerfect()));
        this.MKCount.setText(Integer.toString(this.scoreManager.getGood()));
        this.MissCount.setText(Integer.toString(this.scoreManager.getMiss()));

        var combo = this.scoreManager.getCombo();
        this.ComboCount.setText(combo >= 5 ? Integer.toString(combo) : "");
        this.MaxComboCount
                .setText(Integer.toString(this.scoreManager.getMaxCombo()));
        this.TechnicalScore.setText(GameController.formatter
                .format(ScoreUtil.calculateScore(this.scoreManager)));
        this.PlatinumScore.setText(String.format("%d/%d",
                ScoreUtil.calculatePlatinumScore(this.scoreManager),
                ScoreUtil.calculateCurrentMaxPlatinumScore(this.scoreManager)));

        int border = ScoreUtil.calculateScoreTypeMinus(this.scoreManager)
                - Config.TOP_RIGHT_GOAL;
        this.topRight.setText(border >= 0 ? GameController.formatter
                .format(border) : "FAIL");

        // TEMP
        this._tempCOUNTER += 1;
        if (this._tempCOUNTER >= 5) {
            this._tempCOUNTER = 0;

            if (this.scoreManager.getPlayedNotes() >= this.scoreManager
                    .getTotalNotes()) {
                return;
            }

            double rng = this.random.nextDouble();

            if (rng < 0.7) {
                this.scoreManager.addJudgement(NoteType.TAP,
                        JudgementType.PLATINUM_CRITICAL_PERFECT,
                        FastLateType.NONE);
            } else if (rng < 0.8) {
                this.scoreManager.addJudgement(NoteType.TAP,
                        JudgementType.CRITICAL_PERFECT, FastLateType.NONE);
            } else if (rng < 0.92) {
                this.scoreManager.addJudgement(NoteType.TAP,
                        JudgementType.PERFECT, FastLateType.NONE);
            } else if (rng < 0.97) {
                this.scoreManager.addJudgement(NoteType.TAP,
                        JudgementType.GOOD, FastLateType.NONE);
            } else {
                this.scoreManager.addJudgement(NoteType.TAP,
                        JudgementType.MISS, FastLateType.NONE);
            }
        }
    }

    private void drawNote() {
        gcNote.clearRect(0, 0, WIDTH, HEIGHT);

        // for (DrawNote note : notes) {
        // gcNote.setFill(Color.RED);
        // gcNote.fillRect(note.getX(), note.getY(),
        // Config.LANE_BOTTOM_WIDTH, 10);
        // }
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
