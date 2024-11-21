import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Carrera extends JPanel {

    private Image fondo;
    private Image coche1, coche2, coche3, coche4;
    private int posicionCoche1, posicionCoche2, posicionCoche3, posicionCoche4;
    private int velocidadCoche1, velocidadCoche2, velocidadCoche3, velocidadCoche4;
    private int distanciaCircuito;
    private boolean carreraIniciada = false;
    private boolean[] cochesDetenidos = {false, false, false, false};
    private JProgressBar progresoCoche1, progresoCoche2, progresoCoche3, progresoCoche4;

    public Carrera(int distanciaCircuito) {
        this.distanciaCircuito = distanciaCircuito;

        // el background
        try {
            fondo = ImageIO.read(new File("imagenes/carretera.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // cargar imagenes de coches
        try {
            coche1 = ImageIO.read(new File("imagenes/mario.png"));
            coche2 = ImageIO.read(new File("imagenes/toadette.png"));
            coche3 = ImageIO.read(new File("imagenes/bolt.png"));
            coche4 = ImageIO.read(new File("imagenes/waluigi.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // inicializar las posiciones de los coches
        posicionCoche1 = 0;
        posicionCoche2 = 0;
        posicionCoche3 = 0;
        posicionCoche4 = 0;

        // velocidades aleatprias para los coches
        velocidadCoche1 = (int) (Math.random() * 10) + 1;
        velocidadCoche2 = (int) (Math.random() * 10) + 1;
        velocidadCoche3 = (int) (Math.random() * 10) + 1;
        velocidadCoche4 = (int) (Math.random() * 10) + 1;

        // barras de progreso
        progresoCoche1 = new JProgressBar(0, distanciaCircuito);
        progresoCoche2 = new JProgressBar(0, distanciaCircuito);
        progresoCoche3 = new JProgressBar(0, distanciaCircuito);
        progresoCoche4 = new JProgressBar(0, distanciaCircuito);

        progresoCoche1.setStringPainted(true);
        progresoCoche2.setStringPainted(true);
        progresoCoche3.setStringPainted(true);
        progresoCoche4.setStringPainted(true);

        progresoCoche1.setForeground(Color.decode("#021999")); // Color de la parte llena
        progresoCoche1.setBackground(Color.decode("#d2dc00")); // Color de la parte vacía
        progresoCoche1.setFont(new Font("Arial", Font.BOLD, 12));


        progresoCoche2.setForeground(Color.decode("#021999"));
        progresoCoche2.setBackground(Color.decode("#d2dc00"));
        progresoCoche2.setFont(new Font("Arial", Font.BOLD, 12));


        progresoCoche3.setForeground(Color.decode("#021999"));
        progresoCoche3.setBackground(Color.decode("#d2dc00"));
        progresoCoche3.setFont(new Font("Arial", Font.BOLD, 12));


        progresoCoche4.setForeground(Color.decode("#021999"));
        progresoCoche4.setBackground(Color.decode("#d2dc00"));
        progresoCoche4.setFont(new Font("Arial", Font.BOLD, 12));


        // Crear un Timer para mover los coches cada 50 milisegundos
        Timer timer = new Timer(50, e -> moverCoches());
        timer.start();
    }

    @Override //carga el fondo
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        if (fondo != null) {
            // dibujar el fondo con el tamaño del panel
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        } else {
            // si la imagen no se carga dibujar un color de fondo
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        // pone a cada coche en su lugar
        g.drawImage(coche1, posicionCoche1, 340, 50, 50, this);
        g.drawImage(coche2, posicionCoche2, 390, 50, 50, this);
        g.drawImage(coche3, posicionCoche3, 440, 50, 50, this);
        g.drawImage(coche4, posicionCoche4, 490, 50, 50, this);
    }


    private void moverCoches() {

        if (!carreraIniciada) {
            return;
        }

        // Mover los coches si no han llegado a la meta
        if (!cochesDetenidos[0]) {
            posicionCoche1 += velocidadCoche1;
            progresoCoche1.setValue(posicionCoche1);
            if (posicionCoche1 >= distanciaCircuito) {
                cochesDetenidos[0] = true;
                anunciarGanador("Mario");
            }
        }

        if (!cochesDetenidos[1]) {
            posicionCoche2 += velocidadCoche2;
            progresoCoche2.setValue(posicionCoche2);
            if (posicionCoche2 >= distanciaCircuito) {
                cochesDetenidos[1] = true;
                anunciarGanador("Toadette");
            }
        }

        if (!cochesDetenidos[2]) {
            posicionCoche3 += velocidadCoche3;
            progresoCoche3.setValue(posicionCoche3);
            if (posicionCoche3 >= distanciaCircuito) {
                cochesDetenidos[2] = true;
                anunciarGanador("Bolt");
            }
        }

        if (!cochesDetenidos[3]) {
            posicionCoche4 += velocidadCoche4;
            progresoCoche4.setValue(posicionCoche4);
            if (posicionCoche4 >= distanciaCircuito) {
                cochesDetenidos[3] = true;
                anunciarGanador("Waluigi");
            }
        }


        repaint();
    }


    private void anunciarGanador(String ganador) {
        JOptionPane.showMessageDialog(this, "El ganador es: " + ganador);
        carreraIniciada = false; //para la carrera
    }


    public void iniciarCarrera() {
        carreraIniciada = true;
    }

    public static void main(String[] args) {
        // Crear ventana
        JFrame ventana = new JFrame("Simulación de Carrera");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear panel con fondo y coches
        int distanciaCircuito = 800; // Distancia configurable
        Carrera panel = new Carrera(distanciaCircuito);
        ventana.add(panel);

        // Crear y agregar barras de progreso
        JPanel barrasPanel = new JPanel();
        barrasPanel.setLayout(new GridLayout(4, 1));
        barrasPanel.add(panel.progresoCoche1);
        barrasPanel.add(panel.progresoCoche2);
        barrasPanel.add(panel.progresoCoche3);
        barrasPanel.add(panel.progresoCoche4);
        ventana.add(barrasPanel, BorderLayout.EAST);

        // boton de empezar
        JButton iniciarButton = new JButton("Iniciar Carrera");
        iniciarButton.addActionListener(e -> panel.iniciarCarrera());
        ventana.add(iniciarButton, BorderLayout.SOUTH);
        iniciarButton.setBackground(Color.decode("#021999"));
        iniciarButton.setForeground(Color.decode("#d2dc00"));


        // tamaño ventana
        ventana.setSize(1000, 600); // tamño
        ventana.setLocationRelativeTo(null); // centrar
        ventana.setVisible(true);
    }
}
