import java.util.Objects;

public class Persona implements IdPersona{
    private IdPersona idPersona;
    private Nombre nombreCompleto;
    private String telefono;

    public Persona(IdPersona id, Nombre nombre ){
        this.idPersona = id;
        this.nombreCompleto = nombre;
    }
    public IdPersona getIdPersona() {
        return idPersona;
    }
    public Nombre getNombreCompleto(){
        return nombreCompleto;
    }
    public void setNombreCompleto(Nombre nombreCompleto){
        this.nombreCompleto = nombreCompleto;
    }
    public String getTelefono(){
        return telefono;
    }
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }
    @Override
    public String toString() {
        return "Persona: " + idPersona + "\nNombre: " + nombreCompleto + "\nTelefono: " + telefono;
    }
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        Persona persona =  (Persona) o;

        return Objects.equals(idPersona, persona.idPersona);
    }
}
