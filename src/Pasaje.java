public class Pasaje {
    private long numero;
    private int asiento;
    private Viaje viaje;
    private Pasajero pasajero;
    // Asumiendo que 'Venta' es otra clase del sistema
    private Object venta;

    public Pasaje(long numero, int asiento, Viaje viaje, Pasajero pasajero, Object venta) {
        this.numero = numero;
        this.asiento = asiento;
        this.viaje = viaje;
        this.pasajero = pasajero;
        this.venta = venta;
    }

    public long getNumero() { return numero; }
    public int getAsiento() { return asiento; }
    public Viaje getViaje() { return viaje; }
    public Pasajero getPasajero() { return pasajero; }
    public Object getVenta() { return venta; }
}