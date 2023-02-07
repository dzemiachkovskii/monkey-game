package org.phgdzlk.monkey_game.entities.decorations;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class Clouds {
    LinkedList<Point> coordinates = new LinkedList<>();
    public final BufferedImage image;

    public Clouds() throws IOException {
        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/cloud.png")));
    }
}
