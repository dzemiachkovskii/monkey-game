package org.phgdzlk.monkey_game.entities.player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Monke {
    private int previousBodyX, previousBodyY;
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

    public int getBodyX() {
        return ((hands[0].getX() + hands[1].getX()) >> 1) - headHalfSize;
    }

    public int getBodyY() {
        return ((hands[0].getY() + hands[1].getY() + 100) >> 1) - headHalfSize;
    }

    public boolean isCrashed() {
        if (this.getBodyX() < 0 || this.getBodyY() > 1200 - Hand.handSize || this.getBodyY() < 0 || this.getBodyY() > 600 - Hand.handSize) {
            return true;
        }
        for (Hand hand : hands) {
            if (hand.getX() < 2 || hand.getY() < 2 || hand.getX() > 1148 || hand.getY() > 548) {
                return true;
            }
        }
        return false;
    }
}

