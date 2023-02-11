package org.phgdzlk.grippy_ape;

import org.phgdzlk.grippy_ape.entities.decorations.Clouds;
import org.phgdzlk.grippy_ape.entities.decorations.Herbs;
import org.phgdzlk.grippy_ape.entities.hints.MouseHint;
import org.phgdzlk.grippy_ape.entities.interactive.Vines;
import org.phgdzlk.grippy_ape.entities.obstacles.Obstacles;
import org.phgdzlk.grippy_ape.entities.player.Monke;
import org.phgdzlk.grippy_ape.input_handlers.KeyHandler;
import org.phgdzlk.grippy_ape.input_handlers.MouseHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class GamePanel extends JPanel implements Runnable {
    public static final int screenWidth = 1200;
    public static final int screenHeight = 600;
    public static final int timerX = screenWidth >> 4;
    public static final int timerY = screenHeight >> 4;
    public static final int FPS = 70;
    public static Cursor customCursor;
    public static GameState gameState = GameState.MENU;
    public static MenuChoice menuChoice = MenuChoice.EASY;
    public static int gameSpeed;
    public static long timer;
    public static long secondsPassed;
    public static long secondsSurvived;
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

        // set custom cursor
        try {
            BufferedImage cursorImg = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/custom_cursor.png")));
            customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
            this.setCursor(customCursor);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        initGameThings();

        while (gameThread != null && !gameThread.isInterrupted()) {
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
                if (keyH.isEnterPressed()) {
                    if (menuChoice == MenuChoice.QUIT) {
                        gameThread.interrupt();
                    } else {
                        gameState = GameState.PLAY;
                    }
                }

                // moving cursor between menu options
                switch (menuChoice) {
                    case EASY -> {
                        if (keyH.isDownPressed()) {
                            menuChoice = MenuChoice.MEDIUM;
                        } else if (keyH.isUpPressed()) {
                            menuChoice = MenuChoice.QUIT;
                        }
                    }
                    case MEDIUM -> {
                        if (keyH.isDownPressed()) {
                            menuChoice = MenuChoice.HARD;
                        } else if (keyH.isUpPressed()) {
                            menuChoice = MenuChoice.EASY;
                        }
                    }
                    case HARD -> {
                        if (keyH.isDownPressed()) {
                            menuChoice = MenuChoice.QUIT;
                        } else if (keyH.isUpPressed()) {
                            menuChoice = MenuChoice.MEDIUM;
                        }
                    }
                    case QUIT -> {
                        if (keyH.isDownPressed()) {
                            menuChoice = MenuChoice.EASY;
                        } else if (keyH.isUpPressed()) {
                            menuChoice = MenuChoice.HARD;
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
                if (secondsPassed <= 3) {
                    vines.generate(screenHeight);
                    clouds.generate();
                    herbs.generate(screenHeight);
                } else {
                    vines.update(gameSpeed, screenHeight);
                    obstacles.update(gameSpeed, screenHeight);
                    monke.update(gameSpeed, mouseH, vines);
                    clouds.update();
                    herbs.update(gameSpeed, screenHeight);
                }
            }
            case GAMEOVER -> {
                this.setBackground(SkyColor.GAMEOVER.getColor());
                monke.updateGameOver(screenHeight);
                if (keyH.keyPressed) {
                    gameState = GameState.MENU;
                    // reinitialize game objects and timer
                    initGameThings();
                }
            }
        }

        if (monke.isDead(obstacles)) {
            gameState = GameState.GAMEOVER;
            secondsSurvived = secondsPassed - 4; // -4 bc there's a three seconds delay in update case PLAY
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
                clouds.draw(g2);
                vines.draw(g2, screenHeight);
                monke.draw(g2);
                obstacles.draw(g2);
                herbs.draw(g2);
                mouseHint.draw(g2, mouseH);

                if (secondsPassed <= 3) {
                    g2.setFont(new Font("arial", Font.BOLD, 42));
                    g2.setColor(Color.RED);
                    g2.drawString(String.format("БРОСАЮ КУРИТЬ ЧЕРЕЗ %d...", 4 - secondsPassed), timerX, timerY);
                } else {
                    g2.setFont(new Font("arial", Font.BOLD, 42));
                    g2.setColor(Color.RED);
                    g2.drawString(String.format("НЕ КУРИЛ %d СЕКУНД", secondsPassed - 4), timerX, timerY);
                }
            }
            case GAMEOVER -> {
                monke.drawGameOver(g2);
                g2.setFont(new Font("arial", Font.BOLD, 42));
                g2.setColor(Color.RED);
                g2.drawString(String.format("ПОКУРИЛ... (продержался %d секунд)", secondsSurvived), (screenWidth >> 2), (screenHeight >> 1));
                g2.drawString("((нажмите любую клавишу))", (screenWidth >> 2), ((screenHeight >> 1) + (screenHeight >> 3)));
            }
        }

        g2.dispose();
    }

    private void initGameThings() {
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
