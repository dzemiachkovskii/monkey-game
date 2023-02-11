package org.phgdzlk.grippy_ape;

import java.awt.*;

public enum SkyColor {
    EASY(0x28AEFA),
    MEDIUM(0x2879FA),
    HARD(0x1C3AB0),
    GAMEOVER(0x011338);
    private final Color color;

    SkyColor(int colorCode) {
        color = new Color(colorCode);
    }

    public Color getColor() {
        return color;
    }
}
