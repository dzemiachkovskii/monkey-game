package org.phgdzlk.monkey_game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {
    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    MouseHandler mouseH = new MouseHandler();
    public static final int screenWidth = 1200;
    public static final int screenHeight = 600;
    public static final int FPS = 60;
    public static final int handWidth = 36;
    public static final int handHeight = 30;
    public static final int headSize = 60;
    public static int gameSpeed = 5;
    Monke monke = new Monke();
    BufferedImage openHand;
    BufferedImage closedHand;
    BufferedImage head;

    public GamePanel() throws IOException {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(0x00cccc));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);
        this.setFocusable(true);

        openHand = ImageIO.read(getClass().getClassLoader().getResource("images/open_hand.png"));
        closedHand = ImageIO.read(getClass().getClassLoader().getResource("images/closed_hand.png"));
        head = ImageIO.read(getClass().getClassLoader().getResource("images/head.png"));
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
        for (Hand hand : monke.hands) {
            if (hand.isClenched) {
                hand.x -= gameSpeed;
            } else {
                hand.x = mouseH.mouseX;
                hand.y = mouseH.mouseY;
            }
        }
        if (mouseH.isClicked) {
            monke.hands[0].isClenched = !monke.hands[0].isClenched;
            monke.hands[1].isClenched = !monke.hands[1].isClenched;
            mouseH.isClicked = false;
        }
        monke.checkDeath();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        paintMonke(g);

        g2.dispose();
        g.dispose();
    }

    public void paintMonke(Graphics g) {
        BufferedImage img;
        // right hand
        img = monke.hands[0].isClenched ? closedHand : openHand;
        g.drawImage(img, monke.hands[0].x, monke.hands[0].y, null);
        // left hand
        img = monke.hands[1].isClenched ? closedHand : openHand;
        g.drawImage(img, monke.hands[1].x, monke.hands[1].y, null);
        // body
        g.drawImage(head, monke.getBodyX(), monke.getBodyY(), null);
    }
}
