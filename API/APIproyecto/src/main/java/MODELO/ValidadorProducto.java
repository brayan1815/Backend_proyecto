package MODELO;

public class ValidadorProducto {
    
    // Método estático para validar los datos de un objeto Producto
    public static String validarProducto(Producto producto) {

        // Validar que el nombre no sea nulo ni vacío (quitando espacios)
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            return "{\"error\": \"El nombre no puede estar vacío.\"}";
        }
        // Validar que el nombre solo contenga letras (incluyendo tildes y ñ) y espacios
        if (!producto.getNombre().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            return "{\"error\": \"El nombre solo puede contener letras.\"}";
        }
        // Validar que el nombre no tenga más de 30 caracteres
        if (producto.getNombre().length() > 30) {
            return "{\"error\": \"El nombre no puede tener más de 30 caracteres.\"}";
        }

        // Validar que la descripción no sea nula ni vacía (quitando espacios)
        if (producto.getDescripcion() == null || producto.getDescripcion().trim().isEmpty()) {
            return "{\"error\": \"La descripción no puede estar vacía.\"}";
        }
        // Validar que la descripción tenga mínimo 15 caracteres
        if (producto.getDescripcion().length() < 15) {
            return "{\"error\": \"La descripción debe tener al menos 15 caracteres.\"}";
        }

        // Validar que el precio sea mayor que cero
        if (producto.getPrecio() <= 0) {
            return "{\"error\": \"El precio debe ser mayor que 0.\"}";
        }

        // Validar que la cantidad disponible no sea negativa
        if (producto.getCantidades_disponibles() < 0) {
            return "{\"error\": \"La cantidad disponible no puede ser negativa.\"}";
        }

        // Validar que el id del estado del producto sea mayor que cero
        if (producto.getId_estado_producto() <= 0) {
            return "{\"error\": \"El id del estado del producto no puede estar vacío.\"}";
        }

        // Validar que el id de la imagen sea mayor que cero
        if (producto.getId_imagen() <= 0) {
            return "{\"error\": \"El id de la imagen no puede estar vacío.\"}";
        }

        // Si pasa todas las validaciones, se retorna null (sin errores)
        return null;
    }
}
