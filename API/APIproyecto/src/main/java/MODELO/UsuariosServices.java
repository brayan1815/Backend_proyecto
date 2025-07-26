
package MODELO;

import java.util.ArrayList;
import java.util.List;

public class UsuariosServices {
    
    UsuariosDAO usuDao=new UsuariosDAO();
    RolesDAO rolDao=new RolesDAO();
    
    public boolean login(Usuario usuario){
        String correo = usuario.getCorreo();
        String contrasenia = usuario.getContrasenia();

        Usuario usuBD = usuDao.getByCorreo(correo);

        if (usuBD != null) {
            // Comparar usando BCrypt
            return PasswordUtils.checkPassword(contrasenia, usuBD.getContrasenia());
        }

        return false;                      
    }
    
     public List<UsuarioDTO> obtenerUsuariosConRol() {
        List<Usuario> usuarios = usuDao.get();
        List<UsuarioDTO> listaFinal = new ArrayList<>();

        for (Usuario u : usuarios) {
            Rol r = rolDao.getById(u.getId_rol());
            UsuarioDTO dto = new UsuarioDTO(
                u.getId(),
                u.getDocumento(),
                u.getNombre(),
                u.getTelefono(),
                u.getCorreo(), 
                u.getContrasenia(),
                r.getRol()
            );
            listaFinal.add(dto);
        }

        return listaFinal;
    }
}
