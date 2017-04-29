package com.javaengine.game;

import com.javaengine.game.display.Display;
import com.javaengine.game.display.ScreenManager;
import com.javaengine.game.gfx.Assets;
import com.javaengine.game.gfx.GameCamera;
import com.javaengine.game.gfx.Text;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.handlers.WindowHandler;
import com.javaengine.game.handlers.input.InputHandler;
import com.javaengine.game.handlers.input.MouseManager;
import com.javaengine.game.states.MenuState;
import com.javaengine.game.states.State;
import com.javaengine.game.utils.DebugMode;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JOptionPane;

/**
 * Game class is the core of the game. It sets the dimension of the game and
 * start the main components. Also is responsible to update all variables of the
 * game and render the game.
 *
 * @author leonardo
 */
public class Game implements Runnable {

// STATIC VARIABLES
    public static final String NAME = "Game2dEngine";
    public static final int GAME_WIDTH = 160, GAME_HEIGHT = GAME_WIDTH / 12 * 9;
    public static final int SCALE = 3;
    public static final boolean DEBUG = true;

// PRIVATE VARIBLES
    private Thread thread;
    private boolean running = false;
    private int tickCount = 0;
    private int width, height;
    private DebugMode debugMode;
    private int lastFrame;
    private int lastTick;

    // Window
    private Display display;
    private ScreenManager screen;
    private String[] resolutions;

    // graphics
    private BufferStrategy bs;
    private Graphics g;

    // states
    private State menuState;

    // input
    private InputHandler input;
    private MouseManager mouseManager;

    // window
    private WindowHandler window;

    // camera
    private GameCamera gameCamera;

    // handler
    private Handler handler;

    // frame limiter
    private boolean frameLimiter;

    /**
     * Initializes all the components that were defined in the Game class.
     */
    public void init() {
        handler = new Handler(this);

        width = GAME_WIDTH * SCALE;
        height = GAME_HEIGHT * SCALE;
        display = new Display(NAME, width, height);
        frameLimiter = false;
        debugMode = new DebugMode(handler);

        // ADD THE CONTROLLERS OF THE GAME  
        input = new InputHandler();
        display.getCanvas().addKeyListener(input);

        mouseManager = new MouseManager();
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        window = new WindowHandler(handler);
        display.getFrame().addWindowListener(window);
        screen = new ScreenManager();
        resolutions = new String[screen.getAllCompatibleDisplayModes().length];
        for (int i = 0; i < resolutions.length; i++) {
            resolutions[i] = screen.getAllCompatibleDisplayModes()[i].getWidth() + "x"
                    + screen.getAllCompatibleDisplayModes()[i].getHeight();
        }

        Assets.init();
        gameCamera = new GameCamera(handler, 0, 0);

        menuState = new MenuState(handler);

        State.setState(menuState);
    }

    /**
     * Starts the thread if isn't already started.
     */
    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this, NAME + "_main");
        thread.start();
    }

    /**
     * Ends the thread if isn't already stopped.
     */
    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;

        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Starts the core of the game.
     */
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;
        int frames = 0;
        int ticks = 0;
        long lastTimer = System.currentTimeMillis();
        double delta = 0;
        init();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = !frameLimiter;

            while (delta >= 1) {
                ticks++;
                tick();
                delta -= 1;
                shouldRender = true;
            }

            try { // avoid system overload
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(display.getFrame(),
                        ex.getMessage(), "Erro na Thread", JOptionPane.ABORT);
                ex.printStackTrace();
            }

            if (shouldRender) {
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                lastFrame = frames;
                lastTick = ticks;

                frames = 0;
                ticks = 0;
            }
        }
    }

    /**
     * Verify if the screen dimensions has changed. Updates the variables if
     * necessary.
     */
    private void checkWindow() {
        if (width != display.getFrame().getWidth()
                || height != display.getFrame().getHeight()) {
            width = display.getFrame().getWidth();
            height = display.getFrame().getHeight();
        }
    }

    /**
     * Checks if the window is fullscreen.
     */
    private void checkFullScreen() {
        if (input.f11.isPressed()) {
            input.f11.setPressed(false);
            if (screen.getFullScreenWindow() == null) {
                String resolution = (String) JOptionPane.showInputDialog(null, "Resolução", "Falso UI", JOptionPane.QUESTION_MESSAGE, null,
                        resolutions, resolutions[0]);
                int index = 0;

                for (int i = 0; i < resolutions.length; i++) {
                    if (resolutions[i].equals(resolution)) {
                        index = i;
                        break;
                    }
                }

                screen.setFullScreen(display, screen.getAllCompatibleDisplayModes()[index]);
            } else {
                screen.setWindowScreen();
            }
        }
    }

    /**
     * Updates all variables of the game.
     */
    public void tick() {
        tickCount++;

        if (State.getCurrentState() != null) {
            State.getCurrentState().tick();
        }

        checkFullScreen();
        checkWindow();
    }

    /**
     * Updates the screen of the game.
     */
    public void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        // clear screen
        g.clearRect(0, 0, width, height);

        // draw        
        if (State.getCurrentState() != null) {
            State.getCurrentState().render(g);
        }

        Text.drawString(g, "FPS: " + lastFrame + " TICKS: " + lastTick, 0, 10, false, Color.GREEN, Assets.font16);

        // end draw
        g.dispose();
        bs.show();
    }

    // getters and setters
    /**
     *
     * @return Returns the object of the display.
     */
    public Display getDisplay() {
        return display;
    }

    /**
     *
     * @return Returns the object of the keyboard input.
     */
    public InputHandler getInput() {
        return input;
    }

    /**
     *
     * @return Returns the object of the mouse input.
     */
    public MouseManager getMouseManager() {
        return mouseManager;
    }

    /**
     *
     * @return Returns the object of the game's camera.
     */
    public GameCamera getGameCamera() {
        return gameCamera;
    }

    /**
     *
     * @return Returns the width of the game.
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @return Returns the height of the game.
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @return Returns the object of the menu state.
     */
    public State getMenuState() {
        return menuState;
    }

    /**
     *
     * @return Returns if the game is running or not.
     */
    public boolean isRunning() {
        return running;
    }
}
