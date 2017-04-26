package com.javaengine.game.gfx;

import com.javaengine.game.handlers.Handler;
import com.javaengine.game.entities.Entity;
import com.javaengine.game.level.tiles.Tile;

/**
 * The GameCamera controls the position of the camera in the game.
 *
 * @author leonardo
 */
public class GameCamera {

    private Handler handler;
    private int xOffset, yOffset;

    /**
     * The constructor set the initial position of the camera.
     *
     * @param handler The handler of the game.
     * @param xOffset The xOffset.
     * @param yOffset The yOffset.
     */
    public GameCamera(Handler handler, int xOffset, int yOffset) {
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    /**
     * Verifies if the screen is on the limit of the map.
     */
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

    /**
     *Centers the camera on the main entity.
     * @param e The entity that must be focused.
     */
    public void centerOnEntity(Entity e) {
        xOffset = e.getX() - handler.getWidth() / 2 + e.getWidth() / 2;
        yOffset = e.getY() - handler.getHeight() / 2 + e.getHeight() / 2;

        checkBlankSpace();
    }

    // getters and setters
    
    /**
     * 
     * @return Returns the xOffset.
     */
    public int getxOffset() {
        return xOffset;
    }

    /**
     * 
     * @return Returns the yOffset.
     */
    public int getyOffset() {
        return yOffset;
    }
}
