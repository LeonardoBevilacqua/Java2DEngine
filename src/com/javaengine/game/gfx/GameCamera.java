package com.javaengine.game.gfx;

import com.javaengine.game.handlers.Handler;
import com.javaengine.game.entities.Entity;
import com.javaengine.game.level.tiles.Tile;

/**
 *
 * @author leonardo
 */
public class GameCamera {

    private Handler handler;
    private int xOffset, yOffset;

    public GameCamera(Handler handler, int xOffset, int yOffset) {
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void checkBlankSpace() {
        if (xOffset < 0) {
            xOffset = 0;
        } else if (xOffset > handler.getLevel().getWidth() * Tile.TILE_WIDTH - handler.getWidth()) {
            xOffset = handler.getLevel().getWidth() * Tile.TILE_WIDTH - handler.getWidth();
        }

        if (yOffset < 0) {
            yOffset = 0;
        } else if (yOffset > handler.getLevel().getHeight() * Tile.TILE_HEIGHT - handler.getHeight()) {
            yOffset = handler.getLevel().getHeight() * Tile.TILE_HEIGHT - handler.getHeight();
        }
    }

    public void centerOnEntity(Entity e) {
        xOffset = e.getX() - handler.getWidth() / 2 + e.getWidth() / 2;
        yOffset = e.getY() - handler.getHeight() / 2 + e.getHeight() / 2;

        checkBlankSpace();
    }

    public void move(int xAmt, int yAmt) {
        xOffset += xAmt;
        yOffset += yAmt;

        checkBlankSpace();
    }

    // getters and setters
    public int getxOffset() {
        return xOffset;
    }

    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

}
