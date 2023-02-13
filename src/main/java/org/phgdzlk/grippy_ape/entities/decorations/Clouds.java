package org.phgdzlk.grippy_ape.entities.decorations;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class Clouds {
    public static final int width = 260, height = 145;
    private final Random rand = new Random();
    private final BufferedImage image;
    private final LinkedList<Point> units;


    public Clouds() throws IOException {
        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/cloud.png")));
        units = new LinkedList<>();
    }

    public void update() {
        generate();
        // moving clouds to the left
        units.forEach(cloud -> cloud.setLocation(cloud.x - (3 - (cloud.y >> 6)), cloud.y));
        // deleting all clouds after they cross the left border of the screen
        units.removeIf(cloud -> cloud.x < -width);
    }

    public void draw(Graphics2D g2) {
        units.forEach(cloud -> {
            // why the ConcurrentModificationException...
            g2.drawImage(image, cloud.getLocation().x, cloud.getLocation().y, width, height, null);
        });
    }

    public void generate() {
        int size = units.size();
        if (size < 5) {
            int y = rand.nextInt(0, 150);
            if (size == 0) {
                units.add(new Point(1210, y));
            } else {
                assert units.peek() != null; // for compilator inner harmony
                int x = units.peekLast().x + rand.nextInt(500, 1200);
                units.add(new Point(x, y));
            }
        }
    }
}
