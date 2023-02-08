package org.phgdzlk.monkey_game;

import org.phgdzlk.monkey_game.entities.decorations.Clouds;
import org.phgdzlk.monkey_game.entities.player.Hand;
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
        for (Hand hand : monke.hands) {
            if (hand.isClenched) {
                hand.coordinates.setLocation(hand.coordinates.x - gameSpeed, hand.coordinates.y);
            } else {
                hand.approach(mouseH.coordinates);
            }
        }
        if (mouseH.isClicked) {
            for (var hand : monke.hands) {
                hand.switchClenchState();
            }
            mouseH.isClicked = false;
        }
        clouds.update();
        for (Point cloud : clouds.coordinates) {
            cloud.setLocation(cloud.x - gameSpeed - 1, cloud.y);
        }
        if (monke.isCrashed()) gameThread.interrupt();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        drawClouds(g2);
        drawMonke(g2);
        drawHerbs(g2);

        g2.dispose();
    }

    private void drawHerbs(Graphics2D g2) {

    }

    private void drawClouds(Graphics2D g2) {
        for (Point cloud : clouds.coordinates) {
            g2.drawImage(clouds.image, cloud.x, cloud.y, (Clouds.cloudWidth * 3), (Clouds.cloudHeight * 3), null);
        }
    }

    public void drawMonke(Graphics2D g2) {
        Point head = monke.getCoordinates();
        // arms
        for (var hand : monke.hands) {
            Point handCrd = hand.getCoordinates();
            g2.setStroke(new BasicStroke(15));
            g2.drawLine(handCrd.x + Hand.handHalfSize, handCrd.y + Hand.handHalfSize, (head.x + Monke.headHalfSize), (head.y + Monke.headHalfSize));
            g2.drawImage(hand.getImage(), handCrd.x, handCrd.y, null);
        }
        // body
        g2.drawImage(monke.headImage, head.x, head.y, null);
    }
}
