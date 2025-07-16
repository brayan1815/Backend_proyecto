
package MODELO;

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
}
