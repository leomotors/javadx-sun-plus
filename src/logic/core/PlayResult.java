package logic.core;

public interface PlayResult {
    public default int getTotalNotes() {
        return this.getTap().getTotalNotes() + this.getHold().getTotalNotes()
                + this.getFlick().getTotalNotes();
    }

    public int getMaxCombo();

    public Judgement getTap();

    public Judgement getHold();

    public Judgement getFlick();
}
