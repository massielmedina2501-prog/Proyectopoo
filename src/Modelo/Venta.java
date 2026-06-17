package Modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Venta implements Serializable {

    private String idDocumento;
    private TipoDocumento tipo;
    private LocalDate fecha;
    private Cliente cliente;

    private ArrayList<Pasaje> pasajes;
    private Pago pago;

    private static long contadorPasajes = 1;

    public Venta(String idDocumento, TipoDocumento tipo, LocalDate fecha, Cliente cliente) {

        this.idDocumento = idDocumento;
        this.tipo = tipo;
        this.fecha = fecha;
        this.cliente = cliente;

        this.pasajes = new ArrayList<>();
        this.pago = null;

        cliente.addVenta(this);
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

    public int getMonto() {

        int total = 0;

        for (Pasaje p : pasajes) {
            total += p.getViaje().getPrecio();
        }

        return total;
    }

    public void createPasaje(int asiento, Viaje viaje, Pasajero pasajero) {

        Pasaje pasaje = new Pasaje(
                contadorPasajes++,
                asiento,
                viaje,
                pasajero,
                this
        );

        pasajes.add(pasaje);
        viaje.addPasaje(pasaje);
    }

    public Pasaje[] getPasajes() {
        return pasajes.toArray(new Pasaje[0]);
    }

    public boolean pagaMonto() {

        if (pago != null) {
            return false;
        }

        pago = new PagoEfectivo(getMonto());
        return true;
    }

    public boolean pagaMonto(long nroTarjeta) {

        if (pago != null) {
            return false;
        }

        pago = new PagoTarjeta(getMonto(), nroTarjeta);
        return true;
    }

    public int getMontoPagado() {

        if (pago == null) {
            return 0;
        }

        return pago.getMonto();
    }

    public String getTipoPago() {

        if (pago == null) {
            return "Pendiente";
        }

        if (pago instanceof PagoEfectivo) {
            return "Efectivo";
        }

        return "Tarjeta";
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Venta)) {
            return false;
        }

        Venta venta = (Venta) o;

        return idDocumento.equals(venta.idDocumento)
                && tipo == venta.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDocumento, tipo);
    }
}