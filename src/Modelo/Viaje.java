package Modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Viaje {

    private LocalDate fecha;
    private LocalTime hora;

    private int precio;
    private int duracion;

    private Bus bus;

    private Auxiliar auxiliar;
    private ArrayList<Conductor> conductores;

    private Terminal terminalSalida;
    private Terminal terminalLlegada;

    private ArrayList<Pasaje> pasajes;
    private ArrayList<Venta> ventas;

    public Viaje(LocalDate fecha, LocalTime hora, int precio, int duracion, Bus bus, Auxiliar auxiliar, ArrayList<Conductor> conductores, Terminal terminalSalida, Terminal terminalLlegada) {

        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.duracion = duracion;

        this.bus = bus;
        this.auxiliar = auxiliar;

        this.terminalSalida = terminalSalida;
        this.terminalLlegada = terminalLlegada;

        this.pasajes = new ArrayList<>();
        this.ventas = new ArrayList<>();
        this.conductores = new ArrayList<>();

        if (bus != null) {
            bus.addViaje(this);
        }

        if (auxiliar != null) {
            auxiliar.addViaje(this);
        }

        if (terminalSalida != null) {
            terminalSalida.addSalida(this);
        }

        if (terminalLlegada != null) {
            terminalLlegada.addLlegada(this);
        }
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public Bus getBus() {
        return bus;
    }

    public Auxiliar getAuxiliar() {
        return auxiliar;
    }

    public Conductor[] getConductores() {
        return conductores.toArray(new Conductor[0]);
    }

    public Terminal getTerminalSalida() {
        return terminalSalida;
    }

    public Terminal getTerminalLlegada() {
        return terminalLlegada;
    }

    public Pasaje[] getPasajes() {
        return pasajes.toArray(new Pasaje[0]);
    }

    public Venta[] getVentas() {
        return ventas.toArray(new Venta[0]);
    }

    public boolean addConductor(Conductor conductor) {

        if (conductor == null) {
            return false;
        }

        if (conductores.contains(conductor)) {
            return false;
        }

        conductores.add(conductor);

        conductor.addViaje(this);

        return true;
    }

    public boolean addPasaje(Pasaje pasaje) {

        if (pasaje == null) {
            return false;
        }

        if (!asientoDisponible(pasaje.getAsiento())) {
            return false;
        }

        pasajes.add(pasaje);

        return true;
    }

    public void addVenta(Venta venta) {

        if (venta != null && !ventas.contains(venta)) {
            ventas.add(venta);
        }
    }

    public boolean asientoDisponible(int asiento) {

        if (asiento < 1 || asiento > bus.getNroAsientos()) {
            return false;
        }

        for (Pasaje p : pasajes) {

            if (p.getAsiento() == asiento) {
                return false;
            }
        }

        return true;
    }

    public int getNroAsientosDisponibles() {
        return bus.getNroAsientos() - pasajes.size();
    }

    public boolean existeDisponibilidad(int nroAsientos) {
        return getNroAsientosDisponibles() >= nroAsientos;
    }

    public String[] getAsientos() {

        String[] asientos = new String[bus.getNroAsientos()];

        for (int i = 0; i < asientos.length; i++) {
            asientos[i] = String.valueOf(i + 1);
        }

        for (Pasaje p : pasajes) {
            asientos[p.getAsiento() - 1] = "X";
        }

        return asientos;
    }

    public String[][] getListaPasajeros() {

        String[][] datos = new String[pasajes.size()][3];

        for (int i = 0; i < pasajes.size(); i++) {

            Pasaje p = pasajes.get(i);

            datos[i][0] = String.valueOf(p.getAsiento());
            datos[i][1] = p.getPasajero().getIdPersona().toString();
            datos[i][2] = p.getPasajero().getNombreCompleto().toString();
        }

        return datos;
    }

    public Tripulante[] getTripulantes() {

        ArrayList<Tripulante> lista = new ArrayList<>();

        if (auxiliar != null) {
            lista.add(auxiliar);
        }

        lista.addAll(conductores);

        return lista.toArray(new Tripulante[0]);
    }

    public LocalDateTime getFechaHoraTermino() {
        return LocalDateTime.of(fecha, hora).plusHours(duracion);
    }
}