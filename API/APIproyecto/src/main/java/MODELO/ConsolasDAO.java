
package MODELO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ConsolasDAO {
    public List<Consola> getAll() {
        List<Consola> consolas = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT * FROM consolas";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Consola consola = new Consola();
                consola.setId(rs.getInt("id"));
                consola.setNombre(rs.getString("nombre"));
                consola.setDescripcion(rs.getString("descripcion"));
                consola.setId_tipo(rs.getInt("id_tipo"));
                consola.setId_estado(rs.getInt("id_estado"));
                consola.setId_imagen(rs.getInt("id_imagen"));

                consolas.add(consola);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return consolas;
    }
    
    public Consola getById(int id) {
        Consola consola = null;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT * FROM consolas WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                consola = new Consola();
                consola.setId(rs.getInt("id"));
                consola.setNombre(rs.getString("nombre"));
                consola.setDescripcion(rs.getString("descripcion"));
                consola.setId_tipo(rs.getInt("id_tipo"));
                consola.setId_estado(rs.getInt("id_estado"));
                consola.setId_imagen(rs.getInt("id_imagen"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consola;
    }
    
    public boolean post(Consola consola) {
        boolean exito = false;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "INSERT INTO consolas (nombre, descripcion, id_tipo, id_estado, id_imagen) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, consola.getNombre());
            stmt.setString(2, consola.getDescripcion());
            stmt.setInt(3, consola.getId_tipo());
            stmt.setInt(4, consola.getId_estado());
            stmt.setInt(5, consola.getId_imagen());

            int filas = stmt.executeUpdate();
            exito = filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }
    
    public boolean put(Consola consola) {
        boolean exito = false;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "UPDATE consolas SET nombre = ?, descripcion = ?, id_tipo = ?, id_estado = ?, id_imagen = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, consola.getNombre());
            stmt.setString(2, consola.getDescripcion());
            stmt.setInt(3, consola.getId_tipo());
            stmt.setInt(4, consola.getId_estado());
            stmt.setInt(5, consola.getId_imagen());
            stmt.setInt(6, consola.getId());

            int filas = stmt.executeUpdate();
            exito = filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }
    
    public boolean Delete(int id) {
        boolean eliminado = false;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "DELETE FROM consolas WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int filas = stmt.executeUpdate();
            eliminado = filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eliminado;
    }
}
