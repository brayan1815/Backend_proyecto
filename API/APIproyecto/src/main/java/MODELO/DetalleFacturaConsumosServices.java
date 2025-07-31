
package MODELO;

import java.util.ArrayList;
import java.util.List;


public class DetalleFacturaConsumosServices {
    ReservasDAO reservaDAO = new ReservasDAO();
    FacturasDAO facturaDAO = new FacturasDAO();
    DetalleFacturaConsumosDAO detalleDAO = new DetalleFacturaConsumosDAO();

    public List<DetalleFacturaConsumos> obtenerConsumosPorIdReserva(int idReserva) {
        List<DetalleFacturaConsumos> listaConsumos = new ArrayList<>();

        // Paso 1: Obtener la reserva por ID
        Reserva reserva = reservaDAO.getById(idReserva);

        if (reserva != null) {
            // Paso 2: Obtener la factura asociada a la reserva
            Factura factura = facturaDAO.getByReserva(idReserva);

            if (factura != null) {
                // Paso 3: Obtener los consumos asociados a la factura
                listaConsumos = detalleDAO.getByIdFactura(factura.getId());
            }
        }

        return listaConsumos; // Puede venir vacía si no se encuentra la reserva o la factura
    }
}
