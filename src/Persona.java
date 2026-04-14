public class Persona implements IdPersona{
    private IdPersona idPersona;
    private Nombre nombreCompleto;
    private String telefono;

    public Persona(IdPersona id, Nombre nombre ){
        this.id = idPersona;
        this.nombre = nombreCompleto;
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

    }
}
