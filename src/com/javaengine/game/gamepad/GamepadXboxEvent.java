package com.javaengine.game.gamepad;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

/**
 *
 * @author leonardo
 */
public class GamepadXboxEvent {

    public final static int BUTTON_A = 0;
    public final static int BUTTON_B = 1;
    public final static int BUTTON_X = 2;
    public final static int BUTTON_Y = 3;
    public final static int BUMPER_LEFT = 4;
    public final static int BUMPER_RIGHT = 5;
    public final static int BUTTON_SELECT = 6;
    public final static int BUTTON_START = 7;
    public final static int BUTTON_XBOX = 8;
    public final static int ANALOG_BUTTON_LEFT = 9;
    public final static int ANALOG_BUTTON_RIGHT = 10;
    public final static int ANALOG_LEFT_X = 11;
    public final static int ANALOG_LEFT_Y = 12;
    public final static int TRIGGER_LEFT = 13;
    public final static int ANALOG_RIGHT_X = 14;
    public final static int ANALOG_RIGHT_Y = 15;
    public final static int TRIGGER_RIGHT = 16;
    public final static int D_PAD = 17;

    public final static float D_PAD_LEFT = 1.0f;
    public final static float D_PAD_RIGHT = 0.5f;
    public final static float D_PAD_UP = 0.25f;
    public final static float D_PAD_DOWN = 0.75f;

    protected Controller mainController;
    protected Component[] controllerComponents;

    private boolean pressed;
    private boolean controllerRunning;

    public GamepadXboxEvent() {
        init();
        run();
    }

    private void init() {
        checkController();
        pressed = false;

    }

    private void run() {
        checkController();
        if (controllerRunning) {
            mainController.poll();
        }
    }

    public boolean isPressed(Component component) {
        if (controllerRunning) {
            return pressed = (component.getPollData() > 0);
        }
        return false;
    }

    private void checkController() {
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        if (controllers.length != 0) {
            mainController = controllers[0];
            controllerComponents = mainController.getComponents();
            controllerRunning = true;
        } else {
            controllerRunning = false;
        }
    }

    public Controller getMainController() {
        return mainController;
    }

    public Component[] getControllerComponents() {
        return controllerComponents;
    }

    public Component getComponent(int index){
        return controllerComponents[index];
    }
    
    public float dpad(){
        if (isPressed(this.getComponent(D_PAD))) {
            return this.getComponent(D_PAD).getPollData();
        }
        return 0;
    }
}
