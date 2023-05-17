package logic.core;

public interface Judgement {
    public default int getTotalNotes() {
        return this.getPlatinumCriticalPerfect() + this.getCriticalPerfect()
                + this.getPerfect() + this.getGood() + this.getMiss();
    }

    // Judgements

    public int getPlatinumCriticalPerfect();

    public int getCriticalPerfect();

    public int getPerfect();

    public int getGood();

    public int getMiss();

    // Fast Late

    public int getFastCriticalPerfect();

    public int getLateCriticalPerfect();

    public int getFastPerfect();

    public int getLatePerfect();

    public int getFastGood();

    public int getLateGood();
}
