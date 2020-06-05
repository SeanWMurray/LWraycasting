package main.components;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {

    private View v;

    public Controller(View v) {
        this.v = v;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case (KeyEvent.VK_D):
                v.right = true;
                break;
            case (KeyEvent.VK_A):
                v.left = true;
                break;
            case (KeyEvent.VK_W):
                v.forward = true;
                break;
            case (KeyEvent.VK_S):
                v.backwards = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case (KeyEvent.VK_D):
                v.right = false;
                break;
            case (KeyEvent.VK_A):
                v.left = false;
                break;
            case (KeyEvent.VK_W):
                v.forward = false;
                break;
            case (KeyEvent.VK_S):
                v.backwards = false;
                break;
        }
    }
}
