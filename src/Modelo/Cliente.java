package Modelo;

import Utilidades.*;
import java.util.ArrayList;

public class Cliente extends Persona {

    private Direccion direccion;
    private ArrayList<Venta> ventas;

    public Cliente(IdPersona id, Nombre nombre, Direccion direccion) {
        super(id, nombre);
        this.direccion = direccion;
        this.ventas = new ArrayList<>();
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
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