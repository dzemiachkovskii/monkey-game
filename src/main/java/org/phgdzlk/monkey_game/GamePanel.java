package org.phgdzlk.monkey_game;

import org.phgdzlk.monkey_game.entities.decorations.Clouds;
import org.phgdzlk.monkey_game.entities.interactive.Creepers;
import org.phgdzlk.monkey_game.entities.player.Monke;
import org.phgdzlk.monkey_game.input_handlers.KeyHandler;
import org.phgdzlk.monkey_game.input_handlers.MouseHandler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {
    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    MouseHandler mouseH = new MouseHandler();
    public static final int screenWidth = 1200;
    public static final int screenHeight = 600;
    public static final int FPS = 60;
    public static int gameSpeed = 5;
    Monke monke = new Monke();
    Clouds clouds = new Clouds();
    Creepers creepers = new Creepers();

    public GamePanel() throws IOException {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(0x1BCE7E));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);
        this.setFocusable(true);
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
        monke.update(gameSpeed, mouseH);
        clouds.update();
        creepers.update();
        if (monke.isAlive()) gameThread.interrupt();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        clouds.draw(g2);
        monke.draw(g2);
//        creepers, obstacles, herbs;

        g2.dispose();
    }
}
