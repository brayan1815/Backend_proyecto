
package MODELO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;



public class UsuariosDAO {

    public List<Usuario> get(){
        List<Usuario> lista = new ArrayList<>();
        
        try(Connection conn = ConexionBD.getConnection()){
            String sql="SELECT id,documento,nombre,telefono,correo,contrasenia,id_rol FROM usuarios";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Usuario u = new Usuario(
                    rs.getInt("id"),
                    rs.getInt("documento"),
                    rs.getString("nombre"),
                    rs.getLong("telefono"),
                    rs.getString("correo"),
                    rs.getString("contrasenia"),
                    rs.getInt("id_rol")
                );
                lista.add(u);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return lista;
    }
    
    public Usuario getById(int id) {
        Usuario usuario = null;
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT id, documento, nombre, telefono, correo, contrasenia, id_rol FROM usuarios WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getInt("documento"),
                    rs.getString("nombre"),
                    rs.getLong("telefono"),
                    rs.getString("correo"),
                    rs.getString("contrasenia"),
                    rs.getInt("id_rol")
                );
            }
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return usuario;
    }
    
    public boolean post(Usuario usuario) {
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "INSERT INTO usuarios (documento, nombre, telefono, correo, contrasenia, id_rol) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usuario.getDocumento());
            stmt.setString(2, usuario.getNombre());
            stmt.setLong(3, usuario.getTelefono());
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, usuario.getContrasenia());
            stmt.setInt(6, usuario.getId_rol());

            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) 
        {
            e.printStackTrace();
            return false;
        }   
    }
    
    public boolean put(int id, Usuario usuario) {
        String sql = "UPDATE usuarios SET documento = ?, nombre = ?, telefono = ?, correo = ?, contrasenia = ?, id_rol = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuario.getDocumento());
            stmt.setString(2, usuario.getNombre());
            stmt.setLong(3, usuario.getTelefono());
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, usuario.getContrasenia());
            stmt.setInt(6, usuario.getId_rol());
            stmt.setInt(7, id);

            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
        
            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            return filas > 0;      
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    

}
