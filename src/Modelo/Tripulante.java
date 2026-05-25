package Modelo;
import java.util.ArrayList;
import Utilidades.*;

//Denisse Manzor
public class Tripulante extends Persona {
    private Direccion direccion;
    private ArrayList<Viaje> viajes;

    public Tripulante(IdPersona id, Nombre nom, Direccion dir){
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

    public void addViaje(Viaje viaje){
        viajes.add(viaje);
    }

    public int getNroViajes(){
        return viajes.size();
    }
}