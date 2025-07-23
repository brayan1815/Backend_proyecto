/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class ReservasDAO {
    
    public List<Reserva> getAll() {
        List<Reserva> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT id, id_usuario, id_consola, id_estado_reserva, hora_inicio, hora_finalizacion FROM reservas ORDER BY hora_inicio ASC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reserva reserva = new Reserva(
                    rs.getInt("id"),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_consola"),
                    rs.getInt("id_estado_reserva"),
                    rs.getTimestamp("hora_inicio").toLocalDateTime(),
                    rs.getTimestamp("hora_finalizacion").toLocalDateTime()
                );
                lista.add(reserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    
      public boolean post(Reserva reserva) {
        boolean exito = false;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "INSERT INTO reservas (id_usuario, id_consola, id_estado_reserva, hora_inicio, hora_finalizacion) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, reserva.getId_usuario());
            stmt.setInt(2, reserva.getId_consola());
            stmt.setInt(3, reserva.getId_estado_reserva());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(reserva.getHora_inicio()));
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(reserva.getHora_finalizacion()));

            int filas = stmt.executeUpdate();
            exito = filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }
      
      public List<Reserva> getByIdConsola(int idConsola) {
        List<Reserva> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT id, id_usuario, id_consola, id_estado_reserva, hora_inicio, hora_finalizacion FROM reservas WHERE id_consola = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idConsola);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reserva reserva = new Reserva(
                    rs.getInt("id"),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_consola"),
                    rs.getInt("id_estado_reserva"),
                    rs.getTimestamp("hora_inicio").toLocalDateTime(),
                    rs.getTimestamp("hora_finalizacion").toLocalDateTime()
                );
                lista.add(reserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
      
      public Reserva getById(int id) {
        Reserva reserva = null;
        String sql = "SELECT * FROM reservas WHERE id = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                reserva = new Reserva(
                    rs.getInt("id"),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_consola"),
                    rs.getInt("id_estado_reserva"),
                    rs.getTimestamp("hora_inicio").toLocalDateTime(),
                    rs.getTimestamp("hora_finalizacion").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reserva;
    }
      
      public List<Reserva> getByIdUsuario(int idUsuario) {
          //Se crea el metodo para obtener las resevas por id de usuarios
        List<Reserva> lista = new ArrayList<>();//se crea la lista que almacenara las reservas

        try (Connection conn = ConexionBD.getConnection()) {//se establece la conexion a la base de datos con ayuda de la
                                                            //clase ConexionBD
            String sql = "SELECT * FROM reservas WHERE id_usuario = ?";//se crea la consulta SQL
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta SQL
            stmt.setInt(1, idUsuario);//se reemplazan los parametros de la consulta
            ResultSet rs = stmt.executeQuery();//se almacena el resultado de la consulta en la variable rs

            while (rs.next()) {//se recorre el resultado
                Reserva reserva = new Reserva(//se crea un nuevo objeto reserva
                    rs.getInt("id"),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_consola"),
                    rs.getInt("id_estado_reserva"),
                    rs.getTimestamp("hora_inicio").toLocalDateTime(),
                    rs.getTimestamp("hora_finalizacion").toLocalDateTime()
                );
                lista.add(reserva);//se agrega el objeto a la lista 
            }
        } catch (SQLException e) {
            e.printStackTrace();//se capturan y se manejan los errores
        }

        return lista;//se retorna la lista
    }

      
    public boolean put(Reserva reserva) {
        //se crea el metodo para actualizar la reserva
        boolean exito = false;//se declara la variable exito y se inicializa como falsa

        try (Connection conn = ConexionBD.getConnection()) {//se establece la conexion a la base de datos
            //se crea la consulta SQL
            String sql = "UPDATE reservas SET id_usuario = ?, id_consola = ?, id_estado_reserva = ?, hora_inicio = ?, hora_finalizacion = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta SQL
            
            //se reemplazan los parametros enviados en la conuslta SQL
            stmt.setInt(1, reserva.getId_usuario());
            stmt.setInt(2, reserva.getId_consola());
            stmt.setInt(3, reserva.getId_estado_reserva());
            stmt.setTimestamp(4, Timestamp.valueOf(reserva.getHora_inicio()));
            stmt.setTimestamp(5, Timestamp.valueOf(reserva.getHora_finalizacion()));
            stmt.setInt(6, reserva.getId());

            int filas = stmt.executeUpdate();//se ejecuta la consulta sql y se almacena la cantidad de filas afectadas
            exito = filas > 0;//se almacena en la variable exito el retorno de la condicional 

        } catch (SQLException e) {
            e.printStackTrace();//se atrapan y se manejan los errores
        }

        return exito;//se retorna la variable exito
    }


}
