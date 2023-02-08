package org.phgdzlk.monkey_game.entities.player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Monke {
    private int x, y;
    public final BufferedImage headImage;
    public static final int headSize = 60;
    public static final int headHalfSize = headSize / 2;
    public Hand[] hands = new Hand[2];

    public Monke() throws IOException {
        hands[0] = new Hand();
        hands[0].isClenched = true;
        hands[1] = new Hand();
        headImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/head.png")));
    }

    public Point getCoordinates() {
        Point[] hands = this.getHandCoordinates();
        int x = ((hands[0].x + hands[1].x) >> 1) - headHalfSize;
        int y = ((hands[0].y + hands[1].y + 100) >> 1) - headHalfSize;
        this.x = (this.x + x) >> 1;
        this.y = (this.y + y) >> 1;
        return new Point(this.x, this.y);
    }

    public Point[] getHandCoordinates() {
        Point[] hands = new Point[this.hands.length];
        for (int i = 0; i < hands.length; i++) {
            hands[i] = this.hands[i].getCoordinates();
        }
        return hands;
    }

    public boolean isCrashed() {
        Point head = this.getCoordinates();
        if (head.x < 0 || head.y < 0 || head.x > 1200 - Hand.handSize || head.y > 600 - Hand.handSize) {
            return true;
        }
        for (Hand hand : hands) {
            Point handCrds = hand.getCoordinates();
            if (handCrds.x < 2 || handCrds.y < 2 || handCrds.x > 1148 || handCrds.y > 548) {
                return true;
            }
        }
        return false;
    }
}
