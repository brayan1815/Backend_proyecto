
package MODELO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/bd_proyecto_brayan";
    private static final String USER = "brayan_adso2894667";
    private static final String PASS = "Aprendiz2025";

    public static Connection getConnection() throws SQLException {
        try {
           // Carga explícita del driver
           Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error al cargar el driver de MySQL", e);
        }

        return DriverManager.getConnection(URL, USER, PASS);
    }
}
