package org.phgdzlk.grippy_ape.entities.player;

import org.phgdzlk.grippy_ape.entities.interactive.Vines;
import org.phgdzlk.grippy_ape.input_handlers.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Monke {
    public final BufferedImage image;
    private final Point pos = new Point(600, 300);
    public Hand[] hands = new Hand[2];
    public static final int headSize = 60;
    public static final int headHalfSize = headSize >> 1;

    public Monke() throws IOException {
        var handState = new HandState();
        hands[0] = new Hand(handState, true);
        hands[1] = new Hand(handState, false);
        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/head.png")));
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
        for (var hand : hands) {
            hand.draw(g2, pos, headHalfSize);
            g2.drawImage(image, pos.x, pos.y, null);
        }
    }

    public boolean isDead() {
        return (isSlidToTheLeft() || isTouchedCigarette());
    }

    private boolean isSlidToTheLeft() {
        return (pos.x < 0 || hands[0].getX() < 0 || hands[1].getX() < 0);
    }

    private boolean isTouchedCigarette() {
        return false;
    }
}
