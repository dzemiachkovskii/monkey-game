package org.phgdzlk.grippy_ape.entities.decorations;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class Herbs {
    Random rand = new Random();
    public static final int cloudHeight = 16, cloudWidth = 29;
    public LinkedList<Point> coordinates = new LinkedList<>();
    public final BufferedImage image;

    public Herbs() throws IOException {
        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/herb.png")));
    }
}
