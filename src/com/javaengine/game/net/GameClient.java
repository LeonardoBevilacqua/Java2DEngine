package com.javaengine.game.net;

import com.javaengine.game.Game;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GameClient extends Thread {

    private InetAddress ipAddress;
    private DatagramSocket socket;
    private Game game;

    public GameClient(Game game, String ipAddress) {
        this.game = game;

        try {
            this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }
    
    public void run(){
        while(true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            
            try {
                socket.receive(packet);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            String message = new String(packet.getData());
            System.out.println("SERVER > " + message);
        }
    }
    
    public void sendData(byte[] data){
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
        
        try {
            socket.send(packet);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
