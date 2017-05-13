package com.javaengine.game.states;

import com.javaengine.game.handlers.Handler;
import java.awt.Graphics;

/**
 * The base of the states. Also manages all the states.
 *
 * @author leonardo
 */
public abstract class State {

    /**
     * The current state active.
     */
    private static State currentState = null;

    /**
     * Sets the new current state.
     *
     * @param state The state object.
     */
    public static void setState(State state) {
        currentState = state;
    }

    /**
     * Gets the current state.
     *
     * @return The State.
     */
    public static State getCurrentState() {
        return currentState;
    }

    // class
    protected Handler handler;

    /**
     * Initilize the base of the states.
     *
     * @param handler The handler of the game.
     */
    public State(Handler handler) {
        this.handler = handler;
    }

    /**
     * Update everything in the current state.
     */
    public abstract void tick();

    /**
     * Render everything in the current state.
     *
     * @param g The graphic object.
     */
    public abstract void render(Graphics g);
}
