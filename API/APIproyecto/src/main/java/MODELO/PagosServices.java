
package MODELO;

import java.util.ArrayList;
import java.util.List;

public class PagosServices {
    //se crean las inatsncias de las clases DAO
    private final PagosDAO pagosDAO = new PagosDAO();
    private final FacturasDAO facturasDAO = new FacturasDAO();
    private final ReservasDAO reservasDAO = new ReservasDAO();
    private final UsuariosDAO usuariosDAO = new UsuariosDAO();
        private final ConsolasDAO consolasDAO=new ConsolasDAO();

    

    public double obtenerTotalPagadoPorMetodo(int idMetodoPago) {
        //se crea el metodo que va a obtener el total que se ha pagaso por el metodo de pago
        double total = 0.0;//se declara la variabel total y se inicializa en 0.0

        //se obtienen los pagos por el id de metodo
        List<Pago> pagos = pagosDAO.obtenerPagosPorMetodo(idMetodoPago);

        for (Pago pago : pagos) {//se recorre cada uno de los pagos
            Factura factura = facturasDAO.getById(pago.getId_factura());//se obtiene la factura ssociada a ese pago
            if (factura != null) {
                //si la facrtura existe se obtiene el total de esta y se sima a la varibale total
                total += factura.getTotal();
            }
        }

        return total;//se retorna l avariable total
    }
    
    public List<ReservaDTO> obtenerReservasPorMetodoPago(int idMetodoPago) {
        //se crea el metodo que va a obtener las reservas pagadas depeneidneo del metodo
        List<ReservaDTO> reservas = new ArrayList<>();//se crea una lista en la cual se vana  alamcenar las reeservas
        List<Pago> pagos = pagosDAO.obtenerPagosPorMetodo(idMetodoPago);//se obtienen todos los pagos realizados por
                                                                       //metodo especifico
        //se recorre cada uno de los pagos
        for (Pago pago : pagos) {
            //se obtiene la factura asociada a ese pago
            Factura factura = facturasDAO.getById(pago.getId_factura());

            if (factura != null) {
                //si la factura existe se obtiene la reserva asociada a esa factura
                Reserva r = reservasDAO.getById(factura.getIdReserva());
                if (r != null && !reservas.contains(r)) {
                    
                    //si la reserva existe se obtiene el usuario y la consola asociadas a esta
                    Usuario u=usuariosDAO.getById(r.getId_usuario());
                    Consola c=consolasDAO.getById(r.getId_consola());
                    
                    //se arma un objeto ReservaDTO y se agrega a la lista de reservas
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

        return reservas;//se retorna la lista de reservas
    }
}
