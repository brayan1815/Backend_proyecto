package MODELO;

import BD.ConexionBD; // Importa la clase encargada de manejar la conexión a la base de datos
import java.sql.Connection; // Clase para manejar la conexión a la base de datos
import java.sql.PreparedStatement; // Clase para preparar sentencias SQL con parámetros
import java.sql.ResultSet; // Clase para almacenar y recorrer resultados de consultas SQL
import java.util.ArrayList; // Implementación de la interfaz List que permite listas dinámicas
import java.util.List; // Interfaz de lista genérica

public class EstadoTipoDAO {

    /**
     * Método que obtiene todos los registros de la tabla "estados_tipos"
     * y los devuelve como una lista de objetos EstadoTipo.
     *
     * @return lista con todos los estados de tipo encontrados.
     */
    public List<EstadoTipo> getAll() {
        // Lista donde se guardarán los objetos EstadoTipo
        List<EstadoTipo> lista = new ArrayList<>();

        // Uso de try-with-resources para cerrar automáticamente la conexión al finalizar
        try (Connection conn = ConexionBD.getConnection()) {
            // Consulta SQL para obtener todos los registros
            String sql = "SELECT * FROM estados_tipos";
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepara la sentencia SQL
            ResultSet rs = stmt.executeQuery(); // Ejecuta la consulta y obtiene los resultados

            // Recorre cada fila del resultado y la convierte en un objeto EstadoTipo
            while (rs.next()) {
                EstadoTipo e = new EstadoTipo();
                e.setId(rs.getInt("id")); // Asigna el ID desde la columna "id"
                e.setEstado(rs.getString("estado")); // Asigna el estado desde la columna "estado"
                lista.add(e); // Agrega el objeto a la lista
            }

        } catch (Exception e) {
            // Muestra el error en caso de excepción
            e.printStackTrace();
        }

        // Retorna la lista con todos los registros encontrados
        return lista;
    }  
}
