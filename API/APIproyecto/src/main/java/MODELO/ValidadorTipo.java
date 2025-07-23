package MODELO;


public class ValidadorTipo {
    
    public static String validarTipo(Tipo tipo) {

        //se valida que ele campo tipo no este vacio
        if (tipo.getTipo() == null || tipo.getTipo().trim().isEmpty()) {
            return "El campo 'tipo' no puede estar vacío.";
        }

        //se convierte el campo precio a String
        String precioStr = String.valueOf((int) tipo.getPrecio_hora());

        // Validar que solo contenga números
        if (!precioStr.matches("\\d+")) {
            return "El precio por hora debe contener solo números.";
        }

        // Validar longitud máxima de 4 caracteres
        if (precioStr.length() > 4 && precioStr.length() <4) {
            return "El precio por hora debe tener 4 dígitos.";
        }

        return null; //si pasa todos los filtros se retorna null
    }
}
