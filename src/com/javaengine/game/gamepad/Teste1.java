package com.javaengine.game.gamepad;

/**
 *
 * @author leonardo
 */
public class Teste1 {

    public static void main(String[] args) {
        
        GamepadXboxEvent g = new GamepadXboxEvent();
        
        if (g.mainController != null) {
            
            while (!g.isPressed(g.getControllerComponents()[GamepadXboxEvent.BUTTON_START])) {
                g.getMainController().poll();

                if (g.isPressed(g.getControllerComponents()[GamepadXboxEvent.BUTTON_A])) {
                    System.err.println("hey");
                }
            }
        }
        
//        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
//        Controller main = controllers[0];
//
//        if (main != null) {
//            //System.out.println(main.getName());
//            boolean run = true;
//            while (run) {
//                main.poll();
//                Component[] c = main.getComponents();
//                for (int i = 0; i < c.length; i++ ) {
//                    if (c[i].getName().equals("Left Thumb 3") && c[i].getPollData() > 0) {
//                        //System.out.println(c.getName() + ": " + c.getPollData());
//                        System.out.println(c[i].getName() + " was pressed - " + c[i].getPollData());
//                    }
//                    
//                    if (c[17].getPollData() > 0) {
//                        System.err.println("ESSE > " + c[17].getPollData());
//                    }
//                    
//                    if (c[i].getName().equals("Select") && c[i].getPollData() > 0) {
//                        run = false;
//                    }
//                }
//            }
//
//        }
    }

}
