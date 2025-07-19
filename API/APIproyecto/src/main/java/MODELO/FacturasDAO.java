
package MODELO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacturasDAO {
    public boolean post(int idReserva, double total) {
        //se crea el metodo para insertar en la tabla facturas
        boolean exito = false;//se declara la variable exito y se inicializa como false

        try (Connection conn = ConexionBD.getConnection()) {//se establece la conexion a la base de datos
            String sql = "INSERT INTO facturas (id_reserva, total) VALUES (?, ?)";//se crea la consulta SQL
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            stmt.setInt(1, idReserva);//se reemplazan los parametros de la consulta
            stmt.setDouble(2, total);

            exito = stmt.executeUpdate() > 0;//se ejecuta la consulta y se reescribe el valor de la variable exito
                                            //por el resultado retornado por la condicion

        } catch (SQLException e) {
            e.printStackTrace();//se manejan los errores
        }

        return exito;//se retorna la variable exito
    }

    public Factura getByReserva(int idReserva) {
        Factura factura = null;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT * FROM facturas WHERE id_reserva = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idReserva);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                factura = new Factura();
                factura.setId(rs.getInt("id"));
                factura.setIdReserva(rs.getInt("id_reserva"));
                factura.setTotal(rs.getDouble("total"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return factura;
    }
}
