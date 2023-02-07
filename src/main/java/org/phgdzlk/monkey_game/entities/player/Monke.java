package org.phgdzlk.monkey_game.entities.player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Monke {
    private int previousBodyX, previousBodyY;
    public final BufferedImage headImage;
    public final int headImageSize = 60;
    public Hand[] hands = new Hand[2];

    public Monke() throws IOException {
        hands[0] = new Hand();
        hands[0].isClenched = true;
        hands[1] = new Hand();
        headImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/head.png")));
    }

    public int getBodyX() {
        return (hands[0].x + hands[1].x) >> 1;
    }

    public int getBodyY() {
        return (hands[0].y + hands[1].y + 100) >> 1;
    }

    public boolean isCrashed() {
        if (this.getBodyX() < 2 || this.getBodyY() > 1138 || this.getBodyY() < 2 || this.getBodyY() > 538) {
            return true;
        }
        for (Hand hand : hands) {
            if (hand.x < 2 || hand.y < 2 || hand.x > 1148 || hand.y > 548) {
                return true;
            }
        }
        return false;
    }
}

