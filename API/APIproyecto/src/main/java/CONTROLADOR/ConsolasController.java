
package CONTROLADOR;

import MODELO.Consola;
import MODELO.ConsolaDTO;
import MODELO.ConsolasDAO;
//import MODELO.ConsolasServices;
import MODELO.ValidadorConsola;
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

@Path("/consolas")
public class ConsolasController {
    ConsolasDAO dao = new ConsolasDAO();//se crea una instancia de la clase consolasDAO
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerConsolas() {//se crea el metodo que va a responder a la solicitud get del endpoint     
        List<Consola> consolas = dao.getAll();//se crea una lista de tipo Consola en la cual se va a almacenar
                                             //el retorno de la funcion getAll de la clase consolasDAO
        return Response.ok(consolas).build();//se retorna una respuesta ok con la lista de consolas
    }
    
    @GET
    @Path("/con-precio")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerConsolasConPrecio() {//Se crea el metodo que va a responer a la solicitud get del endpoint consolas/con-precio
        List<ConsolaDTO> consolas = dao.getAllConPrecio();//se crea una lista de tipo CosolaDTO en la cual se va a almacemar
                                                        //el retorno del metodo getAllConPrecio de la clase consolasDAO
        return Response.ok(consolas).build();//se retorna una respuesta ok con la lista de las consolas
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerConsolaPorId(@PathParam("id") int id) {//Se crea el metodo que va a responder a la solicitud
                                                                  //GET del endpoint consolas/{id}, este metodo recibe
                                                                  //como parametro en la URL el ID de la consola a buscar
                                                                  
        Consola consola = dao.getById(id);//Se declara una variable de tipo Consola llamada consola, en esta se almacenara
                                         //el retorno del metodo getById de la clae ConsolasDAO

        if (consola != null) {//en caso de que consola sea diferente de null
            return Response.ok(consola).build();//se retorna una respuesta OK junto con la variable consola
        } else {//en caso contrario y que la consola si sea null
            return Response.status(Response.Status.NOT_FOUND)//se responde con un estado NOT_FOUND y un mensaje de error
                           .entity("{\"error\":\"Consola no encontrada\"}")//este es el mensaje de error
                           .build();
        }
    }

    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearConsola(Consola consola) {
        //se crea el metodo que va a responder a la solicitud POST del endpoint /consolas
        
        String error = ValidadorConsola.validarConsola(consola);//se declara una variable de tipo String llamara error
                                                               //en esta se alamcenara el retorno de la funcion
                                                               //validarConsola de la clase ValidadorConsola
                                                               
        if (error != null) {//en caso de que la variable error sea difernte de null
            return Response.status(Response.Status.BAD_REQUEST)//se responde con un estado BAD_REQUEST y re envia el error
                           .entity(error)//ze envia el error
                           .build();
        }
        boolean creada = dao.post(consola);//se declara la variable de tipo booleana llamada creada, en esta se almacenara
                                           //el retorno de la funcion post de la clase ConsolasDAO

        if (creada) {//en caso de que la variable "creadaa" sea verdadera
            return Response.status(Response.Status.CREATED)//se responde con un estado CREATED
                    .entity("{\"mensaje\":\"Consola creada correctamente\"}")//y se retorna un mensaje indicando que la consola fue creada
                    .build();
        } else {//en caso de que lavariable "creada" no sea verdadera
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)//se responde con un estado INTERNAL_SERVER_ERROR
                    .entity("{\"error\":\"Error al crear consola\"}")//y se retorna un mensaje de error
                    .build();
        }
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarConsola(@PathParam("id") int id, Consola consola) {
        //Se crea el metodo que va a responder a las solicitudes PUT del enpoint consolas/{id}, este metodo recibe 
        //como parametro en la URL el id de la consola que se desea editar y tambien recibe como parametro un objeto consola
        
        consola.setId(id);//se le envia al objeto el is de la consola
        
        String error = ValidadorConsola.validarConsola(consola);//Se declara la variable error, en esta se almacenara
                                                                //el retorno de la funcion validarConsola de la clase
                                                                //ValidadorConsola
                                                                
        if (error != null) {//si la clase error es diferente de null
            return Response.status(Response.Status.BAD_REQUEST)//se retorna un estado BAD_REQUEST
                           .entity(error)//se retorna el error
                           .build();
        }
        boolean actualizado = dao.put(consola);//se declara la variable de tip Booleanada "actualizaca", en esta se 
                                               //almacenara el retorno del metodo put de la clase ConsolasDAO

        if (actualizado) {//si la variable "actualizado" es verdadera
            return Response.ok("{\"mensaje\":\"Consola actualizada correctamente\"}").build();//se retorna un codigo ok
                                                                                              //y se retorna un mensaje
        } else {//en caso de que la variable "actualizado" sea falsa
            return Response.status(Response.Status.NOT_FOUND)//se responde con un estado NOT_FOUND
                           .entity("{\"error\":\"No se pudo actualizar la consola\"}")//y se retorna un mensaje de error
                           .build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarConsola(@PathParam("id") int id) {      
        //Se crea el metodo que va a responder a las solicitudes DELETE del endpoint consolas/{id},
        //este metodo recibe como parametro en la URL el id de la consola que se desea eliminar
        
        Consola consola=dao.getById(id);//se declara el objeto Consola, en esta se almacenara el retorno de la funcion
                                        //getByID de la clase consolasDAO
        consola.setId_estado(2);//se le envia en objeto consola el id de estado 2
        boolean eliminado = dao.put(consola);//se declara la variable de tipo Booleana eliminado, en esta se almacenar
                                            //el retorno del metodo put de la clase ConsolasDAO

        if (eliminado) {//Si la variable "eliminado" es verdadera
            return Response.ok("{\"mensaje\":\"Consola eliminada correctamente\"}").build();//se responde con una respuesta
                                                                                            //OK y se retorna un mensaje
        } else {//en caso de que la variable "elimiando" sea falsa
            return Response.status(Response.Status.NOT_FOUND)//se responde cun un estado NOT_FOUND
                           .entity("{\"error\":\"Consola no encontrada o no se pudo eliminar\"}")//y se retorna un mensaje
                           .build();
        }
    } 
}
