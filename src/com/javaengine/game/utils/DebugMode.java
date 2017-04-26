/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaengine.game.utils;

import static com.javaengine.game.Game.NAME;
import com.javaengine.game.handlers.Handler;

/**
 * DebugMode class allows to manage debug states, showing a message and if
 * necessary stopping the game.
 *
 * @author leonardo
 */
public class DebugMode {

    private static Handler handler;

    /**
     * The constructor needs a handler to have access of the components of the
     * game.
     *
     * @param handler The handler of the game.
     */
    public DebugMode(Handler handler) {
        DebugMode.handler = handler;
    }

    /**
     * Prints a message in the console showing the level of debug. 
     * INFO level will appear if debug is set to true; 
     * WARNING level will always appear;
     * SEVERE level will show the message and stop the game.
     *
     * @param level The level of debug.
     * @param msg The message that must show.
     */
    public void debug(DebugLevel level, String msg) {
        switch (level) {
            default:
            case INFO:
                if (handler.isDebug()) {
                    System.out.println("[" + NAME + "] " + msg);
                }
                break;
            case WARNING:
                System.out.println("[" + NAME + "][WARNING] " + msg);
                break;
            case SEVERE:
                System.out.println("[" + NAME + "][SEVERE] " + msg);
                handler.getGame().stop();
                break;
            case NOTSHOW:
                break;
        }
    }

    /**
     * DebugLevel enum keeps all the levels of debug.
     */
    public static enum DebugLevel {
        INFO, WARNING, SEVERE, NOTSHOW;
    }
}
