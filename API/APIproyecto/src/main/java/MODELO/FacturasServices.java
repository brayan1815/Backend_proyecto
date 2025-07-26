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
     ProductosDAO productosDAO=new ProductosDAO();

    public FacturaDTO obtenerOCrearFactura(int idReserva) {
        //se crea el metodo para obtener o crear la factura
        Factura facturaExistente = facturasDAO.getByReserva(idReserva);//se obtiene la factura por id de reserva

        if (facturaExistente != null) {
            return construirFacturaDTO(idReserva); //si la factura existe se construye un DTO y se retorna
        }
        //en caso de que la factura no exista
        //se obtiene la reserva por id
        Reserva reserva = reservasDAO.getById(idReserva);
        if (reserva == null) return null;//si la reserva no existe se retorna null
        
        //se calculan los minutos que se uso la consola y se almacena ne la variable minutosConsumidos
        long minutosConsumidos = ChronoUnit.MINUTES.between(reserva.getHora_inicio(), reserva.getHora_finalizacion());
        
        //se obtiene la consola por id
        Consola consola = consolasDAO.getById(reserva.getId_consola());
        if (consola == null) return null;//si la consola no existe se retorna null
        
        //se obtiene el tipo por id
        Tipo tipo = tiposDAO.getById(consola.getId_tipo());
        if (tipo == null) return null;//si el tipo no existe se retorna null

        
        double precioHora = tipo.getPrecio_hora();//se obtiene el precio por hora de la consola
        double precioPorMinuto = precioHora / 60.0;//se calcula el precio de cada uno de los minutos
        double subtotalConsola = minutosConsumidos * precioPorMinuto;//se calcula el subtotal del uso de la consola

        // Obtener consumos actuales
        ConsumosDAO consumosDAO = new ConsumosDAO();
        List<Consumo> listaConsumos = consumosDAO.getByIdReserva(idReserva);//se obtienen los consumos

        double subtotalProductos = 0;//se declara la variable subtotalProductos y se inicializa en 0

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

        if (idFactura == -1) return null;//si la factura no se creo se retorna null

        // Ahora agregamos los detalles de productos y calculamos subtotalProductos
        for (Consumo consumo : listaConsumos) {
            Producto producto = productosDAO.getById(consumo.getId_producto());//se obtiene el producto del consumo
            if (producto == null) continue;//si el producto no existe se pasa al siguiente

            double precioUnitario = producto.getPrecio();//se obtiene el precio unitario del producto
            double subtotal = precioUnitario * consumo.getCantidad();//se calcula el subtotal
            subtotalProductos += subtotal;//se suma a la variable subtotal productos el subtotal de ese producto

            detalleDAO.insertar(//se inserta en los detalles de consumos de factura el consumo
                idFactura,
                producto.getId(),
                producto.getNombre(),
                consumo.getCantidad(),
                precioUnitario,
                subtotal
            );
        }

        double total = subtotalConsola + subtotalProductos;//se calcula el total a pagar por la resevra

        // Actualizamos la factura con los subtotales reales
        facturasDAO.actualizarTotales(idFactura, subtotalProductos, total);
        //se retorna la facturaDTO
        return new FacturaDTO(idFactura, minutosConsumidos, subtotalConsola, subtotalProductos, total);
    }

    public FacturaDTO construirFacturaDTO(int idReserva) {
        //se obtiene la factura por ID de reserva
        Factura factura = facturasDAO.getByReserva(idReserva);
        if (factura == null) return null;//si la factura no existe se retorna null

        //se obtienen los datos de la factura
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
        return pagoRegistrado;//se retorna la variabel pagoRegistrado
    }

}
