package com.javaengine.game.level.tiles;

import com.javaengine.game.gfx.animations.Animation;
import com.javaengine.game.gfx.animations.AnimationTile;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class AnimatedTile extends Tile {

    private AnimationTile animation;

    public AnimatedTile(int id, BufferedImage[] tile) {
        super(id, tile);
        animation = new AnimationTile(800, tile);
    }

    @Override
    public void tick() {
        animation.tick();
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(getCurrentAnimationFrame(), x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    private BufferedImage getCurrentAnimationFrame() {
        return animation.getCurrentFrame(true);
    }

}
