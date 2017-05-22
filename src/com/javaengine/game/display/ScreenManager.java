/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaengine.game.display;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;

/**
 * ScreenManager is responsible to set fullscreen window with the appropriate
 * resolution.
 *
 * @author leonardo
 */
public class ScreenManager {

    private GraphicsDevice videoCard;

    /**
     * Gives videoCard access to the monitor screen.
     */
    public ScreenManager() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        videoCard = environment.getDefaultScreenDevice();
    }

    /**
     * Returns a array of displayModes.
     *
     * @return Returns all compartible displayModes.
     */
    public DisplayMode[] getAllCompatibleDisplayModes() {
        return videoCard.getDisplayModes();
    }

    /**
     *
     * @return Returns the current displayMode.
     */
    public DisplayMode getCurrentDisplayMode() {
        return videoCard.getDisplayMode();
    }

    /**
     * Sets the window to full screen.
     *
     * @param display The object of the window.
     * @param displaymode The resolution.
     */
    public void setFullScreen(Display display, DisplayMode displaymode) {
        if (videoCard.isFullScreenSupported()) {
            videoCard.setFullScreenWindow(display.getFrame());
            videoCard.setDisplayMode(displaymode);
        }
    }

    /**
     * Set a new resolution.
     *
     * @param displayMode The resolution.
     */
    public void setResolution(DisplayMode displayMode) {
        if (videoCard.isFullScreenSupported()) {
            videoCard.setDisplayMode(displayMode);
        }
    }

    /**
     * Sets the display to window mode.
     */
    public void setWindowScreen() {
        videoCard.setFullScreenWindow(null);
    }

    /**
     *
     * @return Returns the full screen window.
     */
    public Window getFullScreenWindow() {
        return videoCard.getFullScreenWindow();
    }

}
