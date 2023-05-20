package logic.components.game;

import controller.GameController;
import logic.core.FastLateType;
import logic.core.JudgementType;
import logic.core.NoteType;

public class FlickNote extends BaseNote {

    public FlickNote(int time, int laneStart, int laneEnd) {
        super(time, laneStart, laneEnd);
    }

    @Override
    public NoteType getNoteType() {
        return NoteType.FLICK;
    }

    @Override
    public NoteCheckResult checkJudgement(GameController controller) {
        if (controller.getCurrentTime() > this.getTime()) {
            this.setJudgementType(JudgementType.MISS);
            this.setFastLateType(FastLateType.NONE);
            return NoteCheckResult.REMOVE;
        }

        return NoteCheckResult.NONE;
    }
}
