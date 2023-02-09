package org.phgdzlk.grippy_ape;

import org.phgdzlk.grippy_ape.entities.decorations.Clouds;
import org.phgdzlk.grippy_ape.entities.decorations.Herbs;
import org.phgdzlk.grippy_ape.entities.hints.MouseHint;
import org.phgdzlk.grippy_ape.entities.interactive.Vines;
import org.phgdzlk.grippy_ape.entities.obstacles.Obstacles;
import org.phgdzlk.grippy_ape.entities.player.Monke;
import org.phgdzlk.grippy_ape.input_handlers.KeyHandler;
import org.phgdzlk.grippy_ape.input_handlers.MouseHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {
    public static final int screenWidth = 1200;
    public static final int screenHeight = 600;
    public static final int FPS = 60;
    public static int gameSpeed = 4;
    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    MouseHandler mouseH = new MouseHandler();
    MouseHint mouseHint = new MouseHint(screenWidth);
    Monke monke = new Monke();
    Clouds clouds = new Clouds();
    Vines vines = new Vines();
    Obstacles obstacles = new Obstacles();
    Herbs herbs = new Herbs(screenWidth);

    public GamePanel() throws IOException {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(0x28AEFA));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);
        this.setFocusable(true);
        // make cursor invisible
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        this.setCursor(blankCursor);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / (double) FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null && !gameThread.isInterrupted()) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        vines.update(gameSpeed, screenHeight);
        obstacles.update(gameSpeed, screenHeight);
        monke.update(gameSpeed, mouseH, vines);
        clouds.update();
        herbs.update(gameSpeed, screenHeight);

        if (monke.isDead()) gameThread.interrupt();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        clouds.draw(g2);
        vines.draw(g2, screenHeight);
        monke.draw(g2);
        obstacles.draw(g2);
        herbs.draw(g2);
        mouseHint.draw(g2, mouseH);

        g2.dispose();
    }
}
