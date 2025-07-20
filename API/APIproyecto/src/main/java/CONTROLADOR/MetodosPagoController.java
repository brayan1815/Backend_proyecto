/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import MODELO.MetodoPago;
import MODELO.MetodosPagoDAO;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("metodospago")
@Produces(MediaType.APPLICATION_JSON)
public class MetodosPagoController {
    MetodosPagoDAO dao = new MetodosPagoDAO();

    @GET
    public Response obtenerMetodosPago() {
        List<MetodoPago> lista = dao.getAll();
        return Response.ok(lista).build();
    }
}
