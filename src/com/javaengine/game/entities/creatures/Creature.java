package com.javaengine.game.entities.creatures;

import com.javaengine.game.entities.Entity;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.level.tiles.Tile;

/**
 * Create the base of the creatures
 *
 * @author leonardo
 */
public abstract class Creature extends Entity {

    public static final int DEFAULT_SPEED = 2,
            DEFAULT_CRETURE_WIDTH = 64,
            DEFAULT_CRETURE_HEIGHT = 64;
    protected final int UP = 0,
            DOWN = 1,
            LEFT = 2,
            RIGHT = 3;
    protected String name;
    protected int xMove;
    protected int yMove;
    protected float speed, maxSpeed;
    protected int numSteps = 0;
    protected boolean isMoving;
    protected boolean isSwimming;
    protected int movingDir = 1;
    protected int scale = 1;

    /**
     * Initialize the base of the creature.
     *
     * @param handler The handler of the game.
     * @param name The name of the creature.
     * @param width The width.
     * @param height The height.
     */
    public Creature(Handler handler, String name, int width, int height) {
        super(handler, width, height);
        this.name = name;
        speed = maxSpeed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
    }

    /**
     * Move the creature in the x and y coordinates.
     */
    public void move() {
        if (xMove != 0 || yMove != 0) {
            if (!checkEntityColision(xMove, 0)) {
                moveX();
            }
            if (!checkEntityColision(0, yMove)) {
                moveY();
            }
            isMoving = true;
            numSteps++;
        }
        isSwimming = isOnWater(
                (x + Tile.TILE_WIDTH) / Tile.TILE_WIDTH,
                (y + height) / Tile.TILE_HEIGHT
        );
    }

    /**
     * Move the creature in the x coordinate.
     */
    public void moveX() {
        if (xMove > 0) {//Moving right
            int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH;

            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT)
                    && !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)) {
                movingDir = RIGHT;
                x += xMove;
            } else {
                x = tx * Tile.TILE_WIDTH - bounds.x - bounds.width - 1;
            }

        } else if (xMove < 0) {//Moving left
            int tx = (int) (x + xMove + bounds.x) / Tile.TILE_WIDTH;

            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT)
                    && !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)) {
                movingDir = LEFT;
                x += xMove;
            } else {
                x = tx * Tile.TILE_WIDTH + Tile.TILE_WIDTH - bounds.x;
            }

        }

    }

    /**
     * Move the creature in the y coordinate.
     */
    public void moveY() {
        if (yMove < 0) {//Up
            int ty = (int) (y + yMove + bounds.y) / Tile.TILE_HEIGHT;

            if (!collisionWithTile((x + bounds.x) / Tile.TILE_WIDTH, ty)
                    && !collisionWithTile((x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)) {
                movingDir = UP;
                y += yMove;
            } else {
                y = ty * Tile.TILE_HEIGHT + Tile.TILE_HEIGHT - bounds.y;
            }

        } else if (yMove > 0) {//Down
            int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT;

            if (!collisionWithTile((x + bounds.x) / Tile.TILE_WIDTH, ty)
                    && !collisionWithTile((x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)) {
                movingDir = DOWN;
                y += yMove;
            } else {
                y = ty * Tile.TILE_HEIGHT - bounds.y - bounds.height - 1;
            }

        }

    }

    /**
     * Checks colision with the tile.
     *
     * @param x The x position.
     * @param y The y position.
     * @return Returns if has collision.
     */
    protected boolean collisionWithTile(int x, int y) {
        return handler.getLevel().getTile(x, y).isSolid();
    }

    /**
     * Checks if the creature is on the water.
     *
     * @param x The x position.
     * @param y The y position.
     * @return Returns the creature is on the water.
     */
    protected boolean isOnWater(int x, int y) {
        return handler.getLevel().getTile(x, y).getId() == Tile.WATER.getId();
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getNumSteps() {
        return numSteps;
    }

    public void setNumSteps(int numSteps) {
        this.numSteps = numSteps;
    }

    public boolean isIsMoving() {
        return isMoving;
    }

    public int getMovingDir() {
        return movingDir;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getxMove() {
        return xMove;
    }

    public void setxMove(int xMove) {
        this.xMove = xMove;
    }

    public int getyMove() {
        return yMove;
    }

    public boolean isIsSwimming() {
        return isSwimming;
    }

    public void setyMove(int yMove) {
        this.yMove = yMove;
    }

    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public void setMovingDir(int movingDir) {
        this.movingDir = movingDir;
    }

}
