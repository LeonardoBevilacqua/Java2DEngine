package com.javaengine.game.gfx;

import com.javaengine.game.utils.Utils;
import java.awt.image.BufferedImage;

/**
 *
 * @author leonardo
 */
public class Assets {

    private static final int WIDTH = 8, HEIGHT = 8;
    // tiles
    public static BufferedImage stoneTile, grassTile, voidTile, lavaTile;
    public static BufferedImage[] waterTile, waterWithPlayerTile;

    // entities
    public static BufferedImage tree;
    public static BufferedImage[] player;

    // UI
    public static BufferedImage[] btn_start, btn_mp;

    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/sprite_sheet.png"));

        // tiles
        voidTile = sheet.crop(0, 0, HEIGHT, HEIGHT);
        stoneTile = sheet.crop(WIDTH, 0, HEIGHT, HEIGHT);
        grassTile = sheet.crop(WIDTH * 2, 0, HEIGHT, HEIGHT);
        lavaTile = sheet.crop(WIDTH * 3, 0, HEIGHT, HEIGHT);

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
        tree = sheet.crop(0, WIDTH, WIDTH * 2, HEIGHT * 3);

        player = new BufferedImage[14];

        player[0] = sheet.crop(0 * WIDTH, 25 * HEIGHT, WIDTH * 2, HEIGHT * 2);
        player[1] = Utils.flipImage(player[0]);

        player[2] = sheet.crop(2 * WIDTH, 25 * HEIGHT, WIDTH * 2, HEIGHT * 2);
        player[3] = Utils.flipImage(player[2]);

        player[4] = sheet.crop(4 * WIDTH, 25 * HEIGHT, WIDTH * 2, HEIGHT * 2);
        player[5] = sheet.crop(6 * WIDTH, 25 * HEIGHT, WIDTH * 2, HEIGHT * 2);

        player[6] = Utils.flipImage(player[4]);
        player[7] = Utils.flipImage(player[5]);

        player[8] = sheet.crop(0 * WIDTH, 24 * HEIGHT, WIDTH * 2, HEIGHT * 2);
        player[9] = Utils.flipImage(player[8]);

        player[10] = sheet.crop(2 * WIDTH, 24 * HEIGHT, WIDTH * 2, HEIGHT * 2);
        player[11] = Utils.flipImage(player[10]);

        player[12] = sheet.crop(4 * WIDTH, 24 * HEIGHT, WIDTH * 2, HEIGHT * 2);

        player[13] = Utils.flipImage(player[12]);

        // UI
        btn_start = new BufferedImage[2];

        btn_start[0] = sheet.crop(0 * WIDTH, 18 * HEIGHT, 4 * WIDTH, 2 * HEIGHT);
        btn_start[1] = sheet.crop(0 * WIDTH, 20 * HEIGHT, 4 * WIDTH, 2 * HEIGHT);
        
        btn_mp = new BufferedImage[2];

        btn_mp[0] = sheet.crop(4 * WIDTH, 18 * HEIGHT, 4 * WIDTH, 2 * HEIGHT);
        btn_mp[1] = sheet.crop(4 * WIDTH, 20 * HEIGHT, 4 * WIDTH, 2 * HEIGHT);

    }
}
