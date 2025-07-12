/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import MODELO.Usuario;
import MODELO.UsuariosDAO;
import MODELO.UsuariosServices;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/usuarios")
public class UsuariosController {
    
    UsuariosDAO dao=new UsuariosDAO();//se intancia el objeto de la clase UsuariosDAO
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //se crea el metodo que se encargara de obtener todos los usuarios de la base de datos
    public List<Usuario> obtenerUsuarios() {
        return dao.get();//se llamada al metodo get de usuarisosDAO para retornar la lista de los usuarios
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    //se crea el metodo que se encargara de obtener un usuario por su id en la base de datos
    public Response obtenerUsuarioPorId(@PathParam("id") int id) {
        Usuario usuario = dao.getById(id);//se llama el metodo getById de usuarios DAO para obtener el usuario
        if (usuario != null) 
        {
            return Response.ok(usuario).build();//si el usuario existe se retorna un codigo 200 y se retorna el usuario
        } else 
        {
            //si el usuario no se encuentra se retorna un codigo 404 con un mensaje
            return Response.status(Response.Status.NOT_FOUND).entity("Usuario no encontrado").build();
        }   
    }
    
    @GET
    @Path("/correo/{correo}")
    @Produces(MediaType.APPLICATION_JSON)
    //se crea el metodo que se encargara de obtener el usuario por su correo en la base de datos
    public Response obtenerUsuarioPorCorreo(@PathParam("correo") String correo) {
        UsuariosDAO dao = new UsuariosDAO();//se instancia un objeto de la clase usuariosDAO
        Usuario usuario = dao.getByCorreo(correo);//se llama el metodo getByCorreo de la clase usuariosDAO para
                                                    //obtener el usuario

        if (usuario != null) {
            //si el usuario existe se retorna un codigo 200 y el objeto del usuario en formato JSON
            return Response.ok(usuario).build();
        } else {
            //si el usuario no se encuentra se retorna un codigo 404 con un mensaje en formato JSON
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Usuario no encontrado\"}")
                    .build();
        }
    }
    
    @GET
    @Path("/documento/{documento}")
    @Produces(MediaType.APPLICATION_JSON)
    //se crea el metodo que se encargara de obtener el usuario por su documento
    public Response obtenerUsuarioPorCorreo(@PathParam("documento") long documento) {
        UsuariosDAO dao = new UsuariosDAO();//se instancia un objeto de la clase usuariosDAO
        Usuario usuario = dao.getByDocumento(documento);//se llama al metodo  getByDocumento de usuariod DAO para
                                                        //obtener el usuariuo

        if (usuario != null) {
             //si el usuario existe se retorna un codigo 200 con el objeto usuario el formato JSON
            return Response.ok(usuario).build();
        } else {
            //si el usuario no se encuentra se devuelve un codigo 404 con un mensaje de error el formato JSON
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Usuario no encontrado\"}")
                    .build();
        }
    }
    

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //se crea el metodo que se encargara de validar las credenciales del usuario para iniciar sesion
    public Response login(Usuario usuario){
        
        UsuariosServices ser=new UsuariosServices();//se isntancia el objeto de la clase UsuariosServices
        try{
            if(ser.login(usuario)){
                //si el metodo login de la clase UsuariosServices retorna verdadero
                //se retorna una respuesta exitosa
                return Response.ok("El usuarios puede ingresar").build();
            }else{
                //si el login no fue exitosa se arroja una excepcion indicando que el usuario no puede ingresar
                throw new IllegalArgumentException("El usuario no puede ingresar");
            }
        }catch(Exception e){
            //si ocurtre un error durante el proceso se captura
            return Response.status(500).entity("Ocurrio un error al buscar la informacion").build();
            //se retorna una respuesta con codigo 500 y un mensaje infromativo
        }
    }

    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    //se crea el metodo que se encargara de crear un nuevo usuario en la base de datos 
    public Response crearUsuario(Usuario usuario){
        
        boolean creado = dao.post(usuario);//se lama al metodo post de usuariosDAO para insertar el nuevo usuario

        if (creado) {
            //si el usuario se crea correctamente se devuelve un codigo 201 con un mensaje
            return Response.status(Response.Status.CREATED).entity("Usuario creado correctamente").build();
        } else {
            //si no se puede crear el usuario se retorna un codigo 500 con un mensaje
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo crear el usuario").build();
        }
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    //se crea el metodo que se encargra de actualizar un usuario en la base de datos
    public Response actualizarUsuario(@PathParam("id") int id,Usuario usuario)
        {
            boolean actualizado = dao.put(id, usuario);//se llama el metod put de usuariosDAO para actualizar el
                                                      //usuario
            if (actualizado) 
            {
                //si el usuario se actualizo correctamente se devuleve un codigo 200 con un mensaje
                return Response.ok("Usuario actualizado correctamente").build();
            } 
            else 
            {
                //si el usuario no se encuentra o no hay cambios se retorna un codigo 404 con un mensaje
                return Response.status(Response.Status.NOT_FOUND).entity("Usuario no encontrado o sin cambios").build();
            }   
    }
    
    @DELETE
    @Path("/{id}")
    //se crea el metodo que se encargara de eliminar un usuario de la base de datos
    public Response eliminarUsuario(@PathParam("id") int id) {
        boolean eliminado = dao.delete(id);//se llama al metodo delete de la clase usuariosDAO para eliminar el usuario
    
        if (eliminado) {
            //si el usuario se elimina se retorna un codigo 200 con un mensaje
            return Response.ok("Usuario eliminado correctamente").build();
        } else {
            //si el usuario no se encuentra se retorna un codigo 400 con un mensaje
            return Response.status(Response.Status.NOT_FOUND)
                       .entity("Usuario no encontrado").build();
        }
}
}
