
package MODELO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservasServices {
    ReservasDAO reservaDAO = new ReservasDAO();
    UsuariosDAO usuarioDAO = new UsuariosDAO();
    ConsolasDAO consolaDAO = new ConsolasDAO();
    
    public List<ReservaDTO> obtenerReservasConDatos() {
        //se crea el metodo que me va a obtener las reservas en formato DTO
        List<Reserva> reservas = reservaDAO.getAll();//se obtienenen todas las reservas
        List<ReservaDTO> listaDTO = new ArrayList<>();//se crea la lista donde se almacenaran en formato DTO

        for (Reserva r : reservas) {
            //se recorre cada una de las reservas y se obtiene su usuario y su consola
            Usuario u = usuarioDAO.getById(r.getId_usuario());
            Consola c = consolaDAO.getById(r.getId_consola());

            if (u != null && c != null) {
                //se arma el objeto DTO y se agrega a la lista 
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

        return listaDTO;//se retorna la lista
    }
    
    public List<ReservaDTO> actualizarEstadoReserva(){
        //se crea el metodo que va a actualizar el estado de las reservas
        List<Reserva> reservas = reservaDAO.getAll();//se obtienen todas las reservas
        List<ReservaDTO> listaActualizadas = new ArrayList<>();//se crea la lista donde se almacenaran las reservas actualizacas

        LocalDateTime ahora = LocalDateTime.now();//se obtiene la hora actual

        for (Reserva reserva : reservas) {
            //se recorre cada una de las resercas
            int estadoActual = reserva.getId_estado_reserva();//se obtiene el estado actual se la reserva
            int nuevoEstado;//se declara la variable nuevoEstado

            if (ahora.isBefore(reserva.getHora_inicio())) {
                nuevoEstado = 1; //si la hora actual es antes del inicio de la reserva nuevoEstado es uno
            } else if (!ahora.isBefore(reserva.getHora_inicio()) && ahora.isBefore(reserva.getHora_finalizacion())) {
                nuevoEstado = 2; //si la hora esta entre la hora de inicio y hora de fin el nuevo estado sera 2
            } else {
                nuevoEstado = 3;//si la hora esta despues de la hroa de fin el nueco estado sera 3
            }

            if (estadoActual != nuevoEstado && estadoActual!=4) {
                //si el estado actual es diferente del nuevo y de 4
                //se envia a la reserva el nuevo id de estado
                reserva.setId_estado_reserva(nuevoEstado);
                reservaDAO.put(reserva);//se actualiza la reserva

                Usuario u = usuarioDAO.getById(reserva.getId_usuario());//se obtiene el usuario correspondienye a la reserva
                Consola c = consolaDAO.getById(reserva.getId_consola());//se obtiene la consola correspondiene a la reserva

                if (u != null && c != null) {
                    //se arma el objeto reservDTO y se agrega a la lista de reservasActualizadas
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

        return listaActualizadas;//se retorna la lista de reservas actualizadas
    }
    
    
    public List<ReservaDTO> obtenerReservasPorUsuario(int idUsuario) {
        //se crea el metodo que va a obtener las reservas por usuario
        List<Reserva> reservas = reservaDAO.getByIdUsuario(idUsuario);//se obtienen las reservas por id de usuario
        List<ReservaDTO> listaDTO = new ArrayList<>();//se crea la listaDTO

        for (Reserva r : reservas) {
            //se recorre cada una de las reservas y se obtiene el usuario y la consol asociados
            Usuario u = usuarioDAO.getById(r.getId_usuario());
            Consola c = consolaDAO.getById(r.getId_consola());

            if (u != null && c != null) {
                //se arma el DTO y se almacena en la lista
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

        return listaDTO;//se retorna la lista
    }
}
