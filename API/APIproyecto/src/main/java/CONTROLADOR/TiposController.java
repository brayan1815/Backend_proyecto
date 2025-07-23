
package CONTROLADOR;

import MODELO.Tipo;
import MODELO.TiposDAO;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tipos")
public class TiposController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTipos() {
        TiposDAO dao = new TiposDAO();
        List<Tipo> tipos = dao.getAll();
        return Response.ok(tipos).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTipoPorId(@PathParam("id") int id) {
        TiposDAO dao = new TiposDAO();
        Tipo tipo = dao.getById(id);

        if (tipo != null) {
            return Response.ok(tipo).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Tipo no encontrado\"}")
                    .build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearTipo(Tipo tipo) {
        
        String error = ValidadorTipo.validarTipo(tipo);
        if (error != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\": \"" + error + "\"}")
                           .build();
        }
        TiposDAO dao = new TiposDAO();
        boolean creado = dao.post(tipo);

        if (creado) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"mensaje\": \"Tipo creado correctamente\"}").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"No se pudo crear el tipo\"}").build();
        }
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarTipo(@PathParam("id") int id, Tipo tipo) {
        tipo.setId(id);
        
        String error = ValidadorTipo.validarTipo(tipo);
        if (error != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\": \"" + error + "\"}")
                           .build();
        }
        
        TiposDAO dao = new TiposDAO();
        boolean actualizado = dao.put(tipo);

        if (actualizado) {
            return Response.ok("{\"mensaje\": \"Tipo actualizado correctamente\"}").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"No se pudo actualizar el tipo\"}")
                    .build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarTipo(@PathParam("id") int id) {
        TiposDAO dao = new TiposDAO();
        boolean eliminado = dao.delete(id);

        if (eliminado) {
            return Response.ok("{\"mensaje\": \"Tipo eliminado correctamente\"}").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"No se pudo eliminar el tipo\"}")
                    .build();
        }
    }
}
