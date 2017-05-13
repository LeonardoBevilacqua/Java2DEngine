package com.javaengine.game.level;

import com.javaengine.game.entities.creatures.Player;
import com.javaengine.game.entities.creatures.PlayerMP;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.net.GameClient;
import com.javaengine.game.net.GameServer;
import com.javaengine.game.net.packets.Packet00Login;
import javax.swing.JOptionPane;

/**
 * Create a world with multiplayer;
 *
 * @author leonardo
 */
public abstract class LevelMP extends Level {

    // MP
    private GameClient socketClient;
    private GameServer socketServer;
    private String ipAdress = "localhost";

    /**
     * Initializes the base of the world and start the multiplayer.
     *
     * @param handler The handler of the game.
     * @param path The path of the world.
     * @param player The main player.
     */
    public LevelMP(Handler handler, String path, Player player) {
        super(handler, path, player);
        initServer();
    }

    /**
     * Initializes the multiplayer.
     */
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
