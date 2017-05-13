package com.javaengine.game.net.packets;

import com.javaengine.game.net.GameClient;
import com.javaengine.game.net.GameServer;

/**
 * The Packet03LevelUpdate is responsible to handle the entities.
 *
 * @author leonardo
 */
public class Packet03LevelUpdate extends Packet {

    private String id;
    private float health;
    private boolean active;

    /**
     * Initilize the class if the byte data.
     *
     * @param data The array of byte with the data.
     */
    public Packet03LevelUpdate(byte[] data) {
        super(03);
        String[] dataArray = readData(data).split(",");
        id = dataArray[0];
        health = Float.parseFloat(dataArray[1]);
        active = Integer.parseInt(dataArray[2]) == 1;
    }

    /**
     * Initilize the class.
     *
     * @param id The id of the entity.
     * @param health The health of the entity.
     * @param active The status of the entity.
     */
    public Packet03LevelUpdate(String id, float health, boolean active) {
        super(03);
        this.id = id;
        this.health = health;
        this.active = active;
    }

    /**
     * Write the data to the client.
     *
     * @param client Client object.
     */
    @Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

    /**
     * Write the data to the server.
     *
     * @param server Server object.
     */
    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    /**
     *
     * @return Returns the data.
     */
    @Override
    public byte[] getData() {
        return ("03"
                + id
                + "," + health
                + "," + (active ? 1 : 0)).getBytes();
    }

    // getters and setters
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
