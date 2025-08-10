package MODELO;

public class Imagen {
    // Atributo que almacena el identificador único de la imagen
    private int id;
    // Atributo que almacena la ruta o URL donde se encuentra la imagen
    private String ruta;

    // Constructor vacío para crear un objeto Imagen sin valores iniciales
    public Imagen() {}

    // Constructor con parámetros para inicializar el id y la ruta directamente
    public Imagen(int id, String ruta) {
        this.id = id;
        this.ruta = ruta;
    }

    // Método que retorna el identificador de la imagen
    public int getId() { 
        return id; 
    }

    // Método que asigna un valor al identificador de la imagen
    public void setId(int id) { 
        this.id = id; 
    }

    // Método que retorna la ruta o ubicación de la imagen
    public String getRuta() { 
        return ruta; 
    }

    // Método que asigna un valor a la ruta o ubicación de la imagen
    public void setRuta(String ruta) { 
        this.ruta = ruta; 
    }
}
