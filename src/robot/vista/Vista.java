package robot.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import robot.control.Control;

/**
 *
 * @author bertu
 */
public class Vista extends JFrame implements ChangeListener, ComponentListener {

    private Control control;
    
    private Graphics g;
    
    public Cuadro[][] matrizCuadros;
    private int minX, maxX, minY, maxY;

    private int ancho = 750;
    private int alto = 500;

    private JPanel recinto = new JPanel();
    private JPanel opciones = new JPanel();

    private JButton reiniciar;
    private JButton posicionarAgente;
    private JLabel tamañoRecintoLabel = new JLabel("Tamaño del recinto: ");
    private JTextField tamañoRecintoText = new JTextField();
    private JSlider sliderTamañoRecinto = new JSlider(JSlider.HORIZONTAL, 5, 20, 10);

    public Vista(String nombre, Control control) {

        super(nombre);
        this.control = control;
        repaint();
        initComponents();

    }

    public void mostrar() {

        this.setPreferredSize(new Dimension((int) ancho, (int) alto));
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void initComponents() {

        reiniciar = new JButton("Reiniciar");
        posicionarAgente = new JButton("Agente");
        tamañoRecintoText = new JTextField("5");
        tamañoRecintoText.setEditable(false);
        tamañoRecintoText.setText(String.valueOf(sliderTamañoRecinto.getValue()));
        sliderTamañoRecinto.addChangeListener(this);
        
        JPanel textos = new JPanel();
        textos.setLayout(new FlowLayout());
        textos.add(tamañoRecintoLabel);
        textos.add(tamañoRecintoText);
        
        opciones.setLayout(new GridLayout(4, 1, 10, 10));
        opciones.add(textos);
        opciones.add(sliderTamañoRecinto);
        opciones.add(posicionarAgente);
        opciones.add(reiniciar);
        opciones.setBounds((int)(ancho - (ancho*0.25)), 0, (int)(ancho*0.25), alto);
        this.add(recinto, BorderLayout.WEST);
        this.add(opciones, BorderLayout.EAST);
        this.addComponentListener(this);
        
        double aux = (ancho*0.75);
        if((aux/alto) > 1){
            minX = (int) (((aux-alto)/2) + 30); minY = 50; maxX = (int) (aux - (((aux-alto)/2) + 30)) ; maxY = alto - 30;
        }else{
            minX = 30; minY = (int) (((alto-aux)/2) + 30); maxX = (int) (aux - 30) ; maxY = (int) (alto - (((alto-aux)/2) + 30));
        }
        int tamañoBase = (maxY-minY)/sliderTamañoRecinto.getValue(), posX = minX, posY = minY;
        
        matrizCuadros = new Cuadro[sliderTamañoRecinto.getValue()][sliderTamañoRecinto.getValue()];
        for(int i = 0; i < sliderTamañoRecinto.getValue(); i++){
            for(int j = 0; j < sliderTamañoRecinto.getValue(); j++){
                matrizCuadros[i][j] = new Cuadro(posX, posY, tamañoBase, tamañoBase, false);
                posY += tamañoBase;
            }
            posX += tamañoBase;
            posY = minY;
        }
        repaint();
    }
    
    private void reinit(){
        opciones.setBounds((int)(ancho - (ancho*0.25)), 0, (int)(ancho*0.25), alto);
        this.add(opciones, BorderLayout.EAST);
        double aux = (ancho*0.75);
        if((aux/alto) > 1){
            minX = (int) (((aux-alto)/2) + 30); minY = 50; maxX = (int) (aux - (((aux-alto)/2) + 30)) ; maxY = alto - 30;
        }else{
            minX = 30; minY = (int) (((alto-aux)/2) + 30); maxX = (int) (aux - 30) ; maxY = (int) (alto - (((alto-aux)/2) + 30));
        }
        int tamañoBase = (maxY-minY)/sliderTamañoRecinto.getValue(), posX = minX, posY = minY;
        
        matrizCuadros = new Cuadro[sliderTamañoRecinto.getValue()][sliderTamañoRecinto.getValue()];
        for(int i = 0; i < sliderTamañoRecinto.getValue(); i++){
            for(int j = 0; j < sliderTamañoRecinto.getValue(); j++){
                matrizCuadros[i][j] = new Cuadro(posX, posY, tamañoBase, tamañoBase, false);
                posY += tamañoBase;
            }
            posX += tamañoBase;
            posY = minY;
        }
    }
    
    @Override
    public void paint(Graphics g){
        super.paintComponents(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, (int)(ancho*0.75)-30, alto);
        g.setColor(Color.BLACK);
        for(int i = 0; i < sliderTamañoRecinto.getValue(); i++){
            for(int j = 0; j < sliderTamañoRecinto.getValue(); j++){
                int x = matrizCuadros[i][j].getX(), y = matrizCuadros[i][j].getY(),
                    w = matrizCuadros[i][j].getWidth(), h = matrizCuadros[i][j].getHeight();
                g.drawRect(x, y, w, h);
            }
        }
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider src = (JSlider) e.getSource();
        if (!src.getValueIsAdjusting()) {
            tamañoRecintoText.setText(String.valueOf(src.getValue()));
        }

    }

    @Override
    public void componentResized(ComponentEvent e) {
        Dimension nuevoTamaño = e.getComponent().getBounds().getSize();
        ancho = nuevoTamaño.width;
        alto = nuevoTamaño.height;
        reinit();
    }

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}
}
