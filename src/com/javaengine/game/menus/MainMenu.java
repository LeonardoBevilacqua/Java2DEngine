/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaengine.game.menus;

import com.javaengine.game.menus.config.ConfigMenu;
import com.javaengine.game.gfx.Assets;
import com.javaengine.game.gfx.Text;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.states.GameState;
import com.javaengine.game.states.MPGameState;
import com.javaengine.game.states.State;
import com.javaengine.game.ui.UIImageButton;
import com.javaengine.game.ui.UIManager;
import com.javaengine.game.utils.ImageLoader;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * The first Menu of the game.
 *
 * @author leonardo
 */
public class MainMenu extends Menu {

    private UIManager uiManager;
    private int btnWidth = 192, btnHeight = 64;

    private BufferedImage bg = ImageLoader.loadImage("/background.png");

    UIImageButton[] buttonsImg;

    public MainMenu(Handler handler, String id) {
        super(handler, id);

        uiManager = new UIManager(handler);

        buttonsImg = new UIImageButton[4];

        buttonsImg[0] = new UIImageButton(
                handler,
                50,
                30,
                true,
                btnWidth, btnHeight,
                Assets.btn_start,
                () -> {
                    stopMouseListener();

                    State gameState = new GameState(handler);

                    State.setState(gameState);
                }
        );

        buttonsImg[1] = new UIImageButton(
                handler,
                50,
                45,
                true,
                btnWidth, btnHeight,
                Assets.btn_mp,
                () -> {
                    stopMouseListener();

                    State mpGameState = new MPGameState(handler);

                    State.setState(mpGameState);
                }
        );

        buttonsImg[2] = new UIImageButton(
                handler,
                41,
                55,
                false,
                64, 64,
                Assets.btn_config, () -> {
                    stopMouseListener();
                    Menu.addMenu(new ConfigMenu(handler, "ConfigMenu"));
                    Menu.setMenu("ConfigMenu");
                });

        buttonsImg[3] = new UIImageButton(
                handler,
                15,
                70,
                false,
                64 * 2, 64,
                Assets.btn_exit, () -> {
                    handler.getGame().stop();
                });

        startMouseListener();

        for (UIImageButton buttons : buttonsImg) {
            uiManager.addObject(buttons);
        }

    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(bg, 0, 0, handler.getWidth(), handler.getHeight(), null);
        uiManager.render(g);
        Text.drawString(
                g,
                "JAVA ENGINE 2D - L. Bevilacqua (Alpha V -2.1)",
                10,
                handler.getHeight() - 16,
                false,
                Color.BLACK,
                Assets.font16
        );
    }

    @Override
    public void startMouseListener() {
        handler.getMouseManager().setUIManager(uiManager);
    }

}
