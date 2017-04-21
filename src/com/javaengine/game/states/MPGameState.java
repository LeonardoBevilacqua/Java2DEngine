package com.javaengine.game.states;

import com.javaengine.game.handlers.Handler;
import com.javaengine.game.level.Level;
import com.javaengine.game.level.LevelMP;
import java.awt.Graphics;

/**
 *
 * @author leonardo
 */
public class MPGameState extends State {

    private Level level;

    public MPGameState(Handler handler) {
        super(handler);
        level = new LevelMP(handler, "res/levels/level1.txt", true);
        handler.setLevel(level);

    }

    @Override
    public void tick() {
        level.tick();
    }

    @Override
    public void render(Graphics g) {
        level.render(g);
    }

    // getters and setters
    public Level getLevel() {
        return level;
    }

}
