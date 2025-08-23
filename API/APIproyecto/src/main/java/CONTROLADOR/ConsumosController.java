package CONTROLADOR;

import MODELO.Consumo;
import MODELO.ConsumoDTO;
import MODELO.ConsumosDAO;
import MODELO.ConsumosServices;
import MODELO.Secured;
import MODELO.TienePermiso;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Secured
@Path("/consumos")//se define la ruta base para los endpoints de esta clase
public class ConsumosController {
    
    ConsumosServices service = new ConsumosServices();//se crea una instancia de la clase ConsumosServices
    ConsumosDAO dao = new ConsumosDAO();//se crea una instancia de la clase ConsumosDAO
    
    @TienePermiso("consumos.crear")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearConsumo(Consumo consumo) {
        //se crea el método que va a responder a la solicitud POST del endpoint /consumos
        //este recibe un objeto de tipo Consumo en formato JSON
        
        boolean creado = dao.post(consumo);//se declara la variable booleana "creado", en esta se almacena
                                           //el retorno del método post de la clase ConsumosDAO

        if (creado) {//si la variable "creado" es verdadera
            return Response.status(Response.Status.CREATED)//se responde con el estado CREATED
                .entity("{\"mensaje\":\"Consumo creado correctamente\"}").build();//y se retorna un mensaje
        } else {//en caso de que la variable "creado" sea falsa
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)//se responde con un estado INTERNAL_SERVER_ERROR
                .entity("{\"error\":\"Error al registrar el consumo\"}").build();//y se retorna un mensaje de error
        }
    }
    
    @TienePermiso("consumos.index")
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerConsumoPorId(@PathParam("id") int id) {
        //se crea el método que responde a las solicitudes GET del endpoint /consumos/{id}
        //recibe como parámetro en la URL el id del consumo a buscar
        
        Consumo consumo = dao.getById(id);//se declara una variable de tipo Consumo, en esta se almacena
                                          //el retorno del método getById de la clase ConsumosDAO

        if (consumo != null) {//si el objeto "consumo" es diferente de null
            return Response.ok(consumo).build();//se retorna una respuesta OK con el objeto consumo
        } else {//si el objeto "consumo" es null
            return Response.status(Response.Status.NOT_FOUND)//se responde con un estado NOT_FOUND
                           .entity("{\"mensaje\": \"Consumo no encontrado\"}")//y se retorna un mensaje
                           .build();
        }
    }
    
    @TienePermiso("consumos.index")
    @GET
    @Path("/dto/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerConsumoPorIdDto(@PathParam("id") int idConsumo) {
        //se crea el método que responde a las solicitudes GET del endpoint /consumos/dto/{id}
        //este método obtiene un consumo en formato DTO a partir de su id
        
        try {
            ConsumoDTO dto = service.obtenerConsumoPorId(idConsumo);//se declara un objeto ConsumoDTO
                                                                    //en el cual se almacena el retorno del método
                                                                    //obtenerConsumoPorId de la clase ConsumosServices
            if (dto != null) {//si el objeto dto es diferente de null
                return Response.ok(dto).build();//se retorna una respuesta OK con el objeto dto
            } else {//si el objeto dto es null
                return Response.status(Response.Status.NOT_FOUND)//se responde con un estado NOT_FOUND
                               .entity("No se encontró el consumo con ID " + idConsumo)//y se retorna un mensaje
                               .build();
            }
        } catch (Exception e) {//en caso de ocurrir un error en la ejecución del método
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)//se responde con un estado INTERNAL_SERVER_ERROR
                           .entity("Error al obtener el consumo: " + e.getMessage())//y se retorna un mensaje de error
                           .build();
        }
    }
    
    @TienePermiso("consumos.index")
    @GET
    @Path("/reserva/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConsumosPorReserva(@PathParam("id") int idReserva) {
        //se crea el método que responde a las solicitudes GET del endpoint /consumos/reserva/{id}
        //este método obtiene una lista de consumos asociados a una reserva
        
        List<ConsumoDTO> lista = service.obtenerConsumosPorIdReserva(idReserva);//se crea una lista de tipo ConsumoDTO
                                                                                //en la cual se almacena el retorno del método
                                                                                //obtenerConsumosPorIdReserva de la clase ConsumosServices
        return Response.ok(lista).build();//se retorna una respuesta OK con la lista obtenida
    }
    
    @TienePermiso("consumos.editar")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarConsumo(Consumo consumo) {
        //se crea el método que responde a las solicitudes PUT del endpoint /consumos
        //recibe como parámetro un objeto Consumo en formato JSON
        
        try {
            boolean actualizado = service.editarConsumo(consumo);//se declara la variable booleana "actualizado",
                                                                 //en esta se almacena el retorno del método editarConsumo
                                                                 //de la clase ConsumosServices

            if (actualizado) {//si la variable "actualizado" es verdadera
                return Response.ok("{\"mensaje\": \"Consumo actualizado correctamente\"}").build();//se responde con OK y un mensaje
            } else {//si la variable "actualizado" es falsa
                return Response.status(Response.Status.BAD_REQUEST)//se responde con un estado BAD_REQUEST
                               .entity("{\"error\": \"No se pudo actualizar el consumo\"}").build();//y se retorna un mensaje de error
            }
        } catch (Exception e) {//en caso de ocurrir un error en la ejecución del método
            e.printStackTrace();//se imprime el error en consola
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)//se responde con un estado INTERNAL_SERVER_ERROR
                           .entity("{\"error\": \"Error interno al actualizar el consumo\"}").build();//y se retorna un mensaje de error
        }
    }
    
    @TienePermiso("consumos.eliminar")
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarConsumo(@PathParam("id") int id) {
        //se crea el método que responde a las solicitudes DELETE del endpoint /consumos/{id}
        //recibe como parámetro en la URL el id del consumo a eliminar

        boolean eliminado = service.eliminarConsumo(id);//se declara la variable booleana "eliminado"
                                                        //en esta se almacena el retorno del método eliminarConsumo
                                                        //de la clase ConsumosServices

        if (eliminado) {//si la variable "eliminado" es verdadera
            return Response.ok("{\"mensaje\":\"Consumo eliminado correctamente\"}").build();//se responde con OK y un mensaje
        } else {//si la variable "eliminado" es falsa
            return Response.status(Response.Status.BAD_REQUEST)//se responde con un estado BAD_REQUEST
                           .entity("{\"mensaje\":\"Error al eliminar el consumo\"}")//y se retorna un mensaje de error
                           .build();
        }
    }
}
