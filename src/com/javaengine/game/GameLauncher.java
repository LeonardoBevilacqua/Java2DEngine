package com.javaengine.game;
/**
 * GameLauncher class is responsible to initiate the game.
 * @author leonardo
 */
public class GameLauncher {

    public static void main(String[] args) {
        new Game(1024, 1024/16*9, "Game2dEngine", true);
    }
}
