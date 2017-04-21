package com.javaengine.game.entities;

import com.javaengine.game.handlers.Handler;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity {

    protected Handler handler;
    protected int x, y;
    protected int width, height;
    protected Rectangle bounds;

    public Entity(Handler handler, int x, int y, int width, int height) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        bounds = new Rectangle(0, 0, width, height);
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public boolean checkEntityColision(int xOffset, int yOffset) {
        for (Entity e : handler.getLevel().getEntityManager().getEntities()) {
            if (e.equals(this)) {
                continue;
            }
            if (e.getCollisionBounds(0, 0).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        return false;
    }

    public Rectangle getCollisionBounds(int xOffset, int yOffset) {
        return new Rectangle(x + bounds.x + xOffset, y + bounds.y + yOffset, bounds.width, bounds.height);
    }

    // getters and setters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
