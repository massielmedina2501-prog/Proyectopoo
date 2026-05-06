import java.util.ArrayList;
import java.util.List;


public class Terminal {
    private String nombre;
    private Direccion direccion;
    private List<Viaje> salida;
    private List<Viaje> llegada;
    private ControladorEmpresas controladorEmpresas = ControladorEmpresas.getInstance();
    public Terminal(String nombre, Direccion direccion){
        this.nombre = nombre;
        this.direccion = direccion;
        this.salida = new ArrayList<>();
        this.llegada = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public Direccion getDireccion() {
        return direccion;
    }
    public void setDireccion(Direccion direccion){
        this.direccion = direccion;
    }
    public void addLlegada(Viaje viaje){
        llegada.add(viaje);
    }
    public void addSalida(Viaje viaje){
        salida.add(viaje);
    }
    public Viaje[] getLlegadas(){
        return llegada.toArray(new Viaje[0]);

    }
    public Viaje[] getSalidas(){
        return salida.toArray(new Viaje[0]);
    }

}
