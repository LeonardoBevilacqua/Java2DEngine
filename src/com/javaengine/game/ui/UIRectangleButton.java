package com.javaengine.game.ui;

import com.javaengine.game.gfx.Assets;
import com.javaengine.game.gfx.Text;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author leonardo
 */
public class UIRectangleButton extends UIObject {

    private Color[] colours;
    private ClickListener clicker;
    private String text;

    public UIRectangleButton(int x, int y, int width, int height, Color[] colours, String text, ClickListener clicker) {
        super(x, y, width, height);
        this.clicker = clicker;
        this.colours = colours;
        this.text = text;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        if (!hovering) {
            g.setColor(colours[0]);
            g.fillRect(x, y, width, height);
            Text.drawString(g, text, x + width / 2, y + height / 2, true, Color.red, Assets.font28);
        } else {
            g.setColor(colours[1]);
            g.fillRect(x, y, width, height);
            Text.drawString(g, text, x + width / 2, y + height / 2, true, Color.red, Assets.font28);
        }
    }

    @Override
    public void onClick() {
        clicker.onClick();
    }

}
