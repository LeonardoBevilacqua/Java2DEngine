/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaengine.game.menus;

import com.javaengine.game.display.ScreenManager;
import com.javaengine.game.gfx.Assets;
import com.javaengine.game.gfx.Text;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.ui.ClickListener;
import com.javaengine.game.ui.UIImageButton;
import com.javaengine.game.ui.UIManager;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author leonardo
 */
public class ConfigMenu extends Menu {

    private UIManager uiManager;
    private boolean fullscreen;
    private ScreenManager screen;
    private String[] resolutions;
    private int selectedItem;
    private int currentResolution;

    public ConfigMenu(Handler handler, String id) {
        super(handler, id);
        uiManager = new UIManager(handler);
        fullscreen = false;

        uiManager.addObject(new UIImageButton(handler, 50, 21, false, 32, 32, Assets.checkBox, () -> {
            fullscreen = !fullscreen;
        }));

        uiManager.addObject(new UIImageButton(handler, 20, 80, false, 32, 32, Assets.btn_config, () -> {
            Menu.setMenu("MainMenu");
        }));

        startMouseListener();

        screen = new ScreenManager();

        getResolutions();
        selectedItem = 0;
        currentResolution = resolutions.length - 1;
    }

    @Override
    public void tick() {
        uiManager.tick();
        checkFullScreen();

        if (fullscreen) {
            if (handler.getInput().keyJustPressed(handler.getInput().Iup)) {
                selectedItem--;
            }
            if (handler.getInput().keyJustPressed(handler.getInput().Idown)) {
                selectedItem++;
            }

            if (selectedItem < 0) {
                selectedItem = resolutions.length - 1;
            } else if (selectedItem >= resolutions.length) {
                selectedItem = 0;
            }
            if (handler.getInput().keyJustPressed(KeyEvent.VK_ENTER)) {
                currentResolution = selectedItem;
                changeResolution();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(0, 0, handler.getWidth(), handler.getHeight());

        Text.drawString(g,
                "Fullscreen",
                (int) (handler.getWidth() * 0.25),
                (int) (handler.getHeight() * 0.25),
                false, Color.WHITE, Assets.font28
        );

        Text.drawString(g,
                "Resolution",
                (int) (handler.getWidth() * 0.25),
                (int) (handler.getHeight() * 0.32),
                false, Color.WHITE, Assets.font28
        );

        Text.drawString(g, "> " + resolutions[selectedItem] + " <", (int) (handler.getWidth() * 0.6),
                (int) (handler.getHeight() * 0.31), true, Color.YELLOW, Assets.font28);

        uiManager.render(g);
        if (fullscreen) {
            g.drawImage(Assets.check[0], (int) (handler.getWidth() * 0.50f), (int) (handler.getHeight() * 0.23), 32, 32, null);
        }
    }

    /**
     * Gets all possibles resolutions.
     */
    private void getResolutions() {
        resolutions = new String[screen.getAllCompatibleDisplayModes().length];

        for (int i = 0; i < resolutions.length; i++) {
            resolutions[i] = screen.getAllCompatibleDisplayModes()[i].getWidth() + "x"
                    + screen.getAllCompatibleDisplayModes()[i].getHeight();
        }
    }

    /**
     * Checks if the window is fullscreen.
     */
    private void checkFullScreen() {
        if (fullscreen) {
            if (screen.getFullScreenWindow() == null) {
                setFullScreen();
            }
        } else {
            screen.setWindowScreen();
        }
    }

    private void setFullScreen() {
        screen.setFullScreen(
                handler.getGame().getDisplay(),
                screen.getAllCompatibleDisplayModes()[currentResolution]
        );
    }

    private void changeResolution() {
        screen.setResolution(screen.getAllCompatibleDisplayModes()[currentResolution]);
    }

    @Override
    public void startMouseListener() {
        handler.getMouseManager().setUIManager(uiManager);
    }
}
