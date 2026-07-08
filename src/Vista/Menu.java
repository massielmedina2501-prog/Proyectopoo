package Vista;

import Controlador.SistemaVentaPasajes;
import Excepciones.SVPException;

import javax.swing.*;

public class Menu extends JFrame {

    private JButton guardarSistemaButton;
    private JButton cargarSistemaButton;
    private JButton leerSistemaButton;
    private JButton listasButton;
    private JButton salirButton;
    private JButton comprarPasajesButton;
    private JButton ingresarViajesButton;
    private JPanel PANEL1;

    private final SistemaVentaPasajes sistema = SistemaVentaPasajes.getInstance();

    public Menu() {

        setTitle("MENU");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(PANEL1);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

     

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
            JOptionPane.showMessageDialog(this, "Abrir ventana de compra de pasajes");
            // new VentanaCompraPasajes().setVisible(true);
        });

        ingresarViajesButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abrir ventana de ingreso de viajes");
            // new VentanaViajes().setVisible(true);
        });

        listasButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abrir ventanas de consultas");
            // new VentanaListas().setVisible(true);
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
