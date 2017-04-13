package com.javaengine.game;

import com.javaengine.game.entities.Player;
import com.javaengine.game.gfx.Screen;
import com.javaengine.game.gfx.SpriteSheet;
import com.javaengine.game.level.Level;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game extends Canvas implements Runnable{

    public static final int WIDTH = 160;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final int SCALE = 3;
    public static final String NAME = "Game2dEngine";
    
    private JFrame frame;
    
    public boolean running = false;
    public int tickCount = 0;
    
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private int[] colours = new int[6*6*6];
    
    private Screen screen;
    public InputHandler input;
    public Level level;
    public Player player;
    
    public Game(){
        setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT * SCALE));
        
        frame = new JFrame(NAME);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        frame.add(this,BorderLayout.CENTER);
        frame.pack();
        
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void init(){
        int index = 0;
        for (int r = 0; r < 6; r++) { // reds
            for (int g = 0; g < 6; g++) { // greens
                for (int b = 0; b < 6; b++) { // blues
                    int rr = (r * 255/5); // red
                    int gg = (g * 255/5); // green
                    int bb = (b * 255/5); // blue
                    
                    colours[index++] = rr << 16 | gg << 8 | bb;
                }
            }
        }
        
        screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/sprite_sheet.png"));
        input = new InputHandler(this);
        level = new Level("/levels/water_test_level.png");
        player = new Player(level, 50, 50, input, JOptionPane.showInputDialog(this,"Please enter a username:","Temporary GUI", JOptionPane.INFORMATION_MESSAGE));
        level.addEntity(player);
    }
    
    public synchronized void start() {
        running = true;
        new Thread(this).start();
    }
    
    public synchronized void stop() {
        running = false;
    }
    
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D/60D;
        
        int frames = 0;
        int ticks = 0;
        
        long lastTimer = System.currentTimeMillis();
        double delta = 0;
        
        init();
        
        while(running){
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
                System.out.println(ticks + " ticks, " + frames + " frames");
                frames = 0;
                ticks = 0;
            }
        }
    }
    
    // update the logic of the game
    public void tick(){
        tickCount++;
        
        level.tick();
    }
    // update the visual of the game
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        
        int xOffset = player.x - (screen.width/2);
        int yOffset = player.y - (screen.height/2);
        
        level.renderTiles(screen, xOffset, yOffset);
        
        level.renderEntities(screen);
        
        for (int y = 0; y < screen.height; y++) {
            for (int x = 0; x < screen.width; x++) {
                int colourCode = screen.pixels[x + y * screen.width];
                if (colourCode < 255) pixels[x + y * WIDTH] = colours[colourCode];
            }
        }
        
        Graphics g = bs.getDrawGraphics();
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        
        g.dispose();
        bs.show();
    }
    
    public static void main(String[] args) {
        new Game().start();
    }    
}
