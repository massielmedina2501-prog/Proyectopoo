import java.util.Objects;
public class Nombre{

    private Tratamiento tratamiento;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;

    public Nombre(Tratamiento tratamiento, String nombres, String apellidoPaterno, String apellidoMaterno) {
        this.tratamiento = tratamiento;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    @Override
    public String toString() {
        return tratamiento + " " + nombres + " " + apellidoPaterno + " " + apellidoMaterno;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Nombre other = (Nombre) obj;

        return tratamiento == other.tratamiento &&
                Objects.equals(nombres, other.nombres) &&
                Objects.equals(apellidoPaterno, other.apellidoPaterno) &&
                Objects.equals(apellidoMaterno, other.apellidoMaterno);
    }
}