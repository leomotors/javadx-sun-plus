package constant;

import javafx.scene.paint.Color;

public final class DXColor {
    private DXColor() {}

    private static Color fromRGB(int r, int g, int b) {
        return new Color(r * 1.0 / 0xff, g * 1.0 / 0xff, b * 1.0 / 0xff, 1);
    }

    // #d8e93e
    public static Color CRITICAL_PERFECT = DXColor.fromRGB(0xd8, 0xe9, 0x3e);

    // #c57c32
    public static Color PERFECT = DXColor.fromRGB(0xc5, 0x7c, 0x32);

    // #29c4d8
    public static Color GOOD = DXColor.fromRGB(0x29, 0xc4, 0xd8);

    // #b4b9b9
    public static Color MISS = DXColor.fromRGB(0xb4, 0xb9, 0xb9);
}
