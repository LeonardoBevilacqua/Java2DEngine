/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaengine.game.entities.statics;

import com.javaengine.game.entities.Entity;
import com.javaengine.game.handlers.Handler;

/**
 *
 * @author leonardo
 */
public abstract class StaticEntity  extends Entity{

    public StaticEntity(Handler handler, int x, int y, int width, int height) {
        super(handler, x, y, width, height);
    }
    
    
}
