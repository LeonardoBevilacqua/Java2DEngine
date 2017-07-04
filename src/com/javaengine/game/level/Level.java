package com.javaengine.game.level;

import com.javaengine.game.Game;
import com.javaengine.game.entities.EntityManager;
import com.javaengine.game.entities.creatures.Player;
import com.javaengine.game.gfx.Assets;
import com.javaengine.game.gfx.Text;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.items.ItemManager;
import com.javaengine.game.level.tiles.Tile;
import com.javaengine.game.utils.Utils;
import java.awt.Color;
import java.awt.Graphics;

/**
 * The Level class is the base of the World of the game.
 *
 * @author leonardo
 */
public abstract class Level {

    protected final Handler handler;
    protected String path, msg;
    protected int width, height;
    protected int spawnX, spawnY;

// camera position
    private int xStart, yStart, xEnd, yEnd;
    private int[][] tiles;
    protected Player player;
    protected long timer;

    // entities
    protected EntityManager entityManager;
    // Item
    protected ItemManager itemManager;

    /**
     * Initializes the base of the world.
     *
     * @param handler The handler of the game.
     * @param path The path of the map.
     * @param player The main player object.
     */
    public Level(Handler handler, String path, Player player) {
        this.handler = handler;
        this.path = path;
        this.player = player;
        init();
    }

    /**
     * Initialize the variables.
     */
    protected final void init() {
        entityManager = new EntityManager(handler, player);

        itemManager = new ItemManager(handler);

        loadLevelFromFile(path);

        entityManager.getPlayer().setPosition(spawnX, spawnY);
    }

    /**
     * Sets the position of the camera on the player.
     */
    public void setCameraPosition() {
        xStart = Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILE_WIDTH);
        xEnd = Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILE_WIDTH + 1);
        yStart = Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILE_HEIGHT);
        yEnd = Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILE_HEIGHT + 1);
    }

    /**
     * Update the world and everything on it.
     */
    public void tick() {
        setCameraPosition();
        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getTile(x, y).tick();
            }
        }

        itemManager.tick();
        entityManager.tick();
    }

    /**
     * Renders a menssage.
     *
     * @param g The graphic object.
     */
    public void renderAlert(Graphics g) {
        if (msg != null && System.currentTimeMillis() < timer) {
            Text.drawString(g, msg, 10, handler.getHeight() - 100, false, Color.BLACK, Assets.font16);
        }
    }

    /**
     * Renders the world and everything on it.
     *
     * @param g The graphics object.
     */
    public void render(Graphics g) {
        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getTile(x, y).render(g, (x * Tile.TILE_WIDTH - handler.getGameCamera().getxOffset()),
                        (y * Tile.TILE_HEIGHT - handler.getGameCamera().getyOffset()));
            }
        }
        itemManager.render(g);
        entityManager.render(g);
        renderAlert(g);
    }

    /**
     * Gets a tile.
     *
     * @param x The x position.
     * @param y The y position.
     * @return Returns the Tile.
     */
    public synchronized Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return Tile.VOID;
        }

        Tile t = Tile.tiles[tiles[x][y]];
        if (t == null) {
            return Tile.VOID;
        }
        return t;
    }

    /**
     * Load the map from the file.
     *
     * @param path The path of map.
     */
    private void loadLevelFromFile(String path) {
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");

        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);

        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);

        tiles = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
            }
        }
    }

    // getters and setters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

}
