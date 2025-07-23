package MODELO;

public class ValidadorProducto {
     public static String validarProducto(Producto producto) {

        //Se valida que el nombre del producto no exte vacio
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            return "{\"error\": \"El nombre no puede estar vacío.\"}";
        }
        //se valida que el nombre dle producto solo tenga letras
        if (!producto.getNombre().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            return "{\"error\": \"El nombre solo puede contener letras.\"}";
        }
        //se valida que el nombre tenga maximo 30 caracteres
        if (producto.getNombre().length() > 30) {
            return "{\"error\": \"El nombre no puede tener más de 30 caracteres.\"}";
        }

        //Se valida que la descripcion no este vacia
        if (producto.getDescripcion() == null || producto.getDescripcion().trim().isEmpty()) {
            return "{\"error\": \"La descripción no puede estar vacía.\"}";
        }
        
        //se valida que la descripcion tenga minimo 15 caracteres
        if (producto.getDescripcion().length() < 15) {
            return "{\"error\": \"La descripción debe tener al menos 15 caracteres.\"}";
        }

        // se valida que el precio sea mayor que cero
        if (producto.getPrecio() <= 0) {
            return "{\"error\": \"El precio debe ser mayor que 0.\"}";
        }

        // se valida que las cantidades sean mayores que 0
        if (producto.getCantidades_disponibles() < 0) {
            return "{\"error\": \"La cantidad disponible no puede ser negativa.\"}";
        }

        // se valida que el id de estado del producto no este vacio
        if (producto.getId_estado_producto() <= 0) {
            return "{\"error\": \"El id del estado del producto no puede estar vacío.\"}";
        }

        // se valida que el id de la imagen sea mayor que cero
        if (producto.getId_imagen() <= 0) {
            return "{\"error\": \"El id de la imagen no puede estar vacío.\"}";
        }

        return null; //si se pasan los filtros se retorna null
    }
}
