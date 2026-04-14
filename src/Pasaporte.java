//Denisse Alejandra Manzor Tapia
public class Pasaporte implements IdPersona{
    private String numero;
    private String nacionalidad;

    //Constructor
    public Pasaporte(String numero, String nacionalidad) {
        this.numero = numero;
        this.nacionalidad = nacionalidad;
    }

    //Metodos
    public String getNumero() {
        return numero;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    //toString
    @Override
    public String toString() {
        return numero + " " + nacionalidad;
    }

    //equals
    @Override
    public boolean equals(Object otro) {

        if (this == otro) return true;

        //verifica si es un objeto pasaporte
        if (!(otro instanceof Pasaporte)) return false;

        //casting
        Pasaporte p = (Pasaporte) otro;

        return numero.equals(p.numero) &&
                nacionalidad.equals(p.nacionalidad);
    }

    //of (si uno de los dos es nulo retornara nulo, si no, retornara un nuevo objeto Pasaporte.
    public static Pasaporte of(String numero, String nacionalidad) {

        if (numero == null || nacionalidad == null) {
            return null; //es como un break
        }

        return new Pasaporte(numero, nacionalidad);
    }
}