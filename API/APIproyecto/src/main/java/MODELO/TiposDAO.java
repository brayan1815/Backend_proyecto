package MODELO;

import BD.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TiposDAO {
    // Método para obtener todos los registros de la tabla tipos
    public List<Tipo> getAll() {
        List<Tipo> lista = new ArrayList<>(); // lista para almacenar los tipos obtenidos

        try (Connection conn = ConexionBD.getConnection()) { // se abre la conexión a la base de datos
            String sql = "SELECT * FROM tipos"; // consulta SQL para obtener todos los tipos
            PreparedStatement stmt = conn.prepareStatement(sql); // se prepara la consulta
            ResultSet rs = stmt.executeQuery(); // se ejecuta la consulta y se obtiene el resultado

            while (rs.next()) { // mientras haya registros en el resultado
                Tipo t = new Tipo(); // se crea un objeto Tipo vacío
                t.setId(rs.getInt("id")); // se asigna el id obtenido de la BD
                t.setTipo(rs.getString("tipo")); // se asigna el nombre o descripción del tipo
                t.setPrecio_hora(rs.getDouble("precio_hora")); // se asigna el precio por hora
                t.set_id_estado_tipo(rs.getInt("id_estado_tipo")); // se asigna el estado del tipo
                lista.add(t); // se agrega el objeto Tipo a la lista
            }

        } catch (Exception e) {
            e.printStackTrace(); // en caso de error, se imprime la traza del error
        }

        return lista; // se retorna la lista con todos los tipos obtenidos
    }
    
    // Método para obtener un tipo por su id
    public Tipo getById(int id) {
        Tipo tipo = null; // objeto Tipo inicialmente nulo

        try (Connection conn = ConexionBD.getConnection()) { // se abre conexión a la BD
            String sql = "SELECT * FROM tipos WHERE id = ?"; // consulta SQL con parámetro para id
            PreparedStatement stmt = conn.prepareStatement(sql); // se prepara la consulta
            stmt.setInt(1, id); // se establece el valor del parámetro id

            ResultSet rs = stmt.executeQuery(); // se ejecuta la consulta
            if (rs.next()) { // si hay resultado
                tipo = new Tipo(); // se crea el objeto Tipo
                tipo.setId(rs.getInt("id")); // se asigna el id
                tipo.setTipo(rs.getString("tipo")); // se asigna el nombre o descripción
                tipo.setPrecio_hora(rs.getDouble("precio_hora")); // se asigna el precio por hora
                tipo.set_id_estado_tipo(rs.getInt("id_estado_tipo")); // se asigna el estado
            }

        } catch (Exception e) {
            e.printStackTrace(); // manejo de error
        }

        return tipo; // se retorna el objeto Tipo encontrado o null si no existe
    }
    
    // Método para obtener un tipo por su nombre o descripción
    public Tipo getByNombre(String nombre) {
        Tipo tipo = null; // objeto Tipo inicialmente nulo

        try (Connection conn = ConexionBD.getConnection()) { // abrir conexión
            String sql = "SELECT * FROM tipos WHERE tipo = ?"; // consulta SQL con parámetro nombre
            PreparedStatement stmt = conn.prepareStatement(sql); // preparar consulta
            stmt.setString(1, nombre); // asignar el parámetro nombre

            ResultSet rs = stmt.executeQuery(); // ejecutar consulta
            if (rs.next()) { // si hay resultado
                tipo = new Tipo(); // crear objeto Tipo
                tipo.setId(rs.getInt("id")); // asignar id
                tipo.setTipo(rs.getString("tipo")); // asignar nombre
                tipo.setPrecio_hora(rs.getDouble("precio_hora")); // asignar precio por hora
                // NO se asigna estado aquí, podría agregarse si es necesario
            }

        } catch (Exception e) {
            e.printStackTrace(); // manejo de error
        }

        return tipo; // retornar el objeto Tipo o null
    }

    // Método para insertar un nuevo tipo en la base de datos
    public boolean post(Tipo tipo) {
        boolean exito = false; // variable para indicar éxito o fracaso

        try (Connection conn = ConexionBD.getConnection()) { // abrir conexión
            String sql = "INSERT INTO tipos (tipo, precio_hora, id_estado_tipo) VALUES (?, ?, ?)"; // consulta insert
            PreparedStatement stmt = conn.prepareStatement(sql); // preparar consulta
            stmt.setString(1, tipo.getTipo()); // asignar nombre o descripción
            stmt.setDouble(2, tipo.getPrecio_hora()); // asignar precio por hora
            stmt.setInt(3, tipo.getId_estado_tipo()); // asignar estado

            int filas = stmt.executeUpdate(); // ejecutar insert y obtener filas afectadas
            exito = filas > 0; // éxito si filas afectadas es mayor a cero

        } catch (Exception e) {
            e.printStackTrace(); // manejo de error
        }

        return exito; // retornar resultado
    }
    
    // Método para actualizar un tipo existente
    public boolean put(Tipo tipo) {
        boolean actualizado = false; // variable para indicar si se actualizó correctamente

        try (Connection conn = ConexionBD.getConnection()) { // abrir conexión
            String sql = "UPDATE tipos SET tipo = ?, precio_hora = ?, id_estado_tipo = ? WHERE id = ?"; // consulta update
            PreparedStatement stmt = conn.prepareStatement(sql); // preparar consulta
            stmt.setString(1, tipo.getTipo()); // asignar nombre nuevo
            stmt.setDouble(2, tipo.getPrecio_hora()); // asignar precio nuevo
            stmt.setInt(3, tipo.id_estado_tipo); // asignar estado nuevo
            stmt.setInt(4, tipo.getId()); // asignar id del registro a actualizar

            actualizado = stmt.executeUpdate() > 0; // verdadero si filas afectadas > 0

        } catch (SQLException e) {
            e.printStackTrace(); // manejo de error
        }

        return actualizado; // retornar resultado
    }
    
    // Método para eliminar un tipo por su id
    public boolean delete(int id) {
        boolean eliminado = false; // variable para indicar si se eliminó correctamente

        try (Connection conn = ConexionBD.getConnection()) { // abrir conexión
            String sql = "DELETE FROM tipos WHERE id = ?"; // consulta delete
            PreparedStatement stmt = conn.prepareStatement(sql); // preparar consulta
            stmt.setInt(1, id); // asignar id a eliminar

            eliminado = stmt.executeUpdate() > 0; // verdadero si filas afectadas > 0

        } catch (SQLException e) {
            e.printStackTrace(); // manejo de error
        }

        return eliminado; // retornar resultado
    }
}
