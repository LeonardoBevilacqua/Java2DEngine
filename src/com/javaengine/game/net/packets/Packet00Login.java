package com.javaengine.game.net.packets;

import com.javaengine.game.net.GameClient;
import com.javaengine.game.net.GameServer;

/**
 * The Packet00Login is responsible to handle the login.
 *
 * @author leonardo
 */
public class Packet00Login extends Packet {

    private String userId, username;
    private int x, y;

    /**
     * Initilize the class if the byte data.
     *
     * @param data The array of byte with the data.
     */
    public Packet00Login(byte[] data) {
        super(00);
        String[] dataArray = readData(data).split(",");

        this.userId = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
        this.username = dataArray[3];
    }

    /**
     * Initilize the class.
     *
     * @param userId Id of the user.
     * @param x The x position.
     * @param y The y position.
     * @param username The username.
     */
    public Packet00Login(String userId, int x, int y, String username) {
        super(00);
        this.userId = userId;
        this.x = x;
        this.y = y;
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
        return ("00"
                + this.userId
                + ","
                + getX()
                + ","
                + getY()
                + ","
                + this.username).getBytes();
    }

    // getters and setters
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
