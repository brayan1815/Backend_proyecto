/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import BD.ConexionBD;
import java.sql.Connection;
import java.sql.SQLException;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class RestConfig extends Application{
    public RestConfig(){
        try(Connection conn = ConexionBD.getConnection()){//se abre la conexion a la base de datos
            //si al conexion se establece correctamente se muestra este mensaje
            System.out.println("CONEXION A LA BASE DE DATOS ESTABLECIDA");
        }catch(SQLException e){
            //si ocurre un error al obtener la conexion se muestra este mensaje
            System.out.println("OCURRIO UN ERROR AL INTENTAR CONECTAR CON LA BASE DE DATOS");
        }
    }
}
