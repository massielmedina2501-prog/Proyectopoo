package Vista;
//Massiel Medina
import java.util.Date;
import java.time.ZoneId;
import Controlador.ControladorEmpresas;
import Controlador.SistemaVentaPasajes;
import Excepciones.SVPException;
import Utilidades.IdPersona;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class CrearViaje extends JFrame {
    private boolean cargandoDatos = false;
    private JPanel panel1;
    private JComboBox<String> empresa;
    private JSpinner duracion;
    private JPanel Viaje;
    private JSpinner hora;
    private JButton siguienteButton;
    private JButton cancelarButton;
    private JTextField precio;
    private JComboBox<String> bus;
    private JComboBox<IdPersona> conductor;
    private JComboBox<IdPersona> auxiliar;
    private JButton registrarButton;
    private JButton cancelarButton1;
    private JPanel viaje2;
    private JComboBox<String> Tsalida;
    private JComboBox<String> Tllegada;
    private JSpinner fecha;
    private JFrame menu;

    private SistemaVentaPasajes sistemaVenta = SistemaVentaPasajes.getInstance();
    private ControladorEmpresas controladorEmp = ControladorEmpresas.getInstance();

    public CrearViaje(JFrame menu) {
        this.menu = menu;
        setTitle("CREAR VIAJE");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(panel1);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);


        SpinnerDateModel modeloHora = new SpinnerDateModel();
        hora.setModel(modeloHora);

        JSpinner.DateEditor editorHora = new JSpinner.DateEditor(hora, "HH:mm");
        hora.setEditor(editorHora);


        hora.setValue(new Date());


        SpinnerDateModel modeloFecha = new SpinnerDateModel();
        fecha.setModel(modeloFecha);

        JSpinner.DateEditor editorFecha = new JSpinner.DateEditor(fecha, "dd/MM/yyyy");
        fecha.setEditor(editorFecha);

        fecha.setValue(new Date());

        // AQUÍ SE CARGAN LAS EMPRESAS Y TERMINALES
        llenarDatosIniciales();

        CardLayout layout = (CardLayout) panel1.getLayout();
        layout.first(panel1);


        empresa.addActionListener(e -> {
            if (!cargandoDatos) { // Solo actúa si NO estamos en proceso de carga inicial
                actualizarComponentesPorEmpresa();
            }
        });

        siguienteButton.addActionListener(e -> {
            if (validarPrimeraPantalla()) {
                layout.next(panel1);
            }
        });

        registrarButton.addActionListener(e -> {
            ejecutarRegistroViaje();
        });

        cancelarButton.addActionListener(e -> {
            regresarMenu();
        });

        cancelarButton1.addActionListener(e -> {
            regresarMenu();
        });
    }

    private void llenarDatosIniciales() {
        try {
            cargandoDatos = true;

            empresa.removeAllItems();
            Tsalida.removeAllItems();
            Tllegada.removeAllItems();

            String[][] listaEmpresas = controladorEmp.listEmpresas();
            for (String[] emp : listaEmpresas) {
                empresa.addItem(emp[0]);
            }

            String[] terminalesSistema = controladorEmp.getNombresTerminales();
            for (String t : terminalesSistema) {
                Tsalida.addItem(t);
                Tllegada.addItem(t);
            }

            cargandoDatos = false;


            if (empresa.getItemCount() > 0) {
                empresa.setSelectedIndex(0);
                actualizarComponentesPorEmpresa();
            }

        } catch (Exception ex) {
            cargandoDatos = false;
            JOptionPane.showMessageDialog(this, "Error al cargar datos iniciales: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarComponentesPorEmpresa() {
        String rutEmpresaSeleccionada = (String) empresa.getSelectedItem();
        if (rutEmpresaSeleccionada == null) return;

        bus.removeAllItems();
        conductor.removeAllItems();
        auxiliar.removeAllItems();

        try {
            //Filtra y carga los buses de la empresa seleccionada
            String[] patentes = controladorEmp.getPatentesBusesPorEmpresa(rutEmpresaSeleccionada);
            for (String p : patentes) {
                bus.addItem(p);
            }

            //Filtra y carga los conductores de la empresa seleccionada
            IdPersona[] conductoresSistema = controladorEmp.getConductoresPorEmpresa(rutEmpresaSeleccionada);
            for (IdPersona cond : conductoresSistema) {
                conductor.addItem(cond);
            }

            //Filtra y carga los auxiliares de la empresa seleccionada
            IdPersona[] auxiliaresSistema = controladorEmp.getAuxiliaresPorEmpresa(rutEmpresaSeleccionada);
            for (IdPersona aux : auxiliaresSistema) {
                auxiliar.addItem(aux);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al filtrar componentes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarPrimeraPantalla() {
        if (empresa.getSelectedItem() == null || Tsalida.getSelectedItem() == null || Tllegada.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar la Empresa y los Terminales.", "Datos Faltantes", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (Tsalida.getSelectedItem().equals(Tllegada.getSelectedItem())) {
            JOptionPane.showMessageDialog(this, "El terminal de salida y llegada no pueden ser el mismo.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Date date = (Date) fecha.getValue();

            date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "La fecha seleccionada no es válida",
                    "Formato Inválido",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Date date = (Date) hora.getValue();

            date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalTime();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "La hora seleccionada no es válida",
                    "Formato Inválido",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int dur = (int) duracion.getValue();
        if (dur <= 0) {
            JOptionPane.showMessageDialog(this, "La duración debe ser mayor a 0 minutos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void ejecutarRegistroViaje() {
        try {
            Date fechaDate = (Date) fecha.getValue();

            LocalDate fechaViaje = fechaDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            Date date = (Date) hora.getValue();

            LocalTime horaViaje = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalTime()
                    .withSecond(0)
                    .withNano(0);
            int minsDuracion = (int) duracion.getValue();
            String[] nomTerminales = new String[]{ (String) Tsalida.getSelectedItem(), (String) Tllegada.getSelectedItem() };

            int valorPrecio;
            try {
                valorPrecio = Integer.parseInt(precio.getText().trim());
            } catch (NumberFormatException e) {
                throw new SVPException("El precio debe ser un número entero válido.");
            }

            String patBus = (String) bus.getSelectedItem();
            IdPersona idAuxiliar = (IdPersona) auxiliar.getSelectedItem();
            IdPersona idConductor = (IdPersona) conductor.getSelectedItem();

            if (valorPrecio <= 0) {
                throw new SVPException("El precio del pasaje debe ser mayor a $0.");
            }
            if (patBus == null || idConductor == null || idAuxiliar == null) {
                throw new SVPException("Debe seleccionar un Bus, Conductor y Auxiliar para continuar.");
            }

            IdPersona[] idTripulantes = new IdPersona[]{ idAuxiliar, idConductor };

            sistemaVenta.createViaje(
                    fechaViaje,
                    horaViaje,
                    valorPrecio,
                    minsDuracion,
                    patBus,
                    idTripulantes,
                    nomTerminales
            );

            JOptionPane.showMessageDialog(this, "¡Viaje creado exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            regresarMenu();

        } catch (SVPException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación del Sistema", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error Técnico", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void regresarMenu() {
        menu.setVisible(true);
        dispose();
    }
}