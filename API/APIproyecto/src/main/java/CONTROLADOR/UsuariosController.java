package CONTROLADOR;

import MODELO.PasswordUtils;
import MODELO.PermisosDAO;
import MODELO.PermisosUtils;
import MODELO.Secured;
import MODELO.TokenUtils;
import MODELO.Usuario;
import MODELO.UsuarioDTO;
import MODELO.UsuariosDAO;
import MODELO.UsuariosServices;
import MODELO.ValidadorUsuario;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/usuarios")
public class UsuariosController {
    UsuariosDAO dao = new UsuariosDAO();//se instancia el objeto de la clase UsuariosDAO
    PermisosUtils permUtils=new PermisosUtils();
    
    @Secured
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarios(@Context HttpHeaders headers) {
        //se crea el método que va a responder a las solicitudes GET del endpoint /usuarios
        //este método retorna todos los usuarios de la base de datos
        
        String authHeader = headers.getHeaderString("Authorization");//se obtienen los headers
        String correo=TokenUtils.getCorreoFromToken(authHeader);
        
        if(permUtils.tienePermiso(correo, "usuarios.index")){
           return Response.ok(dao.get()).build();//se llama al método get de la clase UsuariosDAO para obtener la lista de usuarios    
        }
        return Response.status(Response.Status.FORBIDDEN) // 403 - No tiene permiso
               .entity("{\"error\":\"No tiene permiso para listar los usuarios\"}") // mensaje en JSON
               .build();
        
    }
    
    @Secured
    @GET
    @Path("/con-rol")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuariosConCargo(@Context HttpHeaders headers) {
        //se crea el método que va a responder a las solicitudes GET del endpoint /usuarios/con-rol
        
        String authHeader = headers.getHeaderString("Authorization");//se obtienen los headers
        String correo=TokenUtils.getCorreoFromToken(authHeader);
        
        List<UsuarioDTO> usuarios = dao.getConRol();//se declara una lista de tipo UsuarioDTO en la que se almacena el retorno
                                                    //del método getConRol de la clase UsuariosDAO
                                                    
        if(permUtils.tienePermiso(correo, "usuarios.index")){
            return Response.ok(usuarios).build();//se retorna una respuesta OK con la lista de usuarios    
        }
        return Response.status(Response.Status.FORBIDDEN) // 403 - No tiene permiso
               .entity("{\"error\":\"No tiene permiso para listar los usuarios\"}") // mensaje en JSON
               .build();
        
    }
    
    @Secured
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarioPorId(@PathParam("id") int id) {
        //se crea el método que va a responder a las solicitudes GET del endpoint /usuarios/{id}
        //este método recibe como parámetro en la URL el id del usuario a buscar
        Usuario usuario = dao.getById(id);//se declara la variable usuario en la que se almacena el retorno del método getById
                                          //de la clase UsuariosDAO
        if (usuario != null) {//si el usuario existe
            return Response.ok(usuario).build();//se retorna una respuesta OK junto con el usuario
        } else {//si el usuario no existe
            return Response.status(Response.Status.NOT_FOUND)//se retorna un estado NOT_FOUND
                           .entity("Usuario no encontrado")//con un mensaje de error
                           .build();
        }   
    }
    
    @Secured
    @GET
    @Path("/correo/{correo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarioPorCorreo(@PathParam("correo") String correo) {
        //se crea el método que va a responder a las solicitudes GET del endpoint /usuarios/correo/{correo}
        UsuariosDAO dao = new UsuariosDAO();//se instancia un objeto de la clase UsuariosDAO
        Usuario usuario = dao.getByCorreo(correo);//se declara la variable usuario en la que se almacena el retorno
                                                  //del método getByCorreo de la clase UsuariosDAO
        if (usuario != null) {//si el usuario existe
            return Response.ok(usuario).build();//se retorna una respuesta OK con el usuario
        } else {//si el usuario no existe
            return Response.status(Response.Status.NOT_FOUND)//se retorna un estado NOT_FOUND
                           .entity("{\"error\":\"Usuario no encontrado\"}")//con un mensaje de error en formato JSON
                           .build();
        }
    }
    
    @Secured
    @GET
    @Path("/documento/{documento}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarioPorDocumento(@PathParam("documento") long documento) {
        //se crea el método que va a responder a las solicitudes GET del endpoint /usuarios/documento/{documento}
        UsuariosDAO dao = new UsuariosDAO();//se instancia un objeto de la clase UsuariosDAO
        Usuario usuario = dao.getByDocumento(documento);//se declara la variable usuario en la que se almacena el retorno
                                                        //del método getByDocumento de la clase UsuariosDAO
        if (usuario != null) {//si el usuario existe
            return Response.ok(usuario).build();//se retorna una respuesta OK con el usuario
        } else {//si el usuario no existe
            return Response.status(Response.Status.NOT_FOUND)//se retorna un estado NOT_FOUND
                           .entity("{\"error\":\"Usuario no encontrado\"}")//con un mensaje de error en formato JSON
                           .build();
        }
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Usuario usuario){
        //se crea el método que va a responder a las solicitudes POST del endpoint /usuarios/login
        //este método valida las credenciales del usuario para iniciar sesión
        UsuariosDAO dao = new UsuariosDAO();//se instancia un objeto de la clase UsuariosDAO
        PermisosDAO permisosDao=new PermisosDAO();
        UsuariosServices ser = new UsuariosServices();//se instancia un objeto de la clase UsuariosServices
        try {
            if (ser.login(usuario)) {//si el login es exitoso
                Usuario user=dao.getByCorreo(usuario.getCorreo());
                String token = TokenUtils.generarToken(usuario.getCorreo());//se genera el token usando el correo del usuario
                String refreshToken=TokenUtils.generarRefreshToken(usuario.getCorreo());//se genera el toekn de refresco
                List<String> permisos=permisosDao.getPermisosByRol(user.getId_rol());
                String json = "{"
                        + "\"token\":\"" + token + "\","
                        + "\"refreshToken\":\"" + refreshToken + "\","
                        + "\"permisos\":\"" + permisos + "\""
                        + "}";
                
                return Response.ok(json).build();//se retorna el token en formato JSON
            } else {//si las credenciales no son correctas
                Usuario user = dao.getByCorreo(usuario.getCorreo());//se busca el usuario por su correo
                if (user != null) {//si el usuario existe
                    if (user.getId_estado() != 1) {//si el usuario está deshabilitado
                        return Response.status(Response.Status.UNAUTHORIZED)//se retorna un estado UNAUTHORIZED
                                       .entity("{\"error\":\"Su cuenta se encuentra deshabilitada, si desea recuperarla pongase en contacto con un administrador\"}")
                                       .build();
                    }
                }
                return Response.status(Response.Status.UNAUTHORIZED)//se retorna un estado UNAUTHORIZED
                               .entity("{\"error\":\"Credenciales incorrectas\"}")//con un mensaje de error
                               .build();
            }
        } catch (Exception e) {//en caso de que ocurra un error en el servidor
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)//se retorna un estado INTERNAL_SERVER_ERROR
                           .entity("{\"error\":\"Ocurrió un error en el servidor\"}")//con un mensaje de error
                           .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearUsuario(Usuario usuario){
        //se crea el método que va a responder a las solicitudes POST del endpoint /usuarios
        //este método crea un nuevo usuario en la base de datos
        String error = ValidadorUsuario.validarUsuario(usuario, dao, false);//se valida el usuario antes de crearlo
        if (error != null) {//si hay errores en la validación
            return Response.status(Response.Status.BAD_REQUEST)//se retorna un estado BAD_REQUEST
                           .entity("{\"error\": \"" + error + "\"}")//con el mensaje de error
                           .build();
        }
        usuario.setContrasenia(PasswordUtils.hashPassword(usuario.getContrasenia()));//se encripta la contraseña del usuario
        boolean creado = dao.post(usuario);//se llama al método post de la clase UsuariosDAO para crear el usuario

        if (creado) {//si el usuario se crea correctamente
            return Response.status(Response.Status.CREATED)//se retorna un estado CREATED
                           .entity("{\"mensaje\": \"Usuario creado correctamente\"}")//con un mensaje de éxito
                           .build();
        } else {//si el usuario no se pudo crear
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)//se retorna un estado INTERNAL_SERVER_ERROR
                           .entity("{\"error\": \"No se pudo crear el usuario\"}")//con un mensaje de error
                           .build();
        }
    }
    
    @Secured
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarUsuario(@PathParam("id") int id, Usuario usuario) {
        //se crea el método que va a responder a las solicitudes PUT del endpoint /usuarios/{id}
        //este método actualiza los datos de un usuario en la base de datos
        usuario.setId(id);//se asigna el id recibido por parámetro al objeto usuario
        String error = ValidadorUsuario.validarUsuario(usuario, dao, true);//se valida el usuario antes de actualizarlo
        if (error != null) {//si hay errores en la validación
            return Response.status(Response.Status.BAD_REQUEST)//se retorna un estado BAD_REQUEST
                           .entity("{\"error\": \"" + error + "\"}")//con el mensaje de error
                           .build();
        }
        boolean actualizado = dao.put(id, usuario);//se llama al método put de la clase UsuariosDAO para actualizar el usuario
        if (actualizado) {//si el usuario se actualiza correctamente
            return Response.status(Response.Status.CREATED)//se retorna un estado CREATED
                           .entity("{\"mensaje\": \"Usuario actualizado correctamente\"}")//con un mensaje de éxito
                           .build();
        } else {//si el usuario no se pudo actualizar
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)//se retorna un estado INTERNAL_SERVER_ERROR
                           .entity("{\"error\": \"No se pudo actualizar el usuario\"}")//con un mensaje de error
                           .build();
        }
    }
    
    @Secured
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarUsuario(@PathParam("id") int id) {
        //se crea el método que va a responder a las solicitudes DELETE del endpoint /usuarios/{id}
        //este método elimina un usuario de la base de datos
        Usuario usuario = dao.getById(id);//se busca el usuario por su id
        if (usuario == null) {//si el usuario no existe
            return Response.status(Response.Status.NOT_FOUND)//se retorna un estado NOT_FOUND
                           .entity("{\"error\":\"Usuario no encontrado\"}")//con un mensaje de error
                           .build();
        }
        usuario.setId_estado(2);//se cambia el estado del usuario a 2 (eliminado)
        boolean eliminado = dao.put(id, usuario);//se actualiza el usuario con el nuevo estado
        if (eliminado) {//si el usuario se elimina correctamente
            return Response.ok("{\"mensaje\":\"Usuario eliminado correctamente\"}").build();//se retorna una respuesta OK
        } else {//si el usuario no se pudo eliminar
            return Response.status(Response.Status.NOT_FOUND)//se retorna un estado NOT_FOUND
                           .entity("{\"error\":\"Ocurrió un error al intentar eliminar el usuario\"}")//con un mensaje de error
                           .build();
        }
    }
}
