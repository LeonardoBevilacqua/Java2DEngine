package com.javaengine.game.net.packets;

import com.javaengine.game.entities.Entity;
import com.javaengine.game.net.GameClient;
import com.javaengine.game.net.GameServer;
import java.util.ArrayList;

public class Packet03LevelUpdate extends Packet {

    private String id;
    private float health;
    private boolean active;

    public Packet03LevelUpdate(byte[] data) {
        super(03);
        String[] dataArray = readData(data).split(",");
        id = dataArray[0];
        health = Float.parseFloat(dataArray[1]);
        active = Integer.parseInt(dataArray[2]) == 1;
    }

    public Packet03LevelUpdate(String id, float health, boolean active) {
        super(03);
        this.id = id;
        this.health = health;
        this.active = active;
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
        return ("03"
                + id
                + "," + health
                + "," + (active ? 1 : 0)).getBytes();
    }

    public String getId() {
        return id;
    }

    public float getHealth() {
        return health;
    }

    public boolean isActive() {
        return active;
    }
}
