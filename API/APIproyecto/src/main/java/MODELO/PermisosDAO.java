
package MODELO;

import BD.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PermisosDAO {
     public List<String> getPermisosByRol(int id_rol) {
        List<String> permisos = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConnection()) { // se abre la conexión a la base de datos
            String sql = "SELECT \n" +
                        "    p.permiso\n" +
                        "FROM roles r\n" +
                        "INNER JOIN permisos_roles pr ON r.id = pr.id_rol\n" +
                        "INNER JOIN permisos p ON pr.id_permiso = p.id\n" +
                        "WHERE r.id=?"; // consulta SQL para obtener loes permisos correspondientes a cada rol
            PreparedStatement stmt = conn.prepareStatement(sql); // se prepara la consulta
            stmt.setInt(1, id_rol);
            ResultSet rs = stmt.executeQuery(); // se ejecuta la consulta y se obtiene el resultado

            while (rs.next()) { // mientras haya registros en el resultado
                permisos.add(""+rs.getString("permiso")+""); // se agrega el permiso a la lista
            }

        } catch (Exception e) {
            e.printStackTrace(); // en caso de error, se imprime la traza del error
        }

        return permisos; // se retorna la lista con todos los permisos correspondientes al rol
    }
}
