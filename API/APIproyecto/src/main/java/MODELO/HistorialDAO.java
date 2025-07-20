
package MODELO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class HistorialDAO {
    public boolean post(int idReserva) {
        //se crea el metodo para realizar un registro en la tabla historial
        boolean exito = false;//se declara la variable exito y se inicializa como falsa

        try (Connection conn = ConexionBD.getConnection()) {//se establece la conexion a la base de datos
            String sql = "INSERT INTO historial (id_reserva) VALUES (?)";//se crea la consulta SQL
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta SQL
            stmt.setInt(1, idReserva);//se reemplazan los parametros de la consulta SQL

            exito = stmt.executeUpdate() > 0;//se ejecuta la consulta sql y se almacena el resultado de la condicion
                                            //en la variable exito

        } catch (SQLException e) {
            e.printStackTrace();//se captutan y se manejan los errores
        }

        return exito;//se retorna la variable exito 
    }
}
