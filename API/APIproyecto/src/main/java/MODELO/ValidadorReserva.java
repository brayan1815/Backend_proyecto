package MODELO;

import java.time.LocalDateTime;
import java.util.List;

public class ValidadorReserva {
    
    // Método para validar los datos básicos de una reserva
    public static String validar(Reserva reserva) {
        // Validar que el id de usuario sea mayor a 0
        if (reserva.getId_usuario() <= 0) {
            return "El ID de usuario es obligatorio y debe ser mayor a 0.";
        }

        // Validar que el id de consola sea mayor a 0
        if (reserva.getId_consola() <= 0) {
            return "El ID de consola es obligatorio y debe ser mayor a 0.";
        }

        // Validar que el estado de la reserva sea mayor a 0
        if (reserva.getId_estado_reserva() <= 0) {
            return "El estado de la reserva es obligatorio.";
        }

        // Obtener las fechas de inicio y finalización
        LocalDateTime horaInicio = reserva.getHora_inicio();
        LocalDateTime horaFinalizacion = reserva.getHora_finalizacion();

        // Validar que las fechas no sean nulas
        if (horaInicio == null || horaFinalizacion == null) {
            return "Las fechas de inicio y finalización no pueden estar vacías.";
        }

        // Validar que la hora de inicio sea antes que la hora de finalización
        if (!horaInicio.isBefore(horaFinalizacion)) {
            return "La hora de inicio debe ser menor que la hora de finalización.";
        }

        // Si pasa todas las validaciones, retornar null (sin error)
        return null; 
    }
    
    // Método para validar si una reserva puede eliminarse, según la diferencia con la hora actual
    public static String puedeEliminar(Reserva reserva) {
        LocalDateTime ahora = LocalDateTime.now(); // obtener hora actual
        LocalDateTime inicio = reserva.getHora_inicio(); // obtener hora de inicio de la reserva

        // Validar que la hora de inicio sea válida
        if (inicio == null) {
            return "La reserva no tiene fecha de inicio válida.";
        }

        // Validar que la reserva se elimine al menos 1 hora antes del inicio
        if (ahora.plusHours(1).isAfter(inicio)) {
            return "La reserva solo se puede cancelar al menos 1 hora antes de su inicio.";
        }

        // Si cumple la condición, se puede eliminar (retorna null)
        return null; 
    }
    
    // Método para validar si un usuario puede hacer una nueva reserva (máximo 5 activas)
    public static String puedeReservar(Reserva reserva){
        ReservasDAO dao = new ReservasDAO(); // instancia para acceder a datos de reservas
        
        // Obtener todas las reservas activas del usuario
        List<Reserva> reservas = dao.getAllReservasActivasUsuario(reserva.getId_usuario());
        int cantidad = reservas.size(); // contar reservas activas

        // Validar que no tenga más de 5 reservas activas
        if (cantidad >= 5) {
            return "El usuario no puede tener más de cinco reservas activas";
        } else {
            return null; // puede reservar
        }
    }
}
