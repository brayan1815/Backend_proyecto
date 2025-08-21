
package MODELO;

import java.util.List;

public class PermisosUtils {
   
    UsuariosDAO userDao=new UsuariosDAO();
    PermisosDAO perDao=new PermisosDAO();
    
    public boolean tienePermiso(String correo, String permisoNecesario){
        Usuario usuario=userDao.getByCorreo(correo);
        Integer id_rol=usuario.getId_rol();

        
        List<String> permisos=perDao.getPermisosByRol(id_rol);
        
        return permisos.contains(permisoNecesario);
        
    }
}
