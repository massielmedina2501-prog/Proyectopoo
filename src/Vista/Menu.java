package Vista;
//Massiel Medina
import Controlador.SistemaVentaPasajes;
import Excepciones.SVPException;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Menu extends JFrame {

    private JButton guardarSistemaButton;
    private JButton cargarSistemaButton;
    private JButton leerSistemaButton;
    private JButton listasButton;
    private JButton salirButton;
    private JButton comprarPasajesButton;
    private JButton ingresarViajesButton;
    private JPanel PANEL1;
    private JLabel banner;
    private JPanel compraeingresodeV;
    private JPanel listaysalir;
    private JLabel Datosdelsistema;
    private JPanel Sistema;

    private final SistemaVentaPasajes sistema = SistemaVentaPasajes.getInstance();

    public Menu() {

        setTitle("MENU");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(PANEL1);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        //BANNER
        URL ruta = getClass().getResource("/icons/Bienvenido.png");
        System.out.println("Ruta del recurso: " + ruta);
        if (ruta != null) {
            ImageIcon icon = new ImageIcon(ruta);

            int anchoBanner = 1600;
            int altoBanner = 130;

            Image imagen = icon.getImage().getScaledInstance(
                    anchoBanner,
                    altoBanner,
                    Image.SCALE_SMOOTH
            );
            banner.setIcon(new ImageIcon(imagen));

            banner.setPreferredSize(new Dimension(anchoBanner, altoBanner));
            banner.setMinimumSize(new Dimension(anchoBanner, altoBanner));
        } else {
            System.err.println("Error: No se pudo encontrar la imagen en /icons/Bienvenido.png");
            JOptionPane.showMessageDialog(this,
                    "No se encontró la imagen del banner. Verifica el nombre del archivo.",
                    "Error de Recursos",
                    JOptionPane.WARNING_MESSAGE);
        }

     

        leerSistemaButton.addActionListener(e -> {
            try {
                sistema.readDatosIniciales();
                JOptionPane.showMessageDialog(this, "Datos iniciales cargados");
            } catch (SVPException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        guardarSistemaButton.addActionListener(e -> {
            try {
                sistema.saveDatosSistema();
                JOptionPane.showMessageDialog(this, "Sistema guardado correctamente");
            } catch (SVPException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cargarSistemaButton.addActionListener(e -> {
            try {
                sistema.readDatosSistema();
                JOptionPane.showMessageDialog(this, "Sistema cargado correctamente");
            } catch (SVPException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });



        comprarPasajesButton.addActionListener(e -> {
            JDialog dialogoVenta = new JDialog(this, "Venta de Pasajes", true);
            dialogoVenta.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            VentaPasajes panelVenta = new VentaPasajes();

            dialogoVenta.add(panelVenta);
            dialogoVenta.setSize(this.getSize());
            dialogoVenta.setLocationRelativeTo(this);

            dialogoVenta.setVisible(true);
        });

        ingresarViajesButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abrir ventana de ingreso de viajes");
            CrearViaje crearViaje = new CrearViaje(this);
            setVisible(false);
            crearViaje.setVisible(true);
        });

        listasButton.addActionListener(e -> {
            Listas ventanaListas = new Listas(this);
            ventanaListas.setVisible(true);
        });

        salirButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que quieres salir?",
                    "Salir",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });


        setVisible(true);
    }
}
