
package MODELO;

public class Consumo {
     private int id;
    private int id_reserva;
    private int id_producto;
    private int cantidad;
    private double subtotal;

    public Consumo() {}

    public Consumo(int id, int id_reserva, int id_producto, int cantidad, double subtotal) {
        this.id = id;
        this.id_reserva = id_reserva;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getId_reserva() { return id_reserva; }
    public void setId_reserva(int id_reserva) { this.id_reserva = id_reserva; }

    public int getId_producto() { return id_producto; }
    public void setId_producto(int id_producto) { this.id_producto = id_producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}
