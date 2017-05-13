package com.javaengine.game.states;

import com.javaengine.game.entities.creatures.Player;
import com.javaengine.game.entities.creatures.PlayerMP;
import com.javaengine.game.gfx.Assets;
import com.javaengine.game.gfx.Text;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.level.Level;
import com.javaengine.game.level.world.LevelDomingao;
import com.javaengine.game.net.packets.Packet04StartDomingo;
import com.javaengine.game.utils.Utils;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JOptionPane;

/**
 *
 * @author leonardo
 */
public class DomingaoGameState extends State {

    private LevelDomingao level;
    private String nameChar;
    //aa
    public boolean started = false;
    public long minute = 0, second = 0;
    public int jogadores = 1;

    public DomingaoGameState(Handler handler) {
        super(handler);

        nameChar = JOptionPane.showInputDialog("Nome do seu char: ");
        String[] choices = {"A", "B", "C", "D", "E", "F"};
        String playerChar = (String) JOptionPane.showInputDialog(null, "Escolha seu char:", "Falso UI", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
        Player player = new PlayerMP(handler, nameChar, Assets.player, null, -1, true, Utils.getUniqueId());

        level = new LevelDomingao(handler, "/levels/levelDomigao.txt", player);
        handler.setLevel(level);

    }

    @Override
    public void tick() {
        if (handler.getSocketClient() != null) {
            Packet04StartDomingo packet = new Packet04StartDomingo(
                    started, jogadores, minute, second
            );
            packet.writeData(handler.getSocketClient());
        }
        if (!started || minute <= 0 && second <= 0) {
            return;
        }

        level.tick();

    }

    @Override
    public void render(Graphics g) {
        if (!started) {
            Text.drawString(g, "Aguarde os outros viados!", handler.getWidth() / 2, handler.getHeight() / 2, true, Color.red, Assets.font28);
            return;
        }
        if (minute <= 0 && second <= 0) {
            Text.drawString(g, "Fim de jogo!", handler.getWidth() / 2, handler.getHeight() / 2, true, Color.red, Assets.font28);
            return;
        }

        level.render(g);
        Text.drawString(g, minute + ":" + second, handler.getWidth() / 2, 20, true, Color.YELLOW, Assets.font28);
    }

    // getters and setters
    public Level getLevel() {
        return level;
    }

}
