package MODELO;

public class ValidadorTipo {
    
    // Método para validar los datos de un objeto Tipo
    public static String validarTipo(Tipo tipo) {
        TiposDAO dao = new TiposDAO(); // Instancia para acceder a datos de tipos

        // Validar que el campo tipo no esté vacío ni nulo
        if (tipo.getTipo() == null || tipo.getTipo().trim().isEmpty()) {
            return "El campo 'tipo' no puede estar vacío.";
        }

        // Convertir el precio por hora a String para validación
        String precioStr = String.valueOf((int) tipo.getPrecio_hora());

        // Validar que el precio solo contenga números (dígitos)
        if (!precioStr.matches("\\d+")) {
            return "El precio por hora debe contener solo números.";
        }

        // Validar que la longitud sea exactamente 4 caracteres
        if (precioStr.length() != 4) {
            return "El precio por hora debe tener 4 dígitos.";
        }
        
        // Buscar si ya existe un tipo con el mismo nombre (para evitar duplicados)
        Tipo tip = dao.getByNombre(tipo.getTipo());
        
        // Validar que si existe un tipo con el mismo nombre, no sea el mismo registro
        if (tip != null && tip.getId() != tipo.getId()) {
            return "Ya se encuentra un tipo registrado con este nombre";
        }

        // Si pasa todas las validaciones, retornar null (sin error)
        return null;
    }
}
