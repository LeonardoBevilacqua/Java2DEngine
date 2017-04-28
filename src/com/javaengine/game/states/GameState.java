package com.javaengine.game.states;

import com.javaengine.game.entities.creatures.PlayerMP;
import com.javaengine.game.gfx.Assets;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.level.Level;
import com.javaengine.game.level.world.Level1;
import com.javaengine.game.utils.Utils;
import java.awt.Graphics;

/**
 *
 * @author leonardo
 */
public class GameState extends State {

    private Level level;

    public GameState(Handler handler) {
        super(handler);

       level = new Level1(handler, "/levels/level1.txt",new PlayerMP(handler, 0, 0, "teste", Assets.player, null, -1, true, Utils.getUniqueId()));
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
