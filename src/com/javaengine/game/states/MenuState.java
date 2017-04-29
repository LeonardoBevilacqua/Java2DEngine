package com.javaengine.game.states;

import com.javaengine.game.gfx.Assets;
import com.javaengine.game.gfx.Text;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.ui.ClickListener;
import com.javaengine.game.ui.UIManager;
import com.javaengine.game.ui.UIRectangleButton;
import com.javaengine.game.utils.ImageLoader;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author leonardo
 */
public class MenuState extends State {

    private UIManager uiManager;
    private int btnWidth = 200, btnHeight = 50, btnX = handler.getWidth() / 2 - btnWidth / 2,
            btnY = handler.getHeight() / 2;
    private BufferedImage bg = ImageLoader.loadImage("/background.png");

    UIRectangleButton[] buttons;

    public MenuState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);

        buttons = new UIRectangleButton[3];

        buttons[0] = new UIRectangleButton(btnX, btnY - btnHeight * 2 - 20, btnWidth, btnHeight, new Color[]{Color.gray, Color.black}, "Start Game", new ClickListener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);

                State gameState = new GameState(handler);

                State.setState(gameState);
            }
        });

        buttons[1] = new UIRectangleButton(btnX, btnY - btnHeight + 20, btnWidth, btnHeight, new Color[]{Color.gray, Color.black}, "Multiplayer", new ClickListener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);

                State mpGameState = new MPGameState(handler);

                State.setState(mpGameState);
            }
        });

        buttons[2] = new UIRectangleButton(btnX - 20, btnY + btnHeight, btnWidth + 40, btnHeight, new Color[]{Color.gray, Color.black}, "DOMINGO PORA", new ClickListener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);

                State domigao = new DomingaoGameState(handler);
                State.setState(domigao);
            }
        });

        handler.getMouseManager().setUIManager(uiManager);

        uiManager.addObject(buttons[0]);
        uiManager.addObject(buttons[1]);
        uiManager.addObject(buttons[2]);
    }

    @Override
    public void tick() {
        uiManager.tick();
        btnX = handler.getWidth() / 2 - btnWidth / 2;
        btnY = handler.getHeight() / 2;

        buttons[0].setX(btnX);
        buttons[0].setY(btnY - btnHeight * 2 - 20);

        buttons[1].setX(btnX);
        buttons[1].setY(btnY - btnHeight + 20);

        buttons[2].setX(btnX - 20);
        buttons[2].setY(btnY + btnHeight);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(bg, 0, 0, handler.getWidth(), handler.getHeight(), null);
        uiManager.render(g);
        Text.drawString(g, "JAVA ENGINE 2D - L. Bevilacqua (Alpha V -2.1)", 10, handler.getHeight() - 16, false, Color.BLACK, Assets.font16);
    }
}
