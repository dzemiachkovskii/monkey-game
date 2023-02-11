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
    private final BufferedImage[] images;
    private final LinkedList<HerbUnit> units;
    private final int amountOfHerbs;
    private int count = 0;

    public Herbs(int screenWidth) throws IOException {
        images = new BufferedImage[4];
        images[0] = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/jungle_plant_1.png")));
        images[1] = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/palm_tree_1.png")));
        images[2] = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/jungle_plant_2.png")));
        images[3] = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/palm_tree_2.png")));
        units = new LinkedList<>();
        amountOfHerbs = (screenWidth / HerbUnit.width + 3) << 2;
    }

    public void update(int gameSpeed, int screenHeight) {
        int size = units.size();
        // generating herbs
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
        // move herbs to the left
        units.forEach(herb -> herb.pos().setLocation(herb.pos().x - (gameSpeed + 1), herb.pos().y));
        // deleting all herbs after they cross left border of the screen
        units.removeIf(herb -> herb.pos().x < -HerbUnit.width);
    }

    public void draw(Graphics2D g2) {
        units.forEach(herb -> g2.drawImage(herb.image(), herb.pos().x, herb.pos().y, HerbUnit.width, HerbUnit.height, null));
    }
}
