/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaengine.game.utils;

import com.javaengine.game.display.ScreenManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class is responsible to generate the resolutions, save and load.
 *
 * @author leonardo
 */
public abstract class ResolutionsManager {

    /*
    Variables
     */
    private static String[] file;
    public static String[] gameResolutions;
    public static String currentGameResolutions;
    public static boolean isFullscreen;

    /**
     * Gets all possible resolutions.
     *
     * @return Returns all possible resolutions.
     */
    private static String[] getCompatibleResolutions() {
        ScreenManager screen = new ScreenManager();
        String[] resolutions = new String[screen.getAllCompatibleDisplayModes().length];
        int length = screen.getAllCompatibleDisplayModes().length;

        for (int i = 0; i < length; i++) {

            if (screen.getAllCompatibleDisplayModes()[i].getBitDepth() == 32
                    || screen.getAllCompatibleDisplayModes()[i].getBitDepth() == -1) {
                resolutions[i] = screen.getAllCompatibleDisplayModes()[i].getWidth() + "@"
                        + screen.getAllCompatibleDisplayModes()[i].getHeight() + "@"
                        + screen.getAllCompatibleDisplayModes()[i].getBitDepth() + "@"
                        + screen.getAllCompatibleDisplayModes()[i].getRefreshRate();
            }
            if (screen.getAllCompatibleDisplayModes()[i].getWidth() == 800 && screen.getAllCompatibleDisplayModes()[i].getHeight() == 600) {
                break;
            }
        }
        return resolutions;
    }

    /**
     * Create the file with resolution configuration.
     */
    public static void createResolution() {
        String resolutionsText = "fullscreen:0;" + System.lineSeparator()
                + "currentresolution:null;" + System.lineSeparator()
                + "resolutions:" + System.lineSeparator();

        String[] resolutions = getCompatibleResolutions();
        for (String resolution : resolutions) {
            if (resolution == null) {
                break;
            }
            resolutionsText += resolution + ",";
        }

        Utils.writeStringAsFile(resolutionsText, "resolutions", "cfg");
    }

    /**
     * Updates the file resolution.
     *
     * @param param The parameter of the file.
     * @param value The new value.
     */
    public static void updateResolution(String param, String value) {

        String resolutionsText = "";
        switch (param) {
            case "fullscreen":
                file[0] = "fullscreen:" + value;
                break;
            case "currentresolution":
                file[1] = "currentresolution:" + value;
                break;
        }

        resolutionsText += file[0] + ";" + System.lineSeparator() + file[1] + ";" + System.lineSeparator() + file[2] + System.lineSeparator();

        Utils.writeStringAsFile(resolutionsText, "resolutions", "cfg");
    }

    /**
     * Load the resolution file
     */
    public static void loadResolution() {
        StringBuilder builder = new StringBuilder();
        File fileResolution = new File("resolutions.cfg");
        if (!fileResolution.exists()) {
            createResolution();
        }

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(fileResolution))) {
                String line;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
            }

            file = builder.toString().split(";");
            isFullscreen = file[0].split(":")[1].equals("1");
            currentGameResolutions = file[1].split(":")[1];
            gameResolutions = file[2].split(":")[1].split(",");
            if (currentGameResolutions.equals("null")) {
                currentGameResolutions = gameResolutions[0];
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
