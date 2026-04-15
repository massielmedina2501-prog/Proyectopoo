import java.util.Objects;

public class Persona {
    private IdPersona idPersona;
    private Nombre nombreCompleto;
    private String telefono;

    //Constructor
    public Persona(IdPersona idPersona, Nombre nombreCompleto){
        this.idPersona = idPersona;
        this.nombreCompleto = nombreCompleto;
    }

    // Metodos
    public IdPersona getIdPersona() {
        return idPersona;
    }

    public Nombre getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(Nombre nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return idPersona + " " + nombreCompleto + " " + telefono;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return Objects.equals(idPersona, persona.idPersona) && Objects.equals(nombreCompleto, persona.nombreCompleto) && Objects.equals(telefono, persona.telefono);
    }
}