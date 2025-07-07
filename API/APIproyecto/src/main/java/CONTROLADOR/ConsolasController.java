
package CONTROLADOR;

import MODELO.Consola;
import MODELO.ConsolasDAO;
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

@Path("/consolas")
public class ConsolasController {
     @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerConsolas() {
        ConsolasDAO dao = new ConsolasDAO();
        List<Consola> consolas = dao.getAll();
        return Response.ok(consolas).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerConsolaPorId(@PathParam("id") int id) {
        ConsolasDAO dao = new ConsolasDAO();
        Consola consola = dao.getById(id);

        if (consola != null) {
            return Response.ok(consola).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"error\":\"Consola no encontrada\"}")
                           .build();
        }
    }

    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearConsola(Consola consola) {
        ConsolasDAO dao = new ConsolasDAO();
        boolean creada = dao.post(consola);

        if (creada) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"mensaje\":\"Consola creada correctamente\"}")
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"mensaje\":\"Error al crear consola\"}")
                    .build();
        }
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarConsola(@PathParam("id") int id, Consola consola) {
        consola.setId(id); // Se asegura de usar el ID de la URL
        ConsolasDAO dao = new ConsolasDAO();

        boolean actualizado = dao.put(consola);

        if (actualizado) {
            return Response.ok("{\"mensaje\":\"Consola actualizada correctamente\"}").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"error\":\"No se pudo actualizar la consola\"}")
                           .build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarConsola(@PathParam("id") int id) {
        ConsolasDAO dao = new ConsolasDAO();
        boolean eliminado = dao.Delete(id);

        if (eliminado) {
            return Response.ok("{\"mensaje\":\"Consola eliminada correctamente\"}").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"error\":\"Consola no encontrada o no se pudo eliminar\"}")
                           .build();
        }
    } 
}
