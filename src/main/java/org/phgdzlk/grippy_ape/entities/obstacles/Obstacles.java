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
    public static final int width = 72, height = 72;
    private final Random rand = new Random();
    private final Image image;
    private final LinkedList<Point> units;

    public Obstacles() {
        BufferedImage temp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        try {
            temp = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/cigarette.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        image = temp;
        units = new LinkedList<>();
    }

    public void update(int gameSpeed, int screenHeight) {
        int size = units.size();
        // generating obstacles
        if (size < 10) {
            int y = rand.nextInt(10, screenHeight - height - 10);
            if (size == 0) {
                units.add(new Point(1210, y));
            } else {
                assert units.peek() != null; // for compilator inner harmony
                int x = units.peekLast().x + rand.nextInt(100, 300);
                units.add(new Point(x, y));
            }
        }
        // moving obstacles to the left
        units.forEach(obstacle -> obstacle.setLocation((obstacle.x - (gameSpeed - 1)), obstacle.y));
        // deleting all clouds after they cross the left border of the screen
        units.removeIf(obstacle -> obstacle.x < -width);
    }

    public void draw(Graphics2D g2) {
        units.forEach(obstacle -> g2.drawImage(image, obstacle.getLocation().x, obstacle.getLocation().y, width, height, null));
    }

    public ArrayList<Polygon> getObstacles() {
        ArrayList<Polygon> obstaclePolygons = new ArrayList<>();
        units.forEach(obstacle -> {
            var x = new int[]{obstacle.x, obstacle.x + 6, obstacle.x + 71, obstacle.x + 65};
            var y = new int[]{obstacle.y + 6, obstacle.y, obstacle.y + 65, obstacle.y + 71};
            obstaclePolygons.add(new Polygon(x, y, 4));
        });
        return obstaclePolygons;
    }
}
