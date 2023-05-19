package logic.game;

import logic.core.FastLateType;
import logic.core.Judgement;
import logic.core.JudgementType;

public class PartialScoreManager implements Judgement {
    private int platinumCriticalPerfect;
    private int criticalPerfect;
    private int perfect;
    private int good;
    private int miss;

    private int fastCriticalPerfect;
    private int fastPerfect;
    private int fastGood;

    private int lateCriticalPerfect;
    private int latePerfect;
    private int lateGood;

    public void addJudgement(JudgementType judgement, FastLateType fastLate) {
        switch (judgement) {
            case PLATINUM_CRITICAL_PERFECT: {
                this.platinumCriticalPerfect += 1;

                if (fastLate == FastLateType.FAST)
                    this.fastCriticalPerfect += 1;
                else if (fastLate == FastLateType.LATE)
                    this.lateCriticalPerfect += 1;

                break;
            }
            case CRITICAL_PERFECT: {
                this.criticalPerfect += 1;

                if (fastLate == FastLateType.FAST)
                    this.fastCriticalPerfect += 1;
                else if (fastLate == FastLateType.LATE)
                    this.lateCriticalPerfect += 1;

                break;
            }
            case PERFECT: {
                this.perfect += 1;

                if (fastLate == FastLateType.FAST)
                    this.fastPerfect += 1;
                else if (fastLate == FastLateType.LATE)
                    this.latePerfect += 1;

                break;
            }
            case GOOD: {
                this.good += 1;

                if (fastLate == FastLateType.FAST)
                    this.fastGood += 1;
                else if (fastLate == FastLateType.LATE)
                    this.lateGood += 1;

                break;
            }
            case MISS: {
                this.miss += 1;
                break;
            }
        }
    }

    // Interface Getters

    @Override
    public int getPlatinumCriticalPerfect() {
        return this.platinumCriticalPerfect;
    }

    @Override
    public int getCriticalPerfect() {
        return this.criticalPerfect;
    }

    @Override
    public int getPerfect() {
        return this.perfect;
    }

    @Override
    public int getGood() {
        return this.good;
    }

    @Override
    public int getMiss() {
        return this.miss;
    }

    @Override
    public int getFastCriticalPerfect() {
        return this.fastCriticalPerfect;
    }

    @Override
    public int getLateCriticalPerfect() {
        return this.lateCriticalPerfect;
    }

    @Override
    public int getFastPerfect() {
        return this.fastPerfect;
    }

    @Override
    public int getLatePerfect() {
        return this.latePerfect;
    }

    @Override
    public int getFastGood() {
        return this.fastGood;
    }

    @Override
    public int getLateGood() {
        return this.lateGood;
    }

}
