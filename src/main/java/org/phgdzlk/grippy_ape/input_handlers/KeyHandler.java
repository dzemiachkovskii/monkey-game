package org.phgdzlk.grippy_ape.input_handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class KeyHandler implements KeyListener {
    private boolean downPressed, upPressed, enterPressed, keyPressed;
    private final boolean[] cringe = new boolean[6];

    @Override
    public void keyPressed(KeyEvent e) {
        keyPressed = true;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN -> downPressed = true;
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_ENTER -> enterPressed = true;

            // filling cringe array
            case KeyEvent.VK_C -> cringe[0] = true;
            case KeyEvent.VK_R -> {
                if (cringe[0]) {
                    cringe[1] = true;
                } else {
                    uncringe();
                }
            }
            case KeyEvent.VK_I -> {
                if (cringe[1]) {
                    cringe[2] = true;
                } else {
                    uncringe();
                }
            }
            case KeyEvent.VK_N -> {
                if (cringe[2]) {
                    cringe[3] = true;
                } else {
                    uncringe();
                }
            }
            case KeyEvent.VK_G -> {
                if (cringe[3]) {
                    cringe[4] = true;
                } else {
                    uncringe();
                }
            }
            case KeyEvent.VK_E -> {
                if (cringe[4]) {
                    cringe[5] = true;
                } else {
                    uncringe();
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed = false;
    }

    public boolean isDownPressed() {
        boolean temp = downPressed;
        downPressed = false;
        return temp;
    }

    public boolean isUpPressed() {
        boolean temp = upPressed;
        upPressed = false;
        return temp;
    }

    public boolean isEnterPressed() {
        boolean temp = enterPressed;
        enterPressed = false;
        return temp;
    }

    public boolean isKeyPressed() {
        boolean temp = keyPressed;
        keyPressed = false;
        // "turning off" all other key variables to to prevent menu from random actions
        upPressed = false;
        downPressed = false;
        enterPressed = false;
        return temp;
    }

    public boolean isCringePressed() {
        for (boolean letter : cringe) {
            if (!letter) return false;
        }
        uncringe();
        return true;
    }

    private void uncringe() {
        Arrays.fill(cringe, false);
    }
}
