package Modelo;

import java.io.Serializable;

public class Pasaje implements Serializable {

    private long numero;
    private int asiento;

    private Viaje viaje;
    private Pasajero pasajero;
    private Venta venta;

    public Pasaje(long numero, int asiento, Viaje viaje, Pasajero pasajero, Venta venta) {
        this.numero = numero;
        this.asiento = asiento;
        this.viaje = viaje;
        this.pasajero = pasajero;
        this.venta = venta;
    }

    public long getNumero() {
        return numero;
    }

    public int getAsiento() {
        return asiento;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public Venta getVenta() {
        return venta;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------- PASAJE ELECTRÓNICO --------------------\n");

        sb.append(String.format("%-30s %-30s\n", "NOMBRE EMPRESA", "NUMERO DE PASAJE"));
        sb.append(String.format("%-30s %-30s\n", this.getViaje().getBus().getEmpresa().getNombre(), this.numero));


        sb.append(String.format("%-30s %-30s\n", "Nombre Pasajero", "RUT/Pasaporte"));

        sb.append(String.format("%-30s %-30s\n", this.getPasajero().getNombreCompleto().toString(), this.getPasajero().getIdPersona().toString()));


        sb.append(String.format("%-20s %-15s %-25s\n", "Patente bus", "Asiento", "Valor Pagado"));

        sb.append(String.format("%-20s %-15d %-25d\n", this.getViaje().getBus().getPatente(), this.asiento, this.getViaje().getPrecio()));


        sb.append(String.format("%-20s %-20s %-15s %-10s\n", "Terminal origen", "Terminal destino", "Fecha", "Hora"));
        sb.append(String.format("%-20s %-20s %-15s %-10s\n",
                this.getViaje().getTerminalSalida().getNombre(),
                this.getViaje().getTerminalLlegada().getNombre(),
                this.getViaje().getFecha().toString(),
                this.getViaje().getHora().toString()));

        return sb.toString();
    }
}