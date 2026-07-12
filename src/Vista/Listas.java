package Vista;
//Massiel Medina y Luis Reyes
import Controlador.ControladorEmpresas;
import Controlador.SistemaVentaPasajes;
import Excepciones.SVPException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Listas extends JDialog {

    private final CardLayout cardLayout;
    private final JPanel mainContainer;


    private final SistemaVentaPasajes sistemaVenta = SistemaVentaPasajes.getInstance();
    private final ControladorEmpresas controladorEmpresas = ControladorEmpresas.getInstance();


    private final Font fuenteBotones = new Font("Berlin Sans FB", Font.PLAIN, 18);
    private final Font fuenteTablas = new Font("Times New Roman", Font.PLAIN, 12);
    private final Color colorCelesteBotones = Color.decode("#82FAFF");
    private final Color colorRojoSalir = Color.decode("#FF5566");

    public Listas(JFrame padre) {
        super(padre, "Consultas del Sistema", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(padre.getSize());
        setLocationRelativeTo(padre);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // 1. Inicializar las Vistas
        JPanel panelMenuBotones = crearPanelMenuBotones();
        JPanel panelPasajeros = crearPanelPasajeros();
        JPanel panelTerminales = crearPanelTerminales();
        JPanel panelEmpresas = crearPanelEmpresas();


        mainContainer.add(panelMenuBotones, "MENU_BOTONES");
        mainContainer.add(panelPasajeros, "PANEL_PASAJEROS");
        mainContainer.add(panelTerminales, "PANEL_TERMINALES");
        mainContainer.add(panelEmpresas, "PANEL_EMPRESAS");

        add(mainContainer);
        cardLayout.show(mainContainer, "MENU_BOTONES");
    }


    private JPanel crearPanelMenuBotones() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;

        // Botón Pasajeros
        JButton btnPasajeros = new JButton("Lista Pasajeros");
        configurarBotonPrincipal(btnPasajeros);
        btnPasajeros.addActionListener(e -> cardLayout.show(mainContainer, "PANEL_PASAJEROS"));
        gbc.gridy = 0;
        panel.add(btnPasajeros, gbc);

        // Botón Terminales
        JButton btnTerminales = new JButton("Lista Terminales");
        configurarBotonPrincipal(btnTerminales);
        btnTerminales.addActionListener(e -> cardLayout.show(mainContainer, "PANEL_TERMINALES"));
        gbc.gridy = 1;
        panel.add(btnTerminales, gbc);

        // Botón Empresas
        JButton btnEmpresas = new JButton("Lista Empresas");
        configurarBotonPrincipal(btnEmpresas);
        btnEmpresas.addActionListener(e -> cardLayout.show(mainContainer, "PANEL_EMPRESAS"));
        gbc.gridy = 2;
        panel.add(btnEmpresas, gbc);

        // Botón Salir al Menú Principal
        JButton btnSalirMenu = new JButton("Salir al Menú");
        btnSalirMenu.setPreferredSize(new Dimension(200, 50));
        btnSalirMenu.setFont(fuenteBotones);
        btnSalirMenu.setBackground(colorRojoSalir);
        btnSalirMenu.setForeground(Color.WHITE);
        btnSalirMenu.addActionListener(e -> dispose()); // Cierra este diálogo y vuelve al Menu.java
        gbc.gridy = 3;
        gbc.insets = new Insets(40, 15, 15, 15);
        panel.add(btnSalirMenu, gbc);

        return panel;
    }


    private JPanel crearPanelPasajeros() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros del Viaje"));

        JTextField txtFecha = new JTextField("2026-07-12", 8);
        JTextField txtHora = new JTextField("08:00", 5);
        JTextField txtPatente = new JTextField(7);
        JButton btnBuscar = new JButton("Cargar Pasajeros");

        panelFiltros.add(new JLabel("Fecha (yyyy-MM-dd):"));
        panelFiltros.add(txtFecha);
        panelFiltros.add(new JLabel("Hora (HH:mm):"));
        panelFiltros.add(txtHora);
        panelFiltros.add(new JLabel("Patente Bus:"));
        panelFiltros.add(txtPatente);
        panelFiltros.add(btnBuscar);

        // Tabla
        String[] columnas = {"Asiento", "RUT Pasajero", "Nombre Completo"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTabla);
        tabla.setFont(fuenteTablas);
        tabla.setRowHeight(20);
        JScrollPane scrollPane = new JScrollPane(tabla);


        btnBuscar.addActionListener(e -> {
            try {
                modeloTabla.setRowCount(0);
                LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
                java.time.LocalTime hora = java.time.LocalTime.parse(txtHora.getText().trim());
                String patente = txtPatente.getText().trim().toUpperCase();

                if (patente.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar una patente.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String[][] pasajeros = sistemaVenta.listPasajerosViaje(fecha, hora, patente);
                if (pasajeros.length == 0) {
                    JOptionPane.showMessageDialog(this, "No se registraron pasajeros para este viaje.", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (String[] fila : pasajeros) {
                        modeloTabla.addRow(fila);
                    }
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formatos de fecha/hora incorrectos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (SVPException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Consulta", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Botón Salir del panel
        JButton btnVolver = crearBotonVolver();

        panel.add(panelFiltros, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnVolver, BorderLayout.SOUTH);

        return panel;
    }


    private JPanel crearPanelTerminales() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros de Terminal"));

        JTextField txtTerminal = new JTextField(15);
        JTextField txtFecha = new JTextField("2026-07-12", 8);
        JButton btnBuscar = new JButton("Consultar Terminal");

        panelFiltros.add(new JLabel("Nombre Terminal:"));
        panelFiltros.add(txtTerminal);
        panelFiltros.add(new JLabel("Fecha (yyyy-MM-dd):"));
        panelFiltros.add(txtFecha);
        panelFiltros.add(btnBuscar);

        String[] columnas = {"Tipo", "Hora", "Patente Bus", "Empresa", "Pasajeros Vendidos"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTabla);
        tabla.setFont(fuenteTablas);
        tabla.setRowHeight(20);
        JScrollPane scrollPane = new JScrollPane(tabla);

        btnBuscar.addActionListener(e -> {
            try {
                modeloTabla.setRowCount(0);
                String nombreTerm = txtTerminal.getText().trim();
                LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());

                if (nombreTerm.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ingrese el nombre del terminal.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String[][] movimientos = controladorEmpresas.listLlegadasSalidasTerminal(nombreTerm, fecha);
                if (movimientos.length == 0) {
                    JOptionPane.showMessageDialog(this, "No hay arribos ni salidas agendadas para esta fecha.", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (String[] fila : movimientos) {
                        modeloTabla.addRow(fila);
                    }
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (SVPException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Consulta", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnVolver = crearBotonVolver();

        panel.add(panelFiltros, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnVolver, BorderLayout.SOUTH);

        return panel;
    }


    private JPanel crearPanelEmpresas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnCargar = new JButton("Cargar Lista de Empresas");
        panelAcciones.add(btnCargar);

        String[] columnas = {"RUT Empresa", "Razón Social / Nombre"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTabla);
        tabla.setFont(fuenteTablas);
        tabla.setRowHeight(20);
        JScrollPane scrollPane = new JScrollPane(tabla);

        btnCargar.addActionListener(e -> {
            modeloTabla.setRowCount(0);
            String[][] empresas = controladorEmpresas.listEmpresas();
            if (empresas.length == 0) {
                JOptionPane.showMessageDialog(this, "No existen empresas cargadas en el sistema.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (String[] fila : empresas) {
                    modeloTabla.addRow(fila);
                }
            }
        });

        JButton btnVolver = crearBotonVolver();

        panel.add(panelAcciones, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnVolver, BorderLayout.SOUTH);

        return panel;
    }

    // --- MÉTODOS DE SOPORTE / FORMATEO ---

    private void configurarBotonPrincipal(JButton boton) {
        boton.setPreferredSize(new Dimension(200, 50));
        boton.setMinimumSize(new Dimension(200, 50));
        boton.setMaximumSize(new Dimension(200, 50));
        boton.setFont(fuenteBotones);
        boton.setBackground(colorCelesteBotones);
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
    }

    private JButton crearBotonVolver() {
        JButton btnVolver = new JButton("Salir");
        btnVolver.setFont(fuenteBotones);
        btnVolver.setBackground(colorRojoSalir);
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.addActionListener(e -> cardLayout.show(mainContainer, "MENU_BOTONES"));
        return btnVolver;
    }
}