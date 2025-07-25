
package MODELO;

import static MODELO.ConexionBD.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PagosDAO {
    public boolean post(int idFactura, int idMetodo) {
        //se crea el metodo para registrar un nuevo pago
        boolean exito = false;//se declara la variable exito y se inicializa como false

        try (Connection conn = ConexionBD.getConnection()) {//se establece la conexion a la base de datos
            String sql = "INSERT INTO pagos (id_factura, id_metodo) VALUES (?, ?)";//se crea la consulta SQL
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            stmt.setInt(1, idFactura);
            stmt.setInt(2, idMetodo);//se reemplazan los parametros de la consulta

            exito = stmt.executeUpdate() > 0;//se ejecuta la consulta y se almacena el resultado de la condicion
                                            //en la variable exito

        } catch (SQLException e) {
            e.printStackTrace();//se captutan y se manejan los errores
        }

        return exito;//se retorna la variable exito
    }
    
    
    public List<Pago> obtenerPagosPorMetodo(int idMetodo) {
        List<Pago> lista = new ArrayList<>();

        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM pagos WHERE id_metodo = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idMetodo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pago p = new Pago();
                p.setId(rs.getInt("id"));
                p.setId_factura(rs.getInt("id_factura"));
                p.setId_metodo(rs.getInt("id_metodo"));
                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
