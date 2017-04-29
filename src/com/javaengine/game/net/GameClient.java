package com.javaengine.game.net;

import com.javaengine.game.entities.Entity;
import com.javaengine.game.entities.creatures.PlayerMP;
import com.javaengine.game.gfx.Assets;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.net.packets.Packet;
import com.javaengine.game.net.packets.*;
import com.javaengine.game.states.DomingaoGameState;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import javax.swing.JOptionPane;

public class GameClient extends Thread {

    private InetAddress ipAddress;
    private DatagramSocket socket;
    private Handler handler;

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

            case START_DOMINGO:
                packet = new Packet04StartDomingo(data);
                this.handleStart((Packet04StartDomingo) packet);
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
//        System.out.println("[" + address.getHostAddress() + ":" + port + "] "
//                + packet.getUsername() + " has joined the game...");

        handler.getLevel().setMsg(packet.getUsername() + " has joined the game...");
        handler.getLevel().setTimer(System.currentTimeMillis() + 5000);

        PlayerMP player = new PlayerMP(handler, packet.getUsername(), Assets.player, address, port, false, packet.getUserId());
        player.setPosition(packet.getX(), packet.getY());

        handler.getLevel().getEntityManager().addEntity(player);
    }

    private void handleDisconnection(Packet01Disconnect packet, InetAddress address, int port) {
//        System.out.println("[" + address.getHostAddress() + ":" + port + "] "
//                + ((Packet01Disconnect) packet).getUsername() + " has left the world...");

        handler.getLevel().setMsg(((Packet01Disconnect) packet).getUsername() + " has left the world...");
        handler.getLevel().setTimer(System.currentTimeMillis() + 10000);

        handler.getLevel().getEntityManager().removePlayerMP(((Packet01Disconnect) packet).getUserId());
    }

    private void handleMove(Packet02Move packet) {
        this.handler.getLevel().getEntityManager().movePlayer(packet.getUserId(), packet.getX(), packet.getY(), packet.getNumSteps(), packet.isIsMoving(), packet.getMovingDir(), packet.isIsAttacking());
    }

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

    private void handleStart(Packet04StartDomingo packet) {

        DomingaoGameState s = (DomingaoGameState) com.javaengine.game.states.State.getCurrentState();
        if (packet.getJogadores() >= 2) {
            s.started = packet.isStart();
            s.minute = packet.getMinute();
            s.second = packet.getSecond();
        }
        s.jogadores = packet.getJogadores();
    }
}
