package com.javaengine.game.gfx.animations;

import java.awt.image.BufferedImage;

/**
 *
 * @author leonardo
 */
public class AnimationTile extends Animation{

    public AnimationTile(int animationSpeed, BufferedImage[] frames) {
        super(animationSpeed, frames);
    }

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

    @Override
    public BufferedImage getCurrentFrame(boolean isMoving) {
        if (isMoving) {
            return frames[indexFrame];
        }
        return frames[0];
    }
}
