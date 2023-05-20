package logic.components.game;

import controller.GameController;
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
    public boolean checkJudgement(GameController controller) {
        return false;
    }
}
