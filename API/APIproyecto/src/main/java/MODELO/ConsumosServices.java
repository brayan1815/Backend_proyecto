
package MODELO;

import java.util.ArrayList;
import java.util.List;




public class ConsumosServices {
    ConsumosDAO daoCons = new ConsumosDAO();
    ProductosDAO daoProd = new ProductosDAO();
    
    private ConsumoDTO convertirDTO(Consumo consumo) {
        Producto producto = daoProd.getById(consumo.getId_producto());
        ConsumoDTO dto = new ConsumoDTO();
        dto.setIdConsumo(consumo.getId());
        dto.setNombreProducto(producto != null ? producto.getNombre() : "Desconocido");
        dto.setCantidad(consumo.getCantidad());
        dto.setSubtotal(consumo.getSubtotal());
        dto.setPrecioProducto(producto != null ? producto.getPrecio() : 0);
        dto.setCantidadRestanteProducto(producto != null ? producto.getCantidades_disponibles(): 0);
        return dto;
    }
    
    public List<ConsumoDTO> obtenerConsumosPorIdReserva(int idReserva) {
        List<Consumo> consumos = daoCons.getByIdReserva(idReserva);
        List<ConsumoDTO> resultado = new ArrayList<>();
        for (Consumo consumo : consumos) {
            resultado.add(convertirDTO(consumo));
        }
        return resultado;
    }
    
    public ConsumoDTO obtenerConsumoPorId(int idConsumo) {
        Consumo consumo = daoCons.getById(idConsumo);
        if (consumo != null) {
            return convertirDTO(consumo);
        }
        return null;
    }
}
