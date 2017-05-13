package com.javaengine.game.gfx;

import com.javaengine.game.utils.ImageLoader;
import com.javaengine.game.utils.Utils;
import java.awt.Font;
import java.awt.image.BufferedImage;

/**
 * The Assets class loads all the resources.
 *
 * @author leonardo
 */
public class Assets {

    private static final int WIDTH = 8, HEIGHT = 8;
    // fonts
    public static Font font28;
    public static Font font16;

    // tiles
    public static BufferedImage stoneTile, grassTile, voidTile, dirtTile;
    public static BufferedImage[] waterTile, waterWithPlayerTile;

    // entities
    public static BufferedImage tree, rock;
    public static BufferedImage[] player;

    // UI
    public static BufferedImage inventoryScreen;
    public static BufferedImage[] btn_start, btn_mp, lifeBar;

    /**
     * Load the sprite sheet and initializes all the assets.
     */
    public static void init() {
        font28 = FontLoader.loadFont("/fonts/slkscr.ttf", 28);
        font16 = FontLoader.loadFont("/fonts/slkscr.ttf", 16);

        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/sprite_sheet.png"));
        SpriteSheet tiles = new SpriteSheet(ImageLoader.loadImage("/Tiles_test.png"));

        // tiles
        voidTile = sheet.crop(0, 0, HEIGHT, HEIGHT);
        stoneTile = tiles.crop(0, 0, HEIGHT * 2, HEIGHT * 2);
        grassTile = tiles.crop(WIDTH * 2, 0, HEIGHT * 2, HEIGHT * 2);
        dirtTile = tiles.crop(WIDTH * 4, 0, HEIGHT * 2, HEIGHT * 2);

        waterTile = new BufferedImage[4];

        waterTile[0] = sheet.crop(WIDTH * 4, 0, HEIGHT, WIDTH);
        waterTile[1] = sheet.crop(WIDTH * 5, 0, HEIGHT, WIDTH);
        waterTile[2] = sheet.crop(WIDTH * 6, 0, HEIGHT, WIDTH);
        waterTile[3] = waterTile[1];

        waterWithPlayerTile = new BufferedImage[4];

        waterWithPlayerTile[0] = sheet.crop(WIDTH * 0, 27 * HEIGHT, WIDTH, HEIGHT);
        waterWithPlayerTile[1] = sheet.crop(WIDTH * 1, 27 * HEIGHT, WIDTH, HEIGHT);
        waterWithPlayerTile[2] = sheet.crop(WIDTH * 2, 27 * HEIGHT, WIDTH, HEIGHT);
        waterWithPlayerTile[3] = waterWithPlayerTile[1];

        // entities
        tree = sheet.crop(0, WIDTH, HEIGHT * 2, HEIGHT * 3);
        rock = sheet.crop(WIDTH * 2, HEIGHT * 1, WIDTH * 2, HEIGHT * 2);

        player = new BufferedImage[18];

        player[0] = sheet.crop(0 * WIDTH, 25 * HEIGHT, WIDTH * 2, HEIGHT * 2);
        player[1] = Utils.flipImageHorizontally(player[0]);

        player[2] = sheet.crop(2 * WIDTH, 25 * HEIGHT, WIDTH * 2, HEIGHT * 2);
        player[3] = Utils.flipImageHorizontally(player[2]);

        player[4] = sheet.crop(4 * WIDTH, 25 * HEIGHT, WIDTH * 2, HEIGHT * 2);
        player[5] = sheet.crop(6 * WIDTH, 25 * HEIGHT, WIDTH * 2, HEIGHT * 2);

        player[6] = Utils.flipImageHorizontally(player[4]);
        player[7] = Utils.flipImageHorizontally(player[5]);

        player[8] = sheet.crop(0 * WIDTH, 24 * HEIGHT, WIDTH * 2, HEIGHT * 2);
        player[9] = Utils.flipImageHorizontally(player[8]);

        player[10] = sheet.crop(2 * WIDTH, 24 * HEIGHT, WIDTH * 2, HEIGHT * 2);
        player[11] = Utils.flipImageHorizontally(player[10]);

        player[12] = sheet.crop(4 * WIDTH, 24 * HEIGHT, WIDTH * 2, HEIGHT * 2);

        player[13] = Utils.flipImageHorizontally(player[12]);

        player[14] = sheet.crop(10 * WIDTH, 25 * HEIGHT, WIDTH * 2, HEIGHT * 2);

        player[15] = sheet.crop(12 * WIDTH, 25 * HEIGHT, WIDTH * 2, HEIGHT * 2);

        player[16] = sheet.crop(8 * WIDTH, 25 * HEIGHT, WIDTH * 2, HEIGHT * 2);
        player[17] = Utils.flipImageHorizontally(player[16]);

        // UI
        inventoryScreen = ImageLoader.loadImage("/inventoryScreen.png");

        btn_start = new BufferedImage[2];

        btn_start[0] = sheet.crop(0 * WIDTH, 18 * HEIGHT, 4 * WIDTH, 2 * HEIGHT);
        btn_start[1] = sheet.crop(0 * WIDTH, 20 * HEIGHT, 4 * WIDTH, 2 * HEIGHT);

        btn_mp = new BufferedImage[2];

        btn_mp[0] = sheet.crop(4 * WIDTH, 18 * HEIGHT, 4 * WIDTH, 2 * HEIGHT);
        btn_mp[1] = sheet.crop(4 * WIDTH, 20 * HEIGHT, 4 * WIDTH, 2 * HEIGHT);

        lifeBar = new BufferedImage[3];

        lifeBar[0] = sheet.crop(WIDTH * 4, HEIGHT * 4, WIDTH * 2, HEIGHT);
        lifeBar[1] = sheet.crop(WIDTH * 2, HEIGHT * 4, WIDTH * 2, HEIGHT);
        lifeBar[2] = sheet.crop(0, HEIGHT * 4, WIDTH * 2, HEIGHT);

    }
}
