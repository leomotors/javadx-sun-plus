package logic.components.game;

public class DrawNote extends BaseNote {
    private int x;
    private int y;

    public DrawNote() {
        this.time = 0;
        this.laneStart = 0;
        this.laneEnd = 0;
        x = 0;
        y = 0;
    }

    public DrawNote(int time, int laneStart, int laneEnd) {
        this.time = time;
        this.laneStart = laneStart;
        this.laneEnd = laneEnd;
        x = 0;
        y = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
