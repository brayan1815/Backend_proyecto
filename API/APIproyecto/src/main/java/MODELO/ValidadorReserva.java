
package MODELO;

import java.time.LocalDateTime;

public class ValidadorReserva {
    public static String validar(Reserva reserva) {
        if (reserva.getId_usuario() <= 0) {
            return "El ID de usuario es obligatorio y debe ser mayor a 0.";
        }

        if (reserva.getId_consola() <= 0) {
            return "El ID de consola es obligatorio y debe ser mayor a 0.";
        }

        if (reserva.getId_estado_reserva() <= 0) {
            return "El estado de la reserva es obligatorio.";
        }

        LocalDateTime horaInicio = reserva.getHora_inicio();
        LocalDateTime horaFinalizacion = reserva.getHora_finalizacion();

        if (horaInicio == null || horaFinalizacion == null) {
            return "Las fechas de inicio y finalización no pueden estar vacías.";
        }

        if (!horaInicio.isBefore(horaFinalizacion)) {
            return "La hora de inicio debe ser menor que la hora de finalización.";
        }

        return null; // Todo correcto
    }
    
    public static String puedeEliminar(Reserva reserva) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime inicio = reserva.getHora_inicio();

        if (inicio == null) {
            return "La reserva no tiene fecha de inicio válida.";
        }

        // Debe haber al menos 1 hora de diferencia entre ahora e inicio
        if (ahora.plusHours(1).isAfter(inicio)) {
            //se valida que haya una hora entre la hora de iniio y la hora actual
            return "La reserva solo se puede cancelar al menos 1 hora antes de su inicio.";
        }

        return null; // Se puede eliminar
    }
}
