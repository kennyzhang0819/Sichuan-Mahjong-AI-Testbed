package application.gameframe;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean hPressed, cPressed, pPressed, kPressed, sPressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_H) {
            this.hPressed = true;
        }
        if (code == KeyEvent.VK_C) {
            this.cPressed = true;
        }
        if (code == KeyEvent.VK_P) {
            this.pPressed = true;
        }
        if (code == KeyEvent.VK_K) {
            this.kPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            this.sPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_H) {
            this.hPressed = false;
        }
        if (code == KeyEvent.VK_C) {
            this.cPressed = false;
        }
        if (code == KeyEvent.VK_P) {
            this.pPressed = false;
        }
        if (code == KeyEvent.VK_K) {
            this.kPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            this.sPressed = false;
        }
    }
}
