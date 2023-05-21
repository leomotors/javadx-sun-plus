package logic.components.game;

import controller.GameController;
import logic.core.FastLateType;
import logic.core.JudgementType;
import logic.core.NoteType;

public abstract class BaseNote {
    private final int time;
    private final int laneStart;
    private final int laneEnd;
    private boolean removed = false;

    private JudgementType judgementType;
    private FastLateType fastLateType = FastLateType.NONE;

    public BaseNote(int time, int laneStart, int laneEnd) {
        this.time = time;
        this.laneStart = laneStart;
        this.laneEnd = laneEnd;
    }

    public int getTime() {
        return time;
    }

    public int getLaneStart() {
        return laneStart;
    }

    public int getLaneEnd() {
        return laneEnd;
    }

    public JudgementType getJudgementType() {
        return this.judgementType;
    }

    public FastLateType getFastLateType() {
        return this.fastLateType;
    }

    protected void setJudgementType(JudgementType judgementType) {
        this.judgementType = judgementType;
    }

    protected void setFastLateType(FastLateType fastLateType) {
        this.fastLateType = fastLateType;
    }

    public abstract NoteType getNoteType();

    public boolean isRemoved() {
        return this.removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public abstract NoteCheckResult checkJudgement(GameController controller);
}
