
package CONTROLADOR;

import MODELO.Consola;
import MODELO.ConsolasDAO;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
}
