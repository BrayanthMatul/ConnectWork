package models;

public class SaldoRequest {
    private int id;
    private double monto;

    public SaldoRequest(int id, double monto) {
        this.id = id;
        this.monto = monto;
    }

    public int getId() {
        return id;
    }

    public double getMonto() {
        return monto;
    }
}
