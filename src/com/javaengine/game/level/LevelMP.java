package com.javaengine.game.level;

import com.javaengine.game.entities.creatures.PlayerMP;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.net.GameClient;
import com.javaengine.game.net.GameServer;
import com.javaengine.game.net.packets.Packet00Login;
import javax.swing.JOptionPane;

public class LevelMP extends Level {


    // MP
    private GameClient socketClient;
    private GameServer socketServer;

    public LevelMP(Handler handler, String path, boolean isMultiplayer) {
        super(handler, path,isMultiplayer);

        // temporary
        if (JOptionPane.showConfirmDialog(handler.getGame().getDisplay().getFrame(), "Do you want to run the server?") == 0) {

            socketServer = new GameServer(handler);
            socketServer.start();
            handler.setSocketServer(socketServer);
        }

        socketClient = new GameClient(handler, "localhost");
        socketClient.start();
        handler.setSocketClient(socketClient);

        

        
        Packet00Login loginPacket = new Packet00Login(player.getUsername(), player.getX(), player.getY());
        if (handler.getSocketServer() != null) {
            handler.getSocketServer().addConnection((PlayerMP) player, loginPacket);
        }
        loginPacket.writeData(handler.getSocketClient());

    }

}
