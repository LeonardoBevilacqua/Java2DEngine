package com.javaengine.game.level.tiles;

import java.awt.image.BufferedImage;

/**
 * Creates a basic tile
 * @author leonardo
 */
public class BasicTile extends Tile{

    /**
     * Create the Tile.
     *
     * @param id The id of the texture.
     * @param tile The image of the texture.
     */
    public BasicTile(int id, BufferedImage tile) {
        super(id, tile);
    }
}
