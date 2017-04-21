package com.javaengine.game.handlers;

import com.javaengine.game.handlers.input.InputHandler;
import com.javaengine.game.Game;
import com.javaengine.game.entities.creatures.Player;
import com.javaengine.game.gfx.GameCamera;
import com.javaengine.game.handlers.input.MouseManager;
import com.javaengine.game.level.Level;
import com.javaengine.game.net.GameClient;
import com.javaengine.game.net.GameServer;
import com.javaengine.game.states.State;

/**
 *
 * @author leonardo
 */
public class Handler {

    private Game game;
    private Level level;
    private GameServer socketServer;
    private GameClient socketClient;

    public Handler(Game game) {
        this.game = game;
    }

    public GameCamera getGameCamera() {
        return game.getGameCamera();
    }

    public InputHandler getInput() {
        return game.getInput();
    }

    public int getWidth() {
        return game.getWidth();
    }

    public int getHeight() {
        return game.getHeight();
    }

    public boolean isDebug(){
        return game.isDebug();
    }
    
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
    
    public GameServer getSocketServer() {
        return socketServer;
    }

    public void setSocketServer(GameServer socketServer) {
        this.socketServer = socketServer;
    }

    public GameClient getSocketClient() {
        return socketClient;
    }

    public void setSocketClient(GameClient socketClient) {
        this.socketClient = socketClient;
    }
    
    public MouseManager getMouseManager(){
        return game.getMouseManager();
    }
    
//    public State getGameState(){
//        return game.getGameState();
//    }
    
    public State getMenuState(){
        return game.getMenuState();
    }
    
//    public State getMPGameState(){
//        return game.getMPGameState();
//    }

}
