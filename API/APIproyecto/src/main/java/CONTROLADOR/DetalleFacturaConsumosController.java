
package CONTROLADOR;

import MODELO.DetalleFacturaConsumos;
import MODELO.DetalleFacturaConsumosDAO;
import MODELO.DetalleFacturaConsumosServices;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/detalle-factura-consumos")
public class DetalleFacturaConsumosController {
    DetalleFacturaConsumosDAO dao = new DetalleFacturaConsumosDAO();
    DetalleFacturaConsumosServices service=new DetalleFacturaConsumosServices();

    @GET
    @Path("/factura/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDetallesPorIdFactura(@PathParam("id") int idFactura) {
        List<DetalleFacturaConsumos> lista = dao.getByIdFactura(idFactura);

        if (lista != null && !lista.isEmpty()) {
            return Response.ok(lista).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"mensaje\":\"No se encontraron detalles para esta factura\"}")
                           .build();
        }
    }
    
    @GET
    @Path("/reservas/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDetallesPorIdReserva(@PathParam("id") int idReserva){
        List<DetalleFacturaConsumos> lista = service.obtenerConsumosPorIdReserva(idReserva);
        return Response.ok(lista).build();
    }
}
