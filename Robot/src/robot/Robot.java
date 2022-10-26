/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package robot;

import robot.control.Control;
import robot.vista.Vista;

/**
 *
 * @author bertu
 */
public class Robot {
    
    private Control control;
    private Vista vista;
    
    public Robot(){
        this.control = new Control();
        this.vista = new Vista("Robot con pasillos estrechos", control);
        control.setVista(vista);

        this.vista.mostrar();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Robot robot = new Robot();
    }
    
}
