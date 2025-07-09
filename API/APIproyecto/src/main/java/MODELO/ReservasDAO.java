/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ReservasDAO {
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
}
