package com.javaengine.game.handlers.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    private boolean[] keys, justPressed, cantPress;
    public boolean up, down, left, right, meleeAtack;
    public int inventory = KeyEvent.VK_I, Iup = KeyEvent.VK_DOWN, Idown = KeyEvent.VK_UP;

    public KeyManager() {
        keys = new boolean[256];
        justPressed = new boolean[keys.length];
        cantPress = new boolean[keys.length];
    }

    public void tick() {
        for (int i = 0; i < keys.length; i++) {
            if (cantPress[i] && !keys[i]) {
                cantPress[i] = false;
            } else if (justPressed[i]) {
                cantPress[i] = true;
                justPressed[i] = false;
            }

            if (!cantPress[i] && keys[i]) {
                justPressed[i] = true;
            }
        }

        up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
        meleeAtack = keys[KeyEvent.VK_SPACE];
    }

    public boolean keyJustPressed(int keyCode) {
        if (keyCode < 0 || keyCode >= keys.length) {
            return false;
        }
        return justPressed[keyCode];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < 0 || e.getKeyCode() > keys.length) {
            return;
        }
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < 0 || e.getKeyCode() > keys.length) {
            return;
        }
        keys[e.getKeyCode()] = false;
    }

}
