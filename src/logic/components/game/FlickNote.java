package logic.components.game;

import java.util.HashMap;

import constant.Config;
import constant.JudgementWindow;
import controller.GameController;
import logic.core.FastLateType;
import logic.core.JudgementType;
import logic.core.NoteType;

public class FlickNote extends BaseNote {
    private int bestTime = 1000;
    private boolean hasTap = false;

    private HashMap<Integer, Integer> timeMap = new HashMap<>();

    public FlickNote(int time, int laneStart, int laneEnd) {
        super(time, laneStart, laneEnd);
    }

    @Override
    public NoteType getNoteType() {
        return NoteType.FLICK;
    }

    @Override
    public NoteCheckResult checkJudgement(GameController controller) {
        if (this.isRemoved()) {
            return NoteCheckResult.NONE;
        }

        var currentTime = controller.getCurrentTime();

        if (currentTime >= this.getTime() + this.bestTime
                || this.bestTime <= JudgementWindow.FLICK_PLATINUM_CRITICAL_PERFECT
                || currentTime > this.getTime()
                        + JudgementWindow.FLICK_PERFECT) {
            return this.endJudgement();
        }

        int laneStart = Math.max(0, this.getLaneStart() - 1);
        int laneEnd = Math.min(11, this.getLaneEnd() + 1);

        for (int i = laneStart; i <= laneEnd; i++) {
            var lastPressed = controller.getLaneManager(i).getLastHold();
            // Fill array (?)
            timeMap.put(i, lastPressed);

            if (i >= this.getLaneStart() && i <= this.getLaneEnd() && Math.abs(
                    this.getTime() - lastPressed) <= JudgementWindow.GOOD) {
                this.hasTap = true;
            }
        }

        for (int i = laneStart; i < laneEnd; i++) {
            int ta = timeMap.get(i);
            int tb = timeMap.get(i + 1);

            if (Math.abs(ta - tb) <= Config.FLICK_GAP
                    || (ta != tb && ta == Integer.MAX_VALUE
                            || tb == Integer.MAX_VALUE)) {
                var triggerTime = Math.max(ta, tb);
                triggerTime = triggerTime == Integer.MAX_VALUE
                        ? Math.min(ta, tb)
                        : triggerTime;

                var triggerDiff = triggerTime - this.getTime();
                var scoreTime = Math.abs(triggerDiff);

                if (scoreTime < this.bestTime) {
                    this.bestTime = scoreTime;
                    this.setFastLateType(triggerDiff > 0 ? FastLateType.LATE
                            : FastLateType.FAST);
                }
            }
        }

        return NoteCheckResult.NONE;
    }

    private NoteCheckResult endJudgement() {
        this.setRemoved(true);

        if (this.bestTime > JudgementWindow.FLICK_PERFECT) {
            this.setJudgementType(
                    this.hasTap ? JudgementType.GOOD : JudgementType.MISS);
            this.setFastLateType(FastLateType.NONE);
            return NoteCheckResult.REMOVE;
        }

        if (this.bestTime <= JudgementWindow.FLICK_PLATINUM_CRITICAL_PERFECT) {
            this.setJudgementType(JudgementType.PLATINUM_CRITICAL_PERFECT);
            this.setFastLateType(FastLateType.NONE);
        } else {
            this.setJudgementType(JudgementType.PERFECT);
        }

        return NoteCheckResult.REMOVE;
    }
}
