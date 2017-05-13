package com.javaengine.game.level.tiles;

import java.awt.image.BufferedImage;

/**
 * Creates a solid tile.
 *
 * @author leonardo
 */
public class BasicSolidTile extends BasicTile {

    /**
     * Create the Tile.
     *
     * @param id The id of the texture.
     * @param tile The image of the texture.
     */
    public BasicSolidTile(int id, BufferedImage tile) {
        super(id, tile);
    }

    /**
     * Set the tile as solid.
     *
     * @return Returns the solid status.
     */
    @Override
    public boolean isSolid() {
        return true;
    }

}
