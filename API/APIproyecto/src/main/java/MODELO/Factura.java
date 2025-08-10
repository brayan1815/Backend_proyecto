package MODELO;

public class Factura {
    // Identificador único de la factura
    private int id;
    // Identificador de la reserva asociada a la factura
    private int idReserva;
    // Minutos de uso o servicio que se facturan
    private int minutos;
    // Subtotal por consumos (productos)
    private double subtotalConsumos;
    // Subtotal por uso de consola
    private double subtotalConsola;
    // Total a pagar por la factura
    private double total;

    // Constructor vacío
    public Factura() {}

    // Constructor que recibe todos los datos de la factura
    public Factura(int id, int idReserva, int minutos, double subtotalConsumos, double subtotalConsola, double total) {
        this.id = id;
        this.idReserva = idReserva;
        this.minutos = minutos;
        this.subtotalConsumos = subtotalConsumos;
        this.subtotalConsola = subtotalConsola;
        this.total = total;
    }

    // Retorna el ID de la factura
    public int getId() {
        return id;
    }

    // Asigna el ID de la factura
    public void setId(int id) {
        this.id = id;
    }

    // Retorna el ID de la reserva asociada
    public int getIdReserva() {
        return idReserva;
    }

    // Asigna el ID de la reserva asociada
    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    // Retorna la cantidad de minutos facturados
    public int getMinutos() {
        return minutos;
    }

    // Asigna la cantidad de minutos facturados
    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    // Retorna el subtotal por consumos
    public double getSubtotalConsumos() {
        return subtotalConsumos;
    }

    // Asigna el subtotal por consumos
    public void setSubtotalConsumos(double subtotalConsumos) {
        this.subtotalConsumos = subtotalConsumos;
    }

    // Retorna el subtotal por uso de consola
    public double getSubtotalConsola() {
        return subtotalConsola;
    }

    // Asigna el subtotal por uso de consola
    public void setSubtotalConsola(double subtotalConsola) {
        this.subtotalConsola = subtotalConsola;
    }

    // Retorna el total a pagar
    public double getTotal() {
        return total;
    }

    // Asigna el total a pagar
    public void setTotal(double total) {
        this.total = total;
    }
}
