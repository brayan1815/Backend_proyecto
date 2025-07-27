
package MODELO;

import java.util.ArrayList;
import java.util.List;




public class ConsumosServices {
    ConsumosDAO daoCons = new ConsumosDAO();
    ProductosDAO daoProd = new ProductosDAO();
    
    private ConsumoDTO convertirDTO(Consumo consumo) {
        //se crea el metodo para convertir el consumo a DTO
        Producto producto = daoProd.getById(consumo.getId_producto());//se obtiene el producto
        ConsumoDTO dto = new ConsumoDTO();//se crea un objeto der la clase consumo dto
        //se envian los datos al objeto consumoDTO
        dto.setIdConsumo(consumo.getId());
        dto.setNombreProducto(producto != null ? producto.getNombre() : "Desconocido");
        dto.setCantidad(consumo.getCantidad());
        dto.setSubtotal(consumo.getSubtotal());
        dto.setPrecioProducto(producto != null ? producto.getPrecio() : 0);
        dto.setCantidadRestanteProducto(producto != null ? producto.getCantidades_disponibles(): 0);
        //se retorna el dto
        return dto;
    }
    
    public List<ConsumoDTO> obtenerConsumosPorIdReserva(int idReserva) {
        //se crea el metodo para obtener los conumso por id de reservba
        List<Consumo> consumos = daoCons.getByIdReserva(idReserva);//se obtienen todos los consumos por el id de reserva
        List<ConsumoDTO> resultado = new ArrayList<>();//se crea la lista resultado
        for (Consumo consumo : consumos) {
            //se recorren los consumos, se convierten a DTO y se agregan a la lista resultado
            resultado.add(convertirDTO(consumo));
        }
        return resultado;//se retorna la lista resultado
    }
    
    public ConsumoDTO obtenerConsumoPorId(int idConsumo) {
        //se crea el metodo para obtener el cosnumo por ID
        Consumo consumo = daoCons.getById(idConsumo);//se obtiene el consumo
        if (consumo != null) {
            //si el consumo existe se convierte a DTO y se retorna
            return convertirDTO(consumo);
        }
        return null;//si el consumo no existe se retorna null
    }
    
    public boolean editarConsumo(Consumo nuevoConsumo) {
        ConsumosDAO consumoDAO = new ConsumosDAO();//se crea la instancia del objeto de la clase consumosDAO
        ProductosDAO productoDAO = new ProductosDAO();//se crea la instancia del objeto de la clase productosDAO

    
        Consumo consumoAnterior = consumoDAO.getById(nuevoConsumo.getId());//se obtiene el consumo anterior
        if (consumoAnterior == null) {
            return false; //en caso de que el consumo no exista se retorna falso
        }
        
        Producto producto = productoDAO.getById(nuevoConsumo.getId_producto());//se obtiene el producto al cual 
                                                                              //se le vana sumar o restar cantidades
                                                                              //en stock
        
        if(nuevoConsumo.getCantidad()>consumoAnterior.getCantidad()){//en caso de que el nuevo consumo sea mayor que 
                                                                    //el anterior
                                                                    
            //se obtiene la diferencia entre el consumo anterior y el nuevo
            int diferencia=nuevoConsumo.getCantidad()-consumoAnterior.getCantidad();
            
            //se envia al producto las nuevas cantidades disponibles
            producto.setCantidades_disponibles(producto.getCantidades_disponibles()-diferencia);
            
            if(producto.getCantidades_disponibles()==0){
                producto.setId_estado_producto(2);
            }
            else{
                producto.setId_estado_producto(1);
            }
            
            //se actualiza el producto en la base de datos
            boolean productoActualizado=productoDAO.put(producto);
            
            
            if(productoActualizado){
                //si el producto en la base de datos se actualizo correctamente se actualiza el consumo
                boolean consumoActualizado=consumoDAO.actualizarConsumo(nuevoConsumo);
                if(consumoActualizado) return true;//si el consumo se actualiza correctamente se retorna true
                else return false;//en caso contrario se retorna falso
            }
            else return false;
        }else{//en caso de que el nuevo conusmo no sea mayor que el anterior
            
            //se calcula la diferencia entre el consumo anterior y el nuevo
            int diferencia=consumoAnterior.getCantidad()-nuevoConsumo.getCantidad();
            
            //se envia al producto las nuevas cantidades disponibles
            producto.setCantidades_disponibles(producto.getCantidades_disponibles()+diferencia);
            
            //se actualiza el producto
            boolean productoActualizado=productoDAO.put(producto);
            if(productoActualizado){
                //en caso de que el producto se haya actualizaco correctamente se actualiza el consumo
                boolean consumoActualizado=consumoDAO.actualizarConsumo(nuevoConsumo);
                //si el consumo se actualiza correctamente se retornba verdadero
                if(consumoActualizado)return true;
                //en caso contrario se retorna falso
                else return false;
            }
            else return false;
        }
    }
    
    public boolean eliminarConsumo(int idConsumo){
        //se crea el servicio que se encargara de eliminar el consumo
        
        //se crean las instancias de las clases ConsumosDAO y ProductosDAO
        ConsumosDAO consDao=new ConsumosDAO();
        ProductosDAO prodDao=new ProductosDAO();
        
        Consumo consumo=consDao.getById(idConsumo);//se obtiene el consumo   
        if(consumo==null) return false;//si no se encuentra el consumo se retorna falso
        
        Producto producto = prodDao.getById(consumo.getId_producto());//se obtiene el producto
        if(producto==null)return false;//si no se encuentra el producto se retorna falso
        
        //se calcula la nueva cantidad de producto disponible, para esto se obtiene la cantidad anterior y se 
        //suma la cantidad de producto que se habia conusmido
        int nuevaCantidad = producto.getCantidades_disponibles() + consumo.getCantidad();
        producto.setCantidades_disponibles(nuevaCantidad);//se envia al producto las nuevas cantidades
        
        if(producto.getCantidades_disponibles()>0){
            producto.setId_estado_producto(1);
        }
        else{
            producto.setId_estado_producto(2);
        }
        
        //se actualiza el producro
        boolean productoActualizado = prodDao.put(producto);
        
        if (!productoActualizado) return false;//si el producto no se actualizo se retorna falso
        
        boolean consumoEliminado = consDao.del(idConsumo);//se elimina el consumo
        return consumoEliminado;//se retorna consumoEliminado
        
    }


}
