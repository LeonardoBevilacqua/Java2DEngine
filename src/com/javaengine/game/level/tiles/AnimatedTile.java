package com.javaengine.game.level.tiles;

import com.javaengine.game.gfx.animations.AnimationTile;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Creates a animated tile.
 *
 * @author leonardo
 */
public class AnimatedTile extends Tile {

    private AnimationTile animation;

    /**
     * Create the tile
     *
     * @param id The id of the texture.
     * @param tile The image of the texture.
     */
    public AnimatedTile(int id, BufferedImage[] tile) {
        super(id, tile);
        animation = new AnimationTile(800, tile);
    }

    /**
     * Updates the frame of the tile.
     */
    @Override
    public void tick() {
        animation.tick();
    }

    /**
     * Draws the current frame of the tile.
     *
     * @param g The graphic object.
     * @param x The x position.
     * @param y The y position.
     */
    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(getCurrentAnimationFrame(), x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    /**
     * Gets the current frame.
     *
     * @return Returns the image of the frame.
     */
    private BufferedImage getCurrentAnimationFrame() {
        return animation.getCurrentFrame(true);
    }

}
