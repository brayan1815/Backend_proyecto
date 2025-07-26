
package MODELO;

import BD.ConexionBD;
import static BD.ConexionBD.getConnection;
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
        //se crea el metodo que va a obtener los pagos por id del metodo de pago
        List<Pago> lista = new ArrayList<>();//se crea la lista que almacenara los pagos

        try (Connection con = getConnection()) {//se abre la conexion a la base de datos
            String sql = "SELECT * FROM pagos WHERE id_metodo = ?";//se crea la consulta SQL
            PreparedStatement ps = con.prepareStatement(sql);//se prepara la consulta SQL
            ps.setInt(1, idMetodo);//se reemplazan los parametros de la consulta
            ResultSet rs = ps.executeQuery();//se ejecuta la conuslta

            while (rs.next()) {
                //se recorre cada uno de lo resultados, se crea un objeto Pago y se agrega a la lista
                Pago p = new Pago();
                p.setId(rs.getInt("id"));
                p.setId_factura(rs.getInt("id_factura"));
                p.setId_metodo(rs.getInt("id_metodo"));
                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();//si ocurre algun error se imprime
        }

        return lista;//se retorna la lista
    }
}
