package com.javaengine.game.states;

import com.javaengine.game.entities.creatures.PlayerMP;
import com.javaengine.game.gfx.Assets;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.level.Level;
import com.javaengine.game.level.world.LevelMP1;
import com.javaengine.game.utils.Utils;
import java.awt.Graphics;
import javax.swing.JOptionPane;

/**
 * The MPGameState is responsible to manage the game and initialize the
 * multiplayer.
 *
 * @author leonardo
 */
public class MPGameState extends State {

    private Level level;

    /**
     * Initilize the Game.
     *
     * @param handler
     */
    public MPGameState(Handler handler) {
        super(handler);

        level = new LevelMP1(
                handler,
                "/levels/level1.txt",
                new PlayerMP(
                        handler,
                        JOptionPane.showInputDialog("nome"),
                        Assets.player,
                        null,
                        -1,
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
