public class Cliente extends Persona {
    private String email;

    public Cliente(IdPersona id, Nombre nombre,String email) {
        super(idPersona, nombreCompleto);
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
