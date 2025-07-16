
package MODELO;

import java.util.ArrayList;
import java.util.List;

public class UsuariosServices {
    
    UsuariosDAO usuDao=new UsuariosDAO();
    RolesDAO rolDao=new RolesDAO();
    
    public boolean login(Usuario usuario){
        String correo=usuario.getCorreo();//se obtiene el correo del objeto que se recibe como parametro
        String contrasenia=usuario.getContrasenia();//se obtiene la contraseña del objeto que se recibe como parametro
        
        String contraseniaBD="";//se declara la variable contraseniaBD y se inicializa como varia, en esta se
                                //se almacenara la contraseña que se obtiene de la base de datos
        
        boolean Respuesta=false;//se declara la variable de tipo boleeana respues y se inicializa como falsa
                                
        UsuariosDAO dao=new UsuariosDAO();//se crea una instancia de la clase usuariosDAO
        
        Usuario usuBD=dao.getByCorreo(correo);//se hace referencia al metodo getByCorreo con ayuda del objeto
                                              //instanciado, este metodo retornara un objeto que se almacenara  
                                              //en la variable usuBD
        
        if(usuBD!=null){//en caso de que el usuario sea diferente de null
            contraseniaBD=usuBD.getContrasenia();//se reemplaza el valor de la variable contraseniaBD por 
                                                //la contraseña obtenida de la base de datos
                                                
            if(contraseniaBD.equals(contrasenia)){
                Respuesta=true; //si la contraseña obtenida de la base de datos coincide con la contraseña que
                                //se recibio en el objeto usuario la variable respuesta pasa a ser verdadera
            }
        }
        return Respuesta;//se retorna l variable respuesta                         
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
