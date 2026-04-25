import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//Denisse Manzor Tapia
public class Viaje {
    private LocalDate fecha;
    private LocalTime hora;
    private int precio;

    //se agregan
    private Bus bus;
    private List<Pasaje> pasajes;

    public Viaje(LocalDate fecha, LocalTime hora, int precio, Bus bus) {
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.bus = bus;
        this.pasajes = new ArrayList<>();
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public LocalTime getHora() {
        return hora;
    }
    public int getPrecio() {
        return precio;
    }
    public void setPrecio(int precio) {
        this.precio = precio;
    }
    public Bus getBus() {
        return bus;
    }

    public String[][] getAsientos() {
        int total = bus.getNroAsientos();
        String[][] matriz = new String[total][2];

        // Inicializar todos como LIBRE
        for (int i = 0; i < total; i++) {
            matriz[i][0] = String.valueOf(i + 1); // número de asiento
            matriz[i][1] = "LIBRE";
        }
        // Marcar los ocupados
        for (Pasaje p : pasajes) {
            int asiento = p.getAsiento(); // asientos parten en 1
            matriz[asiento - 1][1] = "OCUPADO";
        }
        return matriz;
    }

    public void addPasaje(Pasaje pasaje) {
        this.pasajes.add(pasaje);
    }
    //este lo agregue pq lo necesitaba en SistemaDePasajes
    public List<Pasaje> getPasajes() {
        return pasajes;
    }
    public boolean existeDisponibilidad() {
        return getNroAsientosDisponibles() > 0;
    }
    public int getNroAsientosDisponibles() {
        return bus.getNroAsientos() - pasajes.size();
    }
}