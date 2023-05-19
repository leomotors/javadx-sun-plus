package logic.components;

public abstract class BaseNote {
    protected int time;
    protected int laneStart;
    protected int laneEnd;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLaneStart() {
        return laneStart;
    }

    public void setLaneStart(int laneStart) {
        this.laneStart = laneStart;
    }

    public int getLaneEnd() {
        return laneEnd;
    }

    public void setLaneEnd(int laneEnd) {
        this.laneEnd = laneEnd;
    }

}
