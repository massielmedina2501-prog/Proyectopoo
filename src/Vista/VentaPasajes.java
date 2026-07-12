package Vista;
//Luis Reyes
import Controlador.ControladorEmpresas;
import Controlador.SistemaVentaPasajes;
import Modelo.TipoDocumento;
import Excepciones.SVPException;
import Utilidades.IdPersona;
import Utilidades.Rut;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class VentaPasajes extends JPanel {

    private SistemaVentaPasajes sistema = SistemaVentaPasajes.getInstance();

    private JTextField txtFecha;
    private JTextField txtOrigen;
    private JTextField txtDestino;
    private JTextField txtIdCliente;
    private JTextField txtIdVenta;

    private JComboBox<String> comboViajes;
    private JComboBox<TipoDocumento> comboDocumento;
    private JSpinner spinPasajes;

    private JButton btnBuscar;
    private JButton btnIniciarVenta;
    private JButton btnPagar;

    private String patenteSeleccionada;
    private LocalTime horaSeleccionada;
    private LocalDate fechaSeleccionada;

    public VentaPasajes() {
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Font fuenteTexto = new Font("Times New Roman", Font.PLAIN, 12);
        Font fuenteRespuesta = new Font("Berlin Sans FB", Font.PLAIN, 12);
        Font fuenteTitulo = new Font("Lucida Sans Typewriter", Font.BOLD, 14);
        Color verde = Color.decode("#5CFF38");

        JPanel panelBusqueda = new JPanel(new GridLayout(4, 2, 5, 5));
        panelBusqueda.setBackground(Color.WHITE);
        TitledBorder bordeBusqueda = BorderFactory.createTitledBorder("1. Búsqueda de Viajes");
        bordeBusqueda.setTitleFont(fuenteTitulo);
        panelBusqueda.setBorder(bordeBusqueda);

        JLabel lblFecha = new JLabel("Fecha (yyyy-MM-dd)");
        lblFecha.setFont(fuenteTexto);
        panelBusqueda.add(lblFecha);
        txtFecha = new JTextField();
        txtFecha.setFont(fuenteRespuesta);
        panelBusqueda.add(txtFecha);

        JLabel lblOrigen = new JLabel("Terminal Origen");
        lblOrigen.setFont(fuenteTexto);
        panelBusqueda.add(lblOrigen);
        txtOrigen = new JTextField();
        txtOrigen.setFont(fuenteRespuesta);
        panelBusqueda.add(txtOrigen);

        JLabel lblDestino = new JLabel("Terminal Destino");
        lblDestino.setFont(fuenteTexto);
        panelBusqueda.add(lblDestino);
        txtDestino = new JTextField();
        txtDestino.setFont(fuenteRespuesta);
        panelBusqueda.add(txtDestino);

        panelBusqueda.add(new JLabel());
        btnBuscar = new JButton("Buscar Viajes");
        btnBuscar.setFont(fuenteRespuesta);
        btnBuscar.setBackground(verde);
        btnBuscar.setForeground(Color.BLACK);
        panelBusqueda.add(btnBuscar);

        JPanel panelVenta = new JPanel(new GridLayout(6, 2, 5, 5));
        panelVenta.setBackground(Color.WHITE);
        TitledBorder bordeVenta = BorderFactory.createTitledBorder("2. Venta");
        bordeVenta.setTitleFont(fuenteTitulo);
        panelVenta.setBorder(bordeVenta);

        JLabel lblViaje = new JLabel("Viaje Seleccionado");
        lblViaje.setFont(fuenteTexto);
        panelVenta.add(lblViaje);
        comboViajes = new JComboBox<>();
        comboViajes.setFont(fuenteRespuesta);
        panelVenta.add(comboViajes);

        JLabel lblIdVenta = new JLabel("Nro. Documento (Boleta/Factura)");
        lblIdVenta.setFont(fuenteTexto);
        panelVenta.add(lblIdVenta);
        txtIdVenta = new JTextField();
        txtIdVenta.setFont(fuenteRespuesta);
        panelVenta.add(txtIdVenta);

        JLabel lblRut = new JLabel("RUT Cliente");
        lblRut.setFont(fuenteTexto);
        panelVenta.add(lblRut);
        txtIdCliente = new JTextField();
        txtIdCliente.setFont(fuenteRespuesta);
        panelVenta.add(txtIdCliente);

        JLabel lblCantidad = new JLabel("Cantidad Pasajes");
        lblCantidad.setFont(fuenteTexto);
        panelVenta.add(lblCantidad);
        spinPasajes = new JSpinner(new SpinnerNumberModel(1, 1, 60, 1));
        panelVenta.add(spinPasajes);

        JLabel lblTipo = new JLabel("Tipo Documento");
        lblTipo.setFont(fuenteTexto);
        panelVenta.add(lblTipo);
        comboDocumento = new JComboBox<>(TipoDocumento.values());
        panelVenta.add(comboDocumento);

        panelVenta.add(new JLabel());
        btnIniciarVenta = new JButton("Iniciar Venta");
        btnIniciarVenta.setBackground(verde);
        btnIniciarVenta.setForeground(Color.BLACK);
        btnIniciarVenta.setEnabled(false);
        panelVenta.add(btnIniciarVenta);

        JPanel panelPago = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelPago.setBackground(Color.WHITE);
        TitledBorder bordePago = BorderFactory.createTitledBorder("3. Pago y Cierre");
        bordePago.setTitleFont(fuenteTitulo);
        panelPago.setBorder(bordePago);

        btnPagar = new JButton("Procesar Pago y Pasajes");
        btnPagar.setBackground(verde);
        btnPagar.setForeground(Color.BLACK);
        btnPagar.setEnabled(false);
        panelPago.add(btnPagar);

        add(panelBusqueda);
        add(panelVenta);
        add(panelPago);

        btnBuscar.addActionListener(e -> {
            try {
                if (txtFecha.getText().trim().isEmpty() || txtOrigen.getText().trim().isEmpty() || txtDestino.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor complete todos los campos de búsqueda.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
                String origen = txtOrigen.getText().trim();
                String destino = txtDestino.getText().trim();
                int cantidad = (Integer) spinPasajes.getValue();

                comboViajes.removeAllItems();

                String[][] viajes = sistema.getHorariosDisponibles(fecha, origen, destino, cantidad);

                if (viajes.length == 0) {
                    JOptionPane.showMessageDialog(this, "No hay viajes disponibles que cumplan los criterios o no tienen suficientes asientos.", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
                    btnIniciarVenta.setEnabled(false);
                    return;
                }

                for (String[] v : viajes) {
                    comboViajes.addItem(v[0] + ";" + v[1] + ";$" + v[2] + " (" + v[3] + " disp.)");
                }

                fechaSeleccionada = fecha;
                btnIniciarVenta.setEnabled(true);

            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "La fecha debe estar en formato válido yyyy-MM-dd (Ej: 2026-07-15)", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        comboViajes.addActionListener(e -> {
            if (comboViajes.getSelectedItem() == null) return;

            String datos = comboViajes.getSelectedItem().toString();
            String[] partes = datos.split(";");
            patenteSeleccionada = partes[0];
            horaSeleccionada = LocalTime.parse(partes[1]);
        });

        btnIniciarVenta.addActionListener(e -> {
            try {
                String idDoc = txtIdVenta.getText().trim();
                String rutClieStr = txtIdCliente.getText().trim();
                int cantidadPasajes = (Integer) spinPasajes.getValue();
                TipoDocumento tipoDoc = (TipoDocumento) comboDocumento.getSelectedItem();

                if (idDoc.isEmpty() || rutClieStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar el Número de Documento y el RUT del Cliente.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String rutFormateado = rutClieStr.replace(".", "").trim();
                if (!rutFormateado.contains("-") && rutFormateado.length() > 1) {
                    rutFormateado = rutFormateado.substring(0, rutFormateado.length() - 1) + "-" + rutFormateado.substring(rutFormateado.length() - 1);
                }

                IdPersona idCliente = Rut.of(rutFormateado);

                if (idCliente == null) {
                    JOptionPane.showMessageDialog(this, "El RUT del Cliente ingresado no es válido (Ej: 12345678-9).", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String textoOrigen = txtOrigen.getText().trim().toLowerCase();
                String textoDestino = txtDestino.getText().trim().toLowerCase();

                String comSalida = txtOrigen.getText().trim();
                String comLlegada = txtDestino.getText().trim();

                ControladorEmpresas controladorEmp = ControladorEmpresas.getInstance();
                String[] terminalesSistema = controladorEmp.getNombresTerminales();

                for (String t : terminalesSistema) {
                    if (t.toLowerCase().contains(textoOrigen)) {
                        comSalida = t;
                    }
                    if (t.toLowerCase().contains(textoDestino)) {
                        comLlegada = t;
                    }
                }
                System.out.println("RUT ingresado: " + idCliente);
                sistema.iniciaVenta(idDoc, tipoDoc, fechaSeleccionada, comSalida, comLlegada, idCliente, cantidadPasajes);

                JOptionPane.showMessageDialog(this, "Venta inicializada. A continuación registre el asiento y datos de los " + cantidadPasajes + " pasajero(s).");

                for (int i = 0; i < cantidadPasajes; i++) {
                    boolean registradoCorrectamente = false;

                    while (!registradoCorrectamente) {
                        String inputAsiento = JOptionPane.showInputDialog(this, "Pasajero " + (i + 1) + "/" + cantidadPasajes + "\nIngrese número de asiento:");
                        if (inputAsiento == null) {
                            JOptionPane.showMessageDialog(this, "Operación cancelada. El proceso de pasajeros es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        String inputRutPasajero = JOptionPane.showInputDialog(this, "Pasajero " + (i + 1) + "/" + cantidadPasajes + "\nIngrese RUT del Pasajero (Ej: 22222222-2):");
                        if (inputRutPasajero == null) {
                            JOptionPane.showMessageDialog(this, "Operación cancelada.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        try {
                            int nroAsiento = Integer.parseInt(inputAsiento.trim());

                            String rutPasajeroFormateado = inputRutPasajero.replace(".", "").trim();
                            if (!rutPasajeroFormateado.contains("-") && rutPasajeroFormateado.length() > 1) {
                                rutPasajeroFormateado = rutPasajeroFormateado.substring(0, rutPasajeroFormateado.length() - 1) + "-" + rutPasajeroFormateado.substring(rutPasajeroFormateado.length() - 1);
                            }

                            IdPersona idPasajero = Rut.of(rutPasajeroFormateado);

                            if (idPasajero == null) {
                                JOptionPane.showMessageDialog(this, "RUT de pasajero no válido. Reintente.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                                continue;
                            }

                            sistema.vendePasaje(idDoc, tipoDoc, fechaSeleccionada, horaSeleccionada, patenteSeleccionada, nroAsiento, idPasajero);
                            registradoCorrectamente = true;

                        } catch (NumberFormatException nfe) {
                            JOptionPane.showMessageDialog(this, "El número de asiento debe ser un entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (SVPException svpe) {
                            JOptionPane.showMessageDialog(this, "Error del Sistema: " + svpe.getMessage() + "\nPor favor reintente con otros datos de asiento/pasajero.", "Error de Negocio", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

                txtIdVenta.setEnabled(false);
                txtIdCliente.setEnabled(false);
                spinPasajes.setEnabled(false);
                comboDocumento.setEnabled(false);
                comboViajes.setEnabled(false);
                btnBuscar.setEnabled(false);
                btnIniciarVenta.setEnabled(false);

                btnPagar.setEnabled(true);
                JOptionPane.showMessageDialog(this, "¡Todos los pasajeros se asociaron correctamente! Proceda al pago.", "Pasajeros Listos", JOptionPane.INFORMATION_MESSAGE);

            } catch (SVPException ex) {
                JOptionPane.showMessageDialog(this, "Error al iniciar la venta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnPagar.addActionListener(e -> {
            String idDoc = txtIdVenta.getText().trim();
            TipoDocumento tipoDoc = (TipoDocumento) comboDocumento.getSelectedItem();

            String[] opcionesPago = {"Efectivo", "Tarjeta de Crédito/Débito"};
            int seleccion = JOptionPane.showOptionDialog(this,
                    "Seleccione el medio de pago:",
                    "Procesar Pago",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, opcionesPago, opcionesPago[0]);

            try {
                if (seleccion == 0) {
                    sistema.pagaVenta(idDoc, tipoDoc);
                } else if (seleccion == 1) {
                    String nroTarjStr = JOptionPane.showInputDialog(this, "Ingrese el número de la tarjeta (Sólo dígitos):");
                    if (nroTarjStr == null || nroTarjStr.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Número de tarjeta requerido para proceder.", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    long nroTarjeta = Long.parseLong(nroTarjStr.trim());
                    sistema.pagaVenta(idDoc, tipoDoc, nroTarjeta);
                } else {
                    return;
                }

                sistema.generatePasajesVenta(idDoc, tipoDoc);

                JOptionPane.showMessageDialog(this,
                        "¡Venta Pagada Correctamente!\nSe ha generado el archivo de pasajes electrónicos: \"" + idDoc + tipoDoc.name().toLowerCase() + ".txt\"",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

                limpiarFormulario();

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "El número de tarjeta debe contener solo caracteres numéricos.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            } catch (SVPException ex) {
                JOptionPane.showMessageDialog(this, "Error al procesar el pago: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void limpiarFormulario() {
        txtIdVenta.setText("");
        txtIdVenta.setEnabled(true);
        txtIdCliente.setText("");
        txtIdCliente.setEnabled(true);
        spinPasajes.setValue(1);
        spinPasajes.setEnabled(true);
        comboDocumento.setEnabled(true);
        comboViajes.removeAllItems();
        comboViajes.setEnabled(true);
        btnBuscar.setEnabled(true);
        btnIniciarVenta.setEnabled(false);
        btnPagar.setEnabled(false);
    }
}