package CONTROLADOR;

import MODELO.PermisosUtils;
import MODELO.Secured;
import MODELO.TienePermiso;
import MODELO.Tipo;
import MODELO.TiposDAO;
import MODELO.TokenUtils;
import MODELO.ValidadorTipo;
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

@Secured
@Path("/tipos") //ruta base para acceder a los tipos
public class TiposController {
    @TienePermiso("tipos.index")
    @GET
    @Produces(MediaType.APPLICATION_JSON) //devuelve respuesta en formato JSON
    public Response obtenerTipos() { 
        //crea una instancia del DAO para acceder a la base de datos
        TiposDAO dao = new TiposDAO();
        //obtiene todos los tipos registrados
        List<Tipo> tipos = dao.getAll();
        
        //retorna la lista de tipos con código 200 OK
        return Response.ok(tipos).build();
    }
    @TienePermiso("tipos.index")
    @GET
    @Path("/{id}") //recibe el id del tipo por la url
    @Produces(MediaType.APPLICATION_JSON) //devuelve la respuesta en JSON
    public Response obtenerTipoPorId(@PathParam("id") int id) {
        
        //crea el DAO para realizar la consulta
        TiposDAO dao = new TiposDAO();
        //obtiene el tipo correspondiente al id
        Tipo tipo = dao.getById(id);

        //si encuentra el tipo, lo retorna con código 200 OK
        if (tipo != null) {
            return Response.ok(tipo).build();
        } else {
            //si no lo encuentra, retorna error 404 con mensaje
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Tipo no encontrado\"}")
                    .build();
        }
    }
    
    @TienePermiso("tipos.crear")
    @POST
    @Consumes(MediaType.APPLICATION_JSON) //recibe los datos del tipo en formato JSON
    @Produces(MediaType.APPLICATION_JSON) //responde en formato JSON
    public Response crearTipo(Tipo tipo) {
        
        //valida los datos del tipo antes de guardarlo
        String error = ValidadorTipo.validarTipo(tipo);
        if (error != null) {
            //si hay error en la validación, retorna 400 con el mensaje
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\": \"" + error + "\"}")
                           .build();
        }
        
        //crea el DAO para insertar el tipo
        TiposDAO dao = new TiposDAO();
        boolean creado = dao.post(tipo);

        //si se creó correctamente, retorna código 201 con mensaje
        if (creado) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"mensaje\": \"Tipo creado correctamente\"}").build();
        } else {
            //si no se pudo crear, retorna error 500 con mensaje
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"No se pudo crear el tipo\"}").build();
        }
    }
    
    @TienePermiso("tipos.editar")
    @PUT
    @Path("/{id}") //recibe el id por la url
    @Consumes(MediaType.APPLICATION_JSON) //recibe datos en formato JSON
    @Produces(MediaType.APPLICATION_JSON) //responde en formato JSON
    public Response actualizarTipo(@PathParam("id") int id, Tipo tipo) {
     
        //asigna el id recibido por la url al objeto tipo
        tipo.setId(id);
        
        //valida los datos antes de actualizar
        String error = ValidadorTipo.validarTipo(tipo);
        if (error != null) {
            //si hay error en la validación, retorna 400 con mensaje
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\": \"" + error + "\"}")
                           .build();
        }
        
        //crea el DAO para actualizar el tipo
        TiposDAO dao = new TiposDAO();
        boolean actualizado = dao.put(tipo);

        //si se actualizó correctamente, retorna mensaje 200 OK
        if (actualizado) {
            return Response.ok("{\"mensaje\": \"Tipo actualizado correctamente\"}").build();
        } else {
            //si no se encontró o no se actualizó, retorna 404
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"No se pudo actualizar el tipo\"}")
                    .build();
        }
    }
    
    @TienePermiso("tipos.eliminar")
    @DELETE
    @Path("/{id}") //recibe el id por la url
    @Produces(MediaType.APPLICATION_JSON) //responde en formato JSON
    public Response eliminarTipo(@PathParam("id") int id) {
        
        String error = ValidadorTipo.validarEliminacionTipo(id);
        if (error != null) {
            //si hay error en la validación, retorna 400 con el mensaje
            return Response.status(Response.Status.CONFLICT)
                           .entity("{\"error\": \"" + error + "\"}")
                           .build();
        }
        
        
        //crea el DAO para acceder al tipo
        TiposDAO dao = new TiposDAO();
        
        //obtiene el tipo por su id
        Tipo tipo = dao.getById(id);
        //cambia su estado a 2 (eliminado)
        tipo.set_id_estado_tipo(2);
        
        //actualiza el registro con el nuevo estado
        boolean eliminado = dao.put(tipo);

        //si se eliminó correctamente, retorna mensaje 200 OK
        if (eliminado) {
            return Response.ok("{\"mensaje\": \"Tipo eliminado correctamente\"}").build();
        } else {
            //si no se encontró o no se eliminó, retorna 404
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"No se pudo eliminar el tipo\"}")
                    .build();
        }
    }
}
