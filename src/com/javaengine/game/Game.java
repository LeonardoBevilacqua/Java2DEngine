package com.javaengine.game;

import com.javaengine.game.gfx.SpriteSheet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

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
    
    private SpriteSheet spriteSheet = new SpriteSheet("/sprite_sheet.png");
    
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
        
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = i + tickCount;
        }
    }
    // update the visual of the game
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
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
