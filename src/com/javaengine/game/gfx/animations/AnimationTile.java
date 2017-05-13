package com.javaengine.game.gfx.animations;

import java.awt.image.BufferedImage;

/**
 * The AnimationTile creates the animation of the tiles.
 *
 * @author leonardo
 */
public class AnimationTile extends Animation {

    /**
     * Initializes the base of the animation.
     *
     * @param animationSpeed The speed of the animation.
     * @param frames The array of frame that'll create the animation.
     */
    public AnimationTile(int animationSpeed, BufferedImage[] frames) {
        super(animationSpeed, frames);
    }

    /**
     * Updates the tile frame.
     */
    @Override
    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (timer > animationSpeed) {
            indexFrame++;
            timer = 0;
            if (indexFrame >= frames.length) {
                indexFrame = 0;
            }
        }
    }

    /**
     * Gets the current tile.
     *
     * @param isMoving
     * @return Returns the image of the tile.
     */
    @Override
    public BufferedImage getCurrentFrame(boolean isMoving) {
        if (isMoving) {
            return frames[indexFrame];
        }
        return frames[0];
    }
}
