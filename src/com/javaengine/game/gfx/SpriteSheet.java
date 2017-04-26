package com.javaengine.game.gfx;

import java.awt.image.BufferedImage;

/**
 * SpriteSheet class is responsible to load the image and crop it.
 * @author leonardo
 */
public class SpriteSheet {

    public BufferedImage sheet;

    /**
     * Loads the image.
     * @param sheet The image of the sprite sheet.
     */
    public SpriteSheet(BufferedImage sheet) {
        this.sheet = sheet;
    }

    /**
     * Crops the sprite sheet, creating a new image.
     * @param x The position X of the sprite.
     * @param y The position Y of the sprite.
     * @param width The Width of the sprite.
     * @param height The Height of the sprite.
     * @return Returns the image of the sprite cropped.
     */
    public BufferedImage crop(int x, int y, int width, int height) {
        return sheet.getSubimage(x, y, width, height);
    }
}
