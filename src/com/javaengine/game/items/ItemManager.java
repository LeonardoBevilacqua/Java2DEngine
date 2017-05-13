/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaengine.game.items;

import com.javaengine.game.handlers.Handler;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The ItemManager is responsible to control the items.
 *
 * @author leonardo
 */
public class ItemManager {

    private Handler handler;
    private ArrayList<Item> items;

    /**
     * Initializes the manager.
     *
     * @param handler The handler of the game.
     */
    public ItemManager(Handler handler) {
        this.handler = handler;
        items = new ArrayList<>();
    }

    /**
     * Updates the items.
     */
    public void tick() {
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            Item i = it.next();
            i.tick();
            if (i.isPickUp()) {
                it.remove();
            }
        }
    }

    /**
     * Renders the item.
     *
     * @param g The graphic object.
     */
    public void render(Graphics g) {
        for (Item i : items) {
            i.render(g);
        }
    }

    /**
     * Adds a new item in the manager.
     *
     * @param i The item object.
     */
    public void addItem(Item i) {
        i.setHandler(handler);
        items.add(i);
    }

    // getters and setters
    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

}
