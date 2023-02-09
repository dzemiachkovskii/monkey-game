package org.phgdzlk.grippy_ape.entities.player;

import org.phgdzlk.grippy_ape.entities.interactive.Vines;
import org.phgdzlk.grippy_ape.input_handlers.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Monke {
    private final BufferedImage image;
    private final Point pos;
    private final ArrayList<Hand> hands;
    public static final int width = 60, height = 60;
    public static final int halfWidth = width >> 1;

    public Monke() throws IOException {
        var handState = new HandState();
        hands = new ArrayList<>(2);
        hands.add(new Hand(handState, true));
        hands.add(new Hand(handState, false));
        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/head.png")));
        pos = new Point(600, 300);
    }

    public void update(int gameSpeed, MouseHandler mouseH, Vines vines) {
        Point handAverage = new Point();
        for (Hand hand : hands) {
            hand.update(gameSpeed, mouseH, vines);
            // get sum of hands positions
            int xSum = handAverage.x + hand.getX();
            int ySum = handAverage.y + hand.getY();
            handAverage.setLocation(xSum, ySum);
        }
        // get average of hands positions
        handAverage.setLocation((handAverage.x >> 1), (handAverage.y >> 1));
        int xAverage = (handAverage.x + pos.x) >> 1;
        int yAverage = (handAverage.y + pos.y) >> 1;
        xAverage = (xAverage + pos.x) >> 1;
        yAverage = (yAverage + pos.y) >> 1;
        xAverage = (xAverage + pos.x) >> 1;
        yAverage = (yAverage + pos.y) >> 1;
        pos.setLocation(xAverage, yAverage);
    }

    public void draw(Graphics2D g2) {
        hands.forEach(hand ->
                hand.draw(g2, pos, halfWidth));
        g2.drawImage(image, pos.x, pos.y, width, height, null);
    }

    public boolean isDead() {
        return (isSlidToTheLeft() || isTouchedCigarette());
    }

    private boolean isSlidToTheLeft() {
        return (pos.x < 0 || hands.stream().anyMatch(hand -> hand.getX() < 0));
    }

    private boolean isTouchedCigarette() {
        return false;
    }
}
