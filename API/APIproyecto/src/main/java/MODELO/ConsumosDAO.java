
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
        
    public Consumo getById(int id) {
        Consumo consumo = null;

            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM consumos WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    consumo = new Consumo();
                    consumo.setId(rs.getInt("id"));
                    consumo.setId_reserva(rs.getInt("id_reserva"));
                    consumo.setId_producto(rs.getInt("id_producto"));
                    consumo.setCantidad(rs.getInt("cantidad"));
                    consumo.setSubtotal(rs.getDouble("subtotal"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return consumo;
    }
    
    public boolean actualizarConsumo(Consumo consumo) {
        boolean actualizado = false;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "UPDATE consumos SET cantidad = ?, subtotal = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, consumo.getCantidad());
            stmt.setDouble(2, consumo.getSubtotal());
            stmt.setInt(3, consumo.getId());

            actualizado = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return actualizado;
    }
    
    public boolean del(int id) {//se crea el metodo para eliminar el conusmo
        //se declara la variable eliminado y se iniicializa en falso
        boolean eliminado = false;

        try (Connection conn = ConexionBD.getConnection()) {//se establece la conexion con la base de datos 
            String sql = "DELETE FROM consumos WHERE id = ?";//se crea la consulta SQL
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            stmt.setInt(1, id);//se reemplazan los paramtros enviados en la conuslta, en este caso el id

            int filasAfectadas = stmt.executeUpdate();//se obtiene la cantidad de filas afectadas al realizar la 
                                                      //consulta
            eliminado = filasAfectadas > 0;//se le reasigna a la variable eliminado el valor que retorne la condicional
                                          //filaAfectadas>0

        } catch (SQLException e) {
            e.printStackTrace();//se captura y se manera el error
        }

        return eliminado;//se retorna la variable eliminado
    }
    
    public double calcularTotalPorReserva(int idReserva) {//se crea el metodo para calcular el total de los consumos
                                                          //de la reserva
        double total = 0.0;//se declara la variable total y se inicializa en cero

        try (Connection conn = ConexionBD.getConnection()) {//se establece la conexion a la base de datos
            
            String sql = "SELECT SUM(subtotal) FROM consumos WHERE id_reserva = ?";//se crea la conuslta SQL
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            stmt.setInt(1, idReserva);//se envia el parametro a la conuslta

            ResultSet rs = stmt.executeQuery();//se ejecuta la consulta SQL
            if (rs.next()) {
                total = rs.getDouble(1); // Obtenemos el valor de la suma
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de error
        }

        return total; // Retornamos el total calculado
    }




}


