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

@Path("metodospago")//se define la ruta base para los endpoints de esta clase
@Produces(MediaType.APPLICATION_JSON)//se especifica que las respuestas serán en formato JSON
public class MetodosPagoController {
    
    //se crea una instancia del DAO para acceder a los métodos de la base de datos
    MetodosPagoDAO dao = new MetodosPagoDAO();

    @GET
    public Response obtenerMetodosPago() {
        //se obtiene la lista de métodos de pago desde la base de datos
        List<MetodoPago> lista = dao.getAll();
        
        //se retorna la lista con código 200 OK en formato JSON
        return Response.ok(lista).build();
    }
}
