package org.phgdzlk.monkey_game.entities.player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Hand {
    public final BufferedImage openHandImage;
    public final BufferedImage closeHandImage;
    public static final int handSize = 36;
    public static final int handHalfSize = handSize / 2;
    public int x = 600;
    public int y = 300;
    public boolean isClenched = false;

    public Hand() throws IOException {
        openHandImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/open_hand.png")));
        closeHandImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/closed_hand.png")));
    }

    public BufferedImage getImage() {
        if (isClenched) {
            return closeHandImage;
        } else {
            return openHandImage;
        }
    }

    public int getX() {
        return x - handHalfSize;
    }

    public int getY() {
        return y - handHalfSize;
    }

    public void switchClenchState() {
        isClenched = !isClenched;
    }
}
