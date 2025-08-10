package MODELO;

public class Rol {
    
    // atributo para almacenar el identificador único del rol
    private int id;
    
    // atributo para almacenar el nombre o tipo del rol
    private String rol;
    
    // constructor vacío para crear un objeto sin inicializar atributos
    public Rol(){}
    
    // constructor con parámetros para inicializar los atributos al crear el objeto
    public Rol(int id, String rol) {
        this.id = id; // asigna el id recibido al atributo id
        this.rol = rol; // asigna el nombre del rol recibido al atributo rol
    }
    
    // getter para obtener el id del rol
    public int getId() {
        return id;
    }
    
    // setter para asignar un nuevo id al rol
    public void setId(int id) {
        this.id = id;
    }
    
    // getter para obtener el nombre del rol
    public String getRol() {
        return rol;
    }
    
    // setter para asignar un nuevo nombre al rol
    public void setRol(String rol) {
        this.rol = rol;
    }
}
