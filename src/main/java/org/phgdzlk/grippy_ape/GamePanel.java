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
import java.util.concurrent.TimeUnit;

public class GamePanel extends JPanel implements Runnable {
    public static final int screenWidth = 1200;
    public static final int screenHeight = 600;
    public static final int timerX = screenWidth >> 4;
    public static final int timerY = screenHeight >> 4;
    public static final int FPS = 70;
    public static GameState gameState = GameState.MENU;
    public static MenuChoice menuChoice = MenuChoice.EASY;
    public static int gameSpeed;
    public static long timer = 0;
    public static long secondsPassed;
    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    MouseHandler mouseH = new MouseHandler();
    MouseHint mouseHint;
    Monke monke;
    Clouds clouds;
    Vines vines;
    Obstacles obstacles;
    Herbs herbs;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(SkyColor.EASY.getColor());
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

        try {
            mouseHint = new MouseHint(screenWidth);
            monke = new Monke();
            clouds = new Clouds();
            vines = new Vines();
            obstacles = new Obstacles();
            herbs = new Herbs(screenWidth);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                secondsPassed = TimeUnit.SECONDS.convert(timer, TimeUnit.NANOSECONDS);
                update();
                repaint();
                delta--;
            }
        }

        // QUITTING
        System.exit(0);
    }

    public void update() {
        switch (gameState) {
            case MENU -> {
                this.setBackground(SkyColor.EASY.getColor());
                if (keyH.enterPressed) {
                    if (menuChoice == MenuChoice.QUIT) {
                        gameThread = null;
                    } else {
                        gameState = GameState.PLAY;
                        keyH.enterPressed = false;
                    }
                }

                // moving cursor between menu options
                switch (menuChoice) {
                    case EASY -> {
                        if (keyH.downPressed) {
                            menuChoice = MenuChoice.MEDIUM;
                            keyH.downPressed = false;
                        } else if (keyH.upPressed) {
                            menuChoice = MenuChoice.QUIT;
                            keyH.upPressed = false;
                        }
                    }
                    case MEDIUM -> {
                        if (keyH.downPressed) {
                            menuChoice = MenuChoice.HARD;
                            keyH.downPressed = false;
                        } else if (keyH.upPressed) {
                            menuChoice = MenuChoice.EASY;
                            keyH.upPressed = false;
                        }
                    }
                    case HARD -> {
                        if (keyH.downPressed) {
                            menuChoice = MenuChoice.QUIT;
                            keyH.downPressed = false;
                        } else if (keyH.upPressed) {
                            menuChoice = MenuChoice.MEDIUM;
                            keyH.upPressed = false;
                        }
                    }
                    case QUIT -> {
                        if (keyH.downPressed) {
                            menuChoice = MenuChoice.EASY;
                            keyH.downPressed = false;
                        } else if (keyH.upPressed) {
                            menuChoice = MenuChoice.HARD;
                            keyH.upPressed = false;
                        }
                    }
                }
            }
            case PLAY -> {
                gameSpeed = menuChoice.get();
                switch (gameSpeed) {
                    case 3 -> this.setBackground(SkyColor.EASY.getColor());
                    case 4 -> this.setBackground(SkyColor.MEDIUM.getColor());
                    case 6 -> this.setBackground(SkyColor.HARD.getColor());
                }
                if (secondsPassed > 3) {
                    vines.update(gameSpeed, screenHeight);
                    obstacles.update(gameSpeed, screenHeight);
                    monke.update(gameSpeed, mouseH, vines);
                    clouds.update();
                    herbs.update(gameSpeed, screenHeight);
                }
            }
            case GAMEOVER -> {
                this.setBackground(SkyColor.GAMEOVER.getColor());
                gameState = GameState.MENU;
                System.out.println("GAMEOVER");
                // monke fall
                // draw cigarette
            }
        }

        if (monke.isDead(obstacles)) {
            gameState = GameState.GAMEOVER;
            // generating brand new objects bc screw this
            try {
                mouseHint = new MouseHint(screenWidth);
                monke = new Monke();
                clouds = new Clouds();
                vines = new Vines();
                obstacles = new Obstacles();
                herbs = new Herbs(screenWidth);
            } catch (IOException e) {
                e.printStackTrace();
            }
            timer = 0;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        switch (gameState) {
            case MENU -> {
                g2.setFont(new Font("arial", Font.PLAIN, 32));
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(30));

                int x = screenWidth >> 2;
                int y = screenHeight >> 1;
                int shift = screenHeight >> 4;

                g2.drawString("ЛЕГКО", x, y);
                if (menuChoice == MenuChoice.EASY) g2.fillOval((x - 25), (y - 20), 15, 15);
                y += shift;

                g2.drawString("СРЕДНЕ", x, y);
                if (menuChoice == MenuChoice.MEDIUM) g2.fillOval((x - 25), (y - 20), 15, 15);
                y += shift;

                g2.drawString("СЛОЖНО", x, y);
                if (menuChoice == MenuChoice.HARD) g2.fillOval((x - 25), (y - 20), 15, 15);
                y += shift;

                g2.drawString("ВЫХОД", x, y);
                if (menuChoice == MenuChoice.QUIT) g2.fillOval((x - 25), (y - 20), 15, 15);
            }
            case PLAY -> {
                if (secondsPassed <= 3) {
                    g2.drawString(String.format("БРОСАЮ КУРИТЬ ЧЕРЕЗ %d...", 4 - secondsPassed), 10, 10);
                    monke.draw(g2);
                } else {
                    clouds.draw(g2);
                    vines.draw(g2, screenHeight);
                    monke.draw(g2);
                    obstacles.draw(g2);
                    herbs.draw(g2);
                    mouseHint.draw(g2, mouseH);

                    g2.setFont(new Font("arial", Font.BOLD, 42));
                    g2.setColor(Color.RED);
                    g2.drawString(String.format("Не курил %d секунд", secondsPassed - 3), timerX, timerY);
                }
            }
            case GAMEOVER -> {
                g2.setColor(new Color(0x000000));
                g2.setColor(Color.CYAN);
                g2.fillRect(25, 25, screenWidth - 50, screenHeight - 50);
            }
        }

        g2.dispose();
    }
}
