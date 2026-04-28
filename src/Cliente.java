import java.util.ArrayList;
//
public class Cliente extends Persona {

    private String email;
    ArrayList<Venta> ventas;

    public Cliente(IdPersona id, Nombre nombre,String email) {
        super(id, nombre);
        this.email = email;
        this.ventas = new ArrayList<>();
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email)   {
        this.email = email;
    }
    public void addVenta(Venta venta){
        this.ventas.add(venta);
    }
    public Venta[] getVentas(){
        Venta[] arreglo = new Venta[this.ventas.size()];
        return this.ventas.toArray(arreglo);
    }
}