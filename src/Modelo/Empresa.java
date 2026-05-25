package Modelo;
import Utilidades.*;
import java.util.ArrayList;

//Denisse Manzor
public class Empresa {
    private Rut rut;
    private String nombre;
    private String url;

    private ArrayList<Bus> buses;
    private ArrayList<Conductor> conductores;
    private ArrayList<Auxiliar> auxiliares;
    private ArrayList<Venta> ventas;

    public Empresa(Rut rut, String nombre){
        this.rut = rut;
        this.nombre = nombre;
        this.buses = new ArrayList<>();
        this.auxiliares = new ArrayList<>();
        this.conductores = new ArrayList<>();
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

    public void addBus(Bus bus){
        if (bus != null) {
            buses.add(bus);
        }
    }

    public Bus[] getBuses(){
        return buses.toArray(new Bus[0]);
    }

    public boolean addConductor(IdPersona id, Nombre nom, Direccion dir){
        if (id == null || nom == null || dir == null) return false;

        Conductor c = new Conductor(id, nom, dir);
        conductores.add(c);
        return true;
    }

    public boolean addAuxiliar(IdPersona id, Nombre nom, Direccion dir){
        if (id == null || nom == null || dir == null) return false;

        Auxiliar a = new Auxiliar(id, nom, dir);
        auxiliares.add(a);
        return true;
    }

    public Tripulante[] getTripulantes(){
        ArrayList<Tripulante> tripulantes = new ArrayList<>();
        tripulantes.addAll(conductores);
        tripulantes.addAll(auxiliares);
        return tripulantes.toArray(new Tripulante[0]);
    }

    public Venta[] getVentas(){
        return ventas.toArray(new Venta[0]);
    }
}