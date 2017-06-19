/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaengine.game.menus.config;

import com.javaengine.game.gfx.Assets;
import com.javaengine.game.gfx.Text;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.menus.Menu;
import com.javaengine.game.ui.UIImageButton;
import com.javaengine.game.ui.UIManager;
import static com.javaengine.game.utils.ResolutionsManager.currentGameResolutions;
import static com.javaengine.game.utils.ResolutionsManager.gameResolutions;
import static com.javaengine.game.utils.ResolutionsManager.updateResolution;
import static com.javaengine.game.utils.ResolutionsManager.isFullscreen;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author leonardo
 */
public class ConfigMenu extends Menu {

    private UIManager uiManager;
    //private ScreenManager screen;
    private int selectedItem;
    private String selectResolution;

    public ConfigMenu(Handler handler, String id) {
        super(handler, id);
        uiManager = new UIManager(handler);

        uiManager.addObject(new UIImageButton(handler, 50, 21, false, 32, 32, Assets.checkBox, () -> {
            isFullscreen = !isFullscreen;
            updateResolution("fullscreen", isFullscreen ? "1" : "0");
        }));

        uiManager.addObject(new UIImageButton(handler, 20, 80, false, 32, 32, Assets.btn_config, () -> {
            Menu.setMenu("MainMenu");
        }));

        startMouseListener();

        // screen = new ScreenManager();
        selectedItem = 0;

        selectResolution = currentGameResolutions.split("@")[0] + "x" + currentGameResolutions.split("@")[1];
    }

    @Override
    public void tick() {
        uiManager.tick();
        GameFullscreen.checkFullScreen(handler.getGame().getDisplay());

        if (isFullscreen) {
            if (handler.getInput().keyJustPressed(handler.getInput().Iup)) {
                selectedItem--;
            }
            if (handler.getInput().keyJustPressed(handler.getInput().Idown)) {
                selectedItem++;
            }

            if (selectedItem < 0) {
                selectedItem = gameResolutions.length - 1;
            } else if (selectedItem >= gameResolutions.length) {
                selectedItem = 0;
            }

            selectResolution = gameResolutions[selectedItem].split("@")[0]
                    + "x" + gameResolutions[selectedItem].split("@")[1];

            if (handler.getInput().keyJustPressed(KeyEvent.VK_ENTER)) {
                currentGameResolutions = gameResolutions[selectedItem];
                GameFullscreen.changeResolution();
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

        Text.drawString(g, "> " + selectResolution + " <", (int) (handler.getWidth() * 0.6),
                (int) (handler.getHeight() * 0.31), true, Color.YELLOW, Assets.font28);

        uiManager.render(g);
        if (isFullscreen) {
            g.drawImage(Assets.check[0], (int) (handler.getWidth() * 0.50f), (int) (handler.getHeight() * 0.21), 32, 32, null);
        }
    }

    /**
     * Checks if the window is fullscreen.
     */
    /*private void checkFullScreen() {
        if (isFullscreen) {
            if (screen.getFullScreenWindow() == null) {
                setFullScreen();
            }
        } else {
            screen.setWindowScreen();
        }
    }

    private void setFullScreen() {
        screen.setFullScreen(
                handler.getGame().getDisplay(), getDisplayMode());
    }

    private void changeResolution() {
        updateResolution("currentresolution", currentGameResolutions);
        screen.setResolution(getDisplayMode());
    }

    private DisplayMode getDisplayMode() {
        String[] components = currentGameResolutions.split("@");
        return new DisplayMode(Integer.parseInt(components[0]),
                Integer.parseInt(components[1]),
                Integer.parseInt(components[2]),
                Integer.parseInt(components[3]));
    }*/
    @Override
    public void startMouseListener() {
        handler.getMouseManager().setUIManager(uiManager);
    }
}
