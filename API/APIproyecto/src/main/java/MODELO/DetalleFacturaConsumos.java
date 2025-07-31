
package MODELO;

public class DetalleFacturaConsumos {
    // Atributos que representan los datos de un detalle de factura de consumo
    private int id;
    private int id_factura;
    private int id_producto;
    private String nombre_producto;
    private int cantidad;
    private double precio_unitario;
    private double subtotal;

    public DetalleFacturaConsumos() {} // Constructor vacío

    // Constructor con todos los datos
    public DetalleFacturaConsumos(int id, int id_factura, int id_producto, String nombre_producto, int cantidad, double precio_unitario, double subtotal) {
        this.id = id;
        this.id_factura = id_factura;
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.subtotal = subtotal;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getId_factura() { return id_factura; }
    public void setId_factura(int id_factura) { this.id_factura = id_factura; }

    public int getId_producto() { return id_producto; }
    public void setId_producto(int id_producto) { this.id_producto = id_producto; }

    public String getNombre_producto() { return nombre_producto; }
    public void setNombre_producto(String nombre_producto) { this.nombre_producto = nombre_producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecio_unitario() { return precio_unitario; }
    public void setPrecio_unitario(double precio_unitario) { this.precio_unitario = precio_unitario; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}
