package com.javaengine.game;

import com.javaengine.game.net.packets.Packet01Disconnect;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowHandler implements WindowListener {

    private final Game game;
    
    public WindowHandler(Game game){
        this.game = game;
        this.game.frame.addWindowListener(this);
    }
    
    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Packet01Disconnect packet = new Packet01Disconnect(this.game.player.getUsername());
        packet.writeData(this.game.socketClient);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
    
}
