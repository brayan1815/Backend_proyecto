
package MODELO;

import BD.ConexionBD;
import static BD.ConexionBD.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FacturasDAO {
    public int post(int idReserva, int minutos, double precioHora, double subtotalConsola, double subtotalConsumos, double total) {
        //se crea el metodo para crear una nueva factura, esye metodo devuelve el id despues de crearla
        int idGenerado = -1;//se declara la variable id generado y se inicializa en -1

        try (Connection conn = getConnection()) {//se abre la conexiona a la base de datos
            //se crea la consulra SQL
            String sql = "INSERT INTO facturas (id_reserva, minutos, precio_consola_por_hora, subtotal_consola, subtotal_consumos, total) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//se prepara la consulta
                                                                                       //esta retornara las keys generadas
             
            //re reemplazan los aprametros de la consulta
            stmt.setInt(1, idReserva);
            stmt.setInt(2, minutos);
            stmt.setDouble(3, precioHora);
            stmt.setDouble(4, subtotalConsola);
            stmt.setDouble(5, subtotalConsumos);
            stmt.setDouble(6, total);

            int rows = stmt.executeUpdate();//se ejecuta la consulta

            if (rows > 0) {//si se afectaron mas de 0 columnas
                //se obtienen las keys generadas
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {//si hay keys generadas
                    idGenerado = generatedKeys.getInt(1);//se le asigna a la variable idGenerado la primera key generada
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();//si ocurre un error se imprime
        }

        return idGenerado;//se retorna el id generado
    }

    public Factura getByReserva(int idReserva) {
        //se obtiene la factura por el id de reservas
        Factura factura = null;//se declara la variable factura y se inicializa en null

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexion a la base de datos
            String sql = "SELECT * FROM facturas WHERE id_reserva = ?";//e crea la consulta SQL
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            stmt.setInt(1, idReserva);//se reemplazan los parametros de la cosnulta
            ResultSet rs = stmt.executeQuery();//se ejecuta la consulta

            if (rs.next()) {
                //si la consulta trajo resulatdos se crea un nuevo objeto Factura y se asigna a la variable factura
                factura = new Factura();
                factura.setId(rs.getInt("id"));
                factura.setIdReserva(rs.getInt("id_reserva"));
                factura.setMinutos(rs.getInt("minutos"));
                factura.setSubtotalConsola(rs.getDouble("subtotal_consola"));
                factura.setSubtotalConsumos(rs.getDouble("subtotal_consumos"));
                factura.setTotal(rs.getDouble("total"));
            }

        } catch (SQLException e) {
            e.printStackTrace();//si ocurre algun error se imprime
        }

        return factura;//se retorna la facrtura
    }
    
    public Factura getById(int idFactura) {
        //se crea el metodo para obtener la factura por el id 
        Factura factura = null;//se declara la variable fatura y se inicializa como null

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexiona a la base de datos
            String sql = "SELECT * FROM facturas WHERE id = ?";//se ejecuta la conuslta SQL
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            stmt.setInt(1, idFactura);//se reemplazan los parametros de la consulta
            ResultSet rs = stmt.executeQuery();//se ejecuta la consulta

            if (rs.next()) {
                //si hay resultados se crea un nuevo objeto Factura y se asignaa la variable factura
                factura = new Factura();
                factura.setId(rs.getInt("id"));
                factura.setIdReserva(rs.getInt("id_reserva"));
                factura.setMinutos(rs.getInt("minutos"));
                factura.setSubtotalConsola(rs.getDouble("subtotal_consola"));
                factura.setSubtotalConsumos(rs.getDouble("subtotal_consumos"));
                factura.setTotal(rs.getDouble("total"));
            }

        } catch (SQLException e) {
            e.printStackTrace();//si ocurre un error se imprime
        }

        return factura;//se retorna la factura
    }
    
    //actualiza los totales de la factura si hubo algún cambio
    public void actualizarTotales(int idFactura, double subtotalConsumos, double total) {
        try (Connection conn = getConnection()) {//se establece la conexion a la base de datos
            //se crea la consulta SQL
            String sql = "UPDATE facturas SET subtotal_consumos = ?, total = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            stmt.setDouble(1, subtotalConsumos);//se reemplazan los parametros
            stmt.setDouble(2, total);
            stmt.setInt(3, idFactura);
            stmt.executeUpdate();//se ejecuta la consulta
        } catch (SQLException e) {
            e.printStackTrace();//si ocurre un error se imprime 
        }
    }
    
    public double calcularTotalPagos() {
        //se crea el metodo para calcular el total de los pagos
        double total = 0.0;//se declara la variable total y se inicializa en 0.0

        try (Connection conn = ConexionBD.getConnection()) {//se abre la coenxion a la base de datos
            
            String sql = "SELECT SUM(total) FROM facturas;";//se crea la consulta SQL
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            
            ResultSet rs = stmt.executeQuery();//se ejecuta la consulta
            if (rs.next()) {
                //si hay resultados se almacena en la variable total el primer resultado
                total = rs.getDouble(1); 
            }

        } catch (SQLException e) {
            e.printStackTrace();//si ocurre un error se imprime
        }

        return total; //se retorna la variable total
    }

}
