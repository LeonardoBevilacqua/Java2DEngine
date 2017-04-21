package com.javaengine.game;

import com.javaengine.game.handlers.Handler;
import com.javaengine.game.handlers.input.InputHandler;
import com.javaengine.game.display.Display;
import com.javaengine.game.gfx.Assets;
import com.javaengine.game.gfx.GameCamera;
import com.javaengine.game.handlers.WindowHandler;
import com.javaengine.game.handlers.input.MouseManager;
import com.javaengine.game.states.MenuState;
import com.javaengine.game.states.State;
import com.javaengine.game.utils.DebugMode;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {

// PUBLIC STATIC FINAL VARIABLES
    public static final String NAME = "Game2dEngine";
    public static final int GAME_WIDTH = 160, GAME_HEIGHT = GAME_WIDTH / 12 * 9;
    public static final int SCALE = 3;

// PRIVATE VARIBLES
    private Thread thread;

    private boolean running = false;
    private boolean debug = true;
    private int tickCount = 0;
    private int width, height;
    
    private DebugMode debugMode;

    private Display display;

    // graphics
    private BufferStrategy bs;
    private Graphics g;

    // states
    //private State gameState;
    private State menuState;
    //private State mpGameState;

    // input
    private InputHandler input;
    private MouseManager mouseManager;

    // window
    private WindowHandler window;

    // camera
    private GameCamera gameCamera;

    // handler
    private Handler handler;

    public void init() {
        handler = new Handler(this);
        debugMode = new DebugMode(handler);

        width = GAME_WIDTH * SCALE;
        height = GAME_HEIGHT * SCALE;
        display = new Display(NAME, width, height);

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

        Assets.init();
        gameCamera = new GameCamera(handler, 0, 0);

        //gameState = new GameState(handler);
        menuState = new MenuState(handler);
        //mpGameState = new MPGameState(handler);
        
        State.setState(menuState);
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this, NAME + "_main");
        thread.start();
    }

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
            boolean shouldRender = true; // frames limiter (false to make work)

            while (delta >= 1) {
                ticks++;
                tick();
                delta -= 1;
                shouldRender = true;
            }

            try { // avoid system overload
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            if (shouldRender) {
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                DebugMode.debug(DebugMode.DebugLevel.NOTSHOW, (ticks + " ticks, " + frames + " frames"));
                frames = 0;
                ticks = 0;
            }
        }
    }

    // update the logic of the game
    public void tick() {
        tickCount++;

        if (State.getCurrentState() != null) {
            State.getCurrentState().tick();
        }

        checkWindow();
    }

    // update the visual of the game
    public void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        // clear screen
        g.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        // draw        
        if (State.getCurrentState() != null) {
            State.getCurrentState().render(g);
        }

        // end draw
        g.dispose();
        bs.show();
    }

    private void checkWindow() {
        if (width != display.getFrame().getWidth()
                || height != display.getFrame().getHeight()) {
            width = display.getFrame().getWidth();
            height = display.getFrame().getHeight();
        }
    }

    // getters and setters
    public Display getDisplay() {
        return display;
    }

    public InputHandler getInput() {
        return input;
    }
    
    public MouseManager getMouseManager(){
        return mouseManager;
    }

    public GameCamera getGameCamera() {
        return gameCamera;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

//    public State getGameState() {
//        return gameState;
//    }
    
    public State getMenuState() {
        return menuState;
    }
    
//    public State getMPGameState() {
//        return mpGameState;
//    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isRunning() {
        return running;
    }

    
}
