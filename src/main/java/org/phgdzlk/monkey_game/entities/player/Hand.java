package org.phgdzlk.monkey_game.entities.player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Hand {
    private final BufferedImage openHandImage;
    private final BufferedImage closeHandImage;
    public static final int handSize = 36;
    public static final int handHalfSize = handSize / 2;
    public Point coordinates = new Point(600, 300);
    public boolean isClenched = false;

    public Hand() throws IOException {
        openHandImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/open_hand.png")));
        closeHandImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/closed_hand.png")));
    }

    public BufferedImage getImage() {
        return isClenched ? closeHandImage : openHandImage;
    }

    public Point getCoordinates() {
        return new Point(coordinates.x - handHalfSize, coordinates.y - handHalfSize);
    }

    public void switchClenchState() {
        isClenched = !isClenched;
    }

    public void approach(Point mouse) {
        int x = (coordinates.x + mouse.x) >> 1;
        int y = (coordinates.y + mouse.y) >> 1;
        coordinates.setLocation(x, y);
    }
}
