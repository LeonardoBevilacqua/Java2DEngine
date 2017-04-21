package com.javaengine.game.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private String path;
    public int width;
    public int height;
    public BufferedImage sheet;

    public SpriteSheet(String path) {
        BufferedImage image = null;

        image = ImageLoader.loadImage(path);

        if (image == null) {
            return;
        }

        this.sheet = image;
        this.path = path;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public SpriteSheet(BufferedImage sheet) {
        this.sheet = sheet;
    }

    public BufferedImage crop(int x, int y, int width, int height) {
        return sheet.getSubimage(x, y, width, height);
    }
}
