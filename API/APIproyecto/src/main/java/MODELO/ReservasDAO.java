package MODELO;

import BD.ConexionBD;
import java.sql.Connection; // clase que representa la conexión con la base de datos
import java.sql.PreparedStatement; // clase para preparar sentencias SQL con parámetros
import java.sql.ResultSet; // clase para manejar el resultado de las consultas SQL
import java.sql.SQLException; // clase para manejar excepciones relacionadas con SQL
import java.sql.Timestamp; // clase para manejar fechas y horas en SQL
import java.util.ArrayList; // clase para manejar listas dinámicas
import java.util.List; // interfaz para colecciones de datos tipo lista

public class ReservasDAO {
    
    // método que obtiene todas las reservas de la base de datos
    public List<Reserva> getAll() {
        List<Reserva> lista = new ArrayList<>(); // lista para almacenar las reservas obtenidas

        try (Connection conn = ConexionBD.getConnection()) { // abrir conexión con la base de datos
            // consulta SQL para obtener todos los campos relevantes de la tabla reservas ordenados por hora_inicio
            String sql = "SELECT id, id_usuario, id_consola, id_estado_reserva, hora_inicio, hora_finalizacion FROM reservas ORDER BY hora_inicio ASC";
            PreparedStatement stmt = conn.prepareStatement(sql); // preparar la consulta
            ResultSet rs = stmt.executeQuery(); // ejecutar la consulta y obtener resultados

            while (rs.next()) { // recorrer cada fila del resultado
                // crear un objeto Reserva con los datos obtenidos de la fila actual
                Reserva reserva = new Reserva(
                    rs.getInt("id"), // id de la reserva
                    rs.getInt("id_usuario"), // id del usuario que hizo la reserva
                    rs.getInt("id_consola"), // id de la consola reservada
                    rs.getInt("id_estado_reserva"), // estado de la reserva
                    rs.getTimestamp("hora_inicio").toLocalDateTime(), // convertir Timestamp a LocalDateTime
                    rs.getTimestamp("hora_finalizacion").toLocalDateTime() // convertir Timestamp a LocalDateTime
                );
                lista.add(reserva); // agregar el objeto Reserva a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // imprimir el error en caso de excepción SQL
        }

        return lista; // retornar la lista de reservas
    }
    
    // método que obtiene todas las reservas activas de un usuario específico
    public List<Reserva> getAllReservasActivasUsuario(int id_usuario) {
        List<Reserva> lista = new ArrayList<>(); // lista para almacenar las reservas activas

        try (Connection conn = ConexionBD.getConnection()) { // abrir conexión a la base de datos
            // consulta SQL para obtener reservas que no están en estado 3 ni 4 (activos) para un usuario dado
            String sql = "SELECT * FROM reservas WHERE id_estado_reserva!=4 AND id_estado_reserva!=3 AND id_usuario=?;";
            PreparedStatement stmt = conn.prepareStatement(sql); // preparar consulta
            stmt.setInt(1, id_usuario); // asignar parámetro id_usuario

            ResultSet rs = stmt.executeQuery(); // ejecutar consulta

            while (rs.next()) { // recorrer cada resultado
                // crear objeto Reserva con los datos obtenidos
                Reserva reserva = new Reserva(
                    rs.getInt("id"),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_consola"),
                    rs.getInt("id_estado_reserva"),
                    rs.getTimestamp("hora_inicio").toLocalDateTime(),
                    rs.getTimestamp("hora_finalizacion").toLocalDateTime()
                );
                lista.add(reserva); // agregar reserva a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // manejar posibles excepciones SQL
        }

        return lista; // retornar lista de reservas activas
    }
    
    // método que obtiene todas las reservas con información extendida (usuario y consola)
    public List<ReservaDTO> getAllConInfo() {
        List<ReservaDTO> lista = new ArrayList<>(); // lista para almacenar objetos ReservaDTO

        try (Connection conn = ConexionBD.getConnection()) { // abrir conexión
            // consulta SQL que une reservas, usuarios y consolas para obtener información combinada
            String sql = "SELECT \n" +
                         "    r.id,\n" +
                         "    u.documento AS documentoUsuario,\n" +
                         "    u.nombre AS nombreUsuario,\n" +
                         "    r.hora_inicio,\n" +
                         "    r.hora_finalizacion,\n" +
                         "    c.nombre AS nombreConsola,\n" +
                         "    r.id_estado_reserva AS idEstadoReserva\n" +
                         "FROM reservas r\n" +
                         "INNER JOIN usuarios u ON r.id_usuario = u.id\n" +
                         "INNER JOIN consolas c ON r.id_consola = c.id\n" +
                         "ORDER BY r.hora_inicio ASC;";
            PreparedStatement stmt = conn.prepareStatement(sql); // preparar consulta
            ResultSet rs = stmt.executeQuery(); // ejecutar consulta

            while (rs.next()) { // recorrer resultados
                // crear objeto ReservaDTO con los datos obtenidos
                ReservaDTO reserva = new ReservaDTO(
                    rs.getInt("id"),
                    rs.getLong("documentoUsuario"),
                    rs.getString("nombreUsuario"),
                    rs.getTimestamp("hora_inicio").toLocalDateTime(),
                    rs.getTimestamp("hora_finalizacion").toLocalDateTime(),
                    rs.getString("nombreConsola"),
                    rs.getInt("idEstadoReserva")
                );
                lista.add(reserva); // agregar objeto a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // manejo de errores
        }

        return lista; // retornar lista con info extendida
    }
    
    // método que obtiene todas las reservas con información extendida para el usuario
    public List<ReservaDTO> getAllConInfoByUser(int id) {
        List<ReservaDTO> lista = new ArrayList<>(); // lista para almacenar objetos ReservaDTO

        try (Connection conn = ConexionBD.getConnection()) { // abrir conexión
            // consulta SQL que une reservas, usuarios y consolas para obtener información combinada
            String sql = "SELECT \n" +
                        "	r.id,u.documento AS documentoUsuario,\n" +
                        "    u.nombre AS nombreUsuario,r.hora_inicio,\n" +
                        "    r.hora_finalizacion,\n" +
                        "    c.nombre AS nombreConsola,\n" +
                        "    r.id_estado_reserva AS idEstadoReserva\n" +
                        "    FROM reservas r\n" +
                        "		INNER JOIN usuarios u ON r.id_usuario = u.id\n" +
                        "		INNER JOIN consolas c ON r.id_consola = c.id\n" +
                        "		WHERE r.id_usuario=?\n" +
                        "		ORDER BY r.hora_inicio ASC ;";
            PreparedStatement stmt = conn.prepareStatement(sql); // preparar consulta
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery(); // ejecutar consulta

            while (rs.next()) { // recorrer resultados
                // crear objeto ReservaDTO con los datos obtenidos
                ReservaDTO reserva = new ReservaDTO(
                    rs.getInt("id"),
                    rs.getLong("documentoUsuario"),
                    rs.getString("nombreUsuario"),
                    rs.getTimestamp("hora_inicio").toLocalDateTime(),
                    rs.getTimestamp("hora_finalizacion").toLocalDateTime(),
                    rs.getString("nombreConsola"),
                    rs.getInt("idEstadoReserva")
                );
                lista.add(reserva); // agregar objeto a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // manejo de errores
        }

        return lista; // retornar lista con info extendida
    }
    

    // método para insertar una nueva reserva en la base de datos
    public boolean post(Reserva reserva) {
        boolean exito = false; // variable para indicar éxito de la operación

        try (Connection conn = ConexionBD.getConnection()) { // abrir conexión
            // consulta SQL para insertar una nueva reserva con los campos necesarios
            String sql = "INSERT INTO reservas (id_usuario, id_consola, id_estado_reserva, hora_inicio, hora_finalizacion) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql); // preparar consulta

            // asignar valores de los parámetros en la consulta
            stmt.setInt(1, reserva.getId_usuario());
            stmt.setInt(2, reserva.getId_consola());
            stmt.setInt(3, reserva.getId_estado_reserva());
            stmt.setTimestamp(4, Timestamp.valueOf(reserva.getHora_inicio()));
            stmt.setTimestamp(5, Timestamp.valueOf(reserva.getHora_finalizacion()));

            int filas = stmt.executeUpdate(); // ejecutar consulta y obtener número de filas afectadas
            exito = filas > 0; // si se afectó al menos una fila, la inserción fue exitosa

        } catch (SQLException e) {
            e.printStackTrace(); // manejo de excepción
        }

        return exito; // retornar resultado de la inserción
    }
    
    // método para obtener reservas por id de consola
    public List<Reserva> getByIdConsola(int idConsola) {
        List<Reserva> lista = new ArrayList<>(); // lista para almacenar reservas

        try (Connection conn = ConexionBD.getConnection()) { // abrir conexión
            String sql = "SELECT id, id_usuario, id_consola, id_estado_reserva, hora_inicio, hora_finalizacion FROM reservas WHERE id_consola = ?";
            PreparedStatement stmt = conn.prepareStatement(sql); // preparar consulta
            stmt.setInt(1, idConsola); // asignar parámetro idConsola
            ResultSet rs = stmt.executeQuery(); // ejecutar consulta

            while (rs.next()) { // recorrer resultados
                Reserva reserva = new Reserva(
                    rs.getInt("id"),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_consola"),
                    rs.getInt("id_estado_reserva"),
                    rs.getTimestamp("hora_inicio").toLocalDateTime(),
                    rs.getTimestamp("hora_finalizacion").toLocalDateTime()
                );
                lista.add(reserva); // agregar reserva a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // manejar error
        }

        return lista; // retornar lista
    }
    
    // método para obtener una reserva por su id
    public Reserva getById(int id) {
        Reserva reserva = null; // objeto que almacenará la reserva buscada
        String sql = "SELECT * FROM reservas WHERE id = ?"; // consulta para obtener la reserva por id

        try (Connection conn = ConexionBD.getConnection(); // abrir conexión
             PreparedStatement stmt = conn.prepareStatement(sql)) { // preparar consulta

            stmt.setInt(1, id); // asignar parámetro id
            ResultSet rs = stmt.executeQuery(); // ejecutar consulta

            if (rs.next()) { // si se encontró la reserva
                // crear objeto Reserva con datos obtenidos
                reserva = new Reserva(
                    rs.getInt("id"),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_consola"),
                    rs.getInt("id_estado_reserva"),
                    rs.getTimestamp("hora_inicio").toLocalDateTime(),
                    rs.getTimestamp("hora_finalizacion").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(); // manejar error
        }

        return reserva; // retornar la reserva encontrada o null
    }
    
    // método para obtener todas las reservas de un usuario por su id
    public List<Reserva> getByIdUsuario(int idUsuario) {
        List<Reserva> lista = new ArrayList<>(); // lista para almacenar reservas

        try (Connection conn = ConexionBD.getConnection()) { // abrir conexión
            String sql = "SELECT * FROM reservas WHERE id_usuario = ?"; // consulta para buscar por usuario
            PreparedStatement stmt = conn.prepareStatement(sql); // preparar consulta
            stmt.setInt(1, idUsuario); // asignar parámetro idUsuario
            ResultSet rs = stmt.executeQuery(); // ejecutar consulta

            while (rs.next()) { // recorrer resultados
                Reserva reserva = new Reserva(
                    rs.getInt("id"),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_consola"),
                    rs.getInt("id_estado_reserva"),
                    rs.getTimestamp("hora_inicio").toLocalDateTime(),
                    rs.getTimestamp("hora_finalizacion").toLocalDateTime()
                );
                lista.add(reserva); // agregar reserva a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // manejar error
        }

        return lista; // retornar lista de reservas
    }

    // método para actualizar una reserva existente
    public boolean put(Reserva reserva) {
        boolean exito = false; // variable para indicar éxito de la actualización

        try (Connection conn = ConexionBD.getConnection()) { // abrir conexión
            // consulta para actualizar datos de una reserva según su id
            String sql = "UPDATE reservas SET id_usuario = ?, id_consola = ?, id_estado_reserva = ?, hora_inicio = ?, hora_finalizacion = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql); // preparar consulta
            
            // asignar parámetros de la consulta con los datos de la reserva
            stmt.setInt(1, reserva.getId_usuario());
            stmt.setInt(2, reserva.getId_consola());
            stmt.setInt(3, reserva.getId_estado_reserva());
            stmt.setTimestamp(4, Timestamp.valueOf(reserva.getHora_inicio()));
            stmt.setTimestamp(5, Timestamp.valueOf(reserva.getHora_finalizacion()));
            stmt.setInt(6, reserva.getId());

            int filas = stmt.executeUpdate(); // ejecutar actualización y obtener filas afectadas
            exito = filas > 0; // si se afectó al menos una fila, la actualización fue exitosa

        } catch (SQLException e) {
            e.printStackTrace(); // manejar error
        }

        return exito; // retornar resultado
    }
    
    // método para eliminar una reserva por su id
    public boolean eliminarReserva(int id) {
        try (Connection conn = ConexionBD.getConnection()) { // abrir conexión
            String sql = "DELETE FROM reservas WHERE id = ?"; // consulta para eliminar reserva por id
            PreparedStatement stmt = conn.prepareStatement(sql); // preparar consulta
            stmt.setInt(1, id); // asignar parámetro id

            int filas = stmt.executeUpdate(); // ejecutar eliminación y obtener filas afectadas
            return filas > 0; // retorna true si se eliminó alguna fila

        } catch (SQLException e) {
            e.printStackTrace(); // manejar error
            return false; // en caso de error retornar falso
        }
    }
}
