/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ImagenesDAO {

    public int post(String rutaRelativa) {
        int idGenerado = -1;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "INSERT INTO imagenes (ruta) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, rutaRelativa);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idGenerado;
    }

    public Imagen getById(int id) {
        Imagen imagen = null;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT ruta FROM imagenes WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                imagen = new Imagen(id, rs.getString("ruta")); // solo la ruta relativa
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return imagen;
    }
}
