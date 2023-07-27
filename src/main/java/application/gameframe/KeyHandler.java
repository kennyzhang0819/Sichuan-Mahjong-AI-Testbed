package application.gameframe;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {
    public boolean hPressed, cPressed, pPressed, kPressed, sPressed;
    public boolean pProcessed = false;
    public boolean kProcessed = false;
    public boolean cProcessed = false;
    public boolean hProcessed = false;
    public boolean sProcessed = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_H) {
            this.hPressed = true;
            this.hProcessed = false;
        }
        if (code == KeyEvent.VK_C) {
            this.cPressed = true;
            this.cProcessed = false;
        }
        if (code == KeyEvent.VK_P) {
            this.pPressed = true;
            this.pProcessed = false;
        }
        if (code == KeyEvent.VK_K) {
            this.kPressed = true;
            this.kProcessed = false;
        }
        if (code == KeyEvent.VK_S) {
            this.sPressed = true;
            this.sProcessed = false;
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
