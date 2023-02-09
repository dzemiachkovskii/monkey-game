package org.phgdzlk.grippy_ape.entities.decorations;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class Herbs {
    private final Random rand = new Random();
    private final BufferedImage image;
    private final LinkedList<Point> units;
    public static final int width = 42, height = 42;

    public Herbs() throws IOException {
        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/herb.png")));
        units = new LinkedList<>();
    }
}
