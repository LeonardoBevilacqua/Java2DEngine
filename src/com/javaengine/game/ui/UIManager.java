package com.javaengine.game.ui;

import com.javaengine.game.handlers.Handler;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * The UIManager class is responsible to manage all the UI components.
 *
 * @author leonardo
 */
public class UIManager {

    private Handler handler;
    private ArrayList<UIObject> objects;

    /**
     * Initialize the UIManager.
     *
     * @param handler The handler of the game.
     */
    public UIManager(Handler handler) {
        this.handler = handler;
        objects = new ArrayList<>();
    }

    /**
     * Updates all the components.
     */
    public void tick() {
        for (UIObject o : objects) {
            o.tick();
        }
    }

    /**
     * Renders all the components.
     *
     * @param g
     */
    public void render(Graphics g) {
        for (UIObject o : objects) {
            o.render(g);
        }
    }

    /**
     * Verifies if the mouse is on one of the component.
     *
     * @param e The mouseEvent.
     */
    public void onMouseMove(MouseEvent e) {
        for (UIObject o : objects) {
            o.onMouseMove(e);
        }
    }

    /**
     *
     * @param e
     */
    public void onMouseRelease(MouseEvent e) {
        for (UIObject o : objects) {
            o.onMouseRelease(e);
        }
    }

    /**
     * Add a new UI component.
     *
     * @param o Object of the component.
     */
    public void addObject(UIObject o) {
        objects.add(o);
    }

    /**
     * Remove a UI component.
     *
     * @param o Object of the component.
     */
    public void removeObject(UIObject o) {
        objects.remove(o);
    }

    // getters and setters
    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ArrayList<UIObject> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<UIObject> objects) {
        this.objects = objects;
    }

}
