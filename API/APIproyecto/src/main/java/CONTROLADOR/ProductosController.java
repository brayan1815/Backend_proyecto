/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import MODELO.Producto;
import MODELO.ProductosDAO;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/productos")
public class ProductosController {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearProducto(Producto producto) {
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
}
