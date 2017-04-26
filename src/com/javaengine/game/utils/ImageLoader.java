package com.javaengine.game.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * The ImageLoader class is responsible to load a image.
 * @author leonardo
 */
public class ImageLoader {
    /**
     * Loads a image.
     * @param path The path of the image.
     * @return Returns the image if exists.
     */
    public static BufferedImage loadImage(String path){
        try {
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException ex) {
            Logger.getLogger(ImageLoader.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        return null;
    }
}
