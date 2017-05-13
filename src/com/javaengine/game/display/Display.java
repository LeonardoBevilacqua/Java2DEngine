package com.javaengine.game.display;

import com.javaengine.game.handlers.WindowHandler;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Display class is responsible to create the window of the game.
 *
 * @author leonardo
 */
public class Display {

    // Final variables
    private final String TITLE;
    private final int WIDTH, HEIGHT;
    private final Dimension DIMENSIONS;

    // variables
    private JFrame frame;
    private Canvas canvas;

    /**
     * The constructor create the window.
     *
     * @param title The title that appears in the window.
     * @param width The width of the window.
     * @param height The height of the window.
     */
    public Display(String title, int width, int height) {
        this.TITLE = title;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.DIMENSIONS = new Dimension(WIDTH, HEIGHT);

        createDisplay();
    }

    /**
     * This function is responsible to create and show the window.
     */
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
    }

    /**
     *
     * @return Returns the object of the canvas.
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     *
     * @return Returns the object of the frame.
     */
    public JFrame getFrame() {
        return frame;
    }
}
