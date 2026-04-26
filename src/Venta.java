import java.time.LocalDate;
import java.util.ArrayList;
//Massiel Medina
public class Venta {

    private String idDocumento;
    private TipoDocumento tipo;
    private LocalDate fecha;
    private Cliente cliente;
    private ArrayList<Pasaje> pasajes;

    private static long contadorPasajes = 1;

    public Venta(String id, TipoDocumento tipo, LocalDate fec, Cliente cli) {
        this.idDocumento = id;
        this.tipo = tipo;
        this.fecha = fec;
        this.cliente = cli;
        this.pasajes = new ArrayList<>();

        cli.addVenta(this);
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public TipoDocumento getTipo() {
        return tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void createPasaje(int asiento, Viaje viaje, Pasajero pasajero) {

        long numeroPasaje = contadorPasajes++;


        Pasaje p = new Pasaje(numeroPasaje, asiento, viaje, pasajero, this);

        if (viaje.addPasaje(p)) {
            pasajes.add(p);
            System.out.println(".:| Pasaje creado |:.");
        } else {
            System.out.println(".:| No se pudo crear el pasaje |:.");
        }
    }

    public void addPasaje(Pasaje p) {
        pasajes.add(p);
    }

    public Pasaje[] getPasajes() {
        return pasajes.toArray(new Pasaje[0]);
    }

    public int getMonto() {
        int total = 0;

        for (Pasaje p : pasajes) {
            total += p.getViaje().getPrecio();
        }

        return total;
    }
}