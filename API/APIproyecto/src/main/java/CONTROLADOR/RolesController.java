package CONTROLADOR;

import MODELO.Rol;
import MODELO.RolesDAO;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/roles") //ruta base para acceder a los roles
public class RolesController {
    
    //instancia de RolesDAO para manejar operaciones con la base de datos
    RolesDAO dao = new RolesDAO();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON) //indica que la respuesta se devuelve en formato JSON
    public List<Rol> obtenerUsuarios() {
        //obtiene la lista de todos los roles desde la base de datos
        return dao.get();
    }
}
