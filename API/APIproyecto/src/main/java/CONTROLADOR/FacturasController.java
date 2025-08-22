package CONTROLADOR;

import MODELO.Factura;
import MODELO.FacturasServices;
import MODELO.Secured;
import MODELO.TienePermiso;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Secured
@Path("/facturas") // Define la ruta base para este controlador REST
public class FacturasController {

    private final FacturasServices servicio = new FacturasServices(); // Instancia el servicio que maneja la lógica de facturas

    // Método POST para obtener o crear una factura a partir del id de una reserva
    @POST
    @Path("/reserva/{id}") // Ruta para acceder a este método, recibe el id de la reserva por URL
    @Produces(MediaType.APPLICATION_JSON) // Responde en formato JSON
    public Response obtenerOCrearFactura(@PathParam("id") int idReserva) {
        Factura factura = servicio.obtenerOCrearFactura(idReserva); // Llama al servicio para obtener o crear factura

        if (factura != null) {
            // Si se obtuvo o creó factura correctamente, responder con código 200 OK y la factura en JSON
            return Response.ok(factura).build();
        } else {
            // Si hubo error, responder con código 400 Bad Request y mensaje de error en JSON
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"No se pudo generar la factura para la reserva\"}")
                .build();
        }
    }

    // Método POST para registrar el pago de una factura usando un método de pago
    @TienePermiso("pagos.crear")
    @POST
    @Path("/pago/{idFactura}/{idMetodo}") // Ruta que recibe id de factura y método de pago por URL
    @Produces(MediaType.APPLICATION_JSON) // Respuesta en formato JSON
    public Response cobrarFactura(@PathParam("idFactura") int idFactura, @PathParam("idMetodo") int idMetodoPago) {
        boolean exito = servicio.cobrarFactura(idFactura, idMetodoPago); // Llama al servicio para cobrar factura

        if (exito) {
            // Si el pago se registró con éxito, responde con mensaje de confirmación
            return Response.ok("{\"mensaje\":\"Pago registrado correctamente\"}").build();
        } else {
            // Si hubo error al registrar el pago, responde con error y código 400
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"No se pudo registrar el pago\"}")
                .build();
        }
    }
}
