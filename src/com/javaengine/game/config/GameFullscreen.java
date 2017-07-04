/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaengine.game.config;

import com.javaengine.game.display.Display;
import com.javaengine.game.display.ScreenManager;
import static com.javaengine.game.utils.ResolutionsManager.currentGameResolutions;
import static com.javaengine.game.utils.ResolutionsManager.isFullscreen;
import static com.javaengine.game.utils.ResolutionsManager.updateResolution;
import java.awt.DisplayMode;

/**
 *
 * @author leonardo
 */
public class GameFullscreen {

    private static ScreenManager screen = new ScreenManager();

    /**
     * Checks if the window is fullscreen.
     *
     * @param display
     */
    public static void checkFullScreen(Display display) {
        if (isFullscreen) {
            if (screen.getFullScreenWindow() == null) {
                setFullScreen(display);
            }
        } else {
            screen.setWindowScreen();
        }
    }

    /**
     * Set the game in fullscreen mode.
     *
     * @param display The game frame.
     */
    private static void setFullScreen(Display display) {
        screen.setFullScreen(display, getDisplayMode());
    }

    /**
     * Change the resolution.
     */
    public static void changeResolution() {
        updateResolution("currentresolution", currentGameResolutions);
        screen.setResolution(getDisplayMode());
    }

    /**
     * Get the displayMode.
     *
     * @return Returns the displayMode.
     */
    private static DisplayMode getDisplayMode() {
        String[] components = currentGameResolutions.split("@");
        return new DisplayMode(Integer.parseInt(components[0]),
                Integer.parseInt(components[1]),
                Integer.parseInt(components[2]),
                Integer.parseInt(components[3]));
    }

}
