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
 * The UIObject is abstract and is the base of the other objects that will
 * create the UI.
 *
 * @author leonardo
 */
public abstract class UIObject {

    protected int width, height;
    protected float x, y;
    protected Rectangle bounds;
    protected boolean hovering, active;

    /**
     * Set the base of the UI object.
     *
     * @param x The x position.
     * @param y The y position.
     * @param width The width of the object.
     * @param height The Height of the object.
     */
    public UIObject(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle((int) x, (int) y, width, height);
        hovering = false;
        active = true;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract void onClick();

    /**
     * Check with the mouse is on the object.
     *
     * @param e The mouse event.
     */
    public void onMouseMove(MouseEvent e) {
        hovering = bounds.contains(e.getX(), e.getY());
    }

    public void onMouseRelease(MouseEvent e) {
        if (hovering) {
            onClick();
        }
    }

    // getters and setters
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    

}
