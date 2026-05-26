package Modelo;

import Utilidades.*;
import java.util.Objects;

public class Persona {

    private IdPersona idPersona;
    private Nombre nombreCompleto;
    private String telefono;

    public Persona(IdPersona idPersona, Nombre nombreCompleto) {
        this.idPersona = idPersona;
        this.nombreCompleto = nombreCompleto;
    }

    public IdPersona getIdPersona() {
        return idPersona;
    }

    public Nombre getNombreCompleto() {
        return nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setNombreCompleto(Nombre nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return nombreCompleto.toString();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Persona)) {
            return false;
        }

        Persona persona = (Persona) o;

        return Objects.equals(idPersona, persona.idPersona);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPersona);
    }
}