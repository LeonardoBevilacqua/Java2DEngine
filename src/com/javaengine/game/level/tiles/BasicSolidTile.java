package com.javaengine.game.level.tiles;

import java.awt.image.BufferedImage;

public class BasicSolidTile extends BasicTile{
    
    public BasicSolidTile(int id, BufferedImage tile) {
        super(id, tile);
    }
    
    @Override
    public boolean isSolid(){
        return true;
    }
    
}
