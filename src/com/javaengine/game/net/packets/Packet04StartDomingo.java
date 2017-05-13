package com.javaengine.game.net.packets;

import com.javaengine.game.net.GameClient;
import com.javaengine.game.net.GameServer;

public class Packet04StartDomingo extends Packet {

    private boolean start;
    private long minute, second;
    private static long totalMinutes;
    private int jogadores = 0;

    public Packet04StartDomingo(byte[] data) {
        super(04);
        String[] dataArray = readData(data).split(",");
        this.start = Integer.parseInt(dataArray[0]) == 1;
        this.jogadores = Integer.parseInt(dataArray[1]);
        this.minute = Integer.parseInt(dataArray[2]);
        this.second = Integer.parseInt(dataArray[3]);
    }

    public Packet04StartDomingo(boolean start, int jogadores, long minute, long second) {
        super(04);
        this.start = start;
        this.jogadores = jogadores;
        this.minute = minute;
        this.second = second;
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
        return ("04"
                + (this.start ? 1 : 0)
                + "," + this.jogadores
                + "," + this.minute
                + "," + this.second).getBytes();
    }

    public void checkTimer() {
        minute = (totalMinutes - System.currentTimeMillis()) / 60000;
        second = (totalMinutes - System.currentTimeMillis()) / 1000 - minute * 60;
    }

    public boolean isStart() {
        return start;
    }

    public int getJogadores() {
        return jogadores;
    }

    public long getMinute() {
        return minute;
    }

    public long getSecond() {
        return second;
    }

    public long getMinutes() {
        return totalMinutes;
    }

    public void setMinutes(long minutes) {
        this.totalMinutes = minutes;
    }

    public void setJogadores(int jogadores) {
        this.jogadores = jogadores;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

}
