
package MODELO;


public class Imagen {
    
    private int id;
    private String ruta;
    
    public Imagen() {}

    public Imagen(int id, String ruta) {
        this.id = id;
        this.ruta = ruta;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRuta() { return ruta; }
    public void setRuta(String ruta) { this.ruta = ruta; }
}
