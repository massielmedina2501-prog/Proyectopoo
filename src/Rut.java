//Denisse Alejandra Manzor Tapia
public class Rut implements IdPersona{
    private int numero;
    private char dv;

    public Rut(int num, char dv) {
        this.numero = num;
        this.dv = dv;
    }
    public int getNumero() {
        return numero;
    }
    public char getDv() {
        return dv;
    }

    @Override
    public String toString() {
        String numString = String.valueOf(numero); //convierte el numero a String
        String resultado = ""; //guardara el resultado
        int contador = 0;

        for (int i = numString.length() - 1; i >= 0; i--) { //parte desde el final
            resultado = numString.charAt(i) + resultado; //agrega un caracter(i) adelante del resultado
            contador++;

            if (contador == 3 && i != 0) { //cada 3 caracteres pone un . y reinicia el contador
                resultado = "." + resultado;
                contador = 0;
            }
        }
        return resultado + "-" + dv;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) return true;

        //verifica si es un objeto rut
        if (!(otro instanceof Rut)) return false;

        //casting
        Rut r = (Rut) otro;
        return numero == r.numero && dv == r.dv;
    }

    public static Rut of(String rutConDv) {
        if (rutConDv == null) return null;
        rutConDv = rutConDv.replace(".", ""); //reemplaza los puntos por vacio, osea los elimina
        String[] partes = rutConDv.split("-"); // .SEPARA el numero y el dv, el separador es el "-"

        if (partes.length != 2) return null; //verifica que se separo en dos

        int num = Integer.parseInt(partes[0]); // cambia el numero a int
        char dv = partes[1].charAt(0); //cambia el dv a char
        return new Rut(num, dv); //crea un nuevo objeto Rut con numero en int y el dv en char (separados)
    }
}