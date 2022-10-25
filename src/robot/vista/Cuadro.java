/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package robot.vista;

import java.awt.Rectangle;

/**
 *
 * @author bertu
 */
public class Cuadro {

    Rectangle rectangulo;
    boolean pared;

    public Cuadro(int x, int y, int w, int h, boolean p) {
        this.rectangulo = new Rectangle(x, y, w, h);
        this.pared = p;
    }

    public void setPared(boolean p) {
        this.pared = p;
    }

    public void setWidth(int w) {
        this.rectangulo.setRect(this.rectangulo.getX(),  this.rectangulo.getY(), w,  this.rectangulo.getHeight());
   }

    public void setHeight(int h) {
        this.rectangulo.setRect(this.rectangulo.getX(),  this.rectangulo.getY(), this.rectangulo.getWidth(), h);
    }
    
    public boolean getPared() {
        return this.pared;
    }

    public int getX() {
        return (int) this.rectangulo.getX();
    }

    public int getY() {
        return (int) this.rectangulo.getY();
    }
    
    public int getWidth(){
        return (int) this.rectangulo.getWidth();
    }
    
    public int getHeight(){
        return (int) this.rectangulo.getHeight();
    }

}

/*
@Override
            public void setRect(double x, double y, double w, double h) {}
            
            @Override
            public int outcode(double x, double y) {return 0;}
            
            @Override
            public Rectangle2D createIntersection(Rectangle2D r) {return null;}
            
            @Override
            public Rectangle2D createUnion(Rectangle2D r) {return null;}
            
            @Override
            public double getX() {return 0;}
            
            @Override
            public double getY() {return 0;}
            
            @Override
            public double getWidth() {return 0;}
            
            @Override
            public double getHeight() {return 0;}
            
            @Override
            public boolean isEmpty() {return false;}
*/
