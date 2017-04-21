/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaengine.game.utils;

import static com.javaengine.game.Game.NAME;
import com.javaengine.game.handlers.Handler;

/**
 *
 * @author leonardo
 */
public class DebugMode {
    private static Handler handler;

    public DebugMode(Handler handler) {
        DebugMode.handler = handler;
    }
    
    public static void debug(DebugLevel level, String msg) {
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

    public static enum DebugLevel {
        INFO, WARNING, SEVERE, NOTSHOW;
    }
}
