package com.javaengine.game.states;

import com.javaengine.game.entities.creatures.Player;
import com.javaengine.game.entities.creatures.PlayerMP;
import com.javaengine.game.gfx.Assets;
import com.javaengine.game.gfx.Text;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.level.Level;
import com.javaengine.game.level.world.LevelDomingao;
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

    private long minutes;
    long minute, second;

    public DomingaoGameState(Handler handler) {
        super(handler);

        nameChar = JOptionPane.showInputDialog("Nome do seu char: ");
        String[] choices = {"A", "B", "C", "D", "E", "F"};
        String playerChar = (String) JOptionPane.showInputDialog(null, "Escolha seu char:", "Falso UI", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

        Player player = new PlayerMP(handler, 0, 0, nameChar, Assets.player, null, -1, true, Utils.getUniqueId());

        level = new LevelDomingao(handler, "/levels/levelDomigao.txt", player);
        handler.setLevel(level);

        minutes = System.currentTimeMillis() + 120000;
    }

    @Override
    public void tick() {
        level.tick();
        minute = (minutes - System.currentTimeMillis()) / 60000;
        second = (minutes - System.currentTimeMillis()) / 1000 - minute * 60;
        System.out.println(minute + ":" + second);
    }

    @Override
    public void render(Graphics g) {
        level.render(g);
        Text.drawString(g, minute + ":" + second, handler.getWidth() / 2, 20, true, Color.YELLOW, Assets.font28);
    }

    // getters and setters
    public Level getLevel() {
        return level;
    }

}
