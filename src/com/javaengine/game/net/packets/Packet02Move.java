package com.javaengine.game.net.packets;

import com.javaengine.game.net.GameClient;
import com.javaengine.game.net.GameServer;

public class Packet02Move extends Packet {

    private String username;
    private int x, y;
    private int numSteps = 0;
    private boolean isMoving;
    private int movingDir = 1;
    private boolean isAttacking;

    public Packet02Move(byte[] data) {
        super(02);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
        this.numSteps = Integer.parseInt(dataArray[3]);
        this.isMoving = Integer.parseInt(dataArray[4]) == 1;
        this.movingDir = Integer.parseInt(dataArray[5]);
        this.isAttacking = Integer.parseInt(dataArray[6]) == 1;
    }

    public Packet02Move(String username, int x, int y, int numSteps, boolean isMoving, int movingDir, boolean isAttacking) {
        super(02);
        this.username = username;
        this.x = x;
        this.y = y;
        this.numSteps = numSteps;
        this.isMoving = isMoving;
        this.movingDir = movingDir;
        this.isAttacking = isAttacking;
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
        return ("02"
                + this.username
                + "," + this.x
                + "," + this.y
                + "," + this.numSteps
                + "," + (this.isMoving ? 1 : 0)
                + "," + this.movingDir
                + "," + (this.isAttacking ? 1 : 0)).getBytes();
    }

    public String getUsername() {
        return username;
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
