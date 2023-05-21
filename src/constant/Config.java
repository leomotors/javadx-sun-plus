package constant;

import javafx.scene.input.KeyCode;

/**
 * A static class containing constants related to game configuration.
 */
public class Config {
    private Config() {}

    public static final int SCREEN_HEIGHT = 1600;
    public static final int SCREEN_WIDTH = 900;

    public static final String UI_FONT = "Helvetica";

    public static final int N_LANES = 12;
    public static final int HOLD_TICK = 250;
    public static final int TAP_COOLDOWN = 20;
    public static final int FLICK_GAP = 50;

    public static int getLaneFromKey(KeyCode keyCode) {
        switch (keyCode) {
            case Q:
                return 1;
            case A:
                return 13;
            case W:
                return 2;
            case S:
                return 14;
            case E:
                return 3;
            case D:
                return 15;
            case R:
                return 4;
            case F:
                return 16;
            case T:
                return 5;
            case G:
                return 17;
            case Y:
                return 6;
            case H:
                return 18;
            case U:
                return 7;
            case J:
                return 19;
            case I:
                return 8;
            case K:
                return 20;
            case O:
                return 9;
            case L:
                return 21;
            case P:
                return 10;
            case SEMICOLON:
                return 22;
            case OPEN_BRACKET:
                return 11;
            case QUOTE:
                return 23;
            case CLOSE_BRACKET:
                return 12;
            case ENTER:
                return 24;
            default:
                return 0;
        }
    }

    // TODO Put this in settings
    public static final String TOP_LEFT_TEXT = "SCORE";
    public static final int TOP_RIGHT_GOAL = 000_000;
    public static final int NOTE_SHOW_TIME = 4500;

    // Unused (?), to be removed
    public static final int SUBTILE_PER_TILE = 50;
    public static final int TILE_HEIGHT = SUBTILE_PER_TILE * 10;
    public static final double TILE_SECTION_BORDER_WIDTH = 0.1;
    public static final int PRESS_KEY_HEIGHT = 25;
    public static final int MENU_HEIGHT = 25;

    public static final int TILE_TIME_RANGE = 2000;
    public static final int TIME_RANGE_PER_TILE = TILE_TIME_RANGE
            / SUBTILE_PER_TILE;
    public static final int DELAY_BETWEEN_FRAME = 5;
    public static final int HIT_KEY_PRESS_DELAY = 100;
    public static final int MISS_BEFORE_HIT_KEY_PRESS_DELAY = 50;
    public static final int LANE_COUNT = 12;
    public static final int LANE_BOTTOM_WIDTH = 75;
    public static final int LANE_TOP_WIDTH = 30;
    public static final int LANE_HEIGHT = 600;
}
