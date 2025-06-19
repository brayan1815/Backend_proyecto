/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import MODELO.Rol;
import MODELO.RolesDAO;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Brayan Estiven
 */
@Path("/roles")
public class RolesController {
    RolesDAO dao=new RolesDAO();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Rol> obtenerUsuarios() {
        return dao.get();
    }
}
