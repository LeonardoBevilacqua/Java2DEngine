/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaengine.game.states;

/**
 *
 * @author leonardo
 */
public class StateManager {

    /**
     * The current state active.
     */
    private State currentState;

    public StateManager(State state) {
        setCurrentState(state);
    }

    /**
     * Sets the new current state.
     *
     * @param state The state object.
     */
    public void setCurrentState(State state) {
        currentState = null;
        currentState = state;
    }

    /**
     * Gets the current state.
     *
     * @return The State.
     */
    public State getCurrentState() {
        return currentState;
    }
}
