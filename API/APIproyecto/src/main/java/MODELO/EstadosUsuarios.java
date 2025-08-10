package MODELO;

public class EstadosUsuarios {
    // Atributo que almacena el identificador del estado de usuario
    private int id;
    // Atributo que almacena el nombre del estado de usuario
    private String estado;
    
    // Constructor vacío
    public EstadosUsuarios(){}
    
    // Constructor con parámetros
    public EstadosUsuarios(int id, String estado){
        this.id = id;
        this.estado = estado;
    }
    
    // Método que retorna el identificador del estado de usuario
    public int getId(){ return id; }
    // Método que asigna un valor al identificador del estado de usuario
    public void setId(int id){ this.id = id; }
    
    // Método que retorna el nombre o descripción del estado de usuario
    public String getEstado(){ return estado; }
    // Método que asigna un valor al nombre o descripción del estado de usuario
    public void setEstado(String estado){ this.estado = estado; }
}
