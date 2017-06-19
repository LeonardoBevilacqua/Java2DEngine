package com.javaengine.game;

import com.javaengine.game.display.Display;
import com.javaengine.game.gfx.Assets;
import com.javaengine.game.gfx.GameCamera;
import com.javaengine.game.gfx.Text;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.handlers.WindowHandler;
import com.javaengine.game.handlers.input.KeyManager;
import com.javaengine.game.handlers.input.MouseManager;
import com.javaengine.game.menus.MainMenu;
import com.javaengine.game.menus.Menu;
import com.javaengine.game.menus.config.GameFullscreen;
import com.javaengine.game.states.MenuState;
import com.javaengine.game.states.State;
import com.javaengine.game.utils.DebugMode;
import com.javaengine.game.utils.ResolutionsManager;
import static com.javaengine.game.utils.ResolutionsManager.loadResolution;
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
    public static final int GAME_WIDTH = 1024,
            GAME_HEIGHT = GAME_WIDTH / 16 * 9;
    public static final boolean DEBUG = true;

// VARIBLES
    private Thread thread;
    private boolean running = false;
    private int tickCount = 0;
    private int width, height;
    private DebugMode debugMode;
    private int lastFrame;
    private int lastTick;

    // Window
    private Display display;

    // graphics
    private BufferStrategy bs;
    private Graphics g;

    // states
    private State menuState;

    // input
    private KeyManager keyManager;
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
    private void init() {
        try {
            handler = new Handler(this);

            width = GAME_WIDTH;
            height = GAME_HEIGHT;
            display = new Display(NAME, width, height);
            frameLimiter = true;
            debugMode = new DebugMode(handler);

            // ADD THE CONTROLLERS OF THE GAME  
            keyManager = new KeyManager();
            display.getCanvas().addKeyListener(keyManager);

            mouseManager = new MouseManager();
            display.getFrame().addMouseListener(mouseManager);
            display.getFrame().addMouseMotionListener(mouseManager);
            display.getCanvas().addMouseListener(mouseManager);
            display.getCanvas().addMouseMotionListener(mouseManager);

            window = new WindowHandler(handler);
            display.getFrame().addWindowListener(window);
            
            ResolutionsManager.loadResolution();
            GameFullscreen.checkFullScreen(display);

            Assets.init();
            gameCamera = new GameCamera(handler, 0, 0);

            Menu.addMenu(new MainMenu(handler,"MainMenu"));
            Menu.setMenu("MainMenu");

            menuState = new MenuState(handler);

            State.setState(menuState);

        } catch (Exception e) {
            debugMode.debug(DebugMode.DebugLevel.SEVERE, e.getMessage());
            System.exit(1);

        }
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

        System.out.println("The thread has started . . .");
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
            System.exit(0);
            System.out.println("The thread has stopped . . .");
        } catch (InterruptedException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
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
                JOptionPane.showMessageDialog(null,
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
     * Updates all variables of the game.
     */
    public void tick() {
        tickCount++;
        keyManager.tick();

        if (State.getCurrentState() != null) {
            State.getCurrentState().tick();
        }
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

        if (DEBUG) {
            Text.drawString(
                    g,
                    "FPS: " + lastFrame + " TICKS: " + lastTick,
                    0,
                    10,
                    false,
                    Color.GREEN,
                    Assets.font16
            );
        }

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

    /**
     *
     * @return Returns the object of the keyboard input.
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }

}
