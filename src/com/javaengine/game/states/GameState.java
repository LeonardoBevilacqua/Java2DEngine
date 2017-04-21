package com.javaengine.game.states;

import com.javaengine.game.handlers.Handler;
import com.javaengine.game.level.Level;
import java.awt.Graphics;

/**
 *
 * @author leonardo
 */
public class GameState extends State {

    private Level level;

    public GameState(Handler handler) {
        super(handler);

        level = new Level(handler, "res/levels/level1.txt", false);
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
