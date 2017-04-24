package com.javaengine.game.net.packets;

import com.javaengine.game.net.GameClient;
import com.javaengine.game.net.GameServer;

public class Packet01Disconnect extends Packet {

    private String userId, username;

    public Packet01Disconnect(byte[] data) {
        super(01);
        String[] dataArray = readData(data).split(",");

        this.userId = dataArray[0];
        this.username = dataArray[1];
    }

    public Packet01Disconnect(String userId, String username) {
        super(01);
        this.userId = userId;
        this.username = username;
    }

    @Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    @Override
    public byte[] getData() {
        return ("01" + this.userId + "," + this.username).getBytes();
    }

    public String getUsername() {
        return username;
    }

    public String getUserId() {
        return userId;
    }
}
