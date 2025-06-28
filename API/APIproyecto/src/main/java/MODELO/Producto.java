
package MODELO;


public class Producto {
     private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidades_disponibles;
    private int id_estado_producto;
    private int id_imagen;

    public Producto() {}

    public Producto(int id, String nombre, String descripcion, double precio, int cantidades_disponibles, int id_estado_producto, int id_imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidades_disponibles = cantidades_disponibles;
        this.id_estado_producto = id_estado_producto;
        this.id_imagen = id_imagen;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getCantidades_disponibles() { return cantidades_disponibles; }
    public void setCantidades_disponibles(int cantidades_disponibles) { this.cantidades_disponibles = cantidades_disponibles; }

    public int getId_estado_producto() { return id_estado_producto; }
    public void setId_estado_producto(int id_estado_producto) { this.id_estado_producto = id_estado_producto; }

    public int getId_imagen() { return id_imagen; }
    public void setId_imagen(int id_imagen) { this.id_imagen = id_imagen; }
}
