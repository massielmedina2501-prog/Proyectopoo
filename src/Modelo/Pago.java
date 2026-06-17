package Modelo;

import java.io.Serializable;

public class Pago implements Serializable {

    private int monto;

    public Pago(int monto) {
        this.monto = monto;
    }

    public int getMonto() {
        return monto;
    }
}