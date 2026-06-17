package Modelo;

import Utilidades.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Tripulante extends Persona implements Serializable {

    private Direccion direccion;
    private ArrayList<Viaje> viajes;

    public Tripulante(IdPersona id, Nombre nom, Direccion dir) {
        super(id, nom);
        this.direccion = dir;
        this.viajes = new ArrayList<>();
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public void addViaje(Viaje viaje) {
        if (viaje != null) {
            viajes.add(viaje);
        }
    }

    public Viaje[] getViajes() {
        return viajes.toArray(new Viaje[0]);
    }

    public int getNroViajes() {
        return viajes.size();
    }
}