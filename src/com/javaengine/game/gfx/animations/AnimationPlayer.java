package com.javaengine.game.gfx.animations;

import java.awt.image.BufferedImage;

/**
 *
 * @author leonardo
 */
public class AnimationPlayer extends Animation {

    private int[] frameIndexes;

    public AnimationPlayer(int animationSpeed, BufferedImage[] frames) {
        super(animationSpeed, frames);
    }

    /**
     *
     * @param isMoving
     * @return
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

    public void setIndexes(int[] indexes) {
        frameIndexes = indexes;
    }
}
