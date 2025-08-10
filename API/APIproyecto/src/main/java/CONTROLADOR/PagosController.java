package CONTROLADOR;

import MODELO.FacturasDAO;
import MODELO.Pago;
import MODELO.PagosDAO;
import MODELO.PagosServices;
import MODELO.ReservaDTO;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pagos")//se define la ruta base para todos los endpoints de esta clase
public class PagosController {
    
    //se crean las instancias necesarias para acceder a datos y lógica de negocio
    PagosDAO pagosDAO = new PagosDAO();
    PagosServices pagosService = new PagosServices();
    FacturasDAO facDao = new FacturasDAO();

    @GET
    @Path("/metodo/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPagosPorMetodo(@PathParam("id") int idMetodo) {
        //se obtiene la lista de pagos asociados a un método de pago específico
        List<Pago> lista = pagosDAO.obtenerPagosPorMetodo(idMetodo);

        if (lista.isEmpty()) {
            //si no se encuentran pagos para el método se devuelve un mensaje con código 404
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"mensaje\":\"No se encontraron pagos para este método\"}")
                    .build();
        }

        //si hay resultados se devuelven con código 200 OK en formato JSON
        return Response.ok(lista).build();
    }
    
    @GET
    @Path("/metodo/{id}/total")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTotalPorMetodo(@PathParam("id") int idMetodo) {     
        //se calcula el total pagado con un método de pago específico
        double total = pagosService.obtenerTotalPagadoPorMetodo(idMetodo);

        //se retorna el total pagado en formato JSON
        return Response.ok("{\"total_pagado\":" + total + "}").build();
    }
    
    @GET
    @Path("/metodo/{id}/reservas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReservasPorMetodo(@PathParam("id") int idMetodo) {
        //se obtiene la lista de reservas que se han pagado usando un método de pago específico
        List<ReservaDTO> reservas = pagosService.obtenerReservasPorMetodoPago(idMetodo);

        //se retorna la lista de reservas
        return Response.ok(reservas).build();
    }
    
    @GET
    @Path("/total")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTotal() {     
        //se calcula el total de pagos registrados en todas las facturas
        double total = facDao.calcularTotalPagos();

        //se retorna el total en formato JSON
        return Response.ok("{\"total_pagado\":" + total + "}").build();
    }
}
