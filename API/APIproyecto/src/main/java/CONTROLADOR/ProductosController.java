/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import MODELO.Producto;
import MODELO.ProductosDAO;
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

@Path("/productos")
public class ProductosController {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearProducto(Producto producto) {
        
        String error = ValidadorProducto.validarProducto(producto);
        if (error != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        ProductosDAO dao = new ProductosDAO();
        boolean creado = dao.post(producto);

        if (creado) {
            return Response.status(Response.Status.CREATED).entity("{\"mensaje\":\"Producto creado correctamente\"}").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"mensaje\":\"Error al crear producto\"}").build();
        }
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerProductos() {
        ProductosDAO dao = new ProductosDAO();
        List<Producto> productos = dao.getAll();
        return Response.ok(productos).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerProductoPorId(@PathParam("id") int id) {
        ProductosDAO dao = new ProductosDAO();
        Producto producto = dao.getById(id);

        if (producto != null) {
            return Response.ok(producto).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"error\":\"Producto no encontrado\"}")
                       .build();
        }
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarProducto(@PathParam("id") int id, Producto producto) {
        producto.setId(id);
        
        String error = ValidadorProducto.validarProducto(producto);
        if (error != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        ProductosDAO dao = new ProductosDAO();
    
        boolean actualizado = dao.put(producto);

        if (actualizado) {
            return Response.ok("{\"mensaje\": \"Producto actualizado correctamente\"}").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"error\": \"No se pudo actualizar el producto\"}")
                       .build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarProducto(@PathParam("id") int id) {
        ProductosDAO dao = new ProductosDAO();
        boolean eliminado = dao.Delete(id);

        if (eliminado) {
            return Response.ok("{\"mensaje\":\"Producto eliminado correctamente\"}").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Producto no encontrado o no se pudo eliminar\"}").build();
        }
}


}
