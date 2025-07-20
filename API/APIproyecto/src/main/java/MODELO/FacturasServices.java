/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.time.temporal.ChronoUnit;

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

    public FacturaDTO obtenerOCrearFactura(int idReserva) {
        //obtenemos la factura para esa reserva
        Factura facturaExistente = facturasDAO.getByReserva(idReserva);

        //si la factura existe se retorna con el DTO
        if (facturaExistente != null) {
            return construirFacturaDTO(idReserva);
        }

        //se obtiene la reserva
        Reserva reserva = reservasDAO.getById(idReserva);
        if (reserva == null) return null;//si la reserva no se encuentra se retorna null

        //se calculan los minutos consumidos
        long minutosConsumidos = ChronoUnit.MINUTES.between(reserva.getHora_inicio(), reserva.getHora_finalizacion());
        
        //se obtiene la consona
        Consola consola = consolasDAO.getById(reserva.getId_consola());
        if (consola == null) return null;//en caso de que no se encuentre la consola se retorna null

        Tipo tipo = tiposDAO.getById(consola.getId_tipo());//se obtiene el tipo de la consola
        if (tipo == null) return null;//si el tipo no se encuentra se retorna null

        double precioPorMinuto = tipo.getPrecio_hora() / 60;//se calcula el precio por minuto
        double totalTiempo = minutosConsumidos * precioPorMinuto;//se calcula el total a pagar por el tiempo de uso
        double totalProductos = consumosDAO.calcularTotalPorReserva(idReserva);//se calcula el total de los consumos
        double totalGeneral = totalTiempo + totalProductos;//se calcula el total general

        //se agrega a la tabla facturas la nueva factura
        int idFactura = facturasDAO.post(idReserva, totalGeneral);
        if (idFactura == -1) return null;//si el id de la factura es -1 re retorna null
        
        //se retorna la factura en formato DTO
        return new FacturaDTO(reserva.getId(), minutosConsumidos, totalTiempo, totalProductos, totalGeneral);
    }

    public FacturaDTO construirFacturaDTO(int idReserva) {
        //se obtiene la reserva
        Reserva reserva = reservasDAO.getById(idReserva);
        //si la reserva no existe se reorna null
        if (reserva == null) return null;
        
        //se calculan los minutos de uso de la consola
        long minutosConsumidos = ChronoUnit.MINUTES.between(reserva.getHora_inicio(), reserva.getHora_finalizacion());
        //se obtiene la consola
        Consola consola = consolasDAO.getById(reserva.getId_consola());
        //en caso de que la consola no exista se retorna null
        if (consola == null) return null;
        
        //se obtiene el tipo de la consola
        Tipo tipo = tiposDAO.getById(consola.getId_tipo());
        //si el tipo de la consola no existe se retorrna null
        if (tipo == null) return null;
        
        Factura factura=facturasDAO.getByReserva(idReserva);
        if(factura==null)return null;
        
        //se calcula el precio por minuto
        double precioPorMinuto = tipo.getPrecio_hora() / 60.0;
        //se calcula el total de los minutos de uso de la consola
        double totalTiempo = minutosConsumidos * precioPorMinuto;
        //se calcula el total de los productos consumidos
        double totalProductos = consumosDAO.calcularTotalPorReserva(idReserva);
        
        
        //se calcula el total de los consumos mas el tiempo
        double total=totalTiempo+totalProductos;

        //se retorna la facturaDTO
        return new FacturaDTO(factura.getId(), minutosConsumidos, totalTiempo, totalProductos, total);
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
