/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaengine.game.menus;

import com.javaengine.game.handlers.Handler;
import java.awt.Graphics;
import java.util.HashMap;

/**
 * Create the base of the Menu. Also controls which menu to show.
 *
 * @author leonardo
 */
public abstract class Menu {

    // Static
    public static Menu currentMenu = null;
    public static HashMap<String, Menu> menus = new HashMap<>();

    /**
     * Sets the new current Menu.
     *
     * @param menu The menu.
     */
    public static void setMenu(String menu) {
        if (currentMenu != null) {
            currentMenu.stopMouseListener();
        }
        currentMenu = menus.get(menu);
        currentMenu.startMouseListener();
    }

    public static void addMenu(Menu menu) {
        menus.put(menu.ID, menu);
    }
    // Class
    protected Handler handler;
    public final String ID;

    /**
     * Create the base of the menu.
     *
     * @param handler The handler of the game.
     * @param id the Id of the game.
     */
    public Menu(Handler handler, String id) {
        this.handler = handler;
        this.ID = id;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract void startMouseListener();

    public void stopMouseListener() {
        handler.getMouseManager().setUIManager(null);
    }

}
