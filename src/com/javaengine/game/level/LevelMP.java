package com.javaengine.game.level;

import com.javaengine.game.entities.creatures.Player;
import com.javaengine.game.entities.creatures.PlayerMP;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.net.GameClient;
import com.javaengine.game.net.GameServer;
import com.javaengine.game.net.packets.Packet00Login;
import javax.swing.JOptionPane;

public abstract class LevelMP extends Level {

    // MP
    private GameClient socketClient;
    private GameServer socketServer;
    private String ipAdress = "localhost";

    public LevelMP(Handler handler, String path, Player player) {
        super(handler, path, player);
        initServer();
    }

    protected void initServer() {
        // temporary
        if (JOptionPane.showConfirmDialog(handler.getGame().getDisplay().getFrame(), "Do you want to run the server?") == 0) {

            socketServer = new GameServer(handler);
            socketServer.start();
            handler.setSocketServer(socketServer);

        }
        ipAdress = JOptionPane.showInputDialog("ENDEREÇO:");

        socketClient = new GameClient(handler, ipAdress);
        socketClient.start();

        handler.setSocketClient(socketClient);

        Packet00Login loginPacket = new Packet00Login(player.getUniqueId(), player.getX(), player.getY(), player.getUsername());
        if (handler.getSocketServer() != null) {
            handler.getSocketServer().addConnection((PlayerMP) player, loginPacket);
        }

        loginPacket.writeData(handler.getSocketClient());
    }
}
