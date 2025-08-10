package MODELO;

// Clase que representa el estado asociado a un tipo específico
public class EstadoTipo {
    // Identificador único del estado
    private int id;
    // Nombre o descripción del estado
    private String estado;
    
    // Constructor vacío para crear un objeto sin valores iniciales
    public EstadoTipo(){}
    
    // Constructor que inicializa todos los atributos de la clase
    public EstadoTipo(int id, String estado){
        this.id = id;
        this.estado = estado;
    }
    
    // Devuelve el identificador único del estado
    public int getId(){ return id; }
    
    // Establece el identificador único del estado
    public void setId(int id){ this.id = id; }
    
    // Devuelve el nombre o descripción del estado
    public String getEstado(){ return estado; }
    
    // Establece el nombre o descripción del estado
    public void setEstado(String estado){ this.estado = estado; }
}
