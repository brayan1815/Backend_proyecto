
package MODELO;


public class ConsumoDTO {
    private int idConsumo;
    private String nombreProducto;
    private int cantidad;
    private double subtotal;
    private double precioProducto;
    private int cantidadRestanteProducto;

    public int getIdConsumo() {
        return idConsumo;
    }

    public void setIdConsumo(int idConsumo) {
        this.idConsumo = idConsumo;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public int getCantidadRestanteProducto() {
        return cantidadRestanteProducto;
    }

    public void setCantidadRestanteProducto(int cantidadRestanteProducto) {
        this.cantidadRestanteProducto = cantidadRestanteProducto;
    }
}

