package CONTROLADOR;

import MODELO.EstadosUsuarios;
import MODELO.EstadosUsuariosDAO;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/estadosUsuarios")//se define la ruta base para los endpoints de esta clase
public class EstadosUsuariosController {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTipos() {
        //se crea el método que responde a la solicitud GET del endpoint /estadosUsuarios
        //este método obtiene todos los estados de usuario registrados
        
        EstadosUsuariosDAO dao = new EstadosUsuariosDAO();//se crea una instancia de la clase EstadosUsuariosDAO
        List<EstadosUsuarios> tipos = dao.getAll();//se crea una lista de tipo EstadosUsuarios en la cual se almacena
                                                   //el retorno del método getAll de la clase EstadosUsuariosDAO
        
        return Response.ok(tipos).build();//se retorna una respuesta OK con la lista de estados
    }
}
