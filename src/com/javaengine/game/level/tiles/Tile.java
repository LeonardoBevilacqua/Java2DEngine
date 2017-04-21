package com.javaengine.game.level.tiles;

import com.javaengine.game.gfx.Assets;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Tile {

    // static stuff
    public static Tile[] tiles = new Tile[256];

    public static final Tile VOID = new BasicSolidTile(0, Assets.voidTile);
    public static final Tile STONE = new BasicSolidTile(1, Assets.stoneTile);
    public static final Tile GRASS = new BasicTile(2, Assets.grassTile);
    public static final Tile LAVA = new BasicTile(3, Assets.lavaTile);
    public static final Tile WATER = new AnimatedTile(4, Assets.waterTile);
    public static final Tile WATERWITHPLAYER = new AnimatedTile(5, Assets.waterWithPlayerTile);

    // class
    public static final int TILE_WIDTH = 32, TILE_HEIGHT = 32;

    protected final int id;
    protected BufferedImage texture;
    protected BufferedImage[] textures;

    public Tile(int id, BufferedImage texture) {
        this.id = id;
        this.texture = texture;

        tiles[id] = this;
    }
    
    public Tile(int id, BufferedImage[] textures) {
        this.id = id;
        this.textures = textures;

        tiles[id] = this;
    }

    public void tick() {
    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    public boolean isSolid() {
        return false;
    }

    public int getId() {
        return id;
    }

}
