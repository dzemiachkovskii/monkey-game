package org.phgdzlk.grippy_ape.entities.obstacles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class Obstacles {
    private final Random rand = new Random();
    private final BufferedImage image;
    private final LinkedList<Point> units;
    public static final int width = 90, height = 90;

    public Obstacles() throws IOException {
        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/cigarette.png")));
        units = new LinkedList<>();
    }

    public void update(int gameSpeed, int screenHeight) {
        int size = units.size();
        // generating obstacles
        if (size < 10) {
            int y = rand.nextInt(20, screenHeight - width - 20);
            if (size == 0) {
                units.add(new Point(1210, y));
            } else {
                assert units.peek() != null; // for compilator inner harmony
                int x = units.peekLast().x + rand.nextInt(100, 300);
                units.add(new Point(x, y));
            }
        }
        // moving obstacles to the left
        units.forEach(obstacle -> obstacle.setLocation((obstacle.x - gameSpeed - 1), obstacle.y));
        // deleting all clouds after they cross the left border of the screen
        units.removeIf(obstacle -> obstacle.x < -width);
    }

    public void draw(Graphics2D g2) {
        units.forEach(obstacle ->
                g2.drawImage(image, obstacle.x, obstacle.y, width, height, null));
    }

    public ArrayList<Point> getObstacles() {
        return new ArrayList<>(units);
    }
}
