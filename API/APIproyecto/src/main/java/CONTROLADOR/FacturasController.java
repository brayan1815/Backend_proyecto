
package CONTROLADOR;

import MODELO.FacturaDTO;
import MODELO.FacturasServices;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/facturas")
public class FacturasController {
    
    private final FacturasServices servicio = new FacturasServices();

    @POST
    @Path("/reserva/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerOCrearFactura(@PathParam("id") int idReserva) {
        FacturaDTO factura = servicio.obtenerOCrearFactura(idReserva);

        if (factura != null) {
            return Response.ok(factura).build(); // Se devuelve 200 con el JSON de la factura
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("No se pudo generar la factura para la reserva " + idReserva)
                           .build();
        }
    }

}
