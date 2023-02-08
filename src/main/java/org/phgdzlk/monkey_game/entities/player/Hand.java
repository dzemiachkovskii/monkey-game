package org.phgdzlk.monkey_game.entities.player;

import org.phgdzlk.monkey_game.entities.interactive.Vines;
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
    public final boolean isRight;
    public final HandState handState;
    public static final int handSize = 36;
    public static final int handHalfSize = handSize >> 1;

    public Hand(HandState handState, boolean isRight) throws IOException {
        this.isRight = isRight;
        this.handState = handState;
        openHandImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/open_hand.png")));
        closeHandImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/closed_hand.png")));
    }

    public void update(int gameSpeed, MouseHandler mouseH, Vines vines) {
        if (handState.isClenched(this.isRight)) {
            pos.setLocation(pos.x - gameSpeed, pos.y);
        } else {
            int x = (pos.x + mouseH.coordinates.x) >> 1;
            int y = (pos.y + mouseH.coordinates.y) >> 1;
            pos.setLocation(x, y);
        }
        if (mouseH.isClicked) {
            // check if the hand is over one of the vines
            var vinesX = vines.getVines();
            if (vinesX
                    .stream()
                    .anyMatch(vineX ->
                            // left bound of vine shifted left by half a hand is lower than a hand left bound
                            (vineX - handHalfSize) < pos.x
                                    // right bound of vine shifed right by half a hand is greater than a hand right bound
                                    && vineX + Vines.vineSize + handHalfSize > pos.x + handSize)) {
                handState.switchHands();
                mouseH.isClicked = false;
            }
        }
    }

    public void draw(Graphics2D g2, Point head, int headHalfSize) {
        g2.setStroke(new BasicStroke(10));
        g2.drawLine((pos.x + handHalfSize), (pos.y + handHalfSize), (head.x + headHalfSize), (head.y + headHalfSize));
        g2.drawImage(getImage(), pos.x, pos.y, null);
    }

    private Image getImage() {
        return handState.isClenched(this.isRight) ? closeHandImage : openHandImage;
    }

    public int getX() {
        return pos.x;
    }

    public int getY() {
        return pos.y;
    }
}
