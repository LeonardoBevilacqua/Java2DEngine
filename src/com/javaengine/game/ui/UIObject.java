/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaengine.game.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 *
 * @author leonardo
 */
public abstract class UIObject {

    protected int x, y, width, height;
    protected Rectangle bounds;
    protected boolean hovering = false;

    public UIObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(x, y, width, height);
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract void onClick();

    public void onMouseMove(MouseEvent e) {
        if (bounds.contains(e.getX(), e.getY())) {
            hovering = true;
        } else {
            hovering = false;
        }
    }

    public void onMouseRelease(MouseEvent e) {
        if (hovering) {
            onClick();
        }
    }

    // getters and setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isHovering() {
        return hovering;
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }

}
