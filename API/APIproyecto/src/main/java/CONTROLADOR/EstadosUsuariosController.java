
package CONTROLADOR;

import MODELO.EstadosUsuarios;
import MODELO.EstadosUsuariosDAO;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/estadosUsuarios")
public class EstadosUsuariosController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTipos() {
        EstadosUsuariosDAO dao = new EstadosUsuariosDAO();
        List<EstadosUsuarios> tipos = dao.getAll();
        return Response.ok(tipos).build();
    }
}
