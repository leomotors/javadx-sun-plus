package logic.game;

public class LaneManager {
    private boolean isKey1Pressed = false;
    private boolean isKey2Pressed = false;

    private int lastPressed = 0;
    private int lastHold = 0;

    public void handleKeyPress(int timeStamp, boolean isSecondKey) {
        if (isSecondKey) {
            this.isKey2Pressed = true;
        } else {
            this.isKey1Pressed = true;
        }

        this.lastPressed = timeStamp;
    }

    public void handleKeyRelease(int timeStamp, boolean isSecondKey) {
        if (isSecondKey) {
            this.isKey2Pressed = false;
        } else {
            this.isKey1Pressed = false;
        }

        if (!this.isPressed()) {
            this.lastHold = timeStamp;
        }
    }

    /**
     * Is currently pressed
     */
    public boolean isPressed() {
        return this.isKey1Pressed || this.isKey2Pressed;
    }

    /**
     * Last time key is clicked
     */
    public int getLastPressed() {
        return this.lastPressed;
    }

    /**
     * Last time your finger is on the lane.
     */
    public int getLastHold(int timeStamp) {
        if (this.isPressed())
            return timeStamp;

        return this.lastHold;
    }
}
