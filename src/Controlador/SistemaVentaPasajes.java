package Controlador;

import Modelo.*;
import Utilidades.*;
import Excepciones.*;
import Persistencia.IOSVP;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class SistemaVentaPasajes {

    private static SistemaVentaPasajes instance = null;
    private ControladorEmpresas cEmp = ControladorEmpresas.getInstance();

    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Pasajero> pasajeros = new ArrayList<>();
    private ArrayList<Viaje> viajes = new ArrayList<>();
    private ArrayList<Venta> ventas = new ArrayList<>();

    private SistemaVentaPasajes() {}

    public static SistemaVentaPasajes getInstance() {
        if (instance == null) instance = new SistemaVentaPasajes();
        return instance;
    }

    // --- MÉTODOS DE CREACIÓN ---
    public void createCliente(IdPersona id, Nombre nom, String email) throws SVPException {
        if (findCliente(id).isPresent())
            throw new SVPException("Cliente ya registrado.");

        Cliente c = new Cliente(id, nom, email);
        clientes.add(c);
    }

    public void createPasajero(IdPersona id, Nombre nom, String fono, Nombre nomContacto, String fonoContacto) throws SVPException {
        if (findPasajero(id).isPresent()) throw new SVPException("Pasajero ya registrado.");
        Pasajero p = new Pasajero(id, nom);
        p.setTelefono(fono);
        p.setNomContacto(nomContacto);
        p.setFonoContacto(fonoContacto);
        pasajeros.add(p);
    }

    public void createViaje(LocalDate fecha, LocalTime hora, int precio, int duracion,
                            String patBus, IdPersona[] idTripulantes,
                            String[] nomTerminales) throws SVPException {

        Bus bus = cEmp.findBus(patBus)
                .orElseThrow(() -> new SVPException("Bus no encontrado."));

        Terminal tOrig = cEmp.findTerminal(nomTerminales[0])
                .orElseThrow(() -> new SVPException("Terminal origen no encontrado."));

        Terminal tDest = cEmp.findTerminal(nomTerminales[1])
                .orElseThrow(() -> new SVPException("Terminal destino no encontrado."));

        Auxiliar aux = cEmp.findAuxiliar(idTripulantes[0], bus.getEmpresa().getRut())
                .orElseThrow(() -> new SVPException("Auxiliar no válido."));

        ArrayList<Conductor> conds = new ArrayList<>();

        for (int i = 1; i < idTripulantes.length; i++) {
            conds.add(
                    cEmp.findConductor(idTripulantes[i], bus.getEmpresa().getRut())
                            .orElseThrow(() -> new SVPException("Conductor no válido."))
            );
        }

        Viaje v = new Viaje(fecha, hora, precio, duracion, bus, aux, conds, tOrig, tDest);
        viajes.add(v);
        bus.addViaje(v);
    }

    public void iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaViaje, String comSalida, String comLlegada, IdPersona idCliente, int nroPasajes) throws SVPException {
        Cliente c = findCliente(idCliente).orElseThrow(() -> new SVPException("Cliente no encontrado."));
        ventas.add(new Venta(idDoc, tipo, fechaViaje, c));
    }

    public String[][] getHorariosDisponibles(LocalDate fechaViaje,
                                             String terminalSalida,
                                             String terminalLlegada,
                                             int nroPasajes) {

        return viajes.stream()
                .filter(v -> v.getFecha().equals(fechaViaje) &&
                        v.getTerminalSalida().getNombre().equalsIgnoreCase(terminalSalida) &&
                        v.getTerminalLlegada().getNombre().equalsIgnoreCase(terminalLlegada) &&
                        v.getNroAsientosDisponibles() >= nroPasajes)
                .map(v -> new String[]{
                        v.getBus().getPatente(),
                        v.getHora().toString(),
                        String.valueOf(v.getPrecio()),
                        String.valueOf(v.getNroAsientosDisponibles())
                })
                .toArray(String[][]::new);
    }
    public Optional<String> getNombrePasajero(IdPersona idPasajero) {
        return findPasajero(idPasajero).map(p -> p.getNombreCompleto().toString());
    }

    public Optional<Integer> getMontoVenta(String idDocumento, TipoDocumento tipo) {
        return findVenta(idDocumento, tipo).map(Venta::getMonto);
    }

    // --- MÉTODOS DE VENTA Y PASAJES ---
    public void vendePasaje(String idDoc, TipoDocumento tipo, LocalDate fecha, LocalTime hora, String patBus, int asiento, IdPersona idPasajero) throws SVPException {
        Venta v = findVenta(idDoc, tipo).orElseThrow(() -> new SVPException("Venta no encontrada."));
        Viaje vj = findViaje(fecha, hora, patBus).orElseThrow(() -> new SVPException("Viaje no encontrado."));
        Pasajero p = findPasajero(idPasajero).orElseThrow(() -> new SVPException("Pasajero no encontrado."));

        if(!vj.asientoDisponible(asiento)) throw new SVPException("Asiento ocupado.");
        v.createPasaje(asiento, vj, p);
    }

    public void generatePasajesVenta(String idDocumento, TipoDocumento tipo) throws SVPException {
        // 1. Buscas la venta (manteniendo tu lógica con Optional)
        Venta v = findVenta(idDocumento, tipo)
                .orElseThrow(() -> new SVPException("Venta no encontrada."));

        // 2. Armas el nombre EXACTO como lo pide la guía (id + tipo en minúsculas)
        String nombreArchivo = idDocumento + tipo.name().toLowerCase() + ".txt";

        // 3. Escribes el archivo usando el toString() que creaste
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {

            for (Pasaje pasaje : v.getPasajes()) {
                writer.print(pasaje.toString());
                writer.println(); // Espacio en blanco entre pasajes
            }

        } catch (IOException e) {
            throw new SVPException("Error al guardar el archivo de texto: " + e.getMessage());
        }
    }

    public void pagaVenta(String idDoc, TipoDocumento tipo) throws SVPException {
        findVenta(idDoc, tipo).orElseThrow(() -> new SVPException("Venta no encontrada.")).pagaMonto();
    }

    public void pagaVenta(String idDoc, TipoDocumento tipo, long nroTarjeta) throws SVPException {
        findVenta(idDoc, tipo).orElseThrow(() -> new SVPException("Venta no encontrada.")).pagaMonto(nroTarjeta);
    }

    // --- MÉTODOS DE LISTADO ---
    public String[][] listVentas() {
        return ventas.stream().map(v -> new String[]{
                v.getIdDocumento(), v.getTipo().toString(), v.getFecha().toString(),
                v.getCliente().getIdPersona().toString(), String.valueOf(v.getPasajes().length), String.valueOf(v.getMonto())
        }).toArray(String[][]::new);
    }

    public String[][] listViajes() {
        return viajes.stream().map(v -> new String[]{
                v.getFecha().toString(), v.getHora().toString(), String.valueOf(v.getPrecio()),
                String.valueOf(v.getNroAsientosDisponibles()), v.getBus().getPatente()
        }).toArray(String[][]::new);
    }

    public String[][] listPasajerosViaje(LocalDate fecha, LocalTime hora, String patBus) throws SVPException {
        Viaje vj = findViaje(fecha, hora, patBus).orElseThrow(() -> new SVPException("Viaje no encontrado."));
        return vj.getPasajes().stream().map(p -> new String[]{
                String.valueOf(p.getAsiento()), p.getPasajero().getIdPersona().toString(),
                p.getPasajero().getNombreCompleto().toString()
        }).toArray(String[][]::new);
    }

    public String[] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patBus) throws SVPException {
        Viaje vj = findViaje(fecha, hora, patBus).orElseThrow(() -> new SVPException("Viaje no encontrado."));
        return vj.getAsientos();
    }

    // --- PERSISTENCIA ---
    public void readDatosIniciales() throws SVPException { IOSVP.cargarDatosIniciales(); }

    public void saveDatosSistema() throws SVPException {
        IOSVP.saveControladores(new Object[]{clientes, pasajeros, viajes, ventas});
    }

    @SuppressWarnings("unchecked")
    public void readDatosSistema() throws SVPException {
        Object[] datos = IOSVP.readControladores();
        this.clientes = (ArrayList<Cliente>) datos[0];
        this.pasajeros = (ArrayList<Pasajero>) datos[1];
        this.viajes = (ArrayList<Viaje>) datos[2];
        this.ventas = (ArrayList<Venta>) datos[3];
    }

    // --- BÚSQUEDAS
    private Optional<Cliente> findCliente(IdPersona id) { return clientes.stream().filter(c -> c.getIdPersona().equals(id)).findFirst(); }
    private Optional<Venta> findVenta(String idDoc, TipoDocumento tipo) { return ventas.stream().filter(v -> v.getIdDocumento().equals(idDoc) && v.getTipo() == tipo).findFirst(); }
    private Optional<Viaje> findViaje(LocalDate fecha, LocalTime hora, String pat) { return viajes.stream().filter(v -> v.getFecha().equals(fecha) && v.getHora().equals(hora) && v.getBus().getPatente().equals(pat)).findFirst(); }
    private Optional<Pasajero> findPasajero(IdPersona id) { return pasajeros.stream().filter(p -> p.getIdPersona().equals(id)).findFirst(); }
}