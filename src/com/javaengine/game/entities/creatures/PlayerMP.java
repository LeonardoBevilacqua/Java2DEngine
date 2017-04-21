package com.javaengine.game.entities.creatures;

import com.javaengine.game.handlers.Handler;
import java.net.InetAddress;

public class PlayerMP extends Player {

    public InetAddress ipAddress;
    public int port;

//    public PlayerMP(Handler handler, int x, int y, String username, InetAddress ipAddress, int port) {
//        super(handler, x, y, username);
//        this.ipAddress = ipAddress;
//        this.port = port;
//    }
    
    public PlayerMP(Handler handler, int x, int y, String username, InetAddress ipAddress, int port, boolean hasInput) {
        super(handler, x, y, username);
        this.ipAddress = ipAddress;
        this.port = port;
        this.hasInput = hasInput;
    }
}
