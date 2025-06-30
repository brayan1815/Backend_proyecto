/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TiposDAO {
    public List<Tipo> getAll() {
        List<Tipo> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT * FROM tipos";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Tipo t = new Tipo();
                t.setId(rs.getInt("id"));
                t.setTipo(rs.getString("tipo"));
                t.setPrecio_hora(rs.getDouble("precio_hora"));
                lista.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public Tipo getById(int id) {
        Tipo tipo = null;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT * FROM tipos WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tipo = new Tipo();
                tipo.setId(rs.getInt("id"));
                tipo.setTipo(rs.getString("tipo"));
                tipo.setPrecio_hora(rs.getDouble("precio_hora"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tipo;
    }

    
    public boolean post(Tipo tipo) {
        boolean exito = false;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "INSERT INTO tipos (tipo, precio_hora) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tipo.getTipo());
            stmt.setDouble(2, tipo.getPrecio_hora());

            int filas = stmt.executeUpdate();
            exito = filas > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exito;
    }
    
    public boolean put(Tipo tipo) {
        boolean actualizado = false;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "UPDATE tipos SET tipo = ?, precio_hora = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tipo.getTipo());
            stmt.setDouble(2, tipo.getPrecio_hora());
            stmt.setInt(3, tipo.getId());

            actualizado = stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return actualizado;
    }
    
    public boolean delete(int id) {
        boolean eliminado = false;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "DELETE FROM tipos WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            eliminado = stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eliminado;
    }


}
