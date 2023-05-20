package logic.components.game;

import controller.GameController;
import logic.core.FastLateType;
import logic.core.JudgementType;

public class EXTapNote extends TapNote {

    public EXTapNote(int time, int laneStart, int laneEnd) {
        super(time, laneStart, laneEnd);
    }

    @Override
    public boolean checkJudgement(GameController controller) {
        var result = super.checkJudgement(controller);

        if (result) {
            this.setFastLateType(FastLateType.NONE);
            this.setJudgementType(JudgementType.PLATINUM_CRITICAL_PERFECT);
        }

        return result;
    }
}