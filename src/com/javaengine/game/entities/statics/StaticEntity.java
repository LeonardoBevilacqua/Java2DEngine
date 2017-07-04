/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaengine.game.entities.statics;

import com.javaengine.game.entities.Entity;
import com.javaengine.game.handlers.Handler;

/**
 * Create the base of statics entities
 *
 * @author leonardo
 */
public abstract class StaticEntity extends Entity {

    /**
     * Create the base of the static entity.
     *
     * @param handler The handler of the game.
     * @param x The x position.
     * @param y The y position.
     * @param width The width.
     * @param height The height.
     * @param uniqueId The id of the entity.
     */
    public StaticEntity(Handler handler, int x, int y, int width, int height, String uniqueId) {
        super(handler, width, height);
        this.id = uniqueId;
        this.setPosition(x, y);
    }

}
