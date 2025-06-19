/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import MODELO.Usuario;
import MODELO.UsuariosDAO;
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
    
    UsuariosDAO dao=new UsuariosDAO();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> obtenerUsuarios() {
        return dao.get();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarioPorId(@PathParam("id") int id) {
        Usuario usuario = dao.getById(id);
        if (usuario != null) 
        {
            return Response.ok(usuario).build();
        } else 
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuario no encontrado").build();
        }   
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearUsuario(Usuario usuario){
        boolean creado = dao.post(usuario);

        if (creado) {
            return Response.status(Response.Status.CREATED).entity("Usuario creado correctamente").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo crear el usuario").build();
        }
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarUsuario(
        @PathParam("id") int id,
        Usuario usuario)
        {
            boolean actualizado = dao.put(id, usuario);
            if (actualizado) 
            {
                return Response.ok("Usuario actualizado correctamente").build();
            } 
            else 
            {
                return Response.status(Response.Status.NOT_FOUND).entity("Usuario no encontrado o sin cambios").build();
            }   
    }
    
    @DELETE
    @Path("/{id}")
    public Response eliminarUsuario(@PathParam("id") int id) {
        boolean eliminado = dao.delete(id);
    
        if (eliminado) {
            return Response.ok("Usuario eliminado correctamente").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                       .entity("Usuario no encontrado").build();
        }
}
}
