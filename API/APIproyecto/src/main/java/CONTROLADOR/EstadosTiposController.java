package CONTROLADOR;

import MODELO.EstadoTipo;
import MODELO.EstadoTipoDAO;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/estadosTipo")//se define la ruta base para los endpoints de esta clase
public class EstadosTiposController {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTipos() {
        //se crea el método que responde a la solicitud GET del endpoint /estadosTipo
        //este método obtiene todos los estados de tipo registrados
        
        EstadoTipoDAO dao = new EstadoTipoDAO();//se crea una instancia de la clase EstadoTipoDAO
        List<EstadoTipo> estados = dao.getAll();//se crea una lista de tipo EstadoTipo en la cual se almacena
                                                //el retorno del método getAll de la clase EstadoTipoDAO
        
        return Response.ok(estados).build();//se retorna una respuesta OK con la lista de estados
    }
}
