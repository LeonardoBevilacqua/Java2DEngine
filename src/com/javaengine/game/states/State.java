package com.javaengine.game.states;

import com.javaengine.game.handlers.Handler;
import java.awt.Graphics;

/**
 *
 * @author leonardo
 */
public abstract class State {

    private static State currentState = null;

    public static void setState(State state) {
        currentState = state;
    }

    public static State getCurrentState() {
        return currentState;
    }

    // class
    protected Handler handler;

    public State(Handler handler) {
        this.handler = handler;
    }

    public abstract void tick();

    public abstract void render(Graphics g);
}
