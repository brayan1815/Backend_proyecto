package MODELO;

public class Tipo {
    // atributos que representan las propiedades de un tipo
    private int id; // identificador único del tipo
    private String tipo; // nombre o descripción del tipo
    double precio_hora; // precio por hora asociado al tipo
    public int id_estado_tipo; // identificador del estado del tipo (activo, inactivo)

    public Tipo() {} // constructor vacío para crear un objeto sin inicializar atributos

    // constructor con parámetros para inicializar los atributos al crear el objeto
    public Tipo(int id, String tipo, double precio_hora, int id_estado_tipo) {
        this.id = id; // asigna el id recibido al atributo id
        this.tipo = tipo; // asigna el nombre recibido al atributo tipo
        this.precio_hora = precio_hora; // asigna el precio por hora recibido
        this.id_estado_tipo = id_estado_tipo; // asigna el id del estado del tipo
    }

    // getter para obtener el id del tipo
    public int getId() {
        return id;
    }

    // setter para asignar un nuevo id al tipo
    public void setId(int id) {
        this.id = id;
    }

    // getter para obtener el nombre o descripción del tipo
    public String getTipo() {
        return tipo;
    }

    // setter para asignar un nuevo nombre o descripción al tipo
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // getter para obtener el precio por hora del tipo
    public double getPrecio_hora() {
        return precio_hora;
    }

    // setter para asignar un nuevo precio por hora al tipo
    public void setPrecio_hora(double precio_hora) {
        this.precio_hora = precio_hora;
    }

    // getter para obtener el id del estado del tipo
    public int getId_estado_tipo() {
        return id_estado_tipo;
    }

    // setter para asignar un nuevo id al estado del tipo
    public void set_id_estado_tipo(int id_estado_tipo) {
        this.id_estado_tipo = id_estado_tipo;
    }
}
