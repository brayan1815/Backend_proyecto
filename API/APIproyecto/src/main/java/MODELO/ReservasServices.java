
package MODELO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservasServices {
    private ReservasDAO reservaDAO = new ReservasDAO();
    private UsuariosDAO usuarioDAO = new UsuariosDAO();
    private ConsolasDAO consolaDAO = new ConsolasDAO();
    
    public List<ReservaDTO> obtenerReservasConDatos() {
        List<Reserva> reservas = reservaDAO.getAll();
        List<ReservaDTO> listaDTO = new ArrayList<>();

        for (Reserva r : reservas) {
            Usuario u = usuarioDAO.getById(r.getId_usuario());
            Consola c = consolaDAO.getById(r.getId_consola());

            if (u != null && c != null) {
                ReservaDTO dto = new ReservaDTO(
                    r.getId(),
                    u.getDocumento(),
                    u.getNombre(),
                    r.getHora_inicio(),
                    r.getHora_finalizacion(),
                    c.getNombre(),
                    r.getId_estado_reserva()
                );
                listaDTO.add(dto);
            }
        }

        return listaDTO;
    }
    
    public List<ReservaDTO> actualizarEstadoReserva(){
        List<Reserva> reservas = reservaDAO.getAll();
        List<ReservaDTO> listaActualizadas = new ArrayList<>();

        LocalDateTime ahora = LocalDateTime.now();

        for (Reserva reserva : reservas) {
            int estadoActual = reserva.getId_estado_reserva();
            int nuevoEstado;

            if (ahora.isBefore(reserva.getHora_inicio())) {
                nuevoEstado = 1; // Pendiente
            } else if (!ahora.isBefore(reserva.getHora_inicio()) && ahora.isBefore(reserva.getHora_finalizacion())) {
                nuevoEstado = 2; // En proceso
            } else {
                nuevoEstado = 3; // Terminada
            }

            if (estadoActual != nuevoEstado) {
                reserva.setId_estado_reserva(nuevoEstado);
                reservaDAO.put(reserva); // Actualizamos en la BD

                Usuario u = usuarioDAO.getById(reserva.getId_usuario());
                Consola c = consolaDAO.getById(reserva.getId_consola());

                if (u != null && c != null) {
                    ReservaDTO dto = new ReservaDTO(
                        reserva.getId(),
                        u.getDocumento(),
                        u.getNombre(),
                        reserva.getHora_inicio(),
                        reserva.getHora_finalizacion(),
                        c.getNombre(),
                        nuevoEstado
                    );
                    listaActualizadas.add(dto);
                }
            }
        }

        return listaActualizadas;
    }
}
