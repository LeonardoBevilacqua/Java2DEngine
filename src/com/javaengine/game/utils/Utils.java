package com.javaengine.game.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * The Utils class has static methods to be used as tools.
 *
 * @author leonardo
 */
public class Utils {

    /**
     * This method loads a file and returns the text.
     *
     * @param path The path of the file.
     * @return Returns all the text as a string.
     */
    public static String loadFileAsString(String path) {
        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Utils.class.getResourceAsStream(path)));
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line + "\n");
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    /**
     * Takes a string and convert to a integer.
     *
     * @param number The number as a string.
     * @return Returns the as a integer.
     */
    public static int parseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Flip the image horizontally.
     *
     * @param image The image that should be flipped horizontally.
     * @return Returns the image flipped horizontally.
     */
    public static BufferedImage flipImageHorizontally(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gg = newImage.createGraphics();
        gg.drawImage(image, image.getHeight(), 0, -image.getWidth(), image.getHeight(), null);
        gg.dispose();
        return newImage;
    }

    /**
     * Create a unique five-character ID.
     *
     * @return Returns the ID.
     */
    public static String getUniqueId() {
        String id = UUID.randomUUID().toString();
        return id.substring(0, 5);
    }
}
