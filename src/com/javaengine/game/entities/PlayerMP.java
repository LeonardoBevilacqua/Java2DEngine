package com.javaengine.game.entities;

import com.javaengine.game.InputHandler;
import com.javaengine.game.level.Level;
import java.net.InetAddress;

public class PlayerMP extends Player {

    public InetAddress ipAddress;
    public int port;

    public PlayerMP(Level level, int x, int y, InputHandler input, String username, InetAddress ipAddress, int port) {
        super(level, x, y, input, username);

        this.ipAddress = ipAddress;
        this.port = port;
    }

    public PlayerMP(Level level, int x, int y, String username, InetAddress ipAddress, int port) {
        super(level, x, y, null, username);

        this.ipAddress = ipAddress;
        this.port = port;
    }

    @Override
    public void tick() {
        super.tick(); //To change body of generated methods, choose Tools | Templates.
    }

}
