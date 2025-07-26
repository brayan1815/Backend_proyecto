
package MODELO;

import BD.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MetodosPagoDAO {
    
    public List<MetodoPago> getAll() {
        //se crea el metodo que va a obtener todos los metodos de pago
        List<MetodoPago> lista = new ArrayList<>();//se crea la lista que alacenara los metodos

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexion a la base de datos
            String sql = "SELECT * FROM metodos_pago";//se crea la consulta SQL
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta SQL
            ResultSet rs = stmt.executeQuery();//se ejecuta la consulta SQL

            while (rs.next()) {
                //se recorre cada uno de los resutados, se crea un bjeto y este se agrega a la lista 
                MetodoPago metodo = new MetodoPago();
                metodo.setId(rs.getInt("id"));
                metodo.setMetodoPago(rs.getString("metodo_pago"));
                lista.add(metodo);
            }

        } catch (SQLException e) {
            e.printStackTrace();//si ocurre algun error se imprime
        }

        return lista;//se retorna la lista
    }
}
