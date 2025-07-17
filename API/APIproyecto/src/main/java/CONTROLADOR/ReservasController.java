
package CONTROLADOR;

import MODELO.Reserva;
import MODELO.ReservaDTO;
import MODELO.ReservasDAO;
import MODELO.ReservasServices;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/reservas")
public class ReservasController {
    
    private ReservasServices reservaService = new ReservasServices();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReservas() {
        ReservasDAO dao = new ReservasDAO();
        List<Reserva> reservas = dao.getAll();
        return Response.ok(reservas).build();
    }
    
    @GET
    @Path("/detalle")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReservasConDetalle() {
        List<ReservaDTO> reservas = reservaService.obtenerReservasConDatos();
        return Response.ok(reservas).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReservaPorId(@PathParam("id") int id) {
        ReservasDAO dao = new ReservasDAO();
        Reserva reserva = dao.getById(id);

        if (reserva != null) {
            return Response.ok(reserva).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Reserva no encontrada\"}")
                    .build();
        }
    }

    
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
    
    @GET
    @Path("/consola/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReservasPorConsola(@PathParam("id") int idConsola) {
        ReservasDAO dao = new ReservasDAO();
        List<Reserva> reservas = dao.getByIdConsola(idConsola);

        if (reservas != null && !reservas.isEmpty()) {
            return Response.ok(reservas).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"mensaje\": \"No se encontraron reservas para esta consola\"}")
                    .build();
        }
    }
    
    @GET
    @Path("/estado-actualizado")
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarEstadoDesdeFrontend() {
        List<ReservaDTO> actualizadas = reservaService.actualizarEstadoReserva();
        return Response.ok(actualizadas).build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarReserva(@PathParam("id") int id, Reserva reserva) {
        reserva.setId(id); // Establecer el ID recibido por la URL
        ReservasDAO dao = new ReservasDAO();
        boolean actualizado = dao.put(reserva);

        if (actualizado) {
            return Response.ok("{\"mensaje\": \"Reserva actualizada correctamente\"}").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"No se pudo actualizar la reserva\"}")
                    .build();
        }
    }


}
