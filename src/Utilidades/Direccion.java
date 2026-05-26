package Utilidades;

import java.util.Objects;

public class Direccion {

    private String calle;
    private int numero;
    private String comuna;

    public Direccion(String calle, int numero, String comuna) {
        this.calle = calle;
        this.numero = numero;
        this.comuna = comuna;
    }

    public int getNumero() {
        return numero;
    }

    public String getCalle() {
        return calle;
    }

    public String getComuna() {
        return comuna;
    }

    @Override
    public String toString() {
        return "Calle: " + calle + "\n" +
                "Numero: " + numero + "\n" +
                "Comuna: " + comuna;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Direccion direccion = (Direccion) obj;

        return numero == direccion.numero &&
                calle.equals(direccion.calle) &&
                comuna.equals(direccion.comuna);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calle, numero, comuna);
    }
}