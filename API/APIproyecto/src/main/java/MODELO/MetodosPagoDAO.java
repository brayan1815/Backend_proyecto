
package MODELO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MetodosPagoDAO {
    
    public List<MetodoPago> getAll() {
        List<MetodoPago> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT * FROM metodos_pago";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                MetodoPago metodo = new MetodoPago();
                metodo.setId(rs.getInt("id"));
                metodo.setMetodoPago(rs.getString("metodo_pago"));
                lista.add(metodo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
