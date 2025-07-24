/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 *
 * @author Brayan Estiven
 */
public class FacturasServices {

        
     FacturasDAO facturasDAO = new FacturasDAO();//se crea la instancia del objeto de las clases DAO
     ReservasDAO reservasDAO = new ReservasDAO();
     ConsolasDAO consolasDAO = new ConsolasDAO();
     TiposDAO tiposDAO = new TiposDAO();
     ConsumosDAO consumosDAO = new ConsumosDAO();
     ProductosDAO productosDAO=new ProductosDAO();

    public FacturaDTO obtenerOCrearFactura(int idReserva) {
        Factura facturaExistente = facturasDAO.getByReserva(idReserva);

        if (facturaExistente != null) {
            return construirFacturaDTO(idReserva); // Usamos tu método de siempre si ya existe
        }

        Reserva reserva = reservasDAO.getById(idReserva);
        if (reserva == null) return null;

        long minutosConsumidos = ChronoUnit.MINUTES.between(reserva.getHora_inicio(), reserva.getHora_finalizacion());

        Consola consola = consolasDAO.getById(reserva.getId_consola());
        if (consola == null) return null;

        Tipo tipo = tiposDAO.getById(consola.getId_tipo());
        if (tipo == null) return null;

        double precioHora = tipo.getPrecio_hora();
        double precioPorMinuto = precioHora / 60.0;
        double subtotalConsola = minutosConsumidos * precioPorMinuto;

        // Obtener consumos actuales
        ConsumosDAO consumosDAO = new ConsumosDAO();
        List<Consumo> listaConsumos = consumosDAO.getByIdReserva(idReserva);

        double subtotalProductos = 0;

        // DAO para registrar detalle de consumos con precio congelado
        DetalleFacturaConsumosDAO detalleDAO = new DetalleFacturaConsumosDAO();

        // Primero insertamos la factura sin detalle
        int idFactura = facturasDAO.post(
            idReserva,
            (int) minutosConsumidos,
            precioHora,
            subtotalConsola,
            0, // temporal
            0  // temporal
        );

        if (idFactura == -1) return null;

        // Ahora agregamos los detalles de productos y calculamos subtotalProductos
        for (Consumo consumo : listaConsumos) {
            Producto producto = productosDAO.getById(consumo.getId_producto()); // Asegúrate de tener este método
            if (producto == null) continue;

            double precioUnitario = producto.getPrecio();
            double subtotal = precioUnitario * consumo.getCantidad();
            subtotalProductos += subtotal;

            detalleDAO.insertar(
                idFactura,
                producto.getId(),
                producto.getNombre(),
                consumo.getCantidad(),
                precioUnitario,
                subtotal
            );
        }

        double total = subtotalConsola + subtotalProductos;

        // Actualizamos la factura con los subtotales reales
        facturasDAO.actualizarTotales(idFactura, subtotalProductos, total);

        return new FacturaDTO(idFactura, minutosConsumidos, subtotalConsola, subtotalProductos, total);
    }

    public FacturaDTO construirFacturaDTO(int idReserva) {
        // Buscar si ya existe una factura creada
        Factura factura = facturasDAO.getByReserva(idReserva);
        if (factura == null) return null;

        // Usamos directamente los datos guardados en la factura, sin recalcular nada
        int minutosConsumidos = factura.getMinutos();
        double subtotalConsola = factura.getSubtotalConsola();
        double subtotalConsumos = factura.getSubtotalConsumos();
        double total = factura.getTotal();

        // Construimos y devolvemos el DTO
        return new FacturaDTO(
            factura.getId(),
            minutosConsumidos,
            subtotalConsola,
            subtotalConsumos,
            total
        );
    }
    
    public boolean cobrarFactura(int idFactura, int idMetodoPago) {
        //se obtiene la factura por id
        Factura factura = facturasDAO.getById(idFactura);
        if (factura == null) return false;//si no se encuentra la factura se retorna falso

        int idReserva = factura.getIdReserva();//se obtiene el id de la reserva

        // se obtiene la reserva por ID
        Reserva reserva = reservasDAO.getById(idReserva);
        if (reserva == null) return false;//en caso de no encontrarse la reserva se retorna falso
        
        // se cambia el estado de la reserva a 4(pagada)
        int nuevoEstado=4;
        reserva.setId_estado_reserva(nuevoEstado);
        //se alcualiza la reserva
        boolean reservaActualizada = reservasDAO.put(reserva);
        if (!reservaActualizada) return false;//si la reserva no se actualiza correctamente se retorna falso

        // Registrar el pago
        PagosDAO pagosDAO = new PagosDAO();
        //se registra el pago
        boolean pagoRegistrado = pagosDAO.post(idFactura, idMetodoPago);
        if (!pagoRegistrado) return false;//si el pago no se registoro correctamente se retorna falso

        // Registrar en historial
        HistorialDAO historialDAO = new HistorialDAO();
        boolean historialRegistrado = historialDAO.post(idReserva);

        return historialRegistrado;//se retorna la variabel historialRegistrado
    }

}
