
package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionBD {
    //se define la url para la conexion a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/bd_proyecto_brayan";
    private static final String USER = "brayan_adso2894667";//nombre del usuario para conectarse a MySql
    private static final String PASS = "Aprendiz2025";//contraseña del usuario para acceder a MySql

    public static Connection getConnection() throws SQLException {//metodo para obtener la conexion a la base de datos
        try {
           // Carga explícita del driver
           Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            //si no se encuentra se lanza una exepcion 
            throw new SQLException("Error al cargar el driver de MySQL", e);
        }

        return DriverManager.getConnection(URL, USER, PASS);//si todo sale bien retorna la conexion activa a la BD
    }
}
