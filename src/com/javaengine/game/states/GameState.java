package com.javaengine.game.states;

import com.javaengine.game.entities.creatures.PlayerMP;
import com.javaengine.game.gfx.Assets;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.level.Level;
import com.javaengine.game.level.world.Level1;
import com.javaengine.game.utils.Utils;
import java.awt.Graphics;

/**
 * The GameState is responsible to manage the game.
 *
 * @author leonardo
 */
public class GameState extends State {

    private Level level;

    /**
     * Initialize the game
     *
     * @param handler The handler of the game.
     */
    public GameState(Handler handler) {
        super(handler);

        level = new Level1(
                handler,
                "/levels/level1.txt",
                new PlayerMP(
                        handler,
                        "teste",
                        Assets.player,
                        null, -1,
                        true,
                        Utils.getUniqueId()
                )
        );
        handler.setLevel(level);
    }

    /**
     * Updates the game logic.
     */
    @Override
    public void tick() {
        level.tick();
    }

    /**
     * Draw the game.
     *
     * @param g The graphics object.
     */
    @Override
    public void render(Graphics g) {
        level.render(g);
    }

    // getters and setters
    public Level getLevel() {
        return level;
    }

}
