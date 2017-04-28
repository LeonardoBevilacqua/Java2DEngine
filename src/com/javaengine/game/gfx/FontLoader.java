/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaengine.game.gfx;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author leonardo
 */
public class FontLoader {

    public static Font loadFont(String path, float size) {
        InputStream is = FontLoader.class.getResourceAsStream(path);
        try {
            return Font.createFont(
                    Font.TRUETYPE_FONT, is
            ).deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
