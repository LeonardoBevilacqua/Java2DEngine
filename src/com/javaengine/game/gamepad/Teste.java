package com.javaengine.game.gamepad;


import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;


/**
 *
 * @author leonardo
 */
public class Teste {

    public static void main(String[] args) {
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        for (int i = 0; i < controllers.length; i++) {
            // nome
            System.out.println(controllers[i].getName());

            // tipo
            System.out.println("Type: " + controllers[i].getType().toString());

            // pegar os componentes do controle
            Component[] components = controllers[i].getComponents();

            System.out.println("Components count: " + components.length);

            for (int j = 0; j < components.length; j++) {
                // nome dos componentes
                System.out.println("Componente " + j + ": " + components[j].getName());

                System.out.println("    Identifier: " + components[j].getIdentifier().getName());

                System.out.println("    Tipo do componente: ");

                if (components[j].isRelative()) {
                    System.out.println("Relative");
                } else {
                    System.out.println("Absolute");
                }

                if (components[j].isAnalog()) {
                    System.out.println(" Analog");
                } else {
                    System.out.println(" Digital");
                }
            }

        }
    }

}
