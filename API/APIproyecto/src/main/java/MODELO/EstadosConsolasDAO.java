package MODELO;

import BD.ConexionBD; // Importa la clase para la conexión a la base de datos
import java.sql.Connection; // Importa la clase para manejar la conexión SQL
import java.sql.PreparedStatement; // Importa la clase para ejecutar consultas preparadas
import java.sql.ResultSet; // Importa la clase para manejar los resultados de una consulta
import java.util.ArrayList; // Importa la clase para crear listas dinámicas
import java.util.List; // Importa la interfaz List

public class EstadosConsolasDAO {

    // Método que obtiene todos los registros de la tabla 'estados_consolas'
    public List<EstadoConsola> getAll() {
        List<EstadoConsola> lista = new ArrayList<>(); // Lista donde se almacenarán los resultados

        try (Connection conn = ConexionBD.getConnection()) { // Abre la conexión a la base de datos
            String sql = "SELECT * FROM estados_consolas"; // Consulta SQL para obtener todos los registros
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepara la consulta
            ResultSet rs = stmt.executeQuery(); // Ejecuta la consulta y guarda el resultado

            // Recorre cada fila del resultado
            while (rs.next()) {
                EstadoConsola e = new EstadoConsola(); // Crea un nuevo objeto EstadoConsola
                e.setId(rs.getInt("id")); // Asigna el valor de la columna 'id'
                e.setEstado(rs.getString("estado")); // Asigna el valor de la columna 'estado'
                lista.add(e); // Agrega el objeto a la lista
            }

        } catch (Exception e) {
            e.printStackTrace(); // Muestra el error en caso de que ocurra
        }

        return lista; // Retorna la lista con los resultados
    }
}
