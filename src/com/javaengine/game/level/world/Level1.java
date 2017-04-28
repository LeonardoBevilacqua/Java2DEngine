/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaengine.game.level.world;

import com.javaengine.game.entities.EntityManager;
import com.javaengine.game.entities.creatures.Player;
import com.javaengine.game.entities.statics.Rock;
import com.javaengine.game.entities.statics.Tree;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.level.Level;

/**
 *
 * @author leonardo
 */
public class Level1 extends Level {

    public Level1(Handler handler, String path, Player player) {
        super(handler, path, player);
        

        for (int i = 0; i < 3; i++) {
            entityManager.addEntity(new Tree(handler, 100 * (i + 1), 50, "T" + i));
            entityManager.addEntity(new Rock(handler, 120 * (i + 1), 180, "R" + i));
        }
    }

}
