package logic.game;

import logic.core.FastLateType;
import logic.core.Judgement;
import logic.core.JudgementType;
import logic.core.NoteType;
import logic.core.PlayResult;

public class ScoreManager implements PlayResult {
    private int combo;
    private int maxCombo;

    private PartialScoreManager tap;
    private PartialScoreManager hold;
    private PartialScoreManager flick;

    private int totalNotes;

    public ScoreManager(int totalNotes) {
        this.tap = new PartialScoreManager();
        this.hold = new PartialScoreManager();
        this.flick = new PartialScoreManager();

        this.totalNotes = totalNotes;
    }

    @Override
    public int getTotalNotes() {
        return this.totalNotes;
    }

    public void addJudgement(NoteType noteType, JudgementType judgement,
            FastLateType fastLate) {
        if (judgement == JudgementType.MISS) {
            this.combo = 0;
        } else {
            this.combo += 1;
            this.maxCombo = Math.max(this.combo, this.maxCombo);
        }

        switch (noteType) {
            case TAP:
                this.tap.addJudgement(judgement, fastLate);
                break;
            case HOLD:
                this.hold.addJudgement(judgement, fastLate);
                break;
            case FLICK:
                this.flick.addJudgement(judgement, fastLate);
                break;
        }
    }

    // Interface Getters

    @Override
    public int getMaxCombo() {
        return this.maxCombo;
    }

    @Override
    public Judgement getTap() {
        return this.tap;
    }

    @Override
    public Judgement getHold() {
        return this.hold;
    }

    @Override
    public Judgement getFlick() {
        return this.flick;
    }

}
