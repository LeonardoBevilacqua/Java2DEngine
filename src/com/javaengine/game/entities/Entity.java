package com.javaengine.game.entities;

import com.javaengine.game.entities.creatures.Player;
import com.javaengine.game.entities.creatures.PlayerMP;
import com.javaengine.game.gfx.Assets;
import com.javaengine.game.handlers.Handler;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * The Entity is the responsible to give the default action of the others
 * classes extended of entity.
 *
 * @author leonardo
 */
public abstract class Entity {

    public static final int DEFAULT_HEALTH = 10;

    protected String id;
    protected Handler handler;
    protected int x, y;
    protected int width, height;
    protected float health, maxHealth;
    protected boolean active, damage, isAttacking; // alive
    protected Rectangle bounds;

    /**
     * The constructor is responsible to initialize the default components of
     * the entity.
     *
     * @param handler The handler to have access to all others components.
     * @param width The width of the entity.
     * @param height The height of the entity.
     */
    public Entity(Handler handler, int width, int height) {
        init(handler, width, height);
    }

    /**
     * Initialize the components outside the constructor.
     */
    private void init(Handler handler, int width, int height) {
        this.handler = handler;
        this.width = width;
        this.height = height;
        this.x = 20;
        this.y = 20;
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

    /**
     * The method is responsible to make damage in the entity.
     *
     * @param damageAmt The amount of damage that the entity will take.
     */
    public void hurt(float damageAmt) {
        damage = true;
        health -= damageAmt;
        if (health <= 0) {
            active = false;
            die();
        }
    }

    /**
     * Sets the position of the entity on the screen.
     *
     * @param x The horizontal position.
     * @param y The vertical position.
     */
    public final void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Renders the life bar of the main entity and others entities.
     *
     * @param g The object of the graphics.
     */
    public void renderLifeBar(Graphics g) {
        float healthPercentage = this.health / this.maxHealth;

        if (this instanceof Player && handler.getLevel().getEntityManager().getPlayer().equals(this)) {

            g.drawImage(Assets.lifeBar[2], 20, 20, 96, 32, null);
            g.drawImage(Assets.lifeBar[1], 20, 20, (int) healthPercentage * 96, 32, null);
            g.drawImage(Assets.lifeBar[0], 20, 20, 96, 32, null);
        }

        if (damage && !this.equals(handler.getLevel().getEntityManager().getPlayer())) {

            g.drawImage(Assets.lifeBar[2], x - handler.getGameCamera().getxOffset(), y - handler.getGameCamera().getyOffset() - 8, 96, 32, null);
            g.drawImage(Assets.lifeBar[1], x - handler.getGameCamera().getxOffset(), y - handler.getGameCamera().getyOffset() - 8, (int) (healthPercentage * 96), 32, null);
            g.drawImage(Assets.lifeBar[0], x - handler.getGameCamera().getxOffset(), y - handler.getGameCamera().getyOffset() - 8, 96, 32, null);
        }
    }

    /**
     * Checks the collision with others entities.
     *
     * @param xOffset The horizontal offset.
     * @param yOffset The vertical offset.
     * @return Returns true if has collision with another entity.
     */
    public boolean checkEntityColision(int xOffset, int yOffset) {
        for (Entity e : handler.getLevel().getEntityManager().getEntities()) {
            if (e.equals(this) || e instanceof PlayerMP) {
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

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
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

    public String getUniqueId() {
        return id;
    }

}
