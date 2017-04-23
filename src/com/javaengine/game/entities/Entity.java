package com.javaengine.game.entities;

import com.javaengine.game.entities.creatures.Player;
import com.javaengine.game.handlers.Handler;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity {

    public static final int DEFAULT_HEALTH = 10;

    protected Handler handler;
    protected int x, y;
    protected int width, height;
    protected float health, maxHealth;
    protected boolean active, damage, isAttacking; // alive
    protected Rectangle bounds;

    public Entity(Handler handler, int x, int y, int width, int height) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = maxHealth = DEFAULT_HEALTH;
        this.active = true;
        this.damage = isAttacking = false;

        this.bounds = new Rectangle(0, 0, width, height);
    }

    public abstract void tick();

    public void render(Graphics g) {
        renderLifeBar(g);

    }

    public abstract void die();

    public void hurt(float damageAmt) {
        damage = true;
        health -= damageAmt;
        if (health <= 0) {
            active = false;
            die();
        }
    }

    public void renderLifeBar(Graphics g) {
        float healthPercentage = health / maxHealth;

        if (this instanceof Player && handler.getLevel().getEntityManager().getPlayer().equals(this)) {
            g.setColor(Color.gray);
            g.fillRect(20, 20, (int) maxHealth * 20, 12);

            g.setColor(Color.red);
            g.fillRect(20, 20, (int) (health * 20), 12);

            g.setColor(Color.black);
            g.drawRect(20, 20, (int) maxHealth * 20, 12);
        }

        if (damage && !(this instanceof Player)) {

            g.setColor(Color.gray);
            g.fillRect(x - handler.getGameCamera().getxOffset(), y - handler.getGameCamera().getyOffset() - 8,
                    width, 8);

            g.setColor(Color.red);
            g.fillRect(x - handler.getGameCamera().getxOffset(), y - handler.getGameCamera().getyOffset() - 8,
                    (int) (width * healthPercentage), 8);

            g.setColor(Color.black);
            g.drawRect(x - handler.getGameCamera().getxOffset(), y - handler.getGameCamera().getyOffset() - 8,
                    width, 8);
        }
    }

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

    public float getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDamage() {
        return damage;
    }

    public void setDamage(boolean damage) {
        this.damage = damage;
    }

    public boolean isIsAttacking() {
        return isAttacking;
    }

    public void setIsAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

}
