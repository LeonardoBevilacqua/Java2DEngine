package com.javaengine.game.net;

import com.javaengine.game.entities.Entity;
import com.javaengine.game.entities.creatures.PlayerMP;
import com.javaengine.game.gfx.Assets;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.net.packets.Packet;
import com.javaengine.game.net.packets.Packet.PacketTypes;
import com.javaengine.game.net.packets.Packet00Login;
import com.javaengine.game.net.packets.Packet01Disconnect;
import com.javaengine.game.net.packets.Packet02Move;
import com.javaengine.game.net.packets.Packet03LevelUpdate;
import com.javaengine.game.net.packets.Packet04StartDomingo;
import com.javaengine.game.states.DomingaoGameState;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameServer extends Thread {

    private DatagramSocket socket;
    private Handler handler;
    private List<PlayerMP> connectedPlayers;

    // Domingo temporario
    private long minutes;
    public long minute, second;
    public boolean started = false;

    public GameServer(Handler handler) {
        this.handler = handler;
        connectedPlayers = new ArrayList<>();
        minutes = System.currentTimeMillis() + 120000;

        try {
            this.socket = new DatagramSocket(1331);
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
    }

    public void checkTimer() {
        minute = (minutes - System.currentTimeMillis()) / 60000;
        second = (minutes - System.currentTimeMillis()) / 1000 - minute * 60;
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
        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        PacketTypes type = Packet.lookupPacket(message.substring(0, 2));

        Packet packet = null;

        switch (type) {
            default:
            case INVALID:
                break;
            case LOGIN:
                packet = new Packet00Login(data);

                System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                        + ((Packet00Login) packet).getUsername() + " has connected...");

                PlayerMP player = new PlayerMP(handler, ((Packet00Login) packet).getUsername(), Assets.player, address, port, false, ((Packet00Login) packet).getUserId());
                player.setPosition(20, 20);

                this.addConnection(player, ((Packet00Login) packet));

                break;
            case DISCONNECT:
                packet = new Packet01Disconnect(data);
                System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                        + ((Packet01Disconnect) packet).getUsername() + " has left...");

                this.removeConnection(((Packet01Disconnect) packet));
                break;
            case MOVE:
                packet = new Packet02Move(data);
                this.handleMove(((Packet02Move) packet));
                break;
            case LEVEL_UPDATE:
                packet = new Packet03LevelUpdate(data);
                this.handleUpdate((Packet03LevelUpdate) packet);
                break;

            case START_DOMINGO:
                packet = new Packet04StartDomingo(data);
                this.handleStart((Packet04StartDomingo) packet);
                break;
        }

    }

    public void addConnection(PlayerMP player, Packet00Login packet) {
        boolean alreadyConnected = false;

        for (PlayerMP p : connectedPlayers) {
            if (player.getUniqueId().equalsIgnoreCase(p.getUniqueId())) {
                if (p.ipAddress == null) {
                    p.ipAddress = player.ipAddress;
                }

                if (p.port == -1) {
                    p.port = player.port;
                }
                alreadyConnected = true;
            } else {
                sendData(packet.getData(), p.ipAddress, p.port);
                packet = new Packet00Login(p.getUniqueId(), p.getX(), p.getY(), p.getUsername());
                sendData(packet.getData(), player.ipAddress, player.port);
            }
        }
        if (!alreadyConnected) {
            this.connectedPlayers.add(player);
        }
    }

    public void removeConnection(Packet01Disconnect packet) {
        this.connectedPlayers.remove(getPlayeMPIndex(packet.getUserId()));

        packet.writeData(this);
    }

    public PlayerMP getPlayeMP(String userId) {
        for (PlayerMP player : connectedPlayers) {
            if (player.getUniqueId().equals(userId)) {
                return player;
            }
        }
        return null;
    }

    public int getPlayeMPIndex(String userId) {
        int index = 0;
        for (PlayerMP player : connectedPlayers) {
            if (player.getUniqueId().equals(userId)) {
                break;
            }
            index++;
        }
        return index;
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port) {

        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);

        try {
            this.socket.send(packet);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void sendDataToAllClients(byte[] data) {
        for (PlayerMP p : connectedPlayers) {
            sendData(data, p.ipAddress, p.port);
        }
    }

    private void handleMove(Packet02Move packet) {
        if (getPlayeMP(packet.getUserId()) != null) {
            int index = getPlayeMPIndex(packet.getUserId());

            PlayerMP player = this.connectedPlayers.get(index);

            player.setPosition(packet.getX(), packet.getY());
            player.setNumSteps(packet.getNumSteps());
            player.setIsMoving(packet.isIsMoving());
            player.setMovingDir(packet.getMovingDir());

            packet.writeData(this);
        }
    }

    private void handleUpdate(Packet03LevelUpdate packet) {
        Iterator<Entity> it = this.handler.getLevel().getEntityManager().getEntities().iterator();
        while (it.hasNext()) {
            Entity e = it.next();
            if (e.getUniqueId().equals(packet.getId())) {
                e.setActive(packet.isActive());
                e.setHealth(packet.getHealth());

                packet.writeData(this);
            }
        }
    }

    private void handleStart(Packet04StartDomingo packet) {
        DomingaoGameState s = (DomingaoGameState) com.javaengine.game.states.State.getCurrentState();
        s.jogadores = connectedPlayers.size();
        if (s.jogadores >= 2) {
            if (!s.started) {
                minutes = System.currentTimeMillis() + 120000;
                s.started = true;
            }

            checkTimer();
            s.minute = minute;
            s.second = second;

        }
        packet.writeData(this);
    }
}
