
package MODELO;

import java.util.ArrayList;
import java.util.List;

public class UsuariosServices {
    
    //se instancian las clases DAO
    UsuariosDAO usuDao=new UsuariosDAO();
    RolesDAO rolDao=new RolesDAO();
    
    //se crea el metodo login
    public boolean login(Usuario usuario){
        String correo = usuario.getCorreo();//se obtiene el correo del usuario
        String contrasenia = usuario.getContrasenia();//se obtiene la contraseña

        Usuario usuBD = usuDao.getByCorreo(correo);//se obtiene el usuario de la base de datos por correo
        
        
        if (usuBD != null) {
            if(usuBD.getId_estado()!=1) return false;
            //si el usuario existe
            // Comparar usando BCrypt
            return PasswordUtils.checkPassword(contrasenia, usuBD.getContrasenia());
        }
        
        return false;//si el usuario no existe se retorna false                   
    }
    
     public List<UsuarioDTO> obtenerUsuariosConRol() {
         //se crea el metodo para obtener los usuarioc con su rol
        List<Usuario> usuarios = usuDao.get();//se obtienen los usuarios
        List<UsuarioDTO> listaFinal = new ArrayList<>();//se crea la lista que almacenara los usuarios

        for (Usuario u : usuarios) {//se recorren los usuarios
            Rol r = rolDao.getById(u.getId_rol());//se obtiene el rol asociado al usuario
            UsuarioDTO dto = new UsuarioDTO(//se construye el objeto DTO y se agrega a la lista
                u.getId(),
                u.getDocumento(),
                u.getNombre(),
                u.getTelefono(),
                u.getCorreo(), 
                u.getContrasenia(),
                r.getRol(),
                u.getId_estado()
            );
            listaFinal.add(dto);
        }

        return listaFinal;//se retorna la lista
    }
}
