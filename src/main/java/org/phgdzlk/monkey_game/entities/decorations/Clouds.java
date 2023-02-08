package org.phgdzlk.monkey_game.entities.decorations;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class Clouds {
    Random rand = new Random();
    public static final int cloudHeight = 16, cloudWidth = 29;
    public LinkedList<Point> coordinates = new LinkedList<>();
    public final BufferedImage image;

    public Clouds() throws IOException {
        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/cloud.png")));
    }

    public void update() {
        int size = coordinates.size();
        if (size < 5) {
            int y = rand.nextInt(0, 300 - cloudHeight);
            if (size == 0) {
                coordinates.add(new Point(1210, y));
            } else {
                assert coordinates.peek() != null; // for compilator inner harmony
                int x = coordinates.peekLast().x + rand.nextInt(500, 1488);
                coordinates.add(new Point(x, y));
            }
        } else {
            assert coordinates.peek() != null; // for compilator inner harmony
            if (coordinates.peek().x < -cloudWidth) {
                coordinates.removeFirst();
            }
        }
    }
}
