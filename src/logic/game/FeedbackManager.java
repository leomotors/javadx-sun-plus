package logic.game;

import constant.DXColor;
import constant.JudgementName;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import logic.core.FastLateType;
import logic.core.JudgementType;
import logic.core.NoteType;

public class FeedbackManager {
    public static int DISPLAY_TIME = 1500;

    public static String judgementToName(JudgementType judgementType) {
        switch (judgementType) {
            case PLATINUM_CRITICAL_PERFECT:
            case CRITICAL_PERFECT:
                return JudgementName.CRITICAL_PERFECT;
            case PERFECT:
                return JudgementName.PERFECT;
            case GOOD:
                return JudgementName.GOOD;
            case MISS:
                return JudgementName.MISS;
        }

        return null;
    }

    public static Color judgementToColor(JudgementType judgementType) {
        switch (judgementType) {
            case PLATINUM_CRITICAL_PERFECT:
            case CRITICAL_PERFECT:
                return DXColor.CRITICAL_PERFECT;
            case PERFECT:
                return DXColor.PERFECT;
            case GOOD:
                return DXColor.GOOD;
            case MISS:
                return DXColor.MISS;
        }

        return null;
    }

    public static Effect platinumEffect = new DropShadow() {
        {
            this.setBlurType(BlurType.THREE_PASS_BOX);
            this.setHeight(30);
            this.setWidth(30);
            this.setRadius(14.5);
            this.setColor(new Color(1, 1, 1, 0.8));
        }
    };

    private Label judgementLabel;
    private Label fastLateLabel;
    private ScoreManager proxyScore;

    private JudgementType currentJudgement;
    private FastLateType currentFastLate;

    private long lastJudgementFeedback;
    private long lastFastLateFeedback;

    public FeedbackManager(Label judgementLabel, Label fastLateLabel,
            ScoreManager proxyScore) {
        this.judgementLabel = judgementLabel;
        this.fastLateLabel = fastLateLabel;
        this.proxyScore = proxyScore;
    }

    public void addJudgement(NoteType noteType, JudgementType judgement,
            FastLateType fastLate) {
        this.updateFeedback(judgement, fastLate);
        this.proxyScore.addJudgement(noteType, judgement, fastLate);
    }

    public void updateFeedback(JudgementType judgement, FastLateType fastLate) {
        var currentTime = System.currentTimeMillis();

        if (fastLate != FastLateType.NONE) {
            this.currentFastLate = fastLate;
            this.lastFastLateFeedback = currentTime;
        }

        this.currentJudgement = judgement;
        this.lastJudgementFeedback = currentTime;
    }

    public void updateDisplay() {
        var currentTime = System.currentTimeMillis();
        var acceptableTime = currentTime - DISPLAY_TIME;

        if (this.lastJudgementFeedback < acceptableTime) {
            this.currentJudgement = null;
        }

        if (this.lastFastLateFeedback < acceptableTime) {
            this.currentFastLate = null;
        }

        this.setJudgementFeedback(this.currentJudgement);
        this.setFastLateFeedback(this.currentFastLate);
    }

    private void setJudgementFeedback(JudgementType judgement) {
        if (judgement == null) {
            this.judgementLabel.setText("");
            return;
        }

        this.judgementLabel.setText(FeedbackManager.judgementToName(judgement));
        this.judgementLabel
                .setTextFill(FeedbackManager.judgementToColor(judgement));
        this.judgementLabel
                .setEffect(judgement == JudgementType.PLATINUM_CRITICAL_PERFECT
                        ? FeedbackManager.platinumEffect
                        : null);
    }

    private void setFastLateFeedback(FastLateType fastLate) {
        if (fastLate == null) {
            this.fastLateLabel.setText("");
            return;
        }

        this.fastLateLabel
                .setText(fastLate == FastLateType.FAST ? "FAST" : "LATE");
        this.fastLateLabel.setTextFill(
                fastLate == FastLateType.FAST ? Color.BLUE : Color.RED);
    }
}
