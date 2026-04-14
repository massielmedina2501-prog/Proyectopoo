public class Pasajero extends Persona {

    private Nombre nomContacto;
    private String fonoContacto;


    public Pasajero() {


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
