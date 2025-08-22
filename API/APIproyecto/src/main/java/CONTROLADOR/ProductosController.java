package CONTROLADOR;

import MODELO.Producto;
import MODELO.ProductosDAO;
import MODELO.Secured;
import MODELO.TienePermiso;
import MODELO.ValidadorProducto;
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
@Path("/productos") //ruta base del endpoint productos
public class ProductosController {
    
    @TienePermiso("productos.crear")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearProducto(Producto producto) {
        //se crea el método que va a responder a la solicitud POST del endpoint /productos
        //este método recibe como parámetro un objeto de tipo Producto en formato JSON
        
        String error = ValidadorProducto.validarProducto(producto);//se declara la variable de tipo String llamada error
                                                                   //en esta se almacenará el retorno del método
                                                                   //validarProducto de la clase ValidadorProducto
                                                                   
        if (error != null) {//en caso de que la variable error sea diferente de null
            return Response.status(Response.Status.BAD_REQUEST)//se retorna un estado BAD_REQUEST
                           .entity(error)//se retorna el mensaje de error
                           .build();
        }
        
        ProductosDAO dao = new ProductosDAO();//se crea una instancia de la clase ProductosDAO
        boolean creado = dao.post(producto);//se declara la variable booleana creada, en esta se almacenará el retorno
                                            //del método post de la clase ProductosDAO

        if (creado) {//si la variable creada es verdadera
            return Response.status(Response.Status.CREATED)//se retorna un estado CREATED
                           .entity("{\"mensaje\":\"Producto creado correctamente\"}")//y se retorna un mensaje
                           .build();
        } else {//en caso de que la variable creada sea falsa
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)//se retorna un estado INTERNAL_SERVER_ERROR
                           .entity("{\"mensaje\":\"Error al crear producto\"}")//y se retorna un mensaje de error
                           .build();
        }
    }
    
    @TienePermiso("productos.index")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerProductos() {
        //se crea el método que va a responder a la solicitud GET del endpoint /productos
        
        ProductosDAO dao = new ProductosDAO();//se crea una instancia de la clase ProductosDAO
        List<Producto> productos = dao.getAll();//se crea una lista de tipo Producto en la cual se almacenará el retorno
                                                //del método getAll de la clase ProductosDAO
                                                
        return Response.ok(productos).build();//se retorna una respuesta OK junto con la lista de productos
    }
    
    @TienePermiso("productos.index")
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerProductoPorId(@PathParam("id") int id) {
        //se crea el método que va a responder a la solicitud GET del endpoint productos/{id},
        //este método recibe como parámetro en la URL el ID del producto que se desea buscar
        
        ProductosDAO dao = new ProductosDAO();//se crea una instancia de la clase ProductosDAO
        Producto producto = dao.getById(id);//se declara la variable producto de tipo Producto, en esta se almacenará
                                            //el retorno del método getById de la clase ProductosDAO

        if (producto != null) {//si la variable producto es diferente de null
            return Response.ok(producto).build();//se retorna una respuesta OK junto con el producto
        } else {//en caso de que la variable producto sea null
            return Response.status(Response.Status.NOT_FOUND)//se retorna un estado NOT_FOUND
                           .entity("{\"error\":\"Producto no encontrado\"}")//y se retorna un mensaje de error
                           .build();
        }
    }
    
    @TienePermiso("productos.editar")
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarProducto(@PathParam("id") int id, Producto producto) {
        //se crea el método que va a responder a la solicitud PUT del endpoint productos/{id},
        //este método recibe como parámetro en la URL el ID del producto a editar y como parámetro un objeto producto
        
        producto.setId(id);//se le envía al objeto producto el id del producto
        
        if(producto.getCantidades_disponibles() > 0){//si la cantidad disponible es mayor a cero
            producto.setId_estado_producto(1);//se le envía al objeto producto el id de estado 1 (disponible)
        } else {//en caso contrario
            producto.setId_estado_producto(2);//se le envía al objeto producto el id de estado 2 (agotado)
        }
        
        String error = ValidadorProducto.validarProducto(producto);//se declara la variable error, en esta se almacenará
                                                                   //el retorno del método validarProducto de la clase
                                                                   //ValidadorProducto
                                                                   
        if (error != null) {//si la variable error es diferente de null
            return Response.status(Response.Status.BAD_REQUEST)//se retorna un estado BAD_REQUEST
                           .entity(error)//se retorna el error
                           .build();
        }
        
        ProductosDAO dao = new ProductosDAO();//se crea una instancia de la clase ProductosDAO
        boolean actualizado = dao.put(producto);//se declara la variable actualizada de tipo booleana, en esta se
                                                 //almacenará el retorno del método put de la clase ProductosDAO

        if (actualizado) {//si la variable actualizado es verdadera
            return Response.ok("{\"mensaje\": \"Producto actualizado correctamente\"}").build();//se retorna un estado OK
                                                                                                 //y un mensaje de éxito
        } else {//en caso de que la variable actualizado sea falsa
            return Response.status(Response.Status.NOT_FOUND)//se retorna un estado NOT_FOUND
                           .entity("{\"error\": \"No se pudo actualizar el producto\"}")//y se retorna un mensaje de error
                           .build();
        }
    }
    
    @TienePermiso("productos.eliminar")
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarProducto(@PathParam("id") int id) {
        //se crea el método que va a responder a la solicitud DELETE del endpoint productos/{id},
        //este método recibe como parámetro en la URL el ID del producto que se desea eliminar
        
        ProductosDAO dao = new ProductosDAO();//se crea una instancia de la clase ProductosDAO
        Producto producto = dao.getById(id);//se declara el objeto producto, en esta se almacenará el retorno del método
                                            //getById de la clase ProductosDAO
        
        producto.setId_estado_producto(3);//se le envía al objeto producto el id de estado 3 (eliminado)
        boolean eliminado = dao.put(producto);//se declara la variable eliminada de tipo booleana, en esta se almacenará
                                              //el retorno del método put de la clase ProductosDAO

        if (eliminado) {//si la variable eliminado es verdadera
            return Response.ok("{\"mensaje\":\"Producto eliminado correctamente\"}").build();//se retorna un estado OK
                                                                                              //y un mensaje de éxito
        } else {//en caso de que la variable eliminado sea falsa
            return Response.status(Response.Status.NOT_FOUND)//se retorna un estado NOT_FOUND
                           .entity("{\"error\":\"Producto no encontrado o no se pudo eliminar\"}")//y se retorna un mensaje de error
                           .build();
        }
    }
}
