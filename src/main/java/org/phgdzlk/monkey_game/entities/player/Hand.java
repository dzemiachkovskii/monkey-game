package org.phgdzlk.monkey_game.entities.player;

import org.phgdzlk.monkey_game.input_handlers.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Hand {
    private final BufferedImage openHandImage;
    private final BufferedImage closeHandImage;
    private final Point pos = new Point(600, 300);
    public boolean isClenched = false;
    public static final int handSize = 36;
    public static final int handHalfSize = handSize >> 1;

    public Hand() throws IOException {
        openHandImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/open_hand.png")));
        closeHandImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/closed_hand.png")));
    }

    public void update(int gameSpeed, MouseHandler mouseH) {
        if (isClenched) {
            pos.setLocation(pos.x - gameSpeed, pos.y);
        } else {
            int x = (pos.x + mouseH.coordinates.x) >> 1;
            int y = (pos.y + mouseH.coordinates.y) >> 1;
            pos.setLocation(x, y);
        }
        if (mouseH.isClicked) {
            isClenched = !isClenched;
        }
    }

    public void draw(Graphics2D g2, Point head, int headHalfSize) {
        g2.setStroke(new BasicStroke(15));
        g2.drawLine((pos.x + handHalfSize), (pos.y + handHalfSize), (head.x + headHalfSize), (head.y + headHalfSize));
        g2.drawImage(isClenched ? closeHandImage : openHandImage, pos.x, pos.y, null);
    }

    public int getX() {
        return pos.x;
    }

    public int getY() {
        return pos.y;
    }
}
