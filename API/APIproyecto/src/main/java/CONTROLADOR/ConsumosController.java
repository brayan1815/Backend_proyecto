
package CONTROLADOR;

import MODELO.Consumo;
import MODELO.ConsumosDAO;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/consumos")
public class ConsumosController {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearConsumo(Consumo consumo) {
        ConsumosDAO dao = new ConsumosDAO();
        boolean creado = dao.post(consumo);

        if (creado) {
            return Response.status(Response.Status.CREATED)
                .entity("{\"mensaje\":\"Consumo creado correctamente\"}").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\":\"Error al registrar el consumo\"}").build();
        }
    }
    
    @GET
    @Path("/reserva/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPorIdReserva(@PathParam("id") int idReserva) {
        ConsumosDAO dao = new ConsumosDAO();
        List<Consumo> lista = dao.getByIdReserva(idReserva);

        if (!lista.isEmpty()) {
            return Response.ok(lista).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"mensaje\":\"No se encontraron consumos para esta reserva\"}").build();
        }
    }
}
