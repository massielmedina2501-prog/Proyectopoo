public class Pasajero extends Persona {

    private Nombre nomContacto;
    private String fonoContacto;


    public Pasajero(IdPersona id, Nombre nombreCompleto,String telefono,Nombre nomContacto,String fonoContacto) {
        super(id,nombreCompleto);
        setTelefono(telefono);
        this.nomContacto = nomContacto;
        this.fonoContacto = fonoContacto;
    }

    @Override
    public Nombre getNombreCompleto() {
        return super.getNombreCompleto();
    }
    public void setNomContacto(Nombre nom) {
        this.nomContacto = nom;
    }
    public String getFonoContacto() {
        return fonoContacto;
    }
    public void setFonoContacto(String fonoContacto) {
        this.fonoContacto = fonoContacto;
    }
}
