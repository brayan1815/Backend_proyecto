
package MODELO;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ReservasServices {
    ReservasDAO reservaDAO = new ReservasDAO();
    UsuariosDAO usuarioDAO = new UsuariosDAO();
    ConsolasDAO consolaDAO = new ConsolasDAO();
    TiposDAO tipoDAO=new TiposDAO();
    ConsumosDAO consumoDAO=new ConsumosDAO();
    
    
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

            if (estadoActual != nuevoEstado && estadoActual!=4) {
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
    
    public FacturaDTO generarFactura(int idReserva) {
        Reserva reserva = reservaDAO.getById(idReserva);//se obtiene la reserva
        
        //se calculan los minutos consumidos
        long minutosConsumidos = ChronoUnit.MINUTES.between(reserva.getHora_inicio(), reserva.getHora_finalizacion());
        //se obtiene la consola
        Consola consola = consolaDAO.getById(reserva.getId_consola());
        Tipo tipo = tipoDAO.getById(consola.getId_tipo());//se obtiene el tipo de la consola

        double precioPorMinuto = tipo.getPrecio_hora() / 60;//se calcula el precio del minuto
        double totalTiempo = minutosConsumidos * precioPorMinuto;//se calcula el total a pagar con base en los minutos

        double totalProductos = consumoDAO.calcularTotalPorReserva(idReserva);//se calcula el total de los consumos
        double totalGeneral = totalTiempo + totalProductos;//se calcula el total general (tiempo + consumos)

        // Guardamos la factura en la base de datos
        FacturasDAO facturaDAO = new FacturasDAO();
        facturaDAO.post(idReserva, totalGeneral);

        //se retorna la facturaDTO
        return new FacturaDTO(idReserva,minutosConsumidos, totalTiempo, totalProductos, totalGeneral);
    }
    
    public List<ReservaDTO> obtenerReservasPorUsuario(int idUsuario) {
        List<Reserva> reservas = reservaDAO.getByIdUsuario(idUsuario);
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
