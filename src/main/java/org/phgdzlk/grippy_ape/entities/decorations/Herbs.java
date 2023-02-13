package org.phgdzlk.grippy_ape.entities.decorations;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class Herbs {
    private final Random rand = new Random();
    private final Image[] images;
    private final LinkedList<HerbUnit> units;
    private final int amountOfHerbs;
    private int count = 0;

    public Herbs(int screenWidth) {
        images = new Image[4];
        BufferedImage[] temps = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            temps[i] = new BufferedImage(HerbUnit.width, HerbUnit.height, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            temps[0] = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/jungle_plant_1.png")));
            temps[1] = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/palm_tree_1.png")));
            temps[2] = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/jungle_plant_2.png")));
            temps[3] = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/palm_tree_2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.arraycopy(temps, 0, images, 0, 4);
        units = new LinkedList<>();
        amountOfHerbs = (screenWidth / HerbUnit.width + 3) << 2;
    }

    public void update(int gameSpeed, int screenHeight) {
        generate(screenHeight);
        // move herbs to the left
        units.forEach(herb -> herb.pos().setLocation(herb.pos().x - (gameSpeed + 1), herb.pos().y));
        // deleting all herbs after they cross left border of the screen
        units.removeIf(herb -> herb.pos().x < -HerbUnit.width);
    }

    public void draw(Graphics2D g2) {
        units.forEach(herb -> {
            // Image img = herb.image();
            // why the ConcurrentModificationException...
            g2.drawImage(herb.image(), herb.pos().getLocation().x, herb.pos().getLocation().y, HerbUnit.width, HerbUnit.height, null);
        });
    }

    public void generate(int screenHeight) {
        int size = units.size();
        if (size < amountOfHerbs) {
            int herbY = screenHeight - HerbUnit.height + rand.nextInt(30);
            int palmTreeY = -rand.nextInt(30);
            if (size == 0) {
                // adding herb
                units.add(new HerbUnit(images[count++], new Point(0, herbY)));
                // adding palm tree
                units.add(new HerbUnit(images[count++], new Point(0, palmTreeY)));
            } else {
                assert units.peekLast() != null; // for compilator inner harmony
                int x = units.peekLast().pos().x + (HerbUnit.width >> 1);
                // adding herb
                units.add(new HerbUnit(images[count++], new Point(x, herbY)));
                // adding palm tree
                units.add(new HerbUnit(images[count++], new Point(x, palmTreeY)));
            }
            if (count > 3) count = 0;
        }
    }
}
