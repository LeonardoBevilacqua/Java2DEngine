package com.javaengine.game.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * The ImageLoader class is responsible to load a image.
 *
 * @author leonardo
 */
public class ImageLoader {

    /**
     * Loads a image.
     *
     * @param path The path of the image.
     * @return Returns the image if exists.
     */
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(ImageLoader.class.getResourceAsStream(path));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
