package MODELO;

import java.util.ArrayList;
import java.util.List;

public class ConsumosServices {
    ConsumosDAO daoCons = new ConsumosDAO();//se crea la instancia del objeto de la clase ConsumosDAO
    ProductosDAO daoProd = new ProductosDAO();//se crea la instancia del objeto de la clase ProductosDAO
    
    private ConsumoDTO convertirDTO(Consumo consumo) {
        //se crea el metodo para convertir el objeto consumo a un objeto ConsumoDTO
        Producto producto = daoProd.getById(consumo.getId_producto());//se obtiene el producto por el id del producto almacenado en consumo
        ConsumoDTO dto = new ConsumoDTO();//se crea un objeto de la clase ConsumoDTO
        //se envian los datos al objeto ConsumoDTO
        dto.setIdConsumo(consumo.getId());
        dto.setNombreProducto(producto != null ? producto.getNombre() : "Desconocido");//si el producto no es nulo se le envia su nombre, de lo contrario se envia "Desconocido"
        dto.setCantidad(consumo.getCantidad());
        dto.setSubtotal(consumo.getSubtotal());
        dto.setPrecioProducto(producto != null ? producto.getPrecio() : 0);//si el producto no es nulo se le envia su precio, de lo contrario se envia 0
        dto.setCantidadRestanteProducto(producto != null ? producto.getCantidades_disponibles(): 0);//si el producto no es nulo se le envia su cantidad disponible, de lo contrario se envia 0
        return dto;//se retorna el objeto dto
    }
    
    public List<ConsumoDTO> obtenerConsumosPorIdReserva(int idReserva) {
        //se crea el metodo para obtener todos los consumos por id de reserva
        List<Consumo> consumos = daoCons.getByIdReserva(idReserva);//se obtienen todos los consumos relacionados al id de la reserva
        List<ConsumoDTO> resultado = new ArrayList<>();//se crea la lista resultado en la cual se guardaran los consumos convertidos a DTO
        for (Consumo consumo : consumos) {
            //se recorren todos los consumos, se convierten a DTO y se agregan a la lista resultado
            resultado.add(convertirDTO(consumo));
        }
        return resultado;//se retorna la lista resultado
    }
    
    public ConsumoDTO obtenerConsumoPorId(int idConsumo) {
        //se crea el metodo para obtener un consumo especifico por su ID
        Consumo consumo = daoCons.getById(idConsumo);//se obtiene el consumo por el id
        if (consumo != null) {
            //si el consumo existe se convierte a DTO y se retorna
            return convertirDTO(consumo);
        }
        return null;//si el consumo no existe se retorna null
    }
    
    public boolean editarConsumo(Consumo nuevoConsumo) {
        //se crea el metodo para editar un consumo existente
        ConsumosDAO consumoDAO = new ConsumosDAO();//se crea la instancia de la clase ConsumosDAO
        ProductosDAO productoDAO = new ProductosDAO();//se crea la instancia de la clase ProductosDAO

        Consumo consumoAnterior = consumoDAO.getById(nuevoConsumo.getId());//se obtiene el consumo anterior
        if (consumoAnterior == null) {
            return false; //si el consumo anterior no existe se retorna falso
        }
        
        Producto producto = productoDAO.getById(nuevoConsumo.getId_producto());//se obtiene el producto asociado al consumo
        if(nuevoConsumo.getCantidad() > consumoAnterior.getCantidad()){//si la cantidad del nuevo consumo es mayor a la anterior
            int diferencia = nuevoConsumo.getCantidad() - consumoAnterior.getCantidad();//se calcula la diferencia
            producto.setCantidades_disponibles(producto.getCantidades_disponibles() - diferencia);//se descuentan las cantidades disponibles en el producto
            
            //se valida el estado del producto segun su cantidad
            if(producto.getCantidades_disponibles() == 0){
                producto.setId_estado_producto(2);//producto sin stock
            } else {
                producto.setId_estado_producto(1);//producto con stock
            }
            
            boolean productoActualizado = productoDAO.put(producto);//se actualiza el producto en la base de datos
            if(productoActualizado){
                boolean consumoActualizado = consumoDAO.actualizarConsumo(nuevoConsumo);//se actualiza el consumo
                if(consumoActualizado) return true;//si se actualiza correctamente se retorna verdadero
                else return false;//en caso contrario se retorna falso
            }
            else return false;
        } else {//si la cantidad del nuevo consumo es menor o igual a la anterior
            int diferencia = consumoAnterior.getCantidad() - nuevoConsumo.getCantidad();//se calcula la diferencia
            producto.setCantidades_disponibles(producto.getCantidades_disponibles() + diferencia);//se suman las cantidades al stock del producto
            
            boolean productoActualizado = productoDAO.put(producto);//se actualiza el producto
            if(productoActualizado){
                boolean consumoActualizado = consumoDAO.actualizarConsumo(nuevoConsumo);//se actualiza el consumo
                if(consumoActualizado) return true;//si se actualiza correctamente se retorna verdadero
                else return false;//en caso contrario se retorna falso
            }
            else return false;
        }
    }
    
    public boolean eliminarConsumo(int idConsumo){
        //se crea el metodo para eliminar un consumo existente
        ConsumosDAO consDao = new ConsumosDAO();//se crea la instancia de ConsumosDAO
        ProductosDAO prodDao = new ProductosDAO();//se crea la instancia de ProductosDAO
        
        Consumo consumo = consDao.getById(idConsumo);//se obtiene el consumo por id
        if(consumo == null) return false;//si el consumo no existe se retorna falso
        
        Producto producto = prodDao.getById(consumo.getId_producto());//se obtiene el producto relacionado al consumo
        if(producto == null) return false;//si el producto no existe se retorna falso
        
        int nuevaCantidad = producto.getCantidades_disponibles() + consumo.getCantidad();//se suman las cantidades consumidas al stock
        producto.setCantidades_disponibles(nuevaCantidad);//se actualiza la cantidad disponible del producto
        
        //se valida el estado del producto segun su cantidad disponible
        if(producto.getCantidades_disponibles() > 0){
            producto.setId_estado_producto(1);//producto con stock
        } else {
            producto.setId_estado_producto(2);//producto sin stock
        }
        
        boolean productoActualizado = prodDao.put(producto);//se actualiza el producto en la base de datos
        if (!productoActualizado) return false;//si no se actualiza el producto se retorna falso
        
        boolean consumoEliminado = consDao.del(idConsumo);//se elimina el consumo de la base de datos
        return consumoEliminado;//se retorna el resultado de la eliminacion
    }
}
