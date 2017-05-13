package com.javaengine.game.net.packets;

import com.javaengine.game.net.GameClient;
import com.javaengine.game.net.GameServer;

/**
 * The Packet02Move is responsible to handle the entities movements.
 *
 * @author leonardo
 */
public class Packet02Move extends Packet {

    private String userId;
    private int x, y;
    private int numSteps = 0;
    private boolean isMoving;
    private int movingDir = 1;
    private boolean isAttacking;

    /**
     * Initilize the class if the byte data.
     *
     * @param data The array of byte with the data.
     */
    public Packet02Move(byte[] data) {
        super(02);
        String[] dataArray = readData(data).split(",");
        this.userId = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
        this.numSteps = Integer.parseInt(dataArray[3]);
        this.isMoving = Integer.parseInt(dataArray[4]) == 1;
        this.movingDir = Integer.parseInt(dataArray[5]);
        this.isAttacking = Integer.parseInt(dataArray[6]) == 1;
    }

    /**
     * Initilize the class.
     *
     * @param userId The id of the entity.
     * @param x The x position.
     * @param y The y position.
     * @param numSteps The number of steps.
     * @param isMoving If the entity is moving.
     * @param movingDir The direction of the movement.
     * @param isAttacking If is attacking.
     */
    public Packet02Move(String userId, int x, int y, int numSteps, boolean isMoving, int movingDir, boolean isAttacking) {
        super(02);
        this.userId = userId;
        this.x = x;
        this.y = y;
        this.numSteps = numSteps;
        this.isMoving = isMoving;
        this.movingDir = movingDir;
        this.isAttacking = isAttacking;
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
        return ("02"
                + this.userId
                + "," + this.x
                + "," + this.y
                + "," + this.numSteps
                + "," + (this.isMoving ? 1 : 0)
                + "," + this.movingDir
                + "," + (this.isAttacking ? 1 : 0)).getBytes();
    }

    // getters and setters
    public String getUserId() {
        return userId;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getNumSteps() {
        return numSteps;
    }

    public boolean isIsMoving() {
        return isMoving;
    }

    public int getMovingDir() {
        return movingDir;
    }

    public boolean isIsAttacking() {
        return isAttacking;
    }

}
