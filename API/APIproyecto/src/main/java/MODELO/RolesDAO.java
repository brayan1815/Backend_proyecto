package MODELO;

import BD.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolesDAO {
    
    // método para obtener todos los roles de la base de datos
    public List<Rol> get() {
        List<Rol> lista = new ArrayList<>(); // se crea la lista que almacenará los roles
        
        try (Connection conn = ConexionBD.getConnection()) { // se abre la conexión a la base de datos
            String sql = "SELECT id, rol FROM roles"; // consulta SQL para obtener id y nombre del rol
            PreparedStatement stmt = conn.prepareStatement(sql); // se prepara la consulta
            ResultSet rs = stmt.executeQuery(); // se ejecuta la consulta y se obtiene el resultado
            
            while (rs.next()) { // se recorre cada fila del resultado
                // se crea un objeto Rol con los datos obtenidos
                Rol r = new Rol(
                    rs.getInt("id"), // se obtiene el id del rol
                    rs.getString("rol") // se obtiene el nombre del rol
                );
                lista.add(r); // se agrega el rol a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // en caso de error, se imprime la traza para depuración
        }
        
        return lista; // se retorna la lista con todos los roles obtenidos
    }
    
    // método para obtener un rol específico por su id
    public Rol getById(int id) {
        Rol rol = null; // variable para almacenar el rol encontrado (o null si no existe)
        
        try (Connection conn = ConexionBD.getConnection()) { // se abre la conexión a la base de datos
            String sql = "SELECT id, rol FROM roles WHERE id = ?"; // consulta SQL con parámetro para id
            PreparedStatement stmt = conn.prepareStatement(sql); // se prepara la consulta
            stmt.setInt(1, id); // se asigna el valor del id al parámetro
            
            ResultSet rs = stmt.executeQuery(); // se ejecuta la consulta
            
            if (rs.next()) { // si se encuentra un resultado
                rol = new Rol(); // se crea un objeto Rol vacío
                rol.setId(rs.getInt("id")); // se asigna el id obtenido
                rol.setRol(rs.getString("rol")); // se asigna el nombre del rol obtenido
            }
        } catch (SQLException e) {
            e.printStackTrace(); // en caso de error, se imprime la traza para depuración
        }
        
        return rol; // se retorna el rol encontrado o null si no existe
    }
}
