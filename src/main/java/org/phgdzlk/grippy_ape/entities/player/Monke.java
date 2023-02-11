package org.phgdzlk.grippy_ape.entities.player;

import org.phgdzlk.grippy_ape.entities.interactive.Vines;
import org.phgdzlk.grippy_ape.entities.obstacles.Obstacles;
import org.phgdzlk.grippy_ape.input_handlers.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Monke {
    public static final int width = 60, height = 60;
    public static final int halfWidth = width >> 1;
    public static final int halfHeight = height >> 1;
    private final BufferedImage image;
    private final BufferedImage cigarette;
    private final Point center;
    private final Rectangle hitBox;
    private final ArrayList<Hand> hands;

    public Monke() throws IOException {
        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/head.png")));
        cigarette = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/lighted_cigarette.png")));
        var handState = new HandState();
        hands = new ArrayList<>(2);
        hands.add(new Hand(handState, true));
        hands.add(new Hand(handState, false));
        center = new Point(630, 300);
        hitBox = new Rectangle(center.x - halfWidth, center.y - halfHeight, width, height);
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
        int xAverage = (handAverage.x + center.x) >> 1;
        int yAverage = (handAverage.y + center.y) >> 1;
        xAverage = (xAverage + center.x) >> 1;
        yAverage = (yAverage + center.y) >> 1;
        xAverage = (xAverage + center.x) >> 1;
        yAverage = (yAverage + center.y) >> 1;
        center.setLocation(xAverage, yAverage);
        hitBox.setLocation(center.x - halfWidth, center.y - halfHeight);
    }

    public void updateGameOver(int screenHeight) {
        // if monke hasn't reached the floor yet, move it down
        if (hitBox.y + height + halfHeight < screenHeight - hitBox.height) {
            center.setLocation(center.x, center.y + 5);
        }
        hands.forEach(hand -> hand.updateGameOver(new Point(hitBox.x + hitBox.width, hitBox.y + hitBox.height)));
        hitBox.setLocation(center.x - halfWidth, center.y - halfHeight);
    }

    public void draw(Graphics2D g2) {
        hands.forEach(hand -> hand.draw(g2, new Point(hitBox.x, hitBox.y), halfWidth));
        g2.drawImage(image, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);
    }

    public void drawGameOver(Graphics2D g2) {
        hands.forEach(hand -> hand.draw(g2, new Point(hitBox.x, hitBox.y), halfHeight));
        g2.drawImage(image, hitBox.x, hitBox.y, (hitBox.width << 1), (hitBox.height << 1), null);
        g2.drawImage(cigarette, (hitBox.x + hitBox.width + 10), (hitBox.y + (halfHeight * 3) + 5), width, height, null);
    }

    public boolean isDead(Obstacles o) {
        return (isSlidToTheLeft() || isTouchedCigarette(o));
    }

    private boolean isSlidToTheLeft() {
        return (center.x < 0 || hands.stream().anyMatch(hand -> hand.getX() < 0));
    }

    private boolean isTouchedCigarette(Obstacles o) {
        return o.getObstacles().stream().anyMatch(obstacle -> obstacle.intersects(hitBox)) || hands.stream().anyMatch(hand -> hand.intersetcsWith(o));
    }
}
