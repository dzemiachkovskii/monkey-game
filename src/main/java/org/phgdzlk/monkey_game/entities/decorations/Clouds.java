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
    public static final int cloudHeight = 145, cloudWidth = 260;
    public LinkedList<Point> clouds = new LinkedList<>();
    public final BufferedImage image;

    public Clouds() throws IOException {
        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/cloud.png")));
    }

    public void update() {
        int size = clouds.size();
        // generating clouds
        if (size < 5) {
            int y = rand.nextInt(0, 200 - cloudHeight);
            if (size == 0) {
                clouds.add(new Point(1210, y));
            } else {
                assert clouds.peek() != null; // for compilator inner harmony
                int x = clouds.peekLast().x + rand.nextInt(500, 1200);
                clouds.add(new Point(x, y));
            }
        }
        // moving clouds to the left
        for (var cloud : clouds) {
            cloud.setLocation(cloud.x - ((300 - cloud.y) >> 5), cloud.y);
        }
        // deleting all clouds after they cross the left border of the screen
        clouds.removeIf(cloud -> cloud.x < -cloudWidth);
    }

    public void draw(Graphics2D g2) {
        for (var cloud : clouds) {
            g2.drawImage(image, cloud.x, cloud.y, Clouds.cloudWidth, Clouds.cloudHeight, null);
        }
    }
}
