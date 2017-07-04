package com.javaengine.game.net;

import com.javaengine.game.entities.Entity;
import com.javaengine.game.entities.creatures.PlayerMP;
import com.javaengine.game.gfx.Assets;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.net.packets.Packet;
import com.javaengine.game.net.packets.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 * The GameClient is responsible to send the data of the to the player.
 *
 * @author leonardo
 */
public class GameClient extends Thread {

    private InetAddress ipAddress;
    private DatagramSocket socket;
    private Handler handler;

    /**
     * Create the connection with the server.
     *
     * @param handler The handler of the game.
     * @param ipAddress the Ip address of the server.
     */
    public GameClient(Handler handler, String ipAddress) {
        this.handler = handler;

        try {
            this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (SocketException | UnknownHostException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.exit(1);
        }
    }

    /**
     * Runs the thread.
     */
    @Override
    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);

            try {
                socket.receive(packet);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
        }
    }

    /**
     * Gets the data and choose the right packet.
     *
     * @param data The array of byte with the data.
     * @param address The Ip address.
     * @param port The port.
     */
    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookupPacket(message.substring(0, 2));

        Packet packet;

        switch (type) {
            default:
            case INVALID:
                break;
            case LOGIN:
                packet = new Packet00Login(data);
                handleLogin((Packet00Login) packet, address, port);

                break;
            case DISCONNECT:
                packet = new Packet01Disconnect(data);
                handleDisconnection((Packet01Disconnect) packet, address, port);

                break;
            case MOVE:
                packet = new Packet02Move(data);
                handleMove(((Packet02Move) packet));

                break;
            case LEVEL_UPDATE:
                packet = new Packet03LevelUpdate(data);
                handleUpdate(((Packet03LevelUpdate) packet));

                break;
        }

    }

    /**
     * Send the data.
     *
     * @param data The array of byte with the data.
     */
    public void sendData(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);

        try {
            socket.send(packet);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the login on the client side.
     *
     * @param packet The Login packet.
     * @param address The Ip address.
     * @param port The port.
     */
    private void handleLogin(Packet00Login packet, InetAddress address, int port) {

        // Show a message.
        handler.getLevel().setMsg(packet.getUsername() + " has joined the game...");
        handler.getLevel().setTimer(System.currentTimeMillis() + 5000);

        PlayerMP player = new PlayerMP(
                handler,
                packet.getUsername(),
                Assets.player,
                address,
                port,
                false,
                packet.getUserId()
        );

        player.setPosition(packet.getX(), packet.getY());

        handler.getLevel().getEntityManager().addEntity(player);
    }

    /**
     * Handles the disconnection on the client side.
     *
     * @param packet The disconnection packet.
     * @param address The Ip address.
     * @param port The port.
     */
    private void handleDisconnection(Packet01Disconnect packet, InetAddress address, int port) {
        handler.getLevel().setMsg(((Packet01Disconnect) packet).getUsername() + " has left the world...");
        handler.getLevel().setTimer(System.currentTimeMillis() + 10000);

        handler.getLevel().getEntityManager().removePlayerMP(((Packet01Disconnect) packet).getUserId());
    }

    /**
     * Handles the moviment of the entities on the client side.
     *
     * @param packet The move packet.
     */
    private void handleMove(Packet02Move packet) {
        this.handler.getLevel().getEntityManager().movePlayer(
                packet.getUserId(),
                packet.getX(),
                packet.getY(),
                packet.getNumSteps(),
                packet.isIsMoving(),
                packet.getMovingDir(),
                packet.isIsAttacking()
        );
    }

    /**
     * Handles the update of the entities on the client side.
     *
     * @param packet The update packet.
     */
    private void handleUpdate(Packet03LevelUpdate packet) {
        Iterator<Entity> it = this.handler.getLevel().getEntityManager().getEntities().iterator();
        while (it.hasNext()) {
            Entity e = it.next();
            if (e.getUniqueId().equals(packet.getId())) {
                e.setActive(packet.isActive());
                e.setHealth(packet.getHealth());
            }
        }
    }
}
