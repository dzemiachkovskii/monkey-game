package org.phgdzlk.grippy_ape.entities.player;

import org.phgdzlk.grippy_ape.entities.interactive.Vines;
import org.phgdzlk.grippy_ape.input_handlers.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Hand {
    public static final Color armColor = new Color(86, 48, 0);
    public static final int width = 36, height = 36; // TODO add to pos Rectangle
    public static final int halfWidth = width >> 1;
    private final BufferedImage openHandImage;
    private final BufferedImage closeHandImage;
    private final Point pos; // TODO replace point with Rectangle class
    private final HandState handState;
    private final boolean isRight;

    public Hand(HandState handState, boolean isRight) throws IOException {
        this.isRight = isRight;
        this.handState = handState;
        openHandImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/open_hand.png")));
        closeHandImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/closed_hand.png")));
        pos = new Point(600, 300);
    }

    public void update(int gameSpeed, MouseHandler mouseH, Vines vines) {
        if (handState.isClenched(this.isRight)) {
            pos.setLocation(pos.x - gameSpeed, pos.y);
        } else {
            int x = (pos.x + mouseH.coordinates.x) >> 1;
            int y = (pos.y + mouseH.coordinates.y) >> 1;
            pos.setLocation(x, y);

            if (mouseH.isClicked) {
                // TODO make new collision check with usage of Rectangle class
                // check if the hand is over one of the vines
                if (vines.getVines().stream().anyMatch(vineX ->
                        // left bound of vine shifted left by half a hand is lower than a hand left bound
                        (vineX - halfWidth) < pos.x
                                &&
                                // right bound of vine shifed right by half a hand is greater than a hand right bound
                                vineX + Vines.width + halfWidth > pos.x + width)) {
                    handState.switchHands();
                    mouseH.switchButton();
                }
                mouseH.isClicked = false;
            }
        }
    }

    public void draw(Graphics2D g2, Point head, int headHalfSize) {
        // draw arms
        g2.setStroke(new BasicStroke(10));
        g2.setColor(armColor);
        g2.drawLine((pos.x + halfWidth), (pos.y + halfWidth), (head.x + headHalfSize), (head.y + headHalfSize));
        // draw hands
        g2.drawImage(getImage(), pos.x, pos.y, width, height, null);
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
