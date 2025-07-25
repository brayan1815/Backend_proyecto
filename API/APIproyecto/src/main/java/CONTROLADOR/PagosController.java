
package CONTROLADOR;

import MODELO.FacturasDAO;
import MODELO.Pago;
import MODELO.PagosDAO;
import MODELO.PagosServices;
import MODELO.Reserva;
import MODELO.ReservaDTO;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pagos")
public class PagosController {
    PagosDAO pagosDAO = new PagosDAO();
    PagosServices pagosService = new PagosServices();
    FacturasDAO facDao=new FacturasDAO();

    @GET
    @Path("/metodo/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPagosPorMetodo(@PathParam("id") int idMetodo) {
        List<Pago> lista = pagosDAO.obtenerPagosPorMetodo(idMetodo);

        if (lista.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"mensaje\":\"No se encontraron pagos para este método\"}")
                    .build();
        }

        return Response.ok(lista).build();
    }
    
    @GET
    @Path("/metodo/{id}/total")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTotalPorMetodo(@PathParam("id") int idMetodo) {     
        double total = pagosService.obtenerTotalPagadoPorMetodo(idMetodo);

        return Response.ok("{\"total_pagado\":" + total + "}").build();
    }
    
    @GET
    @Path("/metodo/{id}/reservas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReservasPorMetodo(@PathParam("id") int idMetodo) {
        List<ReservaDTO> reservas = pagosService.obtenerReservasPorMetodoPago(idMetodo);

        return Response.ok(reservas).build();
    }
    
    @GET
    @Path("/total")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTotal() {     
        double total = facDao.calcularTotalPagos();

        return Response.ok("{\"total_pagado\":" + total + "}").build();
    }
}
