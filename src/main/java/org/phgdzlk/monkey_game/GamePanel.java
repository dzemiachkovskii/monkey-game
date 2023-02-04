package org.phgdzlk.monkey_game;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    public static final int screenWidth = 1200;
    public static final int screenHeight = 600;
    public static final int FPS = 60;
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    public static int x = 100;
    public static int y = 100;
    public static int speed = 5;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
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

        while (gameThread != null) {
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
        if (keyH.upPressed && canMove(Direction.UP)) y -= speed;
        if (keyH.rightPressed && canMove(Direction.RIGHT)) x += speed;
        if (keyH.downPressed && canMove(Direction.DOWN)) y += speed;
        if (keyH.leftPressed && canMove(Direction.LEFT)) x -= speed;
    }

    private boolean canMove(Direction d) {
        boolean yesItCan = false;
        switch (d) {
            case UP -> yesItCan = y - speed > 10;
            case RIGHT -> yesItCan = x + speed < screenWidth - 60;
            case DOWN -> yesItCan = y + speed < screenHeight - 60;
            case LEFT -> yesItCan = x - speed > 10;
        }
        return yesItCan;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, 50, 50);

        g2.dispose();
    }
}
