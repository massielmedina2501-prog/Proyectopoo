package Controlador;

import Modelo.*;
import Utilidades.*;
import Excepciones.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class SistemaVentaPasajes {

    private static SistemaVentaPasajes instance = null;

    private ControladorEmpresas controladorEmpresas;

    private ArrayList<Cliente> clientes;
    private ArrayList<Pasajero> pasajeros;
    private ArrayList<Viaje> viajes;
    private ArrayList<Venta> ventas;

    private SistemaVentaPasajes() {

        controladorEmpresas =
                ControladorEmpresas.getInstance();

        clientes = new ArrayList<>();
        pasajeros = new ArrayList<>();
        viajes = new ArrayList<>();
        ventas = new ArrayList<>();
    }

    public static SistemaVentaPasajes getInstance() {

        if (instance == null) {
            instance = new SistemaVentaPasajes();
        }

        return instance;
    }

    public void createCliente(
            IdPersona id,
            Nombre nom,
            Direccion direccion)
            throws SistemaVentaPasajesException {

        if (id == null || nom == null || direccion == null) {

            throw new SistemaVentaPasajesException(
                    "Los datos del cliente no pueden ser nulos"
            );
        }

        if (findCliente(id).isPresent()) {

            throw new SistemaVentaPasajesException(
                    "Ya existe cliente con el id indicado"
            );
        }

        Cliente c =
                new Cliente(id, nom, direccion);

        clientes.add(c);
    }

    public void createPasajero(
            IdPersona id,
            Nombre nom,
            String fono,
            Nombre nomContacto,
            String fonoContacto)
            throws SistemaVentaPasajesException {

        if (findPasajero(id).isPresent()) {

            throw new SistemaVentaPasajesException(
                    "Ya existe pasajero con el id indicado"
            );
        }

        Pasajero p =
                new Pasajero(id, nom);

        p.setTelefono(fono);
        p.setNomContacto(nomContacto);
        p.setFonoContacto(fonoContacto);

        pasajeros.add(p);
    }

    public void createViaje(
            LocalDate fecha,
            LocalTime hora,
            int precio,
            int duracion,
            String patBus,
            IdPersona[] idTripulantes,
            String[] nomComunas)
            throws SistemaVentaPasajesException {

        if (findViaje(fecha, hora, patBus)
                .isPresent()) {

            throw new SistemaVentaPasajesException(
                    "Ya existe viaje con fecha, hora y patente de bus indicados"
            );
        }

        Bus bus = findBus(patBus);

        if (bus == null) {

            throw new SistemaVentaPasajesException(
                    "No existe bus con la patente indicada"
            );
        }

        Empresa emp = bus.getEmpresa();

        Optional<Auxiliar> auxiliar =
                controladorEmpresas.findAuxiliar(
                        idTripulantes[0],
                        emp.getRut()
                );

        if (auxiliar.isEmpty()) {

            throw new SistemaVentaPasajesException(
                    "No existe auxiliar con el id indicado en la empresa"
            );
        }

        ArrayList<Conductor> conductores =
                new ArrayList<>();

        for (int i = 1; i < idTripulantes.length; i++) {

            Optional<Conductor> conductor =
                    controladorEmpresas.findConductor(
                            idTripulantes[i],
                            emp.getRut()
                    );

            if (conductor.isEmpty()) {

                throw new SistemaVentaPasajesException(
                        "No existe conductor con el id indicado en la empresa"
                );
            }

            conductores.add(conductor.get());
        }

        Optional<Terminal> salida =
                controladorEmpresas.findTerminalPorComuna(
                        nomComunas[0]
                );

        if (salida.isEmpty()) {

            throw new SistemaVentaPasajesException(
                    "No existe terminal de salida en la comuna indicada"
            );
        }

        Optional<Terminal> llegada =
                controladorEmpresas.findTerminalPorComuna(nomComunas[1]);

        if (llegada.isEmpty()) {

            throw new SistemaVentaPasajesException("No existe terminal de llegada en la comuna indicada");
        }

        Viaje v = new Viaje(fecha, hora, precio, duracion, bus, auxiliar.get(), conductores, salida.get(), llegada.get());

        viajes.add(v);

        bus.addViaje(v);

        salida.get().addSalida(v);
        llegada.get().addLlegada(v);
    }

    public void iniciaVenta(
            String idDoc,
            TipoDocumento tipo,
            LocalDate fechaViaje,
            String comSalida,
            String comLlegada,
            IdPersona idCliente,
            int nroPasajes)
            throws SistemaVentaPasajesException {

        if (findVenta(idDoc, tipo)
                .isPresent()) {

            throw new SistemaVentaPasajesException(
                    "Ya existe venta con el id y tipo de documento indicados"
            );
        }

        Optional<Cliente> cliente =
                findCliente(idCliente);

        if (cliente.isEmpty()) {

            throw new SistemaVentaPasajesException(
                    "No existe cliente con el id indicado"
            );
        }

        String[][] horarios =
                getHorariosDisponibles(
                        fechaViaje,
                        comSalida,
                        comLlegada,
                        nroPasajes
                );

        if (horarios.length == 0) {

            throw new SistemaVentaPasajesException(
                    "No existen viajes disponibles para la fecha indicada"
            );
        }

        Venta v =
                new Venta(idDoc, tipo, fechaViaje, cliente.get());

        ventas.add(v);
    }

    public String[][] getHorariosDisponibles(
            LocalDate fechaViaje,
            String comunaSalida,
            String comunaLlegada,
            int nroPasajes) {

        ArrayList<String[]> lista =
                new ArrayList<>();

        for (Viaje v : viajes) {

            if (v.getFecha().equals(fechaViaje) && v.getTerminalSalida().getDireccion().getComuna().equalsIgnoreCase(comunaSalida) && v.getTerminalLlegada().getDireccion().getComuna().equalsIgnoreCase(comunaLlegada) && v.existeDisponibilidad(nroPasajes)) {

                String[] fila = new String[4];

                fila[0] = v.getBus().getPatente();

                fila[1] = v.getHora().toString();

                fila[2] = String.valueOf(v.getPrecio());

                fila[3] = String.valueOf(v.getNroAsientosDisponibles());

                lista.add(fila);
            }
        }

        String[][] matriz = new String[lista.size()][4];

        for (int i = 0; i < lista.size(); i++) {

            matriz[i] = lista.get(i);
        }

        return matriz;
    }

    public String[] listAsientosDeViaje(
            LocalDate fecha,
            LocalTime hora,
            String patBus)
            throws SistemaVentaPasajesException {

        Optional<Viaje> viaje = findViaje(fecha, hora, patBus);

        if (viaje.isEmpty()) {

            throw new SistemaVentaPasajesException("No existe viaje con la fecha, hora y patente indicadas");
        }

        return viaje.get().getAsientos();
    }

    public Optional<String> getNombrePasajero(
            IdPersona idPasajero) {

        Optional<Pasajero> pasajero = findPasajero(idPasajero);

        if (pasajero.isEmpty()) {

            return Optional.empty();
        }

        return Optional.of(pasajero.get().getNombreCompleto().toString());
    }

    public Optional<Integer> getMontoVenta(
            String idDocumento,
            TipoDocumento tipo) {

        Optional<Venta> venta =
                findVenta(idDocumento, tipo);

        if (venta.isEmpty()) {

            return Optional.empty();
        }

        return Optional.of(venta.get().getMonto());
    }

    public void vendePasaje(String idDoc, TipoDocumento tipo, IdPersona idPasajero, int asiento, LocalDate fechaViaje, LocalTime hora, String patBus) throws SistemaVentaPasajesException {

        Optional<Venta> venta = findVenta(idDoc, tipo);

        if (venta.isEmpty()) {

            throw new SistemaVentaPasajesException("No existe venta con el id y tipo de documento indicados");
        }

        Optional<Pasajero> pasajero = findPasajero(idPasajero);

        if (pasajero.isEmpty()) {

            throw new SistemaVentaPasajesException("No existe pasajero con el id indicado");
        }

        Optional<Viaje> viaje = findViaje(fechaViaje, hora, patBus);

        if (viaje.isEmpty()) {

            throw new SistemaVentaPasajesException("No existe viaje con la fecha, hora y patente indicadas");
        }

        if (!viaje.get().asientoDisponible(asiento)) {

            throw new SistemaVentaPasajesException("El asiento no se encuentra disponible");
        }

        venta.get().createPasaje(asiento, viaje.get(), pasajero.get());
    }

    public void pagaVenta(String idDocumento, TipoDocumento tipo) throws SistemaVentaPasajesException {

        Optional<Venta> venta = findVenta(idDocumento, tipo);

        if (venta.isEmpty()) {

            throw new SistemaVentaPasajesException("No existe venta con el id y tipo de documento indicados");
        }

        boolean pagado = venta.get().pagaMonto();

        if (!pagado) {

            throw new SistemaVentaPasajesException("La venta ya fue pagada");
        }
    }

    public void pagaVenta(String idDocumento, TipoDocumento tipo, long nroTarjeta) throws SistemaVentaPasajesException {

        Optional<Venta> venta = findVenta(idDocumento, tipo);

        if (venta.isEmpty()) {

            throw new SistemaVentaPasajesException("No existe venta con el id y tipo de documento indicados");
        }

        boolean pagado = venta.get().pagaMonto(nroTarjeta);

        if (!pagado) {

            throw new SistemaVentaPasajesException("La venta ya fue pagada");
        }
    }

    public String[][] getVentas() {

        String[][] matriz = new String[ventas.size()][7];

        for (int i = 0; i < ventas.size(); i++) {

            Venta v = ventas.get(i);

            matriz[i][0] = v.getIdDocumento();

            matriz[i][1] = v.getTipo().toString();

            matriz[i][2] = v.getFecha().toString();

            matriz[i][3] = v.getCliente().getIdPersona().toString();

            matriz[i][4] = v.getCliente().getNombreCompleto().toString();

            matriz[i][5] = String.valueOf(v.getPasajes().length);

            matriz[i][6] = String.valueOf(v.getMonto());
        }

        return matriz;
    }

    public String[][] getViajes() {

        String[][] matriz =
                new String[viajes.size()][5];

        for (int i = 0; i < viajes.size(); i++) {

            Viaje v = viajes.get(i);

            matriz[i][0] = v.getFecha().toString();

            matriz[i][1] = v.getHora().toString();

            matriz[i][2] = String.valueOf(v.getPrecio());

            matriz[i][3] = String.valueOf(v.getNroAsientosDisponibles());

            matriz[i][4] = v.getBus().getPatente();
        }

        return matriz;
    }

    public String[][] listPasajerosViaje(LocalDate fecha, LocalTime hora, String patenteBus) throws SistemaVentaPasajesException {

        Optional<Viaje> viaje = findViaje(fecha, hora, patenteBus);

        if (viaje.isEmpty()) {

            throw new SistemaVentaPasajesException("No existe viaje con la fecha, hora y patente indicadas");
        }

        return viaje.get().getListaPasajeros();
    }

    public String[][] listLlegadasSalidasTerminal(String nombreTerminal, LocalDate fecha) throws SistemaVentaPasajesException {

        Optional<Terminal> terminal = controladorEmpresas.findTerminal(nombreTerminal);

        if (terminal.isEmpty()) {

            throw new SistemaVentaPasajesException("No existe terminal con el nombre indicado");
        }

        ArrayList<String[]> lista = new ArrayList<>();

        for (Viaje v : terminal.get().getSalidas()) {

            if (v.getFecha().equals(fecha)) {

                String[] fila = new String[4];

                fila[0] = "SALIDA";
                fila[1] = v.getHora().toString();
                fila[2] = v.getTerminalLlegada().getNombre();
                fila[3] = v.getBus().getPatente();

                lista.add(fila);
            }
        }

        for (Viaje v : terminal.get().getLlegadas()) {

            if (v.getFecha().equals(fecha)) {

                String[] fila = new String[4];

                fila[0] = "LLEGADA";
                fila[1] = v.getHora().toString();
                fila[2] = v.getTerminalSalida().getNombre();
                fila[3] = v.getBus().getPatente();

                lista.add(fila);
            }
        }

        String[][] matriz = new String[lista.size()][4];

        for (int i = 0; i < lista.size(); i++) {
            matriz[i] = lista.get(i);
        }

        return matriz;
    }

    private Optional<Cliente> findCliente(
            IdPersona id) {

        for (Cliente c : clientes) {

            if (c.getIdPersona().equals(id)) {

                return Optional.of(c);
            }
        }

        return Optional.empty();
    }

    private Optional<Venta> findVenta(String idDocumento, TipoDocumento tipoDocumento) {

        for (Venta v : ventas) {

            if (v.getIdDocumento().equals(idDocumento) && v.getTipo() == tipoDocumento) {

                return Optional.of(v);
            }
        }

        return Optional.empty();
    }

    private Bus findBus(String patente) {

        Optional<Bus> bus = controladorEmpresas.findBus(patente);

        return bus.orElse(null);
    }

    private Optional<Viaje> findViaje(LocalDate fecha, LocalTime hora, String patenteBus) {

        for (Viaje v : viajes) {

            if (v.getFecha().equals(fecha) && v.getHora().equals(hora) && v.getBus().getPatente().equalsIgnoreCase(patenteBus)) {

                return Optional.of(v);
            }
        }

        return Optional.empty();
    }

    private Optional<Pasajero> findPasajero(IdPersona idPersona) {

        for (Pasajero p : pasajeros) {

            if (p.getIdPersona().equals(idPersona)) {

                return Optional.of(p);
            }
        }

        return Optional.empty();
    }
}