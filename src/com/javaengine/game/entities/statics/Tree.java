/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaengine.game.entities.statics;

import com.javaengine.game.gfx.Assets;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.items.Item;
import com.javaengine.game.level.tiles.Tile;
import java.awt.Graphics;

/**
 *
 * @author leonardo
 */
public class Tree extends StaticEntity {

    public Tree(Handler handler, int x, int y, String uniqueId) {
        super(handler, x, y, Tile.TILE_WIDTH * 2, Tile.TILE_HEIGHT * 3, uniqueId);

        bounds.x = 24; // espaço em pixel * 4
        bounds.y = 38;
        bounds.width = 20;
        bounds.height = 42;
    }

    @Override
    public void die() {
        handler.getLevel().getItemManager().addItem(Item.woodItem.createNew(x, y));
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        g.drawImage(Assets.tree, x - handler.getGameCamera().getxOffset(), y - handler.getGameCamera().getyOffset(), width, height, null);
//        g.drawRect((x + bounds.x) - handler.getGameCamera().getxOffset(),
//                (y + bounds.y) - handler.getGameCamera().getyOffset(),
//                bounds.width, bounds.height);
    }

}
