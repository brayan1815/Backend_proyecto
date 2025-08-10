package MODELO;

import java.time.LocalDateTime; // clase para manejar fechas y horas
import java.util.ArrayList; // clase para manejar listas dinámicas
import java.util.List; // interfaz para colecciones tipo lista

public class ReservasServices {
    ReservasDAO reservaDAO = new ReservasDAO(); // instancia para acceder a métodos de reservas en BD
    UsuariosDAO usuarioDAO = new UsuariosDAO(); // instancia para acceder a métodos de usuarios en BD
    ConsolasDAO consolaDAO = new ConsolasDAO(); // instancia para acceder a métodos de consolas en BD
    
    // método que actualiza el estado de las reservas según la hora actual
    public List<ReservaDTO> actualizarEstadoReserva(){
        List<Reserva> reservas = reservaDAO.getAll(); // obtener todas las reservas desde la BD
        List<ReservaDTO> listaActualizadas = new ArrayList<>(); // lista para guardar reservas cuyo estado fue actualizado

        LocalDateTime ahora = LocalDateTime.now(); // obtener la fecha y hora actual

        for (Reserva reserva : reservas) { // recorrer todas las reservas obtenidas
            int estadoActual = reserva.getId_estado_reserva(); // obtener el estado actual de la reserva
            int nuevoEstado; // variable para guardar el nuevo estado que se determinará

            if (ahora.isBefore(reserva.getHora_inicio())) {
                nuevoEstado = 1; // si la hora actual es antes del inicio de la reserva, estado = 1 (pendiente)
            } else if (!ahora.isBefore(reserva.getHora_inicio()) && ahora.isBefore(reserva.getHora_finalizacion())) {
                nuevoEstado = 2; // si la hora actual está entre inicio y finalización, estado = 2 (en curso)
            } else {
                nuevoEstado = 3; // si la hora actual es después de la finalización, estado = 3 (finalizada)
            }

            if (estadoActual != nuevoEstado && estadoActual != 4) {
                // si el estado actual es diferente al nuevo y no es 4
                reserva.setId_estado_reserva(nuevoEstado); // actualizar el estado de la reserva
                reservaDAO.put(reserva); // guardar la actualización en la base de datos

                // obtener el usuario asociado a la reserva
                Usuario u = usuarioDAO.getById(reserva.getId_usuario());
                // obtener la consola asociada a la reserva
                Consola c = consolaDAO.getById(reserva.getId_consola());

                if (u != null && c != null) {
                    // crear un objeto DTO con la información actualizada
                    ReservaDTO dto = new ReservaDTO(
                        reserva.getId(),
                        u.getDocumento(),
                        u.getNombre(),
                        reserva.getHora_inicio(),
                        reserva.getHora_finalizacion(),
                        c.getNombre(),
                        nuevoEstado
                    );
                    listaActualizadas.add(dto); // agregar el DTO a la lista de reservas actualizadas
                }
            }
        }

        return listaActualizadas; // retornar la lista con las reservas que fueron actualizadas
    }
    
    // método que obtiene las reservas de un usuario específico y retorna una lista de DTOs con info detallada
    public List<ReservaDTO> obtenerReservasPorUsuario(int idUsuario) {
        List<Reserva> reservas = reservaDAO.getByIdUsuario(idUsuario); // obtener reservas de un usuario
        List<ReservaDTO> listaDTO = new ArrayList<>(); // lista para almacenar los DTOs

        for (Reserva r : reservas) { // recorrer cada reserva obtenida
            // obtener el usuario asociado (debería ser siempre el mismo)
            Usuario u = usuarioDAO.getById(r.getId_usuario());
            // obtener la consola asociada a la reserva
            Consola c = consolaDAO.getById(r.getId_consola());

            if (u != null && c != null) {
                // crear el DTO con la información combinada
                ReservaDTO dto = new ReservaDTO(
                    r.getId(),
                    u.getDocumento(),
                    u.getNombre(),
                    r.getHora_inicio(),
                    r.getHora_finalizacion(),
                    c.getNombre(),
                    r.getId_estado_reserva()
                );
                listaDTO.add(dto); // agregar el DTO a la lista final
            }
        }

        return listaDTO; // retornar la lista con las reservas en formato DTO
    }
}
