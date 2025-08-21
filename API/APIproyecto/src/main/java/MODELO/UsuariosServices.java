package MODELO;

public class UsuariosServices {
    
    // Instancia de la clase DAO para acceder a la base de datos
    UsuariosDAO usuDao = new UsuariosDAO();
    
    // Método para validar el login de un usuario
    public boolean login(Usuario usuario) {
        String correo = usuario.getCorreo();         // Obtener el correo ingresado
        String contrasenia = usuario.getContrasenia(); // Obtener la contraseña ingresada
        
        // Buscar en la base de datos el usuario con ese correo
        Usuario usuBD = usuDao.getByCorreo(correo);
        
        if (usuBD != null) { // Si el usuario existe en la base de datos
            if (usuBD.getId_estado() != 1) return false; // Si el usuario no está activo, rechazar login
            
            // Verificar que la contraseña ingresada coincide con la almacenada (hasheada) usando BCrypt
            return PasswordUtils.checkPassword(contrasenia, usuBD.getContrasenia());
        }
        
        // Si no se encontró el usuario, retornar false
        return false;                   
    }
    
    

}
