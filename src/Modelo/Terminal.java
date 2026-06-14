package Modelo;

import Utilidades.*;
import java.util.ArrayList;

public class Terminal {

    private String nombre;
    private Direccion direccion;

    private ArrayList<Viaje> salida;
    private ArrayList<Viaje> llegada;

    public Terminal(String nombre, Direccion direccion) {
        this.nombre = nombre;
        this.direccion = direccion;

        this.salida = new ArrayList<>();
        this.llegada = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void addSalida(Viaje viaje) {
        if (viaje != null) {
            salida.add(viaje);
        }
    }

    public void addLlegada(Viaje viaje) {
        if (viaje != null) {
            llegada.add(viaje);
        }
    }

    public ArrayList<Viaje> getSalidas() {
        return salida;
    }

    public ArrayList<Viaje> getLlegadas() {
        return llegada;
    }
}