package MODELO;

// Clase que representa el consumo de un producto con su información asociada
public class ConsumoDTO {
    // Identificador único del consumo
    private int idConsumo;
    // Nombre del producto consumido
    private String nombreProducto;
    // Cantidad de producto consumido
    private int cantidad;
    // Subtotal del consumo (cantidad * precio unitario)
    private double subtotal;
    // Precio unitario del producto
    private double precioProducto;
    // Cantidad restante de producto después del consumo
    private int cantidadRestanteProducto;

    // Métodos getters y setters para acceder y modificar los atributos

    // Devuelve el ID del consumo
    public int getIdConsumo() {
        return idConsumo;
    }

    // Establece el ID del consumo
    public void setIdConsumo(int idConsumo) {
        this.idConsumo = idConsumo;
    }

    // Devuelve el nombre del producto consumido
    public String getNombreProducto() {
        return nombreProducto;
    }

    // Establece el nombre del producto consumido
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    // Devuelve la cantidad consumida
    public int getCantidad() {
        return cantidad;
    }

    // Establece la cantidad consumida
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Devuelve el subtotal del consumo
    public double getSubtotal() {
        return subtotal;
    }

    // Establece el subtotal del consumo
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    // Devuelve el precio unitario del producto
    public double getPrecioProducto() {
        return precioProducto;
    }

    // Establece el precio unitario del producto
    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

    // Devuelve la cantidad restante de producto
    public int getCantidadRestanteProducto() {
        return cantidadRestanteProducto;
    }

    // Establece la cantidad restante de producto
    public void setCantidadRestanteProducto(int cantidadRestanteProducto) {
        this.cantidadRestanteProducto = cantidadRestanteProducto;
    }
}
