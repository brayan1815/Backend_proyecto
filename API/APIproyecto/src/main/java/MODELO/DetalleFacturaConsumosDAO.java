
package MODELO;

import static MODELO.ConexionBD.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DetalleFacturaConsumosDAO {
    public boolean insertar(int idFactura, int idProducto, String nombreProducto, int cantidad, double precioUnitario, double subtotal) {
        boolean exito = false;

        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO detalle_factura_consumos (id_factura, id_producto, nombre_producto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idFactura);
            stmt.setInt(2, idProducto);
            stmt.setString(3, nombreProducto);
            stmt.setInt(4, cantidad);
            stmt.setDouble(5, precioUnitario);
            stmt.setDouble(6, subtotal);

            int rows = stmt.executeUpdate();
            exito = rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }
}
