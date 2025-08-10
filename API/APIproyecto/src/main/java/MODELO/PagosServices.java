package MODELO;

import java.util.ArrayList;
import java.util.List;

public class PagosServices {
    // se crean las instancias de las clases DAO para manejar datos de pagos, facturas, reservas, usuarios y consolas
    private final PagosDAO pagosDAO = new PagosDAO();
    private final FacturasDAO facturasDAO = new FacturasDAO();
    private final ReservasDAO reservasDAO = new ReservasDAO();
    private final UsuariosDAO usuariosDAO = new UsuariosDAO();
    private final ConsolasDAO consolasDAO = new ConsolasDAO();

    public double obtenerTotalPagadoPorMetodo(int idMetodoPago) {
        // método que obtiene el total pagado usando un método de pago específico
        
        double total = 0.0; // variable que acumulará el total pagado, inicia en 0.0
        
        // se obtienen todos los pagos realizados con el método de pago indicado
        List<Pago> pagos = pagosDAO.obtenerPagosPorMetodo(idMetodoPago);

        for (Pago pago : pagos) { // se recorre cada pago obtenido
            // se obtiene la factura asociada a ese pago
            Factura factura = facturasDAO.getById(pago.getId_factura());
            
            if (factura != null) {
                // si la factura existe, se suma su total al acumulador total
                total += factura.getTotal();
            }
        }

        return total; // se retorna el total pagado por el método especificado
    }
    
    public List<ReservaDTO> obtenerReservasPorMetodoPago(int idMetodoPago) {
        // método que obtiene la lista de reservas pagadas con un método de pago específico
        
        List<ReservaDTO> reservas = new ArrayList<>(); // lista que almacenará los datos de las reservas
        // se obtienen todos los pagos realizados con el método de pago indicado
        List<Pago> pagos = pagosDAO.obtenerPagosPorMetodo(idMetodoPago);

        // se recorre cada pago para obtener su reserva asociada
        for (Pago pago : pagos) {
            // se obtiene la factura asociada a ese pago
            Factura factura = facturasDAO.getById(pago.getId_factura());

            if (factura != null) {
                // si la factura existe, se obtiene la reserva vinculada a esa factura
                Reserva r = reservasDAO.getById(factura.getIdReserva());
                // se verifica que la reserva exista y que no esté ya en la lista para evitar duplicados
                if (r != null && !reservas.contains(r)) {
                    
                    // se obtienen los datos del usuario y la consola asociadas a la reserva
                    Usuario u = usuariosDAO.getById(r.getId_usuario());
                    Consola c = consolasDAO.getById(r.getId_consola());
                    
                    // se crea un objeto ReservaDTO con los datos relevantes de la reserva, usuario y consola
                    ReservaDTO dto = new ReservaDTO(
                        r.getId(),
                        u.getDocumento(),
                        u.getNombre(),
                        r.getHora_inicio(),
                        r.getHora_finalizacion(),
                        c.getNombre(),
                        r.getId_estado_reserva()
                    );
                    
                    // se agrega el objeto DTO a la lista de reservas
                    reservas.add(dto);
                }
            }
        }

        return reservas; // se retorna la lista con las reservas encontradas
    }
}
