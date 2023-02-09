package org.phgdzlk.grippy_ape.entities.decorations;

import java.awt.*;
import java.awt.image.BufferedImage;

public record HerbUnit(BufferedImage image, Point pos) {
    public static final int width = 165, height = 105;
}
