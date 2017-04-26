package com.javaengine.game.entities.creatures;

import com.javaengine.game.entities.Entity;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.level.tiles.Tile;

public abstract class Creature extends Entity {

    public static final int DEFAULT_SPEED = 2,
            DEFAULT_CRETURE_WIDTH = 64,
            DEFAULT_CRETURE_HEIGHT = 64;
    protected final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
    protected String name;
    protected int xMove;
    protected int yMove;
    protected int speed;
    protected int numSteps = 0;
    protected boolean isMoving;
    protected int movingDir = 1;
    protected int scale = 1;

    public Creature(Handler handler, String name, int x, int y, int width, int height) {
        super(handler, x, y, width, height);
        this.name = name;
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
    }

    public void move() {
        if (xMove != 0 || yMove != 0) {
            if (!checkEntityColision(xMove, 0)) {
                moveX();
            }
            if (!checkEntityColision(0, yMove)) {
                moveY();
            }
            isMoving = true;
        }

        numSteps++;
    }

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

    protected boolean collisionWithTile(int x, int y) {
        return handler.getLevel().getTile(x, y).isSolid();
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
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
