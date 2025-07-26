
package MODELO;


public class Imagen {
    //se definenen los atributos de la clase 
    private int id;
    private String ruta;
    
    //constructor vacio
    public Imagen() {}

    // Constructor con parámetros para inicializar directamente los atributos.
    public Imagen(int id, String ruta) {
        this.id = id;
        this.ruta = ruta;
    }
    
    //getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRuta() { return ruta; }
    public void setRuta(String ruta) { this.ruta = ruta; }
}
