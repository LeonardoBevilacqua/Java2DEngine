package com.javaengine.game.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author leonardo
 */
public class UIImageButton extends UIObject {

    private BufferedImage[] images;
    private ClickListener clicker;

    public UIImageButton(int x, int y, int width, int height, BufferedImage[] images, ClickListener clicker) {
        super(x, y, width, height);
        this.images = images;
        this.clicker = clicker;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        if (hovering) {
            g.drawImage(images[1], x, y, width, height, null);
        } else {
            g.drawImage(images[0], x, y, width, height, null);
        }
    }

    @Override
    public void onClick() {
        clicker.onClick();
    }

}
