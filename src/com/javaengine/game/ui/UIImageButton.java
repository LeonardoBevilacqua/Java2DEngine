package com.javaengine.game.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * The UIImageButton can create a button with a image.
 *
 * @author leonardo
 */
public class UIImageButton extends UIObject {

    private BufferedImage[] images;
    private ClickListener clicker;

    /**
     * The constructor create the image and the bounds of the button.
     *
     * @param x The x position.
     * @param y The y position.
     * @param width The width of the button.
     * @param height The height of the button.
     * @param images The array of images.
     * @param clicker The action of the button when clicked.
     */
    public UIImageButton(
            int x,
            int y, 
            int width, 
            int height, 
            BufferedImage[] images,
            ClickListener clicker
    ) {
        super(x, y, width, height);
        this.images = images;
        this.clicker = clicker;
    }

    /**
     * Update of the Button.
     */
    @Override
    public void tick() {

    }

    /**
     * Draw the button.
     * @param g The graphics object.
     */
    @Override
    public void render(Graphics g) {
        if (hovering) {
            g.drawImage(images[1], x, y, width, height, null);
        } else {
            g.drawImage(images[0], x, y, width, height, null);
        }
    }

    /**
     * Do the action set on the clickListener.
     */
    @Override
    public void onClick() {
        clicker.onClick();
    }

}
