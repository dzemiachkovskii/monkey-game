package org.phgdzlk.monkey_game.entities.hints;

import org.phgdzlk.monkey_game.input_handlers.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MouseHint {
    private final BufferedImage left_click;
    private final BufferedImage right_click;
    private final int x;
    private final int width;
    private final int height;

    public MouseHint(int screenWidth) throws IOException {
        left_click = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/mouse_hint_left.png")));
        right_click = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/mouse_hint_right.png")));
        width = left_click.getWidth() >> 1;
        height = left_click.getHeight() >> 1;
        x = screenWidth - width - 10;
    }

    public void draw(Graphics2D g2, MouseHandler mouseH) {
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x - 5, 5, width + 10, height + 10, 20, 20);
        g2.drawImage(mouseH.leftButton ? left_click : right_click, x, 10, width, height, null);
    }
}
