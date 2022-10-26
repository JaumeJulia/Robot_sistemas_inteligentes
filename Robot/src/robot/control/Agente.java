/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.control;

import java.util.Random;
import robot.modelo.Direcciones;
import robot.vista.Vista;

/**
 *
 * @author Daxmaster
 */
public class Agente {
    
    Direcciones direcciones[] = {Direcciones.NORTE, Direcciones.ESTE, Direcciones.SUR, Direcciones.OESTE};
    Vista vista;
    int direccionActual;
    int rotacion; // +1 cuando se rota en sentido horario, -1 cuando es antihorario
    int[] posicionActual;
    int atascado = 0;
    
    public Agente(int[] posicionActual, int rotacion){
        Random ran = new Random();
        direccionActual = ran.nextInt(20) % 4;
        this.rotacion = rotacion;
        this.posicionActual = posicionActual;
    }
    
    public int[] moverAgente(){
        //logica del agente para decidir hacia donde moverse
        rotar(-rotacion);
        int[] siguienteCasilla = {direcciones[direccionActual].X + posicionActual[0],
            direcciones[direccionActual].Y + posicionActual[1]}; //aqui espera encontrar pared
        //AQUI QUIERE GIRAR HACIA DONDE ESPERA ENCONTRAR LA PARED QUE SIGUE
        if(!vista.getCasilla(siguienteCasilla).isPared() && atascado < 3){
            atascado += 1;
        } else {
        //AQUI INTENTA SEGUIR RECTO PORQUE PUEDE SEGUIR LA PARED DEL SUPUESTO PERIMETRO
            rotar(rotacion);
            siguienteCasilla[0] = direcciones[direccionActual].X + posicionActual[0];
            siguienteCasilla[1] = direcciones[direccionActual].Y + posicionActual[1];
            if(vista.getCasilla(siguienteCasilla).isPared()){
        //AQUI INTENTA GIRAR HACIA DONDE ESPERA TENER QUE GIRAR PARA SEGUIR CORRECTAMENTE EL PERIMETRO
                rotar(rotacion);
                siguienteCasilla[0] = direcciones[direccionActual].X + posicionActual[0];
                siguienteCasilla[1] = direcciones[direccionActual].Y + posicionActual[1];
                if(!vista.getCasilla(siguienteCasilla).isPared()){
                    if(atascado > 0){
                        atascado -= 1;
                    }
                } else {
        //NO LE QUEDA OTRA QUE RECULAR
        //Esto dejaria de funcionar si justamente el robot se le encierra en un espacio de una casilla, donde tiene paredes en las cuatro direcciones.
        //Es una perrada que hagan eso, no vale la pena comprobar ese caso.
                    rotar(rotacion);
                    siguienteCasilla[0] = direcciones[direccionActual].X + posicionActual[0];
                    siguienteCasilla[1] = direcciones[direccionActual].Y + posicionActual[1];
                }
            }
        }
        posicionActual = siguienteCasilla;
        return siguienteCasilla;
        //avanzar(siguienteCasilla);
    }
    
    //public void avanzar(int[] siguienteCasilla){
        //vista.moverAgente(siguienteCasilla, direcciones[direccionActual]);
    //}
    
    public void rotar(int sentido){
        direccionActual = (direccionActual + sentido) % 4;        
    }
    
}
