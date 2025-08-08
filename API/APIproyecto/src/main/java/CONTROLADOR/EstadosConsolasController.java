/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import MODELO.EstadoConsola;
import MODELO.EstadosConsolasDAO;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/estadosConsolas")
public class EstadosConsolasController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTipos() {
        EstadosConsolasDAO dao = new EstadosConsolasDAO();
        List<EstadoConsola> estados = dao.getAll();
        return Response.ok(estados).build();
    }
}
