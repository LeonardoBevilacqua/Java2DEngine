package com.javaengine.game.net.packets;

import com.javaengine.game.net.GameClient;
import com.javaengine.game.net.GameServer;

/**
 * The Packet01Disconnect is responsible to handle the deisconnections.
 *
 * @author leonardo
 */
public class Packet01Disconnect extends Packet {

    private String userId, username;

    /**
     * Initilize the class if the byte data.
     *
     * @param data The array of byte with the data.
     */
    public Packet01Disconnect(byte[] data) {
        super(01);
        String[] dataArray = readData(data).split(",");

        this.userId = dataArray[0];
        this.username = dataArray[1];
    }

    /**
     * Initilize the class.
     *
     * @param userId The id of the entity.
     * @param username The username of the entity.
     */
    public Packet01Disconnect(String userId, String username) {
        super(01);
        this.userId = userId;
        this.username = username;
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
        return ("01" + this.userId + "," + this.username).getBytes();
    }

    // getters and setters
    public String getUsername() {
        return username;
    }

    public String getUserId() {
        return userId;
    }
}
