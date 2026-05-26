package Modelo;

import Utilidades.*;
import java.util.ArrayList;

public class Empresa {

    private Rut rut;
    private String nombre;
    private String url;

    private ArrayList<Bus> buses;
    private ArrayList<Conductor> conductores;
    private ArrayList<Auxiliar> auxiliares;
    private ArrayList<Venta> ventas;

    public Empresa(Rut rut, String nombre) {

        this.rut = rut;
        this.nombre = nombre;

        this.buses = new ArrayList<>();
        this.conductores = new ArrayList<>();
        this.auxiliares = new ArrayList<>();
        this.ventas = new ArrayList<>();
    }

    public Rut getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addBus(Bus bus) {

        if (bus != null) {
            buses.add(bus);
        }
    }

    public Bus[] getBuses() {
        return buses.toArray(new Bus[0]);
    }

    public boolean addConductor(IdPersona id, Nombre nom, Direccion dir) {

        for (Tripulante t : getTripulantes()) {

            if (t.getIdPersona().equals(id)) {
                return false;
            }
        }

        Conductor c = new Conductor(id, nom, dir);

        conductores.add(c);

        return true;
    }

    public boolean addAuxiliar(IdPersona id, Nombre nom, Direccion dir) {

        for (Tripulante t : getTripulantes()) {

            if (t.getIdPersona().equals(id)) {
                return false;
            }
        }

        Auxiliar a = new Auxiliar(id, nom, dir);

        auxiliares.add(a);

        return true;
    }

    public Tripulante[] getTripulantes() {

        ArrayList<Tripulante> lista = new ArrayList<>();

        lista.addAll(conductores);
        lista.addAll(auxiliares);

        return lista.toArray(new Tripulante[0]);
    }

    public void addVenta(Venta venta) {

        if (venta != null) {
            ventas.add(venta);
        }
    }

    public Venta[] getVentas() {
        return ventas.toArray(new Venta[0]);
    }
}