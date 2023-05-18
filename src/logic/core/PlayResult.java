package logic.core;

public interface PlayResult {
    public int getMaxCombo();

    public Judgement getTap();

    public Judgement getHold();

    public Judgement getFlick();

    // Computed
    public default int getTotalNotes() {
        return this.getTap().getTotalNotes()
                + this.getHold().getTotalNotes()
                + this.getFlick().getTotalNotes();
    }

    public default int getPlatinumCriticalPerfect() {
        return this.getTap().getPlatinumCriticalPerfect()
                + this.getHold().getPlatinumCriticalPerfect()
                + this.getFlick().getPlatinumCriticalPerfect();
    }

    public default int getCriticalPerfect() {
        return this.getTap().getCriticalPerfect()
                + this.getHold().getCriticalPerfect()
                + this.getFlick().getCriticalPerfect();
    }

    public default int getPerfect() {
        return this.getTap().getPerfect()
                + this.getHold().getPerfect()
                + this.getFlick().getPerfect();
    }

    public default int getGood() {
        return this.getTap().getGood()
                + this.getHold().getGood()
                + this.getFlick().getGood();
    }

    public default int getMiss() {
        return this.getTap().getMiss()
                + this.getHold().getMiss()
                + this.getFlick().getMiss();
    }

    public default int getFast() {
        return this.getTap().getFast()
                + this.getHold().getFast()
                + this.getFlick().getFast();
    }

    public default int getLate() {
        return this.getTap().getLate()
                + this.getHold().getLate()
                + this.getFlick().getLate();
    }

    public default int getFastCriticalPerfect() {
        return this.getTap().getFastCriticalPerfect()
                + this.getHold().getFastCriticalPerfect()
                + this.getFlick().getFastCriticalPerfect();
    }

    public default int getLateCriticalPerfect() {
        return this.getTap().getLateCriticalPerfect()
                + this.getHold().getLateCriticalPerfect()
                + this.getFlick().getLateCriticalPerfect();
    }

    public default int getFastPerfect() {
        return this.getTap().getFastPerfect()
                + this.getHold().getFastPerfect()
                + this.getFlick().getFastPerfect();
    }

    public default int getLatePerfect() {
        return this.getTap().getLatePerfect()
                + this.getHold().getLatePerfect()
                + this.getFlick().getLatePerfect();
    }

    public default int getFastGood() {
        return this.getTap().getFastGood()
                + this.getHold().getFastGood()
                + this.getFlick().getFastGood();
    }

    public default int getLateGood() {
        return this.getTap().getLateGood()
                + this.getHold().getLateGood()
                + this.getFlick().getLateGood();
    }
}
