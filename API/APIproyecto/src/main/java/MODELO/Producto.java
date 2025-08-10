package MODELO;

public class Producto {
    // atributos que representan las propiedades de un producto
    private int id; // identificador único del producto
    private String nombre; // nombre del producto
    private String descripcion; // descripción del producto
    private double precio; // precio unitario del producto
    private int cantidades_disponibles; // cantidad disponible en inventario
    private int id_estado_producto; // identificador del estado del producto (activo, inactivo, etc.)
    private int id_imagen; // identificador de la imagen asociada al producto

    public Producto() {} // constructor vacío para crear un objeto sin inicializar atributos

    // constructor con parámetros para inicializar los atributos al crear el objeto
    public Producto(int id, String nombre, String descripcion, double precio, int cantidades_disponibles, int id_estado_producto, int id_imagen) {
        this.id = id; // asigna el id recibido al atributo id
        this.nombre = nombre; // asigna el nombre recibido al atributo nombre
        this.descripcion = descripcion; // asigna la descripción recibida al atributo descripcion
        this.precio = precio; // asigna el precio recibido al atributo precio
        this.cantidades_disponibles = cantidades_disponibles; // asigna la cantidad disponible recibida
        this.id_estado_producto = id_estado_producto; // asigna el id del estado del producto
        this.id_imagen = id_imagen; // asigna el id de la imagen asociada
    }

    // getter para obtener el id del producto
    public int getId() {
        return id;
    }

    // setter para asignar un nuevo id al producto
    public void setId(int id) {
        this.id = id;
    }

    // getter para obtener el nombre del producto
    public String getNombre() {
        return nombre;
    }

    // setter para asignar un nuevo nombre al producto
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // getter para obtener la descripción del producto
    public String getDescripcion() {
        return descripcion;
    }

    // setter para asignar una nueva descripción al producto
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // getter para obtener el precio del producto
    public double getPrecio() {
        return precio;
    }

    // setter para asignar un nuevo precio al producto
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // getter para obtener la cantidad disponible del producto
    public int getCantidades_disponibles() {
        return cantidades_disponibles;
    }

    // setter para asignar una nueva cantidad disponible al producto
    public void setCantidades_disponibles(int cantidades_disponibles) {
        this.cantidades_disponibles = cantidades_disponibles;
    }

    // getter para obtener el id del estado del producto
    public int getId_estado_producto() {
        return id_estado_producto;
    }

    // setter para asignar un nuevo id al estado del producto
    public void setId_estado_producto(int id_estado_producto) {
        this.id_estado_producto = id_estado_producto;
    }

    // getter para obtener el id de la imagen asociada al producto
    public int getId_imagen() {
        return id_imagen;
    }

    // setter para asignar un nuevo id de imagen al producto
    public void setId_imagen(int id_imagen) {
        this.id_imagen = id_imagen;
    }
}
