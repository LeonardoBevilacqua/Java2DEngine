/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaengine.game.inventory;

import com.javaengine.game.gfx.Assets;
import com.javaengine.game.gfx.Text;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.items.Item;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * The Invetory class is responsible to manage the entity items.
 *
 * @author leonardo
 */
public class Inventory {

    private Handler handler;
    private boolean active;
    private ArrayList<Item> inventoryItems;

    private int invX = 0, invY = 0,
            invWidth = 512, invHeight = 384,
            invListCenterX = invX + 171, invListCenterY = invY + invHeight / 2 + 5,
            invListSpacing = 30;

    private int invImageX = 390, invImageY = 30,
            invImageWidth = 64, invImageHeight = 64;

    private int invCountX = 420, invCountY = 120;

    private int selectedItem = 0;

    /**
     * Initialize the inventory.
     *
     * @param handler The handler of the game.
     */
    public Inventory(Handler handler) {
        this.handler = handler;
        active = false;
        inventoryItems = new ArrayList<>();
    }

    /**
     * updates the inventory.
     */
    public void tick() {

        active = handler.getInput().inventory.wasJustPressed();
        if (!active) {
            return;
        }

        if (handler.getInput().inventoryUp.isPressed()) {
            handler.getInput().inventoryUp.toggle(false);
            selectedItem--;
        }
        if (handler.getInput().inventoryDown.isPressed()) {
            handler.getInput().inventoryDown.toggle(false);
            selectedItem++;
        }

        if (selectedItem < 0) {
            selectedItem = inventoryItems.size() - 1;
        } else if (selectedItem >= inventoryItems.size()) {
            selectedItem = 0;
        }
    }

    /**
     * Renders the inventory.
     *
     * @param g The graphic object.
     */
    public void render(Graphics g) {
        if (!active) {
            return;
        }

        g.drawImage(Assets.inventoryScreen, invX, invY, invWidth, invHeight, null);

        int len = inventoryItems.size();
        if (len == 0) {
            return;
        }

        for (int i = -5; i < 6; i++) {
            if (selectedItem + i < 0 || selectedItem + i >= len) {
                continue;
            }

            if (i == 0) {
                Text.drawString(g, "> " + inventoryItems.get(selectedItem + i).getName() + " <", invListCenterX,
                        invListCenterY + i * invListSpacing, true, Color.YELLOW, Assets.font28);

            } else {
                Text.drawString(g, inventoryItems.get(selectedItem + i).getName(), invListCenterX,
                        invListCenterY + i * invListSpacing, true, Color.WHITE, Assets.font28);
            }

        }

        Item item = inventoryItems.get(selectedItem);
        g.drawImage(item.getTexture(), invImageX, invImageY, invImageWidth, invImageHeight, null);
        Text.drawString(g, Integer.toString(item.getCount()), invCountX, invCountY, true, Color.WHITE, Assets.font28);

    }

    // Inventory methods
    /**
     * Adds a item in the inventory.
     *
     * @param item The inventory object.
     */
    public void addItem(Item item) {
        for (Item i : inventoryItems) {
            if (i.getId() == item.getId()) {
                i.setCount(i.getCount() + item.getCount());
                return;
            }
        }
        inventoryItems.add(item);
    }

    // getters and setters
    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public boolean isActive() {
        return active;
    }

}
