
package MODELO;


public class Consola {
    private int id;
    private String nombre;
    private String descripcion;
    private int id_tipo;
    private int id_estado;
    private int id_imagen;

    // Constructor vacío
    public Consola() {}

    // Constructor con todos los atributos
    public Consola(int id, String nombre, String descripcion, int id_tipo, int id_estado, int id_imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_tipo = id_tipo;
        this.id_estado = id_estado;
        this.id_imagen = id_imagen;
    }

    // Getters y Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String decripcion) { this.descripcion = decripcion; }

    public int getId_tipo() { return id_tipo; }
    public void setId_tipo(int id_tipo) { this.id_tipo = id_tipo; }

    public int getId_estado() { return id_estado; }
    public void setId_estado(int id_estado) { this.id_estado = id_estado; }

    public int getId_imagen() { return id_imagen; }
    public void setId_imagen(int id_imagen) { this.id_imagen = id_imagen; }
}
