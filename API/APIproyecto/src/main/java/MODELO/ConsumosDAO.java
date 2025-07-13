
package MODELO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ConsumosDAO {
    public boolean post(Consumo consumo) {
        boolean exito = false;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "INSERT INTO consumos (id_reserva, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, consumo.getId_reserva());
            stmt.setInt(2, consumo.getId_producto());
            stmt.setInt(3, consumo.getCantidad());
            stmt.setDouble(4, consumo.getSubtotal());

            int filas = stmt.executeUpdate();
            exito = filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }
    
        public List<Consumo> getByIdReserva(int idReserva) {
        List<Consumo> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT id, id_reserva, id_producto, cantidad, subtotal FROM consumos WHERE id_reserva = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idReserva);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Consumo consumo = new Consumo(
                    rs.getInt("id"),
                    rs.getInt("id_reserva"),
                    rs.getInt("id_producto"),
                    rs.getInt("cantidad"),
                    rs.getDouble("subtotal")
                );
                lista.add(consumo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}


