
package MODELO;

import java.util.ArrayList;
import java.util.List;

public class PagosServices {
    private final PagosDAO pagosDAO = new PagosDAO();
    private final FacturasDAO facturasDAO = new FacturasDAO();
    private final ReservasDAO reservasDAO = new ReservasDAO();
    private final UsuariosDAO usuariosDAO = new UsuariosDAO();
        private final ConsolasDAO consolasDAO=new ConsolasDAO();

    

    public double obtenerTotalPagadoPorMetodo(int idMetodoPago) {
        double total = 0.0;

        List<Pago> pagos = pagosDAO.obtenerPagosPorMetodo(idMetodoPago);

        for (Pago pago : pagos) {
            Factura factura = facturasDAO.getById(pago.getId_factura());
            if (factura != null) {
                total += factura.getTotal();
            }
        }

        return total;
    }
    
    public List<ReservaDTO> obtenerReservasPorMetodoPago(int idMetodoPago) {
        List<ReservaDTO> reservas = new ArrayList<>();
        List<Pago> pagos = pagosDAO.obtenerPagosPorMetodo(idMetodoPago);

        for (Pago pago : pagos) {
            Factura factura = facturasDAO.getById(pago.getId_factura());

            if (factura != null) {
                Reserva r = reservasDAO.getById(factura.getIdReserva());
                if (r != null && !reservas.contains(r)) {
                    
                    Usuario u=usuariosDAO.getById(r.getId_usuario());
                    Consola c=consolasDAO.getById(r.getId_consola());
                    
                    ReservaDTO dto = new ReservaDTO(
                        r.getId(),
                        u.getDocumento(),
                        u.getNombre(),
                        r.getHora_inicio(),
                        r.getHora_finalizacion(),
                        c.getNombre(),
                        r.getId_estado_reserva()
                    );
                    
                    reservas.add(dto);
                }
            }
        }

        return reservas;
    }
}
