//Denisse Alejandra Manzor Tapia
public class Pasaporte implements IdPersona{
    private String numero;
    private String nacionalidad;

    public Pasaporte(String num, String nacionalidad) {
        this.numero = num;
        this.nacionalidad = nacionalidad;
    }

    public String getNumero() {
        return numero;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    @Override
    public String toString() {
        return numero + " " + nacionalidad;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) return true;

        //verifica si es un objeto pasaporte
        if (!(otro instanceof Pasaporte)) return false;

        //casting
        Pasaporte p = (Pasaporte) otro;
        return numero.equals(p.numero) && nacionalidad.equals(p.nacionalidad);
    }

    //of (si uno de los dos es nulo retornara nulo, si no, retornara un nuevo objeto Pasaporte.
    public static Pasaporte of(String num, String nacionalidad) {
        if (num == null || nacionalidad == null) {
            return null; //es como un break
        }
        return new Pasaporte(num, nacionalidad);
    }
}