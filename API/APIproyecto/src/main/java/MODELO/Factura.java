
package MODELO;

public class Factura {
    //atributos que representan los datos de la factura
    private int id;
    private int idReserva;
    private int minutos;
    private double subtotalConsola;
    private double subtotalConsumos;
    private double total;

    public Factura() {}//constructor vacio

    //constructor con todos los datos
    public Factura(int id, int idReserva, int minutos, double subtotalConsola, double subtotalConsumos, double total) {
        this.id = id;
        this.idReserva = idReserva;
        this.minutos = minutos;
        this.subtotalConsola = subtotalConsola;
        this.subtotalConsumos = subtotalConsumos;
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

    public double getSubtotalConsola() {
        return subtotalConsola;
    }

    public void setSubtotalConsola(double subtotalConsola) {
        this.subtotalConsola = subtotalConsola;
    }

    public double getSubtotalConsumos() {
        return subtotalConsumos;
    }

    public void setSubtotalConsumos(double subtotalConsumos) {
        this.subtotalConsumos = subtotalConsumos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
