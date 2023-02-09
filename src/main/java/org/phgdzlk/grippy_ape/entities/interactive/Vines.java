package org.phgdzlk.grippy_ape.entities.interactive;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class Vines {
    public static final int width = 64, height = 64;
    private final Random rand = new Random();
    private final BufferedImage image;
    private final LinkedList<Point> units;

    public Vines() throws IOException {
        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/vine.png")));
        units = new LinkedList<>();
    }

    public void update(int gameSpeed, int screenHeight) {
        int size = units.size();
        // generating vines
        if (size < 5) {
            if (size == 0) {
                units.add(new Point(600, screenHeight));
            } else {
                assert units.peek() != null; // for compilator inner harmony
                int x = units.peekLast().x + rand.nextInt(250, 350);
                units.add(new Point(x, screenHeight));
            }
        }
        // moving vines to the left
        units.forEach(vine -> vine.setLocation((vine.x - gameSpeed), vine.y));
        // deleting all vines after they cross the left border of the screen
        units.removeIf(vine -> vine.x < -width);
    }

    public void draw(Graphics2D g2, int screenHeight) {
        for (var vine : units) {
            for (int y = 0; y < screenHeight; y += 64) {
                g2.drawImage(image, vine.x, y, width, height, null);
            }
        }
    }

    public ArrayList<Integer> getVines() {
        var vineList = new ArrayList<Integer>();
        units.forEach(vine -> vineList.add(vine.x));
        return vineList;
    }
}
