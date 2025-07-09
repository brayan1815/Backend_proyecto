
package CONTROLADOR;

import MODELO.Reserva;
import MODELO.ReservasDAO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/reservas")
public class ReeservasController {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearReserva(Reserva reserva) {
        ReservasDAO dao = new ReservasDAO();
        boolean creada = dao.post(reserva);

        if (creada) {
            return Response.status(Response.Status.CREATED).entity("{\"mensaje\":\"Reserva creada correctamente\"}").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"mensaje\":\"Error al crear reserva\"}").build();
        }
    }
}
