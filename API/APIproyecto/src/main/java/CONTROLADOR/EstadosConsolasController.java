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

@Path("/estadosConsolas")//se define la ruta base para los endpoints de esta clase
public class EstadosConsolasController {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTipos() {
        //se crea el método que responde a la solicitud GET del endpoint /estadosConsolas
        //este método obtiene todos los estados de consola registrados
        
        EstadosConsolasDAO dao = new EstadosConsolasDAO();//se crea una instancia de la clase EstadosConsolasDAO
        List<EstadoConsola> estados = dao.getAll();//se crea una lista de tipo EstadoConsola en la cual se almacena
                                                   //el retorno del método getAll de la clase EstadosConsolasDAO
        
        return Response.ok(estados).build();//se retorna una respuesta OK con la lista de estados
    }
}
