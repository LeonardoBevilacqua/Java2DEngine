package com.javaengine.game.ui;

import com.javaengine.game.handlers.Handler;
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
    private float xPos, yPos;
    private Handler handler;
    private boolean center;

    /**
     * The constructor create the image and the bounds of the button.
     *
     * @param handler The handler of the game.
     * @param x The percent x position.
     * @param y The percent y position.
     * @param center Check if is centered.
     * @param width The width of the button.
     * @param height The height of the button.
     * @param images The array of images.
     * @param clicker The action of the button when clicked.
     */
    public UIImageButton(
            Handler handler,
            float x,
            float y,
            boolean center,
            int width,
            int height,
            BufferedImage[] images,
            ClickListener clicker
    ) {
        super(x, y, width, height);
        this.handler = handler;
        this.images = images;
        this.clicker = clicker;
        this.xPos = x;
        this.yPos = y;
        this.center = center;
    }

    /**
     * Update of the Button.
     */
    @Override
    public void tick() {
        x = bounds.x = (int) ((xPos / 100) * handler.getWidth());
        y = bounds.y = (int) ((yPos / 100) * handler.getHeight());
        if (center) {
            y = bounds.y -= height / 2;
            x = bounds.x -= width / 2;
        }
    }

    /**
     * Draw the button.
     *
     * @param g The graphics object.
     */
    @Override
    public void render(Graphics g) {
        if (hovering) {
            g.drawImage(images[1], (int) x, (int) y, width, height, null);
        } else {
            g.drawImage(images[0], (int) x, (int) y, width, height, null);
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
