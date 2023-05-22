package controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import constant.Config;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import logic.components.ChartCard;
import logic.components.game.BaseNote;
import logic.components.game.EXTapNote;
import logic.components.game.FlickNote;
import logic.components.game.HoldNote;
import logic.components.game.TapNote;
import logic.game.FeedbackManager;
import logic.game.LaneManager;
import logic.game.MapLoader;
import logic.game.ScoreManager;
import router.AppPage;
import router.Router;
import store.AppState;
import store.DataManager;
import store.Setting;
import store.SoundManager;
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
    private VBox cardBox;

    @FXML
    private Canvas PlayArea;
    @FXML
    private Canvas NoteArea;

    @FXML
    private Label APLabel;
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

    private long UPDATE_DELAY = 10;
    private Timeline animation;

    private ArrayList<BaseNote> notes = new ArrayList<BaseNote>();
    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;

    /**
     * Graphic context for drawing lanes
     */
    private GraphicsContext gcLanes;
    /**
     * Graphic context for drawing notes
     */
    private GraphicsContext gcNotes;

    private final ArrayList<LaneManager> laneManagers = new ArrayList<>();

    public LaneManager getLaneManager(int lane) {
        return this.laneManagers.get(lane);
    }

    private ScoreManager scoreManager;
    private FeedbackManager feedbackManager;
    private MapLoader mapLoader;

    private int timeWindow;
    private static DecimalFormat formatter = new DecimalFormat("#,###");

    /**
     * Runs everytime a game starts
     * 
     * @throws IOException
     */
    @Override
    public void start() {
        this.laneManagers.clear();
        this.notes.clear();
        this.timeWindow = Config.NOTE_SHOW_TIME
                / Integer
                        .parseInt(DataManager.getInstance().get(Setting.SPEED));

        for (int i = 0; i < Config.N_LANES; i++) {
            this.laneManagers.add(new LaneManager());
        }

        try {
            this.mapLoader = new MapLoader(
                    AppState.getInstance().getCurrentChart(),
                    AppState.getInstance().getSelectedDifficulty());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setupLabels();
        this.setupCanvas();

        SoundManager.getInstance().stopBGM();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    SoundManager
                            .getInstance()
                            .playBGM(AppState.getInstance().getCurrentChart());
                    SoundManager.getInstance()
                            .setOnEndOfMedia(() -> this.endPhase());
                }));

        timeline.play();
    }

    private void setupLabels() {
        this.scoreManager = new ScoreManager(this.mapLoader.getTotalNotes());
        this.feedbackManager = new FeedbackManager(this.JudgeName,
                this.fastLateLabel, this.scoreManager);

        this.topLeft.setText(Config.TOP_LEFT_TEXT);
        this.cardBox.getChildren().clear();
        this.cardBox.getChildren()
                .add(new ChartCard(AppState.getInstance().getCurrentChart(),
                        AppState.getInstance().getSelectedDifficulty()));

        this.APLabel.setText("");
    }

    private void setupCanvas() {
        gcLanes = PlayArea.getGraphicsContext2D();
        gcNotes = NoteArea.getGraphicsContext2D();

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

        animation = new Timeline(new KeyFrame(Duration.millis(UPDATE_DELAY),
                event -> {
                    update();
                    notes.addAll(
                            this.mapLoader.getNotes(this.getCurrentTime()));
                }));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }

    public int getCurrentTime() {
        return SoundManager.getInstance().getTime();
    }

    /**
     * Runs every 10ms
     */
    private void update() {
        for (var note : notes) {
            var checkResult = note.checkJudgement(this);

            switch (checkResult) {
                case NONE:
                    break;

                case REMOVE:
                    Platform.runLater(() -> {
                        this.notes.remove(note);

                        // UNCOMMENT THESE LINES TO GET PLAY SOUND
                        // ONLY WORKS ON WINDOWS, will break on MACOS
                        // if (note.getJudgementType() != JudgementType.MISS)
                        // SoundManager.getInstance()
                        // .playFx("fx/tapSound.mp3");
                    });
                case PRESERVE:
                    Platform.runLater(() -> {
                        this.feedbackManager.addJudgement(note.getNoteType(),
                                note.getJudgementType(),
                                note.getFastLateType());
                    });
            }
        }

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

        this.feedbackManager.updateDisplay();
    }

    private void endPhase() {
        System.out.println("Game finished");
        this.animation.stop();

        var partnerName = "partner/"
                + DataManager.getInstance().get(Setting.PARTNER);

        if (ScoreUtil.isAllPerfect(this.scoreManager)) {
            this.APLabel.setText("MADE IN SAMUT PRAKAN");
            SoundManager.getInstance().playPartner(partnerName + "_AP.wav");
        } else if (ScoreUtil.isFullCombo(this.scoreManager)) {
            this.APLabel.setText("FULL COMBO");
            SoundManager.getInstance().playPartner(partnerName + "_FC.wav");
        } else {
            this.APLabel.setText(ScoreUtil
                    .calculateScore(this.scoreManager) >= ScoreUtil.RANK_D
                            ? "CLEAR"
                            : "FAILED");
        }

        AppState.getInstance().setPlayResult(this.scoreManager);

        new Thread(() -> {
            try {
                Thread.sleep(3000);
                Platform.runLater(() -> {
                    Router.getInstance().replace(AppPage.RESULT);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private int calculatePosY(BaseNote note) {
        int timeDiff = Math.max(0, note.getTime() - getCurrentTime());
        float ratio = 1 - ((float) timeDiff / (float) timeWindow);
        return (int) (ratio * HEIGHT);
    }

    private int calculatePosX(BaseNote note) {
        int laneNumber = note.getLaneStart();
        // bottom left
        double x1 = Config.LANE_BOTTOM_WIDTH * laneNumber;
        // empty space
        double buffer = (WIDTH - Config.LANE_TOP_WIDTH * Config.LANE_COUNT) / 2;
        // top left
        double x2 = buffer + Config.LANE_TOP_WIDTH * laneNumber;
        double translation = x1 - x2;
        int timeDiff = Math.max(0, note.getTime() - getCurrentTime());
        // 0-100 = top to bottom
        float ratio = 1 - ((float) timeDiff / (float) timeWindow);
        return (int) (x2 + (ratio * translation));
    }

    private int calculateWidth(BaseNote note) {
        int laneCount = note.getLaneEnd() - note.getLaneStart() + 1;
        int timeDiff = Math.max(0, note.getTime() - getCurrentTime());
        int translation = Config.LANE_BOTTOM_WIDTH - Config.LANE_TOP_WIDTH;
        float ratio = 1 - ((float) timeDiff / (float) timeWindow);
        return (int) ((laneCount * Config.LANE_TOP_WIDTH)
                + ratio * laneCount * translation);
    }

    private void drawNote() {
        gcNotes.clearRect(0, 0, WIDTH, HEIGHT);

        for (var note : notes) {
            // Draw only hold notes
            gcNotes.setFill(Color.WHITE);
            gcNotes.fillRect(calculatePosX(note), calculatePosY(note) - 3,
                    calculateWidth(note), 21);

            if (note instanceof HoldNote) {
                drawHold((HoldNote) note);
                continue;
            }
        }

        for (var note : notes) {
            if (note instanceof HoldNote)
                continue;

            gcNotes.setFill(Color.WHITE);
            gcNotes.fillRect(calculatePosX(note), calculatePosY(note) - 3,
                    calculateWidth(note), 21);
            if (note instanceof EXTapNote) {
                gcNotes.setFill(Color.GOLD);
            } else if (note instanceof FlickNote) {
                gcNotes.setFill(Color.BLUE);
            } else {
                gcNotes.setFill(Color.RED);
            }

            gcNotes.fillRect(calculatePosX(note) + 3, calculatePosY(note),
                    calculateWidth(note) - 6, 15);
        }
    }

    private void drawHold(HoldNote note) {
        BaseNote top = new TapNote(note.getTime(), note.getLaneStart(),
                note.getLaneEnd());
        int topX = calculatePosX(top);
        int topY = calculatePosY(top);
        int topW = calculateWidth(top);
        BaseNote bottom = new TapNote(note.getEndTime(), note.getLaneStart(),
                note.getLaneEnd());
        int bottomX = calculatePosX(bottom);
        int bottomY = calculatePosY(bottom);
        int bottomW = calculateWidth(bottom);

        boolean isPressed = false;

        if (this.getCurrentTime() < note.getTime()) {
            isPressed = true;
        } else {
            for (int i = note.getLaneStart(); i <= note.getLaneEnd(); i++) {
                if (this.getLaneManager(i).isCurrentlyPressed()) {
                    isPressed = true;
                    break;
                }
            }
        }

        gcNotes.setFill(
                isPressed ? Color.CORAL
                        : new Color(0.75, 0, 0, 0.5));

        gcNotes.setStroke(Color.WHITE);
        gcNotes.setLineWidth(3);
        gcNotes.beginPath();
        gcNotes.moveTo(bottomX, bottomY);
        gcNotes.lineTo(topX, topY);
        gcNotes.lineTo(topX + topW, topY);
        gcNotes.lineTo(bottomX + bottomW, bottomY);
        gcNotes.closePath();
        gcNotes.fill();
        gcNotes.stroke();
    }

    private void drawLane(int laneNumber) {
        double x1 = Config.LANE_BOTTOM_WIDTH * laneNumber;
        double buffer = (WIDTH - Config.LANE_TOP_WIDTH * Config.LANE_COUNT) / 2;
        double x2 = buffer + Config.LANE_TOP_WIDTH * laneNumber;

        // Make lane gray if currently pressed
        if (this.getLaneManager(laneNumber).isCurrentlyPressed()) {
            gcLanes.setFill(Color.web("rgba(57, 62, 70, 0.9)"));
        } else {
            gcLanes.setFill(Color.web("rgba(0, 0, 0, 0.7)"));
        }

        gcLanes.setStroke(Color.WHITE);
        gcLanes.setLineWidth(1.0);
        gcLanes.beginPath();
        gcLanes.moveTo(x1, Config.LANE_HEIGHT);
        gcLanes.lineTo(x2, 0);
        gcLanes.lineTo(x2 + Config.LANE_TOP_WIDTH, 0);
        gcLanes.lineTo(x1 + Config.LANE_BOTTOM_WIDTH, Config.LANE_HEIGHT);
        gcLanes.closePath();
        gcLanes.fill();
        gcLanes.stroke();
    }

    @FXML
    public void handleKeyPress(KeyEvent e) {
        int laneId = Config.getLaneFromKey(e.getCode()) - 1;

        if (laneId < 0)
            return;

        int laneNum = laneId % Config.N_LANES;

        this.getLaneManager(laneNum).handleKeyPress(this.getCurrentTime(),
                laneId > Config.N_LANES);

        gcLanes.clearRect(0, 0, WIDTH, HEIGHT);
        for (int i = 0; i < Config.LANE_COUNT; i++) {
            drawLane(i);
        }
    }

    @FXML
    public void handleKeyRelease(KeyEvent e) {
        int laneId = Config.getLaneFromKey(e.getCode()) - 1;

        if (laneId < 0)
            return;

        int laneNum = laneId % Config.N_LANES;

        this.getLaneManager(laneNum).handleKeyRelease(this.getCurrentTime(),
                laneId > Config.N_LANES);

        gcLanes.clearRect(0, 0, WIDTH, HEIGHT);
        for (int i = 0; i < Config.LANE_COUNT; i++) {
            drawLane(i);
        }
    }

}
