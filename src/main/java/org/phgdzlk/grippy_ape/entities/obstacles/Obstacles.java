package org.phgdzlk.grippy_ape.entities.obstacles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class Obstacles {
    public static final int size = 30;
    LinkedList<Point> coordinates = new LinkedList<>();
    public final BufferedImage image;

    public Obstacles() throws IOException {
        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/cigarette.png")));
    }
}
