package MODELO;

public class UsuarioDTO {
    // atributos que representan las propiedades de un usuario con rol como String
    private int id; // identificador único del usuario
    private long documento; // número de documento del usuario
    private String nombre; // nombre completo del usuario
    private long telefono; // número de teléfono del usuario
    private String correo; // correo electrónico del usuario
    private String contrasenia; // contraseña del usuario
    private String rol; // nombre del rol asignado al usuario
    private int id_estado; // identificador del estado del usuario (activo, inactivo, etc.)

    // constructor vacío para crear un objeto sin inicializar atributos
    public UsuarioDTO() {
    }

    // constructor con parámetros para inicializar los atributos al crear el objeto
    public UsuarioDTO(int id, long documento, String nombre, long telefono, String correo, String contrasenia, String rol, int id_estado) {
        this.id = id; // asigna el id recibido
        this.documento = documento; // asigna el documento recibido
        this.nombre = nombre; // asigna el nombre recibido
        this.telefono = telefono; // asigna el teléfono recibido
        this.correo = correo; // asigna el correo recibido
        this.contrasenia = contrasenia; // asigna la contraseña recibida
        this.rol = rol; // asigna el rol recibido
        this.id_estado = id_estado; // asigna el id del estado
    }

    // getter para obtener el id del usuario
    public int getId() {
        return id;
    }

    // setter para asignar un nuevo id al usuario
    public void setId(int id) {
        this.id = id;
    }

    // getter para obtener el documento del usuario
    public long getDocumento() {
        return documento;
    }

    // setter para asignar un nuevo documento al usuario
    public void setDocumento(long documento) {
        this.documento = documento;
    }

    // getter para obtener el nombre del usuario
    public String getNombre() {
        return nombre;
    }

    // setter para asignar un nuevo nombre al usuario
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // getter para obtener el teléfono del usuario
    public long getTelefono() {
        return telefono;
    }

    // setter para asignar un nuevo teléfono al usuario
    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    // getter para obtener el correo del usuario
    public String getCorreo() {
        return correo;
    }

    // setter para asignar un nuevo correo al usuario
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    // getter para obtener la contraseña del usuario
    public String getContrasenia() {
        return contrasenia;
    }

    // setter para asignar una nueva contraseña al usuario
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    // getter para obtener el rol del usuario
    public String getRol() {
        return rol;
    }

    // setter para asignar un nuevo rol al usuario
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    // getter para obtener el id del estado del usuario
    public int getId_estado() {
        return id_estado;
    }
    
    // setter para asignar un nuevo id de estado al usuario
    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }
}
