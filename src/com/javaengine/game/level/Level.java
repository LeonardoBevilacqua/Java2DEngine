package com.javaengine.game.level;

import com.javaengine.game.Game;
import com.javaengine.game.entities.EntityManager;
import com.javaengine.game.entities.creatures.Player;
import com.javaengine.game.entities.creatures.PlayerMP;
import com.javaengine.game.entities.statics.Tree;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.level.tiles.Tile;
import com.javaengine.game.utils.Utils;
import java.awt.Graphics;
import javax.swing.JOptionPane;

public class Level {

    private final Handler handler;
    private int width, height;
    private int spawnX, spawnY;
    private int[][] tiles;
    protected Player player;

    // entities
    private final EntityManager entityManager;

    public Level(Handler handler, String path, boolean isMultiplayer) {
        this.handler = handler;

        entityManager = !isMultiplayer
                ? new EntityManager(handler,player = new Player(handler, 10, 10, "Teste"))
                
                : new EntityManager(handler, player = new PlayerMP(handler, 10, 10, JOptionPane.showInputDialog("nome"), null, -1, true));

        for (int i = 0; i < 3; i++) {
            entityManager.addEntity(new Tree(handler, 100 * (i + 1), 50));
        }

        loadLevelFromFile(path);
        
        entityManager.getPlayer().setX(spawnX);
        entityManager.getPlayer().setY(spawnY);
    }

    public void tick() {
        int xStart = Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth() * Game.SCALE) / Tile.TILE_WIDTH + 1);
        int yStart = Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight() * Game.SCALE) / Tile.TILE_HEIGHT + 1);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getTile(x, y).tick();
            }
        }
        // entities
        entityManager.tick();
    }

    public void render(Graphics g) {
        int xStart = Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth() * Game.SCALE) / Tile.TILE_WIDTH + 1);
        int yStart = Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight() * Game.SCALE) / Tile.TILE_HEIGHT + 1);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getTile(x, y).render(g, (x * Tile.TILE_WIDTH - handler.getGameCamera().getxOffset()),
                        (y * Tile.TILE_HEIGHT - handler.getGameCamera().getyOffset()));
            }
        }
        // entities
        entityManager.render(g);
    }

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
//
//    public void saveLevelToFile() {
//        try {
//            ImageIO.write(image, "png", new File(Level.class.getResource(this.imagePath).getFile()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void alterTile(int x, int y, Tile newTile) {
//        this.tiles[x + y * width] = newTile.getId();
//        image.setRGB(x, y, newTile.getLevelColour());
//    }
//

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
}
