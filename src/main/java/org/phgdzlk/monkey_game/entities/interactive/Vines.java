package org.phgdzlk.monkey_game.entities.interactive;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class Vines {
    Random rand = new Random();
    public final BufferedImage image;
    public LinkedList<Point> vines = new LinkedList<>();
    public static final int vineSize = 64;

    public Vines() throws IOException {
        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/vine.png")));
    }

    public void update(int gameSpeed, int screenHeight) {
        int size = vines.size();
        // generating vines
        if (size < 5) {
            if (size == 0) {
                vines.add(new Point(600, screenHeight));
            } else {
                assert vines.peek() != null; // for compilator inner harmony
                int x = vines.peekLast().x + rand.nextInt(200, 400);
                vines.add(new Point(x, screenHeight));
            }
        }
        // moving vines to the left
        for (var vine : vines) {
            vine.setLocation((vine.x - gameSpeed), vine.y);
        }
        // deleting all vines after they cross the left border of the screen
        vines.removeIf(vine -> vine.x < -vineSize);
    }

    public void draw(Graphics2D g2) {
        for (var vine : vines) {
            for (int y = 0; y < vine.y; y += 64) {
                g2.drawImage(image, vine.x, y, vineSize, vineSize, null);
            }
        }
    }

    public ArrayList<Integer> getVines() {
        var vineList = new ArrayList<Integer>();
        for (var vine : vines) {
            vineList.add(vine.x);
        }
        return vineList;
    }
}
