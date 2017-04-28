package com.javaengine.game.handlers.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    public class Key {

        private int numTimesPressed = 0;
        private boolean pressed = false;
        private boolean justPressed = false;

        public int getNumTimesPressed() {
            return numTimesPressed;
        }

        public boolean isPressed() {
            return pressed;
        }

        public boolean wasJustPressed() {
            return justPressed;
        }

        public void setJustPressed() {
            justPressed = !justPressed;
        }

        public void setPressed(boolean pressed) {
            this.pressed = pressed;
        }

        public void toggle(boolean isPressed) {
            pressed = isPressed;
            if (isPressed) {
                numTimesPressed++;
            }
        }
    }
    
    public Key f11 = new Key();

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key meleeAtack = new Key();

    public Key inventory = new Key();
    public Key inventoryUp = new Key();
    public Key inventoryDown = new Key();

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);
    }

    public void toggleKey(int keyCode, boolean isPressed) {
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            up.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            down.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            left.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            right.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            meleeAtack.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_I) {
            if (isPressed) {
                inventory.setJustPressed();
            }
        }
        if (keyCode == KeyEvent.VK_UP) {
            inventoryUp.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            inventoryDown.toggle(isPressed);
        }
        
        if (keyCode == KeyEvent.VK_F11) {
            f11.toggle(isPressed);
        }
    }
}
