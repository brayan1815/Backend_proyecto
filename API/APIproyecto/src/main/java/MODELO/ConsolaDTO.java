
package MODELO;

public class ConsolaDTO {
    //atributos que representan los datos de una consola
    private int id;
    private String nombre;
    private String descripcion;
    private double precioHora; 
    private int idEstado;
    private int idImagen;
    

    public ConsolaDTO() {
        //constructor vacio, por si acaso se necesita crear un objeto sin valores de entrada
    }

    public ConsolaDTO(int id, String nombre, String descripcion, double precioHora, int idEstado, int idImagen) {
        //constructor con todos los ateibutos, sirve para crear el objeto ya con todos sus datos
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioHora = precioHora;
        this.idEstado = idEstado;
        this.idImagen = idImagen;
    }


    //Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(double precioHora) {
        this.precioHora = precioHora;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }
}
