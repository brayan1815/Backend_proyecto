package MODELO;

public class Factura {
    // Atributos que representan los datos de la factura
    private int id;
    private int idReserva;
    private int minutos;
    private double total;

    // Constructor vacío
    public Factura() {}

    // Constructor con todos los datos
    public Factura(int id, int idReserva, int minutos, double total) {
        this.id = id;
        this.idReserva = idReserva;
        this.minutos = minutos;
        this.total = total;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
