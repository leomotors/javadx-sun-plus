package logic.components.game;

import constant.Config;
import constant.JudgementWindow;
import controller.GameController;
import logic.core.JudgementType;
import logic.core.NoteType;

public class HoldNote extends BaseNote {
    private int endTime;

    private int lastJudgeTime;

    private int judgedTicks = 0;
    private final int totalTicks;

    public HoldNote(int time, int laneStart, int laneEnd, int endTime) {
        super(time, laneStart, laneEnd);

        this.endTime = endTime;
        this.lastJudgeTime = this.getTime();
        this.totalTicks = Math.ceilDiv(this.getEndTime() - this.getTime(),
                Config.HOLD_TICK);
    }

    @Override
    public NoteType getNoteType() {
        return NoteType.HOLD;
    }

    @Override
    public NoteCheckResult checkJudgement(GameController controller) {
        if (this.judgedTicks >= this.getTotalTicks()) {
            return NoteCheckResult.NONE;
        }

        var currentTime = controller.getCurrentTime();

        if (this.lastJudgeTime + Config.HOLD_TICK > currentTime) {
            return NoteCheckResult.NONE;
        }

        this.lastJudgeTime += Config.HOLD_TICK;
        this.judgedTicks += 1;

        // Time for judgement, will be used to calculate error
        var judgeTime = Math.min(this.lastJudgeTime, this.getEndTime());

        int bestLastHold = 0;
        for (int i = this.getLaneStart(); i <= this.getLaneEnd(); i++) {
            var lastHold = controller.getLaneManager(i).getLastHold();
            bestLastHold = Math.max(bestLastHold, lastHold);
        }

        int error = Math.max(0, judgeTime - bestLastHold);

        if (error < JudgementWindow.HOLD_PLATINUM_CRITICAL_PEFECT) {
            this.setJudgementType(JudgementType.PLATINUM_CRITICAL_PERFECT);
        } else if (error < JudgementWindow.HOLD_PERFECT) {
            this.setJudgementType(JudgementType.PERFECT);
        } else if (error < JudgementWindow.HOLD_GOOD) {
            this.setJudgementType(JudgementType.GOOD);
        } else {
            this.setJudgementType(JudgementType.MISS);
        }

        if (this.judgedTicks >= this.getTotalTicks()) {
            this.setRemoved(true);
            return NoteCheckResult.REMOVE;
        } else {
            return NoteCheckResult.PRESERVE;
        }
    }

    public int getEndTime() {
        return this.endTime;
    }

    public int getTotalTicks() {
        return this.totalTicks;
    }
}
