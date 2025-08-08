
package CONTROLADOR;

import MODELO.FacturaDTO;
import MODELO.Reserva;
import MODELO.ReservaDTO;
import MODELO.ReservasDAO;
import MODELO.ReservasServices;
import MODELO.ValidadorReserva;
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
        ReservasDAO dao=new ReservasDAO();
        List<ReservaDTO> reservas = dao.getAllConInfo();
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
        
        String error = ValidadorReserva.validar(reserva);

        if (error != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + error + "\"}").build();
        }
        
        ReservasDAO dao = new ReservasDAO();
        boolean creada = dao.post(reserva);

        if (creada) {
            return Response.status(Response.Status.CREATED).entity("{\"mensaje\":\"Reserva creada correctamente\"}").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error al crear reserva\"}").build();
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
    
    @GET
    @Path("/usuario/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReservasPorUsuario(@PathParam("id") int idUsuario) {
        List<ReservaDTO> reservas = reservaService.obtenerReservasPorUsuario(idUsuario);

        if (reservas != null && !reservas.isEmpty()) {
            return Response.ok(reservas).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"mensaje\": \"No se encontraron reservas para este usuario\"}")
                    .build();
        }
    }
    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarReserva(@PathParam("id") int id) {
        ReservasDAO dao = new ReservasDAO();
        Reserva reserva = dao.getById(id);

        if (reserva == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Reserva no encontrada\"}").build();
        }

        String validacion = ValidadorReserva.puedeEliminar(reserva);

        if (validacion != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"" + validacion + "\"}").build();
        }
        
        boolean eliminada = dao.eliminarReserva(id);

        if (eliminada) {
            return Response.ok("{\"mensaje\":\"Se cancelo la reserva correctamente\"}").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"No se pudo cancelar la reserva\"}").build();
        }
    }
    
}
