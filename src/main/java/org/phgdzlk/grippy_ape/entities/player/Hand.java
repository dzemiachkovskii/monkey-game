package org.phgdzlk.grippy_ape.entities.player;

import org.phgdzlk.grippy_ape.entities.interactive.Vines;
import org.phgdzlk.grippy_ape.entities.obstacles.Obstacles;
import org.phgdzlk.grippy_ape.input_handlers.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Hand {
    public static final Color armColor = new Color(86, 48, 0);
    public static final int width = 36, height = 36;
    public static final int halfWidth = width >> 1;
    public static final int halfHeight = height >> 1;
    private final BufferedImage openHandImage;
    private final BufferedImage closeHandImage;
    private final Point center;
    private final Rectangle hitBox;
    private final HandState handState;
    private final boolean isRight;

    public Hand(HandState handState, boolean isRight) throws IOException {
        int y = isRight ? 400 : 200;
        center = new Point(630, y);
        openHandImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/open_hand.png")));
        closeHandImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/closed_hand.png")));
        this.handState = handState;
        this.isRight = isRight;
        hitBox = new Rectangle(center.x - halfWidth, center.y - halfHeight, width, height);
    }

    public void update(int gameSpeed, MouseHandler mouseH, Vines vines) {
        if (handState.isClenched(this.isRight)) {
            // move clenched arm to the left by gameSpeed value
            center.setLocation(center.x - gameSpeed, center.y);
        } else {
            // find average between current hand position & mouse position
            int x = (center.x + mouseH.coordinates.x) >> 1;
            int y = (center.y + mouseH.coordinates.y) >> 1;
            center.setLocation(x, y);

            if (mouseH.isClicked) {
                // check if the hand is over one of the vines
                if (vines.getVineHitboxes().stream().anyMatch(vine ->
                        // left bound of vine is lower than hand left bound
                        vine.x < hitBox.x &&
                                // right bound of vine is higher than hand right bound
                                vine.x + vine.width > hitBox.x + hitBox.width)) {
                    handState.switchHands();
                    mouseH.switchButton();
                }
                mouseH.isClicked = false;
            }
        }
        hitBox.setLocation(center.x - halfWidth, center.y - halfHeight);
    }

    public void draw(Graphics2D g2, Point head, int headHalfSize) {
        // draw arms // TODO arms made of objects with gravity that will hang like ropes
        int headX = head.x + headHalfSize;
        int headY = head.y + headHalfSize;
        int x1 = (center.x + headX) >> 1;
        int y1 = (center.y + headY) >> 1;
        int x2 = (x1 + headX) >> 1;
        int y2 = (y1 + headY) >> 1;
        g2.setColor(armColor);
        g2.setStroke(new BasicStroke(10));
        g2.drawLine(headX, headY, center.x, center.y);
        g2.setStroke(new BasicStroke(15));
        g2.drawLine(headX, headY, x1, y1);
        g2.setStroke(new BasicStroke(20));
        g2.drawLine(headX, headY, x2, y2);
        // draw hands
        g2.drawImage(getImage(), hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);
    }

    private Image getImage() {
        return handState.isClenched(this.isRight) ? closeHandImage : openHandImage;
    }

    public int getX() {
        return hitBox.x;
    }

    public int getY() {
        return hitBox.y;
    }

    public boolean intersetcsWith(Obstacles o) {
        return o.getObstacles().stream().anyMatch(obstacle -> obstacle.intersects(hitBox));
    }
}
