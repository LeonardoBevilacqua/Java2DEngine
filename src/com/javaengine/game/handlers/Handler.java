package com.javaengine.game.handlers;

import com.javaengine.game.Game;
import com.javaengine.game.gfx.GameCamera;
import com.javaengine.game.handlers.input.KeyManager;
import com.javaengine.game.handlers.input.MouseManager;
import com.javaengine.game.level.Level;
import com.javaengine.game.net.GameClient;
import com.javaengine.game.net.GameServer;
import com.javaengine.game.states.State;

/**
 * The handler class is responsible to manage all the important components of the game.
 * @author leonardo
 */
public class Handler {

    /**
     * Stores the Game object.
     */
    private Game game;
    /**
     * Stores the Level object.
     */
    private Level level;
    /**
     * Stores the Server socket object.
     */
    private GameServer socketServer;
    /**
     * Stores the Client socket object.
     */
    private GameClient socketClient;

    /**
     * The constructor needs a object of the game to have access to the components.
     * @param game The object of the class game.
     */
    public Handler(Game game) {
        this.game = game;
    }

    /**
     * 
     * @return Returns the object of the game's camera.
     */
    public GameCamera getGameCamera() {
        return game.getGameCamera();
    }

    /**
     * 
     * @return Returns the object of the keyboard input.
     */
    public KeyManager getInput() {
        return game.getKeyManager();
    }

    /**
     * 
     * @return Returns the width of the game.
     */
    public int getWidth() {
        return game.getWidth();
    }

    /**
     * 
     * @return Returns the height of the game.
     */
    public int getHeight() {
        return game.getHeight();
    }

    /**
     * 
     * @return Returns if the game is on debug mode.
     */
    public boolean isDebug(){
        return Game.DEBUG;
    }
    
    /**
     * 
     * @return Returns the object of the game.
     */
    public Game getGame() {
        return game;
    }

    /**
     * 
     * @param game Set the game object.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * 
     * @return Returns the Level object.
     */
    public Level getLevel() {
        return level;
    }

    /**
     * 
     * @param level Set the level object.
     */
    public void setLevel(Level level) {
        this.level = level;
    }
    
    /**
     * 
     * @return Returns the server socket.
     */
    public GameServer getSocketServer() {
        return socketServer;
    }

    /**
     * 
     * @param socketServer Set the server socket.
     */
    public void setSocketServer(GameServer socketServer) {
        this.socketServer = socketServer;
    }

    /**
     * 
     * @return Returns the client socket.
     */
    public GameClient getSocketClient() {
        return socketClient;
    }

    /**
     * 
     * @param socketClient Set the client socket.
     */
    public void setSocketClient(GameClient socketClient) {
        this.socketClient = socketClient;
    }
    
    /**
     * 
     * @return Returns the object of the mouse input.
     */
    public MouseManager getMouseManager(){
        return game.getMouseManager();
    }
    
    /**
     * 
     * @return Returns the object of the menu state.
     */
    public State getMenuState(){
        return game.getMenuState();
    }
}
