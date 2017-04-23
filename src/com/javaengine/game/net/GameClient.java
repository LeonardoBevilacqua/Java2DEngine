package com.javaengine.game.net;

import com.javaengine.game.entities.creatures.PlayerMP;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.net.packets.Packet;
import com.javaengine.game.net.packets.Packet00Login;
import com.javaengine.game.net.packets.Packet01Disconnect;
import com.javaengine.game.net.packets.Packet02Move;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GameClient extends Thread {

    private InetAddress ipAddress;
    private DatagramSocket socket;
    private Handler handler;

    public GameClient(Handler handler, String ipAddress) {
        this.handler = handler;

        try {
            this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

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
            //String message = new String(packet.getData());
            //System.out.println("SERVER > " + message);
        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookupPacket(message.substring(0, 2));

        Packet packet = null;

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

                System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                        + ((Packet01Disconnect) packet).getUsername() + " has left the world...");

                handler.getLevel().getEntityManager().removePlayerMP(((Packet01Disconnect) packet).getUsername());

                break;
            case MOVE:
                packet = new Packet02Move(data);
                handleMove(((Packet02Move) packet));

                break;
        }

    }

    public void sendData(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);

        try {
            socket.send(packet);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void handleLogin(Packet00Login packet, InetAddress address, int port) {
        System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                + packet.getUsername() + " has joined the game...");

        PlayerMP player = new PlayerMP(handler, packet.getX(), packet.getY(), packet.getUsername(), address, port,false);

        handler.getLevel().getEntityManager().addEntity(player);
    }

    private void handleMove(Packet02Move packet) {
        this.handler.getLevel().getEntityManager().movePlayer(packet.getUsername(), packet.getX(), packet.getY(), packet.getNumSteps(), packet.isIsMoving(), packet.getMovingDir(),packet.isIsAttacking());
    }
}
