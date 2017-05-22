package com.javaengine.game.states;

import com.javaengine.game.handlers.Handler;
import com.javaengine.game.menus.Menu;
import java.awt.Graphics;

/**
 * The MenuState is responsible to manage the menu.
 *
 * @author leonardo
 */
public class MenuState extends State {

    /**
     * Initilize the menu.
     *
     * @param handler The handler of the game.
     */
    public MenuState(Handler handler) {
        super(handler);
    }

    /**
     * Update everything in the menu state.
     */
    @Override
    public void tick() {
        Menu.currentMenu.tick();
    }

    /**
     * Render everything in the current state.
     *
     * @param g The graphics object.
     */
    @Override
    public void render(Graphics g) {
        Menu.currentMenu.render(g);
    }
}
