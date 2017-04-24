package com.javaengine.game.net.packets;

import com.javaengine.game.net.GameClient;
import com.javaengine.game.net.GameServer;

public class Packet00Login extends Packet {

    private String userId, username;
    private int x, y;

    public Packet00Login(byte[] data) {
        super(00);
        String[] dataArray = readData(data).split(",");

        this.userId = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
        this.username = dataArray[3];
    }

    public Packet00Login(String userId, int x, int y, String username) {
        super(00);
        this.userId = userId;
        this.x = x;
        this.y = y;
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
        return ("00" + this.userId + "," + getX() + "," + getY() + "," + this.username).getBytes();
    }

    public String getUserId() {
        return userId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getUsername() {
        return username;
    }

}
