
package MODELO;

import static MODELO.ConexionBD.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FacturasDAO {
    public int post(int idReserva, double totalGeneral) {
        //se crea el metodo que va a registrar la factura
        int idGenerado = -1;//se declara la variable idGenerado y se inicializa en -1
        try (Connection conn = getConnection()) {//se obtiene la conexion a la base de datos
            String sql = "INSERT INTO facturas (id_reserva, total) VALUES (?, ?)";//se crea la consulta
            //se prepara la consulta y se retornan la llave generadas
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, idReserva);
            stmt.setDouble(2, totalGeneral);//se reemplazan los parametros de la sonsuñta

            int rows = stmt.executeUpdate();//se ejecuenta la consulta ys ealamcenan las columnas afectadas
            if (rows > 0) {//si se afecto ma de una columna
                ResultSet generatedKeys = stmt.getGeneratedKeys();//se obtiene la key
                if (generatedKeys.next()) {
                    idGenerado = generatedKeys.getInt(1); // este es el id de la factura recién insertada
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idGenerado; // devuelve el ID o -1 si falló
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
    
    public Factura getById(int idFactura) {
        //se crea el metodo para obtener la factura por id
        Factura factura = null;//se declara la variable factura y se inicializa como nula

        try (Connection conn = ConexionBD.getConnection()) {//se establece la conexion a la base de datos
            String sql = "SELECT * FROM facturas WHERE id = ?";//se crea la consulta SQL
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta SQL
            stmt.setInt(1, idFactura);//se reemplazan los parametros de la consulta
            ResultSet rs = stmt.executeQuery();//se ejecuta la consulta y el resultado se almacena en la variable rs

            if (rs.next()) {
                factura = new Factura();//se crea una nueva factura con ayuda del modelo
                factura.setId(rs.getInt("id"));
                factura.setIdReserva(rs.getInt("id_reserva"));
                factura.setTotal(rs.getDouble("total"));
            }

        } catch (SQLException e) {
            e.printStackTrace();//se capturtan los errores y se manejan 
        }

        return factura;//se retorna la variable factura
    }

}
