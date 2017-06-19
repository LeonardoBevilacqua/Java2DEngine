/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaengine.game.utils;

import com.javaengine.game.display.ScreenManager;
import java.io.BufferedReader;
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
     * Gets all possibles resolutions.
     */
    private static String[] getCompatibleResolutions() {
        ScreenManager screen = new ScreenManager();
        String[] resolutions = new String[screen.getAllCompatibleDisplayModes().length];
        int length = screen.getAllCompatibleDisplayModes().length;

        for (int i = 0; i < length; i++) {

            if (screen.getAllCompatibleDisplayModes()[i].getBitDepth() == 32 || screen.getAllCompatibleDisplayModes()[i].getBitDepth() == -1) {
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

    public static void createResolution() {
        String resolutionsText = "fullscreen:0;\ncurrentresolution:null;\nresolutions:\n";
        String[] resolutions = getCompatibleResolutions();
        for (String resolution : resolutions) {
            if (resolution == null) {
                break;
            }
            resolutionsText += resolution + ",\n";
        }

        Utils.writeStringAsFile(resolutionsText, "resolutions", "txt");
    }

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

        resolutionsText += file[0] + ";\n" + file[1] + ";\n" + file[2] + "\n";

        Utils.writeStringAsFile(resolutionsText, "resolutions", "txt");
    }

    public static void loadResolution() {
        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("resolutions.txt")
            );

            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }

            br.close();

            file = builder.toString().split(";");
            isFullscreen = file[0].split(":")[1].equals("1");
            currentGameResolutions = file[1].split(":")[1];
            gameResolutions = file[2].split(":")[1].split(",");
            if (currentGameResolutions.equals("null")) {
                currentGameResolutions = gameResolutions[0];
            }

        } catch (IOException e) {
            createResolution();
            loadResolution();
        }
    }
}
