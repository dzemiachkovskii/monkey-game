package org.phgdzlk.grippy_ape.input_handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean keyPressed;
    private boolean downPressed, upPressed, enterPressed;

    @Override
    public void keyPressed(KeyEvent e) {
        keyPressed = true;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN -> downPressed = true;
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_ENTER -> enterPressed = true;
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
}
