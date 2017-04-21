package com.javaengine.game.gfx.animations;

import java.awt.image.BufferedImage;

/**
 *
 * @author leonardo
 */
public abstract class Animation {

    protected int animationSpeed, indexFrame, index;
    protected long lastTime, timer;
    protected BufferedImage[] frames;

    public Animation(int animationSpeed, BufferedImage[] frames) {
        this.animationSpeed = animationSpeed;
        this.frames = frames;
        indexFrame = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    public abstract BufferedImage getCurrentFrame(boolean isMoving);

    public abstract void tick();
}
