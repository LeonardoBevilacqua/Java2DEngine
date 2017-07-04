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
public class AssetsLoader {

    private static final int WIDTH = 32, HEIGHT = 32;
    // fonts
    public static Font font16;

    // tiles
    public static BufferedImage[] tiles;

    // entities
    public static BufferedImage[] player;

    // UI
    public static BufferedImage[] menuUi;

    // HUD
    public static BufferedImage[] gameHud;

    /**
     * Load the sprite sheet and initializes all the assets.
     */
    public static void init() {
        font16 = FontLoader.loadFont("/fonts/slkscr.ttf", 16);

        SpriteSheet tiles = new SpriteSheet(ImageLoader.loadImage("/Tiles_test.png"));
        SpriteSheet ui = new SpriteSheet(ImageLoader.loadImage("/UI.png"));
        SpriteSheet playerSheet = new SpriteSheet(ImageLoader.loadImage("/Player.png"));

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

        // up
        player[0] = playerSheet.crop(0, 0, 32, 64);
        player[1] = playerSheet.crop(32, 0, 32, 64);
        player[2] = playerSheet.crop(32 * 2, 0, 32, 64);
        player[3] = playerSheet.crop(32 * 3, 0, 32, 64);

        // down
        player[4] = playerSheet.crop(0, 64, 32, 64);
        player[5] = playerSheet.crop(32, 64, 32, 64);
        player[6] = playerSheet.crop(32 * 2, 64, 32, 64);
        player[7] = playerSheet.crop(32 * 3, 64, 32, 64);

        //left
        player[8] = playerSheet.crop(0, 64 * 2, 32, 64);
        player[9] = playerSheet.crop(32, 64 * 2, 32, 64);
        player[10] = playerSheet.crop(32 * 2, 64 * 2, 32, 64);
        player[11] = playerSheet.crop(32 * 3, 64 * 2, 32, 64);

        //right
        player[12] = playerSheet.crop(0, 64 * 3, 32, 64);
        player[13] = playerSheet.crop(32, 64 * 3, 32, 64);
        player[14] = playerSheet.crop(32 * 2, 64 * 3, 32, 64);
        player[15] = playerSheet.crop(32 * 3, 64 * 3, 32, 64);

        player[16] = sheet.crop(8 * WIDTH, 25 * HEIGHT, WIDTH * 2, HEIGHT * 2);
        player[17] = Utils.flipImageHorizontally(player[16]);

        // UI
        inventoryScreen = ImageLoader.loadImage("/inventoryScreen.png");

        checkBox = new BufferedImage[2];

        checkBox[0] = ui.crop(0, 32 * 3, 32, 32);
        checkBox[1] = checkBox[0];

        check = new BufferedImage[2];
        check[0] = ui.crop(32, 32 * 3, 32, 32);
        check[1] = check[0];

        btn_start = new BufferedImage[2];

        btn_start[0] = ui.crop(32 * 3, 0, 32 * 6, 32 * 2);
        btn_start[1] = btn_start[0];

        btn_mp = new BufferedImage[2];

        btn_mp[0] = ui.crop(32 * 9, 0, 32 * 6, 32 * 2);
        btn_mp[1] = btn_mp[0];

        btn_config = new BufferedImage[2];
        btn_config[0] = ui.crop(32 * 3, 32 * 2, 64, 64);
        btn_config[1] = btn_config[0];

        btn_exit = new BufferedImage[2];
        btn_exit[0] = ui.crop(32 * 5, 32 * 2, 64 * 2, 64);
        btn_exit[1] = btn_exit[0];

        lifeBar = new BufferedImage[3];

        lifeBar[0] = ui.crop(0, 0, 32 * 3, 32);
        lifeBar[1] = ui.crop(0, 32, 32 * 3, 32);
        lifeBar[2] = ui.crop(0, 32 * 2, 32 * 3, 32);

    }
}
