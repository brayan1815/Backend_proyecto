package MODELO;

import java.time.temporal.ChronoUnit;

public class FacturasServices {

    // Instancia de los DAO necesarios para operaciones sobre facturas, consumos, reservas, consolas y tipos
    FacturasDAO facturasDAO = new FacturasDAO();
    ConsumosDAO consumosDAO = new ConsumosDAO();
    ReservasDAO reservasDAO = new ReservasDAO();
    ConsolasDAO consolasDAO = new ConsolasDAO();
    TiposDAO tiposDAO = new TiposDAO();

    // Método que obtiene una factura asociada a una reserva o la crea si no existe
    public Factura obtenerOCrearFactura(int idReserva) {
        // Buscar si ya existe una factura para la reserva dada
        Factura facturaExistente = facturasDAO.getByReserva(idReserva);

        if (facturaExistente != null) {
            // Si existe la factura, se devuelve tal cual está en la base de datos
            return facturaExistente;
        }

        // Obtener la reserva con el id proporcionado
        Reserva reserva = reservasDAO.getById(idReserva);
        if (reserva == null) return null; // Si no existe la reserva, retornar null

        // Calcular los minutos consumidos entre la hora de inicio y finalización de la reserva
        long minutosConsumidos = ChronoUnit.MINUTES.between(reserva.getHora_inicio(), reserva.getHora_finalizacion());

        // Obtener la consola asociada a la reserva
        Consola consola = consolasDAO.getById(reserva.getId_consola());
        if (consola == null) return null; // Si no existe la consola, retornar null

        // Obtener el tipo de consola para saber el precio por hora
        Tipo tipo = tiposDAO.getById(consola.getId_tipo());
        if (tipo == null) return null; // Si no existe el tipo, retornar null

        // Obtener el precio por hora y calcular precio por minuto
        double precioHora = tipo.getPrecio_hora();
        double precioPorMinuto = precioHora / 60.0;
        // Calcular subtotal de la consola basado en minutos consumidos
        double subtotalConsola = minutosConsumidos * precioPorMinuto;

        // Calcular el subtotal de los productos consumidos en la reserva
        Double subtotalProductos = consumosDAO.calcularTotalPorReserva(idReserva);
        if (subtotalProductos == null) subtotalProductos = 0.0; // Si no hay consumos, subtotal es cero

        // Calcular el total sumando consola y productos
        double total = subtotalConsola + subtotalProductos;

        // Guardar la factura nueva con los datos calculados y obtener su id
        int idFactura = facturasDAO.post(idReserva, (int) minutosConsumidos, subtotalProductos, subtotalConsola, total);

        if (idFactura == -1) return null; // Si la inserción falla, retornar null

        // Retornar la factura recién creada obtenida por su id
        return facturasDAO.getById(idFactura);
    }
    
    // Método para cobrar una factura dada su id y el método de pago usado
    public boolean cobrarFactura(int idFactura, int idMetodoPago) {
        // Obtener la factura por su id
        Factura factura = facturasDAO.getById(idFactura);
        if (factura == null) return false; // Si no existe la factura, retornar falso

        // Obtener la reserva asociada a la factura
        int idReserva = factura.getIdReserva();
        Reserva reserva = reservasDAO.getById(idReserva);
        if (reserva == null) return false; // Si no existe la reserva, retornar falso

        // Cambiar el estado de la reserva a 'pagada'
        reserva.setId_estado_reserva(4);
        // Actualizar la reserva en la base de datos
        boolean reservaActualizada = reservasDAO.put(reserva);
        if (!reservaActualizada) return false; // Si no se pudo actualizar, retornar falso

        // Registrar el pago en la base de datos con el id de factura y método de pago
        PagosDAO pagosDAO = new PagosDAO();
        return pagosDAO.post(idFactura, idMetodoPago); // Retorna true si se registra el pago correctamente
    }

}
