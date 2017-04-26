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
 *
 * @author leonardo
 */
public class ItemManager {
    
    private Handler handler;
    private ArrayList<Item> items;
    
    public ItemManager(Handler handler){
        this.handler = handler;
        items = new ArrayList<>();
    }
    
    public void tick(){
        Iterator<Item> it = items.iterator();
        while(it.hasNext()){
            Item i = it.next();
            i.tick();
            if(i.isPickUp()){
                it.remove();
            }
        }
    }
    
    public void render(Graphics g){
        for (Item i : items) {
            i.render(g);
        }
    }
    
    public void addItem(Item i){
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
