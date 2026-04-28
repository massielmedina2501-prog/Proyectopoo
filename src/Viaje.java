import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//Denisse Manzor Tapia.
public class Viaje {
    private LocalDate fecha;
    private LocalTime hora;
    private int precio;

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

    public List<Pasaje> getPasajes() {
        return pasajes;
    }

    // verificacion de cantidad de asientos
    public boolean asientoValido(int asiento) {
        return asiento >= 1 && asiento <= bus.getNroAsientos();
    }

    // Verifica si el asiento ya está ocupado
    public boolean asientoDisponible(int asiento) {
        for (Pasaje p : pasajes) {
            if (p.getAsiento() == asiento) {
                return false;
            }
        }
        return true;
    }

    public boolean addPasaje(Pasaje pasaje) {
        int asiento = pasaje.getAsiento();

        if (!asientoValido(asiento)) {
            System.out.println(".:| Asiento fuera de rango |:.");
            return false;
        }

        if (!asientoDisponible(asiento)) {
            System.out.println(".:| Asiento ocupado |:.");
            return false;
        }

        pasajes.add(pasaje);
        return true;
    }

    public String[][] getAsientos() {
        int total = bus.getNroAsientos();
        String[][] matriz = new String[total][2];

        for (int i = 0; i < total; i++) {
            matriz[i][0] = String.valueOf(i + 1);
            matriz[i][1] = "LIBRE";
        }
        for (Pasaje p : pasajes) {
            int asiento = p.getAsiento();
            matriz[asiento - 1][1] = "OCUPADO";
        }
        return matriz;
    }

    public boolean existeDisponibilidad() {
        return getNroAsientosDisponibles() > 0;
    }

    public int getNroAsientosDisponibles() {
        return bus.getNroAsientos() - pasajes.size();
    }
}