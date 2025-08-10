package MODELO;

import BD.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstadosUsuariosDAO {

    // Método para obtener todos los registros de la tabla estados_usuarios
    public List<EstadosUsuarios> getAll() {
        // Lista donde se almacenarán los estados de usuarios obtenidos de la base de datos
        List<EstadosUsuarios> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection()) { // Establece la conexión con la base de datos
            String sql = "SELECT * FROM estados_usuarios"; // Consulta SQL para obtener todos los registros
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepara la sentencia SQL
            ResultSet rs = stmt.executeQuery(); // Ejecuta la consulta y obtiene el resultado

            // Recorre los resultados obtenidos
            while (rs.next()) {
                EstadosUsuarios e = new EstadosUsuarios(); // Crea un nuevo objeto EstadosUsuarios
                e.setId(rs.getInt("id")); // Asigna el valor del campo 'id' al objeto
                e.setEstado(rs.getString("estado")); // Asigna el valor del campo 'estado' al objeto
                lista.add(e); // Agrega el objeto a la lista
            }

        } catch (Exception e) { // Captura cualquier error que ocurra durante el proceso
            e.printStackTrace(); // Muestra el error en la consola
        }

        // Retorna la lista con todos los estados de usuarios obtenidos
        return lista;
    }
}
