/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONFIG;

import MODELO.ConexionBD;
import java.sql.Connection;
import java.sql.SQLException;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class RestConfig extends Application{
    public RestConfig(){
        try(Connection conn = ConexionBD.getConnection()){
            System.out.println("CONEXION A LA BASE DE DATOS ESTABLECIDA");
        }catch(SQLException e){
            System.out.println("Ocurrio un error al conectar con la base de datos");
        }
    }
}
