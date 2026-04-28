public class Pasajero extends Persona {
    //Luis Reyes Alcaino
    private Nombre nomContacto;
    private String fonoContacto;

    public Pasajero(IdPersona id, Nombre nom) {
        super(id, nom);
    }

    public Nombre getNomContacto() {
        return nomContacto;
    }

    public void setNomContacto(Nombre nomContacto) {
        this.nomContacto = nomContacto;
    }

    public String getFonoContacto() {
        return fonoContacto;
    }

    public void setFonoContacto(String fonoContacto) {
        this.fonoContacto = fonoContacto;
    }
}