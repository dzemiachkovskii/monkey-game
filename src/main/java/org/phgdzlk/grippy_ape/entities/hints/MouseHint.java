package org.phgdzlk.grippy_ape.entities.hints;

import org.phgdzlk.grippy_ape.input_handlers.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MouseHint {
    private final Image left_click;
    private final Image right_click;
    private final int x, width, height;

    public MouseHint(int screenWidth) {
        BufferedImage temp_lc = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                temp_rc = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        try {
            temp_lc = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/mouse_hint_left.png")));
            temp_rc = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/mouse_hint_right.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        left_click = temp_lc;
        right_click = temp_rc;
        width = left_click.getWidth(null) >> 1;
        height = left_click.getHeight(null) >> 1;
        x = screenWidth - width - 10;
    }

    public void draw(Graphics2D g2, MouseHandler mouseH) {
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x - 5, 5, width + 10, height + 10, 20, 20);
        g2.drawImage(mouseH.leftButton ? left_click : right_click, x, 10, width, height, null);
    }
}
// probably I should move this class to some other package...
