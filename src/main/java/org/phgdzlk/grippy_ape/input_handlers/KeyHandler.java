package org.phgdzlk.grippy_ape.input_handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean downPressed, upPressed, enterPressed;

    @Override
    public void keyPressed(KeyEvent e) {
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

    }
}
