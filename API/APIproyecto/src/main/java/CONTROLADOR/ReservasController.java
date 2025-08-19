package CONTROLADOR;

import MODELO.Reserva;
import MODELO.ReservaDTO;
import MODELO.ReservasDAO;
import MODELO.ReservasServices;
import MODELO.ValidadorReserva;
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

@Path("/reservas") //ruta base del endpoint reservas
public class ReservasController {
    
    private ReservasServices reservaService = new ReservasServices();//se crea una instancia de la clase ReservasServices
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReservas() {
        //se crea el método que va a responder a la solicitud GET del endpoint /reservas
        
        ReservasDAO dao = new ReservasDAO();//se crea una instancia de la clase ReservasDAO
        List<Reserva> reservas = dao.getAll();//se crea una lista de tipo Reserva en la cual se almacenará el retorno
                                              //del método getAll de la clase ReservasDAO
        return Response.ok(reservas).build();//se retorna una respuesta OK junto con la lista de reservas
    }
    
    @GET
    @Path("/detalle")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReservasConDetalle() {
        //se crea el método que va a responder a la solicitud GET del endpoint /reservas/detalle
        
        ReservasDAO dao = new ReservasDAO();//se crea una instancia de la clase ReservasDAO
        List<ReservaDTO> reservas = dao.getAllConInfo();//se crea una lista de tipo ReservaDTO en la cual se almacenará
                                                        //el retorno del método getAllConInfo de la clase ReservasDAO
        return Response.ok(reservas).build();//se retorna una respuesta OK junto con la lista de reservas
    }
    
    @GET
    @Path("/detalle/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReservasConDetalleByUser(@PathParam("id") int id) {
        //se crea el método que va a responder a la solicitud GET del endpoint /reservas/detallle/id
        
        ReservasDAO dao = new ReservasDAO();//se crea una instancia de la clase ReservasDAO
        List<ReservaDTO> reservas = dao.getAllConInfoByUser(id);//se crea una lista de tipo ReservaDTO en la cual se almacenará
                                                        //el retorno del método getAllConInfoByUser de la clase ReservasDAO
        return Response.ok(reservas).build();//se retorna una respuesta OK junto con la lista de reservas
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReservaPorId(@PathParam("id") int id) {
        //se crea el método que va a responder a la solicitud GET del endpoint /reservas/{id},
        //este método recibe como parámetro en la URL el ID de la reserva a buscar
        
        ReservasDAO dao = new ReservasDAO();//se crea una instancia de la clase ReservasDAO
        Reserva reserva = dao.getById(id);//se declara la variable reserva de tipo Reserva, en esta se almacenará
                                          //el retorno del método getById de la clase ReservasDAO

        if (reserva != null) {//si la variable reserva es diferente de null
            return Response.ok(reserva).build();//se retorna una respuesta OK junto con la reserva
        } else {//en caso de que la variable reserva sea null
            return Response.status(Response.Status.NOT_FOUND)//se retorna un estado NOT_FOUND
                           .entity("{\"error\": \"Reserva no encontrada\"}")//y se retorna un mensaje de error
                           .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearReserva(Reserva reserva) {
        //se crea el método que va a responder a la solicitud POST del endpoint /reservas
        //este método recibe como parámetro un objeto de tipo Reserva en formato JSON
        
        String puedeRes = ValidadorReserva.puedeReservar(reserva);//se declara la variable puedeRes, en esta se almacenará
                                                                  //el retorno del método puedeReservar de la clase
                                                                  //ValidadorReserva
        if (puedeRes != null) {//si la variable puedeRes es diferente de null
            return Response.status(Response.Status.BAD_REQUEST)//se retorna un estado BAD_REQUEST
                           .entity("{\"error\": \"" + puedeRes + "\"}")//y se retorna el mensaje de error
                           .build();
        }
        
        String error = ValidadorReserva.validar(reserva);//se declara la variable error, en esta se almacenará
                                                         //el retorno del método validar de la clase ValidadorReserva
        if (error != null) {//si la variable error es diferente de null
            return Response.status(Response.Status.BAD_REQUEST)//se retorna un estado BAD_REQUEST
                           .entity("{\"error\": \"" + error + "\"}")//y se retorna el mensaje de error
                           .build();
        }
        
        ReservasDAO dao = new ReservasDAO();//se crea una instancia de la clase ReservasDAO
        boolean creada = dao.post(reserva);//se declara la variable creada de tipo booleana, en esta se almacenará
                                           //el retorno del método post de la clase ReservasDAO

        if (creada) {//si la variable creada es verdadera
            return Response.status(Response.Status.CREATED)//se retorna un estado CREATED
                           .entity("{\"mensaje\":\"Reserva creada correctamente\"}")//y se retorna un mensaje de éxito
                           .build();
        } else {//en caso de que la variable creada sea falsa
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)//se retorna un estado INTERNAL_SERVER_ERROR
                           .entity("{\"error\":\"Error al crear reserva\"}")//y se retorna un mensaje de error
                           .build();
        }
    }
    
    @GET
    @Path("/consola/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReservasPorConsola(@PathParam("id") int idConsola) {
        //se crea el método que va a responder a la solicitud GET del endpoint /reservas/consola/{id},
        //este método recibe como parámetro en la URL el ID de la consola para filtrar las reservas
        
        ReservasDAO dao = new ReservasDAO();//se crea una instancia de la clase ReservasDAO
        List<Reserva> reservas = dao.getByIdConsola(idConsola);//se crea una lista de tipo Reserva en la cual se almacenará
                                                               //el retorno del método getByIdConsola de la clase ReservasDAO

        if (reservas != null && !reservas.isEmpty()) {//si la lista reservas no es null y no está vacía
            return Response.ok(reservas).build();//se retorna una respuesta OK junto con la lista
        } else {//en caso contrario
            return Response.status(Response.Status.NOT_FOUND)//se retorna un estado NOT_FOUND
                           .entity("{\"mensaje\": \"No se encontraron reservas para esta consola\"}")//y se retorna un mensaje
                           .build();
        }
    }
    
    @GET
    @Path("/estado-actualizado")
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarEstadoDesdeFrontend() {
        //se crea el método que va a responder a la solicitud GET del endpoint /reservas/estado-actualizado
        
        List<ReservaDTO> actualizadas = reservaService.actualizarEstadoReserva();//se crea una lista de tipo ReservaDTO
                                                                                 //en la cual se almacenará el retorno
                                                                                 //del método actualizarEstadoReserva
                                                                                 //de la clase ReservasServices
        return Response.ok(actualizadas).build();//se retorna una respuesta OK junto con la lista de reservas actualizadas
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarReserva(@PathParam("id") int id, Reserva reserva) {
        //se crea el método que va a responder a la solicitud PUT del endpoint /reservas/{id},
        //este método recibe como parámetro en la URL el ID de la reserva a editar y como parámetro un objeto reserva
        
        reserva.setId(id);//se le envía al objeto reserva el id de la reserva
        
        ReservasDAO dao = new ReservasDAO();//se crea una instancia de la clase ReservasDAO
        boolean actualizado = dao.put(reserva);//se declara la variable actualizado de tipo booleana, en esta se
                                               //almacenará el retorno del método put de la clase ReservasDAO

        if (actualizado) {//si la variable actualizado es verdadera
            return Response.ok("{\"mensaje\": \"Reserva actualizada correctamente\"}").build();//se retorna un estado OK
                                                                                               //y un mensaje de éxito
        } else {//en caso de que la variable actualizado sea falsa
            return Response.status(Response.Status.NOT_FOUND)//se retorna un estado NOT_FOUND
                           .entity("{\"error\": \"No se pudo actualizar la reserva\"}")//y se retorna un mensaje de error
                           .build();
        }
    }
    
    @GET
    @Path("/usuario/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReservasPorUsuario(@PathParam("id") int idUsuario) {
        //se crea el método que va a responder a la solicitud GET del endpoint /reservas/usuario/{id},
        //este método recibe como parámetro en la URL el ID del usuario para filtrar las reservas
        
        List<ReservaDTO> reservas = reservaService.obtenerReservasPorUsuario(idUsuario);//se crea una lista de tipo
                                                                                        //ReservaDTO en la cual se almacenará
                                                                                        //el retorno del método obtenerReservasPorUsuario
                                                                                        //de la clase ReservasServices

        if (reservas != null && !reservas.isEmpty()) {//si la lista reservas no es null y no está vacía
            return Response.ok(reservas).build();//se retorna una respuesta OK junto con la lista
        } else {//en caso contrario
            return Response.status(Response.Status.NOT_FOUND)//se retorna un estado NOT_FOUND
                           .entity("{\"mensaje\": \"No se encontraron reservas para este usuario\"}")//y se retorna un mensaje
                           .build();
        }
    }
    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarReserva(@PathParam("id") int id) {
        //se crea el método que va a responder a la solicitud DELETE del endpoint /reservas/{id},
        //este método recibe como parámetro en la URL el ID de la reserva que se desea eliminar
        
        ReservasDAO dao = new ReservasDAO();//se crea una instancia de la clase ReservasDAO
        Reserva reserva = dao.getById(id);//se declara el objeto reserva, en esta se almacenará el retorno del método
                                          //getById de la clase ReservasDAO

        if (reserva == null) {//si la variable reserva es null
            return Response.status(Response.Status.NOT_FOUND)//se retorna un estado NOT_FOUND
                           .entity("{\"error\":\"Reserva no encontrada\"}")//y se retorna un mensaje de error
                           .build();
        }

        String validacion = ValidadorReserva.puedeEliminar(reserva);//se declara la variable validacion, en esta se almacenará
                                                                    //el retorno del método puedeEliminar de la clase
                                                                    //ValidadorReserva

        if (validacion != null) {//si la variable validacion es diferente de null
            return Response.status(Response.Status.BAD_REQUEST)//se retorna un estado BAD_REQUEST
                           .entity("{\"error\":\"" + validacion + "\"}")//y se retorna el mensaje de error
                           .build();
        }
        
        boolean eliminada = dao.eliminarReserva(id);//se declara la variable eliminada de tipo booleana, en esta se almacenará
                                                    //el retorno del método eliminarReserva de la clase ReservasDAO

        if (eliminada) {//si la variable eliminada es verdadera
            return Response.ok("{\"mensaje\":\"Se cancelo la reserva correctamente\"}").build();//se retorna un estado OK
                                                                                                //y un mensaje de éxito
        } else {//en caso de que la variable eliminada sea falsa
            return Response.status(Response.Status.NOT_FOUND)//se retorna un estado NOT_FOUND
                           .entity("{\"error\":\"No se pudo cancelar la reserva\"}")//y se retorna un mensaje de error
                           .build();
        }
    }
}
