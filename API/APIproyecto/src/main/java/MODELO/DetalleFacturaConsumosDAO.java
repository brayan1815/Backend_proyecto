
package MODELO;

import BD.ConexionBD;
import static BD.ConexionBD.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetalleFacturaConsumosDAO {
    public boolean insertar(int idFactura, int idProducto, String nombreProducto, int cantidad, double precioUnitario, double subtotal) {
       // Método para insertar un consumo en la tabla detalle_factura_consumos
        boolean exito = false;//se declara la variable exito y se inincializa en false

        try (Connection conn = getConnection()) {//se abre la conexion a la base de datos
            //se crea la consulta
            String sql = "INSERT INTO detalle_factura_consumos (id_factura, id_producto, nombre_producto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la cosnulta

            //se reemplazan los parametros de la consulra
            stmt.setInt(1, idFactura);
            stmt.setInt(2, idProducto);
            stmt.setString(3, nombreProducto);
            stmt.setInt(4, cantidad);
            stmt.setDouble(5, precioUnitario);
            stmt.setDouble(6, subtotal);

            int rows = stmt.executeUpdate();//se ejecuta la consulta
            exito = rows > 0;//si se afecto mas de una fila exito pasa a ser treue

        } catch (SQLException e) {
            e.printStackTrace();//si ocurre un error se imprime
        }

        return exito;//se retorna la variable exito
    }
    
    public List<DetalleFacturaConsumos> getByIdFactura(int idFactura) {
        // Método para obtener los detalles de factura por el ID de factura
        List<DetalleFacturaConsumos> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT * FROM detalle_factura_consumos WHERE id_factura = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idFactura);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                DetalleFacturaConsumos detalle = new DetalleFacturaConsumos(
                    rs.getInt("id"),
                    rs.getInt("id_factura"),
                    rs.getInt("id_producto"),
                    rs.getString("nombre_producto"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio_unitario"),
                    rs.getDouble("subtotal")
                );
                lista.add(detalle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
