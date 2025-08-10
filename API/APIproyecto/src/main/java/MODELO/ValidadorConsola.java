package MODELO;


public class ValidadorConsola {
    
    // Método estático para validar los datos de un objeto Consola
    public static String validarConsola(Consola consola) {
        ConsolasDAO dao = new ConsolasDAO(); // Instancia DAO para consultar en base de datos

        // Validar que el nombre no sea nulo ni vacío (quitando espacios en blanco)
        if (consola.getNombre() == null || consola.getNombre().trim().isEmpty()) {
            return "{\"error\": \"El nombre no puede estar vacío.\"}";
        }
        // Validar que el nombre solo contenga letras (incluye tildes y ñ) y espacios
        if (!consola.getNombre().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            return "{\"error\": \"El nombre solo puede contener letras.\"}";
        }
        // Validar que el nombre no tenga más de 30 caracteres
        if (consola.getNombre().length() > 30) {
            return "{\"error\": \"El nombre no puede tener más de 30 caracteres.\"}";
        }

        // Validar que la descripción no sea nula ni vacía (quitando espacios)
        if (consola.getDescripcion() == null || consola.getDescripcion().trim().isEmpty()) {
            return "{\"error\": \"La descripción no puede estar vacía.\"}";
        }
        // Validar que la descripción tenga mínimo 15 caracteres
        if (consola.getDescripcion().length() < 15) {
            return "{\"error\": \"La descripción debe tener mínimo 15 caracteres.\"}";
        }

        // Validar que el id_tipo sea mayor a 0 (no nulo ni cero)
        if (consola.getId_tipo() <= 0) {
            return "{\"error\": \"El id_tipo no puede estar vacío o en cero.\"}";
        }

        // Validar que el id_estado sea mayor a 0 (no nulo ni cero)
        if (consola.getId_estado() <= 0) {
            return "{\"error\": \"El id_estado no puede estar vacío o en cero.\"}";
        }

        // Validar que el id_imagen sea mayor a 0 (no nulo ni cero)
        if (consola.getId_imagen() <= 0) {
            return "{\"error\": \"El id_imagen no puede estar vacío o en cero.\"}";
        }
        
        // Validar que el número de serie sea único: se busca en la BD si ya existe otra consola con ese número de serie diferente a esta consola
        Consola cons = dao.getByNumeroSerie(consola.getNumero_serie());
        if (cons != null && cons.getId() != consola.getId()) {
            return "{\"error\": \"Ya se encuentra una consola registrada con este numero de serie.\"}";
        }

        // Si pasa todas las validaciones, se retorna null (sin errores)
        return null;
    }

}
