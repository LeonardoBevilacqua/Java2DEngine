package com.javaengine.game.display;

import com.javaengine.game.handlers.WindowHandler;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author leonardo
 */
public class Display {

    private JFrame frame;
    private Canvas canvas;

    private final String TITLE;
    private final int WIDTH, HEIGHT;
    private final Dimension DIMENSIONS;
    public WindowHandler windowHandler;

    public Display(String title, int width, int height) {
        this.TITLE = title;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.DIMENSIONS = new Dimension(WIDTH, HEIGHT);

        createDisplay();
    }

    private void createDisplay() {
        frame = new JFrame(TITLE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // optional
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        // end optional
        frame.setVisible(true);
        frame.setFocusable(false);
        frame.setMinimumSize(DIMENSIONS);
        frame.setPreferredSize(DIMENSIONS);
        frame.setMaximumSize(DIMENSIONS);

        canvas = new Canvas();
        canvas.setPreferredSize(DIMENSIONS);
        canvas.setMaximumSize(DIMENSIONS);
        canvas.setPreferredSize(DIMENSIONS);

        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();

        //windowHandler = new WindowHandler(new Game());
    } 

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return frame;
    }    
}
