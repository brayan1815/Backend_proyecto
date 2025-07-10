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
      
    public boolean put(Reserva reserva) {
        boolean exito = false;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "UPDATE reservas SET id_usuario = ?, id_consola = ?, id_estado_reserva = ?, hora_inicio = ?, hora_finalizacion = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, reserva.getId_usuario());
            stmt.setInt(2, reserva.getId_consola());
            stmt.setInt(3, reserva.getId_estado_reserva());
            stmt.setTimestamp(4, Timestamp.valueOf(reserva.getHora_inicio()));
            stmt.setTimestamp(5, Timestamp.valueOf(reserva.getHora_finalizacion()));
            stmt.setInt(6, reserva.getId());

            int filas = stmt.executeUpdate();
            exito = filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }


}
