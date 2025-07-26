
package MODELO;

import BD.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ConsumosDAO {
    public boolean post(Consumo consumo) {
        //metodo para registrar un consumo nuevo
        boolean exito = false;//se declara la variable exito y se inicializa como false

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexion a la base de datos
            //se crea la consulta SQL
            String sql = "INSERT INTO consumos (id_reserva, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta SQL

            //se reemolazan los parametros de la consulra
            stmt.setInt(1, consumo.getId_reserva());
            stmt.setInt(2, consumo.getId_producto());
            stmt.setInt(3, consumo.getCantidad());
            stmt.setDouble(4, consumo.getSubtotal());

            int filas = stmt.executeUpdate();//se ejecuta la consulta
            exito = filas > 0;//si se creo mas de 0 filas la variable exito es true

        } catch (SQLException e) {
            e.printStackTrace();//se imprimen los errores
        }

        return exito;//se retorna la variable exito
    }
    
    public List<Consumo> getByIdReserva(int idReserva) {
        //se crea el metodo para obtener los consumos por el ID de reserva
        List<Consumo> lista = new ArrayList<>();//se crea la lista en la cual se almacenaran los consumos

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexion a la base de datos
            //se crea la consulta SQL
            String sql = "SELECT id, id_reserva, id_producto, cantidad, subtotal FROM consumos WHERE id_reserva = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            stmt.setInt(1, idReserva);//se reemplazan los parametros de la consulta
            ResultSet rs = stmt.executeQuery();//se ejecuta la consulta SQL 

            while (rs.next()) {
                //mientras hayan filas se crea un objeto consumo se agrega a la lista
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
            e.printStackTrace();//si ocurre un error se imprime
        }

        return lista;//se retorna la lista
    }
        
    public Consumo getById(int id) {
        //se crea el metodo para obtener el consumo por su ID
        Consumo consumo = null;//se declara la variable consumo y se inicializa como null

            try (Connection conn = ConexionBD.getConnection()) {//se abre la conexion a la base de datos
                String sql = "SELECT * FROM consumos WHERE id = ?";//se crea la consulta SQL
                PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
                stmt.setInt(1, id);//se reemplazan los parametros de la consulta

                ResultSet rs = stmt.executeQuery();//se ejecuta la conulta

                if (rs.next()) {
                    //si hay algun resultao se crea el objeto consumo y se le asigna a la variable consumo
                    consumo = new Consumo();
                    consumo.setId(rs.getInt("id"));
                    consumo.setId_reserva(rs.getInt("id_reserva"));
                    consumo.setId_producto(rs.getInt("id_producto"));
                    consumo.setCantidad(rs.getInt("cantidad"));
                    consumo.setSubtotal(rs.getDouble("subtotal"));
                }

            } catch (SQLException e) {
                e.printStackTrace();//si ocurre un error se imprime
            }

            return consumo;//se retorna la variable consumo, si no hay ninguno se retorna null
    }
    
    public boolean actualizarConsumo(Consumo consumo) {
        //se crea el metodo actualizar consumo
        boolean actualizado = false;//se declara la variable actualizado y se iniicializa como false

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexion a la base de datos
            String sql = "UPDATE consumos SET cantidad = ?, subtotal = ? WHERE id = ?";//se crea la consulta SQL
            PreparedStatement stmt = conn.prepareStatement(sql);// se prepara la consulta SQL
            stmt.setInt(1, consumo.getCantidad());//se reemplazan los parametros enviados en la consulta
            stmt.setDouble(2, consumo.getSubtotal());
            stmt.setInt(3, consumo.getId());

            actualizado = stmt.executeUpdate() > 0;//si se afectaron mas de 0 filas actualizaco es true
        } catch (SQLException e) {
            e.printStackTrace();//si ocurre un error se imprime
        }

        return actualizado;//se retorna la variable actualizado
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


