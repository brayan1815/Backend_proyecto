package MODELO;

import BD.ConexionBD;
import java.sql.Connection;          // Para la conexión a la base de datos
import java.sql.PreparedStatement;  // Para preparar consultas SQL con parámetros
import java.sql.ResultSet;           // Para manejar resultados de consultas
import java.sql.SQLException;        // Para manejo de excepciones SQL
import java.util.ArrayList;          // Para crear listas dinámicas
import java.util.List;               // Interfaz para listas

public class UsuariosDAO {

    // Método que obtiene todos los usuarios de la base de datos como objetos Usuario
    public List<Usuario> get() {
        List<Usuario> lista = new ArrayList<>(); // Lista para almacenar usuarios obtenidos

        try (Connection conn = ConexionBD.getConnection()) { // Abrir conexión a la BD
            // Consulta SQL para obtener todos los usuarios con sus campos relevantes
            String sql = "SELECT id,documento,nombre,telefono,correo,contrasenia,id_rol,id_estado FROM usuarios";
            PreparedStatement stmt = conn.prepareStatement(sql); // Preparar consulta
            ResultSet rs = stmt.executeQuery(); // Ejecutar consulta y obtener resultados

            while (rs.next()) { // Recorrer cada fila del resultado
                // Crear un objeto Usuario con los datos obtenidos
                Usuario u = new Usuario(
                    rs.getInt("id"),
                    rs.getLong("documento"),
                    rs.getString("nombre"),
                    rs.getLong("telefono"),
                    rs.getString("correo"),
                    rs.getString("contrasenia"),
                    rs.getInt("id_rol"),
                    rs.getInt("id_estado")
                );
                lista.add(u); // Agregar usuario a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir error en caso de excepción SQL
        }

        return lista; // Retornar lista de usuarios
    }

    // Método que obtiene todos los usuarios junto con el nombre de su rol (UsuarioDTO)
    public List<UsuarioDTO> getConRol() {
        List<UsuarioDTO> lista = new ArrayList<>(); // Lista para almacenar DTOs de usuarios

        try (Connection conn = ConexionBD.getConnection()) { // Abrir conexión
            // Consulta SQL con INNER JOIN para obtener usuarios junto con nombre de rol
            String sql = "SELECT \n" +
                         "    u.id AS id,\n" +
                         "    u.documento AS documento,\n" +
                         "    u.nombre,\n" +
                         "    u.telefono,\n" +
                         "    u.correo,\n" +
                         "    u.contrasenia,\n" +
                         "    r.rol AS rol,\n" +
                         "    u.id_estado\n" +
                         "FROM \n" +
                         "    usuarios u\n" +
                         "INNER JOIN \n" +
                         "    roles r ON u.id_rol = r.id;";
            PreparedStatement stmt = conn.prepareStatement(sql); // Preparar consulta
            ResultSet rs = stmt.executeQuery(); // Ejecutar consulta

            while (rs.next()) { // Recorrer resultados
                // Crear UsuarioDTO con datos obtenidos
                UsuarioDTO u = new UsuarioDTO(
                    rs.getInt("id"),
                    rs.getLong("documento"),
                    rs.getString("nombre"),
                    rs.getLong("telefono"),
                    rs.getString("correo"),
                    rs.getString("contrasenia"),
                    rs.getString("rol"),
                    rs.getInt("id_estado")
                );
                lista.add(u); // Agregar DTO a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar excepción
        }

        return lista; // Retornar lista con usuarios y roles
    }

    // Método para obtener un usuario por su id
    public Usuario getById(int id) {
        Usuario usuario = null; // Objeto para almacenar el usuario encontrado

        try (Connection conn = ConexionBD.getConnection()) { // Abrir conexión
            String sql = "SELECT id, documento, nombre, telefono, correo, contrasenia, id_rol,id_estado FROM usuarios WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql); // Preparar consulta
            stmt.setInt(1, id); // Asignar parámetro id
            ResultSet rs = stmt.executeQuery(); // Ejecutar consulta

            if (rs.next()) { // Si se encontró usuario
                // Crear objeto Usuario con datos obtenidos
                usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getLong("documento"),
                    rs.getString("nombre"),
                    rs.getLong("telefono"),
                    rs.getString("correo"),
                    rs.getString("contrasenia"),
                    rs.getInt("id_rol"),
                    rs.getInt("id_estado")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar excepción
        }

        return usuario; // Retornar usuario encontrado o null
    }

    // Método para obtener un usuario por su correo electrónico
    public Usuario getByCorreo(String correo) {
        Usuario usuario = null; // Objeto para almacenar el usuario

        try (Connection conn = ConexionBD.getConnection()) { // Abrir conexión
            // Consulta para buscar usuario por correo
            String sql = "SELECT id, documento, nombre, telefono, correo, contrasenia, id_rol,id_estado FROM usuarios WHERE correo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql); // Preparar consulta
            stmt.setString(1, correo); // Asignar parámetro correo
            ResultSet rs = stmt.executeQuery(); // Ejecutar consulta

            if (rs.next()) { // Si se encontró usuario
                usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getLong("documento"),
                    rs.getString("nombre"),
                    rs.getLong("telefono"),
                    rs.getString("correo"),
                    rs.getString("contrasenia"),
                    rs.getInt("id_rol"),
                    rs.getInt("id_estado")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar excepción
        }

        return usuario; // Retornar usuario o null
    }

    // Método para obtener un usuario por su número de documento
    public Usuario getByDocumento(long documento) {
        Usuario usuario = null; // Objeto para almacenar el usuario

        try (Connection conn = ConexionBD.getConnection()) { // Abrir conexión
            String sql = "SELECT * FROM usuarios WHERE documento = ?";
            PreparedStatement stmt = conn.prepareStatement(sql); // Preparar consulta
            stmt.setLong(1, documento); // Asignar parámetro documento
            ResultSet rs = stmt.executeQuery(); // Ejecutar consulta

            if (rs.next()) { // Si se encontró usuario
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setDocumento(rs.getLong("documento"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setTelefono(rs.getLong("telefono"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setContrasenia(rs.getString("contrasenia"));
                usuario.setId_rol(rs.getInt("id_rol"));
                usuario.setId_estado(rs.getInt("id_estado"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar excepción
        }

        return usuario; // Retornar usuario o null
    }

    // Método para insertar un nuevo usuario en la base de datos
    public boolean post(Usuario usuario) {
        try (Connection conn = ConexionBD.getConnection()) { // Abrir conexión
            // Consulta para insertar un nuevo usuario con todos sus campos
            String sql = "INSERT INTO usuarios (documento, nombre, telefono, correo, contrasenia, id_rol,id_estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql); // Preparar consulta
            stmt.setLong(1, usuario.getDocumento());
            stmt.setString(2, usuario.getNombre());
            stmt.setLong(3, usuario.getTelefono());
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, usuario.getContrasenia());
            stmt.setInt(6, usuario.getId_rol());
            stmt.setInt(7, usuario.getId_estado());

            int filas = stmt.executeUpdate(); // Ejecutar inserción y obtener filas afectadas
            return filas > 0; // Retornar true si se insertó al menos un registro
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar excepción
            return false; // Retornar false en caso de error
        }
    }

    // Método para actualizar los datos de un usuario existente (sin modificar contraseña)
    public boolean put(int id, Usuario usuario) {
        String sql = "UPDATE usuarios SET documento = ?, nombre = ?, telefono = ?, correo = ?, id_rol = ?,id_estado=? WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Abrir conexión y preparar consulta

            // Asignar parámetros con los datos a actualizar
            stmt.setLong(1, usuario.getDocumento());
            stmt.setString(2, usuario.getNombre());
            stmt.setLong(3, usuario.getTelefono());
            stmt.setString(4, usuario.getCorreo());
            stmt.setInt(5, usuario.getId_rol());
            stmt.setInt(6, usuario.getId_estado());
            stmt.setInt(7, id);

            int filas = stmt.executeUpdate(); // Ejecutar actualización
            return filas > 0; // Retornar true si se actualizó algún registro
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar excepción
            return false; // Retornar false en caso de error
        }
    }

    // Método para eliminar un usuario por su id
    public boolean delete(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Abrir conexión y preparar consulta

            stmt.setInt(1, id); // Asignar parámetro id
            int filas = stmt.executeUpdate(); // Ejecutar eliminación
            return filas > 0; // Retornar true si se eliminó algún registro
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar excepción
            return false; // Retornar false en caso de error
        }
    }

}
