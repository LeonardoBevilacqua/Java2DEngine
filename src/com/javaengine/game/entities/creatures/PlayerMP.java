package com.javaengine.game.entities.creatures;

import com.javaengine.game.handlers.Handler;
import java.awt.image.BufferedImage;
import java.net.InetAddress;

public class PlayerMP extends Player {

    public InetAddress ipAddress;
    public int port;

    public PlayerMP(
            Handler handler,
            String username,
            BufferedImage[] texture,
            InetAddress ipAddress,
            int port,
            boolean hasInput,
            String uniqueId
    ) {
        super(handler, username, texture);
        this.id = uniqueId;
        this.ipAddress = ipAddress;
        this.port = port;
        this.hasInput = hasInput;
    }
}
