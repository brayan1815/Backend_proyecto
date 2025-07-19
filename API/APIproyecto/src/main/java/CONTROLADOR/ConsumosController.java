
package CONTROLADOR;

import MODELO.Consumo;
import MODELO.ConsumoDTO;
import MODELO.ConsumosDAO;
import MODELO.ConsumosServices;
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

@Path("/consumos")
public class ConsumosController {
    
    ConsumosServices service = new ConsumosServices();
    ConsumosDAO dao = new ConsumosDAO();
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearConsumo(Consumo consumo) {
        
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
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerConsumoPorId(@PathParam("id") int id) {
        Consumo consumo = dao.getById(id);

        if (consumo != null) {
            return Response.ok(consumo).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"mensaje\": \"Consumo no encontrado\"}")
                           .build();
        }
    }
    
    @GET
    @Path("/dto/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerConsumoPorIdDto(@PathParam("id") int idConsumo) {
        try {
            ConsumoDTO dto = service.obtenerConsumoPorId(idConsumo);
            if (dto != null) {
                return Response.ok(dto).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("No se encontró el consumo con ID " + idConsumo)
                               .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error al obtener el consumo: " + e.getMessage())
                           .build();
        }
    }
    
     @GET
    @Path("/reserva/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConsumosPorReserva(@PathParam("id") int idReserva) {
        
        List<ConsumoDTO> lista = service.obtenerConsumosPorIdReserva(idReserva);
        return Response.ok(lista).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarConsumo(Consumo consumo) {
        try {
            boolean actualizado = service.editarConsumo(consumo);

            if (actualizado) {
                return Response.ok("{\"mensaje\": \"Consumo actualizado correctamente\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("{\"error\": \"No se pudo actualizar el consumo\"}").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"error\": \"Error interno al actualizar el consumo\"}").build();
        }
    }
}
