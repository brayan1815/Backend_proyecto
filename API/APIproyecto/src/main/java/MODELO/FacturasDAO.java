
package MODELO;

import static MODELO.ConexionBD.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FacturasDAO {
    public int post(int idReserva, int minutos, double precioHora, double subtotalConsola, double subtotalConsumos, double total) {
        int idGenerado = -1;

        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO facturas (id_reserva, minutos, precio_consola_por_hora, subtotal_consola, subtotal_consumos, total) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, idReserva);
            stmt.setInt(2, minutos);
            stmt.setDouble(3, precioHora);
            stmt.setDouble(4, subtotalConsola);
            stmt.setDouble(5, subtotalConsumos);
            stmt.setDouble(6, total);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idGenerado = generatedKeys.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idGenerado;
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
                factura.setMinutos(rs.getInt("minutos"));
                factura.setSubtotalConsola(rs.getDouble("subtotal_consola"));
                factura.setSubtotalConsumos(rs.getDouble("subtotal_consumos"));
                factura.setTotal(rs.getDouble("total"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return factura;
    }
    
    public Factura getById(int idFactura) {
        Factura factura = null;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT * FROM facturas WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idFactura);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                factura = new Factura();
                factura.setId(rs.getInt("id"));
                factura.setIdReserva(rs.getInt("id_reserva"));
                factura.setMinutos(rs.getInt("minutos"));
                factura.setSubtotalConsola(rs.getDouble("subtotal_consola"));
                factura.setSubtotalConsumos(rs.getDouble("subtotal_consumos"));
                factura.setTotal(rs.getDouble("total"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return factura;
    }
    
    public void actualizarTotales(int idFactura, double subtotalConsumos, double total) {
        try (Connection conn = getConnection()) {
            String sql = "UPDATE facturas SET subtotal_consumos = ?, total = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, subtotalConsumos);
            stmt.setDouble(2, total);
            stmt.setInt(3, idFactura);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public double calcularTotalPagos() {
        double total = 0.0;

        try (Connection conn = ConexionBD.getConnection()) {
            
            String sql = "SELECT SUM(total) FROM facturas;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getDouble(1); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total; 
    }

}
