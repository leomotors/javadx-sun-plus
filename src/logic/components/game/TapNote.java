package logic.components.game;

import constant.JudgementWindow;
import controller.GameController;
import logic.core.FastLateType;
import logic.core.JudgementType;
import logic.core.NoteType;

public class TapNote extends BaseNote {

    public TapNote(int time, int laneStart, int laneEnd) {
        super(time, laneStart, laneEnd);
    }

    @Override
    public NoteType getNoteType() {
        return NoteType.TAP;
    }

    @Override
    public NoteCheckResult checkJudgement(GameController controller) {
        if (this.isRemoved()) {
            // Thread mumbo jumbo
            return NoteCheckResult.NONE;
        }

        var currentTime = controller.getCurrentTime();
        if (currentTime < this.getTime() - JudgementWindow.GOOD) {
            // Too early to check anything
            return NoteCheckResult.NONE;
        }

        int tapTime = Integer.MAX_VALUE;

        for (int i = this.getLaneStart(); i <= this.getLaneEnd(); i++) {
            var manager = controller.getLaneManager(i);
            var lastTap = manager.getLastPressed();

            if (lastTap > this.getTime() - JudgementWindow.GOOD) {
                tapTime = Math.min(tapTime, lastTap);
            }
        }

        if (tapTime != Integer.MAX_VALUE) {
            int error = Math.abs(tapTime - this.getTime());
            var fastLate = tapTime < this.getTime() ? FastLateType.FAST
                    : FastLateType.LATE;

            if (error > JudgementWindow.GOOD) {
                System.out.println("ILLEGAL CONDITION");
            }

            this.setFastLateType(fastLate);
            if (error <= JudgementWindow.PLATINUM_CRITICAL_PERFECT) {
                this.setJudgementType(JudgementType.PLATINUM_CRITICAL_PERFECT);
                // No Fast Late for best judgement
                this.setFastLateType(FastLateType.NONE);
            } else if (error <= JudgementWindow.CRITICAL_PERFECT) {
                this.setJudgementType(JudgementType.CRITICAL_PERFECT);
            } else if (error <= JudgementWindow.PERFECT) {
                this.setJudgementType(JudgementType.PERFECT);
            } else {
                this.setJudgementType(JudgementType.GOOD);
            }

            this.setRemoved(true);
            return NoteCheckResult.REMOVE;
        }

        if (currentTime > this.getTime()
                + JudgementWindow.GOOD) {
            this.setJudgementType(JudgementType.MISS);
            this.setRemoved(true);
            return NoteCheckResult.REMOVE;
        }

        return NoteCheckResult.NONE;
    }
}
