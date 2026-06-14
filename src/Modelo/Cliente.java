package Modelo;

import java.util.ArrayList;
import java.util.List;
import Utilidades.*;

public class Cliente extends Persona {
    private String email;
    private List<Venta> ventas; // Asumiendo que es una lista de ventas

    // Constructor corregido según instrucciones:
    // Persona entrega id y nombre, se agrega email.
    public Cliente(IdPersona id, Nombre nombre, String email) {
        super(id, nombre);
        this.email = email;
        this.ventas = new ArrayList<>();
    }

    // Getters y Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Venta> getVentas() {
        return ventas;
    }


    public void addVenta(Venta venta) {
        this.ventas.add(venta);
    }

    @Override
    public String toString() {
        return super.toString() + "\nEmail: " + email;
    }


}