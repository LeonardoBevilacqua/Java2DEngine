package com.javaengine.game.level.tiles;

import com.javaengine.game.gfx.Assets;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * The Tile class creates the base of the tile. Also Keeps the tiles created.
 *
 * @author leonardo
 */
public abstract class Tile {

    // static variable
    public static Tile[] tiles = new Tile[256];

    public static final Tile VOID = new BasicSolidTile(0, Assets.voidTile);
    public static final Tile STONE = new BasicSolidTile(1, Assets.stoneTile);
    public static final Tile GRASS = new BasicTile(2, Assets.grassTile);
    public static final Tile LAVA = new BasicTile(3, Assets.dirtTile);
    public static final Tile WATER = new AnimatedTile(4, Assets.waterTile);

    // class
    public static final int TILE_WIDTH = 32, TILE_HEIGHT = 32;

    protected final int id;
    protected BufferedImage texture;
    protected BufferedImage[] textures;

    /**
     * Create the Tile.
     *
     * @param id The id of the texture.
     * @param texture The image of the texture.
     */
    public Tile(int id, BufferedImage texture) {
        this.id = id;
        this.texture = texture;

        tiles[id] = this;
    }

    /**
     * Create the array of tiles.
     *
     * @param id The id of the textures.
     * @param textures The array of textures.
     */
    public Tile(int id, BufferedImage[] textures) {
        this.id = id;
        this.textures = textures;

        tiles[id] = this;
    }

    /**
     * Update of the Textures.
     */
    public void tick() {
    }

    /**
     * Draw the texture.
     *
     * @param g The graphic object.
     * @param x
     * @param y
     */
    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    /**
     * @return Verifies if is a solid tile.
     */
    public boolean isSolid() {
        return false;
    }

    /**
     *
     * @return Returns the id of the tile.
     */
    public int getId() {
        return id;
    }

}
