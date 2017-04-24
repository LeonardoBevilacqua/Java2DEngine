package com.javaengine.game.handlers;

import com.javaengine.game.entities.creatures.Player;
import com.javaengine.game.net.packets.Packet01Disconnect;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowHandler implements WindowListener {

    private final Handler handler;

    public WindowHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (handler.getSocketClient() != null) {
            Player p = handler.getLevel().getEntityManager().getPlayer();
            Packet01Disconnect packet = new Packet01Disconnect(p.getUniqueId(), p.getUsername());
            packet.writeData(handler.getSocketClient());
        }
        handler.getGame().stop();
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
