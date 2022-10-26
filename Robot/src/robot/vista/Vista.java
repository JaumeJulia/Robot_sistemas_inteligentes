package robot.vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import robot.control.Agente;
import robot.control.Control;

/**
 *
 * @author bertu
 */
public class Vista extends JFrame implements ChangeListener, ComponentListener, MouseListener, ActionListener {

    private Control control;

    private Graphics g;

    public Cuadro[][] matrizCuadros;
    private int minX, maxX, minY, maxY;

    private int ancho = 800;
    private int alto = 500;

    private final JPanel recinto = new JPanel();
    private final JPanel opciones = new JPanel();

    private JButton posicionarAgente;
    private boolean agente = false;
    private int posicionAgente[] = new int[2];
    private final JLabel tamañoRecintoLabel = new JLabel("Tamaño del recinto: ");
    private JTextField tamañoRecintoText = new JTextField();
    private final JSlider sliderTamañoRecinto = new JSlider(JSlider.HORIZONTAL, 5, 20, 10);

    //CONSTRUCTOR DE VISTA
    public Vista(String nombre, Control control) {

        super(nombre);
        this.control = control;
        initComponents();

    }

    //MOSTRAMOS LA VENTANA
    public void mostrar() {

        this.setPreferredSize(new Dimension((int) ancho, (int) alto));
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    //INICIAMOS LOS COMPONENTES DE LA VENTANA
    private void initComponents() {

        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        opciones.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        posicionarAgente = new JButton("Agente");
        posicionarAgente.addActionListener(this);
        tamañoRecintoText = new JTextField("5");
        tamañoRecintoText.setEditable(false);
        tamañoRecintoText.setText(String.valueOf(sliderTamañoRecinto.getValue()));
        sliderTamañoRecinto.addChangeListener(this);

        JPanel textos = new JPanel();
        textos.setLayout(new FlowLayout());
        textos.add(tamañoRecintoLabel);
        textos.add(tamañoRecintoText);

        c.gridwidth = 1;
        c.weighty = .2;
        c.gridx = 0;
        c.gridy = 1;
        opciones.add(textos, c);
        c.gridx = 0;
        c.gridy = 2;
        opciones.add(sliderTamañoRecinto, c);
        c.gridx = 0;
        c.gridy = 3;
        opciones.add(posicionarAgente, c);
        opciones.setMaximumSize(new Dimension((int) (ancho * 0.25), alto));

        recinto.setMaximumSize(new Dimension((int) (ancho * 0.75), alto));

        this.add(recinto);
        this.add(opciones);
        this.addComponentListener(this);
        this.addMouseListener(this);

        //CALCULAMOS LO QUE OCUPA LA CUADRICULA Y LO QUE OCUPA CADA CUADRO
        double aux = (ancho * 0.75);
        if ((aux / alto) > 1) {
            minX = (int) (((aux - alto) / 2) + 30);
            minY = 50;
            maxX = (int) (aux - (((aux - alto) / 2) + 30));
            maxY = alto - 30;
        } else {
            minX = 30;
            minY = (int) (((alto - aux) / 2) + 30);
            maxX = (int) (aux - 30);
            maxY = (int) (alto - (((alto - aux) / 2) + 30));
        }
        int tamañoBase = (maxY - minY) / sliderTamañoRecinto.getValue(), posX = minX, posY = minY;

        //INICIALIZAMOS LA MATRIZ DE CUADROS CON EL TAMAÑO INDICADO EN EL SLIDER
        matrizCuadros = new Cuadro[sliderTamañoRecinto.getValue()][sliderTamañoRecinto.getValue()];
        for (int i = 0; i < sliderTamañoRecinto.getValue(); i++) {
            for (int j = 0; j < sliderTamañoRecinto.getValue(); j++) {
                matrizCuadros[i][j] = new Cuadro(posX, posY, tamañoBase, tamañoBase, false, false);
                posY += tamañoBase;
            }
            posX += tamañoBase;
            posY = minY;
        }
        repaint();
    }

    //METODO QUE SE EJECUTA CUANDO LA VENTANA SUFRE UNA MODIFICACIÓN DE TAMAÑO
    private void reinit() {
        //RECALCULAMOS LOS ESPACIOS QUE OCUPAN LOS PANELES Y EL TAMAÑO DE LOS
        //CUADROS DE LA MATRIZ
        opciones.setMaximumSize(new Dimension((int) (ancho * 0.25), alto));
        recinto.setMaximumSize(new Dimension((int) (ancho * 0.75), alto));
        this.add(recinto);
        this.add(opciones);
        double aux = (ancho * 0.75);
        if ((aux / alto) > 1) {
            minX = (int) (((aux - alto) / 2) + 30);
            minY = 50;
            maxX = (int) (aux - (((aux - alto) / 2) + 30));
            maxY = alto - 30;
        } else {
            minX = 30;
            minY = (int) (((alto - aux) / 2) + 30);
            maxX = (int) (aux - 30);
            maxY = (int) (alto - (((alto - aux) / 2) + 30));
        }
        int tamañoBase = (maxY - minY) / sliderTamañoRecinto.getValue(), posX = minX, posY = minY;

        Cuadro auxMatrizCuadros[][] = matrizCuadros;
        matrizCuadros = new Cuadro[sliderTamañoRecinto.getValue()][sliderTamañoRecinto.getValue()];
        //CREAMOS UNA NUEVA MATRIZ DEL TAMAÑO INDICADO POR EL SLIDER Y COPIAMOS
        //EL CONTENIDO DE LA MATRIZ ANTERIOR
        for (int i = 0; i < sliderTamañoRecinto.getValue(); i++) {
            for (int j = 0; j < sliderTamañoRecinto.getValue(); j++) {
                if (auxMatrizCuadros[0].length > i && auxMatrizCuadros[0].length > j) {
                    matrizCuadros[i][j] = new Cuadro(posX, posY, tamañoBase, tamañoBase,
                            auxMatrizCuadros[i][j].isPared(), auxMatrizCuadros[i][j].isAgente());
                } else {
                    matrizCuadros[i][j] = new Cuadro(posX, posY, tamañoBase, tamañoBase, false, false);
                }
                posY += tamañoBase;
            }
            posX += tamañoBase;
            posY = minY;
        }
        repaint();

    }
    
    public void moverAgente(int[] posicion){
        matrizCuadros[posicionAgente[0]][posicionAgente[1]].setAgente(false);
        matrizCuadros[posicion[0]][posicion[1]].setAgente(true);
        posicionAgente[0] = posicion[0];
        posicionAgente[1] = posicion[1];
        repaint();
    }

    //PINTAMOS LA MATRIZ
    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, (int) (ancho * 0.75) - 30, alto);
        g.setColor(Color.BLACK);
        for (int i = 0; i < sliderTamañoRecinto.getValue(); i++) {
            for (int j = 0; j < sliderTamañoRecinto.getValue(); j++) {
                int x = matrizCuadros[i][j].getX(), y = matrizCuadros[i][j].getY(),
                        w = matrizCuadros[i][j].getWidth(), h = matrizCuadros[i][j].getHeight();
                g.drawRect(x, y, w, h);
                if (matrizCuadros[i][j].isPared()) {
                    g.fillRect(x, y, w, h);
                }
                if (matrizCuadros[i][j].isAgente()) {
                    g.setColor(Color.BLUE);
                    g.fillRect(x, y, w, h);
                    g.setColor(Color.BLACK);
                    //g.drawImage(img, x, y, w, h, this);
                }
            }
        }
    }

    //LISTENER PARA EL SLIDER
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider src = (JSlider) e.getSource();
        if (!src.getValueIsAdjusting()) {
            tamañoRecintoText.setText(String.valueOf(src.getValue()));
            reinit();
        }

    }

    //LISTENER PARA EL RESIZE DE LA VENTANA
    @Override
    public void componentResized(ComponentEvent e) {
        Dimension nuevoTamaño = e.getComponent().getBounds().getSize();
        ancho = nuevoTamaño.width;
        alto = nuevoTamaño.height;
        reinit();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    //METODO QUE DEVUELVE EL CUADRO INDICADO POR a[]
    public Cuadro getCasilla(int[] a) {
        return matrizCuadros[a[0]][a[1]];
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    //LISTENER PARA CUANDO SE PULSA EL CLICK DEL RATON. CON ESTE METODO CAMBIAMOS
    //LOS ESTADOS DE pared Y agente DE LAS CASILLAS DE LA MATRIZ
    @Override
    public void mousePressed(MouseEvent e) {
        try {
            int i = 0, j = 0;

            for (i = 0; i <= matrizCuadros[0].length; i++) {
                if (i == matrizCuadros[0].length) {
                    if (matrizCuadros[0][i - 1].getY() + matrizCuadros[0][i - 1].getHeight() >= e.getY()) {
                        i--;
                        break;
                    }
                }
                if (matrizCuadros[0][i].getY() > e.getY()) {
                    i--;
                    break;
                }
            }

            for (j = 0; j <= matrizCuadros[0].length; j++) {
                if (j == matrizCuadros[0].length) {
                    if (matrizCuadros[j - 1][0].getX() + matrizCuadros[j - 1][0].getWidth() >= e.getX()) {
                        j--;
                        break;
                    }
                }
                if (matrizCuadros[j][i].getX() > e.getX()) {
                    j--;
                    break;
                }
            }

            if (agente == true && matrizCuadros[j][i].isPared() == false) {
                matrizCuadros[posicionAgente[0]][posicionAgente[1]].setAgente(false);
                matrizCuadros[j][i].setAgente(true);
                posicionAgente[0] = j;
                posicionAgente[1] = i;
                Agente robot = new Agente(posicionAgente, 1);
                control.setAgente(robot);
                agente = false;
            } else if (agente == false && matrizCuadros[j][i].isAgente() == false) {
                if (matrizCuadros[j][i].isPared() == false) {
                    matrizCuadros[j][i].setPared(true);
                } else {
                    matrizCuadros[j][i].setPared(false);
                }
            }
            repaint();
        } catch (Exception outOfBounds) {

        }
    }

    @Override
    public void mouseReleased(MouseEvent e
    ) {
    }

    @Override
    public void mouseEntered(MouseEvent e
    ) {
    }

    @Override
    public void mouseExited(MouseEvent e
    ) {
    }

    //LISTENER PARA LOS BOTONES DE LAS OPCIONES
    @Override
    public void actionPerformed(ActionEvent e
    ) {
        if (e.getSource() == posicionarAgente) {
            agente = true;
        }
    }
}
