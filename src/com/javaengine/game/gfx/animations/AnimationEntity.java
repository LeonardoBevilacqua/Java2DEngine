package com.javaengine.game.gfx.animations;

import java.awt.image.BufferedImage;

/**
 * The AnimationEntity creates the animation of the entity.
 *
 * @author leonardo
 */
public class AnimationEntity extends Animation {

    private int[] frameIndexes;

    /**
     * Initializes the base of the animation.
     *
     * @param animationSpeed The speed of the animation.
     * @param frames The array of frame that'll create the animation.
     */
    public AnimationEntity(int animationSpeed, BufferedImage[] frames) {
        super(animationSpeed, frames);
    }

    /**
     * Gets the current tile.
     *
     * @param isMoving Verifies if the entity is moving.
     * @return Returns the current frame.
     */
    @Override
    public BufferedImage getCurrentFrame(boolean isMoving) {
        if (isMoving) {
            return frames[indexFrame];

        } else if (!isMoving && frameIndexes != null) {
            return frames[frameIndexes[0]];
        }

        return frames[3];
    }

    /**
     * Updates the frame of the entity.
     */
    @Override
    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (timer > animationSpeed) {

            if (frameIndexes.length == 1) {
                indexFrame = frameIndexes[0];
            } else {
                indexFrame = frameIndexes[index++];
            }
            timer = 0;

            if (index >= frameIndexes.length) {
                index = 0;
            }
        }
    }

    /**
     * Sets the indexes of the animation.
     *
     * @param indexes The array of indexes.
     */
    public void setIndexes(int[] indexes) {
        frameIndexes = indexes;
    }
}
