package logic.game;

public class LaneManager {
    private boolean isKey1Pressed = false;
    private boolean isKey2Pressed = false;

    private int lastPressed = 0;
    private int lastHold = 0;

    public void handleKeyPress(int timeStamp, boolean isSecondKey) {
        if (isSecondKey) {
            if (this.isKey2Pressed)
                return;
            this.isKey2Pressed = true;
        } else {
            if (this.isKey1Pressed)
                return;
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

        if (!this.isCurrentlyPressed()) {
            this.lastHold = timeStamp;
        }
    }

    public boolean isCurrentlyPressed() {
        return this.isKey1Pressed || this.isKey2Pressed;
    }

    /**
     * Last time key is clicked, used for judging tap note / flick note
     */
    public int getLastPressed() {
        return this.lastPressed;
    }

    /**
     * Last time your finger is on the lane.
     * Returns {@link Integer.MAX_VALUE} if currently holding
     */
    public int getLastHold() {
        if (this.isCurrentlyPressed())
            return Integer.MAX_VALUE;

        return this.lastHold;
    }
}
