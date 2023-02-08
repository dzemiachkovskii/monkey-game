package org.phgdzlk.monkey_game.entities.interactive;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class Creepers {
    Random rand = new Random();
    public static final int creeperSize = 64;
    public LinkedList<Integer> creepers = new LinkedList<>();
    public final BufferedImage image;

    public Creepers() throws IOException {
        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/creeper.png")));
    }

    public void update() {
        int size = creepers.size();
        // generating creepers
        if (size < 5) {
            if (size == 0) {
                creepers.add(600);
            } else {
                assert creepers.peek() != null; // for compilator inner harmony
                int x = creepers.peekLast() + rand.nextInt(600, 800);
                creepers.add(x);
            }
        }
        // moving creepers to the left
        for (var creeper : creepers) {
            creeper -= 5; // will it blande?
        }
        // deleting all creepers after they cross the left border of the screen
        creepers.removeIf(creeper -> creeper < -creeperSize);
    }
}
