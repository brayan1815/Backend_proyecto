package MODELO;

import BD.ConexionBD;
import static BD.ConexionBD.getConnection;
import java.sql.*;

public class FacturasDAO {

    // Método para insertar una nueva factura en la base de datos
    // Recibe id de reserva, minutos usados, subtotales y total
    // Retorna el id generado de la factura insertada, o -1 si falla
    public int post(int idReserva, int minutos, double subtotalConsumos, double subtotalConsola, double total) {
        int idGenerado = -1; // valor por defecto si no se genera id
        String sql = "INSERT INTO facturas (id_reserva, minutos, subtotal_consumos, subtotal_consola, total) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Asignar valores a los parámetros de la consulta
            stmt.setInt(1, idReserva);
            stmt.setInt(2, minutos);
            stmt.setDouble(3, subtotalConsumos);
            stmt.setDouble(4, subtotalConsola);
            stmt.setDouble(5, total);

            int rows = stmt.executeUpdate(); // ejecutar la inserción

            if (rows > 0) {
                // Obtener la clave generada (id) de la factura insertada
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    idGenerado = keys.getInt(1); // asignar id generado
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // mostrar error en consola si ocurre excepción
        }
        return idGenerado; // retornar id generado o -1 si hubo error
    }

    // Método para obtener una factura por el id de la reserva
    // Retorna un objeto Factura con todos los datos, o null si no existe
    public Factura getByReserva(int idReserva) {
        Factura factura = null;
        String sql = "SELECT * FROM facturas WHERE id_reserva = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idReserva); // asignar parámetro idReserva
            ResultSet rs = stmt.executeQuery(); // ejecutar consulta

            if (rs.next()) {
                // Crear objeto Factura y asignar valores desde la base de datos
                factura = new Factura();
                factura.setId(rs.getInt("id"));
                factura.setIdReserva(rs.getInt("id_reserva"));
                factura.setMinutos(rs.getInt("minutos"));
                factura.setSubtotalConsumos(rs.getDouble("subtotal_consumos"));
                factura.setSubtotalConsola(rs.getDouble("subtotal_consola"));
                factura.setTotal(rs.getDouble("total"));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // mostrar error si hay excepción SQL
        }
        return factura; // retornar factura o null si no se encontró
    }

    // Método para obtener una factura por su id
    // Retorna objeto Factura con todos los datos o null si no existe
    public Factura getById(int idFactura) {
        Factura factura = null;
        String sql = "SELECT * FROM facturas WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFactura); // asignar parámetro idFactura
            ResultSet rs = stmt.executeQuery(); // ejecutar consulta

            if (rs.next()) {
                // Crear objeto Factura y asignar los datos obtenidos
                factura = new Factura();
                factura.setId(rs.getInt("id"));
                factura.setIdReserva(rs.getInt("id_reserva"));
                factura.setMinutos(rs.getInt("minutos"));
                factura.setSubtotalConsumos(rs.getDouble("subtotal_consumos"));
                factura.setSubtotalConsola(rs.getDouble("subtotal_consola"));
                factura.setTotal(rs.getDouble("total"));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // mostrar error si ocurre excepción
        }
        return factura; // retornar factura o null si no existe
    }

    // Método para actualizar los subtotales y total de una factura existente
    public void actualizarTotales(int idFactura, double subtotalConsumos, double subtotalConsola, double total) {
        String sql = "UPDATE facturas SET subtotal_consumos = ?, subtotal_consola = ?, total = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Asignar valores a los parámetros de actualización
            stmt.setDouble(1, subtotalConsumos);
            stmt.setDouble(2, subtotalConsola);
            stmt.setDouble(3, total);
            stmt.setInt(4, idFactura);

            stmt.executeUpdate(); // ejecutar actualización

        } catch (SQLException e) {
            e.printStackTrace(); // imprimir error si hay excepción
        }
    }

    // Método que calcula el total acumulado de todos los pagos realizados
    // Retorna la suma de la columna total en la tabla facturas
    public double calcularTotalPagos() {
        double total = 0.0;
        String sql = "SELECT SUM(total) FROM facturas";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                total = rs.getDouble(1); // obtener suma total de pagos
            }

        } catch (SQLException e) {
            e.printStackTrace(); // mostrar error si ocurre excepción
        }
        return total; // retornar suma total
    }
}
